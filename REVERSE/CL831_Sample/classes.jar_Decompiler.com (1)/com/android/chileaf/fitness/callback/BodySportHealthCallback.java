package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface BodySportHealthCallback {
   void onSportHealthReceived(@NonNull final BluetoothDevice device, final int vo2Max, final int breathRate, final int emotion, final int pressure, final int stamina);
}
