/*    */ package no.nordicsemi.android.ble;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.os.Handler;
/*    */ import androidx.annotation.NonNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public abstract class LegacyBleManager<E extends BleManagerCallbacks>
/*    */   extends BleManager
/*    */ {
/*    */   protected E mCallbacks;
/*    */   
/*    */   public LegacyBleManager(@NonNull Context context) {
/* 23 */     super(context);
/*    */   }
/*    */   
/*    */   public LegacyBleManager(@NonNull Context context, @NonNull Handler handler) {
/* 27 */     super(context, handler);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setGattCallbacks(@NonNull BleManagerCallbacks callbacks) {
/* 32 */     super.setGattCallbacks(callbacks);
/*    */     
/* 34 */     this.mCallbacks = (E)callbacks;
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\LegacyBleManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */