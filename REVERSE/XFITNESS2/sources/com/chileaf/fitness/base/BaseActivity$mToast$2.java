package com.chileaf.fitness.base;

import android.widget.Toast;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: BaseActivity.kt */
final class BaseActivity$mToast$2 extends Lambda implements a<Toast> {
    final /* synthetic */ BaseActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    BaseActivity$mToast$2(BaseActivity baseActivity) {
        super(0);
        this.this$0 = baseActivity;
    }

    public final Toast invoke() {
        return new Toast(this.this$0);
    }
}
