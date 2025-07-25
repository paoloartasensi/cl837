package com.google.android.material.chip;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.content.c.f;
import androidx.core.h.e0.d;
import androidx.core.h.v;
import com.google.android.material.R$attr;
import com.google.android.material.R$string;
import com.google.android.material.R$style;
import com.google.android.material.a.h;
import com.google.android.material.chip.a;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Chip extends AppCompatCheckBox implements a.b {
    /* access modifiers changed from: private */
    public static final Rect u = new Rect();
    private static final int[] v = {16842913};
    /* access modifiers changed from: private */

    /* renamed from: h  reason: collision with root package name */
    public a f1449h;

    /* renamed from: i  reason: collision with root package name */
    private RippleDrawable f1450i;

    /* renamed from: j  reason: collision with root package name */
    private View.OnClickListener f1451j;
    private CompoundButton.OnCheckedChangeListener k;
    private boolean l;
    private int m;
    private boolean n;
    private boolean o;
    private boolean p;
    private final c q;
    private final Rect r;
    private final RectF s;
    private final f.a t;

    class a extends f.a {
        a() {
        }

        public void a(int i2) {
        }

        public void a(Typeface typeface) {
            Chip chip = Chip.this;
            chip.setText(chip.getText());
            Chip.this.requestLayout();
            Chip.this.invalidate();
        }
    }

    class b extends ViewOutlineProvider {
        b() {
        }

        @TargetApi(21)
        public void getOutline(View view, Outline outline) {
            if (Chip.this.f1449h != null) {
                Chip.this.f1449h.getOutline(outline);
            } else {
                outline.setAlpha(0.0f);
            }
        }
    }

    private class c extends androidx.customview.a.a {
        c(Chip chip) {
            super(chip);
        }

        /* access modifiers changed from: protected */
        public int a(float f2, float f3) {
            return (!Chip.this.f() || !Chip.this.getCloseIconTouchBounds().contains(f2, f3)) ? -1 : 0;
        }

        /* access modifiers changed from: protected */
        public void a(List<Integer> list) {
            if (Chip.this.f()) {
                list.add(0);
            }
        }

        /* access modifiers changed from: protected */
        public void a(int i2, d dVar) {
            boolean b = Chip.this.f();
            CharSequence charSequence = BuildConfig.FLAVOR;
            if (b) {
                CharSequence closeIconContentDescription = Chip.this.getCloseIconContentDescription();
                if (closeIconContentDescription != null) {
                    dVar.b(closeIconContentDescription);
                } else {
                    CharSequence text = Chip.this.getText();
                    Context context = Chip.this.getContext();
                    int i3 = R$string.mtrl_chip_close_icon_content_description;
                    Object[] objArr = new Object[1];
                    if (!TextUtils.isEmpty(text)) {
                        charSequence = text;
                    }
                    objArr[0] = charSequence;
                    dVar.b((CharSequence) context.getString(i3, objArr).trim());
                }
                dVar.c(Chip.this.getCloseIconTouchBoundsInt());
                dVar.a(d.a.f513g);
                dVar.h(Chip.this.isEnabled());
                return;
            }
            dVar.b(charSequence);
            dVar.c(Chip.u);
        }

        /* access modifiers changed from: protected */
        public void a(d dVar) {
            dVar.c(Chip.this.f1449h != null && Chip.this.f1449h.D());
            dVar.a((CharSequence) Chip.class.getName());
            CharSequence text = Chip.this.getText();
            if (Build.VERSION.SDK_INT >= 23) {
                dVar.g(text);
            } else {
                dVar.b(text);
            }
        }

        /* access modifiers changed from: protected */
        public boolean a(int i2, int i3, Bundle bundle) {
            if (i3 == 16 && i2 == 0) {
                return Chip.this.b();
            }
            return false;
        }
    }

    public Chip(Context context) {
        this(context, (AttributeSet) null);
    }

    private void e() {
        if (this.m == Integer.MIN_VALUE) {
            setFocusedVirtualView(-1);
        }
    }

    /* access modifiers changed from: private */
    public boolean f() {
        a aVar = this.f1449h;
        return (aVar == null || aVar.m() == null) ? false : true;
    }

    private void g() {
        if (Build.VERSION.SDK_INT >= 21) {
            setOutlineProvider(new b());
        }
    }

    /* access modifiers changed from: private */
    public RectF getCloseIconTouchBounds() {
        this.s.setEmpty();
        if (f()) {
            this.f1449h.a(this.s);
        }
        return this.s;
    }

    /* access modifiers changed from: private */
    public Rect getCloseIconTouchBoundsInt() {
        RectF closeIconTouchBounds = getCloseIconTouchBounds();
        this.r.set((int) closeIconTouchBounds.left, (int) closeIconTouchBounds.top, (int) closeIconTouchBounds.right, (int) closeIconTouchBounds.bottom);
        return this.r;
    }

    private com.google.android.material.f.b getTextAppearance() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.A();
        }
        return null;
    }

    private void h() {
        a aVar;
        if (!TextUtils.isEmpty(getText()) && (aVar = this.f1449h) != null) {
            float j2 = aVar.j() + this.f1449h.e() + this.f1449h.C() + this.f1449h.B();
            if ((this.f1449h.F() && this.f1449h.f() != null) || (this.f1449h.b() != null && this.f1449h.E() && isChecked())) {
                j2 += this.f1449h.w() + this.f1449h.v() + this.f1449h.g();
            }
            if (this.f1449h.H() && this.f1449h.m() != null) {
                j2 += this.f1449h.q() + this.f1449h.o() + this.f1449h.p();
            }
            if (((float) v.s(this)) != j2) {
                v.b(this, v.t(this), getPaddingTop(), (int) j2, getPaddingBottom());
            }
        }
    }

    private void setCloseIconFocused(boolean z) {
        if (this.p != z) {
            this.p = z;
            refreshDrawableState();
        }
    }

    private void setCloseIconHovered(boolean z) {
        if (this.o != z) {
            this.o = z;
            refreshDrawableState();
        }
    }

    private void setCloseIconPressed(boolean z) {
        if (this.n != z) {
            this.n = z;
            refreshDrawableState();
        }
    }

    private void setFocusedVirtualView(int i2) {
        int i3 = this.m;
        if (i3 != i2) {
            if (i3 == 0) {
                setCloseIconFocused(false);
            }
            this.m = i2;
            if (i2 == 0) {
                setCloseIconFocused(true);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return a(motionEvent) || this.q.a(motionEvent) || super.dispatchHoverEvent(motionEvent);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return this.q.a(keyEvent) || super.dispatchKeyEvent(keyEvent);
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        a aVar = this.f1449h;
        if ((aVar == null || !aVar.G()) ? false : this.f1449h.a(d())) {
            invalidate();
        }
    }

    public Drawable getCheckedIcon() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.b();
        }
        return null;
    }

    public ColorStateList getChipBackgroundColor() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.c();
        }
        return null;
    }

    public float getChipCornerRadius() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.d();
        }
        return 0.0f;
    }

    public Drawable getChipDrawable() {
        return this.f1449h;
    }

    public float getChipEndPadding() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.e();
        }
        return 0.0f;
    }

    public Drawable getChipIcon() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.f();
        }
        return null;
    }

    public float getChipIconSize() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.g();
        }
        return 0.0f;
    }

    public ColorStateList getChipIconTint() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.h();
        }
        return null;
    }

    public float getChipMinHeight() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.i();
        }
        return 0.0f;
    }

    public float getChipStartPadding() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.j();
        }
        return 0.0f;
    }

    public ColorStateList getChipStrokeColor() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.k();
        }
        return null;
    }

    public float getChipStrokeWidth() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.l();
        }
        return 0.0f;
    }

    @Deprecated
    public CharSequence getChipText() {
        return getText();
    }

    public Drawable getCloseIcon() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.m();
        }
        return null;
    }

    public CharSequence getCloseIconContentDescription() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.n();
        }
        return null;
    }

    public float getCloseIconEndPadding() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.o();
        }
        return 0.0f;
    }

    public float getCloseIconSize() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.p();
        }
        return 0.0f;
    }

    public float getCloseIconStartPadding() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.q();
        }
        return 0.0f;
    }

    public ColorStateList getCloseIconTint() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.s();
        }
        return null;
    }

    public TextUtils.TruncateAt getEllipsize() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.t();
        }
        return null;
    }

    public void getFocusedRect(Rect rect) {
        if (this.m == 0) {
            rect.set(getCloseIconTouchBoundsInt());
        } else {
            super.getFocusedRect(rect);
        }
    }

    public h getHideMotionSpec() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.u();
        }
        return null;
    }

    public float getIconEndPadding() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.v();
        }
        return 0.0f;
    }

    public float getIconStartPadding() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.w();
        }
        return 0.0f;
    }

    public ColorStateList getRippleColor() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.x();
        }
        return null;
    }

    public h getShowMotionSpec() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.y();
        }
        return null;
    }

    public CharSequence getText() {
        a aVar = this.f1449h;
        return aVar != null ? aVar.z() : BuildConfig.FLAVOR;
    }

    public float getTextEndPadding() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.B();
        }
        return 0.0f;
    }

    public float getTextStartPadding() {
        a aVar = this.f1449h;
        if (aVar != null) {
            return aVar.C();
        }
        return 0.0f;
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int i2) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i2 + 1);
        if (isChecked()) {
            CheckBox.mergeDrawableStates(onCreateDrawableState, v);
        }
        return onCreateDrawableState;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        a aVar;
        if (TextUtils.isEmpty(getText()) || (aVar = this.f1449h) == null || aVar.J()) {
            super.onDraw(canvas);
            return;
        }
        int save = canvas.save();
        canvas.translate(b(this.f1449h), 0.0f);
        super.onDraw(canvas);
        canvas.restoreToCount(save);
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean z, int i2, Rect rect) {
        if (z) {
            setFocusedVirtualView(-1);
        } else {
            setFocusedVirtualView(Integer.MIN_VALUE);
        }
        invalidate();
        super.onFocusChanged(z, i2, rect);
        this.q.a(z, i2, rect);
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 7) {
            setCloseIconHovered(getCloseIconTouchBounds().contains(motionEvent.getX(), motionEvent.getY()));
        } else if (actionMasked == 10) {
            setCloseIconHovered(false);
        }
        return super.onHoverEvent(motionEvent);
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0069  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onKeyDown(int r7, android.view.KeyEvent r8) {
        /*
            r6 = this;
            int r0 = r8.getKeyCode()
            r1 = 61
            r2 = 0
            r3 = 1
            if (r0 == r1) goto L_0x0041
            r1 = 66
            if (r0 == r1) goto L_0x0031
            switch(r0) {
                case 21: goto L_0x0022;
                case 22: goto L_0x0012;
                case 23: goto L_0x0031;
                default: goto L_0x0011;
            }
        L_0x0011:
            goto L_0x006d
        L_0x0012:
            boolean r0 = r8.hasNoModifiers()
            if (r0 == 0) goto L_0x006d
            boolean r0 = com.google.android.material.internal.l.a(r6)
            r0 = r0 ^ r3
            boolean r2 = r6.a((boolean) r0)
            goto L_0x006d
        L_0x0022:
            boolean r0 = r8.hasNoModifiers()
            if (r0 == 0) goto L_0x006d
            boolean r0 = com.google.android.material.internal.l.a(r6)
            boolean r2 = r6.a((boolean) r0)
            goto L_0x006d
        L_0x0031:
            int r0 = r6.m
            r1 = -1
            if (r0 == r1) goto L_0x003d
            if (r0 == 0) goto L_0x0039
            goto L_0x006d
        L_0x0039:
            r6.b()
            return r3
        L_0x003d:
            r6.performClick()
            return r3
        L_0x0041:
            boolean r0 = r8.hasNoModifiers()
            if (r0 == 0) goto L_0x0049
            r0 = 2
            goto L_0x0052
        L_0x0049:
            boolean r0 = r8.hasModifiers(r3)
            if (r0 == 0) goto L_0x0051
            r0 = 1
            goto L_0x0052
        L_0x0051:
            r0 = 0
        L_0x0052:
            if (r0 == 0) goto L_0x006d
            android.view.ViewParent r1 = r6.getParent()
            r4 = r6
        L_0x0059:
            android.view.View r4 = r4.focusSearch(r0)
            if (r4 == 0) goto L_0x0067
            if (r4 == r6) goto L_0x0067
            android.view.ViewParent r5 = r4.getParent()
            if (r5 == r1) goto L_0x0059
        L_0x0067:
            if (r4 == 0) goto L_0x006d
            r4.requestFocus()
            return r3
        L_0x006d:
            if (r2 == 0) goto L_0x0073
            r6.invalidate()
            return r3
        L_0x0073:
            boolean r7 = super.onKeyDown(r7, r8)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.chip.Chip.onKeyDown(int, android.view.KeyEvent):boolean");
    }

    @TargetApi(24)
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i2) {
        if (!getCloseIconTouchBounds().contains(motionEvent.getX(), motionEvent.getY()) || !isEnabled()) {
            return null;
        }
        return PointerIcon.getSystemIcon(getContext(), 1002);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x001e, code lost:
        if (r0 != 3) goto L_0x0040;
     */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0049 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            int r0 = r6.getActionMasked()
            android.graphics.RectF r1 = r5.getCloseIconTouchBounds()
            float r2 = r6.getX()
            float r3 = r6.getY()
            boolean r1 = r1.contains(r2, r3)
            r2 = 0
            r3 = 1
            if (r0 == 0) goto L_0x0039
            if (r0 == r3) goto L_0x002b
            r4 = 2
            if (r0 == r4) goto L_0x0021
            r1 = 3
            if (r0 == r1) goto L_0x0034
            goto L_0x0040
        L_0x0021:
            boolean r0 = r5.n
            if (r0 == 0) goto L_0x0040
            if (r1 != 0) goto L_0x003e
            r5.setCloseIconPressed(r2)
            goto L_0x003e
        L_0x002b:
            boolean r0 = r5.n
            if (r0 == 0) goto L_0x0034
            r5.b()
            r0 = 1
            goto L_0x0035
        L_0x0034:
            r0 = 0
        L_0x0035:
            r5.setCloseIconPressed(r2)
            goto L_0x0041
        L_0x0039:
            if (r1 == 0) goto L_0x0040
            r5.setCloseIconPressed(r3)
        L_0x003e:
            r0 = 1
            goto L_0x0041
        L_0x0040:
            r0 = 0
        L_0x0041:
            if (r0 != 0) goto L_0x0049
            boolean r6 = super.onTouchEvent(r6)
            if (r6 == 0) goto L_0x004a
        L_0x0049:
            r2 = 1
        L_0x004a:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.chip.Chip.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void setBackground(Drawable drawable) {
        if (drawable == this.f1449h || drawable == this.f1450i) {
            super.setBackground(drawable);
            return;
        }
        throw new UnsupportedOperationException("Do not set the background; Chip manages its own background drawable.");
    }

    public void setBackgroundColor(int i2) {
        throw new UnsupportedOperationException("Do not set the background color; Chip manages its own background drawable.");
    }

    public void setBackgroundDrawable(Drawable drawable) {
        if (drawable == this.f1449h || drawable == this.f1450i) {
            super.setBackgroundDrawable(drawable);
            return;
        }
        throw new UnsupportedOperationException("Do not set the background drawable; Chip manages its own background drawable.");
    }

    public void setBackgroundResource(int i2) {
        throw new UnsupportedOperationException("Do not set the background resource; Chip manages its own background drawable.");
    }

    public void setBackgroundTintList(ColorStateList colorStateList) {
        throw new UnsupportedOperationException("Do not set the background tint list; Chip manages its own background drawable.");
    }

    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        throw new UnsupportedOperationException("Do not set the background tint mode; Chip manages its own background drawable.");
    }

    public void setCheckable(boolean z) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.a(z);
        }
    }

    public void setCheckableResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.a(i2);
        }
    }

    public void setChecked(boolean z) {
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
        a aVar = this.f1449h;
        if (aVar == null) {
            this.l = z;
        } else if (aVar.D()) {
            boolean isChecked = isChecked();
            super.setChecked(z);
            if (isChecked != z && (onCheckedChangeListener = this.k) != null) {
                onCheckedChangeListener.onCheckedChanged(this, z);
            }
        }
    }

    public void setCheckedIcon(Drawable drawable) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.a(drawable);
        }
    }

    @Deprecated
    public void setCheckedIconEnabled(boolean z) {
        setCheckedIconVisible(z);
    }

    @Deprecated
    public void setCheckedIconEnabledResource(int i2) {
        setCheckedIconVisible(i2);
    }

    public void setCheckedIconResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.b(i2);
        }
    }

    public void setCheckedIconVisible(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.c(i2);
        }
    }

    public void setChipBackgroundColor(ColorStateList colorStateList) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.a(colorStateList);
        }
    }

    public void setChipBackgroundColorResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.d(i2);
        }
    }

    public void setChipCornerRadius(float f2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.a(f2);
        }
    }

    public void setChipCornerRadiusResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.e(i2);
        }
    }

    public void setChipDrawable(a aVar) {
        a aVar2 = this.f1449h;
        if (aVar2 != aVar) {
            c(aVar2);
            this.f1449h = aVar;
            a(aVar);
            if (com.google.android.material.g.a.a) {
                this.f1450i = new RippleDrawable(com.google.android.material.g.a.a(this.f1449h.x()), this.f1449h, (Drawable) null);
                this.f1449h.f(false);
                v.a((View) this, (Drawable) this.f1450i);
                return;
            }
            this.f1449h.f(true);
            v.a((View) this, (Drawable) this.f1449h);
        }
    }

    public void setChipEndPadding(float f2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.b(f2);
        }
    }

    public void setChipEndPaddingResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.f(i2);
        }
    }

    public void setChipIcon(Drawable drawable) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.b(drawable);
        }
    }

    @Deprecated
    public void setChipIconEnabled(boolean z) {
        setChipIconVisible(z);
    }

    @Deprecated
    public void setChipIconEnabledResource(int i2) {
        setChipIconVisible(i2);
    }

    public void setChipIconResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.g(i2);
        }
    }

    public void setChipIconSize(float f2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.c(f2);
        }
    }

    public void setChipIconSizeResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.h(i2);
        }
    }

    public void setChipIconTint(ColorStateList colorStateList) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.b(colorStateList);
        }
    }

    public void setChipIconTintResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.i(i2);
        }
    }

    public void setChipIconVisible(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.j(i2);
        }
    }

    public void setChipMinHeight(float f2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.d(f2);
        }
    }

    public void setChipMinHeightResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.k(i2);
        }
    }

    public void setChipStartPadding(float f2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.e(f2);
        }
    }

    public void setChipStartPaddingResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.l(i2);
        }
    }

    public void setChipStrokeColor(ColorStateList colorStateList) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.c(colorStateList);
        }
    }

    public void setChipStrokeColorResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.m(i2);
        }
    }

    public void setChipStrokeWidth(float f2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.f(f2);
        }
    }

    public void setChipStrokeWidthResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.n(i2);
        }
    }

    @Deprecated
    public void setChipText(CharSequence charSequence) {
        setText(charSequence);
    }

    @Deprecated
    public void setChipTextResource(int i2) {
        setText(getResources().getString(i2));
    }

    public void setCloseIcon(Drawable drawable) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.c(drawable);
        }
    }

    public void setCloseIconContentDescription(CharSequence charSequence) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.a(charSequence);
        }
    }

    @Deprecated
    public void setCloseIconEnabled(boolean z) {
        setCloseIconVisible(z);
    }

    @Deprecated
    public void setCloseIconEnabledResource(int i2) {
        setCloseIconVisible(i2);
    }

    public void setCloseIconEndPadding(float f2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.g(f2);
        }
    }

    public void setCloseIconEndPaddingResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.o(i2);
        }
    }

    public void setCloseIconResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.p(i2);
        }
    }

    public void setCloseIconSize(float f2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.h(f2);
        }
    }

    public void setCloseIconSizeResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.q(i2);
        }
    }

    public void setCloseIconStartPadding(float f2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.i(f2);
        }
    }

    public void setCloseIconStartPaddingResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.r(i2);
        }
    }

    public void setCloseIconTint(ColorStateList colorStateList) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.d(colorStateList);
        }
    }

    public void setCloseIconTintResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.s(i2);
        }
    }

    public void setCloseIconVisible(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.t(i2);
        }
    }

    public void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (drawable3 == null) {
            super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    public void setCompoundDrawablesRelative(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (drawable3 == null) {
            super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int i2, int i3, int i4, int i5) {
        if (i2 != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (i4 == 0) {
            super.setCompoundDrawablesRelativeWithIntrinsicBounds(i2, i3, i4, i5);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    public void setCompoundDrawablesWithIntrinsicBounds(int i2, int i3, int i4, int i5) {
        if (i2 != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (i4 == 0) {
            super.setCompoundDrawablesWithIntrinsicBounds(i2, i3, i4, i5);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    public void setEllipsize(TextUtils.TruncateAt truncateAt) {
        if (this.f1449h != null) {
            if (truncateAt != TextUtils.TruncateAt.MARQUEE) {
                super.setEllipsize(truncateAt);
                a aVar = this.f1449h;
                if (aVar != null) {
                    aVar.a(truncateAt);
                    return;
                }
                return;
            }
            throw new UnsupportedOperationException("Text within a chip are not allowed to scroll.");
        }
    }

    public void setGravity(int i2) {
        if (i2 != 8388627) {
            Log.w("Chip", "Chip text must be vertically center and start aligned");
        } else {
            super.setGravity(i2);
        }
    }

    public void setHideMotionSpec(h hVar) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.a(hVar);
        }
    }

    public void setHideMotionSpecResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.u(i2);
        }
    }

    public void setIconEndPadding(float f2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.j(f2);
        }
    }

    public void setIconEndPaddingResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.v(i2);
        }
    }

    public void setIconStartPadding(float f2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.k(f2);
        }
    }

    public void setIconStartPaddingResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.w(i2);
        }
    }

    public void setLines(int i2) {
        if (i2 <= 1) {
            super.setLines(i2);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    public void setMaxLines(int i2) {
        if (i2 <= 1) {
            super.setMaxLines(i2);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    public void setMaxWidth(int i2) {
        super.setMaxWidth(i2);
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.x(i2);
        }
    }

    public void setMinLines(int i2) {
        if (i2 <= 1) {
            super.setMinLines(i2);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    /* access modifiers changed from: package-private */
    public void setOnCheckedChangeListenerInternal(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.k = onCheckedChangeListener;
    }

    public void setOnCloseIconClickListener(View.OnClickListener onClickListener) {
        this.f1451j = onClickListener;
    }

    public void setRippleColor(ColorStateList colorStateList) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.e(colorStateList);
        }
    }

    public void setRippleColorResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.y(i2);
        }
    }

    public void setShowMotionSpec(h hVar) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.b(hVar);
        }
    }

    public void setShowMotionSpecResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.z(i2);
        }
    }

    public void setSingleLine(boolean z) {
        if (z) {
            super.setSingleLine(z);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        if (this.f1449h != null) {
            if (charSequence == null) {
                charSequence = BuildConfig.FLAVOR;
            }
            CharSequence a2 = androidx.core.f.a.b().a(charSequence);
            if (this.f1449h.J()) {
                a2 = null;
            }
            super.setText(a2, bufferType);
            a aVar = this.f1449h;
            if (aVar != null) {
                aVar.b(charSequence);
            }
        }
    }

    public void setTextAppearance(com.google.android.material.f.b bVar) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.a(bVar);
        }
        if (getTextAppearance() != null) {
            getTextAppearance().c(getContext(), getPaint(), this.t);
            a(bVar);
        }
    }

    public void setTextAppearanceResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.A(i2);
        }
        setTextAppearance(getContext(), i2);
    }

    public void setTextEndPadding(float f2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.l(f2);
        }
    }

    public void setTextEndPaddingResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.B(i2);
        }
    }

    public void setTextStartPadding(float f2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.m(f2);
        }
    }

    public void setTextStartPaddingResource(int i2) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.C(i2);
        }
    }

    public Chip(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.chipStyle);
    }

    private void a(AttributeSet attributeSet) {
        if (attributeSet != null) {
            if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "background") != null) {
                throw new UnsupportedOperationException("Do not set the background; Chip manages its own background drawable.");
            } else if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableLeft") != null) {
                throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
            } else if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableStart") != null) {
                throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
            } else if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableEnd") != null) {
                throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
            } else if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableRight") != null) {
                throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
            } else if (!attributeSet.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "singleLine", true) || attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "lines", 1) != 1 || attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "minLines", 1) != 1 || attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "maxLines", 1) != 1) {
                throw new UnsupportedOperationException("Chip does not support multi-line text");
            } else if (attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "gravity", 8388627) != 8388627) {
                Log.w("Chip", "Chip text must be vertically center and start aligned");
            }
        }
    }

    private float b(a aVar) {
        float chipStartPadding = getChipStartPadding() + aVar.a() + getTextStartPadding();
        return v.o(this) == 0 ? chipStartPadding : -chipStartPadding;
    }

    private int[] d() {
        int i2 = 0;
        int i3 = isEnabled() ? 1 : 0;
        if (this.p) {
            i3++;
        }
        if (this.o) {
            i3++;
        }
        if (this.n) {
            i3++;
        }
        if (isChecked()) {
            i3++;
        }
        int[] iArr = new int[i3];
        if (isEnabled()) {
            iArr[0] = 16842910;
            i2 = 1;
        }
        if (this.p) {
            iArr[i2] = 16842908;
            i2++;
        }
        if (this.o) {
            iArr[i2] = 16843623;
            i2++;
        }
        if (this.n) {
            iArr[i2] = 16842919;
            i2++;
        }
        if (isChecked()) {
            iArr[i2] = 16842913;
        }
        return iArr;
    }

    public Chip(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.m = Integer.MIN_VALUE;
        this.r = new Rect();
        this.s = new RectF();
        this.t = new a();
        a(attributeSet);
        a a2 = a.a(context, attributeSet, i2, R$style.Widget_MaterialComponents_Chip_Action);
        setChipDrawable(a2);
        c cVar = new c(this);
        this.q = cVar;
        v.a((View) this, (androidx.core.h.a) cVar);
        g();
        setChecked(this.l);
        a2.e(false);
        setText(a2.z());
        setEllipsize(a2.t());
        setIncludeFontPadding(false);
        if (getTextAppearance() != null) {
            a(getTextAppearance());
        }
        setSingleLine();
        setGravity(8388627);
        h();
    }

    private void c(a aVar) {
        if (aVar != null) {
            aVar.a((a.b) null);
        }
    }

    public void setCheckedIconVisible(boolean z) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.b(z);
        }
    }

    public void setChipIconVisible(boolean z) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.c(z);
        }
    }

    public void setCloseIconVisible(boolean z) {
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.d(z);
        }
    }

    public boolean b() {
        boolean z;
        playSoundEffect(0);
        View.OnClickListener onClickListener = this.f1451j;
        if (onClickListener != null) {
            onClickListener.onClick(this);
            z = true;
        } else {
            z = false;
        }
        this.q.a(0, 1);
        return z;
    }

    public void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (drawable3 == null) {
            super.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    public void setCompoundDrawablesWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
        } else if (drawable3 == null) {
            super.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        } else {
            throw new UnsupportedOperationException("Please set right drawable using R.attr#closeIcon.");
        }
    }

    public void setTextAppearance(Context context, int i2) {
        super.setTextAppearance(context, i2);
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.A(i2);
        }
        if (getTextAppearance() != null) {
            getTextAppearance().c(context, getPaint(), this.t);
            a(getTextAppearance());
        }
    }

    public void setTextAppearance(int i2) {
        super.setTextAppearance(i2);
        a aVar = this.f1449h;
        if (aVar != null) {
            aVar.A(i2);
        }
        if (getTextAppearance() != null) {
            getTextAppearance().c(getContext(), getPaint(), this.t);
            a(getTextAppearance());
        }
    }

    private void a(a aVar) {
        aVar.a((a.b) this);
    }

    public void a() {
        h();
        requestLayout();
        if (Build.VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }

    @SuppressLint({"PrivateApi"})
    private boolean a(MotionEvent motionEvent) {
        Class<androidx.customview.a.a> cls = androidx.customview.a.a.class;
        if (motionEvent.getAction() == 10) {
            try {
                Field declaredField = cls.getDeclaredField("m");
                declaredField.setAccessible(true);
                if (((Integer) declaredField.get(this.q)).intValue() != Integer.MIN_VALUE) {
                    Method declaredMethod = cls.getDeclaredMethod("i", new Class[]{Integer.TYPE});
                    declaredMethod.setAccessible(true);
                    declaredMethod.invoke(this.q, new Object[]{Integer.MIN_VALUE});
                    return true;
                }
            } catch (NoSuchMethodException e) {
                Log.e("Chip", "Unable to send Accessibility Exit event", e);
            } catch (IllegalAccessException e2) {
                Log.e("Chip", "Unable to send Accessibility Exit event", e2);
            } catch (InvocationTargetException e3) {
                Log.e("Chip", "Unable to send Accessibility Exit event", e3);
            } catch (NoSuchFieldException e4) {
                Log.e("Chip", "Unable to send Accessibility Exit event", e4);
            }
        }
        return false;
    }

    private boolean a(boolean z) {
        e();
        if (z) {
            if (this.m == -1) {
                setFocusedVirtualView(0);
                return true;
            }
        } else if (this.m == 0) {
            setFocusedVirtualView(-1);
            return true;
        }
        return false;
    }

    private void a(com.google.android.material.f.b bVar) {
        TextPaint paint = getPaint();
        paint.drawableState = this.f1449h.getState();
        bVar.b(getContext(), paint, this.t);
    }
}
