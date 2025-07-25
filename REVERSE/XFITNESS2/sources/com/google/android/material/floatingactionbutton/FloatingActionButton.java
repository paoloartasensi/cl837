package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.widget.f;
import androidx.appcompat.widget.h;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.h.u;
import androidx.core.h.v;
import androidx.core.widget.l;
import com.google.android.material.R$attr;
import com.google.android.material.R$dimen;
import com.google.android.material.R$style;
import com.google.android.material.R$styleable;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.a;
import com.google.android.material.internal.VisibilityAwareImageButton;
import com.google.android.material.internal.d;
import com.google.android.material.internal.k;
import com.google.android.material.stateful.ExtendableSavedState;
import java.util.List;

@CoordinatorLayout.d(Behavior.class)
public class FloatingActionButton extends VisibilityAwareImageButton implements u, l, com.google.android.material.d.a {

    /* renamed from: f  reason: collision with root package name */
    private ColorStateList f1471f;

    /* renamed from: g  reason: collision with root package name */
    private PorterDuff.Mode f1472g;

    /* renamed from: h  reason: collision with root package name */
    private ColorStateList f1473h;

    /* renamed from: i  reason: collision with root package name */
    private PorterDuff.Mode f1474i;

    /* renamed from: j  reason: collision with root package name */
    private int f1475j;
    private ColorStateList k;
    private int l;
    private int m;
    /* access modifiers changed from: private */
    public int n;
    private int o;
    boolean p;
    final Rect q;
    private final Rect r;
    private final h s;
    private final com.google.android.material.d.c t;
    private a u;

    protected static class BaseBehavior<T extends FloatingActionButton> extends CoordinatorLayout.c<T> {
        private Rect a;
        private b b;
        private boolean c;

        public BaseBehavior() {
            this.c = true;
        }

        public void setInternalAutoHideListener(b bVar) {
            this.b = bVar;
        }

        private boolean b(View view, FloatingActionButton floatingActionButton) {
            if (!a(view, floatingActionButton)) {
                return false;
            }
            if (view.getTop() < (floatingActionButton.getHeight() / 2) + ((CoordinatorLayout.f) floatingActionButton.getLayoutParams()).topMargin) {
                floatingActionButton.a(this.b, false);
                return true;
            }
            floatingActionButton.b(this.b, false);
            return true;
        }

        public BaseBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.FloatingActionButton_Behavior_Layout);
            this.c = obtainStyledAttributes.getBoolean(R$styleable.FloatingActionButton_Behavior_Layout_behavior_autoHide, true);
            obtainStyledAttributes.recycle();
        }

        public void a(CoordinatorLayout.f fVar) {
            if (fVar.f429h == 0) {
                fVar.f429h = 80;
            }
        }

        /* renamed from: a */
        public boolean b(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view) {
            if (view instanceof AppBarLayout) {
                a(coordinatorLayout, (AppBarLayout) view, floatingActionButton);
                return false;
            } else if (!a(view)) {
                return false;
            } else {
                b(view, floatingActionButton);
                return false;
            }
        }

        private static boolean a(View view) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof CoordinatorLayout.f) {
                return ((CoordinatorLayout.f) layoutParams).d() instanceof BottomSheetBehavior;
            }
            return false;
        }

        private boolean a(View view, FloatingActionButton floatingActionButton) {
            CoordinatorLayout.f fVar = (CoordinatorLayout.f) floatingActionButton.getLayoutParams();
            if (this.c && fVar.c() == view.getId() && floatingActionButton.getUserSetVisibility() == 0) {
                return true;
            }
            return false;
        }

        private boolean a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, FloatingActionButton floatingActionButton) {
            if (!a((View) appBarLayout, floatingActionButton)) {
                return false;
            }
            if (this.a == null) {
                this.a = new Rect();
            }
            Rect rect = this.a;
            d.a((ViewGroup) coordinatorLayout, (View) appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                floatingActionButton.a(this.b, false);
                return true;
            }
            floatingActionButton.b(this.b, false);
            return true;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, int i2) {
            List<View> b2 = coordinatorLayout.b((View) floatingActionButton);
            int size = b2.size();
            for (int i3 = 0; i3 < size; i3++) {
                View view = b2.get(i3);
                if (!(view instanceof AppBarLayout)) {
                    if (a(view) && b(view, floatingActionButton)) {
                        break;
                    }
                } else if (a(coordinatorLayout, (AppBarLayout) view, floatingActionButton)) {
                    break;
                }
            }
            coordinatorLayout.c((View) floatingActionButton, i2);
            a(coordinatorLayout, floatingActionButton);
            return true;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, Rect rect) {
            Rect rect2 = floatingActionButton.q;
            rect.set(floatingActionButton.getLeft() + rect2.left, floatingActionButton.getTop() + rect2.top, floatingActionButton.getRight() - rect2.right, floatingActionButton.getBottom() - rect2.bottom);
            return true;
        }

        private void a(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton) {
            int i2;
            Rect rect = floatingActionButton.q;
            if (rect != null && rect.centerX() > 0 && rect.centerY() > 0) {
                CoordinatorLayout.f fVar = (CoordinatorLayout.f) floatingActionButton.getLayoutParams();
                int i3 = 0;
                if (floatingActionButton.getRight() >= coordinatorLayout.getWidth() - fVar.rightMargin) {
                    i2 = rect.right;
                } else {
                    i2 = floatingActionButton.getLeft() <= fVar.leftMargin ? -rect.left : 0;
                }
                if (floatingActionButton.getBottom() >= coordinatorLayout.getHeight() - fVar.bottomMargin) {
                    i3 = rect.bottom;
                } else if (floatingActionButton.getTop() <= fVar.topMargin) {
                    i3 = -rect.top;
                }
                if (i3 != 0) {
                    v.e(floatingActionButton, i3);
                }
                if (i2 != 0) {
                    v.d(floatingActionButton, i2);
                }
            }
        }
    }

    public static class Behavior extends BaseBehavior<FloatingActionButton> {
        public Behavior() {
        }

        public /* bridge */ /* synthetic */ void setInternalAutoHideListener(b bVar) {
            super.setInternalAutoHideListener(bVar);
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    class a implements a.g {
        final /* synthetic */ b a;

        a(b bVar) {
            this.a = bVar;
        }

        public void a() {
            this.a.b(FloatingActionButton.this);
        }

        public void b() {
            this.a.a(FloatingActionButton.this);
        }
    }

    public static abstract class b {
        public abstract void a(FloatingActionButton floatingActionButton);

        public abstract void b(FloatingActionButton floatingActionButton);
    }

    private class c implements com.google.android.material.h.b {
        c() {
        }

        public float a() {
            return ((float) FloatingActionButton.this.getSizeDimension()) / 2.0f;
        }

        public boolean b() {
            return FloatingActionButton.this.p;
        }

        public void a(int i2, int i3, int i4, int i5) {
            FloatingActionButton.this.q.set(i2, i3, i4, i5);
            FloatingActionButton floatingActionButton = FloatingActionButton.this;
            floatingActionButton.setPadding(i2 + floatingActionButton.n, i3 + FloatingActionButton.this.n, i4 + FloatingActionButton.this.n, i5 + FloatingActionButton.this.n);
        }

        public void a(Drawable drawable) {
            FloatingActionButton.super.setBackgroundDrawable(drawable);
        }
    }

    public FloatingActionButton(Context context) {
        this(context, (AttributeSet) null);
    }

    private void c() {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            ColorStateList colorStateList = this.f1473h;
            if (colorStateList == null) {
                androidx.core.graphics.drawable.a.b(drawable);
                return;
            }
            int colorForState = colorStateList.getColorForState(getDrawableState(), 0);
            PorterDuff.Mode mode = this.f1474i;
            if (mode == null) {
                mode = PorterDuff.Mode.SRC_IN;
            }
            drawable.mutate().setColorFilter(f.a(colorForState, mode));
        }
    }

    private a getImpl() {
        if (this.u == null) {
            this.u = b();
        }
        return this.u;
    }

    /* access modifiers changed from: package-private */
    public void b(b bVar, boolean z) {
        getImpl().b(a(bVar), z);
    }

    public void d(Animator.AnimatorListener animatorListener) {
        getImpl().d(animatorListener);
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        getImpl().a(getDrawableState());
    }

    public ColorStateList getBackgroundTintList() {
        return this.f1471f;
    }

    public PorterDuff.Mode getBackgroundTintMode() {
        return this.f1472g;
    }

    public float getCompatElevation() {
        return getImpl().c();
    }

    public float getCompatHoveredFocusedTranslationZ() {
        return getImpl().e();
    }

    public float getCompatPressedTranslationZ() {
        return getImpl().f();
    }

    public Drawable getContentBackground() {
        return getImpl().b();
    }

    public int getCustomSize() {
        return this.m;
    }

    public int getExpandedComponentIdHint() {
        return this.t.a();
    }

    public com.google.android.material.a.h getHideMotionSpec() {
        return getImpl().d();
    }

    @Deprecated
    public int getRippleColor() {
        ColorStateList colorStateList = this.k;
        if (colorStateList != null) {
            return colorStateList.getDefaultColor();
        }
        return 0;
    }

    public ColorStateList getRippleColorStateList() {
        return this.k;
    }

    public com.google.android.material.a.h getShowMotionSpec() {
        return getImpl().g();
    }

    public int getSize() {
        return this.l;
    }

    /* access modifiers changed from: package-private */
    public int getSizeDimension() {
        return a(this.l);
    }

    public ColorStateList getSupportBackgroundTintList() {
        return getBackgroundTintList();
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        return getBackgroundTintMode();
    }

    public ColorStateList getSupportImageTintList() {
        return this.f1473h;
    }

    public PorterDuff.Mode getSupportImageTintMode() {
        return this.f1474i;
    }

    public boolean getUseCompatPadding() {
        return this.p;
    }

    public void hide(b bVar) {
        a(bVar, true);
    }

    public boolean isExpanded() {
        return this.t.b();
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        getImpl().j();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getImpl().m();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getImpl().o();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int sizeDimension = getSizeDimension();
        this.n = (sizeDimension - this.o) / 2;
        getImpl().s();
        int min = Math.min(a(sizeDimension, i2), a(sizeDimension, i3));
        Rect rect = this.q;
        setMeasuredDimension(rect.left + min + rect.right, min + rect.top + rect.bottom);
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof ExtendableSavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        ExtendableSavedState extendableSavedState = (ExtendableSavedState) parcelable;
        super.onRestoreInstanceState(extendableSavedState.a());
        this.t.a(extendableSavedState.f1535g.get("expandableWidgetHelper"));
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        ExtendableSavedState extendableSavedState = new ExtendableSavedState(super.onSaveInstanceState());
        extendableSavedState.f1535g.put("expandableWidgetHelper", this.t.c());
        return extendableSavedState;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0 || !a(this.r) || this.r.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }

    public void setBackgroundColor(int i2) {
        Log.i("FloatingActionButton", "Setting a custom background is not supported.");
    }

    public void setBackgroundDrawable(Drawable drawable) {
        Log.i("FloatingActionButton", "Setting a custom background is not supported.");
    }

    public void setBackgroundResource(int i2) {
        Log.i("FloatingActionButton", "Setting a custom background is not supported.");
    }

    public void setBackgroundTintList(ColorStateList colorStateList) {
        if (this.f1471f != colorStateList) {
            this.f1471f = colorStateList;
            getImpl().a(colorStateList);
        }
    }

    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.f1472g != mode) {
            this.f1472g = mode;
            getImpl().a(mode);
        }
    }

    public void setCompatElevation(float f2) {
        getImpl().a(f2);
    }

    public void setCompatElevationResource(int i2) {
        setCompatElevation(getResources().getDimension(i2));
    }

    public void setCompatHoveredFocusedTranslationZ(float f2) {
        getImpl().b(f2);
    }

    public void setCompatHoveredFocusedTranslationZResource(int i2) {
        setCompatHoveredFocusedTranslationZ(getResources().getDimension(i2));
    }

    public void setCompatPressedTranslationZ(float f2) {
        getImpl().d(f2);
    }

    public void setCompatPressedTranslationZResource(int i2) {
        setCompatPressedTranslationZ(getResources().getDimension(i2));
    }

    public void setCustomSize(int i2) {
        if (i2 >= 0) {
            this.m = i2;
            return;
        }
        throw new IllegalArgumentException("Custom size must be non-negative");
    }

    public void setExpandedComponentIdHint(int i2) {
        this.t.a(i2);
    }

    public void setHideMotionSpec(com.google.android.material.a.h hVar) {
        getImpl().a(hVar);
    }

    public void setHideMotionSpecResource(int i2) {
        setHideMotionSpec(com.google.android.material.a.h.a(getContext(), i2));
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        getImpl().r();
    }

    public void setImageResource(int i2) {
        this.s.a(i2);
    }

    public void setRippleColor(int i2) {
        setRippleColor(ColorStateList.valueOf(i2));
    }

    public void setShowMotionSpec(com.google.android.material.a.h hVar) {
        getImpl().b(hVar);
    }

    public void setShowMotionSpecResource(int i2) {
        setShowMotionSpec(com.google.android.material.a.h.a(getContext(), i2));
    }

    public void setSize(int i2) {
        this.m = 0;
        if (i2 != this.l) {
            this.l = i2;
            requestLayout();
        }
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        setBackgroundTintList(colorStateList);
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        setBackgroundTintMode(mode);
    }

    public void setSupportImageTintList(ColorStateList colorStateList) {
        if (this.f1473h != colorStateList) {
            this.f1473h = colorStateList;
            c();
        }
    }

    public void setSupportImageTintMode(PorterDuff.Mode mode) {
        if (this.f1474i != mode) {
            this.f1474i = mode;
            c();
        }
    }

    public void setUseCompatPadding(boolean z) {
        if (this.p != z) {
            this.p = z;
            getImpl().n();
        }
    }

    public void show(b bVar) {
        b(bVar, true);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.floatingActionButtonStyle);
    }

    public void b(Animator.AnimatorListener animatorListener) {
        getImpl().b(animatorListener);
    }

    public void setRippleColor(ColorStateList colorStateList) {
        if (this.k != colorStateList) {
            this.k = colorStateList;
            getImpl().b(this.k);
        }
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.q = new Rect();
        this.r = new Rect();
        TypedArray c2 = k.c(context, attributeSet, R$styleable.FloatingActionButton, i2, R$style.Widget_Design_FloatingActionButton, new int[0]);
        this.f1471f = com.google.android.material.f.a.a(context, c2, R$styleable.FloatingActionButton_backgroundTint);
        this.f1472g = com.google.android.material.internal.l.a(c2.getInt(R$styleable.FloatingActionButton_backgroundTintMode, -1), (PorterDuff.Mode) null);
        this.k = com.google.android.material.f.a.a(context, c2, R$styleable.FloatingActionButton_rippleColor);
        this.l = c2.getInt(R$styleable.FloatingActionButton_fabSize, -1);
        this.m = c2.getDimensionPixelSize(R$styleable.FloatingActionButton_fabCustomSize, 0);
        this.f1475j = c2.getDimensionPixelSize(R$styleable.FloatingActionButton_borderWidth, 0);
        float dimension = c2.getDimension(R$styleable.FloatingActionButton_elevation, 0.0f);
        float dimension2 = c2.getDimension(R$styleable.FloatingActionButton_hoveredFocusedTranslationZ, 0.0f);
        float dimension3 = c2.getDimension(R$styleable.FloatingActionButton_pressedTranslationZ, 0.0f);
        this.p = c2.getBoolean(R$styleable.FloatingActionButton_useCompatPadding, false);
        this.o = c2.getDimensionPixelSize(R$styleable.FloatingActionButton_maxImageSize, 0);
        com.google.android.material.a.h a2 = com.google.android.material.a.h.a(context, c2, R$styleable.FloatingActionButton_showMotionSpec);
        com.google.android.material.a.h a3 = com.google.android.material.a.h.a(context, c2, R$styleable.FloatingActionButton_hideMotionSpec);
        c2.recycle();
        h hVar = new h(this);
        this.s = hVar;
        hVar.a(attributeSet, i2);
        this.t = new com.google.android.material.d.c(this);
        getImpl().a(this.f1471f, this.f1472g, this.k, this.f1475j);
        getImpl().a(dimension);
        getImpl().b(dimension2);
        getImpl().d(dimension3);
        getImpl().a(this.o);
        getImpl().b(a2);
        getImpl().a(a3);
        setScaleType(ImageView.ScaleType.MATRIX);
    }

    /* access modifiers changed from: package-private */
    public void a(b bVar, boolean z) {
        getImpl().a(a(bVar), z);
    }

    public void b(Rect rect) {
        rect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
        c(rect);
    }

    public void a(Animator.AnimatorListener animatorListener) {
        getImpl().a(animatorListener);
    }

    private a.g a(b bVar) {
        if (bVar == null) {
            return null;
        }
        return new a(bVar);
    }

    private a b() {
        if (Build.VERSION.SDK_INT >= 21) {
            return new b(this, new c());
        }
        return new a(this, new c());
    }

    public boolean a() {
        return getImpl().i();
    }

    private int a(int i2) {
        int i3 = this.m;
        if (i3 != 0) {
            return i3;
        }
        Resources resources = getResources();
        if (i2 != -1) {
            if (i2 != 1) {
                return resources.getDimensionPixelSize(R$dimen.design_fab_size_normal);
            }
            return resources.getDimensionPixelSize(R$dimen.design_fab_size_mini);
        } else if (Math.max(resources.getConfiguration().screenWidthDp, resources.getConfiguration().screenHeightDp) < 470) {
            return a(1);
        } else {
            return a(0);
        }
    }

    public void c(Animator.AnimatorListener animatorListener) {
        getImpl().c(animatorListener);
    }

    private void c(Rect rect) {
        int i2 = rect.left;
        Rect rect2 = this.q;
        rect.left = i2 + rect2.left;
        rect.top += rect2.top;
        rect.right -= rect2.right;
        rect.bottom -= rect2.bottom;
    }

    @Deprecated
    public boolean a(Rect rect) {
        if (!v.D(this)) {
            return false;
        }
        rect.set(0, 0, getWidth(), getHeight());
        c(rect);
        return true;
    }

    private static int a(int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i3);
        int size = View.MeasureSpec.getSize(i3);
        if (mode == Integer.MIN_VALUE) {
            return Math.min(i2, size);
        }
        if (mode == 0) {
            return i2;
        }
        if (mode == 1073741824) {
            return size;
        }
        throw new IllegalArgumentException();
    }
}
