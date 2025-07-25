package com.afollestad.materialdialogs.internal.list;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.j.e;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlin.l;

/* compiled from: DialogRecyclerView.kt */
public final class DialogRecyclerView extends RecyclerView {
    private p<? super Boolean, ? super Boolean, l> L0;
    private final a M0;

    /* compiled from: DialogRecyclerView.kt */
    public static final class a extends RecyclerView.t {
        final /* synthetic */ DialogRecyclerView a;

        a(DialogRecyclerView dialogRecyclerView) {
            this.a = dialogRecyclerView;
        }

        public void a(RecyclerView recyclerView, int i2, int i3) {
            i.b(recyclerView, "recyclerView");
            super.a(recyclerView, i2, i3);
            this.a.z();
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ DialogRecyclerView(Context context, AttributeSet attributeSet, int i2, f fVar) {
        this(context, (i2 & 2) != 0 ? null : attributeSet);
    }

    /* access modifiers changed from: private */
    public final void A() {
        int i2 = 2;
        if (!(getChildCount() == 0 || getMeasuredHeight() == 0 || D())) {
            i2 = 1;
        }
        setOverScrollMode(i2);
    }

    private final boolean B() {
        RecyclerView.g adapter = getAdapter();
        if (adapter != null) {
            i.a((Object) adapter, "adapter!!");
            int itemCount = adapter.getItemCount() - 1;
            RecyclerView.o layoutManager = getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                if (((LinearLayoutManager) layoutManager).I() == itemCount) {
                    return true;
                }
            } else if (!(layoutManager instanceof GridLayoutManager) || ((GridLayoutManager) layoutManager).I() != itemCount) {
                return false;
            } else {
                return true;
            }
            return false;
        }
        i.a();
        throw null;
    }

    private final boolean C() {
        RecyclerView.o layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).G() == 0) {
                return true;
            }
        } else if (!(layoutManager instanceof GridLayoutManager) || ((GridLayoutManager) layoutManager).G() != 0) {
            return false;
        } else {
            return true;
        }
        return false;
    }

    private final boolean D() {
        return B() && C();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        e.a.a(this, DialogRecyclerView$onAttachedToWindow$1.INSTANCE);
        addOnScrollListener(this.M0);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        removeOnScrollListener(this.M0);
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int i2, int i3, int i4, int i5) {
        super.onScrollChanged(i2, i3, i4, i5);
        z();
    }

    public final void z() {
        p<? super Boolean, ? super Boolean, l> pVar;
        if (getChildCount() != 0 && getMeasuredHeight() != 0 && (pVar = this.L0) != null) {
            l invoke = pVar.invoke(Boolean.valueOf(!C()), Boolean.valueOf(!B()));
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DialogRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        i.b(context, "context");
        this.M0 = new a(this);
    }

    public final void a(MaterialDialog materialDialog) {
        i.b(materialDialog, "dialog");
        this.L0 = new DialogRecyclerView$attach$1(materialDialog);
    }
}
