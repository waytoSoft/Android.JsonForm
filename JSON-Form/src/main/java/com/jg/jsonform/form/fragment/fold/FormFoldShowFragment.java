package com.jg.jsonform.form.fragment.fold;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/20 10:14
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormFoldShowFragment extends FormFoldBaseFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        renderingFormData(resulutJson, false);
    }
}
