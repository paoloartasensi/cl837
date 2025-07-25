package com.android.chileaf.bluetooth.scanner;

import android.os.Parcel;
import android.os.Parcelable;

public final class ScanSettings implements Parcelable {
    public static final Parcelable.Creator<ScanSettings> CREATOR = new a();
    private final long e;

    /* renamed from: f  reason: collision with root package name */
    private final long f1107f;

    /* renamed from: g  reason: collision with root package name */
    private int f1108g;

    /* renamed from: h  reason: collision with root package name */
    private int f1109h;

    /* renamed from: i  reason: collision with root package name */
    private long f1110i;

    /* renamed from: j  reason: collision with root package name */
    private int f1111j;
    private int k;
    private boolean l;
    private boolean m;
    private boolean n;
    private long o;
    private long p;
    private boolean q;
    private int r;

    static class a implements Parcelable.Creator<ScanSettings> {
        a() {
        }

        public ScanSettings createFromParcel(Parcel parcel) {
            return new ScanSettings(parcel, (a) null);
        }

        public ScanSettings[] newArray(int i2) {
            return new ScanSettings[i2];
        }
    }

    public static final class b {
        private int a = 0;
        private int b = 1;
        private long c = 0;
        private int d = 1;
        private int e = 3;

        /* renamed from: f  reason: collision with root package name */
        private boolean f1112f = true;

        /* renamed from: g  reason: collision with root package name */
        private int f1113g = 255;

        /* renamed from: h  reason: collision with root package name */
        private boolean f1114h = true;

        /* renamed from: i  reason: collision with root package name */
        private boolean f1115i = true;

        /* renamed from: j  reason: collision with root package name */
        private boolean f1116j = true;
        private long k = 10000;
        private long l = 10000;
        private long m = 0;
        private long n = 0;

        private boolean f(int i2) {
            return i2 == 1 || i2 == 2 || i2 == 4 || i2 == 6;
        }

        public b a(int i2) {
            if (f(i2)) {
                this.b = i2;
                return this;
            }
            throw new IllegalArgumentException("invalid callback type - " + i2);
        }

        public b b(int i2) {
            if (i2 < 1 || i2 > 2) {
                throw new IllegalArgumentException("invalid matchMode " + i2);
            }
            this.d = i2;
            return this;
        }

        public b c(int i2) {
            if (i2 < 1 || i2 > 3) {
                throw new IllegalArgumentException("invalid numOfMatches " + i2);
            }
            this.e = i2;
            return this;
        }

        public b d(int i2) {
            this.f1113g = i2;
            return this;
        }

        public b e(int i2) {
            if (i2 < -1 || i2 > 2) {
                throw new IllegalArgumentException("invalid scan mode " + i2);
            }
            this.a = i2;
            return this;
        }

        public b d(boolean z) {
            this.f1114h = z;
            return this;
        }

        public b b(boolean z) {
            this.f1115i = z;
            return this;
        }

        public b c(boolean z) {
            this.f1116j = z;
            return this;
        }

        private void b() {
            int i2 = this.a;
            if (i2 == 1) {
                this.n = 2000;
                this.m = 3000;
            } else if (i2 != 2) {
                this.n = 500;
                this.m = 4500;
            } else {
                this.n = 0;
                this.m = 0;
            }
        }

        public b a(long j2) {
            if (j2 >= 0) {
                this.c = j2;
                return this;
            }
            throw new IllegalArgumentException("reportDelay must be > 0");
        }

        public b a(boolean z) {
            this.f1112f = z;
            return this;
        }

        public b a(long j2, long j3) {
            if (j2 <= 0 || j3 <= 0) {
                throw new IllegalArgumentException("maxDeviceAgeMillis and taskIntervalMillis must be > 0");
            }
            this.k = j2;
            this.l = j3;
            return this;
        }

        public ScanSettings a() {
            if (this.m == 0 && this.n == 0) {
                b();
            }
            return new ScanSettings(this.a, this.b, this.c, this.d, this.e, this.f1112f, this.f1113g, this.f1114h, this.f1115i, this.f1116j, this.k, this.l, this.n, this.m, (a) null);
        }
    }

    /* synthetic */ ScanSettings(int i2, int i3, long j2, int i4, int i5, boolean z, int i6, boolean z2, boolean z3, boolean z4, long j3, long j4, long j5, long j6, a aVar) {
        this(i2, i3, j2, i4, i5, z, i6, z2, z3, z4, j3, j4, j5, j6);
    }

    /* access modifiers changed from: package-private */
    public void a() {
        this.n = false;
    }

    public int b() {
        return this.f1109h;
    }

    public boolean c() {
        return this.q;
    }

    public long d() {
        return this.o;
    }

    public int describeContents() {
        return 0;
    }

    public long e() {
        return this.p;
    }

    public int f() {
        return this.f1111j;
    }

    public int g() {
        return this.k;
    }

    public int h() {
        return this.r;
    }

    public long i() {
        return this.f1107f;
    }

    public long j() {
        return this.e;
    }

    public long k() {
        return this.f1110i;
    }

    public int l() {
        return this.f1108g;
    }

    public boolean m() {
        return this.m;
    }

    public boolean n() {
        return this.n;
    }

    public boolean o() {
        return this.l;
    }

    public boolean p() {
        return this.f1107f > 0 && this.e > 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f1108g);
        parcel.writeInt(this.f1109h);
        parcel.writeLong(this.f1110i);
        parcel.writeInt(this.f1111j);
        parcel.writeInt(this.k);
        parcel.writeInt(this.q ? 1 : 0);
        parcel.writeInt(this.r);
        parcel.writeInt(this.l ? 1 : 0);
        parcel.writeInt(this.m ? 1 : 0);
        parcel.writeLong(this.e);
        parcel.writeLong(this.f1107f);
    }

    /* synthetic */ ScanSettings(Parcel parcel, a aVar) {
        this(parcel);
    }

    private ScanSettings(int i2, int i3, long j2, int i4, int i5, boolean z, int i6, boolean z2, boolean z3, boolean z4, long j3, long j4, long j5, long j6) {
        this.f1108g = i2;
        this.f1109h = i3;
        this.f1110i = j2;
        this.k = i5;
        this.f1111j = i4;
        this.q = z;
        this.r = i6;
        this.l = z2;
        this.m = z3;
        this.n = z4;
        this.o = 1000000 * j3;
        this.p = j4;
        this.e = j5;
        this.f1107f = j6;
    }

    private ScanSettings(Parcel parcel) {
        this.f1108g = parcel.readInt();
        this.f1109h = parcel.readInt();
        this.f1110i = parcel.readLong();
        this.f1111j = parcel.readInt();
        this.k = parcel.readInt();
        boolean z = false;
        this.q = parcel.readInt() != 0;
        this.r = parcel.readInt();
        this.l = parcel.readInt() == 1;
        this.m = parcel.readInt() == 1 ? true : z;
        this.e = parcel.readLong();
        this.f1107f = parcel.readLong();
    }
}
