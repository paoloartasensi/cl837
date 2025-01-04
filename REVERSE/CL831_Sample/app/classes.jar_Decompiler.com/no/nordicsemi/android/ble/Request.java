package no.nordicsemi.android.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import no.nordicsemi.android.ble.callback.AfterCallback;
import no.nordicsemi.android.ble.callback.BeforeCallback;
import no.nordicsemi.android.ble.callback.FailCallback;
import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
import no.nordicsemi.android.ble.callback.SuccessCallback;

public abstract class Request {
   protected static final String TAG = Request.class.getSimpleName();
   protected RequestHandler requestHandler;
   protected CallbackHandler handler;
   final ConditionVariable syncLock;
   final Request.Type type;
   final BluetoothGattCharacteristic characteristic;
   final BluetoothGattDescriptor descriptor;
   BeforeCallback beforeCallback;
   AfterCallback afterCallback;
   SuccessCallback successCallback;
   FailCallback failCallback;
   InvalidRequestCallback invalidRequestCallback;
   BeforeCallback internalBeforeCallback;
   SuccessCallback internalSuccessCallback;
   FailCallback internalFailCallback;
   boolean enqueued;
   boolean started;
   boolean finished;

   Request(@NonNull Request.Type type) {
      this.type = type;
      this.characteristic = null;
      this.descriptor = null;
      this.syncLock = new ConditionVariable(true);
   }

   Request(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic) {
      this.type = type;
      this.characteristic = characteristic;
      this.descriptor = null;
      this.syncLock = new ConditionVariable(true);
   }

   Request(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor) {
      this.type = type;
      this.characteristic = null;
      this.descriptor = descriptor;
      this.syncLock = new ConditionVariable(true);
   }

   @NonNull
   Request setRequestHandler(@NonNull RequestHandler requestHandler) {
      this.requestHandler = requestHandler;
      if (this.handler == null) {
         this.handler = requestHandler;
      }

      return this;
   }

   @NonNull
   public Request setHandler(@Nullable final Handler handler) {
      this.handler = new CallbackHandler() {
         public void post(@NonNull Runnable r) {
            if (handler != null) {
               handler.post(r);
            } else {
               r.run();
            }

         }

         public void postDelayed(@NonNull Runnable r, long delayMillis) {
            if (handler != null) {
               handler.postDelayed(r, delayMillis);
            } else {
               Request.this.requestHandler.postDelayed(r, delayMillis);
            }

         }

         public void removeCallbacks(@NonNull Runnable r) {
            if (handler != null) {
               handler.removeCallbacks(r);
            } else {
               Request.this.requestHandler.removeCallbacks(r);
            }

         }
      };
      return this;
   }

   @NonNull
   static ConnectRequest connect(@NonNull BluetoothDevice device) {
      return new ConnectRequest(Request.Type.CONNECT, device);
   }

   @NonNull
   static DisconnectRequest disconnect() {
      return new DisconnectRequest(Request.Type.DISCONNECT);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static SimpleRequest createBond() {
      return new SimpleRequest(Request.Type.CREATE_BOND);
   }

   @NonNull
   static SimpleRequest ensureBond() {
      return new SimpleRequest(Request.Type.ENSURE_BOND);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static SimpleRequest removeBond() {
      return new SimpleRequest(Request.Type.REMOVE_BOND);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static ReadRequest newReadRequest(@Nullable BluetoothGattCharacteristic characteristic) {
      return new ReadRequest(Request.Type.READ, characteristic);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static WriteRequest newWriteRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value) {
      return new WriteRequest(Request.Type.WRITE, characteristic, value, 0, value != null ? value.length : 0, characteristic != null ? characteristic.getWriteType() : 2);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static WriteRequest newWriteRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value, int writeType) {
      return new WriteRequest(Request.Type.WRITE, characteristic, value, 0, value != null ? value.length : 0, writeType);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static WriteRequest newWriteRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
      return new WriteRequest(Request.Type.WRITE, characteristic, value, offset, length, characteristic != null ? characteristic.getWriteType() : 2);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static WriteRequest newWriteRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length, int writeType) {
      return new WriteRequest(Request.Type.WRITE, characteristic, value, offset, length, writeType);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static ReadRequest newReadRequest(@Nullable BluetoothGattDescriptor descriptor) {
      return new ReadRequest(Request.Type.READ_DESCRIPTOR, descriptor);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static WriteRequest newWriteRequest(@Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] value) {
      return new WriteRequest(Request.Type.WRITE_DESCRIPTOR, descriptor, value, 0, value != null ? value.length : 0);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static WriteRequest newWriteRequest(@Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
      return new WriteRequest(Request.Type.WRITE_DESCRIPTOR, descriptor, value, offset, length);
   }

   @NonNull
   static ReliableWriteRequest newReliableWriteRequest() {
      return new ReliableWriteRequest();
   }

   @NonNull
   static SimpleRequest newBeginReliableWriteRequest() {
      return new SimpleRequest(Request.Type.BEGIN_RELIABLE_WRITE);
   }

   @NonNull
   static SimpleRequest newExecuteReliableWriteRequest() {
      return new SimpleRequest(Request.Type.EXECUTE_RELIABLE_WRITE);
   }

   @NonNull
   static SimpleRequest newAbortReliableWriteRequest() {
      return new SimpleRequest(Request.Type.ABORT_RELIABLE_WRITE);
   }

   @NonNull
   static WriteRequest newNotificationRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value) {
      return new WriteRequest(Request.Type.NOTIFY, characteristic, value, 0, value != null ? value.length : 0);
   }

   @NonNull
   static WriteRequest newNotificationRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
      return new WriteRequest(Request.Type.NOTIFY, characteristic, value, offset, length);
   }

   @NonNull
   static WriteRequest newIndicationRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value) {
      return new WriteRequest(Request.Type.INDICATE, characteristic, value, 0, value != null ? value.length : 0);
   }

   @NonNull
   static WriteRequest newIndicationRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
      return new WriteRequest(Request.Type.INDICATE, characteristic, value, offset, length);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static WriteRequest newEnableNotificationsRequest(@Nullable BluetoothGattCharacteristic characteristic) {
      return new WriteRequest(Request.Type.ENABLE_NOTIFICATIONS, characteristic);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static WriteRequest newDisableNotificationsRequest(@Nullable BluetoothGattCharacteristic characteristic) {
      return new WriteRequest(Request.Type.DISABLE_NOTIFICATIONS, characteristic);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static WriteRequest newEnableIndicationsRequest(@Nullable BluetoothGattCharacteristic characteristic) {
      return new WriteRequest(Request.Type.ENABLE_INDICATIONS, characteristic);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static WriteRequest newDisableIndicationsRequest(@Nullable BluetoothGattCharacteristic characteristic) {
      return new WriteRequest(Request.Type.DISABLE_INDICATIONS, characteristic);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static WaitForValueChangedRequest newWaitForNotificationRequest(@Nullable BluetoothGattCharacteristic characteristic) {
      return new WaitForValueChangedRequest(Request.Type.WAIT_FOR_NOTIFICATION, characteristic);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static WaitForValueChangedRequest newWaitForIndicationRequest(@Nullable BluetoothGattCharacteristic characteristic) {
      return new WaitForValueChangedRequest(Request.Type.WAIT_FOR_INDICATION, characteristic);
   }

   @NonNull
   static WaitForValueChangedRequest newWaitForWriteRequest(@Nullable BluetoothGattCharacteristic characteristic) {
      return new WaitForValueChangedRequest(Request.Type.WAIT_FOR_WRITE, characteristic);
   }

   @NonNull
   static WaitForValueChangedRequest newWaitForWriteRequest(@Nullable BluetoothGattDescriptor descriptor) {
      return new WaitForValueChangedRequest(Request.Type.WAIT_FOR_WRITE, descriptor);
   }

   @NonNull
   static WaitForReadRequest newWaitForReadRequest(@Nullable BluetoothGattCharacteristic characteristic) {
      return new WaitForReadRequest(Request.Type.WAIT_FOR_READ, characteristic);
   }

   @NonNull
   static WaitForReadRequest newWaitForReadRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value) {
      return new WaitForReadRequest(Request.Type.WAIT_FOR_READ, characteristic, value, 0, value != null ? value.length : 0);
   }

   @NonNull
   static WaitForReadRequest newWaitForReadRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
      return new WaitForReadRequest(Request.Type.WAIT_FOR_READ, characteristic, value, offset, length);
   }

   @NonNull
   static WaitForReadRequest newWaitForReadRequest(@Nullable BluetoothGattDescriptor descriptor) {
      return new WaitForReadRequest(Request.Type.WAIT_FOR_READ, descriptor);
   }

   @NonNull
   static WaitForReadRequest newWaitForReadRequest(@Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] value) {
      return new WaitForReadRequest(Request.Type.WAIT_FOR_READ, descriptor, value, 0, value != null ? value.length : 0);
   }

   @NonNull
   static WaitForReadRequest newWaitForReadRequest(@Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
      return new WaitForReadRequest(Request.Type.WAIT_FOR_READ, descriptor, value, offset, length);
   }

   @NonNull
   static <T> ConditionalWaitRequest<T> newConditionalWaitRequest(@NonNull ConditionalWaitRequest.Condition<T> condition, @Nullable T parameter) {
      return new ConditionalWaitRequest(Request.Type.WAIT_FOR_CONDITION, condition, parameter);
   }

   @NonNull
   static SetValueRequest newSetValueRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value) {
      return new SetValueRequest(Request.Type.SET_VALUE, characteristic, value, 0, value != null ? value.length : 0);
   }

   @NonNull
   static SetValueRequest newSetValueRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
      return new SetValueRequest(Request.Type.SET_VALUE, characteristic, value, offset, length);
   }

   @NonNull
   static SetValueRequest newSetValueRequest(@Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] value) {
      return new SetValueRequest(Request.Type.SET_DESCRIPTOR_VALUE, descriptor, value, 0, value != null ? value.length : 0);
   }

   @NonNull
   static SetValueRequest newSetValueRequest(@Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
      return new SetValueRequest(Request.Type.SET_DESCRIPTOR_VALUE, descriptor, value, offset, length);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static ReadRequest newReadBatteryLevelRequest() {
      return new ReadRequest(Request.Type.READ_BATTERY_LEVEL);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static WriteRequest newEnableBatteryLevelNotificationsRequest() {
      return new WriteRequest(Request.Type.ENABLE_BATTERY_LEVEL_NOTIFICATIONS);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static WriteRequest newDisableBatteryLevelNotificationsRequest() {
      return new WriteRequest(Request.Type.DISABLE_BATTERY_LEVEL_NOTIFICATIONS);
   }

   @NonNull
   static WriteRequest newEnableServiceChangedIndicationsRequest() {
      return new WriteRequest(Request.Type.ENABLE_SERVICE_CHANGED_INDICATIONS);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static MtuRequest newMtuRequest(@IntRange(from = 23L,to = 517L) int mtu) {
      return new MtuRequest(Request.Type.REQUEST_MTU, mtu);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static ConnectionPriorityRequest newConnectionPriorityRequest(int priority) {
      return new ConnectionPriorityRequest(Request.Type.REQUEST_CONNECTION_PRIORITY, priority);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static PhyRequest newSetPreferredPhyRequest(int txPhy, int rxPhy, int phyOptions) {
      return new PhyRequest(Request.Type.SET_PREFERRED_PHY, txPhy, rxPhy, phyOptions);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static PhyRequest newReadPhyRequest() {
      return new PhyRequest(Request.Type.READ_PHY);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static ReadRssiRequest newReadRssiRequest() {
      return new ReadRssiRequest(Request.Type.READ_RSSI);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static SimpleRequest newRefreshCacheRequest() {
      return new SimpleRequest(Request.Type.REFRESH_CACHE);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public static SleepRequest newSleepRequest(@IntRange(from = 0L) long delay) {
      return new SleepRequest(Request.Type.SLEEP, delay);
   }

   @NonNull
   public Request done(@NonNull SuccessCallback callback) {
      this.successCallback = callback;
      return this;
   }

   @NonNull
   public Request fail(@NonNull FailCallback callback) {
      this.failCallback = callback;
      return this;
   }

   void internalBefore(@NonNull BeforeCallback callback) {
      this.internalBeforeCallback = callback;
   }

   void internalSuccess(@NonNull SuccessCallback callback) {
      this.internalSuccessCallback = callback;
   }

   void internalFail(@NonNull FailCallback callback) {
      this.internalFailCallback = callback;
   }

   @NonNull
   public Request invalid(@NonNull InvalidRequestCallback callback) {
      this.invalidRequestCallback = callback;
      return this;
   }

   @NonNull
   public Request before(@NonNull BeforeCallback callback) {
      this.beforeCallback = callback;
      return this;
   }

   @NonNull
   public Request then(@NonNull AfterCallback callback) {
      this.afterCallback = callback;
      return this;
   }

   public void enqueue() {
      this.requestHandler.enqueue(this);
   }

   void notifyStarted(@NonNull BluetoothDevice device) {
      if (!this.started) {
         this.started = true;
         if (this.internalBeforeCallback != null) {
            this.internalBeforeCallback.onRequestStarted(device);
         }

         this.handler.post(() -> {
            if (this.beforeCallback != null) {
               try {
                  this.beforeCallback.onRequestStarted(device);
               } catch (Throwable var3) {
                  Log.e(TAG, "Exception in Before callback", var3);
               }
            }

         });
      }

   }

   boolean notifySuccess(@NonNull BluetoothDevice device) {
      if (!this.finished) {
         this.finished = true;
         if (this.internalSuccessCallback != null) {
            this.internalSuccessCallback.onRequestCompleted(device);
         }

         this.handler.post(() -> {
            if (this.successCallback != null) {
               try {
                  this.successCallback.onRequestCompleted(device);
               } catch (Throwable var4) {
                  Log.e(TAG, "Exception in Success callback", var4);
               }
            }

            if (this.afterCallback != null) {
               try {
                  this.afterCallback.onRequestFinished(device);
               } catch (Throwable var3) {
                  Log.e(TAG, "Exception in After callback", var3);
               }
            }

         });
         return true;
      } else {
         return false;
      }
   }

   void notifyFail(@NonNull BluetoothDevice device, int status) {
      if (!this.finished) {
         this.finished = true;
         if (this.internalFailCallback != null) {
            this.internalFailCallback.onRequestFailed(device, status);
         }

         this.handler.post(() -> {
            if (this.failCallback != null) {
               try {
                  this.failCallback.onRequestFailed(device, status);
               } catch (Throwable var5) {
                  Log.e(TAG, "Exception in Fail callback", var5);
               }
            }

            if (this.afterCallback != null) {
               try {
                  this.afterCallback.onRequestFinished(device);
               } catch (Throwable var4) {
                  Log.e(TAG, "Exception in After callback", var4);
               }
            }

         });
      }

   }

   void notifyInvalidRequest() {
      if (!this.finished) {
         this.finished = true;
         this.handler.post(() -> {
            if (this.invalidRequestCallback != null) {
               try {
                  this.invalidRequestCallback.onInvalidRequest();
               } catch (Throwable var2) {
                  Log.e(TAG, "Exception in Invalid Request callback", var2);
               }
            }

         });
      }

   }

   static void assertNotMainThread() throws IllegalStateException {
      if (Looper.myLooper() == Looper.getMainLooper()) {
         throw new IllegalStateException("Cannot execute synchronous operation from the UI thread.");
      }
   }

   final class RequestCallback implements SuccessCallback, FailCallback, InvalidRequestCallback {
      static final int REASON_REQUEST_INVALID = -1000000;
      int status = 0;

      public void onRequestCompleted(@NonNull BluetoothDevice device) {
         Request.this.syncLock.open();
      }

      public void onRequestFailed(@NonNull BluetoothDevice device, int status) {
         this.status = status;
         Request.this.syncLock.open();
      }

      public void onInvalidRequest() {
         this.status = -1000000;
         Request.this.syncLock.open();
      }

      boolean isSuccess() {
         return this.status == 0;
      }
   }

   static enum Type {
      SET,
      CONNECT,
      DISCONNECT,
      CREATE_BOND,
      ENSURE_BOND,
      REMOVE_BOND,
      WRITE,
      NOTIFY,
      INDICATE,
      READ,
      WRITE_DESCRIPTOR,
      READ_DESCRIPTOR,
      BEGIN_RELIABLE_WRITE,
      EXECUTE_RELIABLE_WRITE,
      ABORT_RELIABLE_WRITE,
      ENABLE_NOTIFICATIONS,
      ENABLE_INDICATIONS,
      DISABLE_NOTIFICATIONS,
      DISABLE_INDICATIONS,
      WAIT_FOR_NOTIFICATION,
      WAIT_FOR_INDICATION,
      WAIT_FOR_READ,
      WAIT_FOR_WRITE,
      WAIT_FOR_CONDITION,
      SET_VALUE,
      SET_DESCRIPTOR_VALUE,
      /** @deprecated */
      @Deprecated
      READ_BATTERY_LEVEL,
      /** @deprecated */
      @Deprecated
      ENABLE_BATTERY_LEVEL_NOTIFICATIONS,
      /** @deprecated */
      @Deprecated
      DISABLE_BATTERY_LEVEL_NOTIFICATIONS,
      ENABLE_SERVICE_CHANGED_INDICATIONS,
      REQUEST_MTU,
      REQUEST_CONNECTION_PRIORITY,
      SET_PREFERRED_PHY,
      READ_PHY,
      READ_RSSI,
      REFRESH_CACHE,
      SLEEP;
   }
}
