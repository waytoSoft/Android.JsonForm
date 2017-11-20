package com.jg.jsonform.form.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

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
public class FormAttachmentShowlFragment extends FormRenderingFragment {

    private String resultJson = "{\n" +
            "    \"assetNo\": \"Wayto01\",\n" +
            "    \"workAddress\": \"广东省深圳市南山区科兴大厦\",\n" +
            "    \"status\": 1,\n" +
            "    \"poistion\": \"故障部位2\",\n" +
            "    \"reason\": \"开挖\",\n" +
            "    \"remark\": \"世界和平\",\n" +
            "    \"Attachments\": [\n" +
            "        \"http://fd.topitme.com/d/a8/1d/11315383988791da8do.jpg,http://image.tianjimedia.com/uploadImages/2014/289/01/IGS09651F94M.jpg\"\n" +
            "    ]\n" +
            "}";

    @Override
    public String getJsonForm() {
        return IUtil.getJson("attachment", getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        renderingFormData(resultJson);
    }
}
