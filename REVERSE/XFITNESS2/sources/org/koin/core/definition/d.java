package org.koin.core.definition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.jvm.internal.i;

/* compiled from: Properties.kt */
public final class d {
    private final Map<String, Object> a;

    public d() {
        this((Map) null, 1, (f) null);
    }

    public d(Map<String, Object> map) {
        i.b(map, "data");
        this.a = map;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            return (obj instanceof d) && i.a((Object) this.a, (Object) ((d) obj).a);
        }
        return true;
    }

    public int hashCode() {
        Map<String, Object> map = this.a;
        if (map != null) {
            return map.hashCode();
        }
        return 0;
    }

    public String toString() {
        return "Properties(data=" + this.a + ")";
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ d(Map map, int i2, f fVar) {
        this((i2 & 1) != 0 ? new ConcurrentHashMap() : map);
    }
}
