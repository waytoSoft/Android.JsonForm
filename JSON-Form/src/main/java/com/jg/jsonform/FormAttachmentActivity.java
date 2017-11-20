package com.jg.jsonform;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jg.jsonform.form.FormAttachmentMatchingActivity;
import com.jg.jsonform.form.FormFoldMatchingActivity;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/20 10:20
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormAttachmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pure);

        findViewById(R.id.Button_Form_Pure_fill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FormAttachmentActivity.this,FormAttachmentMatchingActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
            }
        });

        findViewById(R.id.Button_Form_Pure_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FormAttachmentActivity.this,FormAttachmentMatchingActivity.class);
                intent.putExtra("flag",2);
                startActivity(intent);
            }
        });

        findViewById(R.id.Button_Form_Pure_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FormAttachmentActivity.this,FormAttachmentMatchingActivity.class);
                intent.putExtra("flag",3);
                startActivity(intent);
            }
        });
    }
}
