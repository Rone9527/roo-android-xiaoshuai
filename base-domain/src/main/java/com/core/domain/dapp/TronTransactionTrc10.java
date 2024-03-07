package com.core.domain.dapp;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.ArrayList;
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
 * Created on : 2021/9/15
 * PackageName : com.core.domain.dapp
 * Description :
 */
public class TronTransactionTrc10 implements Serializable {

    public boolean visible;
    public String txID;
    public RawDataBean raw_data;
    public String raw_data_hex;
    public ArrayList signature;
    public static class RawDataBean implements Serializable{
        public List<ContractBean> contract;
        public String ref_block_bytes;
        public String ref_block_hash;
        public long expiration;
        public long timestamp;

        public static class ContractBean implements Serializable {
            public ParameterBean parameter;
            public String type;

            public static class ParameterBean implements Serializable {
                public ValueBean value;
                public String type_url;

                public static class ValueBean implements Serializable{
                    public int amount;
                    public String asset_name;
                    public String owner_address;
                    public String to_address;
                }
            }
        }
    }
}
