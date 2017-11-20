package com.jg.jsonform.form.fragment;

import com.jg.jsonform.R;
import com.jg.jsonform.form.FormRenderingFragment;
import com.jg.jsonform.form.SelectPanelBottomDialog;
import com.jg.jsonform.utils.IUtil;
import com.jg.jsonform.view.FormEditView;
import com.jg.jsonform.view.FormTextView;

/**
 * 折叠表单
 * <p>
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/16 11:57
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormFoldFragment extends FormRenderingFragment {

    @Override
    public String getJsonForm() {
        return IUtil.getJson("formfold", getActivity());
    }

    @Override
    public void onClickSelectItemViewListener(FormTextView selectView, String key) {
//        super.onClickSelectItemViewListener(selectView, key);
        if ("type".equals(key)){
            SelectPanelBottomDialog dialog = new SelectPanelBottomDialog(getActivity());
            dialog.setContent(getResources().getStringArray(R.array.asset_type))
                    .setMultieselect(true)
                    .setDefalutContent(selectView.getFormText())
                    .setTextView(selectView.getFormTextView())
                    .setTitle("选择设施类型")
                    .show();
        }else if ("address".equals(key)){
            selectView.setFormText("广东省深圳市斯达大厦10号");
        }

    }

    @Override
    public void onClickWriteItemViewListener(FormEditView writeView, String key) {
//        super.onClickWriteItemViewListener(writeView, key);
        writeView.setFormText("广东省深圳市斯达大厦10号");
    }
}
