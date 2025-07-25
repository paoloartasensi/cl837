package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.h.v;
import androidx.core.h.x;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressLint({"UnknownNullness"})
/* compiled from: FragmentTransitionImpl */
public abstract class s {

    /* compiled from: FragmentTransitionImpl */
    class a implements Runnable {
        final /* synthetic */ int e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ ArrayList f657f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ ArrayList f658g;

        /* renamed from: h  reason: collision with root package name */
        final /* synthetic */ ArrayList f659h;

        /* renamed from: i  reason: collision with root package name */
        final /* synthetic */ ArrayList f660i;

        a(s sVar, int i2, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4) {
            this.e = i2;
            this.f657f = arrayList;
            this.f658g = arrayList2;
            this.f659h = arrayList3;
            this.f660i = arrayList4;
        }

        public void run() {
            for (int i2 = 0; i2 < this.e; i2++) {
                v.a((View) this.f657f.get(i2), (String) this.f658g.get(i2));
                v.a((View) this.f659h.get(i2), (String) this.f660i.get(i2));
            }
        }
    }

    /* compiled from: FragmentTransitionImpl */
    class b implements Runnable {
        final /* synthetic */ ArrayList e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ Map f661f;

        b(s sVar, ArrayList arrayList, Map map) {
            this.e = arrayList;
            this.f661f = map;
        }

        public void run() {
            int size = this.e.size();
            for (int i2 = 0; i2 < size; i2++) {
                View view = (View) this.e.get(i2);
                String v = v.v(view);
                if (v != null) {
                    v.a(view, s.a((Map<String, String>) this.f661f, v));
                }
            }
        }
    }

    /* compiled from: FragmentTransitionImpl */
    class c implements Runnable {
        final /* synthetic */ ArrayList e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ Map f662f;

        c(s sVar, ArrayList arrayList, Map map) {
            this.e = arrayList;
            this.f662f = map;
        }

        public void run() {
            int size = this.e.size();
            for (int i2 = 0; i2 < size; i2++) {
                View view = (View) this.e.get(i2);
                v.a(view, (String) this.f662f.get(v.v(view)));
            }
        }
    }

    public abstract Object a(Object obj, Object obj2, Object obj3);

    /* access modifiers changed from: protected */
    public void a(View view, Rect rect) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        rect.set(iArr[0], iArr[1], iArr[0] + view.getWidth(), iArr[1] + view.getHeight());
    }

    public abstract void a(ViewGroup viewGroup, Object obj);

    public abstract void a(Object obj, Rect rect);

    public abstract void a(Object obj, View view);

    public abstract void a(Object obj, View view, ArrayList<View> arrayList);

    public abstract void a(Object obj, Object obj2, ArrayList<View> arrayList, Object obj3, ArrayList<View> arrayList2, Object obj4, ArrayList<View> arrayList3);

    public abstract void a(Object obj, ArrayList<View> arrayList);

    public abstract void a(Object obj, ArrayList<View> arrayList, ArrayList<View> arrayList2);

    public abstract boolean a(Object obj);

    public abstract Object b(Object obj);

    public abstract Object b(Object obj, Object obj2, Object obj3);

    public abstract void b(Object obj, View view);

    public abstract void b(Object obj, View view, ArrayList<View> arrayList);

    public abstract void b(Object obj, ArrayList<View> arrayList, ArrayList<View> arrayList2);

    public abstract Object c(Object obj);

    public abstract void c(Object obj, View view);

    /* access modifiers changed from: package-private */
    public ArrayList<String> a(ArrayList<View> arrayList) {
        ArrayList<String> arrayList2 = new ArrayList<>();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            View view = arrayList.get(i2);
            arrayList2.add(v.v(view));
            v.a(view, (String) null);
        }
        return arrayList2;
    }

    /* access modifiers changed from: package-private */
    public void a(View view, ArrayList<View> arrayList, ArrayList<View> arrayList2, ArrayList<String> arrayList3, Map<String, String> map) {
        int size = arrayList2.size();
        ArrayList arrayList4 = new ArrayList();
        for (int i2 = 0; i2 < size; i2++) {
            View view2 = arrayList.get(i2);
            String v = v.v(view2);
            arrayList4.add(v);
            if (v != null) {
                v.a(view2, (String) null);
                String str = map.get(v);
                int i3 = 0;
                while (true) {
                    if (i3 >= size) {
                        break;
                    } else if (str.equals(arrayList3.get(i3))) {
                        v.a(arrayList2.get(i3), v);
                        break;
                    } else {
                        i3++;
                    }
                }
            }
        }
        androidx.core.h.s.a(view, new a(this, size, arrayList2, arrayList3, arrayList, arrayList4));
    }

    /* access modifiers changed from: package-private */
    public void a(ArrayList<View> arrayList, View view) {
        if (view.getVisibility() != 0) {
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (x.a(viewGroup)) {
                arrayList.add(viewGroup);
                return;
            }
            int childCount = viewGroup.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                a(arrayList, viewGroup.getChildAt(i2));
            }
            return;
        }
        arrayList.add(view);
    }

    /* access modifiers changed from: package-private */
    public void a(Map<String, View> map, View view) {
        if (view.getVisibility() == 0) {
            String v = v.v(view);
            if (v != null) {
                map.put(v, view);
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    a(map, viewGroup.getChildAt(i2));
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(View view, ArrayList<View> arrayList, Map<String, String> map) {
        androidx.core.h.s.a(view, new b(this, arrayList, map));
    }

    public void a(Fragment fragment, Object obj, androidx.core.d.a aVar, Runnable runnable) {
        runnable.run();
    }

    /* access modifiers changed from: package-private */
    public void a(ViewGroup viewGroup, ArrayList<View> arrayList, Map<String, String> map) {
        androidx.core.h.s.a(viewGroup, new c(this, arrayList, map));
    }

    protected static void a(List<View> list, View view) {
        int size = list.size();
        if (!a(list, view, size)) {
            list.add(view);
            for (int i2 = size; i2 < list.size(); i2++) {
                View view2 = list.get(i2);
                if (view2 instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view2;
                    int childCount = viewGroup.getChildCount();
                    for (int i3 = 0; i3 < childCount; i3++) {
                        View childAt = viewGroup.getChildAt(i3);
                        if (!a(list, childAt, size)) {
                            list.add(childAt);
                        }
                    }
                }
            }
        }
    }

    private static boolean a(List<View> list, View view, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            if (list.get(i3) == view) {
                return true;
            }
        }
        return false;
    }

    protected static boolean a(List list) {
        return list == null || list.isEmpty();
    }

    static String a(Map<String, String> map, String str) {
        for (Map.Entry next : map.entrySet()) {
            if (str.equals(next.getValue())) {
                return (String) next.getKey();
            }
        }
        return null;
    }
}
