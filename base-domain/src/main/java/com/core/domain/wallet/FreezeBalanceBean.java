package com.core.domain.wallet;

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
public class FreezeBalanceBean implements Serializable {

    @Override
    public String toString() {
        return "FreezeBalanceBean{" +
                "visible=" + visible +
                ", txID='" + txID + '\'' +
                ", raw_data=" + raw_data +
                ", raw_data_hex='" + raw_data_hex + '\'' +
                '}';
    }

    public boolean visible;
    public String txID;
    public RawDataBean raw_data;
    public String raw_data_hex;

    public static class RawDataBean implements Serializable {
        @Override
        public String toString() {
            return "RawDataBean{" +
                    "ref_block_bytes='" + ref_block_bytes + '\'' +
                    ", ref_block_hash='" + ref_block_hash + '\'' +
                    ", expiration=" + expiration +
                    ", timestamp=" + timestamp +
                    ", contract=" + contract +
                    '}';
        }

        public String ref_block_bytes;
        public String ref_block_hash;
        public long expiration;
        public long timestamp;
        public List<ContractBean> contract;

        public static class ContractBean implements Serializable {
            @Override
            public String toString() {
                return "ContractBean{" +
                        "parameter=" + parameter +
                        ", type='" + type + '\'' +
                        '}';
            }

            public ParameterBean parameter;
            public String type;

            public static class ParameterBean implements Serializable {
                public ValueBean value;
                public String type_url;

                public static class ValueBean implements Serializable {
                    @Override
                    public String toString() {
                        return "ValueBean{" +
                                "resource='" + resource + '\'' +
                                ", frozen_duration=" + frozen_duration +
                                ", frozen_balance=" + frozen_balance +
                                ", owner_address='" + owner_address + '\'' +
                                '}';
                    }

                    public String resource;
                    public int frozen_duration  ;
                    public int frozen_balance;
                    public String owner_address;
                }
            }
        }
    }
}
