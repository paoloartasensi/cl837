package com.afollestad.materialdialogs.internal.rtl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import com.afollestad.materialdialogs.j.f;
import kotlin.jvm.internal.i;

/* compiled from: RtlTextView.kt */
public final class RtlTextView extends AppCompatTextView {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public RtlTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        i.b(context, "context");
        f.b((TextView) this);
    }
}
