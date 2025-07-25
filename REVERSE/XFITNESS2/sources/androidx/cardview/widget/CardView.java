package androidx.cardview.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.cardview.R$attr;
import androidx.cardview.R$color;
import androidx.cardview.R$style;
import androidx.cardview.R$styleable;

public class CardView extends FrameLayout {
    private static final int[] l = {16842801};
    private static final e m;
    private boolean e;

    /* renamed from: f  reason: collision with root package name */
    private boolean f340f;

    /* renamed from: g  reason: collision with root package name */
    int f341g;

    /* renamed from: h  reason: collision with root package name */
    int f342h;

    /* renamed from: i  reason: collision with root package name */
    final Rect f343i;

    /* renamed from: j  reason: collision with root package name */
    final Rect f344j;
    private final d k;

    static {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 21) {
            m = new b();
        } else if (i2 >= 17) {
            m = new a();
        } else {
            m = new c();
        }
        m.a();
    }

    public CardView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ColorStateList getCardBackgroundColor() {
        return m.f(this.k);
    }

    public float getCardElevation() {
        return m.h(this.k);
    }

    public int getContentPaddingBottom() {
        return this.f343i.bottom;
    }

    public int getContentPaddingLeft() {
        return this.f343i.left;
    }

    public int getContentPaddingRight() {
        return this.f343i.right;
    }

    public int getContentPaddingTop() {
        return this.f343i.top;
    }

    public float getMaxCardElevation() {
        return m.a(this.k);
    }

    public boolean getPreventCornerOverlap() {
        return this.f340f;
    }

    public float getRadius() {
        return m.b(this.k);
    }

    public boolean getUseCompatPadding() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        if (!(m instanceof b)) {
            int mode = View.MeasureSpec.getMode(i2);
            if (mode == Integer.MIN_VALUE || mode == 1073741824) {
                i2 = View.MeasureSpec.makeMeasureSpec(Math.max((int) Math.ceil((double) m.d(this.k)), View.MeasureSpec.getSize(i2)), mode);
            }
            int mode2 = View.MeasureSpec.getMode(i3);
            if (mode2 == Integer.MIN_VALUE || mode2 == 1073741824) {
                i3 = View.MeasureSpec.makeMeasureSpec(Math.max((int) Math.ceil((double) m.c(this.k)), View.MeasureSpec.getSize(i3)), mode2);
            }
            super.onMeasure(i2, i3);
            return;
        }
        super.onMeasure(i2, i3);
    }

    public void setCardBackgroundColor(int i2) {
        m.a(this.k, ColorStateList.valueOf(i2));
    }

    public void setCardElevation(float f2) {
        m.a(this.k, f2);
    }

    public void setMaxCardElevation(float f2) {
        m.c(this.k, f2);
    }

    public void setMinimumHeight(int i2) {
        this.f342h = i2;
        super.setMinimumHeight(i2);
    }

    public void setMinimumWidth(int i2) {
        this.f341g = i2;
        super.setMinimumWidth(i2);
    }

    public void setPadding(int i2, int i3, int i4, int i5) {
    }

    public void setPaddingRelative(int i2, int i3, int i4, int i5) {
    }

    public void setPreventCornerOverlap(boolean z) {
        if (z != this.f340f) {
            this.f340f = z;
            m.g(this.k);
        }
    }

    public void setRadius(float f2) {
        m.b(this.k, f2);
    }

    public void setUseCompatPadding(boolean z) {
        if (this.e != z) {
            this.e = z;
            m.e(this.k);
        }
    }

    class a implements d {
        private Drawable a;

        a() {
        }

        public void a(Drawable drawable) {
            this.a = drawable;
            CardView.this.setBackgroundDrawable(drawable);
        }

        public boolean b() {
            return CardView.this.getUseCompatPadding();
        }

        public Drawable c() {
            return this.a;
        }

        public boolean d() {
            return CardView.this.getPreventCornerOverlap();
        }

        public void a(int i2, int i3, int i4, int i5) {
            CardView.this.f344j.set(i2, i3, i4, i5);
            CardView cardView = CardView.this;
            Rect rect = cardView.f343i;
            CardView.super.setPadding(i2 + rect.left, i3 + rect.top, i4 + rect.right, i5 + rect.bottom);
        }

        public void a(int i2, int i3) {
            CardView cardView = CardView.this;
            if (i2 > cardView.f341g) {
                CardView.super.setMinimumWidth(i2);
            }
            CardView cardView2 = CardView.this;
            if (i3 > cardView2.f342h) {
                CardView.super.setMinimumHeight(i3);
            }
        }

        public View a() {
            return CardView.this;
        }
    }

    public CardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.cardViewStyle);
    }

    public void setCardBackgroundColor(ColorStateList colorStateList) {
        m.a(this.k, colorStateList);
    }

    public CardView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        int i3;
        ColorStateList valueOf;
        this.f343i = new Rect();
        this.f344j = new Rect();
        this.k = new a();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.CardView, i2, R$style.CardView);
        if (obtainStyledAttributes.hasValue(R$styleable.CardView_cardBackgroundColor)) {
            valueOf = obtainStyledAttributes.getColorStateList(R$styleable.CardView_cardBackgroundColor);
        } else {
            TypedArray obtainStyledAttributes2 = getContext().obtainStyledAttributes(l);
            int color = obtainStyledAttributes2.getColor(0, 0);
            obtainStyledAttributes2.recycle();
            float[] fArr = new float[3];
            Color.colorToHSV(color, fArr);
            if (fArr[2] > 0.5f) {
                i3 = getResources().getColor(R$color.cardview_light_background);
            } else {
                i3 = getResources().getColor(R$color.cardview_dark_background);
            }
            valueOf = ColorStateList.valueOf(i3);
        }
        ColorStateList colorStateList = valueOf;
        float dimension = obtainStyledAttributes.getDimension(R$styleable.CardView_cardCornerRadius, 0.0f);
        float dimension2 = obtainStyledAttributes.getDimension(R$styleable.CardView_cardElevation, 0.0f);
        float dimension3 = obtainStyledAttributes.getDimension(R$styleable.CardView_cardMaxElevation, 0.0f);
        this.e = obtainStyledAttributes.getBoolean(R$styleable.CardView_cardUseCompatPadding, false);
        this.f340f = obtainStyledAttributes.getBoolean(R$styleable.CardView_cardPreventCornerOverlap, true);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R$styleable.CardView_contentPadding, 0);
        this.f343i.left = obtainStyledAttributes.getDimensionPixelSize(R$styleable.CardView_contentPaddingLeft, dimensionPixelSize);
        this.f343i.top = obtainStyledAttributes.getDimensionPixelSize(R$styleable.CardView_contentPaddingTop, dimensionPixelSize);
        this.f343i.right = obtainStyledAttributes.getDimensionPixelSize(R$styleable.CardView_contentPaddingRight, dimensionPixelSize);
        this.f343i.bottom = obtainStyledAttributes.getDimensionPixelSize(R$styleable.CardView_contentPaddingBottom, dimensionPixelSize);
        float f2 = dimension2 > dimension3 ? dimension2 : dimension3;
        this.f341g = obtainStyledAttributes.getDimensionPixelSize(R$styleable.CardView_android_minWidth, 0);
        this.f342h = obtainStyledAttributes.getDimensionPixelSize(R$styleable.CardView_android_minHeight, 0);
        obtainStyledAttributes.recycle();
        m.a(this.k, context, colorStateList, dimension, dimension2, f2);
    }

    public void a(int i2, int i3, int i4, int i5) {
        this.f343i.set(i2, i3, i4, i5);
        m.i(this.k);
    }
}
