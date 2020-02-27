package com.yin.component.library.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.yin.component.library.R;
import com.yin.component.library.base.activity.BaseActivity;
import com.yin.component.library.utils.UIUtil;


/**
 * 下拉加载 recyclerview fragment
 * Created by yin13 on 2019/8/29
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class HSwipRefreshFragment extends BaseFragment {

    LoadingFrameLayout flLoading;
    RecyclerView ry;
    LinearLayout llRoot;
    SwipeRefreshLayout swip;

    private ISwipRefreshInterface refreshInterface;
    private BaseActivity activity;
    public RecyclerView.Adapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.include_swip_refresh;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        flLoading = view.findViewById(R.id.fl_loading);
        ry = view.findViewById(R.id.ry);
        llRoot = view.findViewById(R.id.ll_root);
        swip = view.findViewById(R.id.swip);
        if (refreshInterface != null) {
            RecyclerView.LayoutManager layoutManager = refreshInterface.getLayoutManager();
            ry.setLayoutManager(layoutManager);
            if (layoutManager instanceof GridLayoutManager) {
                ry.setPadding(UIUtil.getInstance().dip2px(10), 0, 0, 0);
                ry.addItemDecoration(new GridItemDecoration());
            }
            adapter = refreshInterface.getAdapter();
            ry.setAdapter(adapter);
            flLoading.setReplaceText(refreshInterface.getReplaceText());
            flLoading.setReplaceDrawable(refreshInterface.getReplaceDrawable());
        }
        swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swip.setRefreshing(false);
                loadingData();
            }
        });
        flLoading.setNetErrorOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNetErrorVisible(false);
                loadingData();
            }
        });
        if (refreshInterface != null && refreshInterface.initLoading()) {
            loadingData();
        }
    }

    private void loadingData() {
        if (flLoading != null) {
            flLoading.setNetErrorVisible(false);
        }
        if (refreshInterface != null) {
            refreshInterface.clear();
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        if (refreshInterface != null) {
            refreshInterface.getData();
        }
    }

    /**
     * 设置网络异常按钮隐藏和显示
     *
     * @param netVisible true 显示
     */
    public void setNetErrorVisible(boolean netVisible) {
        if (flLoading != null) {
            flLoading.setNetErrorVisible(netVisible);
            llRoot.setVisibility(netVisible ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * 数据为空隐藏和显示
     *
     * @param emptVisible
     */
    public void setEmptVisible(boolean emptVisible) {
        if (flLoading != null) {
            flLoading.setDataEmptyVisible(emptVisible);
            llRoot.setVisibility(emptVisible ? View.GONE : View.VISIBLE);
        }
    }

    public void setInterface(ISwipRefreshInterface searchInterface) {
        this.refreshInterface = searchInterface;
    }

    public void scrollTop() {
        ry.scrollToPosition(0);
    }

}
