package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.android.chileaf.model.HistoryOfRespiratoryRate;
import java.util.List;

public interface HistoryOfRRDataCallback {
  void onHistoryOfRRDataReceived(@NonNull BluetoothDevice paramBluetoothDevice, List<HistoryOfRespiratoryRate> paramList);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\callback\HistoryOfRRDataCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */