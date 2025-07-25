package kotlin.jvm.internal;

import java.io.Serializable;

/* compiled from: Lambda.kt */
public abstract class Lambda<R> implements h<R>, Serializable {
    private final int arity;

    public Lambda(int i2) {
        this.arity = i2;
    }

    public int getArity() {
        return this.arity;
    }

    public String toString() {
        String a = k.a(this);
        i.a((Object) a, "Reflection.renderLambdaToString(this)");
        return a;
    }
}
