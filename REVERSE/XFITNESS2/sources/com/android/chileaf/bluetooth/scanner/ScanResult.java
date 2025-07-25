package com.android.chileaf.bluetooth.scanner;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

public final class ScanResult implements Parcelable {
    public static final Parcelable.Creator<ScanResult> CREATOR = new a();
    private BluetoothDevice e;

    /* renamed from: f  reason: collision with root package name */
    private k f1102f;

    /* renamed from: g  reason: collision with root package name */
    private int f1103g;

    /* renamed from: h  reason: collision with root package name */
    private long f1104h;

    /* renamed from: i  reason: collision with root package name */
    private int f1105i;

    /* renamed from: j  reason: collision with root package name */
    private int f1106j;
    private int k;
    private int l;
    private int m;
    private int n;

    static class a implements Parcelable.Creator<ScanResult> {
        a() {
        }

        public ScanResult createFromParcel(Parcel parcel) {
            return new ScanResult(parcel, (a) null);
        }

        public ScanResult[] newArray(int i2) {
            return new ScanResult[i2];
        }
    }

    /* synthetic */ ScanResult(Parcel parcel, a aVar) {
        this(parcel);
    }

    private void a(Parcel parcel) {
        this.e = (BluetoothDevice) BluetoothDevice.CREATOR.createFromParcel(parcel);
        if (parcel.readInt() == 1) {
            this.f1102f = k.a(parcel.createByteArray());
        }
        this.f1103g = parcel.readInt();
        this.f1104h = parcel.readLong();
        this.f1105i = parcel.readInt();
        this.f1106j = parcel.readInt();
        this.k = parcel.readInt();
        this.l = parcel.readInt();
        this.m = parcel.readInt();
        this.n = parcel.readInt();
    }

    public int b() {
        return this.f1103g;
    }

    public k c() {
        return this.f1102f;
    }

    public long d() {
        return this.f1104h;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ScanResult.class != obj.getClass()) {
            return false;
        }
        ScanResult scanResult = (ScanResult) obj;
        if (h.b(this.e, scanResult.e) && this.f1103g == scanResult.f1103g && h.b(this.f1102f, scanResult.f1102f) && this.f1104h == scanResult.f1104h && this.f1105i == scanResult.f1105i && this.f1106j == scanResult.f1106j && this.k == scanResult.k && this.l == scanResult.l && this.m == scanResult.m && this.n == scanResult.n) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return h.a(this.e, Integer.valueOf(this.f1103g), this.f1102f, Long.valueOf(this.f1104h), Integer.valueOf(this.f1105i), Integer.valueOf(this.f1106j), Integer.valueOf(this.k), Integer.valueOf(this.l), Integer.valueOf(this.m), Integer.valueOf(this.n));
    }

    public String toString() {
        return "ScanResult{device=" + this.e + ", scanRecord=" + h.a((Object) this.f1102f) + ", rssi=" + this.f1103g + ", timestampNanos=" + this.f1104h + ", eventType=" + this.f1105i + ", primaryPhy=" + this.f1106j + ", secondaryPhy=" + this.k + ", advertisingSid=" + this.l + ", txPower=" + this.m + ", periodicAdvertisingInterval=" + this.n + '}';
    }

    public void writeToParcel(Parcel parcel, int i2) {
        this.e.writeToParcel(parcel, i2);
        if (this.f1102f != null) {
            parcel.writeInt(1);
            parcel.writeByteArray(this.f1102f.a());
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.f1103g);
        parcel.writeLong(this.f1104h);
        parcel.writeInt(this.f1105i);
        parcel.writeInt(this.f1106j);
        parcel.writeInt(this.k);
        parcel.writeInt(this.l);
        parcel.writeInt(this.m);
        parcel.writeInt(this.n);
    }

    public ScanResult(BluetoothDevice bluetoothDevice, k kVar, int i2, long j2) {
        this.e = bluetoothDevice;
        this.f1102f = kVar;
        this.f1103g = i2;
        this.f1104h = j2;
        this.f1105i = 17;
        this.f1106j = 1;
        this.k = 0;
        this.l = 255;
        this.m = 127;
        this.n = 0;
    }

    public BluetoothDevice a() {
        return this.e;
    }

    public ScanResult(BluetoothDevice bluetoothDevice, int i2, int i3, int i4, int i5, int i6, int i7, int i8, k kVar, long j2) {
        this.e = bluetoothDevice;
        this.f1105i = i2;
        this.f1106j = i3;
        this.k = i4;
        this.l = i5;
        this.m = i6;
        this.f1103g = i7;
        this.n = i8;
        this.f1102f = kVar;
        this.f1104h = j2;
    }

    private ScanResult(Parcel parcel) {
        a(parcel);
    }
}
