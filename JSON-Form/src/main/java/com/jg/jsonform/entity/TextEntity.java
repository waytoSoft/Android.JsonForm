package com.jg.jsonform.entity;

import java.io.Serializable;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/10/24 11:08
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class TextEntity implements Serializable {

    /*选择内容*/
    private String[] key;

    /*序列列后对应的值*/
    private int[] value;

    /*是否多选*/
    private boolean multiselect;

    /*图标*/
    private String icon;

    /*是否子类继承处理点击选择事件*/
    private boolean superClick;


    public String[] getKey() {
        return key;
    }

    public void setKey(String[] array) {
        this.key = array;
    }

    public boolean isMultiselect() {
        return multiselect;
    }

    public void setMultiselect(boolean multiselect) {
        this.multiselect = multiselect;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isSuperClick() {
        return superClick;
    }

    public void setSuperClick(boolean superClick) {
        this.superClick = superClick;
    }

    public int[] getValue() {
        return value;
    }

    public void setValue(int[] value) {
        this.value = value;
    }
}
