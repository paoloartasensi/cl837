package com.chileaf.fitness.ui.activity;

import android.content.Intent;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;

/* compiled from: WebResultActivity.kt */
final class WebResultActivity$mTitle$2 extends Lambda implements a<String> {
    final /* synthetic */ WebResultActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    WebResultActivity$mTitle$2(WebResultActivity webResultActivity) {
        super(0);
        this.this$0 = webResultActivity;
    }

    public final String invoke() {
        String stringExtra;
        Intent intent = this.this$0.getIntent();
        if (intent != null && (stringExtra = intent.getStringExtra("web_title")) != null) {
            return stringExtra;
        }
        String string = this.this$0.getString(2131755053);
        i.a((Object) string, "getString(R.string.app_name)");
        return string;
    }
}
