package com.component.skinlibrary2.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.component.skinlibrary2.R;
import com.component.skinlibrary2.core.SkinEngine;
import com.component.skinlibrary2.core.SkinMatch;
import com.component.skinlibrary2.core.SkinResources;
import com.component.skinlibrary2.model.AttrsModel;
import com.component.skinlibrary2.utils.Constanst;
import com.component.skinlibrary2.utils.UiUtils;


/**
 * Created by A35 on 2020/2/20
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class SkinAppCompatTextView extends AppCompatTextView implements SkinMatch {
    private Context mContext;
    AttrsModel model;

    public SkinAppCompatTextView(Context context) {
        this(context, null);
    }

    public SkinAppCompatTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinAppCompatTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        model = new AttrsModel();
        // 匹配属性值 background textColor
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinAppCompatTextView, defStyleAttr, 0);
        // 存储到临时JavaBean对象
        model.saveViewResource(typedArray, R.styleable.SkinAppCompatTextView);
        typedArray.recycle();
    }

    @Override
    public void skinnableView(Activity activity) {
        // 根据自定义属性，获取styleable中的background属性
        int background = model.getViewResource(R.styleable.SkinAppCompatTextView[R.styleable.SkinAppCompatTextView_android_background]);
        // 根据styleable获取控件某属性的resourceId
        if (background > 0) {
            Object backgroud = SkinResources.getInstances().getBackgroud(activity, background);
            if (backgroud instanceof Integer) {
                setBackgroundColor((Integer) backgroud);
            } else {
                // 兼容包转换 控件自带api，这里不用setBackgroundColor()因为在9.0测试不通过
                // setBackgroundDrawable本来过时了，但是兼容包重写了方法
                setBackgroundDrawable(ContextCompat.getDrawable(getContext(), background));
            }
        }
        int textColor = model.getViewResource(R.styleable.SkinAppCompatTextView[R.styleable.SkinAppCompatTextView_android_textColor]);
        if (textColor > 0) {
            setTextColor(SkinResources.getInstances().getColorStateList(activity, textColor));
        }
        setTypeface(SkinResources.getInstances().getTypeface(activity));
    }
}
