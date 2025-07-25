package kotlinx.coroutines.android;

import android.os.Looper;
import java.util.List;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.internal.MainDispatcherFactory;

/* compiled from: HandlerDispatcher.kt */
public final class AndroidDispatcherFactory implements MainDispatcherFactory {
    public int a() {
        return 1073741823;
    }

    public String b() {
        return "For tests Dispatchers.setMain from kotlinx-coroutines-test module can be used";
    }

    public HandlerContext a(List<? extends MainDispatcherFactory> list) {
        i.b(list, "allFactories");
        Looper mainLooper = Looper.getMainLooper();
        i.a((Object) mainLooper, "Looper.getMainLooper()");
        return new HandlerContext(b.a(mainLooper, true), "Main");
    }
}
