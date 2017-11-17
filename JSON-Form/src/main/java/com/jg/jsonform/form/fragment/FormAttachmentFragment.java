package com.jg.jsonform.form.fragment;

import com.jg.jsonform.form.FormRenderingFragment;
import com.jg.jsonform.utils.IUtil;

/**
 * 带附件表单
 * <p>
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/16 11:55
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormAttachmentFragment extends FormRenderingFragment {

    @Override
    public String getJsonForm() {
        return IUtil.getJson("formattachment", getActivity());
    }
}
