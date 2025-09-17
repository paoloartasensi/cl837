/*     */ package no.nordicsemi.android.ble;
/*     */ 
/*     */ import android.bluetooth.BluetoothDevice;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConnectRequest
/*     */   extends TimeoutableRequest
/*     */ {
/*     */   @NonNull
/*     */   private final BluetoothDevice device;
/*     */   private int preferredPhy;
/*     */   @IntRange(from = 0L)
/*  58 */   private int attempt = 0, retries = 0;
/*     */   @IntRange(from = 0L)
/*  60 */   private int delay = 0;
/*     */   
/*     */   private boolean autoConnect = false;
/*     */   
/*     */   ConnectRequest(@NonNull Request.Type type, @NonNull BluetoothDevice device) {
/*  65 */     super(type);
/*  66 */     this.device = device;
/*  67 */     this.preferredPhy = 1;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   ConnectRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
/*  73 */     super.setRequestHandler(requestHandler);
/*  74 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ConnectRequest setHandler(@Nullable Handler handler) {
/*  80 */     super.setHandler(handler);
/*  81 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ConnectRequest timeout(@IntRange(from = 0L) long timeout) {
/*  87 */     super.timeout(timeout);
/*  88 */     return this;
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
/*     */   @NonNull
/*     */   public ConnectRequest done(@NonNull SuccessCallback callback) {
/* 108 */     super.done(callback);
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ConnectRequest fail(@NonNull FailCallback callback) {
/* 115 */     super.fail(callback);
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ConnectRequest invalid(@NonNull InvalidRequestCallback callback) {
/* 122 */     super.invalid(callback);
/* 123 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ConnectRequest before(@NonNull BeforeCallback callback) {
/* 129 */     super.before(callback);
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ConnectRequest then(@NonNull AfterCallback callback) {
/* 136 */     super.then(callback);
/* 137 */     return this;
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
/*     */   public ConnectRequest retry(@IntRange(from = 0L) int count) {
/* 151 */     this.retries = count;
/* 152 */     this.delay = 0;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConnectRequest retry(@IntRange(from = 0L) int count, @IntRange(from = 0L) int delay) {
/* 171 */     this.retries = count;
/* 172 */     this.delay = delay;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConnectRequest useAutoConnect(boolean autoConnect) {
/* 208 */     this.autoConnect = autoConnect;
/* 209 */     return this;
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
/*     */   public ConnectRequest usePreferredPhy(int phy) {
/* 229 */     this.preferredPhy = phy;
/* 230 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelPendingConnection() {
/* 241 */     cancel();
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
/*     */   public void cancel() {
/* 256 */     if (!this.started) {
/*     */       
/* 258 */       this.cancelled = true;
/* 259 */       this.finished = true;
/* 260 */     } else if (!this.finished) {
/* 261 */       this.cancelled = true;
/* 262 */       this.requestHandler.cancelQueue();
/*     */     } 
/*     */   }
/*     */   
/*     */   @NonNull
/*     */   public BluetoothDevice getDevice() {
/* 268 */     return this.device;
/*     */   }
/*     */ 
/*     */   
/*     */   int getPreferredPhy() {
/* 273 */     return this.preferredPhy;
/*     */   }
/*     */   
/*     */   boolean canRetry() {
/* 277 */     if (this.retries > 0) {
/* 278 */       this.retries--;
/* 279 */       return true;
/*     */     } 
/* 281 */     return false;
/*     */   }
/*     */   
/*     */   boolean isFirstAttempt() {
/* 285 */     return (this.attempt++ == 0);
/*     */   }
/*     */   
/*     */   @IntRange(from = 0L)
/*     */   int getRetryDelay() {
/* 290 */     return this.delay;
/*     */   }
/*     */   
/*     */   boolean shouldAutoConnect() {
/* 294 */     return this.autoConnect;
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\ConnectRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */