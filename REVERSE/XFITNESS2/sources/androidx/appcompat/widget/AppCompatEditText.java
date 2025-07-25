package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.textclassifier.TextClassifier;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.R$attr;
import androidx.core.h.u;
import androidx.core.widget.i;

public class AppCompatEditText extends EditText implements u {
    private final d e;

    /* renamed from: f  reason: collision with root package name */
    private final m f205f;

    /* renamed from: g  reason: collision with root package name */
    private final l f206g;

    public AppCompatEditText(Context context) {
        this(context, (AttributeSet) null);
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        d dVar = this.e;
        if (dVar != null) {
            dVar.a();
        }
        m mVar = this.f205f;
        if (mVar != null) {
            mVar.a();
        }
    }

    public ColorStateList getSupportBackgroundTintList() {
        d dVar = this.e;
        if (dVar != null) {
            return dVar.b();
        }
        return null;
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        d dVar = this.e;
        if (dVar != null) {
            return dVar.c();
        }
        return null;
    }

    public TextClassifier getTextClassifier() {
        l lVar;
        if (Build.VERSION.SDK_INT >= 28 || (lVar = this.f206g) == null) {
            return super.getTextClassifier();
        }
        return lVar.a();
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
        g.a(onCreateInputConnection, editorInfo, this);
        return onCreateInputConnection;
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        d dVar = this.e;
        if (dVar != null) {
            dVar.a(drawable);
        }
    }

    public void setBackgroundResource(int i2) {
        super.setBackgroundResource(i2);
        d dVar = this.e;
        if (dVar != null) {
            dVar.a(i2);
        }
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(i.a((TextView) this, callback));
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        d dVar = this.e;
        if (dVar != null) {
            dVar.b(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        d dVar = this.e;
        if (dVar != null) {
            dVar.a(mode);
        }
    }

    public void setTextAppearance(Context context, int i2) {
        super.setTextAppearance(context, i2);
        m mVar = this.f205f;
        if (mVar != null) {
            mVar.a(context, i2);
        }
    }

    public void setTextClassifier(TextClassifier textClassifier) {
        l lVar;
        if (Build.VERSION.SDK_INT >= 28 || (lVar = this.f206g) == null) {
            super.setTextClassifier(textClassifier);
        } else {
            lVar.a(textClassifier);
        }
    }

    public AppCompatEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.editTextStyle);
    }

    public Editable getText() {
        if (Build.VERSION.SDK_INT >= 28) {
            return super.getText();
        }
        return super.getEditableText();
    }

    public AppCompatEditText(Context context, AttributeSet attributeSet, int i2) {
        super(d0.b(context), attributeSet, i2);
        d dVar = new d(this);
        this.e = dVar;
        dVar.a(attributeSet, i2);
        m mVar = new m(this);
        this.f205f = mVar;
        mVar.a(attributeSet, i2);
        this.f205f.a();
        this.f206g = new l(this);
    }
}
