package com.google.android.material.internal;

import android.content.Context;
import android.view.SubMenu;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.view.menu.r;

/* compiled from: NavigationMenu */
public class f extends g {
    public f(Context context) {
        super(context);
    }

    public SubMenu addSubMenu(int i2, int i3, int i4, CharSequence charSequence) {
        i iVar = (i) a(i2, i3, i4, charSequence);
        h hVar = new h(e(), this, iVar);
        iVar.a((r) hVar);
        return hVar;
    }
}
