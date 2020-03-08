package com.yin.component.library.mvp_demo.login;

import com.yin.component.library.mvp_demo.base.BaseModel;
import com.yin.component.library.mvp_demo.bean.UserInfo;

/**
 * 接收P层交给的需求
 * Created by A35 on 2020/3/8
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class LoginModel extends BaseModel<LoginPresenter, LoginContract.Model> {

    public LoginModel(LoginPresenter loginPresenter) {
        super(loginPresenter);
    }

    @Override
    public LoginContract.Model getContract() {
        return new LoginContract.Model() {
            @Override
            public void executeLogin(String name, String psw) throws Exception {
                UserInfo userInfo = new UserInfo(name, psw);
                p.getContract().reponseResult(userInfo);
            }
        };
    }
}
