package com.chad.library.adapter.base.listener;

import android.graphics.Canvas;
import androidx.recyclerview.widget.RecyclerView;

public interface IDraggableListener {
    boolean hasToggleView();

    boolean isItemDraggable();

    boolean isItemSwipeEnable();

    void onItemDragEnd(RecyclerView.c0 c0Var);

    void onItemDragMoving(RecyclerView.c0 c0Var, RecyclerView.c0 c0Var2);

    void onItemDragStart(RecyclerView.c0 c0Var);

    void onItemSwipeClear(RecyclerView.c0 c0Var);

    void onItemSwipeStart(RecyclerView.c0 c0Var);

    void onItemSwiped(RecyclerView.c0 c0Var);

    void onItemSwiping(Canvas canvas, RecyclerView.c0 c0Var, float f2, float f3, boolean z);
}
