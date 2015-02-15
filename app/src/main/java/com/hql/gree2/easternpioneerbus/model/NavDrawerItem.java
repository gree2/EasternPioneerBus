package com.hql.gree2.easternpioneerbus.model;

import com.hql.gree2.easternpioneerbus.R;

public class NavDrawerItem {

    private String title;
    private int icon;
    private String count = "0";
    // boolean to set visibility of the counter
    private boolean isCounterVisible = false;

    public NavDrawerItem() {
    }

    public NavDrawerItem(String title) {
        this.title = title;
        this.icon = R.drawable.directions_bus;
    }

    public NavDrawerItem(String title, boolean isCounterVisible, String count) {
        this.title = title;
        this.icon = R.drawable.directions_bus;
        this.isCounterVisible = isCounterVisible;
        this.count = count;
    }

    public String getTitle() {
        return this.title;
    }

    public int getIcon() {
        return this.icon;
    }

    public String getCount() {
        return this.count;
    }

    public boolean getCounterVisibility() {
        return this.isCounterVisible;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setCounterVisibility(boolean isCounterVisible) {
        this.isCounterVisible = isCounterVisible;
    }
}

