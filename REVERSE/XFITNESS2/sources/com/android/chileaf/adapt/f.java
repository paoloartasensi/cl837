package com.android.chileaf.adapt;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.j;

/* compiled from: FragmentLifecycleCallbacksImpl */
public class f extends j.f {
    private b a;

    public f(b bVar) {
        this.a = bVar;
    }

    public void b(j jVar, Fragment fragment, Bundle bundle) {
        b bVar = this.a;
        if (bVar != null) {
            bVar.a(fragment, fragment.d());
        }
    }
}
