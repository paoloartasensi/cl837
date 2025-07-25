package kotlin.jvm.internal;

/* compiled from: PackageReference.kt */
public final class j implements c {
    private final Class<?> e;

    public j(Class<?> cls, String str) {
        i.b(cls, "jClass");
        i.b(str, "moduleName");
        this.e = cls;
    }

    public Class<?> a() {
        return this.e;
    }

    public boolean equals(Object obj) {
        return (obj instanceof j) && i.a((Object) a(), (Object) ((j) obj).a());
    }

    public int hashCode() {
        return a().hashCode();
    }

    public String toString() {
        return a().toString() + " (Kotlin reflection is not available)";
    }
}
