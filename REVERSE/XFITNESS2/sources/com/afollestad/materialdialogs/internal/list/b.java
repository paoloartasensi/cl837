package com.afollestad.materialdialogs.internal.list;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.R$attr;
import com.afollestad.materialdialogs.R$layout;
import com.afollestad.materialdialogs.WhichButton;
import com.afollestad.materialdialogs.e.a;
import com.afollestad.materialdialogs.j.e;
import java.util.List;
import kotlin.jvm.b.q;
import kotlin.jvm.internal.i;
import kotlin.l;

/* compiled from: PlainListDialogAdapter.kt */
public final class b extends RecyclerView.g<c> implements a<CharSequence, q<? super MaterialDialog, ? super Integer, ? super CharSequence, ? extends l>> {
    private int[] a;
    private MaterialDialog b;
    private List<? extends CharSequence> c;
    private boolean d;
    private q<? super MaterialDialog, ? super Integer, ? super CharSequence, l> e;

    public b(MaterialDialog materialDialog, List<? extends CharSequence> list, int[] iArr, boolean z, q<? super MaterialDialog, ? super Integer, ? super CharSequence, l> qVar) {
        i.b(materialDialog, "dialog");
        i.b(list, "items");
        this.b = materialDialog;
        this.c = list;
        this.d = z;
        this.e = qVar;
        this.a = iArr == null ? new int[0] : iArr;
    }

    public final void a(int i2) {
        if (!this.d || !a.b(this.b, WhichButton.POSITIVE)) {
            q<? super MaterialDialog, ? super Integer, ? super CharSequence, l> qVar = this.e;
            if (qVar != null) {
                l invoke = qVar.invoke(this.b, Integer.valueOf(i2), this.c.get(i2));
            }
            if (this.b.a() && !a.a(this.b)) {
                this.b.dismiss();
                return;
            }
            return;
        }
        Object obj = this.b.c().get("activated_index");
        if (!(obj instanceof Integer)) {
            obj = null;
        }
        Integer num = (Integer) obj;
        this.b.c().put("activated_index", Integer.valueOf(i2));
        if (num != null) {
            notifyItemChanged(num.intValue());
        }
        notifyItemChanged(i2);
    }

    public int getItemCount() {
        return this.c.size();
    }

    public c onCreateViewHolder(ViewGroup viewGroup, int i2) {
        i.b(viewGroup, "parent");
        c cVar = new c(e.a.a(viewGroup, this.b.f(), R$layout.md_listitem), this);
        e.a(e.a, cVar.a(), this.b.f(), Integer.valueOf(R$attr.md_color_content), (Integer) null, 4, (Object) null);
        return cVar;
    }

    /* renamed from: a */
    public void onBindViewHolder(c cVar, int i2) {
        i.b(cVar, "holder");
        View view = cVar.itemView;
        i.a((Object) view, "holder.itemView");
        boolean z = true;
        view.setEnabled(!f.a(this.a, i2));
        cVar.a().setText((CharSequence) this.c.get(i2));
        View view2 = cVar.itemView;
        i.a((Object) view2, "holder.itemView");
        view2.setBackground(com.afollestad.materialdialogs.h.a.a(this.b));
        Object obj = this.b.c().get("activated_index");
        if (!(obj instanceof Integer)) {
            obj = null;
        }
        Integer num = (Integer) obj;
        View view3 = cVar.itemView;
        i.a((Object) view3, "holder.itemView");
        if (num == null || num.intValue() != i2) {
            z = false;
        }
        view3.setActivated(z);
        if (this.b.b() != null) {
            cVar.a().setTypeface(this.b.b());
        }
    }

    public void a() {
        Object obj = this.b.c().get("activated_index");
        if (!(obj instanceof Integer)) {
            obj = null;
        }
        Integer num = (Integer) obj;
        if (num != null) {
            q<? super MaterialDialog, ? super Integer, ? super CharSequence, l> qVar = this.e;
            if (qVar != null) {
                l invoke = qVar.invoke(this.b, num, this.c.get(num.intValue()));
            }
            this.b.c().remove("activated_index");
        }
    }

    public void a(List<? extends CharSequence> list, q<? super MaterialDialog, ? super Integer, ? super CharSequence, l> qVar) {
        i.b(list, "items");
        this.c = list;
        if (qVar != null) {
            this.e = qVar;
        }
        notifyDataSetChanged();
    }

    public void a(int[] iArr) {
        i.b(iArr, "indices");
        this.a = iArr;
        notifyDataSetChanged();
    }
}
