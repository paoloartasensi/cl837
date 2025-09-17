package com.android.chileaf.fitness.common.profile;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface ProfileCallback {
  void onSystemId(@NonNull BluetoothDevice paramBluetoothDevice, String paramString);
  
  void onModelName(@NonNull BluetoothDevice paramBluetoothDevice, String paramString);
  
  void onSerialNumber(@NonNull BluetoothDevice paramBluetoothDevice, String paramString);
  
  void onFirmwareVersion(@NonNull BluetoothDevice paramBluetoothDevice, String paramString);
  
  void onHardwareVersion(@NonNull BluetoothDevice paramBluetoothDevice, String paramString);
  
  void onSoftwareVersion(@NonNull BluetoothDevice paramBluetoothDevice, String paramString);
  
  void onVendorName(@NonNull BluetoothDevice paramBluetoothDevice, String paramString);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\common\profile\ProfileCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */