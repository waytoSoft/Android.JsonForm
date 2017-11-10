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

    public boolean image;

    public boolean imagRequired;

    public boolean video;

    public boolean videoRequired;

    public List<FormEntity> form;

    public List<FormEntity> flodForm;

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public List<FormEntity> getForm() {
        return form;
    }

    public void setForm(List<FormEntity> form) {
        this.form = form;
    }

    public boolean isImagRequired() {
        return imagRequired;
    }

    public void setImagRequired(boolean imagRequired) {
        this.imagRequired = imagRequired;
    }

    public boolean isVideoRequired() {
        return videoRequired;
    }

    public void setVideoRequired(boolean videoRequired) {
        this.videoRequired = videoRequired;
    }

    public List<FormEntity> getFlodForm() {
        return flodForm;
    }

    public void setFlodForm(List<FormEntity> flodForm) {
        this.flodForm = flodForm;
    }
}
