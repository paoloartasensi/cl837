package kotlinx.coroutines;

import kotlin.Result;
import kotlin.coroutines.c;
import kotlin.jvm.internal.i;

/* compiled from: DebugStrings.kt */
public final class k0 {
    public static final String a(c<?> cVar) {
        Object obj;
        i.b(cVar, "$this$toDebugString");
        if (cVar instanceof q0) {
            return cVar.toString();
        }
        try {
            Result.a aVar = Result.Companion;
            obj = Result.m1constructorimpl(cVar + '@' + b(cVar));
        } catch (Throwable th) {
            Result.a aVar2 = Result.Companion;
            obj = Result.m1constructorimpl(kotlin.i.a(th));
        }
        Throwable r2 = Result.m4exceptionOrNullimpl(obj);
        String str = obj;
        if (r2 != null) {
            str = cVar.getClass().getName() + '@' + b(cVar);
        }
        return (String) str;
    }

    public static final String b(Object obj) {
        i.b(obj, "$this$hexAddress");
        String hexString = Integer.toHexString(System.identityHashCode(obj));
        i.a((Object) hexString, "Integer.toHexString(System.identityHashCode(this))");
        return hexString;
    }

    public static final String a(Object obj) {
        i.b(obj, "$this$classSimpleName");
        String simpleName = obj.getClass().getSimpleName();
        i.a((Object) simpleName, "this::class.java.simpleName");
        return simpleName;
    }
}
