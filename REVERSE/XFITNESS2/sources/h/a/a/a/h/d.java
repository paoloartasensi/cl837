package h.a.a.a.h;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.f;
import h.a.a.a.a.a;
import h.a.a.a.e.a.c;
import h.a.a.a.e.a.e;
import h.a.a.a.h.c;
import h.a.a.a.i.g;
import h.a.a.a.i.i;
import h.a.a.a.i.j;
import java.util.List;

/* compiled from: BubbleChartRenderer */
public class d extends c {

    /* renamed from: g  reason: collision with root package name */
    protected c f1688g;

    /* renamed from: h  reason: collision with root package name */
    private float[] f1689h = new float[4];

    /* renamed from: i  reason: collision with root package name */
    private float[] f1690i = new float[2];

    /* renamed from: j  reason: collision with root package name */
    private float[] f1691j = new float[3];

    public d(c cVar, a aVar, j jVar) {
        super(aVar, jVar);
        this.f1688g = cVar;
        this.c.setStyle(Paint.Style.FILL);
        this.d.setStyle(Paint.Style.STROKE);
        this.d.setStrokeWidth(i.a(1.5f));
    }

    public void a() {
    }

    public void a(Canvas canvas) {
        for (h.a.a.a.e.b.c cVar : this.f1688g.getBubbleData().c()) {
            if (cVar.isVisible()) {
                a(canvas, cVar);
            }
        }
    }

    public void b(Canvas canvas) {
    }

    public void c(Canvas canvas) {
        int i2;
        float f2;
        BubbleEntry bubbleEntry;
        float f3;
        f bubbleData = this.f1688g.getBubbleData();
        if (bubbleData != null && a((e) this.f1688g)) {
            List c = bubbleData.c();
            float a = (float) i.a(this.e, "1");
            for (int i3 = 0; i3 < c.size(); i3++) {
                h.a.a.a.e.b.c cVar = (h.a.a.a.e.b.c) c.get(i3);
                if (b(cVar) && cVar.X() >= 1) {
                    a((h.a.a.a.e.b.e) cVar);
                    float max = Math.max(0.0f, Math.min(1.0f, this.b.a()));
                    float b = this.b.b();
                    this.f1687f.a(this.f1688g, cVar);
                    g b2 = this.f1688g.b(cVar.S());
                    c.a aVar = this.f1687f;
                    float[] a2 = b2.a(cVar, b, aVar.a, aVar.b);
                    float f4 = max == 1.0f ? b : max;
                    h.a.a.a.c.e W = cVar.W();
                    h.a.a.a.i.e a3 = h.a.a.a.i.e.a(cVar.Y());
                    a3.f1727g = i.a(a3.f1727g);
                    a3.f1728h = i.a(a3.f1728h);
                    for (int i4 = 0; i4 < a2.length; i4 = i2 + 2) {
                        int i5 = i4 / 2;
                        int b3 = cVar.b(this.f1687f.a + i5);
                        int argb = Color.argb(Math.round(255.0f * f4), Color.red(b3), Color.green(b3), Color.blue(b3));
                        float f5 = a2[i4];
                        float f6 = a2[i4 + 1];
                        if (!this.a.c(f5)) {
                            break;
                        }
                        if (!this.a.b(f5) || !this.a.f(f6)) {
                            i2 = i4;
                        } else {
                            BubbleEntry bubbleEntry2 = (BubbleEntry) cVar.c(i5 + this.f1687f.a);
                            if (cVar.E()) {
                                float f7 = f6 + (0.5f * a);
                                bubbleEntry = bubbleEntry2;
                                f3 = f6;
                                float f8 = f5;
                                f2 = f5;
                                float f9 = f7;
                                i2 = i4;
                                a(canvas, W.a(bubbleEntry2), f8, f9, argb);
                            } else {
                                bubbleEntry = bubbleEntry2;
                                f3 = f6;
                                f2 = f5;
                                i2 = i4;
                            }
                            if (bubbleEntry.b() != null && cVar.G0()) {
                                Drawable b4 = bubbleEntry.b();
                                i.a(canvas, b4, (int) (f2 + a3.f1727g), (int) (f3 + a3.f1728h), b4.getIntrinsicWidth(), b4.getIntrinsicHeight());
                            }
                        }
                    }
                    h.a.a.a.i.e.b(a3);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public float a(float f2, float f3, float f4, boolean z) {
        if (z) {
            f2 = f3 == 0.0f ? 1.0f : (float) Math.sqrt((double) (f2 / f3));
        }
        return f4 * f2;
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, h.a.a.a.e.b.c cVar) {
        if (cVar.X() >= 1) {
            g b = this.f1688g.b(cVar.S());
            float b2 = this.b.b();
            this.f1687f.a(this.f1688g, cVar);
            float[] fArr = this.f1689h;
            fArr[0] = 0.0f;
            fArr[2] = 1.0f;
            b.b(fArr);
            boolean t = cVar.t();
            float[] fArr2 = this.f1689h;
            float min = Math.min(Math.abs(this.a.e() - this.a.i()), Math.abs(fArr2[2] - fArr2[0]));
            int i2 = this.f1687f.a;
            while (true) {
                c.a aVar = this.f1687f;
                if (i2 <= aVar.c + aVar.a) {
                    BubbleEntry bubbleEntry = (BubbleEntry) cVar.c(i2);
                    this.f1690i[0] = bubbleEntry.d();
                    this.f1690i[1] = bubbleEntry.c() * b2;
                    b.b(this.f1690i);
                    float a = a(bubbleEntry.e(), cVar.e(), min, t) / 2.0f;
                    if (this.a.d(this.f1690i[1] + a) && this.a.a(this.f1690i[1] - a) && this.a.b(this.f1690i[0] + a)) {
                        if (this.a.c(this.f1690i[0] - a)) {
                            this.c.setColor(cVar.e((int) bubbleEntry.d()));
                            float[] fArr3 = this.f1690i;
                            canvas.drawCircle(fArr3[0], fArr3[1], a, this.c);
                        } else {
                            return;
                        }
                    }
                    i2++;
                } else {
                    return;
                }
            }
        }
    }

    public void a(Canvas canvas, String str, float f2, float f3, int i2) {
        this.e.setColor(i2);
        canvas.drawText(str, f2, f3, this.e);
    }

    public void a(Canvas canvas, h.a.a.a.d.d[] dVarArr) {
        f bubbleData = this.f1688g.getBubbleData();
        float b = this.b.b();
        for (h.a.a.a.d.d dVar : dVarArr) {
            h.a.a.a.e.b.c cVar = (h.a.a.a.e.b.c) bubbleData.a(dVar.c());
            if (cVar != null && cVar.e0()) {
                BubbleEntry bubbleEntry = (BubbleEntry) cVar.a(dVar.g(), dVar.i());
                if (bubbleEntry.c() == dVar.i() && a(bubbleEntry, cVar)) {
                    g b2 = this.f1688g.b(cVar.S());
                    float[] fArr = this.f1689h;
                    fArr[0] = 0.0f;
                    fArr[2] = 1.0f;
                    b2.b(fArr);
                    boolean t = cVar.t();
                    float[] fArr2 = this.f1689h;
                    float min = Math.min(Math.abs(this.a.e() - this.a.i()), Math.abs(fArr2[2] - fArr2[0]));
                    this.f1690i[0] = bubbleEntry.d();
                    this.f1690i[1] = bubbleEntry.c() * b;
                    b2.b(this.f1690i);
                    float[] fArr3 = this.f1690i;
                    dVar.a(fArr3[0], fArr3[1]);
                    float a = a(bubbleEntry.e(), cVar.e(), min, t) / 2.0f;
                    if (this.a.d(this.f1690i[1] + a) && this.a.a(this.f1690i[1] - a) && this.a.b(this.f1690i[0] + a)) {
                        if (this.a.c(this.f1690i[0] - a)) {
                            int e = cVar.e((int) bubbleEntry.d());
                            Color.RGBToHSV(Color.red(e), Color.green(e), Color.blue(e), this.f1691j);
                            float[] fArr4 = this.f1691j;
                            fArr4[2] = fArr4[2] * 0.5f;
                            this.d.setColor(Color.HSVToColor(Color.alpha(e), this.f1691j));
                            this.d.setStrokeWidth(cVar.x());
                            float[] fArr5 = this.f1690i;
                            canvas.drawCircle(fArr5[0], fArr5[1], a, this.d);
                        } else {
                            return;
                        }
                    }
                }
            }
            Canvas canvas2 = canvas;
        }
    }
}
