package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.textclassifier.TextClassifier;
import android.widget.TextView;
import androidx.appcompat.a.a.a;
import androidx.core.f.c;
import androidx.core.h.u;
import androidx.core.widget.b;
import androidx.core.widget.i;
import androidx.core.widget.k;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AppCompatTextView extends TextView implements u, k, b {
    private final d e;

    /* renamed from: f  reason: collision with root package name */
    private final m f223f;

    /* renamed from: g  reason: collision with root package name */
    private final l f224g;

    /* renamed from: h  reason: collision with root package name */
    private Future<c> f225h;

    public AppCompatTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    private void d() {
        Future<c> future = this.f225h;
        if (future != null) {
            try {
                this.f225h = null;
                i.a((TextView) this, future.get());
            } catch (InterruptedException | ExecutionException unused) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        d dVar = this.e;
        if (dVar != null) {
            dVar.a();
        }
        m mVar = this.f223f;
        if (mVar != null) {
            mVar.a();
        }
    }

    public int getAutoSizeMaxTextSize() {
        if (b.a) {
            return super.getAutoSizeMaxTextSize();
        }
        m mVar = this.f223f;
        if (mVar != null) {
            return mVar.c();
        }
        return -1;
    }

    public int getAutoSizeMinTextSize() {
        if (b.a) {
            return super.getAutoSizeMinTextSize();
        }
        m mVar = this.f223f;
        if (mVar != null) {
            return mVar.d();
        }
        return -1;
    }

    public int getAutoSizeStepGranularity() {
        if (b.a) {
            return super.getAutoSizeStepGranularity();
        }
        m mVar = this.f223f;
        if (mVar != null) {
            return mVar.e();
        }
        return -1;
    }

    public int[] getAutoSizeTextAvailableSizes() {
        if (b.a) {
            return super.getAutoSizeTextAvailableSizes();
        }
        m mVar = this.f223f;
        return mVar != null ? mVar.f() : new int[0];
    }

    @SuppressLint({"WrongConstant"})
    public int getAutoSizeTextType() {
        if (!b.a) {
            m mVar = this.f223f;
            if (mVar != null) {
                return mVar.g();
            }
            return 0;
        } else if (super.getAutoSizeTextType() == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getFirstBaselineToTopHeight() {
        return i.b(this);
    }

    public int getLastBaselineToBottomHeight() {
        return i.c(this);
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

    public ColorStateList getSupportCompoundDrawablesTintList() {
        return this.f223f.h();
    }

    public PorterDuff.Mode getSupportCompoundDrawablesTintMode() {
        return this.f223f.i();
    }

    public CharSequence getText() {
        d();
        return super.getText();
    }

    public TextClassifier getTextClassifier() {
        l lVar;
        if (Build.VERSION.SDK_INT >= 28 || (lVar = this.f224g) == null) {
            return super.getTextClassifier();
        }
        return lVar.a();
    }

    public c.a getTextMetricsParamsCompat() {
        return i.f(this);
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
        g.a(onCreateInputConnection, editorInfo, this);
        return onCreateInputConnection;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        super.onLayout(z, i2, i3, i4, i5);
        m mVar = this.f223f;
        if (mVar != null) {
            mVar.a(z, i2, i3, i4, i5);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        d();
        super.onMeasure(i2, i3);
    }

    /* access modifiers changed from: protected */
    public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        super.onTextChanged(charSequence, i2, i3, i4);
        m mVar = this.f223f;
        if (mVar != null && !b.a && mVar.j()) {
            this.f223f.b();
        }
    }

    public void setAutoSizeTextTypeUniformWithConfiguration(int i2, int i3, int i4, int i5) {
        if (b.a) {
            super.setAutoSizeTextTypeUniformWithConfiguration(i2, i3, i4, i5);
            return;
        }
        m mVar = this.f223f;
        if (mVar != null) {
            mVar.a(i2, i3, i4, i5);
        }
    }

    public void setAutoSizeTextTypeUniformWithPresetSizes(int[] iArr, int i2) {
        if (b.a) {
            super.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i2);
            return;
        }
        m mVar = this.f223f;
        if (mVar != null) {
            mVar.a(iArr, i2);
        }
    }

    public void setAutoSizeTextTypeWithDefaults(int i2) {
        if (b.a) {
            super.setAutoSizeTextTypeWithDefaults(i2);
            return;
        }
        m mVar = this.f223f;
        if (mVar != null) {
            mVar.a(i2);
        }
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

    public void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        m mVar = this.f223f;
        if (mVar != null) {
            mVar.k();
        }
    }

    public void setCompoundDrawablesRelative(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        m mVar = this.f223f;
        if (mVar != null) {
            mVar.k();
        }
    }

    public void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        m mVar = this.f223f;
        if (mVar != null) {
            mVar.k();
        }
    }

    public void setCompoundDrawablesWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        super.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        m mVar = this.f223f;
        if (mVar != null) {
            mVar.k();
        }
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(i.a((TextView) this, callback));
    }

    public void setFirstBaselineToTopHeight(int i2) {
        if (Build.VERSION.SDK_INT >= 28) {
            super.setFirstBaselineToTopHeight(i2);
        } else {
            i.a((TextView) this, i2);
        }
    }

    public void setLastBaselineToBottomHeight(int i2) {
        if (Build.VERSION.SDK_INT >= 28) {
            super.setLastBaselineToBottomHeight(i2);
        } else {
            i.b(this, i2);
        }
    }

    public void setLineHeight(int i2) {
        i.c(this, i2);
    }

    public void setPrecomputedText(c cVar) {
        i.a((TextView) this, cVar);
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

    public void setSupportCompoundDrawablesTintList(ColorStateList colorStateList) {
        this.f223f.a(colorStateList);
        this.f223f.a();
    }

    public void setSupportCompoundDrawablesTintMode(PorterDuff.Mode mode) {
        this.f223f.a(mode);
        this.f223f.a();
    }

    public void setTextAppearance(Context context, int i2) {
        super.setTextAppearance(context, i2);
        m mVar = this.f223f;
        if (mVar != null) {
            mVar.a(context, i2);
        }
    }

    public void setTextClassifier(TextClassifier textClassifier) {
        l lVar;
        if (Build.VERSION.SDK_INT >= 28 || (lVar = this.f224g) == null) {
            super.setTextClassifier(textClassifier);
        } else {
            lVar.a(textClassifier);
        }
    }

    public void setTextFuture(Future<c> future) {
        this.f225h = future;
        if (future != null) {
            requestLayout();
        }
    }

    public void setTextMetricsParamsCompat(c.a aVar) {
        i.a((TextView) this, aVar);
    }

    public void setTextSize(int i2, float f2) {
        if (b.a) {
            super.setTextSize(i2, f2);
            return;
        }
        m mVar = this.f223f;
        if (mVar != null) {
            mVar.a(i2, f2);
        }
    }

    public void setTypeface(Typeface typeface, int i2) {
        Typeface a = (typeface == null || i2 <= 0) ? null : androidx.core.a.c.a(getContext(), typeface, i2);
        if (a != null) {
            typeface = a;
        }
        super.setTypeface(typeface, i2);
    }

    public AppCompatTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public AppCompatTextView(Context context, AttributeSet attributeSet, int i2) {
        super(d0.b(context), attributeSet, i2);
        d dVar = new d(this);
        this.e = dVar;
        dVar.a(attributeSet, i2);
        m mVar = new m(this);
        this.f223f = mVar;
        mVar.a(attributeSet, i2);
        this.f223f.a();
        this.f224g = new l(this);
    }

    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int i2, int i3, int i4, int i5) {
        Context context = getContext();
        Drawable drawable = null;
        Drawable c = i2 != 0 ? a.c(context, i2) : null;
        Drawable c2 = i3 != 0 ? a.c(context, i3) : null;
        Drawable c3 = i4 != 0 ? a.c(context, i4) : null;
        if (i5 != 0) {
            drawable = a.c(context, i5);
        }
        setCompoundDrawablesRelativeWithIntrinsicBounds(c, c2, c3, drawable);
        m mVar = this.f223f;
        if (mVar != null) {
            mVar.k();
        }
    }

    public void setCompoundDrawablesWithIntrinsicBounds(int i2, int i3, int i4, int i5) {
        Context context = getContext();
        Drawable drawable = null;
        Drawable c = i2 != 0 ? a.c(context, i2) : null;
        Drawable c2 = i3 != 0 ? a.c(context, i3) : null;
        Drawable c3 = i4 != 0 ? a.c(context, i4) : null;
        if (i5 != 0) {
            drawable = a.c(context, i5);
        }
        setCompoundDrawablesWithIntrinsicBounds(c, c2, c3, drawable);
        m mVar = this.f223f;
        if (mVar != null) {
            mVar.k();
        }
    }
}
