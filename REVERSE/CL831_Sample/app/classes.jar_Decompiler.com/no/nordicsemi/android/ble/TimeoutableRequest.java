package no.nordicsemi.android.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Handler;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.concurrent.CancellationException;
import no.nordicsemi.android.ble.callback.FailCallback;
import no.nordicsemi.android.ble.callback.SuccessCallback;
import no.nordicsemi.android.ble.exception.BluetoothDisabledException;
import no.nordicsemi.android.ble.exception.DeviceDisconnectedException;
import no.nordicsemi.android.ble.exception.InvalidRequestException;
import no.nordicsemi.android.ble.exception.RequestFailedException;

public abstract class TimeoutableRequest extends Request {
   @Nullable
   private Runnable timeoutCallback;
   protected boolean cancelled;
   protected long timeout;

   TimeoutableRequest(@NonNull Request.Type type) {
      super(type);
   }

   TimeoutableRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic) {
      super(type, characteristic);
   }

   TimeoutableRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor) {
      super(type, descriptor);
   }

   @NonNull
   TimeoutableRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
      super.setRequestHandler(requestHandler);
      return this;
   }

   @NonNull
   public TimeoutableRequest setHandler(@Nullable Handler handler) {
      super.setHandler(handler);
      return this;
   }

   @NonNull
   public TimeoutableRequest timeout(@IntRange(from = 0L) long timeout) {
      if (this.timeoutCallback != null) {
         throw new IllegalStateException("Request already started");
      } else {
         this.timeout = timeout;
         return this;
      }
   }

   public void cancel() {
      if (!this.started) {
         this.cancelled = true;
         this.finished = true;
      } else if (!this.finished) {
         this.cancelled = true;
         this.requestHandler.cancelCurrent();
      }

   }

   public final void enqueue() {
      super.enqueue();
   }

   /** @deprecated */
   @Deprecated
   public final void enqueue(@IntRange(from = 0L) long timeout) {
      this.timeout(timeout).enqueue();
   }

   public final void await() throws RequestFailedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException, InterruptedException {
      assertNotMainThread();
      if (this.cancelled) {
         throw new CancellationException();
      } else if (!this.finished && !this.enqueued) {
         SuccessCallback sc = this.successCallback;
         FailCallback fc = this.failCallback;

         try {
            this.syncLock.close();
            Request.RequestCallback callback = new Request.RequestCallback();
            this.done(callback).fail(callback).invalid(callback).enqueue();
            if (!this.syncLock.block(this.timeout)) {
               throw new InterruptedException();
            }

            if (!callback.isSuccess()) {
               if (callback.status == -7) {
                  throw new CancellationException();
               }

               if (callback.status == -1) {
                  throw new DeviceDisconnectedException();
               }

               if (callback.status == -100) {
                  throw new BluetoothDisabledException();
               }

               if (callback.status == -1000000) {
                  throw new InvalidRequestException(this);
               }

               throw new RequestFailedException(this, callback.status);
            }
         } finally {
            this.successCallback = sc;
            this.failCallback = fc;
         }

      } else {
         throw new IllegalStateException();
      }
   }

   /** @deprecated */
   @Deprecated
   public final void await(@IntRange(from = 0L) long timeout) throws RequestFailedException, InterruptedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException, CancellationException {
      this.timeout(timeout).await();
   }

   void notifyStarted(@NonNull BluetoothDevice device) {
      if (this.timeout > 0L) {
         this.timeoutCallback = () -> {
            this.timeoutCallback = null;
            if (!this.finished) {
               this.requestHandler.onRequestTimeout(device, this);
            }

         };
         this.handler.postDelayed(this.timeoutCallback, this.timeout);
      }

      super.notifyStarted(device);
   }

   boolean notifySuccess(@NonNull BluetoothDevice device) {
      if (this.timeoutCallback != null) {
         this.handler.removeCallbacks(this.timeoutCallback);
         this.timeoutCallback = null;
      }

      return super.notifySuccess(device);
   }

   void notifyFail(@NonNull BluetoothDevice device, int status) {
      if (this.timeoutCallback != null) {
         this.handler.removeCallbacks(this.timeoutCallback);
         this.timeoutCallback = null;
      }

      super.notifyFail(device, status);
   }

   void notifyInvalidRequest() {
      if (this.timeoutCallback != null) {
         this.handler.removeCallbacks(this.timeoutCallback);
         this.timeoutCallback = null;
      }

      super.notifyInvalidRequest();
   }

   public final boolean isCancelled() {
      return this.cancelled;
   }
}
