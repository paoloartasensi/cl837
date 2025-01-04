package no.nordicsemi.android.ble;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import no.nordicsemi.android.ble.callback.BeforeCallback;
import no.nordicsemi.android.ble.callback.FailCallback;
import no.nordicsemi.android.ble.callback.SuccessCallback;
import no.nordicsemi.android.ble.exception.BluetoothDisabledException;
import no.nordicsemi.android.ble.exception.DeviceDisconnectedException;
import no.nordicsemi.android.ble.exception.InvalidRequestException;
import no.nordicsemi.android.ble.exception.RequestFailedException;

public class SimpleRequest extends Request {
   SimpleRequest(@NonNull Request.Type type) {
      super(type);
   }

   SimpleRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic) {
      super(type, characteristic);
   }

   SimpleRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor) {
      super(type, descriptor);
   }

   public final void await() throws RequestFailedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException {
      assertNotMainThread();
      BeforeCallback bc = this.beforeCallback;
      SuccessCallback sc = this.successCallback;
      FailCallback fc = this.failCallback;

      try {
         if (!this.finished && !this.enqueued) {
            this.syncLock.close();
            Request.RequestCallback callback = new Request.RequestCallback();
            this.beforeCallback = null;
            this.done(callback).fail(callback).invalid(callback).enqueue();
            this.syncLock.block();
            if (!callback.isSuccess()) {
               if (callback.status == -1) {
                  throw new DeviceDisconnectedException();
               } else if (callback.status == -100) {
                  throw new BluetoothDisabledException();
               } else if (callback.status == -1000000) {
                  throw new InvalidRequestException(this);
               } else {
                  throw new RequestFailedException(this, callback.status);
               }
            }
         } else {
            throw new IllegalStateException();
         }
      } finally {
         this.beforeCallback = bc;
         this.successCallback = sc;
         this.failCallback = fc;
      }
   }
}
