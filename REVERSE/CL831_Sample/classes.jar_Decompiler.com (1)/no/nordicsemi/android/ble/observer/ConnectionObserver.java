package no.nordicsemi.android.ble.observer;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface ConnectionObserver {
   int REASON_UNKNOWN = -1;
   int REASON_SUCCESS = 0;
   int REASON_TERMINATE_LOCAL_HOST = 1;
   int REASON_TERMINATE_PEER_USER = 2;
   int REASON_LINK_LOSS = 3;
   int REASON_NOT_SUPPORTED = 4;
   int REASON_CANCELLED = 5;
   int REASON_TIMEOUT = 10;

   void onDeviceConnecting(@NonNull BluetoothDevice var1);

   void onDeviceConnected(@NonNull BluetoothDevice var1);

   void onDeviceFailedToConnect(@NonNull BluetoothDevice var1, int var2);

   void onDeviceReady(@NonNull BluetoothDevice var1);

   void onDeviceDisconnecting(@NonNull BluetoothDevice var1);

   void onDeviceDisconnected(@NonNull BluetoothDevice var1, int var2);
}
