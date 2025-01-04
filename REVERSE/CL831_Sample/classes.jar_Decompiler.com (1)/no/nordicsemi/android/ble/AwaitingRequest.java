package no.nordicsemi.android.ble;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.concurrent.CancellationException;
import no.nordicsemi.android.ble.exception.BluetoothDisabledException;
import no.nordicsemi.android.ble.exception.DeviceDisconnectedException;
import no.nordicsemi.android.ble.exception.InvalidRequestException;
import no.nordicsemi.android.ble.exception.RequestFailedException;

public abstract class AwaitingRequest<T> extends TimeoutableValueRequest<T> {
   private static final int NOT_STARTED = -123456;
   private static final int STARTED = -123455;
   private Request trigger;
   private int triggerStatus = 0;

   AwaitingRequest(@NonNull Request.Type type) {
      super(type);
   }

   AwaitingRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic) {
      super(type, characteristic);
   }

   AwaitingRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor) {
      super(type, descriptor);
   }

   @NonNull
   public AwaitingRequest<T> trigger(@NonNull Operation trigger) {
      if (trigger instanceof Request) {
         this.trigger = (Request)trigger;
         this.triggerStatus = -123456;
         this.trigger.internalBefore((device) -> {
            this.triggerStatus = -123455;
         });
         this.trigger.internalSuccess((device) -> {
            this.triggerStatus = 0;
         });
         this.trigger.internalFail((device, status) -> {
            this.triggerStatus = status;
            this.syncLock.open();
            this.notifyFail(device, status);
         });
      }

      return this;
   }

   @NonNull
   public <E extends T> E await(@NonNull E response) throws RequestFailedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException, InterruptedException, CancellationException {
      assertNotMainThread();

      try {
         if (this.trigger != null && this.trigger.enqueued) {
            throw new IllegalStateException("Trigger request already enqueued");
         } else {
            super.await(response);
            return response;
         }
      } catch (RequestFailedException var3) {
         if (this.triggerStatus != 0) {
            throw new RequestFailedException(this.trigger, this.triggerStatus);
         } else {
            throw var3;
         }
      }
   }

   @Nullable
   Request getTrigger() {
      return this.trigger;
   }

   boolean isTriggerPending() {
      return this.triggerStatus == -123456;
   }

   boolean isTriggerCompleteOrNull() {
      return this.triggerStatus != -123455;
   }
}
