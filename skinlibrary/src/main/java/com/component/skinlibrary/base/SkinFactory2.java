package com.component.skinlibrary.base;

import android.app.Activity;
import android.content.Context;
import android.icu.util.EthiopicCalendar;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.component.skinlibrary.views.SkinAppCompatButton;
import com.component.skinlibrary.views.SkinAppCompatImageView;
import com.component.skinlibrary.views.SkinAppCompatTextView;

import org.w3c.dom.ProcessingInstruction;

import java.lang.reflect.Constructor;
import java.util.Map;
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
    private static final Class<?>[] sConstructorSignature = new Class[]{Context.class, AttributeSet.class};
    private static final String[] sClassPrefixList = new String[]{"android.widget.", "android.view.", "android.webkit."};
    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap();
    private SkinWidgetViewList mSkinWidgetViewList;

    public SkinFactory2(Activity activity) {
        mActivity = activity;
        mSkinWidgetViewList = new SkinWidgetViewList(activity);
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
        // 匹配view
        View resultView = null;
        switch (name) {
            case "ImageView":
                resultView = new SkinAppCompatImageView(context, attrs);
                break;
            case "TextView":
                resultView = new SkinAppCompatTextView(context, attrs);
                break;
            case "Button":
                resultView = new SkinAppCompatButton(context, attrs);
                break;
        }
        // 如果为null，可认为是自定义View，所以需要传入 name + "" ---> 自定义控件包名和控件名 + ""
        if (null == resultView) {
            resultView = createViewFromTag(parent, name, context, attrs);
        }

        /*
         * SkinWidgetViewList: 可以理解成布局文件 布局文件里面有多个控件TextView，某个控件就是WidgetView
         * saveWidgetView: 这里保存的resultView attrs 就是为了 构建个WidgetVeiw
         */
        mSkinWidgetViewList.saveWidgetView(attrs, resultView);
        return resultView;
    }

    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return mActivity.onCreateView(name, context, attrs);
    }

    private View createViewFromTag(View parent, String name, Context context, AttributeSet attrs) {
        View view = null;
        for (String s : sClassPrefixList) {
            if (name.contains(".")) {
                view = createViewByPrefix(context, name, null, attrs);
            } else {
                view = createViewByPrefix(context, name, s, attrs);
            }
        }
        return view;
    }

    /**
     * 通过反射创建View
     * AppCompatViewInflater   328行    createViewByPrefix
     * 1.当prefix不为null 系统的控件 传入 name + prefix  -->  控件名 + 控件包名， 需要创建系统的控件。
     * 2.当prefix为null 自定义控件名 传入 name + "" -->  控件名 + ""  这个控件名就是完整的 自定义 包名+自定义控件名
     */
    private View createViewByPrefix(Context context, String name, String prefix, AttributeSet attrs) {
        Constructor constructor = sConstructorMap.get(name);
        try {
            if (constructor == null) {
                Class<? extends View> clazz = context.getClassLoader().loadClass(prefix != null ? prefix + name : name).asSubclass(View.class);
                constructor = clazz.getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            }
            constructor.setAccessible(true);
            return (View) constructor.newInstance(context, attrs);
        } catch (Exception var6) {
            return null;
        }
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
        mSkinWidgetViewList.skinChange();
    }
}
