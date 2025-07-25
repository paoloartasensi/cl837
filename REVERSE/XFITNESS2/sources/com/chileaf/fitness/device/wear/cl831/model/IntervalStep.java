package com.chileaf.fitness.device.wear.cl831.model;

import android.os.Parcel;
import android.os.Parcelable;

public class IntervalStep implements Parcelable {
    public static final Parcelable.Creator<IntervalStep> CREATOR = new a();
    public long e;

    /* renamed from: f  reason: collision with root package name */
    public int f1194f;

    static class a implements Parcelable.Creator<IntervalStep> {
        a() {
        }

        public IntervalStep createFromParcel(Parcel parcel) {
            return new IntervalStep(parcel);
        }

        public IntervalStep[] newArray(int i2) {
            return new IntervalStep[i2];
        }
    }

    public IntervalStep(long j2, int i2) {
        this.e = j2;
        this.f1194f = i2;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "IntervalStep{startTime=" + this.e + ", steps=" + this.f1194f + '}';
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeLong(this.e);
        parcel.writeInt(this.f1194f);
    }

    protected IntervalStep(Parcel parcel) {
        this.e = parcel.readLong();
        this.f1194f = parcel.readInt();
    }
}
