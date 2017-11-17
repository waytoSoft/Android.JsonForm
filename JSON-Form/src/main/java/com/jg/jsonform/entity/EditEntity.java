package com.jg.jsonform.entity;

import java.io.Serializable;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/10/24 11:09
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class EditEntity implements Serializable {

    /*输入类型*/
    private int inputType;

    /*是否可编辑*/
    private boolean enabled = true;

    /*右边图标*/
    private String icon;

    /*正式表单式*/
    private String regular;

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }
}
