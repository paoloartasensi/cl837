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
/*     */ import java.util.Arrays;
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
/*     */ public final class WriteRequest
/*     */   extends SimpleValueRequest<DataSentCallback>
/*     */   implements Operation
/*     */ {
/*  51 */   private static final DataSplitter MTU_SPLITTER = (DataSplitter)new DefaultMtuSplitter();
/*     */   
/*     */   private WriteProgressCallback progressCallback;
/*     */   private DataSplitter dataSplitter;
/*     */   private final byte[] data;
/*     */   private final int writeType;
/*     */   private byte[] currentChunk;
/*     */   private byte[] nextChunk;
/*  59 */   private int count = 0;
/*     */   private boolean complete = false;
/*     */   
/*     */   WriteRequest(@NonNull Request.Type type) {
/*  63 */     this(type, (BluetoothGattCharacteristic)null);
/*     */   }
/*     */   
/*     */   WriteRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic) {
/*  67 */     super(type, characteristic);
/*     */     
/*  69 */     this.data = null;
/*  70 */     this.writeType = 0;
/*     */     
/*  72 */     this.complete = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WriteRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] data, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length, int writeType) {
/*  79 */     super(type, characteristic);
/*  80 */     this.data = Bytes.copy(data, offset, length);
/*  81 */     this.writeType = writeType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   WriteRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] data, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
/*  87 */     super(type, characteristic);
/*  88 */     this.data = Bytes.copy(data, offset, length);
/*  89 */     this.writeType = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   WriteRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] data, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
/*  95 */     super(type, descriptor);
/*  96 */     this.data = Bytes.copy(data, offset, length);
/*  97 */     this.writeType = 2;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   WriteRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
/* 103 */     super.setRequestHandler(requestHandler);
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WriteRequest setHandler(@Nullable Handler handler) {
/* 110 */     super.setHandler(handler);
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WriteRequest done(@NonNull SuccessCallback callback) {
/* 117 */     super.done(callback);
/* 118 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WriteRequest fail(@NonNull FailCallback callback) {
/* 124 */     super.fail(callback);
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WriteRequest invalid(@NonNull InvalidRequestCallback callback) {
/* 131 */     super.invalid(callback);
/* 132 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WriteRequest before(@NonNull BeforeCallback callback) {
/* 138 */     super.before(callback);
/* 139 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WriteRequest then(@NonNull AfterCallback callback) {
/* 145 */     super.then(callback);
/* 146 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public WriteRequest with(@NonNull DataSentCallback callback) {
/* 152 */     super.with(callback);
/* 153 */     return this;
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
/*     */   public WriteRequest split(@NonNull DataSplitter splitter) {
/* 167 */     this.dataSplitter = splitter;
/* 168 */     this.progressCallback = null;
/* 169 */     return this;
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
/*     */   public WriteRequest split(@NonNull DataSplitter splitter, @NonNull WriteProgressCallback callback) {
/* 185 */     this.dataSplitter = splitter;
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
/*     */   @NonNull
/*     */   public WriteRequest split() {
/* 198 */     this.dataSplitter = MTU_SPLITTER;
/* 199 */     this.progressCallback = null;
/* 200 */     return this;
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
/*     */   public WriteRequest split(@NonNull WriteProgressCallback callback) {
/* 212 */     this.dataSplitter = MTU_SPLITTER;
/* 213 */     this.progressCallback = callback;
/* 214 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void forceSplit() {
/* 222 */     if (this.dataSplitter == null) {
/* 223 */       split();
/*     */     }
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
/*     */   @NonNull
/*     */   byte[] getData(@IntRange(from = 23L, to = 517L) int mtu) {
/* 237 */     if (this.dataSplitter == null || this.data == null) {
/* 238 */       this.complete = true;
/* 239 */       this.currentChunk = this.data;
/* 240 */       return (this.data != null) ? this.data : new byte[0];
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 246 */     int maxLength = (this.writeType != 4) ? (mtu - 3) : (mtu - 12);
/*     */     
/* 248 */     byte[] chunk = this.nextChunk;
/*     */     
/* 250 */     if (chunk == null) {
/* 251 */       chunk = this.dataSplitter.chunk(this.data, this.count, maxLength);
/*     */     }
/*     */     
/* 254 */     if (chunk != null) {
/* 255 */       this.nextChunk = this.dataSplitter.chunk(this.data, this.count + 1, maxLength);
/*     */     }
/*     */     
/* 258 */     if (this.nextChunk == null) {
/* 259 */       this.complete = true;
/*     */     }
/* 261 */     this.currentChunk = chunk;
/* 262 */     return (chunk != null) ? chunk : new byte[0];
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
/*     */   boolean notifyPacketSent(@NonNull BluetoothDevice device, @Nullable byte[] data) {
/* 275 */     this.handler.post(() -> {
/*     */           if (this.progressCallback != null) {
/*     */             try {
/*     */               this.progressCallback.onPacketSent(device, this.currentChunk, this.count);
/* 279 */             } catch (Throwable t) {
/*     */               Log.e(TAG, "Exception in Progress callback", t);
/*     */             } 
/*     */           }
/*     */         });
/* 284 */     this.count++;
/* 285 */     if (this.complete) {
/* 286 */       this.handler.post(() -> {
/*     */             if (this.valueCallback != null) {
/*     */               try {
/*     */                 this.valueCallback.onDataSent(device, new Data(this.data));
/* 290 */               } catch (Throwable t) {
/*     */                 Log.e(TAG, "Exception in Value callback", t);
/*     */               } 
/*     */             }
/*     */           });
/*     */     }
/* 296 */     if (this.writeType == 2)
/*     */     {
/* 298 */       return Arrays.equals(data, this.currentChunk);
/*     */     }
/*     */     
/* 301 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasMore() {
/* 311 */     return !this.complete;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getWriteType() {
/* 321 */     return this.writeType;
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\WriteRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */