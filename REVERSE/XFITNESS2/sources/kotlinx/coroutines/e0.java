package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.c2;

/* compiled from: CoroutineContext.kt */
public final class e0 extends kotlin.coroutines.a implements c2<String> {

    /* renamed from: f  reason: collision with root package name */
    public static final a f1791f = new a((f) null);
    private final long e;

    /* compiled from: CoroutineContext.kt */
    public static final class a implements CoroutineContext.b<e0> {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    public e0(long j2) {
        super(f1791f);
        this.e = j2;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof e0) {
                if (this.e == ((e0) obj).e) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public <R> R fold(R r, p<? super R, ? super CoroutineContext.a, ? extends R> pVar) {
        i.b(pVar, "operation");
        return c2.a.a(this, r, pVar);
    }

    public <E extends CoroutineContext.a> E get(CoroutineContext.b<E> bVar) {
        i.b(bVar, "key");
        return c2.a.a(this, bVar);
    }

    public int hashCode() {
        long j2 = this.e;
        return (int) (j2 ^ (j2 >>> 32));
    }

    public CoroutineContext minusKey(CoroutineContext.b<?> bVar) {
        i.b(bVar, "key");
        return c2.a.b(this, bVar);
    }

    public final long n() {
        return this.e;
    }

    public CoroutineContext plus(CoroutineContext coroutineContext) {
        i.b(coroutineContext, "context");
        return c2.a.a(this, coroutineContext);
    }

    public String toString() {
        return "CoroutineId(" + this.e + ')';
    }

    public String a(CoroutineContext coroutineContext) {
        String str;
        i.b(coroutineContext, "context");
        f0 f0Var = (f0) coroutineContext.get(f0.f1792f);
        if (f0Var == null || (str = f0Var.n()) == null) {
            str = "coroutine";
        }
        Thread currentThread = Thread.currentThread();
        i.a((Object) currentThread, "currentThread");
        String name = currentThread.getName();
        i.a((Object) name, "oldName");
        int b = m.b((CharSequence) name, " @", 0, false, 6, (Object) null);
        if (b < 0) {
            b = name.length();
        }
        StringBuilder sb = new StringBuilder(str.length() + b + 10);
        String substring = name.substring(0, b);
        i.a((Object) substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        sb.append(substring);
        sb.append(" @");
        sb.append(str);
        sb.append('#');
        sb.append(this.e);
        String sb2 = sb.toString();
        i.a((Object) sb2, "StringBuilder(capacity).…builderAction).toString()");
        currentThread.setName(sb2);
        return name;
    }

    public void a(CoroutineContext coroutineContext, String str) {
        i.b(coroutineContext, "context");
        i.b(str, "oldState");
        Thread currentThread = Thread.currentThread();
        i.a((Object) currentThread, "Thread.currentThread()");
        currentThread.setName(str);
    }
}
