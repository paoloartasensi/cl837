package com.android.chileaf.bluetooth.scanner;

import android.content.BroadcastReceiver;

public class PendingIntentReceiver extends BroadcastReceiver {
    /* JADX WARNING: Can't wrap try/catch for region: R(3:32|33|34) */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0098, code lost:
        r7.o.a(r0);
        r0 = r1.getParcelableArrayListExtra("android.bluetooth.le.extra.LIST_SCAN_RESULT");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00a4, code lost:
        if (r0 == null) goto L_0x00ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00a6, code lost:
        r0 = r11.a((java.util.List<android.bluetooth.le.ScanResult>) r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00b2, code lost:
        if (r9.k() <= 0) goto L_0x00b8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00b4, code lost:
        r7.a((java.util.List<com.android.chileaf.bluetooth.scanner.ScanResult>) r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00bc, code lost:
        if (r0.isEmpty() != false) goto L_0x00d9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00be, code lost:
        r7.a(r1.getIntExtra("android.bluetooth.le.extra.CALLBACK_TYPE", r12), r0.get(0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00ce, code lost:
        r0 = r1.getIntExtra("android.bluetooth.le.extra.ERROR_CODE", 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00d4, code lost:
        if (r0 == 0) goto L_0x00d9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00d6, code lost:
        r7.a(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00d9, code lost:
        r7.o.a((android.content.Context) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00df, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00e3, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:32:0x00e2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onReceive(android.content.Context r21, android.content.Intent r22) {
        /*
            r20 = this;
            r0 = r21
            r1 = r22
            if (r0 == 0) goto L_0x00e6
            if (r1 != 0) goto L_0x000a
            goto L_0x00e6
        L_0x000a:
            java.lang.String r2 = "com.android.chileaf.bluetooth.EXTRA_PENDING_INTENT"
            android.os.Parcelable r2 = r1.getParcelableExtra(r2)
            android.app.PendingIntent r2 = (android.app.PendingIntent) r2
            if (r2 != 0) goto L_0x0015
            return
        L_0x0015:
            java.lang.String r3 = "com.android.chileaf.bluetooth.EXTRA_FILTERS"
            java.util.ArrayList r3 = r1.getParcelableArrayListExtra(r3)
            java.lang.String r4 = "com.android.chileaf.bluetooth.EXTRA_SETTINGS"
            android.os.Parcelable r4 = r1.getParcelableExtra(r4)
            r6 = r4
            android.bluetooth.le.ScanSettings r6 = (android.bluetooth.le.ScanSettings) r6
            if (r3 == 0) goto L_0x00e6
            if (r6 != 0) goto L_0x002a
            goto L_0x00e6
        L_0x002a:
            r4 = 1
            java.lang.String r5 = "com.android.chileaf.bluetooth.EXTRA_USE_HARDWARE_BATCHING"
            boolean r7 = r1.getBooleanExtra(r5, r4)
            java.lang.String r5 = "com.android.chileaf.bluetooth.EXTRA_USE_HARDWARE_FILTERING"
            boolean r8 = r1.getBooleanExtra(r5, r4)
            java.lang.String r5 = "com.android.chileaf.bluetooth.EXTRA_USE_HARDWARE_CALLBACK_TYPES"
            boolean r9 = r1.getBooleanExtra(r5, r4)
            r10 = 10000(0x2710, double:4.9407E-320)
            java.lang.String r5 = "com.android.chileaf.bluetooth.EXTRA_MATCH_LOST_TIMEOUT"
            long r12 = r1.getLongExtra(r5, r10)
            java.lang.String r5 = "com.android.chileaf.bluetooth.EXTRA_MATCH_LOST_INTERVAL"
            long r14 = r1.getLongExtra(r5, r10)
            java.lang.String r5 = "com.android.chileaf.bluetooth.EXTRA_MATCH_MODE"
            int r16 = r1.getIntExtra(r5, r4)
            r5 = 3
            java.lang.String r10 = "com.android.chileaf.bluetooth.EXTRA_NUM_OF_MATCHES"
            int r17 = r1.getIntExtra(r10, r5)
            com.android.chileaf.bluetooth.scanner.a r18 = com.android.chileaf.bluetooth.scanner.a.a()
            r10 = r18
            com.android.chileaf.bluetooth.scanner.e r10 = (com.android.chileaf.bluetooth.scanner.e) r10
            java.util.ArrayList r19 = r10.c(r3)
            r5 = r10
            r3 = r10
            r10 = r12
            r12 = r14
            r14 = r16
            r15 = r17
            com.android.chileaf.bluetooth.scanner.ScanSettings r9 = r5.a(r6, r7, r8, r9, r10, r12, r14, r15)
            android.bluetooth.BluetoothAdapter r5 = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
            boolean r6 = r5.isOffloadedScanBatchingSupported()
            boolean r5 = r5.isOffloadedFilteringSupported()
            monitor-enter(r18)
            com.android.chileaf.bluetooth.scanner.e$a r7 = r3.a((android.app.PendingIntent) r2)     // Catch:{ IllegalStateException -> 0x00e2 }
            if (r7 != 0) goto L_0x0095
            com.android.chileaf.bluetooth.scanner.e$a r10 = new com.android.chileaf.bluetooth.scanner.e$a     // Catch:{ all -> 0x00e0 }
            r11 = r3
            r3 = r10
            r12 = 1
            r4 = r6
            r6 = r19
            r7 = r9
            r8 = r2
            r3.<init>(r4, r5, r6, r7, r8)     // Catch:{ all -> 0x00e0 }
            r11.a(r2, r10)     // Catch:{ all -> 0x00e0 }
            r7 = r10
            goto L_0x0097
        L_0x0095:
            r11 = r3
            r12 = 1
        L_0x0097:
            monitor-exit(r18)     // Catch:{ all -> 0x00e0 }
            com.android.chileaf.bluetooth.scanner.i r2 = r7.o
            r2.a((android.content.Context) r0)
            java.lang.String r0 = "android.bluetooth.le.extra.LIST_SCAN_RESULT"
            java.util.ArrayList r0 = r1.getParcelableArrayListExtra(r0)
            r2 = 0
            if (r0 == 0) goto L_0x00ce
            java.util.ArrayList r0 = r11.a((java.util.List<android.bluetooth.le.ScanResult>) r0)
            long r3 = r9.k()
            r5 = 0
            int r8 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r8 <= 0) goto L_0x00b8
            r7.a((java.util.List<com.android.chileaf.bluetooth.scanner.ScanResult>) r0)
            goto L_0x00d9
        L_0x00b8:
            boolean r3 = r0.isEmpty()
            if (r3 != 0) goto L_0x00d9
            java.lang.String r3 = "android.bluetooth.le.extra.CALLBACK_TYPE"
            int r1 = r1.getIntExtra(r3, r12)
            java.lang.Object r0 = r0.get(r2)
            com.android.chileaf.bluetooth.scanner.ScanResult r0 = (com.android.chileaf.bluetooth.scanner.ScanResult) r0
            r7.a(r1, r0)
            goto L_0x00d9
        L_0x00ce:
            java.lang.String r0 = "android.bluetooth.le.extra.ERROR_CODE"
            int r0 = r1.getIntExtra(r0, r2)
            if (r0 == 0) goto L_0x00d9
            r7.a((int) r0)
        L_0x00d9:
            com.android.chileaf.bluetooth.scanner.i r0 = r7.o
            r1 = 0
            r0.a((android.content.Context) r1)
            return
        L_0x00e0:
            r0 = move-exception
            goto L_0x00e4
        L_0x00e2:
            monitor-exit(r18)     // Catch:{ all -> 0x00e0 }
            return
        L_0x00e4:
            monitor-exit(r18)     // Catch:{ all -> 0x00e0 }
            throw r0
        L_0x00e6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.chileaf.bluetooth.scanner.PendingIntentReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }
}
