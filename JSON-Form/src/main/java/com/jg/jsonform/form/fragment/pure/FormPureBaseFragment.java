package com.jg.jsonform.form.fragment.pure;

import com.jg.jsonform.form.FormRenderingFragment;
import com.jg.jsonform.utils.IUtil;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/11/18$ 下午10:55$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class FormPureBaseFragment extends FormRenderingFragment {

    protected String resultJson="{\n" +
            "    \"userName\": \"张大大\",\n" +
            "    \"sex\": \"男\",\n" +
            "    \"phoneNumber\": 1862344432,\n" +
            "    \"educationLevel\": \"硕士以上\",\n" +
            "    \"documentType\": 1,\n" +
            "    \"documentNumber\": 432234199009035460,\n" +
            "    \"workAddress\": \"广东省深圳市南山区\",\n" +
            "    \"companyName\": \"未来科技\",\n" +
            "    \"companyAddress\": \"广东省深圳市南山区科兴大厦\",\n" +
            "    \"companyPhone\": 8798783,\n" +
            "    \"remark\": \"世界和平\"\n" +
            "}";

    @Override
    public String getJsonForm() {
        return IUtil.getJson("formpure", getActivity());
    }
}
