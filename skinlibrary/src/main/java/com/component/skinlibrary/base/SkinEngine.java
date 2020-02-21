package com.component.skinlibrary.base;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.ViewGroup;

import com.component.skinlibrary.utils.ActionBarUtils;
import com.component.skinlibrary.utils.NavigationBarUtils;
import com.component.skinlibrary.utils.StatusBarUtils;

import java.util.Observable;

/**
 * 被观察者
 * 1.可以加载内部资源，切换为夜间和白天模式
 * 2.可以加载外部皮肤
 * Created by A35 on 2020/2/19
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class SkinEngine extends Observable {

    private static SkinEngine mSkinEngine;
    private Application mApplication;
    private boolean mIsLoadInternal = true;// 默认 true加载内部资源  false加载外部皮肤

    private SkinEngine() {
    }

    public static SkinEngine getInstances() {
        if (mSkinEngine == null) {
            synchronized (SkinEngine.class) {
                if (mSkinEngine == null) {
                    mSkinEngine = new SkinEngine();
                }
            }
        }
        return mSkinEngine;
    }

    public void registerActivityLifecycleCallbacks(Application application) {
        if (application != null) {
            this.mApplication = application;
            // 注册Activity生命周期监听，AOP切面
            application.registerActivityLifecycleCallbacks(new SkinActivityLifecycleCallbacks());
            SkinResources.getInstances().init(application);
        }
    }

    /**
     * 用户点击 换肤按钮
     * 加载外部皮肤
     *
     * @param skinPath
     */
    public void updateSkin(AppCompatActivity activity, String skinPath) {
        // 我是被观察者，通知所有的观察者 点击换肤第一步：通知所有的观察者，需要换肤了
        setLoadInternal(false);
        SkinResources.getInstances().setSkinResources(mApplication, skinPath);
        SkinResources.getInstances().updatePhoneStatusBarAction(activity);
        setChanged();
        notifyObservers();
    }

    /**
     * 用户点击 换肤按钮
     * 切换为夜间和白天模式
     */
    public void updateNightSkin() {
        // 我是被观察者，通知所有的观察者 点击换肤第一步：通知所有的观察者，需要换肤了
        setChanged();
        notifyObservers();
    }

    // 默认
    public void setDayMode(AppCompatActivity activity) {
        setDayMode(activity, AppCompatDelegate.MODE_NIGHT_NO);
        setStatusBarAction(activity);
    }

    // 换肤
    public void setNightMode(AppCompatActivity activity) {
        setDayMode(activity, AppCompatDelegate.MODE_NIGHT_YES);
        setStatusBarAction(activity);
    }

    public void setStatusBarAction(AppCompatActivity activity) {
        ActionBarUtils.forActionBar(activity);
        NavigationBarUtils.forNavigation(activity);
        StatusBarUtils.forStatusBar(activity);
    }

    private void setDayMode(AppCompatActivity activity, int uiMode) {
        setLoadInternal(true);
        activity.getDelegate().setLocalNightMode(uiMode);
        View decorView = activity.getWindow().getDecorView();
        applyDayNightForView(decorView);
    }

    /**
     * 限定要和改变哪些View
     */
    private void applyDayNightForView(View decorView) {
        if (decorView instanceof SkinMatch) {
            SkinEngine.getInstances().updateNightSkin();
        }
        if (decorView instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) decorView).getChildCount(); i++) {
                View childAt = ((ViewGroup) decorView).getChildAt(i);
                applyDayNightForView(childAt);
            }
        }
    }

    public boolean isLoadInternal() {
        return mIsLoadInternal;
    }

    public void setLoadInternal(boolean loadInternal) {
        this.mIsLoadInternal = loadInternal;
    }
}
