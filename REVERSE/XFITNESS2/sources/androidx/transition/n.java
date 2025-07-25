package androidx.transition;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.core.h.v;
import androidx.transition.l;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: TransitionManager */
public class n {
    private static l a = new b();
    private static ThreadLocal<WeakReference<g.a.a<ViewGroup, ArrayList<l>>>> b = new ThreadLocal<>();
    static ArrayList<ViewGroup> c = new ArrayList<>();

    /* compiled from: TransitionManager */
    private static class a implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {
        l e;

        /* renamed from: f  reason: collision with root package name */
        ViewGroup f902f;

        /* renamed from: androidx.transition.n$a$a  reason: collision with other inner class name */
        /* compiled from: TransitionManager */
        class C0050a extends m {
            final /* synthetic */ g.a.a a;

            C0050a(g.a.a aVar) {
                this.a = aVar;
            }

            public void d(l lVar) {
                ((ArrayList) this.a.get(a.this.f902f)).remove(lVar);
            }
        }

        a(l lVar, ViewGroup viewGroup) {
            this.e = lVar;
            this.f902f = viewGroup;
        }

        private void a() {
            this.f902f.getViewTreeObserver().removeOnPreDrawListener(this);
            this.f902f.removeOnAttachStateChangeListener(this);
        }

        public boolean onPreDraw() {
            a();
            if (!n.c.remove(this.f902f)) {
                return true;
            }
            g.a.a<ViewGroup, ArrayList<l>> a = n.a();
            ArrayList arrayList = a.get(this.f902f);
            ArrayList arrayList2 = null;
            if (arrayList == null) {
                arrayList = new ArrayList();
                a.put(this.f902f, arrayList);
            } else if (arrayList.size() > 0) {
                arrayList2 = new ArrayList(arrayList);
            }
            arrayList.add(this.e);
            this.e.a((l.f) new C0050a(a));
            this.e.a(this.f902f, false);
            if (arrayList2 != null) {
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    ((l) it.next()).e(this.f902f);
                }
            }
            this.e.a(this.f902f);
            return true;
        }

        public void onViewAttachedToWindow(View view) {
        }

        public void onViewDetachedFromWindow(View view) {
            a();
            n.c.remove(this.f902f);
            ArrayList arrayList = n.a().get(this.f902f);
            if (arrayList != null && arrayList.size() > 0) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((l) it.next()).e(this.f902f);
                }
            }
            this.e.a(true);
        }
    }

    static g.a.a<ViewGroup, ArrayList<l>> a() {
        g.a.a<ViewGroup, ArrayList<l>> aVar;
        WeakReference weakReference = b.get();
        if (weakReference != null && (aVar = (g.a.a) weakReference.get()) != null) {
            return aVar;
        }
        g.a.a<ViewGroup, ArrayList<l>> aVar2 = new g.a.a<>();
        b.set(new WeakReference(aVar2));
        return aVar2;
    }

    private static void b(ViewGroup viewGroup, l lVar) {
        if (lVar != null && viewGroup != null) {
            a aVar = new a(lVar, viewGroup);
            viewGroup.addOnAttachStateChangeListener(aVar);
            viewGroup.getViewTreeObserver().addOnPreDrawListener(aVar);
        }
    }

    private static void c(ViewGroup viewGroup, l lVar) {
        ArrayList arrayList = a().get(viewGroup);
        if (arrayList != null && arrayList.size() > 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((l) it.next()).c((View) viewGroup);
            }
        }
        if (lVar != null) {
            lVar.a(viewGroup, true);
        }
        k a2 = k.a(viewGroup);
        if (a2 != null) {
            a2.a();
        }
    }

    public static void a(ViewGroup viewGroup, l lVar) {
        if (!c.contains(viewGroup) && v.D(viewGroup)) {
            c.add(viewGroup);
            if (lVar == null) {
                lVar = a;
            }
            l clone = lVar.clone();
            c(viewGroup, clone);
            k.a(viewGroup, (k) null);
            b(viewGroup, clone);
        }
    }
}
