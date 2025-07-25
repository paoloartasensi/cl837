package com.google.android.material.internal;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.transition.l;
import androidx.transition.r;
import java.util.Map;

/* compiled from: TextScale */
public class j extends l {

    /* compiled from: TextScale */
    class a implements ValueAnimator.AnimatorUpdateListener {
        final /* synthetic */ TextView a;

        a(j jVar, TextView textView) {
            this.a = textView;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            this.a.setScaleX(floatValue);
            this.a.setScaleY(floatValue);
        }
    }

    private void d(r rVar) {
        View view = rVar.b;
        if (view instanceof TextView) {
            rVar.a.put("android:textscale:scale", Float.valueOf(((TextView) view).getScaleX()));
        }
    }

    public void a(r rVar) {
        d(rVar);
    }

    public void c(r rVar) {
        d(rVar);
    }

    public Animator a(ViewGroup viewGroup, r rVar, r rVar2) {
        if (rVar == null || rVar2 == null || !(rVar.b instanceof TextView)) {
            return null;
        }
        View view = rVar2.b;
        if (!(view instanceof TextView)) {
            return null;
        }
        TextView textView = (TextView) view;
        Map<String, Object> map = rVar.a;
        Map<String, Object> map2 = rVar2.a;
        float f2 = 1.0f;
        float floatValue = map.get("android:textscale:scale") != null ? ((Float) map.get("android:textscale:scale")).floatValue() : 1.0f;
        if (map2.get("android:textscale:scale") != null) {
            f2 = ((Float) map2.get("android:textscale:scale")).floatValue();
        }
        if (floatValue == f2) {
            return null;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{floatValue, f2});
        ofFloat.addUpdateListener(new a(this, textView));
        return ofFloat;
    }
}
