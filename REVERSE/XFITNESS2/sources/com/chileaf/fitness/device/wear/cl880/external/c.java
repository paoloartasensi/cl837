package com.chileaf.fitness.device.wear.cl880.external;

import android.content.Context;
import android.content.SharedPreferences;
import com.chileaf.fitness.device.wear.cl880.CL880Manager;
import com.chileaf.fitness.device.wear.cl880.model.AlarmConfig;
import com.chileaf.fitness.device.wear.cl880.model.DrinkConfig;
import com.chileaf.fitness.device.wear.cl880.model.InactivityConfig;
import com.chileaf.fitness.device.wear.cl880.model.MessageConfig;
import com.chileaf.fitness.device.wear.cl880.model.SleepConfig;
import com.chileaf.fitness.device.wear.cl880.model.SleepHistory;
import com.chileaf.fitness.device.wear.cl880.model.SportDayHistory;
import com.chileaf.fitness.device.wear.cl880.model.SportHistory;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/* compiled from: SpecManager */
public class c {
    private com.google.gson.d a;
    private Context b;
    private CL880Manager c;
    private SharedPreferences d;

    /* compiled from: SpecManager */
    class a extends com.google.gson.r.a<List<AlarmConfig>> {
        a(c cVar) {
        }
    }

    /* compiled from: SpecManager */
    class b extends com.google.gson.r.a<List<SportHistory>> {
        b(c cVar) {
        }
    }

    /* renamed from: com.chileaf.fitness.device.wear.cl880.external.c$c  reason: collision with other inner class name */
    /* compiled from: SpecManager */
    class C0065c extends com.google.gson.r.a<List<SleepHistory>> {
        C0065c(c cVar) {
        }
    }

    /* compiled from: SpecManager */
    class d extends com.google.gson.r.a<List<SportDayHistory>> {
        d(c cVar) {
        }
    }

    /* compiled from: SpecManager */
    private static class e {
        /* access modifiers changed from: private */
        public static final c a = new c((a) null);
    }

    /* synthetic */ c(a aVar) {
        this();
    }

    private void b(String str, String str2) {
        m().edit().putString(str, str2).apply();
    }

    private com.google.gson.d k() {
        if (this.a == null) {
            this.a = new com.google.gson.d();
        }
        return this.a;
    }

    public static c l() {
        return e.a;
    }

    private SharedPreferences m() {
        if (this.d == null) {
            this.d = this.b.getSharedPreferences("spec_prefs", 0);
        }
        return this.d;
    }

    public void a(Context context) {
        this.b = context;
    }

    public InactivityConfig c() {
        InactivityConfig inactivityConfig = (InactivityConfig) a("spec_inactivity", InactivityConfig.class);
        return inactivityConfig == null ? new InactivityConfig() : inactivityConfig;
    }

    public MessageConfig d() {
        MessageConfig messageConfig = (MessageConfig) a("spec_message", MessageConfig.class);
        return messageConfig == null ? new MessageConfig() : messageConfig;
    }

    public SleepConfig e() {
        SleepConfig sleepConfig = (SleepConfig) a("spec_sleep", SleepConfig.class);
        return sleepConfig == null ? new SleepConfig() : sleepConfig;
    }

    public List<SleepHistory> f() {
        List<SleepHistory> list = (List) a("sleep_history", new C0065c(this).b());
        return list == null ? Collections.emptyList() : list;
    }

    public List<SportDayHistory> g() {
        List<SportDayHistory> list = (List) a("sport_day_history", new d(this).b());
        return list == null ? Collections.emptyList() : list;
    }

    public List<SportHistory> h() {
        List<SportHistory> list = (List) a("sport_history", new b(this).b());
        return list == null ? Collections.emptyList() : list;
    }

    public boolean i() {
        return m().getBoolean("spec_sport", true);
    }

    public void j() {
        m().edit().clear().apply();
    }

    private c() {
    }

    public void a(CL880Manager cL880Manager) {
        this.c = cL880Manager;
    }

    public DrinkConfig b() {
        DrinkConfig drinkConfig = (DrinkConfig) a("spec_drink", DrinkConfig.class);
        return drinkConfig == null ? new DrinkConfig() : drinkConfig;
    }

    private String a(String str, String str2) {
        return m().getString(str, str2);
    }

    public void c(List<SportDayHistory> list) {
        a("sport_day_history", (Object) list);
    }

    public void d(List<SportHistory> list) {
        a("sport_history", (Object) list);
    }

    private void a(String str, Object obj) {
        b(str, k().a(obj));
    }

    public void b(List<SleepHistory> list) {
        a("sleep_history", (Object) list);
    }

    private <T> T a(String str, Class<T> cls) {
        return k().a(a(str, (String) null), cls);
    }

    private <T> T a(String str, Type type) {
        return k().a(a(str, (String) null), type);
    }

    public void a(SleepConfig sleepConfig) {
        a("spec_sleep", (Object) sleepConfig);
    }

    public void a(MessageConfig messageConfig) {
        a("spec_message", (Object) messageConfig);
    }

    public void a(InactivityConfig inactivityConfig) {
        a("spec_inactivity", (Object) inactivityConfig);
    }

    public void a(DrinkConfig drinkConfig) {
        a("spec_drink", (Object) drinkConfig);
    }

    public void a(List<AlarmConfig> list) {
        a("spec_alarm", (Object) list);
    }

    public List<AlarmConfig> a() {
        List<AlarmConfig> list = (List) a("spec_alarm", new a(this).b());
        return list == null ? Collections.emptyList() : list;
    }

    public void a(b bVar) {
        CL880Manager cL880Manager = this.c;
        if (cL880Manager != null) {
            cL880Manager.a(bVar);
        }
    }

    public void a(a aVar) {
        CL880Manager cL880Manager = this.c;
        if (cL880Manager != null) {
            cL880Manager.a(aVar);
        }
    }
}
