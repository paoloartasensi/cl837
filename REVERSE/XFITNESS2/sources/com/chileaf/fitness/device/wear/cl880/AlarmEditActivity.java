package com.chileaf.fitness.device.wear.cl880;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.appcompat.widget.AppCompatEditText;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.device.wear.cl880.model.AlarmConfig;
import java.util.Calendar;
import java.util.HashMap;

/* compiled from: AlarmEditActivity.kt */
public final class AlarmEditActivity extends BaseActivity<com.chileaf.fitness.b.a> {
    private final kotlin.d E = g.a(new AlarmEditActivity$mAlarm$2(this));
    /* access modifiers changed from: private */
    public int F;
    /* access modifiers changed from: private */
    public int G;
    /* access modifiers changed from: private */
    public int H;
    /* access modifiers changed from: private */
    public int I;
    /* access modifiers changed from: private */
    public int J;
    private HashMap K;

    /* compiled from: AlarmEditActivity.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    /* compiled from: AlarmEditActivity.kt */
    static final class b implements View.OnClickListener {
        final /* synthetic */ AlarmEditActivity e;

        /* compiled from: AlarmEditActivity.kt */
        static final class a implements DatePickerDialog.OnDateSetListener {
            final /* synthetic */ b a;

            a(b bVar) {
                this.a = bVar;
            }

            public final void onDateSet(DatePicker datePicker, int i2, int i3, int i4) {
                this.a.e.F = i2;
                this.a.e.G = i3;
                this.a.e.H = i4;
                this.a.e.s();
            }
        }

        b(AlarmEditActivity alarmEditActivity) {
            this.e = alarmEditActivity;
        }

        public final void onClick(View view) {
            new DatePickerDialog(this.e, new a(this), this.e.F, this.e.G, this.e.H).show();
        }
    }

    /* compiled from: AlarmEditActivity.kt */
    static final class c implements View.OnClickListener {
        final /* synthetic */ AlarmEditActivity e;

        /* compiled from: AlarmEditActivity.kt */
        static final class a implements TimePickerDialog.OnTimeSetListener {
            final /* synthetic */ c a;

            a(c cVar) {
                this.a = cVar;
            }

            public final void onTimeSet(TimePicker timePicker, int i2, int i3) {
                AlarmEditActivity alarmEditActivity = this.a.e;
                alarmEditActivity.a((CharSequence) "Hour:" + i2 + " Minute:" + i3, 0);
                this.a.e.I = i2;
                this.a.e.J = i3;
                this.a.e.s();
            }
        }

        c(AlarmEditActivity alarmEditActivity) {
            this.e = alarmEditActivity;
        }

        public final void onClick(View view) {
            new TimePickerDialog(this.e, new a(this), this.e.I, this.e.J, true).show();
        }
    }

    /* compiled from: AlarmEditActivity.kt */
    static final class d implements View.OnClickListener {
        final /* synthetic */ AlarmEditActivity e;

        d(AlarmEditActivity alarmEditActivity) {
            this.e = alarmEditActivity;
        }

        public final void onClick(View view) {
            this.e.r();
        }
    }

    /* compiled from: AlarmEditActivity.kt */
    static final class e implements View.OnClickListener {
        public static final e e = new e();

        e() {
        }

        public final void onClick(View view) {
            kotlin.jvm.internal.i.b(view, "v");
            ((CheckedTextView) view).toggle();
        }
    }

    /* compiled from: AlarmEditActivity.kt */
    static final class f implements View.OnClickListener {
        public static final f e = new f();

        f() {
        }

        public final void onClick(View view) {
            kotlin.jvm.internal.i.b(view, "v");
            ((CheckedTextView) view).toggle();
        }
    }

    /* compiled from: AlarmEditActivity.kt */
    static final class g implements View.OnClickListener {
        public static final g e = new g();

        g() {
        }

        public final void onClick(View view) {
            kotlin.jvm.internal.i.b(view, "v");
            ((CheckedTextView) view).toggle();
        }
    }

    /* compiled from: AlarmEditActivity.kt */
    static final class h implements View.OnClickListener {
        public static final h e = new h();

        h() {
        }

        public final void onClick(View view) {
            kotlin.jvm.internal.i.b(view, "v");
            ((CheckedTextView) view).toggle();
        }
    }

    /* compiled from: AlarmEditActivity.kt */
    static final class i implements View.OnClickListener {
        public static final i e = new i();

        i() {
        }

        public final void onClick(View view) {
            kotlin.jvm.internal.i.b(view, "v");
            ((CheckedTextView) view).toggle();
        }
    }

    /* compiled from: AlarmEditActivity.kt */
    static final class j implements View.OnClickListener {
        public static final j e = new j();

        j() {
        }

        public final void onClick(View view) {
            kotlin.jvm.internal.i.b(view, "v");
            ((CheckedTextView) view).toggle();
        }
    }

    /* compiled from: AlarmEditActivity.kt */
    static final class k implements View.OnClickListener {
        public static final k e = new k();

        k() {
        }

        public final void onClick(View view) {
            kotlin.jvm.internal.i.b(view, "v");
            ((CheckedTextView) view).toggle();
        }
    }

    /* compiled from: AlarmEditActivity.kt */
    static final class l implements View.OnClickListener {
        public static final l e = new l();

        l() {
        }

        public final void onClick(View view) {
            kotlin.jvm.internal.i.b(view, "v");
            ((CheckedTextView) view).toggle();
        }
    }

    static {
        new a((f) null);
    }

    private final AlarmConfig q() {
        return (AlarmConfig) this.E.getValue();
    }

    /* access modifiers changed from: private */
    public final void r() {
        com.chileaf.fitness.b.a aVar = (com.chileaf.fitness.b.a) m();
        AlarmConfig q = q();
        AppCompatEditText appCompatEditText = aVar.G;
        kotlin.jvm.internal.i.a((Object) appCompatEditText, "alarmName");
        q.content = String.valueOf(appCompatEditText.getText());
        AlarmConfig q2 = q();
        AppCompatCheckedTextView appCompatCheckedTextView = aVar.A;
        kotlin.jvm.internal.i.a((Object) appCompatCheckedTextView, "alarmCbMonday");
        q2.monday = appCompatCheckedTextView.isChecked();
        AlarmConfig q3 = q();
        AppCompatCheckedTextView appCompatCheckedTextView2 = aVar.E;
        kotlin.jvm.internal.i.a((Object) appCompatCheckedTextView2, "alarmCbTuesday");
        q3.tuesday = appCompatCheckedTextView2.isChecked();
        AlarmConfig q4 = q();
        AppCompatCheckedTextView appCompatCheckedTextView3 = aVar.F;
        kotlin.jvm.internal.i.a((Object) appCompatCheckedTextView3, "alarmCbWednesday");
        q4.wednesday = appCompatCheckedTextView3.isChecked();
        AlarmConfig q5 = q();
        AppCompatCheckedTextView appCompatCheckedTextView4 = aVar.D;
        kotlin.jvm.internal.i.a((Object) appCompatCheckedTextView4, "alarmCbThursday");
        q5.thursday = appCompatCheckedTextView4.isChecked();
        AlarmConfig q6 = q();
        AppCompatCheckedTextView appCompatCheckedTextView5 = aVar.z;
        kotlin.jvm.internal.i.a((Object) appCompatCheckedTextView5, "alarmCbFriday");
        q6.friday = appCompatCheckedTextView5.isChecked();
        AlarmConfig q7 = q();
        AppCompatCheckedTextView appCompatCheckedTextView6 = aVar.B;
        kotlin.jvm.internal.i.a((Object) appCompatCheckedTextView6, "alarmCbSaturday");
        q7.saturday = appCompatCheckedTextView6.isChecked();
        AlarmConfig q8 = q();
        AppCompatCheckedTextView appCompatCheckedTextView7 = aVar.C;
        kotlin.jvm.internal.i.a((Object) appCompatCheckedTextView7, "alarmCbSunday");
        q8.sunday = appCompatCheckedTextView7.isChecked();
        AlarmConfig q9 = q();
        AppCompatCheckedTextView appCompatCheckedTextView8 = aVar.H;
        kotlin.jvm.internal.i.a((Object) appCompatCheckedTextView8, "alarmSingle");
        q9.single = appCompatCheckedTextView8.isChecked();
        AlarmConfig q10 = q();
        TimePicker timePicker = aVar.K;
        kotlin.jvm.internal.i.a((Object) timePicker, "alarmTimePicker");
        Integer currentHour = timePicker.getCurrentHour();
        kotlin.jvm.internal.i.a((Object) currentHour, "alarmTimePicker.currentHour");
        q10.hour = currentHour.intValue();
        AlarmConfig q11 = q();
        TimePicker timePicker2 = aVar.K;
        kotlin.jvm.internal.i.a((Object) timePicker2, "alarmTimePicker");
        Integer currentMinute = timePicker2.getCurrentMinute();
        kotlin.jvm.internal.i.a((Object) currentMinute, "alarmTimePicker.currentMinute");
        q11.minute = currentMinute.intValue();
        Intent intent = new Intent();
        intent.putExtra("extra_result", q());
        setResult(-1, intent);
        finish();
    }

    /* access modifiers changed from: private */
    public final void s() {
        Calendar instance = Calendar.getInstance();
        instance.set(1, this.F);
        instance.set(2, this.G);
        instance.set(5, this.H);
        instance.set(11, this.I);
        instance.set(12, this.J);
        AlarmConfig q = q();
        kotlin.jvm.internal.i.a((Object) instance, "calendar");
        q.stamp = instance.getTimeInMillis() / ((long) 1000);
    }

    public View d(int i2) {
        if (this.K == null) {
            this.K = new HashMap();
        }
        View view = (View) this.K.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i2);
        this.K.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public BaseViewModel o() {
        return null;
    }

    public int p() {
        return R$layout.activity_alarm_edit;
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        kotlin.jvm.internal.i.b(view, "view");
        com.chileaf.fitness.b.a aVar = (com.chileaf.fitness.b.a) m();
        aVar.H.setOnClickListener(e.e);
        aVar.A.setOnClickListener(f.e);
        aVar.E.setOnClickListener(g.e);
        aVar.F.setOnClickListener(h.e);
        aVar.D.setOnClickListener(i.e);
        aVar.z.setOnClickListener(j.e);
        aVar.B.setOnClickListener(k.e);
        aVar.C.setOnClickListener(l.e);
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        String string = getString(R$string.alarm_edit);
        kotlin.jvm.internal.i.a((Object) string, "getString(R.string.alarm_edit)");
        a(string);
        String string2 = getString(R$string.confirm);
        kotlin.jvm.internal.i.a((Object) string2, "getString(R.string.confirm)");
        a(string2, (View.OnClickListener) new d(this));
        com.chileaf.fitness.b.a aVar = (com.chileaf.fitness.b.a) m();
        aVar.G.setText(q().content);
        aVar.K.setIs24HourView(Boolean.valueOf(DateFormat.is24HourFormat(this)));
        TimePicker timePicker = aVar.K;
        kotlin.jvm.internal.i.a((Object) timePicker, "alarmTimePicker");
        timePicker.setCurrentHour(Integer.valueOf(q().hour));
        TimePicker timePicker2 = aVar.K;
        kotlin.jvm.internal.i.a((Object) timePicker2, "alarmTimePicker");
        timePicker2.setCurrentMinute(Integer.valueOf(q().minute));
        AppCompatCheckedTextView appCompatCheckedTextView = aVar.H;
        kotlin.jvm.internal.i.a((Object) appCompatCheckedTextView, "alarmSingle");
        appCompatCheckedTextView.setChecked(q().single);
        AppCompatCheckedTextView appCompatCheckedTextView2 = aVar.A;
        kotlin.jvm.internal.i.a((Object) appCompatCheckedTextView2, "alarmCbMonday");
        appCompatCheckedTextView2.setChecked(q().monday);
        AppCompatCheckedTextView appCompatCheckedTextView3 = aVar.E;
        kotlin.jvm.internal.i.a((Object) appCompatCheckedTextView3, "alarmCbTuesday");
        appCompatCheckedTextView3.setChecked(q().tuesday);
        AppCompatCheckedTextView appCompatCheckedTextView4 = aVar.F;
        kotlin.jvm.internal.i.a((Object) appCompatCheckedTextView4, "alarmCbWednesday");
        appCompatCheckedTextView4.setChecked(q().wednesday);
        AppCompatCheckedTextView appCompatCheckedTextView5 = aVar.D;
        kotlin.jvm.internal.i.a((Object) appCompatCheckedTextView5, "alarmCbThursday");
        appCompatCheckedTextView5.setChecked(q().thursday);
        AppCompatCheckedTextView appCompatCheckedTextView6 = aVar.z;
        kotlin.jvm.internal.i.a((Object) appCompatCheckedTextView6, "alarmCbFriday");
        appCompatCheckedTextView6.setChecked(q().friday);
        AppCompatCheckedTextView appCompatCheckedTextView7 = aVar.B;
        kotlin.jvm.internal.i.a((Object) appCompatCheckedTextView7, "alarmCbSaturday");
        appCompatCheckedTextView7.setChecked(q().saturday);
        AppCompatCheckedTextView appCompatCheckedTextView8 = aVar.C;
        kotlin.jvm.internal.i.a((Object) appCompatCheckedTextView8, "alarmCbSunday");
        appCompatCheckedTextView8.setChecked(q().sunday);
        Calendar instance = Calendar.getInstance();
        if (q().stamp != 0) {
            kotlin.jvm.internal.i.a((Object) instance, "calendar");
            instance.setTimeInMillis(q().stamp * 1000);
        }
        this.F = instance.get(1);
        this.G = instance.get(2);
        this.H = instance.get(5);
        this.I = instance.get(11);
        this.J = instance.get(12);
        aVar.I.setOnClickListener(new b(this));
        aVar.J.setOnClickListener(new c(this));
    }
}
