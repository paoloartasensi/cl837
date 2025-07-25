package com.android.chileaf.bluetooth.scanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public final class ScanFilter implements Parcelable {
    public static final Parcelable.Creator<ScanFilter> CREATOR = new a();
    private final String e;

    /* renamed from: f  reason: collision with root package name */
    private final String f1092f;

    /* renamed from: g  reason: collision with root package name */
    private final ParcelUuid f1093g;

    /* renamed from: h  reason: collision with root package name */
    private final ParcelUuid f1094h;

    /* renamed from: i  reason: collision with root package name */
    private final ParcelUuid f1095i;

    /* renamed from: j  reason: collision with root package name */
    private final byte[] f1096j;
    private final byte[] k;
    private final int l;
    private final byte[] m;
    private final byte[] n;

    static class a implements Parcelable.Creator<ScanFilter> {
        a() {
        }

        public ScanFilter createFromParcel(Parcel parcel) {
            b bVar = new b();
            if (parcel.readInt() == 1) {
                bVar.b(parcel.readString());
            }
            if (parcel.readInt() == 1) {
                bVar.a(parcel.readString());
            }
            if (parcel.readInt() == 1) {
                ParcelUuid parcelUuid = (ParcelUuid) parcel.readParcelable(ParcelUuid.class.getClassLoader());
                bVar.a(parcelUuid);
                if (parcel.readInt() == 1) {
                    bVar.a(parcelUuid, (ParcelUuid) parcel.readParcelable(ParcelUuid.class.getClassLoader()));
                }
            }
            if (parcel.readInt() == 1) {
                ParcelUuid parcelUuid2 = (ParcelUuid) parcel.readParcelable(ParcelUuid.class.getClassLoader());
                if (parcel.readInt() == 1) {
                    byte[] bArr = new byte[parcel.readInt()];
                    parcel.readByteArray(bArr);
                    if (parcel.readInt() == 0) {
                        bVar.a(parcelUuid2, bArr);
                    } else {
                        byte[] bArr2 = new byte[parcel.readInt()];
                        parcel.readByteArray(bArr2);
                        bVar.a(parcelUuid2, bArr, bArr2);
                    }
                }
            }
            int readInt = parcel.readInt();
            if (parcel.readInt() == 1) {
                byte[] bArr3 = new byte[parcel.readInt()];
                parcel.readByteArray(bArr3);
                if (parcel.readInt() == 0) {
                    bVar.a(readInt, bArr3);
                } else {
                    byte[] bArr4 = new byte[parcel.readInt()];
                    parcel.readByteArray(bArr4);
                    bVar.a(readInt, bArr3, bArr4);
                }
            }
            return bVar.a();
        }

        public ScanFilter[] newArray(int i2) {
            return new ScanFilter[i2];
        }
    }

    static {
        new b().a();
    }

    /* synthetic */ ScanFilter(String str, String str2, ParcelUuid parcelUuid, ParcelUuid parcelUuid2, ParcelUuid parcelUuid3, byte[] bArr, byte[] bArr2, int i2, byte[] bArr3, byte[] bArr4, a aVar) {
        this(str, str2, parcelUuid, parcelUuid2, parcelUuid3, bArr, bArr2, i2, bArr3, bArr4);
    }

    public String a() {
        return this.f1092f;
    }

    public String b() {
        return this.e;
    }

    public byte[] c() {
        return this.m;
    }

    public byte[] d() {
        return this.n;
    }

    public int describeContents() {
        return 0;
    }

    public int e() {
        return this.l;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ScanFilter.class != obj.getClass()) {
            return false;
        }
        ScanFilter scanFilter = (ScanFilter) obj;
        if (!h.b(this.e, scanFilter.e) || !h.b(this.f1092f, scanFilter.f1092f) || this.l != scanFilter.l || !h.a(this.m, scanFilter.m) || !h.a(this.n, scanFilter.n) || !h.b(this.f1095i, scanFilter.f1095i) || !h.a(this.f1096j, scanFilter.f1096j) || !h.a(this.k, scanFilter.k) || !h.b(this.f1093g, scanFilter.f1093g) || !h.b(this.f1094h, scanFilter.f1094h)) {
            return false;
        }
        return true;
    }

    public byte[] f() {
        return this.f1096j;
    }

    public byte[] g() {
        return this.k;
    }

    public ParcelUuid h() {
        return this.f1095i;
    }

    public int hashCode() {
        return h.a(this.e, this.f1092f, Integer.valueOf(this.l), Integer.valueOf(Arrays.hashCode(this.m)), Integer.valueOf(Arrays.hashCode(this.n)), this.f1095i, Integer.valueOf(Arrays.hashCode(this.f1096j)), Integer.valueOf(Arrays.hashCode(this.k)), this.f1093g, this.f1094h);
    }

    public ParcelUuid i() {
        return this.f1093g;
    }

    public ParcelUuid j() {
        return this.f1094h;
    }

    public String toString() {
        return "BluetoothLeScanFilter [deviceName=" + this.e + ", deviceAddress=" + this.f1092f + ", mUuid=" + this.f1093g + ", uuidMask=" + this.f1094h + ", serviceDataUuid=" + h.a((Object) this.f1095i) + ", serviceData=" + Arrays.toString(this.f1096j) + ", serviceDataMask=" + Arrays.toString(this.k) + ", manufacturerId=" + this.l + ", manufacturerData=" + Arrays.toString(this.m) + ", manufacturerDataMask=" + Arrays.toString(this.n) + "]";
    }

    public void writeToParcel(Parcel parcel, int i2) {
        int i3 = 0;
        parcel.writeInt(this.e == null ? 0 : 1);
        String str = this.e;
        if (str != null) {
            parcel.writeString(str);
        }
        parcel.writeInt(this.f1092f == null ? 0 : 1);
        String str2 = this.f1092f;
        if (str2 != null) {
            parcel.writeString(str2);
        }
        parcel.writeInt(this.f1093g == null ? 0 : 1);
        ParcelUuid parcelUuid = this.f1093g;
        if (parcelUuid != null) {
            parcel.writeParcelable(parcelUuid, i2);
            parcel.writeInt(this.f1094h == null ? 0 : 1);
            ParcelUuid parcelUuid2 = this.f1094h;
            if (parcelUuid2 != null) {
                parcel.writeParcelable(parcelUuid2, i2);
            }
        }
        parcel.writeInt(this.f1095i == null ? 0 : 1);
        ParcelUuid parcelUuid3 = this.f1095i;
        if (parcelUuid3 != null) {
            parcel.writeParcelable(parcelUuid3, i2);
            parcel.writeInt(this.f1096j == null ? 0 : 1);
            byte[] bArr = this.f1096j;
            if (bArr != null) {
                parcel.writeInt(bArr.length);
                parcel.writeByteArray(this.f1096j);
                parcel.writeInt(this.k == null ? 0 : 1);
                byte[] bArr2 = this.k;
                if (bArr2 != null) {
                    parcel.writeInt(bArr2.length);
                    parcel.writeByteArray(this.k);
                }
            }
        }
        parcel.writeInt(this.l);
        parcel.writeInt(this.m == null ? 0 : 1);
        byte[] bArr3 = this.m;
        if (bArr3 != null) {
            parcel.writeInt(bArr3.length);
            parcel.writeByteArray(this.m);
            if (this.n != null) {
                i3 = 1;
            }
            parcel.writeInt(i3);
            byte[] bArr4 = this.n;
            if (bArr4 != null) {
                parcel.writeInt(bArr4.length);
                parcel.writeByteArray(this.n);
            }
        }
    }

    private ScanFilter(String str, String str2, ParcelUuid parcelUuid, ParcelUuid parcelUuid2, ParcelUuid parcelUuid3, byte[] bArr, byte[] bArr2, int i2, byte[] bArr3, byte[] bArr4) {
        this.e = str;
        this.f1093g = parcelUuid;
        this.f1094h = parcelUuid2;
        this.f1092f = str2;
        this.f1095i = parcelUuid3;
        this.f1096j = bArr;
        this.k = bArr2;
        this.l = i2;
        this.m = bArr3;
        this.n = bArr4;
    }

    public boolean a(ScanResult scanResult) {
        if (scanResult == null) {
            return false;
        }
        BluetoothDevice a2 = scanResult.a();
        String str = this.f1092f;
        if (str != null && !str.equals(a2.getAddress())) {
            return false;
        }
        k c = scanResult.c();
        if (c == null && (this.e != null || this.f1093g != null || this.m != null || this.f1096j != null)) {
            return false;
        }
        String str2 = this.e;
        if (str2 != null && !str2.equals(c.b())) {
            return false;
        }
        ParcelUuid parcelUuid = this.f1093g;
        if (parcelUuid != null && !a(parcelUuid, this.f1094h, c.c())) {
            return false;
        }
        ParcelUuid parcelUuid2 = this.f1095i;
        if (parcelUuid2 != null && c != null && !a(this.f1096j, this.k, c.a(parcelUuid2))) {
            return false;
        }
        int i2 = this.l;
        if (i2 < 0 || c == null || a(this.m, this.n, c.a(i2))) {
            return true;
        }
        return false;
    }

    public static final class b {
        private String a;
        private String b;
        private ParcelUuid c;
        private ParcelUuid d;
        private ParcelUuid e;

        /* renamed from: f  reason: collision with root package name */
        private byte[] f1097f;

        /* renamed from: g  reason: collision with root package name */
        private byte[] f1098g;

        /* renamed from: h  reason: collision with root package name */
        private int f1099h = -1;

        /* renamed from: i  reason: collision with root package name */
        private byte[] f1100i;

        /* renamed from: j  reason: collision with root package name */
        private byte[] f1101j;

        public b a(String str) {
            if (str == null || BluetoothAdapter.checkBluetoothAddress(str)) {
                this.b = str;
                return this;
            }
            throw new IllegalArgumentException("invalid device address " + str);
        }

        public b b(String str) {
            this.a = str;
            return this;
        }

        public b a(ParcelUuid parcelUuid) {
            this.c = parcelUuid;
            this.d = null;
            return this;
        }

        public b a(ParcelUuid parcelUuid, ParcelUuid parcelUuid2) {
            if (parcelUuid2 == null || parcelUuid != null) {
                this.c = parcelUuid;
                this.d = parcelUuid2;
                return this;
            }
            throw new IllegalArgumentException("uuid is null while uuidMask is not null!");
        }

        public b a(ParcelUuid parcelUuid, byte[] bArr) {
            if (parcelUuid != null) {
                this.e = parcelUuid;
                this.f1097f = bArr;
                this.f1098g = null;
                return this;
            }
            throw new IllegalArgumentException("serviceDataUuid is null!");
        }

        public b a(ParcelUuid parcelUuid, byte[] bArr, byte[] bArr2) {
            if (parcelUuid != null) {
                if (bArr2 != null) {
                    if (bArr == null) {
                        throw new IllegalArgumentException("serviceData is null while serviceDataMask is not null");
                    } else if (bArr.length != bArr2.length) {
                        throw new IllegalArgumentException("size mismatch for service data and service data mask");
                    }
                }
                this.e = parcelUuid;
                this.f1097f = bArr;
                this.f1098g = bArr2;
                return this;
            }
            throw new IllegalArgumentException("serviceDataUuid is null");
        }

        public b a(int i2, byte[] bArr) {
            if (bArr == null || i2 >= 0) {
                this.f1099h = i2;
                this.f1100i = bArr;
                this.f1101j = null;
                return this;
            }
            throw new IllegalArgumentException("invalid manufacture id");
        }

        public b a(int i2, byte[] bArr, byte[] bArr2) {
            if (bArr == null || i2 >= 0) {
                if (bArr2 != null) {
                    if (bArr == null) {
                        throw new IllegalArgumentException("manufacturerData is null while manufacturerDataMask is not null");
                    } else if (bArr.length != bArr2.length) {
                        throw new IllegalArgumentException("size mismatch for manufacturerData and manufacturerDataMask");
                    }
                }
                this.f1099h = i2;
                this.f1100i = bArr;
                this.f1101j = bArr2;
                return this;
            }
            throw new IllegalArgumentException("invalid manufacture id");
        }

        public ScanFilter a() {
            return new ScanFilter(this.a, this.b, this.c, this.d, this.e, this.f1097f, this.f1098g, this.f1099h, this.f1100i, this.f1101j, (a) null);
        }
    }

    private static boolean a(ParcelUuid parcelUuid, ParcelUuid parcelUuid2, List<ParcelUuid> list) {
        UUID uuid;
        if (parcelUuid == null) {
            return true;
        }
        if (list == null) {
            return false;
        }
        for (ParcelUuid next : list) {
            if (parcelUuid2 == null) {
                uuid = null;
            } else {
                uuid = parcelUuid2.getUuid();
            }
            if (a(parcelUuid.getUuid(), uuid, next.getUuid())) {
                return true;
            }
        }
        return false;
    }

    private static boolean a(UUID uuid, UUID uuid2, UUID uuid3) {
        if (uuid2 == null) {
            return uuid.equals(uuid3);
        }
        if ((uuid.getLeastSignificantBits() & uuid2.getLeastSignificantBits()) != (uuid3.getLeastSignificantBits() & uuid2.getLeastSignificantBits())) {
            return false;
        }
        if ((uuid.getMostSignificantBits() & uuid2.getMostSignificantBits()) == (uuid2.getMostSignificantBits() & uuid3.getMostSignificantBits())) {
            return true;
        }
        return false;
    }

    private boolean a(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        if (bArr == null) {
            return bArr3 != null;
        }
        if (bArr3 == null || bArr3.length < bArr.length) {
            return false;
        }
        if (bArr2 == null) {
            for (int i2 = 0; i2 < bArr.length; i2++) {
                if (bArr3[i2] != bArr[i2]) {
                    return false;
                }
            }
            return true;
        }
        for (int i3 = 0; i3 < bArr.length; i3++) {
            if ((bArr2[i3] & bArr3[i3]) != (bArr2[i3] & bArr[i3])) {
                return false;
            }
        }
        return true;
    }
}
