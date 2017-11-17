package com.jg.jsonform.picture;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * 图片尺寸设置
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:16
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class PhotoAlbumImageView extends AppCompatImageView {
    private OnMeasureListener onMeasureListener;

    public void setOnMeasureListener(OnMeasureListener onMeasureListener) {
        this.onMeasureListener = onMeasureListener;
    }

    public PhotoAlbumImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotoAlbumImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (onMeasureListener != null) {
            onMeasureListener.onMeasureSize(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    public interface OnMeasureListener {
        void onMeasureSize(int width, int height);
    }

}
