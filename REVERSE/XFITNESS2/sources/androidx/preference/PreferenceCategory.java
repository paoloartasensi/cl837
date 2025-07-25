package androidx.preference;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;
import androidx.core.content.a;
import androidx.core.content.c.g;
import androidx.core.h.e0.d;

public class PreferenceCategory extends PreferenceGroup {
    public PreferenceCategory(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
    }

    public boolean F() {
        return !super.u();
    }

    public void a(l lVar) {
        TextView textView;
        super.a(lVar);
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 28) {
            lVar.itemView.setAccessibilityHeading(true);
        } else if (i2 < 21) {
            TypedValue typedValue = new TypedValue();
            if (b().getTheme().resolveAttribute(R$attr.colorAccent, typedValue, true) && (textView = (TextView) lVar.a(16908310)) != null && textView.getCurrentTextColor() == a.a(b(), R$color.preference_fallback_accent_color)) {
                textView.setTextColor(typedValue.data);
            }
        }
    }

    public boolean u() {
        return false;
    }

    public PreferenceCategory(Context context, AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, 0);
    }

    public PreferenceCategory(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, g.a(context, R$attr.preferenceCategoryStyle, 16842892));
    }

    @Deprecated
    public void a(d dVar) {
        d.c d;
        super.a(dVar);
        if (Build.VERSION.SDK_INT < 28 && (d = dVar.d()) != null) {
            dVar.b((Object) d.c.a(d.c(), d.d(), d.a(), d.b(), true, d.e()));
        }
    }
}
