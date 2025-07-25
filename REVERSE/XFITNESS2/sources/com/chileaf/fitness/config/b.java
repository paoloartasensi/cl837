package com.chileaf.fitness.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.util.Locale;
import kotlin.jvm.internal.i;

/* compiled from: LocaleManager.kt */
public final class b {
    private static Locale a;
    public static final b b = new b();

    private b() {
    }

    private final Locale c(Context context) {
        SharedPreferences d = d(context);
        String str = BuildConfig.FLAVOR;
        String string = d.getString("locale_manager_language", str);
        if (string == null) {
            string = str;
        }
        i.a((Object) string, "sharedPreferences.getString(LANGUAGE, \"\") ?: \"\"");
        String string2 = d.getString("locale_manager_country", str);
        if (string2 == null) {
            string2 = str;
        }
        i.a((Object) string2, "sharedPreferences.getString(COUNTRY, \"\") ?: \"\"");
        String string3 = d.getString("locale_manager_variant", str);
        if (string3 != null) {
            str = string3;
        }
        i.a((Object) str, "sharedPreferences.getString(VARIANT, \"\") ?: \"\"");
        boolean z = true;
        if (string.length() == 0) {
            if (string2.length() == 0) {
                if (str.length() != 0) {
                    z = false;
                }
                if (z) {
                    return null;
                }
            }
        }
        return new Locale(string, string2, str);
    }

    private final SharedPreferences d(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("sp_locale", 0);
        i.a((Object) sharedPreferences, "context.getSharedPrefere…e\", Context.MODE_PRIVATE)");
        return sharedPreferences;
    }

    public final void a(Context context, Locale locale) {
        i.b(context, "context");
        i.b(locale, "locale");
        c(context, locale);
        b(context, locale);
    }

    public final void b(Context context) {
        i.b(context, "context");
        Locale c = c(context);
        if (c == null) {
            c = a;
        }
        if (c == null) {
            c = a();
        }
        b(context, c);
    }

    private final void b(Context context, Locale locale) {
        Context applicationContext = context.getApplicationContext();
        i.a((Object) applicationContext, "context.applicationContext");
        Resources resources = applicationContext.getResources();
        i.a((Object) resources, "resources");
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        if (Build.VERSION.SDK_INT >= 24) {
            LocaleList localeList = new LocaleList(new Locale[]{locale});
            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);
            context.getApplicationContext().createConfigurationContext(configuration);
            if (locale != null) {
                Locale.setDefault(locale);
            }
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }

    public final Context a(Context context) {
        if (context == null) {
            return context;
        }
        Locale c = c(context);
        if (c == null) {
            c = a;
        }
        if (c == null) {
            c = a();
        }
        Resources resources = context.getResources();
        i.a((Object) resources, "res");
        Configuration configuration = new Configuration(resources.getConfiguration());
        if (Build.VERSION.SDK_INT >= 24) {
            configuration.setLocale(c);
            return context.createConfigurationContext(configuration);
        }
        configuration.locale = c;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

    private final void c(Context context, Locale locale) {
        d(context).edit().putString("locale_manager_language", locale.getLanguage()).putString("locale_manager_country", locale.getCountry()).putString("locale_manager_variant", locale.getVariant()).commit();
    }

    public final Locale a() {
        if (Build.VERSION.SDK_INT >= 24) {
            Locale locale = LocaleList.getDefault().get(0);
            i.a((Object) locale, "LocaleList.getDefault().get(0)");
            return locale;
        }
        Locale locale2 = Locale.getDefault();
        i.a((Object) locale2, "Locale.getDefault()");
        return locale2;
    }
}
