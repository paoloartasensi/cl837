package com.afollestad.materialdialogs.g;

import android.widget.CheckBox;
import androidx.appcompat.widget.AppCompatCheckBox;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.button.DialogActionButtonLayout;
import kotlin.jvm.internal.i;

/* compiled from: DialogCheckboxExt.kt */
public final class a {
    public static final CheckBox a(MaterialDialog materialDialog) {
        AppCompatCheckBox checkBoxPrompt;
        i.b(materialDialog, "$this$getCheckBoxPrompt");
        DialogActionButtonLayout buttonsLayout = materialDialog.e().getButtonsLayout();
        if (buttonsLayout != null && (checkBoxPrompt = buttonsLayout.getCheckBoxPrompt()) != null) {
            return checkBoxPrompt;
        }
        throw new IllegalStateException("The dialog does not have an attached buttons layout.");
    }
}
