package com.yin.componet.library.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by A35 on 2019/12/5
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class MyApplication extends Application {

    private static Context context;

    public static Context getApplication() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

}
