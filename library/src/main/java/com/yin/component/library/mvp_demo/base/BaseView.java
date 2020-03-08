package com.yin.component.library.mvp_demo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by A35 on 2020/3/8
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public abstract class BaseView<P extends BasePresenter, CONTRACT> extends AppCompatActivity {

    protected P p;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        p = getPresenter();
        // 绑定
        p.bindView(this);
    }

    // 让P层做什么需求
    public abstract CONTRACT getContract();

    // 从子类获取具体的契约
    public abstract P getPresenter();

    // 如果Presenter层出现了异常，需要告知View层
    public void error(Exception e){}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 删除绑定
        p.unBindView( );
    }
}
