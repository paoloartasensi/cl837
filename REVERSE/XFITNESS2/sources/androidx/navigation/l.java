package androidx.navigation;

import android.os.Bundle;
import androidx.navigation.r;

@r.b("navigation")
/* compiled from: NavGraphNavigator */
public class l extends r<k> {
    private final s a;

    public l(s sVar) {
        this.a = sVar;
    }

    public boolean c() {
        return true;
    }

    public k a() {
        return new k(this);
    }

    public j a(k kVar, Bundle bundle, o oVar, r.a aVar) {
        int j2 = kVar.j();
        if (j2 != 0) {
            j a2 = kVar.a(j2, false);
            if (a2 != null) {
                return this.a.a(a2.f()).a(a2, a2.a(bundle), oVar, aVar);
            }
            String i2 = kVar.i();
            throw new IllegalArgumentException("navigation destination " + i2 + " is not a direct child of this NavGraph");
        }
        throw new IllegalStateException("no start destination defined via app:startDestination for " + kVar.c());
    }
}
