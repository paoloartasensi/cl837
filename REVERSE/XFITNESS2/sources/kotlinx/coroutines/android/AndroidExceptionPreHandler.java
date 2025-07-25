package kotlinx.coroutines.android;

import android.os.Build;
import androidx.annotation.Keep;
import java.lang.Thread;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.a;
import kotlin.d;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.i;
import kotlin.jvm.internal.k;
import kotlin.reflect.h;
import kotlinx.coroutines.CoroutineExceptionHandler;

@Keep
/* compiled from: AndroidExceptionPreHandler.kt */
public final class AndroidExceptionPreHandler extends a implements CoroutineExceptionHandler, kotlin.jvm.b.a<Method> {
    static final /* synthetic */ h[] $$delegatedProperties;
    private final d preHandler$delegate = g.a(this);

    static {
        PropertyReference1Impl propertyReference1Impl = new PropertyReference1Impl(k.a(AndroidExceptionPreHandler.class), "preHandler", "getPreHandler()Ljava/lang/reflect/Method;");
        k.a((PropertyReference1) propertyReference1Impl);
        $$delegatedProperties = new h[]{propertyReference1Impl};
    }

    public AndroidExceptionPreHandler() {
        super(CoroutineExceptionHandler.c);
    }

    private final Method getPreHandler() {
        d dVar = this.preHandler$delegate;
        h hVar = $$delegatedProperties[0];
        return (Method) dVar.getValue();
    }

    public void handleException(CoroutineContext coroutineContext, Throwable th) {
        i.b(coroutineContext, "context");
        i.b(th, "exception");
        Thread currentThread = Thread.currentThread();
        if (Build.VERSION.SDK_INT >= 28) {
            i.a((Object) currentThread, "thread");
            currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, th);
            return;
        }
        Method preHandler = getPreHandler();
        Object obj = null;
        Object invoke = preHandler != null ? preHandler.invoke((Object) null, new Object[0]) : null;
        if (invoke instanceof Thread.UncaughtExceptionHandler) {
            obj = invoke;
        }
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (Thread.UncaughtExceptionHandler) obj;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(currentThread, th);
        }
    }

    public Method invoke() {
        boolean z = false;
        try {
            Method declaredMethod = Thread.class.getDeclaredMethod("getUncaughtExceptionPreHandler", new Class[0]);
            i.a((Object) declaredMethod, "it");
            if (Modifier.isPublic(declaredMethod.getModifiers()) && Modifier.isStatic(declaredMethod.getModifiers())) {
                z = true;
            }
            if (z) {
                return declaredMethod;
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }
}
