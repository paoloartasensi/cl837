package com.github.mikephil.charting.data;

import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;

public class Entry extends e implements Parcelable {
    public static final Parcelable.Creator<Entry> CREATOR = new a();

    /* renamed from: h  reason: collision with root package name */
    private float f1355h = 0.0f;

    static class a implements Parcelable.Creator<Entry> {
        a() {
        }

        public Entry createFromParcel(Parcel parcel) {
            return new Entry(parcel);
        }

        public Entry[] newArray(int i2) {
            return new Entry[i2];
        }
    }

    public Entry() {
    }

    public float d() {
        return this.f1355h;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "Entry, x: " + this.f1355h + " y: " + c();
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeFloat(this.f1355h);
        parcel.writeFloat(c());
        if (a() == null) {
            parcel.writeInt(0);
        } else if (a() instanceof Parcelable) {
            parcel.writeInt(1);
            parcel.writeParcelable((Parcelable) a(), i2);
        } else {
            throw new ParcelFormatException("Cannot parcel an Entry with non-parcelable data");
        }
    }

    public Entry(float f2, float f3) {
        super(f3);
        this.f1355h = f2;
    }

    protected Entry(Parcel parcel) {
        this.f1355h = parcel.readFloat();
        a(parcel.readFloat());
        if (parcel.readInt() == 1) {
            a((Object) parcel.readParcelable(Object.class.getClassLoader()));
        }
    }
}
