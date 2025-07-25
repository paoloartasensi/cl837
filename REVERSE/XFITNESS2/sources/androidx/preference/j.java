package androidx.preference;

import android.content.Context;
import android.content.SharedPreferences;

/* compiled from: PreferenceManager */
public class j {
    private Context a;
    private long b = 0;
    private SharedPreferences c;
    private e d;
    private SharedPreferences.Editor e;

    /* renamed from: f  reason: collision with root package name */
    private boolean f762f;

    /* renamed from: g  reason: collision with root package name */
    private String f763g;

    /* renamed from: h  reason: collision with root package name */
    private int f764h;

    /* renamed from: i  reason: collision with root package name */
    private int f765i = 0;

    /* renamed from: j  reason: collision with root package name */
    private PreferenceScreen f766j;
    private d k;
    private c l;
    private a m;
    private b n;

    /* compiled from: PreferenceManager */
    public interface a {
        void a(Preference preference);
    }

    /* compiled from: PreferenceManager */
    public interface b {
        void a(PreferenceScreen preferenceScreen);
    }

    /* compiled from: PreferenceManager */
    public interface c {
        boolean b(Preference preference);
    }

    /* compiled from: PreferenceManager */
    public static abstract class d {
        public abstract boolean a(Preference preference, Preference preference2);

        public abstract boolean b(Preference preference, Preference preference2);
    }

    public j(Context context) {
        this.a = context;
        a(b(context));
    }

    public static SharedPreferences a(Context context) {
        return context.getSharedPreferences(b(context), j());
    }

    private static String b(Context context) {
        return context.getPackageName() + "_preferences";
    }

    private static int j() {
        return 0;
    }

    public b c() {
        return this.n;
    }

    public c d() {
        return this.l;
    }

    public d e() {
        return this.k;
    }

    public e f() {
        return this.d;
    }

    public PreferenceScreen g() {
        return this.f766j;
    }

    public SharedPreferences h() {
        Context context;
        if (f() != null) {
            return null;
        }
        if (this.c == null) {
            if (this.f765i != 1) {
                context = this.a;
            } else {
                context = androidx.core.content.a.a(this.a);
            }
            this.c = context.getSharedPreferences(this.f763g, this.f764h);
        }
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        return !this.f762f;
    }

    public void setOnDisplayPreferenceDialogListener(a aVar) {
        this.m = aVar;
    }

    public void setOnNavigateToScreenListener(b bVar) {
        this.n = bVar;
    }

    public void setOnPreferenceTreeClickListener(c cVar) {
        this.l = cVar;
    }

    /* access modifiers changed from: package-private */
    public long b() {
        long j2;
        synchronized (this) {
            j2 = this.b;
            this.b = 1 + j2;
        }
        return j2;
    }

    public PreferenceScreen a(Context context, int i2, PreferenceScreen preferenceScreen) {
        a(true);
        PreferenceScreen preferenceScreen2 = (PreferenceScreen) new i(context, this).a(i2, (PreferenceGroup) preferenceScreen);
        preferenceScreen2.a(this);
        a(false);
        return preferenceScreen2;
    }

    public void a(String str) {
        this.f763g = str;
        this.c = null;
    }

    public boolean a(PreferenceScreen preferenceScreen) {
        PreferenceScreen preferenceScreen2 = this.f766j;
        if (preferenceScreen == preferenceScreen2) {
            return false;
        }
        if (preferenceScreen2 != null) {
            preferenceScreen2.C();
        }
        this.f766j = preferenceScreen;
        return true;
    }

    public <T extends Preference> T a(CharSequence charSequence) {
        PreferenceScreen preferenceScreen = this.f766j;
        if (preferenceScreen == null) {
            return null;
        }
        return preferenceScreen.c(charSequence);
    }

    /* access modifiers changed from: package-private */
    public SharedPreferences.Editor a() {
        if (this.d != null) {
            return null;
        }
        if (!this.f762f) {
            return h().edit();
        }
        if (this.e == null) {
            this.e = h().edit();
        }
        return this.e;
    }

    private void a(boolean z) {
        SharedPreferences.Editor editor;
        if (!z && (editor = this.e) != null) {
            editor.apply();
        }
        this.f762f = z;
    }

    public void a(Preference preference) {
        a aVar = this.m;
        if (aVar != null) {
            aVar.a(preference);
        }
    }
}
