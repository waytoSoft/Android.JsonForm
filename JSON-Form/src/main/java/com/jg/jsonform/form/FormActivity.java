package com.jg.jsonform.form;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jg.jsonform.R;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/16 11:48
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public abstract class FormActivity extends AppCompatActivity {

    protected Toolbar mToolbar;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        initToolbar();

        getFragmentManager().beginTransaction().add(R.id.Form_FrameLayout, getFormFragment()).commit();
    }

    /**
     * 初始化Toolbar
     * <p>
     * author: hezhiWu
     * created at 2017/11/16 11:12
     */
    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.Form_Toolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mActionBar.setTitle(getToolbarTitle());

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected abstract FormRenderingFragment getFormFragment();

    protected abstract String getToolbarTitle();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getFormFragment().onActivityResult(requestCode, resultCode, data);
    }
}
