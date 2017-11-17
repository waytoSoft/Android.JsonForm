package com.jg.jsonform.attachment;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 附件GridView布局
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:08
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class ImageAttachmentGridView extends GridView {

    public ImageAttachmentGridView(Context context) {
        super(context);
    }

    public ImageAttachmentGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageAttachmentGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
