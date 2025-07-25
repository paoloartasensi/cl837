package com.chileaf.fitness.ui.c;

import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chileaf.fitness.ui.c.c;

/* compiled from: lambda */
public final /* synthetic */ class a implements View.OnClickListener {
    private final /* synthetic */ c.b e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ c.C0069c f1230f;

    /* renamed from: g  reason: collision with root package name */
    private final /* synthetic */ TextView f1231g;

    /* renamed from: h  reason: collision with root package name */
    private final /* synthetic */ BaseViewHolder f1232h;

    public /* synthetic */ a(c.b bVar, c.C0069c cVar, TextView textView, BaseViewHolder baseViewHolder) {
        this.e = bVar;
        this.f1230f = cVar;
        this.f1231g = textView;
        this.f1232h = baseViewHolder;
    }

    public final void onClick(View view) {
        this.e.a(this.f1230f, this.f1231g, this.f1232h, view);
    }
}
