package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.android.chileaf.model.HistorySleep;
import java.util.List;

public interface HistoryOfSleepCallback {
  void onHistoryOfSleepReceived(@NonNull BluetoothDevice paramBluetoothDevice, List<HistorySleep> paramList);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\callback\HistoryOfSleepCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */