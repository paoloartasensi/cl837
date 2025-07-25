package com.afollestad.materialdialogs.h;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.R$attr;
import com.afollestad.materialdialogs.internal.list.DialogRecyclerView;
import com.afollestad.materialdialogs.internal.list.b;
import com.afollestad.materialdialogs.j.e;
import java.util.List;
import kotlin.jvm.b.q;
import kotlin.jvm.internal.i;
import kotlin.l;

/* compiled from: DialogListExt.kt */
public final class a {
    public static /* synthetic */ MaterialDialog a(MaterialDialog materialDialog, RecyclerView.g gVar, RecyclerView.o oVar, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            oVar = null;
        }
        a(materialDialog, gVar, oVar);
        return materialDialog;
    }

    public static final RecyclerView.g<?> b(MaterialDialog materialDialog) {
        i.b(materialDialog, "$this$getListAdapter");
        DialogRecyclerView recyclerView = materialDialog.e().getContentLayout().getRecyclerView();
        if (recyclerView != null) {
            return recyclerView.getAdapter();
        }
        return null;
    }

    public static final MaterialDialog a(MaterialDialog materialDialog, RecyclerView.g<?> gVar, RecyclerView.o oVar) {
        i.b(materialDialog, "$this$customListAdapter");
        i.b(gVar, "adapter");
        materialDialog.e().getContentLayout().a(materialDialog, gVar, oVar);
        return materialDialog;
    }

    public static /* synthetic */ MaterialDialog a(MaterialDialog materialDialog, Integer num, List list, int[] iArr, boolean z, q qVar, int i2, Object obj) {
        a(materialDialog, (i2 & 1) != 0 ? null : num, (i2 & 2) != 0 ? null : list, (i2 & 4) != 0 ? null : iArr, (i2 & 8) != 0 ? true : z, (i2 & 16) != 0 ? null : qVar);
        return materialDialog;
    }

    public static final MaterialDialog a(MaterialDialog materialDialog, Integer num, List<? extends CharSequence> list, int[] iArr, boolean z, q<? super MaterialDialog, ? super Integer, ? super CharSequence, l> qVar) {
        List<? extends CharSequence> list2;
        i.b(materialDialog, "$this$listItems");
        e.a.a("listItems", (Object) list, num);
        if (list != null) {
            list2 = list;
        } else {
            list2 = f.d(e.a.a(materialDialog.f(), num));
        }
        if (b(materialDialog) != null) {
            Log.w("MaterialDialogs", "Prefer calling updateListItems(...) over listItems(...) again.");
            a(materialDialog, num, list, iArr, qVar);
            return materialDialog;
        }
        a(materialDialog, (RecyclerView.g) new b(materialDialog, list2, iArr, z, qVar), (RecyclerView.o) null, 2, (Object) null);
        return materialDialog;
    }

    public static final MaterialDialog a(MaterialDialog materialDialog, Integer num, List<? extends CharSequence> list, int[] iArr, q<? super MaterialDialog, ? super Integer, ? super CharSequence, l> qVar) {
        i.b(materialDialog, "$this$updateListItems");
        e.a.a("updateListItems", (Object) list, num);
        if (list == null) {
            list = f.d(e.a.a(materialDialog.f(), num));
        }
        RecyclerView.g<?> b = b(materialDialog);
        if (b instanceof b) {
            b bVar = (b) b;
            bVar.a(list, qVar);
            if (iArr != null) {
                bVar.a(iArr);
            }
            return materialDialog;
        }
        throw new IllegalStateException("updateListItems(...) can't be used before you've created a plain list dialog.".toString());
    }

    public static final Drawable a(MaterialDialog materialDialog) {
        i.b(materialDialog, "$this$getItemSelector");
        e eVar = e.a;
        Context context = materialDialog.getContext();
        i.a((Object) context, "context");
        Drawable a = e.a(eVar, context, (Integer) null, Integer.valueOf(R$attr.md_item_selector), (Drawable) null, 10, (Object) null);
        if (Build.VERSION.SDK_INT >= 21 && (a instanceof RippleDrawable)) {
            e eVar2 = e.a;
            int a2 = com.afollestad.materialdialogs.j.a.a(materialDialog, (Integer) null, Integer.valueOf(R$attr.md_ripple_color), (kotlin.jvm.b.a) null, 5, (Object) null);
            if (a2 != 0) {
                ((RippleDrawable) a).setColor(ColorStateList.valueOf(a2));
            }
        }
        return a;
    }
}
