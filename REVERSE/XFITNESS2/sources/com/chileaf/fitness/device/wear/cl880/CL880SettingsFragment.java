package com.chileaf.fitness.device.wear.cl880;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.s0;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.device.wear.cl880.model.MessageConfig;
import com.chileaf.fitness.widget.SwitchButton;
import java.util.HashMap;

/* compiled from: CL880SettingsFragment.kt */
public final class CL880SettingsFragment extends com.chileaf.fitness.base.a<s0> {
    private final kotlin.d f0 = g.a(CL880SettingsFragment$mManager$2.INSTANCE);
    private HashMap g0;

    /* compiled from: CL880SettingsFragment.kt */
    static final class a implements View.OnClickListener {
        final /* synthetic */ SwitchButton e;

        a(SwitchButton switchButton) {
            this.e = switchButton;
        }

        public final void onClick(View view) {
            this.e.toggle();
        }
    }

    /* compiled from: CL880SettingsFragment.kt */
    static final class b implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ CL880SettingsFragment a;
        final /* synthetic */ MessageConfig b;

        b(CL880SettingsFragment cL880SettingsFragment, MessageConfig messageConfig) {
            this.a = cL880SettingsFragment;
            this.b = messageConfig;
        }

        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            MessageConfig messageConfig = this.b;
            messageConfig.other = z;
            CL880SettingsFragment cL880SettingsFragment = this.a;
            kotlin.jvm.internal.i.a((Object) messageConfig, "message");
            cL880SettingsFragment.a(messageConfig);
        }
    }

    /* compiled from: CL880SettingsFragment.kt */
    static final class c implements View.OnClickListener {
        final /* synthetic */ CL880SettingsFragment e;

        c(CL880SettingsFragment cL880SettingsFragment, MessageConfig messageConfig) {
            this.e = cL880SettingsFragment;
        }

        public final void onClick(View view) {
            this.e.t0();
        }
    }

    /* compiled from: CL880SettingsFragment.kt */
    static final class d implements View.OnClickListener {
        final /* synthetic */ CL880SettingsFragment e;

        d(CL880SettingsFragment cL880SettingsFragment, MessageConfig messageConfig) {
            this.e = cL880SettingsFragment;
        }

        public final void onClick(View view) {
            BaseActivity a = this.e.o0();
            String a2 = this.e.a((int) R$string.health_remind);
            kotlin.jvm.internal.i.a((Object) a2, "getString(R.string.health_remind)");
            a.a(a2, "setting_health_remind");
        }
    }

    /* compiled from: CL880SettingsFragment.kt */
    static final class e implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ CL880SettingsFragment a;
        final /* synthetic */ MessageConfig b;

        e(CL880SettingsFragment cL880SettingsFragment, MessageConfig messageConfig) {
            this.a = cL880SettingsFragment;
            this.b = messageConfig;
        }

        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            MessageConfig messageConfig = this.b;
            messageConfig.facebook = z;
            CL880SettingsFragment cL880SettingsFragment = this.a;
            kotlin.jvm.internal.i.a((Object) messageConfig, "message");
            cL880SettingsFragment.a(messageConfig);
        }
    }

    /* compiled from: CL880SettingsFragment.kt */
    static final class f implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ CL880SettingsFragment a;
        final /* synthetic */ MessageConfig b;

        f(CL880SettingsFragment cL880SettingsFragment, MessageConfig messageConfig) {
            this.a = cL880SettingsFragment;
            this.b = messageConfig;
        }

        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            MessageConfig messageConfig = this.b;
            messageConfig.whatsApp = z;
            CL880SettingsFragment cL880SettingsFragment = this.a;
            kotlin.jvm.internal.i.a((Object) messageConfig, "message");
            cL880SettingsFragment.a(messageConfig);
        }
    }

    /* compiled from: CL880SettingsFragment.kt */
    static final class g implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ CL880SettingsFragment a;
        final /* synthetic */ MessageConfig b;

        g(CL880SettingsFragment cL880SettingsFragment, MessageConfig messageConfig) {
            this.a = cL880SettingsFragment;
            this.b = messageConfig;
        }

        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            MessageConfig messageConfig = this.b;
            messageConfig.skype = z;
            CL880SettingsFragment cL880SettingsFragment = this.a;
            kotlin.jvm.internal.i.a((Object) messageConfig, "message");
            cL880SettingsFragment.a(messageConfig);
        }
    }

    /* compiled from: CL880SettingsFragment.kt */
    static final class h implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ CL880SettingsFragment a;
        final /* synthetic */ MessageConfig b;

        h(CL880SettingsFragment cL880SettingsFragment, MessageConfig messageConfig) {
            this.a = cL880SettingsFragment;
            this.b = messageConfig;
        }

        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            MessageConfig messageConfig = this.b;
            messageConfig.qq = z;
            CL880SettingsFragment cL880SettingsFragment = this.a;
            kotlin.jvm.internal.i.a((Object) messageConfig, "message");
            cL880SettingsFragment.a(messageConfig);
        }
    }

    /* compiled from: CL880SettingsFragment.kt */
    static final class i implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ CL880SettingsFragment a;
        final /* synthetic */ MessageConfig b;

        i(CL880SettingsFragment cL880SettingsFragment, MessageConfig messageConfig) {
            this.a = cL880SettingsFragment;
            this.b = messageConfig;
        }

        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            MessageConfig messageConfig = this.b;
            messageConfig.wechat = z;
            CL880SettingsFragment cL880SettingsFragment = this.a;
            kotlin.jvm.internal.i.a((Object) messageConfig, "message");
            cL880SettingsFragment.a(messageConfig);
        }
    }

    /* compiled from: CL880SettingsFragment.kt */
    static final class j implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ CL880SettingsFragment a;
        final /* synthetic */ MessageConfig b;

        j(CL880SettingsFragment cL880SettingsFragment, MessageConfig messageConfig) {
            this.a = cL880SettingsFragment;
            this.b = messageConfig;
        }

        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            MessageConfig messageConfig = this.b;
            messageConfig.sms = z;
            CL880SettingsFragment cL880SettingsFragment = this.a;
            kotlin.jvm.internal.i.a((Object) messageConfig, "message");
            cL880SettingsFragment.a(messageConfig);
        }
    }

    /* compiled from: CL880SettingsFragment.kt */
    static final class k implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ CL880SettingsFragment a;
        final /* synthetic */ MessageConfig b;

        k(CL880SettingsFragment cL880SettingsFragment, MessageConfig messageConfig) {
            this.a = cL880SettingsFragment;
            this.b = messageConfig;
        }

        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            MessageConfig messageConfig = this.b;
            messageConfig.missedCall = z;
            CL880SettingsFragment cL880SettingsFragment = this.a;
            kotlin.jvm.internal.i.a((Object) messageConfig, "message");
            cL880SettingsFragment.a(messageConfig);
        }
    }

    private final CL880Manager s0() {
        return (CL880Manager) this.f0.getValue();
    }

    /* JADX WARNING: type inference failed for: r0v7, types: [kotlin.l] */
    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:8:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void t0() {
        /*
            r4 = this;
            r0 = 268435456(0x10000000, float:2.5243549E-29)
            android.content.Intent r1 = new android.content.Intent     // Catch:{ ActivityNotFoundException -> 0x0012 }
            java.lang.String r2 = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
            r1.<init>(r2)     // Catch:{ ActivityNotFoundException -> 0x0012 }
            r1.addFlags(r0)     // Catch:{ ActivityNotFoundException -> 0x0012 }
            r4.a((android.content.Intent) r1)     // Catch:{ ActivityNotFoundException -> 0x0012 }
            kotlin.l r0 = kotlin.l.a     // Catch:{ ActivityNotFoundException -> 0x0012 }
            goto L_0x0039
        L_0x0012:
            android.content.Intent r1 = new android.content.Intent     // Catch:{ Exception -> 0x0033 }
            r1.<init>()     // Catch:{ Exception -> 0x0033 }
            r1.addFlags(r0)     // Catch:{ Exception -> 0x0033 }
            android.content.ComponentName r0 = new android.content.ComponentName     // Catch:{ Exception -> 0x0033 }
            java.lang.String r2 = "com.android.settings"
            java.lang.String r3 = "com.android.settings.Settings$NotificationAccessSettingsActivity"
            r0.<init>(r2, r3)     // Catch:{ Exception -> 0x0033 }
            r1.setComponent(r0)     // Catch:{ Exception -> 0x0033 }
            java.lang.String r0 = ":settings:show_fragment"
            java.lang.String r2 = "NotificationAccessSettings"
            r1.putExtra(r0, r2)     // Catch:{ Exception -> 0x0033 }
            r4.a((android.content.Intent) r1)     // Catch:{ Exception -> 0x0033 }
            kotlin.l r0 = kotlin.l.a     // Catch:{ Exception -> 0x0033 }
            goto L_0x0039
        L_0x0033:
            r0 = move-exception
            r0.printStackTrace()
            kotlin.l r0 = kotlin.l.a
        L_0x0039:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.chileaf.fitness.device.wear.cl880.CL880SettingsFragment.t0():void");
    }

    private final boolean u0() {
        String packageName = o0().getPackageName();
        kotlin.jvm.internal.i.a((Object) packageName, "mActivity.packageName");
        String string = Settings.Secure.getString(o0().getContentResolver(), "enabled_notification_listeners");
        kotlin.jvm.internal.i.a((Object) string, "Settings.Secure.getStrin…_notification_listeners\")");
        return m.a((CharSequence) string, (CharSequence) packageName, false, 2, (Object) null);
    }

    public /* synthetic */ void T() {
        super.T();
        n0();
    }

    public void W() {
        super.W();
        TextView textView = ((s0) p0()).R;
        kotlin.jvm.internal.i.a((Object) textView, "mBinding.tvNotifyStatus");
        textView.setText(a(u0() ? R$string.is_enable : R$string.is_disable));
    }

    public View e(int i2) {
        if (this.g0 == null) {
            this.g0 = new HashMap();
        }
        View view = (View) this.g0.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View F = F();
        if (F == null) {
            return null;
        }
        View findViewById = F.findViewById(i2);
        this.g0.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public void n(Bundle bundle) {
        com.chileaf.fitness.device.wear.cl880.external.c l = com.chileaf.fitness.device.wear.cl880.external.c.l();
        kotlin.jvm.internal.i.a((Object) l, "SpecManager.getInstance()");
        MessageConfig d2 = l.d();
        s0 s0Var = (s0) p0();
        s0Var.C.setOnClickListener(new c(this, d2));
        s0Var.A.setOnClickListener(new d(this, d2));
        SwitchButton switchButton = s0Var.J;
        kotlin.jvm.internal.i.a((Object) switchButton, "swFacebook");
        boolean z = d2.facebook;
        ConstraintLayout constraintLayout = s0Var.z;
        kotlin.jvm.internal.i.a((Object) constraintLayout, "constraintFacebook");
        a(switchButton, z, constraintLayout);
        s0Var.J.setOnCheckedChangeListener(new e(this, d2));
        SwitchButton switchButton2 = s0Var.Q;
        kotlin.jvm.internal.i.a((Object) switchButton2, "swWhats");
        boolean z2 = d2.whatsApp;
        ConstraintLayout constraintLayout2 = s0Var.I;
        kotlin.jvm.internal.i.a((Object) constraintLayout2, "constraintWhats");
        a(switchButton2, z2, constraintLayout2);
        s0Var.Q.setOnCheckedChangeListener(new f(this, d2));
        SwitchButton switchButton3 = s0Var.N;
        kotlin.jvm.internal.i.a((Object) switchButton3, "swSkype");
        boolean z3 = d2.skype;
        ConstraintLayout constraintLayout3 = s0Var.F;
        kotlin.jvm.internal.i.a((Object) constraintLayout3, "constraintSkype");
        a(switchButton3, z3, constraintLayout3);
        s0Var.N.setOnCheckedChangeListener(new g(this, d2));
        SwitchButton switchButton4 = s0Var.M;
        kotlin.jvm.internal.i.a((Object) switchButton4, "swQq");
        boolean z4 = d2.qq;
        ConstraintLayout constraintLayout4 = s0Var.E;
        kotlin.jvm.internal.i.a((Object) constraintLayout4, "constraintQq");
        a(switchButton4, z4, constraintLayout4);
        s0Var.M.setOnCheckedChangeListener(new h(this, d2));
        SwitchButton switchButton5 = s0Var.P;
        kotlin.jvm.internal.i.a((Object) switchButton5, "swWechat");
        boolean z5 = d2.wechat;
        ConstraintLayout constraintLayout5 = s0Var.H;
        kotlin.jvm.internal.i.a((Object) constraintLayout5, "constraintWechat");
        a(switchButton5, z5, constraintLayout5);
        s0Var.P.setOnCheckedChangeListener(new i(this, d2));
        SwitchButton switchButton6 = s0Var.O;
        kotlin.jvm.internal.i.a((Object) switchButton6, "swSms");
        boolean z6 = d2.sms;
        ConstraintLayout constraintLayout6 = s0Var.G;
        kotlin.jvm.internal.i.a((Object) constraintLayout6, "constraintSms");
        a(switchButton6, z6, constraintLayout6);
        s0Var.O.setOnCheckedChangeListener(new j(this, d2));
        SwitchButton switchButton7 = s0Var.K;
        kotlin.jvm.internal.i.a((Object) switchButton7, "swMissCall");
        boolean z7 = d2.missedCall;
        ConstraintLayout constraintLayout7 = s0Var.B;
        kotlin.jvm.internal.i.a((Object) constraintLayout7, "constraintMissCall");
        a(switchButton7, z7, constraintLayout7);
        s0Var.K.setOnCheckedChangeListener(new k(this, d2));
        SwitchButton switchButton8 = s0Var.L;
        kotlin.jvm.internal.i.a((Object) switchButton8, "swOther");
        boolean z8 = d2.other;
        ConstraintLayout constraintLayout8 = s0Var.D;
        kotlin.jvm.internal.i.a((Object) constraintLayout8, "constraintOther");
        a(switchButton8, z8, constraintLayout8);
        s0Var.L.setOnCheckedChangeListener(new b(this, d2));
    }

    public void n0() {
        HashMap hashMap = this.g0;
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
        return R$layout.fragment_cl880_setting;
    }

    private final void a(SwitchButton switchButton, boolean z, ConstraintLayout constraintLayout) {
        switchButton.setChecked(z);
        constraintLayout.setOnClickListener(new a(switchButton));
    }

    /* access modifiers changed from: private */
    public final void a(MessageConfig messageConfig) {
        s0().a(messageConfig);
        com.chileaf.fitness.device.wear.cl880.external.c l = com.chileaf.fitness.device.wear.cl880.external.c.l();
        kotlin.jvm.internal.i.a((Object) l, "SpecManager.getInstance()");
        l.a(messageConfig);
    }
}
