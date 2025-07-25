package com.chileaf.fitness.config.permission;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.i;

/* compiled from: PermissionFragment.kt */
public final class b extends Fragment {
    private HashMap b0;

    public /* synthetic */ void T() {
        super.T();
        n0();
    }

    public void a(int i2, String[] strArr, int[] iArr) {
        i.b(strArr, "permissions");
        i.b(iArr, "grantResults");
        super.a(i2, strArr, iArr);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        int length = strArr.length;
        int i3 = 0;
        int i4 = 0;
        while (i3 < length) {
            String str = strArr[i3];
            int i5 = i4 + 1;
            if (iArr[i4] == 0) {
                arrayList2.add(str);
            } else {
                if (b(str)) {
                    arrayList4.add(str);
                } else {
                    arrayList3.add(str);
                }
                arrayList.add(str);
            }
            i3++;
            i4 = i5;
        }
        d a = e.c.a(i2);
        if ((!arrayList4.isEmpty()) && a != null) {
            a.a(new c(this, arrayList4, i2));
        }
        if ((!arrayList.isEmpty()) && a != null) {
            a.b(arrayList);
        }
        if ((!arrayList3.isEmpty()) && a != null) {
            a.a((List<String>) arrayList3);
        }
        if (arrayList.isEmpty() && arrayList3.isEmpty() && a != null) {
            a.a();
        }
    }

    public final void b(String[] strArr, int i2) {
        i.b(strArr, "permissions");
        a(strArr, i2);
    }

    public void c(Bundle bundle) {
        super.c(bundle);
        i(true);
    }

    public void n0() {
        HashMap hashMap = this.b0;
        if (hashMap != null) {
            hashMap.clear();
        }
    }
}
