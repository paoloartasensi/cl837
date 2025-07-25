package androidx.recyclerview.widget;

import android.annotation.SuppressLint;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/* compiled from: GapWorker */
final class i implements Runnable {

    /* renamed from: i  reason: collision with root package name */
    static final ThreadLocal<i> f839i = new ThreadLocal<>();

    /* renamed from: j  reason: collision with root package name */
    static Comparator<c> f840j = new a();
    ArrayList<RecyclerView> e = new ArrayList<>();

    /* renamed from: f  reason: collision with root package name */
    long f841f;

    /* renamed from: g  reason: collision with root package name */
    long f842g;

    /* renamed from: h  reason: collision with root package name */
    private ArrayList<c> f843h = new ArrayList<>();

    /* compiled from: GapWorker */
    static class a implements Comparator<c> {
        a() {
        }

        /* renamed from: a */
        public int compare(c cVar, c cVar2) {
            if ((cVar.d == null) == (cVar2.d == null)) {
                boolean z = cVar.a;
                if (z == cVar2.a) {
                    int i2 = cVar2.b - cVar.b;
                    if (i2 != 0) {
                        return i2;
                    }
                    int i3 = cVar.c - cVar2.c;
                    if (i3 != 0) {
                        return i3;
                    }
                    return 0;
                } else if (z) {
                    return -1;
                } else {
                    return 1;
                }
            } else if (cVar.d == null) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    /* compiled from: GapWorker */
    static class c {
        public boolean a;
        public int b;
        public int c;
        public RecyclerView d;
        public int e;

        c() {
        }

        public void a() {
            this.a = false;
            this.b = 0;
            this.c = 0;
            this.d = null;
            this.e = 0;
        }
    }

    i() {
    }

    public void a(RecyclerView recyclerView) {
        this.e.add(recyclerView);
    }

    public void b(RecyclerView recyclerView) {
        this.e.remove(recyclerView);
    }

    public void run() {
        try {
            androidx.core.d.b.a("RV Prefetch");
            if (!this.e.isEmpty()) {
                int size = this.e.size();
                long j2 = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    RecyclerView recyclerView = this.e.get(i2);
                    if (recyclerView.getWindowVisibility() == 0) {
                        j2 = Math.max(recyclerView.getDrawingTime(), j2);
                    }
                }
                if (j2 != 0) {
                    a(TimeUnit.MILLISECONDS.toNanos(j2) + this.f842g);
                    this.f841f = 0;
                    androidx.core.d.b.a();
                }
            }
        } finally {
            this.f841f = 0;
            androidx.core.d.b.a();
        }
    }

    private void b(long j2) {
        int i2 = 0;
        while (i2 < this.f843h.size()) {
            c cVar = this.f843h.get(i2);
            if (cVar.d != null) {
                a(cVar, j2);
                cVar.a();
                i2++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(RecyclerView recyclerView, int i2, int i3) {
        if (recyclerView.isAttachedToWindow() && this.f841f == 0) {
            this.f841f = recyclerView.getNanoTime();
            recyclerView.post(this);
        }
        recyclerView.k0.b(i2, i3);
    }

    private void a() {
        c cVar;
        int size = this.e.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            RecyclerView recyclerView = this.e.get(i3);
            if (recyclerView.getWindowVisibility() == 0) {
                recyclerView.k0.a(recyclerView, false);
                i2 += recyclerView.k0.d;
            }
        }
        this.f843h.ensureCapacity(i2);
        int i4 = 0;
        for (int i5 = 0; i5 < size; i5++) {
            RecyclerView recyclerView2 = this.e.get(i5);
            if (recyclerView2.getWindowVisibility() == 0) {
                b bVar = recyclerView2.k0;
                int abs = Math.abs(bVar.a) + Math.abs(bVar.b);
                for (int i6 = 0; i6 < bVar.d * 2; i6 += 2) {
                    if (i4 >= this.f843h.size()) {
                        cVar = new c();
                        this.f843h.add(cVar);
                    } else {
                        cVar = this.f843h.get(i4);
                    }
                    int i7 = bVar.c[i6 + 1];
                    cVar.a = i7 <= abs;
                    cVar.b = abs;
                    cVar.c = i7;
                    cVar.d = recyclerView2;
                    cVar.e = bVar.c[i6];
                    i4++;
                }
            }
        }
        Collections.sort(this.f843h, f840j);
    }

    @SuppressLint({"VisibleForTests"})
    /* compiled from: GapWorker */
    static class b implements RecyclerView.o.c {
        int a;
        int b;
        int[] c;
        int d;

        b() {
        }

        /* access modifiers changed from: package-private */
        public void a(RecyclerView recyclerView, boolean z) {
            this.d = 0;
            int[] iArr = this.c;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            RecyclerView.o oVar = recyclerView.q;
            if (recyclerView.p != null && oVar != null && oVar.w()) {
                if (z) {
                    if (!recyclerView.f780h.c()) {
                        oVar.a(recyclerView.p.getItemCount(), (RecyclerView.o.c) this);
                    }
                } else if (!recyclerView.j()) {
                    oVar.a(this.a, this.b, recyclerView.l0, (RecyclerView.o.c) this);
                }
                int i2 = this.d;
                if (i2 > oVar.m) {
                    oVar.m = i2;
                    oVar.n = z;
                    recyclerView.f778f.j();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void b(int i2, int i3) {
            this.a = i2;
            this.b = i3;
        }

        public void a(int i2, int i3) {
            if (i2 < 0) {
                throw new IllegalArgumentException("Layout positions must be non-negative");
            } else if (i3 >= 0) {
                int i4 = this.d * 2;
                int[] iArr = this.c;
                if (iArr == null) {
                    int[] iArr2 = new int[4];
                    this.c = iArr2;
                    Arrays.fill(iArr2, -1);
                } else if (i4 >= iArr.length) {
                    int[] iArr3 = new int[(i4 * 2)];
                    this.c = iArr3;
                    System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
                }
                int[] iArr4 = this.c;
                iArr4[i4] = i2;
                iArr4[i4 + 1] = i3;
                this.d++;
            } else {
                throw new IllegalArgumentException("Pixel distance must be non-negative");
            }
        }

        /* access modifiers changed from: package-private */
        public boolean a(int i2) {
            if (this.c != null) {
                int i3 = this.d * 2;
                for (int i4 = 0; i4 < i3; i4 += 2) {
                    if (this.c[i4] == i2) {
                        return true;
                    }
                }
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public void a() {
            int[] iArr = this.c;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            this.d = 0;
        }
    }

    static boolean a(RecyclerView recyclerView, int i2) {
        int b2 = recyclerView.f781i.b();
        for (int i3 = 0; i3 < b2; i3++) {
            RecyclerView.c0 m = RecyclerView.m(recyclerView.f781i.d(i3));
            if (m.mPosition == i2 && !m.isInvalid()) {
                return true;
            }
        }
        return false;
    }

    private RecyclerView.c0 a(RecyclerView recyclerView, int i2, long j2) {
        if (a(recyclerView, i2)) {
            return null;
        }
        RecyclerView.v vVar = recyclerView.f778f;
        try {
            recyclerView.r();
            RecyclerView.c0 a2 = vVar.a(i2, false, j2);
            if (a2 != null) {
                if (!a2.isBound() || a2.isInvalid()) {
                    vVar.a(a2, false);
                } else {
                    vVar.b(a2.itemView);
                }
            }
            return a2;
        } finally {
            recyclerView.a(false);
        }
    }

    private void a(RecyclerView recyclerView, long j2) {
        if (recyclerView != null) {
            if (recyclerView.H && recyclerView.f781i.b() != 0) {
                recyclerView.u();
            }
            b bVar = recyclerView.k0;
            bVar.a(recyclerView, true);
            if (bVar.d != 0) {
                try {
                    androidx.core.d.b.a("RV Nested Prefetch");
                    recyclerView.l0.a(recyclerView.p);
                    for (int i2 = 0; i2 < bVar.d * 2; i2 += 2) {
                        a(recyclerView, bVar.c[i2], j2);
                    }
                } finally {
                    androidx.core.d.b.a();
                }
            }
        }
    }

    private void a(c cVar, long j2) {
        RecyclerView.c0 a2 = a(cVar.d, cVar.e, cVar.a ? Long.MAX_VALUE : j2);
        if (a2 != null && a2.mNestedRecyclerView != null && a2.isBound() && !a2.isInvalid()) {
            a((RecyclerView) a2.mNestedRecyclerView.get(), j2);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(long j2) {
        a();
        b(j2);
    }
}
