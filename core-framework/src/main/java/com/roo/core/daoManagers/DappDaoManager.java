package com.roo.core.daoManagers;


import com.core.domain.dapp.DappBean;
import com.core.domain.dapp.DappBeanDao;
import com.roo.core.ComponentApplication;
import com.roo.core.app.constants.Constants;
import com.roo.core.utils.Kits;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class DappDaoManager {

    /**
     * 获取Dao
     */
    public static DappBeanDao getDappDao() {
        return ComponentApplication.daoSession.getDappBeanDao();
    }

    /**
     * 删除
     */
    public static void clear() {
        getDappDao().deleteAll();
    }


    /**
     * 删除
     */
    public static void delete(String name, int dataBaseType) {
        QueryBuilder<DappBean> qb = getDappDao().queryBuilder();
        qb.where(DappBeanDao.Properties.Name.eq(name),
                DappBeanDao.Properties.DataBaseType.eq(dataBaseType)
        );
        List<DappBean> list = qb.build().list();
        for (DappBean dappBean : list) {
            getDappDao().delete(dappBean);
        }
    }


    /**
     * 查询是否已存在
     */
    public static boolean isExist(String name, int dataBaseType) {
        QueryBuilder<DappBean> qb = getDappDao().queryBuilder();
        qb.where(DappBeanDao.Properties.Name.eq(name),
                DappBeanDao.Properties.DataBaseType.eq(dataBaseType)
        );
        return qb.buildCount().count() > 0;
    }

    /**
     * 查询所有
     */
    public static List<DappBean> select(int dataBaseType) {
        return getDappDao().queryBuilder()
                .where(DappBeanDao.Properties.DataBaseType.eq(dataBaseType))
                .build().list();
    }

    /**
     * 查询所有
     */
    public static int selectCount(int dataBaseType) {
        return (int) getDappDao().queryBuilder()
                .where(DappBeanDao.Properties.DataBaseType.eq(dataBaseType))
                .buildCount().count();
    }

    /**
     * 查询一条
     */
    public static DappBean select(String name, int dataBaseType) {
        QueryBuilder<DappBean> qb = getDappDao().queryBuilder();
        qb.where(DappBeanDao.Properties.Name.eq(name),
                DappBeanDao.Properties.DataBaseType.eq(dataBaseType)
        );
        List<DappBean> list = qb.build().list();
        if (Kits.Empty.check(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 保存
     */
    public static boolean insertRecent(DappBean dappBean) {

        if (isExist(dappBean.getName(), Constants.RECENT)) {
            DappBean select = select(dappBean.getName(), Constants.RECENT);
            select.setDataBaseCreateTime(System.currentTimeMillis());
            getDappDao().update(select);
        } else {
            dappBean.setId(null);
            dappBean.setDataBaseType(Constants.RECENT);
            dappBean.setDataBaseCreateTime(System.currentTimeMillis());
            getDappDao().insert(dappBean);
        }
        return true;
    }

    /**
     * 保存
     */
    public static boolean insertFavorite(DappBean dappBean) {

        if (isExist(dappBean.getName(), Constants.FAVORITE)) {
            DappBean select = select(dappBean.getName(), Constants.FAVORITE);
            getDappDao().update(select);
        } else {
            dappBean.setId(null);
            dappBean.setDataBaseType(Constants.FAVORITE);
            dappBean.setDataBaseCreateTime(System.currentTimeMillis());
            getDappDao().insert(dappBean);
        }
        return true;
    }

    /**
     * 保存
     */
    public static void syn(DappBean dappBean) {
        if (dappBean.getDataBaseType() == Constants.FAVORITE ||
                dappBean.getDataBaseType() == Constants.RECENT) {

            DappBean select = select(dappBean.getName(), Constants.FAVORITE);

            select.setName(dappBean.getName());
            select.setIcon(dappBean.getIcon());
            select.setType(dappBean.getType());
            select.setLinks(dappBean.getLinks());
            select.setHots(dappBean.getHots());
            select.setChain(dappBean.getChain());
            select.setDisplay(dappBean.getDisplay());
            select.setSort(dappBean.getSort());
            select.setDiscription(dappBean.getDiscription());
            select.setCreateTime(dappBean.getCreateTime());
            select.setMultis(dappBean.getMultis());

            getDappDao().update(select);
        }
    }

}
