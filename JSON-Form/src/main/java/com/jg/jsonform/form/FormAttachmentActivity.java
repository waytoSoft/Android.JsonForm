package com.jg.jsonform.form;

import com.jg.jsonform.form.fragment.FormAttachmentFragment;

/**
 * 带附件表单
 * <p>
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/16 11:55
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormAttachmentActivity extends FormActivity {

    @Override
    protected FormRenderingFragment getFormFragment() {
        return new FormAttachmentFragment();
    }

    @Override
    protected String getToolbarTitle() {
        return "带附件表单";
    }
}
