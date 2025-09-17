/*     */ package no.nordicsemi.android.support.v18.scanner;
/*     */ 
/*     */ import android.app.PendingIntent;
/*     */ import android.app.Service;
/*     */ import android.content.Intent;
/*     */ import android.os.Handler;
/*     */ import android.os.IBinder;
/*     */ import android.util.Log;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.annotation.RequiresPermission;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ public class ScannerService
/*     */   extends Service
/*     */ {
/*     */   private static final String TAG = "ScannerService";
/*     */   static final String EXTRA_PENDING_INTENT = "no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT";
/*     */   static final String EXTRA_REQUEST_CODE = "no.nordicsemi.android.support.v18.REQUEST_CODE";
/*     */   static final String EXTRA_FILTERS = "no.nordicsemi.android.support.v18.EXTRA_FILTERS";
/*     */   static final String EXTRA_SETTINGS = "no.nordicsemi.android.support.v18.EXTRA_SETTINGS";
/*     */   static final String EXTRA_START = "no.nordicsemi.android.support.v18.EXTRA_START";
/*     */   @NonNull
/*  43 */   private final Object LOCK = new Object();
/*     */   
/*     */   private HashMap<Integer, ScanCallback> callbacks;
/*     */   
/*     */   private Handler handler;
/*     */   
/*     */   public void onCreate() {
/*  50 */     super.onCreate();
/*  51 */     this.callbacks = new HashMap<>();
/*  52 */     this.handler = new Handler();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequiresPermission(allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"})
/*     */   public int onStartCommand(Intent intent, int flags, int startId) {
/*  61 */     if (intent != null) {
/*  62 */       boolean knownCallback; PendingIntent callbackIntent = (PendingIntent)intent.getParcelableExtra("no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT");
/*  63 */       int requestCode = intent.getIntExtra("no.nordicsemi.android.support.v18.REQUEST_CODE", 0);
/*  64 */       boolean start = intent.getBooleanExtra("no.nordicsemi.android.support.v18.EXTRA_START", false);
/*  65 */       boolean stop = !start;
/*     */       
/*  67 */       if (callbackIntent == null) {
/*     */         boolean shouldStop;
/*  69 */         synchronized (this.LOCK) {
/*  70 */           shouldStop = this.callbacks.isEmpty();
/*     */         } 
/*  72 */         if (shouldStop)
/*  73 */           stopSelf(); 
/*  74 */         return 2;
/*     */       } 
/*     */ 
/*     */       
/*  78 */       synchronized (this.LOCK) {
/*  79 */         knownCallback = this.callbacks.containsKey(Integer.valueOf(requestCode));
/*     */       } 
/*     */       
/*  82 */       if (start && !knownCallback) {
/*  83 */         ArrayList<ScanFilter> filters = intent.getParcelableArrayListExtra("no.nordicsemi.android.support.v18.EXTRA_FILTERS");
/*  84 */         ScanSettings settings = (ScanSettings)intent.getParcelableExtra("no.nordicsemi.android.support.v18.EXTRA_SETTINGS");
/*  85 */         startScan((filters != null) ? filters : Collections.<ScanFilter>emptyList(), 
/*  86 */             (settings != null) ? settings : (new ScanSettings.Builder()).build(), callbackIntent, requestCode);
/*     */       }
/*  88 */       else if (stop && knownCallback) {
/*  89 */         stopScan(requestCode);
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IBinder onBind(Intent intent) {
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTaskRemoved(Intent rootIntent) {
/* 105 */     super.onTaskRemoved(rootIntent);
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
/*     */   @RequiresPermission(allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"})
/*     */   public void onDestroy() {
/* 120 */     BluetoothLeScannerCompat scannerCompat = BluetoothLeScannerCompat.getScanner();
/* 121 */     for (ScanCallback callback : this.callbacks.values()) {
/*     */       try {
/* 123 */         scannerCompat.stopScan(callback);
/* 124 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */     
/* 128 */     this.callbacks.clear();
/* 129 */     this.callbacks = null;
/* 130 */     this.handler = null;
/* 131 */     super.onDestroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequiresPermission(allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"})
/*     */   private void startScan(@NonNull List<ScanFilter> filters, @NonNull ScanSettings settings, @NonNull PendingIntent callbackIntent, int requestCode) {
/* 139 */     PendingIntentExecutor executor = new PendingIntentExecutor(callbackIntent, settings, this);
/*     */     
/* 141 */     synchronized (this.LOCK) {
/* 142 */       this.callbacks.put(Integer.valueOf(requestCode), executor);
/*     */     } 
/*     */     
/*     */     try {
/* 146 */       BluetoothLeScannerCompat scannerCompat = BluetoothLeScannerCompat.getScanner();
/* 147 */       scannerCompat.startScanInternal(filters, settings, executor, this.handler);
/* 148 */     } catch (Exception e) {
/* 149 */       Log.w("ScannerService", "Starting scanning failed", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequiresPermission(allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"})
/*     */   private void stopScan(int requestCode) {
/*     */     ScanCallback callback;
/*     */     boolean shouldStop;
/* 157 */     synchronized (this.LOCK) {
/* 158 */       callback = this.callbacks.remove(Integer.valueOf(requestCode));
/* 159 */       shouldStop = this.callbacks.isEmpty();
/*     */     } 
/* 161 */     if (callback == null) {
/*     */       return;
/*     */     }
/*     */     try {
/* 165 */       BluetoothLeScannerCompat scannerCompat = BluetoothLeScannerCompat.getScanner();
/* 166 */       scannerCompat.stopScan(callback);
/* 167 */     } catch (Exception e) {
/* 168 */       Log.w("ScannerService", "Stopping scanning failed", e);
/*     */     } 
/*     */     
/* 171 */     if (shouldStop)
/* 172 */       stopSelf(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\support\v18\scanner\ScannerService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */