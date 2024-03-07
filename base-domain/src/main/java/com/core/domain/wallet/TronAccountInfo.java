package com.core.domain.wallet;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

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
 * Created on : 2021/8/24
 * PackageName : com.core.domain.wallet
 * Description :
 */
public class TronAccountInfo implements Serializable {


    public String address;
    public int balance;
    public long create_time;
    public long latest_opration_time;
    public int free_net_usage;
    public long latest_consume_free_time;
    public AccountResourceBean account_resource;
    public OwnerPermissionBean owner_permission;
    public List<AccountResourceBean.FrozenBalanceForEnergyBean> frozen;
    public List<ActivePermissionBean> active_permission;
    public List<AssetV2Bean> assetV2;
    public List<AssetV2Bean> free_asset_net_usageV2;
    public long acquired_delegated_frozen_balance_for_bandwidth;//他人冻结带宽
    public static class AccountResourceBean implements Serializable {
        public FrozenBalanceForEnergyBean frozen_balance_for_energy;
        public long latest_consume_time_for_energy;
        public long acquired_delegated_frozen_balance_for_energy;
        public static class FrozenBalanceForEnergyBean implements Serializable {
            public int frozen_balance;
            public long expire_time;
        }
    }

    public static class OwnerPermissionBean implements Serializable {
        public String permission_name;
        public int threshold;
        public List<KeysBean> keys;

        public static class KeysBean implements Serializable {
            public String address;
            public int weight;
        }
    }

    public static class ActivePermissionBean implements Serializable {
        public String type;
        public int id;
        public String permission_name;
        public int threshold;
        public String operations;
        public List<OwnerPermissionBean.KeysBean> keys;
    }

    public static class AssetV2Bean implements Serializable {
        public String key;
        public int value;
    }
}
