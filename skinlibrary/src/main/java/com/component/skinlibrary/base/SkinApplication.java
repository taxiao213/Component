package com.component.skinlibrary.base;

import android.app.Activity;
import android.app.Application;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

/**
 * Created by A35 on 2020/2/19
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class SkinApplication extends Application {
    private String Tag = SkinApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        SkinEngine.getInstances().registerActivityLifecycleCallbacks(this);
    }
}
