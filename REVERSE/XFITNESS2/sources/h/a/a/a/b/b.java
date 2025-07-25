package h.a.a.a.b;

import com.github.mikephil.charting.data.BarEntry;
import h.a.a.a.e.b.a;

/* compiled from: BarBuffer */
public class b extends a<a> {
    protected boolean e = false;

    /* renamed from: f  reason: collision with root package name */
    protected boolean f1670f = false;

    /* renamed from: g  reason: collision with root package name */
    protected float f1671g = 1.0f;

    public b(int i2, int i3, boolean z) {
        super(i2);
        this.e = z;
    }

    public void a(float f2) {
        this.f1671g = f2;
    }

    public void a(int i2) {
    }

    public void a(boolean z) {
        this.f1670f = z;
    }

    /* access modifiers changed from: protected */
    public void a(float f2, float f3, float f4, float f5) {
        float[] fArr = this.b;
        int i2 = this.a;
        int i3 = i2 + 1;
        this.a = i3;
        fArr[i2] = f2;
        int i4 = i3 + 1;
        this.a = i4;
        fArr[i3] = f3;
        int i5 = i4 + 1;
        this.a = i5;
        fArr[i4] = f4;
        this.a = i5 + 1;
        fArr[i5] = f5;
    }

    public void a(a aVar) {
        float f2;
        float f3;
        float f4;
        float f5;
        float X = ((float) aVar.X()) * this.c;
        float f6 = this.f1671g / 2.0f;
        for (int i2 = 0; ((float) i2) < X; i2++) {
            BarEntry barEntry = (BarEntry) aVar.c(i2);
            if (barEntry != null) {
                float d = barEntry.d();
                float c = barEntry.c();
                float[] h2 = barEntry.h();
                if (!this.e || h2 == null) {
                    float f7 = d - f6;
                    float f8 = d + f6;
                    if (this.f1670f) {
                        f2 = c >= 0.0f ? c : 0.0f;
                        if (c > 0.0f) {
                            c = 0.0f;
                        }
                    } else {
                        float f9 = c >= 0.0f ? c : 0.0f;
                        if (c > 0.0f) {
                            c = 0.0f;
                        }
                        float f10 = c;
                        c = f9;
                        f2 = f10;
                    }
                    if (c > 0.0f) {
                        c *= this.d;
                    } else {
                        f2 *= this.d;
                    }
                    a(f7, c, f8, f2);
                } else {
                    float f11 = -barEntry.e();
                    int i3 = 0;
                    float f12 = 0.0f;
                    while (i3 < h2.length) {
                        float f13 = h2[i3];
                        if (f13 == 0.0f && (f12 == 0.0f || f11 == 0.0f)) {
                            f3 = f13;
                            f4 = f11;
                            f11 = f3;
                        } else if (f13 >= 0.0f) {
                            f3 = f13 + f12;
                            f4 = f11;
                            f11 = f12;
                            f12 = f3;
                        } else {
                            f3 = Math.abs(f13) + f11;
                            f4 = Math.abs(f13) + f11;
                        }
                        float f14 = d - f6;
                        float f15 = d + f6;
                        if (this.f1670f) {
                            f5 = f11 >= f3 ? f11 : f3;
                            if (f11 > f3) {
                                f11 = f3;
                            }
                        } else {
                            float f16 = f11 >= f3 ? f11 : f3;
                            if (f11 > f3) {
                                f11 = f3;
                            }
                            float f17 = f11;
                            f11 = f16;
                            f5 = f17;
                        }
                        float f18 = this.d;
                        a(f14, f11 * f18, f15, f5 * f18);
                        i3++;
                        f11 = f4;
                    }
                }
            }
        }
        a();
    }
}
