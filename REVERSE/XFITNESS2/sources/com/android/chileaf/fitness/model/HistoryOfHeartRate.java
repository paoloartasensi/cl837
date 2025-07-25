package com.android.chileaf.fitness.model;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryOfHeartRate implements Parcelable {
    public static final Parcelable.Creator<HistoryOfHeartRate> CREATOR = new a();
    public long e;

    /* renamed from: f  reason: collision with root package name */
    public int f1141f;

    static class a implements Parcelable.Creator<HistoryOfHeartRate> {
        a() {
        }

        public HistoryOfHeartRate createFromParcel(Parcel parcel) {
            return new HistoryOfHeartRate(parcel);
        }

        public HistoryOfHeartRate[] newArray(int i2) {
            return new HistoryOfHeartRate[i2];
        }
    }

    public HistoryOfHeartRate(long j2, int i2) {
        this.e = j2;
        this.f1141f = i2;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "HistoryOfHeartRate{startTime=" + this.e + ", heartRate=" + this.f1141f + '}';
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeLong(this.e);
        parcel.writeInt(this.f1141f);
    }

    protected HistoryOfHeartRate(Parcel parcel) {
        this.e = parcel.readLong();
        this.f1141f = parcel.readInt();
    }
}
