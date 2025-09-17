/*      */ package no.nordicsemi.android.ble;
/*      */ 
/*      */ import android.bluetooth.BluetoothDevice;
/*      */ import android.bluetooth.BluetoothGattCharacteristic;
/*      */ import android.bluetooth.BluetoothGattDescriptor;
/*      */ import android.os.ConditionVariable;
/*      */ import android.os.Handler;
/*      */ import android.os.Looper;
/*      */ import android.util.Log;
/*      */ import androidx.annotation.IntRange;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import no.nordicsemi.android.ble.callback.AfterCallback;
/*      */ import no.nordicsemi.android.ble.callback.BeforeCallback;
/*      */ import no.nordicsemi.android.ble.callback.FailCallback;
/*      */ import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
/*      */ import no.nordicsemi.android.ble.callback.SuccessCallback;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Request
/*      */ {
/*      */   protected RequestHandler requestHandler;
/*      */   protected CallbackHandler handler;
/*      */   final ConditionVariable syncLock;
/*      */   final Type type;
/*      */   final BluetoothGattCharacteristic characteristic;
/*      */   final BluetoothGattDescriptor descriptor;
/*      */   BeforeCallback beforeCallback;
/*      */   AfterCallback afterCallback;
/*      */   SuccessCallback successCallback;
/*      */   FailCallback failCallback;
/*      */   InvalidRequestCallback invalidRequestCallback;
/*      */   BeforeCallback internalBeforeCallback;
/*      */   SuccessCallback internalSuccessCallback;
/*      */   FailCallback internalFailCallback;
/*      */   boolean enqueued;
/*      */   boolean started;
/*      */   boolean finished;
/*   58 */   protected static final String TAG = Request.class.getSimpleName();
/*      */   
/*      */   enum Type {
/*   61 */     SET,
/*   62 */     CONNECT,
/*   63 */     DISCONNECT,
/*   64 */     CREATE_BOND,
/*   65 */     ENSURE_BOND,
/*   66 */     REMOVE_BOND,
/*   67 */     WRITE,
/*   68 */     NOTIFY,
/*   69 */     INDICATE,
/*   70 */     READ,
/*   71 */     WRITE_DESCRIPTOR,
/*   72 */     READ_DESCRIPTOR,
/*   73 */     BEGIN_RELIABLE_WRITE,
/*   74 */     EXECUTE_RELIABLE_WRITE,
/*   75 */     ABORT_RELIABLE_WRITE,
/*   76 */     ENABLE_NOTIFICATIONS,
/*   77 */     ENABLE_INDICATIONS,
/*   78 */     DISABLE_NOTIFICATIONS,
/*   79 */     DISABLE_INDICATIONS,
/*   80 */     WAIT_FOR_NOTIFICATION,
/*   81 */     WAIT_FOR_INDICATION,
/*   82 */     WAIT_FOR_READ,
/*   83 */     WAIT_FOR_WRITE,
/*   84 */     WAIT_FOR_CONDITION,
/*   85 */     SET_VALUE,
/*   86 */     SET_DESCRIPTOR_VALUE,
/*   87 */     READ_BATTERY_LEVEL,
/*      */     
/*   89 */     ENABLE_BATTERY_LEVEL_NOTIFICATIONS,
/*      */     
/*   91 */     DISABLE_BATTERY_LEVEL_NOTIFICATIONS,
/*      */     
/*   93 */     ENABLE_SERVICE_CHANGED_INDICATIONS,
/*   94 */     REQUEST_MTU,
/*   95 */     REQUEST_CONNECTION_PRIORITY,
/*   96 */     SET_PREFERRED_PHY,
/*   97 */     READ_PHY,
/*   98 */     READ_RSSI,
/*   99 */     REFRESH_CACHE,
/*  100 */     SLEEP;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Request(@NonNull Type type) {
/*  122 */     this.type = type;
/*  123 */     this.characteristic = null;
/*  124 */     this.descriptor = null;
/*  125 */     this.syncLock = new ConditionVariable(true);
/*      */   }
/*      */   
/*      */   Request(@NonNull Type type, @Nullable BluetoothGattCharacteristic characteristic) {
/*  129 */     this.type = type;
/*  130 */     this.characteristic = characteristic;
/*  131 */     this.descriptor = null;
/*  132 */     this.syncLock = new ConditionVariable(true);
/*      */   }
/*      */   
/*      */   Request(@NonNull Type type, @Nullable BluetoothGattDescriptor descriptor) {
/*  136 */     this.type = type;
/*  137 */     this.characteristic = null;
/*  138 */     this.descriptor = descriptor;
/*  139 */     this.syncLock = new ConditionVariable(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   Request setRequestHandler(@NonNull RequestHandler requestHandler) {
/*  149 */     this.requestHandler = requestHandler;
/*  150 */     if (this.handler == null) {
/*  151 */       this.handler = requestHandler;
/*      */     }
/*  153 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   public Request setHandler(@Nullable final Handler handler) {
/*  168 */     this.handler = new CallbackHandler()
/*      */       {
/*      */         public void post(@NonNull Runnable r) {
/*  171 */           if (handler != null) { handler.post(r); }
/*  172 */           else { r.run(); }
/*      */         
/*      */         }
/*      */         
/*      */         public void postDelayed(@NonNull Runnable r, long delayMillis) {
/*  177 */           if (handler != null) { handler.postDelayed(r, delayMillis); }
/*  178 */           else { Request.this.requestHandler.postDelayed(r, delayMillis); }
/*      */         
/*      */         }
/*      */         
/*      */         public void removeCallbacks(@NonNull Runnable r) {
/*  183 */           if (handler != null) { handler.removeCallbacks(r); }
/*  184 */           else { Request.this.requestHandler.removeCallbacks(r); }
/*      */            }
/*      */       };
/*  187 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static ConnectRequest connect(@NonNull BluetoothDevice device) {
/*  199 */     return new ConnectRequest(Type.CONNECT, device);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static DisconnectRequest disconnect() {
/*  210 */     return new DisconnectRequest(Type.DISCONNECT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static SimpleRequest createBond() {
/*  223 */     return new SimpleRequest(Type.CREATE_BOND);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static SimpleRequest ensureBond() {
/*  235 */     return new SimpleRequest(Type.ENSURE_BOND);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static SimpleRequest removeBond() {
/*  253 */     return new SimpleRequest(Type.REMOVE_BOND);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static ReadRequest newReadRequest(@Nullable BluetoothGattCharacteristic characteristic) {
/*  270 */     return new ReadRequest(Type.READ, characteristic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static WriteRequest newWriteRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value) {
/*  290 */     return new WriteRequest(Type.WRITE, characteristic, value, 0, 
/*  291 */         (value != null) ? value.length : 0, 
/*  292 */         (characteristic != null) ? 
/*  293 */         characteristic.getWriteType() : 
/*  294 */         2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static WriteRequest newWriteRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value, int writeType) {
/*  317 */     return new WriteRequest(Type.WRITE, characteristic, value, 0, 
/*  318 */         (value != null) ? value.length : 0, writeType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static WriteRequest newWriteRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
/*  342 */     return new WriteRequest(Type.WRITE, characteristic, value, offset, length, 
/*  343 */         (characteristic != null) ? 
/*  344 */         characteristic.getWriteType() : 
/*  345 */         2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static WriteRequest newWriteRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length, int writeType) {
/*  374 */     return new WriteRequest(Type.WRITE, characteristic, value, offset, length, writeType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static ReadRequest newReadRequest(@Nullable BluetoothGattDescriptor descriptor) {
/*  389 */     return new ReadRequest(Type.READ_DESCRIPTOR, descriptor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static WriteRequest newWriteRequest(@Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] value) {
/*  407 */     return new WriteRequest(Type.WRITE_DESCRIPTOR, descriptor, value, 0, 
/*  408 */         (value != null) ? value.length : 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static WriteRequest newWriteRequest(@Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
/*  430 */     return new WriteRequest(Type.WRITE_DESCRIPTOR, descriptor, value, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static ReliableWriteRequest newReliableWriteRequest() {
/*  442 */     return new ReliableWriteRequest();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static SimpleRequest newBeginReliableWriteRequest() {
/*  452 */     return new SimpleRequest(Type.BEGIN_RELIABLE_WRITE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static SimpleRequest newExecuteReliableWriteRequest() {
/*  464 */     return new SimpleRequest(Type.EXECUTE_RELIABLE_WRITE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static SimpleRequest newAbortReliableWriteRequest() {
/*  477 */     return new SimpleRequest(Type.ABORT_RELIABLE_WRITE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static WriteRequest newNotificationRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value) {
/*  494 */     return new WriteRequest(Type.NOTIFY, characteristic, value, 0, 
/*  495 */         (value != null) ? value.length : 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static WriteRequest newNotificationRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
/*  515 */     return new WriteRequest(Type.NOTIFY, characteristic, value, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static WriteRequest newIndicationRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value) {
/*  532 */     return new WriteRequest(Type.INDICATE, characteristic, value, 0, 
/*  533 */         (value != null) ? value.length : 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static WriteRequest newIndicationRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
/*  553 */     return new WriteRequest(Type.INDICATE, characteristic, value, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static WriteRequest newEnableNotificationsRequest(@Nullable BluetoothGattCharacteristic characteristic) {
/*  570 */     return new WriteRequest(Type.ENABLE_NOTIFICATIONS, characteristic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static WriteRequest newDisableNotificationsRequest(@Nullable BluetoothGattCharacteristic characteristic) {
/*  587 */     return new WriteRequest(Type.DISABLE_NOTIFICATIONS, characteristic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static WriteRequest newEnableIndicationsRequest(@Nullable BluetoothGattCharacteristic characteristic) {
/*  604 */     return new WriteRequest(Type.ENABLE_INDICATIONS, characteristic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static WriteRequest newDisableIndicationsRequest(@Nullable BluetoothGattCharacteristic characteristic) {
/*  621 */     return new WriteRequest(Type.DISABLE_INDICATIONS, characteristic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static WaitForValueChangedRequest newWaitForNotificationRequest(@Nullable BluetoothGattCharacteristic characteristic) {
/*  641 */     return new WaitForValueChangedRequest(Type.WAIT_FOR_NOTIFICATION, characteristic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static WaitForValueChangedRequest newWaitForIndicationRequest(@Nullable BluetoothGattCharacteristic characteristic) {
/*  661 */     return new WaitForValueChangedRequest(Type.WAIT_FOR_INDICATION, characteristic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static WaitForValueChangedRequest newWaitForWriteRequest(@Nullable BluetoothGattCharacteristic characteristic) {
/*  678 */     return new WaitForValueChangedRequest(Type.WAIT_FOR_WRITE, characteristic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static WaitForValueChangedRequest newWaitForWriteRequest(@Nullable BluetoothGattDescriptor descriptor) {
/*  694 */     return new WaitForValueChangedRequest(Type.WAIT_FOR_WRITE, descriptor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static WaitForReadRequest newWaitForReadRequest(@Nullable BluetoothGattCharacteristic characteristic) {
/*  711 */     return new WaitForReadRequest(Type.WAIT_FOR_READ, characteristic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static WaitForReadRequest newWaitForReadRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value) {
/*  731 */     return new WaitForReadRequest(Type.WAIT_FOR_READ, characteristic, value, 0, 
/*  732 */         (value != null) ? value.length : 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static WaitForReadRequest newWaitForReadRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
/*  755 */     return new WaitForReadRequest(Type.WAIT_FOR_READ, characteristic, value, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static WaitForReadRequest newWaitForReadRequest(@Nullable BluetoothGattDescriptor descriptor) {
/*  772 */     return new WaitForReadRequest(Type.WAIT_FOR_READ, descriptor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static WaitForReadRequest newWaitForReadRequest(@Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] value) {
/*  792 */     return new WaitForReadRequest(Type.WAIT_FOR_READ, descriptor, value, 0, 
/*  793 */         (value != null) ? value.length : 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static WaitForReadRequest newWaitForReadRequest(@Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
/*  816 */     return new WaitForReadRequest(Type.WAIT_FOR_READ, descriptor, value, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static <T> ConditionalWaitRequest<T> newConditionalWaitRequest(@NonNull ConditionalWaitRequest.Condition<T> condition, @Nullable T parameter) {
/*  829 */     return new ConditionalWaitRequest<>(Type.WAIT_FOR_CONDITION, condition, parameter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static SetValueRequest newSetValueRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value) {
/*  843 */     return new SetValueRequest(Type.SET_VALUE, characteristic, value, 0, 
/*  844 */         (value != null) ? value.length : 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static SetValueRequest newSetValueRequest(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
/*  861 */     return new SetValueRequest(Type.SET_VALUE, characteristic, value, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static SetValueRequest newSetValueRequest(@Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] value) {
/*  875 */     return new SetValueRequest(Type.SET_DESCRIPTOR_VALUE, descriptor, value, 0, 
/*  876 */         (value != null) ? value.length : 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static SetValueRequest newSetValueRequest(@Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
/*  893 */     return new SetValueRequest(Type.SET_DESCRIPTOR_VALUE, descriptor, value, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static ReadRequest newReadBatteryLevelRequest() {
/*  908 */     return new ReadRequest(Type.READ_BATTERY_LEVEL);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static WriteRequest newEnableBatteryLevelNotificationsRequest() {
/*  923 */     return new WriteRequest(Type.ENABLE_BATTERY_LEVEL_NOTIFICATIONS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static WriteRequest newDisableBatteryLevelNotificationsRequest() {
/*  937 */     return new WriteRequest(Type.DISABLE_BATTERY_LEVEL_NOTIFICATIONS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   static WriteRequest newEnableServiceChangedIndicationsRequest() {
/*  951 */     return new WriteRequest(Type.ENABLE_SERVICE_CHANGED_INDICATIONS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static MtuRequest newMtuRequest(@IntRange(from = 23L, to = 517L) int mtu) {
/*  968 */     return new MtuRequest(Type.REQUEST_MTU, mtu);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static ConnectionPriorityRequest newConnectionPriorityRequest(int priority) {
/*  997 */     return new ConnectionPriorityRequest(Type.REQUEST_CONNECTION_PRIORITY, priority);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static PhyRequest newSetPreferredPhyRequest(int txPhy, int rxPhy, int phyOptions) {
/* 1025 */     return new PhyRequest(Type.SET_PREFERRED_PHY, txPhy, rxPhy, phyOptions);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static PhyRequest newReadPhyRequest() {
/* 1042 */     return new PhyRequest(Type.READ_PHY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static ReadRssiRequest newReadRssiRequest() {
/* 1055 */     return new ReadRssiRequest(Type.READ_RSSI);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static SimpleRequest newRefreshCacheRequest() {
/* 1078 */     return new SimpleRequest(Type.REFRESH_CACHE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public static SleepRequest newSleepRequest(@IntRange(from = 0L) long delay) {
/* 1092 */     return new SleepRequest(Type.SLEEP, delay);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   public Request done(@NonNull SuccessCallback callback) {
/* 1105 */     this.successCallback = callback;
/* 1106 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   public Request fail(@NonNull FailCallback callback) {
/* 1124 */     this.failCallback = callback;
/* 1125 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void internalBefore(@NonNull BeforeCallback callback) {
/* 1134 */     this.internalBeforeCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void internalSuccess(@NonNull SuccessCallback callback) {
/* 1144 */     this.internalSuccessCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void internalFail(@NonNull FailCallback callback) {
/* 1155 */     this.internalFailCallback = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   public Request invalid(@NonNull InvalidRequestCallback callback) {
/* 1169 */     this.invalidRequestCallback = callback;
/* 1170 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   public Request before(@NonNull BeforeCallback callback) {
/* 1181 */     this.beforeCallback = callback;
/* 1182 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   public Request then(@NonNull AfterCallback callback) {
/* 1201 */     this.afterCallback = callback;
/* 1202 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enqueue() {
/* 1209 */     this.requestHandler.enqueue(this);
/*      */   }
/*      */   
/*      */   void notifyStarted(@NonNull BluetoothDevice device) {
/* 1213 */     if (!this.started) {
/* 1214 */       this.started = true;
/*      */       
/* 1216 */       if (this.internalBeforeCallback != null)
/* 1217 */         this.internalBeforeCallback.onRequestStarted(device); 
/* 1218 */       this.handler.post(() -> {
/*      */             if (this.beforeCallback != null) {
/*      */               try {
/*      */                 this.beforeCallback.onRequestStarted(device);
/* 1222 */               } catch (Throwable t) {
/*      */                 Log.e(TAG, "Exception in Before callback", t);
/*      */               } 
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */   
/*      */   boolean notifySuccess(@NonNull BluetoothDevice device) {
/* 1231 */     if (!this.finished) {
/* 1232 */       this.finished = true;
/*      */       
/* 1234 */       if (this.internalSuccessCallback != null)
/* 1235 */         this.internalSuccessCallback.onRequestCompleted(device); 
/* 1236 */       this.handler.post(() -> {
/*      */             if (this.successCallback != null) {
/*      */               try {
/*      */                 this.successCallback.onRequestCompleted(device);
/* 1240 */               } catch (Throwable t) {
/*      */                 Log.e(TAG, "Exception in Success callback", t);
/*      */               } 
/*      */             }
/*      */             if (this.afterCallback != null) {
/*      */               try {
/*      */                 this.afterCallback.onRequestFinished(device);
/* 1247 */               } catch (Throwable t) {
/*      */                 Log.e(TAG, "Exception in After callback", t);
/*      */               } 
/*      */             }
/*      */           });
/* 1252 */       return true;
/*      */     } 
/* 1254 */     return false;
/*      */   }
/*      */   
/*      */   void notifyFail(@NonNull BluetoothDevice device, int status) {
/* 1258 */     if (!this.finished) {
/* 1259 */       this.finished = true;
/*      */       
/* 1261 */       if (this.internalFailCallback != null)
/* 1262 */         this.internalFailCallback.onRequestFailed(device, status); 
/* 1263 */       this.handler.post(() -> {
/*      */             if (this.failCallback != null) {
/*      */               try {
/*      */                 this.failCallback.onRequestFailed(device, status);
/* 1267 */               } catch (Throwable t) {
/*      */                 Log.e(TAG, "Exception in Fail callback", t);
/*      */               } 
/*      */             }
/*      */             if (this.afterCallback != null) {
/*      */               try {
/*      */                 this.afterCallback.onRequestFinished(device);
/* 1274 */               } catch (Throwable t) {
/*      */                 Log.e(TAG, "Exception in After callback", t);
/*      */               } 
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */   
/*      */   void notifyInvalidRequest() {
/* 1283 */     if (!this.finished) {
/* 1284 */       this.finished = true;
/*      */       
/* 1286 */       this.handler.post(() -> {
/*      */             if (this.invalidRequestCallback != null) {
/*      */               try {
/*      */                 this.invalidRequestCallback.onInvalidRequest();
/* 1290 */               } catch (Throwable t) {
/*      */                 Log.e(TAG, "Exception in Invalid Request callback", t);
/*      */               } 
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void assertNotMainThread() throws IllegalStateException {
/* 1304 */     if (Looper.myLooper() == Looper.getMainLooper())
/* 1305 */       throw new IllegalStateException("Cannot execute synchronous operation from the UI thread."); 
/*      */   }
/*      */   
/*      */   final class RequestCallback
/*      */     implements SuccessCallback, FailCallback, InvalidRequestCallback {
/*      */     static final int REASON_REQUEST_INVALID = -1000000;
/* 1311 */     int status = 0;
/*      */ 
/*      */     
/*      */     public void onRequestCompleted(@NonNull BluetoothDevice device) {
/* 1315 */       Request.this.syncLock.open();
/*      */     }
/*      */ 
/*      */     
/*      */     public void onRequestFailed(@NonNull BluetoothDevice device, int status) {
/* 1320 */       this.status = status;
/* 1321 */       Request.this.syncLock.open();
/*      */     }
/*      */ 
/*      */     
/*      */     public void onInvalidRequest() {
/* 1326 */       this.status = -1000000;
/* 1327 */       Request.this.syncLock.open();
/*      */     }
/*      */ 
/*      */     
/*      */     boolean isSuccess() {
/* 1332 */       return (this.status == 0);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\Request.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */