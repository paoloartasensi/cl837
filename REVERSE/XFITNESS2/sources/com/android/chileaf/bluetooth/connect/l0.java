package com.android.chileaf.bluetooth.connect;

/* compiled from: Bytes */
final class l0 {
    static byte[] a(byte[] bArr, int i2, int i3) {
        if (bArr == null || i2 > bArr.length) {
            return null;
        }
        int min = Math.min(bArr.length - i2, i3);
        byte[] bArr2 = new byte[min];
        System.arraycopy(bArr, i2, bArr2, 0, min);
        return bArr2;
    }
}
