package kotlin.collections;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import kotlin.jvm.internal.i;

/* compiled from: _ArraysJvm.kt */
class e extends d {
    public static final <T> List<T> a(T[] tArr) {
        i.b(tArr, "$this$asList");
        List<T> a = g.a(tArr);
        i.a((Object) a, "ArraysUtilJVM.asList(this)");
        return a;
    }

    public static /* synthetic */ Object[] a(Object[] objArr, Object[] objArr2, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 2) != 0) {
            i2 = 0;
        }
        if ((i5 & 4) != 0) {
            i3 = 0;
        }
        if ((i5 & 8) != 0) {
            i4 = objArr.length;
        }
        a(objArr, objArr2, i2, i3, i4);
        return objArr2;
    }

    public static final <T> T[] a(T[] tArr, T[] tArr2, int i2, int i3, int i4) {
        i.b(tArr, "$this$copyInto");
        i.b(tArr2, "destination");
        System.arraycopy(tArr, i3, tArr2, i2, i4 - i3);
        return tArr2;
    }

    public static final <T> void a(T[] tArr, Comparator<? super T> comparator) {
        i.b(tArr, "$this$sortWith");
        i.b(comparator, "comparator");
        if (tArr.length > 1) {
            Arrays.sort(tArr, comparator);
        }
    }
}
