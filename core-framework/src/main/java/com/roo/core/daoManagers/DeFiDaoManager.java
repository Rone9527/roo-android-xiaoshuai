package com.roo.core.daoManagers;

import com.core.domain.trade.DeFiDataBean;
import com.core.domain.trade.DeFiDataBeanDao;
import com.roo.core.ComponentApplication;
import com.roo.core.utils.Kits;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class DeFiDaoManager {

    /**
     * 获取Dao
     */
    public static DeFiDataBeanDao getDeFiDataDao() {
        return ComponentApplication.daoSession.getDeFiDataBeanDao();
    }

    /**
     * 删除
     */
    public static void clear() {
        getDeFiDataDao().deleteAll();
    }

    /**
     * 删除
     */
    public static void delete(String identity) {
        QueryBuilder<DeFiDataBean> qb = getDeFiDataDao().queryBuilder();
        qb.where(DeFiDataBeanDao.Properties.Identity.eq(identity));
        List<DeFiDataBean> list = qb.build().list();
        for (DeFiDataBean DeFiDataBean : list) {
            getDeFiDataDao().delete(DeFiDataBean);
        }
    }


    /**
     * 查询是否已存在
     */
    public static boolean isExist(String identity) {
        QueryBuilder<DeFiDataBean> qb = getDeFiDataDao().queryBuilder();
        qb.where(DeFiDataBeanDao.Properties.Identity.eq(identity));
        return qb.buildCount().count() > 0;
    }

    /**
     * 查询所有
     */
    public static List<DeFiDataBean> select() {
        return getDeFiDataDao().queryBuilder()
                .build().list();
    }

    /**
     * 查询一条
     */
    public static DeFiDataBean select(long identity) {
        QueryBuilder<DeFiDataBean> qb = getDeFiDataDao().queryBuilder();
        qb.where(DeFiDataBeanDao.Properties.Identity.eq(identity));
        List<DeFiDataBean> list = qb.build().list();
        if (Kits.Empty.check(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 保存
     */
    public static void insert(DeFiDataBean DeFiDataBean) {
        getDeFiDataDao().insert(DeFiDataBean);
    }

    /**
     * 查询所有保存的identity
     */
    public static String getAllIdentity() {
        String ids = "";
        if (Kits.Empty.check(select())) {
            return ids;
        }
        for (DeFiDataBean deFiDataBean : select()) {
            ids = ids.concat(deFiDataBean.getIdentity()).concat(",");
        }
        ids.substring(0, ids.length() - 1);
        return ids;
    }
}
