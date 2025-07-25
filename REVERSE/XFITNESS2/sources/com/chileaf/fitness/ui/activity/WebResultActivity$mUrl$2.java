package com.chileaf.fitness.ui.activity;

import android.content.Intent;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: WebResultActivity.kt */
final class WebResultActivity$mUrl$2 extends Lambda implements a<String> {
    final /* synthetic */ WebResultActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    WebResultActivity$mUrl$2(WebResultActivity webResultActivity) {
        super(0);
        this.this$0 = webResultActivity;
    }

    public final String invoke() {
        Intent intent = this.this$0.getIntent();
        if (intent != null) {
            return intent.getStringExtra("web_url");
        }
        return null;
    }
}
