package com.chileaf.fitness.b;

import android.util.SparseIntArray;
import android.view.View;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.f;
import com.chileaf.fitness.R$id;

/* compiled from: FragmentSleepSettingBindingImpl */
public class f1 extends e1 {
    private static final ViewDataBinding.j H = null;
    private static final SparseIntArray I;
    private final NestedScrollView F;
    private long G;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        I = sparseIntArray;
        sparseIntArray.put(R$id.constraint_sleep, 1);
        I.put(R$id.tv_sleep, 2);
        I.put(R$id.constraint_work, 3);
        I.put(R$id.tv_work, 4);
        I.put(R$id.constraint_rest, 5);
        I.put(R$id.tv_rest, 6);
    }

    public f1(f fVar, View view) {
        this(fVar, view, ViewDataBinding.a(fVar, view, 7, H, I));
    }

    /* access modifiers changed from: protected */
    public void a() {
        synchronized (this) {
            this.G = 0;
        }
    }

    /* access modifiers changed from: protected */
    public boolean a(int i2, Object obj, int i3) {
        return false;
    }

    public boolean d() {
        synchronized (this) {
            if (this.G != 0) {
                return true;
            }
            return false;
        }
    }

    public void e() {
        synchronized (this) {
            this.G = 1;
        }
        f();
    }

    private f1(f fVar, View view, Object[] objArr) {
        super(fVar, view, 0, objArr[5], objArr[1], objArr[3], objArr[6], objArr[2], objArr[4]);
        this.G = -1;
        NestedScrollView nestedScrollView = objArr[0];
        this.F = nestedScrollView;
        nestedScrollView.setTag((Object) null);
        a(view);
        e();
    }
}
