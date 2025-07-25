package com.google.android.material.bottomappbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.h.v;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R$attr;
import com.google.android.material.R$dimen;
import com.google.android.material.R$style;
import com.google.android.material.R$styleable;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.k;
import java.util.ArrayList;
import java.util.List;

public class BottomAppBar extends Toolbar implements CoordinatorLayout.b {
    private final int T;
    /* access modifiers changed from: private */
    public final com.google.android.material.i.c U;
    /* access modifiers changed from: private */
    public final a V;
    /* access modifiers changed from: private */
    public Animator W;
    /* access modifiers changed from: private */
    public Animator a0;
    /* access modifiers changed from: private */
    public Animator b0;
    /* access modifiers changed from: private */
    public int c0;
    private boolean d0;
    /* access modifiers changed from: private */
    public boolean e0;
    AnimatorListenerAdapter f0;

    static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* renamed from: g  reason: collision with root package name */
        int f1405g;

        /* renamed from: h  reason: collision with root package name */
        boolean f1406h;

        static class a implements Parcelable.ClassLoaderCreator<SavedState> {
            a() {
            }

            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.f1405g);
            parcel.writeInt(this.f1406h ? 1 : 0);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f1405g = parcel.readInt();
            this.f1406h = parcel.readInt() != 0;
        }
    }

    class a extends AnimatorListenerAdapter {
        a() {
        }

        public void onAnimationEnd(Animator animator) {
            Animator unused = BottomAppBar.this.a0 = null;
        }
    }

    class b implements ValueAnimator.AnimatorUpdateListener {
        b() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            BottomAppBar.this.V.e(((Float) valueAnimator.getAnimatedValue()).floatValue());
            BottomAppBar.this.U.invalidateSelf();
        }
    }

    class c extends AnimatorListenerAdapter {
        c() {
        }

        public void onAnimationEnd(Animator animator) {
            Animator unused = BottomAppBar.this.b0 = null;
        }
    }

    class d extends AnimatorListenerAdapter {
        public boolean a;
        final /* synthetic */ ActionMenuView b;
        final /* synthetic */ int c;
        final /* synthetic */ boolean d;

        d(ActionMenuView actionMenuView, int i2, boolean z) {
            this.b = actionMenuView;
            this.c = i2;
            this.d = z;
        }

        public void onAnimationCancel(Animator animator) {
            this.a = true;
        }

        public void onAnimationEnd(Animator animator) {
            if (!this.a) {
                BottomAppBar.this.a(this.b, this.c, this.d);
            }
        }
    }

    class e extends AnimatorListenerAdapter {
        e() {
        }

        public void onAnimationEnd(Animator animator) {
            Animator unused = BottomAppBar.this.W = null;
        }
    }

    class f implements ValueAnimator.AnimatorUpdateListener {
        f() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            BottomAppBar.this.U.a(((Float) valueAnimator.getAnimatedValue()).floatValue());
        }
    }

    class g extends AnimatorListenerAdapter {
        g() {
        }

        public void onAnimationStart(Animator animator) {
            BottomAppBar bottomAppBar = BottomAppBar.this;
            bottomAppBar.b(bottomAppBar.e0);
            BottomAppBar bottomAppBar2 = BottomAppBar.this;
            bottomAppBar2.a(bottomAppBar2.c0, BottomAppBar.this.e0);
        }
    }

    public BottomAppBar(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    private ActionMenuView getActionMenuView() {
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (childAt instanceof ActionMenuView) {
                return (ActionMenuView) childAt;
            }
        }
        return null;
    }

    private float getFabTranslationX() {
        return (float) b(this.c0);
    }

    /* access modifiers changed from: private */
    public float getFabTranslationY() {
        return a(this.e0);
    }

    private void l() {
        Animator animator = this.W;
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = this.b0;
        if (animator2 != null) {
            animator2.cancel();
        }
        Animator animator3 = this.a0;
        if (animator3 != null) {
            animator3.cancel();
        }
    }

    /* access modifiers changed from: private */
    public FloatingActionButton m() {
        if (!(getParent() instanceof CoordinatorLayout)) {
            return null;
        }
        for (View next : ((CoordinatorLayout) getParent()).c((View) this)) {
            if (next instanceof FloatingActionButton) {
                return (FloatingActionButton) next;
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000a, code lost:
        r0 = r1.b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0014, code lost:
        r0 = r1.a0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean n() {
        /*
            r1 = this;
            android.animation.Animator r0 = r1.W
            if (r0 == 0) goto L_0x000a
            boolean r0 = r0.isRunning()
            if (r0 != 0) goto L_0x001e
        L_0x000a:
            android.animation.Animator r0 = r1.b0
            if (r0 == 0) goto L_0x0014
            boolean r0 = r0.isRunning()
            if (r0 != 0) goto L_0x001e
        L_0x0014:
            android.animation.Animator r0 = r1.a0
            if (r0 == 0) goto L_0x0020
            boolean r0 = r0.isRunning()
            if (r0 == 0) goto L_0x0020
        L_0x001e:
            r0 = 1
            goto L_0x0021
        L_0x0020:
            r0 = 0
        L_0x0021:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomappbar.BottomAppBar.n():boolean");
    }

    private boolean o() {
        FloatingActionButton m = m();
        return m != null && m.a();
    }

    /* access modifiers changed from: private */
    public void p() {
        this.V.e(getFabTranslationX());
        FloatingActionButton m = m();
        this.U.a((!this.e0 || !o()) ? 0.0f : 1.0f);
        if (m != null) {
            m.setTranslationY(getFabTranslationY());
            m.setTranslationX(getFabTranslationX());
        }
        ActionMenuView actionMenuView = getActionMenuView();
        if (actionMenuView != null) {
            actionMenuView.setAlpha(1.0f);
            if (!o()) {
                a(actionMenuView, 0, false);
            } else {
                a(actionMenuView, this.c0, this.e0);
            }
        }
    }

    public ColorStateList getBackgroundTint() {
        return this.U.b();
    }

    public CoordinatorLayout.c<BottomAppBar> getBehavior() {
        return new Behavior();
    }

    public float getCradleVerticalOffset() {
        return this.V.a();
    }

    public int getFabAlignmentMode() {
        return this.c0;
    }

    public float getFabCradleMargin() {
        return this.V.b();
    }

    public float getFabCradleRoundedCornerRadius() {
        return this.V.c();
    }

    public boolean getHideOnScroll() {
        return this.d0;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        super.onLayout(z, i2, i3, i4, i5);
        l();
        p();
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.a());
        this.c0 = savedState.f1405g;
        this.e0 = savedState.f1406h;
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.f1405g = this.c0;
        savedState.f1406h = this.e0;
        return savedState;
    }

    public void setBackgroundTint(ColorStateList colorStateList) {
        androidx.core.graphics.drawable.a.a((Drawable) this.U, colorStateList);
    }

    public void setCradleVerticalOffset(float f2) {
        if (f2 != getCradleVerticalOffset()) {
            this.V.a(f2);
            this.U.invalidateSelf();
        }
    }

    public void setFabAlignmentMode(int i2) {
        c(i2);
        a(i2, this.e0);
        this.c0 = i2;
    }

    public void setFabCradleMargin(float f2) {
        if (f2 != getFabCradleMargin()) {
            this.V.b(f2);
            this.U.invalidateSelf();
        }
    }

    public void setFabCradleRoundedCornerRadius(float f2) {
        if (f2 != getFabCradleRoundedCornerRadius()) {
            this.V.c(f2);
            this.U.invalidateSelf();
        }
    }

    /* access modifiers changed from: package-private */
    public void setFabDiameter(int i2) {
        float f2 = (float) i2;
        if (f2 != this.V.d()) {
            this.V.d(f2);
            this.U.invalidateSelf();
        }
    }

    public void setHideOnScroll(boolean z) {
        this.d0 = z;
    }

    public void setSubtitle(CharSequence charSequence) {
    }

    public void setTitle(CharSequence charSequence) {
    }

    public static class Behavior extends HideBottomViewOnScrollBehavior<BottomAppBar> {
        private final Rect d = new Rect();

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        private boolean a(FloatingActionButton floatingActionButton, BottomAppBar bottomAppBar) {
            ((CoordinatorLayout.f) floatingActionButton.getLayoutParams()).d = 17;
            bottomAppBar.a(floatingActionButton);
            return true;
        }

        /* access modifiers changed from: protected */
        public void b(BottomAppBar bottomAppBar) {
            super.b(bottomAppBar);
            FloatingActionButton b = bottomAppBar.m();
            if (b != null) {
                b.clearAnimation();
                b.animate().translationY(bottomAppBar.getFabTranslationY()).setInterpolator(com.google.android.material.a.a.d).setDuration(225);
            }
        }

        public boolean a(CoordinatorLayout coordinatorLayout, BottomAppBar bottomAppBar, int i2) {
            FloatingActionButton b = bottomAppBar.m();
            if (b != null) {
                a(b, bottomAppBar);
                b.b(this.d);
                bottomAppBar.setFabDiameter(this.d.height());
            }
            if (!bottomAppBar.n()) {
                bottomAppBar.p();
            }
            coordinatorLayout.c((View) bottomAppBar, i2);
            return super.a(coordinatorLayout, bottomAppBar, i2);
        }

        /* renamed from: a */
        public boolean b(CoordinatorLayout coordinatorLayout, BottomAppBar bottomAppBar, View view, View view2, int i2, int i3) {
            return bottomAppBar.getHideOnScroll() && super.b(coordinatorLayout, bottomAppBar, view, view2, i2, i3);
        }

        /* access modifiers changed from: protected */
        public void a(BottomAppBar bottomAppBar) {
            super.a(bottomAppBar);
            FloatingActionButton b = bottomAppBar.m();
            if (b != null) {
                b.a(this.d);
                b.clearAnimation();
                b.animate().translationY(((float) (-b.getPaddingBottom())) + ((float) (b.getMeasuredHeight() - this.d.height()))).setInterpolator(com.google.android.material.a.a.c).setDuration(175);
            }
        }
    }

    public BottomAppBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.bottomAppBarStyle);
    }

    public BottomAppBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.e0 = true;
        this.f0 = new g();
        TypedArray c2 = k.c(context, attributeSet, R$styleable.BottomAppBar, i2, R$style.Widget_MaterialComponents_BottomAppBar, new int[0]);
        ColorStateList a2 = com.google.android.material.f.a.a(context, c2, R$styleable.BottomAppBar_backgroundTint);
        this.c0 = c2.getInt(R$styleable.BottomAppBar_fabAlignmentMode, 0);
        this.d0 = c2.getBoolean(R$styleable.BottomAppBar_hideOnScroll, false);
        c2.recycle();
        this.T = getResources().getDimensionPixelOffset(R$dimen.mtrl_bottomappbar_fabOffsetEndMode);
        this.V = new a((float) c2.getDimensionPixelOffset(R$styleable.BottomAppBar_fabCradleMargin, 0), (float) c2.getDimensionPixelOffset(R$styleable.BottomAppBar_fabCradleRoundedCornerRadius, 0), (float) c2.getDimensionPixelOffset(R$styleable.BottomAppBar_fabCradleVerticalOffset, 0));
        com.google.android.material.i.e eVar = new com.google.android.material.i.e();
        eVar.a(this.V);
        com.google.android.material.i.c cVar = new com.google.android.material.i.c(eVar);
        this.U = cVar;
        cVar.a(true);
        this.U.a(Paint.Style.FILL);
        androidx.core.graphics.drawable.a.a((Drawable) this.U, a2);
        v.a((View) this, (Drawable) this.U);
    }

    private void b(int i2, List<Animator> list) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(m(), "translationX", new float[]{(float) b(i2)});
        ofFloat.setDuration(300);
        list.add(ofFloat);
    }

    private void c(int i2) {
        if (this.c0 != i2 && v.D(this)) {
            Animator animator = this.a0;
            if (animator != null) {
                animator.cancel();
            }
            ArrayList arrayList = new ArrayList();
            a(i2, (List<Animator>) arrayList);
            b(i2, (List<Animator>) arrayList);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(arrayList);
            this.a0 = animatorSet;
            animatorSet.addListener(new a());
            this.a0.start();
        }
    }

    /* access modifiers changed from: private */
    public void b(boolean z) {
        if (v.D(this)) {
            Animator animator = this.W;
            if (animator != null) {
                animator.cancel();
            }
            ArrayList arrayList = new ArrayList();
            a(z && o(), (List<Animator>) arrayList);
            b(z, (List<Animator>) arrayList);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(arrayList);
            this.W = animatorSet;
            animatorSet.addListener(new e());
            this.W.start();
        }
    }

    private void a(int i2, List<Animator> list) {
        if (this.e0) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.V.e(), (float) b(i2)});
            ofFloat.addUpdateListener(new b());
            ofFloat.setDuration(300);
            list.add(ofFloat);
        }
    }

    /* access modifiers changed from: private */
    public void a(int i2, boolean z) {
        if (v.D(this)) {
            Animator animator = this.b0;
            if (animator != null) {
                animator.cancel();
            }
            ArrayList arrayList = new ArrayList();
            if (!o()) {
                i2 = 0;
                z = false;
            }
            a(i2, z, (List<Animator>) arrayList);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(arrayList);
            this.b0 = animatorSet;
            animatorSet.addListener(new c());
            this.b0.start();
        }
    }

    private void b(boolean z, List<Animator> list) {
        FloatingActionButton m = m();
        if (m != null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(m, "translationY", new float[]{a(z)});
            ofFloat.setDuration(300);
            list.add(ofFloat);
        }
    }

    private int b(int i2) {
        int i3 = 1;
        boolean z = v.o(this) == 1;
        if (i2 != 1) {
            return 0;
        }
        int measuredWidth = (getMeasuredWidth() / 2) - this.T;
        if (z) {
            i3 = -1;
        }
        return measuredWidth * i3;
    }

    private void b(FloatingActionButton floatingActionButton) {
        floatingActionButton.c((Animator.AnimatorListener) this.f0);
        floatingActionButton.d(this.f0);
    }

    private void a(int i2, boolean z, List<Animator> list) {
        ActionMenuView actionMenuView = getActionMenuView();
        if (actionMenuView != null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(actionMenuView, "alpha", new float[]{1.0f});
            if ((this.e0 || (z && o())) && (this.c0 == 1 || i2 == 1)) {
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(actionMenuView, "alpha", new float[]{0.0f});
                ofFloat2.addListener(new d(actionMenuView, i2, z));
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(150);
                animatorSet.playSequentially(new Animator[]{ofFloat2, ofFloat});
                list.add(animatorSet);
            } else if (actionMenuView.getAlpha() < 1.0f) {
                list.add(ofFloat);
            }
        }
    }

    private void a(boolean z, List<Animator> list) {
        if (z) {
            this.V.e(getFabTranslationX());
        }
        float[] fArr = new float[2];
        fArr[0] = this.U.a();
        fArr[1] = z ? 1.0f : 0.0f;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
        ofFloat.addUpdateListener(new f());
        ofFloat.setDuration(300);
        list.add(ofFloat);
    }

    private float a(boolean z) {
        FloatingActionButton m = m();
        if (m == null) {
            return 0.0f;
        }
        Rect rect = new Rect();
        m.a(rect);
        float height = (float) rect.height();
        if (height == 0.0f) {
            height = (float) m.getMeasuredHeight();
        }
        float height2 = (float) (m.getHeight() - rect.height());
        float height3 = (-getCradleVerticalOffset()) + (height / 2.0f) + ((float) (m.getHeight() - rect.bottom));
        float paddingBottom = height2 - ((float) m.getPaddingBottom());
        float f2 = (float) (-getMeasuredHeight());
        if (!z) {
            height3 = paddingBottom;
        }
        return f2 + height3;
    }

    /* access modifiers changed from: private */
    public void a(ActionMenuView actionMenuView, int i2, boolean z) {
        boolean z2 = v.o(this) == 1;
        int i3 = 0;
        for (int i4 = 0; i4 < getChildCount(); i4++) {
            View childAt = getChildAt(i4);
            if ((childAt.getLayoutParams() instanceof Toolbar.e) && (((Toolbar.e) childAt.getLayoutParams()).a & 8388615) == 8388611) {
                i3 = Math.max(i3, z2 ? childAt.getLeft() : childAt.getRight());
            }
        }
        actionMenuView.setTranslationX((i2 != 1 || !z) ? 0.0f : (float) (i3 - (z2 ? actionMenuView.getRight() : actionMenuView.getLeft())));
    }

    /* access modifiers changed from: private */
    public void a(FloatingActionButton floatingActionButton) {
        b(floatingActionButton);
        floatingActionButton.a((Animator.AnimatorListener) this.f0);
        floatingActionButton.b((Animator.AnimatorListener) this.f0);
    }
}
