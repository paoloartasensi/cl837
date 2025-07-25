package com.chileaf.fitness.b;

import android.util.SparseIntArray;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.f;
import com.chileaf.fitness.R$id;

/* compiled from: ActivityWebBindingImpl */
public class j0 extends i0 {
    private static final ViewDataBinding.j D = null;
    private static final SparseIntArray E;
    private final CoordinatorLayout B;
    private long C;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        E = sparseIntArray;
        sparseIntArray.put(R$id.web_result, 1);
        E.put(R$id.pb_progress, 2);
    }

    public j0(f fVar, View view) {
        this(fVar, view, ViewDataBinding.a(fVar, view, 3, D, E));
    }

    /* access modifiers changed from: protected */
    public void a() {
        synchronized (this) {
            this.C = 0;
        }
    }

    /* access modifiers changed from: protected */
    public boolean a(int i2, Object obj, int i3) {
        return false;
    }

    public boolean d() {
        synchronized (this) {
            if (this.C != 0) {
                return true;
            }
            return false;
        }
    }

    public void e() {
        synchronized (this) {
            this.C = 1;
        }
        f();
    }

    private j0(f fVar, View view, Object[] objArr) {
        super(fVar, view, 0, objArr[2], objArr[1]);
        this.C = -1;
        CoordinatorLayout coordinatorLayout = objArr[0];
        this.B = coordinatorLayout;
        coordinatorLayout.setTag((Object) null);
        a(view);
        e();
    }
}
