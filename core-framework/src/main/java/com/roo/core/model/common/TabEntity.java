package com.roo.core.model.common;

import com.flyco.tablayout.listener.CustomTabEntity;

public class TabEntity implements CustomTabEntity {
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;

    public TabEntity() {
    }

    public TabEntity(String title) {
        this(title, 0, 0);
    }

    public TabEntity(String title, int selectedIcon) {
        this(title, selectedIcon, 0);
    }

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }

    public TabEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public TabEntity setSelectedIcon(int selectedIcon) {
        this.selectedIcon = selectedIcon;
        return this;
    }

    public TabEntity setUnSelectedIcon(int unSelectedIcon) {
        this.unSelectedIcon = unSelectedIcon;
        return this;
    }
}