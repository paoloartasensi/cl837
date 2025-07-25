package com.chileaf.fitness.device.wear.cl831.model;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryOfSport implements Parcelable {
    public static final Parcelable.Creator<HistoryOfSport> CREATOR = new a();
    public long e;

    /* renamed from: f  reason: collision with root package name */
    public long f1191f;

    /* renamed from: g  reason: collision with root package name */
    public long f1192g;

    /* renamed from: h  reason: collision with root package name */
    public long f1193h;

    static class a implements Parcelable.Creator<HistoryOfSport> {
        a() {
        }

        public HistoryOfSport createFromParcel(Parcel parcel) {
            return new HistoryOfSport(parcel);
        }

        public HistoryOfSport[] newArray(int i2) {
            return new HistoryOfSport[i2];
        }
    }

    public HistoryOfSport(long j2, long j3, long j4) {
        this.e = j2;
        this.f1192g = j3;
        this.f1193h = j4;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "HistoryOfSport{startTime=" + this.e + ", endTime=" + this.f1191f + ", step=" + this.f1192g + ", calorie=" + this.f1193h + '}';
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeLong(this.e);
        parcel.writeLong(this.f1192g);
        parcel.writeLong(this.f1193h);
        parcel.writeLong(this.f1191f);
    }

    protected HistoryOfSport(Parcel parcel) {
        this.e = parcel.readLong();
        this.f1192g = parcel.readLong();
        this.f1193h = parcel.readLong();
        this.f1191f = parcel.readLong();
    }
}
