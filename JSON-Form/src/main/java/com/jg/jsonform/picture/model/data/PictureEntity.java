package com.jg.jsonform.picture.model.data;

import java.io.Serializable;

/**
 * 选择图片实体类
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:31
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class PictureEntity implements Serializable {

    private String url="";
    private String date="";
    private boolean selecte;
    private String newUrl="";/*压缩过后图片路径*/

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSelecte() {
        return selecte;
    }

    public void setSelecte(boolean selecte) {
        this.selecte = selecte;
    }

    public String getNewUrl() {
        return newUrl;
    }

    public void setNewUrl(String newUrl) {
        this.newUrl = newUrl;
    }
}
