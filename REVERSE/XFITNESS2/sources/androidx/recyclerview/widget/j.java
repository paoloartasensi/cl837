package androidx.recyclerview.widget;

import android.animation.Animator;
import android.graphics.Canvas;
import android.view.View;
import android.view.animation.Interpolator;
import androidx.core.h.v;
import androidx.recyclerview.R$dimen;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/* compiled from: ItemTouchHelper */
public class j extends RecyclerView.n implements RecyclerView.q {

    /* compiled from: ItemTouchHelper */
    public static abstract class a {
        private static final int ABS_HORIZONTAL_DIR_FLAGS = 789516;
        public static final int DEFAULT_DRAG_ANIMATION_DURATION = 200;
        public static final int DEFAULT_SWIPE_ANIMATION_DURATION = 250;
        private static final long DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS = 2000;
        static final int RELATIVE_DIR_FLAGS = 3158064;
        private static final Interpolator sDragScrollInterpolator = new C0045a();
        private static final Interpolator sDragViewScrollCapInterpolator = new b();
        private int mCachedMaxScrollSpeed = -1;

        /* renamed from: androidx.recyclerview.widget.j$a$a  reason: collision with other inner class name */
        /* compiled from: ItemTouchHelper */
        static class C0045a implements Interpolator {
            C0045a() {
            }

            public float getInterpolation(float f2) {
                return f2 * f2 * f2 * f2 * f2;
            }
        }

        /* compiled from: ItemTouchHelper */
        static class b implements Interpolator {
            b() {
            }

            public float getInterpolation(float f2) {
                float f3 = f2 - 1.0f;
                return (f3 * f3 * f3 * f3 * f3) + 1.0f;
            }
        }

        public static int convertToRelativeDirection(int i2, int i3) {
            int i4;
            int i5 = i2 & ABS_HORIZONTAL_DIR_FLAGS;
            if (i5 == 0) {
                return i2;
            }
            int i6 = i2 & (i5 ^ -1);
            if (i3 == 0) {
                i4 = i5 << 2;
            } else {
                int i7 = i5 << 1;
                i6 |= -789517 & i7;
                i4 = (i7 & ABS_HORIZONTAL_DIR_FLAGS) << 2;
            }
            return i6 | i4;
        }

        public static k getDefaultUIUtil() {
            return l.a;
        }

        private int getMaxDragScroll(RecyclerView recyclerView) {
            if (this.mCachedMaxScrollSpeed == -1) {
                this.mCachedMaxScrollSpeed = recyclerView.getResources().getDimensionPixelSize(R$dimen.item_touch_helper_max_drag_scroll_per_frame);
            }
            return this.mCachedMaxScrollSpeed;
        }

        public static int makeFlag(int i2, int i3) {
            return i3 << (i2 * 8);
        }

        public static int makeMovementFlags(int i2, int i3) {
            int makeFlag = makeFlag(0, i3 | i2);
            return makeFlag(2, i2) | makeFlag(1, i3) | makeFlag;
        }

        public boolean canDropOver(RecyclerView recyclerView, RecyclerView.c0 c0Var, RecyclerView.c0 c0Var2) {
            return true;
        }

        public RecyclerView.c0 chooseDropTarget(RecyclerView.c0 c0Var, List<RecyclerView.c0> list, int i2, int i3) {
            int bottom;
            int abs;
            int top;
            int abs2;
            int left;
            int abs3;
            int right;
            int abs4;
            RecyclerView.c0 c0Var2 = c0Var;
            int width = i2 + c0Var2.itemView.getWidth();
            int height = i3 + c0Var2.itemView.getHeight();
            int left2 = i2 - c0Var2.itemView.getLeft();
            int top2 = i3 - c0Var2.itemView.getTop();
            int size = list.size();
            RecyclerView.c0 c0Var3 = null;
            int i4 = -1;
            for (int i5 = 0; i5 < size; i5++) {
                RecyclerView.c0 c0Var4 = list.get(i5);
                if (left2 > 0 && (right = c0Var4.itemView.getRight() - width) < 0 && c0Var4.itemView.getRight() > c0Var2.itemView.getRight() && (abs4 = Math.abs(right)) > i4) {
                    c0Var3 = c0Var4;
                    i4 = abs4;
                }
                if (left2 < 0 && (left = c0Var4.itemView.getLeft() - i2) > 0 && c0Var4.itemView.getLeft() < c0Var2.itemView.getLeft() && (abs3 = Math.abs(left)) > i4) {
                    c0Var3 = c0Var4;
                    i4 = abs3;
                }
                if (top2 < 0 && (top = c0Var4.itemView.getTop() - i3) > 0 && c0Var4.itemView.getTop() < c0Var2.itemView.getTop() && (abs2 = Math.abs(top)) > i4) {
                    c0Var3 = c0Var4;
                    i4 = abs2;
                }
                if (top2 > 0 && (bottom = c0Var4.itemView.getBottom() - height) < 0 && c0Var4.itemView.getBottom() > c0Var2.itemView.getBottom() && (abs = Math.abs(bottom)) > i4) {
                    c0Var3 = c0Var4;
                    i4 = abs;
                }
            }
            return c0Var3;
        }

        public void clearView(RecyclerView recyclerView, RecyclerView.c0 c0Var) {
            l.a.a(c0Var.itemView);
        }

        public int convertToAbsoluteDirection(int i2, int i3) {
            int i4;
            int i5 = i2 & RELATIVE_DIR_FLAGS;
            if (i5 == 0) {
                return i2;
            }
            int i6 = i2 & (i5 ^ -1);
            if (i3 == 0) {
                i4 = i5 >> 2;
            } else {
                int i7 = i5 >> 1;
                i6 |= -3158065 & i7;
                i4 = (i7 & RELATIVE_DIR_FLAGS) >> 2;
            }
            return i6 | i4;
        }

        /* access modifiers changed from: package-private */
        public final int getAbsoluteMovementFlags(RecyclerView recyclerView, RecyclerView.c0 c0Var) {
            return convertToAbsoluteDirection(getMovementFlags(recyclerView, c0Var), v.o(recyclerView));
        }

        public long getAnimationDuration(RecyclerView recyclerView, int i2, float f2, float f3) {
            RecyclerView.l itemAnimator = recyclerView.getItemAnimator();
            if (itemAnimator == null) {
                return i2 == 8 ? 200 : 250;
            }
            if (i2 == 8) {
                return itemAnimator.e();
            }
            return itemAnimator.f();
        }

        public int getBoundingBoxMargin() {
            return 0;
        }

        public abstract float getMoveThreshold(RecyclerView.c0 c0Var);

        public abstract int getMovementFlags(RecyclerView recyclerView, RecyclerView.c0 c0Var);

        public float getSwipeEscapeVelocity(float f2) {
            return f2;
        }

        public abstract float getSwipeThreshold(RecyclerView.c0 c0Var);

        public float getSwipeVelocityThreshold(float f2) {
            return f2;
        }

        /* access modifiers changed from: package-private */
        public boolean hasDragFlag(RecyclerView recyclerView, RecyclerView.c0 c0Var) {
            return (getAbsoluteMovementFlags(recyclerView, c0Var) & 16711680) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean hasSwipeFlag(RecyclerView recyclerView, RecyclerView.c0 c0Var) {
            return (getAbsoluteMovementFlags(recyclerView, c0Var) & 65280) != 0;
        }

        public int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int i2, int i3, int i4, long j2) {
            float f2 = 1.0f;
            int signum = (int) (((float) (((int) Math.signum((float) i3)) * getMaxDragScroll(recyclerView))) * sDragViewScrollCapInterpolator.getInterpolation(Math.min(1.0f, (((float) Math.abs(i3)) * 1.0f) / ((float) i2))));
            if (j2 <= DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS) {
                f2 = ((float) j2) / 2000.0f;
            }
            int interpolation = (int) (((float) signum) * sDragScrollInterpolator.getInterpolation(f2));
            if (interpolation == 0) {
                return i3 > 0 ? 1 : -1;
            }
            return interpolation;
        }

        public abstract boolean isItemViewSwipeEnabled();

        public abstract boolean isLongPressDragEnabled();

        public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.c0 c0Var, float f2, float f3, int i2, boolean z) {
            l.a.b(canvas, recyclerView, c0Var.itemView, f2, f3, i2, z);
        }

        public void onChildDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.c0 c0Var, float f2, float f3, int i2, boolean z) {
            l.a.a(canvas, recyclerView, c0Var.itemView, f2, f3, i2, z);
        }

        /* access modifiers changed from: package-private */
        public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.c0 c0Var, List<b> list, int i2, float f2, float f3) {
            Canvas canvas2 = canvas;
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                b bVar = list.get(i3);
                bVar.a();
                int save = canvas.save();
                onChildDraw(canvas, recyclerView, bVar.e, bVar.f846h, bVar.f847i, bVar.f844f, false);
                canvas.restoreToCount(save);
            }
            if (c0Var != null) {
                int save2 = canvas.save();
                onChildDraw(canvas, recyclerView, c0Var, f2, f3, i2, true);
                canvas.restoreToCount(save2);
            }
        }

        /* access modifiers changed from: package-private */
        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.c0 c0Var, List<b> list, int i2, float f2, float f3) {
            Canvas canvas2 = canvas;
            List<b> list2 = list;
            int size = list.size();
            boolean z = false;
            for (int i3 = 0; i3 < size; i3++) {
                b bVar = list2.get(i3);
                int save = canvas.save();
                onChildDrawOver(canvas, recyclerView, bVar.e, bVar.f846h, bVar.f847i, bVar.f844f, false);
                canvas.restoreToCount(save);
            }
            if (c0Var != null) {
                int save2 = canvas.save();
                onChildDrawOver(canvas, recyclerView, c0Var, f2, f3, i2, true);
                canvas.restoreToCount(save2);
            }
            for (int i4 = size - 1; i4 >= 0; i4--) {
                b bVar2 = list2.get(i4);
                if (bVar2.f848j && !bVar2.f845g) {
                    list2.remove(i4);
                } else if (!bVar2.f848j) {
                    z = true;
                }
            }
            if (z) {
                recyclerView.invalidate();
            }
        }

        public abstract boolean onMove(RecyclerView recyclerView, RecyclerView.c0 c0Var, RecyclerView.c0 c0Var2);

        public void onMoved(RecyclerView recyclerView, RecyclerView.c0 c0Var, int i2, RecyclerView.c0 c0Var2, int i3, int i4, int i5) {
            RecyclerView.o layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof c) {
                ((c) layoutManager).a(c0Var.itemView, c0Var2.itemView, i4, i5);
                return;
            }
            if (layoutManager.a()) {
                if (layoutManager.f(c0Var2.itemView) <= recyclerView.getPaddingLeft()) {
                    recyclerView.i(i3);
                }
                if (layoutManager.i(c0Var2.itemView) >= recyclerView.getWidth() - recyclerView.getPaddingRight()) {
                    recyclerView.i(i3);
                }
            }
            if (layoutManager.b()) {
                if (layoutManager.j(c0Var2.itemView) <= recyclerView.getPaddingTop()) {
                    recyclerView.i(i3);
                }
                if (layoutManager.e(c0Var2.itemView) >= recyclerView.getHeight() - recyclerView.getPaddingBottom()) {
                    recyclerView.i(i3);
                }
            }
        }

        public void onSelectedChanged(RecyclerView.c0 c0Var, int i2) {
            if (c0Var != null) {
                l.a.b(c0Var.itemView);
            }
        }

        public abstract void onSwiped(RecyclerView.c0 c0Var, int i2);
    }

    /* compiled from: ItemTouchHelper */
    private static class b implements Animator.AnimatorListener {
        final float a;
        final float b;
        final float c;
        final float d;
        final RecyclerView.c0 e;

        /* renamed from: f  reason: collision with root package name */
        final int f844f;

        /* renamed from: g  reason: collision with root package name */
        boolean f845g;

        /* renamed from: h  reason: collision with root package name */
        float f846h;

        /* renamed from: i  reason: collision with root package name */
        float f847i;

        /* renamed from: j  reason: collision with root package name */
        boolean f848j;
        private float k;

        public void a(float f2) {
            this.k = f2;
        }

        public void onAnimationCancel(Animator animator) {
            a(1.0f);
        }

        public void onAnimationEnd(Animator animator) {
            if (!this.f848j) {
                this.e.setIsRecyclable(true);
            }
            this.f848j = true;
        }

        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationStart(Animator animator) {
        }

        public void a() {
            float f2 = this.a;
            float f3 = this.c;
            if (f2 == f3) {
                this.f846h = this.e.itemView.getTranslationX();
            } else {
                this.f846h = f2 + (this.k * (f3 - f2));
            }
            float f4 = this.b;
            float f5 = this.d;
            if (f4 == f5) {
                this.f847i = this.e.itemView.getTranslationY();
            } else {
                this.f847i = f4 + (this.k * (f5 - f4));
            }
        }
    }

    /* compiled from: ItemTouchHelper */
    public interface c {
        void a(View view, View view2, int i2, int i3);
    }

    public void a(RecyclerView.c0 c0Var) {
        throw null;
    }
}
