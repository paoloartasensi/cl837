package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.core.content.c.g;

public abstract class DialogPreference extends Preference {
    private CharSequence T;
    private CharSequence U;
    private Drawable V;
    private CharSequence W;
    private CharSequence X;
    private int Y;

    public interface a {
        <T extends Preference> T a(CharSequence charSequence);
    }

    public DialogPreference(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.DialogPreference, i2, i3);
        String b = g.b(obtainStyledAttributes, R$styleable.DialogPreference_dialogTitle, R$styleable.DialogPreference_android_dialogTitle);
        this.T = b;
        if (b == null) {
            this.T = q();
        }
        this.U = g.b(obtainStyledAttributes, R$styleable.DialogPreference_dialogMessage, R$styleable.DialogPreference_android_dialogMessage);
        this.V = g.a(obtainStyledAttributes, R$styleable.DialogPreference_dialogIcon, R$styleable.DialogPreference_android_dialogIcon);
        this.W = g.b(obtainStyledAttributes, R$styleable.DialogPreference_positiveButtonText, R$styleable.DialogPreference_android_positiveButtonText);
        this.X = g.b(obtainStyledAttributes, R$styleable.DialogPreference_negativeButtonText, R$styleable.DialogPreference_android_negativeButtonText);
        this.Y = g.b(obtainStyledAttributes, R$styleable.DialogPreference_dialogLayout, R$styleable.DialogPreference_android_dialogLayout, 0);
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void B() {
        m().a((Preference) this);
    }

    public Drawable H() {
        return this.V;
    }

    public int I() {
        return this.Y;
    }

    public CharSequence J() {
        return this.U;
    }

    public CharSequence K() {
        return this.T;
    }

    public CharSequence L() {
        return this.X;
    }

    public CharSequence M() {
        return this.W;
    }

    public DialogPreference(Context context, AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, 0);
    }

    public DialogPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, g.a(context, R$attr.dialogPreferenceStyle, 16842897));
    }
}
