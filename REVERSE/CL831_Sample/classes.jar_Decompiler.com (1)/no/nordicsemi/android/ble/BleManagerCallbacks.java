package no.nordicsemi.android.ble;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/** @deprecated */
@Deprecated
public interface BleManagerCallbacks {
   /** @deprecated */
   @Deprecated
   void onDeviceConnecting(@NonNull BluetoothDevice var1);

   /** @deprecated */
   @Deprecated
   void onDeviceConnected(@NonNull BluetoothDevice var1);

   /** @deprecated */
   @Deprecated
   void onDeviceDisconnecting(@NonNull BluetoothDevice var1);

   /** @deprecated */
   @Deprecated
   void onDeviceDisconnected(@NonNull BluetoothDevice var1);

   /** @deprecated */
   @Deprecated
   void onLinkLossOccurred(@NonNull BluetoothDevice var1);

   /** @deprecated */
   @Deprecated
   void onServicesDiscovered(@NonNull BluetoothDevice var1, boolean var2);

   /** @deprecated */
   @Deprecated
   void onDeviceReady(@NonNull BluetoothDevice var1);

   /** @deprecated */
   @Deprecated
   default boolean shouldEnableBatteryLevelNotifications(@NonNull BluetoothDevice device) {
      return false;
   }

   /** @deprecated */
   @Deprecated
   default void onBatteryValueReceived(@NonNull BluetoothDevice device, @IntRange(from = 0L,to = 100L) int value) {
   }

   /** @deprecated */
   @Deprecated
   void onBondingRequired(@NonNull BluetoothDevice var1);

   /** @deprecated */
   @Deprecated
   void onBonded(@NonNull BluetoothDevice var1);

   /** @deprecated */
   @Deprecated
   void onBondingFailed(@NonNull BluetoothDevice var1);

   /** @deprecated */
   @Deprecated
   void onError(@NonNull BluetoothDevice var1, @NonNull String var2, int var3);

   /** @deprecated */
   @Deprecated
   void onDeviceNotSupported(@NonNull BluetoothDevice var1);
}
