package com.jg.jsonform.picture.model.data.soure;

import android.content.Context;


import com.jg.jsonform.picture.model.data.PictureEntity;

import java.util.List;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/10/13 11:53
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public interface SelectImageDataSoure {

    interface QueryAlbumCallBack {
        Context getContext();

        int getPageIndex();

        int getPageSize();

        void onQueryAlbumSuccess(List<PictureEntity> imgs);

        void onQueryAlbumFailure(String error);
    }

    void queryAlbum(QueryAlbumCallBack callBack);
}
