package androidx.preference;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.h.v;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.j;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.f;
import java.util.ArrayList;
import java.util.List;

/* compiled from: PreferenceGroupAdapter */
public class h extends RecyclerView.g<l> implements Preference.b {
    private PreferenceGroup a;
    private List<Preference> b;
    private List<Preference> c;
    private List<d> d;
    private Handler e;

    /* renamed from: f  reason: collision with root package name */
    private Runnable f759f = new a();

    /* compiled from: PreferenceGroupAdapter */
    class a implements Runnable {
        a() {
        }

        public void run() {
            h.this.b();
        }
    }

    /* compiled from: PreferenceGroupAdapter */
    class b extends f.b {
        final /* synthetic */ List a;
        final /* synthetic */ List b;
        final /* synthetic */ j.d c;

        b(h hVar, List list, List list2, j.d dVar) {
            this.a = list;
            this.b = list2;
            this.c = dVar;
        }

        public boolean areContentsTheSame(int i2, int i3) {
            return this.c.a((Preference) this.a.get(i2), (Preference) this.b.get(i3));
        }

        public boolean areItemsTheSame(int i2, int i3) {
            return this.c.b((Preference) this.a.get(i2), (Preference) this.b.get(i3));
        }

        public int getNewListSize() {
            return this.b.size();
        }

        public int getOldListSize() {
            return this.a.size();
        }
    }

    /* compiled from: PreferenceGroupAdapter */
    class c implements Preference.d {
        final /* synthetic */ PreferenceGroup a;

        c(PreferenceGroup preferenceGroup) {
            this.a = preferenceGroup;
        }

        public boolean a(Preference preference) {
            this.a.h(Integer.MAX_VALUE);
            h.this.a(preference);
            PreferenceGroup.b I = this.a.I();
            if (I == null) {
                return true;
            }
            I.a();
            return true;
        }
    }

    /* compiled from: PreferenceGroupAdapter */
    private static class d {
        int a;
        int b;
        String c;

        d(Preference preference) {
            this.c = preference.getClass().getName();
            this.a = preference.i();
            this.b = preference.r();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof d)) {
                return false;
            }
            d dVar = (d) obj;
            if (this.a == dVar.a && this.b == dVar.b && TextUtils.equals(this.c, dVar.c)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return ((((527 + this.a) * 31) + this.b) * 31) + this.c.hashCode();
        }
    }

    public h(PreferenceGroup preferenceGroup) {
        this.a = preferenceGroup;
        this.e = new Handler();
        this.a.setOnPreferenceChangeInternalListener(this);
        this.b = new ArrayList();
        this.c = new ArrayList();
        this.d = new ArrayList();
        PreferenceGroup preferenceGroup2 = this.a;
        if (preferenceGroup2 instanceof PreferenceScreen) {
            setHasStableIds(((PreferenceScreen) preferenceGroup2).M());
        } else {
            setHasStableIds(true);
        }
        b();
    }

    private void a(List<Preference> list, PreferenceGroup preferenceGroup) {
        preferenceGroup.L();
        int J = preferenceGroup.J();
        for (int i2 = 0; i2 < J; i2++) {
            Preference g2 = preferenceGroup.g(i2);
            list.add(g2);
            d dVar = new d(g2);
            if (!this.d.contains(dVar)) {
                this.d.add(dVar);
            }
            if (g2 instanceof PreferenceGroup) {
                PreferenceGroup preferenceGroup2 = (PreferenceGroup) g2;
                if (preferenceGroup2.K()) {
                    a(list, preferenceGroup2);
                }
            }
            g2.setOnPreferenceChangeInternalListener(this);
        }
    }

    /* access modifiers changed from: package-private */
    public void b() {
        for (Preference onPreferenceChangeInternalListener : this.b) {
            onPreferenceChangeInternalListener.setOnPreferenceChangeInternalListener((Preference.b) null);
        }
        ArrayList arrayList = new ArrayList(this.b.size());
        this.b = arrayList;
        a((List<Preference>) arrayList, this.a);
        List<Preference> list = this.c;
        List<Preference> a2 = a(this.a);
        this.c = a2;
        j m = this.a.m();
        if (m == null || m.e() == null) {
            notifyDataSetChanged();
        } else {
            f.a(new b(this, list, a2, m.e())).a((RecyclerView.g) this);
        }
        for (Preference a3 : this.b) {
            a3.a();
        }
    }

    public Preference getItem(int i2) {
        if (i2 < 0 || i2 >= getItemCount()) {
            return null;
        }
        return this.c.get(i2);
    }

    public int getItemCount() {
        return this.c.size();
    }

    public long getItemId(int i2) {
        if (!hasStableIds()) {
            return -1;
        }
        return getItem(i2).f();
    }

    public int getItemViewType(int i2) {
        d dVar = new d(getItem(i2));
        int indexOf = this.d.indexOf(dVar);
        if (indexOf != -1) {
            return indexOf;
        }
        int size = this.d.size();
        this.d.add(dVar);
        return size;
    }

    public l onCreateViewHolder(ViewGroup viewGroup, int i2) {
        d dVar = this.d.get(i2);
        LayoutInflater from = LayoutInflater.from(viewGroup.getContext());
        TypedArray obtainStyledAttributes = viewGroup.getContext().obtainStyledAttributes((AttributeSet) null, R$styleable.BackgroundStyle);
        Drawable drawable = obtainStyledAttributes.getDrawable(R$styleable.BackgroundStyle_android_selectableItemBackground);
        if (drawable == null) {
            drawable = androidx.appcompat.a.a.a.c(viewGroup.getContext(), 17301602);
        }
        obtainStyledAttributes.recycle();
        View inflate = from.inflate(dVar.a, viewGroup, false);
        if (inflate.getBackground() == null) {
            v.a(inflate, drawable);
        }
        ViewGroup viewGroup2 = (ViewGroup) inflate.findViewById(16908312);
        if (viewGroup2 != null) {
            int i3 = dVar.b;
            if (i3 != 0) {
                from.inflate(i3, viewGroup2);
            } else {
                viewGroup2.setVisibility(8);
            }
        }
        return new l(inflate);
    }

    private List<Preference> a(PreferenceGroup preferenceGroup) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int J = preferenceGroup.J();
        int i2 = 0;
        for (int i3 = 0; i3 < J; i3++) {
            Preference g2 = preferenceGroup.g(i3);
            if (g2.x()) {
                if (!b(preferenceGroup) || i2 < preferenceGroup.H()) {
                    arrayList.add(g2);
                } else {
                    arrayList2.add(g2);
                }
                if (!(g2 instanceof PreferenceGroup)) {
                    i2++;
                } else {
                    PreferenceGroup preferenceGroup2 = (PreferenceGroup) g2;
                    if (!preferenceGroup2.K()) {
                        continue;
                    } else if (!b(preferenceGroup) || !b(preferenceGroup2)) {
                        for (Preference next : a(preferenceGroup2)) {
                            if (!b(preferenceGroup) || i2 < preferenceGroup.H()) {
                                arrayList.add(next);
                            } else {
                                arrayList2.add(next);
                            }
                            i2++;
                        }
                    } else {
                        throw new IllegalStateException("Nesting an expandable group inside of another expandable group is not supported!");
                    }
                }
            }
        }
        if (b(preferenceGroup) && i2 > preferenceGroup.H()) {
            arrayList.add(a(preferenceGroup, (List<Preference>) arrayList2));
        }
        return arrayList;
    }

    private boolean b(PreferenceGroup preferenceGroup) {
        return preferenceGroup.H() != Integer.MAX_VALUE;
    }

    public void b(Preference preference) {
        int indexOf = this.c.indexOf(preference);
        if (indexOf != -1) {
            notifyItemChanged(indexOf, preference);
        }
    }

    private b a(PreferenceGroup preferenceGroup, List<Preference> list) {
        b bVar = new b(preferenceGroup.b(), list, preferenceGroup.f());
        bVar.setOnPreferenceClickListener(new c(preferenceGroup));
        return bVar;
    }

    public void a(Preference preference) {
        this.e.removeCallbacks(this.f759f);
        this.e.post(this.f759f);
    }

    /* renamed from: a */
    public void onBindViewHolder(l lVar, int i2) {
        getItem(i2).a(lVar);
    }
}
