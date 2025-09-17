/*     */ package no.nordicsemi.android.ble;
/*     */ 
/*     */ import android.bluetooth.BluetoothDevice;
/*     */ import android.bluetooth.BluetoothGattCharacteristic;
/*     */ import android.bluetooth.BluetoothGattDescriptor;
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
/*     */ public abstract class AwaitingRequest<T>
/*     */   extends TimeoutableValueRequest<T>
/*     */ {
/*     */   private static final int NOT_STARTED = -123456;
/*     */   private static final int STARTED = -123455;
/*     */   private Request trigger;
/*  22 */   private int triggerStatus = 0;
/*     */   
/*     */   AwaitingRequest(@NonNull Request.Type type) {
/*  25 */     super(type);
/*     */   }
/*     */   
/*     */   AwaitingRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic characteristic) {
/*  29 */     super(type, characteristic);
/*     */   }
/*     */   
/*     */   AwaitingRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor descriptor) {
/*  33 */     super(type, descriptor);
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
/*     */   public AwaitingRequest<T> trigger(@NonNull Operation trigger) {
/*  46 */     if (trigger instanceof Request) {
/*  47 */       this.trigger = (Request)trigger;
/*  48 */       this.triggerStatus = -123456;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  54 */       this.trigger.internalBefore(device -> this.triggerStatus = -123455);
/*  55 */       this.trigger.internalSuccess(device -> this.triggerStatus = 0);
/*  56 */       this.trigger.internalFail((device, status) -> {
/*     */             this.triggerStatus = status;
/*     */             this.syncLock.open();
/*     */             notifyFail(device, status);
/*     */           });
/*     */     } 
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public <E extends T> E await(@NonNull E response) throws RequestFailedException, DeviceDisconnectedException, BluetoothDisabledException, InvalidRequestException, InterruptedException, CancellationException {
/*  70 */     assertNotMainThread();
/*     */ 
/*     */     
/*     */     try {
/*  74 */       if (this.trigger != null && this.trigger.enqueued) {
/*  75 */         throw new IllegalStateException("Trigger request already enqueued");
/*     */       }
/*  77 */       super.await(response);
/*  78 */       return response;
/*  79 */     } catch (RequestFailedException e) {
/*  80 */       if (this.triggerStatus != 0)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/*  85 */         throw new RequestFailedException(this.trigger, this.triggerStatus);
/*     */       }
/*  87 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   Request getTrigger() {
/*  93 */     return this.trigger;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isTriggerPending() {
/*  98 */     return (this.triggerStatus == -123456);
/*     */   }
/*     */   
/*     */   boolean isTriggerCompleteOrNull() {
/* 102 */     return (this.triggerStatus != -123455);
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\AwaitingRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */