package com.google.gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: JsonArray */
public final class f extends i implements Iterable<i> {
    private final List<i> e = new ArrayList();

    public void a(i iVar) {
        if (iVar == null) {
            iVar = j.a;
        }
        this.e.add(iVar);
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof f) && ((f) obj).e.equals(this.e));
    }

    public int hashCode() {
        return this.e.hashCode();
    }

    public Iterator<i> iterator() {
        return this.e.iterator();
    }
}
