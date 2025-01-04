package no.nordicsemi.android.ble;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import no.nordicsemi.android.ble.callback.AfterCallback;
import no.nordicsemi.android.ble.callback.BeforeCallback;
import no.nordicsemi.android.ble.callback.ConnectionParametersUpdatedCallback;
import no.nordicsemi.android.ble.callback.ConnectionPriorityCallback;
import no.nordicsemi.android.ble.callback.FailCallback;
import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
import no.nordicsemi.android.ble.callback.SuccessCallback;
import no.nordicsemi.android.ble.exception.BluetoothDisabledException;
import no.nordicsemi.android.ble.exception.DeviceDisconnectedException;
import no.nordicsemi.android.ble.exception.InvalidRequestException;
import no.nordicsemi.android.ble.exception.RequestFailedException;

public final class ConnectionPriorityRequest extends SimpleValueRequest<ConnectionParametersUpdatedCallback> implements Operation {
   public static final int CONNECTION_PRIORITY_BALANCED = 0;
   public static final int CONNECTION_PRIORITY_HIGH = 1;
   public static final int CONNECTION_PRIORITY_LOW_POWER = 2;
   private final int value;

   ConnectionPriorityRequest(@NonNull Request.Type type, int priority) {
      super(type);
      if (priority < 0 || priority > 2) {
         priority = 0;
      }

      this.value = priority;
   }

   @NonNull
   ConnectionPriorityRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
      super.setRequestHandler(requestHandler);
      return this;
   }

   @NonNull
   public ConnectionPriorityRequest setHandler(@Nullable Handler handler) {
      super.setHandler(handler);
      return this;
   }

   @NonNull
   public ConnectionPriorityRequest done(@NonNull SuccessCallback callback) {
      super.done(callback);
      return this;
   }

   @NonNull
   public ConnectionPriorityRequest fail(@NonNull FailCallback callback) {
      super.fail(callback);
      return this;
   }

   @NonNull
   public ConnectionPriorityRequest invalid(@NonNull InvalidRequestCallback callback) {
      super.invalid(callback);
      return this;
   }

   @NonNull
   public ConnectionPriorityRequest before(@NonNull BeforeCallback callback) {
      super.before(callback);
      return this;
   }

   @NonNull
   public ConnectionPriorityRequest then(@NonNull AfterCallback callback) {
      super.then(callback);
      return this;
   }

   /** @deprecated */
   @Deprecated
   @RequiresApi(26)
   @NonNull
   public ConnectionPriorityRequest with(@NonNull ConnectionPriorityCallback callback) {
      super.with(callback);
      return this;
   }

   @RequiresApi(26)
   @NonNull
   public ConnectionPriorityRequest with(@NonNull ConnectionParametersUpdatedCallback callback) {
      super.with(callback);
      return this;
   }

   @RequiresApi(26)
   @NonNull
   public <E extends ConnectionParametersUpdatedCallback> E await(@NonNull Class<E> responseClass) throws RequestFailedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException {
      return (ConnectionParametersUpdatedCallback)super.await(responseClass);
   }

   @RequiresApi(26)
   @NonNull
   public <E extends ConnectionParametersUpdatedCallback> E await(@NonNull E response) throws RequestFailedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException {
      return (ConnectionParametersUpdatedCallback)super.await((Object)response);
   }

   @RequiresApi(
      api = 26
   )
   void notifyConnectionPriorityChanged(@NonNull BluetoothDevice device, @IntRange(from = 6L,to = 3200L) int interval, @IntRange(from = 0L,to = 499L) int latency, @IntRange(from = 10L,to = 3200L) int timeout) {
      if (this.valueCallback != null) {
         ((ConnectionParametersUpdatedCallback)this.valueCallback).onConnectionUpdated(device, interval, latency, timeout);
      }

   }

   int getRequiredPriority() {
      return this.value;
   }
}
