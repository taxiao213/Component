package com.yin.component;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.component.skinlibrary.SkinActivity;
import com.component.skinlibrary.base.SkinEngine;

import java.io.File;

/**
 * 动态换肤
 * Created by A35 on 2020/2/19
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class SecondActivity extends SkinActivity {

    private TextView tv_test1;
    private Button bt_test2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tv_test1 = findViewById(R.id.tv_test1);
        bt_test2 = findViewById(R.id.bt_test2);
        tv_test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 加载外部皮肤资源包
                Log.e("test1 >>> ", "-------------start-------------");
                long start = System.currentTimeMillis();
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "test.skin";
                SkinEngine.getInstances().updateSkin(SecondActivity.this, path);
                long end = System.currentTimeMillis() - start;
                Log.e("test1 >>> ", "换肤耗时（毫秒）：" + end);
                Log.e("test1 >>> ", "-------------end---------------");

                // 加载内部资源 夜间模式
//                SkinEngine.getInstances().setNightMode(SecondActivity.this);
            }
        });
        bt_test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 加载外部皮肤资源包
                Log.e("test2 >>> ", "-------------start-------------");
                long start = System.currentTimeMillis();
                SkinEngine.getInstances().updateSkin(SecondActivity.this, "");
                long end = System.currentTimeMillis() - start;
                Log.e("test2 >>> ", "换肤耗时（毫秒）：" + end);
                Log.e("test2 >>> ", "-------------end---------------");

                // 加载内部资源 白天模式
//                SkinEngine.getInstances().setDayMode(SecondActivity.this);
            }
        });
    }
}
