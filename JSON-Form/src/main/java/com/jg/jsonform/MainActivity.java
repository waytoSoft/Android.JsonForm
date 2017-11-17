package com.jg.jsonform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jg.jsonform.asset.FormActivity;
import com.jg.jsonform.form.FormAttachmentActivity;
import com.jg.jsonform.form.FormPureActivity;
import com.jg.jsonform.form.FromFoldActivity;
import com.jg.jsonform.form.fragment.FormAttachmentFragment;
import com.jg.jsonform.form.fragment.FormFoldFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.add_asset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,FormPureActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.show_asset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,FromFoldActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.edit_asset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,FormAttachmentActivity.class);
                startActivity(intent);
            }
        });
    }
}
