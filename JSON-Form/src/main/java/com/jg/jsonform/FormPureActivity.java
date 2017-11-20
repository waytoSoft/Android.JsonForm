package com.jg.jsonform;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jg.jsonform.form.FormPureMatchingActivity;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/11/18$ 下午10:48$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class FormPureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pure);

        findViewById(R.id.Button_Form_Pure_fill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FormPureActivity.this,FormPureMatchingActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
            }
        });

        findViewById(R.id.Button_Form_Pure_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FormPureActivity.this,FormPureMatchingActivity.class);
                intent.putExtra("flag",2);
                startActivity(intent);
            }
        });

        findViewById(R.id.Button_Form_Pure_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FormPureActivity.this,FormPureMatchingActivity.class);
                intent.putExtra("flag",3);
                startActivity(intent);
            }
        });
    }
}
