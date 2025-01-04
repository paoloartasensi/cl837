package no.nordicsemi.android.ble;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.NonNull;

/** @deprecated */
@Deprecated
public abstract class LegacyBleManager<E extends BleManagerCallbacks> extends BleManager {
   protected E mCallbacks;

   public LegacyBleManager(@NonNull Context context) {
      super(context);
   }

   public LegacyBleManager(@NonNull Context context, @NonNull Handler handler) {
      super(context, handler);
   }

   public void setGattCallbacks(@NonNull BleManagerCallbacks callbacks) {
      super.setGattCallbacks(callbacks);
      this.mCallbacks = callbacks;
   }
}
