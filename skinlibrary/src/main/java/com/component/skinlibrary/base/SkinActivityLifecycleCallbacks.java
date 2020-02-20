package com.component.skinlibrary.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;

import java.lang.reflect.Field;

/**
 * ActivityLifecycleCallbacks 注册Activity生命周期监听
 * Created by A35 on 2020/2/19
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class SkinActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private String FIELD_FACTORY_SET = "mFactorySet";
    private SkinFactory2 mSkinFactory2;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        // 利用反射修改LayoutInflater mFactorySet的值，设置为false 防止抛出异常("A factory has already been set on this LayoutInflater");
        try {
            // 尽量使用LayoutInflater.class，不要使用layoutInflater.getClass()
            Field declaredField = LayoutInflater.class.getDeclaredField(FIELD_FACTORY_SET);
            // LayoutInflater 源码312行
            declaredField.setAccessible(true);
            declaredField.set(layoutInflater, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mSkinFactory2 = new SkinFactory2(activity);
        // 利用反射设置 setFactory2
        LayoutInflaterCompat.setFactory2(layoutInflater, mSkinFactory2);
        // 注册观察者（监听用户操作，点击了换肤，通知观察者更新）
        SkinEngine.getInstances().addObserver(mSkinFactory2);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (mSkinFactory2 != null) {
            mSkinFactory2.update(null, null);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        // 解除注册的观察者
        if (mSkinFactory2 != null)
            SkinEngine.getInstances().deleteObserver(mSkinFactory2);
    }
}
