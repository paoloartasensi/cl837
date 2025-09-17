/*     */ package no.nordicsemi.android.ble;
/*     */ 
/*     */ import android.bluetooth.BluetoothDevice;
/*     */ import android.bluetooth.BluetoothGattCharacteristic;
/*     */ import android.bluetooth.BluetoothGattDescriptor;
/*     */ import android.os.Handler;
/*     */ import android.util.Log;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
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
/*     */ public final class ReadRequest
/*     */   extends SimpleValueRequest<DataReceivedCallback>
/*     */   implements Operation
/*     */ {
/*     */   private ReadProgressCallback progressCallback;
/*     */   private DataMerger dataMerger;
/*     */   private DataStream buffer;
/*     */   private DataFilter filter;
/*     */   private PacketFilter packetFilter;
/*  60 */   private int count = 0;
/*     */   private boolean complete = false;
/*     */   
/*     */   ReadRequest(@NonNull Request.Type type) {
/*  64 */     super(type);
/*     */   }
/*     */   
/*     */   ReadRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic) {
/*  68 */     super(type, characteristic);
/*     */   }
/*     */   
/*     */   ReadRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor) {
/*  72 */     super(type, descriptor);
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   ReadRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
/*  78 */     super.setRequestHandler(requestHandler);
/*  79 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReadRequest setHandler(@Nullable Handler handler) {
/*  85 */     super.setHandler(handler);
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReadRequest done(@NonNull SuccessCallback callback) {
/*  92 */     super.done(callback);
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReadRequest fail(@NonNull FailCallback callback) {
/*  99 */     super.fail(callback);
/* 100 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReadRequest invalid(@NonNull InvalidRequestCallback callback) {
/* 106 */     super.invalid(callback);
/* 107 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReadRequest before(@NonNull BeforeCallback callback) {
/* 113 */     super.before(callback);
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReadRequest then(@NonNull AfterCallback callback) {
/* 120 */     super.then(callback);
/* 121 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReadRequest with(@NonNull DataReceivedCallback callback) {
/* 127 */     super.with(callback);
/* 128 */     return this;
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
/*     */   public ReadRequest filter(@NonNull DataFilter filter) {
/* 142 */     this.filter = filter;
/* 143 */     return this;
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
/*     */   public ReadRequest filterPacket(@NonNull PacketFilter filter) {
/* 159 */     this.packetFilter = filter;
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReadRequest merge(@NonNull DataMerger merger) {
/* 171 */     this.dataMerger = merger;
/* 172 */     this.progressCallback = null;
/* 173 */     return this;
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
/*     */   public ReadRequest merge(@NonNull DataMerger merger, @NonNull ReadProgressCallback callback) {
/* 185 */     this.dataMerger = merger;
/* 186 */     this.progressCallback = callback;
/* 187 */     return this;
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
/*     */   @NonNull
/*     */   public <E extends ProfileReadResponse> E awaitValid(@NonNull Class<E> responseClass) throws RequestFailedException, InvalidDataException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException {
/* 218 */     ProfileReadResponse profileReadResponse = (ProfileReadResponse)await(responseClass);
/* 219 */     if (!profileReadResponse.isValid()) {
/* 220 */       throw new InvalidDataException(profileReadResponse);
/*     */     }
/* 222 */     return (E)profileReadResponse;
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
/*     */   @NonNull
/*     */   public <E extends ProfileReadResponse> E awaitValid(@NonNull E response) throws RequestFailedException, InvalidDataException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException {
/* 251 */     await(response);
/* 252 */     if (!response.isValid()) {
/* 253 */       throw new InvalidDataException(response);
/*     */     }
/* 255 */     return response;
/*     */   }
/*     */   
/*     */   boolean matches(byte[] packet) {
/* 259 */     return (this.filter == null || this.filter.filter(packet));
/*     */   }
/*     */ 
/*     */   
/*     */   void notifyValueChanged(@NonNull BluetoothDevice device, @Nullable byte[] value) {
/* 264 */     DataReceivedCallback valueCallback = this.valueCallback;
/*     */ 
/*     */     
/* 267 */     if (valueCallback == null) {
/* 268 */       if (this.packetFilter == null || this.packetFilter.filter(value)) {
/* 269 */         this.complete = true;
/*     */       }
/*     */       return;
/*     */     } 
/* 273 */     if (this.dataMerger == null) {
/* 274 */       this.complete = true;
/* 275 */       Data data = new Data(value);
/* 276 */       this.handler.post(() -> {
/*     */             try {
/*     */               valueCallback.onDataReceived(device, data);
/* 279 */             } catch (Throwable t) {
/*     */               Log.e(TAG, "Exception in Value callback", t);
/*     */             } 
/*     */           });
/*     */     } else {
/* 284 */       this.handler.post(() -> {
/*     */             if (this.progressCallback != null) {
/*     */               try {
/*     */                 this.progressCallback.onPacketReceived(device, value, this.count);
/* 288 */               } catch (Throwable t) {
/*     */                 Log.e(TAG, "Exception in Progress callback", t);
/*     */               } 
/*     */             }
/*     */           });
/* 293 */       if (this.buffer == null)
/* 294 */         this.buffer = new DataStream(); 
/* 295 */       if (this.dataMerger.merge(this.buffer, value, this.count++)) {
/* 296 */         byte[] merged = this.buffer.toByteArray();
/* 297 */         if (this.packetFilter == null || this.packetFilter.filter(merged)) {
/* 298 */           this.complete = true;
/* 299 */           Data data = new Data(merged);
/* 300 */           this.handler.post(() -> {
/*     */                 try {
/*     */                   valueCallback.onDataReceived(device, data);
/* 303 */                 } catch (Throwable t) {
/*     */                   Log.e(TAG, "Exception in Value callback", t);
/*     */                 } 
/*     */               });
/*     */         } 
/* 308 */         this.buffer = null;
/* 309 */         this.count = 0;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasMore() {
/* 317 */     return !this.complete;
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\ReadRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */