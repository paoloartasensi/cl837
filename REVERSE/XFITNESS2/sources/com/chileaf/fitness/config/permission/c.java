package com.chileaf.fitness.config.permission;

import java.util.List;
import kotlin.jvm.internal.i;

/* compiled from: PermissionRequest.kt */
public final class c {
    private final b a;
    private final List<String> b;
    private final int c;

    public c(b bVar, List<String> list, int i2) {
        i.b(bVar, "permissionFragment");
        i.b(list, "permissions");
        this.a = bVar;
        this.b = list;
        this.c = i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return i.a((Object) this.a, (Object) cVar.a) && i.a((Object) this.b, (Object) cVar.b) && this.c == cVar.c;
    }

    public int hashCode() {
        b bVar = this.a;
        int i2 = 0;
        int hashCode = (bVar != null ? bVar.hashCode() : 0) * 31;
        List<String> list = this.b;
        if (list != null) {
            i2 = list.hashCode();
        }
        return ((hashCode + i2) * 31) + this.c;
    }

    public String toString() {
        return "PermissionRequest(permissionFragment=" + this.a + ", permissions=" + this.b + ", requestCode=" + this.c + ")";
    }
}
