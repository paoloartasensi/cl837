package com.android.chileaf.bluetooth.scanner;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Handler;
import android.os.SystemClock;
import com.android.chileaf.bluetooth.scanner.a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TargetApi(21)
/* compiled from: BluetoothLeScannerImplLollipop */
class c extends a {
    private final Map<j, b> b = new HashMap();

    /* compiled from: BluetoothLeScannerImplLollipop */
    static class b extends a.C0054a {
        /* access modifiers changed from: private */
        public final ScanCallback o;

        /* compiled from: BluetoothLeScannerImplLollipop */
        class a extends ScanCallback {
            /* access modifiers changed from: private */
            public long a;

            /* renamed from: com.android.chileaf.bluetooth.scanner.c$b$a$a  reason: collision with other inner class name */
            /* compiled from: BluetoothLeScannerImplLollipop */
            class C0058a implements Runnable {
                final /* synthetic */ ScanResult e;

                /* renamed from: f  reason: collision with root package name */
                final /* synthetic */ int f1130f;

                C0058a(ScanResult scanResult, int i2) {
                    this.e = scanResult;
                    this.f1130f = i2;
                }

                public void run() {
                    b.this.a(this.f1130f, ((c) a.a()).a(this.e));
                }
            }

            /* renamed from: com.android.chileaf.bluetooth.scanner.c$b$a$b  reason: collision with other inner class name */
            /* compiled from: BluetoothLeScannerImplLollipop */
            class C0059b implements Runnable {
                final /* synthetic */ List e;

                C0059b(List list) {
                    this.e = list;
                }

                public void run() {
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    if (a.this.a <= (elapsedRealtime - b.this.f1120g.k()) + 5) {
                        long unused = a.this.a = elapsedRealtime;
                        b.this.a((List<ScanResult>) ((c) a.a()).a((List<ScanResult>) this.e));
                    }
                }
            }

            /* renamed from: com.android.chileaf.bluetooth.scanner.c$b$a$c  reason: collision with other inner class name */
            /* compiled from: BluetoothLeScannerImplLollipop */
            class C0060c implements Runnable {
                final /* synthetic */ int e;

                C0060c(int i2) {
                    this.e = i2;
                }

                /* JADX WARNING: Failed to process nested try/catch */
                /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002f */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                        r5 = this;
                        com.android.chileaf.bluetooth.scanner.c$b$a r0 = com.android.chileaf.bluetooth.scanner.c.b.a.this
                        com.android.chileaf.bluetooth.scanner.c$b r0 = com.android.chileaf.bluetooth.scanner.c.b.this
                        com.android.chileaf.bluetooth.scanner.ScanSettings r0 = r0.f1120g
                        boolean r0 = r0.n()
                        if (r0 == 0) goto L_0x004b
                        com.android.chileaf.bluetooth.scanner.c$b$a r0 = com.android.chileaf.bluetooth.scanner.c.b.a.this
                        com.android.chileaf.bluetooth.scanner.c$b r0 = com.android.chileaf.bluetooth.scanner.c.b.this
                        com.android.chileaf.bluetooth.scanner.ScanSettings r0 = r0.f1120g
                        int r0 = r0.b()
                        r1 = 1
                        if (r0 == r1) goto L_0x004b
                        com.android.chileaf.bluetooth.scanner.c$b$a r0 = com.android.chileaf.bluetooth.scanner.c.b.a.this
                        com.android.chileaf.bluetooth.scanner.c$b r0 = com.android.chileaf.bluetooth.scanner.c.b.this
                        com.android.chileaf.bluetooth.scanner.ScanSettings r0 = r0.f1120g
                        r0.a()
                        com.android.chileaf.bluetooth.scanner.a r0 = com.android.chileaf.bluetooth.scanner.a.a()
                        com.android.chileaf.bluetooth.scanner.c$b$a r1 = com.android.chileaf.bluetooth.scanner.c.b.a.this     // Catch:{ Exception -> 0x002f }
                        com.android.chileaf.bluetooth.scanner.c$b r1 = com.android.chileaf.bluetooth.scanner.c.b.this     // Catch:{ Exception -> 0x002f }
                        com.android.chileaf.bluetooth.scanner.j r1 = r1.f1121h     // Catch:{ Exception -> 0x002f }
                        r0.a(r1)     // Catch:{ Exception -> 0x002f }
                    L_0x002f:
                        com.android.chileaf.bluetooth.scanner.c$b$a r1 = com.android.chileaf.bluetooth.scanner.c.b.a.this     // Catch:{ Exception -> 0x004a }
                        com.android.chileaf.bluetooth.scanner.c$b r1 = com.android.chileaf.bluetooth.scanner.c.b.this     // Catch:{ Exception -> 0x004a }
                        java.util.List<com.android.chileaf.bluetooth.scanner.ScanFilter> r1 = r1.f1119f     // Catch:{ Exception -> 0x004a }
                        com.android.chileaf.bluetooth.scanner.c$b$a r2 = com.android.chileaf.bluetooth.scanner.c.b.a.this     // Catch:{ Exception -> 0x004a }
                        com.android.chileaf.bluetooth.scanner.c$b r2 = com.android.chileaf.bluetooth.scanner.c.b.this     // Catch:{ Exception -> 0x004a }
                        com.android.chileaf.bluetooth.scanner.ScanSettings r2 = r2.f1120g     // Catch:{ Exception -> 0x004a }
                        com.android.chileaf.bluetooth.scanner.c$b$a r3 = com.android.chileaf.bluetooth.scanner.c.b.a.this     // Catch:{ Exception -> 0x004a }
                        com.android.chileaf.bluetooth.scanner.c$b r3 = com.android.chileaf.bluetooth.scanner.c.b.this     // Catch:{ Exception -> 0x004a }
                        com.android.chileaf.bluetooth.scanner.j r3 = r3.f1121h     // Catch:{ Exception -> 0x004a }
                        com.android.chileaf.bluetooth.scanner.c$b$a r4 = com.android.chileaf.bluetooth.scanner.c.b.a.this     // Catch:{ Exception -> 0x004a }
                        com.android.chileaf.bluetooth.scanner.c$b r4 = com.android.chileaf.bluetooth.scanner.c.b.this     // Catch:{ Exception -> 0x004a }
                        android.os.Handler r4 = r4.f1122i     // Catch:{ Exception -> 0x004a }
                        r0.a(r1, r2, r3, r4)     // Catch:{ Exception -> 0x004a }
                    L_0x004a:
                        return
                    L_0x004b:
                        com.android.chileaf.bluetooth.scanner.c$b$a r0 = com.android.chileaf.bluetooth.scanner.c.b.a.this
                        com.android.chileaf.bluetooth.scanner.c$b r0 = com.android.chileaf.bluetooth.scanner.c.b.this
                        int r1 = r5.e
                        r0.a((int) r1)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.chileaf.bluetooth.scanner.c.b.a.C0060c.run():void");
                }
            }

            a() {
            }

            public void onBatchScanResults(List<ScanResult> list) {
                b.this.f1122i.post(new C0059b(list));
            }

            public void onScanFailed(int i2) {
                b.this.f1122i.post(new C0060c(i2));
            }

            public void onScanResult(int i2, ScanResult scanResult) {
                b.this.f1122i.post(new C0058a(scanResult, i2));
            }
        }

        private b(boolean z, boolean z2, List<ScanFilter> list, ScanSettings scanSettings, j jVar, Handler handler) {
            super(z, z2, list, scanSettings, jVar, handler);
            this.o = new a();
        }
    }

    c() {
    }

    /* access modifiers changed from: package-private */
    public void a(List<ScanFilter> list, ScanSettings scanSettings, j jVar, Handler handler) {
        b bVar;
        j jVar2 = jVar;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothLeScanner bluetoothLeScanner = defaultAdapter.getBluetoothLeScanner();
        if (bluetoothLeScanner != null) {
            boolean isOffloadedScanBatchingSupported = defaultAdapter.isOffloadedScanBatchingSupported();
            boolean isOffloadedFilteringSupported = defaultAdapter.isOffloadedFilteringSupported();
            synchronized (this.b) {
                if (!this.b.containsKey(jVar2)) {
                    bVar = new b(isOffloadedScanBatchingSupported, isOffloadedFilteringSupported, list, scanSettings, jVar, handler);
                    this.b.put(jVar2, bVar);
                } else {
                    throw new IllegalArgumentException("scanner already started with given callback");
                }
            }
            ScanSettings a2 = a(defaultAdapter, scanSettings, false);
            ArrayList<ScanFilter> arrayList = null;
            if (!list.isEmpty() && isOffloadedFilteringSupported && scanSettings.o()) {
                arrayList = b(list);
            }
            bluetoothLeScanner.startScan(arrayList, a2, bVar.o);
            return;
        }
        throw new IllegalStateException("BT le scanner not available");
    }

    /* access modifiers changed from: package-private */
    public void b(j jVar) {
        b remove;
        BluetoothLeScanner bluetoothLeScanner;
        synchronized (this.b) {
            remove = this.b.remove(jVar);
        }
        if (remove != null) {
            remove.a();
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null && (bluetoothLeScanner = defaultAdapter.getBluetoothLeScanner()) != null) {
                bluetoothLeScanner.stopScan(remove.o);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public ArrayList<ScanFilter> b(List<ScanFilter> list) {
        ArrayList<ScanFilter> arrayList = new ArrayList<>();
        for (ScanFilter a2 : list) {
            arrayList.add(a(a2));
        }
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    public ScanSettings a(BluetoothAdapter bluetoothAdapter, ScanSettings scanSettings, boolean z) {
        ScanSettings.Builder builder = new ScanSettings.Builder();
        if (z || (bluetoothAdapter.isOffloadedScanBatchingSupported() && scanSettings.m())) {
            builder.setReportDelay(scanSettings.k());
        }
        if (scanSettings.l() != -1) {
            builder.setScanMode(scanSettings.l());
        } else {
            builder.setScanMode(0);
        }
        scanSettings.a();
        return builder.build();
    }

    /* access modifiers changed from: package-private */
    public ScanFilter a(ScanFilter scanFilter) {
        ScanFilter.Builder builder = new ScanFilter.Builder();
        builder.setDeviceAddress(scanFilter.a()).setDeviceName(scanFilter.b()).setServiceUuid(scanFilter.i(), scanFilter.j()).setManufacturerData(scanFilter.e(), scanFilter.c(), scanFilter.d());
        if (scanFilter.h() != null) {
            builder.setServiceData(scanFilter.h(), scanFilter.f(), scanFilter.g());
        }
        return builder.build();
    }

    /* access modifiers changed from: package-private */
    public ScanResult a(ScanResult scanResult) {
        return new ScanResult(scanResult.getDevice(), k.a(scanResult.getScanRecord() != null ? scanResult.getScanRecord().getBytes() : null), scanResult.getRssi(), scanResult.getTimestampNanos());
    }

    /* access modifiers changed from: package-private */
    public ArrayList<ScanResult> a(List<ScanResult> list) {
        ArrayList<ScanResult> arrayList = new ArrayList<>();
        for (ScanResult a2 : list) {
            arrayList.add(a(a2));
        }
        return arrayList;
    }
}
