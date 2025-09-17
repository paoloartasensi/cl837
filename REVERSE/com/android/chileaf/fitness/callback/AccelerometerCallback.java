package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface AccelerometerCallback {
  void onAccelerometerReceived(@NonNull BluetoothDevice paramBluetoothDevice, int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\callback\AccelerometerCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */