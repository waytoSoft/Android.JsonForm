package com.jg.jsonform.form;

import com.jg.jsonform.form.fragment.FormFoldFragment;

/**
 * 折叠表单
 * <p>
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/16 11:56
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FromFoldActivity extends FormActivity {

    @Override
    protected FormRenderingFragment getFormFragment() {
        return new FormFoldFragment();
    }

    @Override
    protected String getToolbarTitle() {
        return "折叠表单";
    }
}
