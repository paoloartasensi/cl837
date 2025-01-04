package com.android.chileaf.fitness;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import com.android.chileaf.fitness.common.battery.BatteryLevelCallback;
import com.android.chileaf.fitness.common.profile.ProfileCallback;
import no.nordicsemi.android.ble.BleManagerCallbacks;
import no.nordicsemi.android.ble.callback.RssiCallback;

public interface FitnessManagerCallbacks extends BleManagerCallbacks, RssiCallback, BatteryLevelCallback, ProfileCallback {
   default void onDeviceConnecting(@NonNull final BluetoothDevice device) {
   }

   void onDeviceConnected(@NonNull final BluetoothDevice device);

   default void onDeviceDisconnecting(@NonNull final BluetoothDevice device) {
   }

   void onDeviceDisconnected(@NonNull final BluetoothDevice device);

   default void onLinkLossOccurred(@NonNull final BluetoothDevice device) {
   }

   default void onServicesDiscovered(@NonNull final BluetoothDevice device, final boolean optionalServicesFound) {
   }

   default void onDeviceReady(@NonNull final BluetoothDevice device) {
   }

   /** @deprecated */
   @Deprecated
   default boolean shouldEnableBatteryLevelNotifications(@NonNull final BluetoothDevice device) {
      return false;
   }

   /** @deprecated */
   @Deprecated
   default void onBatteryValueReceived(@NonNull final BluetoothDevice device, @IntRange(from = 0L,to = 100L) final int value) {
   }

   default void onBondingRequired(@NonNull final BluetoothDevice device) {
   }

   default void onBonded(@NonNull final BluetoothDevice device) {
   }

   default void onBondingFailed(@NonNull final BluetoothDevice device) {
   }

   default void onError(@NonNull final BluetoothDevice device, @NonNull final String message, final int errorCode) {
   }

   default void onDeviceNotSupported(@NonNull final BluetoothDevice device) {
   }

   default void onRssiRead(@NonNull final BluetoothDevice device, final int rssi) {
   }

   default void onBatteryLevelChanged(@NonNull final BluetoothDevice device, final int batteryLevel) {
   }

   default void onSystemId(@NonNull final BluetoothDevice device, final String systemId) {
   }

   default void onModelName(@NonNull final BluetoothDevice device, final String modelName) {
   }

   default void onSerialNumber(@NonNull final BluetoothDevice device, final String serialNumber) {
   }

   default void onFirmwareVersion(@NonNull final BluetoothDevice device, final String firmware) {
   }

   default void onHardwareVersion(@NonNull final BluetoothDevice device, final String hardware) {
   }

   default void onSoftwareVersion(@NonNull final BluetoothDevice device, final String software) {
   }

   default void onVendorName(@NonNull final BluetoothDevice device, final String vendorName) {
   }
}
