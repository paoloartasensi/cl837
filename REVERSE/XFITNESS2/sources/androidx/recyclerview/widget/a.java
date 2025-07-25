package androidx.recyclerview.widget;

import androidx.core.g.e;
import androidx.core.g.f;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.p;
import java.util.ArrayList;
import java.util.List;

/* compiled from: AdapterHelper */
class a implements p.a {
    private e<b> a;
    final ArrayList<b> b;
    final ArrayList<b> c;
    final C0042a d;
    Runnable e;

    /* renamed from: f  reason: collision with root package name */
    final boolean f821f;

    /* renamed from: g  reason: collision with root package name */
    final p f822g;

    /* renamed from: h  reason: collision with root package name */
    private int f823h;

    /* renamed from: androidx.recyclerview.widget.a$a  reason: collision with other inner class name */
    /* compiled from: AdapterHelper */
    interface C0042a {
        RecyclerView.c0 a(int i2);

        void a(int i2, int i3);

        void a(int i2, int i3, Object obj);

        void a(b bVar);

        void b(int i2, int i3);

        void b(b bVar);

        void c(int i2, int i3);

        void d(int i2, int i3);
    }

    /* compiled from: AdapterHelper */
    static class b {
        int a;
        int b;
        Object c;
        int d;

        b(int i2, int i3, int i4, Object obj) {
            this.a = i2;
            this.b = i3;
            this.d = i4;
            this.c = obj;
        }

        /* access modifiers changed from: package-private */
        public String a() {
            int i2 = this.a;
            if (i2 == 1) {
                return "add";
            }
            if (i2 == 2) {
                return "rm";
            }
            if (i2 != 4) {
                return i2 != 8 ? "??" : "mv";
            }
            return "up";
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || b.class != obj.getClass()) {
                return false;
            }
            b bVar = (b) obj;
            int i2 = this.a;
            if (i2 != bVar.a) {
                return false;
            }
            if (i2 == 8 && Math.abs(this.d - this.b) == 1 && this.d == bVar.b && this.b == bVar.d) {
                return true;
            }
            if (this.d != bVar.d || this.b != bVar.b) {
                return false;
            }
            Object obj2 = this.c;
            if (obj2 != null) {
                if (!obj2.equals(bVar.c)) {
                    return false;
                }
            } else if (bVar.c != null) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (((this.a * 31) + this.b) * 31) + this.d;
        }

        public String toString() {
            return Integer.toHexString(System.identityHashCode(this)) + "[" + a() + ",s:" + this.b + "c:" + this.d + ",p:" + this.c + "]";
        }
    }

    a(C0042a aVar) {
        this(aVar, false);
    }

    private void b(b bVar) {
        g(bVar);
    }

    private void c(b bVar) {
        g(bVar);
    }

    private void d(b bVar) {
        char c2;
        boolean z;
        boolean z2;
        int i2 = bVar.b;
        int i3 = bVar.d + i2;
        char c3 = 65535;
        int i4 = i2;
        int i5 = 0;
        while (i4 < i3) {
            if (this.d.a(i4) != null || d(i4)) {
                if (c3 == 0) {
                    f(a(2, i2, i5, (Object) null));
                    z2 = true;
                } else {
                    z2 = false;
                }
                c2 = 1;
            } else {
                if (c3 == 1) {
                    g(a(2, i2, i5, (Object) null));
                    z = true;
                } else {
                    z = false;
                }
                c2 = 0;
            }
            if (z) {
                i4 -= i5;
                i3 -= i5;
                i5 = 1;
            } else {
                i5++;
            }
            i4++;
            c3 = c2;
        }
        if (i5 != bVar.d) {
            a(bVar);
            bVar = a(2, i2, i5, (Object) null);
        }
        if (c3 == 0) {
            f(bVar);
        } else {
            g(bVar);
        }
    }

    private void g(b bVar) {
        this.c.add(bVar);
        int i2 = bVar.a;
        if (i2 == 1) {
            this.d.d(bVar.b, bVar.d);
        } else if (i2 == 2) {
            this.d.c(bVar.b, bVar.d);
        } else if (i2 == 4) {
            this.d.a(bVar.b, bVar.d, bVar.c);
        } else if (i2 == 8) {
            this.d.a(bVar.b, bVar.d);
        } else {
            throw new IllegalArgumentException("Unknown update op type for " + bVar);
        }
    }

    /* access modifiers changed from: package-private */
    public void a() {
        int size = this.c.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.d.a(this.c.get(i2));
        }
        a((List<b>) this.c);
        this.f823h = 0;
    }

    /* access modifiers changed from: package-private */
    public void e() {
        this.f822g.a(this.b);
        int size = this.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            b bVar = this.b.get(i2);
            int i3 = bVar.a;
            if (i3 == 1) {
                b(bVar);
            } else if (i3 == 2) {
                d(bVar);
            } else if (i3 == 4) {
                e(bVar);
            } else if (i3 == 8) {
                c(bVar);
            }
            Runnable runnable = this.e;
            if (runnable != null) {
                runnable.run();
            }
        }
        this.b.clear();
    }

    /* access modifiers changed from: package-private */
    public void f() {
        a((List<b>) this.b);
        a((List<b>) this.c);
        this.f823h = 0;
    }

    a(C0042a aVar, boolean z) {
        this.a = new f(30);
        this.b = new ArrayList<>();
        this.c = new ArrayList<>();
        this.f823h = 0;
        this.d = aVar;
        this.f821f = z;
        this.f822g = new p(this);
    }

    /* access modifiers changed from: package-private */
    public int b(int i2) {
        return a(i2, 0);
    }

    /* access modifiers changed from: package-private */
    public boolean c() {
        return this.b.size() > 0;
    }

    /* access modifiers changed from: package-private */
    public boolean b(int i2, int i3) {
        if (i3 < 1) {
            return false;
        }
        this.b.add(a(1, i2, i3, (Object) null));
        this.f823h |= 1;
        if (this.b.size() == 1) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean c(int i2) {
        return (i2 & this.f823h) != 0;
    }

    private void f(b bVar) {
        int i2;
        int i3 = bVar.a;
        if (i3 == 1 || i3 == 8) {
            throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        int d2 = d(bVar.b, i3);
        int i4 = bVar.b;
        int i5 = bVar.a;
        if (i5 == 2) {
            i2 = 0;
        } else if (i5 == 4) {
            i2 = 1;
        } else {
            throw new IllegalArgumentException("op should be remove or update." + bVar);
        }
        int i6 = 1;
        for (int i7 = 1; i7 < bVar.d; i7++) {
            int d3 = d(bVar.b + (i2 * i7), bVar.a);
            int i8 = bVar.a;
            if (i8 == 2 ? d3 == d2 : i8 == 4 && d3 == d2 + 1) {
                i6++;
            } else {
                b a2 = a(bVar.a, d2, i6, bVar.c);
                a(a2, i4);
                a(a2);
                if (bVar.a == 4) {
                    i4 += i6;
                }
                d2 = d3;
                i6 = 1;
            }
        }
        Object obj = bVar.c;
        a(bVar);
        if (i6 > 0) {
            b a3 = a(bVar.a, d2, i6, obj);
            a(a3, i4);
            a(a3);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean c(int i2, int i3) {
        if (i3 < 1) {
            return false;
        }
        this.b.add(a(2, i2, i3, (Object) null));
        this.f823h |= 2;
        if (this.b.size() == 1) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void a(b bVar, int i2) {
        this.d.b(bVar);
        int i3 = bVar.a;
        if (i3 == 2) {
            this.d.b(i2, bVar.d);
        } else if (i3 == 4) {
            this.d.a(i2, bVar.d, bVar.c);
        } else {
            throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
        }
    }

    /* access modifiers changed from: package-private */
    public void b() {
        a();
        int size = this.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            b bVar = this.b.get(i2);
            int i3 = bVar.a;
            if (i3 == 1) {
                this.d.a(bVar);
                this.d.d(bVar.b, bVar.d);
            } else if (i3 == 2) {
                this.d.a(bVar);
                this.d.b(bVar.b, bVar.d);
            } else if (i3 == 4) {
                this.d.a(bVar);
                this.d.a(bVar.b, bVar.d, bVar.c);
            } else if (i3 == 8) {
                this.d.a(bVar);
                this.d.a(bVar.b, bVar.d);
            }
            Runnable runnable = this.e;
            if (runnable != null) {
                runnable.run();
            }
        }
        a((List<b>) this.b);
        this.f823h = 0;
    }

    /* access modifiers changed from: package-private */
    public int a(int i2, int i3) {
        int size = this.c.size();
        while (i3 < size) {
            b bVar = this.c.get(i3);
            int i4 = bVar.a;
            if (i4 == 8) {
                int i5 = bVar.b;
                if (i5 == i2) {
                    i2 = bVar.d;
                } else {
                    if (i5 < i2) {
                        i2--;
                    }
                    if (bVar.d <= i2) {
                        i2++;
                    }
                }
            } else {
                int i6 = bVar.b;
                if (i6 > i2) {
                    continue;
                } else if (i4 == 2) {
                    int i7 = bVar.d;
                    if (i2 < i6 + i7) {
                        return -1;
                    }
                    i2 -= i7;
                } else if (i4 == 1) {
                    i2 += bVar.d;
                }
            }
            i3++;
        }
        return i2;
    }

    private void e(b bVar) {
        int i2 = bVar.b;
        int i3 = bVar.d + i2;
        int i4 = i2;
        char c2 = 65535;
        int i5 = 0;
        while (i2 < i3) {
            if (this.d.a(i2) != null || d(i2)) {
                if (c2 == 0) {
                    f(a(4, i4, i5, bVar.c));
                    i4 = i2;
                    i5 = 0;
                }
                c2 = 1;
            } else {
                if (c2 == 1) {
                    g(a(4, i4, i5, bVar.c));
                    i4 = i2;
                    i5 = 0;
                }
                c2 = 0;
            }
            i5++;
            i2++;
        }
        if (i5 != bVar.d) {
            Object obj = bVar.c;
            a(bVar);
            bVar = a(4, i4, i5, obj);
        }
        if (c2 == 0) {
            f(bVar);
        } else {
            g(bVar);
        }
    }

    private int d(int i2, int i3) {
        for (int size = this.c.size() - 1; size >= 0; size--) {
            b bVar = this.c.get(size);
            int i4 = bVar.a;
            if (i4 == 8) {
                int i5 = bVar.b;
                int i6 = bVar.d;
                if (i5 >= i6) {
                    int i7 = i6;
                    i6 = i5;
                    i5 = i7;
                }
                if (i2 < i5 || i2 > i6) {
                    int i8 = bVar.b;
                    if (i2 < i8) {
                        if (i3 == 1) {
                            bVar.b = i8 + 1;
                            bVar.d++;
                        } else if (i3 == 2) {
                            bVar.b = i8 - 1;
                            bVar.d--;
                        }
                    }
                } else {
                    int i9 = bVar.b;
                    if (i5 == i9) {
                        if (i3 == 1) {
                            bVar.d++;
                        } else if (i3 == 2) {
                            bVar.d--;
                        }
                        i2++;
                    } else {
                        if (i3 == 1) {
                            bVar.b = i9 + 1;
                        } else if (i3 == 2) {
                            bVar.b = i9 - 1;
                        }
                        i2--;
                    }
                }
            } else {
                int i10 = bVar.b;
                if (i10 <= i2) {
                    if (i4 == 1) {
                        i2 -= bVar.d;
                    } else if (i4 == 2) {
                        i2 += bVar.d;
                    }
                } else if (i3 == 1) {
                    bVar.b = i10 + 1;
                } else if (i3 == 2) {
                    bVar.b = i10 - 1;
                }
            }
        }
        for (int size2 = this.c.size() - 1; size2 >= 0; size2--) {
            b bVar2 = this.c.get(size2);
            if (bVar2.a == 8) {
                int i11 = bVar2.d;
                if (i11 == bVar2.b || i11 < 0) {
                    this.c.remove(size2);
                    a(bVar2);
                }
            } else if (bVar2.d <= 0) {
                this.c.remove(size2);
                a(bVar2);
            }
        }
        return i2;
    }

    /* access modifiers changed from: package-private */
    public boolean a(int i2, int i3, Object obj) {
        if (i3 < 1) {
            return false;
        }
        this.b.add(a(4, i2, i3, obj));
        this.f823h |= 4;
        if (this.b.size() == 1) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean a(int i2, int i3, int i4) {
        if (i2 == i3) {
            return false;
        }
        if (i4 == 1) {
            this.b.add(a(8, i2, i3, (Object) null));
            this.f823h |= 8;
            if (this.b.size() == 1) {
                return true;
            }
            return false;
        }
        throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
    }

    public int a(int i2) {
        int size = this.b.size();
        for (int i3 = 0; i3 < size; i3++) {
            b bVar = this.b.get(i3);
            int i4 = bVar.a;
            if (i4 != 1) {
                if (i4 == 2) {
                    int i5 = bVar.b;
                    if (i5 <= i2) {
                        int i6 = bVar.d;
                        if (i5 + i6 > i2) {
                            return -1;
                        }
                        i2 -= i6;
                    } else {
                        continue;
                    }
                } else if (i4 == 8) {
                    int i7 = bVar.b;
                    if (i7 == i2) {
                        i2 = bVar.d;
                    } else {
                        if (i7 < i2) {
                            i2--;
                        }
                        if (bVar.d <= i2) {
                            i2++;
                        }
                    }
                }
            } else if (bVar.b <= i2) {
                i2 += bVar.d;
            }
        }
        return i2;
    }

    public b a(int i2, int i3, int i4, Object obj) {
        b a2 = this.a.a();
        if (a2 == null) {
            return new b(i2, i3, i4, obj);
        }
        a2.a = i2;
        a2.b = i3;
        a2.d = i4;
        a2.c = obj;
        return a2;
    }

    private boolean d(int i2) {
        int size = this.c.size();
        for (int i3 = 0; i3 < size; i3++) {
            b bVar = this.c.get(i3);
            int i4 = bVar.a;
            if (i4 == 8) {
                if (a(bVar.d, i3 + 1) == i2) {
                    return true;
                }
            } else if (i4 == 1) {
                int i5 = bVar.b;
                int i6 = bVar.d + i5;
                while (i5 < i6) {
                    if (a(i5, i3 + 1) == i2) {
                        return true;
                    }
                    i5++;
                }
                continue;
            } else {
                continue;
            }
        }
        return false;
    }

    public void a(b bVar) {
        if (!this.f821f) {
            bVar.c = null;
            this.a.a(bVar);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(List<b> list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            a(list.get(i2));
        }
        list.clear();
    }

    /* access modifiers changed from: package-private */
    public boolean d() {
        return !this.c.isEmpty() && !this.b.isEmpty();
    }
}
