package com.android.chileaf.adapt;

import android.os.Parcel;
import android.os.Parcelable;

public class DisplayMetricsInfo implements Parcelable {
    public static final Parcelable.Creator<DisplayMetricsInfo> CREATOR = new a();
    private float e;

    /* renamed from: f  reason: collision with root package name */
    private int f1007f;

    /* renamed from: g  reason: collision with root package name */
    private float f1008g;

    /* renamed from: h  reason: collision with root package name */
    private float f1009h;

    /* renamed from: i  reason: collision with root package name */
    private int f1010i;

    /* renamed from: j  reason: collision with root package name */
    private int f1011j;

    static class a implements Parcelable.Creator<DisplayMetricsInfo> {
        a() {
        }

        public DisplayMetricsInfo createFromParcel(Parcel parcel) {
            return new DisplayMetricsInfo(parcel);
        }

        public DisplayMetricsInfo[] newArray(int i2) {
            return new DisplayMetricsInfo[i2];
        }
    }

    public DisplayMetricsInfo(float f2, int i2, float f3, float f4, int i3, int i4) {
        this.e = f2;
        this.f1007f = i2;
        this.f1008g = f3;
        this.f1009h = f4;
        this.f1010i = i3;
        this.f1011j = i4;
    }

    public float a() {
        return this.e;
    }

    public int b() {
        return this.f1007f;
    }

    public float c() {
        return this.f1008g;
    }

    public int d() {
        return this.f1011j;
    }

    public int describeContents() {
        return 0;
    }

    public int e() {
        return this.f1010i;
    }

    public float f() {
        return this.f1009h;
    }

    public String toString() {
        return "DisplayMetricsInfo{density=" + this.e + ", densityDpi=" + this.f1007f + ", scaledDensity=" + this.f1008g + ", xdpi=" + this.f1009h + ", screenWidthDp=" + this.f1010i + ", screenHeightDp=" + this.f1011j + '}';
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeFloat(this.e);
        parcel.writeInt(this.f1007f);
        parcel.writeFloat(this.f1008g);
        parcel.writeFloat(this.f1009h);
        parcel.writeInt(this.f1010i);
        parcel.writeInt(this.f1011j);
    }

    protected DisplayMetricsInfo(Parcel parcel) {
        this.e = parcel.readFloat();
        this.f1007f = parcel.readInt();
        this.f1008g = parcel.readFloat();
        this.f1009h = parcel.readFloat();
        this.f1010i = parcel.readInt();
        this.f1011j = parcel.readInt();
    }
}
