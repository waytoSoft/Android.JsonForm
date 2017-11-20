package com.jg.jsonform.form.fragment.fold;

import com.jg.jsonform.R;
import com.jg.jsonform.form.SelectPanelBottomDialog;
import com.jg.jsonform.form.fragment.FormRenderingFragment;
import com.jg.jsonform.utils.IUtil;
import com.jg.jsonform.view.FormEditView;
import com.jg.jsonform.view.FormTextView;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/20 10:13
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormFoldBaseFragment extends FormRenderingFragment {

    protected String resulutJson="{\n" +
            "    \"assetCode\": \"Wayto001\",\n" +
            "    \"type\": \"管网\",\n" +
            "    \"status\": \"正常\",\n" +
            "    \"address\": \"广东省深圳市福田区梅林路19号\",\n" +
            "    \"PlatedTime\": \"2017-11-05\",\n" +
            "    \"ResponsibleName\": \"张山山\",\n" +
            "    \"remark\": \"一切正常\",\n" +
            "    \"Size\": \"32\",\n" +
            "    \"ReservedCount1\": \"完好\",\n" +
            "    \"ReservedText1\": \"运维网络\",\n" +
            "    \"OperatingVendorName\": \"伟图科技\",\n" +
            "    \"Appearance\": \"完好\",\n" +
            "    \"ReservedText3\": \"完好\",\n" +
            "    \"ReservedCount2\": \"正常\"\n" +
            "}";

    @Override
    public String getJsonForm() {
        return IUtil.getJson("formfold.json", getActivity());
    }

    @Override
    public void onClickSelectItemViewListener(FormTextView selectView, String key) {
        if ("type".equals(key)) {
            SelectPanelBottomDialog dialog = new SelectPanelBottomDialog(getActivity());
            dialog.setContent(getResources().getStringArray(R.array.asset_type))
                    .setMultieselect(true)
                    .setDefalutContent(selectView.getFormText())
                    .setTextView(selectView.getFormTextView())
                    .setTitle("选择设施类型")
                    .show();
        } else if ("address".equals(key)) {
            selectView.setFormText("广东省深圳市斯达大厦10号");
        }

    }

    @Override
    public void onClickWriteItemViewListener(FormEditView writeView, String key) {
        writeView.setFormText("广东省深圳市斯达大厦10号");
    }
}
