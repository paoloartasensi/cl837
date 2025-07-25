package androidx.preference;

import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ExpandButton */
final class b extends Preference {
    private long T;

    b(Context context, List<Preference> list, long j2) {
        super(context);
        H();
        a(list);
        this.T = j2 + 1000000;
    }

    private void H() {
        d(R$layout.expand_button);
        c(R$drawable.ic_arrow_down_24dp);
        f(R$string.expand_button_title);
        e(999);
    }

    private void a(List<Preference> list) {
        ArrayList arrayList = new ArrayList();
        CharSequence charSequence = null;
        for (Preference next : list) {
            CharSequence q = next.q();
            boolean z = next instanceof PreferenceGroup;
            if (z && !TextUtils.isEmpty(q)) {
                arrayList.add((PreferenceGroup) next);
            }
            if (arrayList.contains(next.k())) {
                if (z) {
                    arrayList.add((PreferenceGroup) next);
                }
            } else if (!TextUtils.isEmpty(q)) {
                if (charSequence == null) {
                    charSequence = q;
                } else {
                    charSequence = b().getString(R$string.summary_collapsed_preference_list, new Object[]{charSequence, q});
                }
            }
        }
        a(charSequence);
    }

    /* access modifiers changed from: package-private */
    public long f() {
        return this.T;
    }

    public void a(l lVar) {
        super.a(lVar);
        lVar.a(false);
    }
}
