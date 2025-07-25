package androidx.recyclerview.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ChildHelper */
class d {
    final b a;
    final a b = new a();
    final List<View> c = new ArrayList();

    /* compiled from: ChildHelper */
    interface b {
        View a(int i2);

        void a();

        void a(View view);

        void a(View view, int i2);

        void a(View view, int i2, ViewGroup.LayoutParams layoutParams);

        int b();

        RecyclerView.c0 b(View view);

        void b(int i2);

        void c(int i2);

        void c(View view);

        int d(View view);
    }

    d(b bVar) {
        this.a = bVar;
    }

    private int f(int i2) {
        if (i2 < 0) {
            return -1;
        }
        int b2 = this.a.b();
        int i3 = i2;
        while (i3 < b2) {
            int b3 = i2 - (i3 - this.b.b(i3));
            if (b3 == 0) {
                while (this.b.c(i3)) {
                    i3++;
                }
                return i3;
            }
            i3 += b3;
        }
        return -1;
    }

    private void g(View view) {
        this.c.add(view);
        this.a.a(view);
    }

    private boolean h(View view) {
        if (!this.c.remove(view)) {
            return false;
        }
        this.a.c(view);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void a(View view, boolean z) {
        a(view, -1, z);
    }

    /* access modifiers changed from: package-private */
    public View b(int i2) {
        int size = this.c.size();
        for (int i3 = 0; i3 < size; i3++) {
            View view = this.c.get(i3);
            RecyclerView.c0 b2 = this.a.b(view);
            if (b2.getLayoutPosition() == i2 && !b2.isInvalid() && !b2.isRemoved()) {
                return view;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public View c(int i2) {
        return this.a.a(f(i2));
    }

    /* access modifiers changed from: package-private */
    public void d(View view) {
        int d = this.a.d(view);
        if (d >= 0) {
            if (this.b.d(d)) {
                h(view);
            }
            this.a.b(d);
        }
    }

    /* access modifiers changed from: package-private */
    public void e(int i2) {
        int f2 = f(i2);
        View a2 = this.a.a(f2);
        if (a2 != null) {
            if (this.b.d(f2)) {
                h(a2);
            }
            this.a.b(f2);
        }
    }

    public String toString() {
        return this.b.toString() + ", hidden list:" + this.c.size();
    }

    /* compiled from: ChildHelper */
    static class a {
        long a = 0;
        a b;

        a() {
        }

        private void b() {
            if (this.b == null) {
                this.b = new a();
            }
        }

        /* access modifiers changed from: package-private */
        public void a(int i2) {
            if (i2 >= 64) {
                a aVar = this.b;
                if (aVar != null) {
                    aVar.a(i2 - 64);
                    return;
                }
                return;
            }
            this.a &= (1 << i2) ^ -1;
        }

        /* access modifiers changed from: package-private */
        public boolean c(int i2) {
            if (i2 < 64) {
                return (this.a & (1 << i2)) != 0;
            }
            b();
            return this.b.c(i2 - 64);
        }

        /* access modifiers changed from: package-private */
        public boolean d(int i2) {
            if (i2 >= 64) {
                b();
                return this.b.d(i2 - 64);
            }
            long j2 = 1 << i2;
            boolean z = (this.a & j2) != 0;
            long j3 = this.a & (j2 ^ -1);
            this.a = j3;
            long j4 = j2 - 1;
            this.a = (j3 & j4) | Long.rotateRight((j4 ^ -1) & j3, 1);
            a aVar = this.b;
            if (aVar != null) {
                if (aVar.c(0)) {
                    e(63);
                }
                this.b.d(0);
            }
            return z;
        }

        /* access modifiers changed from: package-private */
        public void e(int i2) {
            if (i2 >= 64) {
                b();
                this.b.e(i2 - 64);
                return;
            }
            this.a |= 1 << i2;
        }

        public String toString() {
            if (this.b == null) {
                return Long.toBinaryString(this.a);
            }
            return this.b.toString() + "xx" + Long.toBinaryString(this.a);
        }

        /* access modifiers changed from: package-private */
        public int b(int i2) {
            a aVar = this.b;
            if (aVar == null) {
                if (i2 >= 64) {
                    return Long.bitCount(this.a);
                }
                return Long.bitCount(this.a & ((1 << i2) - 1));
            } else if (i2 < 64) {
                return Long.bitCount(this.a & ((1 << i2) - 1));
            } else {
                return aVar.b(i2 - 64) + Long.bitCount(this.a);
            }
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.a = 0;
            a aVar = this.b;
            if (aVar != null) {
                aVar.a();
            }
        }

        /* access modifiers changed from: package-private */
        public void a(int i2, boolean z) {
            if (i2 >= 64) {
                b();
                this.b.a(i2 - 64, z);
                return;
            }
            boolean z2 = (this.a & Long.MIN_VALUE) != 0;
            long j2 = (1 << i2) - 1;
            long j3 = this.a;
            this.a = ((j3 & (j2 ^ -1)) << 1) | (j3 & j2);
            if (z) {
                e(i2);
            } else {
                a(i2);
            }
            if (z2 || this.b != null) {
                b();
                this.b.a(0, z2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(View view, int i2, boolean z) {
        int i3;
        if (i2 < 0) {
            i3 = this.a.b();
        } else {
            i3 = f(i2);
        }
        this.b.a(i3, z);
        if (z) {
            g(view);
        }
        this.a.a(view, i3);
    }

    /* access modifiers changed from: package-private */
    public void c() {
        this.b.a();
        for (int size = this.c.size() - 1; size >= 0; size--) {
            this.a.c(this.c.get(size));
            this.c.remove(size);
        }
        this.a.a();
    }

    /* access modifiers changed from: package-private */
    public void f(View view) {
        int d = this.a.d(view);
        if (d < 0) {
            throw new IllegalArgumentException("view is not a child, cannot hide " + view);
        } else if (this.b.c(d)) {
            this.b.a(d);
            h(view);
        } else {
            throw new RuntimeException("trying to unhide a view that was not hidden" + view);
        }
    }

    /* access modifiers changed from: package-private */
    public View d(int i2) {
        return this.a.a(i2);
    }

    /* access modifiers changed from: package-private */
    public boolean e(View view) {
        int d = this.a.d(view);
        if (d == -1) {
            h(view);
            return true;
        } else if (!this.b.c(d)) {
            return false;
        } else {
            this.b.d(d);
            h(view);
            this.a.b(d);
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    public void a(View view, int i2, ViewGroup.LayoutParams layoutParams, boolean z) {
        int i3;
        if (i2 < 0) {
            i3 = this.a.b();
        } else {
            i3 = f(i2);
        }
        this.b.a(i3, z);
        if (z) {
            g(view);
        }
        this.a.a(view, i3, layoutParams);
    }

    /* access modifiers changed from: package-private */
    public int b() {
        return this.a.b();
    }

    /* access modifiers changed from: package-private */
    public int b(View view) {
        int d = this.a.d(view);
        if (d != -1 && !this.b.c(d)) {
            return d - this.b.b(d);
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public boolean c(View view) {
        return this.c.contains(view);
    }

    /* access modifiers changed from: package-private */
    public int a() {
        return this.a.b() - this.c.size();
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        int f2 = f(i2);
        this.b.d(f2);
        this.a.c(f2);
    }

    /* access modifiers changed from: package-private */
    public void a(View view) {
        int d = this.a.d(view);
        if (d >= 0) {
            this.b.e(d);
            g(view);
            return;
        }
        throw new IllegalArgumentException("view is not a child, cannot hide " + view);
    }
}
