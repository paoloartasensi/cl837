package com.chileaf.fitness.ui.fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.c1;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.base.BaseViewModel;
import java.lang.reflect.Method;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.i;

/* compiled from: SettingFragment.kt */
public final class SettingFragment extends com.chileaf.fitness.base.a<c1> {
    private HashMap f0;

    /* compiled from: SettingFragment.kt */
    static final class a implements View.OnClickListener {
        final /* synthetic */ SettingFragment e;

        a(SettingFragment settingFragment) {
            this.e = settingFragment;
        }

        public final void onClick(View view) {
            BaseActivity.a(this.e.o0(), (CharSequence) this.e.a((int) R$string.latest_version), 0, 2, (Object) null);
        }
    }

    /* compiled from: SettingFragment.kt */
    static final class b implements View.OnClickListener {
        final /* synthetic */ SettingFragment e;

        b(SettingFragment settingFragment) {
            this.e = settingFragment;
        }

        public final void onClick(View view) {
            this.e.s0();
        }
    }

    /* access modifiers changed from: private */
    public final void s0() {
        int i2;
        if (com.chileaf.fitness.d.b.b.a()) {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null) {
                i2 = 0;
                for (BluetoothDevice next : defaultAdapter.getBondedDevices()) {
                    i.a((Object) next, "bluetoothDevice");
                    if (a(next)) {
                        i2++;
                    }
                }
            } else {
                i2 = 0;
            }
            BaseActivity<?> o0 = o0();
            BaseActivity.a((BaseActivity) o0, (CharSequence) a((int) R$string.clear_bonded_device) + ':' + i2, 0, 2, (Object) null);
            return;
        }
        com.chileaf.fitness.d.b.b.c(o0());
    }

    public /* synthetic */ void T() {
        super.T();
        n0();
    }

    public View e(int i2) {
        if (this.f0 == null) {
            this.f0 = new HashMap();
        }
        View view = (View) this.f0.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View F = F();
        if (F == null) {
            return null;
        }
        View findViewById = F.findViewById(i2);
        this.f0.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public void n(Bundle bundle) {
        c1 c1Var = (c1) p0();
        c1Var.z.setOnClickListener(new SettingFragment$initData$$inlined$with$lambda$1(this));
        c1Var.B.setOnClickListener(new a(this));
        c1Var.A.setOnClickListener(new b(this));
        ConstraintLayout constraintLayout = c1Var.A;
        i.a((Object) constraintLayout, "constraintUnBond");
        constraintLayout.setVisibility(8);
    }

    public void n0() {
        HashMap hashMap = this.f0;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    /* access modifiers changed from: protected */
    public BaseViewModel q0() {
        return null;
    }

    /* access modifiers changed from: protected */
    public int r0() {
        return R$layout.fragment_setting;
    }

    private final boolean a(BluetoothDevice bluetoothDevice) {
        try {
            Method method = bluetoothDevice.getClass().getMethod("removeBond", new Class[0]);
            i.a((Object) method, "device.javaClass.getMethod(\"removeBond\")");
            j.a.a.a("device.removeBond() (hidden)", new Object[0]);
            Object invoke = method.invoke(bluetoothDevice, new Object[0]);
            if (invoke != null) {
                return ((Boolean) invoke).booleanValue();
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Boolean");
        } catch (Exception unused) {
            j.a.a.a("An exception occurred while removing bond", new Object[0]);
            return false;
        }
    }
}
