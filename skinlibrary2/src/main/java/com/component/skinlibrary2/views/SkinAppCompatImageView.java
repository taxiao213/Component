package com.component.skinlibrary2.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.component.skinlibrary2.R;
import com.component.skinlibrary2.core.SkinMatch;
import com.component.skinlibrary2.core.SkinResources;
import com.component.skinlibrary2.model.AttrsModel;


/**
 * Created by A35 on 2020/2/20
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class SkinAppCompatImageView extends AppCompatImageView implements SkinMatch {
    AttrsModel model;

    public SkinAppCompatImageView(Context context) {
        this(context, null);
    }

    public SkinAppCompatImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinAppCompatImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        model = new AttrsModel();
        // 匹配属性值 background textColor
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinAppCompatImageView, defStyleAttr, 0);
        // 存储到临时JavaBean对象
        model.saveViewResource(typedArray, R.styleable.SkinAppCompatImageView);
        typedArray.recycle();
    }

    @Override
    public void skinnableView(Activity activity) {
        int background = model.getViewResource(R.styleable.SkinAppCompatImageView[R.styleable.SkinAppCompatImageView_android_src]);
        // 根据styleable获取控件某属性的resourceId
        if (background > 0) {
            Object backgroud = SkinResources.getInstances().getSrc(activity, background);
            if (backgroud instanceof Integer) {
                setImageDrawable(new ColorDrawable((Integer) backgroud));
            } else {
                // mView.setBackground((Drawable) background);
                // 用兼容包的
                setImageDrawable((Drawable) backgroud);
            }
        }
    }
}
