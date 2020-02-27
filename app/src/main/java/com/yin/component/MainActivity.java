package com.yin.component;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yin.componenta.ComponentA_MainActivity;
import com.yin.componentb.ComponentB_MainActivity;
import com.yin.component.library.base.activity.BaseActivity;
import com.yin.component.library.base.fragment.HSwipRefreshFragment;
import com.yin.component.library.base.fragment.ISwipRefreshInterface;

public class MainActivity extends BaseActivity {

    private HSwipRefreshFragment smartRefreshFragment;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        smartRefreshFragment = new HSwipRefreshFragment();
        smartRefreshFragment.setInterface(iSmartRefreshInterface);
        transaction.replace(R.id.fl, smartRefreshFragment);
        transaction.commitAllowingStateLoss();
    }

    public ISwipRefreshInterface iSmartRefreshInterface = new ISwipRefreshInterface() {


        @Override
        public RecyclerView.LayoutManager getLayoutManager() {
            return null;
        }

        @Override
        public RecyclerView.Adapter getAdapter() {
            return null;
        }

        @Override
        public void clear() {

        }

        @Override
        public String getReplaceText() {
            return null;
        }

        @Override
        public Drawable getReplaceDrawable() {
            return ContextCompat.getDrawable(mActivity, R.drawable.load_nothing);
        }

        @Override
        public boolean initLoading() {
            return true;
        }

        @Override
        public void getData() {

        }
    };

    public void jump(View view) {
        startActivity(new Intent(mActivity, SecondActivity.class));
    }

    public void jumpA(View view) {
        startActivity(new Intent(mActivity, ComponentA_MainActivity.class));
    }

    public void jumpB(View view) {
        startActivity(new Intent(mActivity, ComponentB_MainActivity.class));
    }
}
