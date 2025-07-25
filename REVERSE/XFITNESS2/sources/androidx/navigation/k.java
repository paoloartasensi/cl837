package androidx.navigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import androidx.navigation.common.R$styleable;
import androidx.navigation.j;
import g.a.h;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: NavGraph */
public class k extends j implements Iterable<j> {
    final h<j> m = new h<>();
    private int n;
    private String o;

    /* compiled from: NavGraph */
    class a implements Iterator<j> {
        private int e = -1;

        /* renamed from: f  reason: collision with root package name */
        private boolean f740f = false;

        a() {
        }

        public boolean hasNext() {
            return this.e + 1 < k.this.m.d();
        }

        public void remove() {
            if (this.f740f) {
                k.this.m.f(this.e).a((k) null);
                k.this.m.e(this.e);
                this.e--;
                this.f740f = false;
                return;
            }
            throw new IllegalStateException("You must call next() before you can remove an element");
        }

        public j next() {
            if (hasNext()) {
                this.f740f = true;
                h<j> hVar = k.this.m;
                int i2 = this.e + 1;
                this.e = i2;
                return hVar.f(i2);
            }
            throw new NoSuchElementException();
        }
    }

    public k(r<? extends k> rVar) {
        super((r<? extends j>) rVar);
    }

    public void a(Context context, AttributeSet attributeSet) {
        super.a(context, attributeSet);
        TypedArray obtainAttributes = context.getResources().obtainAttributes(attributeSet, R$styleable.NavGraphNavigator);
        d(obtainAttributes.getResourceId(R$styleable.NavGraphNavigator_startDestination, 0));
        this.o = j.a(context, this.n);
        obtainAttributes.recycle();
    }

    public final j c(int i2) {
        return a(i2, true);
    }

    public final void d(int i2) {
        this.n = i2;
        this.o = null;
    }

    /* access modifiers changed from: package-private */
    public String i() {
        if (this.o == null) {
            this.o = Integer.toString(this.n);
        }
        return this.o;
    }

    public final Iterator<j> iterator() {
        return new a();
    }

    public final int j() {
        return this.n;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" startDestination=");
        j c = c(j());
        if (c == null) {
            String str = this.o;
            if (str == null) {
                sb.append("0x");
                sb.append(Integer.toHexString(this.n));
            } else {
                sb.append(str);
            }
        } else {
            sb.append("{");
            sb.append(c.toString());
            sb.append("}");
        }
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public String c() {
        return d() != 0 ? super.c() : "the root navigation";
    }

    /* access modifiers changed from: package-private */
    public j.a a(Uri uri) {
        j.a a2 = super.a(uri);
        Iterator<j> it = iterator();
        while (it.hasNext()) {
            j.a a3 = it.next().a(uri);
            if (a3 != null && (a2 == null || a3.compareTo(a2) > 0)) {
                a2 = a3;
            }
        }
        return a2;
    }

    public final void a(j jVar) {
        if (jVar.d() != 0) {
            j a2 = this.m.a(jVar.d());
            if (a2 != jVar) {
                if (jVar.g() == null) {
                    if (a2 != null) {
                        a2.a((k) null);
                    }
                    jVar.a(this);
                    this.m.c(jVar.d(), jVar);
                    return;
                }
                throw new IllegalStateException("Destination already has a parent set. Call NavGraph.remove() to remove the previous parent.");
            }
            return;
        }
        throw new IllegalArgumentException("Destinations must have an id. Call setId() or include an android:id in your navigation XML.");
    }

    /* access modifiers changed from: package-private */
    public final j a(int i2, boolean z) {
        j a2 = this.m.a(i2);
        if (a2 != null) {
            return a2;
        }
        if (!z || g() == null) {
            return null;
        }
        return g().c(i2);
    }
}
