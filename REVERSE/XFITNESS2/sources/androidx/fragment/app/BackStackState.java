package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.fragment.app.p;
import androidx.lifecycle.Lifecycle;
import java.util.ArrayList;

@SuppressLint({"BanParcelableUsage"})
final class BackStackState implements Parcelable {
    public static final Parcelable.Creator<BackStackState> CREATOR = new a();
    final int[] e;

    /* renamed from: f  reason: collision with root package name */
    final ArrayList<String> f580f;

    /* renamed from: g  reason: collision with root package name */
    final int[] f581g;

    /* renamed from: h  reason: collision with root package name */
    final int[] f582h;

    /* renamed from: i  reason: collision with root package name */
    final int f583i;

    /* renamed from: j  reason: collision with root package name */
    final String f584j;
    final int k;
    final int l;
    final CharSequence m;
    final int n;
    final CharSequence o;
    final ArrayList<String> p;
    final ArrayList<String> q;
    final boolean r;

    static class a implements Parcelable.Creator<BackStackState> {
        a() {
        }

        public BackStackState createFromParcel(Parcel parcel) {
            return new BackStackState(parcel);
        }

        public BackStackState[] newArray(int i2) {
            return new BackStackState[i2];
        }
    }

    public BackStackState(a aVar) {
        int size = aVar.a.size();
        this.e = new int[(size * 5)];
        if (aVar.f628g) {
            this.f580f = new ArrayList<>(size);
            this.f581g = new int[size];
            this.f582h = new int[size];
            int i2 = 0;
            int i3 = 0;
            while (i2 < size) {
                p.a aVar2 = aVar.a.get(i2);
                int i4 = i3 + 1;
                this.e[i3] = aVar2.a;
                ArrayList<String> arrayList = this.f580f;
                Fragment fragment = aVar2.b;
                arrayList.add(fragment != null ? fragment.f588i : null);
                int[] iArr = this.e;
                int i5 = i4 + 1;
                iArr[i4] = aVar2.c;
                int i6 = i5 + 1;
                iArr[i5] = aVar2.d;
                int i7 = i6 + 1;
                iArr[i6] = aVar2.e;
                iArr[i7] = aVar2.f632f;
                this.f581g[i2] = aVar2.f633g.ordinal();
                this.f582h[i2] = aVar2.f634h.ordinal();
                i2++;
                i3 = i7 + 1;
            }
            this.f583i = aVar.f627f;
            this.f584j = aVar.f630i;
            this.k = aVar.t;
            this.l = aVar.f631j;
            this.m = aVar.k;
            this.n = aVar.l;
            this.o = aVar.m;
            this.p = aVar.n;
            this.q = aVar.o;
            this.r = aVar.p;
            return;
        }
        throw new IllegalStateException("Not on back stack");
    }

    public a a(j jVar) {
        a aVar = new a(jVar);
        int i2 = 0;
        int i3 = 0;
        while (i2 < this.e.length) {
            p.a aVar2 = new p.a();
            int i4 = i2 + 1;
            aVar2.a = this.e[i2];
            if (j.d(2)) {
                Log.v("FragmentManager", "Instantiate " + aVar + " op #" + i3 + " base fragment #" + this.e[i4]);
            }
            String str = this.f580f.get(i3);
            if (str != null) {
                aVar2.b = jVar.a(str);
            } else {
                aVar2.b = null;
            }
            aVar2.f633g = Lifecycle.State.values()[this.f581g[i3]];
            aVar2.f634h = Lifecycle.State.values()[this.f582h[i3]];
            int[] iArr = this.e;
            int i5 = i4 + 1;
            int i6 = iArr[i4];
            aVar2.c = i6;
            int i7 = i5 + 1;
            int i8 = iArr[i5];
            aVar2.d = i8;
            int i9 = i7 + 1;
            int i10 = iArr[i7];
            aVar2.e = i10;
            int i11 = iArr[i9];
            aVar2.f632f = i11;
            aVar.b = i6;
            aVar.c = i8;
            aVar.d = i10;
            aVar.e = i11;
            aVar.a(aVar2);
            i3++;
            i2 = i9 + 1;
        }
        aVar.f627f = this.f583i;
        aVar.f630i = this.f584j;
        aVar.t = this.k;
        aVar.f628g = true;
        aVar.f631j = this.l;
        aVar.k = this.m;
        aVar.l = this.n;
        aVar.m = this.o;
        aVar.n = this.p;
        aVar.o = this.q;
        aVar.p = this.r;
        aVar.a(1);
        return aVar;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeIntArray(this.e);
        parcel.writeStringList(this.f580f);
        parcel.writeIntArray(this.f581g);
        parcel.writeIntArray(this.f582h);
        parcel.writeInt(this.f583i);
        parcel.writeString(this.f584j);
        parcel.writeInt(this.k);
        parcel.writeInt(this.l);
        TextUtils.writeToParcel(this.m, parcel, 0);
        parcel.writeInt(this.n);
        TextUtils.writeToParcel(this.o, parcel, 0);
        parcel.writeStringList(this.p);
        parcel.writeStringList(this.q);
        parcel.writeInt(this.r ? 1 : 0);
    }

    public BackStackState(Parcel parcel) {
        this.e = parcel.createIntArray();
        this.f580f = parcel.createStringArrayList();
        this.f581g = parcel.createIntArray();
        this.f582h = parcel.createIntArray();
        this.f583i = parcel.readInt();
        this.f584j = parcel.readString();
        this.k = parcel.readInt();
        this.l = parcel.readInt();
        this.m = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.n = parcel.readInt();
        this.o = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.p = parcel.createStringArrayList();
        this.q = parcel.createStringArrayList();
        this.r = parcel.readInt() != 0;
    }
}
