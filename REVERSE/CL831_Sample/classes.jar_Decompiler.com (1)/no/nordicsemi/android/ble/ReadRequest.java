package no.nordicsemi.android.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import no.nordicsemi.android.ble.callback.AfterCallback;
import no.nordicsemi.android.ble.callback.BeforeCallback;
import no.nordicsemi.android.ble.callback.DataReceivedCallback;
import no.nordicsemi.android.ble.callback.FailCallback;
import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
import no.nordicsemi.android.ble.callback.ReadProgressCallback;
import no.nordicsemi.android.ble.callback.SuccessCallback;
import no.nordicsemi.android.ble.callback.profile.ProfileReadResponse;
import no.nordicsemi.android.ble.data.Data;
import no.nordicsemi.android.ble.data.DataFilter;
import no.nordicsemi.android.ble.data.DataMerger;
import no.nordicsemi.android.ble.data.DataStream;
import no.nordicsemi.android.ble.data.PacketFilter;
import no.nordicsemi.android.ble.exception.BluetoothDisabledException;
import no.nordicsemi.android.ble.exception.DeviceDisconnectedException;
import no.nordicsemi.android.ble.exception.InvalidDataException;
import no.nordicsemi.android.ble.exception.InvalidRequestException;
import no.nordicsemi.android.ble.exception.RequestFailedException;

public final class ReadRequest extends SimpleValueRequest<DataReceivedCallback> implements Operation {
   private ReadProgressCallback progressCallback;
   private DataMerger dataMerger;
   private DataStream buffer;
   private DataFilter filter;
   private PacketFilter packetFilter;
   private int count = 0;
   private boolean complete = false;

   ReadRequest(@NonNull Request.Type type) {
      super(type);
   }

   ReadRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic) {
      super(type, characteristic);
   }

   ReadRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor) {
      super(type, descriptor);
   }

   @NonNull
   ReadRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
      super.setRequestHandler(requestHandler);
      return this;
   }

   @NonNull
   public ReadRequest setHandler(@Nullable Handler handler) {
      super.setHandler(handler);
      return this;
   }

   @NonNull
   public ReadRequest done(@NonNull SuccessCallback callback) {
      super.done(callback);
      return this;
   }

   @NonNull
   public ReadRequest fail(@NonNull FailCallback callback) {
      super.fail(callback);
      return this;
   }

   @NonNull
   public ReadRequest invalid(@NonNull InvalidRequestCallback callback) {
      super.invalid(callback);
      return this;
   }

   @NonNull
   public ReadRequest before(@NonNull BeforeCallback callback) {
      super.before(callback);
      return this;
   }

   @NonNull
   public ReadRequest then(@NonNull AfterCallback callback) {
      super.then(callback);
      return this;
   }

   @NonNull
   public ReadRequest with(@NonNull DataReceivedCallback callback) {
      super.with(callback);
      return this;
   }

   @NonNull
   public ReadRequest filter(@NonNull DataFilter filter) {
      this.filter = filter;
      return this;
   }

   @NonNull
   public ReadRequest filterPacket(@NonNull PacketFilter filter) {
      this.packetFilter = filter;
      return this;
   }

   @NonNull
   public ReadRequest merge(@NonNull DataMerger merger) {
      this.dataMerger = merger;
      this.progressCallback = null;
      return this;
   }

   @NonNull
   public ReadRequest merge(@NonNull DataMerger merger, @NonNull ReadProgressCallback callback) {
      this.dataMerger = merger;
      this.progressCallback = callback;
      return this;
   }

   @NonNull
   public <E extends ProfileReadResponse> E awaitValid(@NonNull Class<E> responseClass) throws RequestFailedException, InvalidDataException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException {
      E response = (ProfileReadResponse)this.await(responseClass);
      if (!response.isValid()) {
         throw new InvalidDataException(response);
      } else {
         return response;
      }
   }

   @NonNull
   public <E extends ProfileReadResponse> E awaitValid(@NonNull E response) throws RequestFailedException, InvalidDataException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException {
      this.await(response);
      if (!response.isValid()) {
         throw new InvalidDataException(response);
      } else {
         return response;
      }
   }

   boolean matches(byte[] packet) {
      return this.filter == null || this.filter.filter(packet);
   }

   void notifyValueChanged(@NonNull BluetoothDevice device, @Nullable byte[] value) {
      DataReceivedCallback valueCallback = (DataReceivedCallback)this.valueCallback;
      if (valueCallback == null) {
         if (this.packetFilter == null || this.packetFilter.filter(value)) {
            this.complete = true;
         }

      } else {
         if (this.dataMerger == null) {
            this.complete = true;
            Data data = new Data(value);
            this.handler.post(() -> {
               try {
                  valueCallback.onDataReceived(device, data);
               } catch (Throwable var4) {
                  Log.e(TAG, "Exception in Value callback", var4);
               }

            });
         } else {
            this.handler.post(() -> {
               if (this.progressCallback != null) {
                  try {
                     this.progressCallback.onPacketReceived(device, value, this.count);
                  } catch (Throwable var4) {
                     Log.e(TAG, "Exception in Progress callback", var4);
                  }
               }

            });
            if (this.buffer == null) {
               this.buffer = new DataStream();
            }

            if (this.dataMerger.merge(this.buffer, value, this.count++)) {
               byte[] merged = this.buffer.toByteArray();
               if (this.packetFilter == null || this.packetFilter.filter(merged)) {
                  this.complete = true;
                  Data data = new Data(merged);
                  this.handler.post(() -> {
                     try {
                        valueCallback.onDataReceived(device, data);
                     } catch (Throwable var4) {
                        Log.e(TAG, "Exception in Value callback", var4);
                     }

                  });
               }

               this.buffer = null;
               this.count = 0;
            }
         }

      }
   }

   boolean hasMore() {
      return !this.complete;
   }
}
