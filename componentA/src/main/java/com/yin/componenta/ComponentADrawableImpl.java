package com.yin.componenta;

import com.component.annotation.ARouter;
import com.component.annotation.ARouter2;
import com.yin.component.library.componenta.ComponentADrawable;

/**
 * 模块对外暴露接口实现类，其他模块可以获取返回res资源
 * Created by A35 on 2020/3/6
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
@ARouter2(path = "/componentA/getDrawable", groupName = "componentA")
public class ComponentADrawableImpl implements ComponentADrawable {
    // pathMap.put("/componentA/getDrawable",ARouterBean.create(ARouterBean.Type.CALL,ComponentADrawableImpl.class,"/componentA/getDrawable","componentA"));
    @Override
    public int getDrawable() {
        return R.drawable.load_nothing;
    }
}
