package com.android.chileaf.bluetooth.scanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import com.android.chileaf.bluetooth.scanner.a;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: BluetoothLeScannerImplJB */
class b extends a {
    /* access modifiers changed from: private */
    public final Map<j, a.C0054a> b = new HashMap();
    private HandlerThread c;
    /* access modifiers changed from: private */
    public Handler d;
    /* access modifiers changed from: private */
    public long e;
    /* access modifiers changed from: private */

    /* renamed from: f  reason: collision with root package name */
    public long f1125f;
    /* access modifiers changed from: private */

    /* renamed from: g  reason: collision with root package name */
    public final Runnable f1126g = new a();
    /* access modifiers changed from: private */

    /* renamed from: h  reason: collision with root package name */
    public final Runnable f1127h = new C0057b();
    /* access modifiers changed from: private */

    /* renamed from: i  reason: collision with root package name */
    public final BluetoothAdapter.LeScanCallback f1128i = new c();

    /* compiled from: BluetoothLeScannerImplJB */
    class a implements Runnable {
        a() {
        }

        public void run() {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null && b.this.e > 0 && b.this.f1125f > 0) {
                defaultAdapter.stopLeScan(b.this.f1128i);
                b.this.d.postDelayed(b.this.f1127h, b.this.e);
            }
        }
    }

    /* renamed from: com.android.chileaf.bluetooth.scanner.b$b  reason: collision with other inner class name */
    /* compiled from: BluetoothLeScannerImplJB */
    class C0057b implements Runnable {
        C0057b() {
        }

        public void run() {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null && b.this.e > 0 && b.this.f1125f > 0) {
                defaultAdapter.startLeScan(b.this.f1128i);
                b.this.d.postDelayed(b.this.f1126g, b.this.f1125f);
            }
        }
    }

    /* compiled from: BluetoothLeScannerImplJB */
    class c implements BluetoothAdapter.LeScanCallback {

        /* compiled from: BluetoothLeScannerImplJB */
        class a implements Runnable {
            final /* synthetic */ a.C0054a e;

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ ScanResult f1129f;

            a(c cVar, a.C0054a aVar, ScanResult scanResult) {
                this.e = aVar;
                this.f1129f = scanResult;
            }

            public void run() {
                this.e.a(1, this.f1129f);
            }
        }

        c() {
        }

        public void onLeScan(BluetoothDevice bluetoothDevice, int i2, byte[] bArr) {
            ScanResult scanResult = new ScanResult(bluetoothDevice, k.a(bArr), i2, SystemClock.elapsedRealtimeNanos());
            synchronized (b.this.b) {
                for (a.C0054a aVar : b.this.b.values()) {
                    aVar.f1122i.post(new a(this, aVar, scanResult));
                }
            }
        }
    }

    b() {
    }

    /* access modifiers changed from: package-private */
    public void a(List<ScanFilter> list, ScanSettings scanSettings, j jVar, Handler handler) {
        boolean isEmpty;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        synchronized (this.b) {
            if (!this.b.containsKey(jVar)) {
                a.C0054a aVar = new a.C0054a(false, false, list, scanSettings, jVar, handler);
                isEmpty = this.b.isEmpty();
                this.b.put(jVar, aVar);
            } else {
                throw new IllegalArgumentException("scanner already started with given scanCallback");
            }
        }
        if (this.c == null) {
            HandlerThread handlerThread = new HandlerThread(b.class.getName());
            this.c = handlerThread;
            handlerThread.start();
            this.d = new Handler(this.c.getLooper());
        }
        b();
        if (isEmpty) {
            defaultAdapter.startLeScan(this.f1128i);
        }
    }

    /* access modifiers changed from: package-private */
    public void b(j jVar) {
        a.C0054a remove;
        boolean isEmpty;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        synchronized (this.b) {
            remove = this.b.remove(jVar);
            isEmpty = this.b.isEmpty();
        }
        if (remove != null) {
            remove.a();
            b();
            if (isEmpty) {
                defaultAdapter.stopLeScan(this.f1128i);
                Handler handler = this.d;
                if (handler != null) {
                    handler.removeCallbacksAndMessages((Object) null);
                }
                HandlerThread handlerThread = this.c;
                if (handlerThread != null) {
                    handlerThread.quitSafely();
                    this.c = null;
                }
            }
        }
    }

    private void b() {
        long j2;
        long j3;
        synchronized (this.b) {
            j2 = Long.MAX_VALUE;
            j3 = Long.MAX_VALUE;
            for (a.C0054a aVar : this.b.values()) {
                ScanSettings scanSettings = aVar.f1120g;
                if (scanSettings.p()) {
                    if (j2 > scanSettings.i()) {
                        j2 = scanSettings.i();
                    }
                    if (j3 > scanSettings.j()) {
                        j3 = scanSettings.j();
                    }
                }
            }
        }
        if (j2 >= Long.MAX_VALUE || j3 >= Long.MAX_VALUE) {
            this.f1125f = 0;
            this.e = 0;
            Handler handler = this.d;
            if (handler != null) {
                handler.removeCallbacks(this.f1127h);
                this.d.removeCallbacks(this.f1126g);
                return;
            }
            return;
        }
        this.e = j2;
        this.f1125f = j3;
        Handler handler2 = this.d;
        if (handler2 != null) {
            handler2.removeCallbacks(this.f1127h);
            this.d.removeCallbacks(this.f1126g);
            this.d.postDelayed(this.f1126g, this.f1125f);
        }
    }
}
