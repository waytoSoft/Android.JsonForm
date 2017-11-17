package com.jg.jsonform.picture.model;

import android.app.Activity;
import android.content.Context;

import com.jg.jsonform.picture.model.data.PictureEntity;
import com.jg.jsonform.picture.model.data.soure.SelectImageDataSoure;
import com.jg.jsonform.picture.model.data.soure.SelectImageRemoteRepo;

import java.util.List;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/10/13 11:53
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class SelectImagePresent implements SelectImageContract.Present, SelectImageDataSoure.QueryAlbumCallBack {

    private Activity mActivity;
    private SelectImageContract.SelectImageView selectImageView;

    public SelectImagePresent(Activity activity, SelectImageContract.SelectImageView selectImageView) {
        this.mActivity = activity;
        this.selectImageView = selectImageView;
    }

    @Override
    public void queryAlbum() {
        SelectImageRemoteRepo.newInstance().queryAlbum(this);
    }

    @Override
    public Context getContext() {
        return mActivity;
    }

    @Override
    public int getPageIndex() {
        return selectImageView.getPageIndex();
    }

    @Override
    public int getPageSize() {
        return selectImageView.getPageSize();
    }

    @Override
    public void onQueryAlbumSuccess(final List<PictureEntity> imgs) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (imgs != null && imgs.size() > 0) {
                    if (getPageIndex() == 0) {
                        selectImageView.onQueryAlbumSuccess(imgs);

                        selectImageView.onCloseDownRefresh();
                    } else {
                        selectImageView.onQueryAlbumMoreSuccess(imgs);

                        selectImageView.onClosePullRefresh();
                        if (imgs.size() < 20) {
                            selectImageView.onLoadMoreComplete();
                        }
                    }
                } else {
                    if (getPageIndex() == 0) {
                        selectImageView.onCloseDownRefresh();
                        selectImageView.onQueryEmpty();
                    } else {
                        selectImageView.onClosePullRefresh();
                        selectImageView.onLoadMoreComplete();
                    }
                }
            }
        });
    }

    @Override
    public void onQueryAlbumFailure(final String error) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                selectImageView.onQueryAlbumFailure(error);
            }
        });
    }
}
