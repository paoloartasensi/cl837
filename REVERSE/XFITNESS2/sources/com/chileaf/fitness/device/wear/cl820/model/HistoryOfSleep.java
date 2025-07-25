package com.chileaf.fitness.device.wear.cl820.model;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryOfSleep implements Parcelable {
    public static final Parcelable.Creator<HistoryOfSleep> CREATOR = new Parcelable.Creator<HistoryOfSleep>() {
        public HistoryOfSleep createFromParcel(Parcel parcel) {
            return new HistoryOfSleep(parcel);
        }

        public HistoryOfSleep[] newArray(int i2) {
            return new HistoryOfSleep[i2];
        }
    };
    public int deepSleep;
    public long endTime;
    public int lightSleep;
    public int sorberSleep;
    public long startTime;

    public HistoryOfSleep(long j2, long j3, int i2, int i3, int i4) {
        this.startTime = j2;
        this.endTime = j3;
        this.sorberSleep = i2;
        this.lightSleep = i3;
        this.deepSleep = i4;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "HistoryOfSleep{startTime=" + this.startTime + ", endTime=" + this.endTime + ", sorberSleep=" + this.sorberSleep + ", lightSleep=" + this.lightSleep + ", deepSleep=" + this.deepSleep + '}';
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeLong(this.startTime);
        parcel.writeLong(this.endTime);
        parcel.writeInt(this.sorberSleep);
        parcel.writeInt(this.lightSleep);
        parcel.writeInt(this.deepSleep);
    }

    protected HistoryOfSleep(Parcel parcel) {
        this.startTime = parcel.readLong();
        this.endTime = parcel.readLong();
        this.sorberSleep = parcel.readInt();
        this.lightSleep = parcel.readInt();
        this.deepSleep = parcel.readInt();
    }
}
