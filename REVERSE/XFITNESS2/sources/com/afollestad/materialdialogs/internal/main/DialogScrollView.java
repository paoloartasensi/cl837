package com.afollestad.materialdialogs.internal.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
import com.afollestad.materialdialogs.j.e;
import kotlin.jvm.internal.i;

/* compiled from: DialogScrollView.kt */
public final class DialogScrollView extends ScrollView {
    private DialogLayout e;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ DialogScrollView(Context context, AttributeSet attributeSet, int i2, f fVar) {
        this(context, (i2 & 2) != 0 ? null : attributeSet);
    }

    /* access modifiers changed from: private */
    public final void b() {
        setOverScrollMode((getChildCount() == 0 || getMeasuredHeight() == 0 || !c()) ? 2 : 1);
    }

    private final boolean c() {
        View childAt = getChildAt(0);
        i.a((Object) childAt, "getChildAt(0)");
        return childAt.getMeasuredHeight() > getHeight();
    }

    public final DialogLayout getRootView() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        e.a.a(this, DialogScrollView$onAttachedToWindow$1.INSTANCE);
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int i2, int i3, int i4, int i5) {
        super.onScrollChanged(i2, i3, i4, i5);
        a();
    }

    public final void setRootView(DialogLayout dialogLayout) {
        this.e = dialogLayout;
    }

    public DialogScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void a() {
        boolean z = false;
        if (getChildCount() == 0 || getMeasuredHeight() == 0 || !c()) {
            DialogLayout dialogLayout = this.e;
            if (dialogLayout != null) {
                dialogLayout.a(false, false);
                return;
            }
            return;
        }
        View childAt = getChildAt(getChildCount() - 1);
        i.a((Object) childAt, "view");
        int bottom = childAt.getBottom() - (getMeasuredHeight() + getScrollY());
        DialogLayout dialogLayout2 = this.e;
        if (dialogLayout2 != null) {
            boolean z2 = getScrollY() > 0;
            if (bottom > 0) {
                z = true;
            }
            dialogLayout2.a(z2, z);
        }
    }
}
