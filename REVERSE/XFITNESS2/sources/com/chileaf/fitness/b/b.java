package com.chileaf.fitness.b;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.f;
import com.chileaf.fitness.R$id;

/* compiled from: ActivityAlarmEditBindingImpl */
public class b extends a {
    private static final ViewDataBinding.j N = null;
    private static final SparseIntArray O;
    private final LinearLayout L;
    private long M;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        O = sparseIntArray;
        sparseIntArray.put(2131296583, 1);
        O.put(R$id.alarm_name, 2);
        O.put(R$id.alarm_time_picker, 3);
        O.put(R$id.dowSelector, 4);
        O.put(R$id.alarm_cb_monday, 5);
        O.put(R$id.alarm_cb_tuesday, 6);
        O.put(R$id.alarm_cb_wednesday, 7);
        O.put(R$id.alarm_cb_thursday, 8);
        O.put(R$id.alarm_cb_friday, 9);
        O.put(R$id.alarm_cb_saturday, 10);
        O.put(R$id.alarm_cb_sunday, 11);
        O.put(R$id.alarm_single, 12);
        O.put(R$id.alarm_single_date, 13);
        O.put(R$id.alarm_single_time, 14);
    }

    public b(f fVar, View view) {
        this(fVar, view, ViewDataBinding.a(fVar, view, 15, N, O));
    }

    /* access modifiers changed from: protected */
    public void a() {
        synchronized (this) {
            this.M = 0;
        }
    }

    /* access modifiers changed from: protected */
    public boolean a(int i2, Object obj, int i3) {
        return false;
    }

    public boolean d() {
        synchronized (this) {
            if (this.M != 0) {
                return true;
            }
            return false;
        }
    }

    public void e() {
        synchronized (this) {
            this.M = 1;
        }
        f();
    }

    private b(f fVar, View view, Object[] objArr) {
        super(fVar, view, 0, objArr[9], objArr[5], objArr[10], objArr[11], objArr[8], objArr[6], objArr[7], objArr[2], objArr[12], objArr[13], objArr[14], objArr[3], objArr[4], objArr[1]);
        this.M = -1;
        LinearLayout linearLayout = objArr[0];
        this.L = linearLayout;
        linearLayout.setTag((Object) null);
        a(view);
        e();
    }
}
