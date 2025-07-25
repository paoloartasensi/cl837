package com.afollestad.materialdialogs.e;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.WhichButton;
import com.afollestad.materialdialogs.internal.button.DialogActionButton;
import com.afollestad.materialdialogs.internal.button.DialogActionButtonLayout;
import com.afollestad.materialdialogs.j.f;
import kotlin.jvm.internal.i;

/* compiled from: DialogActionExt.kt */
public final class a {
    public static final boolean a(MaterialDialog materialDialog) {
        DialogActionButton[] visibleButtons;
        i.b(materialDialog, "$this$hasActionButtons");
        DialogActionButtonLayout buttonsLayout = materialDialog.e().getButtonsLayout();
        boolean z = false;
        if (buttonsLayout == null || (visibleButtons = buttonsLayout.getVisibleButtons()) == null) {
            return false;
        }
        if (visibleButtons.length == 0) {
            z = true;
        }
        return !z;
    }

    public static final boolean b(MaterialDialog materialDialog, WhichButton whichButton) {
        i.b(materialDialog, "$this$hasActionButton");
        i.b(whichButton, "which");
        return f.c(a(materialDialog, whichButton));
    }

    public static final DialogActionButton a(MaterialDialog materialDialog, WhichButton whichButton) {
        DialogActionButton[] actionButtons;
        DialogActionButton dialogActionButton;
        i.b(materialDialog, "$this$getActionButton");
        i.b(whichButton, "which");
        DialogActionButtonLayout buttonsLayout = materialDialog.e().getButtonsLayout();
        if (buttonsLayout != null && (actionButtons = buttonsLayout.getActionButtons()) != null && (dialogActionButton = actionButtons[whichButton.getIndex()]) != null) {
            return dialogActionButton;
        }
        throw new IllegalStateException("The dialog does not have an attached buttons layout.");
    }
}
