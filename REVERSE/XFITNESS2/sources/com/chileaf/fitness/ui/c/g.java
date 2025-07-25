package com.chileaf.fitness.ui.c;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.chileaf.fitness.R$id;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.widget.WheelView;

/* compiled from: SingleTimeDialog */
public class g extends com.google.android.material.bottomsheet.a implements View.OnClickListener {
    private WheelView l = ((WheelView) findViewById(R$id.wheel_hour));
    private WheelView m = ((WheelView) findViewById(R$id.wheel_minute));
    private c n;

    /* compiled from: SingleTimeDialog */
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

        public g a() {
            return new g(this);
        }
    }

    /* compiled from: SingleTimeDialog */
    public interface c {
        void a(int i2, int i3, String str);
    }

    public g(b bVar) {
        super(bVar.a);
        setContentView((int) R$layout.dialog_single_time);
        findViewById(R$id.tv_cancel).setOnClickListener(this);
        findViewById(R$id.tv_confirm).setOnClickListener(this);
        this.n = bVar.c;
        a(bVar.b);
        setCanceledOnTouchOutside(true);
    }

    private void a(String str) {
        try {
            String[] split = str.split(":");
            this.l.setSelectedItemValue(split[0]);
            this.m.setSelectedItemValue(split[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        if (view.getId() == 2131296686) {
            dismiss();
        } else if (view.getId() == 2131296687) {
            if (this.n != null) {
                String selectedItemValue = this.l.getSelectedItemValue();
                String selectedItemValue2 = this.m.getSelectedItemValue();
                c cVar = this.n;
                int parseInt = Integer.parseInt(selectedItemValue);
                int parseInt2 = Integer.parseInt(selectedItemValue2);
                cVar.a(parseInt, parseInt2, selectedItemValue + ":" + selectedItemValue2);
            }
            dismiss();
        }
    }

    public static b a(Activity activity) {
        return new b(activity);
    }
}
