package com.core.domain.mine;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransferRecordBean implements Parcelable {

    /**
     * totalPage : 2
     * data : [{"txId":"0xab7f8ae18cd7a7b56310dbe4368c01ce6ae3104cd4688f87faaec12f1a4307a9","blockHash":"0x2b611fc279a6bbda7fcc8f698e5e5b4d84a7a786bec9af089b56e52bfdb35122","blockNum":8784694,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006792},{"txId":"0x7ee662161e2e990223db728e6bb429d9180e1b4cf7c725d5ed3b478c9392f278","blockHash":"0x2b611fc279a6bbda7fcc8f698e5e5b4d84a7a786bec9af089b56e52bfdb35122","blockNum":8784694,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006792},{"txId":"0xc88ce241ce767e546dfa2f4e176e2f542111ebf9d1832b712b4a3d23758d3229","blockHash":"0x2b611fc279a6bbda7fcc8f698e5e5b4d84a7a786bec9af089b56e52bfdb35122","blockNum":8784694,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006792},{"txId":"0xe6738b6a8f8b89d4ab6fb79830d31126040dc12e9dedc3e6afc44080abd2b0f1","blockHash":"0x2b611fc279a6bbda7fcc8f698e5e5b4d84a7a786bec9af089b56e52bfdb35122","blockNum":8784694,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006792},{"txId":"0xe7b8505cd2ac8c56d7f17420894940259922cdc45885ae3c5b4856c175ee7e95","blockHash":"0x2b611fc279a6bbda7fcc8f698e5e5b4d84a7a786bec9af089b56e52bfdb35122","blockNum":8784694,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006792},{"txId":"0x2029c5b9196fa8ba5cf17b717b1d48a6a6d9639b0ee4cfa0c170a912b3bc5b0d","blockHash":"0x2b611fc279a6bbda7fcc8f698e5e5b4d84a7a786bec9af089b56e52bfdb35122","blockNum":8784694,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006792},{"txId":"0xcd7987503733fd8c09a5e8301360b87c1414bb25746a59ebbeca46ef7a69548f","blockHash":"0x2b611fc279a6bbda7fcc8f698e5e5b4d84a7a786bec9af089b56e52bfdb35122","blockNum":8784694,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006792},{"txId":"0x5dd880bab5074690bf7c92bd7153f3e8416ae74fb5a879a2a6be7c64cd933a9a","blockHash":"0x039068c94fadcd87540a115b7c31a9cae3e2e0271a3b80c336f5da2945633563","blockNum":8784693,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006777},{"txId":"0x6c5692004281506b04942f893107d0ff468d2c8b3fb290f9aa96ee1d86912764","blockHash":"0x039068c94fadcd87540a115b7c31a9cae3e2e0271a3b80c336f5da2945633563","blockNum":8784693,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006777},{"txId":"0x7a3bac2592ccace0a54b1aa03ae28b5863f5985ed3a4ca1dba1fc1a5113b69af","blockHash":"0x039068c94fadcd87540a115b7c31a9cae3e2e0271a3b80c336f5da2945633563","blockNum":8784693,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006777},{"txId":"0x5cd001c8a4407ea4deb9ccc6e7b2f9ab32cd8e3dabe1e1cc0513b4c13cc3e56e","blockHash":"0x039068c94fadcd87540a115b7c31a9cae3e2e0271a3b80c336f5da2945633563","blockNum":8784693,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006777},{"txId":"0x6d76b459fd80b5c03ddb745235fb8a729d558b13f04857700956d14b62658c9e","blockHash":"0x039068c94fadcd87540a115b7c31a9cae3e2e0271a3b80c336f5da2945633563","blockNum":8784693,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006777},{"txId":"0x93332f382e6c0fa952a39abc702ddb7106885af0047666027600bf44c82726b1","blockHash":"0x039068c94fadcd87540a115b7c31a9cae3e2e0271a3b80c336f5da2945633563","blockNum":8784693,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006777},{"txId":"0x49aa034136bc94997c7604bd700483e1bc78cf8e77577e0a5a58cbddd0b004ec","blockHash":"0x039068c94fadcd87540a115b7c31a9cae3e2e0271a3b80c336f5da2945633563","blockNum":8784693,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006777},{"txId":"0x852c87185f0b65fc8a4ff524b374e3cf0d385d306c7744dbe346a18b3f8ea089","blockHash":"0x039068c94fadcd87540a115b7c31a9cae3e2e0271a3b80c336f5da2945633563","blockNum":8784693,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006777},{"txId":"0x70c99071360f9ee3b518eb24455f64a91498fff7d5e49cb803173f7ad0f73322","blockHash":"0x3005f40b0c544bbd43b6869785685bf4a845d30b7bd7d0aa2d22d39425cc6622","blockNum":8784691,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006747},{"txId":"0x8d9710ca29eccf7661d07aa509a6a3bdcc94b4a858c11b2a3f82b20d098b7fb8","blockHash":"0x02ffee198db14a0b43bafa731ac80a30e787fee36becd99ea16a1c81218fe0d9","blockNum":8784688,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006702},{"txId":"0xbfb2ab0303f58ac0ede3951998cb460010be472054eff8d7ed9361e55cdb1fe9","blockHash":"0x02ffee198db14a0b43bafa731ac80a30e787fee36becd99ea16a1c81218fe0d9","blockNum":8784688,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006702},{"txId":"0x4e4b6fecbcde869a68dccf0f5a74ffe30d98040eff10d1534bd5a6ebc0d231ec","blockHash":"0x02ffee198db14a0b43bafa731ac80a30e787fee36becd99ea16a1c81218fe0d9","blockNum":8784688,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006702},{"txId":"0x0925b0e03c626613f215cbf623af0995d9ca927ca5247a49f4bd7e03bf5930c2","blockHash":"0x02ffee198db14a0b43bafa731ac80a30e787fee36becd99ea16a1c81218fe0d9","blockNum":8784688,"token":"ETH","fromAddr":"0x26f1ab5a967c51a79863a767a20e0564e5aa5e44","toAddr":"0x6b1141fff79a96496bbb0673009424bae1418fa2","amount":"0","gasLimit":"6000000","gasPrice":"1000000000","gasUsed":"37654","convertGasUsed":"0.000037654","feeToken":"ETH","timeStamp":1624006702}]
     */

    private int totalPage;
    /**
     * txId : 0xab7f8ae18cd7a7b56310dbe4368c01ce6ae3104cd4688f87faaec12f1a4307a9
     * blockHash : 0x2b611fc279a6bbda7fcc8f698e5e5b4d84a7a786bec9af089b56e52bfdb35122
     * blockNum : 8784694
     * token : ETH
     * fromAddr : 0x26f1ab5a967c51a79863a767a20e0564e5aa5e44
     * toAddr : 0x6b1141fff79a96496bbb0673009424bae1418fa2
     * amount : 0
     * gasLimit : 6000000
     * gasPrice : 1000000000
     * gasUsed : 37654
     * convertGasUsed : 0.000037654
     * feeToken : ETH
     * timeStamp : 1624006792
     */

    private List<DataBean> data;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        private String txId;
        private String blockHash;
        private int blockNum;
        private String token;
        private String fromAddr;
        private String toAddr;
        private BigDecimal amount;
        private BigDecimal gasLimit;
        private BigDecimal gasPrice;
        private BigDecimal gasUsed;
        private BigDecimal convertGasUsed;
        private String feeToken;
        private long timeStamp;
        private String statusType;

        public boolean isFromSelf() {
            return isFromSelf;
        }

        public void setFromSelf(boolean fromSelf) {
            isFromSelf = fromSelf;
        }

        public static Creator<DataBean> getCREATOR() {
            return CREATOR;
        }

        private boolean isFromSelf = false;//如果是自己创造的临时Pendding

        public String getTxId() {
            return txId;
        }

        public void setTxId(String txId) {
            this.txId = txId;
        }

        public String getBlockHash() {
            return blockHash;
        }

        public void setBlockHash(String blockHash) {
            this.blockHash = blockHash;
        }

        public int getBlockNum() {
            return blockNum;
        }

        public void setBlockNum(int blockNum) {
            this.blockNum = blockNum;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getFromAddr() {
            return fromAddr;
        }

        public void setFromAddr(String fromAddr) {
            this.fromAddr = fromAddr;
        }

        public String getToAddr() {
            return toAddr;
        }

        public void setToAddr(String toAddr) {
            this.toAddr = toAddr;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public BigDecimal getGasLimit() {
            return gasLimit;
        }

        public void setGasLimit(BigDecimal gasLimit) {
            this.gasLimit = gasLimit;
        }

        public BigDecimal getGasPrice() {
            return gasPrice;
        }

        public void setGasPrice(BigDecimal gasPrice) {
            this.gasPrice = gasPrice;
        }

        public BigDecimal getGasUsed() {
            return gasUsed;
        }

        public void setGasUsed(BigDecimal gasUsed) {
            this.gasUsed = gasUsed;
        }

        public BigDecimal getConvertGasUsed() {
            return convertGasUsed;
        }

        public void setConvertGasUsed(BigDecimal convertGasUsed) {
            this.convertGasUsed = convertGasUsed;
        }

        public String getFeeToken() {
            return feeToken;
        }

        public void setFeeToken(String feeToken) {
            this.feeToken = feeToken;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getStatusType() {
            return statusType;
        }

        public void setStatusType(String statusType) {
            this.statusType = statusType;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.txId);
            dest.writeString(this.blockHash);
            dest.writeInt(this.blockNum);
            dest.writeString(this.token);
            dest.writeString(this.fromAddr);
            dest.writeString(this.toAddr);
            dest.writeSerializable(this.amount);
            dest.writeSerializable(this.gasLimit);
            dest.writeSerializable(this.gasPrice);
            dest.writeSerializable(this.gasUsed);
            dest.writeSerializable(this.convertGasUsed);
            dest.writeString(this.feeToken);
            dest.writeLong(this.timeStamp);
            dest.writeString(this.statusType);
        }

        public void readFromParcel(Parcel source) {
            this.txId = source.readString();
            this.blockHash = source.readString();
            this.blockNum = source.readInt();
            this.token = source.readString();
            this.fromAddr = source.readString();
            this.toAddr = source.readString();
            this.amount = (BigDecimal) source.readSerializable();
            this.gasLimit = (BigDecimal) source.readSerializable();
            this.gasPrice = (BigDecimal) source.readSerializable();
            this.gasUsed = (BigDecimal) source.readSerializable();
            this.convertGasUsed = (BigDecimal) source.readSerializable();
            this.feeToken = source.readString();
            this.timeStamp = source.readLong();
            this.statusType = source.readString();
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.txId = in.readString();
            this.blockHash = in.readString();
            this.blockNum = in.readInt();
            this.token = in.readString();
            this.fromAddr = in.readString();
            this.toAddr = in.readString();
            this.amount = (BigDecimal) in.readSerializable();
            this.gasLimit = (BigDecimal) in.readSerializable();
            this.gasPrice = (BigDecimal) in.readSerializable();
            this.gasUsed = (BigDecimal) in.readSerializable();
            this.convertGasUsed = (BigDecimal) in.readSerializable();
            this.feeToken = in.readString();
            this.timeStamp = in.readLong();
            this.statusType = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.totalPage);
        dest.writeList(this.data);
    }

    public void readFromParcel(Parcel source) {
        this.totalPage = source.readInt();
        this.data = new ArrayList<DataBean>();
        source.readList(this.data, DataBean.class.getClassLoader());
    }

    public TransferRecordBean() {
    }

    protected TransferRecordBean(Parcel in) {
        this.totalPage = in.readInt();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Creator<TransferRecordBean> CREATOR = new Creator<TransferRecordBean>() {
        @Override
        public TransferRecordBean createFromParcel(Parcel source) {
            return new TransferRecordBean(source);
        }

        @Override
        public TransferRecordBean[] newArray(int size) {
            return new TransferRecordBean[size];
        }
    };
}
