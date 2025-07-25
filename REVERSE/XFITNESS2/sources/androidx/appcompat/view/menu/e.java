package androidx.appcompat.view.menu;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import androidx.appcompat.R$layout;
import androidx.appcompat.view.menu.m;
import androidx.appcompat.view.menu.n;
import java.util.ArrayList;

/* compiled from: ListMenuPresenter */
public class e implements m, AdapterView.OnItemClickListener {
    Context e;

    /* renamed from: f  reason: collision with root package name */
    LayoutInflater f139f;

    /* renamed from: g  reason: collision with root package name */
    g f140g;

    /* renamed from: h  reason: collision with root package name */
    ExpandedMenuView f141h;

    /* renamed from: i  reason: collision with root package name */
    int f142i;

    /* renamed from: j  reason: collision with root package name */
    int f143j;
    int k;
    private m.a l;
    a m;
    private int n;

    /* compiled from: ListMenuPresenter */
    private class a extends BaseAdapter {
        private int e = -1;

        public a() {
            a();
        }

        /* access modifiers changed from: package-private */
        public void a() {
            i f2 = e.this.f140g.f();
            if (f2 != null) {
                ArrayList<i> j2 = e.this.f140g.j();
                int size = j2.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (j2.get(i2) == f2) {
                        this.e = i2;
                        return;
                    }
                }
            }
            this.e = -1;
        }

        public int getCount() {
            int size = e.this.f140g.j().size() - e.this.f142i;
            return this.e < 0 ? size : size - 1;
        }

        public long getItemId(int i2) {
            return (long) i2;
        }

        public View getView(int i2, View view, ViewGroup viewGroup) {
            if (view == null) {
                e eVar = e.this;
                view = eVar.f139f.inflate(eVar.k, viewGroup, false);
            }
            ((n.a) view).a(getItem(i2), 0);
            return view;
        }

        public void notifyDataSetChanged() {
            a();
            super.notifyDataSetChanged();
        }

        public i getItem(int i2) {
            ArrayList<i> j2 = e.this.f140g.j();
            int i3 = i2 + e.this.f142i;
            int i4 = this.e;
            if (i4 >= 0 && i3 >= i4) {
                i3++;
            }
            return j2.get(i3);
        }
    }

    public e(Context context, int i2) {
        this(i2, 0);
        this.e = context;
        this.f139f = LayoutInflater.from(context);
    }

    public void a(Context context, g gVar) {
        if (this.f143j != 0) {
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, this.f143j);
            this.e = contextThemeWrapper;
            this.f139f = LayoutInflater.from(contextThemeWrapper);
        } else if (this.e != null) {
            this.e = context;
            if (this.f139f == null) {
                this.f139f = LayoutInflater.from(context);
            }
        }
        this.f140g = gVar;
        a aVar = this.m;
        if (aVar != null) {
            aVar.notifyDataSetChanged();
        }
    }

    public boolean a(g gVar, i iVar) {
        return false;
    }

    public void b(Bundle bundle) {
        SparseArray sparseArray = new SparseArray();
        ExpandedMenuView expandedMenuView = this.f141h;
        if (expandedMenuView != null) {
            expandedMenuView.saveHierarchyState(sparseArray);
        }
        bundle.putSparseParcelableArray("android:menu:list", sparseArray);
    }

    public boolean b(g gVar, i iVar) {
        return false;
    }

    public boolean d() {
        return false;
    }

    public Parcelable e() {
        if (this.f141h == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        b(bundle);
        return bundle;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
        this.f140g.a((MenuItem) this.m.getItem(i2), (m) this, 0);
    }

    public e(int i2, int i3) {
        this.k = i2;
        this.f143j = i3;
    }

    public int b() {
        return this.n;
    }

    public n a(ViewGroup viewGroup) {
        if (this.f141h == null) {
            this.f141h = (ExpandedMenuView) this.f139f.inflate(R$layout.abc_expanded_menu_layout, viewGroup, false);
            if (this.m == null) {
                this.m = new a();
            }
            this.f141h.setAdapter(this.m);
            this.f141h.setOnItemClickListener(this);
        }
        return this.f141h;
    }

    public ListAdapter a() {
        if (this.m == null) {
            this.m = new a();
        }
        return this.m;
    }

    public void a(boolean z) {
        a aVar = this.m;
        if (aVar != null) {
            aVar.notifyDataSetChanged();
        }
    }

    public void a(m.a aVar) {
        this.l = aVar;
    }

    public boolean a(r rVar) {
        if (!rVar.hasVisibleItems()) {
            return false;
        }
        new h(rVar).a((IBinder) null);
        m.a aVar = this.l;
        if (aVar == null) {
            return true;
        }
        aVar.a(rVar);
        return true;
    }

    public void a(g gVar, boolean z) {
        m.a aVar = this.l;
        if (aVar != null) {
            aVar.a(gVar, z);
        }
    }

    public void a(Bundle bundle) {
        SparseArray sparseParcelableArray = bundle.getSparseParcelableArray("android:menu:list");
        if (sparseParcelableArray != null) {
            this.f141h.restoreHierarchyState(sparseParcelableArray);
        }
    }

    public void a(Parcelable parcelable) {
        a((Bundle) parcelable);
    }
}
