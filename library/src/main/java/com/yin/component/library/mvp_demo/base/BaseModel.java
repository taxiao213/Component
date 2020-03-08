package com.yin.component.library.mvp_demo.base;

/**
 * Created by A35 on 2020/3/8
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public abstract class BaseModel<P extends BasePresenter, CONTRACT> {
    protected P p;

    // 通知Presenter层
    public BaseModel(P p) {
        this.p = p;
    }

    public abstract CONTRACT getContract();
}
