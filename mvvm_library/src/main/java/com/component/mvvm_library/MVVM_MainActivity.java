package com.component.mvvm_library;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.component.mvvm_library.bean.LoginViewModel;
import com.component.mvvm_library.bean.Model;
import com.component.mvvm_library.bean.UserInfo;
import com.component.mvvm_library.databinding.ActivityMvvm2Binding;
import com.component.mvvm_library.databinding.ActivityMvvm3Binding;
import com.component.mvvm_library.databinding.ActivityMvvm4Binding;
import com.component.mvvm_library.databinding.ActivityMvvmBinding;

/**
 * Created by A35 on 2020/3/9
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class MVVM_MainActivity extends AppCompatActivity {

    private Model model;
    private UserInfo userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mvvm);
        // 1.必须先Rebuilder   2.书写代码绑定
//        ActivityMvvmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);
//        LoginViewModel viewModel = new LoginViewModel(binding);


        // 单向绑定第一种
//        ActivityMvvm3Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm3);
//        userInfo = new UserInfo();
//        userInfo.name.set("000");
//        userInfo.psw.set("111");
//        binding.setUserinfo(userInfo);
//        Log.e(">>> ", " name " + userInfo.name.get() + " psw " + userInfo.psw.get());
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    Thread.sleep(1000);
//                    userInfo.name.set("1111");
//                    userInfo.psw.set("2222");
//                    Log.e(">>> ", " name " +userInfo.name.get() + " psw " + userInfo.psw.get());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();


        // 单向绑定第二种
//        ActivityMvvm2Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm2);
//        model = new Model("11", "22");
//        binding.setModel(model);
//        Log.e(">>> ", " name " + model.getName() + " psw " + model.getPsw());
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    Thread.sleep(1000);
//                    model.setName("222");
//                    model.setPsw("手机");
//                    Log.e(">>> ", " name " + model.getName() + " psw " + model.getPsw());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();

        // 双向绑定 model --> view , view --> model,在xml中 调用 @={userinfo.name}
//        android:text="@{userinfo.name}"
//        android:text="@={userinfo.psw}"
        ActivityMvvm4Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm4);
        userInfo = new UserInfo();
        userInfo.name.set("000");
        userInfo.psw.set("111");
        binding.setUserinfo(userInfo);
        Log.e(">>> ", " name " + userInfo.name.get() + " psw " + userInfo.psw.get());
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(6000);
                    Log.e(">>> ", " name " +userInfo.name.get() + " psw " + userInfo.psw.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
