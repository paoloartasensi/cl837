package com.afollestad.materialdialogs.internal.message;

import android.text.style.URLSpan;
import android.view.View;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.i;

/* compiled from: CustomUrlSpan.kt */
public final class CustomUrlSpan extends URLSpan {
    private final l<String, kotlin.l> e;

    public void onClick(View view) {
        i.b(view, "widget");
        l<String, kotlin.l> lVar = this.e;
        String url = getURL();
        i.a((Object) url, "url");
        lVar.invoke(url);
    }
}
