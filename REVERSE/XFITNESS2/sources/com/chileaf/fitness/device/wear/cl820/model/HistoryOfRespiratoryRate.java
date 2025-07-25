package com.chileaf.fitness.device.wear.cl820.model;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryOfRespiratoryRate implements Parcelable {
    public static final Parcelable.Creator<HistoryOfRespiratoryRate> CREATOR = new Parcelable.Creator<HistoryOfRespiratoryRate>() {
        public HistoryOfRespiratoryRate createFromParcel(Parcel parcel) {
            return new HistoryOfRespiratoryRate(parcel);
        }

        public HistoryOfRespiratoryRate[] newArray(int i2) {
            return new HistoryOfRespiratoryRate[i2];
        }
    };
    public int heartRate;
    public int respiratoryRate;
    public long stamp;

    public HistoryOfRespiratoryRate(long j2, int i2, int i3) {
        this.stamp = j2;
        this.heartRate = i2;
        this.respiratoryRate = i3;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "HistoryOfHeartRate{stamp=" + this.stamp + ", heartRates=" + this.heartRate + ", respiratoryRate=" + this.respiratoryRate + '}';
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeLong(this.stamp);
        parcel.writeInt(this.heartRate);
        parcel.writeInt(this.respiratoryRate);
    }

    protected HistoryOfRespiratoryRate(Parcel parcel) {
        this.stamp = parcel.readLong();
        this.heartRate = parcel.readInt();
        this.respiratoryRate = parcel.readInt();
    }
}
