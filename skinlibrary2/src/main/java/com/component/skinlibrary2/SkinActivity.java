package com.component.skinlibrary2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.component.skinlibrary2.core.CustomAppCompatViewInflater;
import com.component.skinlibrary2.core.SkinEngine;
import com.component.skinlibrary2.core.SkinMatch;
import com.component.skinlibrary2.utils.ActionBarUtils;
import com.component.skinlibrary2.utils.NavigationBarUtils;
import com.component.skinlibrary2.utils.StatusBarUtils;

import java.io.File;

/**
 * 动态换肤 另一种写法
 * {@link AppCompatActivity#onCreate(Bundle)}  49行 --> delegate.installViewFactory();
 * AppCompatDelegateImpl  1008行 --> installViewFactory() 去初始化Factory2 实现createView接口
 * AppCompatDelegateImpl  969行 --> createView()  new AppCompatViewInflater()
 */
public class SkinActivity extends AppCompatActivity {

    private CustomAppCompatViewInflater viewInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(layoutInflater, this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_main);
        final TextView tv_test1 = findViewById(R.id.tv_1);
        final Button bt_test2 = findViewById(R.id.tv_2);
        tv_test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 加载外部皮肤资源包
                Log.e("test1 >>> ", "-------------start-------------");
                long start = System.currentTimeMillis();
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "test.skin";
                SkinEngine.getInstances().updateSkin(SkinActivity.this, path);
                long end = System.currentTimeMillis() - start;
                Log.e("test1 >>> ", "换肤耗时（毫秒）：" + end);
                Log.e("test1 >>> ", "-------------end---------------");

                // 加载内部资源 夜间模式
//                SkinEngine.getInstances().setNightMode(SecondActivity.this);
            }
        });
        bt_test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 加载外部皮肤资源包
                Log.e("test2 >>> ", "-------------start-------------");
                long start = System.currentTimeMillis();
                SkinEngine.getInstances().updateSkin(SkinActivity.this, "");
                long end = System.currentTimeMillis() - start;
                Log.e("test2 >>> ", "换肤耗时（毫秒）：" + end);
                Log.e("test2 >>> ", "-------------end---------------");

                // 加载内部资源 白天模式
//                SkinEngine.getInstances().setDayMode(SecondActivity.this);
            }
        });
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        /**
         * AppCompatDelegateImpl  1008行 --> installViewFactory() 去初始化Factory2 实现createView接口
         * AppCompatDelegateImpl  969行 --> createView()  new AppCompatViewInflater()
         */
        if (openChangeSkin()) {
            if (viewInflater == null) {
                viewInflater = new CustomAppCompatViewInflater();
            }
            viewInflater.setAttr(context, name, attrs);
            return viewInflater.autoMatch();
        }
        return super.onCreateView(parent, name, context, attrs);
    }

    /**
     * @return 是否开启换肤，增加此开关是为了避免开发者误继承此父类，导致未知bug
     */
    protected boolean openChangeSkin() {
        return true;
    }

    /**
     * 设置白天模式
     */
    protected void setDayMode() {
        setMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    /**
     * 设置夜间模式
     */
    protected void setNightMode() {
        setMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    /**
     * 设置模式
     *
     * @param nightMode
     */
    protected void setMode(@AppCompatDelegate.NightMode int nightMode) {
        final boolean isPost21 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        getDelegate().setLocalNightMode(nightMode);
        if (isPost21) {
            // 换状态栏
            StatusBarUtils.forStatusBar(this);
            // 换标题栏
            ActionBarUtils.forActionBar(this);
            // 换底部导航栏
            NavigationBarUtils.forNavigation(this);
        }
        View decorView = getWindow().getDecorView();
        applyDayNightForView(SkinActivity.this, decorView);
    }

    /**
     * 限定要和改变哪些View
     */
    private void applyDayNightForView(Activity activity, View decorView) {
        if (decorView instanceof SkinMatch) {
            ((SkinMatch) decorView).skinnableView(activity);
        }
        if (decorView instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) decorView).getChildCount(); i++) {
                View childAt = ((ViewGroup) decorView).getChildAt(i);
                applyDayNightForView(activity, childAt);
            }
        }
    }
}
