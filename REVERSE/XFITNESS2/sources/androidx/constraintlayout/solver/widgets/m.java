package androidx.constraintlayout.solver.widgets;

import java.util.HashSet;
import java.util.Iterator;

/* compiled from: ResolutionNode */
public class m {
    HashSet<m> a = new HashSet<>(2);
    int b = 0;

    public void a(m mVar) {
        this.a.add(mVar);
    }

    public void b() {
        this.b = 0;
        Iterator<m> it = this.a.iterator();
        while (it.hasNext()) {
            it.next().b();
        }
    }

    public boolean c() {
        return this.b == 1;
    }

    public void d() {
        this.b = 0;
        this.a.clear();
    }

    public void e() {
    }

    public void a() {
        this.b = 1;
        Iterator<m> it = this.a.iterator();
        while (it.hasNext()) {
            it.next().e();
        }
    }
}
