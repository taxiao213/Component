package com.yin.component.library.mvp;

import com.yin.component.library.mvp.model.ImageBean;

/**
 * View层交互，Model层交互共同的需求（契约、合同）
 * Created by A35 on 2020/3/7
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public interface DownLoaderContract {
    interface M {
        // P层告诉M层，需要做什么事情
        void requestDownloader(ImageBean imageBean) throws Exception;
    }

    interface PV {
        // V层告诉P层，需要做什么
        void requestDownLoader(ImageBean imageBean);
        // P层得到结果返回，通知V层
        void reponseDownLoaderResult(boolean isSuccess, ImageBean imageBean);
    }
}
