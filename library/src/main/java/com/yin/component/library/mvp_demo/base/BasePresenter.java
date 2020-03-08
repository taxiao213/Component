package com.yin.component.library.mvp_demo.base;

import java.lang.ref.WeakReference;

/**
 * Presenter层
 * Created by A35 on 2020/3/8
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public abstract class BasePresenter<V extends BaseView, M extends BaseModel, CONTRACT> {
    protected M m;
    private WeakReference<V> weakReference;

    public BasePresenter() {
        m = getModel();
    }

    public void bindView(V v) {
        weakReference = new WeakReference<>(v);
    }

    public void unBindView() {
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
            System.gc();
        }
    }

    // Presenter层获取View层
    public V getView() {
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    // 获取子类具体的契约
    public abstract CONTRACT getContract();

    public abstract M getModel();
}
