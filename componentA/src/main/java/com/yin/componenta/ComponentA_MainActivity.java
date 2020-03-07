package com.yin.componenta;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.component.annotation.ARouter2;
import com.component.arouterlibrary.ARouterManager;

/**
 * Created by A35 on 2020/2/27
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
@ARouter2(path = "/componentA/ComponentA_MainActivity", groupName = "componentA")
public class ComponentA_MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.componenta_main_activity);
    }

    public void jumpMain(View view) {
        finish();
    }

    public void jumpOther(View view) {
        //  第一种写法 容易出错
        /*try {
            Class<?> targetClass = Class.forName("com.yin.componentb.ComponentB_MainActivity");
            startActivity(new Intent(this, targetClass));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        //  第二种写法
        /*Class<?> targetClass = RecordPathManager.getTargetClass(Content.COMPONENT_B, Content.COMPONENT_PATH_B_1);
        startActivity(new Intent(this, targetClass));*/


        ARouterManager
                .getInstance()
                .build("/app/MainActivity")
                .withResultString("name","value")
                .navigation(this, 100);
    }
}
