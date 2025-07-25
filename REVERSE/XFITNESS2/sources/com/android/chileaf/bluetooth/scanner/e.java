package com.android.chileaf.bluetooth.scanner;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Handler;
import com.android.chileaf.bluetooth.scanner.ScanFilter;
import com.android.chileaf.bluetooth.scanner.ScanSettings;
import com.android.chileaf.bluetooth.scanner.a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@TargetApi(26)
/* compiled from: BluetoothLeScannerImplOreo */
class e extends d {
    private final HashMap<PendingIntent, a> c = new HashMap<>();

    /* compiled from: BluetoothLeScannerImplOreo */
    static class a extends a.C0054a {
        final i o = ((i) this.f1121h);

        a(boolean z, boolean z2, List<ScanFilter> list, ScanSettings scanSettings, PendingIntent pendingIntent) {
            super(z, z2, list, scanSettings, new i(pendingIntent, scanSettings), new Handler());
        }
    }

    e() {
    }

    /* access modifiers changed from: package-private */
    public a a(PendingIntent pendingIntent) {
        synchronized (this.c) {
            if (!this.c.containsKey(pendingIntent)) {
                return null;
            }
            a aVar = this.c.get(pendingIntent);
            if (aVar != null) {
                return aVar;
            }
            throw new IllegalStateException("Scanning has been stopped");
        }
    }

    /* access modifiers changed from: package-private */
    public ArrayList<ScanFilter> c(List<ScanFilter> list) {
        ArrayList<ScanFilter> arrayList = new ArrayList<>();
        for (ScanFilter a2 : list) {
            arrayList.add(a(a2));
        }
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    public void a(PendingIntent pendingIntent, a aVar) {
        synchronized (this.c) {
            this.c.put(pendingIntent, aVar);
        }
    }

    /* access modifiers changed from: package-private */
    public ScanSettings a(BluetoothAdapter bluetoothAdapter, ScanSettings scanSettings, boolean z) {
        ScanSettings.Builder builder = new ScanSettings.Builder();
        if (z || (bluetoothAdapter.isOffloadedScanBatchingSupported() && scanSettings.m())) {
            builder.setReportDelay(scanSettings.k());
        }
        if (z || scanSettings.n()) {
            builder.setCallbackType(scanSettings.b()).setMatchMode(scanSettings.f()).setNumOfMatches(scanSettings.g());
        }
        builder.setScanMode(scanSettings.l()).setLegacy(scanSettings.c()).setPhy(scanSettings.h());
        return builder.build();
    }

    /* access modifiers changed from: package-private */
    public ScanSettings a(ScanSettings scanSettings, boolean z, boolean z2, boolean z3, long j2, long j3, int i2, int i3) {
        ScanSettings.b bVar = new ScanSettings.b();
        bVar.a(scanSettings.getLegacy());
        bVar.d(scanSettings.getPhy());
        bVar.a(scanSettings.getCallbackType());
        bVar.e(scanSettings.getScanMode());
        bVar.a(scanSettings.getReportDelayMillis());
        bVar.b(z);
        bVar.d(z2);
        bVar.c(z3);
        bVar.a(j2, j3);
        bVar.b(i2);
        bVar.c(i3);
        return bVar.a();
    }

    /* access modifiers changed from: package-private */
    public ScanFilter a(ScanFilter scanFilter) {
        ScanFilter.b bVar = new ScanFilter.b();
        bVar.a(scanFilter.getDeviceAddress());
        bVar.b(scanFilter.getDeviceName());
        bVar.a(scanFilter.getServiceUuid(), scanFilter.getServiceUuidMask());
        bVar.a(scanFilter.getManufacturerId(), scanFilter.getManufacturerData(), scanFilter.getManufacturerDataMask());
        if (scanFilter.getServiceDataUuid() != null) {
            bVar.a(scanFilter.getServiceDataUuid(), scanFilter.getServiceData(), scanFilter.getServiceDataMask());
        }
        return bVar.a();
    }

    /* access modifiers changed from: package-private */
    public ScanResult a(ScanResult scanResult) {
        return new ScanResult(scanResult.getDevice(), ((scanResult.getDataStatus() << 5) | (scanResult.isLegacy() ? 16 : 0)) | scanResult.isConnectable() ? 1 : 0, scanResult.getPrimaryPhy(), scanResult.getSecondaryPhy(), scanResult.getAdvertisingSid(), scanResult.getTxPower(), scanResult.getRssi(), scanResult.getPeriodicAdvertisingInterval(), k.a(scanResult.getScanRecord() != null ? scanResult.getScanRecord().getBytes() : null), scanResult.getTimestampNanos());
    }
}
