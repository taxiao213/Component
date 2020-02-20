package com.component.skinlibrary.base;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

/**
 * 访问 本地存储的皮肤包资源(xxx.skin) 或 当前app运行的apk包资源
 * Created by A35 on 2020/2/19
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class SkinResources {
    private static final String TAG = SkinResources.class.getSimpleName();
    private static final String TYPE_PATH = "fonts/yizhiqingshu.ttf";
    private static SkinResources mInstances;
    private boolean mDefaultSkin = true; // 默认是 默认的皮肤(最原始的)
    private Application mApplication;
    private Resources mSkinResources;// 此Resource 可以加载本地存储 xxx.skin 皮肤包资源
    private Resources mAppResources;// 此Resource 可以加载当前App运行的apk资源
    private String mSkinPkgName; // 皮肤包 包名
    private final static String ADD_ASSET_PATH = "addAssetPath"; // 添加资源的方法名称 AssetManager 356行

    private SkinResources() {
    }

    static SkinResources getInstances() {
        if (mInstances == null) {
            synchronized (SkinEngine.class) {
                if (mInstances == null) {
                    mInstances = new SkinResources();
                }
            }
        }
        return mInstances;
    }

    /**
     * 给Application进行初始化的，目的是绑定Application 而不是绑定Activity
     */
    void init(Application application) {
        mApplication = application;
        mAppResources = application.getResources();
    }

    /**
     * 设置皮肤资源 目的就是创建出mSkinResources ---> 访问 本地存储 xxx.skin 皮肤包
     */
    void setSkinResources(Application application, String path) {
        File file = new File(path);
        if (!file.exists()) {
            Log.e(TAG, "Error skinPath not exist...");
        }
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            // 由于AssetManager中的addAssetPath和setApkAssets方法都被@hide，目前只能通过反射去执行方法
            Method addAssetPath = assetManager.getClass().getDeclaredMethod(ADD_ASSET_PATH, String.class);
            // 设置私有方法可访问
            addAssetPath.setAccessible(true);
            // addAssetPath
            addAssetPath.invoke(assetManager, path);
            // 创建外部的资源包
            mSkinResources = new Resources(assetManager, mAppResources.getDisplayMetrics(), mAppResources.getConfiguration());
            // 根据apk路径获取应用包名
            mSkinPkgName = application.getPackageManager().getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES).packageName;
            mDefaultSkin = TextUtils.isEmpty(mSkinPkgName);
        } catch (Exception e) {
            // 发生异常就证明 通过skinPath 获取 packageName 失败了
            mDefaultSkin = true;
        }
    }

    /**
     * 获取资源id
     */
    private int getIdentifier(int id) {
        // 如果没有皮肤，直接返回
        if (mDefaultSkin) {
            return id;
        }
        String resourceEntryName = mAppResources.getResourceEntryName(id);// 名称 ic_launcher
        String resourceTypeName = mAppResources.getResourceTypeName(id);// 类型 drawable
        return mSkinResources.getIdentifier(resourceEntryName, resourceTypeName, mSkinPkgName);
    }

    /**
     * 获取background 是特殊情况，因为：
     * 可能是color
     * 可能是drawable
     * 可能是mipmap
     * 所有得到当前属性的类型Resources.getResourceTypeName(resId); 进行判断
     */
    Object getBackgroud(Activity activity, int id) {
        Object object = null;
        String resourceTypeName = null;
        if (SkinEngine.getInstances().isLoadInternal()) {
            // 加载内部资源，夜间 白天模式切换
            resourceTypeName = activity.getResources().getResourceTypeName(id);
        } else {
            if (mAppResources != null) {
                resourceTypeName = mAppResources.getResourceTypeName(id);
            }
        }
        if (TextUtils.equals(resourceTypeName, "color")) {
            object = getColor(activity, id);
        } else if (TextUtils.equals(resourceTypeName, "drawable") || TextUtils.equals(resourceTypeName, "mipmap")) {
            object = getDrawable(activity, id);
        } else {
            object = getColorStateList(activity, id);
        }
        return object;
    }

    /**
     * 获取 Drawable
     */
    private Drawable getDrawable(Activity activity, int id) {
        if (SkinEngine.getInstances().isLoadInternal()) {
            // 加载内部资源，夜间 白天模式切换
            return activity.getResources().getDrawable(id);
        } else {
            if (mDefaultSkin) {
                // 如果没有皮肤，那就加载当前App运行的Apk资源
                return mAppResources.getDrawable(id);
            } else {
                int identifier = getIdentifier(id);
                if (identifier == 0) {
                    // 如果为0，那就加载当前App运行的Apk资源
                    return mAppResources.getDrawable(id);
                } else {
                    // skinId不等于0 ，就加载 本地存储的 xxx.skin皮肤包资源
                    return mSkinResources.getDrawable(identifier);
                }
            }
        }
    }

    /**
     * 获取 Color
     */
    public int getColor(Activity activity, int id) {
        if (SkinEngine.getInstances().isLoadInternal()) {
            // 加载内部资源，夜间 白天模式切换
            return activity.getResources().getColor(id);
        } else {
            if (mDefaultSkin) {
                // 如果没有皮肤，那就加载当前App运行的Apk资源
                return mAppResources.getColor(id);
            } else {
                int identifier = getIdentifier(id);
                if (identifier == 0) {
                    // 如果为0，那就加载当前App运行的Apk资源
                    return mAppResources.getColor(id);
                } else {
                    // skinId不等于0 ，就加载 本地存储的 xxx.skin皮肤包资源
                    return mSkinResources.getColor(identifier);
                }
            }
        }
    }

    /**
     * 获取 Color
     */
    ColorStateList getColorStateList(Activity activity, int resId) {
        if (SkinEngine.getInstances().isLoadInternal()) {
            // 加载内部资源，夜间 白天模式切换
            return activity.getResources().getColorStateList(resId);
        } else {
            if (mDefaultSkin) {
                // 如果没有皮肤，那就加载当前App运行的Apk资源
                return mAppResources.getColorStateList(resId);
            } else {
                int identifier = getIdentifier(resId);
                if (identifier == 0) { // 如果为0，那就加载当前App运行的Apk资源
                    return mAppResources.getColorStateList(resId);
                } else {
                    // skinId不等于0 ，就加载 本地存储的 xxx.skin皮肤包资源
                    return mSkinResources.getColorStateList(identifier);
                }
            }
        }
    }

    /**
     * 获取 字体
     */
    Typeface getTypeface(Activity activity) {
        if (SkinEngine.getInstances().isLoadInternal()) {
            Typeface typeface = null;
            int uiMode = activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            switch (uiMode) {
                case Configuration.UI_MODE_NIGHT_YES:
                    typeface = Typeface.createFromAsset(mAppResources.getAssets(), TYPE_PATH);
                    break;
                case Configuration.UI_MODE_NIGHT_NO:
                    typeface = Typeface.DEFAULT;
                    break;
            }
            return typeface;
        } else {
            if (mDefaultSkin) {
                // 如果没有皮肤，那就加载当前App运行的Apk资源
                return Typeface.DEFAULT;
            }
            try {
                return Typeface.createFromAsset(mSkinResources.getAssets(), TYPE_PATH);
            } catch (Exception e) {
                return Typeface.DEFAULT;
            }
        }
    }

    public boolean ismDefaultSkin() {
        return mDefaultSkin;
    }

}
