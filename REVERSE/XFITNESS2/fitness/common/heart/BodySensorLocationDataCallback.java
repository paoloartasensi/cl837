package com.android.chileaf.fitness.common.heart;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import androidx.annotation.NonNull;
import no.nordicsemi.android.ble.callback.profile.ProfileReadResponse;
import no.nordicsemi.android.ble.data.Data;

public abstract class BodySensorLocationDataCallback extends ProfileReadResponse implements BodySensorLocationCallback {
   public BodySensorLocationDataCallback() {
   }

   protected BodySensorLocationDataCallback(final Parcel in) {
      super(in);
   }

   public void onDataReceived(@NonNull final BluetoothDevice device, @NonNull final Data data) {
      super.onDataReceived(device, data);
      if (data.size() < 1) {
         this.onInvalidDataReceived(device, data);
      } else {
         int sensorLocation = data.getIntValue(17, 0);
         this.onBodySensorLocationReceived(device, sensorLocation);
      }
   }
}
