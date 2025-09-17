package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

public interface UserInfoCallback {
  void onUserInfoReceived(@NonNull BluetoothDevice paramBluetoothDevice, @IntRange(from = 0L) int paramInt1, @IntRange(from = 0L) int paramInt2, @IntRange(from = 0L) int paramInt3, @IntRange(from = 0L) int paramInt4, long paramLong);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\callback\UserInfoCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */