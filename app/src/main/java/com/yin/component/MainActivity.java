package com.yin.component;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.component.annotation.ARouter;
import com.component.annotation.ARouterBean;
import com.component.arouterlibrary.core.ARouterLoadGroup;
import com.component.arouterlibrary.core.ARouterLoadPath;
import com.yin.component.apt.ARouter$$Group$$app;
import com.yin.component.test.ARouter$$Group$$componentA;
import com.yin.componenta.ComponentA_MainActivity;
import com.yin.componentb.ComponentB_MainActivity;
import com.yin.component.library.base.activity.BaseActivity;
import com.yin.component.library.base.fragment.HSwipRefreshFragment;
import com.yin.component.library.base.fragment.ISwipRefreshInterface;

import java.util.HashMap;

@ARouter(path = "/app/MainActivity")
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
//        startActivity(new Intent(mActivity, ComponentA_MainActivity.class));

        ARouterLoadGroup aRouter$$Group$$componentA = new ARouter$$Group$$componentA();
        HashMap<String, Class<? extends ARouterLoadPath>> stringClassHashMap = aRouter$$Group$$componentA.loadGroup();
        // 通过componentA组名获取对应路由路径对象
        Class<? extends ARouterLoadPath> componentA = stringClassHashMap.get("componentA");
        try {
            // 类加载动态加载路由路径对象
            ARouterLoadPath aRouterLoadPath = componentA.newInstance();
            HashMap<String, ARouterBean> aRouterBeanHashMap = aRouterLoadPath.loadPath();
            ARouterBean aRouterBean = aRouterBeanHashMap.get("/componentA/ComponentA_MainActivity");
            Intent intent = new Intent(this, aRouterBean.getClazz());
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void jumpB(View view) {
        startActivity(new Intent(mActivity, ComponentB_MainActivity.class));
    }

    public void jumpArouter(View view) {
//        startActivity(new Intent(mActivity, ArouterActivity.class));
        ARouter$$Group$$app group = new ARouter$$Group$$app();
        HashMap<String, Class<? extends ARouterLoadPath>> map = group.loadGroup();
        Class<? extends ARouterLoadPath> aClass = map.get("app");
        try {
            if (aClass != null) {
                ARouterLoadPath aRouterLoadPath = aClass.newInstance();
                HashMap<String, ARouterBean> loadPath = aRouterLoadPath.loadPath();
                ARouterBean aRouterBean = loadPath.get("/app/ParameterActivity");
                Intent intent = new Intent(mActivity, aRouterBean.getClazz());
                intent.putExtra("name","我是mainActitivy");
                intent.putExtra("age",1);
                startActivity(intent);
                // 通过注解传参数 Parameter$$ParameterActivity
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
