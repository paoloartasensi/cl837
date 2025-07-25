package h.a.a.a.i;

import android.os.Parcel;
import android.os.Parcelable;
import h.a.a.a.i.f;

/* compiled from: MPPointF */
public class e extends f.a {

    /* renamed from: i  reason: collision with root package name */
    private static f<e> f1726i;

    /* renamed from: g  reason: collision with root package name */
    public float f1727g;

    /* renamed from: h  reason: collision with root package name */
    public float f1728h;

    /* compiled from: MPPointF */
    static class a implements Parcelable.Creator<e> {
        a() {
        }

        public e createFromParcel(Parcel parcel) {
            e eVar = new e(0.0f, 0.0f);
            eVar.a(parcel);
            return eVar;
        }

        public e[] newArray(int i2) {
            return new e[i2];
        }
    }

    static {
        f<e> a2 = f.a(32, new e(0.0f, 0.0f));
        f1726i = a2;
        a2.a(0.5f);
        new a();
    }

    public e() {
    }

    public static e a(float f2, float f3) {
        e a2 = f1726i.a();
        a2.f1727g = f2;
        a2.f1728h = f3;
        return a2;
    }

    public static e b() {
        return f1726i.a();
    }

    public e(float f2, float f3) {
        this.f1727g = f2;
        this.f1728h = f3;
    }

    public static void b(e eVar) {
        f1726i.a(eVar);
    }

    public static e a(e eVar) {
        e a2 = f1726i.a();
        a2.f1727g = eVar.f1727g;
        a2.f1728h = eVar.f1728h;
        return a2;
    }

    public void a(Parcel parcel) {
        this.f1727g = parcel.readFloat();
        this.f1728h = parcel.readFloat();
    }

    /* access modifiers changed from: protected */
    public f.a a() {
        return new e(0.0f, 0.0f);
    }
}
