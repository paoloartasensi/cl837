package androidx.appcompat.c;

import android.content.Context;
import android.graphics.Rect;
import android.text.method.TransformationMethod;
import android.view.View;
import java.util.Locale;

/* compiled from: AllCapsTransformationMethod */
public class a implements TransformationMethod {
    private Locale e;

    public a(Context context) {
        this.e = context.getResources().getConfiguration().locale;
    }

    public CharSequence getTransformation(CharSequence charSequence, View view) {
        if (charSequence != null) {
            return charSequence.toString().toUpperCase(this.e);
        }
        return null;
    }

    public void onFocusChanged(View view, CharSequence charSequence, boolean z, int i2, Rect rect) {
    }
}
