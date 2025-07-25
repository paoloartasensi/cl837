package androidx.recyclerview.widget;

import android.view.View;

/* compiled from: ViewBoundsCheck */
class w {
    final b a;
    a b = new a();

    /* compiled from: ViewBoundsCheck */
    interface b {
        int a();

        int a(View view);

        View a(int i2);

        int b();

        int b(View view);
    }

    w(b bVar) {
        this.a = bVar;
    }

    /* access modifiers changed from: package-private */
    public View a(int i2, int i3, int i4, int i5) {
        int b2 = this.a.b();
        int a2 = this.a.a();
        int i6 = i3 > i2 ? 1 : -1;
        View view = null;
        while (i2 != i3) {
            View a3 = this.a.a(i2);
            this.b.a(b2, a2, this.a.b(a3), this.a.a(a3));
            if (i4 != 0) {
                this.b.b();
                this.b.a(i4);
                if (this.b.a()) {
                    return a3;
                }
            }
            if (i5 != 0) {
                this.b.b();
                this.b.a(i5);
                if (this.b.a()) {
                    view = a3;
                }
            }
            i2 += i6;
        }
        return view;
    }

    /* compiled from: ViewBoundsCheck */
    static class a {
        int a = 0;
        int b;
        int c;
        int d;
        int e;

        a() {
        }

        /* access modifiers changed from: package-private */
        public int a(int i2, int i3) {
            if (i2 > i3) {
                return 1;
            }
            return i2 == i3 ? 2 : 4;
        }

        /* access modifiers changed from: package-private */
        public void a(int i2, int i3, int i4, int i5) {
            this.b = i2;
            this.c = i3;
            this.d = i4;
            this.e = i5;
        }

        /* access modifiers changed from: package-private */
        public void b() {
            this.a = 0;
        }

        /* access modifiers changed from: package-private */
        public void a(int i2) {
            this.a = i2 | this.a;
        }

        /* access modifiers changed from: package-private */
        public boolean a() {
            int i2 = this.a;
            if ((i2 & 7) != 0 && (i2 & (a(this.d, this.b) << 0)) == 0) {
                return false;
            }
            int i3 = this.a;
            if ((i3 & 112) != 0 && (i3 & (a(this.d, this.c) << 4)) == 0) {
                return false;
            }
            int i4 = this.a;
            if ((i4 & 1792) != 0 && (i4 & (a(this.e, this.b) << 8)) == 0) {
                return false;
            }
            int i5 = this.a;
            if ((i5 & 28672) == 0 || (i5 & (a(this.e, this.c) << 12)) != 0) {
                return true;
            }
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean a(View view, int i2) {
        this.b.a(this.a.b(), this.a.a(), this.a.b(view), this.a.a(view));
        if (i2 == 0) {
            return false;
        }
        this.b.b();
        this.b.a(i2);
        return this.b.a();
    }
}
