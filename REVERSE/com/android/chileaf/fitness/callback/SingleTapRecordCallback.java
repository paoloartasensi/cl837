package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.android.chileaf.model.HistoryOfRecord;
import java.util.List;

public interface SingleTapRecordCallback {
  void onSingleTapRecordReceived(@NonNull BluetoothDevice paramBluetoothDevice, List<HistoryOfRecord> paramList);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\callback\SingleTapRecordCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */