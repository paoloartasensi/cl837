package com.afollestad.materialdialogs.i;

import android.text.Html;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.R$attr;
import com.afollestad.materialdialogs.j.e;
import kotlin.jvm.internal.i;

/* compiled from: DialogMessageSettings.kt */
public final class a {
    private boolean a;
    private boolean b;
    private final MaterialDialog c;
    private final TextView d;

    public a(MaterialDialog materialDialog, TextView textView) {
        i.b(materialDialog, "dialog");
        i.b(textView, "messageTextView");
        this.c = materialDialog;
        this.d = textView;
    }

    public final a a(float f2) {
        this.b = true;
        this.d.setLineSpacing(0.0f, f2);
        return this;
    }

    public final void a(Integer num, CharSequence charSequence) {
        if (!this.b) {
            a(e.a.a(this.c.f(), R$attr.md_line_spacing_body, 1.1f));
        }
        TextView textView = this.d;
        CharSequence a2 = a(charSequence, this.a);
        if (a2 == null) {
            a2 = e.a(e.a, this.c, num, (Integer) null, this.a, 4, (Object) null);
        }
        textView.setText(a2);
    }

    private final CharSequence a(CharSequence charSequence, boolean z) {
        if (charSequence == null) {
            return null;
        }
        return z ? Html.fromHtml(charSequence.toString()) : charSequence;
    }
}
