package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.android.chileaf.model.HistoryOf3D;

public interface HistoryOf3DDataCallback {
  void onHistoryOf3DDataReceived(@NonNull BluetoothDevice paramBluetoothDevice, HistoryOf3D paramHistoryOf3D, boolean paramBoolean);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\callback\HistoryOf3DDataCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */