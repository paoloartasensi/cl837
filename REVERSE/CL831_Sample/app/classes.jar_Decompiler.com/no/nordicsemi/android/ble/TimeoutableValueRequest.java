package no.nordicsemi.android.ble;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.concurrent.CancellationException;
import no.nordicsemi.android.ble.exception.BluetoothDisabledException;
import no.nordicsemi.android.ble.exception.DeviceDisconnectedException;
import no.nordicsemi.android.ble.exception.InvalidRequestException;
import no.nordicsemi.android.ble.exception.RequestFailedException;

public abstract class TimeoutableValueRequest<T> extends TimeoutableRequest {
   T valueCallback;

   TimeoutableValueRequest(@NonNull Request.Type type) {
      super(type);
   }

   TimeoutableValueRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic) {
      super(type, characteristic);
   }

   TimeoutableValueRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor) {
      super(type, descriptor);
   }

   @NonNull
   public TimeoutableValueRequest<T> timeout(@IntRange(from = 0L) long timeout) {
      super.timeout(timeout);
      return this;
   }

   @NonNull
   public TimeoutableValueRequest<T> with(@NonNull T callback) {
      this.valueCallback = callback;
      return this;
   }

   @NonNull
   public <E extends T> E await(@NonNull E response) throws RequestFailedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException, InterruptedException, CancellationException {
      assertNotMainThread();
      Object vc = this.valueCallback;

      Object var3;
      try {
         this.with(response).await();
         var3 = response;
      } finally {
         this.valueCallback = vc;
      }

      return var3;
   }

   @NonNull
   public <E extends T> E await(@NonNull Class<E> responseClass) throws RequestFailedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException, InterruptedException, CancellationException {
      assertNotMainThread();

      try {
         E response = responseClass.newInstance();
         return this.await(response);
      } catch (IllegalAccessException var3) {
         throw new IllegalArgumentException("Couldn't instantiate " + responseClass.getCanonicalName() + " class. Is the default constructor accessible?");
      } catch (InstantiationException var4) {
         throw new IllegalArgumentException("Couldn't instantiate " + responseClass.getCanonicalName() + " class. Does it have a default constructor with no arguments?");
      }
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public <E extends T> E await(@NonNull Class<E> responseClass, @IntRange(from = 0L) long timeout) throws RequestFailedException, InterruptedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException, CancellationException {
      return this.timeout(timeout).await(responseClass);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public <E extends T> E await(@NonNull E response, @IntRange(from = 0L) long timeout) throws RequestFailedException, InterruptedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException, CancellationException {
      return this.timeout(timeout).await(response);
   }
}
