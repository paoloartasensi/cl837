package no.nordicsemi.android.ble;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import no.nordicsemi.android.ble.callback.AfterCallback;
import no.nordicsemi.android.ble.callback.BeforeCallback;
import no.nordicsemi.android.ble.callback.FailCallback;
import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
import no.nordicsemi.android.ble.callback.RssiCallback;
import no.nordicsemi.android.ble.callback.SuccessCallback;

public final class ReadRssiRequest extends SimpleValueRequest<RssiCallback> implements Operation {
   ReadRssiRequest(@NonNull Request.Type type) {
      super(type);
   }

   @NonNull
   ReadRssiRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
      super.setRequestHandler(requestHandler);
      return this;
   }

   @NonNull
   public ReadRssiRequest setHandler(@Nullable Handler handler) {
      super.setHandler(handler);
      return this;
   }

   @NonNull
   public ReadRssiRequest done(@NonNull SuccessCallback callback) {
      super.done(callback);
      return this;
   }

   @NonNull
   public ReadRssiRequest fail(@NonNull FailCallback callback) {
      super.fail(callback);
      return this;
   }

   @NonNull
   public ReadRssiRequest invalid(@NonNull InvalidRequestCallback callback) {
      super.invalid(callback);
      return this;
   }

   @NonNull
   public ReadRssiRequest before(@NonNull BeforeCallback callback) {
      super.before(callback);
      return this;
   }

   @NonNull
   public ReadRssiRequest then(@NonNull AfterCallback callback) {
      super.then(callback);
      return this;
   }

   @NonNull
   public ReadRssiRequest with(@NonNull RssiCallback callback) {
      super.with(callback);
      return this;
   }

   void notifyRssiRead(@NonNull BluetoothDevice device, @IntRange(from = -128L,to = 20L) int rssi) {
      this.handler.post(() -> {
         if (this.valueCallback != null) {
            try {
               ((RssiCallback)this.valueCallback).onRssiRead(device, rssi);
            } catch (Throwable var4) {
               Log.e(TAG, "Exception in Value callback", var4);
            }
         }

      });
   }
}
