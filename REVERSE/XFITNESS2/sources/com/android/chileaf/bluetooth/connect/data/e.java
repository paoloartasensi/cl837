package com.android.chileaf.bluetooth.connect.data;

/* compiled from: DefaultMtuSplitter */
public final class e implements c {
    public byte[] a(byte[] bArr, int i2, int i3) {
        int i4 = i2 * i3;
        int min = Math.min(i3, bArr.length - i4);
        if (min <= 0) {
            return null;
        }
        byte[] bArr2 = new byte[min];
        System.arraycopy(bArr, i4, bArr2, 0, min);
        return bArr2;
    }
}
