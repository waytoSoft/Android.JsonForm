package com.jg.jsonform.picture.model;

import com.jg.jsonform.picture.model.data.PictureEntity;

import java.util.List;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/10/13 11:42
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public interface SelectImageContract {

    interface SelectImageView {
        int getPageIndex();

        int getPageSize();

        void onQueryAlbumSuccess(List<PictureEntity> imgs);

        void onQueryAlbumMoreSuccess(List<PictureEntity> imgs);

        void onQueryAlbumFailure(String error);

        void onQueryEmpty();

        void onCloseDownRefresh();

        void onClosePullRefresh();

        void onLoadMoreComplete();
    }

    interface Present {
        void queryAlbum();
    }
}
