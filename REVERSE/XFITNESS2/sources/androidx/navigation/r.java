package androidx.navigation;

import android.os.Bundle;
import androidx.navigation.j;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* compiled from: Navigator */
public abstract class r<D extends j> {

    /* compiled from: Navigator */
    public interface a {
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    /* compiled from: Navigator */
    public @interface b {
        String value();
    }

    public abstract D a();

    public abstract j a(D d, Bundle bundle, o oVar, a aVar);

    public void a(Bundle bundle) {
    }

    public Bundle b() {
        return null;
    }

    public abstract boolean c();
}
