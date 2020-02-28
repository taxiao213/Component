package com.component.arouterlibrary.core;

import com.component.annotation.ARouterBean;

import java.util.HashMap;

/**
 * 路由Group对应的详细Path加载数据接口
 * Created by A35 on 2020/2/28
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public interface ARouterLoadPath {
    // key:"/app/MainActivity",value:ArouterBean
    HashMap<String, ARouterBean> loadPath();
}
