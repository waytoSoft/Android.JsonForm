package com.jg.jsonform.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/10/31 14:08
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class SelfAdapterGridView extends GridView {

    public SelfAdapterGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelfAdapterGridView(Context context) {
        super(context);
    }

    public SelfAdapterGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
