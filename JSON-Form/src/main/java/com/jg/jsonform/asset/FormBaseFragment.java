package com.jg.jsonform.asset;

import com.jg.jsonform.form.FormRenderingFragment;
import com.jg.jsonform.utils.IUtil;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/16 11:18
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormBaseFragment extends FormRenderingFragment {

    @Override
    public String getJsonForm() {
        return IUtil.getJson("asset.json", getActivity());
    }
}
