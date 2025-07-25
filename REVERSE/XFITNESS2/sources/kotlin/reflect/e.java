package kotlin.reflect;

import kotlin.c;

/* compiled from: KFunction.kt */
public interface e<R> extends b<R>, c<R> {
    boolean isExternal();

    boolean isInfix();

    boolean isInline();

    boolean isOperator();

    boolean isSuspend();
}
