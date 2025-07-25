package androidx.preference;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.c;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/* compiled from: MultiSelectListPreferenceDialogFragmentCompat */
public class d extends f {
    CharSequence[] A0;
    Set<String> x0 = new HashSet();
    boolean y0;
    CharSequence[] z0;

    /* compiled from: MultiSelectListPreferenceDialogFragmentCompat */
    class a implements DialogInterface.OnMultiChoiceClickListener {
        a() {
        }

        public void onClick(DialogInterface dialogInterface, int i2, boolean z) {
            if (z) {
                d dVar = d.this;
                dVar.y0 = dVar.x0.add(dVar.A0[i2].toString()) | dVar.y0;
                return;
            }
            d dVar2 = d.this;
            dVar2.y0 = dVar2.x0.remove(dVar2.A0[i2].toString()) | dVar2.y0;
        }
    }

    public static d c(String str) {
        d dVar = new d();
        Bundle bundle = new Bundle(1);
        bundle.putString("key", str);
        dVar.m(bundle);
        return dVar;
    }

    private MultiSelectListPreference t0() {
        return (MultiSelectListPreference) r0();
    }

    /* access modifiers changed from: protected */
    public void a(c.a aVar) {
        super.a(aVar);
        int length = this.A0.length;
        boolean[] zArr = new boolean[length];
        for (int i2 = 0; i2 < length; i2++) {
            zArr[i2] = this.x0.contains(this.A0[i2].toString());
        }
        aVar.a(this.z0, zArr, (DialogInterface.OnMultiChoiceClickListener) new a());
    }

    public void e(Bundle bundle) {
        super.e(bundle);
        bundle.putStringArrayList("MultiSelectListPreferenceDialogFragmentCompat.values", new ArrayList(this.x0));
        bundle.putBoolean("MultiSelectListPreferenceDialogFragmentCompat.changed", this.y0);
        bundle.putCharSequenceArray("MultiSelectListPreferenceDialogFragmentCompat.entries", this.z0);
        bundle.putCharSequenceArray("MultiSelectListPreferenceDialogFragmentCompat.entryValues", this.A0);
    }

    public void k(boolean z) {
        if (z && this.y0) {
            MultiSelectListPreference t0 = t0();
            if (t0.a((Object) this.x0)) {
                t0.c(this.x0);
            }
        }
        this.y0 = false;
    }

    public void c(Bundle bundle) {
        super.c(bundle);
        if (bundle == null) {
            MultiSelectListPreference t0 = t0();
            if (t0.N() == null || t0.O() == null) {
                throw new IllegalStateException("MultiSelectListPreference requires an entries array and an entryValues array.");
            }
            this.x0.clear();
            this.x0.addAll(t0.P());
            this.y0 = false;
            this.z0 = t0.N();
            this.A0 = t0.O();
            return;
        }
        this.x0.clear();
        this.x0.addAll(bundle.getStringArrayList("MultiSelectListPreferenceDialogFragmentCompat.values"));
        this.y0 = bundle.getBoolean("MultiSelectListPreferenceDialogFragmentCompat.changed", false);
        this.z0 = bundle.getCharSequenceArray("MultiSelectListPreferenceDialogFragmentCompat.entries");
        this.A0 = bundle.getCharSequenceArray("MultiSelectListPreferenceDialogFragmentCompat.entryValues");
    }
}
