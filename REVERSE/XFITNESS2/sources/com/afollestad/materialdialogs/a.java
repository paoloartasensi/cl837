package com.afollestad.materialdialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import com.afollestad.materialdialogs.internal.main.DialogLayout;

/* compiled from: DialogBehavior.kt */
public interface a {
    int a(boolean z);

    ViewGroup a(Context context, Window window, LayoutInflater layoutInflater, MaterialDialog materialDialog);

    DialogLayout a(ViewGroup viewGroup);

    void a(Context context, Window window, DialogLayout dialogLayout, Integer num);

    void a(MaterialDialog materialDialog);

    void a(DialogLayout dialogLayout, int i2, float f2);

    void b(MaterialDialog materialDialog);

    boolean onDismiss();
}
