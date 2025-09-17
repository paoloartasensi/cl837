package com.android.chileaf.fitness.common.heart;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface BodySensorLocationCallback {
  public static final int SENSOR_LOCATION_OTHER = 0;
  
  public static final int SENSOR_LOCATION_CHEST = 1;
  
  public static final int SENSOR_LOCATION_WRIST = 2;
  
  public static final int SENSOR_LOCATION_FINGER = 3;
  
  public static final int SENSOR_LOCATION_HAND = 4;
  
  public static final int SENSOR_LOCATION_EAR_LOBE = 5;
  
  public static final int SENSOR_LOCATION_FOOT = 6;
  
  public static final int SENSOR_LOCATION_FIRST = 0;
  
  public static final int SENSOR_LOCATION_LAST = 6;
  
  void onBodySensorLocationReceived(@NonNull BluetoothDevice paramBluetoothDevice, int paramInt);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\common\heart\BodySensorLocationCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */