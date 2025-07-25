package androidx.appcompat.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$color;
import androidx.appcompat.R$id;
import androidx.appcompat.R$layout;
import androidx.appcompat.R$style;
import androidx.appcompat.R$styleable;
import androidx.appcompat.d.b;
import androidx.appcompat.d.f;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.m;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.g0;
import androidx.appcompat.widget.l0;
import androidx.appcompat.widget.m0;
import androidx.appcompat.widget.s;
import androidx.core.h.a0;
import androidx.core.h.b0;
import androidx.core.h.d0;
import androidx.core.h.e;
import androidx.core.h.r;
import androidx.core.h.v;
import androidx.core.h.z;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import java.lang.Thread;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

class AppCompatDelegateImpl extends e implements g.a, LayoutInflater.Factory2 {
    private static final Map<Class<?>, Integer> e0 = new g.a.a();
    private static final boolean f0 = (Build.VERSION.SDK_INT < 21);
    private static final int[] g0 = {16842836};
    private static boolean h0 = true;
    private static final boolean i0;
    private TextView A;
    private View B;
    private boolean C;
    private boolean D;
    boolean E;
    boolean F;
    boolean G;
    boolean H;
    boolean I;
    private boolean J;
    private PanelFeatureState[] K;
    private PanelFeatureState L;
    private boolean M;
    private boolean N;
    private boolean O;
    private boolean P;
    boolean Q;
    private int R;
    private int S;
    private boolean T;
    private boolean U;
    private m V;
    private m W;
    boolean X;
    int Y;
    private final Runnable Z;
    private boolean a0;
    private Rect b0;
    private Rect c0;
    private AppCompatViewInflater d0;

    /* renamed from: h  reason: collision with root package name */
    final Object f47h;

    /* renamed from: i  reason: collision with root package name */
    final Context f48i;

    /* renamed from: j  reason: collision with root package name */
    Window f49j;
    private k k;
    final d l;
    a m;
    MenuInflater n;
    private CharSequence o;
    private androidx.appcompat.widget.o p;
    private i q;
    private p r;
    androidx.appcompat.d.b s;
    ActionBarContextView t;
    PopupWindow u;
    Runnable v;
    z w;
    private boolean x;
    private boolean y;
    private ViewGroup z;

    static class a implements Thread.UncaughtExceptionHandler {
        final /* synthetic */ Thread.UncaughtExceptionHandler a;

        a(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
            this.a = uncaughtExceptionHandler;
        }

        private boolean a(Throwable th) {
            String message;
            if (!(th instanceof Resources.NotFoundException) || (message = th.getMessage()) == null) {
                return false;
            }
            if (message.contains("drawable") || message.contains("Drawable")) {
                return true;
            }
            return false;
        }

        public void uncaughtException(Thread thread, Throwable th) {
            if (a(th)) {
                Resources.NotFoundException notFoundException = new Resources.NotFoundException(th.getMessage() + ". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.");
                notFoundException.initCause(th.getCause());
                notFoundException.setStackTrace(th.getStackTrace());
                this.a.uncaughtException(thread, notFoundException);
                return;
            }
            this.a.uncaughtException(thread, th);
        }
    }

    class b implements Runnable {
        b() {
        }

        public void run() {
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if ((appCompatDelegateImpl.Y & 1) != 0) {
                appCompatDelegateImpl.f(0);
            }
            AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
            if ((appCompatDelegateImpl2.Y & 4096) != 0) {
                appCompatDelegateImpl2.f(108);
            }
            AppCompatDelegateImpl appCompatDelegateImpl3 = AppCompatDelegateImpl.this;
            appCompatDelegateImpl3.X = false;
            appCompatDelegateImpl3.Y = 0;
        }
    }

    class c implements r {
        c() {
        }

        public d0 a(View view, d0 d0Var) {
            int e = d0Var.e();
            int j2 = AppCompatDelegateImpl.this.j(e);
            if (e != j2) {
                d0Var = d0Var.a(d0Var.c(), j2, d0Var.d(), d0Var.b());
            }
            return v.b(view, d0Var);
        }
    }

    class d implements s.a {
        d() {
        }

        public void a(Rect rect) {
            rect.top = AppCompatDelegateImpl.this.j(rect.top);
        }
    }

    class e implements ContentFrameLayout.a {
        e() {
        }

        public void a() {
        }

        public void onDetachedFromWindow() {
            AppCompatDelegateImpl.this.m();
        }
    }

    class f implements Runnable {

        class a extends b0 {
            a() {
            }

            public void a(View view) {
                AppCompatDelegateImpl.this.t.setAlpha(1.0f);
                AppCompatDelegateImpl.this.w.a((a0) null);
                AppCompatDelegateImpl.this.w = null;
            }

            public void b(View view) {
                AppCompatDelegateImpl.this.t.setVisibility(0);
            }
        }

        f() {
        }

        public void run() {
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            appCompatDelegateImpl.u.showAtLocation(appCompatDelegateImpl.t, 55, 0, 0);
            AppCompatDelegateImpl.this.n();
            if (AppCompatDelegateImpl.this.v()) {
                AppCompatDelegateImpl.this.t.setAlpha(0.0f);
                AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
                z a2 = v.a(appCompatDelegateImpl2.t);
                a2.a(1.0f);
                appCompatDelegateImpl2.w = a2;
                AppCompatDelegateImpl.this.w.a((a0) new a());
                return;
            }
            AppCompatDelegateImpl.this.t.setAlpha(1.0f);
            AppCompatDelegateImpl.this.t.setVisibility(0);
        }
    }

    class g extends b0 {
        g() {
        }

        public void a(View view) {
            AppCompatDelegateImpl.this.t.setAlpha(1.0f);
            AppCompatDelegateImpl.this.w.a((a0) null);
            AppCompatDelegateImpl.this.w = null;
        }

        public void b(View view) {
            AppCompatDelegateImpl.this.t.setVisibility(0);
            AppCompatDelegateImpl.this.t.sendAccessibilityEvent(32);
            if (AppCompatDelegateImpl.this.t.getParent() instanceof View) {
                v.I((View) AppCompatDelegateImpl.this.t.getParent());
            }
        }
    }

    private class h implements b {
        h() {
        }

        public Context a() {
            return AppCompatDelegateImpl.this.o();
        }

        public void a(Drawable drawable, int i2) {
            a d = AppCompatDelegateImpl.this.d();
            if (d != null) {
                d.a(drawable);
                d.b(i2);
            }
        }
    }

    class j implements b.a {
        private b.a a;

        class a extends b0 {
            a() {
            }

            public void a(View view) {
                AppCompatDelegateImpl.this.t.setVisibility(8);
                AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                PopupWindow popupWindow = appCompatDelegateImpl.u;
                if (popupWindow != null) {
                    popupWindow.dismiss();
                } else if (appCompatDelegateImpl.t.getParent() instanceof View) {
                    v.I((View) AppCompatDelegateImpl.this.t.getParent());
                }
                AppCompatDelegateImpl.this.t.removeAllViews();
                AppCompatDelegateImpl.this.w.a((a0) null);
                AppCompatDelegateImpl.this.w = null;
            }
        }

        public j(b.a aVar) {
            this.a = aVar;
        }

        public boolean a(androidx.appcompat.d.b bVar, Menu menu) {
            return this.a.a(bVar, menu);
        }

        public boolean b(androidx.appcompat.d.b bVar, Menu menu) {
            return this.a.b(bVar, menu);
        }

        public boolean a(androidx.appcompat.d.b bVar, MenuItem menuItem) {
            return this.a.a(bVar, menuItem);
        }

        public void a(androidx.appcompat.d.b bVar) {
            this.a.a(bVar);
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (appCompatDelegateImpl.u != null) {
                appCompatDelegateImpl.f49j.getDecorView().removeCallbacks(AppCompatDelegateImpl.this.v);
            }
            AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
            if (appCompatDelegateImpl2.t != null) {
                appCompatDelegateImpl2.n();
                AppCompatDelegateImpl appCompatDelegateImpl3 = AppCompatDelegateImpl.this;
                z a2 = v.a(appCompatDelegateImpl3.t);
                a2.a(0.0f);
                appCompatDelegateImpl3.w = a2;
                AppCompatDelegateImpl.this.w.a((a0) new a());
            }
            AppCompatDelegateImpl appCompatDelegateImpl4 = AppCompatDelegateImpl.this;
            d dVar = appCompatDelegateImpl4.l;
            if (dVar != null) {
                dVar.b(appCompatDelegateImpl4.s);
            }
            AppCompatDelegateImpl.this.s = null;
        }
    }

    private class l extends m {
        private final PowerManager c;

        l(Context context) {
            super();
            this.c = (PowerManager) context.getSystemService("power");
        }

        /* access modifiers changed from: package-private */
        public IntentFilter b() {
            if (Build.VERSION.SDK_INT < 21) {
                return null;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
            return intentFilter;
        }

        public int c() {
            if (Build.VERSION.SDK_INT < 21 || !this.c.isPowerSaveMode()) {
                return 1;
            }
            return 2;
        }

        public void d() {
            AppCompatDelegateImpl.this.l();
        }
    }

    abstract class m {
        private BroadcastReceiver a;

        class a extends BroadcastReceiver {
            a() {
            }

            public void onReceive(Context context, Intent intent) {
                m.this.d();
            }
        }

        m() {
        }

        /* access modifiers changed from: package-private */
        public void a() {
            BroadcastReceiver broadcastReceiver = this.a;
            if (broadcastReceiver != null) {
                try {
                    AppCompatDelegateImpl.this.f48i.unregisterReceiver(broadcastReceiver);
                } catch (IllegalArgumentException unused) {
                }
                this.a = null;
            }
        }

        /* access modifiers changed from: package-private */
        public abstract IntentFilter b();

        /* access modifiers changed from: package-private */
        public abstract int c();

        /* access modifiers changed from: package-private */
        public abstract void d();

        /* access modifiers changed from: package-private */
        public void e() {
            a();
            IntentFilter b2 = b();
            if (b2 != null && b2.countActions() != 0) {
                if (this.a == null) {
                    this.a = new a();
                }
                AppCompatDelegateImpl.this.f48i.registerReceiver(this.a, b2);
            }
        }
    }

    private class n extends m {
        private final j c;

        n(j jVar) {
            super();
            this.c = jVar;
        }

        /* access modifiers changed from: package-private */
        public IntentFilter b() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            intentFilter.addAction("android.intent.action.TIME_TICK");
            return intentFilter;
        }

        public int c() {
            return this.c.a() ? 2 : 1;
        }

        public void d() {
            AppCompatDelegateImpl.this.l();
        }
    }

    private class o extends ContentFrameLayout {
        public o(Context context) {
            super(context);
        }

        private boolean a(int i2, int i3) {
            return i2 < -5 || i3 < -5 || i2 > getWidth() + 5 || i3 > getHeight() + 5;
        }

        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImpl.this.a(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0 || !a((int) motionEvent.getX(), (int) motionEvent.getY())) {
                return super.onInterceptTouchEvent(motionEvent);
            }
            AppCompatDelegateImpl.this.e(0);
            return true;
        }

        public void setBackgroundResource(int i2) {
            setBackgroundDrawable(androidx.appcompat.a.a.a.c(getContext(), i2));
        }
    }

    static {
        boolean z2 = false;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 21 && i2 <= 25) {
            z2 = true;
        }
        i0 = z2;
        if (f0 && !h0) {
            Thread.setDefaultUncaughtExceptionHandler(new a(Thread.getDefaultUncaughtExceptionHandler()));
        }
    }

    AppCompatDelegateImpl(Activity activity, d dVar) {
        this(activity, (Window) null, dVar, activity);
    }

    private void A() {
        if (!this.y) {
            this.z = z();
            CharSequence q2 = q();
            if (!TextUtils.isEmpty(q2)) {
                androidx.appcompat.widget.o oVar = this.p;
                if (oVar != null) {
                    oVar.setWindowTitle(q2);
                } else if (u() != null) {
                    u().b(q2);
                } else {
                    TextView textView = this.A;
                    if (textView != null) {
                        textView.setText(q2);
                    }
                }
            }
            w();
            a(this.z);
            this.y = true;
            PanelFeatureState a2 = a(0, false);
            if (this.Q) {
                return;
            }
            if (a2 == null || a2.f54j == null) {
                k(108);
            }
        }
    }

    private void B() {
        if (this.f49j == null) {
            Object obj = this.f47h;
            if (obj instanceof Activity) {
                a(((Activity) obj).getWindow());
            }
        }
        if (this.f49j == null) {
            throw new IllegalStateException("We have not been given a Window");
        }
    }

    private m C() {
        if (this.W == null) {
            this.W = new l(this.f48i);
        }
        return this.W;
    }

    private void D() {
        A();
        if (this.E && this.m == null) {
            Object obj = this.f47h;
            if (obj instanceof Activity) {
                this.m = new k((Activity) this.f47h, this.F);
            } else if (obj instanceof Dialog) {
                this.m = new k((Dialog) this.f47h);
            }
            a aVar = this.m;
            if (aVar != null) {
                aVar.c(this.a0);
            }
        }
    }

    private boolean E() {
        if (!this.U && (this.f47h instanceof Activity)) {
            PackageManager packageManager = this.f48i.getPackageManager();
            if (packageManager == null) {
                return false;
            }
            try {
                ActivityInfo activityInfo = packageManager.getActivityInfo(new ComponentName(this.f48i, this.f47h.getClass()), 0);
                this.T = (activityInfo == null || (activityInfo.configChanges & 512) == 0) ? false : true;
            } catch (PackageManager.NameNotFoundException e2) {
                Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", e2);
                this.T = false;
            }
        }
        this.U = true;
        return this.T;
    }

    private void F() {
        if (this.y) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    private AppCompatActivity G() {
        Context context = this.f48i;
        while (context != null) {
            if (!(context instanceof AppCompatActivity)) {
                if (!(context instanceof ContextWrapper)) {
                    break;
                }
                context = ((ContextWrapper) context).getBaseContext();
            } else {
                return (AppCompatActivity) context;
            }
        }
        return null;
    }

    private void k(int i2) {
        this.Y = (1 << i2) | this.Y;
        if (!this.X) {
            v.a(this.f49j.getDecorView(), this.Z);
            this.X = true;
        }
    }

    private int l(int i2) {
        if (i2 == 8) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            return 108;
        } else if (i2 != 9) {
            return i2;
        } else {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
            return 109;
        }
    }

    private void w() {
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) this.z.findViewById(16908290);
        View decorView = this.f49j.getDecorView();
        contentFrameLayout.a(decorView.getPaddingLeft(), decorView.getPaddingTop(), decorView.getPaddingRight(), decorView.getPaddingBottom());
        TypedArray obtainStyledAttributes = this.f48i.obtainStyledAttributes(R$styleable.AppCompatTheme);
        obtainStyledAttributes.getValue(R$styleable.AppCompatTheme_windowMinWidthMajor, contentFrameLayout.getMinWidthMajor());
        obtainStyledAttributes.getValue(R$styleable.AppCompatTheme_windowMinWidthMinor, contentFrameLayout.getMinWidthMinor());
        if (obtainStyledAttributes.hasValue(R$styleable.AppCompatTheme_windowFixedWidthMajor)) {
            obtainStyledAttributes.getValue(R$styleable.AppCompatTheme_windowFixedWidthMajor, contentFrameLayout.getFixedWidthMajor());
        }
        if (obtainStyledAttributes.hasValue(R$styleable.AppCompatTheme_windowFixedWidthMinor)) {
            obtainStyledAttributes.getValue(R$styleable.AppCompatTheme_windowFixedWidthMinor, contentFrameLayout.getFixedWidthMinor());
        }
        if (obtainStyledAttributes.hasValue(R$styleable.AppCompatTheme_windowFixedHeightMajor)) {
            obtainStyledAttributes.getValue(R$styleable.AppCompatTheme_windowFixedHeightMajor, contentFrameLayout.getFixedHeightMajor());
        }
        if (obtainStyledAttributes.hasValue(R$styleable.AppCompatTheme_windowFixedHeightMinor)) {
            obtainStyledAttributes.getValue(R$styleable.AppCompatTheme_windowFixedHeightMinor, contentFrameLayout.getFixedHeightMinor());
        }
        obtainStyledAttributes.recycle();
        contentFrameLayout.requestLayout();
    }

    private int x() {
        int i2 = this.R;
        return i2 != -100 ? i2 : e.k();
    }

    private void y() {
        m mVar = this.V;
        if (mVar != null) {
            mVar.a();
        }
        m mVar2 = this.W;
        if (mVar2 != null) {
            mVar2.a();
        }
    }

    private ViewGroup z() {
        ViewGroup viewGroup;
        Context context;
        TypedArray obtainStyledAttributes = this.f48i.obtainStyledAttributes(R$styleable.AppCompatTheme);
        if (obtainStyledAttributes.hasValue(R$styleable.AppCompatTheme_windowActionBar)) {
            if (obtainStyledAttributes.getBoolean(R$styleable.AppCompatTheme_windowNoTitle, false)) {
                b(1);
            } else if (obtainStyledAttributes.getBoolean(R$styleable.AppCompatTheme_windowActionBar, false)) {
                b(108);
            }
            if (obtainStyledAttributes.getBoolean(R$styleable.AppCompatTheme_windowActionBarOverlay, false)) {
                b(109);
            }
            if (obtainStyledAttributes.getBoolean(R$styleable.AppCompatTheme_windowActionModeOverlay, false)) {
                b(10);
            }
            this.H = obtainStyledAttributes.getBoolean(R$styleable.AppCompatTheme_android_windowIsFloating, false);
            obtainStyledAttributes.recycle();
            B();
            this.f49j.getDecorView();
            LayoutInflater from = LayoutInflater.from(this.f48i);
            if (this.I) {
                if (this.G) {
                    viewGroup = (ViewGroup) from.inflate(R$layout.abc_screen_simple_overlay_action_mode, (ViewGroup) null);
                } else {
                    viewGroup = (ViewGroup) from.inflate(R$layout.abc_screen_simple, (ViewGroup) null);
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    v.a((View) viewGroup, (r) new c());
                } else {
                    ((s) viewGroup).setOnFitSystemWindowsListener(new d());
                }
            } else if (this.H) {
                viewGroup = (ViewGroup) from.inflate(R$layout.abc_dialog_title_material, (ViewGroup) null);
                this.F = false;
                this.E = false;
            } else if (this.E) {
                TypedValue typedValue = new TypedValue();
                this.f48i.getTheme().resolveAttribute(R$attr.actionBarTheme, typedValue, true);
                if (typedValue.resourceId != 0) {
                    context = new androidx.appcompat.d.d(this.f48i, typedValue.resourceId);
                } else {
                    context = this.f48i;
                }
                viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R$layout.abc_screen_toolbar, (ViewGroup) null);
                androidx.appcompat.widget.o oVar = (androidx.appcompat.widget.o) viewGroup.findViewById(R$id.decor_content_parent);
                this.p = oVar;
                oVar.setWindowCallback(r());
                if (this.F) {
                    this.p.a(109);
                }
                if (this.C) {
                    this.p.a(2);
                }
                if (this.D) {
                    this.p.a(5);
                }
            } else {
                viewGroup = null;
            }
            if (viewGroup != null) {
                if (this.p == null) {
                    this.A = (TextView) viewGroup.findViewById(R$id.title);
                }
                m0.b(viewGroup);
                ContentFrameLayout contentFrameLayout = (ContentFrameLayout) viewGroup.findViewById(R$id.action_bar_activity_content);
                ViewGroup viewGroup2 = (ViewGroup) this.f49j.findViewById(16908290);
                if (viewGroup2 != null) {
                    while (viewGroup2.getChildCount() > 0) {
                        View childAt = viewGroup2.getChildAt(0);
                        viewGroup2.removeViewAt(0);
                        contentFrameLayout.addView(childAt);
                    }
                    viewGroup2.setId(-1);
                    contentFrameLayout.setId(16908290);
                    if (viewGroup2 instanceof FrameLayout) {
                        ((FrameLayout) viewGroup2).setForeground((Drawable) null);
                    }
                }
                this.f49j.setContentView(viewGroup);
                contentFrameLayout.setAttachListener(new e());
                return viewGroup;
            }
            throw new IllegalArgumentException("AppCompat does not support the current theme features: { windowActionBar: " + this.E + ", windowActionBarOverlay: " + this.F + ", android:windowIsFloating: " + this.H + ", windowActionModeOverlay: " + this.G + ", windowNoTitle: " + this.I + " }");
        }
        obtainStyledAttributes.recycle();
        throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
    }

    public void a(Context context) {
        b(false);
        this.N = true;
    }

    /* access modifiers changed from: package-private */
    public void a(ViewGroup viewGroup) {
    }

    public void b(Bundle bundle) {
        A();
    }

    public MenuInflater c() {
        if (this.n == null) {
            D();
            a aVar = this.m;
            this.n = new androidx.appcompat.d.g(aVar != null ? aVar.h() : this.f48i);
        }
        return this.n;
    }

    public a d() {
        D();
        return this.m;
    }

    public void e() {
        LayoutInflater from = LayoutInflater.from(this.f48i);
        if (from.getFactory() == null) {
            androidx.core.h.f.b(from, this);
        } else if (!(from.getFactory2() instanceof AppCompatDelegateImpl)) {
            Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
        }
    }

    public void f() {
        a d2 = d();
        if (d2 == null || !d2.i()) {
            k(0);
        }
    }

    public void g() {
        e.b((e) this);
        if (this.X) {
            this.f49j.getDecorView().removeCallbacks(this.Z);
        }
        this.P = false;
        this.Q = true;
        a aVar = this.m;
        if (aVar != null) {
            aVar.j();
        }
        y();
    }

    public void h() {
        a d2 = d();
        if (d2 != null) {
            d2.e(true);
        }
    }

    public void i() {
        this.P = true;
        l();
        e.a((e) this);
    }

    public void j() {
        this.P = false;
        e.b((e) this);
        a d2 = d();
        if (d2 != null) {
            d2.e(false);
        }
        if (this.f47h instanceof Dialog) {
            y();
        }
    }

    /* access modifiers changed from: package-private */
    public void m() {
        androidx.appcompat.view.menu.g gVar;
        androidx.appcompat.widget.o oVar = this.p;
        if (oVar != null) {
            oVar.g();
        }
        if (this.u != null) {
            this.f49j.getDecorView().removeCallbacks(this.v);
            if (this.u.isShowing()) {
                try {
                    this.u.dismiss();
                } catch (IllegalArgumentException unused) {
                }
            }
            this.u = null;
        }
        n();
        PanelFeatureState a2 = a(0, false);
        if (a2 != null && (gVar = a2.f54j) != null) {
            gVar.close();
        }
    }

    /* access modifiers changed from: package-private */
    public void n() {
        z zVar = this.w;
        if (zVar != null) {
            zVar.a();
        }
    }

    /* access modifiers changed from: package-private */
    public final Context o() {
        a d2 = d();
        Context h2 = d2 != null ? d2.h() : null;
        return h2 == null ? this.f48i : h2;
    }

    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return a(view, str, context, attributeSet);
    }

    /* access modifiers changed from: package-private */
    public final m p() {
        if (this.V == null) {
            this.V = new n(j.a(this.f48i));
        }
        return this.V;
    }

    /* access modifiers changed from: package-private */
    public final CharSequence q() {
        Object obj = this.f47h;
        if (obj instanceof Activity) {
            return ((Activity) obj).getTitle();
        }
        return this.o;
    }

    /* access modifiers changed from: package-private */
    public final Window.Callback r() {
        return this.f49j.getCallback();
    }

    public boolean s() {
        return this.x;
    }

    /* access modifiers changed from: package-private */
    public boolean t() {
        androidx.appcompat.d.b bVar = this.s;
        if (bVar != null) {
            bVar.a();
            return true;
        }
        a d2 = d();
        if (d2 == null || !d2.f()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public final a u() {
        return this.m;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = r1.z;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean v() {
        /*
            r1 = this;
            boolean r0 = r1.y
            if (r0 == 0) goto L_0x0010
            android.view.ViewGroup r0 = r1.z
            if (r0 == 0) goto L_0x0010
            boolean r0 = androidx.core.h.v.D(r0)
            if (r0 == 0) goto L_0x0010
            r0 = 1
            goto L_0x0011
        L_0x0010:
            r0 = 0
        L_0x0011:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.v():boolean");
    }

    private final class i implements m.a {
        i() {
        }

        public boolean a(androidx.appcompat.view.menu.g gVar) {
            Window.Callback r = AppCompatDelegateImpl.this.r();
            if (r == null) {
                return true;
            }
            r.onMenuOpened(108, gVar);
            return true;
        }

        public void a(androidx.appcompat.view.menu.g gVar, boolean z) {
            AppCompatDelegateImpl.this.b(gVar);
        }
    }

    AppCompatDelegateImpl(Dialog dialog, d dVar) {
        this(dialog.getContext(), dialog.getWindow(), dVar, dialog);
    }

    public void b(View view, ViewGroup.LayoutParams layoutParams) {
        A();
        ViewGroup viewGroup = (ViewGroup) this.z.findViewById(16908290);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        this.k.a().onContentChanged();
    }

    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView((View) null, str, context, attributeSet);
    }

    protected static final class PanelFeatureState {
        int a;
        int b;
        int c;
        int d;
        int e;

        /* renamed from: f  reason: collision with root package name */
        int f50f;

        /* renamed from: g  reason: collision with root package name */
        ViewGroup f51g;

        /* renamed from: h  reason: collision with root package name */
        View f52h;

        /* renamed from: i  reason: collision with root package name */
        View f53i;

        /* renamed from: j  reason: collision with root package name */
        androidx.appcompat.view.menu.g f54j;
        androidx.appcompat.view.menu.e k;
        Context l;
        boolean m;
        boolean n;
        boolean o;
        public boolean p;
        boolean q = false;
        boolean r;
        Bundle s;

        @SuppressLint({"BanParcelableUsage"})
        private static class SavedState implements Parcelable {
            public static final Parcelable.Creator<SavedState> CREATOR = new a();
            int e;

            /* renamed from: f  reason: collision with root package name */
            boolean f55f;

            /* renamed from: g  reason: collision with root package name */
            Bundle f56g;

            static class a implements Parcelable.ClassLoaderCreator<SavedState> {
                a() {
                }

                public SavedState[] newArray(int i2) {
                    return new SavedState[i2];
                }

                public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                    return SavedState.a(parcel, classLoader);
                }

                public SavedState createFromParcel(Parcel parcel) {
                    return SavedState.a(parcel, (ClassLoader) null);
                }
            }

            SavedState() {
            }

            static SavedState a(Parcel parcel, ClassLoader classLoader) {
                SavedState savedState = new SavedState();
                savedState.e = parcel.readInt();
                boolean z = true;
                if (parcel.readInt() != 1) {
                    z = false;
                }
                savedState.f55f = z;
                if (z) {
                    savedState.f56g = parcel.readBundle(classLoader);
                }
                return savedState;
            }

            public int describeContents() {
                return 0;
            }

            public void writeToParcel(Parcel parcel, int i2) {
                parcel.writeInt(this.e);
                parcel.writeInt(this.f55f ? 1 : 0);
                if (this.f55f) {
                    parcel.writeBundle(this.f56g);
                }
            }
        }

        PanelFeatureState(int i2) {
            this.a = i2;
        }

        public boolean a() {
            if (this.f52h == null) {
                return false;
            }
            if (this.f53i == null && this.k.a().getCount() <= 0) {
                return false;
            }
            return true;
        }

        /* access modifiers changed from: package-private */
        public void a(Context context) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme newTheme = context.getResources().newTheme();
            newTheme.setTo(context.getTheme());
            newTheme.resolveAttribute(R$attr.actionBarPopupTheme, typedValue, true);
            int i2 = typedValue.resourceId;
            if (i2 != 0) {
                newTheme.applyStyle(i2, true);
            }
            newTheme.resolveAttribute(R$attr.panelMenuListTheme, typedValue, true);
            int i3 = typedValue.resourceId;
            if (i3 != 0) {
                newTheme.applyStyle(i3, true);
            } else {
                newTheme.applyStyle(R$style.Theme_AppCompat_CompactMenu, true);
            }
            androidx.appcompat.d.d dVar = new androidx.appcompat.d.d(context, 0);
            dVar.getTheme().setTo(newTheme);
            this.l = dVar;
            TypedArray obtainStyledAttributes = dVar.obtainStyledAttributes(R$styleable.AppCompatTheme);
            this.b = obtainStyledAttributes.getResourceId(R$styleable.AppCompatTheme_panelBackground, 0);
            this.f50f = obtainStyledAttributes.getResourceId(R$styleable.AppCompatTheme_android_windowAnimationStyle, 0);
            obtainStyledAttributes.recycle();
        }

        /* access modifiers changed from: package-private */
        public void a(androidx.appcompat.view.menu.g gVar) {
            androidx.appcompat.view.menu.e eVar;
            androidx.appcompat.view.menu.g gVar2 = this.f54j;
            if (gVar != gVar2) {
                if (gVar2 != null) {
                    gVar2.b((androidx.appcompat.view.menu.m) this.k);
                }
                this.f54j = gVar;
                if (gVar != null && (eVar = this.k) != null) {
                    gVar.a((androidx.appcompat.view.menu.m) eVar);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public androidx.appcompat.view.menu.n a(m.a aVar) {
            if (this.f54j == null) {
                return null;
            }
            if (this.k == null) {
                androidx.appcompat.view.menu.e eVar = new androidx.appcompat.view.menu.e(this.l, R$layout.abc_list_menu_item_layout);
                this.k = eVar;
                eVar.a(aVar);
                this.f54j.a((androidx.appcompat.view.menu.m) this.k);
            }
            return this.k.a(this.f51g);
        }
    }

    private AppCompatDelegateImpl(Context context, Window window, d dVar, Object obj) {
        Integer num;
        AppCompatActivity G2;
        this.w = null;
        this.x = true;
        this.R = -100;
        this.Z = new b();
        this.f48i = context;
        this.l = dVar;
        this.f47h = obj;
        if (this.R == -100 && (obj instanceof Dialog) && (G2 = G()) != null) {
            this.R = G2.h().b();
        }
        if (this.R == -100 && (num = e0.get(this.f47h.getClass())) != null) {
            this.R = num.intValue();
            e0.remove(this.f47h.getClass());
        }
        if (window != null) {
            a(window);
        }
        androidx.appcompat.widget.f.c();
    }

    public void a(Bundle bundle) {
        this.N = true;
        b(false);
        B();
        Object obj = this.f47h;
        if (obj instanceof Activity) {
            String str = null;
            try {
                str = androidx.core.app.f.b((Activity) obj);
            } catch (IllegalArgumentException unused) {
            }
            if (str != null) {
                a u2 = u();
                if (u2 == null) {
                    this.a0 = true;
                } else {
                    u2.c(true);
                }
            }
        }
        this.O = true;
    }

    public void d(int i2) {
        this.S = i2;
    }

    /* access modifiers changed from: package-private */
    public void h(int i2) {
        a d2;
        if (i2 == 108 && (d2 = d()) != null) {
            d2.b(true);
        }
    }

    public boolean l() {
        return b(true);
    }

    class k extends androidx.appcompat.d.i {
        k(Window.Callback callback) {
            super(callback);
        }

        /* access modifiers changed from: package-private */
        public final ActionMode a(ActionMode.Callback callback) {
            f.a aVar = new f.a(AppCompatDelegateImpl.this.f48i, callback);
            androidx.appcompat.d.b a = AppCompatDelegateImpl.this.a((b.a) aVar);
            if (a != null) {
                return aVar.b(a);
            }
            return null;
        }

        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImpl.this.a(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
            return super.dispatchKeyShortcutEvent(keyEvent) || AppCompatDelegateImpl.this.b(keyEvent.getKeyCode(), keyEvent);
        }

        public void onContentChanged() {
        }

        public boolean onCreatePanelMenu(int i2, Menu menu) {
            if (i2 != 0 || (menu instanceof androidx.appcompat.view.menu.g)) {
                return super.onCreatePanelMenu(i2, menu);
            }
            return false;
        }

        public boolean onMenuOpened(int i2, Menu menu) {
            super.onMenuOpened(i2, menu);
            AppCompatDelegateImpl.this.h(i2);
            return true;
        }

        public void onPanelClosed(int i2, Menu menu) {
            super.onPanelClosed(i2, menu);
            AppCompatDelegateImpl.this.i(i2);
        }

        public boolean onPreparePanel(int i2, View view, Menu menu) {
            androidx.appcompat.view.menu.g gVar = menu instanceof androidx.appcompat.view.menu.g ? (androidx.appcompat.view.menu.g) menu : null;
            if (i2 == 0 && gVar == null) {
                return false;
            }
            if (gVar != null) {
                gVar.c(true);
            }
            boolean onPreparePanel = super.onPreparePanel(i2, view, menu);
            if (gVar != null) {
                gVar.c(false);
            }
            return onPreparePanel;
        }

        public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu, int i2) {
            androidx.appcompat.view.menu.g gVar;
            PanelFeatureState a = AppCompatDelegateImpl.this.a(0, true);
            if (a == null || (gVar = a.f54j) == null) {
                super.onProvideKeyboardShortcuts(list, menu, i2);
            } else {
                super.onProvideKeyboardShortcuts(list, gVar, i2);
            }
        }

        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            if (Build.VERSION.SDK_INT >= 23) {
                return null;
            }
            if (AppCompatDelegateImpl.this.s()) {
                return a(callback);
            }
            return super.onWindowStartingActionMode(callback);
        }

        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i2) {
            if (!AppCompatDelegateImpl.this.s() || i2 != 0) {
                return super.onWindowStartingActionMode(callback, i2);
            }
            return a(callback);
        }
    }

    private boolean d(int i2, KeyEvent keyEvent) {
        if (keyEvent.getRepeatCount() != 0) {
            return false;
        }
        PanelFeatureState a2 = a(i2, true);
        if (!a2.o) {
            return b(a2, keyEvent);
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void f(int i2) {
        PanelFeatureState a2;
        PanelFeatureState a3 = a(i2, true);
        if (a3.f54j != null) {
            Bundle bundle = new Bundle();
            a3.f54j.c(bundle);
            if (bundle.size() > 0) {
                a3.s = bundle;
            }
            a3.f54j.s();
            a3.f54j.clear();
        }
        a3.r = true;
        a3.q = true;
        if ((i2 == 108 || i2 == 0) && this.p != null && (a2 = a(0, false)) != null) {
            a2.m = false;
            b(a2, (KeyEvent) null);
        }
    }

    /* access modifiers changed from: package-private */
    public void i(int i2) {
        if (i2 == 108) {
            a d2 = d();
            if (d2 != null) {
                d2.b(false);
            }
        } else if (i2 == 0) {
            PanelFeatureState a2 = a(i2, true);
            if (a2.o) {
                a(a2, false);
            }
        }
    }

    private final class p implements m.a {
        p() {
        }

        public void a(androidx.appcompat.view.menu.g gVar, boolean z) {
            androidx.appcompat.view.menu.g m = gVar.m();
            boolean z2 = m != gVar;
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (z2) {
                gVar = m;
            }
            PanelFeatureState a = appCompatDelegateImpl.a((Menu) gVar);
            if (a == null) {
                return;
            }
            if (z2) {
                AppCompatDelegateImpl.this.a(a.a, a, m);
                AppCompatDelegateImpl.this.a(a, true);
                return;
            }
            AppCompatDelegateImpl.this.a(a, z);
        }

        public boolean a(androidx.appcompat.view.menu.g gVar) {
            Window.Callback r;
            if (gVar != null) {
                return true;
            }
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (!appCompatDelegateImpl.E || (r = appCompatDelegateImpl.r()) == null || AppCompatDelegateImpl.this.Q) {
                return true;
            }
            r.onMenuOpened(108, gVar);
            return true;
        }
    }

    public void c(int i2) {
        A();
        ViewGroup viewGroup = (ViewGroup) this.z.findViewById(16908290);
        viewGroup.removeAllViews();
        LayoutInflater.from(this.f48i).inflate(i2, viewGroup);
        this.k.a().onContentChanged();
    }

    /* access modifiers changed from: package-private */
    public void e(int i2) {
        a(a(i2, true), true);
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x006c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean e(int r4, android.view.KeyEvent r5) {
        /*
            r3 = this;
            androidx.appcompat.d.b r0 = r3.s
            r1 = 0
            if (r0 == 0) goto L_0x0006
            return r1
        L_0x0006:
            r0 = 1
            androidx.appcompat.app.AppCompatDelegateImpl$PanelFeatureState r2 = r3.a((int) r4, (boolean) r0)
            if (r4 != 0) goto L_0x0043
            androidx.appcompat.widget.o r4 = r3.p
            if (r4 == 0) goto L_0x0043
            boolean r4 = r4.f()
            if (r4 == 0) goto L_0x0043
            android.content.Context r4 = r3.f48i
            android.view.ViewConfiguration r4 = android.view.ViewConfiguration.get(r4)
            boolean r4 = r4.hasPermanentMenuKey()
            if (r4 != 0) goto L_0x0043
            androidx.appcompat.widget.o r4 = r3.p
            boolean r4 = r4.b()
            if (r4 != 0) goto L_0x003c
            boolean r4 = r3.Q
            if (r4 != 0) goto L_0x0062
            boolean r4 = r3.b((androidx.appcompat.app.AppCompatDelegateImpl.PanelFeatureState) r2, (android.view.KeyEvent) r5)
            if (r4 == 0) goto L_0x0062
            androidx.appcompat.widget.o r4 = r3.p
            boolean r0 = r4.d()
            goto L_0x006a
        L_0x003c:
            androidx.appcompat.widget.o r4 = r3.p
            boolean r0 = r4.c()
            goto L_0x006a
        L_0x0043:
            boolean r4 = r2.o
            if (r4 != 0) goto L_0x0064
            boolean r4 = r2.n
            if (r4 == 0) goto L_0x004c
            goto L_0x0064
        L_0x004c:
            boolean r4 = r2.m
            if (r4 == 0) goto L_0x0062
            boolean r4 = r2.r
            if (r4 == 0) goto L_0x005b
            r2.m = r1
            boolean r4 = r3.b((androidx.appcompat.app.AppCompatDelegateImpl.PanelFeatureState) r2, (android.view.KeyEvent) r5)
            goto L_0x005c
        L_0x005b:
            r4 = 1
        L_0x005c:
            if (r4 == 0) goto L_0x0062
            r3.a((androidx.appcompat.app.AppCompatDelegateImpl.PanelFeatureState) r2, (android.view.KeyEvent) r5)
            goto L_0x006a
        L_0x0062:
            r0 = 0
            goto L_0x006a
        L_0x0064:
            boolean r4 = r2.o
            r3.a((androidx.appcompat.app.AppCompatDelegateImpl.PanelFeatureState) r2, (boolean) r0)
            r0 = r4
        L_0x006a:
            if (r0 == 0) goto L_0x0083
            android.content.Context r4 = r3.f48i
            java.lang.String r5 = "audio"
            java.lang.Object r4 = r4.getSystemService(r5)
            android.media.AudioManager r4 = (android.media.AudioManager) r4
            if (r4 == 0) goto L_0x007c
            r4.playSoundEffect(r1)
            goto L_0x0083
        L_0x007c:
            java.lang.String r4 = "AppCompatDelegate"
            java.lang.String r5 = "Couldn't get audio manager"
            android.util.Log.w(r4, r5)
        L_0x0083:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.e(int, android.view.KeyEvent):boolean");
    }

    public boolean b(int i2) {
        int l2 = l(i2);
        if (this.I && l2 == 108) {
            return false;
        }
        if (this.E && l2 == 1) {
            this.E = false;
        }
        if (l2 == 1) {
            F();
            this.I = true;
            return true;
        } else if (l2 == 2) {
            F();
            this.C = true;
            return true;
        } else if (l2 == 5) {
            F();
            this.D = true;
            return true;
        } else if (l2 == 10) {
            F();
            this.G = true;
            return true;
        } else if (l2 == 108) {
            F();
            this.E = true;
            return true;
        } else if (l2 != 109) {
            return this.f49j.requestFeature(l2);
        } else {
            F();
            this.F = true;
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    public int j(int i2) {
        boolean z2;
        boolean z3;
        ActionBarContextView actionBarContextView = this.t;
        int i3 = 0;
        if (actionBarContextView == null || !(actionBarContextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            z2 = false;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.t.getLayoutParams();
            boolean z4 = true;
            if (this.t.isShown()) {
                if (this.b0 == null) {
                    this.b0 = new Rect();
                    this.c0 = new Rect();
                }
                Rect rect = this.b0;
                Rect rect2 = this.c0;
                rect.set(0, i2, 0, 0);
                m0.a(this.z, rect, rect2);
                if (marginLayoutParams.topMargin != (rect2.top == 0 ? i2 : 0)) {
                    marginLayoutParams.topMargin = i2;
                    View view = this.B;
                    if (view == null) {
                        View view2 = new View(this.f48i);
                        this.B = view2;
                        view2.setBackgroundColor(this.f48i.getResources().getColor(R$color.abc_input_method_navigation_guard));
                        this.z.addView(this.B, -1, new ViewGroup.LayoutParams(-1, i2));
                    } else {
                        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                        if (layoutParams.height != i2) {
                            layoutParams.height = i2;
                            this.B.setLayoutParams(layoutParams);
                        }
                    }
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (this.B == null) {
                    z4 = false;
                }
                if (!this.G && z4) {
                    i2 = 0;
                }
                boolean z5 = z4;
                z4 = z3;
                z2 = z5;
            } else if (marginLayoutParams.topMargin != 0) {
                marginLayoutParams.topMargin = 0;
                z2 = false;
            } else {
                z2 = false;
                z4 = false;
            }
            if (z4) {
                this.t.setLayoutParams(marginLayoutParams);
            }
        }
        View view3 = this.B;
        if (view3 != null) {
            if (!z2) {
                i3 = 8;
            }
            view3.setVisibility(i3);
        }
        return i2;
    }

    /* access modifiers changed from: package-private */
    public int g(int i2) {
        if (i2 == -100) {
            return -1;
        }
        if (i2 == -1) {
            return i2;
        }
        if (i2 != 0) {
            if (i2 == 1 || i2 == 2) {
                return i2;
            }
            if (i2 == 3) {
                return C().c();
            }
            throw new IllegalStateException("Unknown value set for night mode. Please use one of the MODE_NIGHT values from AppCompatDelegate.");
        } else if (Build.VERSION.SDK_INT < 23 || ((UiModeManager) this.f48i.getSystemService(UiModeManager.class)).getNightMode() != 0) {
            return p().c();
        } else {
            return -1;
        }
    }

    public void c(Bundle bundle) {
        if (this.R != -100) {
            e0.put(this.f47h.getClass(), Integer.valueOf(this.R));
        }
    }

    public void a(Toolbar toolbar) {
        if (this.f47h instanceof Activity) {
            a d2 = d();
            if (!(d2 instanceof k)) {
                this.n = null;
                if (d2 != null) {
                    d2.j();
                }
                if (toolbar != null) {
                    h hVar = new h(toolbar, q(), this.k);
                    this.m = hVar;
                    this.f49j.setCallback(hVar.l());
                } else {
                    this.m = null;
                    this.f49j.setCallback(this.k);
                }
                f();
                return;
            }
            throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
        }
    }

    /* access modifiers changed from: package-private */
    public boolean c(int i2, KeyEvent keyEvent) {
        if (i2 == 4) {
            boolean z2 = this.M;
            this.M = false;
            PanelFeatureState a2 = a(0, false);
            if (a2 != null && a2.o) {
                if (!z2) {
                    a(a2, true);
                }
                return true;
            } else if (t()) {
                return true;
            }
        } else if (i2 == 82) {
            e(0, keyEvent);
            return true;
        }
        return false;
    }

    private boolean c(PanelFeatureState panelFeatureState) {
        Context context = this.f48i;
        int i2 = panelFeatureState.a;
        if ((i2 == 0 || i2 == 108) && this.p != null) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = context.getTheme();
            theme.resolveAttribute(R$attr.actionBarTheme, typedValue, true);
            Resources.Theme theme2 = null;
            if (typedValue.resourceId != 0) {
                theme2 = context.getResources().newTheme();
                theme2.setTo(theme);
                theme2.applyStyle(typedValue.resourceId, true);
                theme2.resolveAttribute(R$attr.actionBarWidgetTheme, typedValue, true);
            } else {
                theme.resolveAttribute(R$attr.actionBarWidgetTheme, typedValue, true);
            }
            if (typedValue.resourceId != 0) {
                if (theme2 == null) {
                    theme2 = context.getResources().newTheme();
                    theme2.setTo(theme);
                }
                theme2.applyStyle(typedValue.resourceId, true);
            }
            if (theme2 != null) {
                androidx.appcompat.d.d dVar = new androidx.appcompat.d.d(context, 0);
                dVar.getTheme().setTo(theme2);
                context = dVar;
            }
        }
        androidx.appcompat.view.menu.g gVar = new androidx.appcompat.view.menu.g(context);
        gVar.a((g.a) this);
        panelFeatureState.a(gVar);
        return true;
    }

    public <T extends View> T a(int i2) {
        A();
        return this.f49j.findViewById(i2);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0025  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0029  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public androidx.appcompat.d.b b(androidx.appcompat.d.b.a r8) {
        /*
            r7 = this;
            r7.n()
            androidx.appcompat.d.b r0 = r7.s
            if (r0 == 0) goto L_0x000a
            r0.a()
        L_0x000a:
            boolean r0 = r8 instanceof androidx.appcompat.app.AppCompatDelegateImpl.j
            if (r0 != 0) goto L_0x0014
            androidx.appcompat.app.AppCompatDelegateImpl$j r0 = new androidx.appcompat.app.AppCompatDelegateImpl$j
            r0.<init>(r8)
            r8 = r0
        L_0x0014:
            androidx.appcompat.app.d r0 = r7.l
            r1 = 0
            if (r0 == 0) goto L_0x0022
            boolean r2 = r7.Q
            if (r2 != 0) goto L_0x0022
            androidx.appcompat.d.b r0 = r0.a((androidx.appcompat.d.b.a) r8)     // Catch:{ AbstractMethodError -> 0x0022 }
            goto L_0x0023
        L_0x0022:
            r0 = r1
        L_0x0023:
            if (r0 == 0) goto L_0x0029
            r7.s = r0
            goto L_0x0161
        L_0x0029:
            androidx.appcompat.widget.ActionBarContextView r0 = r7.t
            r2 = 0
            r3 = 1
            if (r0 != 0) goto L_0x00d4
            boolean r0 = r7.H
            if (r0 == 0) goto L_0x00b5
            android.util.TypedValue r0 = new android.util.TypedValue
            r0.<init>()
            android.content.Context r4 = r7.f48i
            android.content.res.Resources$Theme r4 = r4.getTheme()
            int r5 = androidx.appcompat.R$attr.actionBarTheme
            r4.resolveAttribute(r5, r0, r3)
            int r5 = r0.resourceId
            if (r5 == 0) goto L_0x0068
            android.content.Context r5 = r7.f48i
            android.content.res.Resources r5 = r5.getResources()
            android.content.res.Resources$Theme r5 = r5.newTheme()
            r5.setTo(r4)
            int r4 = r0.resourceId
            r5.applyStyle(r4, r3)
            androidx.appcompat.d.d r4 = new androidx.appcompat.d.d
            android.content.Context r6 = r7.f48i
            r4.<init>((android.content.Context) r6, (int) r2)
            android.content.res.Resources$Theme r6 = r4.getTheme()
            r6.setTo(r5)
            goto L_0x006a
        L_0x0068:
            android.content.Context r4 = r7.f48i
        L_0x006a:
            androidx.appcompat.widget.ActionBarContextView r5 = new androidx.appcompat.widget.ActionBarContextView
            r5.<init>(r4)
            r7.t = r5
            android.widget.PopupWindow r5 = new android.widget.PopupWindow
            int r6 = androidx.appcompat.R$attr.actionModePopupWindowStyle
            r5.<init>(r4, r1, r6)
            r7.u = r5
            r6 = 2
            androidx.core.widget.h.a((android.widget.PopupWindow) r5, (int) r6)
            android.widget.PopupWindow r5 = r7.u
            androidx.appcompat.widget.ActionBarContextView r6 = r7.t
            r5.setContentView(r6)
            android.widget.PopupWindow r5 = r7.u
            r6 = -1
            r5.setWidth(r6)
            android.content.res.Resources$Theme r5 = r4.getTheme()
            int r6 = androidx.appcompat.R$attr.actionBarSize
            r5.resolveAttribute(r6, r0, r3)
            int r0 = r0.data
            android.content.res.Resources r4 = r4.getResources()
            android.util.DisplayMetrics r4 = r4.getDisplayMetrics()
            int r0 = android.util.TypedValue.complexToDimensionPixelSize(r0, r4)
            androidx.appcompat.widget.ActionBarContextView r4 = r7.t
            r4.setContentHeight(r0)
            android.widget.PopupWindow r0 = r7.u
            r4 = -2
            r0.setHeight(r4)
            androidx.appcompat.app.AppCompatDelegateImpl$f r0 = new androidx.appcompat.app.AppCompatDelegateImpl$f
            r0.<init>()
            r7.v = r0
            goto L_0x00d4
        L_0x00b5:
            android.view.ViewGroup r0 = r7.z
            int r4 = androidx.appcompat.R$id.action_mode_bar_stub
            android.view.View r0 = r0.findViewById(r4)
            androidx.appcompat.widget.ViewStubCompat r0 = (androidx.appcompat.widget.ViewStubCompat) r0
            if (r0 == 0) goto L_0x00d4
            android.content.Context r4 = r7.o()
            android.view.LayoutInflater r4 = android.view.LayoutInflater.from(r4)
            r0.setLayoutInflater(r4)
            android.view.View r0 = r0.a()
            androidx.appcompat.widget.ActionBarContextView r0 = (androidx.appcompat.widget.ActionBarContextView) r0
            r7.t = r0
        L_0x00d4:
            androidx.appcompat.widget.ActionBarContextView r0 = r7.t
            if (r0 == 0) goto L_0x0161
            r7.n()
            androidx.appcompat.widget.ActionBarContextView r0 = r7.t
            r0.c()
            androidx.appcompat.d.e r0 = new androidx.appcompat.d.e
            androidx.appcompat.widget.ActionBarContextView r4 = r7.t
            android.content.Context r4 = r4.getContext()
            androidx.appcompat.widget.ActionBarContextView r5 = r7.t
            android.widget.PopupWindow r6 = r7.u
            if (r6 != 0) goto L_0x00ef
            goto L_0x00f0
        L_0x00ef:
            r3 = 0
        L_0x00f0:
            r0.<init>(r4, r5, r8, r3)
            android.view.Menu r3 = r0.c()
            boolean r8 = r8.b(r0, r3)
            if (r8 == 0) goto L_0x015f
            r0.i()
            androidx.appcompat.widget.ActionBarContextView r8 = r7.t
            r8.a(r0)
            r7.s = r0
            boolean r8 = r7.v()
            r0 = 1065353216(0x3f800000, float:1.0)
            if (r8 == 0) goto L_0x0129
            androidx.appcompat.widget.ActionBarContextView r8 = r7.t
            r1 = 0
            r8.setAlpha(r1)
            androidx.appcompat.widget.ActionBarContextView r8 = r7.t
            androidx.core.h.z r8 = androidx.core.h.v.a(r8)
            r8.a((float) r0)
            r7.w = r8
            androidx.appcompat.app.AppCompatDelegateImpl$g r0 = new androidx.appcompat.app.AppCompatDelegateImpl$g
            r0.<init>()
            r8.a((androidx.core.h.a0) r0)
            goto L_0x014f
        L_0x0129:
            androidx.appcompat.widget.ActionBarContextView r8 = r7.t
            r8.setAlpha(r0)
            androidx.appcompat.widget.ActionBarContextView r8 = r7.t
            r8.setVisibility(r2)
            androidx.appcompat.widget.ActionBarContextView r8 = r7.t
            r0 = 32
            r8.sendAccessibilityEvent(r0)
            androidx.appcompat.widget.ActionBarContextView r8 = r7.t
            android.view.ViewParent r8 = r8.getParent()
            boolean r8 = r8 instanceof android.view.View
            if (r8 == 0) goto L_0x014f
            androidx.appcompat.widget.ActionBarContextView r8 = r7.t
            android.view.ViewParent r8 = r8.getParent()
            android.view.View r8 = (android.view.View) r8
            androidx.core.h.v.I(r8)
        L_0x014f:
            android.widget.PopupWindow r8 = r7.u
            if (r8 == 0) goto L_0x0161
            android.view.Window r8 = r7.f49j
            android.view.View r8 = r8.getDecorView()
            java.lang.Runnable r0 = r7.v
            r8.post(r0)
            goto L_0x0161
        L_0x015f:
            r7.s = r1
        L_0x0161:
            androidx.appcompat.d.b r8 = r7.s
            if (r8 == 0) goto L_0x016c
            androidx.appcompat.app.d r0 = r7.l
            if (r0 == 0) goto L_0x016c
            r0.a((androidx.appcompat.d.b) r8)
        L_0x016c:
            androidx.appcompat.d.b r8 = r7.s
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.b(androidx.appcompat.d.b$a):androidx.appcompat.d.b");
    }

    public void a(Configuration configuration) {
        a d2;
        if (this.E && this.y && (d2 = d()) != null) {
            d2.a(configuration);
        }
        androidx.appcompat.widget.f.b().a(this.f48i);
        b(false);
    }

    public void a(View view) {
        A();
        ViewGroup viewGroup = (ViewGroup) this.z.findViewById(16908290);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        this.k.a().onContentChanged();
    }

    public void a(View view, ViewGroup.LayoutParams layoutParams) {
        A();
        ((ViewGroup) this.z.findViewById(16908290)).addView(view, layoutParams);
        this.k.a().onContentChanged();
    }

    private void a(Window window) {
        if (this.f49j == null) {
            Window.Callback callback = window.getCallback();
            if (!(callback instanceof k)) {
                k kVar = new k(callback);
                this.k = kVar;
                window.setCallback(kVar);
                g0 a2 = g0.a(this.f48i, (AttributeSet) null, g0);
                Drawable c2 = a2.c(0);
                if (c2 != null) {
                    window.setBackgroundDrawable(c2);
                }
                a2.a();
                this.f49j = window;
                return;
            }
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        throw new IllegalStateException("AppCompat has already installed itself into the Window");
    }

    private void c(int i2, boolean z2) {
        Resources resources = this.f48i.getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());
        configuration.uiMode = i2 | (resources.getConfiguration().uiMode & -49);
        resources.updateConfiguration(configuration, (DisplayMetrics) null);
        if (Build.VERSION.SDK_INT < 26) {
            g.a(resources);
        }
        int i3 = this.S;
        if (i3 != 0) {
            this.f48i.setTheme(i3);
            if (Build.VERSION.SDK_INT >= 23) {
                this.f48i.getTheme().applyStyle(this.S, true);
            }
        }
        if (z2) {
            Object obj = this.f47h;
            if (obj instanceof Activity) {
                Activity activity = (Activity) obj;
                if (activity instanceof LifecycleOwner) {
                    if (((LifecycleOwner) activity).getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                        activity.onConfigurationChanged(configuration);
                    }
                } else if (this.P) {
                    activity.onConfigurationChanged(configuration);
                }
            }
        }
    }

    public final void a(CharSequence charSequence) {
        this.o = charSequence;
        androidx.appcompat.widget.o oVar = this.p;
        if (oVar != null) {
            oVar.setWindowTitle(charSequence);
        } else if (u() != null) {
            u().b(charSequence);
        } else {
            TextView textView = this.A;
            if (textView != null) {
                textView.setText(charSequence);
            }
        }
    }

    public boolean a(androidx.appcompat.view.menu.g gVar, MenuItem menuItem) {
        PanelFeatureState a2;
        Window.Callback r2 = r();
        if (r2 == null || this.Q || (a2 = a((Menu) gVar.m())) == null) {
            return false;
        }
        return r2.onMenuItemSelected(a2.a, menuItem);
    }

    public void a(androidx.appcompat.view.menu.g gVar) {
        a(gVar, true);
    }

    public androidx.appcompat.d.b a(b.a aVar) {
        d dVar;
        if (aVar != null) {
            androidx.appcompat.d.b bVar = this.s;
            if (bVar != null) {
                bVar.a();
            }
            j jVar = new j(aVar);
            a d2 = d();
            if (d2 != null) {
                androidx.appcompat.d.b a2 = d2.a((b.a) jVar);
                this.s = a2;
                if (!(a2 == null || (dVar = this.l) == null)) {
                    dVar.a(a2);
                }
            }
            if (this.s == null) {
                this.s = b((b.a) jVar);
            }
            return this.s;
        }
        throw new IllegalArgumentException("ActionMode callback can not be null.");
    }

    /* access modifiers changed from: package-private */
    public boolean a(KeyEvent keyEvent) {
        View decorView;
        Object obj = this.f47h;
        boolean z2 = true;
        if (((obj instanceof e.a) || (obj instanceof f)) && (decorView = this.f49j.getDecorView()) != null && androidx.core.h.e.a(decorView, keyEvent)) {
            return true;
        }
        if (keyEvent.getKeyCode() == 82 && this.k.a().dispatchKeyEvent(keyEvent)) {
            return true;
        }
        int keyCode = keyEvent.getKeyCode();
        if (keyEvent.getAction() != 0) {
            z2 = false;
        }
        return z2 ? a(keyCode, keyEvent) : c(keyCode, keyEvent);
    }

    /* access modifiers changed from: package-private */
    public boolean a(int i2, KeyEvent keyEvent) {
        boolean z2 = true;
        if (i2 == 4) {
            if ((keyEvent.getFlags() & 128) == 0) {
                z2 = false;
            }
            this.M = z2;
        } else if (i2 == 82) {
            d(0, keyEvent);
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean b(int i2, KeyEvent keyEvent) {
        a d2 = d();
        if (d2 != null && d2.a(i2, keyEvent)) {
            return true;
        }
        PanelFeatureState panelFeatureState = this.L;
        if (panelFeatureState == null || !a(panelFeatureState, keyEvent.getKeyCode(), keyEvent, 1)) {
            if (this.L == null) {
                PanelFeatureState a2 = a(0, true);
                b(a2, keyEvent);
                boolean a3 = a(a2, keyEvent.getKeyCode(), keyEvent, 1);
                a2.m = false;
                if (a3) {
                    return true;
                }
            }
            return false;
        }
        PanelFeatureState panelFeatureState2 = this.L;
        if (panelFeatureState2 != null) {
            panelFeatureState2.n = true;
        }
        return true;
    }

    public View a(View view, String str, Context context, AttributeSet attributeSet) {
        boolean z2;
        boolean z3 = false;
        if (this.d0 == null) {
            String string = this.f48i.obtainStyledAttributes(R$styleable.AppCompatTheme).getString(R$styleable.AppCompatTheme_viewInflaterClass);
            if (string == null || AppCompatViewInflater.class.getName().equals(string)) {
                this.d0 = new AppCompatViewInflater();
            } else {
                try {
                    this.d0 = (AppCompatViewInflater) Class.forName(string).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                } catch (Throwable th) {
                    Log.i("AppCompatDelegate", "Failed to instantiate custom view inflater " + string + ". Falling back to default.", th);
                    this.d0 = new AppCompatViewInflater();
                }
            }
        }
        if (f0) {
            if (!(attributeSet instanceof XmlPullParser)) {
                z3 = a((ViewParent) view);
            } else if (((XmlPullParser) attributeSet).getDepth() > 1) {
                z3 = true;
            }
            z2 = z3;
        } else {
            z2 = false;
        }
        return this.d0.createView(view, str, context, attributeSet, z2, f0, true, l0.b());
    }

    private boolean b(PanelFeatureState panelFeatureState) {
        panelFeatureState.a(o());
        panelFeatureState.f51g = new o(panelFeatureState.l);
        panelFeatureState.c = 81;
        return true;
    }

    private boolean b(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        androidx.appcompat.widget.o oVar;
        androidx.appcompat.widget.o oVar2;
        androidx.appcompat.widget.o oVar3;
        if (this.Q) {
            return false;
        }
        if (panelFeatureState.m) {
            return true;
        }
        PanelFeatureState panelFeatureState2 = this.L;
        if (!(panelFeatureState2 == null || panelFeatureState2 == panelFeatureState)) {
            a(panelFeatureState2, false);
        }
        Window.Callback r2 = r();
        if (r2 != null) {
            panelFeatureState.f53i = r2.onCreatePanelView(panelFeatureState.a);
        }
        int i2 = panelFeatureState.a;
        boolean z2 = i2 == 0 || i2 == 108;
        if (z2 && (oVar3 = this.p) != null) {
            oVar3.e();
        }
        if (panelFeatureState.f53i == null && (!z2 || !(u() instanceof h))) {
            if (panelFeatureState.f54j == null || panelFeatureState.r) {
                if (panelFeatureState.f54j == null && (!c(panelFeatureState) || panelFeatureState.f54j == null)) {
                    return false;
                }
                if (z2 && this.p != null) {
                    if (this.q == null) {
                        this.q = new i();
                    }
                    this.p.a(panelFeatureState.f54j, this.q);
                }
                panelFeatureState.f54j.s();
                if (!r2.onCreatePanelMenu(panelFeatureState.a, panelFeatureState.f54j)) {
                    panelFeatureState.a((androidx.appcompat.view.menu.g) null);
                    if (z2 && (oVar2 = this.p) != null) {
                        oVar2.a((Menu) null, this.q);
                    }
                    return false;
                }
                panelFeatureState.r = false;
            }
            panelFeatureState.f54j.s();
            Bundle bundle = panelFeatureState.s;
            if (bundle != null) {
                panelFeatureState.f54j.a(bundle);
                panelFeatureState.s = null;
            }
            if (!r2.onPreparePanel(0, panelFeatureState.f53i, panelFeatureState.f54j)) {
                if (z2 && (oVar = this.p) != null) {
                    oVar.a((Menu) null, this.q);
                }
                panelFeatureState.f54j.r();
                return false;
            }
            boolean z3 = KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1;
            panelFeatureState.p = z3;
            panelFeatureState.f54j.setQwertyMode(z3);
            panelFeatureState.f54j.r();
        }
        panelFeatureState.m = true;
        panelFeatureState.n = false;
        this.L = panelFeatureState;
        return true;
    }

    private boolean a(ViewParent viewParent) {
        if (viewParent == null) {
            return false;
        }
        View decorView = this.f49j.getDecorView();
        while (viewParent != null) {
            if (viewParent == decorView || !(viewParent instanceof View) || v.C((View) viewParent)) {
                return false;
            }
            viewParent = viewParent.getParent();
        }
        return true;
    }

    private void a(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        int i2;
        ViewGroup.LayoutParams layoutParams;
        if (!panelFeatureState.o && !this.Q) {
            if (panelFeatureState.a == 0) {
                if ((this.f48i.getResources().getConfiguration().screenLayout & 15) == 4) {
                    return;
                }
            }
            Window.Callback r2 = r();
            if (r2 == null || r2.onMenuOpened(panelFeatureState.a, panelFeatureState.f54j)) {
                WindowManager windowManager = (WindowManager) this.f48i.getSystemService("window");
                if (windowManager != null && b(panelFeatureState, keyEvent)) {
                    if (panelFeatureState.f51g == null || panelFeatureState.q) {
                        ViewGroup viewGroup = panelFeatureState.f51g;
                        if (viewGroup == null) {
                            if (!b(panelFeatureState) || panelFeatureState.f51g == null) {
                                return;
                            }
                        } else if (panelFeatureState.q && viewGroup.getChildCount() > 0) {
                            panelFeatureState.f51g.removeAllViews();
                        }
                        if (a(panelFeatureState) && panelFeatureState.a()) {
                            ViewGroup.LayoutParams layoutParams2 = panelFeatureState.f52h.getLayoutParams();
                            if (layoutParams2 == null) {
                                layoutParams2 = new ViewGroup.LayoutParams(-2, -2);
                            }
                            panelFeatureState.f51g.setBackgroundResource(panelFeatureState.b);
                            ViewParent parent = panelFeatureState.f52h.getParent();
                            if (parent instanceof ViewGroup) {
                                ((ViewGroup) parent).removeView(panelFeatureState.f52h);
                            }
                            panelFeatureState.f51g.addView(panelFeatureState.f52h, layoutParams2);
                            if (!panelFeatureState.f52h.hasFocus()) {
                                panelFeatureState.f52h.requestFocus();
                            }
                        } else {
                            return;
                        }
                    } else {
                        View view = panelFeatureState.f53i;
                        if (!(view == null || (layoutParams = view.getLayoutParams()) == null || layoutParams.width != -1)) {
                            i2 = -1;
                            panelFeatureState.n = false;
                            WindowManager.LayoutParams layoutParams3 = new WindowManager.LayoutParams(i2, -2, panelFeatureState.d, panelFeatureState.e, 1002, 8519680, -3);
                            layoutParams3.gravity = panelFeatureState.c;
                            layoutParams3.windowAnimations = panelFeatureState.f50f;
                            windowManager.addView(panelFeatureState.f51g, layoutParams3);
                            panelFeatureState.o = true;
                            return;
                        }
                    }
                    i2 = -2;
                    panelFeatureState.n = false;
                    WindowManager.LayoutParams layoutParams32 = new WindowManager.LayoutParams(i2, -2, panelFeatureState.d, panelFeatureState.e, 1002, 8519680, -3);
                    layoutParams32.gravity = panelFeatureState.c;
                    layoutParams32.windowAnimations = panelFeatureState.f50f;
                    windowManager.addView(panelFeatureState.f51g, layoutParams32);
                    panelFeatureState.o = true;
                    return;
                }
                return;
            }
            a(panelFeatureState, true);
        }
    }

    /* access modifiers changed from: package-private */
    public void b(androidx.appcompat.view.menu.g gVar) {
        if (!this.J) {
            this.J = true;
            this.p.g();
            Window.Callback r2 = r();
            if (r2 != null && !this.Q) {
                r2.onPanelClosed(108, gVar);
            }
            this.J = false;
        }
    }

    private void a(androidx.appcompat.view.menu.g gVar, boolean z2) {
        androidx.appcompat.widget.o oVar = this.p;
        if (oVar == null || !oVar.f() || (ViewConfiguration.get(this.f48i).hasPermanentMenuKey() && !this.p.a())) {
            PanelFeatureState a2 = a(0, true);
            a2.q = true;
            a(a2, false);
            a(a2, (KeyEvent) null);
            return;
        }
        Window.Callback r2 = r();
        if (this.p.b() && z2) {
            this.p.c();
            if (!this.Q) {
                r2.onPanelClosed(108, a(0, true).f54j);
            }
        } else if (r2 != null && !this.Q) {
            if (this.X && (this.Y & 1) != 0) {
                this.f49j.getDecorView().removeCallbacks(this.Z);
                this.Z.run();
            }
            PanelFeatureState a3 = a(0, true);
            androidx.appcompat.view.menu.g gVar2 = a3.f54j;
            if (gVar2 != null && !a3.r && r2.onPreparePanel(0, a3.f53i, gVar2)) {
                r2.onMenuOpened(108, a3.f54j);
                this.p.d();
            }
        }
    }

    private boolean b(boolean z2) {
        if (this.Q) {
            return false;
        }
        int x2 = x();
        boolean b2 = b(g(x2), z2);
        if (x2 == 0) {
            p().e();
        } else {
            m mVar = this.V;
            if (mVar != null) {
                mVar.a();
            }
        }
        if (x2 == 3) {
            C().e();
        } else {
            m mVar2 = this.W;
            if (mVar2 != null) {
                mVar2.a();
            }
        }
        return b2;
    }

    public int b() {
        return this.R;
    }

    private boolean b(int i2, boolean z2) {
        int i3 = this.f48i.getApplicationContext().getResources().getConfiguration().uiMode & 48;
        boolean z3 = true;
        int i4 = i2 != 1 ? i2 != 2 ? i3 : 32 : 16;
        boolean E2 = E();
        boolean z4 = false;
        if ((i0 || i4 != i3) && !E2 && Build.VERSION.SDK_INT >= 17 && !this.N && (this.f47h instanceof ContextThemeWrapper)) {
            Configuration configuration = new Configuration();
            configuration.uiMode = (configuration.uiMode & -49) | i4;
            try {
                ((ContextThemeWrapper) this.f47h).applyOverrideConfiguration(configuration);
                z4 = true;
            } catch (IllegalStateException e2) {
                Log.e("AppCompatDelegate", "updateForNightMode. Calling applyOverrideConfiguration() failed with an exception. Will fall back to using Resources.updateConfiguration()", e2);
            }
        }
        int i5 = this.f48i.getResources().getConfiguration().uiMode & 48;
        if (!z4 && i5 != i4 && z2 && !E2 && this.N && (Build.VERSION.SDK_INT >= 17 || this.O)) {
            Object obj = this.f47h;
            if (obj instanceof Activity) {
                androidx.core.app.a.b((Activity) obj);
                z4 = true;
            }
        }
        if (z4 || i5 == i4) {
            z3 = z4;
        } else {
            c(i4, E2);
        }
        if (z3) {
            Object obj2 = this.f47h;
            if (obj2 instanceof AppCompatActivity) {
                ((AppCompatActivity) obj2).c(i2);
            }
        }
        return z3;
    }

    private boolean a(PanelFeatureState panelFeatureState) {
        View view = panelFeatureState.f53i;
        if (view != null) {
            panelFeatureState.f52h = view;
            return true;
        } else if (panelFeatureState.f54j == null) {
            return false;
        } else {
            if (this.r == null) {
                this.r = new p();
            }
            View view2 = (View) panelFeatureState.a((m.a) this.r);
            panelFeatureState.f52h = view2;
            if (view2 != null) {
                return true;
            }
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void a(PanelFeatureState panelFeatureState, boolean z2) {
        ViewGroup viewGroup;
        androidx.appcompat.widget.o oVar;
        if (!z2 || panelFeatureState.a != 0 || (oVar = this.p) == null || !oVar.b()) {
            WindowManager windowManager = (WindowManager) this.f48i.getSystemService("window");
            if (!(windowManager == null || !panelFeatureState.o || (viewGroup = panelFeatureState.f51g) == null)) {
                windowManager.removeView(viewGroup);
                if (z2) {
                    a(panelFeatureState.a, panelFeatureState, (Menu) null);
                }
            }
            panelFeatureState.m = false;
            panelFeatureState.n = false;
            panelFeatureState.o = false;
            panelFeatureState.f52h = null;
            panelFeatureState.q = true;
            if (this.L == panelFeatureState) {
                this.L = null;
                return;
            }
            return;
        }
        b(panelFeatureState.f54j);
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, PanelFeatureState panelFeatureState, Menu menu) {
        if (menu == null) {
            if (panelFeatureState == null && i2 >= 0) {
                PanelFeatureState[] panelFeatureStateArr = this.K;
                if (i2 < panelFeatureStateArr.length) {
                    panelFeatureState = panelFeatureStateArr[i2];
                }
            }
            if (panelFeatureState != null) {
                menu = panelFeatureState.f54j;
            }
        }
        if ((panelFeatureState == null || panelFeatureState.o) && !this.Q) {
            this.k.a().onPanelClosed(i2, menu);
        }
    }

    /* access modifiers changed from: package-private */
    public PanelFeatureState a(Menu menu) {
        PanelFeatureState[] panelFeatureStateArr = this.K;
        int length = panelFeatureStateArr != null ? panelFeatureStateArr.length : 0;
        for (int i2 = 0; i2 < length; i2++) {
            PanelFeatureState panelFeatureState = panelFeatureStateArr[i2];
            if (panelFeatureState != null && panelFeatureState.f54j == menu) {
                return panelFeatureState;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public PanelFeatureState a(int i2, boolean z2) {
        PanelFeatureState[] panelFeatureStateArr = this.K;
        if (panelFeatureStateArr == null || panelFeatureStateArr.length <= i2) {
            PanelFeatureState[] panelFeatureStateArr2 = new PanelFeatureState[(i2 + 1)];
            if (panelFeatureStateArr != null) {
                System.arraycopy(panelFeatureStateArr, 0, panelFeatureStateArr2, 0, panelFeatureStateArr.length);
            }
            this.K = panelFeatureStateArr2;
            panelFeatureStateArr = panelFeatureStateArr2;
        }
        PanelFeatureState panelFeatureState = panelFeatureStateArr[i2];
        if (panelFeatureState != null) {
            return panelFeatureState;
        }
        PanelFeatureState panelFeatureState2 = new PanelFeatureState(i2);
        panelFeatureStateArr[i2] = panelFeatureState2;
        return panelFeatureState2;
    }

    private boolean a(PanelFeatureState panelFeatureState, int i2, KeyEvent keyEvent, int i3) {
        androidx.appcompat.view.menu.g gVar;
        boolean z2 = false;
        if (keyEvent.isSystem()) {
            return false;
        }
        if ((panelFeatureState.m || b(panelFeatureState, keyEvent)) && (gVar = panelFeatureState.f54j) != null) {
            z2 = gVar.performShortcut(i2, keyEvent, i3);
        }
        if (z2 && (i3 & 1) == 0 && this.p == null) {
            a(panelFeatureState, true);
        }
        return z2;
    }

    public final b a() {
        return new h();
    }
}
