package h.a.a.a.f;

import android.view.View;
import h.a.a.a.i.f;
import h.a.a.a.i.g;
import h.a.a.a.i.j;

/* compiled from: MoveViewJob */
public class a extends b {
    private static f<a> m;

    static {
        f<a> a = f.a(2, new a((j) null, 0.0f, 0.0f, (g) null, (View) null));
        m = a;
        a.a(0.5f);
    }

    public a(j jVar, float f2, float f3, g gVar, View view) {
        super(jVar, f2, f3, gVar, view);
    }

    public static a a(j jVar, float f2, float f3, g gVar, View view) {
        a a = m.a();
        a.f1678h = jVar;
        a.f1679i = f2;
        a.f1680j = f3;
        a.k = gVar;
        a.l = view;
        return a;
    }

    public void run() {
        float[] fArr = this.f1677g;
        fArr[0] = this.f1679i;
        fArr[1] = this.f1680j;
        this.k.b(fArr);
        this.f1678h.a(this.f1677g, this.l);
        a(this);
    }

    public static void a(a aVar) {
        m.a(aVar);
    }

    /* access modifiers changed from: protected */
    public f.a a() {
        return new a(this.f1678h, this.f1679i, this.f1680j, this.k, this.l);
    }
}
