package h.a.a.a.h;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import h.a.a.a.a.a;
import h.a.a.a.d.d;
import h.a.a.a.e.a.e;
import h.a.a.a.i.i;
import h.a.a.a.i.j;

/* compiled from: DataRenderer */
public abstract class g extends o {
    protected a b;
    protected Paint c;
    protected Paint d;
    protected Paint e;

    public g(a aVar, j jVar) {
        super(jVar);
        this.b = aVar;
        Paint paint = new Paint(1);
        this.c = paint;
        paint.setStyle(Paint.Style.FILL);
        new Paint(4);
        Paint paint2 = new Paint(1);
        this.e = paint2;
        paint2.setColor(Color.rgb(63, 63, 63));
        this.e.setTextAlign(Paint.Align.CENTER);
        this.e.setTextSize(i.a(9.0f));
        Paint paint3 = new Paint(1);
        this.d = paint3;
        paint3.setStyle(Paint.Style.STROKE);
        this.d.setStrokeWidth(2.0f);
        this.d.setColor(Color.rgb(255, 187, 115));
    }

    public abstract void a();

    public abstract void a(Canvas canvas);

    public abstract void a(Canvas canvas, d[] dVarArr);

    /* access modifiers changed from: protected */
    public boolean a(e eVar) {
        return ((float) eVar.getData().d()) < ((float) eVar.getMaxVisibleCount()) * this.a.p();
    }

    public abstract void b(Canvas canvas);

    public abstract void c(Canvas canvas);

    /* access modifiers changed from: protected */
    public void a(h.a.a.a.e.b.e eVar) {
        this.e.setTypeface(eVar.l());
        this.e.setTextSize(eVar.T());
    }
}
