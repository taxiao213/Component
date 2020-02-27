package com.yin.component.library.base.fragment;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by yin13 on 2019/10/12
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public interface ISwipRefresh2Interface extends IBaseInterface {

    View getView();

    void clear();//清除数据

    String getReplaceText();//占位语

    Drawable getReplaceDrawable();//占位图

    boolean initLoading();//初始化是否加载数据

    boolean isEnableRefresh();//是否禁用下拉刷新
}
