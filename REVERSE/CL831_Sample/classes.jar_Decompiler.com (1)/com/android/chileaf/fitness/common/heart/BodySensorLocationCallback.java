package com.android.chileaf.fitness.common.heart;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface BodySensorLocationCallback {
   int SENSOR_LOCATION_OTHER = 0;
   int SENSOR_LOCATION_CHEST = 1;
   int SENSOR_LOCATION_WRIST = 2;
   int SENSOR_LOCATION_FINGER = 3;
   int SENSOR_LOCATION_HAND = 4;
   int SENSOR_LOCATION_EAR_LOBE = 5;
   int SENSOR_LOCATION_FOOT = 6;
   int SENSOR_LOCATION_FIRST = 0;
   int SENSOR_LOCATION_LAST = 6;

   void onBodySensorLocationReceived(@NonNull final BluetoothDevice device, final int sensorLocation);
}
