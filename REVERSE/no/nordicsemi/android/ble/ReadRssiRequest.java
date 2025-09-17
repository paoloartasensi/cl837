/*     */ package no.nordicsemi.android.ble;
/*     */ 
/*     */ import android.bluetooth.BluetoothDevice;
/*     */ import android.os.Handler;
/*     */ import android.util.Log;
/*     */ import androidx.annotation.IntRange;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import no.nordicsemi.android.ble.callback.AfterCallback;
/*     */ import no.nordicsemi.android.ble.callback.BeforeCallback;
/*     */ import no.nordicsemi.android.ble.callback.FailCallback;
/*     */ import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
/*     */ import no.nordicsemi.android.ble.callback.RssiCallback;
/*     */ import no.nordicsemi.android.ble.callback.SuccessCallback;
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
/*     */ public final class ReadRssiRequest
/*     */   extends SimpleValueRequest<RssiCallback>
/*     */   implements Operation
/*     */ {
/*     */   ReadRssiRequest(@NonNull Request.Type type) {
/*  42 */     super(type);
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   ReadRssiRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
/*  48 */     super.setRequestHandler(requestHandler);
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReadRssiRequest setHandler(@Nullable Handler handler) {
/*  55 */     super.setHandler(handler);
/*  56 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReadRssiRequest done(@NonNull SuccessCallback callback) {
/*  62 */     super.done(callback);
/*  63 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReadRssiRequest fail(@NonNull FailCallback callback) {
/*  69 */     super.fail(callback);
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReadRssiRequest invalid(@NonNull InvalidRequestCallback callback) {
/*  76 */     super.invalid(callback);
/*  77 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReadRssiRequest before(@NonNull BeforeCallback callback) {
/*  83 */     super.before(callback);
/*  84 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReadRssiRequest then(@NonNull AfterCallback callback) {
/*  90 */     super.then(callback);
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReadRssiRequest with(@NonNull RssiCallback callback) {
/*  97 */     super.with(callback);
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   void notifyRssiRead(@NonNull BluetoothDevice device, @IntRange(from = -128L, to = 20L) int rssi) {
/* 103 */     this.handler.post(() -> {
/*     */           if (this.valueCallback != null)
/*     */             try {
/*     */               this.valueCallback.onRssiRead(device, rssi);
/* 107 */             } catch (Throwable t) {
/*     */               Log.e(TAG, "Exception in Value callback", t);
/*     */             }  
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\ReadRssiRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */