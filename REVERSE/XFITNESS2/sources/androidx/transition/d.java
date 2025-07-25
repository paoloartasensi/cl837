package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.h.v;
import androidx.transition.l;

/* compiled from: Fade */
public class d extends h0 {

    /* compiled from: Fade */
    class a extends m {
        final /* synthetic */ View a;

        a(d dVar, View view) {
            this.a = view;
        }

        public void d(l lVar) {
            c0.a(this.a, 1.0f);
            c0.a(this.a);
            lVar.b((l.f) this);
        }
    }

    /* compiled from: Fade */
    private static class b extends AnimatorListenerAdapter {
        private final View a;
        private boolean b = false;

        b(View view) {
            this.a = view;
        }

        public void onAnimationEnd(Animator animator) {
            c0.a(this.a, 1.0f);
            if (this.b) {
                this.a.setLayerType(0, (Paint) null);
            }
        }

        public void onAnimationStart(Animator animator) {
            if (v.z(this.a) && this.a.getLayerType() == 0) {
                this.b = true;
                this.a.setLayerType(2, (Paint) null);
            }
        }
    }

    public d(int i2) {
        a(i2);
    }

    private Animator a(View view, float f2, float f3) {
        if (f2 == f3) {
            return null;
        }
        c0.a(view, f2);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, c0.d, new float[]{f3});
        ofFloat.addListener(new b(view));
        a((l.f) new a(this, view));
        return ofFloat;
    }

    public Animator b(ViewGroup viewGroup, View view, r rVar, r rVar2) {
        c0.e(view);
        return a(view, a(rVar, 1.0f), 0.0f);
    }

    public void c(r rVar) {
        super.c(rVar);
        rVar.a.put("android:fade:transitionAlpha", Float.valueOf(c0.c(rVar.b)));
    }

    public Animator a(ViewGroup viewGroup, View view, r rVar, r rVar2) {
        float f2 = 0.0f;
        float a2 = a(rVar, 0.0f);
        if (a2 != 1.0f) {
            f2 = a2;
        }
        return a(view, f2, 1.0f);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:1:0x0002, code lost:
        r1 = (java.lang.Float) r1.a.get("android:fade:transitionAlpha");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static float a(androidx.transition.r r1, float r2) {
        /*
            if (r1 == 0) goto L_0x0012
            java.util.Map<java.lang.String, java.lang.Object> r1 = r1.a
            java.lang.String r0 = "android:fade:transitionAlpha"
            java.lang.Object r1 = r1.get(r0)
            java.lang.Float r1 = (java.lang.Float) r1
            if (r1 == 0) goto L_0x0012
            float r2 = r1.floatValue()
        L_0x0012:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.transition.d.a(androidx.transition.r, float):float");
    }
}
