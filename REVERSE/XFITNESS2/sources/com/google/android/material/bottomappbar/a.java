package com.google.android.material.bottomappbar;

import com.google.android.material.i.b;
import com.google.android.material.i.d;

/* compiled from: BottomAppBarTopEdgeTreatment */
public class a extends b {
    private float a;
    private float b;
    private float c;
    private float d;
    private float e;

    public a(float f2, float f3, float f4) {
        this.b = f2;
        this.a = f3;
        this.d = f4;
        if (f4 >= 0.0f) {
            this.e = 0.0f;
            return;
        }
        throw new IllegalArgumentException("cradleVerticalOffset must be positive.");
    }

    public void a(float f2, float f3, d dVar) {
        float f4 = f2;
        d dVar2 = dVar;
        float f5 = this.c;
        if (f5 == 0.0f) {
            dVar2.a(f4, 0.0f);
            return;
        }
        float f6 = ((this.b * 2.0f) + f5) / 2.0f;
        float f7 = f3 * this.a;
        float f8 = (f4 / 2.0f) + this.e;
        float f9 = (this.d * f3) + ((1.0f - f3) * f6);
        if (f9 / f6 >= 1.0f) {
            dVar2.a(f4, 0.0f);
            return;
        }
        float f10 = f6 + f7;
        float f11 = f9 + f7;
        float sqrt = (float) Math.sqrt((double) ((f10 * f10) - (f11 * f11)));
        float f12 = f8 - sqrt;
        float f13 = f8 + sqrt;
        float degrees = (float) Math.toDegrees(Math.atan((double) (sqrt / f11)));
        float f14 = 90.0f - degrees;
        float f15 = f12 - f7;
        dVar2.a(f15, 0.0f);
        float f16 = f7 * 2.0f;
        float f17 = degrees;
        dVar.a(f15, 0.0f, f12 + f7, f16, 270.0f, degrees);
        dVar.a(f8 - f6, (-f6) - f9, f8 + f6, f6 - f9, 180.0f - f14, (f14 * 2.0f) - 180.0f);
        dVar.a(f13 - f7, 0.0f, f13 + f7, f16, 270.0f - f17, f17);
        dVar2.a(f4, 0.0f);
    }

    /* access modifiers changed from: package-private */
    public float b() {
        return this.b;
    }

    /* access modifiers changed from: package-private */
    public float c() {
        return this.a;
    }

    /* access modifiers changed from: package-private */
    public float d() {
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public void e(float f2) {
        this.e = f2;
    }

    /* access modifiers changed from: package-private */
    public void b(float f2) {
        this.b = f2;
    }

    /* access modifiers changed from: package-private */
    public void c(float f2) {
        this.a = f2;
    }

    /* access modifiers changed from: package-private */
    public void d(float f2) {
        this.c = f2;
    }

    /* access modifiers changed from: package-private */
    public float e() {
        return this.e;
    }

    /* access modifiers changed from: package-private */
    public float a() {
        return this.d;
    }

    /* access modifiers changed from: package-private */
    public void a(float f2) {
        this.d = f2;
    }
}
