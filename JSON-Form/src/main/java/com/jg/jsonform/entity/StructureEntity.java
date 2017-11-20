package com.jg.jsonform.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/10/24 17:32
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class StructureEntity implements Serializable {

    public boolean img;

    public boolean imgRequired;

    private String submitKey;

    private String matchingKey;

    public List<FormEntity> form;

    public List<FormEntity> flodForm;

    public boolean isImg() {
        return img;
    }

    public void setImg(boolean img) {
        this.img = img;
    }

    public String getSubmitKey() {
        return submitKey;
    }

    public void setSubmitKey(String submitKey) {
        this.submitKey = submitKey;
    }

    public String getMatchingKey() {
        return matchingKey;
    }

    public void setMatchingKey(String matchingKey) {
        this.matchingKey = matchingKey;
    }

    public List<FormEntity> getForm() {
        return form;
    }

    public void setForm(List<FormEntity> form) {
        this.form = form;
    }

    public boolean isImgRequired() {
        return imgRequired;
    }

    public void setImgRequired(boolean imgRequired) {
        this.imgRequired = imgRequired;
    }

    public List<FormEntity> getFlodForm() {
        return flodForm;
    }

    public void setFlodForm(List<FormEntity> flodForm) {
        this.flodForm = flodForm;
    }
}
