package androidx.recyclerview.widget;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: ScrollbarHelper */
class t {
    static int a(RecyclerView.z zVar, q qVar, View view, View view2, RecyclerView.o oVar, boolean z, boolean z2) {
        int i2;
        if (oVar.e() == 0 || zVar.a() == 0 || view == null || view2 == null) {
            return 0;
        }
        int min = Math.min(oVar.l(view), oVar.l(view2));
        int max = Math.max(oVar.l(view), oVar.l(view2));
        if (z2) {
            i2 = Math.max(0, (zVar.a() - max) - 1);
        } else {
            i2 = Math.max(0, min);
        }
        if (!z) {
            return i2;
        }
        return Math.round((((float) i2) * (((float) Math.abs(qVar.a(view2) - qVar.d(view))) / ((float) (Math.abs(oVar.l(view) - oVar.l(view2)) + 1)))) + ((float) (qVar.f() - qVar.d(view))));
    }

    static int b(RecyclerView.z zVar, q qVar, View view, View view2, RecyclerView.o oVar, boolean z) {
        if (oVar.e() == 0 || zVar.a() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return zVar.a();
        }
        return (int) ((((float) (qVar.a(view2) - qVar.d(view))) / ((float) (Math.abs(oVar.l(view) - oVar.l(view2)) + 1))) * ((float) zVar.a()));
    }

    static int a(RecyclerView.z zVar, q qVar, View view, View view2, RecyclerView.o oVar, boolean z) {
        if (oVar.e() == 0 || zVar.a() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return Math.abs(oVar.l(view) - oVar.l(view2)) + 1;
        }
        return Math.min(qVar.g(), qVar.a(view2) - qVar.d(view));
    }
}
