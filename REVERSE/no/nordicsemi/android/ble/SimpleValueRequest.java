/*     */ package no.nordicsemi.android.ble;
/*     */ 
/*     */ import android.bluetooth.BluetoothGattCharacteristic;
/*     */ import android.bluetooth.BluetoothGattDescriptor;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
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
/*     */ public abstract class SimpleValueRequest<T>
/*     */   extends SimpleRequest
/*     */ {
/*     */   T valueCallback;
/*     */   
/*     */   SimpleValueRequest(@NonNull Request.Type type) {
/*  47 */     super(type);
/*     */   }
/*     */ 
/*     */   
/*     */   SimpleValueRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic) {
/*  52 */     super(type, characteristic);
/*     */   }
/*     */ 
/*     */   
/*     */   SimpleValueRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor) {
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
/*     */   @NonNull
/*     */   public SimpleValueRequest<T> with(@NonNull T callback) {
/*  69 */     this.valueCallback = callback;
/*  70 */     return this;
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
/*     */   @NonNull
/*     */   public <E extends T> E await(@NonNull E response) throws RequestFailedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException {
/* 100 */     assertNotMainThread();
/*     */     
/* 102 */     T vc = this.valueCallback;
/*     */     try {
/* 104 */       with((T)response).await();
/* 105 */       return response;
/*     */     } finally {
/* 107 */       this.valueCallback = vc;
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
/*     */   @NonNull
/*     */   public <E extends T> E await(@NonNull Class<E> responseClass) throws RequestFailedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException {
/* 138 */     assertNotMainThread();
/*     */     
/*     */     try {
/* 141 */       E response = responseClass.newInstance();
/* 142 */       return await(response);
/* 143 */     } catch (IllegalAccessException e) {
/* 144 */       throw new IllegalArgumentException("Couldn't instantiate " + responseClass
/* 145 */           .getCanonicalName() + " class. Is the default constructor accessible?");
/*     */     }
/* 147 */     catch (InstantiationException e) {
/* 148 */       throw new IllegalArgumentException("Couldn't instantiate " + responseClass
/* 149 */           .getCanonicalName() + " class. Does it have a default constructor with no arguments?");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\SimpleValueRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */