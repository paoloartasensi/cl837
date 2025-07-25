package com.chileaf.fitness.viewmodel;

import aicare.net.cn.iweightlibrary.entity.BM09Data;
import aicare.net.cn.iweightlibrary.entity.BM15Data;
import aicare.net.cn.iweightlibrary.entity.BodyFatData;
import aicare.net.cn.iweightlibrary.entity.BroadData;
import aicare.net.cn.iweightlibrary.entity.DecimalInfo;
import aicare.net.cn.iweightlibrary.entity.User;
import aicare.net.cn.iweightlibrary.entity.WeightData;
import aicare.net.cn.iweightlibrary.wby.WBYService;
import android.app.Application;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.model.a.a;
import kotlin.d;
import kotlin.jvm.internal.g;
import kotlin.jvm.internal.i;

/* compiled from: WeightViewModel.kt */
public final class WeightViewModel extends BaseViewModel {
    private WBYService.a A;
    private final d B = g.a(WeightViewModel$mUser$2.INSTANCE);

    /* renamed from: f  reason: collision with root package name */
    private a<String> f1289f = new a<>();

    /* renamed from: g  reason: collision with root package name */
    private a<String> f1290g = new a<>();

    /* renamed from: h  reason: collision with root package name */
    private a<Integer> f1291h = new a<>();

    /* renamed from: i  reason: collision with root package name */
    private a<Integer> f1292i = new a<>();

    /* renamed from: j  reason: collision with root package name */
    private a<String> f1293j = new a<>();
    private a<String> k = new a<>();
    private a<String> l = new a<>();
    private a<String> m = new a<>();
    private a<String> n = new a<>();
    private a<String> o = new a<>();
    private a<String> p = new a<>();
    private a<String> q = new a<>();
    private a<String> r = new a<>();
    private a<String> s = new a<>();
    private a<String> t = new a<>();
    private a<String> u = new a<>();
    private a<String> v = new a<>();
    private a<String> w = new a<>();
    private a<String> x = new a<>();
    private byte y;
    private String z = com.chileaf.fitness.config.a.a(R$string.unit_kg);

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public WeightViewModel(Application application) {
        super(application);
        i.b(application, "application");
        w();
    }

    private final void a(String str) {
        this.u.a(com.chileaf.fitness.config.a.a(R$string.measure_body_age, str));
    }

    private final void b(String str) {
        this.r.a(com.chileaf.fitness.config.a.a(R$string.measure_bone, str));
    }

    private final void w() {
        this.f1289f.a("18");
        this.f1290g.a("170");
        this.f1291h.a(1);
        this.f1292i.a(1);
        m("0");
        c("0");
        d("0");
        i("0");
        e("0");
        g("0");
        f("0");
        b("0");
        l("0");
        h("0");
        a("0");
        k("0");
        j("0");
    }

    private final User x() {
        return (User) this.B.getValue();
    }

    public final a<String> c() {
        return this.f1289f;
    }

    public final a<String> d() {
        return this.u;
    }

    public final a<String> e() {
        return this.r;
    }

    public final a<String> f() {
        return this.l;
    }

    public final a<String> g() {
        return this.m;
    }

    public final a<String> h() {
        return this.f1290g;
    }

    public final a<String> i() {
        return this.o;
    }

    public final a<String> j() {
        return this.x;
    }

    public final a<String> k() {
        return this.q;
    }

    public final a<String> l() {
        return this.p;
    }

    public final a<String> m() {
        return this.t;
    }

    public final a<String> n() {
        return this.n;
    }

    public final a<Integer> o() {
        return this.f1291h;
    }

    /* access modifiers changed from: protected */
    public void onCleared() {
        WBYService.a aVar = this.A;
        if (aVar != null) {
            aVar.a();
        }
        super.onCleared();
    }

    public final a<String> p() {
        return this.w;
    }

    public final a<String> q() {
        return this.v;
    }

    public final a<String> r() {
        return this.f1293j;
    }

    public final a<Integer> s() {
        return this.f1292i;
    }

    public final a<String> t() {
        return this.s;
    }

    public final a<String> u() {
        return this.k;
    }

    public final void v() {
        Integer value = this.f1291h.getValue();
        if (value != null) {
            User x2 = x();
            i.a((Object) value, "this");
            x2.setSex(value.intValue());
        }
        String value2 = this.f1289f.getValue();
        if (value2 != null) {
            User x3 = x();
            Integer valueOf = Integer.valueOf(value2);
            i.a((Object) valueOf, "Integer.valueOf(this)");
            x3.setAge(valueOf.intValue());
        }
        String value3 = this.f1290g.getValue();
        if (value3 != null) {
            User x4 = x();
            Integer valueOf2 = Integer.valueOf(value3);
            i.a((Object) valueOf2, "Integer.valueOf(this)");
            x4.setHeight(valueOf2.intValue());
        }
        WBYService.a aVar = this.A;
        if (aVar != null) {
            aVar.a(x());
        }
    }

    private final void c(String str) {
        this.l.a(com.chileaf.fitness.config.a.a(R$string.measure_fat, str));
    }

    private final void d(String str) {
        this.m.a(com.chileaf.fitness.config.a.a(R$string.measure_fat_rate, str));
    }

    private final void e(String str) {
        this.o.a(com.chileaf.fitness.config.a.a(R$string.measure_humidity, str));
    }

    private final void f(String str) {
        this.q.a(com.chileaf.fitness.config.a.a(R$string.measure_metabolic, str));
    }

    private final void g(String str) {
        this.p.a(com.chileaf.fitness.config.a.a(R$string.measure_muscle, str));
    }

    private final void h(String str) {
        this.t.a(com.chileaf.fitness.config.a.a(R$string.measure_protein, str));
    }

    private final void i(String str) {
        this.n.a(com.chileaf.fitness.config.a.a(R$string.measure_quality, str));
    }

    private final void j(String str) {
        this.w.a(com.chileaf.fitness.config.a.a(R$string.measure_subcutaneous, str));
    }

    private final void k(String str) {
        this.v.a(com.chileaf.fitness.config.a.a(R$string.measure_temp, str));
    }

    private final void l(String str) {
        this.s.a(com.chileaf.fitness.config.a.a(R$string.measure_viscera, str));
    }

    private final void m(String str) {
        a<String> aVar = this.k;
        aVar.a(com.chileaf.fitness.config.a.a(R$string.measure_weight, str + this.z));
    }

    public final void a(WBYService.a aVar) {
        this.A = aVar;
    }

    public final void b(int i2) {
        if (i2 == 1) {
            this.y = 0;
            this.z = com.chileaf.fitness.config.a.a(R$string.unit_kg);
        } else if (i2 == 2) {
            this.y = 1;
            this.z = com.chileaf.fitness.config.a.a(R$string.unit_lb);
        } else if (i2 == 3) {
            this.y = 2;
            this.z = com.chileaf.fitness.config.a.a(R$string.unit_st);
        } else if (i2 == 4) {
            this.y = 3;
            this.z = com.chileaf.fitness.config.a.a(R$string.unit_jin);
        }
        WBYService.a aVar = this.A;
        if (aVar != null) {
            aVar.a(this.y);
        }
        this.f1292i.a(Integer.valueOf(i2));
    }

    public final void a(int i2) {
        x().setSex(i2);
    }

    public final void c(int i2) {
        if (i2 == 0) {
            j.a.a.b("SettingStatus NORMAL= " + com.chileaf.fitness.config.a.a(R$string.normal), new Object[0]);
        } else if (i2 == 1) {
            this.x.a(com.chileaf.fitness.config.a.a(R$string.low_power));
        } else if (i2 == 2) {
            this.x.a(com.chileaf.fitness.config.a.a(R$string.low_voltage));
        } else if (i2 == 3) {
            this.x.a(com.chileaf.fitness.config.a.a(R$string.error));
        } else if (i2 == 4) {
            this.x.a(com.chileaf.fitness.config.a.a(R$string.time_out));
        } else if (i2 == 6) {
            this.x.a(com.chileaf.fitness.config.a.a(R$string.set_unit_success));
        } else if (i2 == 7) {
            this.x.a(com.chileaf.fitness.config.a.a(R$string.set_unit_failed));
        } else if (i2 == 10) {
            this.x.a(com.chileaf.fitness.config.a.a(R$string.set_user_success));
        } else if (i2 == 11) {
            this.x.a(com.chileaf.fitness.config.a.a(R$string.set_user_failed));
        } else if (i2 == 20) {
            j.a.a.b("SettingStatus ADC_MEASURED_ING= " + com.chileaf.fitness.config.a.a(R$string.adc_measured_ind), new Object[0]);
        } else if (i2 == 21) {
            j.a.a.b("SettingStatus ADC_ERROR= " + com.chileaf.fitness.config.a.a(R$string.adc_error), new Object[0]);
        }
    }

    public final void a(int i2, String str) {
        j.a.a.b("index = " + i2 + "; result = " + str, new Object[0]);
        if (i2 == 0) {
            this.x.a(com.chileaf.fitness.config.a.a(R$string.state_connected));
        } else if (i2 == 1) {
            this.x.a(com.chileaf.fitness.config.a.a(R$string.mcu_date, str));
        } else if (i2 == 2) {
            this.x.a(com.chileaf.fitness.config.a.a(R$string.mcu_time, str));
        } else if (i2 == 3) {
            this.x.a(com.chileaf.fitness.config.a.a(R$string.user_id, str));
        } else if (i2 == 4) {
            this.x.a(com.chileaf.fitness.config.a.a(R$string.adc, str));
        }
    }

    public final void a(BroadData broadData) {
        i.b(broadData, "broadData");
        if (broadData.getDeviceType() == 9) {
            if (broadData.getSpecificData() != null) {
                BM09Data a = f.a.a.a.b.a.a(broadData.getAddress(), broadData.getSpecificData());
                i.a((Object) a, "AicareBleConfig.getBm09D…, broadData.specificData)");
                int i2 = (a.getWeight() > 0.0d ? 1 : (a.getWeight() == 0.0d ? 0 : -1));
            }
        } else if (broadData.getDeviceType() == 15) {
            if (broadData.getSpecificData() != null) {
                BM15Data b = f.a.a.a.b.a.b(broadData.getAddress(), broadData.getSpecificData());
                WeightData weightData = new WeightData();
                i.a((Object) b, "data");
                weightData.setWeight(b.getWeight());
                weightData.setTemp(b.getTemp());
                weightData.setAdc(b.getAdc());
                weightData.setCmdType(b.getAgreementType());
                weightData.setDeviceType(15);
                switch (b.getUnitType()) {
                    case 1:
                    case 2:
                    case 3:
                        weightData.setDecimalInfo(new DecimalInfo(1, 1, 1, 1, 1, 2));
                        break;
                    case 4:
                    case 5:
                    case 6:
                        weightData.setDecimalInfo(new DecimalInfo(2, 1, 1, 1, 1, 2));
                        break;
                }
                a(weightData);
            }
        } else if (broadData.getSpecificData() != null) {
            WeightData s2 = f.a.a.a.b.a.s(broadData.getSpecificData());
            i.a((Object) s2, "weightData");
            a(s2);
        }
    }

    public final void a(WeightData weightData) {
        BodyFatData a;
        i.b(weightData, "weightData");
        String a2 = f.a.a.a.b.a.a(weightData.getWeight(), this.y, weightData.getDecimalInfo());
        i.a((Object) a2, "AicareBleConfig.getWeigh…, weightData.decimalInfo)");
        m(a2);
        if (weightData.getTemp() != g.b.a()) {
            k(com.chileaf.fitness.config.a.a(R$string.single_decimal, String.valueOf(weightData.getTemp())));
        }
        if (weightData.getDeviceType() == 15 && weightData.getCmdType() == 3 && weightData.getAdc() > 0 && (a = f.a.a.a.b.a.a(weightData, x().getSex(), x().getAge(), x().getHeight())) != null) {
            a(a);
        }
    }

    public final void a(BodyFatData bodyFatData) {
        i.b(bodyFatData, "bodyFatData");
        a<String> aVar = this.f1293j;
        aVar.a(com.chileaf.fitness.config.a.a(R$string.measure_time, bodyFatData.getDate() + bodyFatData.getTime()));
        c(com.chileaf.fitness.config.a.a(R$string.single_decimal, Double.valueOf(bodyFatData.getBfr())));
        d(com.chileaf.fitness.config.a.a(R$string.single_decimal, Double.valueOf(bodyFatData.getWeight())));
        i(com.chileaf.fitness.config.a.a(R$string.single_decimal, Double.valueOf(bodyFatData.getBmi())));
        e(com.chileaf.fitness.config.a.a(R$string.single_decimal, Double.valueOf(bodyFatData.getVwc())));
        g(com.chileaf.fitness.config.a.a(R$string.single_decimal, Double.valueOf(bodyFatData.getRom())));
        f(com.chileaf.fitness.config.a.a(R$string.single_decimal, Double.valueOf(bodyFatData.getBmr())));
        b(com.chileaf.fitness.config.a.a(R$string.single_decimal, Double.valueOf(bodyFatData.getBm())));
        l(String.valueOf(bodyFatData.getUvi()));
        h(com.chileaf.fitness.config.a.a(R$string.single_decimal, Double.valueOf(bodyFatData.getPp())));
        a(String.valueOf(bodyFatData.getBodyAge()));
        j(com.chileaf.fitness.config.a.a(R$string.single_decimal, Double.valueOf(bodyFatData.getSfr())));
    }
}
