package com.android.chileaf.bluetooth.connect.response;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.bluetooth.connect.f1.b;

public class ReadResponse implements b, Parcelable {
    public static final Parcelable.Creator<ReadResponse> CREATOR = new a();
    private BluetoothDevice e;

    /* renamed from: f  reason: collision with root package name */
    private Data f1078f;

    static class a implements Parcelable.Creator<ReadResponse> {
        a() {
        }

        public ReadResponse createFromParcel(Parcel parcel) {
            return new ReadResponse(parcel);
        }

        public ReadResponse[] newArray(int i2) {
            return new ReadResponse[i2];
        }
    }

    public ReadResponse() {
    }

    public void a(BluetoothDevice bluetoothDevice, Data data) {
        this.e = bluetoothDevice;
        this.f1078f = data;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelable(this.e, i2);
        parcel.writeParcelable(this.f1078f, i2);
    }

    protected ReadResponse(Parcel parcel) {
        this.e = (BluetoothDevice) parcel.readParcelable(BluetoothDevice.class.getClassLoader());
        this.f1078f = (Data) parcel.readParcelable(Data.class.getClassLoader());
    }
}
