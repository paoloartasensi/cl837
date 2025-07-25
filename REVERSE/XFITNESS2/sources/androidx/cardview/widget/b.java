package androidx.cardview.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;

/* compiled from: CardViewApi21Impl */
class b implements e {
    b() {
    }

    private f j(d dVar) {
        return (f) dVar.c();
    }

    public void a() {
    }

    public void a(d dVar, Context context, ColorStateList colorStateList, float f2, float f3, float f4) {
        dVar.a(new f(colorStateList, f2));
        View a = dVar.a();
        a.setClipToOutline(true);
        a.setElevation(f3);
        c(dVar, f4);
    }

    public void b(d dVar, float f2) {
        j(dVar).a(f2);
    }

    public void c(d dVar, float f2) {
        j(dVar).a(f2, dVar.b(), dVar.d());
        i(dVar);
    }

    public float d(d dVar) {
        return b(dVar) * 2.0f;
    }

    public void e(d dVar) {
        c(dVar, a(dVar));
    }

    public ColorStateList f(d dVar) {
        return j(dVar).a();
    }

    public void g(d dVar) {
        c(dVar, a(dVar));
    }

    public float h(d dVar) {
        return dVar.a().getElevation();
    }

    public void i(d dVar) {
        if (!dVar.b()) {
            dVar.a(0, 0, 0, 0);
            return;
        }
        float a = a(dVar);
        float b = b(dVar);
        int ceil = (int) Math.ceil((double) g.a(a, b, dVar.d()));
        int ceil2 = (int) Math.ceil((double) g.b(a, b, dVar.d()));
        dVar.a(ceil, ceil2, ceil, ceil2);
    }

    public float b(d dVar) {
        return j(dVar).c();
    }

    public float c(d dVar) {
        return b(dVar) * 2.0f;
    }

    public float a(d dVar) {
        return j(dVar).b();
    }

    public void a(d dVar, float f2) {
        dVar.a().setElevation(f2);
    }

    public void a(d dVar, ColorStateList colorStateList) {
        j(dVar).a(colorStateList);
    }
}
