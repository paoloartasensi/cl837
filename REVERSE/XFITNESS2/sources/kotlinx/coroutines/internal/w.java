package kotlinx.coroutines.internal;

import kotlin.jvm.internal.i;

/* compiled from: SystemProps.common.kt */
final /* synthetic */ class w {
    public static final boolean a(String str, boolean z) {
        i.b(str, "propertyName");
        String a = u.a(str);
        return a != null ? Boolean.parseBoolean(a) : z;
    }

    public static /* synthetic */ int a(String str, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 4) != 0) {
            i3 = 1;
        }
        if ((i5 & 8) != 0) {
            i4 = Integer.MAX_VALUE;
        }
        return u.a(str, i2, i3, i4);
    }

    public static final int a(String str, int i2, int i3, int i4) {
        i.b(str, "propertyName");
        return (int) u.a(str, (long) i2, (long) i3, (long) i4);
    }

    public static /* synthetic */ long a(String str, long j2, long j3, long j4, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            j3 = 1;
        }
        long j5 = j3;
        if ((i2 & 8) != 0) {
            j4 = Long.MAX_VALUE;
        }
        return u.a(str, j2, j5, j4);
    }

    public static final long a(String str, long j2, long j3, long j4) {
        i.b(str, "propertyName");
        String a = u.a(str);
        if (a == null) {
            return j2;
        }
        Long b = k.b(a);
        if (b != null) {
            long longValue = b.longValue();
            if (j3 <= longValue && j4 >= longValue) {
                return longValue;
            }
            throw new IllegalStateException(("System property '" + str + "' should be in range " + j3 + ".." + j4 + ", but is '" + longValue + '\'').toString());
        }
        throw new IllegalStateException(("System property '" + str + "' has unrecognized value '" + a + '\'').toString());
    }
}
