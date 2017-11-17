package com.jg.jsonform.asset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jg.jsonform.form.FormRenderingFragment;
import com.jg.jsonform.R;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/15 17:12
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormActivity extends AppCompatActivity {

    private FormRenderingFragment renderingFragment;
    private Toolbar mToolbar;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        initToolbar();

        int flag = getIntent().getIntExtra("flag", 1);
        if (flag == 1) {
            renderingFragment = new FormAddFragment();
            mToolbar.setTitle("添加设施");
        } else if (flag == 2) {
            mToolbar.setTitle("编辑设施");
            renderingFragment = new FormShowFragment();
        } else if (flag == 3) {
            mToolbar.setTitle("查看设施");
            renderingFragment = new FormEditFragment();
        }

        getFragmentManager().beginTransaction().add(R.id.Form_FrameLayout, renderingFragment).commit();
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

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        renderingFragment.onActivityResult(requestCode, resultCode, data);
    }
}
