package androidx.databinding.o;

import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.widget.TextView;
import androidx.databinding.h;
import androidx.databinding.library.baseAdapters.R$id;

/* compiled from: TextViewBindingAdapter */
public class c {

    /* compiled from: TextViewBindingAdapter */
    static class a implements TextWatcher {
        final /* synthetic */ C0031c e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ d f568f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ h f569g;

        /* renamed from: h  reason: collision with root package name */
        final /* synthetic */ b f570h;

        a(C0031c cVar, d dVar, h hVar, b bVar) {
            this.e = cVar;
            this.f568f = dVar;
            this.f569g = hVar;
            this.f570h = bVar;
        }

        public void afterTextChanged(Editable editable) {
            b bVar = this.f570h;
            if (bVar != null) {
                bVar.afterTextChanged(editable);
            }
        }

        public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            C0031c cVar = this.e;
            if (cVar != null) {
                cVar.beforeTextChanged(charSequence, i2, i3, i4);
            }
        }

        public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            d dVar = this.f568f;
            if (dVar != null) {
                dVar.onTextChanged(charSequence, i2, i3, i4);
            }
            h hVar = this.f569g;
            if (hVar != null) {
                hVar.a();
            }
        }
    }

    /* compiled from: TextViewBindingAdapter */
    public interface b {
        void afterTextChanged(Editable editable);
    }

    /* renamed from: androidx.databinding.o.c$c  reason: collision with other inner class name */
    /* compiled from: TextViewBindingAdapter */
    public interface C0031c {
        void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4);
    }

    /* compiled from: TextViewBindingAdapter */
    public interface d {
        void onTextChanged(CharSequence charSequence, int i2, int i3, int i4);
    }

    public static void a(TextView textView, CharSequence charSequence) {
        CharSequence text = textView.getText();
        if (charSequence == text) {
            return;
        }
        if (charSequence != null || text.length() != 0) {
            if (charSequence instanceof Spanned) {
                if (charSequence.equals(text)) {
                    return;
                }
            } else if (!a(charSequence, text)) {
                return;
            }
            textView.setText(charSequence);
        }
    }

    public static String a(TextView textView) {
        return textView.getText().toString();
    }

    private static boolean a(CharSequence charSequence, CharSequence charSequence2) {
        if ((charSequence == null) != (charSequence2 == null)) {
            return true;
        }
        if (charSequence == null) {
            return false;
        }
        int length = charSequence.length();
        if (length != charSequence2.length()) {
            return true;
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (charSequence.charAt(i2) != charSequence2.charAt(i2)) {
                return true;
            }
        }
        return false;
    }

    public static void a(TextView textView, C0031c cVar, d dVar, b bVar, h hVar) {
        a aVar = (cVar == null && bVar == null && dVar == null && hVar == null) ? null : new a(cVar, dVar, hVar, bVar);
        TextWatcher textWatcher = (TextWatcher) b.a(textView, aVar, R$id.textWatcher);
        if (textWatcher != null) {
            textView.removeTextChangedListener(textWatcher);
        }
        if (aVar != null) {
            textView.addTextChangedListener(aVar);
        }
    }
}
