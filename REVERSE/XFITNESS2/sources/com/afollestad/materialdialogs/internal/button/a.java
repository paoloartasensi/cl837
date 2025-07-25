package com.afollestad.materialdialogs.internal.button;

import com.afollestad.materialdialogs.j.f;

/* compiled from: DialogActionButtonLayout.kt */
public final class a {
    public static final boolean a(DialogActionButtonLayout dialogActionButtonLayout) {
        if (dialogActionButtonLayout == null) {
            return false;
        }
        return ((dialogActionButtonLayout.getVisibleButtons().length == 0) ^ true) || f.c(dialogActionButtonLayout.getCheckBoxPrompt());
    }
}
