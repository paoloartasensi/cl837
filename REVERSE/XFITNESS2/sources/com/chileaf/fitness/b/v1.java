package com.chileaf.fitness.b;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.f;
import com.chileaf.fitness.R$id;

/* compiled from: ItemDeviceBindingImpl */
public class v1 extends u1 {
    private static final ViewDataBinding.j G = null;
    private static final SparseIntArray H;
    private final RelativeLayout E;
    private long F;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        H = sparseIntArray;
        sparseIntArray.put(2131296456, 1);
        H.put(R$id.device_name, 2);
        H.put(R$id.device_address, 3);
        H.put(R$id.device_bonded, 4);
        H.put(R$id.device_signal, 5);
        H.put(R$id.device_rssi, 6);
    }

    public v1(f fVar, View view) {
        this(fVar, view, ViewDataBinding.a(fVar, view, 7, G, H));
    }

    /* access modifiers changed from: protected */
    public void a() {
        synchronized (this) {
            this.F = 0;
        }
    }

    /* access modifiers changed from: protected */
    public boolean a(int i2, Object obj, int i3) {
        return false;
    }

    public boolean d() {
        synchronized (this) {
            if (this.F != 0) {
                return true;
            }
            return false;
        }
    }

    public void e() {
        synchronized (this) {
            this.F = 1;
        }
        f();
    }

    private v1(f fVar, View view, Object[] objArr) {
        super(fVar, view, 0, objArr[3], objArr[4], objArr[2], objArr[6], objArr[5], objArr[1]);
        this.F = -1;
        RelativeLayout relativeLayout = objArr[0];
        this.E = relativeLayout;
        relativeLayout.setTag((Object) null);
        a(view);
        e();
    }
}
