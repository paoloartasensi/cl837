package com.afollestad.materialdialogs.internal.list;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.TypeCastException;
import kotlin.jvm.internal.i;

/* compiled from: PlainListDialogAdapter.kt */
public final class c extends RecyclerView.c0 implements View.OnClickListener {
    private final TextView e;

    /* renamed from: f  reason: collision with root package name */
    private final b f989f;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public c(View view, b bVar) {
        super(view);
        i.b(view, "itemView");
        i.b(bVar, "adapter");
        this.f989f = bVar;
        view.setOnClickListener(this);
        View childAt = ((ViewGroup) view).getChildAt(0);
        if (childAt != null) {
            this.e = (TextView) childAt;
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type android.widget.TextView");
    }

    public final TextView a() {
        return this.e;
    }

    public void onClick(View view) {
        i.b(view, "view");
        this.f989f.a(getAdapterPosition());
    }
}
