package com.jg.jsonform.form;

import com.jg.jsonform.form.fragment.pure.FormPureEditFragment;
import com.jg.jsonform.form.fragment.pure.FormPureFillFragment;
import com.jg.jsonform.form.fragment.pure.FormPureShowFragment;

/**
 * 表单
 * <p>
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/16 11:52
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormPureMatchingActivity extends FormActivity {


    @Override
    protected FormRenderingFragment getFormFragment() {
        int flag=getIntent().getIntExtra("flag",1);

        FormRenderingFragment fragment=null;
        if (flag==1){
            fragment=new FormPureFillFragment();
        }else if (flag==2){
            fragment=new FormPureShowFragment();
        }else if (flag==3){
            fragment=new FormPureEditFragment();
        }else {
            finish();
        }

        return fragment;

    }

    @Override
    protected String getToolbarTitle() {
        return "纯表单";
    }
}
