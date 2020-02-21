package com.component.skinlibrary.base;

import android.app.Activity;
import android.app.Application;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.component.skinlibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 存储控件
 * Created by A35 on 2020/2/19
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class SkinWidgetViewList {
    private final String TAG = SkinWidgetViewList.class.getSimpleName();
    private Activity mActivity;
    // 认定以下定义的标记，就是需要换肤的
    private final List<String> mAttributeList = new ArrayList<>();

    {
        mAttributeList.add(BACKGROUND);
        mAttributeList.add(SRC);
        mAttributeList.add(TEXTCOLOR);
        mAttributeList.add("drawableLeft");
        mAttributeList.add("drawableTop");
        mAttributeList.add("drawableRight");
        mAttributeList.add("drawableBottom");
    }

    private final static String BACKGROUND = "background";
    private final static String SRC = "src";
    private final static String TEXTCOLOR = "textColor";

    private final List<WidgetView> mWidgetList = new ArrayList<>();

    public SkinWidgetViewList(Activity activity) {
        mActivity = activity;
    }

    public void saveWidgetView(AttributeSet attrs, View resultView) {
        ArrayList<AttributeNameAndValue> attributeNameAndValues = new ArrayList<>();
        if (attrs != null) {
            int count = attrs.getAttributeCount();
            if (count > 0) {
                /**
                 *      <TextView
                 *         android:layout_width="wrap_content"
                 *         android:layout_height="wrap_content"
                 *         android:text="测试"
                 *         android:textSize="30sp"
                 *         android:textColor="@color/skin_my_textColor"
                 *         />
                 *
                 *       遍历，就相当于要遍历上面的这种属性=属性值
                 */
                for (int i = 0; i < count; i++) {
                    String attrName = attrs.getAttributeName(i);
                    String attrValue = attrs.getAttributeValue(i);
                    Log.d(TAG, attrName + " == " + attrValue);
                    // 这种情况 不换肤
                    if (attrValue.startsWith("#") || attrValue.startsWith("?")) {
                        continue;
                    }
                    if (mAttributeList.contains(attrName)) {
                        Log.d(TAG, "=============attrValue:" + attrValue);
                        // 现在拿到的attrValue可能是 @46464345，所以需要把@给去掉
                        attrValue = attrValue.substring(1);
                        int attrValueInt = Integer.parseInt(attrValue);
                        // 拿到🆔，就可以通过🆔去加载资源
                        Log.d(TAG, "resId==============attrValueInt:" + attrValueInt);
                        if (attrValueInt != 0) {
                            // 需要被替换的属性+属性值 【注意⚠️】
                            AttributeNameAndValue nameAndValue = new AttributeNameAndValue(attrName, attrValueInt);
                            attributeNameAndValues.add(nameAndValue);
                        }
                    }
                }
            }
        }
        WidgetView widgetView = new WidgetView(resultView, attributeNameAndValues);
        mWidgetList.add(widgetView);
    }

    /**
     * 点击换肤第四步：遍历所有保存的 控件(WidgetView)
     * 然后告诉每一个控件(WidgetView) 去换肤
     */
    public void skinChange() {
        for (WidgetView widgetView : mWidgetList) {
            widgetView.skinChange();
        }
    }

    class WidgetView {
        View mView;
        List<AttributeNameAndValue> mAttributeNameAndValues;

        WidgetView(View resultView, ArrayList<AttributeNameAndValue> attributeNameAndValues) {
            mView = resultView;
            mAttributeNameAndValues = attributeNameAndValues;
        }

        /**
         * 点击换肤第五步
         * 遍历当前这个控件(WidgetView==TextView) 里面的属性(AttributeNameAndValue)，属性例如如下：
         * android:layout_width="wrap_content"
         * android:layout_height="wrap_content"
         * android:text="测试"
         * android:textSize="30sp"
         * android:textColor="@color/skin_my_textColor"
         */
        void skinChange() {
            // 改变字体
            changeTypeface(mView);
            for (AttributeNameAndValue att : mAttributeNameAndValues) {
                int attrValueInt = att.attrValueInt;
                switch (att.attrName) {
                    case BACKGROUND:
                        Object backgroud = SkinResources.getInstances().getBackgroud(mActivity, attrValueInt);
                        if (backgroud instanceof Integer) {
                            mView.setBackgroundColor((Integer) backgroud);
                        } else {
                            // 用兼容包的
                            ViewCompat.setBackground(mView, (Drawable) backgroud);
                        }
                        break;
                    case SRC:
                        Object src = SkinResources.getInstances().getSrc(mActivity, attrValueInt);
                        if (src instanceof Integer) {
                            ((ImageView) mView).setImageDrawable(new ColorDrawable((Integer) src));
                        } else {
                            // mView.setBackground((Drawable) background);
                            // 用兼容包的
                            ((ImageView) mView).setImageDrawable((Drawable) src);
                        }
                        break;
                    case TEXTCOLOR:
                        ((TextView) mView).setTextColor(SkinResources.getInstances().getColorStateList(mActivity, attrValueInt));
                        break;
                }
            }
        }
    }

    /**
     * 改变字体 夜间 白天模式 改变字体
     */
    private void changeTypeface(View view) {
        if (view instanceof TextView) {
            Typeface typeface = SkinResources.getInstances().getTypeface(mActivity);
            if (typeface != null) {
                ((TextView) view).setTypeface(typeface);
            }
        }
    }

    /**
     * 属性名 = 资源ID(Int型)attrValueInt
     * 类似于：android:textColor=
     * 所以定义JavaBean
     */
    class AttributeNameAndValue {
        String attrName;
        int attrValueInt;

        public AttributeNameAndValue(String attrName, int attrValueInt) {
            this.attrName = attrName;
            this.attrValueInt = attrValueInt;
        }
    }
}
