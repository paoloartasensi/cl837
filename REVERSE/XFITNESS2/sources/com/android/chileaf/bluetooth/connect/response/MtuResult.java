package com.android.chileaf.bluetooth.connect.response;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.chileaf.bluetooth.connect.f1.f;

public class MtuResult implements f, Parcelable {
    public static final Parcelable.Creator<MtuResult> CREATOR = new a();
    private BluetoothDevice e;

    /* renamed from: f  reason: collision with root package name */
    private int f1075f;

    static class a implements Parcelable.Creator<MtuResult> {
        a() {
        }

        public MtuResult createFromParcel(Parcel parcel) {
            return new MtuResult(parcel);
        }

        public MtuResult[] newArray(int i2) {
            return new MtuResult[i2];
        }
    }

    protected MtuResult(Parcel parcel) {
        this.e = (BluetoothDevice) parcel.readParcelable(BluetoothDevice.class.getClassLoader());
        this.f1075f = parcel.readInt();
    }

    public void a(BluetoothDevice bluetoothDevice, int i2) {
        this.e = bluetoothDevice;
        this.f1075f = i2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelable(this.e, i2);
        parcel.writeInt(this.f1075f);
    }
}
