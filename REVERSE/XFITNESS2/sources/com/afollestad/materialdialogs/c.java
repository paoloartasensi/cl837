package com.afollestad.materialdialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.afollestad.materialdialogs.internal.button.DialogActionButton;
import com.afollestad.materialdialogs.internal.main.DialogLayout;
import com.afollestad.materialdialogs.j.e;
import com.afollestad.materialdialogs.j.f;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.internal.i;

/* compiled from: DialogBehavior.kt */
public final class c implements a {
    public static final c a = new c();

    /* compiled from: DialogBehavior.kt */
    static final class a implements Runnable {
        final /* synthetic */ DialogActionButton e;

        a(DialogActionButton dialogActionButton) {
            this.e = dialogActionButton;
        }

        public final void run() {
            this.e.requestFocus();
        }
    }

    /* compiled from: DialogBehavior.kt */
    static final class b implements Runnable {
        final /* synthetic */ DialogActionButton e;

        b(DialogActionButton dialogActionButton) {
            this.e = dialogActionButton;
        }

        public final void run() {
            this.e.requestFocus();
        }
    }

    private c() {
    }

    public int a(boolean z) {
        if (z) {
            return R$style.MD_Dark;
        }
        return R$style.MD_Light;
    }

    public void a(MaterialDialog materialDialog) {
        i.b(materialDialog, "dialog");
    }

    public void b(MaterialDialog materialDialog) {
        i.b(materialDialog, "dialog");
        DialogActionButton a2 = com.afollestad.materialdialogs.e.a.a(materialDialog, WhichButton.NEGATIVE);
        if (f.c(a2)) {
            a2.post(new a(a2));
            return;
        }
        DialogActionButton a3 = com.afollestad.materialdialogs.e.a.a(materialDialog, WhichButton.POSITIVE);
        if (f.c(a3)) {
            a3.post(new b(a3));
        }
    }

    public boolean onDismiss() {
        return false;
    }

    @SuppressLint({"InflateParams"})
    public ViewGroup a(Context context, Window window, LayoutInflater layoutInflater, MaterialDialog materialDialog) {
        i.b(context, "creatingContext");
        i.b(window, "dialogWindow");
        i.b(layoutInflater, "layoutInflater");
        i.b(materialDialog, "dialog");
        View inflate = layoutInflater.inflate(R$layout.md_dialog_base, (ViewGroup) null, false);
        if (inflate != null) {
            return (ViewGroup) inflate;
        }
        throw new TypeCastException("null cannot be cast to non-null type android.view.ViewGroup");
    }

    public DialogLayout a(ViewGroup viewGroup) {
        i.b(viewGroup, "root");
        return (DialogLayout) viewGroup;
    }

    public void a(Context context, Window window, DialogLayout dialogLayout, Integer num) {
        i.b(context, "context");
        i.b(window, "window");
        i.b(dialogLayout, "view");
        if (num == null || num.intValue() != 0) {
            window.setSoftInputMode(16);
            WindowManager windowManager = window.getWindowManager();
            if (windowManager != null) {
                Resources resources = context.getResources();
                Pair<Integer, Integer> a2 = e.a.a(windowManager);
                int intValue = a2.component1().intValue();
                dialogLayout.setMaxHeight(a2.component2().intValue() - (resources.getDimensionPixelSize(R$dimen.md_dialog_vertical_margin) * 2));
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(window.getAttributes());
                layoutParams.width = Math.min(num != null ? num.intValue() : resources.getDimensionPixelSize(R$dimen.md_dialog_max_width), intValue - (resources.getDimensionPixelSize(R$dimen.md_dialog_horizontal_margin) * 2));
                window.setAttributes(layoutParams);
            }
        }
    }

    public void a(DialogLayout dialogLayout, int i2, float f2) {
        i.b(dialogLayout, "view");
        dialogLayout.setCornerRadii(new float[]{f2, f2, f2, f2, 0.0f, 0.0f, 0.0f, 0.0f});
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(f2);
        gradientDrawable.setColor(i2);
        dialogLayout.setBackground(gradientDrawable);
    }
}
