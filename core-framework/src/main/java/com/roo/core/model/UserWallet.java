package com.roo.core.model;

import com.core.domain.link.LinkTokenBean;
import com.roo.core.app.annotation.CreateType;
import com.roo.core.utils.Kits;

import org.web3j.crypto.WalletFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义钱包数据结构
 */
public class UserWallet implements Serializable {

    private String remark;//钱包名称
    private boolean isSelected;//是否选中
    /**
     * 钱包创建方式
     * {@link CreateType 1:创建的 2:助记词导入的 3:私钥导入的}
     */
    private int createType;
    private boolean isBackUpped;//是否已备份
    private String mnemonics;//助记词
    private String mnemonics2;//助记词2
    private String privateKey;//私钥
    private String privateKey2;//私钥2
    private List<ChainWallet> chainWallets;//链钱包
    private List<String> listMainChainCode;//主链集合
    private ArrayList<LinkTokenBean.TokensBean> tokenList;//本钱包的token列表
    private ArrayList<LinkTokenBean.NodesBean> nodes;
    private long createTime;

    public UserWallet() {
        createTime = System.currentTimeMillis();
    }

    public int getCreateType() {
        return createType;
    }

    public void setCreateType(int createType) {
        this.createType = createType;
    }

    public List<String> getListMainChainCode() {
        listMainChainCode = Kits.Empty.check(listMainChainCode) ? new ArrayList<>() : listMainChainCode;
        return listMainChainCode;
    }

    public void setListMainChainCode(List<String> listMainChainCode) {
        this.listMainChainCode = listMainChainCode;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPrivateKey2() {
        return privateKey2;
    }

    public void setPrivateKey2(String privateKey) {
        this.privateKey2 = privateKey;
    }

    public List<ChainWallet> getChainWallets() {
        chainWallets = Kits.Empty.check(chainWallets) ? new ArrayList<>() : chainWallets;
        return chainWallets;
    }

    public void setChainWallets(List<ChainWallet> chainWallets) {
        this.chainWallets = chainWallets;
    }

    public String getMnemonics() {
        return mnemonics;
    }

    public void setMnemonics(String mnemonics) {
        this.mnemonics = mnemonics;
    }

    public String getMnemonics2() {
        return mnemonics2;
    }

    public void setMnemonics2(String mnemonics) {
        this.mnemonics2 = mnemonics;
    }

    public boolean isBackUpped() {
        return isBackUpped;
    }

    public void setBackUpped(boolean backUpped) {
        isBackUpped = backUpped;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ArrayList<LinkTokenBean.NodesBean> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<LinkTokenBean.NodesBean> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<LinkTokenBean.TokensBean> getTokenList() {
        return tokenList == null ? new ArrayList<>() : tokenList;
    }


    public void setTokenList(ArrayList<LinkTokenBean.TokensBean> tokenList) {
        this.tokenList = tokenList;
    }

    public static class ChainWallet {
        private WalletFile walletFile;
        private WalletFile walletFile2;
        private String chainCode;//所属主链

        public String getChainCode() {
            return chainCode;
        }

        public void setChainCode(String chainCode) {
            this.chainCode = chainCode;
        }

        public WalletFile getWalletFile() {
            return walletFile;
        }

        public void setWalletFile(WalletFile walletFile) {
            this.walletFile = walletFile;
        }
        public WalletFile getWalletFile2() {
            return walletFile2;
        }

        public void setWalletFile2(WalletFile walletFile) {
            this.walletFile2 = walletFile;
        }
    }

}
