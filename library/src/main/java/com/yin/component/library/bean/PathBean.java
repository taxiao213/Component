package com.yin.component.library.bean;

/**
 * 路径model
 * Created by A35 on 2020/2/27
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class PathBean {
    private String path;
    private Class clazz;

    public PathBean(String path, Class clazz) {
        this.path = path;
        this.clazz = clazz;
    }

    public PathBean() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
