package com.component.arouterlibrary.core;

import java.util.HashMap;

/**
 * 路由Group加载数据接口
 * Created by A35 on 2020/2/28
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public interface ARouterLoadGroup {
    // eg: key:"app",value:ARouter$$Path$$app.class "实现ARouterLoadPath接口"
    HashMap<String, Class<? extends ARouterLoadPath>> loadGroup();
}
