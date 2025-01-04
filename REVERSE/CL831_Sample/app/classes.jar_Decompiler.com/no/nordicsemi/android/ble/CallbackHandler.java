package no.nordicsemi.android.ble;

import androidx.annotation.NonNull;

interface CallbackHandler {
   void post(@NonNull Runnable var1);

   void postDelayed(@NonNull Runnable var1, long var2);

   void removeCallbacks(@NonNull Runnable var1);
}
