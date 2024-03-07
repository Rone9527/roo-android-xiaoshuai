package com.roo.home.mvp.utils;

import static java.sql.DriverManager.println;

import android.text.TextUtils;

import com.google.protobuf.ByteString;
import com.roo.core.app.constants.Constants;

import org.web3j.abi.datatypes.generated.Int64;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

import wallet.core.java.AnySigner;
import wallet.core.jni.CoinType;
import wallet.core.jni.proto.Tron;

/**
 * //  ┏┓　　　┏┓
 * //┏┛┻━━━┛┻┓
 * //┃　　　　　　　┃
 * //┃　　　━　　　┃
 * //┃　┳┛　┗┳　┃
 * //┃　　　　　　　┃
 * //┃　　　┻　　　┃
 * //┃　　　　　　　┃
 * //┗━┓　　　┏━┛
 * //    ┃　　　┃   神兽保佑
 * //    ┃　　　┃   代码无BUG！
 * //    ┃　　　┗━━━┓
 * //    ┃　　　　　　　┣┓
 * //    ┃　　　　　　　┏┛
 * //    ┗┓┓┏━┳┓┏┛
 * //      ┃┫┫　┃┫┫
 * //      ┗┻┛　┗┻┛
 * Created by : Arvin
 * Created on : 2021/8/12
 * PackageName : com.roo.home.mvp.utils
 * Description :
 */
public class WalletCore {
    public static String getSignTransferTrc20Contract(
            String ownerAddress,
            String contractAddress,
            String toAddress,
            BigInteger amount,
            long timestamp,
            String txTrieRoot,
            String parentHash,
            long number,
            String witnessAddress,
            int version,
            String privateKeyHex,
            String token,
            Long gasLimit) {

        String hexStrin = "";

        if (TextUtils.isEmpty(token)) {//主网币 trx  transfer
            Tron.TransferContract.Builder transferContract = Tron.TransferContract.newBuilder()
                    .setOwnerAddress(ownerAddress)
                    .setToAddress(toAddress)
                    .setAmount(new Int64(amount).getValue().intValue());

            Tron.BlockHeader blockHeader = Tron.BlockHeader.newBuilder()
                    .setTimestamp(timestamp)
                    .setTxTrieRoot(ByteString.copyFrom(Numeric.hexStringToByteArray(Constants.PREFIX_16 + txTrieRoot)))
                    .setParentHash(ByteString.copyFrom(Numeric.hexStringToByteArray(Constants.PREFIX_16 + parentHash)))
                    .setNumber(new Int64(number).getValue().intValue())
                    .setWitnessAddress(ByteString.copyFrom(Numeric.hexStringToByteArray(Constants.PREFIX_16 + witnessAddress)))
                    .setVersion(version)
                    .build();
            Tron.Transaction transaction = Tron.Transaction.newBuilder()
                    .setTransfer(transferContract)
                    .setTimestamp(timestamp)
                    .setBlockHeader(blockHeader)
                    .build();

            Tron.SigningInput.Builder signingInput = Tron.SigningInput.newBuilder()
                    .setTransaction(transaction)
                    .setPrivateKey(ByteString.copyFrom(Numeric.hexStringToByteArray(privateKeyHex)));
            try {
                Tron.SigningOutput output = AnySigner.sign(signingInput.build(), CoinType.TRON, Tron.SigningOutput.parser());
                hexStrin = output.getJson();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            String amoutHexStr = stringTo64b(amount.toString(16));
            Tron.TransferTRC20Contract.Builder trc20Contract = Tron.TransferTRC20Contract.newBuilder()
                    .setOwnerAddress(ownerAddress)
                    .setContractAddress(token)
                    .setToAddress(toAddress)
                    .setAmount(ByteString.copyFrom(Numeric.hexStringToByteArray(amoutHexStr)));

            Tron.BlockHeader blockHeader = Tron.BlockHeader.newBuilder()
                    .setTimestamp(timestamp)
                    .setTxTrieRoot(ByteString.copyFrom(Numeric.hexStringToByteArray(Constants.PREFIX_16 + txTrieRoot)))
                    .setParentHash(ByteString.copyFrom(Numeric.hexStringToByteArray(Constants.PREFIX_16 + parentHash)))
                    .setNumber(new Int64(number).getValue().intValue())
                    .setWitnessAddress(ByteString.copyFrom(Numeric.hexStringToByteArray(Constants.PREFIX_16 + witnessAddress)))
                    .setVersion(version)
                    .build();

            Tron.Transaction transaction = Tron.Transaction.newBuilder()
                    .setTransferTrc20Contract(trc20Contract)
                    .setTimestamp(timestamp)
                    .setFeeLimit(new Int64(gasLimit).getValue().intValue())
                    .setBlockHeader(blockHeader)
                    .build();




            Tron.SigningInput.Builder signingInput = Tron.SigningInput.newBuilder()
                    .setTransaction(transaction)
                    .setPrivateKey(ByteString.copyFrom(Numeric.hexStringToByteArray(privateKeyHex)));
            try {
                Tron.SigningOutput output = AnySigner.sign(signingInput.build(), CoinType.TRON, Tron.SigningOutput.parser());
                hexStrin = output.getJson();

            } catch (Exception e) {
                e.printStackTrace();
            }
            println("*****signing_output${output.json}");
            return hexStrin;
        }

        return hexStrin;
    }


    /**
     * 十六进制的去掉前面的0x,补0，凑够64个字符长度
     */


    static String stringTo64b(String str) {
        String strNew = str;
        String new000 = "";
        String str0000 = "0000000000000000000000000000000000000000000000000000000000000000";
        if (str.startsWith("0x")) {
            strNew = str.replace("0x", "");

        }
        int len = strNew.length();

        if (len < 64) {

            String s2 = str0000.substring(str0000.length() - len, str0000.length());

            new000 = s2 + strNew;

        }
        return new000;
    }
}
