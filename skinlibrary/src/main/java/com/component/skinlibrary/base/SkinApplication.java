package com.component.skinlibrary.base;

import android.app.Application;

/**
 * Created by A35 on 2020/2/19
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class SkinApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinEngine.getInstances().registerActivityLifecycleCallbacks(this);
    }
}
