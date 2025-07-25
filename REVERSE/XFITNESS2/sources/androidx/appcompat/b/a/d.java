package androidx.appcompat.b.a;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$style;
import androidx.appcompat.R$styleable;
import androidx.core.graphics.drawable.a;

/* compiled from: DrawerArrowDrawable */
public class d extends Drawable {
    private static final float m = ((float) Math.toRadians(45.0d));
    private final Paint a = new Paint();
    private float b;
    private float c;
    private float d;
    private float e;

    /* renamed from: f  reason: collision with root package name */
    private boolean f93f;

    /* renamed from: g  reason: collision with root package name */
    private final Path f94g = new Path();

    /* renamed from: h  reason: collision with root package name */
    private final int f95h;

    /* renamed from: i  reason: collision with root package name */
    private boolean f96i = false;

    /* renamed from: j  reason: collision with root package name */
    private float f97j;
    private float k;
    private int l = 2;

    public d(Context context) {
        this.a.setStyle(Paint.Style.STROKE);
        this.a.setStrokeJoin(Paint.Join.MITER);
        this.a.setStrokeCap(Paint.Cap.BUTT);
        this.a.setAntiAlias(true);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes((AttributeSet) null, R$styleable.DrawerArrowToggle, R$attr.drawerArrowStyle, R$style.Base_Widget_AppCompat_DrawerArrowToggle);
        a(obtainStyledAttributes.getColor(R$styleable.DrawerArrowToggle_color, 0));
        a(obtainStyledAttributes.getDimension(R$styleable.DrawerArrowToggle_thickness, 0.0f));
        a(obtainStyledAttributes.getBoolean(R$styleable.DrawerArrowToggle_spinBars, true));
        b((float) Math.round(obtainStyledAttributes.getDimension(R$styleable.DrawerArrowToggle_gapBetweenBars, 0.0f)));
        this.f95h = obtainStyledAttributes.getDimensionPixelSize(R$styleable.DrawerArrowToggle_drawableSize, 0);
        this.c = (float) Math.round(obtainStyledAttributes.getDimension(R$styleable.DrawerArrowToggle_barLength, 0.0f));
        this.b = (float) Math.round(obtainStyledAttributes.getDimension(R$styleable.DrawerArrowToggle_arrowHeadLength, 0.0f));
        this.d = obtainStyledAttributes.getDimension(R$styleable.DrawerArrowToggle_arrowShaftLength, 0.0f);
        obtainStyledAttributes.recycle();
    }

    private static float a(float f2, float f3, float f4) {
        return f2 + ((f3 - f2) * f4);
    }

    public void a(int i2) {
        if (i2 != this.a.getColor()) {
            this.a.setColor(i2);
            invalidateSelf();
        }
    }

    public void b(float f2) {
        if (f2 != this.e) {
            this.e = f2;
            invalidateSelf();
        }
    }

    public void draw(Canvas canvas) {
        Canvas canvas2 = canvas;
        Rect bounds = getBounds();
        int i2 = this.l;
        boolean z = false;
        if (i2 != 0 && (i2 == 1 || (i2 == 3 ? a.e(this) == 0 : a.e(this) == 1))) {
            z = true;
        }
        float f2 = this.b;
        float a2 = a(this.c, (float) Math.sqrt((double) (f2 * f2 * 2.0f)), this.f97j);
        float a3 = a(this.c, this.d, this.f97j);
        float round = (float) Math.round(a(0.0f, this.k, this.f97j));
        float a4 = a(0.0f, m, this.f97j);
        double d2 = (double) a2;
        float a5 = a(z ? 0.0f : -180.0f, z ? 180.0f : 0.0f, this.f97j);
        double d3 = (double) a4;
        double cos = Math.cos(d3);
        Double.isNaN(d2);
        boolean z2 = z;
        float round2 = (float) Math.round(cos * d2);
        double sin = Math.sin(d3);
        Double.isNaN(d2);
        float round3 = (float) Math.round(d2 * sin);
        this.f94g.rewind();
        float a6 = a(this.e + this.a.getStrokeWidth(), -this.k, this.f97j);
        float f3 = (-a3) / 2.0f;
        this.f94g.moveTo(f3 + round, 0.0f);
        this.f94g.rLineTo(a3 - (round * 2.0f), 0.0f);
        this.f94g.moveTo(f3, a6);
        this.f94g.rLineTo(round2, round3);
        this.f94g.moveTo(f3, -a6);
        this.f94g.rLineTo(round2, -round3);
        this.f94g.close();
        canvas.save();
        float strokeWidth = this.a.getStrokeWidth();
        float height = ((float) bounds.height()) - (3.0f * strokeWidth);
        float f4 = this.e;
        canvas2.translate((float) bounds.centerX(), ((float) ((((int) (height - (2.0f * f4))) / 4) * 2)) + (strokeWidth * 1.5f) + f4);
        if (this.f93f) {
            canvas2.rotate(a5 * ((float) (this.f96i ^ z2 ? -1 : 1)));
        } else if (z2) {
            canvas2.rotate(180.0f);
        }
        canvas2.drawPath(this.f94g, this.a);
        canvas.restore();
    }

    public int getIntrinsicHeight() {
        return this.f95h;
    }

    public int getIntrinsicWidth() {
        return this.f95h;
    }

    public int getOpacity() {
        return -3;
    }

    public void setAlpha(int i2) {
        if (i2 != this.a.getAlpha()) {
            this.a.setAlpha(i2);
            invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.a.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void setProgress(float f2) {
        if (this.f97j != f2) {
            this.f97j = f2;
            invalidateSelf();
        }
    }

    public void a(float f2) {
        if (this.a.getStrokeWidth() != f2) {
            this.a.setStrokeWidth(f2);
            double d2 = (double) (f2 / 2.0f);
            double cos = Math.cos((double) m);
            Double.isNaN(d2);
            this.k = (float) (d2 * cos);
            invalidateSelf();
        }
    }

    public void a(boolean z) {
        if (this.f93f != z) {
            this.f93f = z;
            invalidateSelf();
        }
    }

    public float a() {
        return this.f97j;
    }
}
