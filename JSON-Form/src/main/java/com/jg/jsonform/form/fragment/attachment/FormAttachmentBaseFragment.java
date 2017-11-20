package com.jg.jsonform.form.fragment.attachment;

import com.jg.jsonform.form.fragment.FormRenderingFragment;
import com.jg.jsonform.utils.IUtil;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/20 10:15
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormAttachmentBaseFragment extends FormRenderingFragment {
    protected String resultJson = "{\n" +
            "    \"assetNo\": \"Wayto01\",\n" +
            "    \"workAddress\": \"广东省深圳市南山区科兴大厦\",\n" +
            "    \"status\": 1,\n" +
            "    \"position\": \"故障部位二\",\n" +
            "    \"reason\": \"开挖\",\n" +
            "    \"remark\": \"世界和平\",\n" +
            "    \"Attachments\": [\n" +
            "        \"http://fd.topitme.com/d/a8/1d/11315383988791da8do.jpg\"\n" +
            "    ]\n" +
            "}";

    @Override
    public String getJsonForm() {
        return IUtil.getJson("attachment.json", getActivity());
    }
}
