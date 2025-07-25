package com.afollestad.materialdialogs.internal.message;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.R$attr;
import com.afollestad.materialdialogs.R$dimen;
import com.afollestad.materialdialogs.R$layout;
import com.afollestad.materialdialogs.i.a;
import com.afollestad.materialdialogs.internal.list.DialogRecyclerView;
import com.afollestad.materialdialogs.internal.main.DialogLayout;
import com.afollestad.materialdialogs.internal.main.DialogScrollView;
import com.afollestad.materialdialogs.j.e;
import com.afollestad.materialdialogs.j.f;
import kotlin.TypeCastException;
import kotlin.d;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.i;
import kotlin.jvm.internal.k;
import kotlin.reflect.h;

/* compiled from: DialogContentLayout.kt */
public final class DialogContentLayout extends FrameLayout {
    static final /* synthetic */ h[] l;
    private ViewGroup e;

    /* renamed from: f  reason: collision with root package name */
    private TextView f1000f;

    /* renamed from: g  reason: collision with root package name */
    private boolean f1001g;

    /* renamed from: h  reason: collision with root package name */
    private final d f1002h;

    /* renamed from: i  reason: collision with root package name */
    private DialogScrollView f1003i;

    /* renamed from: j  reason: collision with root package name */
    private DialogRecyclerView f1004j;
    private View k;

    static {
        PropertyReference1Impl propertyReference1Impl = new PropertyReference1Impl(k.a(DialogContentLayout.class), "frameHorizontalMargin", "getFrameHorizontalMargin()I");
        k.a((PropertyReference1) propertyReference1Impl);
        l = new h[]{propertyReference1Impl};
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ DialogContentLayout(Context context, AttributeSet attributeSet, int i2, f fVar) {
        this(context, (i2 & 2) != 0 ? null : attributeSet);
    }

    public static /* synthetic */ void b(DialogContentLayout dialogContentLayout, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = -1;
        }
        if ((i4 & 2) != 0) {
            i3 = -1;
        }
        dialogContentLayout.b(i2, i3);
    }

    private final int getFrameHorizontalMargin() {
        d dVar = this.f1002h;
        h hVar = l[0];
        return ((Number) dVar.getValue()).intValue();
    }

    private final DialogLayout getRootLayout() {
        ViewParent parent = getParent();
        if (parent != null) {
            return (DialogLayout) parent;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.afollestad.materialdialogs.internal.main.DialogLayout");
    }

    public final void a(MaterialDialog materialDialog, Integer num, CharSequence charSequence, Typeface typeface, l<? super a, kotlin.l> lVar) {
        i.b(materialDialog, "dialog");
        a(false);
        if (this.f1000f == null) {
            int i2 = R$layout.md_dialog_stub_message;
            ViewGroup viewGroup = this.e;
            if (viewGroup != null) {
                TextView textView = (TextView) f.a(this, i2, viewGroup);
                ViewGroup viewGroup2 = this.e;
                if (viewGroup2 != null) {
                    viewGroup2.addView(textView);
                    this.f1000f = textView;
                } else {
                    i.a();
                    throw null;
                }
            } else {
                i.a();
                throw null;
            }
        }
        TextView textView2 = this.f1000f;
        if (textView2 != null) {
            a aVar = new a(materialDialog, textView2);
            if (lVar != null) {
                kotlin.l invoke = lVar.invoke(aVar);
            }
            TextView textView3 = this.f1000f;
            if (textView3 != null) {
                if (typeface != null) {
                    textView3.setTypeface(typeface);
                }
                e.a(e.a, textView3, materialDialog.f(), Integer.valueOf(R$attr.md_color_content), (Integer) null, 4, (Object) null);
                aVar.a(num, charSequence);
                return;
            }
            return;
        }
        i.a();
        throw null;
    }

    public final View getCustomView() {
        return this.k;
    }

    public final DialogRecyclerView getRecyclerView() {
        return this.f1004j;
    }

    public final DialogScrollView getScrollView() {
        return this.f1003i;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int childCount = getChildCount();
        int i8 = 0;
        int i9 = 0;
        while (i8 < childCount) {
            View childAt = getChildAt(i8);
            i.a((Object) childAt, "currentChild");
            int measuredHeight = childAt.getMeasuredHeight() + i9;
            if (!i.a((Object) childAt, (Object) this.k) || !this.f1001g) {
                i6 = getMeasuredWidth();
                i7 = 0;
            } else {
                i7 = getFrameHorizontalMargin();
                i6 = getMeasuredWidth() - getFrameHorizontalMargin();
            }
            childAt.layout(i7, i9, i6, measuredHeight);
            i8++;
            i9 = measuredHeight;
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int i4;
        int size = View.MeasureSpec.getSize(i2);
        int size2 = View.MeasureSpec.getSize(i3);
        DialogScrollView dialogScrollView = this.f1003i;
        if (dialogScrollView != null) {
            dialogScrollView.measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(size2, Integer.MIN_VALUE));
        }
        DialogScrollView dialogScrollView2 = this.f1003i;
        int measuredHeight = dialogScrollView2 != null ? dialogScrollView2.getMeasuredHeight() : 0;
        int i5 = size2 - measuredHeight;
        int childCount = this.f1003i != null ? getChildCount() - 1 : getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(size, measuredHeight);
            return;
        }
        int i6 = i5 / childCount;
        int childCount2 = getChildCount();
        for (int i7 = 0; i7 < childCount2; i7++) {
            View childAt = getChildAt(i7);
            i.a((Object) childAt, "currentChild");
            int id = childAt.getId();
            DialogScrollView dialogScrollView3 = this.f1003i;
            if (dialogScrollView3 == null || id != dialogScrollView3.getId()) {
                if (!i.a((Object) childAt, (Object) this.k) || !this.f1001g) {
                    i4 = View.MeasureSpec.makeMeasureSpec(size, 1073741824);
                } else {
                    i4 = View.MeasureSpec.makeMeasureSpec(size - (getFrameHorizontalMargin() * 2), 1073741824);
                }
                childAt.measure(i4, View.MeasureSpec.makeMeasureSpec(i6, Integer.MIN_VALUE));
                measuredHeight += childAt.getMeasuredHeight();
            }
        }
        setMeasuredDimension(size, measuredHeight);
    }

    public final void setCustomView(View view) {
        this.k = view;
    }

    public final void setRecyclerView(DialogRecyclerView dialogRecyclerView) {
        this.f1004j = dialogRecyclerView;
    }

    public final void setScrollView(DialogScrollView dialogScrollView) {
        this.f1003i = dialogScrollView;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DialogContentLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        i.b(context, "context");
        this.f1002h = g.a(new DialogContentLayout$frameHorizontalMargin$2(this));
    }

    public final void b(int i2, int i3) {
        View view = this.f1003i;
        if (view == null) {
            view = this.f1004j;
        }
        if (i2 != -1) {
            e.a(e.a, view, 0, i2, 0, 0, 13, (Object) null);
        }
        if (i3 != -1) {
            e.a(e.a, view, 0, 0, 0, i3, 7, (Object) null);
        }
    }

    public final void a(MaterialDialog materialDialog, RecyclerView.g<?> gVar, RecyclerView.o oVar) {
        i.b(materialDialog, "dialog");
        i.b(gVar, "adapter");
        if (this.f1004j == null) {
            DialogRecyclerView dialogRecyclerView = (DialogRecyclerView) f.a(this, R$layout.md_dialog_stub_recyclerview, (ViewGroup) null, 2, (Object) null);
            dialogRecyclerView.a(materialDialog);
            if (oVar == null) {
                oVar = new LinearLayoutManager(materialDialog.f());
            }
            dialogRecyclerView.setLayoutManager(oVar);
            this.f1004j = dialogRecyclerView;
            addView(dialogRecyclerView);
        }
        DialogRecyclerView dialogRecyclerView2 = this.f1004j;
        if (dialogRecyclerView2 != null) {
            dialogRecyclerView2.setAdapter(gVar);
        }
    }

    public final boolean a() {
        return getChildCount() > 1;
    }

    public static /* synthetic */ void a(DialogContentLayout dialogContentLayout, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = -1;
        }
        if ((i4 & 2) != 0) {
            i3 = -1;
        }
        dialogContentLayout.a(i2, i3);
    }

    public final void a(int i2, int i3) {
        if (i2 != -1) {
            e.a(e.a, getChildAt(0), 0, i2, 0, 0, 13, (Object) null);
        }
        if (i3 != -1) {
            e.a(e.a, getChildAt(getChildCount() - 1), 0, 0, 0, i3, 7, (Object) null);
        }
    }

    private final void a(boolean z) {
        if (this.f1003i == null) {
            DialogScrollView dialogScrollView = (DialogScrollView) f.a(this, R$layout.md_dialog_stub_scrollview, (ViewGroup) null, 2, (Object) null);
            dialogScrollView.setRootView(getRootLayout());
            View childAt = dialogScrollView.getChildAt(0);
            if (childAt != null) {
                this.e = (ViewGroup) childAt;
                if (!z) {
                    e.a(e.a, dialogScrollView, 0, 0, 0, e.a.a(dialogScrollView, R$dimen.md_dialog_frame_margin_vertical), 7, (Object) null);
                }
                this.f1003i = dialogScrollView;
                addView(dialogScrollView);
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type android.view.ViewGroup");
        }
    }
}
