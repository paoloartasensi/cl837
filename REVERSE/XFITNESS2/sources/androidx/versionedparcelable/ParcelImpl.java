package androidx.versionedparcelable;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint({"BanParcelableUsage"})
public class ParcelImpl implements Parcelable {
    public static final Parcelable.Creator<ParcelImpl> CREATOR = new a();
    private final c e;

    static class a implements Parcelable.Creator<ParcelImpl> {
        a() {
        }

        public ParcelImpl createFromParcel(Parcel parcel) {
            return new ParcelImpl(parcel);
        }

        public ParcelImpl[] newArray(int i2) {
            return new ParcelImpl[i2];
        }
    }

    protected ParcelImpl(Parcel parcel) {
        this.e = new b(parcel).j();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        new b(parcel).a(this.e);
    }
}
