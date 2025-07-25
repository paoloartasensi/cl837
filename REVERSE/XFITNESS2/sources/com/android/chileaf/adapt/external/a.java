package com.android.chileaf.adapt.external;

import com.android.chileaf.adapt.j.b;
import java.util.List;
import java.util.Map;

/* compiled from: ExternalAdaptManager */
public class a {
    private List<String> a;
    private Map<String, ExternalAdaptInfo> b;
    private boolean c;

    public synchronized ExternalAdaptInfo a(Class<?> cls) {
        b.a(cls, (Object) "targetClass == null");
        if (this.b == null) {
            return null;
        }
        return this.b.get(cls.getCanonicalName());
    }

    public synchronized boolean b(Class<?> cls) {
        b.a(cls, (Object) "targetClass == null");
        if (this.a == null) {
            return false;
        }
        return this.a.contains(cls.getCanonicalName());
    }

    public boolean a() {
        return this.c;
    }
}
