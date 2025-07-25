package com.chileaf.fitness.b;

import android.util.SparseIntArray;
import android.view.View;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.f;
import com.chileaf.fitness.R$id;

/* compiled from: FragmentCl880SettingBindingImpl */
public class t0 extends s0 {
    private static final ViewDataBinding.j U = null;
    private static final SparseIntArray V;
    private final NestedScrollView S;
    private long T;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        V = sparseIntArray;
        sparseIntArray.put(R$id.constraint_notify_usage, 1);
        V.put(R$id.tv_notify_status, 2);
        V.put(R$id.tv_setting_notify, 3);
        V.put(R$id.constraint_health_remind, 4);
        V.put(R$id.iv_notify_arrow, 5);
        V.put(R$id.constraint_facebook, 6);
        V.put(R$id.sw_facebook, 7);
        V.put(R$id.constraint_whats, 8);
        V.put(R$id.sw_whats, 9);
        V.put(R$id.constraint_skype, 10);
        V.put(R$id.sw_skype, 11);
        V.put(R$id.constraint_qq, 12);
        V.put(R$id.sw_qq, 13);
        V.put(R$id.constraint_wechat, 14);
        V.put(R$id.sw_wechat, 15);
        V.put(R$id.constraint_sms, 16);
        V.put(R$id.sw_sms, 17);
        V.put(R$id.constraint_miss_call, 18);
        V.put(R$id.sw_miss_call, 19);
        V.put(R$id.constraint_other, 20);
        V.put(R$id.sw_other, 21);
    }

    public t0(f fVar, View view) {
        this(fVar, view, ViewDataBinding.a(fVar, view, 22, U, V));
    }

    /* access modifiers changed from: protected */
    public void a() {
        synchronized (this) {
            this.T = 0;
        }
    }

    /* access modifiers changed from: protected */
    public boolean a(int i2, Object obj, int i3) {
        return false;
    }

    public boolean d() {
        synchronized (this) {
            if (this.T != 0) {
                return true;
            }
            return false;
        }
    }

    public void e() {
        synchronized (this) {
            this.T = 1;
        }
        f();
    }

    private t0(f fVar, View view, Object[] objArr) {
        super(fVar, view, 0, objArr[6], objArr[4], objArr[18], objArr[1], objArr[20], objArr[12], objArr[10], objArr[16], objArr[14], objArr[8], objArr[5], objArr[7], objArr[19], objArr[21], objArr[13], objArr[11], objArr[17], objArr[15], objArr[9], objArr[2], objArr[3]);
        this.T = -1;
        NestedScrollView nestedScrollView = objArr[0];
        this.S = nestedScrollView;
        nestedScrollView.setTag((Object) null);
        a(view);
        e();
    }
}
