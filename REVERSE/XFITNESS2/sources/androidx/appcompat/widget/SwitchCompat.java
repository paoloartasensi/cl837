package androidx.appcompat.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$styleable;
import androidx.core.h.v;
import androidx.core.widget.i;

public class SwitchCompat extends CompoundButton {
    private static final Property<SwitchCompat, Float> R = new a(Float.class, "thumbPos");
    private static final int[] S = {16842912};
    private int A;
    float B;
    private int C;
    private int D;
    private int E;
    private int F;
    private int G;
    private int H;
    private int I;
    private final TextPaint J;
    private ColorStateList K;
    private Layout L;
    private Layout M;
    private TransformationMethod N;
    ObjectAnimator O;
    private final m P;
    private final Rect Q;
    private Drawable e;

    /* renamed from: f  reason: collision with root package name */
    private ColorStateList f254f;

    /* renamed from: g  reason: collision with root package name */
    private PorterDuff.Mode f255g;

    /* renamed from: h  reason: collision with root package name */
    private boolean f256h;

    /* renamed from: i  reason: collision with root package name */
    private boolean f257i;

    /* renamed from: j  reason: collision with root package name */
    private Drawable f258j;
    private ColorStateList k;
    private PorterDuff.Mode l;
    private boolean m;
    private boolean n;
    private int o;
    private int p;
    private int q;
    private boolean r;
    private CharSequence s;
    private CharSequence t;
    private boolean u;
    private int v;
    private int w;
    private float x;
    private float y;
    private VelocityTracker z;

    static class a extends Property<SwitchCompat, Float> {
        a(Class cls, String str) {
            super(cls, str);
        }

        /* renamed from: a */
        public Float get(SwitchCompat switchCompat) {
            return Float.valueOf(switchCompat.B);
        }

        /* renamed from: a */
        public void set(SwitchCompat switchCompat, Float f2) {
            switchCompat.setThumbPosition(f2.floatValue());
        }
    }

    public SwitchCompat(Context context) {
        this(context, (AttributeSet) null);
    }

    private static float a(float f2, float f3, float f4) {
        return f2 < f3 ? f3 : f2 > f4 ? f4 : f2;
    }

    private void b() {
        if (this.f258j == null) {
            return;
        }
        if (this.m || this.n) {
            Drawable mutate = androidx.core.graphics.drawable.a.i(this.f258j).mutate();
            this.f258j = mutate;
            if (this.m) {
                androidx.core.graphics.drawable.a.a(mutate, this.k);
            }
            if (this.n) {
                androidx.core.graphics.drawable.a.a(this.f258j, this.l);
            }
            if (this.f258j.isStateful()) {
                this.f258j.setState(getDrawableState());
            }
        }
    }

    private void c() {
        ObjectAnimator objectAnimator = this.O;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    private boolean getTargetCheckedState() {
        return this.B > 0.5f;
    }

    private int getThumbOffset() {
        float f2;
        if (m0.a(this)) {
            f2 = 1.0f - this.B;
        } else {
            f2 = this.B;
        }
        return (int) ((f2 * ((float) getThumbScrollRange())) + 0.5f);
    }

    private int getThumbScrollRange() {
        Rect rect;
        Drawable drawable = this.f258j;
        if (drawable == null) {
            return 0;
        }
        Rect rect2 = this.Q;
        drawable.getPadding(rect2);
        Drawable drawable2 = this.e;
        if (drawable2 != null) {
            rect = q.d(drawable2);
        } else {
            rect = q.c;
        }
        return ((((this.C - this.E) - rect2.left) - rect2.right) - rect.left) - rect.right;
    }

    public void a(Context context, int i2) {
        g0 a2 = g0.a(context, i2, R$styleable.TextAppearance);
        ColorStateList a3 = a2.a(R$styleable.TextAppearance_android_textColor);
        if (a3 != null) {
            this.K = a3;
        } else {
            this.K = getTextColors();
        }
        int c = a2.c(R$styleable.TextAppearance_android_textSize, 0);
        if (c != 0) {
            float f2 = (float) c;
            if (f2 != this.J.getTextSize()) {
                this.J.setTextSize(f2);
                requestLayout();
            }
        }
        a(a2.d(R$styleable.TextAppearance_android_typeface, -1), a2.d(R$styleable.TextAppearance_android_textStyle, -1));
        if (a2.a(R$styleable.TextAppearance_textAllCaps, false)) {
            this.N = new androidx.appcompat.c.a(getContext());
        } else {
            this.N = null;
        }
        a2.a();
    }

    public void draw(Canvas canvas) {
        Rect rect;
        int i2;
        int i3;
        Rect rect2 = this.Q;
        int i4 = this.F;
        int i5 = this.G;
        int i6 = this.H;
        int i7 = this.I;
        int thumbOffset = getThumbOffset() + i4;
        Drawable drawable = this.e;
        if (drawable != null) {
            rect = q.d(drawable);
        } else {
            rect = q.c;
        }
        Drawable drawable2 = this.f258j;
        if (drawable2 != null) {
            drawable2.getPadding(rect2);
            int i8 = rect2.left;
            thumbOffset += i8;
            if (rect != null) {
                int i9 = rect.left;
                if (i9 > i8) {
                    i4 += i9 - i8;
                }
                int i10 = rect.top;
                int i11 = rect2.top;
                i2 = i10 > i11 ? (i10 - i11) + i5 : i5;
                int i12 = rect.right;
                int i13 = rect2.right;
                if (i12 > i13) {
                    i6 -= i12 - i13;
                }
                int i14 = rect.bottom;
                int i15 = rect2.bottom;
                if (i14 > i15) {
                    i3 = i7 - (i14 - i15);
                    this.f258j.setBounds(i4, i2, i6, i3);
                }
            } else {
                i2 = i5;
            }
            i3 = i7;
            this.f258j.setBounds(i4, i2, i6, i3);
        }
        Drawable drawable3 = this.e;
        if (drawable3 != null) {
            drawable3.getPadding(rect2);
            int i16 = thumbOffset - rect2.left;
            int i17 = thumbOffset + this.E + rect2.right;
            this.e.setBounds(i16, i5, i17, i7);
            Drawable background = getBackground();
            if (background != null) {
                androidx.core.graphics.drawable.a.a(background, i16, i5, i17, i7);
            }
        }
        super.draw(canvas);
    }

    public void drawableHotspotChanged(float f2, float f3) {
        if (Build.VERSION.SDK_INT >= 21) {
            super.drawableHotspotChanged(f2, f3);
        }
        Drawable drawable = this.e;
        if (drawable != null) {
            androidx.core.graphics.drawable.a.a(drawable, f2, f3);
        }
        Drawable drawable2 = this.f258j;
        if (drawable2 != null) {
            androidx.core.graphics.drawable.a.a(drawable2, f2, f3);
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.e;
        boolean z2 = false;
        if (drawable != null && drawable.isStateful()) {
            z2 = false | drawable.setState(drawableState);
        }
        Drawable drawable2 = this.f258j;
        if (drawable2 != null && drawable2.isStateful()) {
            z2 |= drawable2.setState(drawableState);
        }
        if (z2) {
            invalidate();
        }
    }

    public int getCompoundPaddingLeft() {
        if (!m0.a(this)) {
            return super.getCompoundPaddingLeft();
        }
        int compoundPaddingLeft = super.getCompoundPaddingLeft() + this.C;
        return !TextUtils.isEmpty(getText()) ? compoundPaddingLeft + this.q : compoundPaddingLeft;
    }

    public int getCompoundPaddingRight() {
        if (m0.a(this)) {
            return super.getCompoundPaddingRight();
        }
        int compoundPaddingRight = super.getCompoundPaddingRight() + this.C;
        return !TextUtils.isEmpty(getText()) ? compoundPaddingRight + this.q : compoundPaddingRight;
    }

    public boolean getShowText() {
        return this.u;
    }

    public boolean getSplitTrack() {
        return this.r;
    }

    public int getSwitchMinWidth() {
        return this.p;
    }

    public int getSwitchPadding() {
        return this.q;
    }

    public CharSequence getTextOff() {
        return this.t;
    }

    public CharSequence getTextOn() {
        return this.s;
    }

    public Drawable getThumbDrawable() {
        return this.e;
    }

    public int getThumbTextPadding() {
        return this.o;
    }

    public ColorStateList getThumbTintList() {
        return this.f254f;
    }

    public PorterDuff.Mode getThumbTintMode() {
        return this.f255g;
    }

    public Drawable getTrackDrawable() {
        return this.f258j;
    }

    public ColorStateList getTrackTintList() {
        return this.k;
    }

    public PorterDuff.Mode getTrackTintMode() {
        return this.l;
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.e;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
        Drawable drawable2 = this.f258j;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
        ObjectAnimator objectAnimator = this.O;
        if (objectAnimator != null && objectAnimator.isStarted()) {
            this.O.end();
            this.O = null;
        }
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int i2) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i2 + 1);
        if (isChecked()) {
            CompoundButton.mergeDrawableStates(onCreateDrawableState, S);
        }
        return onCreateDrawableState;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int i2;
        super.onDraw(canvas);
        Rect rect = this.Q;
        Drawable drawable = this.f258j;
        if (drawable != null) {
            drawable.getPadding(rect);
        } else {
            rect.setEmpty();
        }
        int i3 = this.G;
        int i4 = this.I;
        int i5 = i3 + rect.top;
        int i6 = i4 - rect.bottom;
        Drawable drawable2 = this.e;
        if (drawable != null) {
            if (!this.r || drawable2 == null) {
                drawable.draw(canvas);
            } else {
                Rect d = q.d(drawable2);
                drawable2.copyBounds(rect);
                rect.left += d.left;
                rect.right -= d.right;
                int save = canvas.save();
                canvas.clipRect(rect, Region.Op.DIFFERENCE);
                drawable.draw(canvas);
                canvas.restoreToCount(save);
            }
        }
        int save2 = canvas.save();
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        Layout layout = getTargetCheckedState() ? this.L : this.M;
        if (layout != null) {
            int[] drawableState = getDrawableState();
            ColorStateList colorStateList = this.K;
            if (colorStateList != null) {
                this.J.setColor(colorStateList.getColorForState(drawableState, 0));
            }
            this.J.drawableState = drawableState;
            if (drawable2 != null) {
                Rect bounds = drawable2.getBounds();
                i2 = bounds.left + bounds.right;
            } else {
                i2 = getWidth();
            }
            canvas.translate((float) ((i2 / 2) - (layout.getWidth() / 2)), (float) (((i5 + i6) / 2) - (layout.getHeight() / 2)));
            layout.draw(canvas);
        }
        canvas.restoreToCount(save2);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName("android.widget.Switch");
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("android.widget.Switch");
        CharSequence charSequence = isChecked() ? this.s : this.t;
        if (!TextUtils.isEmpty(charSequence)) {
            CharSequence text = accessibilityNodeInfo.getText();
            if (TextUtils.isEmpty(text)) {
                accessibilityNodeInfo.setText(charSequence);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(text);
            sb.append(' ');
            sb.append(charSequence);
            accessibilityNodeInfo.setText(sb);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        super.onLayout(z2, i2, i3, i4, i5);
        int i12 = 0;
        if (this.e != null) {
            Rect rect = this.Q;
            Drawable drawable = this.f258j;
            if (drawable != null) {
                drawable.getPadding(rect);
            } else {
                rect.setEmpty();
            }
            Rect d = q.d(this.e);
            i6 = Math.max(0, d.left - rect.left);
            i12 = Math.max(0, d.right - rect.right);
        } else {
            i6 = 0;
        }
        if (m0.a(this)) {
            i8 = getPaddingLeft() + i6;
            i7 = ((this.C + i8) - i6) - i12;
        } else {
            i7 = (getWidth() - getPaddingRight()) - i12;
            i8 = (i7 - this.C) + i6 + i12;
        }
        int gravity = getGravity() & 112;
        if (gravity == 16) {
            i11 = this.D;
            i10 = (((getPaddingTop() + getHeight()) - getPaddingBottom()) / 2) - (i11 / 2);
        } else if (gravity != 80) {
            i10 = getPaddingTop();
            i11 = this.D;
        } else {
            i9 = getHeight() - getPaddingBottom();
            i10 = i9 - this.D;
            this.F = i8;
            this.G = i10;
            this.I = i9;
            this.H = i7;
        }
        i9 = i11 + i10;
        this.F = i8;
        this.G = i10;
        this.I = i9;
        this.H = i7;
    }

    public void onMeasure(int i2, int i3) {
        int i4;
        int i5;
        if (this.u) {
            if (this.L == null) {
                this.L = a(this.s);
            }
            if (this.M == null) {
                this.M = a(this.t);
            }
        }
        Rect rect = this.Q;
        Drawable drawable = this.e;
        int i6 = 0;
        if (drawable != null) {
            drawable.getPadding(rect);
            i5 = (this.e.getIntrinsicWidth() - rect.left) - rect.right;
            i4 = this.e.getIntrinsicHeight();
        } else {
            i5 = 0;
            i4 = 0;
        }
        this.E = Math.max(this.u ? Math.max(this.L.getWidth(), this.M.getWidth()) + (this.o * 2) : 0, i5);
        Drawable drawable2 = this.f258j;
        if (drawable2 != null) {
            drawable2.getPadding(rect);
            i6 = this.f258j.getIntrinsicHeight();
        } else {
            rect.setEmpty();
        }
        int i7 = rect.left;
        int i8 = rect.right;
        Drawable drawable3 = this.e;
        if (drawable3 != null) {
            Rect d = q.d(drawable3);
            i7 = Math.max(i7, d.left);
            i8 = Math.max(i8, d.right);
        }
        int max = Math.max(this.p, (this.E * 2) + i7 + i8);
        int max2 = Math.max(i6, i4);
        this.C = max;
        this.D = max2;
        super.onMeasure(i2, i3);
        if (getMeasuredHeight() < max2) {
            setMeasuredDimension(getMeasuredWidthAndState(), max2);
        }
    }

    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        CharSequence charSequence = isChecked() ? this.s : this.t;
        if (charSequence != null) {
            accessibilityEvent.getText().add(charSequence);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0012, code lost:
        if (r0 != 3) goto L_0x00b9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            android.view.VelocityTracker r0 = r6.z
            r0.addMovement(r7)
            int r0 = r7.getActionMasked()
            r1 = 1
            if (r0 == 0) goto L_0x009f
            r2 = 2
            if (r0 == r1) goto L_0x008b
            if (r0 == r2) goto L_0x0016
            r3 = 3
            if (r0 == r3) goto L_0x008b
            goto L_0x00b9
        L_0x0016:
            int r0 = r6.v
            if (r0 == r1) goto L_0x0057
            if (r0 == r2) goto L_0x001e
            goto L_0x00b9
        L_0x001e:
            float r7 = r7.getX()
            int r0 = r6.getThumbScrollRange()
            float r2 = r6.x
            float r2 = r7 - r2
            r3 = 1065353216(0x3f800000, float:1.0)
            r4 = 0
            if (r0 == 0) goto L_0x0032
            float r0 = (float) r0
            float r2 = r2 / r0
            goto L_0x003d
        L_0x0032:
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 <= 0) goto L_0x0039
            r2 = 1065353216(0x3f800000, float:1.0)
            goto L_0x003d
        L_0x0039:
            r0 = -1082130432(0xffffffffbf800000, float:-1.0)
            r2 = -1082130432(0xffffffffbf800000, float:-1.0)
        L_0x003d:
            boolean r0 = androidx.appcompat.widget.m0.a(r6)
            if (r0 == 0) goto L_0x0044
            float r2 = -r2
        L_0x0044:
            float r0 = r6.B
            float r0 = r0 + r2
            float r0 = a(r0, r4, r3)
            float r2 = r6.B
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 == 0) goto L_0x0056
            r6.x = r7
            r6.setThumbPosition(r0)
        L_0x0056:
            return r1
        L_0x0057:
            float r0 = r7.getX()
            float r3 = r7.getY()
            float r4 = r6.x
            float r4 = r0 - r4
            float r4 = java.lang.Math.abs(r4)
            int r5 = r6.w
            float r5 = (float) r5
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 > 0) goto L_0x007d
            float r4 = r6.y
            float r4 = r3 - r4
            float r4 = java.lang.Math.abs(r4)
            int r5 = r6.w
            float r5 = (float) r5
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 <= 0) goto L_0x00b9
        L_0x007d:
            r6.v = r2
            android.view.ViewParent r7 = r6.getParent()
            r7.requestDisallowInterceptTouchEvent(r1)
            r6.x = r0
            r6.y = r3
            return r1
        L_0x008b:
            int r0 = r6.v
            if (r0 != r2) goto L_0x0096
            r6.b(r7)
            super.onTouchEvent(r7)
            return r1
        L_0x0096:
            r0 = 0
            r6.v = r0
            android.view.VelocityTracker r0 = r6.z
            r0.clear()
            goto L_0x00b9
        L_0x009f:
            float r0 = r7.getX()
            float r2 = r7.getY()
            boolean r3 = r6.isEnabled()
            if (r3 == 0) goto L_0x00b9
            boolean r3 = r6.a((float) r0, (float) r2)
            if (r3 == 0) goto L_0x00b9
            r6.v = r1
            r6.x = r0
            r6.y = r2
        L_0x00b9:
            boolean r7 = super.onTouchEvent(r7)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.SwitchCompat.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void setChecked(boolean z2) {
        super.setChecked(z2);
        boolean isChecked = isChecked();
        if (getWindowToken() == null || !v.D(this)) {
            c();
            setThumbPosition(isChecked ? 1.0f : 0.0f);
            return;
        }
        a(isChecked);
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(i.a((TextView) this, callback));
    }

    public void setShowText(boolean z2) {
        if (this.u != z2) {
            this.u = z2;
            requestLayout();
        }
    }

    public void setSplitTrack(boolean z2) {
        this.r = z2;
        invalidate();
    }

    public void setSwitchMinWidth(int i2) {
        this.p = i2;
        requestLayout();
    }

    public void setSwitchPadding(int i2) {
        this.q = i2;
        requestLayout();
    }

    public void setSwitchTypeface(Typeface typeface) {
        if ((this.J.getTypeface() != null && !this.J.getTypeface().equals(typeface)) || (this.J.getTypeface() == null && typeface != null)) {
            this.J.setTypeface(typeface);
            requestLayout();
            invalidate();
        }
    }

    public void setTextOff(CharSequence charSequence) {
        this.t = charSequence;
        requestLayout();
    }

    public void setTextOn(CharSequence charSequence) {
        this.s = charSequence;
        requestLayout();
    }

    public void setThumbDrawable(Drawable drawable) {
        Drawable drawable2 = this.e;
        if (drawable2 != null) {
            drawable2.setCallback((Drawable.Callback) null);
        }
        this.e = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    /* access modifiers changed from: package-private */
    public void setThumbPosition(float f2) {
        this.B = f2;
        invalidate();
    }

    public void setThumbResource(int i2) {
        setThumbDrawable(androidx.appcompat.a.a.a.c(getContext(), i2));
    }

    public void setThumbTextPadding(int i2) {
        this.o = i2;
        requestLayout();
    }

    public void setThumbTintList(ColorStateList colorStateList) {
        this.f254f = colorStateList;
        this.f256h = true;
        a();
    }

    public void setThumbTintMode(PorterDuff.Mode mode) {
        this.f255g = mode;
        this.f257i = true;
        a();
    }

    public void setTrackDrawable(Drawable drawable) {
        Drawable drawable2 = this.f258j;
        if (drawable2 != null) {
            drawable2.setCallback((Drawable.Callback) null);
        }
        this.f258j = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    public void setTrackResource(int i2) {
        setTrackDrawable(androidx.appcompat.a.a.a.c(getContext(), i2));
    }

    public void setTrackTintList(ColorStateList colorStateList) {
        this.k = colorStateList;
        this.m = true;
        b();
    }

    public void setTrackTintMode(PorterDuff.Mode mode) {
        this.l = mode;
        this.n = true;
        b();
    }

    public void toggle() {
        setChecked(!isChecked());
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.e || drawable == this.f258j;
    }

    public SwitchCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.switchStyle);
    }

    public SwitchCompat(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f254f = null;
        this.f255g = null;
        this.f256h = false;
        this.f257i = false;
        this.k = null;
        this.l = null;
        this.m = false;
        this.n = false;
        this.z = VelocityTracker.obtain();
        this.Q = new Rect();
        this.J = new TextPaint(1);
        Resources resources = getResources();
        this.J.density = resources.getDisplayMetrics().density;
        g0 a2 = g0.a(context, attributeSet, R$styleable.SwitchCompat, i2, 0);
        Drawable b = a2.b(R$styleable.SwitchCompat_android_thumb);
        this.e = b;
        if (b != null) {
            b.setCallback(this);
        }
        Drawable b2 = a2.b(R$styleable.SwitchCompat_track);
        this.f258j = b2;
        if (b2 != null) {
            b2.setCallback(this);
        }
        this.s = a2.e(R$styleable.SwitchCompat_android_textOn);
        this.t = a2.e(R$styleable.SwitchCompat_android_textOff);
        this.u = a2.a(R$styleable.SwitchCompat_showText, true);
        this.o = a2.c(R$styleable.SwitchCompat_thumbTextPadding, 0);
        this.p = a2.c(R$styleable.SwitchCompat_switchMinWidth, 0);
        this.q = a2.c(R$styleable.SwitchCompat_switchPadding, 0);
        this.r = a2.a(R$styleable.SwitchCompat_splitTrack, false);
        ColorStateList a3 = a2.a(R$styleable.SwitchCompat_thumbTint);
        if (a3 != null) {
            this.f254f = a3;
            this.f256h = true;
        }
        PorterDuff.Mode a4 = q.a(a2.d(R$styleable.SwitchCompat_thumbTintMode, -1), (PorterDuff.Mode) null);
        if (this.f255g != a4) {
            this.f255g = a4;
            this.f257i = true;
        }
        if (this.f256h || this.f257i) {
            a();
        }
        ColorStateList a5 = a2.a(R$styleable.SwitchCompat_trackTint);
        if (a5 != null) {
            this.k = a5;
            this.m = true;
        }
        PorterDuff.Mode a6 = q.a(a2.d(R$styleable.SwitchCompat_trackTintMode, -1), (PorterDuff.Mode) null);
        if (this.l != a6) {
            this.l = a6;
            this.n = true;
        }
        if (this.m || this.n) {
            b();
        }
        int g2 = a2.g(R$styleable.SwitchCompat_switchTextAppearance, 0);
        if (g2 != 0) {
            a(context, g2);
        }
        m mVar = new m(this);
        this.P = mVar;
        mVar.a(attributeSet, i2);
        a2.a();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.w = viewConfiguration.getScaledTouchSlop();
        this.A = viewConfiguration.getScaledMinimumFlingVelocity();
        refreshDrawableState();
        setChecked(isChecked());
    }

    private void b(MotionEvent motionEvent) {
        this.v = 0;
        boolean z2 = true;
        boolean z3 = motionEvent.getAction() == 1 && isEnabled();
        boolean isChecked = isChecked();
        if (z3) {
            this.z.computeCurrentVelocity(1000);
            float xVelocity = this.z.getXVelocity();
            if (Math.abs(xVelocity) <= ((float) this.A)) {
                z2 = getTargetCheckedState();
            } else if (!m0.a(this) ? xVelocity <= 0.0f : xVelocity >= 0.0f) {
                z2 = false;
            }
        } else {
            z2 = isChecked;
        }
        if (z2 != isChecked) {
            playSoundEffect(0);
        }
        setChecked(z2);
        a(motionEvent);
    }

    private void a(int i2, int i3) {
        Typeface typeface;
        if (i2 == 1) {
            typeface = Typeface.SANS_SERIF;
        } else if (i2 != 2) {
            typeface = i2 != 3 ? null : Typeface.MONOSPACE;
        } else {
            typeface = Typeface.SERIF;
        }
        a(typeface, i3);
    }

    public void a(Typeface typeface, int i2) {
        Typeface typeface2;
        float f2 = 0.0f;
        boolean z2 = false;
        if (i2 > 0) {
            if (typeface == null) {
                typeface2 = Typeface.defaultFromStyle(i2);
            } else {
                typeface2 = Typeface.create(typeface, i2);
            }
            setSwitchTypeface(typeface2);
            int style = ((typeface2 != null ? typeface2.getStyle() : 0) ^ -1) & i2;
            TextPaint textPaint = this.J;
            if ((style & 1) != 0) {
                z2 = true;
            }
            textPaint.setFakeBoldText(z2);
            TextPaint textPaint2 = this.J;
            if ((style & 2) != 0) {
                f2 = -0.25f;
            }
            textPaint2.setTextSkewX(f2);
            return;
        }
        this.J.setFakeBoldText(false);
        this.J.setTextSkewX(0.0f);
        setSwitchTypeface(typeface);
    }

    private void a() {
        if (this.e == null) {
            return;
        }
        if (this.f256h || this.f257i) {
            Drawable mutate = androidx.core.graphics.drawable.a.i(this.e).mutate();
            this.e = mutate;
            if (this.f256h) {
                androidx.core.graphics.drawable.a.a(mutate, this.f254f);
            }
            if (this.f257i) {
                androidx.core.graphics.drawable.a.a(this.e, this.f255g);
            }
            if (this.e.isStateful()) {
                this.e.setState(getDrawableState());
            }
        }
    }

    private Layout a(CharSequence charSequence) {
        TransformationMethod transformationMethod = this.N;
        if (transformationMethod != null) {
            charSequence = transformationMethod.getTransformation(charSequence, this);
        }
        CharSequence charSequence2 = charSequence;
        TextPaint textPaint = this.J;
        return new StaticLayout(charSequence2, textPaint, charSequence2 != null ? (int) Math.ceil((double) Layout.getDesiredWidth(charSequence2, textPaint)) : 0, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
    }

    private boolean a(float f2, float f3) {
        if (this.e == null) {
            return false;
        }
        int thumbOffset = getThumbOffset();
        this.e.getPadding(this.Q);
        int i2 = this.G;
        int i3 = this.w;
        int i4 = i2 - i3;
        int i5 = (this.F + thumbOffset) - i3;
        Rect rect = this.Q;
        int i6 = this.E + i5 + rect.left + rect.right + i3;
        int i7 = this.I + i3;
        if (f2 <= ((float) i5) || f2 >= ((float) i6) || f3 <= ((float) i4) || f3 >= ((float) i7)) {
            return false;
        }
        return true;
    }

    private void a(MotionEvent motionEvent) {
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.setAction(3);
        super.onTouchEvent(obtain);
        obtain.recycle();
    }

    private void a(boolean z2) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, R, new float[]{z2 ? 1.0f : 0.0f});
        this.O = ofFloat;
        ofFloat.setDuration(250);
        if (Build.VERSION.SDK_INT >= 18) {
            this.O.setAutoCancel(true);
        }
        this.O.start();
    }
}
