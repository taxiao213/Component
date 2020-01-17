package com.yin.componenta.base;

import android.app.Application;

import com.yin.componet.library.base.IComponentInterface;

/**
 * Created by A35 on 2020/1/17
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class MyApplication implements IComponentInterface {

    private static Application mApplication;

    @Override
    public void setApplication(Application application) {
        mApplication=application;
    }

    public static Application getApplication() {
        return mApplication;
    }
}

