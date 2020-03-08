package com.yin.component.library.mvp_demo.login;

import com.yin.component.library.mvp_demo.bean.UserInfo;

/**
 * 登陆功能模块
 * Created by A35 on 2020/3/8
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class LoginEngine<P extends LoginPresenter> {

    private P p;

    public LoginEngine(P p) {
        this.p = p;
    }

    public void post(String name, String psw) {
        UserInfo userInfo = new UserInfo(name, psw);
        p.getContract().reponseResult(userInfo);
    }
}
