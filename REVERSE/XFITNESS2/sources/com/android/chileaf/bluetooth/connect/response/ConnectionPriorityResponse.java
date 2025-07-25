package com.android.chileaf.bluetooth.connect.response;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

public class ConnectionPriorityResponse implements Parcelable {
    public static final Parcelable.Creator<ConnectionPriorityResponse> CREATOR = new a();
    private BluetoothDevice e;

    /* renamed from: f  reason: collision with root package name */
    private int f1072f;

    /* renamed from: g  reason: collision with root package name */
    private int f1073g;

    /* renamed from: h  reason: collision with root package name */
    private int f1074h;

    static class a implements Parcelable.Creator<ConnectionPriorityResponse> {
        a() {
        }

        public ConnectionPriorityResponse createFromParcel(Parcel parcel) {
            return new ConnectionPriorityResponse(parcel);
        }

        public ConnectionPriorityResponse[] newArray(int i2) {
            return new ConnectionPriorityResponse[i2];
        }
    }

    protected ConnectionPriorityResponse(Parcel parcel) {
        this.e = (BluetoothDevice) parcel.readParcelable(BluetoothDevice.class.getClassLoader());
        this.f1072f = parcel.readInt();
        this.f1073g = parcel.readInt();
        this.f1074h = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelable(this.e, i2);
        parcel.writeInt(this.f1072f);
        parcel.writeInt(this.f1073g);
        parcel.writeInt(this.f1074h);
    }
}
