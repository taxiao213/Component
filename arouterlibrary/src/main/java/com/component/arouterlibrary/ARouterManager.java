package com.component.arouterlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;

import com.component.annotation.ARouterBean;
import com.component.arouterlibrary.core.ARouterLoadGroup;
import com.component.arouterlibrary.core.ARouterLoadPath;

import java.util.HashMap;
import java.util.IllegalFormatCodePointException;

/**
 * 路由管理
 * Created by A35 on 2020/3/6
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class ARouterManager {
    // 路由组
    private String mGroup;
    // 路由path
    private String mPath;
    private LruCache<String, ARouterLoadGroup> mGroupCache;
    private LruCache<String, ARouterLoadPath> mPathCache;
    // APT生成的路由组Group源文件前缀名
    private static final String GROUP_FILE_PREFIX_NAME = ".ARouter$$Group$$";
    private static ARouterManager mArouterManager;

    public static ARouterManager getInstance() {
        if (mArouterManager == null) {
            synchronized (ARouterManager.class) {
                if (mArouterManager == null) {
                    mArouterManager = new ARouterManager();
                }
            }
        }
        return mArouterManager;
    }

    /**
     * 初始化LruCache
     */
    private ARouterManager() {
        mGroupCache = new LruCache<>(1024);
        mPathCache = new LruCache<>(1024);
    }

    public BundleManager build(String path) {
        if (TextUtils.isEmpty(path) || !path.startsWith("/")) {
            throw new IllegalArgumentException("未按规范配置，如：/app/MainActivity");
        }
        mGroup = subFromPath2Group(path);
        this.mPath = path;
        return new BundleManager();
    }

    /**
     * 截取组名
     *
     * @param path /app/MainActivity
     * @return String
     */
    private String subFromPath2Group(String path) {
        // 比如开发者代码为：path = "/MainActivity"，最后一个 / 符号必然在字符串第1位
        if (path.lastIndexOf("/") == 0) {
            // 架构师定义规范，让开发者遵循
            throw new IllegalArgumentException("@ARouter注解未按规范配置，如：/app/MainActivity");
        }
        // 从第一个 / 到第二个 / 中间截取，如：/app/MainActivity 截取出 app 作为group
        String finalGroup = path.substring(1, path.indexOf("/", 1));
        if (TextUtils.isEmpty(finalGroup)) {
            // 架构师定义规范，让开发者遵循
            throw new IllegalArgumentException("@ARouter注解未按规范配置，如：/app/MainActivity");
        }
        return finalGroup;
    }

    /**
     * 跳转
     *
     * @param context
     * @param bundleManager Bundle拼接参数管理类
     * @param code          这里的code，可能是requestCode，也可能是resultCode。取决于isResult
     * @return 普通跳转可以忽略，用于跨模块CALL接口
     */
    public Object navigation(Context context, BundleManager bundleManager, int code) {
        try {
            String className = context.getPackageName() + ".apt" + GROUP_FILE_PREFIX_NAME + mGroup;
            Log.e(">>> ", "groupClassName -> " + className);
            ARouterLoadGroup aRouterLoadGroup = mGroupCache.get(mGroup);
            if (aRouterLoadGroup == null) {
                Class<?> aClass = Class.forName(className);
                aRouterLoadGroup = (ARouterLoadGroup) aClass.newInstance();
                mGroupCache.put(mGroup, aRouterLoadGroup);
            }
            // 获取路由路径类ARouter$$Path$$app
            if (aRouterLoadGroup.loadGroup() == null) {
                throw new RuntimeException("路由加载失败");
            }
            ARouterLoadPath aRouterLoadPath = mPathCache.get(mPath);
            if (aRouterLoadPath == null) {
                HashMap<String, Class<? extends ARouterLoadPath>> loadGroup = aRouterLoadGroup.loadGroup();
                // 通过componentA组名获取对应路由路径对象
                Class<? extends ARouterLoadPath> aClass = loadGroup.get(mGroup);
                if (aClass != null) {
                    aRouterLoadPath = aClass.newInstance();
                    mPathCache.put(mPath, aRouterLoadPath);
                }
            }
            if (aRouterLoadPath != null) {
                HashMap<String, ARouterBean> aRouterBeanHashMap = aRouterLoadPath.loadPath();
                if (aRouterBeanHashMap.isEmpty()) {
                    throw new RuntimeException("路由路径加载失败");
                }
                ARouterBean aRouterBean = aRouterBeanHashMap.get(mPath);
                if (aRouterBean != null) {
                    ARouterBean.Type type = aRouterBean.getType();
                    switch (type) {
                        case ACTIVITY:
                            // 类加载动态加载路由路径对象
                            Intent intent = new Intent(context, aRouterBean.getClazz());
                            intent.putExtras(bundleManager.getBundle());
                            if (bundleManager.isResult()) {
                                ((Activity) context).setResult(Activity.RESULT_OK, intent);
                                ((Activity) context).finish();
                            }
                            if (code == -1) {
                                context.startActivity(intent);
                            } else {
                                // 跳转时是否回调
                                ((Activity) context).startActivityForResult(intent, code);
                            }
                            break;
                        case CALL:
                            //返回接口实现类
                            return aRouterBean.getClazz().newInstance();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
