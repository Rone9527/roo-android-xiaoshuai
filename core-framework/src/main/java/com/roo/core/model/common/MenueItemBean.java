package com.roo.core.model.common;

/**
 * <pre>
 *     project name: xuetu_android
 *     author      : 李琼
 *     create time : 2018/9/28 下午9:39
 *     desc        : 描述--//UserItemBean
 * </pre>
 */

public class MenueItemBean {
    protected String title;
    protected String desc;
    protected boolean activate;
    protected boolean isDot;
    protected int idRes;
    protected int drawableRes;
    protected int position;

    public MenueItemBean() {
    }

    public MenueItemBean(int idRes, String title, int drawableRes) {
        this(idRes, title, "", drawableRes, true);
    }

    public MenueItemBean(int idRes, String title, String desc, int drawableRes, boolean activate) {
        this.idRes = idRes;
        this.title = title;
        this.desc = desc;
        this.drawableRes = drawableRes;
        this.activate = activate;
    }

    protected MenueItemBean(Builder builder) {
        title = builder.title;
        desc = builder.desc;
        isDot = builder.isDot;
        activate = builder.activate;
        idRes = builder.idRes;
        drawableRes = builder.drawableRes;
        position = builder.position;
    }

    public boolean isDot() {
        return isDot;
    }

    public MenueItemBean setDot(boolean dot) {
        isDot = dot;
        return this;
    }

    public int getPosition() {
        return position;
    }

    public String getDesc() {
        return desc;
    }

    public MenueItemBean setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public int getIdRes() {
        return idRes;
    }

    public MenueItemBean setIdRes(int idRes) {
        this.idRes = idRes;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MenueItemBean setTitle(String title) {
        this.title = title;
        return this;
    }

    public MenueItemBean setTitle(int position) {
        this.position = position;
        return this;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public MenueItemBean setDrawableRes(int drawableRes) {
        this.drawableRes = drawableRes;
        return this;
    }

    public boolean isActivate() {
        return activate;
    }

    public MenueItemBean setActivate(boolean activate) {
        this.activate = activate;
        return this;
    }

    public static class Builder {
        private String title;
        private String desc;
        private boolean activate = true;
        private int idRes;
        private int drawableRes;
        private boolean isDot;
        private int position;

        public Builder() {
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder position(int position) {
            this.position = position;
            return this;
        }

        public Builder setDot(boolean dot) {
            isDot = dot;
            return this;
        }

        public Builder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public Builder activate(boolean activate) {
            this.activate = activate;
            return this;
        }

        public Builder idRes(int idRes) {
            this.idRes = idRes;
            return this;
        }

        public Builder drawableRes(int drawableRes) {
            this.drawableRes = drawableRes;
            return this;
        }

        public MenueItemBean build() {
            return new MenueItemBean(this);
        }
    }
}
