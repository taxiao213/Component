package com.component.skinlibrary2.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatViewInflater;
import android.util.AttributeSet;
import android.view.View;

import com.component.skinlibrary2.views.SkinAppCompatButton;
import com.component.skinlibrary2.views.SkinAppCompatImageView;
import com.component.skinlibrary2.views.SkinAppCompatTextView;
import com.component.skinlibrary2.views.SkinLinearLayout;

/**
 * Created by A35 on 2020/2/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class CustomAppCompatViewInflater extends AppCompatViewInflater {
    private Context mContext;
    private String mName;
    private AttributeSet mAttrs;

    public void setAttr(Context context, String name, AttributeSet attrs) {
        this.mName = name;
        this.mAttrs = attrs;
        this.mContext = context;
    }

    /**
     * @return 自动匹配控件名，并初始化控件对象
     */
    public View autoMatch() {
        View view = null;
        switch (mName) {
            case "LinearLayout":
                // view = super.createTextView(context, attrs); // 源码写法
                view = new SkinLinearLayout(mContext, mAttrs);
                this.verifyNotNull(view, mName);
                break;
            case "TextView":
                view = new SkinAppCompatTextView(mContext, mAttrs);
                this.verifyNotNull(view, mName);
                break;
            case "ImageView":
                view = new SkinAppCompatImageView(mContext, mAttrs);
                this.verifyNotNull(view, mName);
                break;
            case "Button":
                view = new SkinAppCompatButton(mContext, mAttrs);
                this.verifyNotNull(view, mName);
                break;
        }
        return view;
    }

    private void verifyNotNull(View view, String name) {
        if (view == null) {
            throw new IllegalStateException(this.getClass().getName() + " asked to inflate view for <" + name + ">, but returned null");
        }
    }
}
