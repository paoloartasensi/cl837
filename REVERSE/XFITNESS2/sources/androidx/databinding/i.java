package androidx.databinding;

import android.util.Log;
import android.view.View;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: MergedDataBinderMapper */
public class i extends d {
    private Set<Class<? extends d>> a = new HashSet();
    private List<d> b = new CopyOnWriteArrayList();
    private List<String> c = new CopyOnWriteArrayList();

    private boolean b() {
        boolean z = false;
        for (String next : this.c) {
            try {
                Class<?> cls = Class.forName(next);
                if (d.class.isAssignableFrom(cls)) {
                    a((d) cls.newInstance());
                    this.c.remove(next);
                    z = true;
                }
            } catch (ClassNotFoundException unused) {
            } catch (IllegalAccessException e) {
                Log.e("MergedDataBinderMapper", "unable to add feature mapper for " + next, e);
            } catch (InstantiationException e2) {
                Log.e("MergedDataBinderMapper", "unable to add feature mapper for " + next, e2);
            }
        }
        return z;
    }

    public void a(d dVar) {
        if (this.a.add(dVar.getClass())) {
            this.b.add(dVar);
            for (d a2 : dVar.a()) {
                a(a2);
            }
        }
    }

    public ViewDataBinding a(f fVar, View view, int i2) {
        for (d a2 : this.b) {
            ViewDataBinding a3 = a2.a(fVar, view, i2);
            if (a3 != null) {
                return a3;
            }
        }
        if (b()) {
            return a(fVar, view, i2);
        }
        return null;
    }

    public ViewDataBinding a(f fVar, View[] viewArr, int i2) {
        for (d a2 : this.b) {
            ViewDataBinding a3 = a2.a(fVar, viewArr, i2);
            if (a3 != null) {
                return a3;
            }
        }
        if (b()) {
            return a(fVar, viewArr, i2);
        }
        return null;
    }
}
