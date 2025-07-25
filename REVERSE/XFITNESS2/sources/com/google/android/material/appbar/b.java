package com.google.android.material.appbar;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.c.a;
import androidx.core.h.d;
import androidx.core.h.d0;
import androidx.core.h.v;
import java.util.List;

/* compiled from: HeaderScrollingViewBehavior */
abstract class b extends c<View> {
    final Rect d = new Rect();
    final Rect e = new Rect();

    /* renamed from: f  reason: collision with root package name */
    private int f1396f = 0;

    /* renamed from: g  reason: collision with root package name */
    private int f1397g;

    public b() {
    }

    private static int c(int i2) {
        if (i2 == 0) {
            return 8388659;
        }
        return i2;
    }

    /* access modifiers changed from: package-private */
    public abstract View a(List<View> list);

    public boolean a(CoordinatorLayout coordinatorLayout, View view, int i2, int i3, int i4, int i5) {
        View a;
        int i6 = view.getLayoutParams().height;
        if ((i6 != -1 && i6 != -2) || (a = a(coordinatorLayout.b(view))) == null) {
            return false;
        }
        if (!v.l(a) || v.l(view)) {
            View view2 = view;
        } else {
            View view3 = view;
            v.a(view, true);
            if (v.l(view)) {
                view.requestLayout();
                return true;
            }
        }
        int size = View.MeasureSpec.getSize(i4);
        if (size == 0) {
            size = coordinatorLayout.getHeight();
        }
        coordinatorLayout.a(view, i2, i3, View.MeasureSpec.makeMeasureSpec((size - a.getMeasuredHeight()) + c(a), i6 == -1 ? 1073741824 : Integer.MIN_VALUE), i5);
        return true;
    }

    /* access modifiers changed from: package-private */
    public abstract float b(View view);

    /* access modifiers changed from: protected */
    public void b(CoordinatorLayout coordinatorLayout, View view, int i2) {
        View a = a(coordinatorLayout.b(view));
        if (a != null) {
            CoordinatorLayout.f fVar = (CoordinatorLayout.f) view.getLayoutParams();
            Rect rect = this.d;
            rect.set(coordinatorLayout.getPaddingLeft() + fVar.leftMargin, a.getBottom() + fVar.topMargin, (coordinatorLayout.getWidth() - coordinatorLayout.getPaddingRight()) - fVar.rightMargin, ((coordinatorLayout.getHeight() + a.getBottom()) - coordinatorLayout.getPaddingBottom()) - fVar.bottomMargin);
            d0 lastWindowInsets = coordinatorLayout.getLastWindowInsets();
            if (lastWindowInsets != null && v.l(coordinatorLayout) && !v.l(view)) {
                rect.left += lastWindowInsets.c();
                rect.right -= lastWindowInsets.d();
            }
            Rect rect2 = this.e;
            d.a(c(fVar.c), view.getMeasuredWidth(), view.getMeasuredHeight(), rect, rect2, i2);
            int a2 = a(a);
            view.layout(rect2.left, rect2.top - a2, rect2.right, rect2.bottom - a2);
            this.f1396f = rect2.top - a.getBottom();
            return;
        }
        super.b(coordinatorLayout, view, i2);
        this.f1396f = 0;
    }

    /* access modifiers changed from: package-private */
    public int c(View view) {
        return view.getMeasuredHeight();
    }

    /* access modifiers changed from: package-private */
    public final int d() {
        return this.f1396f;
    }

    public final int c() {
        return this.f1397g;
    }

    public b(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: package-private */
    public final int a(View view) {
        if (this.f1397g == 0) {
            return 0;
        }
        float b = b(view);
        int i2 = this.f1397g;
        return a.a((int) (b * ((float) i2)), 0, i2);
    }

    public final void b(int i2) {
        this.f1397g = i2;
    }
}
