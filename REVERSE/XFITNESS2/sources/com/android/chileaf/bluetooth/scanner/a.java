package com.android.chileaf.bluetooth.scanner;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import com.android.chileaf.bluetooth.scanner.ScanSettings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: BluetoothLeScannerCompat */
public abstract class a {
    private static a a;

    /* renamed from: com.android.chileaf.bluetooth.scanner.a$a  reason: collision with other inner class name */
    /* compiled from: BluetoothLeScannerCompat */
    static class C0054a {
        /* access modifiers changed from: private */
        public final Object a = new Object();
        private final boolean b;
        private final boolean c;
        private final boolean d;
        /* access modifiers changed from: private */
        public boolean e;

        /* renamed from: f  reason: collision with root package name */
        final List<ScanFilter> f1119f;

        /* renamed from: g  reason: collision with root package name */
        final ScanSettings f1120g;

        /* renamed from: h  reason: collision with root package name */
        final j f1121h;

        /* renamed from: i  reason: collision with root package name */
        final Handler f1122i;

        /* renamed from: j  reason: collision with root package name */
        private final List<ScanResult> f1123j = new ArrayList();
        private final Set<String> k = new HashSet();
        /* access modifiers changed from: private */
        public final Map<String, ScanResult> l = new HashMap();
        private final Runnable m = new C0055a();
        private final Runnable n = new b();

        /* renamed from: com.android.chileaf.bluetooth.scanner.a$a$a  reason: collision with other inner class name */
        /* compiled from: BluetoothLeScannerCompat */
        class C0055a implements Runnable {
            C0055a() {
            }

            public void run() {
                if (!C0054a.this.e) {
                    C0054a.this.b();
                    C0054a aVar = C0054a.this;
                    aVar.f1122i.postDelayed(this, aVar.f1120g.k());
                }
            }
        }

        /* renamed from: com.android.chileaf.bluetooth.scanner.a$a$b */
        /* compiled from: BluetoothLeScannerCompat */
        class b implements Runnable {

            /* renamed from: com.android.chileaf.bluetooth.scanner.a$a$b$a  reason: collision with other inner class name */
            /* compiled from: BluetoothLeScannerCompat */
            class C0056a implements Runnable {
                final /* synthetic */ ScanResult e;

                C0056a(ScanResult scanResult) {
                    this.e = scanResult;
                }

                public void run() {
                    C0054a.this.f1121h.a(4, this.e);
                }
            }

            b() {
            }

            public void run() {
                long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
                synchronized (C0054a.this.a) {
                    Iterator it = C0054a.this.l.values().iterator();
                    while (it.hasNext()) {
                        ScanResult scanResult = (ScanResult) it.next();
                        if (scanResult.d() < elapsedRealtimeNanos - C0054a.this.f1120g.d()) {
                            it.remove();
                            C0054a.this.f1122i.post(new C0056a(scanResult));
                        }
                    }
                    if (!C0054a.this.l.isEmpty()) {
                        C0054a.this.f1122i.postDelayed(this, C0054a.this.f1120g.e());
                    }
                }
            }
        }

        C0054a(boolean z, boolean z2, List<ScanFilter> list, ScanSettings scanSettings, j jVar, Handler handler) {
            this.f1119f = Collections.unmodifiableList(list);
            this.f1120g = scanSettings;
            this.f1121h = jVar;
            this.f1122i = handler;
            boolean z3 = false;
            this.e = false;
            this.d = scanSettings.b() != 1 && (!(Build.VERSION.SDK_INT >= 23) || !scanSettings.n());
            this.b = !list.isEmpty() && (!z2 || !scanSettings.o());
            long k2 = scanSettings.k();
            if (k2 > 0 && (!z || !scanSettings.m())) {
                z3 = true;
            }
            this.c = z3;
            if (z3) {
                handler.postDelayed(this.m, k2);
            }
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.e = true;
            this.f1122i.removeCallbacksAndMessages((Object) null);
            synchronized (this.a) {
                this.l.clear();
                this.k.clear();
                this.f1123j.clear();
            }
        }

        /* access modifiers changed from: package-private */
        public void b() {
            if (this.c && !this.e) {
                synchronized (this.a) {
                    this.f1121h.a((List<ScanResult>) new ArrayList(this.f1123j));
                    this.f1123j.clear();
                    this.k.clear();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a(int i2, ScanResult scanResult) {
            boolean isEmpty;
            ScanResult put;
            if (this.e) {
                return;
            }
            if (this.f1119f.isEmpty() || a(scanResult)) {
                String address = scanResult.a().getAddress();
                if (this.d) {
                    synchronized (this.l) {
                        isEmpty = this.l.isEmpty();
                        put = this.l.put(address, scanResult);
                    }
                    if (put == null && (this.f1120g.b() & 2) > 0) {
                        this.f1121h.a(2, scanResult);
                    }
                    if (isEmpty && (this.f1120g.b() & 4) > 0) {
                        this.f1122i.removeCallbacks(this.n);
                        this.f1122i.postDelayed(this.n, this.f1120g.e());
                    }
                } else if (this.c) {
                    synchronized (this.a) {
                        if (!this.k.contains(address)) {
                            this.f1123j.add(scanResult);
                            this.k.add(address);
                        }
                    }
                } else {
                    this.f1121h.a(i2, scanResult);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a(List<ScanResult> list) {
            if (!this.e) {
                if (this.b) {
                    ArrayList arrayList = new ArrayList();
                    for (ScanResult next : list) {
                        if (a(next)) {
                            arrayList.add(next);
                        }
                    }
                    list = arrayList;
                }
                this.f1121h.a(list);
            }
        }

        /* access modifiers changed from: package-private */
        public void a(int i2) {
            this.f1121h.a(i2);
        }

        private boolean a(ScanResult scanResult) {
            for (ScanFilter a2 : this.f1119f) {
                if (a2.a(scanResult)) {
                    return true;
                }
            }
            return false;
        }
    }

    a() {
    }

    public static synchronized a a() {
        synchronized (a.class) {
            if (a != null) {
                a aVar = a;
                return aVar;
            } else if (Build.VERSION.SDK_INT >= 26) {
                e eVar = new e();
                a = eVar;
                return eVar;
            } else if (Build.VERSION.SDK_INT >= 23) {
                d dVar = new d();
                a = dVar;
                return dVar;
            } else if (Build.VERSION.SDK_INT >= 21) {
                c cVar = new c();
                a = cVar;
                return cVar;
            } else {
                b bVar = new b();
                a = bVar;
                return bVar;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public abstract void a(List<ScanFilter> list, ScanSettings scanSettings, j jVar, Handler handler);

    /* access modifiers changed from: package-private */
    public abstract void b(j jVar);

    public final void a(List<ScanFilter> list, ScanSettings scanSettings, j jVar) {
        if (jVar != null) {
            Handler handler = new Handler(Looper.getMainLooper());
            if (list == null) {
                list = Collections.emptyList();
            }
            if (scanSettings == null) {
                scanSettings = new ScanSettings.b().a();
            }
            a(list, scanSettings, jVar, handler);
            return;
        }
        throw new IllegalArgumentException("callback is null");
    }

    public final void a(j jVar) {
        if (jVar != null) {
            b(jVar);
            return;
        }
        throw new IllegalArgumentException("callback is null");
    }
}
