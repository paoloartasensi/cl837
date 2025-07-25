package androidx.navigation;

import android.annotation.SuppressLint;
import androidx.navigation.r;
import java.util.HashMap;
import java.util.Map;

@SuppressLint({"TypeParameterUnusedInFormals"})
/* compiled from: NavigatorProvider */
public class s {
    private static final HashMap<Class<?>, String> b = new HashMap<>();
    private final HashMap<String, r<? extends j>> a = new HashMap<>();

    static String a(Class<? extends r> cls) {
        String str = b.get(cls);
        if (str == null) {
            r.b bVar = (r.b) cls.getAnnotation(r.b.class);
            str = bVar != null ? bVar.value() : null;
            if (b(str)) {
                b.put(cls, str);
            } else {
                throw new IllegalArgumentException("No @Navigator.Name annotation found for " + cls.getSimpleName());
            }
        }
        return str;
    }

    private static boolean b(String str) {
        return str != null && !str.isEmpty();
    }

    public <T extends r<?>> T a(String str) {
        if (b(str)) {
            T t = (r) this.a.get(str);
            if (t != null) {
                return t;
            }
            throw new IllegalStateException("Could not find Navigator with name \"" + str + "\". You must call NavController.addNavigator() for each navigation type.");
        }
        throw new IllegalArgumentException("navigator name cannot be an empty string");
    }

    public final r<? extends j> a(r<? extends j> rVar) {
        return a(a((Class<? extends r>) rVar.getClass()), rVar);
    }

    public r<? extends j> a(String str, r<? extends j> rVar) {
        if (b(str)) {
            return this.a.put(str, rVar);
        }
        throw new IllegalArgumentException("navigator name cannot be an empty string");
    }

    /* access modifiers changed from: package-private */
    public Map<String, r<? extends j>> a() {
        return this.a;
    }
}
