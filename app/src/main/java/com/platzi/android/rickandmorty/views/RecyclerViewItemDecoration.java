package com.platzi.android.rickandmorty.views;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {

    private final int space;

    public RecyclerViewItemDecoration(int space){
        this.space = space;
    }

    @Override
    public void getItemOffsets(
            @NonNull Rect outRect,
            @NonNull View view,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state
    ) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager) {
            GridLayoutViewItemDecoration.getItemOffsets(
                    outRect,
                    (GridLayoutManager) layoutManager,
                    parent.getChildViewHolder(view).getAdapterPosition(),
                    state.getItemCount(),
                    space
            );
        }else if(layoutManager instanceof LinearLayoutManager) {
            LinearLayoutViewItemDecoration.getItemOffsets(
                    outRect,
                    (LinearLayoutManager) layoutManager,
                    parent.getChildViewHolder(view).getAdapterPosition(),
                    state.getItemCount(),
                    space
            );
        }
    }
}
