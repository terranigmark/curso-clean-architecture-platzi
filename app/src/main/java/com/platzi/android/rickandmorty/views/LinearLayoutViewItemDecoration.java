package com.platzi.android.rickandmorty.views;

import android.graphics.Rect;

import androidx.recyclerview.widget.LinearLayoutManager;

class LinearLayoutViewItemDecoration {

    public static void getItemOffsets(
            Rect outRect,
            LinearLayoutManager linearLayoutManager,
            int position,
            int itemCount,
            int space
    ) {
        outRect.top = space;
        outRect.left = space;
        if (linearLayoutManager.canScrollHorizontally()) {
            if (position == itemCount - 1) {
                outRect.right = space;
            } else {
                outRect.right = 0;
            }

            outRect.bottom = space;
        } else if (linearLayoutManager.canScrollVertically()) {
            outRect.right = space;

            if (position == itemCount - 1) {
                outRect.bottom = space;
            } else {
                outRect.bottom = 0;
            }
        }
    }
}
