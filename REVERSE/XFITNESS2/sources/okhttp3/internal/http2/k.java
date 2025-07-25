package okhttp3.internal.http2;

import java.util.Arrays;

/* compiled from: Settings */
public final class k {
    private int a;
    private final int[] b = new int[10];

    /* access modifiers changed from: package-private */
    public void a() {
        this.a = 0;
        Arrays.fill(this.b, 0);
    }

    /* access modifiers changed from: package-private */
    public int b() {
        if ((this.a & 2) != 0) {
            return this.b[1];
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public int c(int i2) {
        return (this.a & 32) != 0 ? this.b[5] : i2;
    }

    /* access modifiers changed from: package-private */
    public boolean d(int i2) {
        return ((1 << i2) & this.a) != 0;
    }

    /* access modifiers changed from: package-private */
    public int b(int i2) {
        return (this.a & 16) != 0 ? this.b[4] : i2;
    }

    /* access modifiers changed from: package-private */
    public int c() {
        if ((this.a & 128) != 0) {
            return this.b[7];
        }
        return 65535;
    }

    /* access modifiers changed from: package-private */
    public int d() {
        return Integer.bitCount(this.a);
    }

    /* access modifiers changed from: package-private */
    public k a(int i2, int i3) {
        if (i2 >= 0) {
            int[] iArr = this.b;
            if (i2 < iArr.length) {
                this.a = (1 << i2) | this.a;
                iArr[i2] = i3;
            }
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    public int a(int i2) {
        return this.b[i2];
    }

    /* access modifiers changed from: package-private */
    public void a(k kVar) {
        for (int i2 = 0; i2 < 10; i2++) {
            if (kVar.d(i2)) {
                a(i2, kVar.a(i2));
            }
        }
    }
}
