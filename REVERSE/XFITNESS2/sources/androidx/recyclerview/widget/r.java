package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: PagerSnapHelper */
public class r extends v {
    private q c;
    private q d;

    private boolean b(RecyclerView.o oVar, int i2, int i3) {
        return oVar.a() ? i2 > 0 : i3 > 0;
    }

    private q d(RecyclerView.o oVar) {
        q qVar = this.d;
        if (qVar == null || qVar.a != oVar) {
            this.d = q.a(oVar);
        }
        return this.d;
    }

    private q e(RecyclerView.o oVar) {
        if (oVar.b()) {
            return f(oVar);
        }
        if (oVar.a()) {
            return d(oVar);
        }
        return null;
    }

    private q f(RecyclerView.o oVar) {
        q qVar = this.c;
        if (qVar == null || qVar.a != oVar) {
            this.c = q.b(oVar);
        }
        return this.c;
    }

    private boolean g(RecyclerView.o oVar) {
        PointF a2;
        int j2 = oVar.j();
        if (!(oVar instanceof RecyclerView.y.b) || (a2 = ((RecyclerView.y.b) oVar).a(j2 - 1)) == null) {
            return false;
        }
        if (a2.x < 0.0f || a2.y < 0.0f) {
            return true;
        }
        return false;
    }

    public int[] a(RecyclerView.o oVar, View view) {
        int[] iArr = new int[2];
        if (oVar.a()) {
            iArr[0] = a(oVar, view, d(oVar));
        } else {
            iArr[0] = 0;
        }
        if (oVar.b()) {
            iArr[1] = a(oVar, view, f(oVar));
        } else {
            iArr[1] = 0;
        }
        return iArr;
    }

    public View c(RecyclerView.o oVar) {
        if (oVar.b()) {
            return a(oVar, f(oVar));
        }
        if (oVar.a()) {
            return a(oVar, d(oVar));
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public n b(RecyclerView.o oVar) {
        if (!(oVar instanceof RecyclerView.y.b)) {
            return null;
        }
        return new a(this.a.getContext());
    }

    /* compiled from: PagerSnapHelper */
    class a extends n {
        a(Context context) {
            super(context);
        }

        /* access modifiers changed from: protected */
        public void a(View view, RecyclerView.z zVar, RecyclerView.y.a aVar) {
            r rVar = r.this;
            int[] a = rVar.a(rVar.a.getLayoutManager(), view);
            int i2 = a[0];
            int i3 = a[1];
            int d = d(Math.max(Math.abs(i2), Math.abs(i3)));
            if (d > 0) {
                aVar.a(i2, i3, d, this.f854j);
            }
        }

        /* access modifiers changed from: protected */
        public int e(int i2) {
            return Math.min(100, super.e(i2));
        }

        /* access modifiers changed from: protected */
        public float a(DisplayMetrics displayMetrics) {
            return 100.0f / ((float) displayMetrics.densityDpi);
        }
    }

    public int a(RecyclerView.o oVar, int i2, int i3) {
        q e;
        int j2 = oVar.j();
        if (j2 == 0 || (e = e(oVar)) == null) {
            return -1;
        }
        int i4 = Integer.MIN_VALUE;
        int i5 = Integer.MAX_VALUE;
        int e2 = oVar.e();
        View view = null;
        View view2 = null;
        for (int i6 = 0; i6 < e2; i6++) {
            View d2 = oVar.d(i6);
            if (d2 != null) {
                int a2 = a(oVar, d2, e);
                if (a2 <= 0 && a2 > i4) {
                    view2 = d2;
                    i4 = a2;
                }
                if (a2 >= 0 && a2 < i5) {
                    view = d2;
                    i5 = a2;
                }
            }
        }
        boolean b = b(oVar, i2, i3);
        if (b && view != null) {
            return oVar.l(view);
        }
        if (!b && view2 != null) {
            return oVar.l(view2);
        }
        if (b) {
            view = view2;
        }
        if (view == null) {
            return -1;
        }
        int l = oVar.l(view) + (g(oVar) == b ? -1 : 1);
        if (l < 0 || l >= j2) {
            return -1;
        }
        return l;
    }

    private int a(RecyclerView.o oVar, View view, q qVar) {
        return (qVar.d(view) + (qVar.b(view) / 2)) - (qVar.f() + (qVar.g() / 2));
    }

    private View a(RecyclerView.o oVar, q qVar) {
        int e = oVar.e();
        View view = null;
        if (e == 0) {
            return null;
        }
        int f2 = qVar.f() + (qVar.g() / 2);
        int i2 = Integer.MAX_VALUE;
        for (int i3 = 0; i3 < e; i3++) {
            View d2 = oVar.d(i3);
            int abs = Math.abs((qVar.d(d2) + (qVar.b(d2) / 2)) - f2);
            if (abs < i2) {
                view = d2;
                i2 = abs;
            }
        }
        return view;
    }
}
