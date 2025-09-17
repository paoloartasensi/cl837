package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface CustomDataReceivedCallback {
  void onDataReceived(@NonNull BluetoothDevice paramBluetoothDevice, byte[] paramArrayOfbyte);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\callback\CustomDataReceivedCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */