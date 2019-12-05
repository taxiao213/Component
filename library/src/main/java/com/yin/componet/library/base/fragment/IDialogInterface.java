package com.yin.componet.library.base.fragment;

import android.support.v7.widget.RecyclerView;

/**
 * Created by yin13 on 2019/11/5
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public interface IDialogInterface {

    RecyclerView.LayoutManager getLayoutManager();

    RecyclerView.Adapter getAdapter();

    int getGravity();

    void onDetermine();

    boolean cancelable();

    void onClick(int position);
}
