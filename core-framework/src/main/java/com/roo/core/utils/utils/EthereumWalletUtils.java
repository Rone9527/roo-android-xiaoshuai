package com.roo.core.utils.utils;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.PrimaryKey;
import com.roo.core.model.UserWallet;
import com.roo.core.utils.Codec;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.utils.Convert;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Objects;

import io.github.novacrypto.bip32.ExtendedPrivateKey;
import io.github.novacrypto.bip39.MnemonicGenerator;
import io.github.novacrypto.bip39.SeedCalculator;
import io.github.novacrypto.bip39.Words;
import io.github.novacrypto.bip39.wordlists.English;
import io.github.novacrypto.bip44.AddressIndex;
import io.github.novacrypto.bip44.BIP44;

import static android.content.Context.MODE_PRIVATE;
import static io.github.novacrypto.bip32.networks.Bitcoin.TEST_NET;

public class EthereumWalletUtils {

    private static final String TAG = EthereumWalletUtils.class.getSimpleName();

    private static final String DIR = "roo";
    private static final String FILE_NAME = "_child_wallet.json";

    private static EthereumWalletUtils ethWalletUtil = null;

    public static EthereumWalletUtils getInstance() {
        if (ethWalletUtil == null) {
            ethWalletUtil = new EthereumWalletUtils();
        }
        return ethWalletUtil;
    }

    /**
     * 生成助记词
     */
    public String generateMnemonics() {
        StringBuilder sb = new StringBuilder();
        byte[] entropy = new byte[Words.TWELVE.byteLength()];
        new SecureRandom().nextBytes(entropy);
        new MnemonicGenerator(English.INSTANCE).createMnemonic(entropy, sb::append);
        return sb.toString();
    }

    /**
     * generate key pair to create eth wallet
     * 生成KeyPair , 用于创建钱包
     */
    public ECKeyPair generateKeyPair(String mnemonics) {
        // 1. we just need eth wallet for now
        AddressIndex addressIndex = BIP44
                .m()
                .purpose44()
                .coinType(60)
                .account(0)
                .external()
                .address(0);
        // 2. calculate seed from mnemonics , then get master/root key ; Note that the bip39 passphrase we set "" for common
        ExtendedPrivateKey rootKey = ExtendedPrivateKey.fromSeed(new SeedCalculator().calculateSeed(mnemonics, ""), TEST_NET);
        //String extendedBase58 = rootKey.extendedBase58();
        // 3. get child private key deriving from master/root key
        ExtendedPrivateKey childPrivateKey = rootKey.derive(addressIndex, AddressIndex.DERIVATION);
        //String childExtendedBase58 = childPrivateKey.extendedBase58();
        // 4. get key pair
        byte[] privateKeyBytes = childPrivateKey.getKey();
        ECKeyPair keyPair = ECKeyPair.create(privateKeyBytes);
        return keyPair;
    }

    //通过私钥获取ECKeyPair-----导入钱包使用
    public ECKeyPair getKeyPair(String privateKey) {
        ECKeyPair keyPair;
        try {
            keyPair = ECKeyPair.create(new BigInteger(privateKey, 16));
        } catch (Exception e) {
            return null;
        }
        return keyPair;
    }

    //根据密码和钱包文件获取ECKeyPair对象
    public ECKeyPair decrypt(String password, String walletFile) {
        try {
            Credentials credentials = WalletUtils.loadCredentials(password, String.valueOf(walletFile));
            return credentials.getEcKeyPair();
        } catch (IOException | CipherException e) {
            e.printStackTrace();
        }
        return null;
    }


    //保存钱包到手机文件
    public void updateWallet(Context context, UserWallet userWallet, boolean changeToSelect) {
        if (changeToSelect) {
            for (UserWallet wallet : getWallet(context)) {
                if (userWallet.getRemark().equals(wallet.getRemark())) {
                    continue;
                }
                wallet.setSelected(false);
                doUpdate(context, wallet);
            }
            userWallet.setSelected(true);
        }
        doUpdate(context, userWallet);

    }

    private void doUpdate(Context context, UserWallet userWallet) {
        LogManage.d(TAG, "run:  创建新的钱包");
        //获取钱包文件
        //比特币的钱包文件是protobuf格式，以太坊钱包文件是json格式
        File file = new File(context.getDir(DIR, MODE_PRIVATE), userWallet.getRemark() + FILE_NAME);
        //创建新的钱包文件，也就是keystore
        //将钱包以文件的形式存储
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.writeValue(file, userWallet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取钱包--通过钱包名称
    public UserWallet getWalletByRemark(Context context, String remark) {
        for (UserWallet wallet : getWallet(context)) {
            if (wallet.getRemark().equals(remark)) {
                return wallet;
            }
        }
        return null;
    }

    public ArrayList<UserWallet> getWallet(Context context) {
        ArrayList<UserWallet> allWallets = new ArrayList<>();
        try {
            //获取钱包文件
            for (File file : context.getDir(DIR, MODE_PRIVATE).listFiles()) {
                if (file.isFile()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    UserWallet userWallet = objectMapper.readValue(file, UserWallet.class);
                    allWallets.add(userWallet);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allWallets;
    }

    //获取钱包的个数
    public String getWalletDefaultName(Context context) {
        String newRemark;

        do {
            newRemark = Constants.PREFIX_WALLET + PrimaryKey.getIndexNext(PrimaryKey.WALLET_NAME);
            if (getWalletByRemark(context, newRemark) != null) {
                PrimaryKey.addIndex(PrimaryKey.WALLET_NAME);
                newRemark = null;
            }
        } while (TextUtils.isEmpty(newRemark));

        return newRemark;
    }

    /*是否是同一个钱包*/
    public boolean isSameWallet(UserWallet w1, UserWallet w2) {
        if (w1 == null || w2 == null) {
            return false;
        }
        String m1 = Codec.MD5.getStringMD5(w1.getMnemonics() + w1.getPrivateKey());
        String m2 = Codec.MD5.getStringMD5(w2.getMnemonics() + w2.getPrivateKey());
        return Objects.equals(m1, m2);
    }

    //获取当前选中的钱包
    public UserWallet getSelectedWalletOrFirst(Context context) {
        ArrayList<UserWallet> wallets = EthereumWalletUtils.getInstance().getWallet(context);
        if (wallets.isEmpty()) {
            return null;
        }
        UserWallet selectedUserWallet = getSelectedWalletOrNull(context);
        if (selectedUserWallet == null) {
            selectedUserWallet = wallets.get(0);
            selectedUserWallet.setSelected(true);
            EthereumWalletUtils.getInstance().doUpdate(context, selectedUserWallet);
        }
        return selectedUserWallet;
    }

    //获取当前选中的钱包
    public UserWallet getSelectedWalletOrNull(Context context) {
        for (UserWallet userWallet : getWallet(context)) {
            if (userWallet.isSelected()) {
                return userWallet;
            }
        }
        return null;
    }

    //获取当前选中的钱包
    public UserWallet.ChainWallet getWalletByChainCode(UserWallet userWallet, String chainCode) {
        if (Kits.Empty.check(userWallet)) return null;
        for (UserWallet.ChainWallet chainWallet : userWallet.getChainWallets()) {
            if (chainCode.equals(chainWallet.getChainCode())) {
                return chainWallet;
            }
        }
        return null;
    }

    //删除单个钱包
    public void deleteWalletByRemark(Context context, String remark) {
        //获取钱包文件
        File file = new File(context.getDir(DIR, MODE_PRIVATE), remark + FILE_NAME);
        if (file.isFile() && file.exists()) {
            file.delete();
        }

        if (Kits.Empty.check(EthereumWalletUtils.getInstance().getWallet(context))) {
            PrimaryKey.resetIndex(PrimaryKey.WALLET_NAME);
        }
    }

    //判断钱包是否已存在-----助记词
    public boolean isWalletExistedByMnemonics(String mnemonics, Context context) {
        for (UserWallet wallet : getWallet(context)) {
            if (!TextUtils.isEmpty(wallet.getMnemonics()) && wallet.getMnemonics().equals(mnemonics)) {
                return true;
            }
        }
        return false;
    }

    //判断钱包是否已存在-----私钥
    public boolean isWalletExistedByPk(String pk, Context context) {
        for (UserWallet wallet : getWallet(context)) {
            if (!TextUtils.isEmpty(wallet.getPrivateKey()) && wallet.getPrivateKey().equals(pk)) {
                return true;
            }
        }
        return false;
    }

    public ECKeyPair decrypt(String password, WalletFile walletFile) {
        try {
            Credentials credentials = WalletUtils.loadJsonCredentials(password, JSON.toJSONString(walletFile));
            return credentials.getEcKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新钱包名称
     *
     * @param oldRemark 钱包名称
     * @return
     */
    public boolean setRemarkForWallet(String oldRemark, String newRemark, Context context) {
        if (getWalletByRemark(context, newRemark) == null) {
            UserWallet oldWallet = getWalletByRemark(context, oldRemark);
            if (oldWallet == null) {
                return false;
            }
            oldWallet.setRemark(newRemark);

            File file = new File(context.getDir(DIR, MODE_PRIVATE), oldRemark + FILE_NAME);
            if (file.isFile() && file.exists()) {
                File newfile = new File(context.getDir(DIR, MODE_PRIVATE), newRemark + FILE_NAME);
                file.renameTo(newfile);
            }

            updateWallet(context, oldWallet, false);
            return true;
        }
        return false;
    }

    //获取签名
    public RawTransaction getTransaction(BigInteger nonce, String gasPrice, BigInteger gasLimit, String contractAddress, String data) {
        return RawTransaction.createTransaction(
                nonce,
                Convert.toWei(gasPrice, Convert.Unit.GWEI).toBigInteger(),
                gasLimit,
                contractAddress,
                data);
    }

    public BigInteger getNonce(String chainCode, String address) {
        BigInteger nonce;
        try {
            nonce = Web3jUtil.getInstance().getBlockConnect(chainCode)
                    .ethGetTransactionCount(address, DefaultBlockParameterName.LATEST)
                    .send()
                    .getTransactionCount();
        } catch (IOException e) {
            e.printStackTrace();
            return BigInteger.ZERO;
        }
        return nonce;
    }

}
