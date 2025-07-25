package androidx.core.f;

import android.os.Build;
import android.text.PrecomputedText;
import android.text.Spannable;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.MetricAffectingSpan;

/* compiled from: PrecomputedTextCompat */
public class c implements Spannable {
    private final Spannable e;

    /* renamed from: f  reason: collision with root package name */
    private final a f493f;

    /* compiled from: PrecomputedTextCompat */
    public static final class a {
        private final TextPaint a;
        private final TextDirectionHeuristic b;
        private final int c;
        private final int d;
        final PrecomputedText.Params e = null;

        /* renamed from: androidx.core.f.c$a$a  reason: collision with other inner class name */
        /* compiled from: PrecomputedTextCompat */
        public static class C0021a {
            private final TextPaint a;
            private TextDirectionHeuristic b;
            private int c;
            private int d;

            public C0021a(TextPaint textPaint) {
                this.a = textPaint;
                if (Build.VERSION.SDK_INT >= 23) {
                    this.c = 1;
                    this.d = 1;
                } else {
                    this.d = 0;
                    this.c = 0;
                }
                if (Build.VERSION.SDK_INT >= 18) {
                    this.b = TextDirectionHeuristics.FIRSTSTRONG_LTR;
                } else {
                    this.b = null;
                }
            }

            public C0021a a(int i2) {
                this.c = i2;
                return this;
            }

            public C0021a b(int i2) {
                this.d = i2;
                return this;
            }

            public C0021a a(TextDirectionHeuristic textDirectionHeuristic) {
                this.b = textDirectionHeuristic;
                return this;
            }

            public a a() {
                return new a(this.a, this.b, this.c, this.d);
            }
        }

        a(TextPaint textPaint, TextDirectionHeuristic textDirectionHeuristic, int i2, int i3) {
            this.a = textPaint;
            this.b = textDirectionHeuristic;
            this.c = i2;
            this.d = i3;
        }

        public int a() {
            return this.c;
        }

        public int b() {
            return this.d;
        }

        public TextDirectionHeuristic c() {
            return this.b;
        }

        public TextPaint d() {
            return this.a;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            if (!a(aVar)) {
                return false;
            }
            return Build.VERSION.SDK_INT < 18 || this.b == aVar.c();
        }

        public int hashCode() {
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 24) {
                return androidx.core.g.c.a(Float.valueOf(this.a.getTextSize()), Float.valueOf(this.a.getTextScaleX()), Float.valueOf(this.a.getTextSkewX()), Float.valueOf(this.a.getLetterSpacing()), Integer.valueOf(this.a.getFlags()), this.a.getTextLocales(), this.a.getTypeface(), Boolean.valueOf(this.a.isElegantTextHeight()), this.b, Integer.valueOf(this.c), Integer.valueOf(this.d));
            } else if (i2 >= 21) {
                return androidx.core.g.c.a(Float.valueOf(this.a.getTextSize()), Float.valueOf(this.a.getTextScaleX()), Float.valueOf(this.a.getTextSkewX()), Float.valueOf(this.a.getLetterSpacing()), Integer.valueOf(this.a.getFlags()), this.a.getTextLocale(), this.a.getTypeface(), Boolean.valueOf(this.a.isElegantTextHeight()), this.b, Integer.valueOf(this.c), Integer.valueOf(this.d));
            } else if (i2 >= 18) {
                return androidx.core.g.c.a(Float.valueOf(this.a.getTextSize()), Float.valueOf(this.a.getTextScaleX()), Float.valueOf(this.a.getTextSkewX()), Integer.valueOf(this.a.getFlags()), this.a.getTextLocale(), this.a.getTypeface(), this.b, Integer.valueOf(this.c), Integer.valueOf(this.d));
            } else if (i2 >= 17) {
                return androidx.core.g.c.a(Float.valueOf(this.a.getTextSize()), Float.valueOf(this.a.getTextScaleX()), Float.valueOf(this.a.getTextSkewX()), Integer.valueOf(this.a.getFlags()), this.a.getTextLocale(), this.a.getTypeface(), this.b, Integer.valueOf(this.c), Integer.valueOf(this.d));
            } else {
                return androidx.core.g.c.a(Float.valueOf(this.a.getTextSize()), Float.valueOf(this.a.getTextScaleX()), Float.valueOf(this.a.getTextSkewX()), Integer.valueOf(this.a.getFlags()), this.a.getTypeface(), this.b, Integer.valueOf(this.c), Integer.valueOf(this.d));
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("{");
            sb.append("textSize=" + this.a.getTextSize());
            sb.append(", textScaleX=" + this.a.getTextScaleX());
            sb.append(", textSkewX=" + this.a.getTextSkewX());
            if (Build.VERSION.SDK_INT >= 21) {
                sb.append(", letterSpacing=" + this.a.getLetterSpacing());
                sb.append(", elegantTextHeight=" + this.a.isElegantTextHeight());
            }
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 24) {
                sb.append(", textLocale=" + this.a.getTextLocales());
            } else if (i2 >= 17) {
                sb.append(", textLocale=" + this.a.getTextLocale());
            }
            sb.append(", typeface=" + this.a.getTypeface());
            if (Build.VERSION.SDK_INT >= 26) {
                sb.append(", variationSettings=" + this.a.getFontVariationSettings());
            }
            sb.append(", textDir=" + this.b);
            sb.append(", breakStrategy=" + this.c);
            sb.append(", hyphenationFrequency=" + this.d);
            sb.append("}");
            return sb.toString();
        }

        public boolean a(a aVar) {
            PrecomputedText.Params params = this.e;
            if (params != null) {
                return params.equals(aVar.e);
            }
            if ((Build.VERSION.SDK_INT >= 23 && (this.c != aVar.a() || this.d != aVar.b())) || this.a.getTextSize() != aVar.d().getTextSize() || this.a.getTextScaleX() != aVar.d().getTextScaleX() || this.a.getTextSkewX() != aVar.d().getTextSkewX()) {
                return false;
            }
            if ((Build.VERSION.SDK_INT >= 21 && (this.a.getLetterSpacing() != aVar.d().getLetterSpacing() || !TextUtils.equals(this.a.getFontFeatureSettings(), aVar.d().getFontFeatureSettings()))) || this.a.getFlags() != aVar.d().getFlags()) {
                return false;
            }
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 24) {
                if (!this.a.getTextLocales().equals(aVar.d().getTextLocales())) {
                    return false;
                }
            } else if (i2 >= 17 && !this.a.getTextLocale().equals(aVar.d().getTextLocale())) {
                return false;
            }
            if (this.a.getTypeface() == null) {
                if (aVar.d().getTypeface() != null) {
                    return false;
                }
                return true;
            } else if (!this.a.getTypeface().equals(aVar.d().getTypeface())) {
                return false;
            } else {
                return true;
            }
        }

        public a(PrecomputedText.Params params) {
            this.a = params.getTextPaint();
            this.b = params.getTextDirection();
            this.c = params.getBreakStrategy();
            this.d = params.getHyphenationFrequency();
        }
    }

    public a a() {
        return this.f493f;
    }

    public char charAt(int i2) {
        return this.e.charAt(i2);
    }

    public int getSpanEnd(Object obj) {
        return this.e.getSpanEnd(obj);
    }

    public int getSpanFlags(Object obj) {
        return this.e.getSpanFlags(obj);
    }

    public int getSpanStart(Object obj) {
        return this.e.getSpanStart(obj);
    }

    public <T> T[] getSpans(int i2, int i3, Class<T> cls) {
        return this.e.getSpans(i2, i3, cls);
    }

    public int length() {
        return this.e.length();
    }

    public int nextSpanTransition(int i2, int i3, Class cls) {
        return this.e.nextSpanTransition(i2, i3, cls);
    }

    public void removeSpan(Object obj) {
        if (!(obj instanceof MetricAffectingSpan)) {
            this.e.removeSpan(obj);
            return;
        }
        throw new IllegalArgumentException("MetricAffectingSpan can not be removed from PrecomputedText.");
    }

    public void setSpan(Object obj, int i2, int i3, int i4) {
        if (!(obj instanceof MetricAffectingSpan)) {
            this.e.setSpan(obj, i2, i3, i4);
            return;
        }
        throw new IllegalArgumentException("MetricAffectingSpan can not be set to PrecomputedText.");
    }

    public CharSequence subSequence(int i2, int i3) {
        return this.e.subSequence(i2, i3);
    }

    public String toString() {
        return this.e.toString();
    }
}
