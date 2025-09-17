/*    */ package no.nordicsemi.android.support.v18.scanner;
/*    */ 
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import java.util.HashSet;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ class ScanCallbackWrapperSet<W extends BluetoothLeScannerCompat.ScanCallbackWrapper> {
/*    */   @NonNull
/* 12 */   private final Set<W> wrappers = new HashSet<>();
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   public Set<W> values() {
/* 17 */     return this.wrappers;
/*    */   }
/*    */   
/*    */   boolean isEmpty() {
/* 21 */     return this.wrappers.isEmpty();
/*    */   }
/*    */   
/*    */   void add(@NonNull W wrapper) {
/* 25 */     this.wrappers.add(wrapper);
/*    */   }
/*    */   
/*    */   boolean contains(@NonNull ScanCallback callback) {
/* 29 */     for (BluetoothLeScannerCompat.ScanCallbackWrapper scanCallbackWrapper : this.wrappers) {
/* 30 */       if (scanCallbackWrapper.scanCallback == callback) {
/* 31 */         return true;
/*    */       }
/* 33 */       if (scanCallbackWrapper.scanCallback instanceof UserScanCallbackWrapper) {
/* 34 */         UserScanCallbackWrapper callbackWrapper = (UserScanCallbackWrapper)scanCallbackWrapper.scanCallback;
/* 35 */         if (callbackWrapper.get() == callback) {
/* 36 */           return true;
/*    */         }
/*    */       } 
/*    */     } 
/* 40 */     return false;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   W get(@NonNull ScanCallback callback) {
/* 45 */     for (BluetoothLeScannerCompat.ScanCallbackWrapper scanCallbackWrapper : this.wrappers) {
/* 46 */       if (scanCallbackWrapper.scanCallback == callback) {
/* 47 */         return (W)scanCallbackWrapper;
/*    */       }
/* 49 */       if (scanCallbackWrapper.scanCallback instanceof UserScanCallbackWrapper) {
/* 50 */         UserScanCallbackWrapper callbackWrapper = (UserScanCallbackWrapper)scanCallbackWrapper.scanCallback;
/* 51 */         if (callbackWrapper.get() == callback) {
/* 52 */           return (W)scanCallbackWrapper;
/*    */         }
/*    */       } 
/*    */     } 
/* 56 */     return null;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   W remove(@NonNull ScanCallback callback) {
/* 61 */     for (BluetoothLeScannerCompat.ScanCallbackWrapper scanCallbackWrapper : this.wrappers) {
/* 62 */       if (scanCallbackWrapper.scanCallback == callback) {
/* 63 */         return (W)scanCallbackWrapper;
/*    */       }
/* 65 */       if (scanCallbackWrapper.scanCallback instanceof UserScanCallbackWrapper) {
/* 66 */         UserScanCallbackWrapper callbackWrapper = (UserScanCallbackWrapper)scanCallbackWrapper.scanCallback;
/* 67 */         if (callbackWrapper.get() == callback) {
/* 68 */           this.wrappers.remove(scanCallbackWrapper);
/* 69 */           return (W)scanCallbackWrapper;
/*    */         } 
/*    */       } 
/*    */     } 
/* 73 */     cleanUp();
/* 74 */     return null;
/*    */   }
/*    */   
/*    */   private void cleanUp() {
/* 78 */     List<W> deadWrappers = new LinkedList<>();
/* 79 */     for (BluetoothLeScannerCompat.ScanCallbackWrapper scanCallbackWrapper : this.wrappers) {
/* 80 */       if (scanCallbackWrapper.scanCallback instanceof UserScanCallbackWrapper) {
/* 81 */         UserScanCallbackWrapper callbackWrapper = (UserScanCallbackWrapper)scanCallbackWrapper.scanCallback;
/* 82 */         if (callbackWrapper.isDead())
/* 83 */           deadWrappers.add((W)scanCallbackWrapper); 
/*    */       } 
/*    */     } 
/* 86 */     for (BluetoothLeScannerCompat.ScanCallbackWrapper scanCallbackWrapper : deadWrappers)
/* 87 */       this.wrappers.remove(scanCallbackWrapper); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\support\v18\scanner\ScanCallbackWrapperSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */