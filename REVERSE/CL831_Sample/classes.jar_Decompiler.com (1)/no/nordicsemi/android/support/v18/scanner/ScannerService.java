package no.nordicsemi.android.support.v18.scanner;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ScannerService extends Service {
   private static final String TAG = "ScannerService";
   static final String EXTRA_PENDING_INTENT = "no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT";
   static final String EXTRA_REQUEST_CODE = "no.nordicsemi.android.support.v18.REQUEST_CODE";
   static final String EXTRA_FILTERS = "no.nordicsemi.android.support.v18.EXTRA_FILTERS";
   static final String EXTRA_SETTINGS = "no.nordicsemi.android.support.v18.EXTRA_SETTINGS";
   static final String EXTRA_START = "no.nordicsemi.android.support.v18.EXTRA_START";
   @NonNull
   private final Object LOCK = new Object();
   private HashMap<Integer, ScanCallback> callbacks;
   private Handler handler;

   public void onCreate() {
      super.onCreate();
      this.callbacks = new HashMap();
      this.handler = new Handler();
   }

   @RequiresPermission(
      allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"}
   )
   public int onStartCommand(Intent intent, int flags, int startId) {
      if (intent != null) {
         PendingIntent callbackIntent = (PendingIntent)intent.getParcelableExtra("no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT");
         int requestCode = intent.getIntExtra("no.nordicsemi.android.support.v18.REQUEST_CODE", 0);
         boolean start = intent.getBooleanExtra("no.nordicsemi.android.support.v18.EXTRA_START", false);
         boolean stop = !start;
         boolean knownCallback;
         if (callbackIntent == null) {
            synchronized(this.LOCK) {
               knownCallback = this.callbacks.isEmpty();
            }

            if (knownCallback) {
               this.stopSelf();
            }

            return 2;
         }

         synchronized(this.LOCK) {
            knownCallback = this.callbacks.containsKey(requestCode);
         }

         if (start && !knownCallback) {
            ArrayList<ScanFilter> filters = intent.getParcelableArrayListExtra("no.nordicsemi.android.support.v18.EXTRA_FILTERS");
            ScanSettings settings = (ScanSettings)intent.getParcelableExtra("no.nordicsemi.android.support.v18.EXTRA_SETTINGS");
            this.startScan((List)(filters != null ? filters : Collections.emptyList()), settings != null ? settings : (new ScanSettings.Builder()).build(), callbackIntent, requestCode);
         } else if (stop && knownCallback) {
            this.stopScan(requestCode);
         }
      }

      return 2;
   }

   @Nullable
   public IBinder onBind(Intent intent) {
      return null;
   }

   public void onTaskRemoved(Intent rootIntent) {
      super.onTaskRemoved(rootIntent);
   }

   @RequiresPermission(
      allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"}
   )
   public void onDestroy() {
      BluetoothLeScannerCompat scannerCompat = BluetoothLeScannerCompat.getScanner();
      Iterator var2 = this.callbacks.values().iterator();

      while(var2.hasNext()) {
         ScanCallback callback = (ScanCallback)var2.next();

         try {
            scannerCompat.stopScan(callback);
         } catch (Exception var5) {
         }
      }

      this.callbacks.clear();
      this.callbacks = null;
      this.handler = null;
      super.onDestroy();
   }

   @RequiresPermission(
      allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"}
   )
   private void startScan(@NonNull List<ScanFilter> filters, @NonNull ScanSettings settings, @NonNull PendingIntent callbackIntent, int requestCode) {
      PendingIntentExecutor executor = new PendingIntentExecutor(callbackIntent, settings, this);
      synchronized(this.LOCK) {
         this.callbacks.put(requestCode, executor);
      }

      try {
         BluetoothLeScannerCompat scannerCompat = BluetoothLeScannerCompat.getScanner();
         scannerCompat.startScanInternal(filters, settings, executor, this.handler);
      } catch (Exception var8) {
         Log.w("ScannerService", "Starting scanning failed", var8);
      }

   }

   @RequiresPermission(
      allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"}
   )
   private void stopScan(int requestCode) {
      ScanCallback callback;
      boolean shouldStop;
      synchronized(this.LOCK) {
         callback = (ScanCallback)this.callbacks.remove(requestCode);
         shouldStop = this.callbacks.isEmpty();
      }

      if (callback != null) {
         try {
            BluetoothLeScannerCompat scannerCompat = BluetoothLeScannerCompat.getScanner();
            scannerCompat.stopScan(callback);
         } catch (Exception var6) {
            Log.w("ScannerService", "Stopping scanning failed", var6);
         }

         if (shouldStop) {
            this.stopSelf();
         }

      }
   }
}
