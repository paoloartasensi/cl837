package androidx.transition;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.s;
import androidx.transition.l;
import java.util.ArrayList;
import java.util.List;

/* compiled from: FragmentTransitionSupport */
public class e extends s {

    /* compiled from: FragmentTransitionSupport */
    class a extends l.e {
        a(e eVar, Rect rect) {
        }
    }

    /* compiled from: FragmentTransitionSupport */
    class b implements l.f {
        final /* synthetic */ View a;
        final /* synthetic */ ArrayList b;

        b(e eVar, View view, ArrayList arrayList) {
            this.a = view;
            this.b = arrayList;
        }

        public void a(l lVar) {
        }

        public void b(l lVar) {
        }

        public void c(l lVar) {
        }

        public void d(l lVar) {
            lVar.b((l.f) this);
            this.a.setVisibility(8);
            int size = this.b.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((View) this.b.get(i2)).setVisibility(0);
            }
        }
    }

    /* compiled from: FragmentTransitionSupport */
    class c implements l.f {
        final /* synthetic */ Object a;
        final /* synthetic */ ArrayList b;
        final /* synthetic */ Object c;
        final /* synthetic */ ArrayList d;
        final /* synthetic */ Object e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ ArrayList f887f;

        c(Object obj, ArrayList arrayList, Object obj2, ArrayList arrayList2, Object obj3, ArrayList arrayList3) {
            this.a = obj;
            this.b = arrayList;
            this.c = obj2;
            this.d = arrayList2;
            this.e = obj3;
            this.f887f = arrayList3;
        }

        public void a(l lVar) {
        }

        public void b(l lVar) {
        }

        public void c(l lVar) {
            Object obj = this.a;
            if (obj != null) {
                e.this.a(obj, (ArrayList<View>) this.b, (ArrayList<View>) null);
            }
            Object obj2 = this.c;
            if (obj2 != null) {
                e.this.a(obj2, (ArrayList<View>) this.d, (ArrayList<View>) null);
            }
            Object obj3 = this.e;
            if (obj3 != null) {
                e.this.a(obj3, (ArrayList<View>) this.f887f, (ArrayList<View>) null);
            }
        }

        public void d(l lVar) {
        }
    }

    /* compiled from: FragmentTransitionSupport */
    class d extends l.e {
        d(e eVar, Rect rect) {
        }
    }

    public boolean a(Object obj) {
        return obj instanceof l;
    }

    public Object b(Object obj) {
        if (obj != null) {
            return ((l) obj).clone();
        }
        return null;
    }

    public Object c(Object obj) {
        if (obj == null) {
            return null;
        }
        p pVar = new p();
        pVar.a((l) obj);
        return pVar;
    }

    public void a(Object obj, ArrayList<View> arrayList) {
        l lVar = (l) obj;
        if (lVar != null) {
            int i2 = 0;
            if (lVar instanceof p) {
                p pVar = (p) lVar;
                int r = pVar.r();
                while (i2 < r) {
                    a((Object) pVar.a(i2), arrayList);
                    i2++;
                }
            } else if (!a(lVar) && s.a((List) lVar.n())) {
                int size = arrayList.size();
                while (i2 < size) {
                    lVar.a(arrayList.get(i2));
                    i2++;
                }
            }
        }
    }

    public void b(Object obj, View view, ArrayList<View> arrayList) {
        p pVar = (p) obj;
        List<View> n = pVar.n();
        n.clear();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            s.a(n, arrayList.get(i2));
        }
        n.add(view);
        arrayList.add(view);
        a((Object) pVar, arrayList);
    }

    public void c(Object obj, View view) {
        if (view != null) {
            Rect rect = new Rect();
            a(view, rect);
            ((l) obj).a((l.e) new a(this, rect));
        }
    }

    public Object b(Object obj, Object obj2, Object obj3) {
        p pVar = new p();
        if (obj != null) {
            pVar.a((l) obj);
        }
        if (obj2 != null) {
            pVar.a((l) obj2);
        }
        if (obj3 != null) {
            pVar.a((l) obj3);
        }
        return pVar;
    }

    private static boolean a(l lVar) {
        return !s.a((List) lVar.k()) || !s.a((List) lVar.l()) || !s.a((List) lVar.m());
    }

    public void b(Object obj, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        p pVar = (p) obj;
        if (pVar != null) {
            pVar.n().clear();
            pVar.n().addAll(arrayList2);
            a((Object) pVar, arrayList, arrayList2);
        }
    }

    public void a(Object obj, View view, ArrayList<View> arrayList) {
        ((l) obj).a((l.f) new b(this, view, arrayList));
    }

    public Object a(Object obj, Object obj2, Object obj3) {
        l lVar = (l) obj;
        l lVar2 = (l) obj2;
        l lVar3 = (l) obj3;
        if (lVar != null && lVar2 != null) {
            p pVar = new p();
            pVar.a(lVar);
            pVar.a(lVar2);
            pVar.b(1);
            lVar = pVar;
        } else if (lVar == null) {
            lVar = lVar2 != null ? lVar2 : null;
        }
        if (lVar3 == null) {
            return lVar;
        }
        p pVar2 = new p();
        if (lVar != null) {
            pVar2.a(lVar);
        }
        pVar2.a(lVar3);
        return pVar2;
    }

    public void b(Object obj, View view) {
        if (obj != null) {
            ((l) obj).d(view);
        }
    }

    public void a(ViewGroup viewGroup, Object obj) {
        n.a(viewGroup, (l) obj);
    }

    public void a(Object obj, Object obj2, ArrayList<View> arrayList, Object obj3, ArrayList<View> arrayList2, Object obj4, ArrayList<View> arrayList3) {
        ((l) obj).a((l.f) new c(obj2, arrayList, obj3, arrayList2, obj4, arrayList3));
    }

    public void a(Object obj, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        int i2;
        l lVar = (l) obj;
        int i3 = 0;
        if (lVar instanceof p) {
            p pVar = (p) lVar;
            int r = pVar.r();
            while (i3 < r) {
                a((Object) pVar.a(i3), arrayList, arrayList2);
                i3++;
            }
        } else if (!a(lVar)) {
            List<View> n = lVar.n();
            if (n.size() == arrayList.size() && n.containsAll(arrayList)) {
                if (arrayList2 == null) {
                    i2 = 0;
                } else {
                    i2 = arrayList2.size();
                }
                while (i3 < i2) {
                    lVar.a(arrayList2.get(i3));
                    i3++;
                }
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    lVar.d(arrayList.get(size));
                }
            }
        }
    }

    public void a(Object obj, View view) {
        if (obj != null) {
            ((l) obj).a(view);
        }
    }

    public void a(Object obj, Rect rect) {
        if (obj != null) {
            ((l) obj).a((l.e) new d(this, rect));
        }
    }
}
