package com.yin.component.test;

import com.component.arouterlibrary.core.ParameterLoad;
import com.yin.component.MainActivity;
import com.yin.component.ParameterActivity;

/**
 * Created by A35 on 2020/3/5
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class Parameter$$MainActivity implements ParameterLoad {
    @Override
    public void loadParameter(Object object) {
        //  加载一次
        ParameterActivity mainActivity = (ParameterActivity) object;
        //  循环
//        mainActivity.name = mainActivity.getIntent().getStringExtra("name");
//        mainActivity.age = mainActivity.getIntent().getIntExtra("age", mainActivity.age);
    }
}
