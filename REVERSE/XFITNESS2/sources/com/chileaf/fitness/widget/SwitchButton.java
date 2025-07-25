package com.chileaf.fitness.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CompoundButton;
import androidx.recyclerview.widget.j;
import com.chileaf.fitness.R$styleable;

public class SwitchButton extends CompoundButton {
    private static int[] j0 = {16842912, 16842910, 16842919};
    private static int[] k0 = {-16842912, 16842910, 16842919};
    private RectF A;
    private RectF B;
    private RectF C;
    private RectF D;
    private RectF E;
    private Paint F;
    private boolean G;
    private boolean H;
    private boolean I = false;
    private ObjectAnimator J;
    private float K;
    private RectF L;
    private float M;
    private float N;
    private float O;
    private int P;
    private int Q;
    private Paint R;
    private CharSequence S;
    private CharSequence T;
    private TextPaint U;
    private Layout V;
    private Layout W;
    private float a0;
    private float b0;
    private int c0;
    private int d0;
    private Drawable e;
    private int e0;

    /* renamed from: f  reason: collision with root package name */
    private Drawable f1309f;
    private boolean f0 = false;

    /* renamed from: g  reason: collision with root package name */
    private ColorStateList f1310g;
    private boolean g0 = false;

    /* renamed from: h  reason: collision with root package name */
    private ColorStateList f1311h;
    private boolean h0 = false;

    /* renamed from: i  reason: collision with root package name */
    private float f1312i;
    private CompoundButton.OnCheckedChangeListener i0;

    /* renamed from: j  reason: collision with root package name */
    private float f1313j;
    private RectF k;
    private float l;
    private long m;
    private boolean n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private Drawable y;
    private Drawable z;

    private static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        CharSequence e;

        /* renamed from: f  reason: collision with root package name */
        CharSequence f1314f;

        static class a implements Parcelable.Creator<SavedState> {
            a() {
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            TextUtils.writeToParcel(this.e, parcel, i2);
            TextUtils.writeToParcel(this.f1314f, parcel, i2);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.e = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.f1314f = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }
    }

    public SwitchButton(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a(attributeSet);
    }

    private void a(AttributeSet attributeSet) {
        TypedArray typedArray;
        int i2;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        boolean z2;
        float f7;
        float f8;
        Drawable drawable;
        ColorStateList colorStateList;
        float f9;
        ColorStateList colorStateList2;
        float f10;
        Drawable drawable2;
        int i3;
        int i4;
        int i5;
        int i6;
        String str;
        String str2;
        float f11;
        TypedArray typedArray2;
        ColorStateList colorStateList3;
        AttributeSet attributeSet2 = attributeSet;
        this.P = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.Q = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
        this.F = new Paint(1);
        Paint paint = new Paint(1);
        this.R = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.R.setStrokeWidth(getResources().getDisplayMetrics().density);
        this.U = getPaint();
        this.A = new RectF();
        this.B = new RectF();
        this.C = new RectF();
        this.k = new RectF();
        this.D = new RectF();
        this.E = new RectF();
        ObjectAnimator duration = ObjectAnimator.ofFloat(this, "progress", new float[]{0.0f, 0.0f}).setDuration(250);
        this.J = duration;
        duration.setInterpolator(new AccelerateDecelerateInterpolator());
        this.L = new RectF();
        float f12 = getResources().getDisplayMetrics().density * 2.0f;
        if (attributeSet2 == null) {
            typedArray = null;
        } else {
            typedArray = getContext().obtainStyledAttributes(attributeSet2, R$styleable.SwitchButton);
        }
        if (typedArray != null) {
            Drawable drawable3 = typedArray.getDrawable(11);
            ColorStateList colorStateList4 = typedArray.getColorStateList(10);
            float dimension = typedArray.getDimension(13, f12);
            float dimension2 = typedArray.getDimension(15, dimension);
            float dimension3 = typedArray.getDimension(16, dimension);
            float dimension4 = typedArray.getDimension(17, dimension);
            float dimension5 = typedArray.getDimension(14, dimension);
            float dimension6 = typedArray.getDimension(20, 0.0f);
            float dimension7 = typedArray.getDimension(12, 0.0f);
            float dimension8 = typedArray.getDimension(18, -1.0f);
            float dimension9 = typedArray.getDimension(1, -1.0f);
            Drawable drawable4 = typedArray.getDrawable(3);
            ColorStateList colorStateList5 = typedArray.getColorStateList(2);
            float f13 = typedArray.getFloat(19, 1.8f);
            Drawable drawable5 = drawable4;
            int integer = typedArray.getInteger(0, j.a.DEFAULT_SWIPE_ANIMATION_DURATION);
            boolean z3 = typedArray.getBoolean(4, true);
            int color = typedArray.getColor(21, 0);
            String string = typedArray.getString(8);
            String string2 = typedArray.getString(7);
            int i7 = color;
            int dimensionPixelSize = typedArray.getDimensionPixelSize(9, 0);
            int dimensionPixelSize2 = typedArray.getDimensionPixelSize(6, 0);
            int dimensionPixelSize3 = typedArray.getDimensionPixelSize(5, 0);
            typedArray.recycle();
            i4 = dimensionPixelSize3;
            i2 = integer;
            str2 = string;
            str = string2;
            f3 = dimension5;
            f5 = dimension8;
            f2 = dimension3;
            drawable2 = drawable5;
            z2 = z3;
            i6 = dimensionPixelSize;
            f4 = f13;
            f7 = dimension2;
            colorStateList2 = colorStateList5;
            f6 = dimension9;
            f8 = dimension6;
            f10 = dimension7;
            i3 = i7;
            f9 = dimension4;
            i5 = dimensionPixelSize2;
            ColorStateList colorStateList6 = colorStateList4;
            drawable = drawable3;
            colorStateList = colorStateList6;
        } else {
            str2 = null;
            str = null;
            i6 = 0;
            i5 = 0;
            i4 = 0;
            i3 = 0;
            drawable2 = null;
            f10 = 0.0f;
            colorStateList2 = null;
            f9 = 0.0f;
            colorStateList = null;
            drawable = null;
            f8 = 0.0f;
            f7 = 0.0f;
            z2 = true;
            f6 = -1.0f;
            f5 = -1.0f;
            f4 = 1.8f;
            f3 = 0.0f;
            f2 = 0.0f;
            i2 = j.a.DEFAULT_SWIPE_ANIMATION_DURATION;
        }
        float f14 = f9;
        if (attributeSet2 == null) {
            f11 = f7;
            typedArray2 = null;
        } else {
            f11 = f7;
            typedArray2 = getContext().obtainStyledAttributes(attributeSet2, new int[]{16842970, 16842981});
        }
        if (typedArray2 != null) {
            colorStateList3 = colorStateList2;
            boolean z4 = typedArray2.getBoolean(0, true);
            boolean z5 = typedArray2.getBoolean(1, z4);
            setFocusable(z4);
            setClickable(z5);
            typedArray2.recycle();
        } else {
            colorStateList3 = colorStateList2;
            setFocusable(true);
            setClickable(true);
        }
        this.S = str2;
        this.T = str;
        this.c0 = i6;
        this.d0 = i5;
        this.e0 = i4;
        this.e = drawable;
        this.f1311h = colorStateList;
        this.G = drawable != null;
        this.o = i3;
        if (i3 == 0) {
            TypedValue typedValue = new TypedValue();
            if (getContext().getTheme().resolveAttribute(2130968721, typedValue, true)) {
                this.o = typedValue.data;
            } else {
                this.o = 3309506;
            }
        }
        if (!this.G && this.f1311h == null) {
            ColorStateList b = b(this.o);
            this.f1311h = b;
            this.t = b.getDefaultColor();
        }
        this.p = a((double) f8);
        this.q = a((double) f10);
        this.f1309f = drawable2;
        this.f1310g = colorStateList3;
        boolean z6 = drawable2 != null;
        this.H = z6;
        if (!z6 && this.f1310g == null) {
            ColorStateList a2 = a(this.o);
            this.f1310g = a2;
            int defaultColor = a2.getDefaultColor();
            this.u = defaultColor;
            this.v = this.f1310g.getColorForState(j0, defaultColor);
        }
        this.k.set(f11, f14, f2, f3);
        float f15 = f4;
        this.l = this.k.width() >= 0.0f ? Math.max(f15, 1.0f) : f15;
        this.f1312i = f5;
        this.f1313j = f6;
        long j2 = (long) i2;
        this.m = j2;
        this.n = z2;
        this.J.setDuration(j2);
        if (isChecked()) {
            setProgress(1.0f);
        }
    }

    private void b() {
        int i2;
        float f2;
        float f3;
        int i3 = this.p;
        if (i3 != 0 && (i2 = this.q) != 0 && this.r != 0 && this.s != 0) {
            if (this.f1312i == -1.0f) {
                this.f1312i = (float) (Math.min(i3, i2) / 2);
            }
            if (this.f1313j == -1.0f) {
                this.f1313j = (float) (Math.min(this.r, this.s) / 2);
            }
            int measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
            int measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
            int a2 = a((double) ((((float) this.r) - Math.min(0.0f, this.k.left)) - Math.min(0.0f, this.k.right)));
            int a3 = a((double) ((((float) this.s) - Math.min(0.0f, this.k.top)) - Math.min(0.0f, this.k.bottom)));
            if (measuredHeight <= a3) {
                f2 = ((float) getPaddingTop()) + Math.max(0.0f, this.k.top);
            } else {
                f2 = ((float) (((measuredHeight - a3) + 1) / 2)) + ((float) getPaddingTop()) + Math.max(0.0f, this.k.top);
            }
            if (measuredWidth <= this.r) {
                f3 = ((float) getPaddingLeft()) + Math.max(0.0f, this.k.left);
            } else {
                f3 = ((float) (((measuredWidth - a2) + 1) / 2)) + ((float) getPaddingLeft()) + Math.max(0.0f, this.k.left);
            }
            this.A.set(f3, f2, ((float) this.p) + f3, ((float) this.q) + f2);
            RectF rectF = this.A;
            float f4 = rectF.left;
            RectF rectF2 = this.k;
            float f5 = f4 - rectF2.left;
            RectF rectF3 = this.B;
            float f6 = rectF.top;
            float f7 = rectF2.top;
            rectF3.set(f5, f6 - f7, ((float) this.r) + f5, (f6 - f7) + ((float) this.s));
            RectF rectF4 = this.C;
            RectF rectF5 = this.A;
            rectF4.set(rectF5.left, 0.0f, (this.B.right - this.k.right) - rectF5.width(), 0.0f);
            this.f1313j = Math.min(Math.min(this.B.width(), this.B.height()) / 2.0f, this.f1313j);
            Drawable drawable = this.f1309f;
            if (drawable != null) {
                RectF rectF6 = this.B;
                drawable.setBounds((int) rectF6.left, (int) rectF6.top, a((double) rectF6.right), a((double) this.B.bottom));
            }
            if (this.V != null) {
                RectF rectF7 = this.B;
                float width = (rectF7.left + (((((rectF7.width() + ((float) this.c0)) - ((float) this.p)) - this.k.right) - ((float) this.V.getWidth())) / 2.0f)) - ((float) this.e0);
                RectF rectF8 = this.B;
                float height = rectF8.top + ((rectF8.height() - ((float) this.V.getHeight())) / 2.0f);
                this.D.set(width, height, ((float) this.V.getWidth()) + width, ((float) this.V.getHeight()) + height);
            }
            if (this.W != null) {
                RectF rectF9 = this.B;
                float width2 = ((rectF9.right - (((((rectF9.width() + ((float) this.c0)) - ((float) this.p)) - this.k.left) - ((float) this.W.getWidth())) / 2.0f)) - ((float) this.W.getWidth())) + ((float) this.e0);
                RectF rectF10 = this.B;
                float height2 = rectF10.top + ((rectF10.height() - ((float) this.W.getHeight())) / 2.0f);
                this.E.set(width2, height2, ((float) this.W.getWidth()) + width2, ((float) this.W.getHeight()) + height2);
            }
            this.g0 = true;
        }
    }

    private int c(int i2) {
        int size = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i2);
        if (this.q == 0 && this.G) {
            this.q = this.e.getIntrinsicHeight();
        }
        if (mode == 1073741824) {
            int i3 = this.q;
            if (i3 != 0) {
                RectF rectF = this.k;
                int a2 = a((double) (((float) i3) + rectF.top + rectF.bottom));
                this.s = a2;
                int a3 = a((double) Math.max((float) a2, this.b0));
                this.s = a3;
                if ((((float) ((a3 + getPaddingTop()) + getPaddingBottom())) - Math.min(0.0f, this.k.top)) - Math.min(0.0f, this.k.bottom) > ((float) size)) {
                    this.q = 0;
                }
            }
            if (this.q == 0) {
                int a4 = a((double) (((float) ((size - getPaddingTop()) - getPaddingBottom())) + Math.min(0.0f, this.k.top) + Math.min(0.0f, this.k.bottom)));
                this.s = a4;
                if (a4 < 0) {
                    this.s = 0;
                    this.q = 0;
                    return size;
                }
                RectF rectF2 = this.k;
                this.q = a((double) ((((float) a4) - rectF2.top) - rectF2.bottom));
            }
            if (this.q >= 0) {
                return size;
            }
            this.s = 0;
            this.q = 0;
            return size;
        }
        if (this.q == 0) {
            this.q = a((double) (getResources().getDisplayMetrics().density * 20.0f));
        }
        RectF rectF3 = this.k;
        int a5 = a((double) (((float) this.q) + rectF3.top + rectF3.bottom));
        this.s = a5;
        if (a5 < 0) {
            this.s = 0;
            this.q = 0;
            return size;
        }
        int a6 = a((double) (this.b0 - ((float) a5)));
        if (a6 > 0) {
            this.s += a6;
            this.q += a6;
        }
        int max = Math.max(this.q, this.s);
        return Math.max(Math.max(max, getPaddingTop() + max + getPaddingBottom()), getSuggestedMinimumHeight());
    }

    private int d(int i2) {
        int size = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i2);
        if (this.p == 0 && this.G) {
            this.p = this.e.getIntrinsicWidth();
        }
        int a2 = a((double) this.a0);
        if (this.l == 0.0f) {
            this.l = 1.8f;
        }
        if (mode == 1073741824) {
            int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
            int i3 = this.p;
            if (i3 != 0) {
                int a3 = a((double) (((float) i3) * this.l));
                RectF rectF = this.k;
                int a4 = (this.d0 + a2) - ((a3 - this.p) + a((double) Math.max(rectF.left, rectF.right)));
                float f2 = (float) a3;
                RectF rectF2 = this.k;
                int a5 = a((double) (rectF2.left + f2 + rectF2.right + ((float) Math.max(a4, 0))));
                this.r = a5;
                if (a5 < 0) {
                    this.p = 0;
                }
                if (f2 + Math.max(this.k.left, 0.0f) + Math.max(this.k.right, 0.0f) + ((float) Math.max(a4, 0)) > ((float) paddingLeft)) {
                    this.p = 0;
                }
            }
            if (this.p != 0) {
                return size;
            }
            int a6 = a((double) ((((float) ((size - getPaddingLeft()) - getPaddingRight())) - Math.max(this.k.left, 0.0f)) - Math.max(this.k.right, 0.0f)));
            if (a6 < 0) {
                this.p = 0;
                this.r = 0;
                return size;
            }
            float f3 = (float) a6;
            this.p = a((double) (f3 / this.l));
            RectF rectF3 = this.k;
            int a7 = a((double) (f3 + rectF3.left + rectF3.right));
            this.r = a7;
            if (a7 < 0) {
                this.p = 0;
                this.r = 0;
                return size;
            }
            int i4 = a2 + this.d0;
            int i5 = a6 - this.p;
            RectF rectF4 = this.k;
            int a8 = i4 - (i5 + a((double) Math.max(rectF4.left, rectF4.right)));
            if (a8 > 0) {
                this.p -= a8;
            }
            if (this.p >= 0) {
                return size;
            }
            this.p = 0;
            this.r = 0;
            return size;
        }
        if (this.p == 0) {
            this.p = a((double) (getResources().getDisplayMetrics().density * 20.0f));
        }
        if (this.l == 0.0f) {
            this.l = 1.8f;
        }
        int a9 = a((double) (((float) this.p) * this.l));
        RectF rectF5 = this.k;
        int a10 = a((double) (((float) (a2 + this.d0)) - ((((float) (a9 - this.p)) + Math.max(rectF5.left, rectF5.right)) + ((float) this.c0))));
        float f4 = (float) a9;
        RectF rectF6 = this.k;
        int a11 = a((double) (rectF6.left + f4 + rectF6.right + ((float) Math.max(0, a10))));
        this.r = a11;
        if (a11 < 0) {
            this.p = 0;
            this.r = 0;
            return size;
        }
        int a12 = a((double) (f4 + Math.max(0.0f, this.k.left) + Math.max(0.0f, this.k.right) + ((float) Math.max(0, a10))));
        return Math.max(a12, getPaddingLeft() + a12 + getPaddingRight());
    }

    private float getProgress() {
        return this.K;
    }

    private boolean getStatusBasedOnPos() {
        return getProgress() > 0.5f;
    }

    private void setDrawableState(Drawable drawable) {
        if (drawable != null) {
            drawable.setState(getDrawableState());
            invalidate();
        }
    }

    private void setProgress(float f2) {
        if (f2 > 1.0f) {
            f2 = 1.0f;
        } else if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        this.K = f2;
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        super.drawableStateChanged();
        if (this.G || (colorStateList2 = this.f1311h) == null) {
            setDrawableState(this.e);
        } else {
            this.t = colorStateList2.getColorForState(getDrawableState(), this.t);
        }
        int[] iArr = isChecked() ? k0 : j0;
        ColorStateList textColors = getTextColors();
        if (textColors != null) {
            int defaultColor = textColors.getDefaultColor();
            this.w = textColors.getColorForState(j0, defaultColor);
            this.x = textColors.getColorForState(k0, defaultColor);
        }
        if (this.H || (colorStateList = this.f1310g) == null) {
            Drawable drawable = this.f1309f;
            if (!(drawable instanceof StateListDrawable) || !this.n) {
                this.z = null;
            } else {
                drawable.setState(iArr);
                this.z = this.f1309f.getCurrent().mutate();
            }
            setDrawableState(this.f1309f);
            Drawable drawable2 = this.f1309f;
            if (drawable2 != null) {
                this.y = drawable2.getCurrent().mutate();
                return;
            }
            return;
        }
        int colorForState = colorStateList.getColorForState(getDrawableState(), this.u);
        this.u = colorForState;
        this.v = this.f1310g.getColorForState(iArr, colorForState);
    }

    public long getAnimationDuration() {
        return this.m;
    }

    public ColorStateList getBackColor() {
        return this.f1310g;
    }

    public Drawable getBackDrawable() {
        return this.f1309f;
    }

    public float getBackRadius() {
        return this.f1313j;
    }

    public PointF getBackSizeF() {
        return new PointF(this.B.width(), this.B.height());
    }

    public CharSequence getTextOff() {
        return this.T;
    }

    public CharSequence getTextOn() {
        return this.S;
    }

    public ColorStateList getThumbColor() {
        return this.f1311h;
    }

    public Drawable getThumbDrawable() {
        return this.e;
    }

    public float getThumbHeight() {
        return (float) this.q;
    }

    public RectF getThumbMargin() {
        return this.k;
    }

    public float getThumbRadius() {
        return this.f1312i;
    }

    public float getThumbRangeRatio() {
        return this.l;
    }

    public float getThumbWidth() {
        return (float) this.p;
    }

    public int getTintColor() {
        return this.o;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x012e  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0131  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDraw(android.graphics.Canvas r14) {
        /*
            r13 = this;
            super.onDraw(r14)
            boolean r0 = r13.g0
            if (r0 != 0) goto L_0x000a
            r13.b()
        L_0x000a:
            boolean r0 = r13.g0
            if (r0 != 0) goto L_0x000f
            return
        L_0x000f:
            boolean r0 = r13.H
            r1 = 1132396544(0x437f0000, float:255.0)
            r2 = 255(0xff, float:3.57E-43)
            if (r0 == 0) goto L_0x005c
            boolean r0 = r13.n
            if (r0 == 0) goto L_0x0050
            android.graphics.drawable.Drawable r0 = r13.y
            if (r0 == 0) goto L_0x0050
            android.graphics.drawable.Drawable r0 = r13.z
            if (r0 == 0) goto L_0x0050
            boolean r0 = r13.isChecked()
            if (r0 == 0) goto L_0x002c
            android.graphics.drawable.Drawable r0 = r13.y
            goto L_0x002e
        L_0x002c:
            android.graphics.drawable.Drawable r0 = r13.z
        L_0x002e:
            boolean r3 = r13.isChecked()
            if (r3 == 0) goto L_0x0037
            android.graphics.drawable.Drawable r3 = r13.z
            goto L_0x0039
        L_0x0037:
            android.graphics.drawable.Drawable r3 = r13.y
        L_0x0039:
            float r4 = r13.getProgress()
            float r4 = r4 * r1
            int r4 = (int) r4
            r0.setAlpha(r4)
            r0.draw(r14)
            int r0 = 255 - r4
            r3.setAlpha(r0)
            r3.draw(r14)
            goto L_0x00d7
        L_0x0050:
            android.graphics.drawable.Drawable r0 = r13.f1309f
            r0.setAlpha(r2)
            android.graphics.drawable.Drawable r0 = r13.f1309f
            r0.draw(r14)
            goto L_0x00d7
        L_0x005c:
            boolean r0 = r13.n
            if (r0 == 0) goto L_0x00c7
            boolean r0 = r13.isChecked()
            if (r0 == 0) goto L_0x0069
            int r0 = r13.u
            goto L_0x006b
        L_0x0069:
            int r0 = r13.v
        L_0x006b:
            boolean r3 = r13.isChecked()
            if (r3 == 0) goto L_0x0074
            int r3 = r13.v
            goto L_0x0076
        L_0x0074:
            int r3 = r13.u
        L_0x0076:
            float r4 = r13.getProgress()
            float r4 = r4 * r1
            int r4 = (int) r4
            int r5 = android.graphics.Color.alpha(r0)
            int r5 = r5 * r4
            int r5 = r5 / r2
            android.graphics.Paint r6 = r13.F
            int r7 = android.graphics.Color.red(r0)
            int r8 = android.graphics.Color.green(r0)
            int r0 = android.graphics.Color.blue(r0)
            r6.setARGB(r5, r7, r8, r0)
            android.graphics.RectF r0 = r13.B
            float r5 = r13.f1313j
            android.graphics.Paint r6 = r13.F
            r14.drawRoundRect(r0, r5, r5, r6)
            int r0 = 255 - r4
            int r4 = android.graphics.Color.alpha(r3)
            int r4 = r4 * r0
            int r4 = r4 / r2
            android.graphics.Paint r0 = r13.F
            int r5 = android.graphics.Color.red(r3)
            int r6 = android.graphics.Color.green(r3)
            int r3 = android.graphics.Color.blue(r3)
            r0.setARGB(r4, r5, r6, r3)
            android.graphics.RectF r0 = r13.B
            float r3 = r13.f1313j
            android.graphics.Paint r4 = r13.F
            r14.drawRoundRect(r0, r3, r3, r4)
            android.graphics.Paint r0 = r13.F
            r0.setAlpha(r2)
            goto L_0x00d7
        L_0x00c7:
            android.graphics.Paint r0 = r13.F
            int r3 = r13.u
            r0.setColor(r3)
            android.graphics.RectF r0 = r13.B
            float r3 = r13.f1313j
            android.graphics.Paint r4 = r13.F
            r14.drawRoundRect(r0, r3, r3, r4)
        L_0x00d7:
            float r0 = r13.getProgress()
            double r3 = (double) r0
            r5 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 <= 0) goto L_0x00e5
            android.text.Layout r0 = r13.V
            goto L_0x00e7
        L_0x00e5:
            android.text.Layout r0 = r13.W
        L_0x00e7:
            float r3 = r13.getProgress()
            double r3 = (double) r3
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 <= 0) goto L_0x00f3
            android.graphics.RectF r3 = r13.D
            goto L_0x00f5
        L_0x00f3:
            android.graphics.RectF r3 = r13.E
        L_0x00f5:
            r4 = 0
            if (r0 == 0) goto L_0x015d
            if (r3 == 0) goto L_0x015d
            float r7 = r13.getProgress()
            double r7 = (double) r7
            r9 = 4604930618986332160(0x3fe8000000000000, double:0.75)
            r11 = 1082130432(0x40800000, float:4.0)
            int r12 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            float r7 = r13.getProgress()
            if (r12 < 0) goto L_0x0111
            float r7 = r7 * r11
            r8 = 1077936128(0x40400000, float:3.0)
        L_0x010f:
            float r7 = r7 - r8
            goto L_0x0122
        L_0x0111:
            double r7 = (double) r7
            r9 = 4598175219545276416(0x3fd0000000000000, double:0.25)
            int r12 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r12 >= 0) goto L_0x0121
            r7 = 1065353216(0x3f800000, float:1.0)
            float r8 = r13.getProgress()
            float r8 = r8 * r11
            goto L_0x010f
        L_0x0121:
            r7 = 0
        L_0x0122:
            float r7 = r7 * r1
            int r1 = (int) r7
            float r7 = r13.getProgress()
            double r7 = (double) r7
            int r9 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r9 <= 0) goto L_0x0131
            int r7 = r13.w
            goto L_0x0133
        L_0x0131:
            int r7 = r13.x
        L_0x0133:
            int r8 = android.graphics.Color.alpha(r7)
            int r8 = r8 * r1
            int r8 = r8 / r2
            android.text.TextPaint r1 = r0.getPaint()
            int r2 = android.graphics.Color.red(r7)
            int r9 = android.graphics.Color.green(r7)
            int r7 = android.graphics.Color.blue(r7)
            r1.setARGB(r8, r2, r9, r7)
            r14.save()
            float r1 = r3.left
            float r2 = r3.top
            r14.translate(r1, r2)
            r0.draw(r14)
            r14.restore()
        L_0x015d:
            android.graphics.RectF r0 = r13.L
            android.graphics.RectF r1 = r13.A
            r0.set(r1)
            android.graphics.RectF r0 = r13.L
            float r1 = r13.K
            android.graphics.RectF r2 = r13.C
            float r2 = r2.width()
            float r1 = r1 * r2
            r0.offset(r1, r4)
            boolean r0 = r13.G
            if (r0 == 0) goto L_0x019a
            android.graphics.drawable.Drawable r0 = r13.e
            android.graphics.RectF r1 = r13.L
            float r2 = r1.left
            int r2 = (int) r2
            float r3 = r1.top
            int r3 = (int) r3
            float r1 = r1.right
            double r7 = (double) r1
            int r1 = r13.a((double) r7)
            android.graphics.RectF r4 = r13.L
            float r4 = r4.bottom
            double r7 = (double) r4
            int r4 = r13.a((double) r7)
            r0.setBounds(r2, r3, r1, r4)
            android.graphics.drawable.Drawable r0 = r13.e
            r0.draw(r14)
            goto L_0x01aa
        L_0x019a:
            android.graphics.Paint r0 = r13.F
            int r1 = r13.t
            r0.setColor(r1)
            android.graphics.RectF r0 = r13.L
            float r1 = r13.f1312i
            android.graphics.Paint r2 = r13.F
            r14.drawRoundRect(r0, r1, r1, r2)
        L_0x01aa:
            boolean r0 = r13.I
            if (r0 == 0) goto L_0x020c
            android.graphics.Paint r0 = r13.R
            java.lang.String r1 = "#AA0000"
            int r1 = android.graphics.Color.parseColor(r1)
            r0.setColor(r1)
            android.graphics.RectF r0 = r13.B
            android.graphics.Paint r1 = r13.R
            r14.drawRect(r0, r1)
            android.graphics.Paint r0 = r13.R
            java.lang.String r1 = "#0000FF"
            int r1 = android.graphics.Color.parseColor(r1)
            r0.setColor(r1)
            android.graphics.RectF r0 = r13.L
            android.graphics.Paint r1 = r13.R
            r14.drawRect(r0, r1)
            android.graphics.Paint r0 = r13.R
            java.lang.String r1 = "#000000"
            int r1 = android.graphics.Color.parseColor(r1)
            r0.setColor(r1)
            android.graphics.RectF r0 = r13.C
            float r8 = r0.left
            android.graphics.RectF r1 = r13.A
            float r11 = r1.top
            float r10 = r0.right
            android.graphics.Paint r12 = r13.R
            r7 = r14
            r9 = r11
            r7.drawLine(r8, r9, r10, r11, r12)
            android.graphics.Paint r0 = r13.R
            java.lang.String r1 = "#00CC00"
            int r1 = android.graphics.Color.parseColor(r1)
            r0.setColor(r1)
            float r0 = r13.getProgress()
            double r0 = (double) r0
            int r2 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r2 <= 0) goto L_0x0205
            android.graphics.RectF r0 = r13.D
            goto L_0x0207
        L_0x0205:
            android.graphics.RectF r0 = r13.E
        L_0x0207:
            android.graphics.Paint r1 = r13.R
            r14.drawRect(r0, r1)
        L_0x020c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.chileaf.fitness.widget.SwitchButton.onDraw(android.graphics.Canvas):void");
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        if (this.V == null && !TextUtils.isEmpty(this.S)) {
            this.V = a(this.S);
        }
        if (this.W == null && !TextUtils.isEmpty(this.T)) {
            this.W = a(this.T);
        }
        Layout layout = this.V;
        float width = layout != null ? (float) layout.getWidth() : 0.0f;
        Layout layout2 = this.W;
        float width2 = layout2 != null ? (float) layout2.getWidth() : 0.0f;
        if (width == 0.0f && width2 == 0.0f) {
            this.a0 = 0.0f;
        } else {
            this.a0 = Math.max(width, width2);
        }
        Layout layout3 = this.V;
        float height = layout3 != null ? (float) layout3.getHeight() : 0.0f;
        Layout layout4 = this.W;
        float height2 = layout4 != null ? (float) layout4.getHeight() : 0.0f;
        if (height == 0.0f && height2 == 0.0f) {
            this.b0 = 0.0f;
        } else {
            this.b0 = Math.max(height, height2);
        }
        setMeasuredDimension(d(i2), c(i3));
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        a(savedState.e, savedState.f1314f);
        this.f0 = true;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.f0 = false;
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.e = this.S;
        savedState.f1314f = this.T;
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i2 != i4 || i3 != i5) {
            b();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0034, code lost:
        if (r0 != 3) goto L_0x00e8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r10) {
        /*
            r9 = this;
            boolean r0 = r9.isEnabled()
            r1 = 0
            if (r0 == 0) goto L_0x00e9
            boolean r0 = r9.isClickable()
            if (r0 == 0) goto L_0x00e9
            boolean r0 = r9.isFocusable()
            if (r0 == 0) goto L_0x00e9
            boolean r0 = r9.g0
            if (r0 != 0) goto L_0x0019
            goto L_0x00e9
        L_0x0019:
            int r0 = r10.getAction()
            float r2 = r10.getX()
            float r3 = r9.M
            float r2 = r2 - r3
            float r3 = r10.getY()
            float r4 = r9.N
            float r3 = r3 - r4
            r4 = 1
            if (r0 == 0) goto L_0x00d5
            if (r0 == r4) goto L_0x0090
            r5 = 2
            if (r0 == r5) goto L_0x0038
            r5 = 3
            if (r0 == r5) goto L_0x0090
            goto L_0x00e8
        L_0x0038:
            float r10 = r10.getX()
            float r0 = r9.getProgress()
            float r6 = r9.O
            float r6 = r10 - r6
            android.graphics.RectF r7 = r9.C
            float r7 = r7.width()
            float r6 = r6 / r7
            float r0 = r0 + r6
            r9.setProgress(r0)
            boolean r0 = r9.h0
            if (r0 != 0) goto L_0x008d
            float r0 = java.lang.Math.abs(r2)
            int r6 = r9.P
            int r6 = r6 / r5
            float r6 = (float) r6
            int r0 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r0 > 0) goto L_0x006b
            float r0 = java.lang.Math.abs(r3)
            int r6 = r9.P
            int r6 = r6 / r5
            float r5 = (float) r6
            int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r0 <= 0) goto L_0x008d
        L_0x006b:
            r0 = 0
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 == 0) goto L_0x008a
            float r0 = java.lang.Math.abs(r2)
            float r5 = java.lang.Math.abs(r3)
            int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r0 <= 0) goto L_0x007d
            goto L_0x008a
        L_0x007d:
            float r0 = java.lang.Math.abs(r3)
            float r2 = java.lang.Math.abs(r2)
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 <= 0) goto L_0x008d
            return r1
        L_0x008a:
            r9.a()
        L_0x008d:
            r9.O = r10
            goto L_0x00e8
        L_0x0090:
            r9.h0 = r1
            r9.setPressed(r1)
            long r5 = r10.getEventTime()
            long r7 = r10.getDownTime()
            long r5 = r5 - r7
            float r10 = (float) r5
            float r0 = java.lang.Math.abs(r2)
            int r2 = r9.P
            float r2 = (float) r2
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 >= 0) goto L_0x00c0
            float r0 = java.lang.Math.abs(r3)
            int r2 = r9.P
            float r2 = (float) r2
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 >= 0) goto L_0x00c0
            int r0 = r9.Q
            float r0 = (float) r0
            int r10 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r10 >= 0) goto L_0x00c0
            r9.performClick()
            goto L_0x00e8
        L_0x00c0:
            boolean r10 = r9.getStatusBasedOnPos()
            boolean r0 = r9.isChecked()
            if (r10 == r0) goto L_0x00d1
            r9.playSoundEffect(r1)
            r9.setChecked(r10)
            goto L_0x00e8
        L_0x00d1:
            r9.a((boolean) r10)
            goto L_0x00e8
        L_0x00d5:
            float r0 = r10.getX()
            r9.M = r0
            float r10 = r10.getY()
            r9.N = r10
            float r10 = r9.M
            r9.O = r10
            r9.setPressed(r4)
        L_0x00e8:
            return r4
        L_0x00e9:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.chileaf.fitness.widget.SwitchButton.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public boolean performClick() {
        return super.performClick();
    }

    public void setAnimationDuration(long j2) {
        this.m = j2;
    }

    public void setBackColor(ColorStateList colorStateList) {
        this.f1310g = colorStateList;
        if (colorStateList != null) {
            setBackDrawable((Drawable) null);
        }
        invalidate();
    }

    public void setBackColorRes(int i2) {
        setBackColor(androidx.core.content.a.b(getContext(), i2));
    }

    public void setBackDrawable(Drawable drawable) {
        this.f1309f = drawable;
        this.H = drawable != null;
        refreshDrawableState();
        this.g0 = false;
        requestLayout();
        invalidate();
    }

    public void setBackDrawableRes(int i2) {
        setBackDrawable(androidx.core.content.a.c(getContext(), i2));
    }

    public void setBackRadius(float f2) {
        this.f1313j = f2;
        if (!this.H) {
            invalidate();
        }
    }

    public void setChecked(boolean z2) {
        if (isChecked() != z2) {
            a(z2);
        }
        if (this.f0) {
            setCheckedImmediatelyNoEvent(z2);
        } else {
            super.setChecked(z2);
        }
    }

    public void setCheckedImmediately(boolean z2) {
        super.setChecked(z2);
        ObjectAnimator objectAnimator = this.J;
        if (objectAnimator != null && objectAnimator.isRunning()) {
            this.J.cancel();
        }
        setProgress(z2 ? 1.0f : 0.0f);
        invalidate();
    }

    public void setCheckedImmediatelyNoEvent(boolean z2) {
        if (this.i0 == null) {
            setCheckedImmediately(z2);
            return;
        }
        super.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
        setCheckedImmediately(z2);
        super.setOnCheckedChangeListener(this.i0);
    }

    public void setCheckedNoEvent(boolean z2) {
        if (this.i0 == null) {
            setChecked(z2);
            return;
        }
        super.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
        setChecked(z2);
        super.setOnCheckedChangeListener(this.i0);
    }

    public void setDrawDebugRect(boolean z2) {
        this.I = z2;
        invalidate();
    }

    public void setFadeBack(boolean z2) {
        this.n = z2;
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        super.setOnCheckedChangeListener(onCheckedChangeListener);
        this.i0 = onCheckedChangeListener;
    }

    public void setTextAdjust(int i2) {
        this.e0 = i2;
        this.g0 = false;
        requestLayout();
        invalidate();
    }

    public void setTextExtra(int i2) {
        this.d0 = i2;
        this.g0 = false;
        requestLayout();
        invalidate();
    }

    public void setTextThumbInset(int i2) {
        this.c0 = i2;
        this.g0 = false;
        requestLayout();
        invalidate();
    }

    public void setThumbColor(ColorStateList colorStateList) {
        this.f1311h = colorStateList;
        if (colorStateList != null) {
            setThumbDrawable((Drawable) null);
        }
        invalidate();
    }

    public void setThumbColorRes(int i2) {
        setThumbColor(androidx.core.content.a.b(getContext(), i2));
    }

    public void setThumbDrawable(Drawable drawable) {
        this.e = drawable;
        this.G = drawable != null;
        refreshDrawableState();
        this.g0 = false;
        requestLayout();
        invalidate();
    }

    public void setThumbDrawableRes(int i2) {
        setThumbDrawable(androidx.core.content.a.c(getContext(), i2));
    }

    public void setThumbMargin(RectF rectF) {
        if (rectF == null) {
            a(0.0f, 0.0f, 0.0f, 0.0f);
        } else {
            a(rectF.left, rectF.top, rectF.right, rectF.bottom);
        }
    }

    public void setThumbRadius(float f2) {
        this.f1312i = f2;
        if (!this.G) {
            invalidate();
        }
    }

    public void setThumbRangeRatio(float f2) {
        this.l = f2;
        this.g0 = false;
        requestLayout();
    }

    public void setTintColor(int i2) {
        this.o = i2;
        this.f1311h = b(i2);
        this.f1310g = a(this.o);
        this.H = false;
        this.G = false;
        refreshDrawableState();
        invalidate();
    }

    public SwitchButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(attributeSet);
    }

    public SwitchButton(Context context) {
        super(context);
        a((AttributeSet) null);
    }

    private ColorStateList b(int i2) {
        int i3 = i2 - -1728053248;
        return new ColorStateList(new int[][]{new int[]{-16842910, 16842912}, new int[]{-16842910}, new int[]{16842919, -16842912}, new int[]{16842919, 16842912}, new int[]{16842912}, new int[]{-16842912}}, new int[]{i2 - -1442840576, -4539718, i3, i3, i2 | -16777216, -1118482});
    }

    private Layout a(CharSequence charSequence) {
        TextPaint textPaint = this.U;
        return new StaticLayout(charSequence, textPaint, (int) Math.ceil((double) Layout.getDesiredWidth(charSequence, textPaint)), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
    }

    private int a(double d) {
        return (int) Math.ceil(d);
    }

    /* access modifiers changed from: protected */
    public void a(boolean z2) {
        ObjectAnimator objectAnimator = this.J;
        if (objectAnimator != null) {
            if (objectAnimator.isRunning()) {
                this.J.cancel();
            }
            this.J.setDuration(this.m);
            if (z2) {
                this.J.setFloatValues(new float[]{this.K, 1.0f});
            } else {
                this.J.setFloatValues(new float[]{this.K, 0.0f});
            }
            this.J.start();
        }
    }

    private void a() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        this.h0 = true;
    }

    public void a(float f2, float f3, float f4, float f5) {
        this.k.set(f2, f3, f4, f5);
        this.g0 = false;
        requestLayout();
    }

    public void a(CharSequence charSequence, CharSequence charSequence2) {
        this.S = charSequence;
        this.T = charSequence2;
        this.V = null;
        this.W = null;
        this.g0 = false;
        requestLayout();
        invalidate();
    }

    private ColorStateList a(int i2) {
        int i3 = i2 - -805306368;
        return new ColorStateList(new int[][]{new int[]{-16842910, 16842912}, new int[]{-16842910}, new int[]{16842912, 16842919}, new int[]{-16842912, 16842919}, new int[]{16842912}, new int[]{-16842912}}, new int[]{i2 - -520093696, 268435456, i3, 536870912, i3, 536870912});
    }
}
