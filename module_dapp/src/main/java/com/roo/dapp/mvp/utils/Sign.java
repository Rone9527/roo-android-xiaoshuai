package com.roo.dapp.mvp.utils;

import com.roo.core.utils.LogManage;

import org.tron.common.crypto.ECKey;
import org.tron.common.crypto.cryptohash.Digest;
import org.tron.common.crypto.cryptohash.Keccak256;
import org.tron.common.utils.ByteArray;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;

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
 * Created on : 2021/9/17
 * PackageName : com.roo.dapp.mvp.utils
 * Description : Tron 游戏签名
 */
public class Sign {

    static final String TRX_MESSAGE_HEADER = "\u0019TRON Signed Message:\n32";


    public static String signMessage(byte[] message, byte[] privateKey) {
        byte[] messageBytes = Sign.byteMerger(TRX_MESSAGE_HEADER.getBytes(), message);
        Digest sha3 = new Keccak256();
        byte[] digest = sha3.digest(messageBytes);
//        ECKeyPair ecKeyPair = ECKeyPair.create(privateKey);
//        org.web3j.crypto.Sign.SignatureData signatureData = org.web3j.crypto.Sign.signMessage(digest, ecKeyPair, false);
//        LogManage.e("signatureData--->" + ByteArray.toHexString(signatureData.getR()));
//        LogManage.e("signatureData--->" + ByteArray.toHexString(signatureData.getS()));
//        LogManage.e("signatureData--->" + ByteArray.toHexString(signatureData.getV()));

        ECKey ecKey = ECKey.fromPrivate(privateKey);
        ECKey.ECDSASignature signature = ecKey.sign(digest);
        String r = ByteArray.toHexString(signature.r.toByteArray());
        String s = ByteArray.toHexString(signature.s.toByteArray());
        String v = String.valueOf(signature.v);
        LogManage.e("sig--->" + r);
        LogManage.e("sig--->" + s);
        LogManage.e("sig--->" + v);
        LogManage.e("sig--->" + new BigInteger(v).toString(16));
        String signatureHex = "0x" + r.substring(2) + s + "1b";
        LogManage.e("sig--->" + signature.toHex());
        LogManage.e("sig--->" + signatureHex);

        //0x06bdda103c9fb1bd162c4703e192b20b17e06023a484ff12a1993806cc1a5059442a443afe484e4caea1d491a8cf2ff896c6d2925f48723a2e356973e431b1051c
        //0xd5b92791cf14f132f9517c2e4d5279e35ee38485e5cff0a6e88c9276916044043e268f29d6d3ede4a0d9309c2d4f38d9aa5c4fd713416ecf0cf2fcde85a879831b
        //0x06bdda103c9fb1bd162c4703e192b20b17e06023a484ff12a1993806cc1a5059442a443afe484e4caea1d491a8cf2ff896c6d2925f48723a2e356973e431b10501
        return signatureHex;
    }

    //System.arraycopy()方法
    public static byte[] byteMerger(byte[] bt1, byte[] bt2) {
        byte[] bt3 = new byte[bt1.length + bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }

}
