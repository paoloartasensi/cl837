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
import no.nordicsemi.android.ble.callback.MtuCallback;
import no.nordicsemi.android.ble.callback.SuccessCallback;

public final class MtuRequest extends SimpleValueRequest<MtuCallback> implements Operation {
   private final int value;

   MtuRequest(@NonNull Request.Type type, @IntRange(from = 23L,to = 517L) int mtu) {
      super(type);
      if (mtu < 23) {
         mtu = 23;
      }

      if (mtu > 517) {
         mtu = 517;
      }

      this.value = mtu;
   }

   @NonNull
   MtuRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
      super.setRequestHandler(requestHandler);
      return this;
   }

   @NonNull
   public MtuRequest setHandler(@Nullable Handler handler) {
      super.setHandler(handler);
      return this;
   }

   @NonNull
   public MtuRequest done(@NonNull SuccessCallback callback) {
      super.done(callback);
      return this;
   }

   @NonNull
   public MtuRequest fail(@NonNull FailCallback callback) {
      super.fail(callback);
      return this;
   }

   @NonNull
   public MtuRequest invalid(@NonNull InvalidRequestCallback callback) {
      super.invalid(callback);
      return this;
   }

   @NonNull
   public MtuRequest before(@NonNull BeforeCallback callback) {
      super.before(callback);
      return this;
   }

   @NonNull
   public MtuRequest then(@NonNull AfterCallback callback) {
      super.then(callback);
      return this;
   }

   @NonNull
   public MtuRequest with(@NonNull MtuCallback callback) {
      super.with(callback);
      return this;
   }

   void notifyMtuChanged(@NonNull BluetoothDevice device, @IntRange(from = 23L,to = 517L) int mtu) {
      this.handler.post(() -> {
         if (this.valueCallback != null) {
            try {
               ((MtuCallback)this.valueCallback).onMtuChanged(device, mtu);
            } catch (Throwable var4) {
               Log.e(TAG, "Exception in Value callback", var4);
            }
         }

      });
   }

   int getRequiredMtu() {
      return this.value;
   }
}
