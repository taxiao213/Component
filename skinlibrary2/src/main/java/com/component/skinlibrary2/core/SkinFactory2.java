package com.component.skinlibrary2.core;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;


import java.util.Observable;
import java.util.Observer;

/**
 * Factory2 实现观察者
 * Created by A35 on 2020/2/19
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class SkinFactory2 implements LayoutInflater.Factory2, Observer {
    private Activity mActivity;

    public SkinFactory2(Activity activity) {
        mActivity = activity;
    }

    /**
     * AppCompatDelegateImpl createView 969行 生成一个AppCompatViewInflater
     * 可以追加需要改变的View
     *
     * @param parent 父控件View
     * @param name   控件的名字，例如：TextView
     * @param attrs  控件的属性，例如：TextView(定义了很多的属性 宽 高 text textColor ...)
     */
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        CustomAppCompatViewInflater viewInflater = new CustomAppCompatViewInflater();
        viewInflater.setAttr(context, name, attrs);
        return viewInflater.autoMatch();
    }

    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return mActivity.onCreateView(name, context, attrs);
    }


    /**
     * 点击换肤第二步：收到 被观察者 发出的---> 改变通知
     * 当被被观察者发生改变时回调
     */
    @Override
    public void update(Observable o, Object arg) {
        /**
         * 点击换肤第三步：告诉WidgetViewList去换肤，因为WidgetViewList身上有所有需要换肤的控件
         * 开始换皮肤
         */
        SkinEngine.getInstances().applyDayNightForView((AppCompatActivity) mActivity, mActivity.getWindow().getDecorView());
    }
}
