package com.jg.jsonform.form;

import com.jg.jsonform.form.fragment.FormRenderingFragment;
import com.jg.jsonform.form.fragment.fold.FormFoldEditFragment;
import com.jg.jsonform.form.fragment.fold.FormFoldFillFragment;
import com.jg.jsonform.form.fragment.fold.FormFoldShowFragment;

/**
 * 折叠表单
 * <p>
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/16 11:56
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormFoldMatchingActivity extends FormActivity {

    @Override
    protected FormRenderingFragment getFormFragment() {
        int flag = getIntent().getIntExtra("flag", 1);
        FormRenderingFragment fragment = null;
        if (flag == 1) {
            fragment = new FormFoldFillFragment();
        } else if (flag == 2) {
            fragment = new FormFoldShowFragment();
        } else if (flag == 3) {
            fragment = new FormFoldEditFragment();
        }
        return fragment;
    }

    @Override
    protected String getToolbarTitle() {
        return "折叠表单";
    }
}
