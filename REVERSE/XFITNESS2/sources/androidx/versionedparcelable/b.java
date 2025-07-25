package androidx.versionedparcelable;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseIntArray;
import com.jeremyliao.liveeventbus.BuildConfig;
import g.a.a;
import java.lang.reflect.Method;

/* compiled from: VersionedParcelParcel */
class b extends a {
    private final SparseIntArray d;
    private final Parcel e;

    /* renamed from: f  reason: collision with root package name */
    private final int f940f;

    /* renamed from: g  reason: collision with root package name */
    private final int f941g;

    /* renamed from: h  reason: collision with root package name */
    private final String f942h;

    /* renamed from: i  reason: collision with root package name */
    private int f943i;

    /* renamed from: j  reason: collision with root package name */
    private int f944j;
    private int k;

    b(Parcel parcel) {
        this(parcel, parcel.dataPosition(), parcel.dataSize(), BuildConfig.FLAVOR, new a(), new a(), new a());
    }

    public boolean a(int i2) {
        while (this.f944j < this.f941g) {
            int i3 = this.k;
            if (i3 == i2) {
                return true;
            }
            if (String.valueOf(i3).compareTo(String.valueOf(i2)) > 0) {
                return false;
            }
            this.e.setDataPosition(this.f944j);
            int readInt = this.e.readInt();
            this.k = this.e.readInt();
            this.f944j += readInt;
        }
        if (this.k == i2) {
            return true;
        }
        return false;
    }

    public void b(int i2) {
        a();
        this.f943i = i2;
        this.d.put(i2, this.e.dataPosition());
        c(0);
        c(i2);
    }

    public void c(int i2) {
        this.e.writeInt(i2);
    }

    public boolean d() {
        return this.e.readInt() != 0;
    }

    public byte[] e() {
        int readInt = this.e.readInt();
        if (readInt < 0) {
            return null;
        }
        byte[] bArr = new byte[readInt];
        this.e.readByteArray(bArr);
        return bArr;
    }

    /* access modifiers changed from: protected */
    public CharSequence f() {
        return (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(this.e);
    }

    public int g() {
        return this.e.readInt();
    }

    public <T extends Parcelable> T h() {
        return this.e.readParcelable(b.class.getClassLoader());
    }

    public String i() {
        return this.e.readString();
    }

    private b(Parcel parcel, int i2, int i3, String str, a<String, Method> aVar, a<String, Method> aVar2, a<String, Class> aVar3) {
        super(aVar, aVar2, aVar3);
        this.d = new SparseIntArray();
        this.f943i = -1;
        this.f944j = 0;
        this.k = -1;
        this.e = parcel;
        this.f940f = i2;
        this.f941g = i3;
        this.f944j = i2;
        this.f942h = str;
    }

    /* access modifiers changed from: protected */
    public a b() {
        Parcel parcel = this.e;
        int dataPosition = parcel.dataPosition();
        int i2 = this.f944j;
        if (i2 == this.f940f) {
            i2 = this.f941g;
        }
        int i3 = i2;
        return new b(parcel, dataPosition, i3, this.f942h + "  ", this.a, this.b, this.c);
    }

    public void a() {
        int i2 = this.f943i;
        if (i2 >= 0) {
            int i3 = this.d.get(i2);
            int dataPosition = this.e.dataPosition();
            this.e.setDataPosition(i3);
            this.e.writeInt(dataPosition - i3);
            this.e.setDataPosition(dataPosition);
        }
    }

    public void a(byte[] bArr) {
        if (bArr != null) {
            this.e.writeInt(bArr.length);
            this.e.writeByteArray(bArr);
            return;
        }
        this.e.writeInt(-1);
    }

    public void a(String str) {
        this.e.writeString(str);
    }

    public void a(Parcelable parcelable) {
        this.e.writeParcelable(parcelable, 0);
    }

    public void a(boolean z) {
        this.e.writeInt(z ? 1 : 0);
    }

    /* access modifiers changed from: protected */
    public void a(CharSequence charSequence) {
        TextUtils.writeToParcel(charSequence, this.e, 0);
    }
}
