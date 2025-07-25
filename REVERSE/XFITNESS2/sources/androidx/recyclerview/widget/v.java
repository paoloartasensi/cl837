package androidx.recyclerview.widget;

import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: SnapHelper */
public abstract class v extends RecyclerView.r {
    RecyclerView a;
    private final RecyclerView.t b = new a();

    private void b() {
        this.a.removeOnScrollListener(this.b);
        this.a.setOnFlingListener((RecyclerView.r) null);
    }

    private void c() {
        if (this.a.getOnFlingListener() == null) {
            this.a.addOnScrollListener(this.b);
            this.a.setOnFlingListener(this);
            return;
        }
        throw new IllegalStateException("An instance of OnFlingListener already set.");
    }

    public abstract int a(RecyclerView.o oVar, int i2, int i3);

    public boolean a(int i2, int i3) {
        RecyclerView.o layoutManager = this.a.getLayoutManager();
        if (layoutManager == null || this.a.getAdapter() == null) {
            return false;
        }
        int minFlingVelocity = this.a.getMinFlingVelocity();
        if ((Math.abs(i3) > minFlingVelocity || Math.abs(i2) > minFlingVelocity) && b(layoutManager, i2, i3)) {
            return true;
        }
        return false;
    }

    public abstract int[] a(RecyclerView.o oVar, View view);

    /* access modifiers changed from: protected */
    @Deprecated
    public abstract n b(RecyclerView.o oVar);

    public abstract View c(RecyclerView.o oVar);

    private boolean b(RecyclerView.o oVar, int i2, int i3) {
        RecyclerView.y a2;
        int a3;
        if (!(oVar instanceof RecyclerView.y.b) || (a2 = a(oVar)) == null || (a3 = a(oVar, i2, i3)) == -1) {
            return false;
        }
        a2.c(a3);
        oVar.b(a2);
        return true;
    }

    /* compiled from: SnapHelper */
    class a extends RecyclerView.t {
        boolean a = false;

        a() {
        }

        public void a(RecyclerView recyclerView, int i2) {
            super.a(recyclerView, i2);
            if (i2 == 0 && this.a) {
                this.a = false;
                v.this.a();
            }
        }

        public void a(RecyclerView recyclerView, int i2, int i3) {
            if (i2 != 0 || i3 != 0) {
                this.a = true;
            }
        }
    }

    public void a(RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.a;
        if (recyclerView2 != recyclerView) {
            if (recyclerView2 != null) {
                b();
            }
            this.a = recyclerView;
            if (recyclerView != null) {
                c();
                new Scroller(this.a.getContext(), new DecelerateInterpolator());
                a();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a() {
        RecyclerView.o layoutManager;
        View c;
        RecyclerView recyclerView = this.a;
        if (recyclerView != null && (layoutManager = recyclerView.getLayoutManager()) != null && (c = c(layoutManager)) != null) {
            int[] a2 = a(layoutManager, c);
            if (a2[0] != 0 || a2[1] != 0) {
                this.a.i(a2[0], a2[1]);
            }
        }
    }

    /* access modifiers changed from: protected */
    public RecyclerView.y a(RecyclerView.o oVar) {
        return b(oVar);
    }
}
