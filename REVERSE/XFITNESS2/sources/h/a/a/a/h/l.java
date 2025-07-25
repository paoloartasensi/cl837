package h.a.a.a.h;

import android.graphics.Canvas;
import android.graphics.Path;
import h.a.a.a.a.a;
import h.a.a.a.e.b.h;
import h.a.a.a.i.j;

/* compiled from: LineScatterCandleRadarRenderer */
public abstract class l extends c {

    /* renamed from: g  reason: collision with root package name */
    private Path f1703g = new Path();

    public l(a aVar, j jVar) {
        super(aVar, jVar);
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, float f2, float f3, h hVar) {
        this.d.setColor(hVar.K());
        this.d.setStrokeWidth(hVar.z());
        this.d.setPathEffect(hVar.h0());
        if (hVar.f0()) {
            this.f1703g.reset();
            this.f1703g.moveTo(f2, this.a.i());
            this.f1703g.lineTo(f2, this.a.e());
            canvas.drawPath(this.f1703g, this.d);
        }
        if (hVar.v0()) {
            this.f1703g.reset();
            this.f1703g.moveTo(this.a.g(), f3);
            this.f1703g.lineTo(this.a.h(), f3);
            canvas.drawPath(this.f1703g, this.d);
        }
    }
}
