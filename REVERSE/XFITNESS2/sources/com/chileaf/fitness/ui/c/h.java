package com.chileaf.fitness.ui.c;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.chileaf.fitness.R$id;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.widget.WheelView;
import java.util.ArrayList;

/* compiled from: WarningDialog */
public class h extends com.google.android.material.bottomsheet.a implements View.OnClickListener {
    private WheelView l = ((WheelView) findViewById(R$id.wheel_warning));
    private c m;

    /* compiled from: WarningDialog */
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

        public h a() {
            return new h(this);
        }
    }

    /* compiled from: WarningDialog */
    public interface c {
        void a(String str);
    }

    public h(b bVar) {
        super(bVar.a);
        setContentView((int) R$layout.dialog_warning);
        findViewById(R$id.tv_cancel).setOnClickListener(this);
        findViewById(R$id.tv_confirm).setOnClickListener(this);
        this.m = bVar.c;
        a(bVar.b);
        setCanceledOnTouchOutside(true);
    }

    private void a(String str) {
        try {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 60; i2 <= 250; i2++) {
                arrayList.add(String.valueOf(i2));
            }
            this.l.setData(arrayList);
            this.l.setSelectedItemValue(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        if (view.getId() == 2131296686) {
            dismiss();
        } else if (view.getId() == 2131296687) {
            if (this.m != null) {
                this.m.a(this.l.getSelectedItemValue());
            }
            dismiss();
        }
    }

    public static b a(Activity activity) {
        return new b(activity);
    }
}
