package kotlin.collections;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.internal.i;

/* compiled from: Maps.kt */
class x extends w {
    public static final <K, V> Map<K, V> a() {
        EmptyMap emptyMap = EmptyMap.INSTANCE;
        if (emptyMap != null) {
            return emptyMap;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
    }

    public static final <K, V> Map<K, V> b(Map<K, ? extends V> map) {
        i.b(map, "$this$optimizeReadOnlyMap");
        int size = map.size();
        if (size == 0) {
            return a();
        }
        if (size != 1) {
            return map;
        }
        return w.a(map);
    }

    public static <K, V> Map<K, V> a(Pair<? extends K, ? extends V>... pairArr) {
        i.b(pairArr, "pairs");
        if (pairArr.length <= 0) {
            return a();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(w.a(pairArr.length));
        a(pairArr, linkedHashMap);
        return linkedHashMap;
    }

    public static final <K, V> void a(Map<? super K, ? super V> map, Pair<? extends K, ? extends V>[] pairArr) {
        i.b(map, "$this$putAll");
        i.b(pairArr, "pairs");
        for (Pair<? extends K, ? extends V> pair : pairArr) {
            map.put(pair.component1(), pair.component2());
        }
    }

    public static final <K, V> void a(Map<? super K, ? super V> map, Iterable<? extends Pair<? extends K, ? extends V>> iterable) {
        i.b(map, "$this$putAll");
        i.b(iterable, "pairs");
        for (Pair pair : iterable) {
            map.put(pair.component1(), pair.component2());
        }
    }

    public static <K, V> Map<K, V> a(Iterable<? extends Pair<? extends K, ? extends V>> iterable) {
        i.b(iterable, "$this$toMap");
        if (iterable instanceof Collection) {
            Collection collection = (Collection) iterable;
            int size = collection.size();
            if (size == 0) {
                return a();
            }
            if (size != 1) {
                LinkedHashMap linkedHashMap = new LinkedHashMap(w.a(collection.size()));
                a(iterable, linkedHashMap);
                return linkedHashMap;
            }
            return w.a((Pair) (iterable instanceof List ? ((List) iterable).get(0) : iterable.iterator().next()));
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        a(iterable, linkedHashMap2);
        return b(linkedHashMap2);
    }

    public static final <K, V, M extends Map<? super K, ? super V>> M a(Iterable<? extends Pair<? extends K, ? extends V>> iterable, M m) {
        i.b(iterable, "$this$toMap");
        i.b(m, "destination");
        a(m, iterable);
        return m;
    }

    public static final <K, V, M extends Map<? super K, ? super V>> M a(Pair<? extends K, ? extends V>[] pairArr, M m) {
        i.b(pairArr, "$this$toMap");
        i.b(m, "destination");
        a(m, pairArr);
        return m;
    }
}
