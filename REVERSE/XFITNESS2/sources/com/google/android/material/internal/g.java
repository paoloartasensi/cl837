package com.google.android.material.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.view.menu.m;
import androidx.appcompat.view.menu.n;
import androidx.appcompat.view.menu.r;
import androidx.core.h.d0;
import androidx.core.h.v;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R$dimen;
import com.google.android.material.R$layout;
import java.util.ArrayList;

/* compiled from: NavigationMenuPresenter */
public class g implements m {
    private NavigationMenuView e;

    /* renamed from: f  reason: collision with root package name */
    LinearLayout f1520f;

    /* renamed from: g  reason: collision with root package name */
    private m.a f1521g;

    /* renamed from: h  reason: collision with root package name */
    androidx.appcompat.view.menu.g f1522h;

    /* renamed from: i  reason: collision with root package name */
    private int f1523i;

    /* renamed from: j  reason: collision with root package name */
    c f1524j;
    LayoutInflater k;
    int l;
    boolean m;
    ColorStateList n;
    ColorStateList o;
    Drawable p;
    int q;
    int r;
    private int s;
    int t;
    final View.OnClickListener u = new a();

    /* compiled from: NavigationMenuPresenter */
    class a implements View.OnClickListener {
        a() {
        }

        public void onClick(View view) {
            g.this.b(true);
            androidx.appcompat.view.menu.i itemData = ((NavigationMenuItemView) view).getItemData();
            g gVar = g.this;
            boolean a = gVar.f1522h.a((MenuItem) itemData, (m) gVar, 0);
            if (itemData != null && itemData.isCheckable() && a) {
                g.this.f1524j.a(itemData);
            }
            g.this.b(false);
            g.this.a(false);
        }
    }

    /* compiled from: NavigationMenuPresenter */
    private static class b extends k {
        public b(View view) {
            super(view);
        }
    }

    /* compiled from: NavigationMenuPresenter */
    private class c extends RecyclerView.g<k> {
        private final ArrayList<e> a = new ArrayList<>();
        private androidx.appcompat.view.menu.i b;
        private boolean c;

        c() {
            e();
        }

        private void e() {
            if (!this.c) {
                this.c = true;
                this.a.clear();
                this.a.add(new d());
                int i2 = -1;
                int size = g.this.f1522h.n().size();
                boolean z = false;
                int i3 = 0;
                for (int i4 = 0; i4 < size; i4++) {
                    androidx.appcompat.view.menu.i iVar = g.this.f1522h.n().get(i4);
                    if (iVar.isChecked()) {
                        a(iVar);
                    }
                    if (iVar.isCheckable()) {
                        iVar.c(false);
                    }
                    if (iVar.hasSubMenu()) {
                        SubMenu subMenu = iVar.getSubMenu();
                        if (subMenu.hasVisibleItems()) {
                            if (i4 != 0) {
                                this.a.add(new f(g.this.t, 0));
                            }
                            this.a.add(new C0081g(iVar));
                            int size2 = this.a.size();
                            int size3 = subMenu.size();
                            boolean z2 = false;
                            for (int i5 = 0; i5 < size3; i5++) {
                                androidx.appcompat.view.menu.i iVar2 = (androidx.appcompat.view.menu.i) subMenu.getItem(i5);
                                if (iVar2.isVisible()) {
                                    if (!z2 && iVar2.getIcon() != null) {
                                        z2 = true;
                                    }
                                    if (iVar2.isCheckable()) {
                                        iVar2.c(false);
                                    }
                                    if (iVar.isChecked()) {
                                        a(iVar);
                                    }
                                    this.a.add(new C0081g(iVar2));
                                }
                            }
                            if (z2) {
                                a(size2, this.a.size());
                            }
                        }
                    } else {
                        int groupId = iVar.getGroupId();
                        if (groupId != i2) {
                            i3 = this.a.size();
                            z = iVar.getIcon() != null;
                            if (i4 != 0) {
                                i3++;
                                ArrayList<e> arrayList = this.a;
                                int i6 = g.this.t;
                                arrayList.add(new f(i6, i6));
                            }
                        } else if (!z && iVar.getIcon() != null) {
                            a(i3, this.a.size());
                            z = true;
                        }
                        C0081g gVar = new C0081g(iVar);
                        gVar.b = z;
                        this.a.add(gVar);
                        i2 = groupId;
                    }
                }
                this.c = false;
            }
        }

        /* renamed from: a */
        public void onBindViewHolder(k kVar, int i2) {
            int itemViewType = getItemViewType(i2);
            if (itemViewType == 0) {
                NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView) kVar.itemView;
                navigationMenuItemView.setIconTintList(g.this.o);
                g gVar = g.this;
                if (gVar.m) {
                    navigationMenuItemView.setTextAppearance(gVar.l);
                }
                ColorStateList colorStateList = g.this.n;
                if (colorStateList != null) {
                    navigationMenuItemView.setTextColor(colorStateList);
                }
                Drawable drawable = g.this.p;
                v.a((View) navigationMenuItemView, drawable != null ? drawable.getConstantState().newDrawable() : null);
                C0081g gVar2 = (C0081g) this.a.get(i2);
                navigationMenuItemView.setNeedsEmptyIcon(gVar2.b);
                navigationMenuItemView.setHorizontalPadding(g.this.q);
                navigationMenuItemView.setIconPadding(g.this.r);
                navigationMenuItemView.a(gVar2.a(), 0);
            } else if (itemViewType == 1) {
                ((TextView) kVar.itemView).setText(((C0081g) this.a.get(i2)).a().getTitle());
            } else if (itemViewType == 2) {
                f fVar = (f) this.a.get(i2);
                kVar.itemView.setPadding(0, fVar.b(), 0, fVar.a());
            }
        }

        public Bundle b() {
            Bundle bundle = new Bundle();
            androidx.appcompat.view.menu.i iVar = this.b;
            if (iVar != null) {
                bundle.putInt("android:menu:checked", iVar.getItemId());
            }
            SparseArray sparseArray = new SparseArray();
            int size = this.a.size();
            for (int i2 = 0; i2 < size; i2++) {
                e eVar = this.a.get(i2);
                if (eVar instanceof C0081g) {
                    androidx.appcompat.view.menu.i a2 = ((C0081g) eVar).a();
                    View actionView = a2 != null ? a2.getActionView() : null;
                    if (actionView != null) {
                        ParcelableSparseArray parcelableSparseArray = new ParcelableSparseArray();
                        actionView.saveHierarchyState(parcelableSparseArray);
                        sparseArray.put(a2.getItemId(), parcelableSparseArray);
                    }
                }
            }
            bundle.putSparseParcelableArray("android:menu:action_views", sparseArray);
            return bundle;
        }

        public androidx.appcompat.view.menu.i c() {
            return this.b;
        }

        public void d() {
            e();
            notifyDataSetChanged();
        }

        public int getItemCount() {
            return this.a.size();
        }

        public long getItemId(int i2) {
            return (long) i2;
        }

        public int getItemViewType(int i2) {
            e eVar = this.a.get(i2);
            if (eVar instanceof f) {
                return 2;
            }
            if (eVar instanceof d) {
                return 3;
            }
            if (eVar instanceof C0081g) {
                return ((C0081g) eVar).a().hasSubMenu() ? 1 : 0;
            }
            throw new RuntimeException("Unknown item type.");
        }

        public k onCreateViewHolder(ViewGroup viewGroup, int i2) {
            if (i2 == 0) {
                g gVar = g.this;
                return new h(gVar.k, viewGroup, gVar.u);
            } else if (i2 == 1) {
                return new j(g.this.k, viewGroup);
            } else {
                if (i2 == 2) {
                    return new i(g.this.k, viewGroup);
                }
                if (i2 != 3) {
                    return null;
                }
                return new b(g.this.f1520f);
            }
        }

        /* renamed from: a */
        public void onViewRecycled(k kVar) {
            if (kVar instanceof h) {
                ((NavigationMenuItemView) kVar.itemView).d();
            }
        }

        private void a(int i2, int i3) {
            while (i2 < i3) {
                ((C0081g) this.a.get(i2)).b = true;
                i2++;
            }
        }

        public void a(androidx.appcompat.view.menu.i iVar) {
            if (this.b != iVar && iVar.isCheckable()) {
                androidx.appcompat.view.menu.i iVar2 = this.b;
                if (iVar2 != null) {
                    iVar2.setChecked(false);
                }
                this.b = iVar;
                iVar.setChecked(true);
            }
        }

        public void a(Bundle bundle) {
            androidx.appcompat.view.menu.i a2;
            View actionView;
            ParcelableSparseArray parcelableSparseArray;
            androidx.appcompat.view.menu.i a3;
            int i2 = bundle.getInt("android:menu:checked", 0);
            if (i2 != 0) {
                this.c = true;
                int size = this.a.size();
                int i3 = 0;
                while (true) {
                    if (i3 >= size) {
                        break;
                    }
                    e eVar = this.a.get(i3);
                    if ((eVar instanceof C0081g) && (a3 = ((C0081g) eVar).a()) != null && a3.getItemId() == i2) {
                        a(a3);
                        break;
                    }
                    i3++;
                }
                this.c = false;
                e();
            }
            SparseArray sparseParcelableArray = bundle.getSparseParcelableArray("android:menu:action_views");
            if (sparseParcelableArray != null) {
                int size2 = this.a.size();
                for (int i4 = 0; i4 < size2; i4++) {
                    e eVar2 = this.a.get(i4);
                    if (!(!(eVar2 instanceof C0081g) || (a2 = ((C0081g) eVar2).a()) == null || (actionView = a2.getActionView()) == null || (parcelableSparseArray = (ParcelableSparseArray) sparseParcelableArray.get(a2.getItemId())) == null)) {
                        actionView.restoreHierarchyState(parcelableSparseArray);
                    }
                }
            }
        }

        public void a(boolean z) {
            this.c = z;
        }
    }

    /* compiled from: NavigationMenuPresenter */
    private static class d implements e {
        d() {
        }
    }

    /* compiled from: NavigationMenuPresenter */
    private interface e {
    }

    /* compiled from: NavigationMenuPresenter */
    private static class f implements e {
        private final int a;
        private final int b;

        public f(int i2, int i3) {
            this.a = i2;
            this.b = i3;
        }

        public int a() {
            return this.b;
        }

        public int b() {
            return this.a;
        }
    }

    /* renamed from: com.google.android.material.internal.g$g  reason: collision with other inner class name */
    /* compiled from: NavigationMenuPresenter */
    private static class C0081g implements e {
        private final androidx.appcompat.view.menu.i a;
        boolean b;

        C0081g(androidx.appcompat.view.menu.i iVar) {
            this.a = iVar;
        }

        public androidx.appcompat.view.menu.i a() {
            return this.a;
        }
    }

    /* compiled from: NavigationMenuPresenter */
    private static class h extends k {
        public h(LayoutInflater layoutInflater, ViewGroup viewGroup, View.OnClickListener onClickListener) {
            super(layoutInflater.inflate(R$layout.design_navigation_item, viewGroup, false));
            this.itemView.setOnClickListener(onClickListener);
        }
    }

    /* compiled from: NavigationMenuPresenter */
    private static class i extends k {
        public i(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R$layout.design_navigation_item_separator, viewGroup, false));
        }
    }

    /* compiled from: NavigationMenuPresenter */
    private static class j extends k {
        public j(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R$layout.design_navigation_item_subheader, viewGroup, false));
        }
    }

    /* compiled from: NavigationMenuPresenter */
    private static abstract class k extends RecyclerView.c0 {
        public k(View view) {
            super(view);
        }
    }

    public void a(Context context, androidx.appcompat.view.menu.g gVar) {
        this.k = LayoutInflater.from(context);
        this.f1522h = gVar;
        this.t = context.getResources().getDimensionPixelOffset(R$dimen.design_navigation_separator_vertical_padding);
    }

    public boolean a(androidx.appcompat.view.menu.g gVar, androidx.appcompat.view.menu.i iVar) {
        return false;
    }

    public boolean a(r rVar) {
        return false;
    }

    public int b() {
        return this.f1523i;
    }

    public boolean b(androidx.appcompat.view.menu.g gVar, androidx.appcompat.view.menu.i iVar) {
        return false;
    }

    public int c() {
        return this.f1520f.getChildCount();
    }

    public void d(int i2) {
        this.r = i2;
        a(false);
    }

    public boolean d() {
        return false;
    }

    public Parcelable e() {
        Bundle bundle = new Bundle();
        if (this.e != null) {
            SparseArray sparseArray = new SparseArray();
            this.e.saveHierarchyState(sparseArray);
            bundle.putSparseParcelableArray("android:menu:list", sparseArray);
        }
        c cVar = this.f1524j;
        if (cVar != null) {
            bundle.putBundle("android:menu:adapter", cVar.b());
        }
        if (this.f1520f != null) {
            SparseArray sparseArray2 = new SparseArray();
            this.f1520f.saveHierarchyState(sparseArray2);
            bundle.putSparseParcelableArray("android:menu:header", sparseArray2);
        }
        return bundle;
    }

    public Drawable f() {
        return this.p;
    }

    public int g() {
        return this.q;
    }

    public int h() {
        return this.r;
    }

    public ColorStateList i() {
        return this.n;
    }

    public ColorStateList j() {
        return this.o;
    }

    public void b(int i2) {
        this.f1523i = i2;
    }

    public void c(int i2) {
        this.q = i2;
        a(false);
    }

    public void b(ColorStateList colorStateList) {
        this.n = colorStateList;
        a(false);
    }

    public void b(boolean z) {
        c cVar = this.f1524j;
        if (cVar != null) {
            cVar.a(z);
        }
    }

    public n a(ViewGroup viewGroup) {
        if (this.e == null) {
            this.e = (NavigationMenuView) this.k.inflate(R$layout.design_navigation_menu, viewGroup, false);
            if (this.f1524j == null) {
                this.f1524j = new c();
            }
            this.f1520f = (LinearLayout) this.k.inflate(R$layout.design_navigation_item_header, this.e, false);
            this.e.setAdapter(this.f1524j);
        }
        return this.e;
    }

    public void e(int i2) {
        this.l = i2;
        this.m = true;
        a(false);
    }

    public void a(boolean z) {
        c cVar = this.f1524j;
        if (cVar != null) {
            cVar.d();
        }
    }

    public void a(m.a aVar) {
        this.f1521g = aVar;
    }

    public void a(androidx.appcompat.view.menu.g gVar, boolean z) {
        m.a aVar = this.f1521g;
        if (aVar != null) {
            aVar.a(gVar, z);
        }
    }

    public void a(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            SparseArray sparseParcelableArray = bundle.getSparseParcelableArray("android:menu:list");
            if (sparseParcelableArray != null) {
                this.e.restoreHierarchyState(sparseParcelableArray);
            }
            Bundle bundle2 = bundle.getBundle("android:menu:adapter");
            if (bundle2 != null) {
                this.f1524j.a(bundle2);
            }
            SparseArray sparseParcelableArray2 = bundle.getSparseParcelableArray("android:menu:header");
            if (sparseParcelableArray2 != null) {
                this.f1520f.restoreHierarchyState(sparseParcelableArray2);
            }
        }
    }

    public void a(androidx.appcompat.view.menu.i iVar) {
        this.f1524j.a(iVar);
    }

    public androidx.appcompat.view.menu.i a() {
        return this.f1524j.c();
    }

    public View a(int i2) {
        View inflate = this.k.inflate(i2, this.f1520f, false);
        a(inflate);
        return inflate;
    }

    public void a(View view) {
        this.f1520f.addView(view);
        NavigationMenuView navigationMenuView = this.e;
        navigationMenuView.setPadding(0, 0, 0, navigationMenuView.getPaddingBottom());
    }

    public void a(ColorStateList colorStateList) {
        this.o = colorStateList;
        a(false);
    }

    public void a(Drawable drawable) {
        this.p = drawable;
        a(false);
    }

    public void a(d0 d0Var) {
        int e2 = d0Var.e();
        if (this.s != e2) {
            this.s = e2;
            if (this.f1520f.getChildCount() == 0) {
                NavigationMenuView navigationMenuView = this.e;
                navigationMenuView.setPadding(0, this.s, 0, navigationMenuView.getPaddingBottom());
            }
        }
        v.a((View) this.f1520f, d0Var);
    }
}
