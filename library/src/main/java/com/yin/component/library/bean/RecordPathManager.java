package com.yin.component.library.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 全局路径记录（根据子模块分组）
 * Created by A35 on 2020/2/27
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class RecordPathManager {
    private static HashMap<String, List<PathBean>> groupMap = new HashMap<>();

    /**
     * 收集需要跳转的类
     *
     * @param groupName 组名 eg:"componeta"
     * @param pathName  路径名称 eg:"ComponentA_MainActivity"
     * @param clazz     类对象 eg:ComponentA_MainActivity.class
     */
    public static void joinGroup(String groupName, String pathName, Class<?> clazz) {
        List<PathBean> list = groupMap.get(groupName);
        if (list == null) {
            list = new ArrayList<>();
            list.add(new PathBean(pathName, clazz));
            groupMap.put(groupName, list);
        } else {
            for (PathBean path : list) {
                if (!pathName.equals(path.getPath())) {
                    list.add(new PathBean(pathName, clazz));
                    groupMap.put(groupName, list);
                }
            }
        }
    }

    /**
     * 获取目标类对象
     *
     * @param groupName
     * @param pathName
     */
    public static Class<?> getTargetClass(String groupName, String pathName) {
        if (!groupMap.isEmpty()) {
            List<PathBean> pathBeans = groupMap.get(groupName);
            if (pathBeans != null) {
                for (PathBean path : pathBeans) {
                    if (pathName.equalsIgnoreCase(path.getPath())) {
                        return path.getClazz();
                    }
                }
            }
        }
        return null;
    }
}
