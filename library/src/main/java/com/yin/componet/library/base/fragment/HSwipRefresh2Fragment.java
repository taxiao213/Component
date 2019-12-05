package com.yin.componet.library.base.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;

import com.yin.componet.library.R;


/**
 * 下拉加载 view fragment
 * Created by yin13 on 2019/8/29
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class HSwipRefresh2Fragment extends BaseFragment {


    LoadingFrameLayout flLoading;
    LinearLayout llRoot;
    SwipeRefreshLayout swip;

    private ISwipRefresh2Interface refreshInterface;

    @Override
    protected int getLayoutId() {
        return R.layout.include_swip_refresh2;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        flLoading = view.findViewById(R.id.fl_loading);
        llRoot = view.findViewById(R.id.ll_root);
        swip = view.findViewById(R.id.swip);
        if (refreshInterface != null) {
            llRoot.addView(refreshInterface.getView());
            flLoading.setReplaceText(refreshInterface.getReplaceText());
            flLoading.setReplaceDrawable(refreshInterface.getReplaceDrawable());
            swipEnable(refreshInterface.isEnableRefresh());
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
        if (llRoot != null) {
            llRoot.setVisibility(View.GONE);
        }
        if (refreshInterface != null) {
            refreshInterface.clear();
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

    public void setInterface(ISwipRefresh2Interface searchInterface) {
        this.refreshInterface = searchInterface;
    }

    public void loading(int visible) {
        if (llRoot != null) {
            llRoot.setVisibility(visible);
        }
    }

    /**
     * 是否禁止下拉刷新
     *
     * @param isSwip false 禁止 true 可刷新
     */
    public void swipEnable(boolean isSwip) {
        if (swip != null) {
            swip.setEnabled(isSwip);
        }
    }
}
