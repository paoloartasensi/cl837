package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.i;

/* compiled from: CoroutineName.kt */
public final class f0 extends kotlin.coroutines.a {

    /* renamed from: f  reason: collision with root package name */
    public static final a f1792f = new a((f) null);
    private final String e;

    /* compiled from: CoroutineName.kt */
    public static final class a implements CoroutineContext.b<f0> {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            return (obj instanceof f0) && i.a((Object) this.e, (Object) ((f0) obj).e);
        }
        return true;
    }

    public int hashCode() {
        String str = this.e;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    public final String n() {
        return this.e;
    }

    public String toString() {
        return "CoroutineName(" + this.e + ')';
    }
}
