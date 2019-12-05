package com.yin.componet.library.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


import com.yin.componet.library.base.MyApplication;

import java.lang.reflect.Method;
import java.util.ArrayList;


/**
 * UI工具类,对一些界面数据的获取与操作
 * Created by zhangjutao on 16/7/4.
 */
public class UIUtil {

    //屏幕宽度
    public static int mScreenWidth;
    //屏幕高度
    public static int mScreenHeight;


    public static Context getContext() {
        return MyApplication.getApplication();
    }

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }


    /**
     * px转换dip
     */
    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }


    /**
     * 获取资源
     */
    public static Resources getResources() {
        return getContext().getResources();
    }


    /**
     * 获取dimen
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId) {
        return ActivityCompat.getDrawable(getContext(), resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度
     */
    public static int getScreenHeight() {
        if (mScreenHeight == 0) {
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            mScreenHeight = wm.getDefaultDisplay().getHeight();
        }
        return mScreenHeight;
    }

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度
     */
    public static int getScreenWidth() {
        if (mScreenWidth == 0) {
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            mScreenWidth = wm.getDefaultDisplay().getWidth();
        }
        return mScreenWidth;
    }

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度
     */
    public static int getRealScreenHeight() {
        int mScreenHeight = 0;
        Point display = getDisplay();
        if (display != null) {
            mScreenHeight = display.y;
        }
        return mScreenHeight;
    }

    private static Point getDisplay() {
        Point point = null;
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            Display defaultDisplay = wm.getDefaultDisplay();
            point = new Point();
            defaultDisplay.getRealSize(point);
        }
        return point;
    }

    /**
     * 设置指定textView的字体大小，单位sp
     *
     * @param textView textView
     * @param textSize textSize
     */
    public static void setTextSize(TextView textView, int textSize) {
        if (textView != null)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }

}


