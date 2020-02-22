package com.component.skinlibrary2.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;

/**
 * Created by A35 on 2020/2/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class UiUtils {
    private static UiUtils mUiUtils;

    private UiUtils() {
    }

    public static UiUtils getInstances() {
        if (mUiUtils == null) {
            synchronized (UiUtils.class) {
                if (mUiUtils == null) {
                    mUiUtils = new UiUtils();
                }
            }
        }
        return mUiUtils;
    }

    public Typeface getNightMode(Context context) {
        int uiMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        Typeface typeface = null;
        switch (uiMode) {
            case Configuration.UI_MODE_NIGHT_YES:
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), Constanst.TYPE_FACE);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                typeface = Typeface.DEFAULT;
                break;
        }
        return typeface;
    }
}
