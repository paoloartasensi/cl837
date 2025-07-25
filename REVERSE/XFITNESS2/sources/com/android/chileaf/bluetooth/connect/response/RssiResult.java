package com.android.chileaf.bluetooth.connect.response;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.chileaf.bluetooth.connect.f1.i;

public class RssiResult implements i, Parcelable {
    public static final Parcelable.Creator<RssiResult> CREATOR = new a();
    private BluetoothDevice e;

    /* renamed from: f  reason: collision with root package name */
    private int f1079f;

    static class a implements Parcelable.Creator<RssiResult> {
        a() {
        }

        public RssiResult createFromParcel(Parcel parcel) {
            return new RssiResult(parcel);
        }

        public RssiResult[] newArray(int i2) {
            return new RssiResult[i2];
        }
    }

    protected RssiResult(Parcel parcel) {
        this.e = (BluetoothDevice) parcel.readParcelable(BluetoothDevice.class.getClassLoader());
        this.f1079f = parcel.readInt();
    }

    public void c(BluetoothDevice bluetoothDevice, int i2) {
        this.e = bluetoothDevice;
        this.f1079f = i2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelable(this.e, i2);
        parcel.writeInt(this.f1079f);
    }
}
