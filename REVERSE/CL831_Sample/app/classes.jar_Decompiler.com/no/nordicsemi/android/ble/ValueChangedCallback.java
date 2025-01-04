package no.nordicsemi.android.ble;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import no.nordicsemi.android.ble.callback.ClosedCallback;
import no.nordicsemi.android.ble.callback.DataReceivedCallback;
import no.nordicsemi.android.ble.callback.ReadProgressCallback;
import no.nordicsemi.android.ble.data.Data;
import no.nordicsemi.android.ble.data.DataFilter;
import no.nordicsemi.android.ble.data.DataMerger;
import no.nordicsemi.android.ble.data.DataStream;
import no.nordicsemi.android.ble.data.PacketFilter;

public class ValueChangedCallback {
   private static final String TAG = ValueChangedCallback.class.getSimpleName();
   private ClosedCallback closedCallback;
   private ReadProgressCallback progressCallback;
   private DataReceivedCallback valueCallback;
   private DataMerger dataMerger;
   private DataStream buffer;
   private DataFilter filter;
   private PacketFilter packetFilter;
   private CallbackHandler handler;
   private int count = 0;

   ValueChangedCallback(CallbackHandler handler) {
      this.handler = handler;
   }

   @NonNull
   public ValueChangedCallback setHandler(@Nullable final Handler handler) {
      this.handler = new CallbackHandler() {
         public void post(@NonNull Runnable r) {
            if (handler != null) {
               handler.post(r);
            } else {
               r.run();
            }

         }

         public void postDelayed(@NonNull Runnable r, long delayMillis) {
         }

         public void removeCallbacks(@NonNull Runnable r) {
         }
      };
      return this;
   }

   @NonNull
   public ValueChangedCallback with(@NonNull DataReceivedCallback callback) {
      this.valueCallback = callback;
      return this;
   }

   @NonNull
   public ValueChangedCallback filter(@NonNull DataFilter filter) {
      this.filter = filter;
      return this;
   }

   @NonNull
   public ValueChangedCallback filterPacket(@NonNull PacketFilter filter) {
      this.packetFilter = filter;
      return this;
   }

   @NonNull
   public ValueChangedCallback merge(@NonNull DataMerger merger) {
      this.dataMerger = merger;
      this.progressCallback = null;
      return this;
   }

   @NonNull
   public ValueChangedCallback merge(@NonNull DataMerger merger, @NonNull ReadProgressCallback callback) {
      this.dataMerger = merger;
      this.progressCallback = callback;
      return this;
   }

   @NonNull
   public ValueChangedCallback then(@NonNull ClosedCallback callback) {
      this.closedCallback = callback;
      return this;
   }

   boolean matches(byte[] packet) {
      return this.filter == null || this.filter.filter(packet);
   }

   void notifyValueChanged(@NonNull BluetoothDevice device, @Nullable byte[] value) {
      DataReceivedCallback valueCallback = this.valueCallback;
      if (valueCallback != null) {
         if (this.dataMerger != null || this.packetFilter != null && !this.packetFilter.filter(value)) {
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
         } else {
            Data data = new Data(value);
            this.handler.post(() -> {
               try {
                  valueCallback.onDataReceived(device, data);
               } catch (Throwable var4) {
                  Log.e(TAG, "Exception in Value callback", var4);
               }

            });
         }

      }
   }

   void notifyClosed() {
      if (this.closedCallback != null) {
         try {
            this.closedCallback.onClosed();
         } catch (Throwable var2) {
            Log.e(TAG, "Exception in Closed callback", var2);
         }
      }

      this.free();
   }

   private void free() {
      this.closedCallback = null;
      this.valueCallback = null;
      this.dataMerger = null;
      this.progressCallback = null;
      this.filter = null;
      this.packetFilter = null;
      this.buffer = null;
      this.count = 0;
   }
}
