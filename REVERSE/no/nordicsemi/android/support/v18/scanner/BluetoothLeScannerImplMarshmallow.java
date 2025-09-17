/*    */ package no.nordicsemi.android.support.v18.scanner;
/*    */ 
/*    */ import android.annotation.TargetApi;
/*    */ import android.bluetooth.BluetoothAdapter;
/*    */ import android.bluetooth.le.ScanSettings;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @TargetApi(23)
/*    */ class BluetoothLeScannerImplMarshmallow
/*    */   extends BluetoothLeScannerImplLollipop
/*    */ {
/*    */   @NonNull
/*    */   ScanSettings toNativeScanSettings(@NonNull BluetoothAdapter adapter, @NonNull ScanSettings settings, boolean exactCopy) {
/* 39 */     ScanSettings.Builder builder = new ScanSettings.Builder();
/*    */ 
/*    */     
/* 42 */     if (exactCopy || (adapter.isOffloadedScanBatchingSupported() && settings.getUseHardwareBatchingIfSupported())) {
/* 43 */       builder.setReportDelay(settings.getReportDelayMillis());
/*    */     }
/* 45 */     if (exactCopy || settings.getUseHardwareCallbackTypesIfSupported()) {
/* 46 */       builder.setCallbackType(settings.getCallbackType())
/* 47 */         .setMatchMode(settings.getMatchMode())
/* 48 */         .setNumOfMatches(settings.getNumOfMatches());
/*    */     }
/* 50 */     builder.setScanMode(settings.getScanMode());
/*    */     
/* 52 */     return builder.build();
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\support\v18\scanner\BluetoothLeScannerImplMarshmallow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */