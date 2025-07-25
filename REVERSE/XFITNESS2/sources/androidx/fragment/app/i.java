package androidx.fragment.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.j;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: FragmentLifecycleCallbacksDispatcher */
class i {
    private final CopyOnWriteArrayList<a> a = new CopyOnWriteArrayList<>();
    private final j b;

    /* compiled from: FragmentLifecycleCallbacksDispatcher */
    private static final class a {
        final j.f a;
        final boolean b;

        a(j.f fVar, boolean z) {
            this.a = fVar;
            this.b = z;
        }
    }

    i(j jVar) {
        this.b = jVar;
    }

    public void a(j.f fVar, boolean z) {
        this.a.add(new a(fVar, z));
    }

    /* access modifiers changed from: package-private */
    public void b(Fragment fragment, Context context, boolean z) {
        Fragment t = this.b.t();
        if (t != null) {
            t.w().s().b(fragment, context, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.b(this.b, fragment, context);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void c(Fragment fragment, Bundle bundle, boolean z) {
        Fragment t = this.b.t();
        if (t != null) {
            t.w().s().c(fragment, bundle, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.c(this.b, fragment, bundle);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void d(Fragment fragment, boolean z) {
        Fragment t = this.b.t();
        if (t != null) {
            t.w().s().d(fragment, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.d(this.b, fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void e(Fragment fragment, boolean z) {
        Fragment t = this.b.t();
        if (t != null) {
            t.w().s().e(fragment, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.e(this.b, fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void f(Fragment fragment, boolean z) {
        Fragment t = this.b.t();
        if (t != null) {
            t.w().s().f(fragment, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.f(this.b, fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void g(Fragment fragment, boolean z) {
        Fragment t = this.b.t();
        if (t != null) {
            t.w().s().g(fragment, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.g(this.b, fragment);
            }
        }
    }

    public void a(j.f fVar) {
        synchronized (this.a) {
            int i2 = 0;
            int size = this.a.size();
            while (true) {
                if (i2 >= size) {
                    break;
                } else if (this.a.get(i2).a == fVar) {
                    this.a.remove(i2);
                    break;
                } else {
                    i2++;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Fragment fragment, Context context, boolean z) {
        Fragment t = this.b.t();
        if (t != null) {
            t.w().s().a(fragment, context, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.a(this.b, fragment, context);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void b(Fragment fragment, Bundle bundle, boolean z) {
        Fragment t = this.b.t();
        if (t != null) {
            t.w().s().b(fragment, bundle, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.b(this.b, fragment, bundle);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void c(Fragment fragment, boolean z) {
        Fragment t = this.b.t();
        if (t != null) {
            t.w().s().c(fragment, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.c(this.b, fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void d(Fragment fragment, Bundle bundle, boolean z) {
        Fragment t = this.b.t();
        if (t != null) {
            t.w().s().d(fragment, bundle, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.d(this.b, fragment, bundle);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Fragment fragment, Bundle bundle, boolean z) {
        Fragment t = this.b.t();
        if (t != null) {
            t.w().s().a(fragment, bundle, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.a(this.b, fragment, bundle);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void b(Fragment fragment, boolean z) {
        Fragment t = this.b.t();
        if (t != null) {
            t.w().s().b(fragment, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.b(this.b, fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Fragment fragment, View view, Bundle bundle, boolean z) {
        Fragment t = this.b.t();
        if (t != null) {
            t.w().s().a(fragment, view, bundle, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.a(this.b, fragment, view, bundle);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Fragment fragment, boolean z) {
        Fragment t = this.b.t();
        if (t != null) {
            t.w().s().a(fragment, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.a(this.b, fragment);
            }
        }
    }
}
