package h.a.a.a.b;

import com.github.mikephil.charting.data.BarEntry;
import h.a.a.a.e.b.a;

/* compiled from: HorizontalBarBuffer */
public class c extends b {
    public c(int i2, int i3, boolean z) {
        super(i2, i3, z);
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
                    a(f2, f8, c, f7);
                } else {
                    float f11 = -barEntry.e();
                    int i3 = 0;
                    float f12 = 0.0f;
                    while (i3 < h2.length) {
                        float f13 = h2[i3];
                        if (f13 >= 0.0f) {
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
                        a(f5 * f18, f15, f11 * f18, f14);
                        i3++;
                        f11 = f4;
                    }
                }
            }
        }
        a();
    }
}
