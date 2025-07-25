package io.objectbox.internal;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/* compiled from: ReflectionCache */
public class c {
    private static final c b = new c();
    private final Map<Class, Map<String, Field>> a = new HashMap();

    public static c a() {
        return b;
    }

    public synchronized Field a(Class cls, String str) {
        Field field;
        Map map = this.a.get(cls);
        if (map == null) {
            map = new HashMap();
            this.a.put(cls, map);
        }
        field = (Field) map.get(str);
        if (field == null) {
            try {
                field = cls.getDeclaredField(str);
                field.setAccessible(true);
                map.put(str, field);
            } catch (NoSuchFieldException e) {
                throw new IllegalStateException(e);
            }
        }
        return field;
    }
}
