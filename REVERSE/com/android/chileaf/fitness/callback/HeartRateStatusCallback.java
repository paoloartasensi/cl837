package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

public interface HeartRateStatusCallback {
  void onHeartRateStatusReceived(@NonNull BluetoothDevice paramBluetoothDevice, @IntRange(from = 0L) int paramInt1, @IntRange(from = 0L) int paramInt2, @IntRange(from = 0L) int paramInt3);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\callback\HeartRateStatusCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */