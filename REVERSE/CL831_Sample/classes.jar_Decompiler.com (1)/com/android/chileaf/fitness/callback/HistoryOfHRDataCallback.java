package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.android.chileaf.model.HistoryOfHeartRate;
import java.util.List;

public interface HistoryOfHRDataCallback {
   void onHistoryOfHRDataReceived(@NonNull final BluetoothDevice device, List<HistoryOfHeartRate> heartRates);
}
