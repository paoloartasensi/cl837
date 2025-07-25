package com.google.android.material.circularreveal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import com.google.android.material.circularreveal.c;

/* compiled from: CircularRevealCompat */
public final class a {

    /* renamed from: com.google.android.material.circularreveal.a$a  reason: collision with other inner class name */
    /* compiled from: CircularRevealCompat */
    static class C0077a extends AnimatorListenerAdapter {
        final /* synthetic */ c a;

        C0077a(c cVar) {
            this.a = cVar;
        }

        public void onAnimationEnd(Animator animator) {
            this.a.b();
        }

        public void onAnimationStart(Animator animator) {
            this.a.a();
        }
    }

    public static Animator a(c cVar, float f2, float f3, float f4) {
        ObjectAnimator ofObject = ObjectAnimator.ofObject(cVar, c.C0078c.a, c.b.b, new c.e[]{new c.e(f2, f3, f4)});
        if (Build.VERSION.SDK_INT < 21) {
            return ofObject;
        }
        c.e revealInfo = cVar.getRevealInfo();
        if (revealInfo != null) {
            Animator createCircularReveal = ViewAnimationUtils.createCircularReveal((View) cVar, (int) f2, (int) f3, revealInfo.c, f4);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(new Animator[]{ofObject, createCircularReveal});
            return animatorSet;
        }
        throw new IllegalStateException("Caller must set a non-null RevealInfo before calling this.");
    }

    public static Animator.AnimatorListener a(c cVar) {
        return new C0077a(cVar);
    }
}
