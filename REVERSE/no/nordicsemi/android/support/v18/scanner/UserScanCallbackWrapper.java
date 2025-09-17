/*    */ package no.nordicsemi.android.support.v18.scanner;
/*    */ 
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class UserScanCallbackWrapper
/*    */   extends ScanCallback
/*    */ {
/*    */   private final WeakReference<ScanCallback> weakScanCallback;
/*    */   
/*    */   UserScanCallbackWrapper(@NonNull ScanCallback userCallback) {
/* 20 */     this.weakScanCallback = new WeakReference<>(userCallback);
/*    */   }
/*    */   
/*    */   boolean isDead() {
/* 24 */     return (this.weakScanCallback.get() == null);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   ScanCallback get() {
/* 29 */     return this.weakScanCallback.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onScanResult(int callbackType, @NonNull ScanResult result) {
/* 34 */     ScanCallback userCallback = this.weakScanCallback.get();
/* 35 */     if (userCallback != null) {
/* 36 */       userCallback.onScanResult(callbackType, result);
/*    */     }
/*    */   }
/*    */   
/*    */   public void onBatchScanResults(@NonNull List<ScanResult> results) {
/* 41 */     ScanCallback userCallback = this.weakScanCallback.get();
/* 42 */     if (userCallback != null) {
/* 43 */       userCallback.onBatchScanResults(results);
/*    */     }
/*    */   }
/*    */   
/*    */   public void onScanFailed(int errorCode) {
/* 48 */     ScanCallback userCallback = this.weakScanCallback.get();
/* 49 */     if (userCallback != null)
/* 50 */       userCallback.onScanFailed(errorCode); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\support\v18\scanner\UserScanCallbackWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */