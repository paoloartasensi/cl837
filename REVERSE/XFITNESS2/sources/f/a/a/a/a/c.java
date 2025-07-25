package f.a.a.a.a;

import android.os.ParcelUuid;
import android.util.Log;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: ScanRecord */
public final class c {
    private final int a;
    private final List<ParcelUuid> b;
    private final SparseArray<byte[]> c;
    private final Map<ParcelUuid, byte[]> d;
    private final int e;

    /* renamed from: f  reason: collision with root package name */
    private final String f1632f;

    private c(List<ParcelUuid> list, SparseArray<byte[]> sparseArray, Map<ParcelUuid, byte[]> map, int i2, int i3, String str, byte[] bArr) {
        this.b = list;
        this.c = sparseArray;
        this.d = map;
        this.f1632f = str;
        this.a = i2;
        this.e = i3;
    }

    public SparseArray<byte[]> a() {
        return this.c;
    }

    public List<ParcelUuid> b() {
        return this.b;
    }

    public String toString() {
        return "ScanRecord [mAdvertiseFlags=" + this.a + ", mServiceUuids=" + this.b + ", mManufacturerSpecificData=" + a.a(this.c) + ", mServiceData=" + a.a(this.d) + ", mTxPowerLevel=" + this.e + ", mDeviceName=" + this.f1632f + "]";
    }

    public static c a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int i2 = 0;
        ArrayList arrayList = new ArrayList();
        SparseArray sparseArray = new SparseArray();
        HashMap hashMap = new HashMap();
        String str = null;
        byte b2 = -1;
        byte b3 = -2147483648;
        while (true) {
            try {
                if (i2 < bArr.length) {
                    int i3 = i2 + 1;
                    byte b4 = bArr[i2] & 255;
                    if (b4 != 0) {
                        int i4 = b4 - 1;
                        int i5 = i3 + 1;
                        byte b5 = bArr[i3] & 255;
                        if (b5 == 22) {
                            hashMap.put(b.a(a(bArr, i5, 2)), a(bArr, i5 + 2, i4 - 2));
                        } else if (b5 != 255) {
                            switch (b5) {
                                case 1:
                                    b2 = bArr[i5] & 255;
                                    break;
                                case 2:
                                case 3:
                                    a(bArr, i5, i4, 2, arrayList);
                                    break;
                                case 4:
                                case 5:
                                    a(bArr, i5, i4, 4, arrayList);
                                    break;
                                case 6:
                                case 7:
                                    a(bArr, i5, i4, 16, arrayList);
                                    break;
                                case 8:
                                case 9:
                                    str = new String(a(bArr, i5, i4));
                                    break;
                                case 10:
                                    b3 = bArr[i5];
                                    break;
                            }
                        } else {
                            sparseArray.put(((bArr[i5 + 1] & 255) << 8) + (255 & bArr[i5]), a(bArr, i5 + 2, i4 - 2));
                        }
                        i2 = i4 + i5;
                    }
                }
            } catch (Exception unused) {
                Log.e("ScanRecord", "unable to parse scan record: " + Arrays.toString(bArr));
                return new c((List<ParcelUuid>) null, (SparseArray<byte[]>) null, (Map<ParcelUuid, byte[]>) null, -1, Integer.MIN_VALUE, (String) null, bArr);
            }
        }
        return new c(arrayList.isEmpty() ? null : arrayList, sparseArray, hashMap, b2, b3, str, bArr);
    }

    private static int a(byte[] bArr, int i2, int i3, int i4, List<ParcelUuid> list) {
        while (i3 > 0) {
            list.add(b.a(a(bArr, i2, i4)));
            i3 -= i4;
            i2 += i4;
        }
        return i2;
    }

    private static byte[] a(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        return bArr2;
    }
}
