package no.nordicsemi.android.support.v18.scanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.List;

class UserScanCallbackWrapper extends ScanCallback {
   private final WeakReference<ScanCallback> weakScanCallback;

   UserScanCallbackWrapper(@NonNull ScanCallback userCallback) {
      this.weakScanCallback = new WeakReference(userCallback);
   }

   boolean isDead() {
      return this.weakScanCallback.get() == null;
   }

   @Nullable
   ScanCallback get() {
      return (ScanCallback)this.weakScanCallback.get();
   }

   public void onScanResult(int callbackType, @NonNull ScanResult result) {
      ScanCallback userCallback = (ScanCallback)this.weakScanCallback.get();
      if (userCallback != null) {
         userCallback.onScanResult(callbackType, result);
      }

   }

   public void onBatchScanResults(@NonNull List<ScanResult> results) {
      ScanCallback userCallback = (ScanCallback)this.weakScanCallback.get();
      if (userCallback != null) {
         userCallback.onBatchScanResults(results);
      }

   }

   public void onScanFailed(int errorCode) {
      ScanCallback userCallback = (ScanCallback)this.weakScanCallback.get();
      if (userCallback != null) {
         userCallback.onScanFailed(errorCode);
      }

   }
}
