package kotlin.s;

import kotlin.jvm.b.l;
import kotlin.jvm.internal.i;

/* compiled from: Appendable.kt */
class d {
    public static <T> void a(Appendable appendable, T t, l<? super T, ? extends CharSequence> lVar) {
        i.b(appendable, "$this$appendElement");
        if (lVar != null) {
            appendable.append((CharSequence) lVar.invoke(t));
            return;
        }
        if (t != null ? t instanceof CharSequence : true) {
            appendable.append((CharSequence) t);
        } else if (t instanceof Character) {
            appendable.append(((Character) t).charValue());
        } else {
            appendable.append(String.valueOf(t));
        }
    }
}
