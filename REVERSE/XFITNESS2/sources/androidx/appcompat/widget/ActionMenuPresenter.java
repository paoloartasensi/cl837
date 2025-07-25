package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$layout;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.view.menu.l;
import androidx.appcompat.view.menu.m;
import androidx.appcompat.view.menu.n;
import androidx.appcompat.view.menu.p;
import androidx.appcompat.view.menu.r;
import androidx.appcompat.widget.ActionMenuView;
import androidx.core.h.b;
import java.util.ArrayList;

class ActionMenuPresenter extends androidx.appcompat.view.menu.b implements b.a {
    private final SparseBooleanArray A = new SparseBooleanArray();
    e B;
    a C;
    c D;
    private b E;
    final f F = new f();
    int G;
    d n;
    private Drawable o;
    private boolean p;
    private boolean q;
    private boolean r;
    private int s;
    private int t;
    private int u;
    private boolean v;
    private boolean w;
    private boolean x;
    private boolean y;
    private int z;

    @SuppressLint({"BanParcelableUsage"})
    private static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        public int e;

        static class a implements Parcelable.Creator<SavedState> {
            a() {
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }
        }

        SavedState() {
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeInt(this.e);
        }

        SavedState(Parcel parcel) {
            this.e = parcel.readInt();
        }
    }

    private class a extends l {
        public a(Context context, r rVar, View view) {
            super(context, rVar, view, false, R$attr.actionOverflowMenuStyle);
            if (!((i) rVar.getItem()).h()) {
                View view2 = ActionMenuPresenter.this.n;
                a(view2 == null ? (View) ActionMenuPresenter.this.l : view2);
            }
            a((m.a) ActionMenuPresenter.this.F);
        }

        /* access modifiers changed from: protected */
        public void d() {
            ActionMenuPresenter actionMenuPresenter = ActionMenuPresenter.this;
            actionMenuPresenter.C = null;
            actionMenuPresenter.G = 0;
            super.d();
        }
    }

    private class b extends ActionMenuItemView.b {
        b() {
        }

        public p a() {
            a aVar = ActionMenuPresenter.this.C;
            if (aVar != null) {
                return aVar.b();
            }
            return null;
        }
    }

    private class c implements Runnable {
        private e e;

        public c(e eVar) {
            this.e = eVar;
        }

        public void run() {
            if (ActionMenuPresenter.this.f127g != null) {
                ActionMenuPresenter.this.f127g.a();
            }
            View view = (View) ActionMenuPresenter.this.l;
            if (!(view == null || view.getWindowToken() == null || !this.e.f())) {
                ActionMenuPresenter.this.B = this.e;
            }
            ActionMenuPresenter.this.D = null;
        }
    }

    private class d extends AppCompatImageView implements ActionMenuView.a {

        class a extends t {
            a(View view, ActionMenuPresenter actionMenuPresenter) {
                super(view);
            }

            public p a() {
                e eVar = ActionMenuPresenter.this.B;
                if (eVar == null) {
                    return null;
                }
                return eVar.b();
            }

            public boolean b() {
                ActionMenuPresenter.this.k();
                return true;
            }

            public boolean c() {
                ActionMenuPresenter actionMenuPresenter = ActionMenuPresenter.this;
                if (actionMenuPresenter.D != null) {
                    return false;
                }
                actionMenuPresenter.g();
                return true;
            }
        }

        public d(Context context) {
            super(context, (AttributeSet) null, R$attr.actionOverflowButtonStyle);
            setClickable(true);
            setFocusable(true);
            setVisibility(0);
            setEnabled(true);
            i0.a(this, getContentDescription());
            setOnTouchListener(new a(this, ActionMenuPresenter.this));
        }

        public boolean a() {
            return false;
        }

        public boolean b() {
            return false;
        }

        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            playSoundEffect(0);
            ActionMenuPresenter.this.k();
            return true;
        }

        /* access modifiers changed from: protected */
        public boolean setFrame(int i2, int i3, int i4, int i5) {
            boolean frame = super.setFrame(i2, i3, i4, i5);
            Drawable drawable = getDrawable();
            Drawable background = getBackground();
            if (!(drawable == null || background == null)) {
                int width = getWidth();
                int height = getHeight();
                int max = Math.max(width, height) / 2;
                int paddingLeft = (width + (getPaddingLeft() - getPaddingRight())) / 2;
                int paddingTop = (height + (getPaddingTop() - getPaddingBottom())) / 2;
                androidx.core.graphics.drawable.a.a(background, paddingLeft - max, paddingTop - max, paddingLeft + max, paddingTop + max);
            }
            return frame;
        }
    }

    private class e extends l {
        public e(Context context, g gVar, View view, boolean z) {
            super(context, gVar, view, z, R$attr.actionOverflowMenuStyle);
            a(8388613);
            a((m.a) ActionMenuPresenter.this.F);
        }

        /* access modifiers changed from: protected */
        public void d() {
            if (ActionMenuPresenter.this.f127g != null) {
                ActionMenuPresenter.this.f127g.close();
            }
            ActionMenuPresenter.this.B = null;
            super.d();
        }
    }

    public ActionMenuPresenter(Context context) {
        super(context, R$layout.abc_action_menu_layout, R$layout.abc_action_menu_item_layout);
    }

    public boolean g() {
        n nVar;
        c cVar = this.D;
        if (cVar == null || (nVar = this.l) == null) {
            e eVar = this.B;
            if (eVar == null) {
                return false;
            }
            eVar.a();
            return true;
        }
        ((View) nVar).removeCallbacks(cVar);
        this.D = null;
        return true;
    }

    public boolean h() {
        a aVar = this.C;
        if (aVar == null) {
            return false;
        }
        aVar.a();
        return true;
    }

    public boolean i() {
        return this.D != null || j();
    }

    public boolean j() {
        e eVar = this.B;
        return eVar != null && eVar.c();
    }

    public boolean k() {
        g gVar;
        if (!this.q || j() || (gVar = this.f127g) == null || this.l == null || this.D != null || gVar.j().isEmpty()) {
            return false;
        }
        c cVar = new c(new e(this.f126f, this.f127g, this.n, true));
        this.D = cVar;
        ((View) this.l).post(cVar);
        super.a((r) null);
        return true;
    }

    public void a(Context context, g gVar) {
        super.a(context, gVar);
        Resources resources = context.getResources();
        androidx.appcompat.d.a a2 = androidx.appcompat.d.a.a(context);
        if (!this.r) {
            this.q = a2.g();
        }
        if (!this.x) {
            this.s = a2.b();
        }
        if (!this.v) {
            this.u = a2.c();
        }
        int i2 = this.s;
        if (this.q) {
            if (this.n == null) {
                d dVar = new d(this.e);
                this.n = dVar;
                if (this.p) {
                    dVar.setImageDrawable(this.o);
                    this.o = null;
                    this.p = false;
                }
                int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                this.n.measure(makeMeasureSpec, makeMeasureSpec);
            }
            i2 -= this.n.getMeasuredWidth();
        } else {
            this.n = null;
        }
        this.t = i2;
        this.z = (int) (resources.getDisplayMetrics().density * 56.0f);
    }

    public n b(ViewGroup viewGroup) {
        n nVar = this.l;
        n b2 = super.b(viewGroup);
        if (nVar != b2) {
            ((ActionMenuView) b2).setPresenter(this);
        }
        return b2;
    }

    public void c(boolean z2) {
        this.y = z2;
    }

    public void d(boolean z2) {
        this.q = z2;
        this.r = true;
    }

    public Parcelable e() {
        SavedState savedState = new SavedState();
        savedState.e = this.G;
        return savedState;
    }

    public Drawable f() {
        d dVar = this.n;
        if (dVar != null) {
            return dVar.getDrawable();
        }
        if (this.p) {
            return this.o;
        }
        return null;
    }

    private class f implements m.a {
        f() {
        }

        public boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            ActionMenuPresenter.this.G = ((r) gVar).getItem().getItemId();
            m.a a = ActionMenuPresenter.this.a();
            if (a != null) {
                return a.a(gVar);
            }
            return false;
        }

        public void a(g gVar, boolean z) {
            if (gVar instanceof r) {
                gVar.m().a(false);
            }
            m.a a = ActionMenuPresenter.this.a();
            if (a != null) {
                a.a(gVar, z);
            }
        }
    }

    public boolean c() {
        return g() | h();
    }

    public boolean d() {
        int i2;
        ArrayList<i> arrayList;
        int i3;
        int i4;
        int i5;
        ActionMenuPresenter actionMenuPresenter = this;
        g gVar = actionMenuPresenter.f127g;
        View view = null;
        int i6 = 0;
        if (gVar != null) {
            arrayList = gVar.n();
            i2 = arrayList.size();
        } else {
            arrayList = null;
            i2 = 0;
        }
        int i7 = actionMenuPresenter.u;
        int i8 = actionMenuPresenter.t;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        ViewGroup viewGroup = (ViewGroup) actionMenuPresenter.l;
        boolean z2 = false;
        int i9 = 0;
        int i10 = 0;
        for (int i11 = 0; i11 < i2; i11++) {
            i iVar = arrayList.get(i11);
            if (iVar.k()) {
                i9++;
            } else if (iVar.j()) {
                i10++;
            } else {
                z2 = true;
            }
            if (actionMenuPresenter.y && iVar.isActionViewExpanded()) {
                i7 = 0;
            }
        }
        if (actionMenuPresenter.q && (z2 || i10 + i9 > i7)) {
            i7--;
        }
        int i12 = i7 - i9;
        SparseBooleanArray sparseBooleanArray = actionMenuPresenter.A;
        sparseBooleanArray.clear();
        if (actionMenuPresenter.w) {
            int i13 = actionMenuPresenter.z;
            i3 = i8 / i13;
            i4 = i13 + ((i8 % i13) / i3);
        } else {
            i4 = 0;
            i3 = 0;
        }
        int i14 = 0;
        int i15 = 0;
        while (i14 < i2) {
            i iVar2 = arrayList.get(i14);
            if (iVar2.k()) {
                View a2 = actionMenuPresenter.a(iVar2, view, viewGroup);
                if (actionMenuPresenter.w) {
                    i3 -= ActionMenuView.a(a2, i4, i3, makeMeasureSpec, i6);
                } else {
                    a2.measure(makeMeasureSpec, makeMeasureSpec);
                }
                int measuredWidth = a2.getMeasuredWidth();
                i8 -= measuredWidth;
                if (i15 == 0) {
                    i15 = measuredWidth;
                }
                int groupId = iVar2.getGroupId();
                if (groupId != 0) {
                    sparseBooleanArray.put(groupId, true);
                }
                iVar2.d(true);
                i5 = i2;
            } else if (iVar2.j()) {
                int groupId2 = iVar2.getGroupId();
                boolean z3 = sparseBooleanArray.get(groupId2);
                boolean z4 = (i12 > 0 || z3) && i8 > 0 && (!actionMenuPresenter.w || i3 > 0);
                boolean z5 = z4;
                i5 = i2;
                if (z4) {
                    View a3 = actionMenuPresenter.a(iVar2, (View) null, viewGroup);
                    if (actionMenuPresenter.w) {
                        int a4 = ActionMenuView.a(a3, i4, i3, makeMeasureSpec, 0);
                        i3 -= a4;
                        if (a4 == 0) {
                            z5 = false;
                        }
                    } else {
                        a3.measure(makeMeasureSpec, makeMeasureSpec);
                    }
                    boolean z6 = z5;
                    int measuredWidth2 = a3.getMeasuredWidth();
                    i8 -= measuredWidth2;
                    if (i15 == 0) {
                        i15 = measuredWidth2;
                    }
                    z4 = z6 & (!actionMenuPresenter.w ? i8 + i15 > 0 : i8 >= 0);
                }
                if (z4 && groupId2 != 0) {
                    sparseBooleanArray.put(groupId2, true);
                } else if (z3) {
                    sparseBooleanArray.put(groupId2, false);
                    int i16 = 0;
                    while (i16 < i14) {
                        i iVar3 = arrayList.get(i16);
                        if (iVar3.getGroupId() == groupId2) {
                            if (iVar3.h()) {
                                i12++;
                            }
                            iVar3.d(false);
                        }
                        i16++;
                    }
                }
                if (z4) {
                    i12--;
                }
                iVar2.d(z4);
            } else {
                i5 = i2;
                iVar2.d(false);
                i14++;
                i2 = i5;
                view = null;
                i6 = 0;
                actionMenuPresenter = this;
            }
            i14++;
            i2 = i5;
            view = null;
            i6 = 0;
            actionMenuPresenter = this;
        }
        return true;
    }

    public void b(boolean z2) {
        if (z2) {
            super.a((r) null);
            return;
        }
        g gVar = this.f127g;
        if (gVar != null) {
            gVar.a(false);
        }
    }

    public void a(Configuration configuration) {
        if (!this.v) {
            this.u = androidx.appcompat.d.a.a(this.f126f).c();
        }
        g gVar = this.f127g;
        if (gVar != null) {
            gVar.b(true);
        }
    }

    public void a(Drawable drawable) {
        d dVar = this.n;
        if (dVar != null) {
            dVar.setImageDrawable(drawable);
            return;
        }
        this.p = true;
        this.o = drawable;
    }

    public View a(i iVar, View view, ViewGroup viewGroup) {
        View actionView = iVar.getActionView();
        if (actionView == null || iVar.f()) {
            actionView = super.a(iVar, view, viewGroup);
        }
        actionView.setVisibility(iVar.isActionViewExpanded() ? 8 : 0);
        ActionMenuView actionMenuView = (ActionMenuView) viewGroup;
        ViewGroup.LayoutParams layoutParams = actionView.getLayoutParams();
        if (!actionMenuView.checkLayoutParams(layoutParams)) {
            actionView.setLayoutParams(actionMenuView.generateLayoutParams(layoutParams));
        }
        return actionView;
    }

    public void a(i iVar, n.a aVar) {
        aVar.a(iVar, 0);
        ActionMenuItemView actionMenuItemView = (ActionMenuItemView) aVar;
        actionMenuItemView.setItemInvoker((ActionMenuView) this.l);
        if (this.E == null) {
            this.E = new b();
        }
        actionMenuItemView.setPopupCallback(this.E);
    }

    public boolean a(int i2, i iVar) {
        return iVar.h();
    }

    public void a(boolean z2) {
        n nVar;
        super.a(z2);
        ((View) this.l).requestLayout();
        g gVar = this.f127g;
        boolean z3 = false;
        if (gVar != null) {
            ArrayList<i> c2 = gVar.c();
            int size = c2.size();
            for (int i2 = 0; i2 < size; i2++) {
                androidx.core.h.b a2 = c2.get(i2).a();
                if (a2 != null) {
                    a2.a((b.a) this);
                }
            }
        }
        g gVar2 = this.f127g;
        ArrayList<i> j2 = gVar2 != null ? gVar2.j() : null;
        if (this.q && j2 != null) {
            int size2 = j2.size();
            if (size2 == 1) {
                z3 = !j2.get(0).isActionViewExpanded();
            } else if (size2 > 0) {
                z3 = true;
            }
        }
        if (z3) {
            if (this.n == null) {
                this.n = new d(this.e);
            }
            ViewGroup viewGroup = (ViewGroup) this.n.getParent();
            if (viewGroup != this.l) {
                if (viewGroup != null) {
                    viewGroup.removeView(this.n);
                }
                ActionMenuView actionMenuView = (ActionMenuView) this.l;
                actionMenuView.addView(this.n, actionMenuView.e());
            }
        } else {
            d dVar = this.n;
            if (dVar != null && dVar.getParent() == (nVar = this.l)) {
                ((ViewGroup) nVar).removeView(this.n);
            }
        }
        ((ActionMenuView) this.l).setOverflowReserved(this.q);
    }

    public boolean a(ViewGroup viewGroup, int i2) {
        if (viewGroup.getChildAt(i2) == this.n) {
            return false;
        }
        return super.a(viewGroup, i2);
    }

    public boolean a(r rVar) {
        boolean z2 = false;
        if (!rVar.hasVisibleItems()) {
            return false;
        }
        r rVar2 = rVar;
        while (rVar2.t() != this.f127g) {
            rVar2 = (r) rVar2.t();
        }
        View a2 = a(rVar2.getItem());
        if (a2 == null) {
            return false;
        }
        this.G = rVar.getItem().getItemId();
        int size = rVar.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                break;
            }
            MenuItem item = rVar.getItem(i2);
            if (item.isVisible() && item.getIcon() != null) {
                z2 = true;
                break;
            }
            i2++;
        }
        a aVar = new a(this.f126f, rVar, a2);
        this.C = aVar;
        aVar.a(z2);
        this.C.e();
        super.a(rVar);
        return true;
    }

    private View a(MenuItem menuItem) {
        ViewGroup viewGroup = (ViewGroup) this.l;
        if (viewGroup == null) {
            return null;
        }
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if ((childAt instanceof n.a) && ((n.a) childAt).getItemData() == menuItem) {
                return childAt;
            }
        }
        return null;
    }

    public void a(g gVar, boolean z2) {
        c();
        super.a(gVar, z2);
    }

    public void a(Parcelable parcelable) {
        int i2;
        MenuItem findItem;
        if ((parcelable instanceof SavedState) && (i2 = ((SavedState) parcelable).e) > 0 && (findItem = this.f127g.findItem(i2)) != null) {
            a((r) findItem.getSubMenu());
        }
    }

    public void a(ActionMenuView actionMenuView) {
        this.l = actionMenuView;
        actionMenuView.a(this.f127g);
    }
}
