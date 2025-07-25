package com.chileaf.fitness.b;

import android.util.SparseIntArray;
import android.view.View;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.f;
import com.chileaf.fitness.R$id;

/* compiled from: FragmentHealthSettingBindingImpl */
public class v0 extends u0 {
    private static final ViewDataBinding.j S = null;
    private static final SparseIntArray T;
    private final NestedScrollView Q;
    private long R;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        T = sparseIntArray;
        sparseIntArray.put(R$id.constraint_drink, 1);
        T.put(R$id.sw_drink, 2);
        T.put(R$id.constraint_drink_am, 3);
        T.put(R$id.tv_drink_am, 4);
        T.put(R$id.constraint_drink_pm, 5);
        T.put(R$id.tv_drink_pm, 6);
        T.put(R$id.constraint_drink_interval, 7);
        T.put(R$id.tv_drink_interval, 8);
        T.put(R$id.constraint_inactivity, 9);
        T.put(R$id.sw_inactivity, 10);
        T.put(R$id.constraint_inactivity_am, 11);
        T.put(R$id.tv_inactivity_am, 12);
        T.put(R$id.constraint_inactivity_pm, 13);
        T.put(R$id.tv_inactivity_pm, 14);
        T.put(R$id.constraint_inactivity_interval, 15);
        T.put(R$id.tv_inactivity_interval, 16);
        T.put(R$id.constraint_matter, 17);
        T.put(R$id.iv_notify_arrow, 18);
    }

    public v0(f fVar, View view) {
        this(fVar, view, ViewDataBinding.a(fVar, view, 19, S, T));
    }

    /* access modifiers changed from: protected */
    public void a() {
        synchronized (this) {
            this.R = 0;
        }
    }

    /* access modifiers changed from: protected */
    public boolean a(int i2, Object obj, int i3) {
        return false;
    }

    public boolean d() {
        synchronized (this) {
            if (this.R != 0) {
                return true;
            }
            return false;
        }
    }

    public void e() {
        synchronized (this) {
            this.R = 1;
        }
        f();
    }

    private v0(f fVar, View view, Object[] objArr) {
        super(fVar, view, 0, objArr[1], objArr[3], objArr[7], objArr[5], objArr[9], objArr[11], objArr[15], objArr[13], objArr[17], objArr[18], objArr[2], objArr[10], objArr[4], objArr[8], objArr[6], objArr[12], objArr[16], objArr[14]);
        this.R = -1;
        NestedScrollView nestedScrollView = objArr[0];
        this.Q = nestedScrollView;
        nestedScrollView.setTag((Object) null);
        a(view);
        e();
    }
}
