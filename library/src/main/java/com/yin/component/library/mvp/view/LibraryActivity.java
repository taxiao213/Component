package com.yin.component.library.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yin.component.library.R;
import com.yin.component.library.mvp.DownLoaderContract;
import com.yin.component.library.mvp.model.ImageBean;
import com.yin.component.library.mvp.presenter.DownLoaderPresenter;
import com.yin.component.library.mvp.utils.Constant;

/**
 * V 层
 * Created by A35 on 2020/3/7
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public class LibraryActivity extends AppCompatActivity implements DownLoaderContract.PV {

    private DownLoaderPresenter presenter;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        View load = findViewById(R.id.load);
        imageView = findViewById(R.id.iv);
        presenter = new DownLoaderPresenter(this);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage();
            }
        });
    }

    private void loadImage() {
        ImageBean imageBean = new ImageBean();
        imageBean.setRequestPath(Constant.IMAGE_PATH);
        requestDownLoader(imageBean);
    }


    @Override
    public void requestDownLoader(ImageBean imageBean) {
        if (presenter != null) presenter.requestDownLoader(imageBean);
    }

    @Override
    public void reponseDownLoaderResult(boolean isSuccess, ImageBean imageBean) {

        Toast.makeText(this, isSuccess + "", Toast.LENGTH_SHORT).show();
        if (isSuccess && imageBean.getBitmap() != null) {
            imageView.setImageBitmap(imageBean.getBitmap());
        }
    }
}
