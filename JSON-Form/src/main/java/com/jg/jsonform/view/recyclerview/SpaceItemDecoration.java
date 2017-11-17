package com.jg.jsonform.view.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:20
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        //注释这两行是为了上下间距相同
//        if(parent.getChildAdapterPosition(view)==0){
        outRect.top = space;
//        }
    }
}