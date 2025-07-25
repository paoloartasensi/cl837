package com.chileaf.fitness.config.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.p;
import java.util.ArrayList;
import kotlin.TypeCastException;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.i;

/* compiled from: PermissionEx.kt */
public final class a {
    public static final void a(FragmentActivity fragmentActivity, String[] strArr, l<? super PermissionsCallbackImpl, kotlin.l> lVar) {
        i.b(fragmentActivity, "$this$request");
        i.b(strArr, "permissions");
        i.b(lVar, "callbacks");
        PermissionsCallbackImpl permissionsCallbackImpl = new PermissionsCallbackImpl();
        lVar.invoke(permissionsCallbackImpl);
        int a = e.c.a((d) permissionsCallbackImpl);
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            if (!a(fragmentActivity, str)) {
                arrayList.add(str);
            }
        }
        if (arrayList.isEmpty()) {
            permissionsCallbackImpl.a();
        } else if (!arrayList.isEmpty()) {
            b a2 = a(fragmentActivity);
            Object[] array = arrayList.toArray(new String[0]);
            if (array != null) {
                a2.b((String[]) array, a);
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
    }

    private static final b a(FragmentActivity fragmentActivity) {
        Fragment b = fragmentActivity.e().b("PermissionEx");
        if (b == null) {
            b = new b();
            p b2 = fragmentActivity.e().b();
            b2.a(b, "PermissionEx");
            b2.c();
        }
        return (b) b;
    }

    @TargetApi(23)
    private static final boolean a(Activity activity, String str) {
        return Build.VERSION.SDK_INT < 23 || androidx.core.content.a.a((Context) activity, str) == 0;
    }
}
