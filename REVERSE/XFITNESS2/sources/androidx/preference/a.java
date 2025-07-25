package androidx.preference;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/* compiled from: EditTextPreferenceDialogFragmentCompat */
public class a extends f {
    private EditText x0;
    private CharSequence y0;

    public static a c(String str) {
        a aVar = new a();
        Bundle bundle = new Bundle(1);
        bundle.putString("key", str);
        aVar.m(bundle);
        return aVar;
    }

    private EditTextPreference t0() {
        return (EditTextPreference) r0();
    }

    /* access modifiers changed from: protected */
    public void b(View view) {
        super.b(view);
        EditText editText = (EditText) view.findViewById(16908291);
        this.x0 = editText;
        if (editText != null) {
            editText.requestFocus();
            this.x0.setText(this.y0);
            EditText editText2 = this.x0;
            editText2.setSelection(editText2.getText().length());
            if (t0().N() != null) {
                t0().N().a(this.x0);
                return;
            }
            return;
        }
        throw new IllegalStateException("Dialog view must contain an EditText with id @android:id/edit");
    }

    public void e(Bundle bundle) {
        super.e(bundle);
        bundle.putCharSequence("EditTextPreferenceDialogFragment.text", this.y0);
    }

    public void k(boolean z) {
        if (z) {
            String obj = this.x0.getText().toString();
            EditTextPreference t0 = t0();
            if (t0.a((Object) obj)) {
                t0.d(obj);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean s0() {
        return true;
    }

    public void c(Bundle bundle) {
        super.c(bundle);
        if (bundle == null) {
            this.y0 = t0().O();
        } else {
            this.y0 = bundle.getCharSequence("EditTextPreferenceDialogFragment.text");
        }
    }
}
