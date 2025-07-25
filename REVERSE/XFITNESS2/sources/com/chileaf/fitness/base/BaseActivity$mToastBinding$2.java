package com.chileaf.fitness.base;

import android.view.ViewGroup;
import androidx.databinding.g;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.b.y1;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: BaseActivity.kt */
final class BaseActivity$mToastBinding$2 extends Lambda implements a<y1> {
    final /* synthetic */ BaseActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    BaseActivity$mToastBinding$2(BaseActivity baseActivity) {
        super(0);
        this.this$0 = baseActivity;
    }

    public final y1 invoke() {
        return (y1) g.a(this.this$0.getLayoutInflater(), (int) R$layout.layout_toast, (ViewGroup) null, false);
    }
}
