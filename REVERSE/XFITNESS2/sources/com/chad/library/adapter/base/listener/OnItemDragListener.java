package com.chad.library.adapter.base.listener;

import androidx.recyclerview.widget.RecyclerView;

public interface OnItemDragListener {
    void onItemDragEnd(RecyclerView.c0 c0Var, int i2);

    void onItemDragMoving(RecyclerView.c0 c0Var, int i2, RecyclerView.c0 c0Var2, int i3);

    void onItemDragStart(RecyclerView.c0 c0Var, int i2);
}
