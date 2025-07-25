package androidx.recyclerview.widget;

/* compiled from: BatchingListUpdateCallback */
public class c implements o {
    final o a;
    int b = 0;
    int c = -1;
    int d = -1;
    Object e = null;

    public c(o oVar) {
        this.a = oVar;
    }

    public void a() {
        int i2 = this.b;
        if (i2 != 0) {
            if (i2 == 1) {
                this.a.onInserted(this.c, this.d);
            } else if (i2 == 2) {
                this.a.onRemoved(this.c, this.d);
            } else if (i2 == 3) {
                this.a.onChanged(this.c, this.d, this.e);
            }
            this.e = null;
            this.b = 0;
        }
    }

    public void onChanged(int i2, int i3, Object obj) {
        int i4;
        if (this.b == 3) {
            int i5 = this.c;
            int i6 = this.d;
            if (i2 <= i5 + i6 && (i4 = i2 + i3) >= i5 && this.e == obj) {
                this.c = Math.min(i2, i5);
                this.d = Math.max(i6 + i5, i4) - this.c;
                return;
            }
        }
        a();
        this.c = i2;
        this.d = i3;
        this.e = obj;
        this.b = 3;
    }

    public void onInserted(int i2, int i3) {
        int i4;
        if (this.b == 1 && i2 >= (i4 = this.c)) {
            int i5 = this.d;
            if (i2 <= i4 + i5) {
                this.d = i5 + i3;
                this.c = Math.min(i2, i4);
                return;
            }
        }
        a();
        this.c = i2;
        this.d = i3;
        this.b = 1;
    }

    public void onMoved(int i2, int i3) {
        a();
        this.a.onMoved(i2, i3);
    }

    public void onRemoved(int i2, int i3) {
        int i4;
        if (this.b != 2 || (i4 = this.c) < i2 || i4 > i2 + i3) {
            a();
            this.c = i2;
            this.d = i3;
            this.b = 2;
            return;
        }
        this.d += i3;
        this.c = i2;
    }
}
