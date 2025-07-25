package com.chileaf.fitness;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import androidx.appcompat.app.e;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;
import com.chileaf.fitness.config.ForegroundService;
import com.jeremyliao.liveeventbus.LiveEventBus;
import kotlin.TypeCastException;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.i;
import kotlin.jvm.internal.k;
import kotlin.p.c;
import kotlin.reflect.h;
import org.koin.core.b.b;

/* compiled from: App.kt */
public final class App extends Application implements LifecycleObserver {
    private static boolean e;
    /* access modifiers changed from: private */

    /* renamed from: f  reason: collision with root package name */
    public static final c f1143f = kotlin.p.a.a.a();

    /* renamed from: g  reason: collision with root package name */
    public static final a f1144g = new a((f) null);

    /* compiled from: App.kt */
    public static final class a {
        static final /* synthetic */ h[] a;

        static {
            MutablePropertyReference1Impl mutablePropertyReference1Impl = new MutablePropertyReference1Impl(k.a(a.class), "instance", "getInstance$app_release()Landroid/content/Context;");
            k.a((MutablePropertyReference1) mutablePropertyReference1Impl);
            a = new h[]{mutablePropertyReference1Impl};
        }

        private a() {
        }

        public final Context a() {
            return (Context) App.f1143f.a(App.f1144g, a[0]);
        }

        public final void a(Context context) {
            i.b(context, "<set-?>");
            App.f1143f.a(App.f1144g, a[0], context);
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    private final void b() {
        b.a(new App$initInjector$1(this));
    }

    private final void c(Context context) {
        Intent intent = new Intent(context, ForegroundService.class);
        intent.putExtra("extra_notification", true);
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(com.chileaf.fitness.config.b.b.a(context));
        androidx.multidex.a.d(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public final void onBackground() {
        if (e) {
            c(this);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        i.b(configuration, "newConfig");
        super.onConfigurationChanged(configuration);
        com.chileaf.fitness.config.b.b.b(this);
    }

    public void onCreate() {
        super.onCreate();
        com.chileaf.fitness.config.b.b.b(this);
        b();
        a aVar = f1144g;
        Context applicationContext = getApplicationContext();
        i.a((Object) applicationContext, "applicationContext");
        aVar.a(applicationContext);
        if (Build.VERSION.SDK_INT < 21) {
            e.a(true);
        }
        LiveEventBus.config().supportBroadcast(this).lifecycleObserverAlwaysActive(true);
        a(this);
        LifecycleOwner lifecycleOwner = ProcessLifecycleOwner.get();
        i.a((Object) lifecycleOwner, "ProcessLifecycleOwner.get()");
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public final void onForeground() {
        b(this);
    }

    private final void a(Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("foreground_id", "foreground", 4);
            notificationChannel.setDescription("Foreground Service");
            Object systemService = context.getSystemService("notification");
            if (systemService != null) {
                ((NotificationManager) systemService).createNotificationChannel(notificationChannel);
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type android.app.NotificationManager");
        }
    }

    private final void b(Context context) {
        context.stopService(new Intent(context, ForegroundService.class));
    }
}
