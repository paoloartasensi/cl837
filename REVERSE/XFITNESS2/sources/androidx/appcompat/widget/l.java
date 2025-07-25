package androidx.appcompat.widget;

import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.widget.TextView;
import androidx.core.g.h;

/* compiled from: AppCompatTextClassifierHelper */
final class l {
    private TextView a;
    private TextClassifier b;

    l(TextView textView) {
        h.a(textView);
        this.a = textView;
    }

    public void a(TextClassifier textClassifier) {
        this.b = textClassifier;
    }

    public TextClassifier a() {
        TextClassifier textClassifier = this.b;
        if (textClassifier != null) {
            return textClassifier;
        }
        TextClassificationManager textClassificationManager = (TextClassificationManager) this.a.getContext().getSystemService(TextClassificationManager.class);
        if (textClassificationManager != null) {
            return textClassificationManager.getTextClassifier();
        }
        return TextClassifier.NO_OP;
    }
}
