package androidx.core.f;

import android.os.Build;
import android.text.TextUtils;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.util.Locale;

/* compiled from: TextUtilsCompat */
public final class f {
    private static final Locale a = new Locale(BuildConfig.FLAVOR, BuildConfig.FLAVOR);

    private static int a(Locale locale) {
        byte directionality = Character.getDirectionality(locale.getDisplayName(locale).charAt(0));
        return (directionality == 1 || directionality == 2) ? 1 : 0;
    }

    public static int b(Locale locale) {
        if (Build.VERSION.SDK_INT >= 17) {
            return TextUtils.getLayoutDirectionFromLocale(locale);
        }
        if (locale == null || locale.equals(a)) {
            return 0;
        }
        String b = b.b(locale);
        if (b == null) {
            return a(locale);
        }
        return (b.equalsIgnoreCase("Arab") || b.equalsIgnoreCase("Hebr")) ? 1 : 0;
    }
}
