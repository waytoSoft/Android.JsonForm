package com.jg.jsonform.form.fragment.fold;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.jg.jsonform.R;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/20 10:14
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormFoldEditFragment extends FormFoldBaseFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        renderingFormData(resulutJson, true);
    }

    @Override
    public View getBottomView() {
        View bottomView = LayoutInflater.from(getActivity()).inflate(R.layout.submit_layout, null);
        bottomView.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFormVale() != null)
                    Log.d("FormFoldEditFragment", getFormVale().toString());
            }
        });
        return bottomView;
    }

}
