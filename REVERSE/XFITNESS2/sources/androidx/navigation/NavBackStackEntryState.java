package androidx.navigation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.UUID;

@SuppressLint({"BanParcelableUsage"})
final class NavBackStackEntryState implements Parcelable {
    public static final Parcelable.Creator<NavBackStackEntryState> CREATOR = new a();
    private final UUID e;

    /* renamed from: f  reason: collision with root package name */
    private final int f719f;

    /* renamed from: g  reason: collision with root package name */
    private final Bundle f720g;

    /* renamed from: h  reason: collision with root package name */
    private final Bundle f721h;

    static class a implements Parcelable.Creator<NavBackStackEntryState> {
        a() {
        }

        public NavBackStackEntryState createFromParcel(Parcel parcel) {
            return new NavBackStackEntryState(parcel);
        }

        public NavBackStackEntryState[] newArray(int i2) {
            return new NavBackStackEntryState[i2];
        }
    }

    NavBackStackEntryState(f fVar) {
        this.e = fVar.f731j;
        this.f719f = fVar.d().d();
        this.f720g = fVar.c();
        Bundle bundle = new Bundle();
        this.f721h = bundle;
        fVar.a(bundle);
    }

    /* access modifiers changed from: package-private */
    public Bundle a() {
        return this.f720g;
    }

    /* access modifiers changed from: package-private */
    public int b() {
        return this.f719f;
    }

    /* access modifiers changed from: package-private */
    public Bundle c() {
        return this.f721h;
    }

    /* access modifiers changed from: package-private */
    public UUID d() {
        return this.e;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.e.toString());
        parcel.writeInt(this.f719f);
        parcel.writeBundle(this.f720g);
        parcel.writeBundle(this.f721h);
    }

    NavBackStackEntryState(Parcel parcel) {
        this.e = UUID.fromString(parcel.readString());
        this.f719f = parcel.readInt();
        this.f720g = parcel.readBundle(NavBackStackEntryState.class.getClassLoader());
        this.f721h = parcel.readBundle(NavBackStackEntryState.class.getClassLoader());
    }
}
