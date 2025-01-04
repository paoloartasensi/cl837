package com.android.chileaf.fitness.common.battery;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import androidx.annotation.NonNull;
import no.nordicsemi.android.ble.callback.profile.ProfileReadResponse;
import no.nordicsemi.android.ble.data.Data;

public abstract class BatteryLevelDataCallback extends ProfileReadResponse implements BatteryLevelCallback {
   public BatteryLevelDataCallback() {
   }

   protected BatteryLevelDataCallback(final Parcel in) {
      super(in);
   }

   public void onDataReceived(@NonNull final BluetoothDevice device, @NonNull final Data data) {
      super.onDataReceived(device, data);
      if (data.size() == 1) {
         int batteryLevel = data.getIntValue(17, 0);
         if (batteryLevel >= 0 && batteryLevel <= 100) {
            this.onBatteryLevelChanged(device, batteryLevel);
            return;
         }
      }

      this.onInvalidDataReceived(device, data);
   }
}
