package androidx.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.m;
import java.util.ArrayDeque;
import java.util.Iterator;

/* compiled from: NavDeepLinkBuilder */
public final class i {
    private final Context a;
    private final Intent b;
    private k c;
    private int d;

    public i(Context context) {
        this.a = context;
        if (context instanceof Activity) {
            Context context2 = this.a;
            this.b = new Intent(context2, context2.getClass());
        } else {
            Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(this.a.getPackageName());
            this.b = launchIntentForPackage == null ? new Intent() : launchIntentForPackage;
        }
        this.b.addFlags(268468224);
    }

    private void b() {
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.add(this.c);
        j jVar = null;
        while (!arrayDeque.isEmpty() && jVar == null) {
            j jVar2 = (j) arrayDeque.poll();
            if (jVar2.d() == this.d) {
                jVar = jVar2;
            } else if (jVar2 instanceof k) {
                Iterator<j> it = ((k) jVar2).iterator();
                while (it.hasNext()) {
                    arrayDeque.add(it.next());
                }
            }
        }
        if (jVar != null) {
            this.b.putExtra("android-support-nav:controller:deepLinkIds", jVar.a());
            return;
        }
        String a2 = j.a(this.a, this.d);
        throw new IllegalArgumentException("navigation destination " + a2 + " is unknown to this NavController");
    }

    public i a(int i2) {
        this.d = i2;
        if (this.c != null) {
            b();
        }
        return this;
    }

    public m a() {
        if (this.b.getIntArrayExtra("android-support-nav:controller:deepLinkIds") != null) {
            m a2 = m.a(this.a);
            a2.b(new Intent(this.b));
            for (int i2 = 0; i2 < a2.a(); i2++) {
                a2.a(i2).putExtra("android-support-nav:controller:deepLinkIntent", this.b);
            }
            return a2;
        } else if (this.c == null) {
            throw new IllegalStateException("You must call setGraph() before constructing the deep link");
        } else {
            throw new IllegalStateException("You must call setDestination() before constructing the deep link");
        }
    }

    i(NavController navController) {
        this(navController.a());
        this.c = navController.c();
    }
}
