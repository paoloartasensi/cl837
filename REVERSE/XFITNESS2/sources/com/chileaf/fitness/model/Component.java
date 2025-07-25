package com.chileaf.fitness.model;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.i;

/* compiled from: Component.kt */
public final class Component implements Parcelable {
    public static final Parcelable.Creator CREATOR = new a();
    private final String e;

    /* renamed from: f  reason: collision with root package name */
    private final int f1223f;

    /* renamed from: g  reason: collision with root package name */
    private final int f1224g;

    public static class a implements Parcelable.Creator {
        public final Object createFromParcel(Parcel parcel) {
            i.b(parcel, "in");
            return new Component(parcel.readString(), parcel.readInt(), parcel.readInt());
        }

        public final Object[] newArray(int i2) {
            return new Component[i2];
        }
    }

    public Component(String str, int i2, int i3) {
        i.b(str, "name");
        this.e = str;
        this.f1223f = i2;
        this.f1224g = i3;
    }

    public final int a() {
        return this.f1224g;
    }

    public final int b() {
        return this.f1223f;
    }

    public final String c() {
        return this.e;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Component)) {
            return false;
        }
        Component component = (Component) obj;
        return i.a((Object) this.e, (Object) component.e) && this.f1223f == component.f1223f && this.f1224g == component.f1224g;
    }

    public int hashCode() {
        String str = this.e;
        return ((((str != null ? str.hashCode() : 0) * 31) + this.f1223f) * 31) + this.f1224g;
    }

    public String toString() {
        return "Component(name=" + this.e + ", image=" + this.f1223f + ", description=" + this.f1224g + ")";
    }

    public void writeToParcel(Parcel parcel, int i2) {
        i.b(parcel, "parcel");
        parcel.writeString(this.e);
        parcel.writeInt(this.f1223f);
        parcel.writeInt(this.f1224g);
    }
}
