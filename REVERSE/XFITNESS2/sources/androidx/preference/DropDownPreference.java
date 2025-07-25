package androidx.preference;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class DropDownPreference extends ListPreference {
    private final Context e0;
    private final ArrayAdapter f0;
    private Spinner g0;
    private final AdapterView.OnItemSelectedListener h0;

    class a implements AdapterView.OnItemSelectedListener {
        a() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j2) {
            if (i2 >= 0) {
                String charSequence = DropDownPreference.this.P()[i2].toString();
                if (!charSequence.equals(DropDownPreference.this.Q()) && DropDownPreference.this.a((Object) charSequence)) {
                    DropDownPreference.this.e(charSequence);
                }
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public DropDownPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.dropdownPreferenceStyle);
    }

    private void S() {
        this.f0.clear();
        if (N() != null) {
            for (CharSequence charSequence : N()) {
                this.f0.add(charSequence.toString());
            }
        }
    }

    private int f(String str) {
        CharSequence[] P = P();
        if (str == null || P == null) {
            return -1;
        }
        for (int length = P.length - 1; length >= 0; length--) {
            if (P[length].equals(str)) {
                return length;
            }
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public void B() {
        this.g0.performClick();
    }

    /* access modifiers changed from: protected */
    public ArrayAdapter R() {
        return new ArrayAdapter(this.e0, 17367049);
    }

    public void a(l lVar) {
        Spinner spinner = (Spinner) lVar.itemView.findViewById(R$id.spinner);
        this.g0 = spinner;
        spinner.setAdapter(this.f0);
        this.g0.setOnItemSelectedListener(this.h0);
        this.g0.setSelection(f(Q()));
        super.a(lVar);
    }

    /* access modifiers changed from: protected */
    public void y() {
        super.y();
        ArrayAdapter arrayAdapter = this.f0;
        if (arrayAdapter != null) {
            arrayAdapter.notifyDataSetChanged();
        }
    }

    public DropDownPreference(Context context, AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, 0);
    }

    public DropDownPreference(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        this.h0 = new a();
        this.e0 = context;
        this.f0 = R();
        S();
    }
}
