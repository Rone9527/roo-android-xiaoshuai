package com.roo.core.utils.utils;

import android.text.TextUtils;

import com.core.domain.link.LinkTokenBean;
import com.core.domain.trade.MyAssetsBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.GlobalContext;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.UserWallet;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;

import java.util.ArrayList;
import java.util.Objects;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/16 16:10
 *     desc        : 描述--//TokenManager
 * </pre>
 */

public class TokenManager {
    private TokenManager() {

    }

    public static TokenManager getInstance() {
        return Holder.instantce;
    }

    private static class Holder {
        static TokenManager instantce = new TokenManager();
    }

    public ArrayList<String> tokenList() {
        ArrayList<String> result = new ArrayList<>();
        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(
                GlobalContext.getAppContext()
        );
        ArrayList<LinkTokenBean.TokensBean> tokenList = userWallet.getTokenList();
        for (LinkTokenBean.TokensBean tokensBean : tokenList) {
            if (tokensBean != null)
                result.add(tokensBean.getContractId() + tokensBean.getSymbol());
        }
        return result;
    }

    public boolean isTokenHasSelected(LinkTokenBean.TokensBean selected) {
        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(
                GlobalContext.getAppContext()
        );
        ArrayList<LinkTokenBean.TokensBean> tokenList = userWallet.getTokenList();
        for (LinkTokenBean.TokensBean tokensBean : tokenList) {
            if (Objects.equals(tokensBean.getContractId(), selected.getContractId())
                    && tokensBean.getSymbol().equals(selected.getSymbol())) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<LinkTokenBean> getLink() {
        String json = SharedPref.getString(Constants.KEY_OBJ_LINK_TOKEN);
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        return new Gson().fromJson(json, new TypeToken<ArrayList<LinkTokenBean>>() {
        }.getType());
    }

    /*通过token获取其主链节点*/
    public ArrayList<LinkTokenBean.NodesBean> getChainNode() {
        ArrayList<LinkTokenBean.NodesBean> nodesBeans = new ArrayList<>();
        for (LinkTokenBean tokenBean : getLink()) {
            if (Kits.Empty.check(tokenBean.getNodes())) {
                continue;
            }
            nodesBeans.addAll(tokenBean.getNodes());
        }
        return nodesBeans;
    }

    /*通过token获取其所有平行链*/
    public ArrayList<LinkTokenBean.TokensBean> getTokensByChainCode(String chainCode) {
        ArrayList<LinkTokenBean> link = getLink();
        if (Kits.Empty.check(link)) {
            return new ArrayList<>();
        }
        for (LinkTokenBean tokenBean : link) {
            if (tokenBean.getCode().equalsIgnoreCase(chainCode)) {
                return tokenBean.getTokens();
            }
        }
        return new ArrayList<>();
    }


    /*通过token获取其主链币*/
    public LinkTokenBean.TokensBean getMainTokenBeanByChainCode(String chainCode) {
        for (LinkTokenBean.TokensBean token : getTokensByChainCode(chainCode)) {
            if (token.isMain()) {
                return token;
            }
        }
        return null;
    }

    /*通过token获取其主链节点*/
    public String getMainSymbolByChainCode(String chainCode) {
        for (LinkTokenBean.TokensBean token : getTokensByChainCode(chainCode)) {
            if (token.isMain()) {
                return token.getSymbol();
            }
        }
        return null;
    }
}
