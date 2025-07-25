package com.google.gson;

import com.google.gson.internal.LinkedTreeMap;
import java.util.Map;
import java.util.Set;

/* compiled from: JsonObject */
public final class k extends i {
    private final LinkedTreeMap<String, i> a = new LinkedTreeMap<>();

    public void a(String str, i iVar) {
        LinkedTreeMap<String, i> linkedTreeMap = this.a;
        if (iVar == null) {
            iVar = j.a;
        }
        linkedTreeMap.put(str, iVar);
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof k) && ((k) obj).a.equals(this.a));
    }

    public Set<Map.Entry<String, i>> h() {
        return this.a.entrySet();
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
