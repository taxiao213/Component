package com.yin.component.test;

import com.component.annotation.ARouterBean;
import com.component.arouterlibrary.core.ARouterLoadPath;
import com.yin.componenta.ComponentADrawableImpl;
import com.yin.componenta.ComponentA_MainActivity;

import java.util.HashMap;

/**
 * Created by A35 on 2020/2/28
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class ARouter$$Path$$componentA implements ARouterLoadPath {

    @Override
    public HashMap<String, ARouterBean> loadPath() {
        HashMap<String, ARouterBean> map = new HashMap<>();
        ARouterBean aroutBean = ARouterBean.create(ARouterBean.Type.ACTIVITY, ComponentA_MainActivity.class, "/componentA/ComponentA_MainActivity", "componentA");
        map.put("/componentA/ComponentA_MainActivity", aroutBean);
        // CALL 类型
        map.put("/componenta/getDrawable", ARouterBean.create(ARouterBean.Type.CALL, ComponentADrawableImpl.class, "/componenta/getDrawable", "componenta"));
        return map;
    }
}
