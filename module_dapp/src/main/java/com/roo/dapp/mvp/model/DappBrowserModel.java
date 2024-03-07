package com.roo.dapp.mvp.model;

import android.app.Application;

import com.alibaba.fastjson.JSON;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.mine.GasBean;
import com.core.domain.trade.SignatureFromKey;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.SafePassword;
import com.roo.core.utils.utils.Web3jUtil;
import com.roo.dapp.mvp.beans.TransactionData;
import com.roo.dapp.mvp.beans.Web3Transaction;
import com.roo.dapp.mvp.contract.DappBrowserContract;
import com.roo.dapp.mvp.interfaces.Signable;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.Sign;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@FragmentScope
public class DappBrowserModel extends BaseModel implements DappBrowserContract.Model {
    @Inject
    Application mApplication;

    @Inject
    public DappBrowserModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Single<TransactionData> createWithSig(LinkTokenBean.TokensBean tokensBean,
                                                 WalletFile walletFile,
                                                 BigInteger gasPrice,
                                                 BigInteger gasLimit,
                                                 String data,
                                                 int chainId) {
        return createTransactionWithSig(tokensBean, walletFile, gasPrice, gasLimit, data, chainId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<TransactionData> createWithSig(LinkTokenBean.TokensBean tokensBean,
                                                 WalletFile walletFile,
                                                 Web3Transaction web3Tx,
                                                 int chainId) {
        return createTransactionWithSig(tokensBean, walletFile, web3Tx.recipient.toString(), web3Tx.value,
                web3Tx.gasPrice, web3Tx.gasLimit, Numeric.hexStringToByteArray(web3Tx.payload), chainId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<TransactionData> createTransactionWithSig(LinkTokenBean.TokensBean tokensBean,
                                                            WalletFile walletFile,
                                                            String toAddress,
                                                            BigInteger subunitAmount,
                                                            BigInteger gasPrice,
                                                            BigInteger gasLimit,
                                                            byte[] data,
                                                            int chainId) {
        Web3j web3j = getWeb3jService(tokensBean.getChainCode());
        TransactionData txData = new TransactionData();
        return getNonceForTransaction(web3j, walletFile)
                .flatMap(txNonce -> {
                    LogManage.e(Constants.LOG_STRING, "-------nonce-----" + txNonce.longValue());
                    return signTransaction(walletFile, toAddress, subunitAmount, gasPrice, gasLimit, txNonce.longValue(), data, chainId);
                })
                .flatMap(signedMessage -> Single.fromCallable(() -> {
                    txData.signature = Numeric.toHexString(signedMessage.signature);
                    EthSendTransaction raw = web3j.ethSendRawTransaction(txData.signature).send();
                    if (raw.hasError()) {
                        throw new Exception(raw.getError().getMessage());
                    }
                    txData.txHash = raw.getTransactionHash();
                    return txData;
                }))
                .subscribeOn(Schedulers.io());
    }

    // Called for constructors from web3 Dapp transaction
    public Single<TransactionData> createTransactionWithSig(LinkTokenBean.TokensBean tokensBean,
                                                            WalletFile walletFile,
                                                            BigInteger gasPrice,
                                                            BigInteger gasLimit,
                                                            String data,
                                                            int chainId) {
        Web3j web3j = getWeb3jService(tokensBean.getChainCode());

        TransactionData txData = new TransactionData();

        return getNonceForTransaction(web3j, walletFile)
                .flatMap(txNonce -> {
                    txData.nonce = txNonce;
                    return getRawTransaction(txNonce, gasPrice, gasLimit, BigInteger.ZERO, data);
                })
                .flatMap(rawTx -> signEncodeRawTransaction(rawTx, walletFile, chainId))
                .flatMap(signedMessage -> Single.fromCallable(() -> {
                    txData.signature = Numeric.toHexString(signedMessage);
                    EthSendTransaction raw = web3j
                            .ethSendRawTransaction(Numeric.toHexString(signedMessage))
                            .send();
                    if (raw.hasError()) {
                        throw new Exception(raw.getError().getMessage());
                    }
                    txData.txHash = raw.getTransactionHash();
                    return txData;
                }))
                .subscribeOn(Schedulers.io());
    }


    private Web3j getWeb3jService(String chainCode) {
        return Web3jUtil.getInstance().getBlockConnect(chainCode);
    }


    public Single<BigInteger> getNonceForTransaction(Web3j web3j, WalletFile walletFile) {
        return Single.fromCallable(() -> {
            try {
                String address = Constants.PREFIX_16 + walletFile.getAddress();
                EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).send();
                return ethGetTransactionCount.getTransactionCount();
            } catch (Exception e) {
                return BigInteger.ZERO;
            }
        });
    }

    private Single<RawTransaction> getRawTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, BigInteger value, String data) {
        return Single.fromCallable(() ->
                RawTransaction.createContractTransaction(
                        nonce,
                        gasPrice,
                        gasLimit,
                        value,
                        data));
    }

    public Single<SignatureFromKey> signTransaction(WalletFile walletFile, String toAddress, BigInteger amount, BigInteger gasPrice, BigInteger gasLimit, long nonce, byte[] data, long chainId) {
        return Single.fromCallable(() -> {
            Sign.SignatureData sigData;
            String dataStr = data != null ? Numeric.toHexString(data) : "";

            RawTransaction rtx = RawTransaction.createTransaction(
                    BigInteger.valueOf(nonce),
                    gasPrice,
                    gasLimit,
                    toAddress,
                    amount,
                    dataStr
            );
            LogManage.e("signTransaction-->" + walletFile.getAddress());
            LogManage.e("signTransaction-->" + toAddress);
            LogManage.e("signTransaction-->" + amount);
            LogManage.e("signTransaction-->" + gasPrice);
            LogManage.e("signTransaction-->" + gasLimit);
            LogManage.e("signTransaction-->" + nonce);
            LogManage.e("signTransaction-->" + chainId);

            byte[] signData = TransactionEncoder.encode(rtx, chainId);
            SignatureFromKey returnSig = signWithKeystore(signData, walletFile);
            sigData = sigFromByteArray(returnSig.signature);
            sigData = TransactionEncoder.createEip155SignatureData(sigData, chainId);
            returnSig.signature = encode(rtx, sigData);
            return returnSig;
        }).subscribeOn(Schedulers.io());
    }

    private Single<byte[]> signEncodeRawTransaction(RawTransaction rtx, WalletFile wallet, int chainId) {
        return Single.fromCallable(() -> TransactionEncoder.encode(rtx))
                .flatMap(encoded -> signTransaction(wallet, encoded))
                .flatMap(signatureReturn -> {
                    return encodeTransaction(signatureReturn.signature, rtx);
                });
    }

    public Single<SignatureFromKey> signTransaction(WalletFile walletFile, byte[] signData) {
        return Single.fromCallable(() -> {
            SignatureFromKey returnSig = signWithKeystore(signData, walletFile);
            returnSig.signature = patchSignatureVComponent(returnSig.signature);
            return returnSig;
        }).subscribeOn(Schedulers.io());
    }

    private Single<byte[]> encodeTransaction(byte[] signatureBytes, RawTransaction rtx) {
        return Single.fromCallable(() -> {
            Sign.SignatureData sigData = sigFromByteArray(signatureBytes);
            return encode(rtx, sigData);
        });
    }

    private static byte[] encode(RawTransaction rawTransaction, Sign.SignatureData signatureData) {
        List<RlpType> values = TransactionEncoder.asRlpValues(rawTransaction, signatureData);
        RlpList rlpList = new RlpList(values);
        return RlpEncoder.encode(rlpList);
    }

    public static Sign.SignatureData sigFromByteArray(byte[] sig) {
        if (sig.length < 64 || sig.length > 65) return null;

        byte subv = sig[64];
        if (subv < 27) subv += 27;

        byte[] subrRev = Arrays.copyOfRange(sig, 0, 32);
        byte[] subsRev = Arrays.copyOfRange(sig, 32, 64);

        BigInteger r = new BigInteger(1, subrRev);
        BigInteger s = new BigInteger(1, subsRev);

        return new Sign.SignatureData(subv, subrRev, subsRev);
    }


    /**
     * 用户授权
     * 1. Get authentication event if required.
     * 2. Resume operation at getAuthenticationForSignature
     * 3. get mnemonic/password
     * 4. rebuild private key
     * 5. sign.
     */
    @Override
    public SignatureFromKey signMessage(Signable message, WalletFile walletFile) {
        SignatureFromKey returnSig = signWithKeystore(message.getPrehash(), walletFile);
        if (returnSig != null) {
            returnSig.signature = patchSignatureVComponent(returnSig.signature);
        }
        return returnSig;
    }

    @Override
    public Observable<BaseResponse<GasBean>> getGasDataSet(String chainCode) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getGas(chainCode)
                .retryWhen(new RetryWithDelay());
    }


    private byte[] patchSignatureVComponent(byte[] signature) {
        if (signature != null && signature.length == 65 && signature[64] < 27) {
            signature[64] = (byte) (signature[64] + (byte) 0x1b);
        }
        return signature;
    }

    public static byte[] bytesFromSignature(Sign.SignatureData signature) {
        byte[] sigBytes = new byte[65];
        Arrays.fill(sigBytes, (byte) 0);

        try {
            System.arraycopy(signature.getR(), 0, sigBytes, 0, 32);
            System.arraycopy(signature.getS(), 0, sigBytes, 32, 32);
            System.arraycopy(signature.getV(), 0, sigBytes, 64, 1);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return sigBytes;
    }

    //1. get password from store
    //2. construct credentials
    //3. sign
    private synchronized SignatureFromKey signWithKeystore(byte[] transactionBytes, WalletFile walletFile) {

        try {
            SignatureFromKey returnSig = new SignatureFromKey();
            Credentials credentials = WalletUtils.loadJsonCredentials(SafePassword.get(), JSON.toJSONString(walletFile));

            Sign.SignatureData signatureData = Sign.signMessage(transactionBytes, credentials.getEcKeyPair());

            returnSig.signature = bytesFromSignature(signatureData);
            return returnSig;
        } catch (Exception ignored) {
            return null;
        }
    }

}