package com.chileaf.fitness.ui.activity;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.p;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.b.w;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.device.wear.cl880.CL880SettingsFragment;
import com.chileaf.fitness.device.wear.cl880.HealthSettingsFragment;
import com.chileaf.fitness.device.wear.cl880.SleepSettingsFragment;
import com.chileaf.fitness.device.wear.cl880.TrainSettingsFragment;
import java.util.HashMap;
import kotlin.d;
import kotlin.jvm.internal.i;

/* compiled from: DeviceSettingsActivity.kt */
public final class DeviceSettingsActivity extends BaseActivity<w> {
    private Fragment E;
    private final d F = g.a(new DeviceSettingsActivity$mTitle$2(this));
    private final d G = g.a(new DeviceSettingsActivity$mSetting$2(this));
    private HashMap H;

    /* compiled from: DeviceSettingsActivity.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    static {
        new a((f) null);
    }

    private final String q() {
        return (String) this.G.getValue();
    }

    private final String r() {
        return (String) this.F.getValue();
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        a(r());
        String q = q();
        if (q != null) {
            switch (q.hashCode()) {
                case -121890940:
                    if (q.equals("setting_csc")) {
                        this.E = new com.chileaf.fitness.device.cdn.d();
                        break;
                    }
                    break;
                case 696061452:
                    if (q.equals("setting_message_remind")) {
                        this.E = new CL880SettingsFragment();
                        break;
                    }
                    break;
                case 1030446905:
                    if (q.equals("setting_sleep_config")) {
                        this.E = new SleepSettingsFragment();
                        break;
                    }
                    break;
                case 1923273448:
                    if (q.equals("setting_train_config")) {
                        this.E = new TrainSettingsFragment();
                        break;
                    }
                    break;
                case 2113491097:
                    if (q.equals("setting_health_remind")) {
                        this.E = new HealthSettingsFragment();
                        break;
                    }
                    break;
            }
        }
        p b = e().b();
        Fragment fragment = this.E;
        if (fragment != null) {
            b.a(2131296408, fragment);
            b.a();
            return;
        }
        i.d("mFragment");
        throw null;
    }

    public View d(int i2) {
        if (this.H == null) {
            this.H = new HashMap();
        }
        View view = (View) this.H.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i2);
        this.H.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public BaseViewModel o() {
        return null;
    }

    /* access modifiers changed from: protected */
    public int p() {
        return R$layout.activity_device_settings;
    }
}
