package com.component.skinlibrary2.model;

import android.content.res.TypedArray;
import android.util.SparseIntArray;

import java.util.HashMap;

/**
 * 临时JavaBean对象，用于存储控件的key、value
 * 如：key:android:textColor, value:@Color/xxx
 * Created by A35 on 2020/2/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class AttrsModel {
    private static final int DEFAULT_VALUE = -1;
    private SparseIntArray resourcesMap;

    public AttrsModel() {
        resourcesMap = new SparseIntArray();
    }

    /**
     * 储控件的key、value
     *
     * @param typedArray 控件属性的类型集合，如：background / textColor
     * @param styleable  自定义属性，参考value/attrs.xml
     */
    public void saveViewResource(TypedArray typedArray, int[] styleable) {
        for (int i = 0; i < typedArray.length(); i++) {
            int key = styleable[i];
            int resourceId = typedArray.getResourceId(i, DEFAULT_VALUE);
            resourcesMap.put(key, resourceId);
        }
    }

    /**
     * 获取控件某属性的resourceId
     *
     * @param styleable 自定义属性，参考value/attrs.xml
     * @return 某控件某属性的resourceId
     */
    public int getViewResource(int styleable) {
        return resourcesMap.get(styleable);
    }
}
