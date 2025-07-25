package androidx.activity;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: OnBackPressedCallback */
public abstract class b {
    private boolean a;
    private CopyOnWriteArrayList<a> b = new CopyOnWriteArrayList<>();

    public b(boolean z) {
        this.a = z;
    }

    public abstract void a();

    public final void a(boolean z) {
        this.a = z;
    }

    public final boolean b() {
        return this.a;
    }

    public final void c() {
        Iterator<a> it = this.b.iterator();
        while (it.hasNext()) {
            it.next().cancel();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(a aVar) {
        this.b.add(aVar);
    }

    /* access modifiers changed from: package-private */
    public void b(a aVar) {
        this.b.remove(aVar);
    }
}
