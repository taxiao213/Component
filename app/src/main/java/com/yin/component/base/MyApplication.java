package com.yin.component.base;

import android.app.Application;
import android.text.TextUtils;

import com.component.skinlibrary.base.SkinApplication;
import com.yin.component.library.base.IComponentInterface;
import com.yin.component.library.bean.Content;
import com.yin.component.library.bean.RecordPathManager;
import com.yin.componenta.ComponentA_MainActivity;
import com.yin.componentb.ComponentB_MainActivity;

/**
 * Created by A35 on 2020/1/17
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class MyApplication extends SkinApplication {
    private Application application;
    private String[] componentApplication = new String[]{"com.yin.componenta.base.MyApplication", "com.yin.componentb.base.MyApplication", "com.yin.componet.library.base.MyApplication", "com.yin.componet.library.base.MyApplication"};

    @Override
    public void onCreate() {
        super.onCreate();
        this.application = this;
        initComponentApplication();
        loadClass();
    }

    /**
     * 加载需要收集跳转的类
     */
    private void loadClass() {
        RecordPathManager.joinGroup(Content.COMPONENT_A, Content.COMPONENT_PATH_A_1, ComponentA_MainActivity.class);
        RecordPathManager.joinGroup(Content.COMPONENT_B, Content.COMPONENT_PATH_B_1, ComponentB_MainActivity.class);
    }

    /**
     * 初始化组件Application
     */
    private void initComponentApplication() {
        if (componentApplication != null && componentApplication.length > 0) {
            for (int i = 0; i < componentApplication.length; i++) {
                String clazz = componentApplication[i];
                if (!TextUtils.isEmpty(clazz)) {
                    try {
                        Class<?> className = Class.forName(clazz);
                        Object object = className.newInstance();
                        if (object instanceof IComponentInterface) {
                            ((IComponentInterface) object).setApplication(application);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
