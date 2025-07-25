package androidx.cardview.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/* compiled from: RoundRectDrawable */
class f extends Drawable {
    private float a;
    private final Paint b;
    private final RectF c;
    private final Rect d;
    private float e;

    /* renamed from: f  reason: collision with root package name */
    private boolean f345f = false;

    /* renamed from: g  reason: collision with root package name */
    private boolean f346g = true;

    /* renamed from: h  reason: collision with root package name */
    private ColorStateList f347h;

    /* renamed from: i  reason: collision with root package name */
    private PorterDuffColorFilter f348i;

    /* renamed from: j  reason: collision with root package name */
    private ColorStateList f349j;
    private PorterDuff.Mode k = PorterDuff.Mode.SRC_IN;

    f(ColorStateList colorStateList, float f2) {
        this.a = f2;
        this.b = new Paint(5);
        b(colorStateList);
        this.c = new RectF();
        this.d = new Rect();
    }

    private void b(ColorStateList colorStateList) {
        if (colorStateList == null) {
            colorStateList = ColorStateList.valueOf(0);
        }
        this.f347h = colorStateList;
        this.b.setColor(colorStateList.getColorForState(getState(), this.f347h.getDefaultColor()));
    }

    /* access modifiers changed from: package-private */
    public void a(float f2, boolean z, boolean z2) {
        if (f2 != this.e || this.f345f != z || this.f346g != z2) {
            this.e = f2;
            this.f345f = z;
            this.f346g = z2;
            a((Rect) null);
            invalidateSelf();
        }
    }

    public float c() {
        return this.a;
    }

    public void draw(Canvas canvas) {
        boolean z;
        Paint paint = this.b;
        if (this.f348i == null || paint.getColorFilter() != null) {
            z = false;
        } else {
            paint.setColorFilter(this.f348i);
            z = true;
        }
        RectF rectF = this.c;
        float f2 = this.a;
        canvas.drawRoundRect(rectF, f2, f2, paint);
        if (z) {
            paint.setColorFilter((ColorFilter) null);
        }
    }

    public int getOpacity() {
        return -3;
    }

    public void getOutline(Outline outline) {
        outline.setRoundRect(this.d, this.a);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000a, code lost:
        r0 = r1.f347h;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isStateful() {
        /*
            r1 = this;
            android.content.res.ColorStateList r0 = r1.f349j
            if (r0 == 0) goto L_0x000a
            boolean r0 = r0.isStateful()
            if (r0 != 0) goto L_0x001a
        L_0x000a:
            android.content.res.ColorStateList r0 = r1.f347h
            if (r0 == 0) goto L_0x0014
            boolean r0 = r0.isStateful()
            if (r0 != 0) goto L_0x001a
        L_0x0014:
            boolean r0 = super.isStateful()
            if (r0 == 0) goto L_0x001c
        L_0x001a:
            r0 = 1
            goto L_0x001d
        L_0x001c:
            r0 = 0
        L_0x001d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.cardview.widget.f.isStateful():boolean");
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        a(rect);
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        PorterDuff.Mode mode;
        ColorStateList colorStateList = this.f347h;
        int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
        boolean z = colorForState != this.b.getColor();
        if (z) {
            this.b.setColor(colorForState);
        }
        ColorStateList colorStateList2 = this.f349j;
        if (colorStateList2 == null || (mode = this.k) == null) {
            return z;
        }
        this.f348i = a(colorStateList2, mode);
        return true;
    }

    public void setAlpha(int i2) {
        this.b.setAlpha(i2);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.b.setColorFilter(colorFilter);
    }

    public void setTintList(ColorStateList colorStateList) {
        this.f349j = colorStateList;
        this.f348i = a(colorStateList, this.k);
        invalidateSelf();
    }

    public void setTintMode(PorterDuff.Mode mode) {
        this.k = mode;
        this.f348i = a(this.f349j, mode);
        invalidateSelf();
    }

    /* access modifiers changed from: package-private */
    public float b() {
        return this.e;
    }

    private void a(Rect rect) {
        if (rect == null) {
            rect = getBounds();
        }
        this.c.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        this.d.set(rect);
        if (this.f345f) {
            float b2 = g.b(this.e, this.a, this.f346g);
            this.d.inset((int) Math.ceil((double) g.a(this.e, this.a, this.f346g)), (int) Math.ceil((double) b2));
            this.c.set(this.d);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(float f2) {
        if (f2 != this.a) {
            this.a = f2;
            a((Rect) null);
            invalidateSelf();
        }
    }

    public void a(ColorStateList colorStateList) {
        b(colorStateList);
        invalidateSelf();
    }

    public ColorStateList a() {
        return this.f347h;
    }

    private PorterDuffColorFilter a(ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }
}
