package com.core.domain.mine;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/7 14:33
 *     desc        : 描述--//LegalCurrencyBean 计价货币
 * </pre>
 */

public class SysConfigBean implements Serializable {

    /**
     * id : 1
     * type : 1
     * name : 帮助中心链接
     * code : HELP_CENTER_URl
     * value : [{"code":"help","name":"帮助中心","value":"https://roowallet.gitbook.io/roowallet/untitled\u2063","remark":"帮助中心"}]
     * sort : 1
     * createDate : 2021-06-05 15:04:20
     * updateDate : 2021-08-06 13:53:36
     */

    private int id;
    private int type;
    private String name;
    private String code;
    private int sort;
    private String createDate;
    private String updateDate;
    /**
     * code : help
     * name : 帮助中心
     * value : https://roowallet.gitbook.io/roowallet/untitled⁣
     * remark : 帮助中心
     */

    private List<ValueBean> value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public List<ValueBean> getValue() {
        return value;
    }

    public void setValue(List<ValueBean> value) {
        this.value = value;
    }

    public static class ValueBean implements Serializable{
        private String code;
        private String name;
        private String value;
        private String remark;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
