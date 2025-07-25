package com.google.android.material.textfield;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;

/* compiled from: CutoutDrawable */
class a extends GradientDrawable {
    private final Paint a = new Paint(1);
    private final RectF b;
    private int c;

    a() {
        c();
        this.b = new RectF();
    }

    private void c() {
        this.a.setStyle(Paint.Style.FILL_AND_STROKE);
        this.a.setColor(-1);
        this.a.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    /* access modifiers changed from: package-private */
    public boolean a() {
        return !this.b.isEmpty();
    }

    /* access modifiers changed from: package-private */
    public void b() {
        a(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void draw(Canvas canvas) {
        b(canvas);
        super.draw(canvas);
        canvas.drawRect(this.b, this.a);
        a(canvas);
    }

    private void b(Canvas canvas) {
        Drawable.Callback callback = getCallback();
        if (a(callback)) {
            ((View) callback).setLayerType(2, (Paint) null);
        } else {
            c(canvas);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(float f2, float f3, float f4, float f5) {
        RectF rectF = this.b;
        if (f2 != rectF.left || f3 != rectF.top || f4 != rectF.right || f5 != rectF.bottom) {
            this.b.set(f2, f3, f4, f5);
            invalidateSelf();
        }
    }

    private void c(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.c = canvas.saveLayer(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight(), (Paint) null);
            return;
        }
        this.c = canvas.saveLayer(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight(), (Paint) null, 31);
    }

    /* access modifiers changed from: package-private */
    public void a(RectF rectF) {
        a(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    private void a(Canvas canvas) {
        if (!a(getCallback())) {
            canvas.restoreToCount(this.c);
        }
    }

    private boolean a(Drawable.Callback callback) {
        return callback instanceof View;
    }
}
