package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.core.h.v;
import com.google.android.material.R$animator;
import com.google.android.material.R$color;
import com.google.android.material.internal.VisibilityAwareImageButton;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: FloatingActionButtonImpl */
class a {
    static final TimeInterpolator B = com.google.android.material.a.a.c;
    static final int[] C = {16842919, 16842910};
    static final int[] D = {16843623, 16842908, 16842910};
    static final int[] E = {16842908, 16842910};
    static final int[] F = {16843623, 16842910};
    static final int[] G = {16842910};
    static final int[] H = new int[0];
    private ViewTreeObserver.OnPreDrawListener A;
    int a = 0;
    Animator b;
    com.google.android.material.a.h c;
    com.google.android.material.a.h d;
    private com.google.android.material.a.h e;

    /* renamed from: f  reason: collision with root package name */
    private com.google.android.material.a.h f1476f;

    /* renamed from: g  reason: collision with root package name */
    private final com.google.android.material.internal.i f1477g;

    /* renamed from: h  reason: collision with root package name */
    com.google.android.material.h.a f1478h;

    /* renamed from: i  reason: collision with root package name */
    private float f1479i;

    /* renamed from: j  reason: collision with root package name */
    Drawable f1480j;
    Drawable k;
    com.google.android.material.internal.a l;
    Drawable m;
    float n;
    float o;
    float p;
    int q;
    float r = 1.0f;
    private ArrayList<Animator.AnimatorListener> s;
    private ArrayList<Animator.AnimatorListener> t;
    final VisibilityAwareImageButton u;
    final com.google.android.material.h.b v;
    private final Rect w = new Rect();
    private final RectF x = new RectF();
    private final RectF y = new RectF();
    private final Matrix z = new Matrix();

    /* renamed from: com.google.android.material.floatingactionbutton.a$a  reason: collision with other inner class name */
    /* compiled from: FloatingActionButtonImpl */
    class C0079a extends AnimatorListenerAdapter {
        private boolean a;
        final /* synthetic */ boolean b;
        final /* synthetic */ g c;

        C0079a(boolean z, g gVar) {
            this.b = z;
            this.c = gVar;
        }

        public void onAnimationCancel(Animator animator) {
            this.a = true;
        }

        public void onAnimationEnd(Animator animator) {
            a aVar = a.this;
            aVar.a = 0;
            aVar.b = null;
            if (!this.a) {
                aVar.u.a(this.b ? 8 : 4, this.b);
                g gVar = this.c;
                if (gVar != null) {
                    gVar.b();
                }
            }
        }

        public void onAnimationStart(Animator animator) {
            a.this.u.a(0, this.b);
            a aVar = a.this;
            aVar.a = 1;
            aVar.b = animator;
            this.a = false;
        }
    }

    /* compiled from: FloatingActionButtonImpl */
    class b extends AnimatorListenerAdapter {
        final /* synthetic */ boolean a;
        final /* synthetic */ g b;

        b(boolean z, g gVar) {
            this.a = z;
            this.b = gVar;
        }

        public void onAnimationEnd(Animator animator) {
            a aVar = a.this;
            aVar.a = 0;
            aVar.b = null;
            g gVar = this.b;
            if (gVar != null) {
                gVar.a();
            }
        }

        public void onAnimationStart(Animator animator) {
            a.this.u.a(0, this.a);
            a aVar = a.this;
            aVar.a = 2;
            aVar.b = animator;
        }
    }

    /* compiled from: FloatingActionButtonImpl */
    class c implements ViewTreeObserver.OnPreDrawListener {
        c() {
        }

        public boolean onPreDraw() {
            a.this.p();
            return true;
        }
    }

    /* compiled from: FloatingActionButtonImpl */
    private class d extends i {
        d(a aVar) {
            super(aVar, (C0079a) null);
        }

        /* access modifiers changed from: protected */
        public float a() {
            return 0.0f;
        }
    }

    /* compiled from: FloatingActionButtonImpl */
    private class e extends i {
        e() {
            super(a.this, (C0079a) null);
        }

        /* access modifiers changed from: protected */
        public float a() {
            a aVar = a.this;
            return aVar.n + aVar.o;
        }
    }

    /* compiled from: FloatingActionButtonImpl */
    private class f extends i {
        f() {
            super(a.this, (C0079a) null);
        }

        /* access modifiers changed from: protected */
        public float a() {
            a aVar = a.this;
            return aVar.n + aVar.p;
        }
    }

    /* compiled from: FloatingActionButtonImpl */
    interface g {
        void a();

        void b();
    }

    /* compiled from: FloatingActionButtonImpl */
    private class h extends i {
        h() {
            super(a.this, (C0079a) null);
        }

        /* access modifiers changed from: protected */
        public float a() {
            return a.this.n;
        }
    }

    /* compiled from: FloatingActionButtonImpl */
    private abstract class i extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener {
        private boolean a;
        private float b;
        private float c;

        private i() {
        }

        /* access modifiers changed from: protected */
        public abstract float a();

        public void onAnimationEnd(Animator animator) {
            a.this.f1478h.b(this.c);
            this.a = false;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            if (!this.a) {
                this.b = a.this.f1478h.b();
                this.c = a();
                this.a = true;
            }
            com.google.android.material.h.a aVar = a.this.f1478h;
            float f2 = this.b;
            aVar.b(f2 + ((this.c - f2) * valueAnimator.getAnimatedFraction()));
        }

        /* synthetic */ i(a aVar, C0079a aVar2) {
            this();
        }
    }

    a(VisibilityAwareImageButton visibilityAwareImageButton, com.google.android.material.h.b bVar) {
        this.u = visibilityAwareImageButton;
        this.v = bVar;
        com.google.android.material.internal.i iVar = new com.google.android.material.internal.i();
        this.f1477g = iVar;
        iVar.a(C, a((i) new f()));
        this.f1477g.a(D, a((i) new e()));
        this.f1477g.a(E, a((i) new e()));
        this.f1477g.a(F, a((i) new e()));
        this.f1477g.a(G, a((i) new h()));
        this.f1477g.a(H, a((i) new d(this)));
        this.f1479i = this.u.getRotation();
    }

    private void t() {
        if (this.A == null) {
            this.A = new c();
        }
    }

    private com.google.android.material.a.h u() {
        if (this.f1476f == null) {
            this.f1476f = com.google.android.material.a.h.a(this.u.getContext(), R$animator.design_fab_hide_motion_spec);
        }
        return this.f1476f;
    }

    private com.google.android.material.a.h v() {
        if (this.e == null) {
            this.e = com.google.android.material.a.h.a(this.u.getContext(), R$animator.design_fab_show_motion_spec);
        }
        return this.e;
    }

    private boolean w() {
        return v.D(this.u) && !this.u.isInEditMode();
    }

    private void x() {
        if (Build.VERSION.SDK_INT == 19) {
            if (this.f1479i % 90.0f != 0.0f) {
                if (this.u.getLayerType() != 1) {
                    this.u.setLayerType(1, (Paint) null);
                }
            } else if (this.u.getLayerType() != 0) {
                this.u.setLayerType(0, (Paint) null);
            }
        }
        com.google.android.material.h.a aVar = this.f1478h;
        if (aVar != null) {
            aVar.a(-this.f1479i);
        }
        com.google.android.material.internal.a aVar2 = this.l;
        if (aVar2 != null) {
            aVar2.b(-this.f1479i);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(ColorStateList colorStateList, PorterDuff.Mode mode, ColorStateList colorStateList2, int i2) {
        Drawable[] drawableArr;
        Drawable i3 = androidx.core.graphics.drawable.a.i(a());
        this.f1480j = i3;
        androidx.core.graphics.drawable.a.a(i3, colorStateList);
        if (mode != null) {
            androidx.core.graphics.drawable.a.a(this.f1480j, mode);
        }
        Drawable i4 = androidx.core.graphics.drawable.a.i(a());
        this.k = i4;
        androidx.core.graphics.drawable.a.a(i4, com.google.android.material.g.a.a(colorStateList2));
        if (i2 > 0) {
            com.google.android.material.internal.a a2 = a(i2, colorStateList);
            this.l = a2;
            drawableArr = new Drawable[]{a2, this.f1480j, this.k};
        } else {
            this.l = null;
            drawableArr = new Drawable[]{this.f1480j, this.k};
        }
        this.m = new LayerDrawable(drawableArr);
        Context context = this.u.getContext();
        Drawable drawable = this.m;
        float a3 = this.v.a();
        float f2 = this.n;
        com.google.android.material.h.a aVar = new com.google.android.material.h.a(context, drawable, a3, f2, f2 + this.p);
        this.f1478h = aVar;
        aVar.a(false);
        this.v.a(this.f1478h);
    }

    /* access modifiers changed from: package-private */
    public void b(ColorStateList colorStateList) {
        Drawable drawable = this.k;
        if (drawable != null) {
            androidx.core.graphics.drawable.a.a(drawable, com.google.android.material.g.a.a(colorStateList));
        }
    }

    /* access modifiers changed from: package-private */
    public void b(Rect rect) {
    }

    /* access modifiers changed from: package-private */
    public float c() {
        return this.n;
    }

    /* access modifiers changed from: package-private */
    public final void d(float f2) {
        if (this.p != f2) {
            this.p = f2;
            a(this.n, this.o, f2);
        }
    }

    /* access modifiers changed from: package-private */
    public float e() {
        return this.o;
    }

    /* access modifiers changed from: package-private */
    public float f() {
        return this.p;
    }

    /* access modifiers changed from: package-private */
    public final com.google.android.material.a.h g() {
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public boolean h() {
        if (this.u.getVisibility() == 0) {
            if (this.a == 1) {
                return true;
            }
            return false;
        } else if (this.a != 2) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        if (this.u.getVisibility() != 0) {
            if (this.a == 2) {
                return true;
            }
            return false;
        } else if (this.a != 1) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void j() {
        this.f1477g.a();
    }

    /* access modifiers changed from: package-private */
    public com.google.android.material.internal.a k() {
        return new com.google.android.material.internal.a();
    }

    /* access modifiers changed from: package-private */
    public GradientDrawable l() {
        return new GradientDrawable();
    }

    /* access modifiers changed from: package-private */
    public void m() {
        if (q()) {
            t();
            this.u.getViewTreeObserver().addOnPreDrawListener(this.A);
        }
    }

    /* access modifiers changed from: package-private */
    public void n() {
    }

    /* access modifiers changed from: package-private */
    public void o() {
        if (this.A != null) {
            this.u.getViewTreeObserver().removeOnPreDrawListener(this.A);
            this.A = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void p() {
        float rotation = this.u.getRotation();
        if (this.f1479i != rotation) {
            this.f1479i = rotation;
            x();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean q() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public final void r() {
        c(this.r);
    }

    /* access modifiers changed from: package-private */
    public final void s() {
        Rect rect = this.w;
        a(rect);
        b(rect);
        this.v.a(rect.left, rect.top, rect.right, rect.bottom);
    }

    /* access modifiers changed from: package-private */
    public final void c(float f2) {
        this.r = f2;
        Matrix matrix = this.z;
        a(f2, matrix);
        this.u.setImageMatrix(matrix);
    }

    /* access modifiers changed from: package-private */
    public final void b(float f2) {
        if (this.o != f2) {
            this.o = f2;
            a(this.n, f2, this.p);
        }
    }

    /* access modifiers changed from: package-private */
    public final com.google.android.material.a.h d() {
        return this.d;
    }

    /* access modifiers changed from: package-private */
    public void d(Animator.AnimatorListener animatorListener) {
        ArrayList<Animator.AnimatorListener> arrayList = this.s;
        if (arrayList != null) {
            arrayList.remove(animatorListener);
        }
    }

    public void c(Animator.AnimatorListener animatorListener) {
        ArrayList<Animator.AnimatorListener> arrayList = this.t;
        if (arrayList != null) {
            arrayList.remove(animatorListener);
        }
    }

    /* access modifiers changed from: package-private */
    public final void b(com.google.android.material.a.h hVar) {
        this.c = hVar;
    }

    /* access modifiers changed from: package-private */
    public void b(Animator.AnimatorListener animatorListener) {
        if (this.s == null) {
            this.s = new ArrayList<>();
        }
        this.s.add(animatorListener);
    }

    /* access modifiers changed from: package-private */
    public void b(g gVar, boolean z2) {
        if (!i()) {
            Animator animator = this.b;
            if (animator != null) {
                animator.cancel();
            }
            if (w()) {
                if (this.u.getVisibility() != 0) {
                    this.u.setAlpha(0.0f);
                    this.u.setScaleY(0.0f);
                    this.u.setScaleX(0.0f);
                    c(0.0f);
                }
                com.google.android.material.a.h hVar = this.c;
                if (hVar == null) {
                    hVar = v();
                }
                AnimatorSet a2 = a(hVar, 1.0f, 1.0f, 1.0f);
                a2.addListener(new b(z2, gVar));
                ArrayList<Animator.AnimatorListener> arrayList = this.s;
                if (arrayList != null) {
                    Iterator<Animator.AnimatorListener> it = arrayList.iterator();
                    while (it.hasNext()) {
                        a2.addListener(it.next());
                    }
                }
                a2.start();
                return;
            }
            this.u.a(0, z2);
            this.u.setAlpha(1.0f);
            this.u.setScaleY(1.0f);
            this.u.setScaleX(1.0f);
            c(1.0f);
            if (gVar != null) {
                gVar.a();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(ColorStateList colorStateList) {
        Drawable drawable = this.f1480j;
        if (drawable != null) {
            androidx.core.graphics.drawable.a.a(drawable, colorStateList);
        }
        com.google.android.material.internal.a aVar = this.l;
        if (aVar != null) {
            aVar.a(colorStateList);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(PorterDuff.Mode mode) {
        Drawable drawable = this.f1480j;
        if (drawable != null) {
            androidx.core.graphics.drawable.a.a(drawable, mode);
        }
    }

    /* access modifiers changed from: package-private */
    public final void a(float f2) {
        if (this.n != f2) {
            this.n = f2;
            a(f2, this.o, this.p);
        }
    }

    /* access modifiers changed from: package-private */
    public final void a(int i2) {
        if (this.q != i2) {
            this.q = i2;
            r();
        }
    }

    private void a(float f2, Matrix matrix) {
        matrix.reset();
        Drawable drawable = this.u.getDrawable();
        if (drawable != null && this.q != 0) {
            RectF rectF = this.x;
            RectF rectF2 = this.y;
            rectF.set(0.0f, 0.0f, (float) drawable.getIntrinsicWidth(), (float) drawable.getIntrinsicHeight());
            int i2 = this.q;
            rectF2.set(0.0f, 0.0f, (float) i2, (float) i2);
            matrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.CENTER);
            int i3 = this.q;
            matrix.postScale(f2, f2, ((float) i3) / 2.0f, ((float) i3) / 2.0f);
        }
    }

    /* access modifiers changed from: package-private */
    public final Drawable b() {
        return this.m;
    }

    /* access modifiers changed from: package-private */
    public final void a(com.google.android.material.a.h hVar) {
        this.d = hVar;
    }

    /* access modifiers changed from: package-private */
    public void a(float f2, float f3, float f4) {
        com.google.android.material.h.a aVar = this.f1478h;
        if (aVar != null) {
            aVar.a(f2, this.p + f2);
            s();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int[] iArr) {
        this.f1477g.a(iArr);
    }

    public void a(Animator.AnimatorListener animatorListener) {
        if (this.t == null) {
            this.t = new ArrayList<>();
        }
        this.t.add(animatorListener);
    }

    /* access modifiers changed from: package-private */
    public void a(g gVar, boolean z2) {
        if (!h()) {
            Animator animator = this.b;
            if (animator != null) {
                animator.cancel();
            }
            if (w()) {
                com.google.android.material.a.h hVar = this.d;
                if (hVar == null) {
                    hVar = u();
                }
                AnimatorSet a2 = a(hVar, 0.0f, 0.0f, 0.0f);
                a2.addListener(new C0079a(z2, gVar));
                ArrayList<Animator.AnimatorListener> arrayList = this.t;
                if (arrayList != null) {
                    Iterator<Animator.AnimatorListener> it = arrayList.iterator();
                    while (it.hasNext()) {
                        a2.addListener(it.next());
                    }
                }
                a2.start();
                return;
            }
            this.u.a(z2 ? 8 : 4, z2);
            if (gVar != null) {
                gVar.b();
            }
        }
    }

    private AnimatorSet a(com.google.android.material.a.h hVar, float f2, float f3, float f4) {
        ArrayList arrayList = new ArrayList();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.u, View.ALPHA, new float[]{f2});
        hVar.a("opacity").a((Animator) ofFloat);
        arrayList.add(ofFloat);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.u, View.SCALE_X, new float[]{f3});
        hVar.a("scale").a((Animator) ofFloat2);
        arrayList.add(ofFloat2);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.u, View.SCALE_Y, new float[]{f3});
        hVar.a("scale").a((Animator) ofFloat3);
        arrayList.add(ofFloat3);
        a(f4, this.z);
        ObjectAnimator ofObject = ObjectAnimator.ofObject(this.u, new com.google.android.material.a.f(), new com.google.android.material.a.g(), new Matrix[]{new Matrix(this.z)});
        hVar.a("iconScale").a((Animator) ofObject);
        arrayList.add(ofObject);
        AnimatorSet animatorSet = new AnimatorSet();
        com.google.android.material.a.b.a(animatorSet, arrayList);
        return animatorSet;
    }

    /* access modifiers changed from: package-private */
    public void a(Rect rect) {
        this.f1478h.getPadding(rect);
    }

    /* access modifiers changed from: package-private */
    public com.google.android.material.internal.a a(int i2, ColorStateList colorStateList) {
        Context context = this.u.getContext();
        com.google.android.material.internal.a k2 = k();
        k2.a(androidx.core.content.a.a(context, R$color.design_fab_stroke_top_outer_color), androidx.core.content.a.a(context, R$color.design_fab_stroke_top_inner_color), androidx.core.content.a.a(context, R$color.design_fab_stroke_end_inner_color), androidx.core.content.a.a(context, R$color.design_fab_stroke_end_outer_color));
        k2.a((float) i2);
        k2.a(colorStateList);
        return k2;
    }

    /* access modifiers changed from: package-private */
    public GradientDrawable a() {
        GradientDrawable l2 = l();
        l2.setShape(1);
        l2.setColor(-1);
        return l2;
    }

    private ValueAnimator a(i iVar) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(B);
        valueAnimator.setDuration(100);
        valueAnimator.addListener(iVar);
        valueAnimator.addUpdateListener(iVar);
        valueAnimator.setFloatValues(new float[]{0.0f, 1.0f});
        return valueAnimator;
    }
}
