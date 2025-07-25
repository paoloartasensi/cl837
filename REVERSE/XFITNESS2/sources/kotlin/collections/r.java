package kotlin.collections;

import com.jeremyliao.liveeventbus.BuildConfig;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.i;

/* compiled from: _Collections.kt */
class r extends q {
    public static final <T, C extends Collection<? super T>> C a(Iterable<? extends T> iterable, C c) {
        i.b(iterable, "$this$toCollection");
        i.b(c, "destination");
        for (Object add : iterable) {
            c.add(add);
        }
        return c;
    }

    public static <T> List<T> b(Iterable<? extends T> iterable) {
        i.b(iterable, "$this$toList");
        if (!(iterable instanceof Collection)) {
            return j.a(c(iterable));
        }
        Collection collection = (Collection) iterable;
        int size = collection.size();
        if (size == 0) {
            return j.a();
        }
        if (size != 1) {
            return a(collection);
        }
        return i.a(iterable instanceof List ? ((List) iterable).get(0) : iterable.iterator().next());
    }

    public static final <T> List<T> c(Iterable<? extends T> iterable) {
        i.b(iterable, "$this$toMutableList");
        if (iterable instanceof Collection) {
            return a((Collection) iterable);
        }
        ArrayList arrayList = new ArrayList();
        a(iterable, arrayList);
        return arrayList;
    }

    public static <T> Set<T> d(Iterable<? extends T> iterable) {
        i.b(iterable, "$this$toSet");
        if (iterable instanceof Collection) {
            Collection collection = (Collection) iterable;
            int size = collection.size();
            if (size == 0) {
                return b0.a();
            }
            if (size != 1) {
                LinkedHashSet linkedHashSet = new LinkedHashSet(w.a(collection.size()));
                a(iterable, linkedHashSet);
                return linkedHashSet;
            }
            return a0.a(iterable instanceof List ? ((List) iterable).get(0) : iterable.iterator().next());
        }
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        a(iterable, linkedHashSet2);
        return b0.a(linkedHashSet2);
    }

    public static final <T> List<T> a(Collection<? extends T> collection) {
        i.b(collection, "$this$toMutableList");
        return new ArrayList(collection);
    }

    public static final <T, A extends Appendable> A a(Iterable<? extends T> iterable, A a, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i2, CharSequence charSequence4, l<? super T, ? extends CharSequence> lVar) {
        i.b(iterable, "$this$joinTo");
        i.b(a, "buffer");
        i.b(charSequence, "separator");
        i.b(charSequence2, "prefix");
        i.b(charSequence3, "postfix");
        i.b(charSequence4, "truncated");
        a.append(charSequence2);
        int i3 = 0;
        for (Object next : iterable) {
            i3++;
            if (i3 > 1) {
                a.append(charSequence);
            }
            if (i2 >= 0 && i3 > i2) {
                break;
            }
            d.a(a, next, lVar);
        }
        if (i2 >= 0 && i3 > i2) {
            a.append(charSequence4);
        }
        a.append(charSequence3);
        return a;
    }

    public static <T> List<T> b(Collection<? extends T> collection, Iterable<? extends T> iterable) {
        i.b(collection, "$this$plus");
        i.b(iterable, "elements");
        if (iterable instanceof Collection) {
            Collection collection2 = (Collection) iterable;
            ArrayList arrayList = new ArrayList(collection.size() + collection2.size());
            arrayList.addAll(collection);
            arrayList.addAll(collection2);
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList(collection);
        boolean unused = o.a(arrayList2, iterable);
        return arrayList2;
    }

    public static /* synthetic */ String a(Iterable iterable, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i2, CharSequence charSequence4, l lVar, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            charSequence = ", ";
        }
        int i4 = i3 & 2;
        CharSequence charSequence5 = BuildConfig.FLAVOR;
        CharSequence charSequence6 = i4 != 0 ? charSequence5 : charSequence2;
        if ((i3 & 4) == 0) {
            charSequence5 = charSequence3;
        }
        int i5 = (i3 & 8) != 0 ? -1 : i2;
        if ((i3 & 16) != 0) {
            charSequence4 = "...";
        }
        CharSequence charSequence7 = charSequence4;
        if ((i3 & 32) != 0) {
            lVar = null;
        }
        return a(iterable, charSequence, charSequence6, charSequence5, i5, charSequence7, lVar);
    }

    public static final <T> String a(Iterable<? extends T> iterable, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i2, CharSequence charSequence4, l<? super T, ? extends CharSequence> lVar) {
        i.b(iterable, "$this$joinToString");
        i.b(charSequence, "separator");
        i.b(charSequence2, "prefix");
        i.b(charSequence3, "postfix");
        i.b(charSequence4, "truncated");
        StringBuilder sb = new StringBuilder();
        a(iterable, sb, charSequence, charSequence2, charSequence3, i2, charSequence4, lVar);
        String sb2 = sb.toString();
        i.a((Object) sb2, "joinTo(StringBuilder(), …ed, transform).toString()");
        return sb2;
    }

    public static int a(Iterable<Integer> iterable) {
        i.b(iterable, "$this$sum");
        int i2 = 0;
        for (Integer intValue : iterable) {
            i2 += intValue.intValue();
        }
        return i2;
    }
}
