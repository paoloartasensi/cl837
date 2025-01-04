package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.android.chileaf.model.HistoryOfRespiratoryRate;
import java.util.List;

public interface HistoryOfRRDataCallback {
   void onHistoryOfRRDataReceived(@NonNull final BluetoothDevice device, List<HistoryOfRespiratoryRate> respiratoryRates);
}
