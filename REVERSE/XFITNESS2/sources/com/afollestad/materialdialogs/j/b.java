package com.afollestad.materialdialogs.j;

import android.graphics.Typeface;
import android.os.IBinder;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.f.a;
import com.afollestad.materialdialogs.internal.main.DialogLayout;
import com.afollestad.materialdialogs.internal.message.DialogContentLayout;
import kotlin.TypeCastException;
import kotlin.jvm.internal.i;

/* compiled from: Dialogs.kt */
public final class b {
    public static final void a(MaterialDialog materialDialog, boolean z, boolean z2) {
        i.b(materialDialog, "$this$invalidateDividers");
        materialDialog.e().a(z, z2);
    }

    public static final void b(MaterialDialog materialDialog) {
        i.b(materialDialog, "$this$preShow");
        Object obj = materialDialog.c().get("md.custom_view_no_vertical_padding");
        if (!(obj instanceof Boolean)) {
            obj = null;
        }
        boolean a = i.a((Object) (Boolean) obj, (Object) true);
        a.a(materialDialog.d(), materialDialog);
        DialogLayout e = materialDialog.e();
        if (e.getTitleLayout().b() && !a) {
            e.getContentLayout().a(e.getFrameMarginVertical$core(), e.getFrameMarginVertical$core());
        }
        if (f.c(com.afollestad.materialdialogs.g.a.a(materialDialog))) {
            DialogContentLayout.a(e.getContentLayout(), 0, 0, 1, (Object) null);
        } else if (e.getContentLayout().a()) {
            DialogContentLayout.b(e.getContentLayout(), 0, e.getFrameMarginVerticalLess$core(), 1, (Object) null);
        }
    }

    public static /* synthetic */ void a(MaterialDialog materialDialog, TextView textView, Integer num, CharSequence charSequence, int i2, Typeface typeface, Integer num2, int i3, Object obj) {
        a(materialDialog, textView, (i3 & 2) != 0 ? null : num, (i3 & 4) != 0 ? null : charSequence, (i3 & 8) != 0 ? 0 : i2, typeface, (i3 & 32) != 0 ? null : num2);
    }

    public static final void a(MaterialDialog materialDialog, TextView textView, Integer num, CharSequence charSequence, int i2, Typeface typeface, Integer num2) {
        i.b(materialDialog, "$this$populateText");
        i.b(textView, "textView");
        if (charSequence == null) {
            charSequence = e.a(e.a, materialDialog, num, Integer.valueOf(i2), false, 8, (Object) null);
        }
        if (charSequence != null) {
            ViewParent parent = textView.getParent();
            if (parent != null) {
                ((View) parent).setVisibility(0);
                textView.setVisibility(0);
                textView.setText(charSequence);
                if (typeface != null) {
                    textView.setTypeface(typeface);
                }
                e.a(e.a, textView, materialDialog.f(), num2, (Integer) null, 4, (Object) null);
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type android.view.View");
        }
        textView.setVisibility(8);
    }

    public static final void a(MaterialDialog materialDialog) {
        IBinder iBinder;
        i.b(materialDialog, "$this$hideKeyboard");
        Object systemService = materialDialog.f().getSystemService("input_method");
        if (systemService != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) systemService;
            View currentFocus = materialDialog.getCurrentFocus();
            if (currentFocus != null) {
                iBinder = currentFocus.getWindowToken();
            } else {
                iBinder = materialDialog.e().getWindowToken();
            }
            inputMethodManager.hideSoftInputFromWindow(iBinder, 0);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
    }
}
