package com.google.android.material.f;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.TextPaint;
import android.util.Log;
import androidx.core.content.c.f;
import com.google.android.material.R$styleable;

/* compiled from: TextAppearance */
public class b {
    public final float a;
    public final ColorStateList b;
    public final int c;
    public final int d;
    public final String e;

    /* renamed from: f  reason: collision with root package name */
    public final ColorStateList f1466f;

    /* renamed from: g  reason: collision with root package name */
    public final float f1467g;

    /* renamed from: h  reason: collision with root package name */
    public final float f1468h;

    /* renamed from: i  reason: collision with root package name */
    public final float f1469i;

    /* renamed from: j  reason: collision with root package name */
    private final int f1470j;
    /* access modifiers changed from: private */
    public boolean k = false;
    /* access modifiers changed from: private */
    public Typeface l;

    public b(Context context, int i2) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(i2, R$styleable.TextAppearance);
        this.a = obtainStyledAttributes.getDimension(R$styleable.TextAppearance_android_textSize, 0.0f);
        this.b = a.a(context, obtainStyledAttributes, R$styleable.TextAppearance_android_textColor);
        a.a(context, obtainStyledAttributes, R$styleable.TextAppearance_android_textColorHint);
        a.a(context, obtainStyledAttributes, R$styleable.TextAppearance_android_textColorLink);
        this.c = obtainStyledAttributes.getInt(R$styleable.TextAppearance_android_textStyle, 0);
        this.d = obtainStyledAttributes.getInt(R$styleable.TextAppearance_android_typeface, 1);
        int a2 = a.a(obtainStyledAttributes, R$styleable.TextAppearance_fontFamily, R$styleable.TextAppearance_android_fontFamily);
        this.f1470j = obtainStyledAttributes.getResourceId(a2, 0);
        this.e = obtainStyledAttributes.getString(a2);
        obtainStyledAttributes.getBoolean(R$styleable.TextAppearance_textAllCaps, false);
        this.f1466f = a.a(context, obtainStyledAttributes, R$styleable.TextAppearance_android_shadowColor);
        this.f1467g = obtainStyledAttributes.getFloat(R$styleable.TextAppearance_android_shadowDx, 0.0f);
        this.f1468h = obtainStyledAttributes.getFloat(R$styleable.TextAppearance_android_shadowDy, 0.0f);
        this.f1469i = obtainStyledAttributes.getFloat(R$styleable.TextAppearance_android_shadowRadius, 0.0f);
        obtainStyledAttributes.recycle();
    }

    public void b(Context context, TextPaint textPaint, f.a aVar) {
        c(context, textPaint, aVar);
        ColorStateList colorStateList = this.b;
        textPaint.setColor(colorStateList != null ? colorStateList.getColorForState(textPaint.drawableState, colorStateList.getDefaultColor()) : -16777216);
        float f2 = this.f1469i;
        float f3 = this.f1467g;
        float f4 = this.f1468h;
        ColorStateList colorStateList2 = this.f1466f;
        textPaint.setShadowLayer(f2, f3, f4, colorStateList2 != null ? colorStateList2.getColorForState(textPaint.drawableState, colorStateList2.getDefaultColor()) : 0);
    }

    public void c(Context context, TextPaint textPaint, f.a aVar) {
        if (c.a()) {
            a(textPaint, a(context));
            return;
        }
        a(context, textPaint, aVar);
        if (!this.k) {
            a(textPaint, this.l);
        }
    }

    /* compiled from: TextAppearance */
    class a extends f.a {
        final /* synthetic */ TextPaint a;
        final /* synthetic */ f.a b;

        a(TextPaint textPaint, f.a aVar) {
            this.a = textPaint;
            this.b = aVar;
        }

        public void a(Typeface typeface) {
            b bVar = b.this;
            Typeface unused = bVar.l = Typeface.create(typeface, bVar.c);
            b.this.a(this.a, typeface);
            boolean unused2 = b.this.k = true;
            this.b.a(typeface);
        }

        public void a(int i2) {
            b.this.a();
            boolean unused = b.this.k = true;
            this.b.a(i2);
        }
    }

    public Typeface a(Context context) {
        if (this.k) {
            return this.l;
        }
        if (!context.isRestricted()) {
            try {
                Typeface a2 = f.a(context, this.f1470j);
                this.l = a2;
                if (a2 != null) {
                    this.l = Typeface.create(a2, this.c);
                }
            } catch (Resources.NotFoundException | UnsupportedOperationException unused) {
            } catch (Exception e2) {
                Log.d("TextAppearance", "Error loading font " + this.e, e2);
            }
        }
        a();
        this.k = true;
        return this.l;
    }

    public void a(Context context, TextPaint textPaint, f.a aVar) {
        if (this.k) {
            a(textPaint, this.l);
            return;
        }
        a();
        if (context.isRestricted()) {
            this.k = true;
            a(textPaint, this.l);
            return;
        }
        try {
            f.a(context, this.f1470j, new a(textPaint, aVar), (Handler) null);
        } catch (Resources.NotFoundException | UnsupportedOperationException unused) {
        } catch (Exception e2) {
            Log.d("TextAppearance", "Error loading font " + this.e, e2);
        }
    }

    /* access modifiers changed from: private */
    public void a() {
        if (this.l == null) {
            this.l = Typeface.create(this.e, this.c);
        }
        if (this.l == null) {
            int i2 = this.d;
            if (i2 == 1) {
                this.l = Typeface.SANS_SERIF;
            } else if (i2 == 2) {
                this.l = Typeface.SERIF;
            } else if (i2 != 3) {
                this.l = Typeface.DEFAULT;
            } else {
                this.l = Typeface.MONOSPACE;
            }
            Typeface typeface = this.l;
            if (typeface != null) {
                this.l = Typeface.create(typeface, this.c);
            }
        }
    }

    public void a(TextPaint textPaint, Typeface typeface) {
        textPaint.setTypeface(typeface);
        int style = (typeface.getStyle() ^ -1) & this.c;
        textPaint.setFakeBoldText((style & 1) != 0);
        textPaint.setTextSkewX((style & 2) != 0 ? -0.25f : 0.0f);
        textPaint.setTextSize(this.a);
    }
}
