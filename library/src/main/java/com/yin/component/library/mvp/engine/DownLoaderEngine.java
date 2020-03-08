package com.yin.component.library.mvp.engine;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.yin.component.library.mvp.DownLoaderContract;
import com.yin.component.library.mvp.model.ImageBean;
import com.yin.component.library.mvp.presenter.DownLoaderPresenter;
import com.yin.component.library.mvp.utils.Constant;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * M 层
 * Created by A35 on 2020/3/7
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class DownLoaderEngine implements DownLoaderContract.M {
    private DownLoaderPresenter presenter;

    public DownLoaderEngine(DownLoaderPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void requestDownloader(ImageBean imageBean) throws Exception {
        // P层 告诉M层 做什么
        new Thread(new DownLoad(imageBean)).start();
    }

    public class DownLoad implements Runnable {
        private ImageBean imageBean;

        DownLoad(ImageBean imageBean) {
            this.imageBean = imageBean;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(imageBean.getRequestPath());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(5000);
                connection.setRequestMethod("GET");
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    showUi(Constant.SUCCESS, bitmap);
                } else {
                    showUi(Constant.ERROR, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void showUi(int resultCode, Bitmap bitmap) {
            imageBean.setBitmap(bitmap);
            presenter.reponseDownLoaderResult(resultCode == Constant.SUCCESS, imageBean);
        }
    }
}
