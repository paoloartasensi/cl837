package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

@SuppressLint({"BanParcelableUsage"})
final class FragmentManagerState implements Parcelable {
    public static final Parcelable.Creator<FragmentManagerState> CREATOR = new a();
    ArrayList<FragmentState> e;

    /* renamed from: f  reason: collision with root package name */
    ArrayList<String> f598f;

    /* renamed from: g  reason: collision with root package name */
    BackStackState[] f599g;

    /* renamed from: h  reason: collision with root package name */
    int f600h;

    /* renamed from: i  reason: collision with root package name */
    String f601i = null;

    static class a implements Parcelable.Creator<FragmentManagerState> {
        a() {
        }

        public FragmentManagerState createFromParcel(Parcel parcel) {
            return new FragmentManagerState(parcel);
        }

        public FragmentManagerState[] newArray(int i2) {
            return new FragmentManagerState[i2];
        }
    }

    public FragmentManagerState() {
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeTypedList(this.e);
        parcel.writeStringList(this.f598f);
        parcel.writeTypedArray(this.f599g, i2);
        parcel.writeInt(this.f600h);
        parcel.writeString(this.f601i);
    }

    public FragmentManagerState(Parcel parcel) {
        this.e = parcel.createTypedArrayList(FragmentState.CREATOR);
        this.f598f = parcel.createStringArrayList();
        this.f599g = (BackStackState[]) parcel.createTypedArray(BackStackState.CREATOR);
        this.f600h = parcel.readInt();
        this.f601i = parcel.readString();
    }
}
