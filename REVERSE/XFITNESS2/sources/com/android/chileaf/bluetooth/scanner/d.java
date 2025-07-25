package com.android.chileaf.bluetooth.scanner;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanSettings;

@TargetApi(23)
/* compiled from: BluetoothLeScannerImplMarshmallow */
class d extends c {
    d() {
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
        builder.setScanMode(scanSettings.l());
        return builder.build();
    }
}
