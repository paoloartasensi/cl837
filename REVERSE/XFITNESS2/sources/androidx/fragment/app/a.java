package androidx.fragment.app;

import android.util.Log;
import androidx.core.g.b;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.j;
import androidx.fragment.app.p;
import androidx.lifecycle.Lifecycle;
import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: BackStackRecord */
final class a extends p implements j.h {
    final j r;
    boolean s;
    int t;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    a(androidx.fragment.app.j r3) {
        /*
            r2 = this;
            androidx.fragment.app.f r0 = r3.p()
            androidx.fragment.app.g<?> r1 = r3.o
            if (r1 == 0) goto L_0x0011
            android.content.Context r1 = r1.e()
            java.lang.ClassLoader r1 = r1.getClassLoader()
            goto L_0x0012
        L_0x0011:
            r1 = 0
        L_0x0012:
            r2.<init>(r0, r1)
            r0 = -1
            r2.t = r0
            r2.r = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.a.<init>(androidx.fragment.app.j):void");
    }

    public void a(String str, PrintWriter printWriter) {
        a(str, printWriter, true);
    }

    public p b(Fragment fragment) {
        j jVar = fragment.v;
        if (jVar == null || jVar == this.r) {
            super.b(fragment);
            return this;
        }
        throw new IllegalStateException("Cannot detach Fragment attached to a different FragmentManager. Fragment " + fragment.toString() + " is already attached to a FragmentManager.");
    }

    public p c(Fragment fragment) {
        j jVar = fragment.v;
        if (jVar == null || jVar == this.r) {
            super.c(fragment);
            return this;
        }
        throw new IllegalStateException("Cannot remove Fragment attached to a different FragmentManager. Fragment " + fragment.toString() + " is already attached to a FragmentManager.");
    }

    public p d(Fragment fragment) {
        j jVar;
        if (fragment == null || (jVar = fragment.v) == null || jVar == this.r) {
            super.d(fragment);
            return this;
        }
        throw new IllegalStateException("Cannot setPrimaryNavigation for Fragment attached to a different FragmentManager. Fragment " + fragment.toString() + " is already attached to a FragmentManager.");
    }

    public boolean f() {
        return this.a.isEmpty();
    }

    /* access modifiers changed from: package-private */
    public void g() {
        int size = this.a.size();
        for (int i2 = 0; i2 < size; i2++) {
            p.a aVar = this.a.get(i2);
            Fragment fragment = aVar.b;
            if (fragment != null) {
                fragment.c(this.f627f);
            }
            switch (aVar.a) {
                case 1:
                    fragment.b(aVar.c);
                    this.r.a(fragment, false);
                    this.r.a(fragment);
                    break;
                case 3:
                    fragment.b(aVar.d);
                    this.r.l(fragment);
                    break;
                case 4:
                    fragment.b(aVar.d);
                    this.r.f(fragment);
                    break;
                case 5:
                    fragment.b(aVar.c);
                    this.r.a(fragment, false);
                    this.r.p(fragment);
                    break;
                case 6:
                    fragment.b(aVar.d);
                    this.r.d(fragment);
                    break;
                case 7:
                    fragment.b(aVar.c);
                    this.r.a(fragment, false);
                    this.r.c(fragment);
                    break;
                case 8:
                    this.r.o(fragment);
                    break;
                case 9:
                    this.r.o((Fragment) null);
                    break;
                case 10:
                    this.r.a(fragment, aVar.f634h);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown cmd: " + aVar.a);
            }
            if (!(this.p || aVar.a == 1 || fragment == null)) {
                this.r.i(fragment);
            }
        }
        if (!this.p) {
            j jVar = this.r;
            jVar.a(jVar.n, true);
        }
    }

    public String h() {
        return this.f630i;
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        for (int i2 = 0; i2 < this.a.size(); i2++) {
            if (b(this.a.get(i2))) {
                return true;
            }
        }
        return false;
    }

    public void j() {
        if (this.q != null) {
            for (int i2 = 0; i2 < this.q.size(); i2++) {
                this.q.get(i2).run();
            }
            this.q = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void setOnStartPostponedListener(Fragment.e eVar) {
        for (int i2 = 0; i2 < this.a.size(); i2++) {
            p.a aVar = this.a.get(i2);
            if (b(aVar)) {
                aVar.b.setOnStartEnterTransitionListener(eVar);
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("BackStackEntry{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.t >= 0) {
            sb.append(" #");
            sb.append(this.t);
        }
        if (this.f630i != null) {
            sb.append(" ");
            sb.append(this.f630i);
        }
        sb.append("}");
        return sb.toString();
    }

    public void a(String str, PrintWriter printWriter, boolean z) {
        String str2;
        if (z) {
            printWriter.print(str);
            printWriter.print("mName=");
            printWriter.print(this.f630i);
            printWriter.print(" mIndex=");
            printWriter.print(this.t);
            printWriter.print(" mCommitted=");
            printWriter.println(this.s);
            if (this.f627f != 0) {
                printWriter.print(str);
                printWriter.print("mTransition=#");
                printWriter.print(Integer.toHexString(this.f627f));
            }
            if (!(this.b == 0 && this.c == 0)) {
                printWriter.print(str);
                printWriter.print("mEnterAnim=#");
                printWriter.print(Integer.toHexString(this.b));
                printWriter.print(" mExitAnim=#");
                printWriter.println(Integer.toHexString(this.c));
            }
            if (!(this.d == 0 && this.e == 0)) {
                printWriter.print(str);
                printWriter.print("mPopEnterAnim=#");
                printWriter.print(Integer.toHexString(this.d));
                printWriter.print(" mPopExitAnim=#");
                printWriter.println(Integer.toHexString(this.e));
            }
            if (!(this.f631j == 0 && this.k == null)) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbTitleRes=#");
                printWriter.print(Integer.toHexString(this.f631j));
                printWriter.print(" mBreadCrumbTitleText=");
                printWriter.println(this.k);
            }
            if (!(this.l == 0 && this.m == null)) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbShortTitleRes=#");
                printWriter.print(Integer.toHexString(this.l));
                printWriter.print(" mBreadCrumbShortTitleText=");
                printWriter.println(this.m);
            }
        }
        if (!this.a.isEmpty()) {
            printWriter.print(str);
            printWriter.println("Operations:");
            int size = this.a.size();
            for (int i2 = 0; i2 < size; i2++) {
                p.a aVar = this.a.get(i2);
                switch (aVar.a) {
                    case 0:
                        str2 = "NULL";
                        break;
                    case 1:
                        str2 = "ADD";
                        break;
                    case 2:
                        str2 = "REPLACE";
                        break;
                    case 3:
                        str2 = "REMOVE";
                        break;
                    case 4:
                        str2 = "HIDE";
                        break;
                    case 5:
                        str2 = "SHOW";
                        break;
                    case 6:
                        str2 = "DETACH";
                        break;
                    case 7:
                        str2 = "ATTACH";
                        break;
                    case 8:
                        str2 = "SET_PRIMARY_NAV";
                        break;
                    case 9:
                        str2 = "UNSET_PRIMARY_NAV";
                        break;
                    case 10:
                        str2 = "OP_SET_MAX_LIFECYCLE";
                        break;
                    default:
                        str2 = "cmd=" + aVar.a;
                        break;
                }
                printWriter.print(str);
                printWriter.print("  Op #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.print(str2);
                printWriter.print(" ");
                printWriter.println(aVar.b);
                if (z) {
                    if (!(aVar.c == 0 && aVar.d == 0)) {
                        printWriter.print(str);
                        printWriter.print("enterAnim=#");
                        printWriter.print(Integer.toHexString(aVar.c));
                        printWriter.print(" exitAnim=#");
                        printWriter.println(Integer.toHexString(aVar.d));
                    }
                    if (aVar.e != 0 || aVar.f632f != 0) {
                        printWriter.print(str);
                        printWriter.print("popEnterAnim=#");
                        printWriter.print(Integer.toHexString(aVar.e));
                        printWriter.print(" popExitAnim=#");
                        printWriter.println(Integer.toHexString(aVar.f632f));
                    }
                }
            }
        }
    }

    public int b() {
        return b(true);
    }

    public void c() {
        e();
        this.r.b((j.h) this, false);
    }

    public void d() {
        e();
        this.r.b((j.h) this, true);
    }

    /* access modifiers changed from: package-private */
    public int b(boolean z) {
        if (!this.s) {
            if (j.d(2)) {
                Log.v("FragmentManager", "Commit: " + this);
                PrintWriter printWriter = new PrintWriter(new b("FragmentManager"));
                a("  ", printWriter);
                printWriter.close();
            }
            this.s = true;
            if (this.f628g) {
                this.t = this.r.a();
            } else {
                this.t = -1;
            }
            this.r.a((j.h) this, z);
            return this.t;
        }
        throw new IllegalStateException("commit already called");
    }

    /* access modifiers changed from: package-private */
    public void c(boolean z) {
        for (int size = this.a.size() - 1; size >= 0; size--) {
            p.a aVar = this.a.get(size);
            Fragment fragment = aVar.b;
            if (fragment != null) {
                fragment.c(j.e(this.f627f));
            }
            switch (aVar.a) {
                case 1:
                    fragment.b(aVar.f632f);
                    this.r.a(fragment, true);
                    this.r.l(fragment);
                    break;
                case 3:
                    fragment.b(aVar.e);
                    this.r.a(fragment);
                    break;
                case 4:
                    fragment.b(aVar.e);
                    this.r.p(fragment);
                    break;
                case 5:
                    fragment.b(aVar.f632f);
                    this.r.a(fragment, true);
                    this.r.f(fragment);
                    break;
                case 6:
                    fragment.b(aVar.e);
                    this.r.c(fragment);
                    break;
                case 7:
                    fragment.b(aVar.f632f);
                    this.r.a(fragment, true);
                    this.r.d(fragment);
                    break;
                case 8:
                    this.r.o((Fragment) null);
                    break;
                case 9:
                    this.r.o(fragment);
                    break;
                case 10:
                    this.r.a(fragment, aVar.f633g);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown cmd: " + aVar.a);
            }
            if (!(this.p || aVar.a == 3 || fragment == null)) {
                this.r.i(fragment);
            }
        }
        if (!this.p && z) {
            j jVar = this.r;
            jVar.a(jVar.n, true);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean b(int i2) {
        int size = this.a.size();
        for (int i3 = 0; i3 < size; i3++) {
            Fragment fragment = this.a.get(i3).b;
            int i4 = fragment != null ? fragment.A : 0;
            if (i4 != 0 && i4 == i2) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public Fragment b(ArrayList<Fragment> arrayList, Fragment fragment) {
        for (int size = this.a.size() - 1; size >= 0; size--) {
            p.a aVar = this.a.get(size);
            int i2 = aVar.a;
            if (i2 != 1) {
                if (i2 != 3) {
                    switch (i2) {
                        case 6:
                            break;
                        case 7:
                            break;
                        case 8:
                            fragment = null;
                            break;
                        case 9:
                            fragment = aVar.b;
                            break;
                        case 10:
                            aVar.f634h = aVar.f633g;
                            break;
                    }
                }
                arrayList.add(aVar.b);
            }
            arrayList.remove(aVar.b);
        }
        return fragment;
    }

    private static boolean b(p.a aVar) {
        Fragment fragment = aVar.b;
        return fragment != null && fragment.o && fragment.K != null && !fragment.D && !fragment.C && fragment.M();
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, Fragment fragment, String str, int i3) {
        super.a(i2, fragment, str, i3);
        fragment.v = this.r;
    }

    public p a(Fragment fragment, Lifecycle.State state) {
        if (fragment.v != this.r) {
            throw new IllegalArgumentException("Cannot setMaxLifecycle for Fragment not attached to FragmentManager " + this.r);
        } else if (state.isAtLeast(Lifecycle.State.CREATED)) {
            super.a(fragment, state);
            return this;
        } else {
            throw new IllegalArgumentException("Cannot set maximum Lifecycle below " + Lifecycle.State.CREATED);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        if (this.f628g) {
            if (j.d(2)) {
                Log.v("FragmentManager", "Bump nesting in " + this + " by " + i2);
            }
            int size = this.a.size();
            for (int i3 = 0; i3 < size; i3++) {
                p.a aVar = this.a.get(i3);
                Fragment fragment = aVar.b;
                if (fragment != null) {
                    fragment.u += i2;
                    if (j.d(2)) {
                        Log.v("FragmentManager", "Bump nesting of " + aVar.b + " to " + aVar.b.u);
                    }
                }
            }
        }
    }

    public int a() {
        return b(false);
    }

    public boolean a(ArrayList<a> arrayList, ArrayList<Boolean> arrayList2) {
        if (j.d(2)) {
            Log.v("FragmentManager", "Run: " + this);
        }
        arrayList.add(this);
        arrayList2.add(false);
        if (!this.f628g) {
            return true;
        }
        this.r.a(this);
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean a(ArrayList<a> arrayList, int i2, int i3) {
        if (i3 == i2) {
            return false;
        }
        int size = this.a.size();
        int i4 = -1;
        for (int i5 = 0; i5 < size; i5++) {
            Fragment fragment = this.a.get(i5).b;
            int i6 = fragment != null ? fragment.A : 0;
            if (!(i6 == 0 || i6 == i4)) {
                for (int i7 = i2; i7 < i3; i7++) {
                    a aVar = arrayList.get(i7);
                    int size2 = aVar.a.size();
                    for (int i8 = 0; i8 < size2; i8++) {
                        Fragment fragment2 = aVar.a.get(i8).b;
                        if ((fragment2 != null ? fragment2.A : 0) == i6) {
                            return true;
                        }
                    }
                }
                i4 = i6;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public Fragment a(ArrayList<Fragment> arrayList, Fragment fragment) {
        ArrayList<Fragment> arrayList2 = arrayList;
        Fragment fragment2 = fragment;
        int i2 = 0;
        while (i2 < this.a.size()) {
            p.a aVar = this.a.get(i2);
            int i3 = aVar.a;
            if (i3 != 1) {
                if (i3 == 2) {
                    Fragment fragment3 = aVar.b;
                    int i4 = fragment3.A;
                    boolean z = false;
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        Fragment fragment4 = arrayList2.get(size);
                        if (fragment4.A == i4) {
                            if (fragment4 == fragment3) {
                                z = true;
                            } else {
                                if (fragment4 == fragment2) {
                                    this.a.add(i2, new p.a(9, fragment4));
                                    i2++;
                                    fragment2 = null;
                                }
                                p.a aVar2 = new p.a(3, fragment4);
                                aVar2.c = aVar.c;
                                aVar2.e = aVar.e;
                                aVar2.d = aVar.d;
                                aVar2.f632f = aVar.f632f;
                                this.a.add(i2, aVar2);
                                arrayList2.remove(fragment4);
                                i2++;
                            }
                        }
                    }
                    if (z) {
                        this.a.remove(i2);
                        i2--;
                    } else {
                        aVar.a = 1;
                        arrayList2.add(fragment3);
                    }
                } else if (i3 == 3 || i3 == 6) {
                    arrayList2.remove(aVar.b);
                    Fragment fragment5 = aVar.b;
                    if (fragment5 == fragment2) {
                        this.a.add(i2, new p.a(9, fragment5));
                        i2++;
                        fragment2 = null;
                    }
                } else if (i3 != 7) {
                    if (i3 == 8) {
                        this.a.add(i2, new p.a(9, fragment2));
                        i2++;
                        fragment2 = aVar.b;
                    }
                }
                i2++;
            }
            arrayList2.add(aVar.b);
            i2++;
        }
        return fragment2;
    }
}
