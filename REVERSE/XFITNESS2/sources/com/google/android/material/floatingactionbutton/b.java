package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.view.View;
import com.google.android.material.internal.VisibilityAwareImageButton;
import java.util.ArrayList;

/* compiled from: FloatingActionButtonImplLollipop */
class b extends a {
    private InsetDrawable I;

    /* compiled from: FloatingActionButtonImplLollipop */
    static class a extends GradientDrawable {
        a() {
        }

        public boolean isStateful() {
            return true;
        }
    }

    b(VisibilityAwareImageButton visibilityAwareImageButton, com.google.android.material.h.b bVar) {
        super(visibilityAwareImageButton, bVar);
    }

    /* access modifiers changed from: package-private */
    public void a(ColorStateList colorStateList, PorterDuff.Mode mode, ColorStateList colorStateList2, int i2) {
        Drawable drawable;
        Drawable i3 = androidx.core.graphics.drawable.a.i(a());
        this.f1480j = i3;
        androidx.core.graphics.drawable.a.a(i3, colorStateList);
        if (mode != null) {
            androidx.core.graphics.drawable.a.a(this.f1480j, mode);
        }
        if (i2 > 0) {
            this.l = a(i2, colorStateList);
            drawable = new LayerDrawable(new Drawable[]{this.l, this.f1480j});
        } else {
            this.l = null;
            drawable = this.f1480j;
        }
        RippleDrawable rippleDrawable = new RippleDrawable(com.google.android.material.g.a.a(colorStateList2), drawable, (Drawable) null);
        this.k = rippleDrawable;
        this.m = rippleDrawable;
        this.v.a(rippleDrawable);
    }

    /* access modifiers changed from: package-private */
    public void b(ColorStateList colorStateList) {
        Drawable drawable = this.k;
        if (drawable instanceof RippleDrawable) {
            ((RippleDrawable) drawable).setColor(com.google.android.material.g.a.a(colorStateList));
        } else {
            super.b(colorStateList);
        }
    }

    public float c() {
        return this.u.getElevation();
    }

    /* access modifiers changed from: package-private */
    public void j() {
    }

    /* access modifiers changed from: package-private */
    public com.google.android.material.internal.a k() {
        return new com.google.android.material.internal.b();
    }

    /* access modifiers changed from: package-private */
    public GradientDrawable l() {
        return new a();
    }

    /* access modifiers changed from: package-private */
    public void n() {
        s();
    }

    /* access modifiers changed from: package-private */
    public boolean q() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void b(Rect rect) {
        if (this.v.b()) {
            InsetDrawable insetDrawable = new InsetDrawable(this.k, rect.left, rect.top, rect.right, rect.bottom);
            this.I = insetDrawable;
            this.v.a(insetDrawable);
            return;
        }
        this.v.a(this.k);
    }

    /* access modifiers changed from: package-private */
    public void a(float f2, float f3, float f4) {
        if (Build.VERSION.SDK_INT == 21) {
            this.u.refreshDrawableState();
        } else {
            StateListAnimator stateListAnimator = new StateListAnimator();
            stateListAnimator.addState(a.C, a(f2, f4));
            stateListAnimator.addState(a.D, a(f2, f3));
            stateListAnimator.addState(a.E, a(f2, f3));
            stateListAnimator.addState(a.F, a(f2, f3));
            AnimatorSet animatorSet = new AnimatorSet();
            ArrayList arrayList = new ArrayList();
            arrayList.add(ObjectAnimator.ofFloat(this.u, "elevation", new float[]{f2}).setDuration(0));
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 22 && i2 <= 24) {
                VisibilityAwareImageButton visibilityAwareImageButton = this.u;
                arrayList.add(ObjectAnimator.ofFloat(visibilityAwareImageButton, View.TRANSLATION_Z, new float[]{visibilityAwareImageButton.getTranslationZ()}).setDuration(100));
            }
            arrayList.add(ObjectAnimator.ofFloat(this.u, View.TRANSLATION_Z, new float[]{0.0f}).setDuration(100));
            animatorSet.playSequentially((Animator[]) arrayList.toArray(new Animator[0]));
            animatorSet.setInterpolator(a.B);
            stateListAnimator.addState(a.G, animatorSet);
            stateListAnimator.addState(a.H, a(0.0f, 0.0f));
            this.u.setStateListAnimator(stateListAnimator);
        }
        if (this.v.b()) {
            s();
        }
    }

    private Animator a(float f2, float f3) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(ObjectAnimator.ofFloat(this.u, "elevation", new float[]{f2}).setDuration(0)).with(ObjectAnimator.ofFloat(this.u, View.TRANSLATION_Z, new float[]{f3}).setDuration(100));
        animatorSet.setInterpolator(a.B);
        return animatorSet;
    }

    /* access modifiers changed from: package-private */
    public void a(int[] iArr) {
        if (Build.VERSION.SDK_INT != 21) {
            return;
        }
        if (this.u.isEnabled()) {
            this.u.setElevation(this.n);
            if (this.u.isPressed()) {
                this.u.setTranslationZ(this.p);
            } else if (this.u.isFocused() || this.u.isHovered()) {
                this.u.setTranslationZ(this.o);
            } else {
                this.u.setTranslationZ(0.0f);
            }
        } else {
            this.u.setElevation(0.0f);
            this.u.setTranslationZ(0.0f);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Rect rect) {
        if (this.v.b()) {
            float a2 = this.v.a();
            float c = c() + this.p;
            int ceil = (int) Math.ceil((double) com.google.android.material.h.a.a(c, a2, false));
            int ceil2 = (int) Math.ceil((double) com.google.android.material.h.a.b(c, a2, false));
            rect.set(ceil, ceil2, ceil, ceil2);
            return;
        }
        rect.set(0, 0, 0, 0);
    }
}
