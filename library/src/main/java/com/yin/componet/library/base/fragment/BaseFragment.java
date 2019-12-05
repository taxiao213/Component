package com.yin.componet.library.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yin.componet.library.base.activity.BaseActivity;


/**
 * Created by aaa on 2017/4/19.
 */

public abstract class BaseFragment extends Fragment {

    protected static BaseActivity mActivity;
    protected abstract int getLayoutId();
    protected abstract void initView(View view, Bundle savedInstanceState);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);

        initView(view, savedInstanceState);
        return view;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }


}

