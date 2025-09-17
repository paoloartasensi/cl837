/*     */ package no.nordicsemi.android.ble;
/*     */ 
/*     */ import android.bluetooth.BluetoothGattCharacteristic;
/*     */ import android.bluetooth.BluetoothGattDescriptor;
/*     */ import android.os.Handler;
/*     */ import androidx.annotation.IntRange;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import no.nordicsemi.android.ble.callback.AfterCallback;
/*     */ import no.nordicsemi.android.ble.callback.BeforeCallback;
/*     */ import no.nordicsemi.android.ble.callback.FailCallback;
/*     */ import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
/*     */ import no.nordicsemi.android.ble.callback.SuccessCallback;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SetValueRequest
/*     */   extends SimpleRequest
/*     */ {
/*     */   private final byte[] data;
/*     */   private boolean longReadSupported = true;
/*     */   
/*     */   SetValueRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] data, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
/*  24 */     super(type, characteristic);
/*  25 */     this.data = Bytes.copy(data, offset, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   SetValueRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] data, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
/*  31 */     super(type, descriptor);
/*  32 */     this.data = Bytes.copy(data, offset, length);
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   SetValueRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
/*  38 */     super.setRequestHandler(requestHandler);
/*  39 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public SetValueRequest setHandler(@Nullable Handler handler) {
/*  45 */     super.setHandler(handler);
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public SetValueRequest done(@NonNull SuccessCallback callback) {
/*  52 */     super.done(callback);
/*  53 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public SetValueRequest fail(@NonNull FailCallback callback) {
/*  59 */     super.fail(callback);
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public SetValueRequest invalid(@NonNull InvalidRequestCallback callback) {
/*  66 */     super.invalid(callback);
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public SetValueRequest before(@NonNull BeforeCallback callback) {
/*  73 */     super.before(callback);
/*  74 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public SetValueRequest then(@NonNull AfterCallback callback) {
/*  80 */     super.then(callback);
/*  81 */     return this;
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
/*     */   @NonNull
/*     */   public SetValueRequest allowLongRead(boolean longReadSupported) {
/*  96 */     this.longReadSupported = longReadSupported;
/*  97 */     return this;
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
/*     */   byte[] getData(@IntRange(from = 23L, to = 517L) int mtu) {
/* 111 */     int maxLength = this.longReadSupported ? 512 : (mtu - 3);
/* 112 */     if (this.data.length < maxLength)
/* 113 */       return this.data; 
/* 114 */     return Bytes.copy(this.data, 0, maxLength);
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\SetValueRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */