package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

public interface UserInfoCallback {
   void onUserInfoReceived(@NonNull final BluetoothDevice device, @IntRange(from = 0L) final int age, @IntRange(from = 0L) final int sex, @IntRange(from = 0L) final int weight, @IntRange(from = 0L) final int height, final long userId);
}
