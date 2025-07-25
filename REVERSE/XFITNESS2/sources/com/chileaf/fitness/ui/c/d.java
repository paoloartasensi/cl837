package com.chileaf.fitness.ui.c;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.chileaf.fitness.R$id;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.widget.WheelView;

/* compiled from: DoubleTimeDialog */
public class d extends com.google.android.material.bottomsheet.a implements View.OnClickListener {
    private WheelView l = ((WheelView) findViewById(R$id.wheel_hour_start));
    private WheelView m = ((WheelView) findViewById(R$id.wheel_minute_start));
    private WheelView n = ((WheelView) findViewById(R$id.wheel_hour_end));
    private WheelView o = ((WheelView) findViewById(R$id.wheel_minute_end));
    private c p;

    /* compiled from: DoubleTimeDialog */
    public static final class b {
        /* access modifiers changed from: private */
        public Context a;
        /* access modifiers changed from: private */
        public String b;
        /* access modifiers changed from: private */
        public c c;

        private b(Context context) {
            this.a = context;
        }

        public b a(String str) {
            this.b = str;
            return this;
        }

        public b a(c cVar) {
            this.c = cVar;
            return this;
        }

        public d a() {
            return new d(this);
        }
    }

    /* compiled from: DoubleTimeDialog */
    public interface c {
        void a(int i2, int i3, int i4, int i5, String str);
    }

    public d(b bVar) {
        super(bVar.a);
        setContentView((int) R$layout.dialog_double_time);
        findViewById(R$id.tv_cancel).setOnClickListener(this);
        findViewById(R$id.tv_confirm).setOnClickListener(this);
        this.p = bVar.c;
        a(bVar.b);
        setCanceledOnTouchOutside(true);
    }

    private void a(String str) {
        try {
            String[] split = str.split("-");
            String[] split2 = split[0].split(":");
            String[] split3 = split[1].split(":");
            this.l.setSelectedItemValue(split2[0]);
            this.m.setSelectedItemValue(split2[1]);
            this.n.setSelectedItemValue(split3[0]);
            this.o.setSelectedItemValue(split3[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        if (view.getId() == 2131296686) {
            dismiss();
        } else if (view.getId() == 2131296687) {
            if (this.p != null) {
                String selectedItemValue = this.l.getSelectedItemValue();
                String selectedItemValue2 = this.m.getSelectedItemValue();
                String selectedItemValue3 = this.n.getSelectedItemValue();
                String selectedItemValue4 = this.o.getSelectedItemValue();
                c cVar = this.p;
                int parseInt = Integer.parseInt(selectedItemValue);
                int parseInt2 = Integer.parseInt(selectedItemValue2);
                int parseInt3 = Integer.parseInt(selectedItemValue3);
                int parseInt4 = Integer.parseInt(selectedItemValue4);
                cVar.a(parseInt, parseInt2, parseInt3, parseInt4, selectedItemValue + ":" + selectedItemValue2 + "-" + selectedItemValue3 + ":" + selectedItemValue4);
            }
            dismiss();
        }
    }

    public static b a(Activity activity) {
        return new b(activity);
    }
}
