package com.afollestad.materialdialogs.j;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.i;

/* compiled from: MDUtil.kt */
public final class e {
    public static final e a = new e();

    /* compiled from: MDUtil.kt */
    public static final class a implements ViewTreeObserver.OnGlobalLayoutListener {
        private Integer e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ View f1005f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ l f1006g;

        a(T t, l lVar) {
            this.f1005f = t;
            this.f1006g = lVar;
        }

        public void onGlobalLayout() {
            Integer num = this.e;
            if (num != null) {
                int measuredWidth = this.f1005f.getMeasuredWidth();
                if (num != null && num.intValue() == measuredWidth) {
                    this.f1005f.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    return;
                }
            }
            if (this.f1005f.getMeasuredWidth() > 0 && this.f1005f.getMeasuredHeight() > 0) {
                Integer num2 = this.e;
                int measuredWidth2 = this.f1005f.getMeasuredWidth();
                if (num2 == null || num2.intValue() != measuredWidth2) {
                    this.e = Integer.valueOf(this.f1005f.getMeasuredWidth());
                    this.f1006g.invoke(this.f1005f);
                }
            }
        }
    }

    private e() {
    }

    public static /* synthetic */ CharSequence a(e eVar, MaterialDialog materialDialog, Integer num, Integer num2, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            num = null;
        }
        if ((i2 & 4) != 0) {
            num2 = null;
        }
        if ((i2 & 8) != 0) {
            z = false;
        }
        return eVar.a(materialDialog, num, num2, z);
    }

    public final CharSequence a(MaterialDialog materialDialog, Integer num, Integer num2, boolean z) {
        i.b(materialDialog, "materialDialog");
        return a(materialDialog.f(), num, num2, z);
    }

    public final CharSequence a(Context context, Integer num, Integer num2, boolean z) {
        i.b(context, "context");
        int intValue = num != null ? num.intValue() : num2 != null ? num2.intValue() : 0;
        if (intValue == 0) {
            return null;
        }
        CharSequence text = context.getResources().getText(intValue);
        i.a((Object) text, "context.resources.getText(resourceId)");
        return z ? Html.fromHtml(text.toString()) : text;
    }

    public static /* synthetic */ Drawable a(e eVar, Context context, Integer num, Integer num2, Drawable drawable, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            num = null;
        }
        if ((i2 & 4) != 0) {
            num2 = null;
        }
        if ((i2 & 8) != 0) {
            drawable = null;
        }
        return eVar.a(context, num, num2, drawable);
    }

    public final Drawable a(Context context, Integer num, Integer num2, Drawable drawable) {
        i.b(context, "context");
        if (num2 != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{num2.intValue()});
            try {
                Drawable drawable2 = obtainStyledAttributes.getDrawable(0);
                if (drawable2 != null || drawable == null) {
                    drawable = drawable2;
                }
                return drawable;
            } finally {
                obtainStyledAttributes.recycle();
            }
        } else if (num == null) {
            return drawable;
        } else {
            return androidx.core.content.a.c(context, num.intValue());
        }
    }

    public static /* synthetic */ int a(e eVar, Context context, Integer num, Integer num2, kotlin.jvm.b.a aVar, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            num = null;
        }
        if ((i2 & 4) != 0) {
            num2 = null;
        }
        if ((i2 & 8) != 0) {
            aVar = null;
        }
        return eVar.a(context, num, num2, (kotlin.jvm.b.a<Integer>) aVar);
    }

    public final int a(Context context, Integer num, Integer num2, kotlin.jvm.b.a<Integer> aVar) {
        i.b(context, "context");
        int i2 = 0;
        if (num2 != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{num2.intValue()});
            try {
                int color = obtainStyledAttributes.getColor(0, 0);
                if (color == 0 && aVar != null) {
                    return aVar.invoke().intValue();
                }
                obtainStyledAttributes.recycle();
                return color;
            } finally {
                obtainStyledAttributes.recycle();
            }
        } else {
            if (num != null) {
                i2 = num.intValue();
            }
            return androidx.core.content.a.a(context, i2);
        }
    }

    public final int a(Context context, int i2, int i3) {
        i.b(context, "context");
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i2});
        try {
            return obtainStyledAttributes.getInt(0, i3);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public final float a(Context context, int i2, kotlin.jvm.b.a<Float> aVar) {
        float f2;
        i.b(context, "context");
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i2});
        if (aVar != null) {
            try {
                Float invoke = aVar.invoke();
                if (invoke != null) {
                    f2 = invoke.floatValue();
                    float dimension = obtainStyledAttributes.getDimension(0, f2);
                    obtainStyledAttributes.recycle();
                    return dimension;
                }
            } catch (Throwable th) {
                obtainStyledAttributes.recycle();
                throw th;
            }
        }
        f2 = 0.0f;
        float dimension2 = obtainStyledAttributes.getDimension(0, f2);
        obtainStyledAttributes.recycle();
        return dimension2;
    }

    public final float a(Context context, int i2, float f2) {
        i.b(context, "context");
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i2});
        try {
            return obtainStyledAttributes.getFloat(0, f2);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public static /* synthetic */ boolean a(e eVar, int i2, double d, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            d = 0.5d;
        }
        return eVar.a(i2, d);
    }

    public final boolean a(int i2, double d) {
        if (i2 == 0) {
            return false;
        }
        double d2 = (double) 1;
        double red = (double) Color.red(i2);
        Double.isNaN(red);
        double green = (double) Color.green(i2);
        Double.isNaN(green);
        double d3 = (red * 0.299d) + (green * 0.587d);
        double blue = (double) Color.blue(i2);
        Double.isNaN(blue);
        double d4 = (double) 255;
        Double.isNaN(d4);
        Double.isNaN(d2);
        return d2 - ((d3 + (blue * 0.114d)) / d4) >= d;
    }

    public final <T extends View> int a(T t, int i2) {
        i.b(t, "$this$dimenPx");
        Context context = t.getContext();
        i.a((Object) context, "context");
        return context.getResources().getDimensionPixelSize(i2);
    }

    public static /* synthetic */ void a(e eVar, TextView textView, Context context, Integer num, Integer num2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            num2 = null;
        }
        eVar.a(textView, context, num, num2);
    }

    public final void a(TextView textView, Context context, Integer num, Integer num2) {
        int a2;
        int a3;
        i.b(context, "context");
        if (textView == null) {
            return;
        }
        if (num != null || num2 != null) {
            if (!(num == null || (a3 = a(this, context, (Integer) null, num, (kotlin.jvm.b.a) null, 10, (Object) null)) == 0)) {
                textView.setTextColor(a3);
            }
            if (num2 != null && (a2 = a(this, context, (Integer) null, num2, (kotlin.jvm.b.a) null, 10, (Object) null)) != 0) {
                textView.setHintTextColor(a2);
            }
        }
    }

    public final int a(TextView textView) {
        i.b(textView, "$this$additionalPaddingForFont");
        TextPaint paint = textView.getPaint();
        i.a((Object) paint, "paint");
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float f2 = fontMetrics.descent - fontMetrics.ascent;
        if (f2 > ((float) textView.getMeasuredHeight())) {
            return (int) (f2 - ((float) textView.getMeasuredHeight()));
        }
        return 0;
    }

    public final <T extends View> void a(T t, l<? super T, kotlin.l> lVar) {
        i.b(t, "$this$waitForWidth");
        i.b(lVar, "block");
        if (t.getMeasuredWidth() <= 0 || t.getMeasuredHeight() <= 0) {
            t.getViewTreeObserver().addOnGlobalLayoutListener(new a(t, lVar));
        } else {
            lVar.invoke(t);
        }
    }

    public final Pair<Integer, Integer> a(WindowManager windowManager) {
        i.b(windowManager, "$this$getWidthAndHeight");
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return new Pair<>(Integer.valueOf(point.x), Integer.valueOf(point.y));
    }

    public static /* synthetic */ void a(e eVar, View view, int i2, int i3, int i4, int i5, int i6, Object obj) {
        if ((i6 & 1) != 0) {
            i2 = view != null ? view.getPaddingLeft() : 0;
        }
        int i7 = i2;
        if ((i6 & 2) != 0) {
            i3 = view != null ? view.getPaddingTop() : 0;
        }
        int i8 = i3;
        if ((i6 & 4) != 0) {
            i4 = view != null ? view.getPaddingRight() : 0;
        }
        int i9 = i4;
        if ((i6 & 8) != 0) {
            i5 = view != null ? view.getPaddingBottom() : 0;
        }
        eVar.a(view, i7, i8, i9, i5);
    }

    public final <T extends View> void a(T t, int i2, int i3, int i4, int i5) {
        if ((t == null || i2 != t.getPaddingLeft() || i3 != t.getPaddingTop() || i4 != t.getPaddingRight() || i5 != t.getPaddingBottom()) && t != null) {
            t.setPadding(i2, i3, i4, i5);
        }
    }

    public final void a(String str, Object obj, Integer num) {
        i.b(str, "method");
        if (num == null && obj == null) {
            throw new IllegalArgumentException(str + ": You must specify a resource ID or literal value");
        }
    }

    public final String[] a(Context context, Integer num) {
        i.b(context, "$this$getStringArray");
        if (num == null) {
            return new String[0];
        }
        String[] stringArray = context.getResources().getStringArray(num.intValue());
        i.a((Object) stringArray, "resources.getStringArray(res)");
        return stringArray;
    }

    public final <R extends View> R a(ViewGroup viewGroup, Context context, int i2) {
        i.b(viewGroup, "$this$inflate");
        i.b(context, "ctxt");
        R inflate = LayoutInflater.from(context).inflate(i2, viewGroup, false);
        if (inflate != null) {
            return inflate;
        }
        throw new TypeCastException("null cannot be cast to non-null type R");
    }
}
