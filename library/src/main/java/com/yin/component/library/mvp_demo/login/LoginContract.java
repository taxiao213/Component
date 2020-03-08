package com.yin.component.library.mvp_demo.login;

import com.yin.component.library.mvp_demo.bean.BaseEntity;

/**
 * 将model层、view层、presenter层协商的共同业务、封装接口
 * 契约 合同
 * Created by A35 on 2020/3/8
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public interface LoginContract {

    interface Model {
        // model层执行登陆具体实现   ---2
        void executeLogin(String name, String psw) throws Exception;
    }

    interface View<T extends BaseEntity> {
        // 刷新View   ---4
        void handlerResult(T t);
    }

    interface Presenter<T extends BaseEntity> {
        // 登陆请求 ---1
        void requestLogin(String name, String psw);

        // 结果相应 接收到model层的结果，通知View层刷新  ---3
        void reponseResult(T t);
    }
}
