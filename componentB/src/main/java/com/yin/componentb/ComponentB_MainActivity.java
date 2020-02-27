package com.yin.componentb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yin.component.library.bean.Content;
import com.yin.component.library.bean.RecordPathManager;

/**
 * Created by A35 on 2020/2/27
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class ComponentB_MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.componentb_main_activity);
    }

    public void jumpMain(View view) {
        finish();
    }

    public void jumpOther(View view) {
        //  第一种写法 容易出错
        /*try {
            Class<?> targetClass = Class.forName("com.yin.componenta.ComponentA_MainActivity");
            startActivity(new Intent(this, targetClass));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        //  第二种写法
        Class<?> targetClass = RecordPathManager.getTargetClass(Content.COMPONENT_A, Content.COMPONENT_PATH_A_1);
        startActivity(new Intent(this, targetClass));
    }
}
