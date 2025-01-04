package no.nordicsemi.android.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import no.nordicsemi.android.ble.callback.AfterCallback;
import no.nordicsemi.android.ble.callback.BeforeCallback;
import no.nordicsemi.android.ble.callback.DataSentCallback;
import no.nordicsemi.android.ble.callback.FailCallback;
import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
import no.nordicsemi.android.ble.callback.SuccessCallback;
import no.nordicsemi.android.ble.callback.WriteProgressCallback;
import no.nordicsemi.android.ble.data.Data;
import no.nordicsemi.android.ble.data.DataSplitter;
import no.nordicsemi.android.ble.data.DefaultMtuSplitter;

public final class WaitForReadRequest extends AwaitingRequest<DataSentCallback> implements Operation {
   private static final DataSplitter MTU_SPLITTER = new DefaultMtuSplitter();
   private WriteProgressCallback progressCallback;
   private DataSplitter dataSplitter;
   private byte[] data;
   private byte[] nextChunk;
   private int count = 0;
   private boolean complete = false;

   WaitForReadRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic) {
      super(type, characteristic);
      this.data = null;
      this.complete = true;
   }

   WaitForReadRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor) {
      super(type, descriptor);
      this.data = null;
      this.complete = true;
   }

   WaitForReadRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] data, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
      super(type, characteristic);
      this.data = Bytes.copy(data, offset, length);
   }

   WaitForReadRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] data, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
      super(type, descriptor);
      this.data = Bytes.copy(data, offset, length);
   }

   void setDataIfNull(@Nullable byte[] data) {
      if (this.data == null) {
         this.data = data;
      }

   }

   @NonNull
   WaitForReadRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
      super.setRequestHandler(requestHandler);
      return this;
   }

   @NonNull
   public WaitForReadRequest setHandler(@Nullable Handler handler) {
      super.setHandler(handler);
      return this;
   }

   @NonNull
   public WaitForReadRequest done(@NonNull SuccessCallback callback) {
      super.done(callback);
      return this;
   }

   @NonNull
   public WaitForReadRequest fail(@NonNull FailCallback callback) {
      super.fail(callback);
      return this;
   }

   @NonNull
   public WaitForReadRequest invalid(@NonNull InvalidRequestCallback callback) {
      super.invalid(callback);
      return this;
   }

   @NonNull
   public WaitForReadRequest before(@NonNull BeforeCallback callback) {
      super.before(callback);
      return this;
   }

   @NonNull
   public WaitForReadRequest then(@NonNull AfterCallback callback) {
      super.then(callback);
      return this;
   }

   @NonNull
   public WaitForReadRequest with(@NonNull DataSentCallback callback) {
      super.with(callback);
      return this;
   }

   @NonNull
   public WaitForReadRequest trigger(@NonNull Operation trigger) {
      super.trigger(trigger);
      return this;
   }

   @NonNull
   public WaitForReadRequest split(@NonNull DataSplitter splitter) {
      this.dataSplitter = splitter;
      this.progressCallback = null;
      return this;
   }

   @NonNull
   public WaitForReadRequest split(@NonNull DataSplitter splitter, @NonNull WriteProgressCallback callback) {
      this.dataSplitter = splitter;
      this.progressCallback = callback;
      return this;
   }

   @NonNull
   public WaitForReadRequest split() {
      this.dataSplitter = MTU_SPLITTER;
      this.progressCallback = null;
      return this;
   }

   @NonNull
   public WaitForReadRequest split(@NonNull WriteProgressCallback callback) {
      this.dataSplitter = MTU_SPLITTER;
      this.progressCallback = callback;
      return this;
   }

   @NonNull
   byte[] getData(@IntRange(from = 23L,to = 517L) int mtu) {
      if (this.dataSplitter != null && this.data != null) {
         int maxLength = mtu - 1;
         byte[] chunk = this.nextChunk;
         if (chunk == null) {
            chunk = this.dataSplitter.chunk(this.data, this.count, maxLength);
         }

         if (chunk != null) {
            this.nextChunk = this.dataSplitter.chunk(this.data, this.count + 1, maxLength);
         }

         if (this.nextChunk == null) {
            this.complete = true;
         }

         return chunk != null ? chunk : new byte[0];
      } else {
         this.complete = true;
         return this.data != null ? this.data : new byte[0];
      }
   }

   void notifyPacketRead(@NonNull BluetoothDevice device, @Nullable byte[] data) {
      this.handler.post(() -> {
         if (this.progressCallback != null) {
            try {
               this.progressCallback.onPacketSent(device, data, this.count);
            } catch (Throwable var4) {
               Log.e(TAG, "Exception in Progress callback", var4);
            }
         }

      });
      ++this.count;
   }

   boolean notifySuccess(@NonNull BluetoothDevice device) {
      this.handler.post(() -> {
         if (this.valueCallback != null) {
            try {
               ((DataSentCallback)this.valueCallback).onDataSent(device, new Data(this.data));
            } catch (Throwable var3) {
               Log.e(TAG, "Exception in Value callback", var3);
            }
         }

      });
      return super.notifySuccess(device);
   }

   boolean hasMore() {
      return !this.complete;
   }
}
