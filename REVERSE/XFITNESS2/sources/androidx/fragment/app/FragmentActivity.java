package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import androidx.activity.ComponentActivity;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.c;
import androidx.core.app.a;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import g.a.h;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class FragmentActivity extends ComponentActivity implements a.b, a.d {
    final e m = e.a((g<?>) new a());
    final LifecycleRegistry n = new LifecycleRegistry(this);
    boolean o;
    boolean p;
    boolean q = true;
    boolean r;
    boolean s;
    boolean t;
    int u;
    h<String> v;

    class a extends g<FragmentActivity> implements ViewModelStoreOwner, c {
        public a() {
            super(FragmentActivity.this);
        }

        public OnBackPressedDispatcher a() {
            return FragmentActivity.this.a();
        }

        public boolean b(Fragment fragment) {
            return !FragmentActivity.this.isFinishing();
        }

        public boolean c() {
            Window window = FragmentActivity.this.getWindow();
            return (window == null || window.peekDecorView() == null) ? false : true;
        }

        public Lifecycle getLifecycle() {
            return FragmentActivity.this.n;
        }

        public ViewModelStore getViewModelStore() {
            return FragmentActivity.this.getViewModelStore();
        }

        public LayoutInflater h() {
            return FragmentActivity.this.getLayoutInflater().cloneInContext(FragmentActivity.this);
        }

        public void i() {
            FragmentActivity.this.g();
        }

        public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            FragmentActivity.this.dump(str, fileDescriptor, printWriter, strArr);
        }

        public FragmentActivity g() {
            return FragmentActivity.this;
        }

        public void a(Fragment fragment, Intent intent, int i2, Bundle bundle) {
            FragmentActivity.this.a(fragment, intent, i2, bundle);
        }

        public void a(Fragment fragment, String[] strArr, int i2) {
            FragmentActivity.this.a(fragment, strArr, i2);
        }

        public boolean a(String str) {
            return androidx.core.app.a.a((Activity) FragmentActivity.this, str);
        }

        public void a(Fragment fragment) {
            FragmentActivity.this.a(fragment);
        }

        public View a(int i2) {
            return FragmentActivity.this.findViewById(i2);
        }
    }

    public FragmentActivity() {
    }

    static void b(int i2) {
        if ((i2 & -65536) != 0) {
            throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
        }
    }

    private void h() {
        do {
        } while (a(e(), Lifecycle.State.CREATED));
    }

    /* access modifiers changed from: package-private */
    public final View a(View view, String str, Context context, AttributeSet attributeSet) {
        return this.m.a(view, str, context, attributeSet);
    }

    public void a(Fragment fragment) {
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        printWriter.print(str);
        printWriter.print("Local FragmentActivity ");
        printWriter.print(Integer.toHexString(System.identityHashCode(this)));
        printWriter.println(" State:");
        String str2 = str + "  ";
        printWriter.print(str2);
        printWriter.print("mCreated=");
        printWriter.print(this.o);
        printWriter.print(" mResumed=");
        printWriter.print(this.p);
        printWriter.print(" mStopped=");
        printWriter.print(this.q);
        if (getApplication() != null) {
            androidx.loader.a.a.a(this).a(str2, fileDescriptor, printWriter, strArr);
        }
        this.m.j().a(str, fileDescriptor, printWriter, strArr);
    }

    public j e() {
        return this.m.j();
    }

    /* access modifiers changed from: protected */
    public void f() {
        this.n.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        this.m.f();
    }

    @Deprecated
    public void g() {
        invalidateOptionsMenu();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i2, int i3, Intent intent) {
        this.m.k();
        int i4 = i2 >> 16;
        if (i4 != 0) {
            int i5 = i4 - 1;
            String a2 = this.v.a(i5);
            this.v.d(i5);
            if (a2 == null) {
                Log.w("FragmentActivity", "Activity result delivered for unknown Fragment.");
                return;
            }
            Fragment a3 = this.m.a(a2);
            if (a3 == null) {
                Log.w("FragmentActivity", "Activity result no fragment exists for who: " + a2);
                return;
            }
            a3.a(i2 & 65535, i3, intent);
            return;
        }
        a.c a4 = androidx.core.app.a.a();
        if (a4 == null || !a4.a(this, i2, i3, intent)) {
            super.onActivityResult(i2, i3, intent);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.m.k();
        this.m.a(configuration);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        this.m.a((Fragment) null);
        if (bundle != null) {
            this.m.a(bundle.getParcelable("android:support:fragments"));
            if (bundle.containsKey("android:support:next_request_index")) {
                this.u = bundle.getInt("android:support:next_request_index");
                int[] intArray = bundle.getIntArray("android:support:request_indicies");
                String[] stringArray = bundle.getStringArray("android:support:request_fragment_who");
                if (intArray == null || stringArray == null || intArray.length != stringArray.length) {
                    Log.w("FragmentActivity", "Invalid requestCode mapping in savedInstanceState.");
                } else {
                    this.v = new h<>(intArray.length);
                    for (int i2 = 0; i2 < intArray.length; i2++) {
                        this.v.c(intArray[i2], stringArray[i2]);
                    }
                }
            }
        }
        if (this.v == null) {
            this.v = new h<>();
            this.u = 0;
        }
        super.onCreate(bundle);
        this.n.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        this.m.b();
    }

    public boolean onCreatePanelMenu(int i2, Menu menu) {
        if (i2 == 0) {
            return super.onCreatePanelMenu(i2, menu) | this.m.a(menu, getMenuInflater());
        }
        return super.onCreatePanelMenu(i2, menu);
    }

    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View a2 = a(view, str, context, attributeSet);
        return a2 == null ? super.onCreateView(view, str, context, attributeSet) : a2;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.m.c();
        this.n.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    public void onLowMemory() {
        super.onLowMemory();
        this.m.d();
    }

    public boolean onMenuItemSelected(int i2, MenuItem menuItem) {
        if (super.onMenuItemSelected(i2, menuItem)) {
            return true;
        }
        if (i2 == 0) {
            return this.m.b(menuItem);
        }
        if (i2 != 6) {
            return false;
        }
        return this.m.a(menuItem);
    }

    public void onMultiWindowModeChanged(boolean z) {
        this.m.a(z);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(@SuppressLint({"UnknownNullness"}) Intent intent) {
        super.onNewIntent(intent);
        this.m.k();
    }

    public void onPanelClosed(int i2, Menu menu) {
        if (i2 == 0) {
            this.m.a(menu);
        }
        super.onPanelClosed(i2, menu);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.p = false;
        this.m.e();
        this.n.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    public void onPictureInPictureModeChanged(boolean z) {
        this.m.b(z);
    }

    /* access modifiers changed from: protected */
    public void onPostResume() {
        super.onPostResume();
        f();
    }

    public boolean onPreparePanel(int i2, View view, Menu menu) {
        if (i2 == 0) {
            return a(view, menu) | this.m.b(menu);
        }
        return super.onPreparePanel(i2, view, menu);
    }

    public void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        this.m.k();
        int i3 = (i2 >> 16) & 65535;
        if (i3 != 0) {
            int i4 = i3 - 1;
            String a2 = this.v.a(i4);
            this.v.d(i4);
            if (a2 == null) {
                Log.w("FragmentActivity", "Activity result delivered for unknown Fragment.");
                return;
            }
            Fragment a3 = this.m.a(a2);
            if (a3 == null) {
                Log.w("FragmentActivity", "Activity result no fragment exists for who: " + a2);
                return;
            }
            a3.a(i2 & 65535, strArr, iArr);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.p = true;
        this.m.k();
        this.m.i();
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        h();
        this.n.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        Parcelable l = this.m.l();
        if (l != null) {
            bundle.putParcelable("android:support:fragments", l);
        }
        if (this.v.d() > 0) {
            bundle.putInt("android:support:next_request_index", this.u);
            int[] iArr = new int[this.v.d()];
            String[] strArr = new String[this.v.d()];
            for (int i2 = 0; i2 < this.v.d(); i2++) {
                iArr[i2] = this.v.c(i2);
                strArr[i2] = this.v.f(i2);
            }
            bundle.putIntArray("android:support:request_indicies", iArr);
            bundle.putStringArray("android:support:request_fragment_who", strArr);
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.q = false;
        if (!this.o) {
            this.o = true;
            this.m.a();
        }
        this.m.k();
        this.m.i();
        this.n.handleLifecycleEvent(Lifecycle.Event.ON_START);
        this.m.g();
    }

    public void onStateNotSaved() {
        this.m.k();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        this.q = true;
        h();
        this.m.h();
        this.n.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    public void startActivityForResult(@SuppressLint({"UnknownNullness"}) Intent intent, int i2) {
        if (!this.t && i2 != -1) {
            b(i2);
        }
        super.startActivityForResult(intent, i2);
    }

    public void startIntentSenderForResult(@SuppressLint({"UnknownNullness"}) IntentSender intentSender, int i2, Intent intent, int i3, int i4, int i5) {
        if (!this.s && i2 != -1) {
            b(i2);
        }
        super.startIntentSenderForResult(intentSender, i2, intent, i3, i4, i5);
    }

    private int b(Fragment fragment) {
        if (this.v.d() < 65534) {
            while (this.v.b(this.u) >= 0) {
                this.u = (this.u + 1) % 65534;
            }
            int i2 = this.u;
            this.v.c(i2, fragment.f588i);
            this.u = (this.u + 1) % 65534;
            return i2;
        }
        throw new IllegalStateException("Too many pending Fragment activity results.");
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public boolean a(View view, Menu menu) {
        return super.onPreparePanel(0, view, menu);
    }

    public final void a(int i2) {
        if (!this.r && i2 != -1) {
            b(i2);
        }
    }

    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        View a2 = a((View) null, str, context, attributeSet);
        return a2 == null ? super.onCreateView(str, context, attributeSet) : a2;
    }

    public void startActivityForResult(@SuppressLint({"UnknownNullness"}) Intent intent, int i2, Bundle bundle) {
        if (!this.t && i2 != -1) {
            b(i2);
        }
        super.startActivityForResult(intent, i2, bundle);
    }

    public void startIntentSenderForResult(@SuppressLint({"UnknownNullness"}) IntentSender intentSender, int i2, Intent intent, int i3, int i4, int i5, Bundle bundle) {
        if (!this.s && i2 != -1) {
            b(i2);
        }
        super.startIntentSenderForResult(intentSender, i2, intent, i3, i4, i5, bundle);
    }

    public FragmentActivity(int i2) {
        super(i2);
    }

    public void a(Fragment fragment, @SuppressLint({"UnknownNullness"}) Intent intent, int i2, Bundle bundle) {
        this.t = true;
        if (i2 == -1) {
            try {
                androidx.core.app.a.a(this, intent, -1, bundle);
            } finally {
                this.t = false;
            }
        } else {
            b(i2);
            androidx.core.app.a.a(this, intent, ((b(fragment) + 1) << 16) + (i2 & 65535), bundle);
            this.t = false;
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public void a(Fragment fragment, String[] strArr, int i2) {
        if (i2 == -1) {
            androidx.core.app.a.a(this, strArr, i2);
            return;
        }
        b(i2);
        try {
            this.r = true;
            androidx.core.app.a.a(this, strArr, ((b(fragment) + 1) << 16) + (i2 & 65535));
            this.r = false;
        } catch (Throwable th) {
            this.r = false;
            throw th;
        }
    }

    private static boolean a(j jVar, Lifecycle.State state) {
        boolean z = false;
        for (Fragment next : jVar.q()) {
            if (next != null) {
                if (next.q() != null) {
                    z |= a(next.j(), state);
                }
                if (next.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    next.U.setCurrentState(state);
                    z = true;
                }
            }
        }
        return z;
    }
}
