package com.google.android.material.transformation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.h.v;
import com.google.android.material.d.b;
import java.util.List;

public abstract class ExpandableBehavior extends CoordinatorLayout.c<View> {
    /* access modifiers changed from: private */
    public int a = 0;

    class a implements ViewTreeObserver.OnPreDrawListener {
        final /* synthetic */ View e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ int f1567f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ b f1568g;

        a(View view, int i2, b bVar) {
            this.e = view;
            this.f1567f = i2;
            this.f1568g = bVar;
        }

        public boolean onPreDraw() {
            this.e.getViewTreeObserver().removeOnPreDrawListener(this);
            if (ExpandableBehavior.this.a == this.f1567f) {
                ExpandableBehavior expandableBehavior = ExpandableBehavior.this;
                b bVar = this.f1568g;
                expandableBehavior.a((View) bVar, this.e, bVar.isExpanded(), false);
            }
            return false;
        }
    }

    public ExpandableBehavior() {
    }

    /* access modifiers changed from: protected */
    public abstract boolean a(View view, View view2, boolean z, boolean z2);

    public boolean b(CoordinatorLayout coordinatorLayout, View view, View view2) {
        b bVar = (b) view2;
        if (!a(bVar.isExpanded())) {
            return false;
        }
        this.a = bVar.isExpanded() ? 1 : 2;
        return a((View) bVar, view, bVar.isExpanded(), true);
    }

    /* access modifiers changed from: protected */
    public b e(CoordinatorLayout coordinatorLayout, View view) {
        List<View> b = coordinatorLayout.b(view);
        int size = b.size();
        for (int i2 = 0; i2 < size; i2++) {
            View view2 = b.get(i2);
            if (a(coordinatorLayout, view, view2)) {
                return (b) view2;
            }
        }
        return null;
    }

    public boolean a(CoordinatorLayout coordinatorLayout, View view, int i2) {
        b e;
        if (v.D(view) || (e = e(coordinatorLayout, view)) == null || !a(e.isExpanded())) {
            return false;
        }
        int i3 = e.isExpanded() ? 1 : 2;
        this.a = i3;
        view.getViewTreeObserver().addOnPreDrawListener(new a(view, i3, e));
        return false;
    }

    public ExpandableBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private boolean a(boolean z) {
        if (z) {
            int i2 = this.a;
            return i2 == 0 || i2 == 2;
        } else if (this.a == 1) {
            return true;
        } else {
            return false;
        }
    }
}
