package com.jg.jsonform.entity;

import java.util.List;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/10/31 11:24
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class SelectionBoxEntity {

    private String name;

    private boolean check;

    private int key;

    private List<FormEntity> form;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public List<FormEntity> getForm() {
        return form;
    }

    public void setForm(List<FormEntity> form) {
        this.form = form;
    }
}
