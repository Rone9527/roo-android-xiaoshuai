package com.core.domain.mine;

import java.io.Serializable;

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
 * Created on : 2021/8/12
 * PackageName : com.core.domain.mine
 * Description :
 */
public class BlockHeader implements Serializable {

    @Override
    public String toString() {
        return "BlockHeader{" +
                "blockID='" + blockID + '\'' +
                ", block_header=" + block_header +
                '}';
    }

    public String blockID;
    public BlockHeaderDTO block_header;

    public static class BlockHeaderDTO implements Serializable {
        public RawDataDTO raw_data;
        public String witness_signature;

        public static class RawDataDTO implements Serializable {
            public long number;
            public String txTrieRoot;

            @Override
            public String toString() {
                return "RawDataDTO{" +
                        "number=" + number +
                        ", txTrieRoot='" + txTrieRoot + '\'' +
                        ", witness_address='" + witness_address + '\'' +
                        ", parentHash='" + parentHash + '\'' +
                        ", version=" + version +
                        ", timestamp=" + timestamp +
                        '}';
            }

            public String witness_address;
            public String parentHash;
            public int version;
            public long timestamp;
        }
    }


}
