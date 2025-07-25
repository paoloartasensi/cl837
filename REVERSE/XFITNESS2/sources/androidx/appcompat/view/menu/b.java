package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.view.menu.m;
import androidx.appcompat.view.menu.n;
import java.util.ArrayList;

/* compiled from: BaseMenuPresenter */
public abstract class b implements m {
    protected Context e;

    /* renamed from: f  reason: collision with root package name */
    protected Context f126f;

    /* renamed from: g  reason: collision with root package name */
    protected g f127g;

    /* renamed from: h  reason: collision with root package name */
    protected LayoutInflater f128h;

    /* renamed from: i  reason: collision with root package name */
    private m.a f129i;

    /* renamed from: j  reason: collision with root package name */
    private int f130j;
    private int k;
    protected n l;
    private int m;

    public b(Context context, int i2, int i3) {
        this.e = context;
        this.f128h = LayoutInflater.from(context);
        this.f130j = i2;
        this.k = i3;
    }

    public void a(Context context, g gVar) {
        this.f126f = context;
        LayoutInflater.from(context);
        this.f127g = gVar;
    }

    public abstract void a(i iVar, n.a aVar);

    public abstract boolean a(int i2, i iVar);

    public boolean a(g gVar, i iVar) {
        return false;
    }

    public n b(ViewGroup viewGroup) {
        if (this.l == null) {
            n nVar = (n) this.f128h.inflate(this.f130j, viewGroup, false);
            this.l = nVar;
            nVar.a(this.f127g);
            a(true);
        }
        return this.l;
    }

    public boolean b(g gVar, i iVar) {
        return false;
    }

    public void a(boolean z) {
        ViewGroup viewGroup = (ViewGroup) this.l;
        if (viewGroup != null) {
            g gVar = this.f127g;
            int i2 = 0;
            if (gVar != null) {
                gVar.b();
                ArrayList<i> n = this.f127g.n();
                int size = n.size();
                int i3 = 0;
                for (int i4 = 0; i4 < size; i4++) {
                    i iVar = n.get(i4);
                    if (a(i3, iVar)) {
                        View childAt = viewGroup.getChildAt(i3);
                        i itemData = childAt instanceof n.a ? ((n.a) childAt).getItemData() : null;
                        View a = a(iVar, childAt, viewGroup);
                        if (iVar != itemData) {
                            a.setPressed(false);
                            a.jumpDrawablesToCurrentState();
                        }
                        if (a != childAt) {
                            a(a, i3);
                        }
                        i3++;
                    }
                }
                i2 = i3;
            }
            while (i2 < viewGroup.getChildCount()) {
                if (!a(viewGroup, i2)) {
                    i2++;
                }
            }
        }
    }

    public int b() {
        return this.m;
    }

    /* access modifiers changed from: protected */
    public void a(View view, int i2) {
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }
        ((ViewGroup) this.l).addView(view, i2);
    }

    /* access modifiers changed from: protected */
    public boolean a(ViewGroup viewGroup, int i2) {
        viewGroup.removeViewAt(i2);
        return true;
    }

    public void a(m.a aVar) {
        this.f129i = aVar;
    }

    public m.a a() {
        return this.f129i;
    }

    public n.a a(ViewGroup viewGroup) {
        return (n.a) this.f128h.inflate(this.k, viewGroup, false);
    }

    public View a(i iVar, View view, ViewGroup viewGroup) {
        n.a aVar;
        if (view instanceof n.a) {
            aVar = (n.a) view;
        } else {
            aVar = a(viewGroup);
        }
        a(iVar, aVar);
        return (View) aVar;
    }

    public void a(g gVar, boolean z) {
        m.a aVar = this.f129i;
        if (aVar != null) {
            aVar.a(gVar, z);
        }
    }

    public boolean a(r rVar) {
        m.a aVar = this.f129i;
        if (aVar != null) {
            return aVar.a(rVar);
        }
        return false;
    }

    public void a(int i2) {
        this.m = i2;
    }
}
