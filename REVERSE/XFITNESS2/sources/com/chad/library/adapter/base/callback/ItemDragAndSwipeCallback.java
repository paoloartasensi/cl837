package com.chad.library.adapter.base.callback;

import android.graphics.Canvas;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.j;
import com.chad.library.R$id;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.DraggableController;
import com.chad.library.adapter.base.listener.IDraggableListener;

public class ItemDragAndSwipeCallback extends j.a {
    private BaseItemDraggableAdapter mBaseItemDraggableAdapter;
    private int mDragMoveFlags = 15;
    private IDraggableListener mDraggableListener;
    private float mMoveThreshold = 0.1f;
    private int mSwipeMoveFlags = 32;
    private float mSwipeThreshold = 0.7f;

    public ItemDragAndSwipeCallback(BaseItemDraggableAdapter baseItemDraggableAdapter) {
        this.mBaseItemDraggableAdapter = baseItemDraggableAdapter;
    }

    private boolean isViewCreateByAdapter(RecyclerView.c0 c0Var) {
        int itemViewType = c0Var.getItemViewType();
        return itemViewType == 273 || itemViewType == 546 || itemViewType == 819 || itemViewType == 1365;
    }

    public void clearView(RecyclerView recyclerView, RecyclerView.c0 c0Var) {
        super.clearView(recyclerView, c0Var);
        if (!isViewCreateByAdapter(c0Var)) {
            if (c0Var.itemView.getTag(R$id.BaseQuickAdapter_dragging_support) != null && ((Boolean) c0Var.itemView.getTag(R$id.BaseQuickAdapter_dragging_support)).booleanValue()) {
                BaseItemDraggableAdapter baseItemDraggableAdapter = this.mBaseItemDraggableAdapter;
                if (baseItemDraggableAdapter != null) {
                    baseItemDraggableAdapter.onItemDragEnd(c0Var);
                } else {
                    IDraggableListener iDraggableListener = this.mDraggableListener;
                    if (iDraggableListener != null) {
                        iDraggableListener.onItemDragEnd(c0Var);
                    }
                }
                c0Var.itemView.setTag(R$id.BaseQuickAdapter_dragging_support, false);
            }
            if (c0Var.itemView.getTag(R$id.BaseQuickAdapter_swiping_support) != null && ((Boolean) c0Var.itemView.getTag(R$id.BaseQuickAdapter_swiping_support)).booleanValue()) {
                BaseItemDraggableAdapter baseItemDraggableAdapter2 = this.mBaseItemDraggableAdapter;
                if (baseItemDraggableAdapter2 != null) {
                    baseItemDraggableAdapter2.onItemSwipeClear(c0Var);
                } else {
                    IDraggableListener iDraggableListener2 = this.mDraggableListener;
                    if (iDraggableListener2 != null) {
                        iDraggableListener2.onItemSwipeClear(c0Var);
                    }
                }
                c0Var.itemView.setTag(R$id.BaseQuickAdapter_swiping_support, false);
            }
        }
    }

    public float getMoveThreshold(RecyclerView.c0 c0Var) {
        return this.mMoveThreshold;
    }

    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.c0 c0Var) {
        if (isViewCreateByAdapter(c0Var)) {
            return j.a.makeMovementFlags(0, 0);
        }
        return j.a.makeMovementFlags(this.mDragMoveFlags, this.mSwipeMoveFlags);
    }

    public float getSwipeThreshold(RecyclerView.c0 c0Var) {
        return this.mSwipeThreshold;
    }

    public boolean isItemViewSwipeEnabled() {
        BaseItemDraggableAdapter baseItemDraggableAdapter = this.mBaseItemDraggableAdapter;
        if (baseItemDraggableAdapter != null) {
            return baseItemDraggableAdapter.isItemSwipeEnable();
        }
        IDraggableListener iDraggableListener = this.mDraggableListener;
        if (iDraggableListener != null) {
            return iDraggableListener.isItemSwipeEnable();
        }
        return false;
    }

    public boolean isLongPressDragEnabled() {
        BaseItemDraggableAdapter baseItemDraggableAdapter = this.mBaseItemDraggableAdapter;
        if (baseItemDraggableAdapter == null) {
            IDraggableListener iDraggableListener = this.mDraggableListener;
            if (iDraggableListener == null || !iDraggableListener.isItemDraggable() || this.mDraggableListener.hasToggleView()) {
                return false;
            }
            return true;
        } else if (!baseItemDraggableAdapter.isItemDraggable() || this.mBaseItemDraggableAdapter.hasToggleView()) {
            return false;
        } else {
            return true;
        }
    }

    public void onChildDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.c0 c0Var, float f2, float f3, int i2, boolean z) {
        super.onChildDrawOver(canvas, recyclerView, c0Var, f2, f3, i2, z);
        if (i2 == 1 && !isViewCreateByAdapter(c0Var)) {
            View view = c0Var.itemView;
            canvas.save();
            if (f2 > 0.0f) {
                canvas.clipRect((float) view.getLeft(), (float) view.getTop(), ((float) view.getLeft()) + f2, (float) view.getBottom());
                canvas.translate((float) view.getLeft(), (float) view.getTop());
            } else {
                canvas.clipRect(((float) view.getRight()) + f2, (float) view.getTop(), (float) view.getRight(), (float) view.getBottom());
                canvas.translate(((float) view.getRight()) + f2, (float) view.getTop());
            }
            BaseItemDraggableAdapter baseItemDraggableAdapter = this.mBaseItemDraggableAdapter;
            if (baseItemDraggableAdapter != null) {
                baseItemDraggableAdapter.onItemSwiping(canvas, c0Var, f2, f3, z);
            } else {
                IDraggableListener iDraggableListener = this.mDraggableListener;
                if (iDraggableListener != null) {
                    iDraggableListener.onItemSwiping(canvas, c0Var, f2, f3, z);
                }
            }
            canvas.restore();
        }
    }

    public boolean onMove(RecyclerView recyclerView, RecyclerView.c0 c0Var, RecyclerView.c0 c0Var2) {
        return c0Var.getItemViewType() == c0Var2.getItemViewType();
    }

    public void onMoved(RecyclerView recyclerView, RecyclerView.c0 c0Var, int i2, RecyclerView.c0 c0Var2, int i3, int i4, int i5) {
        super.onMoved(recyclerView, c0Var, i2, c0Var2, i3, i4, i5);
        BaseItemDraggableAdapter baseItemDraggableAdapter = this.mBaseItemDraggableAdapter;
        if (baseItemDraggableAdapter != null) {
            baseItemDraggableAdapter.onItemDragMoving(c0Var, c0Var2);
            return;
        }
        IDraggableListener iDraggableListener = this.mDraggableListener;
        if (iDraggableListener != null) {
            iDraggableListener.onItemDragMoving(c0Var, c0Var2);
        }
    }

    public void onSelectedChanged(RecyclerView.c0 c0Var, int i2) {
        if (i2 == 2 && !isViewCreateByAdapter(c0Var)) {
            BaseItemDraggableAdapter baseItemDraggableAdapter = this.mBaseItemDraggableAdapter;
            if (baseItemDraggableAdapter != null) {
                baseItemDraggableAdapter.onItemDragStart(c0Var);
            } else {
                IDraggableListener iDraggableListener = this.mDraggableListener;
                if (iDraggableListener != null) {
                    iDraggableListener.onItemDragStart(c0Var);
                }
            }
            c0Var.itemView.setTag(R$id.BaseQuickAdapter_dragging_support, true);
        } else if (i2 == 1 && !isViewCreateByAdapter(c0Var)) {
            BaseItemDraggableAdapter baseItemDraggableAdapter2 = this.mBaseItemDraggableAdapter;
            if (baseItemDraggableAdapter2 != null) {
                baseItemDraggableAdapter2.onItemSwipeStart(c0Var);
            } else {
                IDraggableListener iDraggableListener2 = this.mDraggableListener;
                if (iDraggableListener2 != null) {
                    iDraggableListener2.onItemSwipeStart(c0Var);
                }
            }
            c0Var.itemView.setTag(R$id.BaseQuickAdapter_swiping_support, true);
        }
        super.onSelectedChanged(c0Var, i2);
    }

    public void onSwiped(RecyclerView.c0 c0Var, int i2) {
        if (!isViewCreateByAdapter(c0Var)) {
            BaseItemDraggableAdapter baseItemDraggableAdapter = this.mBaseItemDraggableAdapter;
            if (baseItemDraggableAdapter != null) {
                baseItemDraggableAdapter.onItemSwiped(c0Var);
                return;
            }
            IDraggableListener iDraggableListener = this.mDraggableListener;
            if (iDraggableListener != null) {
                iDraggableListener.onItemSwiped(c0Var);
            }
        }
    }

    public void setDragMoveFlags(int i2) {
        this.mDragMoveFlags = i2;
    }

    public void setMoveThreshold(float f2) {
        this.mMoveThreshold = f2;
    }

    public void setSwipeMoveFlags(int i2) {
        this.mSwipeMoveFlags = i2;
    }

    public void setSwipeThreshold(float f2) {
        this.mSwipeThreshold = f2;
    }

    public ItemDragAndSwipeCallback(DraggableController draggableController) {
        this.mDraggableListener = draggableController;
    }
}
