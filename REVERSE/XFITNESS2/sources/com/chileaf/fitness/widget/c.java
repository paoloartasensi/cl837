package com.chileaf.fitness.widget;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/* compiled from: TabLayoutMediator */
public final class c {

    /* renamed from: j  reason: collision with root package name */
    private static Method f1321j;
    private static Method k;
    private final TabLayout a;
    private final ViewPager2 b;
    private final boolean c;
    private final a d;
    private RecyclerView.g e;

    /* renamed from: f  reason: collision with root package name */
    private boolean f1322f;

    /* renamed from: g  reason: collision with root package name */
    private C0073c f1323g;

    /* renamed from: h  reason: collision with root package name */
    private TabLayout.d f1324h;

    /* renamed from: i  reason: collision with root package name */
    private RecyclerView.i f1325i;

    /* compiled from: TabLayoutMediator */
    public interface a {
        void a(TabLayout.g gVar, int i2);
    }

    /* compiled from: TabLayoutMediator */
    private class b extends RecyclerView.i {
        b() {
        }

        public void a() {
            c.this.b();
        }

        public void b(int i2, int i3) {
            c.this.b();
        }

        public void c(int i2, int i3) {
            c.this.b();
        }

        public void a(int i2, int i3) {
            c.this.b();
        }

        public void a(int i2, int i3, Object obj) {
            c.this.b();
        }

        public void a(int i2, int i3, int i4) {
            c.this.b();
        }
    }

    /* compiled from: TabLayoutMediator */
    private static class d implements TabLayout.d {
        private final ViewPager2 e;

        d(ViewPager2 viewPager2) {
            this.e = viewPager2;
        }

        public void a(TabLayout.g gVar) {
        }

        public void b(TabLayout.g gVar) {
            this.e.a(gVar.c(), true);
        }

        public void c(TabLayout.g gVar) {
        }
    }

    static {
        Class<TabLayout> cls = TabLayout.class;
        try {
            Method declaredMethod = cls.getDeclaredMethod("a", new Class[]{Integer.TYPE, Float.TYPE, Boolean.TYPE, Boolean.TYPE});
            f1321j = declaredMethod;
            declaredMethod.setAccessible(true);
            Method declaredMethod2 = TabLayout.class.getDeclaredMethod("b", new Class[]{TabLayout.g.class, Boolean.TYPE});
            k = declaredMethod2;
            declaredMethod2.setAccessible(true);
        } catch (NoSuchMethodException unused) {
            throw new IllegalStateException("Can't reflect into method TabLayout.setScrollPosition(int, float, boolean, boolean)");
        }
    }

    public c(TabLayout tabLayout, ViewPager2 viewPager2, a aVar) {
        this(tabLayout, viewPager2, true, aVar);
    }

    public void a() {
        if (!this.f1322f) {
            RecyclerView.g adapter = this.b.getAdapter();
            this.e = adapter;
            if (adapter != null) {
                this.f1322f = true;
                C0073c cVar = new C0073c(this.a);
                this.f1323g = cVar;
                this.b.a((ViewPager2.i) cVar);
                d dVar = new d(this.b);
                this.f1324h = dVar;
                this.a.addOnTabSelectedListener(dVar);
                if (this.c) {
                    b bVar = new b();
                    this.f1325i = bVar;
                    this.e.registerAdapterDataObserver(bVar);
                }
                b();
                this.a.a(this.b.getCurrentItem(), 0.0f, true);
                return;
            }
            throw new IllegalStateException("TabLayoutMediator attached before ViewPager2 has an adapter");
        }
        throw new IllegalStateException("TabLayoutMediator is already attached");
    }

    /* access modifiers changed from: package-private */
    public void b() {
        int currentItem;
        this.a.d();
        RecyclerView.g gVar = this.e;
        if (gVar != null) {
            int itemCount = gVar.getItemCount();
            for (int i2 = 0; i2 < itemCount; i2++) {
                TabLayout.g b2 = this.a.b();
                this.d.a(b2, i2);
                this.a.a(b2, false);
            }
            if (itemCount > 0 && (currentItem = this.b.getCurrentItem()) != this.a.getSelectedTabPosition()) {
                this.a.b(currentItem).g();
            }
        }
    }

    /* renamed from: com.chileaf.fitness.widget.c$c  reason: collision with other inner class name */
    /* compiled from: TabLayoutMediator */
    private static class C0073c extends ViewPager2.i {
        private final WeakReference<TabLayout> a;
        private int b;
        private int c;

        C0073c(TabLayout tabLayout) {
            this.a = new WeakReference<>(tabLayout);
            a();
        }

        public void a(int i2) {
            this.b = this.c;
            this.c = i2;
        }

        public void b(int i2) {
            TabLayout tabLayout = (TabLayout) this.a.get();
            if (tabLayout != null && tabLayout.getSelectedTabPosition() != i2 && i2 < tabLayout.getTabCount()) {
                int i3 = this.c;
                c.a(tabLayout, tabLayout.b(i2), i3 == 0 || (i3 == 2 && this.b == 0));
            }
        }

        public void a(int i2, float f2, int i3) {
            TabLayout tabLayout = (TabLayout) this.a.get();
            if (tabLayout != null) {
                boolean z = false;
                boolean z2 = this.c != 2 || this.b == 1;
                if (!(this.c == 2 && this.b == 0)) {
                    z = true;
                }
                c.a(tabLayout, i2, f2, z2, z);
            }
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.c = 0;
            this.b = 0;
        }
    }

    public c(TabLayout tabLayout, ViewPager2 viewPager2, boolean z, a aVar) {
        this.a = tabLayout;
        this.b = viewPager2;
        this.c = z;
        this.d = aVar;
    }

    private static void b(String str) {
        throw new IllegalStateException("Method " + str + " not found");
    }

    static void a(TabLayout tabLayout, int i2, float f2, boolean z, boolean z2) {
        try {
            if (f1321j != null) {
                f1321j.invoke(tabLayout, new Object[]{Integer.valueOf(i2), Float.valueOf(f2), Boolean.valueOf(z), Boolean.valueOf(z2)});
                return;
            }
            b("TabLayout.setScrollPosition(int, float, boolean, boolean)");
            throw null;
        } catch (Exception unused) {
            a("TabLayout.setScrollPosition(int, float, boolean, boolean)");
            throw null;
        }
    }

    static void a(TabLayout tabLayout, TabLayout.g gVar, boolean z) {
        try {
            if (k != null) {
                k.invoke(tabLayout, new Object[]{gVar, Boolean.valueOf(z)});
                return;
            }
            b("TabLayout.selectTab(TabLayout.Tab, boolean)");
            throw null;
        } catch (Exception unused) {
            a("TabLayout.selectTab(TabLayout.Tab, boolean)");
            throw null;
        }
    }

    private static void a(String str) {
        throw new IllegalStateException("Couldn't invoke method " + str);
    }
}
