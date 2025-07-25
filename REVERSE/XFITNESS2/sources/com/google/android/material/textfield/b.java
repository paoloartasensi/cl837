package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.h.v;
import androidx.core.widget.i;
import androidx.legacy.widget.Space;
import com.google.android.material.R$dimen;
import com.google.android.material.R$id;
import java.util.ArrayList;
import java.util.List;

/* compiled from: IndicatorViewController */
final class b {
    private final Context a;
    private final TextInputLayout b;
    private LinearLayout c;
    private int d;
    private FrameLayout e;

    /* renamed from: f  reason: collision with root package name */
    private int f1562f;
    /* access modifiers changed from: private */

    /* renamed from: g  reason: collision with root package name */
    public Animator f1563g;

    /* renamed from: h  reason: collision with root package name */
    private final float f1564h;
    /* access modifiers changed from: private */

    /* renamed from: i  reason: collision with root package name */
    public int f1565i;

    /* renamed from: j  reason: collision with root package name */
    private int f1566j;
    private CharSequence k;
    private boolean l;
    /* access modifiers changed from: private */
    public TextView m;
    private int n;
    private CharSequence o;
    private boolean p;
    private TextView q;
    private int r;
    private Typeface s;

    /* compiled from: IndicatorViewController */
    class a extends AnimatorListenerAdapter {
        final /* synthetic */ int a;
        final /* synthetic */ TextView b;
        final /* synthetic */ int c;
        final /* synthetic */ TextView d;

        a(int i2, TextView textView, int i3, TextView textView2) {
            this.a = i2;
            this.b = textView;
            this.c = i3;
            this.d = textView2;
        }

        public void onAnimationEnd(Animator animator) {
            int unused = b.this.f1565i = this.a;
            Animator unused2 = b.this.f1563g = null;
            TextView textView = this.b;
            if (textView != null) {
                textView.setVisibility(4);
                if (this.c == 1 && b.this.m != null) {
                    b.this.m.setText((CharSequence) null);
                }
            }
        }

        public void onAnimationStart(Animator animator) {
            TextView textView = this.d;
            if (textView != null) {
                textView.setVisibility(0);
            }
        }
    }

    public b(TextInputLayout textInputLayout) {
        Context context = textInputLayout.getContext();
        this.a = context;
        this.b = textInputLayout;
        this.f1564h = (float) context.getResources().getDimensionPixelSize(R$dimen.design_textinput_caption_translate_y);
    }

    private TextView d(int i2) {
        if (i2 == 1) {
            return this.m;
        }
        if (i2 != 2) {
            return null;
        }
        return this.q;
    }

    private boolean e(int i2) {
        if (i2 != 1 || this.m == null || TextUtils.isEmpty(this.k)) {
            return false;
        }
        return true;
    }

    private boolean m() {
        return (this.c == null || this.b.getEditText() == null) ? false : true;
    }

    /* access modifiers changed from: package-private */
    public boolean a(int i2) {
        return i2 == 0 || i2 == 1;
    }

    /* access modifiers changed from: package-private */
    public void b(CharSequence charSequence) {
        b();
        this.o = charSequence;
        this.q.setText(charSequence);
        if (this.f1565i != 2) {
            this.f1566j = 2;
        }
        a(this.f1565i, this.f1566j, a(this.q, charSequence));
    }

    /* access modifiers changed from: package-private */
    public boolean c() {
        return e(this.f1566j);
    }

    /* access modifiers changed from: package-private */
    public ColorStateList f() {
        TextView textView = this.m;
        if (textView != null) {
            return textView.getTextColors();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public CharSequence g() {
        return this.o;
    }

    /* access modifiers changed from: package-private */
    public int h() {
        TextView textView = this.q;
        if (textView != null) {
            return textView.getCurrentTextColor();
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public void i() {
        this.k = null;
        b();
        if (this.f1565i == 1) {
            if (!this.p || TextUtils.isEmpty(this.o)) {
                this.f1566j = 0;
            } else {
                this.f1566j = 2;
            }
        }
        a(this.f1565i, this.f1566j, a(this.m, (CharSequence) null));
    }

    /* access modifiers changed from: package-private */
    public void j() {
        b();
        if (this.f1565i == 2) {
            this.f1566j = 0;
        }
        a(this.f1565i, this.f1566j, a(this.q, (CharSequence) null));
    }

    /* access modifiers changed from: package-private */
    public boolean k() {
        return this.l;
    }

    /* access modifiers changed from: package-private */
    public boolean l() {
        return this.p;
    }

    /* access modifiers changed from: package-private */
    public void c(int i2) {
        this.r = i2;
        TextView textView = this.q;
        if (textView != null) {
            i.d(textView, i2);
        }
    }

    /* access modifiers changed from: package-private */
    public CharSequence d() {
        return this.k;
    }

    /* access modifiers changed from: package-private */
    public int e() {
        TextView textView = this.m;
        if (textView != null) {
            return textView.getCurrentTextColor();
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public void a(CharSequence charSequence) {
        b();
        this.k = charSequence;
        this.m.setText(charSequence);
        if (this.f1565i != 1) {
            this.f1566j = 1;
        }
        a(this.f1565i, this.f1566j, a(this.m, charSequence));
    }

    /* access modifiers changed from: package-private */
    public void b() {
        Animator animator = this.f1563g;
        if (animator != null) {
            animator.cancel();
        }
    }

    /* access modifiers changed from: package-private */
    public void b(TextView textView, int i2) {
        FrameLayout frameLayout;
        if (this.c != null) {
            if (!a(i2) || (frameLayout = this.e) == null) {
                this.c.removeView(textView);
            } else {
                int i3 = this.f1562f - 1;
                this.f1562f = i3;
                a((ViewGroup) frameLayout, i3);
                this.e.removeView(textView);
            }
            int i4 = this.d - 1;
            this.d = i4;
            a((ViewGroup) this.c, i4);
        }
    }

    private boolean a(TextView textView, CharSequence charSequence) {
        return v.D(this.b) && this.b.isEnabled() && (this.f1566j != this.f1565i || textView == null || !TextUtils.equals(textView.getText(), charSequence));
    }

    private void a(int i2, int i3, boolean z) {
        if (z) {
            AnimatorSet animatorSet = new AnimatorSet();
            this.f1563g = animatorSet;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = arrayList;
            int i4 = i2;
            int i5 = i3;
            a(arrayList2, this.p, this.q, 2, i4, i5);
            a(arrayList2, this.l, this.m, 1, i4, i5);
            com.google.android.material.a.b.a(animatorSet, arrayList);
            animatorSet.addListener(new a(i3, d(i2), i2, d(i3)));
            animatorSet.start();
        } else {
            a(i2, i3);
        }
        this.b.c();
        this.b.b(z);
        this.b.d();
    }

    /* access modifiers changed from: package-private */
    public void b(boolean z) {
        if (this.p != z) {
            b();
            if (z) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(this.a);
                this.q = appCompatTextView;
                appCompatTextView.setId(R$id.textinput_helper_text);
                Typeface typeface = this.s;
                if (typeface != null) {
                    this.q.setTypeface(typeface);
                }
                this.q.setVisibility(4);
                v.g(this.q, 1);
                c(this.r);
                a(this.q, 1);
            } else {
                j();
                b(this.q, 1);
                this.q = null;
                this.b.c();
                this.b.d();
            }
            this.p = z;
        }
    }

    private void a(int i2, int i3) {
        TextView d2;
        TextView d3;
        if (i2 != i3) {
            if (!(i3 == 0 || (d3 = d(i3)) == null)) {
                d3.setVisibility(0);
                d3.setAlpha(1.0f);
            }
            if (!(i2 == 0 || (d2 = d(i2)) == null)) {
                d2.setVisibility(4);
                if (i2 == 1) {
                    d2.setText((CharSequence) null);
                }
            }
            this.f1565i = i3;
        }
    }

    /* access modifiers changed from: package-private */
    public void b(int i2) {
        this.n = i2;
        TextView textView = this.m;
        if (textView != null) {
            this.b.a(textView, i2);
        }
    }

    private void a(List<Animator> list, boolean z, TextView textView, int i2, int i3, int i4) {
        if (textView != null && z) {
            if (i2 == i4 || i2 == i3) {
                list.add(a(textView, i4 == i2));
                if (i4 == i2) {
                    list.add(a(textView));
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void b(ColorStateList colorStateList) {
        TextView textView = this.q;
        if (textView != null) {
            textView.setTextColor(colorStateList);
        }
    }

    private ObjectAnimator a(TextView textView, boolean z) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(textView, View.ALPHA, new float[]{z ? 1.0f : 0.0f});
        ofFloat.setDuration(167);
        ofFloat.setInterpolator(com.google.android.material.a.a.a);
        return ofFloat;
    }

    private ObjectAnimator a(TextView textView) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(textView, View.TRANSLATION_Y, new float[]{-this.f1564h, 0.0f});
        ofFloat.setDuration(217);
        ofFloat.setInterpolator(com.google.android.material.a.a.d);
        return ofFloat;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        if (m()) {
            v.b(this.c, v.t(this.b.getEditText()), 0, v.s(this.b.getEditText()), 0);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(TextView textView, int i2) {
        if (this.c == null && this.e == null) {
            LinearLayout linearLayout = new LinearLayout(this.a);
            this.c = linearLayout;
            linearLayout.setOrientation(0);
            this.b.addView(this.c, -1, -2);
            FrameLayout frameLayout = new FrameLayout(this.a);
            this.e = frameLayout;
            this.c.addView(frameLayout, -1, new FrameLayout.LayoutParams(-2, -2));
            this.c.addView(new Space(this.a), new LinearLayout.LayoutParams(0, 0, 1.0f));
            if (this.b.getEditText() != null) {
                a();
            }
        }
        if (a(i2)) {
            this.e.setVisibility(0);
            this.e.addView(textView);
            this.f1562f++;
        } else {
            this.c.addView(textView, i2);
        }
        this.c.setVisibility(0);
        this.d++;
    }

    private void a(ViewGroup viewGroup, int i2) {
        if (i2 == 0) {
            viewGroup.setVisibility(8);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(boolean z) {
        if (this.l != z) {
            b();
            if (z) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(this.a);
                this.m = appCompatTextView;
                appCompatTextView.setId(R$id.textinput_error);
                Typeface typeface = this.s;
                if (typeface != null) {
                    this.m.setTypeface(typeface);
                }
                b(this.n);
                this.m.setVisibility(4);
                v.g(this.m, 1);
                a(this.m, 0);
            } else {
                i();
                b(this.m, 0);
                this.m = null;
                this.b.c();
                this.b.d();
            }
            this.l = z;
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Typeface typeface) {
        if (typeface != this.s) {
            this.s = typeface;
            a(this.m, typeface);
            a(this.q, typeface);
        }
    }

    private void a(TextView textView, Typeface typeface) {
        if (textView != null) {
            textView.setTypeface(typeface);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(ColorStateList colorStateList) {
        TextView textView = this.m;
        if (textView != null) {
            textView.setTextColor(colorStateList);
        }
    }
}
