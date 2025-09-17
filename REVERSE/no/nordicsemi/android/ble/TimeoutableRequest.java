/*     */ package no.nordicsemi.android.ble;
/*     */ 
/*     */ import android.bluetooth.BluetoothDevice;
/*     */ import android.bluetooth.BluetoothGattCharacteristic;
/*     */ import android.bluetooth.BluetoothGattDescriptor;
/*     */ import android.os.Handler;
/*     */ import androidx.annotation.IntRange;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import java.util.concurrent.CancellationException;
/*     */ import no.nordicsemi.android.ble.callback.FailCallback;
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
/*     */ public abstract class TimeoutableRequest
/*     */   extends Request
/*     */ {
/*     */   @Nullable
/*     */   private Runnable timeoutCallback;
/*     */   protected boolean cancelled;
/*     */   protected long timeout;
/*     */   
/*     */   TimeoutableRequest(@NonNull Request.Type type) {
/*  37 */     super(type);
/*     */   }
/*     */   
/*     */   TimeoutableRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic) {
/*  41 */     super(type, characteristic);
/*     */   }
/*     */   
/*     */   TimeoutableRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor) {
/*  45 */     super(type, descriptor);
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   TimeoutableRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
/*  51 */     super.setRequestHandler(requestHandler);
/*  52 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public TimeoutableRequest setHandler(@Nullable Handler handler) {
/*  58 */     super.setHandler(handler);
/*  59 */     return this;
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
/*     */   public TimeoutableRequest timeout(@IntRange(from = 0L) long timeout) {
/*  74 */     if (this.timeoutCallback != null)
/*  75 */       throw new IllegalStateException("Request already started"); 
/*  76 */     this.timeout = timeout;
/*  77 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancel() {
/*  87 */     if (!this.started) {
/*     */       
/*  89 */       this.cancelled = true;
/*  90 */       this.finished = true;
/*  91 */     } else if (!this.finished) {
/*  92 */       this.cancelled = true;
/*  93 */       this.requestHandler.cancelCurrent();
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
/*     */   public final void enqueue() {
/* 106 */     super.enqueue();
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
/*     */   public final void enqueue(@IntRange(from = 0L) long timeout) {
/* 121 */     timeout(timeout).enqueue();
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
/*     */   public final void await() throws RequestFailedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException, InterruptedException {
/* 151 */     assertNotMainThread();
/*     */     
/* 153 */     if (this.cancelled) {
/* 154 */       throw new CancellationException();
/*     */     }
/* 156 */     if (this.finished || this.enqueued) {
/* 157 */       throw new IllegalStateException();
/*     */     }
/* 159 */     SuccessCallback sc = this.successCallback;
/* 160 */     FailCallback fc = this.failCallback;
/*     */     try {
/* 162 */       this.syncLock.close();
/* 163 */       Request.RequestCallback callback = new Request.RequestCallback(this);
/* 164 */       done(callback).fail(callback).invalid(callback).enqueue();
/*     */       
/* 166 */       if (!this.syncLock.block(this.timeout)) {
/* 167 */         throw new InterruptedException();
/*     */       }
/* 169 */       if (!callback.isSuccess()) {
/* 170 */         if (callback.status == -7) {
/* 171 */           throw new CancellationException();
/*     */         }
/* 173 */         if (callback.status == -1) {
/* 174 */           throw new DeviceDisconnectedException();
/*     */         }
/* 176 */         if (callback.status == -100) {
/* 177 */           throw new BluetoothDisabledException();
/*     */         }
/* 179 */         if (callback.status == -1000000) {
/* 180 */           throw new InvalidRequestException(this);
/*     */         }
/* 182 */         throw new RequestFailedException(this, callback.status);
/*     */       } 
/*     */     } finally {
/* 185 */       this.successCallback = sc;
/* 186 */       this.failCallback = fc;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public final void await(@IntRange(from = 0L) long timeout) throws RequestFailedException, InterruptedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException, CancellationException {
/* 221 */     timeout(timeout).await();
/*     */   }
/*     */ 
/*     */   
/*     */   void notifyStarted(@NonNull BluetoothDevice device) {
/* 226 */     if (this.timeout > 0L) {
/* 227 */       this.timeoutCallback = (() -> {
/*     */           this.timeoutCallback = null;
/*     */           if (!this.finished) {
/*     */             this.requestHandler.onRequestTimeout(device, this);
/*     */           }
/*     */         });
/* 233 */       this.handler.postDelayed(this.timeoutCallback, this.timeout);
/*     */     } 
/* 235 */     super.notifyStarted(device);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean notifySuccess(@NonNull BluetoothDevice device) {
/* 240 */     if (this.timeoutCallback != null) {
/* 241 */       this.handler.removeCallbacks(this.timeoutCallback);
/* 242 */       this.timeoutCallback = null;
/*     */     } 
/* 244 */     return super.notifySuccess(device);
/*     */   }
/*     */ 
/*     */   
/*     */   void notifyFail(@NonNull BluetoothDevice device, int status) {
/* 249 */     if (this.timeoutCallback != null) {
/* 250 */       this.handler.removeCallbacks(this.timeoutCallback);
/* 251 */       this.timeoutCallback = null;
/*     */     } 
/* 253 */     super.notifyFail(device, status);
/*     */   }
/*     */ 
/*     */   
/*     */   void notifyInvalidRequest() {
/* 258 */     if (this.timeoutCallback != null) {
/* 259 */       this.handler.removeCallbacks(this.timeoutCallback);
/* 260 */       this.timeoutCallback = null;
/*     */     } 
/* 262 */     super.notifyInvalidRequest();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isCancelled() {
/* 271 */     return this.cancelled;
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\TimeoutableRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */