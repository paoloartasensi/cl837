package androidx.recyclerview.widget;

import androidx.core.g.e;
import androidx.core.g.f;
import androidx.recyclerview.widget.RecyclerView;
import g.a.d;
import g.a.g;

/* compiled from: ViewInfoStore */
class x {
    final g<RecyclerView.c0, a> a = new g<>();
    final d<RecyclerView.c0> b = new d<>();

    /* compiled from: ViewInfoStore */
    interface b {
        void a(RecyclerView.c0 c0Var);

        void a(RecyclerView.c0 c0Var, RecyclerView.l.c cVar, RecyclerView.l.c cVar2);

        void b(RecyclerView.c0 c0Var, RecyclerView.l.c cVar, RecyclerView.l.c cVar2);

        void c(RecyclerView.c0 c0Var, RecyclerView.l.c cVar, RecyclerView.l.c cVar2);
    }

    x() {
    }

    /* access modifiers changed from: package-private */
    public void a() {
        this.a.clear();
        this.b.b();
    }

    /* access modifiers changed from: package-private */
    public boolean b(RecyclerView.c0 c0Var) {
        a aVar = this.a.get(c0Var);
        if (aVar == null || (aVar.a & 1) == 0) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void c(RecyclerView.c0 c0Var, RecyclerView.l.c cVar) {
        a aVar = this.a.get(c0Var);
        if (aVar == null) {
            aVar = a.b();
            this.a.put(c0Var, aVar);
        }
        aVar.b = cVar;
        aVar.a |= 4;
    }

    public void d(RecyclerView.c0 c0Var) {
        g(c0Var);
    }

    /* access modifiers changed from: package-private */
    public RecyclerView.l.c e(RecyclerView.c0 c0Var) {
        return a(c0Var, 8);
    }

    /* access modifiers changed from: package-private */
    public RecyclerView.l.c f(RecyclerView.c0 c0Var) {
        return a(c0Var, 4);
    }

    /* access modifiers changed from: package-private */
    public void g(RecyclerView.c0 c0Var) {
        a aVar = this.a.get(c0Var);
        if (aVar != null) {
            aVar.a &= -2;
        }
    }

    /* access modifiers changed from: package-private */
    public void h(RecyclerView.c0 c0Var) {
        int e = this.b.e() - 1;
        while (true) {
            if (e < 0) {
                break;
            } else if (c0Var == this.b.c(e)) {
                this.b.b(e);
                break;
            } else {
                e--;
            }
        }
        a remove = this.a.remove(c0Var);
        if (remove != null) {
            a.a(remove);
        }
    }

    private RecyclerView.l.c a(RecyclerView.c0 c0Var, int i2) {
        a d;
        RecyclerView.l.c cVar;
        int a2 = this.a.a((Object) c0Var);
        if (a2 >= 0 && (d = this.a.d(a2)) != null) {
            int i3 = d.a;
            if ((i3 & i2) != 0) {
                d.a = (i2 ^ -1) & i3;
                if (i2 == 4) {
                    cVar = d.b;
                } else if (i2 == 8) {
                    cVar = d.c;
                } else {
                    throw new IllegalArgumentException("Must provide flag PRE or POST");
                }
                if ((d.a & 12) == 0) {
                    this.a.c(a2);
                    a.a(d);
                }
                return cVar;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void b(RecyclerView.c0 c0Var, RecyclerView.l.c cVar) {
        a aVar = this.a.get(c0Var);
        if (aVar == null) {
            aVar = a.b();
            this.a.put(c0Var, aVar);
        }
        aVar.c = cVar;
        aVar.a |= 8;
    }

    /* compiled from: ViewInfoStore */
    static class a {
        static e<a> d = new f(20);
        int a;
        RecyclerView.l.c b;
        RecyclerView.l.c c;

        private a() {
        }

        static void a(a aVar) {
            aVar.a = 0;
            aVar.b = null;
            aVar.c = null;
            d.a(aVar);
        }

        static a b() {
            a a2 = d.a();
            return a2 == null ? new a() : a2;
        }

        static void a() {
            do {
            } while (d.a() != null);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean c(RecyclerView.c0 c0Var) {
        a aVar = this.a.get(c0Var);
        return (aVar == null || (aVar.a & 4) == 0) ? false : true;
    }

    /* access modifiers changed from: package-private */
    public void b() {
        a.a();
    }

    /* access modifiers changed from: package-private */
    public void a(long j2, RecyclerView.c0 c0Var) {
        this.b.c(j2, c0Var);
    }

    /* access modifiers changed from: package-private */
    public void a(RecyclerView.c0 c0Var, RecyclerView.l.c cVar) {
        a aVar = this.a.get(c0Var);
        if (aVar == null) {
            aVar = a.b();
            this.a.put(c0Var, aVar);
        }
        aVar.a |= 2;
        aVar.b = cVar;
    }

    /* access modifiers changed from: package-private */
    public RecyclerView.c0 a(long j2) {
        return this.b.c(j2);
    }

    /* access modifiers changed from: package-private */
    public void a(RecyclerView.c0 c0Var) {
        a aVar = this.a.get(c0Var);
        if (aVar == null) {
            aVar = a.b();
            this.a.put(c0Var, aVar);
        }
        aVar.a |= 1;
    }

    /* access modifiers changed from: package-private */
    public void a(b bVar) {
        for (int size = this.a.size() - 1; size >= 0; size--) {
            RecyclerView.c0 b2 = this.a.b(size);
            a c = this.a.c(size);
            int i2 = c.a;
            if ((i2 & 3) == 3) {
                bVar.a(b2);
            } else if ((i2 & 1) != 0) {
                RecyclerView.l.c cVar = c.b;
                if (cVar == null) {
                    bVar.a(b2);
                } else {
                    bVar.b(b2, cVar, c.c);
                }
            } else if ((i2 & 14) == 14) {
                bVar.a(b2, c.b, c.c);
            } else if ((i2 & 12) == 12) {
                bVar.c(b2, c.b, c.c);
            } else if ((i2 & 4) != 0) {
                bVar.b(b2, c.b, (RecyclerView.l.c) null);
            } else if ((i2 & 8) != 0) {
                bVar.a(b2, c.b, c.c);
            }
            a.a(c);
        }
    }
}
