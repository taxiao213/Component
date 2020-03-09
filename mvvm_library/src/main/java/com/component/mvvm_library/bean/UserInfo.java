package com.component.mvvm_library.bean;

import android.databinding.ObservableField;

/**
 * Created by A35 on 2020/3/9
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class UserInfo {

    // 被观察者的属性（切记：必须是public修饰，DataBinding的规范）
    public ObservableField<String> name = new ObservableField<>();

    public ObservableField<String> psw = new ObservableField<>();

}
