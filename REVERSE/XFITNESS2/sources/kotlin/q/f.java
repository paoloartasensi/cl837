package kotlin.q;

/* compiled from: _Ranges.kt */
class f extends e {
    public static int a(int i2, int i3) {
        return i2 < i3 ? i3 : i2;
    }

    public static int a(int i2, int i3, int i4) {
        if (i3 > i4) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + i4 + " is less than minimum " + i3 + '.');
        } else if (i2 < i3) {
            return i3;
        } else {
            return i2 > i4 ? i4 : i2;
        }
    }

    public static long a(long j2, long j3) {
        return j2 < j3 ? j3 : j2;
    }

    public static int b(int i2, int i3) {
        return i2 > i3 ? i3 : i2;
    }

    public static long b(long j2, long j3) {
        return j2 > j3 ? j3 : j2;
    }

    public static a c(int i2, int i3) {
        return a.f1778h.a(i2, i3, -1);
    }
}
