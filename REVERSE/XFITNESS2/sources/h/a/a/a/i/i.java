package h.a.a.a.i;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import h.a.a.a.c.c;
import h.a.a.a.c.e;

/* compiled from: Utils */
public abstract class i {
    private static DisplayMetrics a = null;
    private static int b = 50;
    private static int c = 8000;
    public static final float d = Float.intBitsToFloat(1);
    private static Rect e = new Rect();

    /* renamed from: f  reason: collision with root package name */
    private static Paint.FontMetrics f1737f = new Paint.FontMetrics();

    /* renamed from: g  reason: collision with root package name */
    private static Rect f1738g = new Rect();

    /* renamed from: h  reason: collision with root package name */
    private static e f1739h = a();

    /* renamed from: i  reason: collision with root package name */
    private static Rect f1740i = new Rect();

    /* renamed from: j  reason: collision with root package name */
    private static Rect f1741j = new Rect();
    private static Paint.FontMetrics k = new Paint.FontMetrics();

    static {
        Double.longBitsToDouble(1);
    }

    public static void a(Context context) {
        if (context == null) {
            b = ViewConfiguration.getMinimumFlingVelocity();
            c = ViewConfiguration.getMaximumFlingVelocity();
            Log.e("MPChartLib-Utils", "Utils.init(...) PROVIDED CONTEXT OBJECT IS NULL");
            return;
        }
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        b = viewConfiguration.getScaledMinimumFlingVelocity();
        c = viewConfiguration.getScaledMaximumFlingVelocity();
        a = context.getResources().getDisplayMetrics();
    }

    public static float b(Paint paint) {
        return b(paint, f1737f);
    }

    public static float c(float f2) {
        while (f2 < 0.0f) {
            f2 += 360.0f;
        }
        return f2 % 360.0f;
    }

    public static int c(Paint paint, String str) {
        return (int) paint.measureText(str);
    }

    public static int d() {
        return b;
    }

    public static int e() {
        return Build.VERSION.SDK_INT;
    }

    public static float b(Paint paint, Paint.FontMetrics fontMetrics) {
        paint.getFontMetrics(fontMetrics);
        return (fontMetrics.ascent - fontMetrics.top) + fontMetrics.bottom;
    }

    public static int c() {
        return c;
    }

    public static b b(Paint paint, String str) {
        b a2 = b.a(0.0f, 0.0f);
        a(paint, str, a2);
        return a2;
    }

    public static e b() {
        return f1739h;
    }

    public static float b(double d2) {
        if (Double.isInfinite(d2) || Double.isNaN(d2) || d2 == 0.0d) {
            return 0.0f;
        }
        float pow = (float) Math.pow(10.0d, (double) (1 - ((int) ((float) Math.ceil((double) ((float) Math.log10(d2 < 0.0d ? -d2 : d2)))))));
        double d3 = (double) pow;
        Double.isNaN(d3);
        return ((float) Math.round(d2 * d3)) / pow;
    }

    public static float a(float f2) {
        DisplayMetrics displayMetrics = a;
        if (displayMetrics != null) {
            return f2 * displayMetrics.density;
        }
        Log.e("MPChartLib-Utils", "Utils NOT INITIALIZED. You need to call Utils.init(...) at least once before calling Utils.convertDpToPixel(...). Otherwise conversion does not take place.");
        return f2;
    }

    public static int a(Paint paint, String str) {
        Rect rect = e;
        rect.set(0, 0, 0, 0);
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.height();
    }

    public static int b(float f2) {
        float b2 = b((double) f2);
        if (Float.isInfinite(b2)) {
            return 0;
        }
        return ((int) Math.ceil(-Math.log10((double) b2))) + 2;
    }

    public static b b(float f2, float f3, float f4) {
        double d2 = (double) f4;
        return b.a(Math.abs(((float) Math.cos(d2)) * f2) + Math.abs(((float) Math.sin(d2)) * f3), Math.abs(f2 * ((float) Math.sin(d2))) + Math.abs(f3 * ((float) Math.cos(d2))));
    }

    public static float a(Paint paint) {
        return a(paint, f1737f);
    }

    public static float a(Paint paint, Paint.FontMetrics fontMetrics) {
        paint.getFontMetrics(fontMetrics);
        return fontMetrics.descent - fontMetrics.ascent;
    }

    public static void a(Paint paint, String str, b bVar) {
        Rect rect = f1738g;
        rect.set(0, 0, 0, 0);
        paint.getTextBounds(str, 0, str.length(), rect);
        bVar.f1721g = (float) rect.width();
        bVar.f1722h = (float) rect.height();
    }

    private static e a() {
        return new c(1);
    }

    public static double a(double d2) {
        if (d2 == Double.POSITIVE_INFINITY) {
            return d2;
        }
        double d3 = d2 + 0.0d;
        return Double.longBitsToDouble(Double.doubleToRawLongBits(d3) + (d3 >= 0.0d ? 1 : -1));
    }

    public static void a(e eVar, float f2, float f3, e eVar2) {
        double d2 = (double) eVar.f1727g;
        double d3 = (double) f2;
        double d4 = (double) f3;
        double cos = Math.cos(Math.toRadians(d4));
        Double.isNaN(d3);
        Double.isNaN(d2);
        eVar2.f1727g = (float) (d2 + (cos * d3));
        double d5 = (double) eVar.f1728h;
        double sin = Math.sin(Math.toRadians(d4));
        Double.isNaN(d3);
        Double.isNaN(d5);
        eVar2.f1728h = (float) (d5 + (d3 * sin));
    }

    public static void a(MotionEvent motionEvent, VelocityTracker velocityTracker) {
        velocityTracker.computeCurrentVelocity(1000, (float) c);
        int actionIndex = motionEvent.getActionIndex();
        int pointerId = motionEvent.getPointerId(actionIndex);
        float xVelocity = velocityTracker.getXVelocity(pointerId);
        float yVelocity = velocityTracker.getYVelocity(pointerId);
        int pointerCount = motionEvent.getPointerCount();
        for (int i2 = 0; i2 < pointerCount; i2++) {
            if (i2 != actionIndex) {
                int pointerId2 = motionEvent.getPointerId(i2);
                if ((velocityTracker.getXVelocity(pointerId2) * xVelocity) + (velocityTracker.getYVelocity(pointerId2) * yVelocity) < 0.0f) {
                    velocityTracker.clear();
                    return;
                }
            }
        }
    }

    @SuppressLint({"NewApi"})
    public static void a(View view) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.postInvalidateOnAnimation();
        } else {
            view.postInvalidateDelayed(10);
        }
    }

    public static void a(Canvas canvas, Drawable drawable, int i2, int i3, int i4, int i5) {
        e b2 = e.b();
        b2.f1727g = (float) (i2 - (i4 / 2));
        b2.f1728h = (float) (i3 - (i5 / 2));
        drawable.copyBounds(f1740i);
        Rect rect = f1740i;
        int i6 = rect.left;
        int i7 = rect.top;
        drawable.setBounds(i6, i7, i6 + i4, i4 + i7);
        int save = canvas.save();
        canvas.translate(b2.f1727g, b2.f1728h);
        drawable.draw(canvas);
        canvas.restoreToCount(save);
    }

    public static void a(Canvas canvas, String str, float f2, float f3, Paint paint, e eVar, float f4) {
        float fontMetrics = paint.getFontMetrics(k);
        paint.getTextBounds(str, 0, str.length(), f1741j);
        float f5 = 0.0f - ((float) f1741j.left);
        float f6 = (-k.ascent) + 0.0f;
        Paint.Align textAlign = paint.getTextAlign();
        paint.setTextAlign(Paint.Align.LEFT);
        if (f4 != 0.0f) {
            float width = f5 - (((float) f1741j.width()) * 0.5f);
            float f7 = f6 - (fontMetrics * 0.5f);
            if (!(eVar.f1727g == 0.5f && eVar.f1728h == 0.5f)) {
                b a2 = a((float) f1741j.width(), fontMetrics, f4);
                f2 -= a2.f1721g * (eVar.f1727g - 0.5f);
                f3 -= a2.f1722h * (eVar.f1728h - 0.5f);
                b.a(a2);
            }
            canvas.save();
            canvas.translate(f2, f3);
            canvas.rotate(f4);
            canvas.drawText(str, width, f7, paint);
            canvas.restore();
        } else {
            if (!(eVar.f1727g == 0.0f && eVar.f1728h == 0.0f)) {
                f5 -= ((float) f1741j.width()) * eVar.f1727g;
                f6 -= fontMetrics * eVar.f1728h;
            }
            canvas.drawText(str, f5 + f2, f6 + f3, paint);
        }
        paint.setTextAlign(textAlign);
    }

    public static b a(float f2, float f3, float f4) {
        return b(f2, f3, f4 * 0.017453292f);
    }
}
