package androidx.preference;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.c;

/* compiled from: ListPreferenceDialogFragmentCompat */
public class c extends f {
    int x0;
    private CharSequence[] y0;
    private CharSequence[] z0;

    /* compiled from: ListPreferenceDialogFragmentCompat */
    class a implements DialogInterface.OnClickListener {
        a() {
        }

        public void onClick(DialogInterface dialogInterface, int i2) {
            c cVar = c.this;
            cVar.x0 = i2;
            cVar.onClick(dialogInterface, -1);
            dialogInterface.dismiss();
        }
    }

    public static c c(String str) {
        c cVar = new c();
        Bundle bundle = new Bundle(1);
        bundle.putString("key", str);
        cVar.m(bundle);
        return cVar;
    }

    private ListPreference t0() {
        return (ListPreference) r0();
    }

    /* access modifiers changed from: protected */
    public void a(c.a aVar) {
        super.a(aVar);
        aVar.a(this.y0, this.x0, (DialogInterface.OnClickListener) new a());
        aVar.b((CharSequence) null, (DialogInterface.OnClickListener) null);
    }

    public void e(Bundle bundle) {
        super.e(bundle);
        bundle.putInt("ListPreferenceDialogFragment.index", this.x0);
        bundle.putCharSequenceArray("ListPreferenceDialogFragment.entries", this.y0);
        bundle.putCharSequenceArray("ListPreferenceDialogFragment.entryValues", this.z0);
    }

    public void k(boolean z) {
        int i2;
        if (z && (i2 = this.x0) >= 0) {
            String charSequence = this.z0[i2].toString();
            ListPreference t0 = t0();
            if (t0.a((Object) charSequence)) {
                t0.e(charSequence);
            }
        }
    }

    public void c(Bundle bundle) {
        super.c(bundle);
        if (bundle == null) {
            ListPreference t0 = t0();
            if (t0.N() == null || t0.P() == null) {
                throw new IllegalStateException("ListPreference requires an entries array and an entryValues array.");
            }
            this.x0 = t0.d(t0.Q());
            this.y0 = t0.N();
            this.z0 = t0.P();
            return;
        }
        this.x0 = bundle.getInt("ListPreferenceDialogFragment.index", 0);
        this.y0 = bundle.getCharSequenceArray("ListPreferenceDialogFragment.entries");
        this.z0 = bundle.getCharSequenceArray("ListPreferenceDialogFragment.entryValues");
    }
}
