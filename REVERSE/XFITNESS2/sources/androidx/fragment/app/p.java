package androidx.fragment.app;

import android.view.View;
import android.view.ViewGroup;
import androidx.core.h.v;
import androidx.lifecycle.Lifecycle;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/* compiled from: FragmentTransaction */
public abstract class p {
    ArrayList<a> a = new ArrayList<>();
    int b;
    int c;
    int d;
    int e;

    /* renamed from: f  reason: collision with root package name */
    int f627f;

    /* renamed from: g  reason: collision with root package name */
    boolean f628g;

    /* renamed from: h  reason: collision with root package name */
    boolean f629h = true;

    /* renamed from: i  reason: collision with root package name */
    String f630i;

    /* renamed from: j  reason: collision with root package name */
    int f631j;
    CharSequence k;
    int l;
    CharSequence m;
    ArrayList<String> n;
    ArrayList<String> o;
    boolean p = false;
    ArrayList<Runnable> q;

    /* compiled from: FragmentTransaction */
    static final class a {
        int a;
        Fragment b;
        int c;
        int d;
        int e;

        /* renamed from: f  reason: collision with root package name */
        int f632f;

        /* renamed from: g  reason: collision with root package name */
        Lifecycle.State f633g;

        /* renamed from: h  reason: collision with root package name */
        Lifecycle.State f634h;

        a() {
        }

        a(int i2, Fragment fragment) {
            this.a = i2;
            this.b = fragment;
            Lifecycle.State state = Lifecycle.State.RESUMED;
            this.f633g = state;
            this.f634h = state;
        }

        a(int i2, Fragment fragment, Lifecycle.State state) {
            this.a = i2;
            this.b = fragment;
            this.f633g = fragment.T;
            this.f634h = state;
        }
    }

    p(f fVar, ClassLoader classLoader) {
    }

    public abstract int a();

    /* access modifiers changed from: package-private */
    public void a(a aVar) {
        this.a.add(aVar);
        aVar.c = this.b;
        aVar.d = this.c;
        aVar.e = this.d;
        aVar.f632f = this.e;
    }

    public abstract int b();

    public p b(int i2, Fragment fragment, String str) {
        if (i2 != 0) {
            a(i2, fragment, str, 2);
            return this;
        }
        throw new IllegalArgumentException("Must use non-zero containerViewId");
    }

    public p c(Fragment fragment) {
        a(new a(3, fragment));
        return this;
    }

    public abstract void c();

    public p d(Fragment fragment) {
        a(new a(8, fragment));
        return this;
    }

    public abstract void d();

    public p e() {
        if (!this.f628g) {
            this.f629h = false;
            return this;
        }
        throw new IllegalStateException("This transaction is already being added to the back stack");
    }

    public abstract boolean f();

    public p b(Fragment fragment) {
        a(new a(6, fragment));
        return this;
    }

    public p a(Fragment fragment, String str) {
        a(0, fragment, str, 1);
        return this;
    }

    public p a(int i2, Fragment fragment, String str) {
        a(i2, fragment, str, 1);
        return this;
    }

    /* access modifiers changed from: package-private */
    public p a(ViewGroup viewGroup, Fragment fragment, String str) {
        fragment.J = viewGroup;
        a(viewGroup.getId(), fragment, str);
        return this;
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, Fragment fragment, String str, int i3) {
        Class<?> cls = fragment.getClass();
        int modifiers = cls.getModifiers();
        if (cls.isAnonymousClass() || !Modifier.isPublic(modifiers) || (cls.isMemberClass() && !Modifier.isStatic(modifiers))) {
            throw new IllegalStateException("Fragment " + cls.getCanonicalName() + " must be a public static class to be  properly recreated from instance state.");
        }
        if (str != null) {
            String str2 = fragment.B;
            if (str2 == null || str.equals(str2)) {
                fragment.B = str;
            } else {
                throw new IllegalStateException("Can't change tag of fragment " + fragment + ": was " + fragment.B + " now " + str);
            }
        }
        if (i2 != 0) {
            if (i2 != -1) {
                int i4 = fragment.z;
                if (i4 == 0 || i4 == i2) {
                    fragment.z = i2;
                    fragment.A = i2;
                } else {
                    throw new IllegalStateException("Can't change container ID of fragment " + fragment + ": was " + fragment.z + " now " + i2);
                }
            } else {
                throw new IllegalArgumentException("Can't add fragment " + fragment + " with tag " + str + " to container view with no id");
            }
        }
        a(new a(i3, fragment));
    }

    public p a(int i2, Fragment fragment) {
        b(i2, fragment, (String) null);
        return this;
    }

    public p a(Fragment fragment) {
        a(new a(7, fragment));
        return this;
    }

    public p a(Fragment fragment, Lifecycle.State state) {
        a(new a(10, fragment, state));
        return this;
    }

    public p a(int i2, int i3, int i4, int i5) {
        this.b = i2;
        this.c = i3;
        this.d = i4;
        this.e = i5;
        return this;
    }

    public p a(View view, String str) {
        if (q.b()) {
            String v = v.v(view);
            if (v != null) {
                if (this.n == null) {
                    this.n = new ArrayList<>();
                    this.o = new ArrayList<>();
                } else if (this.o.contains(str)) {
                    throw new IllegalArgumentException("A shared element with the target name '" + str + "' has already been added to the transaction.");
                } else if (this.n.contains(v)) {
                    throw new IllegalArgumentException("A shared element with the source name '" + v + "' has already been added to the transaction.");
                }
                this.n.add(v);
                this.o.add(str);
            } else {
                throw new IllegalArgumentException("Unique transitionNames are required for all sharedElements");
            }
        }
        return this;
    }

    public p a(String str) {
        if (this.f629h) {
            this.f628g = true;
            this.f630i = str;
            return this;
        }
        throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
    }

    public p a(boolean z) {
        this.p = z;
        return this;
    }
}
