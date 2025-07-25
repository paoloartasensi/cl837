package androidx.preference;

import android.content.Context;
import android.util.AttributeSet;
import androidx.core.content.c.g;
import androidx.preference.j;

public final class PreferenceScreen extends PreferenceGroup {
    private boolean a0 = true;

    public PreferenceScreen(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, g.a(context, R$attr.preferenceScreenStyle, 16842891));
    }

    /* access modifiers changed from: protected */
    public void B() {
        j.b c;
        if (g() == null && e() == null && J() != 0 && (c = m().c()) != null) {
            c.a(this);
        }
    }

    /* access modifiers changed from: protected */
    public boolean K() {
        return false;
    }

    public boolean M() {
        return this.a0;
    }
}
