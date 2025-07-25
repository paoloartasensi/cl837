package com.chileaf.fitness.b;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.f;
import com.chileaf.fitness.R$id;

/* compiled from: ActivityAlarmSettingBindingImpl */
public class d extends c {
    private static final ViewDataBinding.j C = null;
    private static final SparseIntArray D;
    private final LinearLayout A;
    private long B;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        D = sparseIntArray;
        sparseIntArray.put(R$id.rv_alarm, 1);
    }

    public d(f fVar, View view) {
        this(fVar, view, ViewDataBinding.a(fVar, view, 2, C, D));
    }

    /* access modifiers changed from: protected */
    public void a() {
        synchronized (this) {
            this.B = 0;
        }
    }

    /* access modifiers changed from: protected */
    public boolean a(int i2, Object obj, int i3) {
        return false;
    }

    public boolean d() {
        synchronized (this) {
            if (this.B != 0) {
                return true;
            }
            return false;
        }
    }

    public void e() {
        synchronized (this) {
            this.B = 1;
        }
        f();
    }

    private d(f fVar, View view, Object[] objArr) {
        super(fVar, view, 0, objArr[1]);
        this.B = -1;
        LinearLayout linearLayout = objArr[0];
        this.A = linearLayout;
        linearLayout.setTag((Object) null);
        a(view);
        e();
    }
}
