package com.component.arouterlibrary.core;

/**
 * 参数Parameter加载数据接口
 * Created by A35 on 2020/3/5
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public interface ParameterLoad {
    /**
     * 目标对象 属性名 = getIntent().属性类型（注解值or属性名），完成赋值
     *
     * @param object 目标对象，如MainActivity(中某些属性)
     */
    void loadParameter(Object object);
}
