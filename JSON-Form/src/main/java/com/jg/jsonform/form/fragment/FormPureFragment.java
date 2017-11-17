package com.jg.jsonform.form.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.jg.jsonform.R;
import com.jg.jsonform.form.FormRenderingFragment;
import com.jg.jsonform.utils.IUtil;

/**
 * 表单
 * <p>
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/16 11:53
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormPureFragment extends FormRenderingFragment {

    @Override
    public String getJsonForm() {
        return IUtil.getJson("formpure.json", getActivity());
    }

    @Override
    public View getBottomView() {
        View bottomView = LayoutInflater.from(getActivity()).inflate(R.layout.submit_layout, null);
        bottomView.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFormVale() != null)
                    Log.d("FormPureFragment", getFormVale().toString());
            }
        });
        return bottomView;
    }
}
