package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.core.content.c.g;

public class SwitchPreference extends TwoStatePreference {
    private final a Y;
    private CharSequence Z;
    private CharSequence a0;

    private class a implements CompoundButton.OnCheckedChangeListener {
        a() {
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (!SwitchPreference.this.a((Object) Boolean.valueOf(z))) {
                compoundButton.setChecked(!z);
            } else {
                SwitchPreference.this.d(z);
            }
        }
    }

    public SwitchPreference(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        this.Y = new a();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SwitchPreference, i2, i3);
        d((CharSequence) g.b(obtainStyledAttributes, R$styleable.SwitchPreference_summaryOn, R$styleable.SwitchPreference_android_summaryOn));
        c(g.b(obtainStyledAttributes, R$styleable.SwitchPreference_summaryOff, R$styleable.SwitchPreference_android_summaryOff));
        f(g.b(obtainStyledAttributes, R$styleable.SwitchPreference_switchTextOn, R$styleable.SwitchPreference_android_switchTextOn));
        e(g.b(obtainStyledAttributes, R$styleable.SwitchPreference_switchTextOff, R$styleable.SwitchPreference_android_switchTextOff));
        e(g.a(obtainStyledAttributes, R$styleable.SwitchPreference_disableDependentsState, R$styleable.SwitchPreference_android_disableDependentsState, false));
        obtainStyledAttributes.recycle();
    }

    private void c(View view) {
        boolean z = view instanceof Switch;
        if (z) {
            ((Switch) view).setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
        }
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(this.T);
        }
        if (z) {
            Switch switchR = (Switch) view;
            switchR.setTextOn(this.Z);
            switchR.setTextOff(this.a0);
            switchR.setOnCheckedChangeListener(this.Y);
        }
    }

    private void d(View view) {
        if (((AccessibilityManager) b().getSystemService("accessibility")).isEnabled()) {
            c(view.findViewById(16908352));
            b(view.findViewById(16908304));
        }
    }

    public void a(l lVar) {
        super.a(lVar);
        c(lVar.a(16908352));
        b(lVar);
    }

    public void e(CharSequence charSequence) {
        this.a0 = charSequence;
        y();
    }

    public void f(CharSequence charSequence) {
        this.Z = charSequence;
        y();
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        super.a(view);
        d(view);
    }

    public SwitchPreference(Context context, AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, 0);
    }

    public SwitchPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, g.a(context, R$attr.switchPreferenceStyle, 16843629));
    }
}
