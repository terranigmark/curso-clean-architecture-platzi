package com.platzi.android.rickandmorty.views;

import android.graphics.Rect;

import androidx.recyclerview.widget.GridLayoutManager;

import static java.lang.Math.ceil;

class GridLayoutViewItemDecoration {

    public static void getItemOffsets(
            Rect outRect,
            GridLayoutManager gridLayoutManager,
            int position,
            int itemCount,
            int space
    ) {
        int cols = gridLayoutManager.getSpanCount();
        int rows = (int) ceil(itemCount / (double) cols);

        outRect.top = space;
        outRect.left = space;
        if(position % cols == cols - 1) {
            outRect.right = space;
        }else {
            outRect.right = 0;
        }

        if(position / cols == rows - 1) {
            outRect.bottom = space;
        }else {
            outRect.bottom = 0;
        }
    }
}
