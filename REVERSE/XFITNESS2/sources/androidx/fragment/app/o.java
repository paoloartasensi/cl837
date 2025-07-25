package androidx.fragment.app;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: FragmentStore */
class o {
    private final ArrayList<Fragment> a = new ArrayList<>();
    private final HashMap<String, n> b = new HashMap<>();

    o() {
    }

    /* access modifiers changed from: package-private */
    public void a(List<String> list) {
        this.a.clear();
        if (list != null) {
            for (String next : list) {
                Fragment b2 = b(next);
                if (b2 != null) {
                    if (j.d(2)) {
                        Log.v("FragmentManager", "restoreSaveState: added (" + next + "): " + b2);
                    }
                    a(b2);
                } else {
                    throw new IllegalStateException("No instantiated fragment for (" + next + ")");
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void b(n nVar) {
        Fragment e = nVar.e();
        for (n next : this.b.values()) {
            if (next != null) {
                Fragment e2 = next.e();
                if (e.f588i.equals(e2.l)) {
                    e2.k = e;
                    e2.l = null;
                }
            }
        }
        this.b.put(e.f588i, (Object) null);
        String str = e.l;
        if (str != null) {
            e.k = b(str);
        }
    }

    /* access modifiers changed from: package-private */
    public void c(Fragment fragment) {
        synchronized (this.a) {
            this.a.remove(fragment);
        }
        fragment.o = false;
    }

    /* access modifiers changed from: package-private */
    public void d() {
        this.b.clear();
    }

    /* access modifiers changed from: package-private */
    public ArrayList<FragmentState> e() {
        ArrayList<FragmentState> arrayList = new ArrayList<>(this.b.size());
        for (n next : this.b.values()) {
            if (next != null) {
                Fragment e = next.e();
                FragmentState j2 = next.j();
                arrayList.add(j2);
                if (j.d(2)) {
                    Log.v("FragmentManager", "Saved state of " + e + ": " + j2.q);
                }
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    public ArrayList<String> f() {
        synchronized (this.a) {
            if (this.a.isEmpty()) {
                return null;
            }
            ArrayList<String> arrayList = new ArrayList<>(this.a.size());
            Iterator<Fragment> it = this.a.iterator();
            while (it.hasNext()) {
                Fragment next = it.next();
                arrayList.add(next.f588i);
                if (j.d(2)) {
                    Log.v("FragmentManager", "saveAllState: adding fragment (" + next.f588i + "): " + next);
                }
            }
            return arrayList;
        }
    }

    /* access modifiers changed from: package-private */
    public Fragment d(String str) {
        Fragment a2;
        for (n next : this.b.values()) {
            if (next != null && (a2 = next.e().a(str)) != null) {
                return a2;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public List<Fragment> c() {
        ArrayList arrayList;
        if (this.a.isEmpty()) {
            return Collections.emptyList();
        }
        synchronized (this.a) {
            arrayList = new ArrayList(this.a);
        }
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    public void a(n nVar) {
        this.b.put(nVar.e().f588i, nVar);
    }

    /* access modifiers changed from: package-private */
    public n e(String str) {
        return this.b.get(str);
    }

    /* access modifiers changed from: package-private */
    public void a(Fragment fragment) {
        if (!this.a.contains(fragment)) {
            synchronized (this.a) {
                this.a.add(fragment);
            }
            fragment.o = true;
            return;
        }
        throw new IllegalStateException("Fragment already added: " + fragment);
    }

    /* access modifiers changed from: package-private */
    public List<Fragment> b() {
        ArrayList arrayList = new ArrayList();
        for (n next : this.b.values()) {
            if (next != null) {
                arrayList.add(next.e());
            } else {
                arrayList.add((Object) null);
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    public Fragment c(String str) {
        if (str != null) {
            for (int size = this.a.size() - 1; size >= 0; size--) {
                Fragment fragment = this.a.get(size);
                if (fragment != null && str.equals(fragment.B)) {
                    return fragment;
                }
            }
        }
        if (str == null) {
            return null;
        }
        for (n next : this.b.values()) {
            if (next != null) {
                Fragment e = next.e();
                if (str.equals(e.B)) {
                    return e;
                }
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public Fragment b(int i2) {
        for (int size = this.a.size() - 1; size >= 0; size--) {
            Fragment fragment = this.a.get(size);
            if (fragment != null && fragment.z == i2) {
                return fragment;
            }
        }
        for (n next : this.b.values()) {
            if (next != null) {
                Fragment e = next.e();
                if (e.z == i2) {
                    return e;
                }
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        Iterator<Fragment> it = this.a.iterator();
        while (it.hasNext()) {
            n nVar = this.b.get(it.next().f588i);
            if (nVar != null) {
                nVar.a(i2);
            }
        }
        for (n next : this.b.values()) {
            if (next != null) {
                next.a(i2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Fragment b(String str) {
        n nVar = this.b.get(str);
        if (nVar != null) {
            return nVar.e();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        this.b.values().removeAll(Collections.singleton((Object) null));
    }

    /* access modifiers changed from: package-private */
    public Fragment b(Fragment fragment) {
        ViewGroup viewGroup = fragment.J;
        View view = fragment.K;
        if (!(viewGroup == null || view == null)) {
            for (int indexOf = this.a.indexOf(fragment) - 1; indexOf >= 0; indexOf--) {
                Fragment fragment2 = this.a.get(indexOf);
                if (fragment2.J == viewGroup && fragment2.K != null) {
                    return fragment2;
                }
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public boolean a(String str) {
        return this.b.containsKey(str);
    }

    /* access modifiers changed from: package-private */
    public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str2 = str + "    ";
        if (!this.b.isEmpty()) {
            printWriter.print(str);
            printWriter.print("Active Fragments:");
            for (n next : this.b.values()) {
                printWriter.print(str);
                if (next != null) {
                    Fragment e = next.e();
                    printWriter.println(e);
                    e.a(str2, fileDescriptor, printWriter, strArr);
                } else {
                    printWriter.println("null");
                }
            }
        }
        int size = this.a.size();
        if (size > 0) {
            printWriter.print(str);
            printWriter.println("Added Fragments:");
            for (int i2 = 0; i2 < size; i2++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(this.a.get(i2).toString());
            }
        }
    }
}
