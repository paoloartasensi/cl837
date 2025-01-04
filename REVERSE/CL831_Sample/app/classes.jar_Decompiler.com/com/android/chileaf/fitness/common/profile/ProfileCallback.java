package com.android.chileaf.fitness.common.profile;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface ProfileCallback {
   void onSystemId(@NonNull final BluetoothDevice device, final String systemId);

   void onModelName(@NonNull final BluetoothDevice device, final String modelName);

   void onSerialNumber(@NonNull final BluetoothDevice device, final String serialNumber);

   void onFirmwareVersion(@NonNull final BluetoothDevice device, final String firmware);

   void onHardwareVersion(@NonNull final BluetoothDevice device, final String hardware);

   void onSoftwareVersion(@NonNull final BluetoothDevice device, final String software);

   void onVendorName(@NonNull final BluetoothDevice device, final String vendorName);
}
