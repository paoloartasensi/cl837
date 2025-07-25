package com.android.chileaf.bluetooth.connect.response;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.chileaf.bluetooth.connect.f1.g;

public class PhyResult implements g, Parcelable {
    public static final Parcelable.Creator<PhyResult> CREATOR = new a();
    private BluetoothDevice e;

    /* renamed from: f  reason: collision with root package name */
    private int f1076f;

    /* renamed from: g  reason: collision with root package name */
    private int f1077g;

    static class a implements Parcelable.Creator<PhyResult> {
        a() {
        }

        public PhyResult createFromParcel(Parcel parcel) {
            return new PhyResult(parcel);
        }

        public PhyResult[] newArray(int i2) {
            return new PhyResult[i2];
        }
    }

    protected PhyResult(Parcel parcel) {
        this.e = (BluetoothDevice) parcel.readParcelable(BluetoothDevice.class.getClassLoader());
        this.f1076f = parcel.readInt();
        this.f1077g = parcel.readInt();
    }

    public void a(BluetoothDevice bluetoothDevice, int i2, int i3) {
        this.e = bluetoothDevice;
        this.f1076f = i2;
        this.f1077g = i3;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelable(this.e, i2);
        parcel.writeInt(this.f1076f);
        parcel.writeInt(this.f1077g);
    }
}
