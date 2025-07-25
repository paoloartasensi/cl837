package com.chileaf.fitness.ui.fragment;

import android.view.View;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.a;
import com.chileaf.fitness.R$array;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.config.b;
import java.util.List;
import java.util.Locale;
import kotlin.jvm.b.q;
import kotlin.jvm.internal.i;
import kotlin.l;

/* compiled from: SettingFragment.kt */
final class SettingFragment$initData$$inlined$with$lambda$1 implements View.OnClickListener {
    final /* synthetic */ SettingFragment e;

    SettingFragment$initData$$inlined$with$lambda$1(SettingFragment settingFragment) {
        this.e = settingFragment;
    }

    public final void onClick(View view) {
        MaterialDialog materialDialog = new MaterialDialog(this.e.o0(), (a) null, 2, (f) null);
        MaterialDialog.a(materialDialog, (Integer) null, this.e.a((int) R$string.select_language), 1, (Object) null);
        com.afollestad.materialdialogs.h.a.a(materialDialog, Integer.valueOf(R$array.array_language), (List) null, (int[]) null, false, new q<MaterialDialog, Integer, CharSequence, l>(this) {
            final /* synthetic */ SettingFragment$initData$$inlined$with$lambda$1 this$0;

            {
                this.this$0 = r1;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
                invoke((MaterialDialog) obj, ((Number) obj2).intValue(), (CharSequence) obj3);
                return l.a;
            }

            public final void invoke(MaterialDialog materialDialog, int i2, CharSequence charSequence) {
                i.b(materialDialog, "<anonymous parameter 0>");
                i.b(charSequence, "<anonymous parameter 2>");
                if (i2 == 0) {
                    b bVar = b.b;
                    BaseActivity a = this.this$0.e.o0();
                    Locale locale = Locale.ENGLISH;
                    i.a((Object) locale, "Locale.ENGLISH");
                    bVar.a(a, locale);
                } else if (i2 == 1) {
                    b bVar2 = b.b;
                    BaseActivity a2 = this.this$0.e.o0();
                    Locale locale2 = Locale.CHINESE;
                    i.a((Object) locale2, "Locale.CHINESE");
                    bVar2.a(a2, locale2);
                }
                this.this$0.e.o0().recreate();
            }
        }, 14, (Object) null);
        materialDialog.show();
    }
}
