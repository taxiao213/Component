package com.yin.component.test;

import com.component.arouterlibrary.core.ARouterLoadGroup;
import com.component.arouterlibrary.core.ARouterLoadPath;

import java.util.HashMap;

/**
 * Created by A35 on 2020/2/28
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class ARouter$$Group$$componentA implements ARouterLoadGroup {
    @Override
    public HashMap<String, Class<? extends ARouterLoadPath>> loadGroup() {
        HashMap<String, Class<? extends ARouterLoadPath>> map = new HashMap<>();
        map.put("componentA", ARouter$$Path$$componentA.class);
        return map;
    }
}
