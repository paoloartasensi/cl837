package i.a.a.a;

import android.util.Log;
import kotlin.jvm.internal.i;
import org.koin.core.logger.Level;

/* compiled from: AndroidLogger.kt */
public final class b extends org.koin.core.logger.b {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public b(Level level) {
        super(level);
        i.b(level, "level");
    }

    private final void d(String str) {
        int i2 = a.a[a().ordinal()];
        if (i2 == 1) {
            Log.d("[Koin]", str);
        } else if (i2 == 2) {
            Log.i("[Koin]", str);
        } else if (i2 == 3) {
            Log.e("[Koin]", str);
        }
    }

    public void a(Level level, String str) {
        i.b(level, "level");
        i.b(str, "msg");
        if (a().compareTo(level) <= 0) {
            d(str);
        }
    }
}
