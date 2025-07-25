package kotlin.coroutines.jvm.internal;

import kotlin.coroutines.c;
import kotlin.jvm.internal.h;
import kotlin.jvm.internal.i;
import kotlin.jvm.internal.k;

/* compiled from: ContinuationImpl.kt */
public abstract class SuspendLambda extends ContinuationImpl implements h<Object> {
    private final int arity;

    public SuspendLambda(int i2, c<Object> cVar) {
        super(cVar);
        this.arity = i2;
    }

    public int getArity() {
        return this.arity;
    }

    public String toString() {
        if (getCompletion() != null) {
            return super.toString();
        }
        String a = k.a((h) this);
        i.a((Object) a, "Reflection.renderLambdaToString(this)");
        return a;
    }

    public SuspendLambda(int i2) {
        this(i2, (c<Object>) null);
    }
}
