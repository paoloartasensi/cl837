package com.google.android.material.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class HideBottomViewOnScrollBehavior<V extends View> extends CoordinatorLayout.c<V> {
    private int a = 0;
    private int b = 2;
    /* access modifiers changed from: private */
    public ViewPropertyAnimator c;

    class a extends AnimatorListenerAdapter {
        a() {
        }

        public void onAnimationEnd(Animator animator) {
            ViewPropertyAnimator unused = HideBottomViewOnScrollBehavior.this.c = null;
        }
    }

    public HideBottomViewOnScrollBehavior() {
    }

    /* access modifiers changed from: protected */
    public void b(V v) {
        ViewPropertyAnimator viewPropertyAnimator = this.c;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
            v.clearAnimation();
        }
        this.b = 2;
        a(v, 0, 225, com.google.android.material.a.a.d);
    }

    public boolean b(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i2) {
        return i2 == 2;
    }

    public boolean a(CoordinatorLayout coordinatorLayout, V v, int i2) {
        this.a = v.getMeasuredHeight();
        return super.a(coordinatorLayout, v, i2);
    }

    public HideBottomViewOnScrollBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void a(CoordinatorLayout coordinatorLayout, V v, View view, int i2, int i3, int i4, int i5) {
        if (this.b != 1 && i3 > 0) {
            a(v);
        } else if (this.b != 2 && i3 < 0) {
            b(v);
        }
    }

    /* access modifiers changed from: protected */
    public void a(V v) {
        ViewPropertyAnimator viewPropertyAnimator = this.c;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
            v.clearAnimation();
        }
        this.b = 1;
        a(v, this.a, 175, com.google.android.material.a.a.c);
    }

    private void a(V v, int i2, long j2, TimeInterpolator timeInterpolator) {
        this.c = v.animate().translationY((float) i2).setInterpolator(timeInterpolator).setDuration(j2).setListener(new a());
    }
}
