package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface BodyHealthCallback {
   void onHealthReceived(@NonNull final BluetoothDevice device, final int vo2Max, final int breathRate, final int emotionLevel, final int stressPercent, final int stamina, final float tp, final float lf, final float hf);
}
