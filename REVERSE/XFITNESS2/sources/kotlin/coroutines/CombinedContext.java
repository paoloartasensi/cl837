package kotlin.coroutines;

import com.jeremyliao.liveeventbus.BuildConfig;
import java.io.Serializable;
import kotlin.TypeCastException;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.i;
import kotlin.l;

/* compiled from: CoroutineContextImpl.kt */
public final class CombinedContext implements CoroutineContext, Serializable {
    private final CoroutineContext.a element;
    private final CoroutineContext left;

    /* compiled from: CoroutineContextImpl.kt */
    private static final class Serialized implements Serializable {
        public static final a Companion = new a((f) null);
        private static final long serialVersionUID = 0;
        private final CoroutineContext[] elements;

        /* compiled from: CoroutineContextImpl.kt */
        public static final class a {
            private a() {
            }

            public /* synthetic */ a(f fVar) {
                this();
            }
        }

        public Serialized(CoroutineContext[] coroutineContextArr) {
            i.b(coroutineContextArr, "elements");
            this.elements = coroutineContextArr;
        }

        private final Object readResolve() {
            CoroutineContext[] coroutineContextArr = this.elements;
            CoroutineContext coroutineContext = EmptyCoroutineContext.INSTANCE;
            for (CoroutineContext plus : coroutineContextArr) {
                coroutineContext = coroutineContext.plus(plus);
            }
            return coroutineContext;
        }

        public final CoroutineContext[] getElements() {
            return this.elements;
        }
    }

    public CombinedContext(CoroutineContext coroutineContext, CoroutineContext.a aVar) {
        i.b(coroutineContext, "left");
        i.b(aVar, "element");
        this.left = coroutineContext;
        this.element = aVar;
    }

    private final boolean a(CoroutineContext.a aVar) {
        return i.a((Object) get(aVar.getKey()), (Object) aVar);
    }

    private final int b() {
        int i2 = 2;
        CombinedContext combinedContext = this;
        while (true) {
            CoroutineContext coroutineContext = combinedContext.left;
            if (!(coroutineContext instanceof CombinedContext)) {
                coroutineContext = null;
            }
            combinedContext = (CombinedContext) coroutineContext;
            if (combinedContext == null) {
                return i2;
            }
            i2++;
        }
    }

    private final Object writeReplace() {
        int b = b();
        CoroutineContext[] coroutineContextArr = new CoroutineContext[b];
        Ref$IntRef ref$IntRef = new Ref$IntRef();
        boolean z = false;
        ref$IntRef.element = 0;
        fold(l.a, new CombinedContext$writeReplace$1(coroutineContextArr, ref$IntRef));
        if (ref$IntRef.element == b) {
            z = true;
        }
        if (z) {
            return new Serialized(coroutineContextArr);
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof CombinedContext) {
                CombinedContext combinedContext = (CombinedContext) obj;
                if (combinedContext.b() != b() || !combinedContext.a(this)) {
                    return false;
                }
            }
            return false;
        }
        return true;
    }

    public <R> R fold(R r, p<? super R, ? super CoroutineContext.a, ? extends R> pVar) {
        i.b(pVar, "operation");
        return pVar.invoke(this.left.fold(r, pVar), this.element);
    }

    public <E extends CoroutineContext.a> E get(CoroutineContext.b<E> bVar) {
        i.b(bVar, "key");
        CombinedContext combinedContext = this;
        while (true) {
            E e = combinedContext.element.get(bVar);
            if (e != null) {
                return e;
            }
            CoroutineContext coroutineContext = combinedContext.left;
            if (!(coroutineContext instanceof CombinedContext)) {
                return coroutineContext.get(bVar);
            }
            combinedContext = (CombinedContext) coroutineContext;
        }
    }

    public int hashCode() {
        return this.left.hashCode() + this.element.hashCode();
    }

    public CoroutineContext minusKey(CoroutineContext.b<?> bVar) {
        i.b(bVar, "key");
        if (this.element.get(bVar) != null) {
            return this.left;
        }
        CoroutineContext minusKey = this.left.minusKey(bVar);
        if (minusKey == this.left) {
            return this;
        }
        if (minusKey == EmptyCoroutineContext.INSTANCE) {
            return this.element;
        }
        return new CombinedContext(minusKey, this.element);
    }

    public CoroutineContext plus(CoroutineContext coroutineContext) {
        i.b(coroutineContext, "context");
        return CoroutineContext.DefaultImpls.a(this, coroutineContext);
    }

    public String toString() {
        return "[" + ((String) fold(BuildConfig.FLAVOR, CombinedContext$toString$1.INSTANCE)) + "]";
    }

    private final boolean a(CombinedContext combinedContext) {
        while (a(combinedContext.element)) {
            CoroutineContext coroutineContext = combinedContext.left;
            if (coroutineContext instanceof CombinedContext) {
                combinedContext = (CombinedContext) coroutineContext;
            } else if (coroutineContext != null) {
                return a((CoroutineContext.a) coroutineContext);
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.CoroutineContext.Element");
            }
        }
        return false;
    }
}
