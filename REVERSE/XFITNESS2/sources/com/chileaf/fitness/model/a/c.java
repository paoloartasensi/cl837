package com.chileaf.fitness.model.a;

import android.os.ParcelUuid;
import androidx.lifecycle.LiveData;
import com.android.chileaf.bluetooth.scanner.ScanResult;
import com.chileaf.fitness.model.DiscoveredDevice;
import j.a.a;
import java.util.ArrayList;
import java.util.List;

/* compiled from: DevicesLiveData */
public class c extends LiveData<List<DiscoveredDevice>> {
    private final List<DiscoveredDevice> a = new ArrayList();
    private final List<ParcelUuid> b = new ArrayList();
    private final List<String> c = new ArrayList();
    private int d = -100;
    private boolean e = true;

    /* renamed from: f  reason: collision with root package name */
    private boolean f1225f;

    /* renamed from: g  reason: collision with root package name */
    private boolean f1226g;

    public c(boolean z, boolean z2) {
        this.f1226g = z;
        this.f1225f = z2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0056, code lost:
        return false;
     */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x003f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean c(com.android.chileaf.bluetooth.scanner.ScanResult r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            boolean r0 = r5.f1225f     // Catch:{ all -> 0x0057 }
            r1 = 1
            if (r0 != 0) goto L_0x0008
            monitor-exit(r5)
            return r1
        L_0x0008:
            android.bluetooth.BluetoothDevice r0 = r6.a()     // Catch:{ all -> 0x0057 }
            com.android.chileaf.bluetooth.scanner.k r6 = r6.c()     // Catch:{ all -> 0x0057 }
            r2 = 0
            r3 = 0
            if (r6 == 0) goto L_0x0020
            java.lang.String r4 = r6.b()     // Catch:{ all -> 0x0057 }
            if (r4 == 0) goto L_0x0020
            java.lang.String r2 = r6.b()     // Catch:{ all -> 0x0057 }
            r6 = 1
            goto L_0x002e
        L_0x0020:
            java.lang.String r6 = r0.getName()     // Catch:{ all -> 0x0057 }
            if (r6 == 0) goto L_0x002d
            java.lang.String r2 = r0.getName()     // Catch:{ all -> 0x0057 }
            r6 = 0
            r0 = 1
            goto L_0x002f
        L_0x002d:
            r6 = 0
        L_0x002e:
            r0 = 0
        L_0x002f:
            if (r6 != 0) goto L_0x0033
            if (r0 == 0) goto L_0x0055
        L_0x0033:
            java.util.List<java.lang.String> r6 = r5.c     // Catch:{ all -> 0x0057 }
            java.util.Iterator r6 = r6.iterator()     // Catch:{ all -> 0x0057 }
        L_0x0039:
            boolean r0 = r6.hasNext()     // Catch:{ all -> 0x0057 }
            if (r0 == 0) goto L_0x0055
            java.lang.Object r0 = r6.next()     // Catch:{ all -> 0x0057 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x0057 }
            java.lang.String r4 = r2.toUpperCase()     // Catch:{ all -> 0x0057 }
            java.lang.String r0 = r0.toUpperCase()     // Catch:{ all -> 0x0057 }
            boolean r0 = r4.startsWith(r0)     // Catch:{ all -> 0x0057 }
            if (r0 == 0) goto L_0x0039
            monitor-exit(r5)
            return r1
        L_0x0055:
            monitor-exit(r5)
            return r3
        L_0x0057:
            r6 = move-exception
            monitor-exit(r5)
            goto L_0x005b
        L_0x005a:
            throw r6
        L_0x005b:
            goto L_0x005a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.chileaf.fitness.model.a.c.c(com.android.chileaf.bluetooth.scanner.ScanResult):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0045, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean d(com.android.chileaf.bluetooth.scanner.ScanResult r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r0 = r4.f1226g     // Catch:{ all -> 0x0046 }
            r1 = 1
            if (r0 != 0) goto L_0x0008
            monitor-exit(r4)
            return r1
        L_0x0008:
            com.android.chileaf.bluetooth.scanner.k r5 = r5.c()     // Catch:{ all -> 0x0046 }
            r0 = 0
            if (r5 == 0) goto L_0x0044
            java.lang.String r2 = r5.b()     // Catch:{ all -> 0x0046 }
            if (r2 == 0) goto L_0x0044
            java.lang.String r2 = r5.b()     // Catch:{ all -> 0x0046 }
            boolean r2 = r2.isEmpty()     // Catch:{ all -> 0x0046 }
            if (r2 == 0) goto L_0x0020
            goto L_0x0044
        L_0x0020:
            java.util.List r5 = r5.c()     // Catch:{ all -> 0x0046 }
            if (r5 != 0) goto L_0x0028
            monitor-exit(r4)
            return r0
        L_0x0028:
            java.util.List<android.os.ParcelUuid> r2 = r4.b     // Catch:{ all -> 0x0046 }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ all -> 0x0046 }
        L_0x002e:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x0046 }
            if (r3 == 0) goto L_0x0042
            java.lang.Object r3 = r2.next()     // Catch:{ all -> 0x0046 }
            android.os.ParcelUuid r3 = (android.os.ParcelUuid) r3     // Catch:{ all -> 0x0046 }
            boolean r3 = r5.contains(r3)     // Catch:{ all -> 0x0046 }
            if (r3 == 0) goto L_0x002e
            monitor-exit(r4)
            return r1
        L_0x0042:
            monitor-exit(r4)
            return r0
        L_0x0044:
            monitor-exit(r4)
            return r0
        L_0x0046:
            r5 = move-exception
            monitor-exit(r4)
            goto L_0x004a
        L_0x0049:
            throw r5
        L_0x004a:
            goto L_0x0049
        */
        throw new UnsupportedOperationException("Method not decompiled: com.chileaf.fitness.model.a.c.d(com.android.chileaf.bluetooth.scanner.ScanResult):boolean");
    }

    public boolean a(int i2) {
        this.d = i2;
        return a();
    }

    public synchronized void b() {
        this.a.clear();
        postValue(this.a);
    }

    public void a(String str) {
        a.b("addFilterName:%s", str);
        if (str == null || str.isEmpty()) {
            this.c.clear();
            this.f1225f = false;
            return;
        }
        this.c.addAll(com.chileaf.fitness.config.c.a(str));
        this.f1225f = true;
    }

    private int b(ScanResult scanResult) {
        int i2 = 0;
        for (DiscoveredDevice matches : this.a) {
            if (matches.matches(scanResult)) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x000f, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean b(int r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.e     // Catch:{ all -> 0x0010 }
            r1 = 1
            if (r0 != 0) goto L_0x0008
            monitor-exit(r2)
            return r1
        L_0x0008:
            int r0 = r2.d     // Catch:{ all -> 0x0010 }
            if (r3 < r0) goto L_0x000d
            goto L_0x000e
        L_0x000d:
            r1 = 0
        L_0x000e:
            monitor-exit(r2)
            return r1
        L_0x0010:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.chileaf.fitness.model.a.c.b(int):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0048, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean a(com.android.chileaf.bluetooth.scanner.ScanResult r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r0 = r4.c(r5)     // Catch:{ all -> 0x004b }
            r1 = 0
            if (r0 == 0) goto L_0x0049
            int r0 = r4.b((com.android.chileaf.bluetooth.scanner.ScanResult) r5)     // Catch:{ all -> 0x004b }
            r2 = -1
            if (r0 != r2) goto L_0x001a
            com.chileaf.fitness.model.DiscoveredDevice r0 = new com.chileaf.fitness.model.DiscoveredDevice     // Catch:{ all -> 0x004b }
            r0.<init>((com.android.chileaf.bluetooth.scanner.ScanResult) r5)     // Catch:{ all -> 0x004b }
            java.util.List<com.chileaf.fitness.model.DiscoveredDevice> r2 = r4.a     // Catch:{ all -> 0x004b }
            r2.add(r0)     // Catch:{ all -> 0x004b }
            goto L_0x0022
        L_0x001a:
            java.util.List<com.chileaf.fitness.model.DiscoveredDevice> r2 = r4.a     // Catch:{ all -> 0x004b }
            java.lang.Object r0 = r2.get(r0)     // Catch:{ all -> 0x004b }
            com.chileaf.fitness.model.DiscoveredDevice r0 = (com.chileaf.fitness.model.DiscoveredDevice) r0     // Catch:{ all -> 0x004b }
        L_0x0022:
            r0.update(r5)     // Catch:{ all -> 0x004b }
            boolean r2 = r4.e     // Catch:{ all -> 0x004b }
            if (r2 == 0) goto L_0x0036
            int r2 = r0.getRssi()     // Catch:{ all -> 0x004b }
            int r3 = r4.d     // Catch:{ all -> 0x004b }
            if (r2 >= r3) goto L_0x0036
            java.util.List<com.chileaf.fitness.model.DiscoveredDevice> r2 = r4.a     // Catch:{ all -> 0x004b }
            r2.remove(r0)     // Catch:{ all -> 0x004b }
        L_0x0036:
            boolean r5 = r4.d(r5)     // Catch:{ all -> 0x004b }
            if (r5 == 0) goto L_0x0047
            int r5 = r0.getHighestRssi()     // Catch:{ all -> 0x004b }
            boolean r5 = r4.b((int) r5)     // Catch:{ all -> 0x004b }
            if (r5 == 0) goto L_0x0047
            r1 = 1
        L_0x0047:
            monitor-exit(r4)
            return r1
        L_0x0049:
            monitor-exit(r4)
            return r1
        L_0x004b:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.chileaf.fitness.model.a.c.a(com.android.chileaf.bluetooth.scanner.ScanResult):boolean");
    }

    public synchronized boolean a() {
        postValue(this.a);
        return !this.a.isEmpty();
    }
}
