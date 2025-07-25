package com.android.chileaf.bluetooth.connect.callback.profile;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.bluetooth.connect.response.ReadResponse;

public class ProfileReadResponse extends ReadResponse implements a, Parcelable {
    public static final Parcelable.Creator<ProfileReadResponse> CREATOR = new a();

    /* renamed from: g  reason: collision with root package name */
    private boolean f1037g = true;

    static class a implements Parcelable.Creator<ProfileReadResponse> {
        a() {
        }

        public ProfileReadResponse createFromParcel(Parcel parcel) {
            return new ProfileReadResponse(parcel);
        }

        public ProfileReadResponse[] newArray(int i2) {
            return new ProfileReadResponse[i2];
        }
    }

    public ProfileReadResponse() {
    }

    public void b(BluetoothDevice bluetoothDevice, Data data) {
        this.f1037g = false;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        super.writeToParcel(parcel, i2);
        parcel.writeByte(this.f1037g ? (byte) 1 : 0);
    }

    protected ProfileReadResponse(Parcel parcel) {
        super(parcel);
        boolean z = true;
        this.f1037g = parcel.readByte() == 0 ? false : z;
    }
}
