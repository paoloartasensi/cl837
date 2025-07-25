package kotlin.coroutines.jvm.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.c;
import kotlin.coroutines.d;
import kotlin.jvm.internal.i;

/* compiled from: ContinuationImpl.kt */
public abstract class ContinuationImpl extends BaseContinuationImpl {
    private final CoroutineContext _context;
    private transient c<Object> intercepted;

    public ContinuationImpl(c<Object> cVar, CoroutineContext coroutineContext) {
        super(cVar);
        this._context = coroutineContext;
    }

    public CoroutineContext getContext() {
        CoroutineContext coroutineContext = this._context;
        if (coroutineContext != null) {
            return coroutineContext;
        }
        i.a();
        throw null;
    }

    public final c<Object> intercepted() {
        c<Object> cVar = this.intercepted;
        if (cVar == null) {
            d dVar = (d) getContext().get(d.b);
            if (dVar == null || (cVar = dVar.interceptContinuation(this)) == null) {
                cVar = this;
            }
            this.intercepted = cVar;
        }
        return cVar;
    }

    /* access modifiers changed from: protected */
    public void releaseIntercepted() {
        c<Object> cVar = this.intercepted;
        if (!(cVar == null || cVar == this)) {
            CoroutineContext.a aVar = getContext().get(d.b);
            if (aVar != null) {
                ((d) aVar).releaseInterceptedContinuation(cVar);
            } else {
                i.a();
                throw null;
            }
        }
        this.intercepted = a.e;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ContinuationImpl(c<Object> cVar) {
        this(cVar, cVar != null ? cVar.getContext() : null);
    }
}
