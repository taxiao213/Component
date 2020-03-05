package com.yin.component;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.component.annotation.ARouter;
import com.component.annotation.ARouter2;
import com.component.annotation.Parameter;
import com.component.arouterlibrary.core.ParameterLoad;
import com.yin.component.test.Parameter$$MainActivity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created by A35 on 2020/3/5
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
@ARouter2(path = "/app/ParameterActivity", groupName = "app")
public class ParameterActivity extends AppCompatActivity {

    @Parameter(name = "name")
    String name;
    @Parameter
    int age;
    @Parameter
    boolean sex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter);

        // 加载参数类 给intent参数赋值
        ParameterLoad parameterLoad = new Parameter$$ParameterActivity();
        parameterLoad.loadParameter(this);

        // 通过注解获取参数 Parameter$$ParameterActivity
        Log.e("ParameterActivity", " intent>> name == " + name + " age == " + age);
    }
}
