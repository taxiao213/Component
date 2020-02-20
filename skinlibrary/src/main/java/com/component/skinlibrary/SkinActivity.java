package com.component.skinlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.component.skinlibrary.base.SkinEngine;

/**
 * 1.可以加载内部资源，切换为夜间和白天模式
 * 2.可以加载外部皮肤
 */
public class SkinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_main);
        final TextView test1 = findViewById(R.id.tv_1);
        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinEngine.getInstances().setNightMode(SkinActivity.this);
            }
        });

        final Button test2 = findViewById(R.id.tv_2);
        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinEngine.getInstances().setDayMode(SkinActivity.this);
            }
        });
    }
}
