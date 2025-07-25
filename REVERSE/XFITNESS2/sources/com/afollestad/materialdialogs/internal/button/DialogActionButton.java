package com.afollestad.materialdialogs.internal.button;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import com.afollestad.materialdialogs.R$attr;
import com.afollestad.materialdialogs.R$color;
import com.afollestad.materialdialogs.d;
import com.afollestad.materialdialogs.j.e;
import com.afollestad.materialdialogs.j.f;
import kotlin.jvm.internal.i;

/* compiled from: DialogActionButton.kt */
public final class DialogActionButton extends AppCompatButton {

    /* renamed from: g  reason: collision with root package name */
    private int f983g;

    /* renamed from: h  reason: collision with root package name */
    private int f984h;

    /* renamed from: i  reason: collision with root package name */
    private Integer f985i;

    /* compiled from: DialogActionButton.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    static {
        new a((f) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ DialogActionButton(Context context, AttributeSet attributeSet, int i2, f fVar) {
        this(context, (i2 & 2) != 0 ? null : attributeSet);
    }

    public final void a(Context context, Context context2, boolean z) {
        int i2;
        int a2;
        i.b(context, "baseContext");
        i.b(context2, "appContext");
        boolean z2 = true;
        if (e.a.a(context2, R$attr.md_button_casing, 1) != 1) {
            z2 = false;
        }
        setSupportAllCaps(z2);
        boolean a3 = d.a(context2);
        this.f983g = e.a(e.a, context2, (Integer) null, Integer.valueOf(R$attr.md_color_button_text), (kotlin.jvm.b.a) new DialogActionButton$update$1(context2), 2, (Object) null);
        if (a3) {
            i2 = R$color.md_disabled_text_light_theme;
        } else {
            i2 = R$color.md_disabled_text_dark_theme;
        }
        this.f984h = e.a(e.a, context, Integer.valueOf(i2), (Integer) null, (kotlin.jvm.b.a) null, 12, (Object) null);
        Integer num = this.f985i;
        setTextColor(num != null ? num.intValue() : this.f983g);
        Drawable a4 = e.a(e.a, context, (Integer) null, Integer.valueOf(R$attr.md_button_selector), (Drawable) null, 10, (Object) null);
        if (Build.VERSION.SDK_INT >= 21 && (a4 instanceof RippleDrawable) && (a2 = e.a(e.a, context, (Integer) null, Integer.valueOf(R$attr.md_ripple_color), (kotlin.jvm.b.a) new DialogActionButton$update$2(context2), 2, (Object) null)) != 0) {
            ((RippleDrawable) a4).setColor(ColorStateList.valueOf(a2));
        }
        setBackground(a4);
        if (z) {
            f.a((TextView) this);
        } else {
            setGravity(17);
        }
        setEnabled(isEnabled());
    }

    public void setEnabled(boolean z) {
        int i2;
        super.setEnabled(z);
        if (z) {
            Integer num = this.f985i;
            i2 = num != null ? num.intValue() : this.f983g;
        } else {
            i2 = this.f984h;
        }
        setTextColor(i2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DialogActionButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        i.b(context, "context");
        setClickable(true);
        setFocusable(true);
    }
}
