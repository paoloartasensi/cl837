package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint({"BanParcelableUsage"})
final class FragmentState implements Parcelable {
    public static final Parcelable.Creator<FragmentState> CREATOR = new a();
    final String e;

    /* renamed from: f  reason: collision with root package name */
    final String f602f;

    /* renamed from: g  reason: collision with root package name */
    final boolean f603g;

    /* renamed from: h  reason: collision with root package name */
    final int f604h;

    /* renamed from: i  reason: collision with root package name */
    final int f605i;

    /* renamed from: j  reason: collision with root package name */
    final String f606j;
    final boolean k;
    final boolean l;
    final boolean m;
    final Bundle n;
    final boolean o;
    final int p;
    Bundle q;

    static class a implements Parcelable.Creator<FragmentState> {
        a() {
        }

        public FragmentState createFromParcel(Parcel parcel) {
            return new FragmentState(parcel);
        }

        public FragmentState[] newArray(int i2) {
            return new FragmentState[i2];
        }
    }

    FragmentState(Fragment fragment) {
        this.e = fragment.getClass().getName();
        this.f602f = fragment.f588i;
        this.f603g = fragment.q;
        this.f604h = fragment.z;
        this.f605i = fragment.A;
        this.f606j = fragment.B;
        this.k = fragment.E;
        this.l = fragment.p;
        this.m = fragment.D;
        this.n = fragment.f589j;
        this.o = fragment.C;
        this.p = fragment.T.ordinal();
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentState{");
        sb.append(this.e);
        sb.append(" (");
        sb.append(this.f602f);
        sb.append(")}:");
        if (this.f603g) {
            sb.append(" fromLayout");
        }
        if (this.f605i != 0) {
            sb.append(" id=0x");
            sb.append(Integer.toHexString(this.f605i));
        }
        String str = this.f606j;
        if (str != null && !str.isEmpty()) {
            sb.append(" tag=");
            sb.append(this.f606j);
        }
        if (this.k) {
            sb.append(" retainInstance");
        }
        if (this.l) {
            sb.append(" removing");
        }
        if (this.m) {
            sb.append(" detached");
        }
        if (this.o) {
            sb.append(" hidden");
        }
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.e);
        parcel.writeString(this.f602f);
        parcel.writeInt(this.f603g ? 1 : 0);
        parcel.writeInt(this.f604h);
        parcel.writeInt(this.f605i);
        parcel.writeString(this.f606j);
        parcel.writeInt(this.k ? 1 : 0);
        parcel.writeInt(this.l ? 1 : 0);
        parcel.writeInt(this.m ? 1 : 0);
        parcel.writeBundle(this.n);
        parcel.writeInt(this.o ? 1 : 0);
        parcel.writeBundle(this.q);
        parcel.writeInt(this.p);
    }

    FragmentState(Parcel parcel) {
        this.e = parcel.readString();
        this.f602f = parcel.readString();
        boolean z = true;
        this.f603g = parcel.readInt() != 0;
        this.f604h = parcel.readInt();
        this.f605i = parcel.readInt();
        this.f606j = parcel.readString();
        this.k = parcel.readInt() != 0;
        this.l = parcel.readInt() != 0;
        this.m = parcel.readInt() != 0;
        this.n = parcel.readBundle();
        this.o = parcel.readInt() == 0 ? false : z;
        this.q = parcel.readBundle();
        this.p = parcel.readInt();
    }
}
