package androidx.fragment.app;

import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.a;

/* compiled from: FragmentPagerAdapter */
public abstract class m extends a {
    private final j b;
    private final int c;
    private p d = null;
    private Fragment e = null;

    /* renamed from: f  reason: collision with root package name */
    private boolean f626f;

    public m(j jVar, int i2) {
        this.b = jVar;
        this.c = i2;
    }

    public Object a(ViewGroup viewGroup, int i2) {
        if (this.d == null) {
            this.d = this.b.b();
        }
        long d2 = d(i2);
        Fragment b2 = this.b.b(a(viewGroup.getId(), d2));
        if (b2 != null) {
            this.d.a(b2);
        } else {
            b2 = c(i2);
            this.d.a(viewGroup.getId(), b2, a(viewGroup.getId(), d2));
        }
        if (b2 != this.e) {
            b2.h(false);
            if (this.c == 1) {
                this.d.a(b2, Lifecycle.State.STARTED);
            } else {
                b2.j(false);
            }
        }
        return b2;
    }

    public void a(Parcelable parcelable, ClassLoader classLoader) {
    }

    public Parcelable b() {
        return null;
    }

    public void b(ViewGroup viewGroup) {
        if (viewGroup.getId() == -1) {
            throw new IllegalStateException("ViewPager with adapter " + this + " requires a view id");
        }
    }

    public abstract Fragment c(int i2);

    public long d(int i2) {
        return (long) i2;
    }

    public void b(ViewGroup viewGroup, int i2, Object obj) {
        Fragment fragment = (Fragment) obj;
        Fragment fragment2 = this.e;
        if (fragment != fragment2) {
            if (fragment2 != null) {
                fragment2.h(false);
                if (this.c == 1) {
                    if (this.d == null) {
                        this.d = this.b.b();
                    }
                    this.d.a(this.e, Lifecycle.State.STARTED);
                } else {
                    this.e.j(false);
                }
            }
            fragment.h(true);
            if (this.c == 1) {
                if (this.d == null) {
                    this.d = this.b.b();
                }
                this.d.a(fragment, Lifecycle.State.RESUMED);
            } else {
                fragment.j(true);
            }
            this.e = fragment;
        }
    }

    public void a(ViewGroup viewGroup, int i2, Object obj) {
        Fragment fragment = (Fragment) obj;
        if (this.d == null) {
            this.d = this.b.b();
        }
        this.d.b(fragment);
        if (fragment.equals(this.e)) {
            this.e = null;
        }
    }

    /* JADX INFO: finally extract failed */
    public void a(ViewGroup viewGroup) {
        p pVar = this.d;
        if (pVar != null) {
            if (!this.f626f) {
                try {
                    this.f626f = true;
                    pVar.d();
                    this.f626f = false;
                } catch (Throwable th) {
                    this.f626f = false;
                    throw th;
                }
            }
            this.d = null;
        }
    }

    public boolean a(View view, Object obj) {
        return ((Fragment) obj).F() == view;
    }

    private static String a(int i2, long j2) {
        return "android:switcher:" + i2 + ":" + j2;
    }
}
