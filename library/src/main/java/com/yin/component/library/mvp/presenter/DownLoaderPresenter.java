package com.yin.component.library.mvp.presenter;

import android.app.Activity;

import com.yin.component.library.mvp.DownLoaderContract;
import com.yin.component.library.mvp.engine.DownLoaderEngine;
import com.yin.component.library.mvp.model.ImageBean;
import com.yin.component.library.mvp.view.LibraryActivity;

/**
 * Created by A35 on 2020/3/7
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class DownLoaderPresenter implements DownLoaderContract.PV {
    private LibraryActivity activity;

    public DownLoaderPresenter(LibraryActivity activity) {
        this.activity = activity;
    }

    @Override
    public void requestDownLoader(ImageBean imageBean) {
        DownLoaderEngine downLoaderEngine = new DownLoaderEngine(this);
        try {
            downLoaderEngine.requestDownloader(imageBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reponseDownLoaderResult(final boolean isSuccess, final ImageBean imageBean) {
        // 将完成的结果告知View层（刷新UI）
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.reponseDownLoaderResult(isSuccess, imageBean);
            }
        });
    }
}
