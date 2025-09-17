/*     */ package no.nordicsemi.android.ble;
/*     */ 
/*     */ import android.bluetooth.BluetoothDevice;
/*     */ import android.os.Handler;
/*     */ import androidx.annotation.IntRange;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.annotation.RequiresApi;
/*     */ import no.nordicsemi.android.ble.callback.AfterCallback;
/*     */ import no.nordicsemi.android.ble.callback.BeforeCallback;
/*     */ import no.nordicsemi.android.ble.callback.ConnectionParametersUpdatedCallback;
/*     */ import no.nordicsemi.android.ble.callback.ConnectionPriorityCallback;
/*     */ import no.nordicsemi.android.ble.callback.FailCallback;
/*     */ import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
/*     */ import no.nordicsemi.android.ble.callback.SuccessCallback;
/*     */ import no.nordicsemi.android.ble.exception.BluetoothDisabledException;
/*     */ import no.nordicsemi.android.ble.exception.DeviceDisconnectedException;
/*     */ import no.nordicsemi.android.ble.exception.InvalidRequestException;
/*     */ import no.nordicsemi.android.ble.exception.RequestFailedException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ConnectionPriorityRequest
/*     */   extends SimpleValueRequest<ConnectionParametersUpdatedCallback>
/*     */   implements Operation
/*     */ {
/*     */   public static final int CONNECTION_PRIORITY_BALANCED = 0;
/*     */   public static final int CONNECTION_PRIORITY_HIGH = 1;
/*     */   public static final int CONNECTION_PRIORITY_LOW_POWER = 2;
/*     */   private final int value;
/*     */   
/*     */   ConnectionPriorityRequest(@NonNull Request.Type type, int priority) {
/*  87 */     super(type);
/*  88 */     if (priority < 0 || priority > 2)
/*  89 */       priority = 0; 
/*  90 */     this.value = priority;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   ConnectionPriorityRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
/*  96 */     super.setRequestHandler(requestHandler);
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ConnectionPriorityRequest setHandler(@Nullable Handler handler) {
/* 103 */     super.setHandler(handler);
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ConnectionPriorityRequest done(@NonNull SuccessCallback callback) {
/* 110 */     super.done(callback);
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ConnectionPriorityRequest fail(@NonNull FailCallback callback) {
/* 117 */     super.fail(callback);
/* 118 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ConnectionPriorityRequest invalid(@NonNull InvalidRequestCallback callback) {
/* 124 */     super.invalid(callback);
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ConnectionPriorityRequest before(@NonNull BeforeCallback callback) {
/* 131 */     super.before(callback);
/* 132 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ConnectionPriorityRequest then(@NonNull AfterCallback callback) {
/* 138 */     super.then(callback);
/* 139 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @RequiresApi(26)
/*     */   @NonNull
/*     */   public ConnectionPriorityRequest with(@NonNull ConnectionPriorityCallback callback) {
/* 156 */     super.with(callback);
/* 157 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequiresApi(26)
/*     */   @NonNull
/*     */   public ConnectionPriorityRequest with(@NonNull ConnectionParametersUpdatedCallback callback) {
/* 165 */     super.with(callback);
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequiresApi(26)
/*     */   @NonNull
/*     */   public <E extends ConnectionParametersUpdatedCallback> E await(@NonNull Class<E> responseClass) throws RequestFailedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException {
/* 176 */     return (E)super.<ConnectionParametersUpdatedCallback>await(responseClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequiresApi(26)
/*     */   @NonNull
/*     */   public <E extends ConnectionParametersUpdatedCallback> E await(@NonNull E response) throws RequestFailedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException {
/* 186 */     return (E)super.<ConnectionParametersUpdatedCallback>await((ConnectionParametersUpdatedCallback)response);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequiresApi(api = 26)
/*     */   void notifyConnectionPriorityChanged(@NonNull BluetoothDevice device, @IntRange(from = 6L, to = 3200L) int interval, @IntRange(from = 0L, to = 499L) int latency, @IntRange(from = 10L, to = 3200L) int timeout) {
/* 194 */     if (this.valueCallback != null) {
/* 195 */       this.valueCallback.onConnectionUpdated(device, interval, latency, timeout);
/*     */     }
/*     */   }
/*     */   
/*     */   int getRequiredPriority() {
/* 200 */     return this.value;
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\ConnectionPriorityRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */