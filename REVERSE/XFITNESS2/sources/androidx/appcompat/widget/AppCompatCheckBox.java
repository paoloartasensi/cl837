package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CheckBox;
import androidx.appcompat.R$attr;
import androidx.appcompat.a.a.a;
import androidx.core.h.u;
import androidx.core.widget.j;

public class AppCompatCheckBox extends CheckBox implements j, u {
    private final e e;

    /* renamed from: f  reason: collision with root package name */
    private final d f202f;

    /* renamed from: g  reason: collision with root package name */
    private final m f203g;

    public AppCompatCheckBox(Context context) {
        this(context, (AttributeSet) null);
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        d dVar = this.f202f;
        if (dVar != null) {
            dVar.a();
        }
        m mVar = this.f203g;
        if (mVar != null) {
            mVar.a();
        }
    }

    public int getCompoundPaddingLeft() {
        int compoundPaddingLeft = super.getCompoundPaddingLeft();
        e eVar = this.e;
        return eVar != null ? eVar.a(compoundPaddingLeft) : compoundPaddingLeft;
    }

    public ColorStateList getSupportBackgroundTintList() {
        d dVar = this.f202f;
        if (dVar != null) {
            return dVar.b();
        }
        return null;
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        d dVar = this.f202f;
        if (dVar != null) {
            return dVar.c();
        }
        return null;
    }

    public ColorStateList getSupportButtonTintList() {
        e eVar = this.e;
        if (eVar != null) {
            return eVar.b();
        }
        return null;
    }

    public PorterDuff.Mode getSupportButtonTintMode() {
        e eVar = this.e;
        if (eVar != null) {
            return eVar.c();
        }
        return null;
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        d dVar = this.f202f;
        if (dVar != null) {
            dVar.a(drawable);
        }
    }

    public void setBackgroundResource(int i2) {
        super.setBackgroundResource(i2);
        d dVar = this.f202f;
        if (dVar != null) {
            dVar.a(i2);
        }
    }

    public void setButtonDrawable(Drawable drawable) {
        super.setButtonDrawable(drawable);
        e eVar = this.e;
        if (eVar != null) {
            eVar.d();
        }
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        d dVar = this.f202f;
        if (dVar != null) {
            dVar.b(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        d dVar = this.f202f;
        if (dVar != null) {
            dVar.a(mode);
        }
    }

    public void setSupportButtonTintList(ColorStateList colorStateList) {
        e eVar = this.e;
        if (eVar != null) {
            eVar.a(colorStateList);
        }
    }

    public void setSupportButtonTintMode(PorterDuff.Mode mode) {
        e eVar = this.e;
        if (eVar != null) {
            eVar.a(mode);
        }
    }

    public AppCompatCheckBox(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.checkboxStyle);
    }

    public AppCompatCheckBox(Context context, AttributeSet attributeSet, int i2) {
        super(d0.b(context), attributeSet, i2);
        e eVar = new e(this);
        this.e = eVar;
        eVar.a(attributeSet, i2);
        d dVar = new d(this);
        this.f202f = dVar;
        dVar.a(attributeSet, i2);
        m mVar = new m(this);
        this.f203g = mVar;
        mVar.a(attributeSet, i2);
    }

    public void setButtonDrawable(int i2) {
        setButtonDrawable(a.c(getContext(), i2));
    }
}
