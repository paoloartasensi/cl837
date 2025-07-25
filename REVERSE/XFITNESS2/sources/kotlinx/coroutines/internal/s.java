package kotlinx.coroutines.internal;

import java.util.ArrayDeque;
import kotlin.Pair;
import kotlin.Result;
import kotlin.TypeCastException;
import kotlin.coroutines.c;
import kotlin.coroutines.jvm.internal.b;
import kotlin.j;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.j0;

/* compiled from: StackTraceRecovery.kt */
public final class s {
    private static final String a;

    static {
        Object obj;
        Object obj2;
        try {
            Result.a aVar = Result.Companion;
            Class<?> cls = Class.forName("kotlin.coroutines.jvm.internal.BaseContinuationImpl");
            i.a((Object) cls, "Class.forName(baseContinuationImplClass)");
            obj = Result.m1constructorimpl(cls.getCanonicalName());
        } catch (Throwable th) {
            Result.a aVar2 = Result.Companion;
            obj = Result.m1constructorimpl(kotlin.i.a(th));
        }
        if (Result.m4exceptionOrNullimpl(obj) != null) {
            obj = "kotlin.coroutines.jvm.internal.BaseContinuationImpl";
        }
        a = (String) obj;
        try {
            Result.a aVar3 = Result.Companion;
            Class<?> cls2 = Class.forName("kotlinx.coroutines.internal.s");
            i.a((Object) cls2, "Class.forName(stackTraceRecoveryClass)");
            obj2 = Result.m1constructorimpl(cls2.getCanonicalName());
        } catch (Throwable th2) {
            Result.a aVar4 = Result.Companion;
            obj2 = Result.m1constructorimpl(kotlin.i.a(th2));
        }
        if (Result.m4exceptionOrNullimpl(obj2) != null) {
            obj2 = "kotlinx.coroutines.internal.StackTraceRecoveryKt";
        }
        String str = (String) obj2;
    }

    public static final <E extends Throwable> E a(E e, c<?> cVar) {
        i.b(e, "exception");
        i.b(cVar, "continuation");
        return (!j0.d() || !(cVar instanceof b)) ? e : a(e, (b) cVar);
    }

    public static final <E extends Throwable> E b(E e) {
        E cause;
        i.b(e, "exception");
        if (j0.d() && (cause = e.getCause()) != null) {
            boolean z = true;
            if (!(!i.a((Object) cause.getClass(), (Object) e.getClass()))) {
                StackTraceElement[] stackTrace = e.getStackTrace();
                i.a((Object) stackTrace, "exception.stackTrace");
                int length = stackTrace.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        z = false;
                        break;
                    }
                    StackTraceElement stackTraceElement = stackTrace[i2];
                    i.a((Object) stackTraceElement, "it");
                    if (a(stackTraceElement)) {
                        break;
                    }
                    i2++;
                }
                if (z) {
                    return cause;
                }
            }
        }
        return e;
    }

    private static final <E extends Throwable> E a(E e, b bVar) {
        Pair a2 = a(e);
        E e2 = (Throwable) a2.component1();
        StackTraceElement[] stackTraceElementArr = (StackTraceElement[]) a2.component2();
        E a3 = ExceptionsConstuctorKt.a(e2);
        if (a3 == null) {
            return e;
        }
        ArrayDeque<StackTraceElement> a4 = a(bVar);
        if (a4.isEmpty()) {
            return e;
        }
        if (e2 != e) {
            a(stackTraceElementArr, a4);
        }
        a(e2, a3, a4);
        return a3;
    }

    private static final <E extends Throwable> E a(E e, E e2, ArrayDeque<StackTraceElement> arrayDeque) {
        arrayDeque.addFirst(a("Coroutine boundary"));
        StackTraceElement[] stackTrace = e.getStackTrace();
        i.a((Object) stackTrace, "causeTrace");
        String str = a;
        i.a((Object) str, "baseContinuationImplClassName");
        int a2 = a(stackTrace, str);
        int i2 = 0;
        if (a2 == -1) {
            Object[] array = arrayDeque.toArray(new StackTraceElement[0]);
            if (array != null) {
                e2.setStackTrace((StackTraceElement[]) array);
                return e2;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        StackTraceElement[] stackTraceElementArr = new StackTraceElement[(arrayDeque.size() + a2)];
        for (int i3 = 0; i3 < a2; i3++) {
            stackTraceElementArr[i3] = stackTrace[i3];
        }
        for (StackTraceElement stackTraceElement : arrayDeque) {
            stackTraceElementArr[a2 + i2] = stackTraceElement;
            i2++;
        }
        e2.setStackTrace(stackTraceElementArr);
        return e2;
    }

    private static final <E extends Throwable> Pair<E, StackTraceElement[]> a(E e) {
        boolean z;
        Throwable cause = e.getCause();
        if (cause == null || !i.a((Object) cause.getClass(), (Object) e.getClass())) {
            return j.a(e, new StackTraceElement[0]);
        }
        StackTraceElement[] stackTrace = e.getStackTrace();
        i.a((Object) stackTrace, "currentTrace");
        int length = stackTrace.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                z = false;
                break;
            }
            StackTraceElement stackTraceElement = stackTrace[i2];
            i.a((Object) stackTraceElement, "it");
            if (a(stackTraceElement)) {
                z = true;
                break;
            }
            i2++;
        }
        if (z) {
            return j.a(cause, stackTrace);
        }
        return j.a(e, new StackTraceElement[0]);
    }

    private static final ArrayDeque<StackTraceElement> a(b bVar) {
        ArrayDeque<StackTraceElement> arrayDeque = new ArrayDeque<>();
        StackTraceElement stackTraceElement = bVar.getStackTraceElement();
        if (stackTraceElement != null) {
            arrayDeque.add(stackTraceElement);
        }
        while (true) {
            if (!(bVar instanceof b)) {
                bVar = null;
            }
            if (bVar == null || (bVar = bVar.getCallerFrame()) == null) {
                return arrayDeque;
            }
            StackTraceElement stackTraceElement2 = bVar.getStackTraceElement();
            if (stackTraceElement2 != null) {
                arrayDeque.add(stackTraceElement2);
            }
        }
        return arrayDeque;
    }

    public static final StackTraceElement a(String str) {
        i.b(str, "message");
        return new StackTraceElement("\b\b\b(" + str, "\b", "\b", -1);
    }

    public static final boolean a(StackTraceElement stackTraceElement) {
        i.b(stackTraceElement, "$this$isArtificial");
        String className = stackTraceElement.getClassName();
        i.a((Object) className, "className");
        return l.a(className, "\b\b\b", false, 2, (Object) null);
    }

    private static final boolean a(StackTraceElement stackTraceElement, StackTraceElement stackTraceElement2) {
        return stackTraceElement.getLineNumber() == stackTraceElement2.getLineNumber() && i.a((Object) stackTraceElement.getMethodName(), (Object) stackTraceElement2.getMethodName()) && i.a((Object) stackTraceElement.getFileName(), (Object) stackTraceElement2.getFileName()) && i.a((Object) stackTraceElement.getClassName(), (Object) stackTraceElement2.getClassName());
    }

    private static final void a(StackTraceElement[] stackTraceElementArr, ArrayDeque<StackTraceElement> arrayDeque) {
        int length = stackTraceElementArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                i2 = -1;
                break;
            } else if (a(stackTraceElementArr[i2])) {
                break;
            } else {
                i2++;
            }
        }
        int i3 = i2 + 1;
        int length2 = stackTraceElementArr.length - 1;
        if (length2 >= i3) {
            while (true) {
                StackTraceElement stackTraceElement = stackTraceElementArr[length2];
                StackTraceElement last = arrayDeque.getLast();
                i.a((Object) last, "result.last");
                if (a(stackTraceElement, last)) {
                    arrayDeque.removeLast();
                }
                arrayDeque.addFirst(stackTraceElementArr[length2]);
                if (length2 != i3) {
                    length2--;
                } else {
                    return;
                }
            }
        }
    }

    private static final int a(StackTraceElement[] stackTraceElementArr, String str) {
        int length = stackTraceElementArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (i.a((Object) str, (Object) stackTraceElementArr[i2].getClassName())) {
                return i2;
            }
        }
        return -1;
    }
}
