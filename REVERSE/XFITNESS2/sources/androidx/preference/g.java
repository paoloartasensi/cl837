package androidx.preference;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.p;
import androidx.preference.DialogPreference;
import androidx.preference.j;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: PreferenceFragmentCompat */
public abstract class g extends Fragment implements j.c, j.a, j.b, DialogPreference.a {
    private final c b0 = new c();
    private j c0;
    RecyclerView d0;
    private boolean e0;
    private boolean f0;
    private int g0 = R$layout.preference_list_fragment;
    private Runnable h0;
    private Handler i0 = new a();
    private final Runnable j0 = new b();

    /* compiled from: PreferenceFragmentCompat */
    class a extends Handler {
        a() {
        }

        public void handleMessage(Message message) {
            if (message.what == 1) {
                g.this.n0();
            }
        }
    }

    /* compiled from: PreferenceFragmentCompat */
    class b implements Runnable {
        b() {
        }

        public void run() {
            RecyclerView recyclerView = g.this.d0;
            recyclerView.focusableViewAvailable(recyclerView);
        }
    }

    /* compiled from: PreferenceFragmentCompat */
    public interface d {
        boolean a(g gVar, Preference preference);
    }

    /* compiled from: PreferenceFragmentCompat */
    public interface e {
        boolean a(g gVar, Preference preference);
    }

    /* compiled from: PreferenceFragmentCompat */
    public interface f {
        boolean a(g gVar, PreferenceScreen preferenceScreen);
    }

    private void v0() {
        if (!this.i0.hasMessages(1)) {
            this.i0.obtainMessage(1).sendToTarget();
        }
    }

    private void w0() {
        if (this.c0 == null) {
            throw new RuntimeException("This should be called after super.onCreate.");
        }
    }

    private void x0() {
        p0().setAdapter((RecyclerView.g) null);
        PreferenceScreen r0 = r0();
        if (r0 != null) {
            r0.C();
        }
        u0();
    }

    public void T() {
        this.i0.removeCallbacks(this.j0);
        this.i0.removeMessages(1);
        if (this.e0) {
            x0();
        }
        this.d0 = null;
        super.T();
    }

    public void X() {
        super.X();
        this.c0.setOnPreferenceTreeClickListener(this);
        this.c0.setOnDisplayPreferenceDialogListener(this);
    }

    public void Y() {
        super.Y();
        this.c0.setOnPreferenceTreeClickListener((j.c) null);
        this.c0.setOnDisplayPreferenceDialogListener((j.a) null);
    }

    public View a(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        TypedArray obtainStyledAttributes = k().obtainStyledAttributes((AttributeSet) null, R$styleable.PreferenceFragmentCompat, R$attr.preferenceFragmentCompatStyle, 0);
        this.g0 = obtainStyledAttributes.getResourceId(R$styleable.PreferenceFragmentCompat_android_layout, this.g0);
        Drawable drawable = obtainStyledAttributes.getDrawable(R$styleable.PreferenceFragmentCompat_android_divider);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R$styleable.PreferenceFragmentCompat_android_dividerHeight, -1);
        boolean z = obtainStyledAttributes.getBoolean(R$styleable.PreferenceFragmentCompat_allowDividerAfterLastItem, true);
        obtainStyledAttributes.recycle();
        LayoutInflater cloneInContext = layoutInflater.cloneInContext(k());
        View inflate = cloneInContext.inflate(this.g0, viewGroup, false);
        View findViewById = inflate.findViewById(16908351);
        if (findViewById instanceof ViewGroup) {
            ViewGroup viewGroup2 = (ViewGroup) findViewById;
            RecyclerView c2 = c(cloneInContext, viewGroup2, bundle);
            if (c2 != null) {
                this.d0 = c2;
                c2.a((RecyclerView.n) this.b0);
                a(drawable);
                if (dimensionPixelSize != -1) {
                    f(dimensionPixelSize);
                }
                this.b0.a(z);
                if (this.d0.getParent() == null) {
                    viewGroup2.addView(this.d0);
                }
                this.i0.post(this.j0);
                return inflate;
            }
            throw new RuntimeException("Could not create RecyclerView");
        }
        throw new IllegalStateException("Content has view with id attribute 'android.R.id.list_container' that is not a ViewGroup class");
    }

    public abstract void a(Bundle bundle, String str);

    public boolean b(Preference preference) {
        if (preference.e() == null) {
            return false;
        }
        boolean a2 = o0() instanceof e ? ((e) o0()).a(this, preference) : false;
        if (!a2 && (d() instanceof e)) {
            a2 = ((e) d()).a(this, preference);
        }
        if (a2) {
            return true;
        }
        Log.w("PreferenceFragment", "onPreferenceStartFragment is not implemented in the parent activity - attempting to use a fallback implementation. You should implement this method so that you can configure the new fragment that will be displayed, and set a transition between the fragments.");
        androidx.fragment.app.j e2 = j0().e();
        Bundle c2 = preference.c();
        Fragment a3 = e2.p().a(j0().getClassLoader(), preference.e());
        a3.m(c2);
        a3.a((Fragment) this, 0);
        p b2 = e2.b();
        b2.a(((View) F().getParent()).getId(), a3);
        b2.a((String) null);
        b2.a();
        return true;
    }

    public void c(Bundle bundle) {
        super.c(bundle);
        TypedValue typedValue = new TypedValue();
        d().getTheme().resolveAttribute(R$attr.preferenceTheme, typedValue, true);
        int i2 = typedValue.resourceId;
        if (i2 == 0) {
            i2 = R$style.PreferenceThemeOverlay;
        }
        d().getTheme().applyStyle(i2, false);
        j jVar = new j(k());
        this.c0 = jVar;
        jVar.setOnNavigateToScreenListener(this);
        a(bundle, i() != null ? i().getString("androidx.preference.PreferenceFragmentCompat.PREFERENCE_ROOT") : null);
    }

    public void e(Bundle bundle) {
        super.e(bundle);
        PreferenceScreen r0 = r0();
        if (r0 != null) {
            Bundle bundle2 = new Bundle();
            r0.d(bundle2);
            bundle.putBundle("android:preferences", bundle2);
        }
    }

    public void f(int i2) {
        this.b0.a(i2);
    }

    /* access modifiers changed from: package-private */
    public void n0() {
        PreferenceScreen r0 = r0();
        if (r0 != null) {
            p0().setAdapter(b(r0));
            r0.A();
        }
        s0();
    }

    public Fragment o0() {
        return null;
    }

    public final RecyclerView p0() {
        return this.d0;
    }

    public j q0() {
        return this.c0;
    }

    public PreferenceScreen r0() {
        return this.c0.g();
    }

    /* access modifiers changed from: protected */
    public void s0() {
    }

    public RecyclerView.o t0() {
        return new LinearLayoutManager(k());
    }

    /* access modifiers changed from: protected */
    public void u0() {
    }

    /* compiled from: PreferenceFragmentCompat */
    private class c extends RecyclerView.n {
        private Drawable a;
        private int b;
        private boolean c = true;

        c() {
        }

        public void a(Rect rect, View view, RecyclerView recyclerView, RecyclerView.z zVar) {
            if (a(view, recyclerView)) {
                rect.bottom = this.b;
            }
        }

        public void b(Canvas canvas, RecyclerView recyclerView, RecyclerView.z zVar) {
            if (this.a != null) {
                int childCount = recyclerView.getChildCount();
                int width = recyclerView.getWidth();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = recyclerView.getChildAt(i2);
                    if (a(childAt, recyclerView)) {
                        int y = ((int) childAt.getY()) + childAt.getHeight();
                        this.a.setBounds(0, y, width, this.b + y);
                        this.a.draw(canvas);
                    }
                }
            }
        }

        private boolean a(View view, RecyclerView recyclerView) {
            RecyclerView.c0 g2 = recyclerView.g(view);
            boolean z = false;
            if (!((g2 instanceof l) && ((l) g2).b())) {
                return false;
            }
            boolean z2 = this.c;
            int indexOfChild = recyclerView.indexOfChild(view);
            if (indexOfChild >= recyclerView.getChildCount() - 1) {
                return z2;
            }
            RecyclerView.c0 g3 = recyclerView.g(recyclerView.getChildAt(indexOfChild + 1));
            if ((g3 instanceof l) && ((l) g3).a()) {
                z = true;
            }
            return z;
        }

        public void a(Drawable drawable) {
            if (drawable != null) {
                this.b = drawable.getIntrinsicHeight();
            } else {
                this.b = 0;
            }
            this.a = drawable;
            g.this.d0.m();
        }

        public void a(int i2) {
            this.b = i2;
            g.this.d0.m();
        }

        public void a(boolean z) {
            this.c = z;
        }
    }

    public void e(int i2) {
        w0();
        c(this.c0.a(k(), i2, r0()));
    }

    public void c(PreferenceScreen preferenceScreen) {
        if (this.c0.a(preferenceScreen) && preferenceScreen != null) {
            u0();
            this.e0 = true;
            if (this.f0) {
                v0();
            }
        }
    }

    public RecyclerView c(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        RecyclerView recyclerView;
        if (k().getPackageManager().hasSystemFeature("android.hardware.type.automotive") && (recyclerView = (RecyclerView) viewGroup.findViewById(R$id.recycler_view)) != null) {
            return recyclerView;
        }
        RecyclerView recyclerView2 = (RecyclerView) layoutInflater.inflate(R$layout.preference_recyclerview, viewGroup, false);
        recyclerView2.setLayoutManager(t0());
        recyclerView2.setAccessibilityDelegateCompat(new k(recyclerView2));
        return recyclerView2;
    }

    /* access modifiers changed from: protected */
    public RecyclerView.g b(PreferenceScreen preferenceScreen) {
        return new h(preferenceScreen);
    }

    public void a(Drawable drawable) {
        this.b0.a(drawable);
    }

    public void a(View view, Bundle bundle) {
        Bundle bundle2;
        PreferenceScreen r0;
        super.a(view, bundle);
        if (!(bundle == null || (bundle2 = bundle.getBundle("android:preferences")) == null || (r0 = r0()) == null)) {
            r0.c(bundle2);
        }
        if (this.e0) {
            n0();
            Runnable runnable = this.h0;
            if (runnable != null) {
                runnable.run();
                this.h0 = null;
            }
        }
        this.f0 = true;
    }

    public void a(PreferenceScreen preferenceScreen) {
        if (!(o0() instanceof f ? ((f) o0()).a(this, preferenceScreen) : false) && (d() instanceof f)) {
            ((f) d()).a(this, preferenceScreen);
        }
    }

    public <T extends Preference> T a(CharSequence charSequence) {
        j jVar = this.c0;
        if (jVar == null) {
            return null;
        }
        return jVar.a(charSequence);
    }

    public void a(Preference preference) {
        androidx.fragment.app.b bVar;
        boolean a2 = o0() instanceof d ? ((d) o0()).a(this, preference) : false;
        if (!a2 && (d() instanceof d)) {
            a2 = ((d) d()).a(this, preference);
        }
        if (!a2 && p().b("androidx.preference.PreferenceFragment.DIALOG") == null) {
            if (preference instanceof EditTextPreference) {
                bVar = a.c(preference.h());
            } else if (preference instanceof ListPreference) {
                bVar = c.c(preference.h());
            } else if (preference instanceof MultiSelectListPreference) {
                bVar = d.c(preference.h());
            } else {
                throw new IllegalArgumentException("Cannot display dialog for an unknown Preference type: " + preference.getClass().getSimpleName() + ". Make sure to implement onPreferenceDisplayDialog() to handle displaying a custom dialog for this Preference.");
            }
            bVar.a((Fragment) this, 0);
            bVar.a(p(), "androidx.preference.PreferenceFragment.DIALOG");
        }
    }
}
