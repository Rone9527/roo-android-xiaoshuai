package com.roo.home.mvp.model.bean;

public class BackupWord {
    private String text;
    private boolean select;
    private int index;

    public BackupWord(String text, boolean select, int index) {
        this.text = text;
        this.select = select;
        this.index = index;
    }

    public String getText() {
        return text;
    }

    public boolean isSelect() {
        return select;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getIndex() {
        return index;
    }
}
