package com.android.chileaf.bluetooth.connect.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.jeremyliao.liveeventbus.BuildConfig;

public class Data implements Parcelable {
    public static final Parcelable.Creator<Data> CREATOR = new a();

    /* renamed from: f  reason: collision with root package name */
    private static char[] f1040f = "0123456789ABCDEF".toCharArray();
    protected byte[] e;

    static class a implements Parcelable.Creator<Data> {
        a() {
        }

        public Data createFromParcel(Parcel parcel) {
            return new Data(parcel);
        }

        public Data[] newArray(int i2) {
            return new Data[i2];
        }
    }

    public Data() {
        this.e = null;
    }

    private static int a(byte b) {
        return b & 255;
    }

    private static long a(long j2, int i2) {
        int i3 = 1 << (i2 - 1);
        long j3 = (long) i3;
        return (j2 & j3) != 0 ? (j3 - (j2 & ((long) (i3 - 1)))) * -1 : j2;
    }

    public static int b(int i2) {
        return i2 & 15;
    }

    private static long b(byte b) {
        return ((long) b) & 255;
    }

    private static int c(int i2, int i3) {
        int i4 = 1 << (i3 - 1);
        return (i2 & i4) != 0 ? (i4 - (i2 & (i4 - 1))) * -1 : i2;
    }

    public byte[] a() {
        return this.e;
    }

    public int b() {
        byte[] bArr = this.e;
        if (bArr != null) {
            return bArr.length;
        }
        return 0;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        if (b() == 0) {
            return BuildConfig.FLAVOR;
        }
        char[] cArr = new char[((this.e.length * 3) - 1)];
        int i2 = 0;
        while (true) {
            byte[] bArr = this.e;
            if (i2 < bArr.length) {
                byte b = bArr[i2] & 255;
                int i3 = i2 * 3;
                char[] cArr2 = f1040f;
                cArr[i3] = cArr2[b >>> 4];
                cArr[i3 + 1] = cArr2[b & 15];
                if (i2 != bArr.length - 1) {
                    cArr[i3 + 2] = '-';
                }
                i2++;
            } else {
                return "(0x) " + new String(cArr);
            }
        }
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeByteArray(this.e);
    }

    public Byte a(int i2) {
        if (i2 + 1 > b()) {
            return null;
        }
        return Byte.valueOf(this.e[i2]);
    }

    public Long b(int i2, int i3) {
        if (b(i2) + i3 > b()) {
            return null;
        }
        if (i2 == 20) {
            byte[] bArr = this.e;
            return Long.valueOf(b(bArr[i3], bArr[i3 + 1], bArr[i3 + 2], bArr[i3 + 3]));
        } else if (i2 != 36) {
            return null;
        } else {
            byte[] bArr2 = this.e;
            return Long.valueOf(a(b(bArr2[i3], bArr2[i3 + 1], bArr2[i3 + 2], bArr2[i3 + 3]), 32));
        }
    }

    public Data(byte[] bArr) {
        this.e = bArr;
    }

    public Integer a(int i2, int i3) {
        if (b(i2) + i3 > b()) {
            return null;
        }
        switch (i2) {
            case 17:
                return Integer.valueOf(a(this.e[i3]));
            case 18:
                byte[] bArr = this.e;
                return Integer.valueOf(a(bArr[i3], bArr[i3 + 1]));
            case 19:
                byte[] bArr2 = this.e;
                return Integer.valueOf(a(bArr2[i3], bArr2[i3 + 1], bArr2[i3 + 2], (byte) 0));
            case 20:
                byte[] bArr3 = this.e;
                return Integer.valueOf(a(bArr3[i3], bArr3[i3 + 1], bArr3[i3 + 2], bArr3[i3 + 3]));
            default:
                switch (i2) {
                    case 33:
                        return Integer.valueOf(c(a(this.e[i3]), 8));
                    case 34:
                        byte[] bArr4 = this.e;
                        return Integer.valueOf(c(a(bArr4[i3], bArr4[i3 + 1]), 16));
                    case 35:
                        byte[] bArr5 = this.e;
                        return Integer.valueOf(c(a(bArr5[i3], bArr5[i3 + 1], bArr5[i3 + 2], (byte) 0), 24));
                    case 36:
                        byte[] bArr6 = this.e;
                        return Integer.valueOf(c(a(bArr6[i3], bArr6[i3 + 1], bArr6[i3 + 2], bArr6[i3 + 3]), 32));
                    default:
                        return null;
                }
        }
    }

    protected Data(Parcel parcel) {
        this.e = parcel.createByteArray();
    }

    private static long b(byte b, byte b2, byte b3, byte b4) {
        return b(b) + (b(b2) << 8) + (b(b3) << 16) + (b(b4) << 24);
    }

    private static int a(byte b, byte b2) {
        return a(b) + (a(b2) << 8);
    }

    private static int a(byte b, byte b2, byte b3, byte b4) {
        return a(b) + (a(b2) << 8) + (a(b3) << 16) + (a(b4) << 24);
    }
}
