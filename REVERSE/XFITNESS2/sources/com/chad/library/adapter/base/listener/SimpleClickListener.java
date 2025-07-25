package com.chad.library.adapter.base.listener;

import android.os.Build;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.h.c;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.HashSet;
import java.util.Set;

public abstract class SimpleClickListener implements RecyclerView.s {
    public static String TAG = "SimpleClickListener";
    protected BaseQuickAdapter baseQuickAdapter;
    private c mGestureDetector;
    /* access modifiers changed from: private */
    public boolean mIsPrepressed = false;
    /* access modifiers changed from: private */
    public boolean mIsShowPress = false;
    /* access modifiers changed from: private */
    public View mPressedView = null;
    private RecyclerView recyclerView;

    private class ItemTouchHelperGestureListener implements GestureDetector.OnGestureListener {
        private RecyclerView recyclerView;

        ItemTouchHelperGestureListener(RecyclerView recyclerView2) {
            this.recyclerView = recyclerView2;
        }

        private void resetPressedView(final View view) {
            if (view != null) {
                view.postDelayed(new Runnable() {
                    public void run() {
                        View view = view;
                        if (view != null) {
                            view.setPressed(false);
                        }
                    }
                }, 50);
            }
            boolean unused = SimpleClickListener.this.mIsPrepressed = false;
            View unused2 = SimpleClickListener.this.mPressedView = null;
        }

        public boolean onDown(MotionEvent motionEvent) {
            boolean unused = SimpleClickListener.this.mIsPrepressed = true;
            View unused2 = SimpleClickListener.this.mPressedView = this.recyclerView.a(motionEvent.getX(), motionEvent.getY());
            return false;
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
            return false;
        }

        /* JADX WARNING: Removed duplicated region for block: B:30:0x00a6  */
        /* JADX WARNING: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onLongPress(android.view.MotionEvent r10) {
            /*
                r9 = this;
                androidx.recyclerview.widget.RecyclerView r0 = r9.recyclerView
                int r0 = r0.getScrollState()
                if (r0 == 0) goto L_0x0009
                return
            L_0x0009:
                com.chad.library.adapter.base.listener.SimpleClickListener r0 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                boolean r0 = r0.mIsPrepressed
                if (r0 == 0) goto L_0x00f7
                com.chad.library.adapter.base.listener.SimpleClickListener r0 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                android.view.View r0 = r0.mPressedView
                if (r0 == 0) goto L_0x00f7
                com.chad.library.adapter.base.listener.SimpleClickListener r0 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                android.view.View r0 = r0.mPressedView
                r1 = 0
                r0.performHapticFeedback(r1)
                androidx.recyclerview.widget.RecyclerView r0 = r9.recyclerView
                com.chad.library.adapter.base.listener.SimpleClickListener r2 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                android.view.View r2 = r2.mPressedView
                androidx.recyclerview.widget.RecyclerView$c0 r0 = r0.g((android.view.View) r2)
                com.chad.library.adapter.base.BaseViewHolder r0 = (com.chad.library.adapter.base.BaseViewHolder) r0
                int r2 = r0.getAdapterPosition()
                r3 = -1
                if (r2 != r3) goto L_0x0039
                return
            L_0x0039:
                com.chad.library.adapter.base.listener.SimpleClickListener r3 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                boolean r3 = r3.isHeaderOrFooterPosition(r2)
                if (r3 != 0) goto L_0x00f7
                java.util.HashSet r3 = r0.getItemChildLongClickViewIds()
                java.util.Set r0 = r0.getNestViews()
                r4 = 1
                if (r3 == 0) goto L_0x00a3
                int r5 = r3.size()
                if (r5 <= 0) goto L_0x00a3
                java.util.Iterator r5 = r3.iterator()
            L_0x0056:
                boolean r6 = r5.hasNext()
                if (r6 == 0) goto L_0x00a3
                java.lang.Object r6 = r5.next()
                java.lang.Integer r6 = (java.lang.Integer) r6
                com.chad.library.adapter.base.listener.SimpleClickListener r7 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                android.view.View r7 = r7.mPressedView
                int r8 = r6.intValue()
                android.view.View r7 = r7.findViewById(r8)
                com.chad.library.adapter.base.listener.SimpleClickListener r8 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                boolean r8 = r8.inRangeOfView(r7, r10)
                if (r8 == 0) goto L_0x0056
                boolean r8 = r7.isEnabled()
                if (r8 == 0) goto L_0x0056
                if (r0 == 0) goto L_0x0087
                boolean r0 = r0.contains(r6)
                if (r0 == 0) goto L_0x0087
                goto L_0x00a1
            L_0x0087:
                com.chad.library.adapter.base.listener.SimpleClickListener r0 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                r0.setPressViewHotSpot(r10, r7)
                com.chad.library.adapter.base.listener.SimpleClickListener r0 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                com.chad.library.adapter.base.BaseQuickAdapter r5 = r0.baseQuickAdapter
                int r6 = r5.getHeaderLayoutCount()
                int r6 = r2 - r6
                r0.onItemChildLongClick(r5, r7, r6)
                r7.setPressed(r4)
                com.chad.library.adapter.base.listener.SimpleClickListener r0 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                boolean unused = r0.mIsShowPress = r4
            L_0x00a1:
                r0 = 1
                goto L_0x00a4
            L_0x00a3:
                r0 = 0
            L_0x00a4:
                if (r0 != 0) goto L_0x00f7
                com.chad.library.adapter.base.listener.SimpleClickListener r0 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                com.chad.library.adapter.base.BaseQuickAdapter r5 = r0.baseQuickAdapter
                android.view.View r6 = r0.mPressedView
                com.chad.library.adapter.base.listener.SimpleClickListener r7 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                com.chad.library.adapter.base.BaseQuickAdapter r7 = r7.baseQuickAdapter
                int r7 = r7.getHeaderLayoutCount()
                int r2 = r2 - r7
                r0.onItemLongClick(r5, r6, r2)
                com.chad.library.adapter.base.listener.SimpleClickListener r0 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                android.view.View r2 = r0.mPressedView
                r0.setPressViewHotSpot(r10, r2)
                com.chad.library.adapter.base.listener.SimpleClickListener r10 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                android.view.View r10 = r10.mPressedView
                r10.setPressed(r4)
                if (r3 == 0) goto L_0x00f2
                java.util.Iterator r10 = r3.iterator()
            L_0x00d2:
                boolean r0 = r10.hasNext()
                if (r0 == 0) goto L_0x00f2
                java.lang.Object r0 = r10.next()
                java.lang.Integer r0 = (java.lang.Integer) r0
                com.chad.library.adapter.base.listener.SimpleClickListener r2 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                android.view.View r2 = r2.mPressedView
                int r0 = r0.intValue()
                android.view.View r0 = r2.findViewById(r0)
                if (r0 == 0) goto L_0x00d2
                r0.setPressed(r1)
                goto L_0x00d2
            L_0x00f2:
                com.chad.library.adapter.base.listener.SimpleClickListener r10 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                boolean unused = r10.mIsShowPress = r4
            L_0x00f7:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.chad.library.adapter.base.listener.SimpleClickListener.ItemTouchHelperGestureListener.onLongPress(android.view.MotionEvent):void");
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
            return false;
        }

        public void onShowPress(MotionEvent motionEvent) {
            if (SimpleClickListener.this.mIsPrepressed && SimpleClickListener.this.mPressedView != null) {
                boolean unused = SimpleClickListener.this.mIsShowPress = true;
            }
        }

        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if (SimpleClickListener.this.mIsPrepressed && SimpleClickListener.this.mPressedView != null) {
                if (this.recyclerView.getScrollState() != 0) {
                    return false;
                }
                View access$100 = SimpleClickListener.this.mPressedView;
                BaseViewHolder baseViewHolder = (BaseViewHolder) this.recyclerView.g(access$100);
                int adapterPosition = baseViewHolder.getAdapterPosition();
                if (adapterPosition == -1 || SimpleClickListener.this.isHeaderOrFooterPosition(adapterPosition)) {
                    return false;
                }
                int headerLayoutCount = adapterPosition - SimpleClickListener.this.baseQuickAdapter.getHeaderLayoutCount();
                HashSet<Integer> childClickViewIds = baseViewHolder.getChildClickViewIds();
                Set<Integer> nestViews = baseViewHolder.getNestViews();
                if (childClickViewIds == null || childClickViewIds.size() <= 0) {
                    SimpleClickListener.this.setPressViewHotSpot(motionEvent, access$100);
                    SimpleClickListener.this.mPressedView.setPressed(true);
                    if (childClickViewIds != null && childClickViewIds.size() > 0) {
                        for (Integer intValue : childClickViewIds) {
                            View findViewById = access$100.findViewById(intValue.intValue());
                            if (findViewById != null) {
                                findViewById.setPressed(false);
                            }
                        }
                    }
                    SimpleClickListener simpleClickListener = SimpleClickListener.this;
                    simpleClickListener.onItemClick(simpleClickListener.baseQuickAdapter, access$100, headerLayoutCount);
                } else {
                    for (Integer next : childClickViewIds) {
                        View findViewById2 = access$100.findViewById(next.intValue());
                        if (findViewById2 != null) {
                            if (!SimpleClickListener.this.inRangeOfView(findViewById2, motionEvent) || !findViewById2.isEnabled()) {
                                findViewById2.setPressed(false);
                            } else if (nestViews != null && nestViews.contains(next)) {
                                return false;
                            } else {
                                SimpleClickListener.this.setPressViewHotSpot(motionEvent, findViewById2);
                                findViewById2.setPressed(true);
                                SimpleClickListener simpleClickListener2 = SimpleClickListener.this;
                                simpleClickListener2.onItemChildClick(simpleClickListener2.baseQuickAdapter, findViewById2, headerLayoutCount);
                                resetPressedView(findViewById2);
                                return true;
                            }
                        }
                    }
                    SimpleClickListener.this.setPressViewHotSpot(motionEvent, access$100);
                    SimpleClickListener.this.mPressedView.setPressed(true);
                    for (Integer intValue2 : childClickViewIds) {
                        View findViewById3 = access$100.findViewById(intValue2.intValue());
                        if (findViewById3 != null) {
                            findViewById3.setPressed(false);
                        }
                    }
                    SimpleClickListener simpleClickListener3 = SimpleClickListener.this;
                    simpleClickListener3.onItemClick(simpleClickListener3.baseQuickAdapter, access$100, headerLayoutCount);
                }
                resetPressedView(access$100);
            }
            return true;
        }
    }

    /* access modifiers changed from: private */
    public boolean isHeaderOrFooterPosition(int i2) {
        if (this.baseQuickAdapter == null) {
            RecyclerView recyclerView2 = this.recyclerView;
            if (recyclerView2 == null) {
                return false;
            }
            this.baseQuickAdapter = (BaseQuickAdapter) recyclerView2.getAdapter();
        }
        int itemViewType = this.baseQuickAdapter.getItemViewType(i2);
        if (itemViewType == 1365 || itemViewType == 273 || itemViewType == 819 || itemViewType == 546) {
            return true;
        }
        return false;
    }

    private boolean isHeaderOrFooterView(int i2) {
        return i2 == 1365 || i2 == 273 || i2 == 819 || i2 == 546;
    }

    /* access modifiers changed from: private */
    public void setPressViewHotSpot(MotionEvent motionEvent, View view) {
        if (Build.VERSION.SDK_INT >= 21 && view != null && view.getBackground() != null) {
            view.getBackground().setHotspot(motionEvent.getRawX(), motionEvent.getY() - view.getY());
        }
    }

    public boolean inRangeOfView(View view, MotionEvent motionEvent) {
        int[] iArr = new int[2];
        if (view != null && view.isShown()) {
            view.getLocationOnScreen(iArr);
            int i2 = iArr[0];
            int i3 = iArr[1];
            if (motionEvent.getRawX() < ((float) i2) || motionEvent.getRawX() > ((float) (i2 + view.getWidth())) || motionEvent.getRawY() < ((float) i3) || motionEvent.getRawY() > ((float) (i3 + view.getHeight()))) {
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean onInterceptTouchEvent(RecyclerView recyclerView2, MotionEvent motionEvent) {
        BaseViewHolder baseViewHolder;
        RecyclerView recyclerView3 = this.recyclerView;
        if (recyclerView3 == null) {
            this.recyclerView = recyclerView2;
            this.baseQuickAdapter = (BaseQuickAdapter) recyclerView2.getAdapter();
            this.mGestureDetector = new c(this.recyclerView.getContext(), new ItemTouchHelperGestureListener(this.recyclerView));
        } else if (recyclerView3 != recyclerView2) {
            this.recyclerView = recyclerView2;
            this.baseQuickAdapter = (BaseQuickAdapter) recyclerView2.getAdapter();
            this.mGestureDetector = new c(this.recyclerView.getContext(), new ItemTouchHelperGestureListener(this.recyclerView));
        }
        if (!this.mGestureDetector.a(motionEvent) && motionEvent.getActionMasked() == 1 && this.mIsShowPress) {
            View view = this.mPressedView;
            if (view != null && ((baseViewHolder = (BaseViewHolder) this.recyclerView.g(view)) == null || !isHeaderOrFooterView(baseViewHolder.getItemViewType()))) {
                this.mPressedView.setPressed(false);
            }
            this.mIsShowPress = false;
            this.mIsPrepressed = false;
        }
        return false;
    }

    public abstract void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2);

    public abstract void onItemChildLongClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2);

    public abstract void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2);

    public abstract void onItemLongClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2);

    public void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    public void onTouchEvent(RecyclerView recyclerView2, MotionEvent motionEvent) {
        this.mGestureDetector.a(motionEvent);
    }
}
