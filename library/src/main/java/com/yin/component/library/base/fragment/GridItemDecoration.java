package com.yin.component.library.base.fragment;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * 网格分割线 由于宽和高相同，所有分割时只能设置单边距离
 * Create by Han on 2019/5/21
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private int spacing;//设置的间距

    public GridItemDecoration() {
    }

    public GridItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildLayoutPosition(view);
        GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
        if (manager != null) {
            int gridSize = manager.getSpanCount();//每列数
            outRect.right = spacing;
            if (position < gridSize) {
                outRect.top = spacing;
                setBottom(outRect, spacing);
            } else {
                setBottom(outRect, spacing);
            }
        }
    }

    /**
     * 设置底部边距
     *
     * @param outRect Rect
     * @param spacing int
     */
    private void setBottom(@NonNull Rect outRect, int spacing) {
        outRect.bottom = spacing;
    }
}
