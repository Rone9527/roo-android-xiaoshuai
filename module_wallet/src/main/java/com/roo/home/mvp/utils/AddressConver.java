package com.roo.home.mvp.utils;

import com.roo.core.app.constants.Constants;
import com.roo.core.utils.LogManage;
import com.subgraph.orchid.encoders.Hex;

import org.bitcoinj.core.Sha256Hash;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;
//import org.tron.common.utils.Base58;
//import org.tron.common.utils.ByteArray;
//import org.tron.common.utils.ByteUtil;

import java.util.regex.Pattern;

import wallet.core.jni.Base58;

public class AddressConver {

    //    public static void main(String[] args) {
//        String base58 = "CUQZuLvkQecocG9UAQQcsJk51z3W4uf5rk";
//        // base58转hex
//        String base58ConverHex = tryToHexAddr(base58);
//        System.out.println(base58ConverHex);
//
//        String hexStr = "41ca3e1c5ee4dec7a118f89a54e271a55dcb514784";
//        // hex转base58
//        String hexConverBase58 = tryToBase58Addr(hexStr);
//        System.out.println(hexConverBase58);
//
//        // 校验
//        System.out.println(base58ConverHex.equalsIgnoreCase(hexStr));
//        System.out.println(hexConverBase58.equalsIgnoreCase(base58));
//    }


    public static String tryToHexAddr(String addr) {
        addr = addr.replace(Constants.PREFIX_16, "");
        String regPattern = "[0-9a-fA-F]{42}";
        if (Pattern.matches(regPattern, addr)) {
            return addr;
        }
        return decodeBase58Address(addr);

    }

    public static String tryToBase58Addr(String hexAddr) {
//            String base58 = "[123456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ]{34}";
        hexAddr = hexAddr.replace("0x", "");
        String regPattern = "[0-9a-fA-F]{42}";
        if (Pattern.matches(regPattern, hexAddr)) {
            return encode58Check(ByteArray.fromHexString(hexAddr));
        }
        return hexAddr;
    }

    public static String decodeBase58Address(String addr) {
        byte[] bytes = decode58Check(addr);
        if (bytes == null) {
            LogManage.e("bytes==null");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (byte data : bytes) {
            stringBuilder.append(ByteUtil.oneByteToHexString(data));
        }
        return stringBuilder.toString();
    }

    public static String encode58Check(byte[] input) {
        byte[] hash0 = Sha256Hash.hash(input);
        byte[] hash1 = Sha256Hash.hash(hash0);
        byte[] inputCheck = new byte[input.length + 4];
        System.arraycopy(input, 0, inputCheck, 0, input.length);
        System.arraycopy(hash1, 0, inputCheck, input.length, 4);
        return Base58.encode(inputCheck);
    }

    public static byte[] decode58Check(String input) {
        byte[] decodeCheck = Base58.decode(input);
        if (decodeCheck.length <= 4) {
            return null;
        }
        byte[] decodeData = new byte[decodeCheck.length - 4];
        System.arraycopy(decodeCheck, 0, decodeData, 0, decodeData.length);
        byte[] hash0 = Sha256Hash.hash(decodeData);
        byte[] hash1 = Sha256Hash.hash(hash0);
        if (hash1[0] == decodeCheck[decodeData.length] &&
                hash1[1] == decodeCheck[decodeData.length + 1] &&
                hash1[2] == decodeCheck[decodeData.length + 2] &&
                hash1[3] == decodeCheck[decodeData.length + 3]) {
            return decodeData;
        }
        return null;
    }

    public static String eth2TronAddress(String ethAddress) {
        byte[] decode = Hex.decode(ethAddress.replace(Constants.PREFIX_16, "41"));
        return Base58Check.bytesToBase58(decode);
    }
}