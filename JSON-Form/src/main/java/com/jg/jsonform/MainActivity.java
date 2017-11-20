package com.jg.jsonform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jg.jsonform.form.FormAttachmentActivity;
import com.jg.jsonform.form.FormPureMatchingActivity;
import com.jg.jsonform.form.FromFoldActivity;

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
                intent.putExtra("flag",1);
                startActivity(intent);
            }
        });

        findViewById(R.id.show_attachment_form).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,FormAttachmentActivity.class);
                intent.putExtra("flag",2);
                startActivity(intent);
            }
        });
    }
}
