package androidx.appcompat.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;
import androidx.appcompat.R$styleable;
import androidx.core.widget.h;

/* compiled from: AppCompatPopupWindow */
class i extends PopupWindow {
    private static final boolean b = (Build.VERSION.SDK_INT < 21);
    private boolean a;

    public i(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        a(context, attributeSet, i2, i3);
    }

    private void a(Context context, AttributeSet attributeSet, int i2, int i3) {
        g0 a2 = g0.a(context, attributeSet, R$styleable.PopupWindow, i2, i3);
        if (a2.g(R$styleable.PopupWindow_overlapAnchor)) {
            a(a2.a(R$styleable.PopupWindow_overlapAnchor, false));
        }
        setBackgroundDrawable(a2.b(R$styleable.PopupWindow_android_popupBackground));
        a2.a();
    }

    public void showAsDropDown(View view, int i2, int i3) {
        if (b && this.a) {
            i3 -= view.getHeight();
        }
        super.showAsDropDown(view, i2, i3);
    }

    public void update(View view, int i2, int i3, int i4, int i5) {
        if (b && this.a) {
            i3 -= view.getHeight();
        }
        super.update(view, i2, i3, i4, i5);
    }

    public void showAsDropDown(View view, int i2, int i3, int i4) {
        if (b && this.a) {
            i3 -= view.getHeight();
        }
        super.showAsDropDown(view, i2, i3, i4);
    }

    private void a(boolean z) {
        if (b) {
            this.a = z;
        } else {
            h.a((PopupWindow) this, z);
        }
    }
}
