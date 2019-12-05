package com.yin.componet.library.base.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yin.componet.library.R;
import com.yin.componet.library.base.activity.BaseActivity;
import com.yin.componet.library.utils.StringUtils;
import com.yin.componet.library.utils.UIUtil;


/**
 * Created by Han on 2019/3/13
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class BaseDialogUtils {

    private AlertDialog mAlertDialog;
    private IDialogInterface iDialogInterface;
    private TextView title;
    private TextView cancel;
    private TextView ok;

    public BaseDialogUtils(BaseActivity mActivity, IDialogInterface iDialogInterface) {
        this.mAlertDialog = new AlertDialog
                .Builder(mActivity)
                .setCancelable(iDialogInterface == null || iDialogInterface.cancelable())
                .create();
        mAlertDialog.show();
        this.iDialogInterface = iDialogInterface;
        init();
    }

    private void init() {
        Window window = mAlertDialog.getWindow();
        if (window == null) return;
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setContentView(R.layout.dialog_base_layout);
        title = window.findViewById(R.id.tip_title);
        cancel = window.findViewById(R.id.cancle_click);
        ok = window.findViewById(R.id.ok_click);
        RecyclerView ry = window.findViewById(R.id.ry);
        if (iDialogInterface != null) {
            window.setGravity(iDialogInterface.getGravity());
            RecyclerView.LayoutManager layoutManager = iDialogInterface.getLayoutManager();
            ry.setLayoutManager(layoutManager);
            if (layoutManager instanceof GridLayoutManager) {
                ry.setPadding(UIUtil.dip2px(10), 0, 0, 0);
                ry.addItemDecoration(new GridItemDecoration(UIUtil.dip2px(10)));
            }
            RecyclerView.Adapter adapter = iDialogInterface.getAdapter();
            ry.setAdapter(adapter);
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iDialogInterface != null) {
                    iDialogInterface.onDetermine();
                }
                dismiss();
            }
        });
    }

    public BaseDialogUtils setTitle(String text) {
        if (title != null) {
            title.setText(StringUtils.null2Length0(text));
        }
        return this;
    }

    public BaseDialogUtils setCancelString(String text) {
        if (cancel != null) {
            cancel.setText(StringUtils.null2Length0(text));
        }
        return this;
    }

    public BaseDialogUtils setOKString(String text) {
        if (ok != null) {
            ok.setText(StringUtils.null2Length0(text));
        }
        return this;
    }

    public BaseDialogUtils setTitleColor(int color) {
        if (title != null) {
            title.setTextColor(color);
        }
        return this;
    }

    public BaseDialogUtils setCancelColor(int color) {
        if (cancel != null) {
            cancel.setTextColor(color);
        }
        return this;
    }

    public BaseDialogUtils setOKColor(int color) {
        if (ok != null) {
            ok.setTextColor(color);
        }
        return this;
    }

    /**
     * AlertDialog消失
     */
    public void dismiss() {
        if (mAlertDialog != null)
            mAlertDialog.dismiss();
    }

}
