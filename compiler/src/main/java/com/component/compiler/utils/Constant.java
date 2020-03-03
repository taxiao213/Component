package com.component.compiler.utils;

/**
 * Created by A35 on 2020/3/3
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class Constant {
    public final static String MODULE_NAME = "moduleName";// 用于接收模块名称
    public final static String PACKAGE_NAME_FOR_APT = "packageNameForAPT";// 用于接收生成的apt路径
    public final static String APT_NAME_PREFIX_PATH = "ARouter$$Path$$";// 生成的文件名称前缀
    public final static String APT_NAME_PREFIX_GROUP = "ARouter$$Group$$";// 生成的文件名称前缀

    // Activity全类名 解析Activity类
    public static final String ACTIVITY = "android.app.Activity";
    public static final String SPLITTER = "/";
    // 包名前缀封装
    static final String BASE_PACKAGE = "com.component.arouterlibrary";
    // 路由组Group加载接口
    public static final String AROUTE_GROUP = BASE_PACKAGE + ".core.ARouterLoadGroup";
    // 路由组Group对应的详细Path加载接口
    public static final String AROUTE_PATH = BASE_PACKAGE + ".core.ARouterLoadPath";
    // 路由组Group，参数名
    public static final String GROUP_PARAMETER_NAME = "groupMap";
    // 路由组Group，方法名
    public static final String GROUP_METHOD_NAME = "loadGroup";
    // 路由组Group对应的详细Path，参数名
    public static final String PATH_PARAMETER_NAME = "pathMap";
    // 路由组Group对应的详细Path，方法名
    public static final String PATH_METHOD_NAME = "loadPath";
}
