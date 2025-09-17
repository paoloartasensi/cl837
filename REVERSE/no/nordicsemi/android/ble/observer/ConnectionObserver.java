package no.nordicsemi.android.ble.observer;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface ConnectionObserver {
  public static final int REASON_UNKNOWN = -1;
  
  public static final int REASON_SUCCESS = 0;
  
  public static final int REASON_TERMINATE_LOCAL_HOST = 1;
  
  public static final int REASON_TERMINATE_PEER_USER = 2;
  
  public static final int REASON_LINK_LOSS = 3;
  
  public static final int REASON_NOT_SUPPORTED = 4;
  
  public static final int REASON_CANCELLED = 5;
  
  public static final int REASON_TIMEOUT = 10;
  
  void onDeviceConnecting(@NonNull BluetoothDevice paramBluetoothDevice);
  
  void onDeviceConnected(@NonNull BluetoothDevice paramBluetoothDevice);
  
  void onDeviceFailedToConnect(@NonNull BluetoothDevice paramBluetoothDevice, int paramInt);
  
  void onDeviceReady(@NonNull BluetoothDevice paramBluetoothDevice);
  
  void onDeviceDisconnecting(@NonNull BluetoothDevice paramBluetoothDevice);
  
  void onDeviceDisconnected(@NonNull BluetoothDevice paramBluetoothDevice, int paramInt);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\observer\ConnectionObserver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */