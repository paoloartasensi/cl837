package no.nordicsemi.android.ble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

@FunctionalInterface
public interface PhyCallback {
  public static final int PHY_LE_1M = 1;
  
  public static final int PHY_LE_2M = 2;
  
  public static final int PHY_LE_CODED = 3;
  
  void onPhyChanged(@NonNull BluetoothDevice paramBluetoothDevice, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\callback\PhyCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */