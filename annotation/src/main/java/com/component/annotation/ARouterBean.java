package com.component.annotation;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;

/**
 * 路由存储
 * Created by A35 on 2020/2/28
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class ARouterBean {

    public enum Type {
        ACTIVITY
    }

    private Type type;// 枚举类型
    private Element element;// 类节点
    private Class<?> clazz;// 注解使用的类对象
    private String groupName;// 路由组
    private String path;// 路由地址

    private ARouterBean(Builder builder) {
        this.type = builder.type;
        this.element = builder.element;
        this.clazz = builder.clazz;
        this.groupName = builder.groupName;
        this.path = builder.path;
    }

    private ARouterBean(Type type, Class<?> clazz, String groupName, String path) {
        this.type = type;
        this.clazz = clazz;
        this.groupName = groupName;
        this.path = path;
    }

    // 对外提供简易版构造方法，主要是为了方便APT生成代码
    public static ARouterBean create(Type type, Class<?> clazz, String path, String group) {
        return new ARouterBean(type, clazz, path, group);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static class Builder {
        private Type type;// 枚举类型
        private Element element;// 类节点
        private Class<?> clazz;// 注解使用的类对象
        private String groupName;// 路由组
        private String path;// 路由地址

        public Builder() {
        }

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        public Builder setElement(Element element) {
            this.element = element;
            return this;
        }

        public Builder setClazz(Class<?> clazz) {
            this.clazz = clazz;
            return this;
        }

        public Builder setGroupName(String groupName) {
            this.groupName = groupName;
            return this;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        // 最后的build或者create，往往是做参数的校验或者初始化赋值工作
        public ARouterBean build() {
            if (path == null || path.length() == 0) {
                throw new IllegalArgumentException("path必填项为空，如：/app/MainActivity");
            }
            return new ARouterBean(this);
        }
    }

    @Override
    public String toString() {
        return "ARouterBean{" +
                "element=" + element +
                ", clazz=" + clazz +
                ", groupName='" + groupName + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
