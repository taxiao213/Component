package com.component.mvvm_library.bean;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.component.mvvm_library.databinding.ActivityMvvmBinding;

/**
 * Created by A35 on 2020/3/9
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class LoginViewModel {

    public UserInfo userInfo;

    public LoginViewModel(ActivityMvvmBinding binding) {
        this.userInfo = new UserInfo();
        binding.setUser(this);
    }

    public TextWatcher nameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // view层收到用户的输入，改变model层的JavaBean属性
            userInfo.name.set(String.valueOf(s));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public TextWatcher pswTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // view层收到用户的输入，改变model层的JavaBean属性
            userInfo.psw.set(String.valueOf(s));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public View.OnClickListener loginClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Thread.sleep(300);
                Log.e(">>> ", " name " + userInfo.name.get() + " psw " + userInfo.psw.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
