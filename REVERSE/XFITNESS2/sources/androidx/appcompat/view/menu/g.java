package androidx.appcompat.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.core.h.w;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: MenuBuilder */
public class g implements androidx.core.b.a.a {
    private static final int[] A = {1, 4, 5, 3, 2, 0};
    private final Context a;
    private final Resources b;
    private boolean c;
    private boolean d;
    private a e;

    /* renamed from: f  reason: collision with root package name */
    private ArrayList<i> f150f;

    /* renamed from: g  reason: collision with root package name */
    private ArrayList<i> f151g;

    /* renamed from: h  reason: collision with root package name */
    private boolean f152h;

    /* renamed from: i  reason: collision with root package name */
    private ArrayList<i> f153i;

    /* renamed from: j  reason: collision with root package name */
    private ArrayList<i> f154j;
    private boolean k;
    private int l = 0;
    private ContextMenu.ContextMenuInfo m;
    CharSequence n;
    Drawable o;
    View p;
    private boolean q = false;
    private boolean r = false;
    private boolean s = false;
    private boolean t = false;
    private boolean u = false;
    private ArrayList<i> v = new ArrayList<>();
    private CopyOnWriteArrayList<WeakReference<m>> w = new CopyOnWriteArrayList<>();
    private i x;
    private boolean y = false;
    private boolean z;

    /* compiled from: MenuBuilder */
    public interface a {
        void a(g gVar);

        boolean a(g gVar, MenuItem menuItem);
    }

    /* compiled from: MenuBuilder */
    public interface b {
        boolean a(i iVar);
    }

    public g(Context context) {
        this.a = context;
        this.b = context.getResources();
        this.f150f = new ArrayList<>();
        this.f151g = new ArrayList<>();
        this.f152h = true;
        this.f153i = new ArrayList<>();
        this.f154j = new ArrayList<>();
        this.k = true;
        e(true);
    }

    private void d(boolean z2) {
        if (!this.w.isEmpty()) {
            s();
            Iterator<WeakReference<m>> it = this.w.iterator();
            while (it.hasNext()) {
                WeakReference next = it.next();
                m mVar = (m) next.get();
                if (mVar == null) {
                    this.w.remove(next);
                } else {
                    mVar.a(z2);
                }
            }
            r();
        }
    }

    private void e(Bundle bundle) {
        Parcelable parcelable;
        SparseArray sparseParcelableArray = bundle.getSparseParcelableArray("android:menu:presenters");
        if (sparseParcelableArray != null && !this.w.isEmpty()) {
            Iterator<WeakReference<m>> it = this.w.iterator();
            while (it.hasNext()) {
                WeakReference next = it.next();
                m mVar = (m) next.get();
                if (mVar == null) {
                    this.w.remove(next);
                } else {
                    int b2 = mVar.b();
                    if (b2 > 0 && (parcelable = (Parcelable) sparseParcelableArray.get(b2)) != null) {
                        mVar.a(parcelable);
                    }
                }
            }
        }
    }

    private void f(Bundle bundle) {
        Parcelable e2;
        if (!this.w.isEmpty()) {
            SparseArray sparseArray = new SparseArray();
            Iterator<WeakReference<m>> it = this.w.iterator();
            while (it.hasNext()) {
                WeakReference next = it.next();
                m mVar = (m) next.get();
                if (mVar == null) {
                    this.w.remove(next);
                } else {
                    int b2 = mVar.b();
                    if (b2 > 0 && (e2 = mVar.e()) != null) {
                        sparseArray.put(b2, e2);
                    }
                }
            }
            bundle.putSparseParcelableArray("android:menu:presenters", sparseArray);
        }
    }

    public void a(m mVar) {
        a(mVar, this.a);
    }

    public MenuItem add(CharSequence charSequence) {
        return a(0, 0, 0, charSequence);
    }

    public int addIntentOptions(int i2, int i3, int i4, ComponentName componentName, Intent[] intentArr, Intent intent, int i5, MenuItem[] menuItemArr) {
        int i6;
        PackageManager packageManager = this.a.getPackageManager();
        List<ResolveInfo> queryIntentActivityOptions = packageManager.queryIntentActivityOptions(componentName, intentArr, intent, 0);
        int size = queryIntentActivityOptions != null ? queryIntentActivityOptions.size() : 0;
        if ((i5 & 1) == 0) {
            removeGroup(i2);
        }
        for (int i7 = 0; i7 < size; i7++) {
            ResolveInfo resolveInfo = queryIntentActivityOptions.get(i7);
            int i8 = resolveInfo.specificIndex;
            Intent intent2 = new Intent(i8 < 0 ? intent : intentArr[i8]);
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            intent2.setComponent(new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name));
            MenuItem intent3 = add(i2, i3, i4, resolveInfo.loadLabel(packageManager)).setIcon(resolveInfo.loadIcon(packageManager)).setIntent(intent2);
            if (menuItemArr != null && (i6 = resolveInfo.specificIndex) >= 0) {
                menuItemArr[i6] = intent3;
            }
        }
        return size;
    }

    public SubMenu addSubMenu(CharSequence charSequence) {
        return addSubMenu(0, 0, 0, charSequence);
    }

    public void b(m mVar) {
        Iterator<WeakReference<m>> it = this.w.iterator();
        while (it.hasNext()) {
            WeakReference next = it.next();
            m mVar2 = (m) next.get();
            if (mVar2 == null || mVar2 == mVar) {
                this.w.remove(next);
            }
        }
    }

    public g c(int i2) {
        this.l = i2;
        return this;
    }

    public void clear() {
        i iVar = this.x;
        if (iVar != null) {
            a(iVar);
        }
        this.f150f.clear();
        b(true);
    }

    public void clearHeader() {
        this.o = null;
        this.n = null;
        this.p = null;
        b(false);
    }

    public void close() {
        a(true);
    }

    /* access modifiers changed from: protected */
    public String d() {
        return "android:menu:actionviewstates";
    }

    public MenuItem findItem(int i2) {
        MenuItem findItem;
        int size = size();
        for (int i3 = 0; i3 < size; i3++) {
            i iVar = this.f150f.get(i3);
            if (iVar.getItemId() == i2) {
                return iVar;
            }
            if (iVar.hasSubMenu() && (findItem = iVar.getSubMenu().findItem(i2)) != null) {
                return findItem;
            }
        }
        return null;
    }

    public Drawable g() {
        return this.o;
    }

    public MenuItem getItem(int i2) {
        return this.f150f.get(i2);
    }

    public CharSequence h() {
        return this.n;
    }

    public boolean hasVisibleItems() {
        if (this.z) {
            return true;
        }
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            if (this.f150f.get(i2).isVisible()) {
                return true;
            }
        }
        return false;
    }

    public View i() {
        return this.p;
    }

    public boolean isShortcutKey(int i2, KeyEvent keyEvent) {
        return a(i2, keyEvent) != null;
    }

    public ArrayList<i> j() {
        b();
        return this.f154j;
    }

    /* access modifiers changed from: package-private */
    public boolean k() {
        return this.t;
    }

    /* access modifiers changed from: package-private */
    public Resources l() {
        return this.b;
    }

    public g m() {
        return this;
    }

    public ArrayList<i> n() {
        if (!this.f152h) {
            return this.f151g;
        }
        this.f151g.clear();
        int size = this.f150f.size();
        for (int i2 = 0; i2 < size; i2++) {
            i iVar = this.f150f.get(i2);
            if (iVar.isVisible()) {
                this.f151g.add(iVar);
            }
        }
        this.f152h = false;
        this.k = true;
        return this.f151g;
    }

    public boolean o() {
        return this.y;
    }

    /* access modifiers changed from: package-private */
    public boolean p() {
        return this.c;
    }

    public boolean performIdentifierAction(int i2, int i3) {
        return a(findItem(i2), i3);
    }

    public boolean performShortcut(int i2, KeyEvent keyEvent, int i3) {
        i a2 = a(i2, keyEvent);
        boolean a3 = a2 != null ? a((MenuItem) a2, i3) : false;
        if ((i3 & 2) != 0) {
            a(true);
        }
        return a3;
    }

    public boolean q() {
        return this.d;
    }

    public void r() {
        this.q = false;
        if (this.r) {
            this.r = false;
            b(this.s);
        }
    }

    public void removeGroup(int i2) {
        int a2 = a(i2);
        if (a2 >= 0) {
            int size = this.f150f.size() - a2;
            int i3 = 0;
            while (true) {
                int i4 = i3 + 1;
                if (i3 >= size || this.f150f.get(a2).getGroupId() != i2) {
                    b(true);
                } else {
                    a(a2, false);
                    i3 = i4;
                }
            }
            b(true);
        }
    }

    public void removeItem(int i2) {
        a(b(i2), true);
    }

    public void s() {
        if (!this.q) {
            this.q = true;
            this.r = false;
            this.s = false;
        }
    }

    public void setGroupCheckable(int i2, boolean z2, boolean z3) {
        int size = this.f150f.size();
        for (int i3 = 0; i3 < size; i3++) {
            i iVar = this.f150f.get(i3);
            if (iVar.getGroupId() == i2) {
                iVar.c(z3);
                iVar.setCheckable(z2);
            }
        }
    }

    public void setGroupDividerEnabled(boolean z2) {
        this.y = z2;
    }

    public void setGroupEnabled(int i2, boolean z2) {
        int size = this.f150f.size();
        for (int i3 = 0; i3 < size; i3++) {
            i iVar = this.f150f.get(i3);
            if (iVar.getGroupId() == i2) {
                iVar.setEnabled(z2);
            }
        }
    }

    public void setGroupVisible(int i2, boolean z2) {
        int size = this.f150f.size();
        boolean z3 = false;
        for (int i3 = 0; i3 < size; i3++) {
            i iVar = this.f150f.get(i3);
            if (iVar.getGroupId() == i2 && iVar.e(z2)) {
                z3 = true;
            }
        }
        if (z3) {
            b(true);
        }
    }

    public void setQwertyMode(boolean z2) {
        this.c = z2;
        b(false);
    }

    public int size() {
        return this.f150f.size();
    }

    public void a(m mVar, Context context) {
        this.w.add(new WeakReference(mVar));
        mVar.a(context, this);
        this.k = true;
    }

    public MenuItem add(int i2) {
        return a(0, 0, 0, this.b.getString(i2));
    }

    public SubMenu addSubMenu(int i2) {
        return addSubMenu(0, 0, 0, (CharSequence) this.b.getString(i2));
    }

    public void c(Bundle bundle) {
        int size = size();
        SparseArray sparseArray = null;
        for (int i2 = 0; i2 < size; i2++) {
            MenuItem item = getItem(i2);
            View actionView = item.getActionView();
            if (!(actionView == null || actionView.getId() == -1)) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray();
                }
                actionView.saveHierarchyState(sparseArray);
                if (item.isActionViewExpanded()) {
                    bundle.putInt("android:menu:expandedactionview", item.getItemId());
                }
            }
            if (item.hasSubMenu()) {
                ((r) item.getSubMenu()).c(bundle);
            }
        }
        if (sparseArray != null) {
            bundle.putSparseParcelableArray(d(), sparseArray);
        }
    }

    public MenuItem add(int i2, int i3, int i4, CharSequence charSequence) {
        return a(i2, i3, i4, charSequence);
    }

    public SubMenu addSubMenu(int i2, int i3, int i4, CharSequence charSequence) {
        i iVar = (i) a(i2, i3, i4, charSequence);
        r rVar = new r(this.a, this, iVar);
        iVar.a(rVar);
        return rVar;
    }

    public MenuItem add(int i2, int i3, int i4, int i5) {
        return a(i2, i3, i4, this.b.getString(i5));
    }

    public void b(Bundle bundle) {
        e(bundle);
    }

    private boolean a(r rVar, m mVar) {
        boolean z2 = false;
        if (this.w.isEmpty()) {
            return false;
        }
        if (mVar != null) {
            z2 = mVar.a(rVar);
        }
        Iterator<WeakReference<m>> it = this.w.iterator();
        while (it.hasNext()) {
            WeakReference next = it.next();
            m mVar2 = (m) next.get();
            if (mVar2 == null) {
                this.w.remove(next);
            } else if (!z2) {
                z2 = mVar2.a(rVar);
            }
        }
        return z2;
    }

    public int b(int i2) {
        int size = size();
        for (int i3 = 0; i3 < size; i3++) {
            if (this.f150f.get(i3).getItemId() == i2) {
                return i3;
            }
        }
        return -1;
    }

    public SubMenu addSubMenu(int i2, int i3, int i4, int i5) {
        return addSubMenu(i2, i3, i4, (CharSequence) this.b.getString(i5));
    }

    public void b(boolean z2) {
        if (!this.q) {
            if (z2) {
                this.f152h = true;
                this.k = true;
            }
            d(z2);
            return;
        }
        this.r = true;
        if (z2) {
            this.s = true;
        }
    }

    public void d(Bundle bundle) {
        f(bundle);
    }

    private void e(boolean z2) {
        boolean z3 = true;
        if (!z2 || this.b.getConfiguration().keyboard == 1 || !w.d(ViewConfiguration.get(this.a), this.a)) {
            z3 = false;
        }
        this.d = z3;
    }

    /* access modifiers changed from: package-private */
    public void d(i iVar) {
        this.f152h = true;
        b(true);
    }

    private static int f(int i2) {
        int i3 = (-65536 & i2) >> 16;
        if (i3 >= 0) {
            int[] iArr = A;
            if (i3 < iArr.length) {
                return (i2 & 65535) | (iArr[i3] << 16);
            }
        }
        throw new IllegalArgumentException("order does not contain a valid category.");
    }

    public void a(Bundle bundle) {
        MenuItem findItem;
        if (bundle != null) {
            SparseArray sparseParcelableArray = bundle.getSparseParcelableArray(d());
            int size = size();
            for (int i2 = 0; i2 < size; i2++) {
                MenuItem item = getItem(i2);
                View actionView = item.getActionView();
                if (!(actionView == null || actionView.getId() == -1)) {
                    actionView.restoreHierarchyState(sparseParcelableArray);
                }
                if (item.hasSubMenu()) {
                    ((r) item.getSubMenu()).a(bundle);
                }
            }
            int i3 = bundle.getInt("android:menu:expandedactionview");
            if (i3 > 0 && (findItem = findItem(i3)) != null) {
                findItem.expandActionView();
            }
        }
    }

    /* access modifiers changed from: protected */
    public g d(int i2) {
        a(0, (CharSequence) null, i2, (Drawable) null, (View) null);
        return this;
    }

    public Context e() {
        return this.a;
    }

    public i f() {
        return this.x;
    }

    public void b() {
        ArrayList<i> n2 = n();
        if (this.k) {
            Iterator<WeakReference<m>> it = this.w.iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                WeakReference next = it.next();
                m mVar = (m) next.get();
                if (mVar == null) {
                    this.w.remove(next);
                } else {
                    z2 |= mVar.d();
                }
            }
            if (z2) {
                this.f153i.clear();
                this.f154j.clear();
                int size = n2.size();
                for (int i2 = 0; i2 < size; i2++) {
                    i iVar = n2.get(i2);
                    if (iVar.h()) {
                        this.f153i.add(iVar);
                    } else {
                        this.f154j.add(iVar);
                    }
                }
            } else {
                this.f153i.clear();
                this.f154j.clear();
                this.f154j.addAll(n());
            }
            this.k = false;
        }
    }

    /* access modifiers changed from: package-private */
    public void c(i iVar) {
        this.k = true;
        b(true);
    }

    /* access modifiers changed from: protected */
    public g e(int i2) {
        a(i2, (CharSequence) null, 0, (Drawable) null, (View) null);
        return this;
    }

    public ArrayList<i> c() {
        b();
        return this.f153i;
    }

    public void c(boolean z2) {
        this.z = z2;
    }

    public void a(a aVar) {
        this.e = aVar;
    }

    /* access modifiers changed from: protected */
    public MenuItem a(int i2, int i3, int i4, CharSequence charSequence) {
        int f2 = f(i4);
        i a2 = a(i2, i3, i4, f2, charSequence, this.l);
        ContextMenu.ContextMenuInfo contextMenuInfo = this.m;
        if (contextMenuInfo != null) {
            a2.a(contextMenuInfo);
        }
        ArrayList<i> arrayList = this.f150f;
        arrayList.add(a(arrayList, f2), a2);
        b(true);
        return a2;
    }

    private i a(int i2, int i3, int i4, int i5, CharSequence charSequence, int i6) {
        return new i(this, i2, i3, i4, i5, charSequence, i6);
    }

    public boolean b(i iVar) {
        boolean z2 = false;
        if (this.w.isEmpty()) {
            return false;
        }
        s();
        Iterator<WeakReference<m>> it = this.w.iterator();
        while (it.hasNext()) {
            WeakReference next = it.next();
            m mVar = (m) next.get();
            if (mVar == null) {
                this.w.remove(next);
            } else {
                z2 = mVar.b(this, iVar);
                if (z2) {
                    break;
                }
            }
        }
        r();
        if (z2) {
            this.x = iVar;
        }
        return z2;
    }

    private void a(int i2, boolean z2) {
        if (i2 >= 0 && i2 < this.f150f.size()) {
            this.f150f.remove(i2);
            if (z2) {
                b(true);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(MenuItem menuItem) {
        int groupId = menuItem.getGroupId();
        int size = this.f150f.size();
        s();
        for (int i2 = 0; i2 < size; i2++) {
            i iVar = this.f150f.get(i2);
            if (iVar.getGroupId() == groupId && iVar.i() && iVar.isCheckable()) {
                iVar.b(iVar == menuItem);
            }
        }
        r();
    }

    public int a(int i2) {
        return a(i2, 0);
    }

    public int a(int i2, int i3) {
        int size = size();
        if (i3 < 0) {
            i3 = 0;
        }
        while (i3 < size) {
            if (this.f150f.get(i3).getGroupId() == i2) {
                return i3;
            }
            i3++;
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public boolean a(g gVar, MenuItem menuItem) {
        a aVar = this.e;
        return aVar != null && aVar.a(gVar, menuItem);
    }

    public void a() {
        a aVar = this.e;
        if (aVar != null) {
            aVar.a(this);
        }
    }

    private static int a(ArrayList<i> arrayList, int i2) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size).c() <= i2) {
                return size + 1;
            }
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public void a(List<i> list, int i2, KeyEvent keyEvent) {
        boolean p2 = p();
        int modifiers = keyEvent.getModifiers();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        if (keyEvent.getKeyData(keyData) || i2 == 67) {
            int size = this.f150f.size();
            for (int i3 = 0; i3 < size; i3++) {
                i iVar = this.f150f.get(i3);
                if (iVar.hasSubMenu()) {
                    ((g) iVar.getSubMenu()).a(list, i2, keyEvent);
                }
                char alphabeticShortcut = p2 ? iVar.getAlphabeticShortcut() : iVar.getNumericShortcut();
                if (((modifiers & 69647) == ((p2 ? iVar.getAlphabeticModifiers() : iVar.getNumericModifiers()) & 69647)) && alphabeticShortcut != 0) {
                    char[] cArr = keyData.meta;
                    if ((alphabeticShortcut == cArr[0] || alphabeticShortcut == cArr[2] || (p2 && alphabeticShortcut == 8 && i2 == 67)) && iVar.isEnabled()) {
                        list.add(iVar);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public i a(int i2, KeyEvent keyEvent) {
        char c2;
        ArrayList<i> arrayList = this.v;
        arrayList.clear();
        a((List<i>) arrayList, i2, keyEvent);
        if (arrayList.isEmpty()) {
            return null;
        }
        int metaState = keyEvent.getMetaState();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        keyEvent.getKeyData(keyData);
        int size = arrayList.size();
        if (size == 1) {
            return arrayList.get(0);
        }
        boolean p2 = p();
        for (int i3 = 0; i3 < size; i3++) {
            i iVar = arrayList.get(i3);
            if (p2) {
                c2 = iVar.getAlphabeticShortcut();
            } else {
                c2 = iVar.getNumericShortcut();
            }
            if ((c2 == keyData.meta[0] && (metaState & 2) == 0) || ((c2 == keyData.meta[2] && (metaState & 2) != 0) || (p2 && c2 == 8 && i2 == 67))) {
                return iVar;
            }
        }
        return null;
    }

    public boolean a(MenuItem menuItem, int i2) {
        return a(menuItem, (m) null, i2);
    }

    public boolean a(MenuItem menuItem, m mVar, int i2) {
        i iVar = (i) menuItem;
        if (iVar == null || !iVar.isEnabled()) {
            return false;
        }
        boolean g2 = iVar.g();
        androidx.core.h.b a2 = iVar.a();
        boolean z2 = a2 != null && a2.a();
        if (iVar.f()) {
            g2 |= iVar.expandActionView();
            if (g2) {
                a(true);
            }
        } else if (iVar.hasSubMenu() || z2) {
            if ((i2 & 4) == 0) {
                a(false);
            }
            if (!iVar.hasSubMenu()) {
                iVar.a(new r(e(), this, iVar));
            }
            r rVar = (r) iVar.getSubMenu();
            if (z2) {
                a2.a((SubMenu) rVar);
            }
            g2 |= a(rVar, mVar);
            if (!g2) {
                a(true);
            }
        } else if ((i2 & 1) == 0) {
            a(true);
        }
        return g2;
    }

    public final void a(boolean z2) {
        if (!this.u) {
            this.u = true;
            Iterator<WeakReference<m>> it = this.w.iterator();
            while (it.hasNext()) {
                WeakReference next = it.next();
                m mVar = (m) next.get();
                if (mVar == null) {
                    this.w.remove(next);
                } else {
                    mVar.a(this, z2);
                }
            }
            this.u = false;
        }
    }

    private void a(int i2, CharSequence charSequence, int i3, Drawable drawable, View view) {
        Resources l2 = l();
        if (view != null) {
            this.p = view;
            this.n = null;
            this.o = null;
        } else {
            if (i2 > 0) {
                this.n = l2.getText(i2);
            } else if (charSequence != null) {
                this.n = charSequence;
            }
            if (i3 > 0) {
                this.o = androidx.core.content.a.c(e(), i3);
            } else if (drawable != null) {
                this.o = drawable;
            }
            this.p = null;
        }
        b(false);
    }

    /* access modifiers changed from: protected */
    public g a(CharSequence charSequence) {
        a(0, charSequence, 0, (Drawable) null, (View) null);
        return this;
    }

    /* access modifiers changed from: protected */
    public g a(Drawable drawable) {
        a(0, (CharSequence) null, 0, drawable, (View) null);
        return this;
    }

    /* access modifiers changed from: protected */
    public g a(View view) {
        a(0, (CharSequence) null, 0, (Drawable) null, view);
        return this;
    }

    public boolean a(i iVar) {
        boolean z2 = false;
        if (!this.w.isEmpty() && this.x == iVar) {
            s();
            Iterator<WeakReference<m>> it = this.w.iterator();
            while (it.hasNext()) {
                WeakReference next = it.next();
                m mVar = (m) next.get();
                if (mVar == null) {
                    this.w.remove(next);
                } else {
                    z2 = mVar.a(this, iVar);
                    if (z2) {
                        break;
                    }
                }
            }
            r();
            if (z2) {
                this.x = null;
            }
        }
        return z2;
    }
}
