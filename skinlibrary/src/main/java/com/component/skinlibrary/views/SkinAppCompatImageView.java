package com.component.skinlibrary.views;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.component.skinlibrary.base.SkinMatch;

/**
 * Created by A35 on 2020/2/20
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class SkinAppCompatImageView extends AppCompatImageView implements SkinMatch {
    public SkinAppCompatImageView(Context context) {
        this(context, null);
    }

    public SkinAppCompatImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinAppCompatImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void skinnableView() {

    }
}
