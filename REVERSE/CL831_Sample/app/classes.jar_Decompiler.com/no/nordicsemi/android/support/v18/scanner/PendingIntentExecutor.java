package no.nordicsemi.android.support.v18.scanner;

import android.app.PendingIntent;
import android.app.Service;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class PendingIntentExecutor extends ScanCallback {
   @NonNull
   private final PendingIntent callbackIntent;
   @Nullable
   private Context context;
   @Nullable
   private Context service;
   private long lastBatchTimestamp;
   private final long reportDelay;

   PendingIntentExecutor(@NonNull PendingIntent callbackIntent, @NonNull ScanSettings settings) {
      this.callbackIntent = callbackIntent;
      this.reportDelay = settings.getReportDelayMillis();
   }

   PendingIntentExecutor(@NonNull PendingIntent callbackIntent, @NonNull ScanSettings settings, @NonNull Service service) {
      this.callbackIntent = callbackIntent;
      this.reportDelay = settings.getReportDelayMillis();
      this.service = service;
   }

   void setTemporaryContext(@Nullable Context context) {
      this.context = context;
   }

   public void onScanResult(int callbackType, @NonNull ScanResult result) {
      Context context = this.context != null ? this.context : this.service;
      if (context != null) {
         try {
            Intent extrasIntent = new Intent();
            extrasIntent.putExtra("android.bluetooth.le.extra.CALLBACK_TYPE", callbackType);
            extrasIntent.putParcelableArrayListExtra("android.bluetooth.le.extra.LIST_SCAN_RESULT", new ArrayList(Collections.singletonList(result)));
            this.callbackIntent.send(context, 0, extrasIntent);
         } catch (CanceledException var5) {
         }

      }
   }

   public void onBatchScanResults(@NonNull List<ScanResult> results) {
      Context context = this.context != null ? this.context : this.service;
      if (context != null) {
         long now = SystemClock.elapsedRealtime();
         if (this.lastBatchTimestamp <= now - this.reportDelay + 5L) {
            this.lastBatchTimestamp = now;

            try {
               Intent extrasIntent = new Intent();
               extrasIntent.putExtra("android.bluetooth.le.extra.CALLBACK_TYPE", 1);
               extrasIntent.putParcelableArrayListExtra("android.bluetooth.le.extra.LIST_SCAN_RESULT", new ArrayList(results));
               extrasIntent.setExtrasClassLoader(ScanResult.class.getClassLoader());
               this.callbackIntent.send(context, 0, extrasIntent);
            } catch (CanceledException var6) {
            }

         }
      }
   }

   public void onScanFailed(int errorCode) {
      Context context = this.context != null ? this.context : this.service;
      if (context != null) {
         try {
            Intent extrasIntent = new Intent();
            extrasIntent.putExtra("android.bluetooth.le.extra.ERROR_CODE", errorCode);
            this.callbackIntent.send(context, 0, extrasIntent);
         } catch (CanceledException var4) {
         }

      }
   }
}
