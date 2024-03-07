package com.roo.home.mvp.utils;

import com.core.domain.link.LinkTokenBean;
import com.core.domain.mine.TransferRecordBean;
import com.roo.core.app.GlobalContext;
import com.roo.core.model.UserWallet;
import com.roo.core.utils.Codec;
import com.roo.core.utils.Kits;
import com.roo.core.utils.utils.EthereumWalletUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/16 16:10
 *     desc        : 描述--//TokenManager
 * </pre>
 */

public class PenddingManager {

    private final HashMap<String, ArrayList<TransferRecordBean.DataBean>> pendding = new HashMap<>();

    private PenddingManager() {

    }

    public static PenddingManager getInstance() {
        return Holder.instantce;
    }

    public void addPendding(TransferRecordBean.DataBean localPendding, LinkTokenBean.TokensBean tokensBean) {

        ArrayList<TransferRecordBean.DataBean> pendding = getPendding(tokensBean);
        pendding.add(0, localPendding);

        this.pendding.put(getKey(tokensBean), pendding);
    }

    private String getKey(LinkTokenBean.TokensBean tokensBean) {
        UserWallet userWallet = EthereumWalletUtils.getInstance()
                .getSelectedWalletOrNull(GlobalContext.getAppContext());
        return Codec.MD5.getStringMD5(userWallet.getMnemonics2()
                + userWallet.getPrivateKey2() + tokensBean.toString());
    }

    public void checkPendding(LinkTokenBean.TokensBean tokensBean, List<TransferRecordBean.DataBean> list) {
        Iterator<TransferRecordBean.DataBean> iterator = getPendding(tokensBean).iterator();
        while (iterator.hasNext()) {
            TransferRecordBean.DataBean next = iterator.next();
            for (TransferRecordBean.DataBean dataBean : list) {
                if (Objects.equals(dataBean.getTxId(), next.getTxId())) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    private static class Holder {
        static PenddingManager instantce = new PenddingManager();
    }

    public ArrayList<TransferRecordBean.DataBean> getPendding(LinkTokenBean.TokensBean tokensBean) {
        if (Kits.Empty.check(pendding)) {
            return new ArrayList<>();
        }

        ArrayList<TransferRecordBean.DataBean> result = pendding.get(getKey(tokensBean));
        if (Kits.Empty.check(result)) {
            return new ArrayList<>();
        }
        return result;
    }

}
