package com.jg.jsonform.form.fragment.attachment;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.jg.jsonform.R;
import com.jg.jsonform.picture.SelectImageActivity;

/**
 * 带附件表单
 * <p>
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/16 11:55
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormAttachmentFillFragment extends FormAttachmentBaseFragment {

    @Override
    public View getBottomView() {
        View bottomView = LayoutInflater.from(getActivity()).inflate(R.layout.submit_layout, null);
        bottomView.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFormVale() != null)
                    Log.d("FormFoldFillFragment", getFormVale().toString());
            }
        });
        return bottomView;
    }
}