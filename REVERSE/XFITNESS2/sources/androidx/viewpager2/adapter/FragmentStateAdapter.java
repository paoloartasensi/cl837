package androidx.viewpager2.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.core.g.h;
import androidx.core.h.v;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.j;
import androidx.fragment.app.p;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public abstract class FragmentStateAdapter extends RecyclerView.g<a> implements b {
    final Lifecycle a;
    final j b;
    final g.a.d<Fragment> c;
    private final g.a.d<Fragment.SavedState> d;
    private final g.a.d<Integer> e;

    /* renamed from: f  reason: collision with root package name */
    private FragmentMaxLifecycleEnforcer f959f;

    /* renamed from: g  reason: collision with root package name */
    boolean f960g;

    /* renamed from: h  reason: collision with root package name */
    private boolean f961h;

    class a implements View.OnLayoutChangeListener {
        final /* synthetic */ FrameLayout a;
        final /* synthetic */ a b;

        a(FrameLayout frameLayout, a aVar) {
            this.a = frameLayout;
            this.b = aVar;
        }

        public void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
            if (this.a.getParent() != null) {
                this.a.removeOnLayoutChangeListener(this);
                FragmentStateAdapter.this.d(this.b);
            }
        }
    }

    class b extends j.f {
        final /* synthetic */ Fragment a;
        final /* synthetic */ FrameLayout b;

        b(Fragment fragment, FrameLayout frameLayout) {
            this.a = fragment;
            this.b = frameLayout;
        }

        public void a(j jVar, Fragment fragment, View view, Bundle bundle) {
            if (fragment == this.a) {
                jVar.a((j.f) this);
                FragmentStateAdapter.this.a(view, this.b);
            }
        }
    }

    class c implements Runnable {
        c() {
        }

        public void run() {
            FragmentStateAdapter fragmentStateAdapter = FragmentStateAdapter.this;
            fragmentStateAdapter.f960g = false;
            fragmentStateAdapter.b();
        }
    }

    private static abstract class d extends RecyclerView.i {
        private d() {
        }

        public final void a(int i2, int i3) {
            a();
        }

        public final void b(int i2, int i3) {
            a();
        }

        public final void c(int i2, int i3) {
            a();
        }

        /* synthetic */ d(a aVar) {
            this();
        }

        public final void a(int i2, int i3, Object obj) {
            a();
        }

        public final void a(int i2, int i3, int i4) {
            a();
        }
    }

    public FragmentStateAdapter(Fragment fragment) {
        this(fragment.j(), fragment.getLifecycle());
    }

    private Long c(int i2) {
        Long l = null;
        for (int i3 = 0; i3 < this.e.e(); i3++) {
            if (this.e.c(i3).intValue() == i2) {
                if (l == null) {
                    l = Long.valueOf(this.e.a(i3));
                } else {
                    throw new IllegalStateException("Design assumption violated: a ViewHolder can only be bound to one item at a time.");
                }
            }
        }
        return l;
    }

    public abstract Fragment a(int i2);

    /* renamed from: a */
    public final void onBindViewHolder(a aVar, int i2) {
        long itemId = aVar.getItemId();
        int id = aVar.a().getId();
        Long c2 = c(id);
        if (!(c2 == null || c2.longValue() == itemId)) {
            c(c2.longValue());
            this.e.e(c2.longValue());
        }
        this.e.c(itemId, Integer.valueOf(id));
        b(i2);
        FrameLayout a2 = aVar.a();
        if (v.C(a2)) {
            if (a2.getParent() == null) {
                a2.addOnLayoutChangeListener(new a(a2, aVar));
            } else {
                throw new IllegalStateException("Design assumption violated.");
            }
        }
        b();
    }

    /* renamed from: a */
    public final boolean onFailedToRecycleView(a aVar) {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void b() {
        if (this.f961h && !c()) {
            g.a.b<Long> bVar = new g.a.b<>();
            for (int i2 = 0; i2 < this.c.e(); i2++) {
                long a2 = this.c.a(i2);
                if (!a(a2)) {
                    bVar.add(Long.valueOf(a2));
                    this.e.e(a2);
                }
            }
            if (!this.f960g) {
                this.f961h = false;
                for (int i3 = 0; i3 < this.c.e(); i3++) {
                    long a3 = this.c.a(i3);
                    if (!b(a3)) {
                        bVar.add(Long.valueOf(a3));
                    }
                }
            }
            for (Long longValue : bVar) {
                c(longValue.longValue());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void d(final a aVar) {
        Fragment c2 = this.c.c(aVar.getItemId());
        if (c2 != null) {
            FrameLayout a2 = aVar.a();
            View F = c2.F();
            if (!c2.H() && F != null) {
                throw new IllegalStateException("Design assumption violated.");
            } else if (c2.H() && F == null) {
                a(c2, a2);
            } else if (!c2.H() || F.getParent() == null) {
                if (c2.H()) {
                    a(F, a2);
                } else if (!c()) {
                    a(c2, a2);
                    p b2 = this.b.b();
                    b2.a(c2, "f" + aVar.getItemId());
                    b2.a(c2, Lifecycle.State.STARTED);
                    b2.c();
                    this.f959f.a(false);
                } else if (!this.b.w()) {
                    this.a.addObserver(new LifecycleEventObserver() {
                        public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                            if (!FragmentStateAdapter.this.c()) {
                                lifecycleOwner.getLifecycle().removeObserver(this);
                                if (v.C(aVar.a())) {
                                    FragmentStateAdapter.this.d(aVar);
                                }
                            }
                        }
                    });
                }
            } else if (F.getParent() != a2) {
                a(F, a2);
            }
        } else {
            throw new IllegalStateException("Design assumption violated.");
        }
    }

    public long getItemId(int i2) {
        return (long) i2;
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        h.a(this.f959f == null);
        FragmentMaxLifecycleEnforcer fragmentMaxLifecycleEnforcer = new FragmentMaxLifecycleEnforcer();
        this.f959f = fragmentMaxLifecycleEnforcer;
        fragmentMaxLifecycleEnforcer.a(recyclerView);
    }

    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        this.f959f.b(recyclerView);
        this.f959f = null;
    }

    public final Parcelable saveState() {
        Bundle bundle = new Bundle(this.c.e() + this.d.e());
        for (int i2 = 0; i2 < this.c.e(); i2++) {
            long a2 = this.c.a(i2);
            Fragment c2 = this.c.c(a2);
            if (c2 != null && c2.H()) {
                this.b.a(bundle, a("f#", a2), c2);
            }
        }
        for (int i3 = 0; i3 < this.d.e(); i3++) {
            long a3 = this.d.a(i3);
            if (a(a3)) {
                bundle.putParcelable(a("s#", a3), this.d.c(a3));
            }
        }
        return bundle;
    }

    public final void setHasStableIds(boolean z) {
        throw new UnsupportedOperationException("Stable Ids are required for the adapter to function properly, and the adapter takes care of setting the flag.");
    }

    public FragmentStateAdapter(j jVar, Lifecycle lifecycle) {
        this.c = new g.a.d<>();
        this.d = new g.a.d<>();
        this.e = new g.a.d<>();
        this.f960g = false;
        this.f961h = false;
        this.b = jVar;
        this.a = lifecycle;
        super.setHasStableIds(true);
    }

    public final a onCreateViewHolder(ViewGroup viewGroup, int i2) {
        return a.a(viewGroup);
    }

    /* renamed from: c */
    public final void onViewRecycled(a aVar) {
        Long c2 = c(aVar.a().getId());
        if (c2 != null) {
            c(c2.longValue());
            this.e.e(c2.longValue());
        }
    }

    class FragmentMaxLifecycleEnforcer {
        private ViewPager2.i a;
        private RecyclerView.i b;
        private LifecycleEventObserver c;
        private ViewPager2 d;
        private long e = -1;

        class a extends ViewPager2.i {
            a() {
            }

            public void a(int i2) {
                FragmentMaxLifecycleEnforcer.this.a(false);
            }

            public void b(int i2) {
                FragmentMaxLifecycleEnforcer.this.a(false);
            }
        }

        class b extends d {
            b() {
                super((a) null);
            }

            public void a() {
                FragmentMaxLifecycleEnforcer.this.a(true);
            }
        }

        FragmentMaxLifecycleEnforcer() {
        }

        private ViewPager2 c(RecyclerView recyclerView) {
            ViewParent parent = recyclerView.getParent();
            if (parent instanceof ViewPager2) {
                return (ViewPager2) parent;
            }
            throw new IllegalStateException("Expected ViewPager2 instance. Got: " + parent);
        }

        /* access modifiers changed from: package-private */
        public void a(RecyclerView recyclerView) {
            this.d = c(recyclerView);
            a aVar = new a();
            this.a = aVar;
            this.d.a((ViewPager2.i) aVar);
            b bVar = new b();
            this.b = bVar;
            FragmentStateAdapter.this.registerAdapterDataObserver(bVar);
            AnonymousClass3 r2 = new LifecycleEventObserver() {
                public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                    FragmentMaxLifecycleEnforcer.this.a(false);
                }
            };
            this.c = r2;
            FragmentStateAdapter.this.a.addObserver(r2);
        }

        /* access modifiers changed from: package-private */
        public void b(RecyclerView recyclerView) {
            c(recyclerView).b(this.a);
            FragmentStateAdapter.this.unregisterAdapterDataObserver(this.b);
            FragmentStateAdapter.this.a.removeObserver(this.c);
            this.d = null;
        }

        /* access modifiers changed from: package-private */
        public void a(boolean z) {
            int currentItem;
            Fragment c2;
            if (!FragmentStateAdapter.this.c() && this.d.getScrollState() == 0 && !FragmentStateAdapter.this.c.d() && FragmentStateAdapter.this.getItemCount() != 0 && (currentItem = this.d.getCurrentItem()) < FragmentStateAdapter.this.getItemCount()) {
                long itemId = FragmentStateAdapter.this.getItemId(currentItem);
                if ((itemId != this.e || z) && (c2 = FragmentStateAdapter.this.c.c(itemId)) != null && c2.H()) {
                    this.e = itemId;
                    p b2 = FragmentStateAdapter.this.b.b();
                    Fragment fragment = null;
                    for (int i2 = 0; i2 < FragmentStateAdapter.this.c.e(); i2++) {
                        long a2 = FragmentStateAdapter.this.c.a(i2);
                        Fragment c3 = FragmentStateAdapter.this.c.c(i2);
                        if (c3.H()) {
                            if (a2 != this.e) {
                                b2.a(c3, Lifecycle.State.STARTED);
                            } else {
                                fragment = c3;
                            }
                            c3.h(a2 == this.e);
                        }
                    }
                    if (fragment != null) {
                        b2.a(fragment, Lifecycle.State.RESUMED);
                    }
                    if (!b2.f()) {
                        b2.c();
                    }
                }
            }
        }
    }

    private void c(long j2) {
        ViewParent parent;
        Fragment c2 = this.c.c(j2);
        if (c2 != null) {
            if (!(c2.F() == null || (parent = c2.F().getParent()) == null)) {
                ((FrameLayout) parent).removeAllViews();
            }
            if (!a(j2)) {
                this.d.e(j2);
            }
            if (!c2.H()) {
                this.c.e(j2);
            } else if (c()) {
                this.f961h = true;
            } else {
                if (c2.H() && a(j2)) {
                    this.d.c(j2, this.b.n(c2));
                }
                p b2 = this.b.b();
                b2.c(c2);
                b2.c();
                this.c.e(j2);
            }
        }
    }

    private void a(Fragment fragment, FrameLayout frameLayout) {
        this.b.a((j.f) new b(fragment, frameLayout), false);
    }

    private boolean b(long j2) {
        View F;
        if (this.e.a(j2)) {
            return true;
        }
        Fragment c2 = this.c.c(j2);
        if (c2 == null || (F = c2.F()) == null || F.getParent() == null) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void a(View view, FrameLayout frameLayout) {
        if (frameLayout.getChildCount() > 1) {
            throw new IllegalStateException("Design assumption violated.");
        } else if (view.getParent() != frameLayout) {
            if (frameLayout.getChildCount() > 0) {
                frameLayout.removeAllViews();
            }
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            frameLayout.addView(view);
        }
    }

    private void b(int i2) {
        long itemId = getItemId(i2);
        if (!this.c.a(itemId)) {
            Fragment a2 = a(i2);
            a2.a(this.d.c(itemId));
            this.c.c(itemId, a2);
        }
    }

    private void d() {
        final Handler handler = new Handler(Looper.getMainLooper());
        final c cVar = new c();
        this.a.addObserver(new LifecycleEventObserver(this) {
            public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    handler.removeCallbacks(cVar);
                    lifecycleOwner.getLifecycle().removeObserver(this);
                }
            }
        });
        handler.postDelayed(cVar, 10000);
    }

    /* access modifiers changed from: package-private */
    public boolean c() {
        return this.b.x();
    }

    public boolean a(long j2) {
        return j2 >= 0 && j2 < ((long) getItemCount());
    }

    public final void a(Parcelable parcelable) {
        if (!this.d.d() || !this.c.d()) {
            throw new IllegalStateException("Expected the adapter to be 'fresh' while restoring state.");
        }
        Bundle bundle = (Bundle) parcelable;
        if (bundle.getClassLoader() == null) {
            bundle.setClassLoader(getClass().getClassLoader());
        }
        for (String str : bundle.keySet()) {
            if (a(str, "f#")) {
                this.c.c(b(str, "f#"), this.b.a(bundle, str));
            } else if (a(str, "s#")) {
                long b2 = b(str, "s#");
                Fragment.SavedState savedState = (Fragment.SavedState) bundle.getParcelable(str);
                if (a(b2)) {
                    this.d.c(b2, savedState);
                }
            } else {
                throw new IllegalArgumentException("Unexpected key in savedState: " + str);
            }
        }
        if (!this.c.d()) {
            this.f961h = true;
            this.f960g = true;
            b();
            d();
        }
    }

    /* renamed from: b */
    public final void onViewAttachedToWindow(a aVar) {
        d(aVar);
        b();
    }

    private static long b(String str, String str2) {
        return Long.parseLong(str.substring(str2.length()));
    }

    private static String a(String str, long j2) {
        return str + j2;
    }

    private static boolean a(String str, String str2) {
        return str.startsWith(str2) && str.length() > str2.length();
    }
}
