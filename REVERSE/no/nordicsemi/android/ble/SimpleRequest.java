/*     */ package no.nordicsemi.android.ble;
/*     */ 
/*     */ import android.bluetooth.BluetoothGattCharacteristic;
/*     */ import android.bluetooth.BluetoothGattDescriptor;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import no.nordicsemi.android.ble.callback.BeforeCallback;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleRequest
/*     */   extends Request
/*     */ {
/*     */   SimpleRequest(@NonNull Request.Type type) {
/*  47 */     super(type);
/*     */   }
/*     */ 
/*     */   
/*     */   SimpleRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic) {
/*  52 */     super(type, characteristic);
/*     */   }
/*     */ 
/*     */   
/*     */   SimpleRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor) {
/*  57 */     super(type, descriptor);
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
/*     */   public final void await() throws RequestFailedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException {
/*  80 */     assertNotMainThread();
/*     */     
/*  82 */     BeforeCallback bc = this.beforeCallback;
/*  83 */     SuccessCallback sc = this.successCallback;
/*  84 */     FailCallback fc = this.failCallback;
/*     */     try {
/*  86 */       if (this.finished || this.enqueued) {
/*  87 */         throw new IllegalStateException();
/*     */       }
/*  89 */       this.syncLock.close();
/*  90 */       Request.RequestCallback callback = new Request.RequestCallback(this);
/*  91 */       this.beforeCallback = null;
/*  92 */       done(callback).fail(callback).invalid(callback).enqueue();
/*     */       
/*  94 */       this.syncLock.block();
/*  95 */       if (!callback.isSuccess()) {
/*  96 */         if (callback.status == -1) {
/*  97 */           throw new DeviceDisconnectedException();
/*     */         }
/*  99 */         if (callback.status == -100) {
/* 100 */           throw new BluetoothDisabledException();
/*     */         }
/* 102 */         if (callback.status == -1000000) {
/* 103 */           throw new InvalidRequestException(this);
/*     */         }
/* 105 */         throw new RequestFailedException(this, callback.status);
/*     */       } 
/*     */     } finally {
/* 108 */       this.beforeCallback = bc;
/* 109 */       this.successCallback = sc;
/* 110 */       this.failCallback = fc;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\SimpleRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */