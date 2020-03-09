package com.component.mvvm_library.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.component.mvvm_library.BR;

/**
 * 数据绑定刷新 类 extends BaseObservable
 * 在获取的方法上加@Bindable
 * 在设置的方法里  notifyPropertyChanged(com.component.mvvm_library.BR.name);
 * <p>
 * Created by A35 on 2020/3/9
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class Model extends BaseObservable {
    public Model() {
    }

    public Model(String name, String psw) {
        this.name = name;
        this.psw = psw;
    }

    private String name;
    private String psw;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(com.component.mvvm_library.BR.name);
    }

    @Bindable
    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
        notifyPropertyChanged(com.component.mvvm_library.BR.psw);
    }
}
