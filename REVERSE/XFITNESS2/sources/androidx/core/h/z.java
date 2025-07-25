package androidx.core.h;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;

/* compiled from: ViewPropertyAnimatorCompat */
public final class z {
    private WeakReference<View> a;
    Runnable b = null;
    Runnable c = null;
    int d = -1;

    /* compiled from: ViewPropertyAnimatorCompat */
    class a extends AnimatorListenerAdapter {
        final /* synthetic */ a0 a;
        final /* synthetic */ View b;

        a(z zVar, a0 a0Var, View view) {
            this.a = a0Var;
            this.b = view;
        }

        public void onAnimationCancel(Animator animator) {
            this.a.c(this.b);
        }

        public void onAnimationEnd(Animator animator) {
            this.a.a(this.b);
        }

        public void onAnimationStart(Animator animator) {
            this.a.b(this.b);
        }
    }

    /* compiled from: ViewPropertyAnimatorCompat */
    class b implements ValueAnimator.AnimatorUpdateListener {
        final /* synthetic */ c0 a;
        final /* synthetic */ View b;

        b(z zVar, c0 c0Var, View view) {
            this.a = c0Var;
            this.b = view;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.a.a(this.b);
        }
    }

    /* compiled from: ViewPropertyAnimatorCompat */
    static class c implements a0 {
        z a;
        boolean b;

        c(z zVar) {
            this.a = zVar;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: androidx.core.h.a0} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(android.view.View r4) {
            /*
                r3 = this;
                androidx.core.h.z r0 = r3.a
                int r0 = r0.d
                r1 = -1
                r2 = 0
                if (r0 <= r1) goto L_0x000f
                r4.setLayerType(r0, r2)
                androidx.core.h.z r0 = r3.a
                r0.d = r1
            L_0x000f:
                int r0 = android.os.Build.VERSION.SDK_INT
                r1 = 16
                if (r0 >= r1) goto L_0x0019
                boolean r0 = r3.b
                if (r0 != 0) goto L_0x0039
            L_0x0019:
                androidx.core.h.z r0 = r3.a
                java.lang.Runnable r1 = r0.c
                if (r1 == 0) goto L_0x0024
                r0.c = r2
                r1.run()
            L_0x0024:
                r0 = 2113929216(0x7e000000, float:4.2535296E37)
                java.lang.Object r0 = r4.getTag(r0)
                boolean r1 = r0 instanceof androidx.core.h.a0
                if (r1 == 0) goto L_0x0031
                r2 = r0
                androidx.core.h.a0 r2 = (androidx.core.h.a0) r2
            L_0x0031:
                if (r2 == 0) goto L_0x0036
                r2.a(r4)
            L_0x0036:
                r4 = 1
                r3.b = r4
            L_0x0039:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.h.z.c.a(android.view.View):void");
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: androidx.core.h.a0} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void b(android.view.View r4) {
            /*
                r3 = this;
                r0 = 0
                r3.b = r0
                androidx.core.h.z r0 = r3.a
                int r0 = r0.d
                r1 = 0
                r2 = -1
                if (r0 <= r2) goto L_0x000f
                r0 = 2
                r4.setLayerType(r0, r1)
            L_0x000f:
                androidx.core.h.z r0 = r3.a
                java.lang.Runnable r2 = r0.b
                if (r2 == 0) goto L_0x001a
                r0.b = r1
                r2.run()
            L_0x001a:
                r0 = 2113929216(0x7e000000, float:4.2535296E37)
                java.lang.Object r0 = r4.getTag(r0)
                boolean r2 = r0 instanceof androidx.core.h.a0
                if (r2 == 0) goto L_0x0027
                r1 = r0
                androidx.core.h.a0 r1 = (androidx.core.h.a0) r1
            L_0x0027:
                if (r1 == 0) goto L_0x002c
                r1.b(r4)
            L_0x002c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.h.z.c.b(android.view.View):void");
        }

        public void c(View view) {
            Object tag = view.getTag(2113929216);
            a0 a0Var = tag instanceof a0 ? (a0) tag : null;
            if (a0Var != null) {
                a0Var.c(view);
            }
        }
    }

    z(View view) {
        this.a = new WeakReference<>(view);
    }

    public z a(long j2) {
        View view = (View) this.a.get();
        if (view != null) {
            view.animate().setDuration(j2);
        }
        return this;
    }

    public z b(float f2) {
        View view = (View) this.a.get();
        if (view != null) {
            view.animate().translationY(f2);
        }
        return this;
    }

    public void c() {
        View view = (View) this.a.get();
        if (view != null) {
            view.animate().start();
        }
    }

    public z a(float f2) {
        View view = (View) this.a.get();
        if (view != null) {
            view.animate().alpha(f2);
        }
        return this;
    }

    public long b() {
        View view = (View) this.a.get();
        if (view != null) {
            return view.animate().getDuration();
        }
        return 0;
    }

    public z a(Interpolator interpolator) {
        View view = (View) this.a.get();
        if (view != null) {
            view.animate().setInterpolator(interpolator);
        }
        return this;
    }

    public z b(long j2) {
        View view = (View) this.a.get();
        if (view != null) {
            view.animate().setStartDelay(j2);
        }
        return this;
    }

    public void a() {
        View view = (View) this.a.get();
        if (view != null) {
            view.animate().cancel();
        }
    }

    public z a(a0 a0Var) {
        View view = (View) this.a.get();
        if (view != null) {
            if (Build.VERSION.SDK_INT >= 16) {
                a(view, a0Var);
            } else {
                view.setTag(2113929216, a0Var);
                a(view, new c(this));
            }
        }
        return this;
    }

    private void a(View view, a0 a0Var) {
        if (a0Var != null) {
            view.animate().setListener(new a(this, a0Var, view));
        } else {
            view.animate().setListener((Animator.AnimatorListener) null);
        }
    }

    public z a(c0 c0Var) {
        View view = (View) this.a.get();
        if (view != null && Build.VERSION.SDK_INT >= 19) {
            b bVar = null;
            if (c0Var != null) {
                bVar = new b(this, c0Var, view);
            }
            view.animate().setUpdateListener(bVar);
        }
        return this;
    }
}
