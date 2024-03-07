package com.roo.core.utils.utils;

import com.core.domain.dapp.JpushUpload;
import com.core.domain.link.LinkTokenBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiameng.mmkv.SharedPref;
import com.lzh.easythread.AsyncCallback;
import com.roo.core.app.GlobalContext;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.UserWallet;
import com.roo.core.utils.Kits;
import com.roo.core.utils.ThreadManager;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/13 18:12
 *     desc        : 描述--//AddressManager
 * </pre>
 */

public class AddressManager {
    private static final String KEY_ALL_ADDRESS = "KEY_ALL_ADDRESS";

    private List<JpushUpload.SubAddrListBean> subAddrList = new ArrayList<>();

    private AddressManager() {

    }

    public static AddressManager getInstance() {
        return Holder.instantce;
    }

    private static class Holder {
        static AddressManager instantce = new AddressManager();
    }

    public List<JpushUpload.SubAddrListBean> getSubAddrList() {
        if (Kits.Empty.check(subAddrList)) {
            ThreadManager.getCache().async(() -> {
                ArrayList<JpushUpload.SubAddrListBean> subAddrList = new ArrayList<>();
                for (UserWallet userWallet : EthereumWalletUtils.getInstance().getWallet(GlobalContext.getAppContext())) {
                    for (LinkTokenBean.TokensBean tokensBean : userWallet.getTokenList()) {
                        JpushUpload.SubAddrListBean item = new JpushUpload.SubAddrListBean();
                        item.setChainCode(tokensBean.getChainCode());
                        item.setWalletName(userWallet.getRemark());
                        UserWallet.ChainWallet chainWallet = EthereumWalletUtils.getInstance()
                                .getWalletByChainCode(userWallet, tokensBean.getChainCode());
                        item.setAddress(Constants.PREFIX_16 + chainWallet.getWalletFile().getAddress());

                        subAddrList.add(item);
                    }
                }
                return subAddrList;
            }, new AsyncCallback<ArrayList<JpushUpload.SubAddrListBean>>() {
                @Override
                public void onSuccess(ArrayList<JpushUpload.SubAddrListBean> t) {
                    AddressManager.getInstance().setSubAddrList(t);
                }

                @Override
                public void onFailed(Throwable t) {

                }
            });

            if (SharedPref.contains(KEY_ALL_ADDRESS)) {
                String json = SharedPref.getString(KEY_ALL_ADDRESS);
                return new Gson().fromJson(json, new TypeToken<List<JpushUpload.SubAddrListBean>>() {
                }.getType());
            }
        }
        return subAddrList;
    }

    public void setSubAddrList(List<JpushUpload.SubAddrListBean> subAddrList) {
        SharedPref.putString(KEY_ALL_ADDRESS, new Gson().toJson(subAddrList));

        this.subAddrList = subAddrList;
    }

    public boolean isAddressExpired(String addr) {
        List<JpushUpload.SubAddrListBean> subAddrList = AddressManager.getInstance().getSubAddrList();
        if (Kits.Empty.check(subAddrList)) {
            return false;
        }
        for (JpushUpload.SubAddrListBean bean : subAddrList) {
            if (bean.getAddress().equals(addr)) {
                return false;
            }
        }
        return true;
    }

}
