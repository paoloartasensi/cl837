package com.afollestad.materialdialogs.internal.main;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.R$attr;
import com.afollestad.materialdialogs.R$dimen;
import com.afollestad.materialdialogs.j.e;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.i;

/* compiled from: BaseSubLayout.kt */
public abstract class BaseSubLayout extends ViewGroup {
    private final Paint e;

    /* renamed from: f  reason: collision with root package name */
    private final int f990f;

    /* renamed from: g  reason: collision with root package name */
    public MaterialDialog f991g;

    /* renamed from: h  reason: collision with root package name */
    private boolean f992h;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ BaseSubLayout(Context context, AttributeSet attributeSet, int i2, f fVar) {
        this(context, (i2 & 2) != 0 ? null : attributeSet);
    }

    private final int getDividerColor() {
        e eVar = e.a;
        MaterialDialog materialDialog = this.f991g;
        if (materialDialog != null) {
            Context context = materialDialog.getContext();
            i.a((Object) context, "dialog.context");
            return e.a(eVar, context, (Integer) null, Integer.valueOf(R$attr.md_divider_color), (a) null, 10, (Object) null);
        }
        i.d("dialog");
        throw null;
    }

    /* access modifiers changed from: protected */
    public final Paint a() {
        this.e.setColor(getDividerColor());
        return this.e;
    }

    public final MaterialDialog getDialog() {
        MaterialDialog materialDialog = this.f991g;
        if (materialDialog != null) {
            return materialDialog;
        }
        i.d("dialog");
        throw null;
    }

    /* access modifiers changed from: protected */
    public final int getDividerHeight() {
        return this.f990f;
    }

    public final boolean getDrawDivider() {
        return this.f992h;
    }

    public final void setDialog(MaterialDialog materialDialog) {
        i.b(materialDialog, "<set-?>");
        this.f991g = materialDialog;
    }

    public final void setDrawDivider(boolean z) {
        this.f992h = z;
        invalidate();
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BaseSubLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        i.b(context, "context");
        this.e = new Paint();
        this.f990f = e.a.a(this, R$dimen.md_divider_height);
        setWillNotDraw(false);
        this.e.setStyle(Paint.Style.STROKE);
        this.e.setStrokeWidth(context.getResources().getDimension(R$dimen.md_divider_height));
        this.e.setAntiAlias(true);
    }
}
