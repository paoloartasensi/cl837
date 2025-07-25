package kotlinx.coroutines.android;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import java.lang.reflect.Constructor;
import kotlin.Result;
import kotlin.TypeCastException;
import kotlin.jvm.internal.i;

/* compiled from: HandlerDispatcher.kt */
public final class b {
    static {
        Object obj;
        try {
            Result.a aVar = Result.Companion;
            Looper mainLooper = Looper.getMainLooper();
            i.a((Object) mainLooper, "Looper.getMainLooper()");
            obj = Result.m1constructorimpl(new HandlerContext(a(mainLooper, true), "Main"));
        } catch (Throwable th) {
            Result.a aVar2 = Result.Companion;
            obj = Result.m1constructorimpl(kotlin.i.a(th));
        }
        if (Result.m6isFailureimpl(obj)) {
            obj = null;
        }
        a aVar3 = (a) obj;
    }

    public static final Handler a(Looper looper, boolean z) {
        int i2;
        i.b(looper, "$this$asHandler");
        if (!z || (i2 = Build.VERSION.SDK_INT) < 16) {
            return new Handler(looper);
        }
        if (i2 >= 28) {
            Object invoke = Handler.class.getDeclaredMethod("createAsync", new Class[]{Looper.class}).invoke((Object) null, new Object[]{looper});
            if (invoke != null) {
                return (Handler) invoke;
            }
            throw new TypeCastException("null cannot be cast to non-null type android.os.Handler");
        }
        Class<Handler> cls = Handler.class;
        try {
            Constructor<Handler> declaredConstructor = cls.getDeclaredConstructor(new Class[]{Looper.class, Handler.Callback.class, Boolean.TYPE});
            i.a((Object) declaredConstructor, "Handler::class.java.getD…:class.javaPrimitiveType)");
            Handler newInstance = declaredConstructor.newInstance(new Object[]{looper, null, true});
            i.a((Object) newInstance, "constructor.newInstance(this, null, true)");
            return newInstance;
        } catch (NoSuchMethodException unused) {
            return new Handler(looper);
        }
    }
}
