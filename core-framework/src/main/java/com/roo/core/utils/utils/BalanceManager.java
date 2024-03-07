package com.roo.core.utils.utils;


import android.text.TextUtils;

import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.UserWallet;
import com.roo.core.utils.Codec;
import com.roo.core.utils.LogManage;
import com.subgraph.orchid.encoders.Hex;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.util.HashMap;

import wallet.core.jni.Base58;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;


/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/19 10:22
 *     desc        : 描述--//BalanceManager 余额管理类
 * </pre>
 */

public class BalanceManager {
    private HashMap<String, BigDecimal> balanceMap = new HashMap<>();

    private BalanceManager() {

    }

    public static BalanceManager getInstance() {
        return Holder.instantce;
    }

    private static class Holder {
        static BalanceManager instantce = new BalanceManager();
    }

    public void store() {
        String json = new Gson().toJson(balanceMap);
        SharedPref.putString(Constants.KEY_BALANCE_LIST, json);
    }

    public void restore() {
        String json = SharedPref.getString(Constants.KEY_BALANCE_LIST);
        if (!TextUtils.isEmpty(json)) {
            balanceMap = new Gson().fromJson(json, new TypeToken<HashMap<String, BigDecimal>>() {
            }.getType());
        }
    }

    public void put(String chainCode, String address, String contractId, BigDecimal balance) {
        balanceMap.put(getKey(chainCode, address, contractId), balance);
    }

    private String getKey(String chainCode, String address, String contractId) {
        return Codec.MD5.getStringMD5(chainCode + address + (TextUtils.isEmpty(contractId) ? "" : contractId));
    }

    public BigDecimal get(UserWallet userWallet, LinkTokenBean.TokensBean tokensBean) {
        return get(userWallet, tokensBean.getChainCode(), tokensBean.getContractId());
    }

    public BigDecimal get(UserWallet userWallet, String chainCode, String contractId) {

        UserWallet.ChainWallet chainWallet = EthereumWalletUtils.getInstance()
                .getWalletByChainCode(userWallet, chainCode);
        if (chainWallet == null) {
            return BigDecimal.ZERO;
        }
        String address;
        if (chainCode.equalsIgnoreCase(ChainCode.TRON)) {
            if (userWallet.getPrivateKey2() != null) {
                address = getPrivateKeyToAddress(userWallet.getPrivateKey2());
            } else {
                HDWallet hdWallet = new HDWallet(userWallet.getMnemonics2(), "");
                address = hdWallet.getAddressForCoin(CoinType.TRON);
            }
        } else {
            address = Constants.PREFIX_16 + chainWallet.getWalletFile2().getAddress();
        }


        BigDecimal bigDecimal = balanceMap.get(getKey(chainCode, address, contractId));
        if (bigDecimal == null) {
            return BigDecimal.ZERO;
        }
        return bigDecimal;
    }

    private String getPrivateKeyToAddress(String privateKey) {
        String address = Keys.toChecksumAddress(Keys.getAddress(ECKeyPair.create(Numeric.toBigInt(privateKey))));
        byte[] decode = Hex.decode(address.replace(Constants.PREFIX_16, "41"));
        return Base58.encode(decode);
    }
}