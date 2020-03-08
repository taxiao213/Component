package com.yin.component.library.mvp_demo.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.yin.component.library.R;
import com.yin.component.library.mvp_demo.base.BaseView;
import com.yin.component.library.mvp_demo.bean.UserInfo;

/**
 * Created by A35 on 2020/3/8
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class LoginActivity extends BaseView<LoginPresenter, LoginContract.View> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        View login = findViewById(R.id.tv_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        p.getContract().requestLogin("111", "222");
    }

    @Override
    public LoginContract.View getContract() {
        return new LoginContract.View<UserInfo>() {
            @Override
            public void handlerResult(UserInfo userInfo) {
                if (userInfo != null) {
                    Log.e(">>> ", userInfo.toString());
                } else {
                    Log.e(">>> ", "null");
                }
            }
        };
    }

    @Override
    public LoginPresenter getPresenter() {
        return new LoginPresenter();
    }
}
