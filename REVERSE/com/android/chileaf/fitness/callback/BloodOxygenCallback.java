package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface BloodOxygenCallback {
  void onBloodOxygenReceived(@NonNull BluetoothDevice paramBluetoothDevice, int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\callback\BloodOxygenCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */