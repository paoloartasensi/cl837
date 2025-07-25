package kotlinx.coroutines;

import kotlin.Result;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.c;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlinx.coroutines.internal.ThreadContextKt;
import kotlinx.coroutines.internal.s;

/* compiled from: ResumeMode.kt */
public final class y1 {
    public static final <T> void a(c<? super T> cVar, T t, int i2) {
        i.b(cVar, "$this$resumeMode");
        if (i2 == 0) {
            Result.a aVar = Result.Companion;
            cVar.resumeWith(Result.m1constructorimpl(t));
        } else if (i2 == 1) {
            s0.a(cVar, t);
        } else if (i2 == 2) {
            s0.b(cVar, t);
        } else if (i2 == 3) {
            q0 q0Var = (q0) cVar;
            CoroutineContext context = q0Var.getContext();
            Object b = ThreadContextKt.b(context, q0Var.f1815j);
            try {
                c<T> cVar2 = q0Var.l;
                Result.a aVar2 = Result.Companion;
                cVar2.resumeWith(Result.m1constructorimpl(t));
                l lVar = l.a;
            } finally {
                ThreadContextKt.a(context, b);
            }
        } else if (i2 != 4) {
            throw new IllegalStateException(("Invalid mode " + i2).toString());
        }
    }

    public static final boolean a(int i2) {
        return i2 == 1;
    }

    public static final <T> void b(c<? super T> cVar, Throwable th, int i2) {
        i.b(cVar, "$this$resumeWithExceptionMode");
        i.b(th, "exception");
        if (i2 == 0) {
            Result.a aVar = Result.Companion;
            cVar.resumeWith(Result.m1constructorimpl(kotlin.i.a(th)));
        } else if (i2 == 1) {
            s0.a(cVar, th);
        } else if (i2 == 2) {
            s0.b(cVar, th);
        } else if (i2 == 3) {
            q0 q0Var = (q0) cVar;
            CoroutineContext context = q0Var.getContext();
            Object b = ThreadContextKt.b(context, q0Var.f1815j);
            try {
                c<T> cVar2 = q0Var.l;
                Result.a aVar2 = Result.Companion;
                cVar2.resumeWith(Result.m1constructorimpl(kotlin.i.a(s.a(th, (c<?>) cVar2))));
                l lVar = l.a;
            } finally {
                ThreadContextKt.a(context, b);
            }
        } else if (i2 != 4) {
            throw new IllegalStateException(("Invalid mode " + i2).toString());
        }
    }

    public static final boolean b(int i2) {
        return i2 == 0 || i2 == 1;
    }

    public static final <T> void a(c<? super T> cVar, Throwable th, int i2) {
        i.b(cVar, "$this$resumeUninterceptedWithExceptionMode");
        i.b(th, "exception");
        if (i2 == 0) {
            c<? super T> a = IntrinsicsKt__IntrinsicsJvmKt.a(cVar);
            Result.a aVar = Result.Companion;
            a.resumeWith(Result.m1constructorimpl(kotlin.i.a(th)));
        } else if (i2 == 1) {
            s0.a(IntrinsicsKt__IntrinsicsJvmKt.a(cVar), th);
        } else if (i2 == 2) {
            Result.a aVar2 = Result.Companion;
            cVar.resumeWith(Result.m1constructorimpl(kotlin.i.a(th)));
        } else if (i2 == 3) {
            CoroutineContext context = cVar.getContext();
            Object b = ThreadContextKt.b(context, (Object) null);
            try {
                Result.a aVar3 = Result.Companion;
                cVar.resumeWith(Result.m1constructorimpl(kotlin.i.a(th)));
                l lVar = l.a;
            } finally {
                ThreadContextKt.a(context, b);
            }
        } else if (i2 != 4) {
            throw new IllegalStateException(("Invalid mode " + i2).toString());
        }
    }

    public static final <T> void b(c<? super T> cVar, T t, int i2) {
        i.b(cVar, "$this$resumeUninterceptedMode");
        if (i2 == 0) {
            c<? super T> a = IntrinsicsKt__IntrinsicsJvmKt.a(cVar);
            Result.a aVar = Result.Companion;
            a.resumeWith(Result.m1constructorimpl(t));
        } else if (i2 == 1) {
            s0.a(IntrinsicsKt__IntrinsicsJvmKt.a(cVar), t);
        } else if (i2 == 2) {
            Result.a aVar2 = Result.Companion;
            cVar.resumeWith(Result.m1constructorimpl(t));
        } else if (i2 == 3) {
            CoroutineContext context = cVar.getContext();
            Object b = ThreadContextKt.b(context, (Object) null);
            try {
                Result.a aVar3 = Result.Companion;
                cVar.resumeWith(Result.m1constructorimpl(t));
                l lVar = l.a;
            } finally {
                ThreadContextKt.a(context, b);
            }
        } else if (i2 != 4) {
            throw new IllegalStateException(("Invalid mode " + i2).toString());
        }
    }
}
