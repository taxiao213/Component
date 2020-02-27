package com.yin.component.library.base;

import android.app.Application;

/**
 * Created by A35 on 2019/12/5
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class MyApplication implements IComponentInterface {

    private static Application mApplication;

    @Override
    public void setApplication(Application application) {

    }

    public static Application getApplication() {
        return mApplication;
    }
}
