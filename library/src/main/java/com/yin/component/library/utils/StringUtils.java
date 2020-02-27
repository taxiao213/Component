package com.yin.component.library.utils;

import android.text.TextUtils;

/**
 * 返回非空字段
 * Created by Han on 2018/9/5
 *
 */

public class StringUtils {

    public static String null2Length0(String string) {
        if (!TextUtils.isEmpty(string)) {
            return string;
        } else {
            return "";
        }
    }
}
