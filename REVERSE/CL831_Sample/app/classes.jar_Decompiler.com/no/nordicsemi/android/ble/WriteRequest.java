package no.nordicsemi.android.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Arrays;
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

public final class WriteRequest extends SimpleValueRequest<DataSentCallback> implements Operation {
   private static final DataSplitter MTU_SPLITTER = new DefaultMtuSplitter();
   private WriteProgressCallback progressCallback;
   private DataSplitter dataSplitter;
   private final byte[] data;
   private final int writeType;
   private byte[] currentChunk;
   private byte[] nextChunk;
   private int count;
   private boolean complete;

   WriteRequest(@NonNull Request.Type type) {
      this(type, (BluetoothGattCharacteristic)null);
   }

   WriteRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic) {
      super(type, characteristic);
      this.count = 0;
      this.complete = false;
      this.data = null;
      this.writeType = 0;
      this.complete = true;
   }

   WriteRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] data, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length, int writeType) {
      super(type, characteristic);
      this.count = 0;
      this.complete = false;
      this.data = Bytes.copy(data, offset, length);
      this.writeType = writeType;
   }

   WriteRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] data, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
      super(type, characteristic);
      this.count = 0;
      this.complete = false;
      this.data = Bytes.copy(data, offset, length);
      this.writeType = 0;
   }

   WriteRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] data, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
      super(type, descriptor);
      this.count = 0;
      this.complete = false;
      this.data = Bytes.copy(data, offset, length);
      this.writeType = 2;
   }

   @NonNull
   WriteRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
      super.setRequestHandler(requestHandler);
      return this;
   }

   @NonNull
   public WriteRequest setHandler(@Nullable Handler handler) {
      super.setHandler(handler);
      return this;
   }

   @NonNull
   public WriteRequest done(@NonNull SuccessCallback callback) {
      super.done(callback);
      return this;
   }

   @NonNull
   public WriteRequest fail(@NonNull FailCallback callback) {
      super.fail(callback);
      return this;
   }

   @NonNull
   public WriteRequest invalid(@NonNull InvalidRequestCallback callback) {
      super.invalid(callback);
      return this;
   }

   @NonNull
   public WriteRequest before(@NonNull BeforeCallback callback) {
      super.before(callback);
      return this;
   }

   @NonNull
   public WriteRequest then(@NonNull AfterCallback callback) {
      super.then(callback);
      return this;
   }

   @NonNull
   public WriteRequest with(@NonNull DataSentCallback callback) {
      super.with(callback);
      return this;
   }

   @NonNull
   public WriteRequest split(@NonNull DataSplitter splitter) {
      this.dataSplitter = splitter;
      this.progressCallback = null;
      return this;
   }

   @NonNull
   public WriteRequest split(@NonNull DataSplitter splitter, @NonNull WriteProgressCallback callback) {
      this.dataSplitter = splitter;
      this.progressCallback = callback;
      return this;
   }

   @NonNull
   public WriteRequest split() {
      this.dataSplitter = MTU_SPLITTER;
      this.progressCallback = null;
      return this;
   }

   @NonNull
   public WriteRequest split(@NonNull WriteProgressCallback callback) {
      this.dataSplitter = MTU_SPLITTER;
      this.progressCallback = callback;
      return this;
   }

   void forceSplit() {
      if (this.dataSplitter == null) {
         this.split();
      }

   }

   @NonNull
   byte[] getData(@IntRange(from = 23L,to = 517L) int mtu) {
      if (this.dataSplitter != null && this.data != null) {
         int maxLength = this.writeType != 4 ? mtu - 3 : mtu - 12;
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

         this.currentChunk = chunk;
         return chunk != null ? chunk : new byte[0];
      } else {
         this.complete = true;
         this.currentChunk = this.data;
         return this.data != null ? this.data : new byte[0];
      }
   }

   boolean notifyPacketSent(@NonNull BluetoothDevice device, @Nullable byte[] data) {
      this.handler.post(() -> {
         if (this.progressCallback != null) {
            try {
               this.progressCallback.onPacketSent(device, this.currentChunk, this.count);
            } catch (Throwable var3) {
               Log.e(TAG, "Exception in Progress callback", var3);
            }
         }

      });
      ++this.count;
      if (this.complete) {
         this.handler.post(() -> {
            if (this.valueCallback != null) {
               try {
                  ((DataSentCallback)this.valueCallback).onDataSent(device, new Data(this.data));
               } catch (Throwable var3) {
                  Log.e(TAG, "Exception in Value callback", var3);
               }
            }

         });
      }

      return this.writeType == 2 ? Arrays.equals(data, this.currentChunk) : true;
   }

   boolean hasMore() {
      return !this.complete;
   }

   int getWriteType() {
      return this.writeType;
   }
}
