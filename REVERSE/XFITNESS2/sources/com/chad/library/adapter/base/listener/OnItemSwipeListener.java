package com.chad.library.adapter.base.listener;

import android.graphics.Canvas;
import androidx.recyclerview.widget.RecyclerView;

public interface OnItemSwipeListener {
    void clearView(RecyclerView.c0 c0Var, int i2);

    void onItemSwipeMoving(Canvas canvas, RecyclerView.c0 c0Var, float f2, float f3, boolean z);

    void onItemSwipeStart(RecyclerView.c0 c0Var, int i2);

    void onItemSwiped(RecyclerView.c0 c0Var, int i2);
}
