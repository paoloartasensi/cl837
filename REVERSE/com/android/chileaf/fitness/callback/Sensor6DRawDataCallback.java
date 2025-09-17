package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface Sensor6DRawDataCallback {
  void onSensor6DRawDataReceived(@NonNull BluetoothDevice paramBluetoothDevice, long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\callback\Sensor6DRawDataCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */