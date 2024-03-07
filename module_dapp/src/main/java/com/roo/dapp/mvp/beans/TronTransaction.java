package com.roo.dapp.mvp.beans;

import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;


public class TronTransaction implements Serializable {

    public boolean visible;
    public String txID;
    public RawDataBean raw_data ;
    public String raw_data_hex;
    public PayInfoBean payInfo;

    public static class RawDataBean {
        public List<ContractBean> contract;
        public String ref_block_bytes;
        public String ref_block_hash;
        public long expiration;
        public int fee_limit;
        public long timestamp;

        public static class ContractBean implements Serializable{
            public ParameterBean parameter;
            public String type;

            public static class ParameterBean implements Serializable{
                public ValueBean value;
                public String type_url;

                public static class ValueBean implements Serializable{
                    public String data;
                    public String owner_address;
                    public String contract_address;
                    public long call_value;
                }
            }
        }
    }

    public static class PayInfoBean {
        public String contract_address;
        public String owner_address;
        public String function_selector;
        public String parameter;
        public int call_value;
        public int fee_limit;
    }
}
