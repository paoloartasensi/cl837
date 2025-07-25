package com.android.chileaf.bluetooth.connect;

/* compiled from: SetValueRequest */
public final class w0 extends x0 {
    private final byte[] p;
    private boolean q;

    /* access modifiers changed from: package-private */
    public byte[] b(int i2) {
        int i3 = this.q ? 512 : i2 - 3;
        byte[] bArr = this.p;
        if (bArr.length < i3) {
            return bArr;
        }
        return l0.a(bArr, 0, i3);
    }

    /* access modifiers changed from: package-private */
    public w0 a(u0 u0Var) {
        super.a(u0Var);
        return this;
    }
}
