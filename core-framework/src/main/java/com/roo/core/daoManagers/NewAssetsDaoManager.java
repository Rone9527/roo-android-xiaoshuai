package com.roo.core.daoManagers;

import com.core.domain.link.LinkTokenBean;
import com.core.domain.trade.MyAssetsBean;
import com.core.domain.trade.MyAssetsBeanDao;
import com.core.domain.trade.NewAssetsBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.roo.core.ComponentApplication;
import com.roo.core.app.GlobalContext;
import com.roo.core.model.UserWallet;
import com.roo.core.utils.Kits;
import com.roo.core.utils.utils.EthereumWalletUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class NewAssetsDaoManager {

    /**
     * 获取Dao
     */
    public static MyAssetsBeanDao getMyAssetsBeanDataDao() {
        return ComponentApplication.daoSession.getMyAssetsBeanDao();
    }

    /**
     * 删除
     */
    public static void clear() {
        getMyAssetsBeanDataDao().deleteAll();
    }

    /**
     * 删除
     */
    public static void delete(String walletName) {
        QueryBuilder<MyAssetsBean> qb = getMyAssetsBeanDataDao().queryBuilder();
        qb.where(MyAssetsBeanDao.Properties.WalletRemark.eq(walletName));
        List<MyAssetsBean> list = qb.build().list();
        for (MyAssetsBean myAssetsBean : list) {
            getMyAssetsBeanDataDao().delete(myAssetsBean);
        }
    }


    /**
     * 查询是否已存在
     */
    public static boolean isExist(String walletName) {
        QueryBuilder<MyAssetsBean> qb = getMyAssetsBeanDataDao().queryBuilder();
        qb.where(MyAssetsBeanDao.Properties.WalletRemark.eq(walletName));
        return qb.buildCount().count() > 0;
    }

    /**
     * 查询所有
     */
    public static List<MyAssetsBean> select() {
        return getMyAssetsBeanDataDao().queryBuilder()
                .build().list();
    }

    /**
     * 查询一条
     */
    public static MyAssetsBean select(String walletName) {
        QueryBuilder<MyAssetsBean> qb = getMyAssetsBeanDataDao().queryBuilder();
        qb.where(MyAssetsBeanDao.Properties.WalletRemark.eq(walletName));
        List<MyAssetsBean> list = qb.build().list();
        if (Kits.Empty.check(list)) {
            return null;
        }
        return list.get(0);
    }

    public static List<NewAssetsBean> getAssetsListByWalletName(String walletName) {
        MyAssetsBean myAssetsBean = select(walletName);
        if (Kits.Empty.check(myAssetsBean)) {
            return new ArrayList<>();
        }
        if (Kits.Empty.check(myAssetsBean.getAllAssets())) {
            return new ArrayList<>();
        }
        List<NewAssetsBean> list = null;
        try {
            list = new Gson().fromJson(myAssetsBean.getAllAssets(), new TypeToken<List<NewAssetsBean>>() {
            }.getType());//GJSONObject.parseArray(data, NewAssetsBean.class);
        } catch (Exception exception) {
            return new ArrayList<>();
        }
        return Kits.Empty.check(list) ? new ArrayList<>() : list;
    }

    /**
     * 获取单个资产数据是否已经存在
     *
     * @param newAssetsBean
     * @param walletName
     * @return
     */
    public static NewAssetsBean getTokenIsExist(NewAssetsBean newAssetsBean, String walletName) {
        List<NewAssetsBean> listSaved = getAssetsListByWalletName(walletName);
        if (Kits.Empty.check(listSaved)) {
            return null;
        } else {
            return getTokenIsExist(listSaved, newAssetsBean);
        }
    }

    public static NewAssetsBean getTokenIsExist(List<NewAssetsBean> listSaved, NewAssetsBean newAssetsBean) {
        boolean isExist = false;
        for (NewAssetsBean assetsBean : listSaved) {
            if ((Kits.Empty.check(assetsBean.getContractId()) && Kits.Empty.check(newAssetsBean.getContractId()) && newAssetsBean.getChainCode().equals(assetsBean.getChainCode())
            ) || (!Kits.Empty.check(assetsBean.getContractId()) && !Kits.Empty.check(newAssetsBean.getContractId()) && newAssetsBean.getContractId().equals(assetsBean.getContractId()))) {
                isExist = true;
                return assetsBean;

            }
        }
        return null;
    }

    public static boolean getTokenIsExistBollen(List<NewAssetsBean> listSaved, NewAssetsBean newAssetsBean) {
        boolean isExist = false;
        for (NewAssetsBean assetsBean : listSaved) {
            if ((Kits.Empty.check(assetsBean.getContractId()) && Kits.Empty.check(newAssetsBean.getContractId()) && newAssetsBean.getChainCode().equals(assetsBean.getChainCode())
            ) || (!Kits.Empty.check(assetsBean.getContractId()) && !Kits.Empty.check(newAssetsBean.getContractId()) && newAssetsBean.getContractId().equals(assetsBean.getContractId()))) {
                isExist = true;
                break;

            }
        }
        return isExist;
    }



    public static NewAssetsBean getTokenIsExistData(List<NewAssetsBean> listSaved, NewAssetsBean newAssetsBean) {
        boolean isExist = false;
        for (NewAssetsBean assetsBean : listSaved) {
            if ((Kits.Empty.check(assetsBean.getContractId()) && Kits.Empty.check(newAssetsBean.getContractId()) && newAssetsBean.getChainCode().equals(assetsBean.getChainCode())
            ) || (!Kits.Empty.check(assetsBean.getContractId()) && !Kits.Empty.check(newAssetsBean.getContractId()) && newAssetsBean.getContractId().equals(assetsBean.getContractId()))) {
                isExist = true;
                return assetsBean;
            }
        }
        return null;
    }

    public static int getUnAddedAmount(List<NewAssetsBean> newAssetsBeanList, String walletName) {
        int amount = 0;
        List<NewAssetsBean> listSaved = getAssetsListByWalletName(walletName);
        if (Kits.Empty.check(listSaved)) {
            for (NewAssetsBean newAssetsBean : newAssetsBeanList) {
                if (!Kits.Empty.check(newAssetsBean.getContractId())) {
                    amount++;
                }
            }
        } else {
            for (NewAssetsBean bean : newAssetsBeanList) {
                if (!getTokenIsExistBollen(listSaved, bean) && !Kits.Empty.check(bean.getContractId())) {
                    amount++;
                }
            }
        }
        return amount;
    }

    /**
     * @param myAssetsBean 我的资产页面使用
     * @return
     */
    public static boolean isAssetsBeanExistInWallet(NewAssetsBean myAssetsBean) {
        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(
                GlobalContext.getAppContext()
        );
        ArrayList<LinkTokenBean.TokensBean> tokenList = userWallet.getTokenList();
        for (LinkTokenBean.TokensBean tokensBean : tokenList) {
            if (Kits.Empty.check(myAssetsBean.getContractId()) && myAssetsBean.getChainCode().equals(tokensBean.getChainCode())) {
                return true;
            }
            if (!Kits.Empty.check(myAssetsBean.getContractId()) && myAssetsBean.getContractId().equals(tokensBean.getContractId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 首次保存
     */
    public static void insert(MyAssetsBean myAssetsBean) {
        getMyAssetsBeanDataDao().insert(myAssetsBean);
    }

    /**
     * 更新
     */
    public static void update(MyAssetsBean myAssetsBean) {
        getMyAssetsBeanDataDao().update(myAssetsBean);
    }
}
