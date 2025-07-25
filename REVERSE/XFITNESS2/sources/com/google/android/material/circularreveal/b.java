package com.google.android.material.circularreveal;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import com.google.android.material.circularreveal.c;

/* compiled from: CircularRevealHelper */
public class b {

    /* renamed from: j  reason: collision with root package name */
    public static final int f1461j;
    private final a a;
    private final View b;
    private final Path c = new Path();
    private final Paint d = new Paint(7);
    private final Paint e;

    /* renamed from: f  reason: collision with root package name */
    private c.e f1462f;

    /* renamed from: g  reason: collision with root package name */
    private Drawable f1463g;

    /* renamed from: h  reason: collision with root package name */
    private boolean f1464h;

    /* renamed from: i  reason: collision with root package name */
    private boolean f1465i;

    /* compiled from: CircularRevealHelper */
    interface a {
        void a(Canvas canvas);

        boolean c();
    }

    static {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 21) {
            f1461j = 2;
        } else if (i2 >= 18) {
            f1461j = 1;
        } else {
            f1461j = 0;
        }
    }

    public b(a aVar) {
        this.a = aVar;
        View view = (View) aVar;
        this.b = view;
        view.setWillNotDraw(false);
        Paint paint = new Paint(1);
        this.e = paint;
        paint.setColor(0);
    }

    private void g() {
        if (f1461j == 1) {
            this.c.rewind();
            c.e eVar = this.f1462f;
            if (eVar != null) {
                this.c.addCircle(eVar.a, eVar.b, eVar.c, Path.Direction.CW);
            }
        }
        this.b.invalidate();
    }

    private boolean h() {
        c.e eVar = this.f1462f;
        boolean z = eVar == null || eVar.a();
        if (f1461j != 0) {
            return !z;
        }
        if (z || !this.f1465i) {
            return false;
        }
        return true;
    }

    private boolean i() {
        return (this.f1464h || this.f1463g == null || this.f1462f == null) ? false : true;
    }

    private boolean j() {
        return !this.f1464h && Color.alpha(this.e.getColor()) != 0;
    }

    public void a() {
        if (f1461j == 0) {
            this.f1464h = true;
            this.f1465i = false;
            this.b.buildDrawingCache();
            Bitmap drawingCache = this.b.getDrawingCache();
            if (!(drawingCache != null || this.b.getWidth() == 0 || this.b.getHeight() == 0)) {
                drawingCache = Bitmap.createBitmap(this.b.getWidth(), this.b.getHeight(), Bitmap.Config.ARGB_8888);
                this.b.draw(new Canvas(drawingCache));
            }
            if (drawingCache != null) {
                Paint paint = this.d;
                Shader.TileMode tileMode = Shader.TileMode.CLAMP;
                paint.setShader(new BitmapShader(drawingCache, tileMode, tileMode));
            }
            this.f1464h = false;
            this.f1465i = true;
        }
    }

    public void b() {
        if (f1461j == 0) {
            this.f1465i = false;
            this.b.destroyDrawingCache();
            this.d.setShader((Shader) null);
            this.b.invalidate();
        }
    }

    public Drawable c() {
        return this.f1463g;
    }

    public int d() {
        return this.e.getColor();
    }

    public c.e e() {
        c.e eVar = this.f1462f;
        if (eVar == null) {
            return null;
        }
        c.e eVar2 = new c.e(eVar);
        if (eVar2.a()) {
            eVar2.c = b(eVar2);
        }
        return eVar2;
    }

    public boolean f() {
        return this.a.c() && !h();
    }

    private float b(c.e eVar) {
        return com.google.android.material.e.a.a(eVar.a, eVar.b, 0.0f, 0.0f, (float) this.b.getWidth(), (float) this.b.getHeight());
    }

    private void b(Canvas canvas) {
        if (i()) {
            Rect bounds = this.f1463g.getBounds();
            float width = this.f1462f.a - (((float) bounds.width()) / 2.0f);
            float height = this.f1462f.b - (((float) bounds.height()) / 2.0f);
            canvas.translate(width, height);
            this.f1463g.draw(canvas);
            canvas.translate(-width, -height);
        }
    }

    public void a(c.e eVar) {
        if (eVar == null) {
            this.f1462f = null;
        } else {
            c.e eVar2 = this.f1462f;
            if (eVar2 == null) {
                this.f1462f = new c.e(eVar);
            } else {
                eVar2.a(eVar);
            }
            if (com.google.android.material.e.a.a(eVar.c, b(eVar), 1.0E-4f)) {
                this.f1462f.c = Float.MAX_VALUE;
            }
        }
        g();
    }

    public void a(int i2) {
        this.e.setColor(i2);
        this.b.invalidate();
    }

    public void a(Drawable drawable) {
        this.f1463g = drawable;
        this.b.invalidate();
    }

    public void a(Canvas canvas) {
        if (h()) {
            int i2 = f1461j;
            if (i2 == 0) {
                c.e eVar = this.f1462f;
                canvas.drawCircle(eVar.a, eVar.b, eVar.c, this.d);
                if (j()) {
                    c.e eVar2 = this.f1462f;
                    canvas.drawCircle(eVar2.a, eVar2.b, eVar2.c, this.e);
                }
            } else if (i2 == 1) {
                int save = canvas.save();
                canvas.clipPath(this.c);
                this.a.a(canvas);
                if (j()) {
                    canvas.drawRect(0.0f, 0.0f, (float) this.b.getWidth(), (float) this.b.getHeight(), this.e);
                }
                canvas.restoreToCount(save);
            } else if (i2 == 2) {
                this.a.a(canvas);
                if (j()) {
                    canvas.drawRect(0.0f, 0.0f, (float) this.b.getWidth(), (float) this.b.getHeight(), this.e);
                }
            } else {
                throw new IllegalStateException("Unsupported strategy " + f1461j);
            }
        } else {
            this.a.a(canvas);
            if (j()) {
                canvas.drawRect(0.0f, 0.0f, (float) this.b.getWidth(), (float) this.b.getHeight(), this.e);
            }
        }
        b(canvas);
    }
}
