package com.yin.componet.library.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;


import com.yin.componet.library.base.MyApplication;



/**
 * UI工具类,对一些界面数据的获取与操作
 * Created by zhangjutao on 16/7/4.
 */
public class UIUtil {

    private static UIUtil mUiUti;

    public UIUtil() {

    }

    public static UIUtil getInstance() {
        if (mUiUti == null) {
            synchronized (UIUtil.class) {
                if (mUiUti == null) {
                    mUiUti = new UIUtil();
                }
            }
        }
        return mUiUti;
    }

    public Application getContext() {
        return MyApplication.getApplication();
    }

    private float getDisplayScale() {
        float scale = 0;
        Application context = getContext();
        if (context != null) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            if (displayMetrics != null) {
                scale = displayMetrics.density;
            }
        }
        return scale;
    }

    /**
     * dip转换px
     */
    public int dip2px(int dip) {
        return (int) (dip * getDisplayScale() + 0.5f);
    }

    /**
     * px转换dip
     */
    public int px2dip(int px) {
        return (int) (px / getDisplayScale() + 0.5f);
    }


    /**
     * 获取资源
     */
    public Resources getResources() {
        Resources resources = null;
        Application context = getContext();
        if (context != null) {
            resources = context.getResources();
        }
        return resources;
    }

    /**
     * 获取drawable
     */
    public Drawable getDrawable(int resId) {
        Drawable drawable = null;
        Application context = getContext();
        if (context != null) {
            drawable = context.getResources().getDrawable(resId);
        }
        return drawable;
    }

    /**
     * 获取颜色
     */
    public int getColor(int resId) {
        int color = 0;
        Application context = getContext();
        if (context != null) {
            color = context.getResources().getColor(resId);
        }
        return color;
    }

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度
     */
    public int getScreenHeight() {
        int mScreenHeight = 0;
        Application context = getContext();
        if (context != null) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (windowManager != null) {
                mScreenHeight = windowManager.getDefaultDisplay().getHeight();
            }
        }
        return mScreenHeight;
    }

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度
     */
    public int getScreenWidth() {
        int mScreenWidth = 0;
        Application context = getContext();
        if (context != null) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (windowManager != null) {
                mScreenWidth = windowManager.getDefaultDisplay().getWidth();
            }
        }
        return mScreenWidth;
    }

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度
     */
    public int getRealScreenHeight() {
        int mScreenHeight = 0;
        Point display = getDisplay();
        if (display != null) {
            mScreenHeight = display.y;
        }
        return mScreenHeight;
    }

    private Point getDisplay() {
        Point point = null;
        Application context = getContext();
        if (context != null) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (windowManager != null) {
                Display defaultDisplay = windowManager.getDefaultDisplay();
                point = new Point();
                defaultDisplay.getRealSize(point);
            }
        }
        return point;
    }

}


