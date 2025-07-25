package com.google.android.material.behavior;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.h.v;
import androidx.customview.a.c;

public class SwipeDismissBehavior<V extends View> extends CoordinatorLayout.c<V> {
    androidx.customview.a.c a;
    b b;
    private boolean c;
    private float d = 0.0f;
    private boolean e;

    /* renamed from: f  reason: collision with root package name */
    int f1398f = 2;

    /* renamed from: g  reason: collision with root package name */
    float f1399g = 0.5f;

    /* renamed from: h  reason: collision with root package name */
    float f1400h = 0.0f;

    /* renamed from: i  reason: collision with root package name */
    float f1401i = 0.5f;

    /* renamed from: j  reason: collision with root package name */
    private final c.C0030c f1402j = new a();

    class a extends c.C0030c {
        private int a;
        private int b = -1;

        a() {
        }

        public void a(View view, int i2) {
            this.b = i2;
            this.a = view.getLeft();
            ViewParent parent = view.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }

        public boolean b(View view, int i2) {
            return this.b == -1 && SwipeDismissBehavior.this.a(view);
        }

        public void c(int i2) {
            b bVar = SwipeDismissBehavior.this.b;
            if (bVar != null) {
                bVar.a(i2);
            }
        }

        public int b(View view, int i2, int i3) {
            return view.getTop();
        }

        public void a(View view, float f2, float f3) {
            boolean z;
            int i2;
            b bVar;
            this.b = -1;
            int width = view.getWidth();
            if (a(view, f2)) {
                int left = view.getLeft();
                int i3 = this.a;
                i2 = left < i3 ? i3 - width : i3 + width;
                z = true;
            } else {
                i2 = this.a;
                z = false;
            }
            if (SwipeDismissBehavior.this.a.d(i2, view.getTop())) {
                v.a(view, (Runnable) new c(view, z));
            } else if (z && (bVar = SwipeDismissBehavior.this.b) != null) {
                bVar.a(view);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:15:0x0025 A[ORIG_RETURN, RETURN, SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:22:0x0034 A[ORIG_RETURN, RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean a(android.view.View r6, float r7) {
            /*
                r5 = this;
                r0 = 0
                r1 = 0
                r2 = 1
                int r3 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
                if (r3 == 0) goto L_0x0036
                int r6 = androidx.core.h.v.o(r6)
                if (r6 != r2) goto L_0x000f
                r6 = 1
                goto L_0x0010
            L_0x000f:
                r6 = 0
            L_0x0010:
                com.google.android.material.behavior.SwipeDismissBehavior r3 = com.google.android.material.behavior.SwipeDismissBehavior.this
                int r3 = r3.f1398f
                r4 = 2
                if (r3 != r4) goto L_0x0018
                return r2
            L_0x0018:
                if (r3 != 0) goto L_0x0027
                if (r6 == 0) goto L_0x0021
                int r6 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
                if (r6 >= 0) goto L_0x0026
                goto L_0x0025
            L_0x0021:
                int r6 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
                if (r6 <= 0) goto L_0x0026
            L_0x0025:
                r1 = 1
            L_0x0026:
                return r1
            L_0x0027:
                if (r3 != r2) goto L_0x0035
                if (r6 == 0) goto L_0x0030
                int r6 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
                if (r6 <= 0) goto L_0x0035
                goto L_0x0034
            L_0x0030:
                int r6 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
                if (r6 >= 0) goto L_0x0035
            L_0x0034:
                r1 = 1
            L_0x0035:
                return r1
            L_0x0036:
                int r7 = r6.getLeft()
                int r0 = r5.a
                int r7 = r7 - r0
                int r6 = r6.getWidth()
                float r6 = (float) r6
                com.google.android.material.behavior.SwipeDismissBehavior r0 = com.google.android.material.behavior.SwipeDismissBehavior.this
                float r0 = r0.f1399g
                float r6 = r6 * r0
                int r6 = java.lang.Math.round(r6)
                int r7 = java.lang.Math.abs(r7)
                if (r7 < r6) goto L_0x0053
                r1 = 1
            L_0x0053:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.behavior.SwipeDismissBehavior.a.a(android.view.View, float):boolean");
        }

        public int a(View view) {
            return view.getWidth();
        }

        public int a(View view, int i2, int i3) {
            int i4;
            int i5;
            int width;
            boolean z = v.o(view) == 1;
            int i6 = SwipeDismissBehavior.this.f1398f;
            if (i6 != 0) {
                if (i6 != 1) {
                    i4 = this.a - view.getWidth();
                    i5 = view.getWidth() + this.a;
                } else if (z) {
                    i4 = this.a;
                    width = view.getWidth();
                } else {
                    i4 = this.a - view.getWidth();
                    i5 = this.a;
                }
                return SwipeDismissBehavior.a(i4, i2, i5);
            } else if (z) {
                i4 = this.a - view.getWidth();
                i5 = this.a;
                return SwipeDismissBehavior.a(i4, i2, i5);
            } else {
                i4 = this.a;
                width = view.getWidth();
            }
            i5 = width + i4;
            return SwipeDismissBehavior.a(i4, i2, i5);
        }

        public void a(View view, int i2, int i3, int i4, int i5) {
            float width = ((float) this.a) + (((float) view.getWidth()) * SwipeDismissBehavior.this.f1400h);
            float width2 = ((float) this.a) + (((float) view.getWidth()) * SwipeDismissBehavior.this.f1401i);
            float f2 = (float) i2;
            if (f2 <= width) {
                view.setAlpha(1.0f);
            } else if (f2 >= width2) {
                view.setAlpha(0.0f);
            } else {
                view.setAlpha(SwipeDismissBehavior.a(0.0f, 1.0f - SwipeDismissBehavior.b(width, width2, f2), 1.0f));
            }
        }
    }

    public interface b {
        void a(int i2);

        void a(View view);
    }

    private class c implements Runnable {
        private final View e;

        /* renamed from: f  reason: collision with root package name */
        private final boolean f1403f;

        c(View view, boolean z) {
            this.e = view;
            this.f1403f = z;
        }

        public void run() {
            b bVar;
            androidx.customview.a.c cVar = SwipeDismissBehavior.this.a;
            if (cVar != null && cVar.a(true)) {
                v.a(this.e, (Runnable) this);
            } else if (this.f1403f && (bVar = SwipeDismissBehavior.this.b) != null) {
                bVar.a(this.e);
            }
        }
    }

    static float b(float f2, float f3, float f4) {
        return (f4 - f2) / (f3 - f2);
    }

    public void a(int i2) {
        this.f1398f = i2;
    }

    public boolean a(View view) {
        return true;
    }

    public void b(float f2) {
        this.f1400h = a(0.0f, f2, 1.0f);
    }

    public void setListener(b bVar) {
        this.b = bVar;
    }

    public void a(float f2) {
        this.f1401i = a(0.0f, f2, 1.0f);
    }

    public boolean b(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        androidx.customview.a.c cVar = this.a;
        if (cVar == null) {
            return false;
        }
        cVar.a(motionEvent);
        return true;
    }

    public boolean a(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        boolean z = this.c;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            z = coordinatorLayout.a((View) v, (int) motionEvent.getX(), (int) motionEvent.getY());
            this.c = z;
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.c = false;
        }
        if (!z) {
            return false;
        }
        a((ViewGroup) coordinatorLayout);
        return this.a.b(motionEvent);
    }

    private void a(ViewGroup viewGroup) {
        androidx.customview.a.c cVar;
        if (this.a == null) {
            if (this.e) {
                cVar = androidx.customview.a.c.a(viewGroup, this.d, this.f1402j);
            } else {
                cVar = androidx.customview.a.c.a(viewGroup, this.f1402j);
            }
            this.a = cVar;
        }
    }

    static float a(float f2, float f3, float f4) {
        return Math.min(Math.max(f2, f3), f4);
    }

    static int a(int i2, int i3, int i4) {
        return Math.min(Math.max(i2, i3), i4);
    }
}
