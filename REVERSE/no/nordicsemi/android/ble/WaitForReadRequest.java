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
/*     */ import no.nordicsemi.android.ble.callback.AfterCallback;
/*     */ import no.nordicsemi.android.ble.callback.BeforeCallback;
/*     */ import no.nordicsemi.android.ble.callback.DataSentCallback;
/*     */ import no.nordicsemi.android.ble.callback.FailCallback;
/*     */ import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
/*     */ import no.nordicsemi.android.ble.callback.SuccessCallback;
/*     */ import no.nordicsemi.android.ble.callback.WriteProgressCallback;
/*     */ import no.nordicsemi.android.ble.data.Data;
/*     */ import no.nordicsemi.android.ble.data.DataSplitter;
/*     */ import no.nordicsemi.android.ble.data.DefaultMtuSplitter;
/*     */ 
/*     */ public final class WaitForReadRequest
/*     */   extends AwaitingRequest<DataSentCallback>
/*     */   implements Operation {
/*  25 */   private static final DataSplitter MTU_SPLITTER = (DataSplitter)new DefaultMtuSplitter();
/*     */   
/*     */   private WriteProgressCallback progressCallback;
/*     */   private DataSplitter dataSplitter;
/*     */   private byte[] data;
/*     */   private byte[] nextChunk;
/*  31 */   private int count = 0;
/*     */   private boolean complete = false;
/*     */   
/*     */   WaitForReadRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic) {
/*  35 */     super(type, characteristic);
/*     */     
/*  37 */     this.data = null;
/*     */     
/*  39 */     this.complete = true;
/*     */   }
/*     */   
/*     */   WaitForReadRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor) {
/*  43 */     super(type, descriptor);
/*     */     
/*  45 */     this.data = null;
/*     */     
/*  47 */     this.complete = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   WaitForReadRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] data, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
/*  53 */     super(type, characteristic);
/*  54 */     this.data = Bytes.copy(data, offset, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   WaitForReadRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] data, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
/*  60 */     super(type, descriptor);
/*  61 */     this.data = Bytes.copy(data, offset, length);
/*     */   }
/*     */   
/*     */   void setDataIfNull(@Nullable byte[] data) {
/*  65 */     if (this.data == null) {
/*  66 */       this.data = data;
/*     */     }
/*     */   }
/*     */   
/*     */   @NonNull
/*     */   WaitForReadRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
/*  72 */     super.setRequestHandler(requestHandler);
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WaitForReadRequest setHandler(@Nullable Handler handler) {
/*  79 */     super.setHandler(handler);
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WaitForReadRequest done(@NonNull SuccessCallback callback) {
/*  86 */     super.done(callback);
/*  87 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WaitForReadRequest fail(@NonNull FailCallback callback) {
/*  93 */     super.fail(callback);
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WaitForReadRequest invalid(@NonNull InvalidRequestCallback callback) {
/* 100 */     super.invalid(callback);
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WaitForReadRequest before(@NonNull BeforeCallback callback) {
/* 107 */     super.before(callback);
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WaitForReadRequest then(@NonNull AfterCallback callback) {
/* 114 */     super.then(callback);
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WaitForReadRequest with(@NonNull DataSentCallback callback) {
/* 121 */     super.with(callback);
/* 122 */     return this;
/*     */   }
/*     */   
/*     */   @NonNull
/*     */   public WaitForReadRequest trigger(@NonNull Operation trigger) {
/* 127 */     super.trigger(trigger);
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
/*     */   public WaitForReadRequest split(@NonNull DataSplitter splitter) {
/* 142 */     this.dataSplitter = splitter;
/* 143 */     this.progressCallback = null;
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
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WaitForReadRequest split(@NonNull DataSplitter splitter, @NonNull WriteProgressCallback callback) {
/* 160 */     this.dataSplitter = splitter;
/* 161 */     this.progressCallback = callback;
/* 162 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WaitForReadRequest split() {
/* 173 */     this.dataSplitter = MTU_SPLITTER;
/* 174 */     this.progressCallback = null;
/* 175 */     return this;
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
/*     */   public WaitForReadRequest split(@NonNull WriteProgressCallback callback) {
/* 187 */     this.dataSplitter = MTU_SPLITTER;
/* 188 */     this.progressCallback = callback;
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
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   byte[] getData(@IntRange(from = 23L, to = 517L) int mtu) {
/* 203 */     if (this.dataSplitter == null || this.data == null) {
/* 204 */       this.complete = true;
/* 205 */       return (this.data != null) ? this.data : new byte[0];
/*     */     } 
/*     */ 
/*     */     
/* 209 */     int maxLength = mtu - 1;
/*     */     
/* 211 */     byte[] chunk = this.nextChunk;
/*     */     
/* 213 */     if (chunk == null) {
/* 214 */       chunk = this.dataSplitter.chunk(this.data, this.count, maxLength);
/*     */     }
/*     */     
/* 217 */     if (chunk != null) {
/* 218 */       this.nextChunk = this.dataSplitter.chunk(this.data, this.count + 1, maxLength);
/*     */     }
/*     */     
/* 221 */     if (this.nextChunk == null) {
/* 222 */       this.complete = true;
/*     */     }
/* 224 */     return (chunk != null) ? chunk : new byte[0];
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
/*     */   void notifyPacketRead(@NonNull BluetoothDevice device, @Nullable byte[] data) {
/* 238 */     this.handler.post(() -> {
/*     */           if (this.progressCallback != null) {
/*     */             try {
/*     */               this.progressCallback.onPacketSent(device, data, this.count);
/* 242 */             } catch (Throwable t) {
/*     */               Log.e(TAG, "Exception in Progress callback", t);
/*     */             } 
/*     */           }
/*     */         });
/* 247 */     this.count++;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean notifySuccess(@NonNull BluetoothDevice device) {
/* 252 */     this.handler.post(() -> {
/*     */           if (this.valueCallback != null) {
/*     */             try {
/*     */               this.valueCallback.onDataSent(device, new Data(this.data));
/* 256 */             } catch (Throwable t) {
/*     */               Log.e(TAG, "Exception in Value callback", t);
/*     */             } 
/*     */           }
/*     */         });
/* 261 */     return super.notifySuccess(device);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasMore() {
/* 271 */     return !this.complete;
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\WaitForReadRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */