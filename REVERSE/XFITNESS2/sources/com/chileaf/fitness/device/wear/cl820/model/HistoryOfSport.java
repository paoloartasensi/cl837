package com.chileaf.fitness.device.wear.cl820.model;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryOfSport implements Parcelable {
    public static final Parcelable.Creator<HistoryOfSport> CREATOR = new Parcelable.Creator<HistoryOfSport>() {
        public HistoryOfSport createFromParcel(Parcel parcel) {
            return new HistoryOfSport(parcel);
        }

        public HistoryOfSport[] newArray(int i2) {
            return new HistoryOfSport[i2];
        }
    };
    public long calorie;
    public long stamp;
    public long step;

    public HistoryOfSport(long j2, long j3, long j4) {
        this.stamp = j2;
        this.step = j3;
        this.calorie = j4;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "HistoryOfSport{stamp=" + this.stamp + ", step=" + this.step + ", calorie=" + this.calorie + '}';
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeLong(this.stamp);
        parcel.writeLong(this.step);
        parcel.writeLong(this.calorie);
    }

    protected HistoryOfSport(Parcel parcel) {
        this.stamp = parcel.readLong();
        this.step = parcel.readLong();
        this.calorie = parcel.readLong();
    }
}
