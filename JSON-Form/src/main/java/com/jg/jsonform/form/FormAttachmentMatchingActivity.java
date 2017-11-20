package com.jg.jsonform.form;

import com.jg.jsonform.form.fragment.attachment.FormAttachmentEditFragment;
import com.jg.jsonform.form.fragment.attachment.FormAttachmentFillFragment;
import com.jg.jsonform.form.fragment.attachment.FormAttachmentShowlFragment;
import com.jg.jsonform.form.fragment.FormRenderingFragment;

/**
 * 带附件表单
 * <p>
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/16 11:55
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormAttachmentMatchingActivity extends FormActivity {

    @Override
    protected FormRenderingFragment getFormFragment() {
        int flag = getIntent().getIntExtra("flag", 1);
        FormRenderingFragment fragment = null;
        if (flag == 1) {
            fragment = new FormAttachmentFillFragment();
        } else if (flag == 2) {
            fragment = new FormAttachmentShowlFragment();
        } else if (flag == 3) {
            fragment = new FormAttachmentEditFragment();
        }
        return fragment;
    }

    @Override
    protected String getToolbarTitle() {
        return "带附件表单";
    }
}
