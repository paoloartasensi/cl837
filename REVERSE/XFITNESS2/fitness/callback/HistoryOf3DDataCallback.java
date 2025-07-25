package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.android.chileaf.model.HistoryOf3D;

public interface HistoryOf3DDataCallback {
   void onHistoryOf3DDataReceived(@NonNull final BluetoothDevice device, HistoryOf3D history, boolean finish);
}
