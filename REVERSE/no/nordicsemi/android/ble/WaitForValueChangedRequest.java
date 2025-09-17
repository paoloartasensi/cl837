/*     */ package no.nordicsemi.android.ble;
/*     */ 
/*     */ import android.bluetooth.BluetoothDevice;
/*     */ import android.bluetooth.BluetoothGattCharacteristic;
/*     */ import android.bluetooth.BluetoothGattDescriptor;
/*     */ import android.os.Handler;
/*     */ import android.util.Log;
/*     */ import androidx.annotation.IntRange;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import java.util.concurrent.CancellationException;
/*     */ import no.nordicsemi.android.ble.callback.AfterCallback;
/*     */ import no.nordicsemi.android.ble.callback.BeforeCallback;
/*     */ import no.nordicsemi.android.ble.callback.DataReceivedCallback;
/*     */ import no.nordicsemi.android.ble.callback.FailCallback;
/*     */ import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
/*     */ import no.nordicsemi.android.ble.callback.ReadProgressCallback;
/*     */ import no.nordicsemi.android.ble.callback.SuccessCallback;
/*     */ import no.nordicsemi.android.ble.callback.profile.ProfileReadResponse;
/*     */ import no.nordicsemi.android.ble.data.Data;
/*     */ import no.nordicsemi.android.ble.data.DataFilter;
/*     */ import no.nordicsemi.android.ble.data.DataMerger;
/*     */ import no.nordicsemi.android.ble.data.DataStream;
/*     */ import no.nordicsemi.android.ble.data.PacketFilter;
/*     */ import no.nordicsemi.android.ble.exception.BluetoothDisabledException;
/*     */ import no.nordicsemi.android.ble.exception.DeviceDisconnectedException;
/*     */ import no.nordicsemi.android.ble.exception.InvalidDataException;
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
/*     */ public final class WaitForValueChangedRequest
/*     */   extends AwaitingRequest<DataReceivedCallback>
/*     */   implements Operation
/*     */ {
/*     */   private ReadProgressCallback progressCallback;
/*     */   private DataMerger dataMerger;
/*     */   private DataStream buffer;
/*     */   private DataFilter filter;
/*     */   private PacketFilter packetFilter;
/*     */   private boolean deviceDisconnected;
/*     */   private boolean bluetoothDisabled;
/*  65 */   private int count = 0;
/*     */   
/*     */   private boolean complete = false;
/*     */   
/*     */   WaitForValueChangedRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic) {
/*  70 */     super(type, characteristic);
/*     */   }
/*     */ 
/*     */   
/*     */   WaitForValueChangedRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor) {
/*  75 */     super(type, descriptor);
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   WaitForValueChangedRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
/*  81 */     super.setRequestHandler(requestHandler);
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WaitForValueChangedRequest setHandler(@Nullable Handler handler) {
/*  88 */     super.setHandler(handler);
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WaitForValueChangedRequest timeout(@IntRange(from = 0L) long timeout) {
/*  95 */     super.timeout(timeout);
/*  96 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WaitForValueChangedRequest done(@NonNull SuccessCallback callback) {
/* 102 */     super.done(callback);
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WaitForValueChangedRequest fail(@NonNull FailCallback callback) {
/* 109 */     super.fail(callback);
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WaitForValueChangedRequest invalid(@NonNull InvalidRequestCallback callback) {
/* 116 */     super.invalid(callback);
/* 117 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WaitForValueChangedRequest before(@NonNull BeforeCallback callback) {
/* 123 */     super.before(callback);
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WaitForValueChangedRequest then(@NonNull AfterCallback callback) {
/* 130 */     super.then(callback);
/* 131 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WaitForValueChangedRequest with(@NonNull DataReceivedCallback callback) {
/* 137 */     super.with(callback);
/* 138 */     return this;
/*     */   }
/*     */   
/*     */   @NonNull
/*     */   public WaitForValueChangedRequest trigger(@NonNull Operation trigger) {
/* 143 */     super.trigger(trigger);
/* 144 */     return this;
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
/*     */   @NonNull
/*     */   public WaitForValueChangedRequest filter(@NonNull DataFilter filter) {
/* 158 */     this.filter = filter;
/* 159 */     return this;
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
/*     */   
/*     */   @NonNull
/*     */   public WaitForValueChangedRequest filterPacket(@NonNull PacketFilter filter) {
/* 175 */     this.packetFilter = filter;
/* 176 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WaitForValueChangedRequest merge(@NonNull DataMerger merger) {
/* 187 */     this.dataMerger = merger;
/* 188 */     this.progressCallback = null;
/* 189 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WaitForValueChangedRequest merge(@NonNull DataMerger merger, @NonNull ReadProgressCallback callback) {
/* 201 */     this.dataMerger = merger;
/* 202 */     this.progressCallback = callback;
/* 203 */     return this;
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
/*     */   @NonNull
/*     */   public <E extends ProfileReadResponse> E awaitValid(@NonNull E response) throws RequestFailedException, InvalidDataException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException, InterruptedException, CancellationException {
/* 236 */     ProfileReadResponse profileReadResponse = (ProfileReadResponse)await(response);
/* 237 */     if (profileReadResponse != null && !profileReadResponse.isValid()) {
/* 238 */       throw new InvalidDataException(profileReadResponse);
/*     */     }
/* 240 */     return (E)profileReadResponse;
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
/*     */   @NonNull
/*     */   public <E extends ProfileReadResponse> E awaitValid(@NonNull Class<E> responseClass) throws RequestFailedException, InvalidDataException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException, InterruptedException, CancellationException {
/* 275 */     ProfileReadResponse profileReadResponse = (ProfileReadResponse)await(responseClass);
/* 276 */     if (profileReadResponse != null && !profileReadResponse.isValid()) {
/* 277 */       throw new InvalidDataException(profileReadResponse);
/*     */     }
/* 279 */     return (E)profileReadResponse;
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
/*     */   @Deprecated
/*     */   @NonNull
/*     */   public <E extends ProfileReadResponse> E awaitValid(@NonNull Class<E> responseClass, @IntRange(from = 0L) long timeout) throws InterruptedException, InvalidDataException, RequestFailedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException, CancellationException {
/* 316 */     return timeout(timeout).awaitValid(responseClass);
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
/*     */   @Deprecated
/*     */   @NonNull
/*     */   public <E extends ProfileReadResponse> E awaitValid(@NonNull E response, @IntRange(from = 0L) long timeout) throws InterruptedException, InvalidDataException, DeviceDisconnectedException, RequestFailedException, BluetoothDisabledException, InvalidRequestException, CancellationException {
/* 351 */     return timeout(timeout).awaitValid(response);
/*     */   }
/*     */   
/*     */   boolean matches(byte[] packet) {
/* 355 */     return (this.filter == null || this.filter.filter(packet));
/*     */   }
/*     */ 
/*     */   
/*     */   void notifyValueChanged(BluetoothDevice device, byte[] value) {
/* 360 */     DataReceivedCallback valueCallback = this.valueCallback;
/*     */ 
/*     */     
/* 363 */     if (valueCallback == null) {
/* 364 */       if (this.packetFilter == null || this.packetFilter.filter(value)) {
/* 365 */         this.complete = true;
/*     */       }
/*     */       return;
/*     */     } 
/* 369 */     if (this.dataMerger == null && (this.packetFilter == null || this.packetFilter.filter(value))) {
/* 370 */       this.complete = true;
/* 371 */       Data data = new Data(value);
/* 372 */       this.handler.post(() -> {
/*     */             try {
/*     */               valueCallback.onDataReceived(device, data);
/* 375 */             } catch (Throwable t) {
/*     */               Log.e(TAG, "Exception in Value callback", t);
/*     */             } 
/*     */           });
/*     */     } else {
/* 380 */       int c = this.count;
/* 381 */       this.handler.post(() -> {
/*     */             if (this.progressCallback != null) {
/*     */               try {
/*     */                 this.progressCallback.onPacketReceived(device, value, c);
/* 385 */               } catch (Throwable t) {
/*     */                 Log.e(TAG, "Exception in Progress callback", t);
/*     */               } 
/*     */             }
/*     */           });
/* 390 */       if (this.buffer == null)
/* 391 */         this.buffer = new DataStream(); 
/* 392 */       if (this.dataMerger.merge(this.buffer, value, this.count++)) {
/* 393 */         byte[] merged = this.buffer.toByteArray();
/* 394 */         if (this.packetFilter == null || this.packetFilter.filter(merged)) {
/* 395 */           this.complete = true;
/* 396 */           Data data = new Data(merged);
/* 397 */           this.handler.post(() -> {
/*     */                 try {
/*     */                   valueCallback.onDataReceived(device, data);
/* 400 */                 } catch (Throwable t) {
/*     */                   Log.e(TAG, "Exception in Value callback", t);
/*     */                 } 
/*     */               });
/*     */         } 
/* 405 */         this.buffer = null;
/* 406 */         this.count = 0;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isComplete() {
/* 413 */     return this.complete;
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\WaitForValueChangedRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */