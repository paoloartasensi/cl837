/*     */ package no.nordicsemi.android.ble;
/*     */ 
/*     */ import android.bluetooth.BluetoothGattCharacteristic;
/*     */ import android.bluetooth.BluetoothGattDescriptor;
/*     */ import androidx.annotation.IntRange;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import java.util.concurrent.CancellationException;
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
/*     */ 
/*     */ 
/*     */ public abstract class TimeoutableValueRequest<T>
/*     */   extends TimeoutableRequest
/*     */ {
/*     */   T valueCallback;
/*     */   
/*     */   TimeoutableValueRequest(@NonNull Request.Type type) {
/*  50 */     super(type);
/*     */   }
/*     */ 
/*     */   
/*     */   TimeoutableValueRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic) {
/*  55 */     super(type, characteristic);
/*     */   }
/*     */ 
/*     */   
/*     */   TimeoutableValueRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor) {
/*  60 */     super(type, descriptor);
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public TimeoutableValueRequest<T> timeout(@IntRange(from = 0L) long timeout) {
/*  66 */     super.timeout(timeout);
/*  67 */     return this;
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
/*     */   public TimeoutableValueRequest<T> with(@NonNull T callback) {
/*  79 */     this.valueCallback = callback;
/*  80 */     return this;
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
/*     */   @NonNull
/*     */   public <E extends T> E await(@NonNull E response) throws RequestFailedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException, InterruptedException, CancellationException {
/* 114 */     assertNotMainThread();
/*     */     
/* 116 */     T vc = this.valueCallback;
/*     */     try {
/* 118 */       with((T)response).await();
/* 119 */       return response;
/*     */     } finally {
/* 121 */       this.valueCallback = vc;
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
/*     */   @NonNull
/*     */   public <E extends T> E await(@NonNull Class<E> responseClass) throws RequestFailedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException, InterruptedException, CancellationException {
/* 155 */     assertNotMainThread();
/*     */     
/*     */     try {
/* 158 */       E response = responseClass.newInstance();
/* 159 */       return await(response);
/* 160 */     } catch (IllegalAccessException e) {
/* 161 */       throw new IllegalArgumentException("Couldn't instantiate " + responseClass
/* 162 */           .getCanonicalName() + " class. Is the default constructor accessible?");
/*     */     }
/* 164 */     catch (InstantiationException e) {
/* 165 */       throw new IllegalArgumentException("Couldn't instantiate " + responseClass
/* 166 */           .getCanonicalName() + " class. Does it have a default constructor with no arguments?");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @NonNull
/*     */   public <E extends T> E await(@NonNull Class<E> responseClass, @IntRange(from = 0L) long timeout) throws RequestFailedException, InterruptedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException, CancellationException {
/* 207 */     return timeout(timeout).await(responseClass);
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
/*     */   @Deprecated
/*     */   @NonNull
/*     */   public <E extends T> E await(@NonNull E response, @IntRange(from = 0L) long timeout) throws RequestFailedException, InterruptedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException, CancellationException {
/* 243 */     return timeout(timeout).await(response);
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\TimeoutableValueRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */