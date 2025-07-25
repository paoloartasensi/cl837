package kotlin.coroutines.jvm.internal;

import java.io.Serializable;
import kotlin.Result;
import kotlin.coroutines.c;
import kotlin.jvm.internal.i;
import kotlin.l;

/* compiled from: ContinuationImpl.kt */
public abstract class BaseContinuationImpl implements c<Object>, b, Serializable {
    private final c<Object> completion;

    public BaseContinuationImpl(c<Object> cVar) {
        this.completion = cVar;
    }

    public c<l> create(c<?> cVar) {
        i.b(cVar, "completion");
        throw new UnsupportedOperationException("create(Continuation) has not been overridden");
    }

    public b getCallerFrame() {
        c<Object> cVar = this.completion;
        if (!(cVar instanceof b)) {
            cVar = null;
        }
        return (b) cVar;
    }

    public final c<Object> getCompletion() {
        return this.completion;
    }

    public StackTraceElement getStackTraceElement() {
        return d.c(this);
    }

    /* access modifiers changed from: protected */
    public abstract Object invokeSuspend(Object obj);

    /* access modifiers changed from: protected */
    public void releaseIntercepted() {
    }

    public final void resumeWith(Object obj) {
        BaseContinuationImpl baseContinuationImpl = this;
        while (true) {
            e.b(baseContinuationImpl);
            c cVar = baseContinuationImpl.completion;
            if (cVar != null) {
                try {
                    Object invokeSuspend = baseContinuationImpl.invokeSuspend(obj);
                    if (invokeSuspend != b.a()) {
                        Result.a aVar = Result.Companion;
                        obj = Result.m1constructorimpl(invokeSuspend);
                        baseContinuationImpl.releaseIntercepted();
                        if (cVar instanceof BaseContinuationImpl) {
                            baseContinuationImpl = (BaseContinuationImpl) cVar;
                        } else {
                            cVar.resumeWith(obj);
                            return;
                        }
                    } else {
                        return;
                    }
                } catch (Throwable th) {
                    Result.a aVar2 = Result.Companion;
                    obj = Result.m1constructorimpl(kotlin.i.a(th));
                }
            } else {
                i.a();
                throw null;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Continuation at ");
        Object stackTraceElement = getStackTraceElement();
        if (stackTraceElement == null) {
            stackTraceElement = getClass().getName();
        }
        sb.append(stackTraceElement);
        return sb.toString();
    }

    public c<l> create(Object obj, c<?> cVar) {
        i.b(cVar, "completion");
        throw new UnsupportedOperationException("create(Any?;Continuation) has not been overridden");
    }
}
