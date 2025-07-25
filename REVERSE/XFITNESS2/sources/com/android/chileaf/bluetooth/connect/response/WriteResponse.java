package com.android.chileaf.bluetooth.connect.response;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.bluetooth.connect.f1.c;

public class WriteResponse implements c, Parcelable {
    public static final Parcelable.Creator<WriteResponse> CREATOR = new a();
    private BluetoothDevice e;

    /* renamed from: f  reason: collision with root package name */
    private Data f1080f;

    static class a implements Parcelable.Creator<WriteResponse> {
        a() {
        }

        public WriteResponse createFromParcel(Parcel parcel) {
            return new WriteResponse(parcel);
        }

        public WriteResponse[] newArray(int i2) {
            return new WriteResponse[i2];
        }
    }

    protected WriteResponse(Parcel parcel) {
        this.e = (BluetoothDevice) parcel.readParcelable(BluetoothDevice.class.getClassLoader());
        this.f1080f = (Data) parcel.readParcelable(Data.class.getClassLoader());
    }

    public void a(BluetoothDevice bluetoothDevice, Data data) {
        this.e = bluetoothDevice;
        this.f1080f = data;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelable(this.e, i2);
        parcel.writeParcelable(this.f1080f, i2);
    }
}
