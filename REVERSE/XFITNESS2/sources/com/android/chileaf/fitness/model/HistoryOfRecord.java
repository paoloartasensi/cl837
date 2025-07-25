package com.android.chileaf.fitness.model;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryOfRecord implements Parcelable {
    public static final Parcelable.Creator<HistoryOfRecord> CREATOR = new a();
    public long e;

    /* renamed from: f  reason: collision with root package name */
    public long f1142f;

    static class a implements Parcelable.Creator<HistoryOfRecord> {
        a() {
        }

        public HistoryOfRecord createFromParcel(Parcel parcel) {
            return new HistoryOfRecord(parcel);
        }

        public HistoryOfRecord[] newArray(int i2) {
            return new HistoryOfRecord[i2];
        }
    }

    public HistoryOfRecord(long j2, long j3) {
        this.e = j2;
        this.f1142f = j3;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "HistoryOfSport{startTime=" + this.e + ", record=" + this.f1142f + '}';
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeLong(this.e);
        parcel.writeLong(this.f1142f);
    }

    protected HistoryOfRecord(Parcel parcel) {
        this.e = parcel.readLong();
        this.f1142f = parcel.readLong();
    }
}
