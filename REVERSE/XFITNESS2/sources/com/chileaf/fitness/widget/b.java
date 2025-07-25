package com.chileaf.fitness.widget;

import android.text.TextWatcher;
import kotlin.jvm.internal.i;

/* compiled from: SimpleTextWatcher.kt */
public abstract class b implements TextWatcher {
    public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        i.b(charSequence, "s");
    }

    public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        i.b(charSequence, "s");
    }
}
