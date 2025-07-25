package com.android.chileaf.adapt.external;

import android.os.Parcel;
import android.os.Parcelable;

public class ExternalAdaptInfo implements Parcelable {
    public static final Parcelable.Creator<ExternalAdaptInfo> CREATOR = new a();
    private boolean e;

    /* renamed from: f  reason: collision with root package name */
    private float f1019f;

    static class a implements Parcelable.Creator<ExternalAdaptInfo> {
        a() {
        }

        public ExternalAdaptInfo createFromParcel(Parcel parcel) {
            return new ExternalAdaptInfo(parcel);
        }

        public ExternalAdaptInfo[] newArray(int i2) {
            return new ExternalAdaptInfo[i2];
        }
    }

    protected ExternalAdaptInfo(Parcel parcel) {
        this.e = parcel.readByte() != 0;
        this.f1019f = parcel.readFloat();
    }

    public float a() {
        return this.f1019f;
    }

    public boolean b() {
        return this.e;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "ExternalAdaptInfo{isBaseOnWidth=" + this.e + ", sizeInDp=" + this.f1019f + '}';
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeByte(this.e ? (byte) 1 : 0);
        parcel.writeFloat(this.f1019f);
    }
}
