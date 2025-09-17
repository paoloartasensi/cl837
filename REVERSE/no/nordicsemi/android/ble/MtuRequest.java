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
/*     */ import no.nordicsemi.android.ble.callback.MtuCallback;
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
/*     */ public final class MtuRequest
/*     */   extends SimpleValueRequest<MtuCallback>
/*     */   implements Operation
/*     */ {
/*     */   private final int value;
/*     */   
/*     */   MtuRequest(@NonNull Request.Type type, @IntRange(from = 23L, to = 517L) int mtu) {
/*  43 */     super(type);
/*  44 */     if (mtu < 23)
/*  45 */       mtu = 23; 
/*  46 */     if (mtu > 517)
/*  47 */       mtu = 517; 
/*  48 */     this.value = mtu;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   MtuRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
/*  54 */     super.setRequestHandler(requestHandler);
/*  55 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public MtuRequest setHandler(@Nullable Handler handler) {
/*  61 */     super.setHandler(handler);
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public MtuRequest done(@NonNull SuccessCallback callback) {
/*  68 */     super.done(callback);
/*  69 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public MtuRequest fail(@NonNull FailCallback callback) {
/*  75 */     super.fail(callback);
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public MtuRequest invalid(@NonNull InvalidRequestCallback callback) {
/*  82 */     super.invalid(callback);
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public MtuRequest before(@NonNull BeforeCallback callback) {
/*  89 */     super.before(callback);
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public MtuRequest then(@NonNull AfterCallback callback) {
/*  96 */     super.then(callback);
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public MtuRequest with(@NonNull MtuCallback callback) {
/* 103 */     super.with(callback);
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   void notifyMtuChanged(@NonNull BluetoothDevice device, @IntRange(from = 23L, to = 517L) int mtu) {
/* 109 */     this.handler.post(() -> {
/*     */           if (this.valueCallback != null) {
/*     */             try {
/*     */               this.valueCallback.onMtuChanged(device, mtu);
/* 113 */             } catch (Throwable t) {
/*     */               Log.e(TAG, "Exception in Value callback", t);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   int getRequiredMtu() {
/* 121 */     return this.value;
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\MtuRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */