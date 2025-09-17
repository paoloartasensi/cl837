package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.android.chileaf.model.HistoryOfSport;
import java.util.List;

public interface HistoryOfSportCallback {
  void onHistoryOfSportReceived(@NonNull BluetoothDevice paramBluetoothDevice, List<HistoryOfSport> paramList);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\callback\HistoryOfSportCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */