package androidx.appcompat.view.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import androidx.appcompat.view.menu.n;
import java.util.ArrayList;

/* compiled from: MenuAdapter */
public class f extends BaseAdapter {
    g e;

    /* renamed from: f  reason: collision with root package name */
    private int f145f = -1;

    /* renamed from: g  reason: collision with root package name */
    private boolean f146g;

    /* renamed from: h  reason: collision with root package name */
    private final boolean f147h;

    /* renamed from: i  reason: collision with root package name */
    private final LayoutInflater f148i;

    /* renamed from: j  reason: collision with root package name */
    private final int f149j;

    public f(g gVar, LayoutInflater layoutInflater, boolean z, int i2) {
        this.f147h = z;
        this.f148i = layoutInflater;
        this.e = gVar;
        this.f149j = i2;
        a();
    }

    public void a(boolean z) {
        this.f146g = z;
    }

    public g b() {
        return this.e;
    }

    public int getCount() {
        ArrayList<i> j2 = this.f147h ? this.e.j() : this.e.n();
        if (this.f145f < 0) {
            return j2.size();
        }
        return j2.size() - 1;
    }

    public long getItemId(int i2) {
        return (long) i2;
    }

    public View getView(int i2, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.f148i.inflate(this.f149j, viewGroup, false);
        }
        int groupId = getItem(i2).getGroupId();
        int i3 = i2 - 1;
        ListMenuItemView listMenuItemView = (ListMenuItemView) view;
        listMenuItemView.setGroupDividerEnabled(this.e.o() && groupId != (i3 >= 0 ? getItem(i3).getGroupId() : groupId));
        n.a aVar = (n.a) view;
        if (this.f146g) {
            listMenuItemView.setForceShowIcon(true);
        }
        aVar.a(getItem(i2), 0);
        return view;
    }

    public void notifyDataSetChanged() {
        a();
        super.notifyDataSetChanged();
    }

    /* access modifiers changed from: package-private */
    public void a() {
        i f2 = this.e.f();
        if (f2 != null) {
            ArrayList<i> j2 = this.e.j();
            int size = j2.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (j2.get(i2) == f2) {
                    this.f145f = i2;
                    return;
                }
            }
        }
        this.f145f = -1;
    }

    public i getItem(int i2) {
        ArrayList<i> j2 = this.f147h ? this.e.j() : this.e.n();
        int i3 = this.f145f;
        if (i3 >= 0 && i2 >= i3) {
            i2++;
        }
        return j2.get(i2);
    }
}
