package com.chad.library.adapter.base;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.h.i;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.j;
import com.chad.library.R$id;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import java.util.Collections;
import java.util.List;

public abstract class BaseItemDraggableAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    private static final String ERROR_NOT_SAME_ITEMTOUCHHELPER = "Item drag and item swipe should pass the same ItemTouchHelper";
    private static final int NO_TOGGLE_VIEW = 0;
    protected boolean itemDragEnabled = false;
    protected boolean itemSwipeEnabled = false;
    protected boolean mDragOnLongPress = true;
    protected j mItemTouchHelper;
    protected OnItemDragListener mOnItemDragListener;
    protected OnItemSwipeListener mOnItemSwipeListener;
    protected View.OnLongClickListener mOnToggleViewLongClickListener;
    protected View.OnTouchListener mOnToggleViewTouchListener;
    protected int mToggleViewId = 0;

    public BaseItemDraggableAdapter(List<T> list) {
        super(list);
    }

    private boolean inRange(int i2) {
        return i2 >= 0 && i2 < this.mData.size();
    }

    public void disableDragItem() {
        this.itemDragEnabled = false;
        this.mItemTouchHelper = null;
    }

    public void disableSwipeItem() {
        this.itemSwipeEnabled = false;
    }

    public void enableDragItem(j jVar) {
        enableDragItem(jVar, 0, true);
    }

    public void enableSwipeItem() {
        this.itemSwipeEnabled = true;
    }

    public int getViewHolderPosition(RecyclerView.c0 c0Var) {
        return c0Var.getAdapterPosition() - getHeaderLayoutCount();
    }

    public boolean hasToggleView() {
        return this.mToggleViewId != 0;
    }

    public boolean isItemDraggable() {
        return this.itemDragEnabled;
    }

    public boolean isItemSwipeEnable() {
        return this.itemSwipeEnabled;
    }

    public void onItemDragEnd(RecyclerView.c0 c0Var) {
        OnItemDragListener onItemDragListener = this.mOnItemDragListener;
        if (onItemDragListener != null && this.itemDragEnabled) {
            onItemDragListener.onItemDragEnd(c0Var, getViewHolderPosition(c0Var));
        }
    }

    public void onItemDragMoving(RecyclerView.c0 c0Var, RecyclerView.c0 c0Var2) {
        int viewHolderPosition = getViewHolderPosition(c0Var);
        int viewHolderPosition2 = getViewHolderPosition(c0Var2);
        if (inRange(viewHolderPosition) && inRange(viewHolderPosition2)) {
            if (viewHolderPosition < viewHolderPosition2) {
                int i2 = viewHolderPosition;
                while (i2 < viewHolderPosition2) {
                    int i3 = i2 + 1;
                    Collections.swap(this.mData, i2, i3);
                    i2 = i3;
                }
            } else {
                for (int i4 = viewHolderPosition; i4 > viewHolderPosition2; i4--) {
                    Collections.swap(this.mData, i4, i4 - 1);
                }
            }
            notifyItemMoved(c0Var.getAdapterPosition(), c0Var2.getAdapterPosition());
        }
        OnItemDragListener onItemDragListener = this.mOnItemDragListener;
        if (onItemDragListener != null && this.itemDragEnabled) {
            onItemDragListener.onItemDragMoving(c0Var, viewHolderPosition, c0Var2, viewHolderPosition2);
        }
    }

    public void onItemDragStart(RecyclerView.c0 c0Var) {
        OnItemDragListener onItemDragListener = this.mOnItemDragListener;
        if (onItemDragListener != null && this.itemDragEnabled) {
            onItemDragListener.onItemDragStart(c0Var, getViewHolderPosition(c0Var));
        }
    }

    public void onItemSwipeClear(RecyclerView.c0 c0Var) {
        OnItemSwipeListener onItemSwipeListener = this.mOnItemSwipeListener;
        if (onItemSwipeListener != null && this.itemSwipeEnabled) {
            onItemSwipeListener.clearView(c0Var, getViewHolderPosition(c0Var));
        }
    }

    public void onItemSwipeStart(RecyclerView.c0 c0Var) {
        OnItemSwipeListener onItemSwipeListener = this.mOnItemSwipeListener;
        if (onItemSwipeListener != null && this.itemSwipeEnabled) {
            onItemSwipeListener.onItemSwipeStart(c0Var, getViewHolderPosition(c0Var));
        }
    }

    public void onItemSwiped(RecyclerView.c0 c0Var) {
        int viewHolderPosition = getViewHolderPosition(c0Var);
        if (inRange(viewHolderPosition)) {
            this.mData.remove(viewHolderPosition);
            notifyItemRemoved(c0Var.getAdapterPosition());
            OnItemSwipeListener onItemSwipeListener = this.mOnItemSwipeListener;
            if (onItemSwipeListener != null && this.itemSwipeEnabled) {
                onItemSwipeListener.onItemSwiped(c0Var, viewHolderPosition);
            }
        }
    }

    public void onItemSwiping(Canvas canvas, RecyclerView.c0 c0Var, float f2, float f3, boolean z) {
        OnItemSwipeListener onItemSwipeListener = this.mOnItemSwipeListener;
        if (onItemSwipeListener != null && this.itemSwipeEnabled) {
            onItemSwipeListener.onItemSwipeMoving(canvas, c0Var, f2, f3, z);
        }
    }

    public void setOnItemDragListener(OnItemDragListener onItemDragListener) {
        this.mOnItemDragListener = onItemDragListener;
    }

    public void setOnItemSwipeListener(OnItemSwipeListener onItemSwipeListener) {
        this.mOnItemSwipeListener = onItemSwipeListener;
    }

    public void setToggleDragOnLongPress(boolean z) {
        this.mDragOnLongPress = z;
        if (z) {
            this.mOnToggleViewTouchListener = null;
            this.mOnToggleViewLongClickListener = new View.OnLongClickListener() {
                public boolean onLongClick(View view) {
                    BaseItemDraggableAdapter baseItemDraggableAdapter = BaseItemDraggableAdapter.this;
                    j jVar = baseItemDraggableAdapter.mItemTouchHelper;
                    if (jVar == null || !baseItemDraggableAdapter.itemDragEnabled) {
                        return true;
                    }
                    jVar.a((RecyclerView.c0) view.getTag(R$id.BaseQuickAdapter_viewholder_support));
                    throw null;
                }
            };
            return;
        }
        this.mOnToggleViewTouchListener = new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (i.a(motionEvent) != 0) {
                    return false;
                }
                BaseItemDraggableAdapter baseItemDraggableAdapter = BaseItemDraggableAdapter.this;
                if (baseItemDraggableAdapter.mDragOnLongPress) {
                    return false;
                }
                j jVar = baseItemDraggableAdapter.mItemTouchHelper;
                if (jVar == null || !baseItemDraggableAdapter.itemDragEnabled) {
                    return true;
                }
                jVar.a((RecyclerView.c0) view.getTag(R$id.BaseQuickAdapter_viewholder_support));
                throw null;
            }
        };
        this.mOnToggleViewLongClickListener = null;
    }

    public void setToggleViewId(int i2) {
        this.mToggleViewId = i2;
    }

    public void enableDragItem(j jVar, int i2) {
        enableDragItem(jVar, i2, true);
    }

    public void onBindViewHolder(K k, int i2) {
        View view;
        super.onBindViewHolder(k, i2);
        int itemViewType = k.getItemViewType();
        if (this.mItemTouchHelper != null && this.itemDragEnabled && itemViewType != 546 && itemViewType != 273 && itemViewType != 1365 && itemViewType != 819 && hasToggleView() && (view = k.getView(this.mToggleViewId)) != null) {
            view.setTag(R$id.BaseQuickAdapter_viewholder_support, k);
            if (this.mDragOnLongPress) {
                view.setOnLongClickListener(this.mOnToggleViewLongClickListener);
            } else {
                view.setOnTouchListener(this.mOnToggleViewTouchListener);
            }
        }
    }

    public void enableDragItem(j jVar, int i2, boolean z) {
        this.itemDragEnabled = true;
        this.mItemTouchHelper = jVar;
        setToggleViewId(i2);
        setToggleDragOnLongPress(z);
    }

    public BaseItemDraggableAdapter(int i2, List<T> list) {
        super(i2, list);
    }
}
