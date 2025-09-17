package no.nordicsemi.android.ble;

import androidx.annotation.NonNull;

interface CallbackHandler {
  void post(@NonNull Runnable paramRunnable);
  
  void postDelayed(@NonNull Runnable paramRunnable, long paramLong);
  
  void removeCallbacks(@NonNull Runnable paramRunnable);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\CallbackHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */