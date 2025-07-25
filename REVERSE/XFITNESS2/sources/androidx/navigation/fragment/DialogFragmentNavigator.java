package androidx.navigation.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.b;
import androidx.fragment.app.j;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.c;
import androidx.navigation.o;
import androidx.navigation.r;

@r.b("dialog")
public final class DialogFragmentNavigator extends r<a> {
    private final Context a;
    private final j b;
    private int c = 0;
    private LifecycleEventObserver d = new LifecycleEventObserver(this) {
        public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_STOP) {
                b bVar = (b) lifecycleOwner;
                if (!bVar.q0().isShowing()) {
                    NavHostFragment.b(bVar).g();
                }
            }
        }
    };

    public static class a extends androidx.navigation.j implements c {
        private String m;

        public a(r<? extends a> rVar) {
            super((r<? extends androidx.navigation.j>) rVar);
        }

        public void a(Context context, AttributeSet attributeSet) {
            super.a(context, attributeSet);
            TypedArray obtainAttributes = context.getResources().obtainAttributes(attributeSet, R$styleable.DialogFragmentNavigator);
            String string = obtainAttributes.getString(R$styleable.DialogFragmentNavigator_android_name);
            if (string != null) {
                b(string);
            }
            obtainAttributes.recycle();
        }

        public final a b(String str) {
            this.m = str;
            return this;
        }

        public final String i() {
            String str = this.m;
            if (str != null) {
                return str;
            }
            throw new IllegalStateException("DialogFragment class was not set");
        }
    }

    public DialogFragmentNavigator(Context context, j jVar) {
        this.a = context;
        this.b = jVar;
    }

    public Bundle b() {
        if (this.c == 0) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("androidx-nav-dialogfragment:navigator:count", this.c);
        return bundle;
    }

    public boolean c() {
        if (this.c == 0) {
            return false;
        }
        if (this.b.x()) {
            Log.i("DialogFragmentNavigator", "Ignoring popBackStack() call: FragmentManager has already saved its state");
            return false;
        }
        j jVar = this.b;
        StringBuilder sb = new StringBuilder();
        sb.append("androidx-nav-fragment:navigator:dialog:");
        int i2 = this.c - 1;
        this.c = i2;
        sb.append(i2);
        Fragment b2 = jVar.b(sb.toString());
        if (b2 != null) {
            b2.getLifecycle().removeObserver(this.d);
            ((b) b2).n0();
        }
        return true;
    }

    public a a() {
        return new a(this);
    }

    public androidx.navigation.j a(a aVar, Bundle bundle, o oVar, r.a aVar2) {
        if (this.b.x()) {
            Log.i("DialogFragmentNavigator", "Ignoring navigate() call: FragmentManager has already saved its state");
            return null;
        }
        String i2 = aVar.i();
        if (i2.charAt(0) == '.') {
            i2 = this.a.getPackageName() + i2;
        }
        Fragment a2 = this.b.p().a(this.a.getClassLoader(), i2);
        if (b.class.isAssignableFrom(a2.getClass())) {
            b bVar = (b) a2;
            bVar.m(bundle);
            bVar.getLifecycle().addObserver(this.d);
            j jVar = this.b;
            StringBuilder sb = new StringBuilder();
            sb.append("androidx-nav-fragment:navigator:dialog:");
            int i3 = this.c;
            this.c = i3 + 1;
            sb.append(i3);
            bVar.a(jVar, sb.toString());
            return aVar;
        }
        throw new IllegalArgumentException("Dialog destination " + aVar.i() + " is not an instance of DialogFragment");
    }

    public void a(Bundle bundle) {
        if (bundle != null) {
            int i2 = 0;
            this.c = bundle.getInt("androidx-nav-dialogfragment:navigator:count", 0);
            while (i2 < this.c) {
                j jVar = this.b;
                b bVar = (b) jVar.b("androidx-nav-fragment:navigator:dialog:" + i2);
                if (bVar != null) {
                    bVar.getLifecycle().addObserver(this.d);
                    i2++;
                } else {
                    throw new IllegalStateException("DialogFragment " + i2 + " doesn't exist in the FragmentManager");
                }
            }
        }
    }
}
