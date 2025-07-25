package kotlin.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.jvm.internal.i;

/* compiled from: _Arrays.kt */
class f extends e {
    public static final <T> boolean a(T[] tArr, T t) {
        i.b(tArr, "$this$contains");
        return b(tArr, t) >= 0;
    }

    public static final <T> int b(T[] tArr, T t) {
        i.b(tArr, "$this$indexOf");
        int i2 = 0;
        if (t == null) {
            int length = tArr.length;
            while (i2 < length) {
                if (tArr[i2] == null) {
                    return i2;
                }
                i2++;
            }
            return -1;
        }
        int length2 = tArr.length;
        while (i2 < length2) {
            if (i.a((Object) t, (Object) tArr[i2])) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public static <T> T c(T[] tArr) {
        i.b(tArr, "$this$singleOrNull");
        if (tArr.length == 1) {
            return tArr[0];
        }
        return null;
    }

    public static <T> List<T> d(T[] tArr) {
        i.b(tArr, "$this$toList");
        int length = tArr.length;
        if (length == 0) {
            return j.a();
        }
        if (length != 1) {
            return e(tArr);
        }
        return i.a(tArr[0]);
    }

    public static final <T> List<T> e(T[] tArr) {
        i.b(tArr, "$this$toMutableList");
        return new ArrayList(j.a(tArr));
    }

    public static final <T> Set<T> f(T[] tArr) {
        i.b(tArr, "$this$toSet");
        int length = tArr.length;
        if (length == 0) {
            return b0.a();
        }
        if (length == 1) {
            return a0.a(tArr[0]);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(w.a(tArr.length));
        a(tArr, linkedHashSet);
        return linkedHashSet;
    }

    public static boolean a(int[] iArr, int i2) {
        i.b(iArr, "$this$contains");
        return b(iArr, i2) >= 0;
    }

    public static <T> List<T> c(T[] tArr, Comparator<? super T> comparator) {
        i.b(tArr, "$this$sortedWith");
        i.b(comparator, "comparator");
        return e.a(b(tArr, comparator));
    }

    public static char a(char[] cArr) {
        i.b(cArr, "$this$single");
        int length = cArr.length;
        if (length == 0) {
            throw new NoSuchElementException("Array is empty.");
        } else if (length == 1) {
            return cArr[0];
        } else {
            throw new IllegalArgumentException("Array has more than one element.");
        }
    }

    public static final int b(int[] iArr, int i2) {
        i.b(iArr, "$this$indexOf");
        int length = iArr.length;
        for (int i3 = 0; i3 < length; i3++) {
            if (i2 == iArr[i3]) {
                return i3;
            }
        }
        return -1;
    }

    public static final <T, C extends Collection<? super T>> C a(T[] tArr, C c) {
        i.b(tArr, "$this$toCollection");
        i.b(c, "destination");
        for (T add : tArr) {
            c.add(add);
        }
        return c;
    }

    public static <T> List<T> b(T[] tArr) {
        i.b(tArr, "$this$reversed");
        if (tArr.length == 0) {
            return j.a();
        }
        List<T> e = e(tArr);
        q.b(e);
        return e;
    }

    public static final <T> T[] b(T[] tArr, Comparator<? super T> comparator) {
        i.b(tArr, "$this$sortedArrayWith");
        i.b(comparator, "comparator");
        if (tArr.length == 0) {
            return tArr;
        }
        T[] copyOf = Arrays.copyOf(tArr, tArr.length);
        i.a((Object) copyOf, "java.util.Arrays.copyOf(this, size)");
        e.a(copyOf, comparator);
        return copyOf;
    }
}
