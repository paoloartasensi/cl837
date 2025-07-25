package androidx.appcompat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.CheckedTextView;
import android.widget.TextView;
import androidx.appcompat.a.a.a;
import androidx.core.widget.i;

public class AppCompatCheckedTextView extends CheckedTextView {

    /* renamed from: f  reason: collision with root package name */
    private static final int[] f204f = {16843016};
    private final m e;

    public AppCompatCheckedTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        m mVar = this.e;
        if (mVar != null) {
            mVar.a();
        }
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
        g.a(onCreateInputConnection, editorInfo, this);
        return onCreateInputConnection;
    }

    public void setCheckMarkDrawable(int i2) {
        setCheckMarkDrawable(a.c(getContext(), i2));
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(i.a((TextView) this, callback));
    }

    public void setTextAppearance(Context context, int i2) {
        super.setTextAppearance(context, i2);
        m mVar = this.e;
        if (mVar != null) {
            mVar.a(context, i2);
        }
    }

    public AppCompatCheckedTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843720);
    }

    public AppCompatCheckedTextView(Context context, AttributeSet attributeSet, int i2) {
        super(d0.b(context), attributeSet, i2);
        m mVar = new m(this);
        this.e = mVar;
        mVar.a(attributeSet, i2);
        this.e.a();
        g0 a = g0.a(getContext(), attributeSet, f204f, i2, 0);
        setCheckMarkDrawable(a.b(0));
        a.a();
    }
}
