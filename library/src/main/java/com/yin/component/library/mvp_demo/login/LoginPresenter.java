package com.yin.component.library.mvp_demo.login;

import android.util.Log;

import com.yin.component.library.mvp_demo.base.BasePresenter;
import com.yin.component.library.mvp_demo.bean.BaseEntity;
import com.yin.component.library.mvp_demo.bean.UserInfo;

/**
 * Created by A35 on 2020/3/8
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class LoginPresenter extends BasePresenter<LoginActivity, LoginModel, LoginContract.Presenter> {

    @Override
    public LoginContract.Presenter getContract() {
        // 需要执行View 给的需要，又要分配工作给Model执行
        return new LoginContract.Presenter<UserInfo>() {
            @Override
            public void requestLogin(String name, String psw) {
                try {
                    // 三种风格 （P层极端，要么不做事只转发，要么就是拼命一个人干活）
//                    m.getContract().executeLogin(name, psw);

                    // 第二种，让功能模块去做
                    LoginEngine<LoginPresenter> loginEngine = new LoginEngine<>(LoginPresenter.this);
                    loginEngine.post(name, psw);

                    // 第三种，自己处理
//                    UserInfo userInfo = new UserInfo(name,psw);
//                    reponseResult(userInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void reponseResult(UserInfo userInfo) {
                // 将结果告知View层
                getView().getContract().handlerResult(userInfo);
            }
        };
    }

    @Override
    public LoginModel getModel() {
        return new LoginModel(this);
    }
}
