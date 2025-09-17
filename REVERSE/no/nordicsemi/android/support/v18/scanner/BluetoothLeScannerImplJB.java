/*     */ package no.nordicsemi.android.support.v18.scanner;
/*     */ 
/*     */ import android.app.PendingIntent;
/*     */ import android.bluetooth.BluetoothAdapter;
/*     */ import android.bluetooth.BluetoothDevice;
/*     */ import android.content.Context;
/*     */ import android.content.Intent;
/*     */ import android.os.Handler;
/*     */ import android.os.HandlerThread;
/*     */ import android.os.Parcelable;
/*     */ import android.os.SystemClock;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ class BluetoothLeScannerImplJB
/*     */   extends BluetoothLeScannerCompat
/*     */ {
/*     */   @NonNull
/*  48 */   private final ScanCallbackWrapperSet<BluetoothLeScannerCompat.ScanCallbackWrapper> wrappers = new ScanCallbackWrapperSet<>();
/*     */   @Nullable
/*     */   private HandlerThread handlerThread;
/*     */   @Nullable
/*     */   private Handler powerSaveHandler;
/*     */   private long powerSaveRestInterval;
/*     */   private long powerSaveScanInterval;
/*     */   
/*  56 */   private final Runnable powerSaveSleepTask = new Runnable()
/*     */     {
/*     */       public void run() {
/*  59 */         BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
/*  60 */         if (adapter != null && BluetoothLeScannerImplJB.this.powerSaveRestInterval > 0L && BluetoothLeScannerImplJB.this.powerSaveScanInterval > 0L) {
/*  61 */           adapter.stopLeScan(BluetoothLeScannerImplJB.this.scanCallback);
/*  62 */           BluetoothLeScannerImplJB.this.powerSaveHandler.postDelayed(BluetoothLeScannerImplJB.this.powerSaveScanTask, BluetoothLeScannerImplJB.this.powerSaveRestInterval);
/*     */         } 
/*     */       }
/*     */     };
/*     */   
/*  67 */   private final Runnable powerSaveScanTask = new Runnable()
/*     */     {
/*     */       public void run() {
/*  70 */         BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
/*  71 */         if (adapter != null && BluetoothLeScannerImplJB.this.powerSaveRestInterval > 0L && BluetoothLeScannerImplJB.this.powerSaveScanInterval > 0L) {
/*  72 */           adapter.startLeScan(BluetoothLeScannerImplJB.this.scanCallback);
/*  73 */           BluetoothLeScannerImplJB.this.powerSaveHandler.postDelayed(BluetoothLeScannerImplJB.this.powerSaveSleepTask, BluetoothLeScannerImplJB.this.powerSaveScanInterval);
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   private final BluetoothAdapter.LeScanCallback scanCallback;
/*     */ 
/*     */   
/*     */   void startScanInternal(@NonNull List<ScanFilter> filters, @NonNull ScanSettings settings, @NonNull ScanCallback callback, @NonNull Handler handler) {
/*     */     boolean shouldStart;
/*  85 */     BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
/*     */ 
/*     */ 
/*     */     
/*  89 */     synchronized (this.wrappers) {
/*  90 */       if (this.wrappers.contains(callback)) {
/*  91 */         throw new IllegalArgumentException("scanner already started with given scanCallback");
/*     */       }
/*  93 */       UserScanCallbackWrapper callbackWrapper = new UserScanCallbackWrapper(callback);
/*  94 */       BluetoothLeScannerCompat.ScanCallbackWrapper wrapper = new BluetoothLeScannerCompat.ScanCallbackWrapper(false, false, filters, settings, callbackWrapper, handler);
/*     */ 
/*     */       
/*  97 */       shouldStart = this.wrappers.isEmpty();
/*  98 */       this.wrappers.add(wrapper);
/*     */     } 
/*     */     
/* 101 */     if (this.handlerThread == null) {
/* 102 */       this.handlerThread = new HandlerThread(BluetoothLeScannerImplJB.class.getName());
/* 103 */       this.handlerThread.start();
/* 104 */       this.powerSaveHandler = new Handler(this.handlerThread.getLooper());
/*     */     } 
/*     */     
/* 107 */     setPowerSaveSettings();
/*     */     
/* 109 */     if (shouldStart)
/* 110 */       adapter.startLeScan(this.scanCallback); 
/*     */   }
/*     */   
/*     */   void stopScanInternal(@NonNull ScanCallback callback) {
/*     */     boolean shouldStop;
/*     */     BluetoothLeScannerCompat.ScanCallbackWrapper wrapper;
/* 116 */     BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
/*     */ 
/*     */ 
/*     */     
/* 120 */     synchronized (this.wrappers) {
/* 121 */       wrapper = this.wrappers.remove(callback);
/* 122 */       shouldStop = this.wrappers.isEmpty();
/*     */     } 
/* 124 */     if (wrapper == null) {
/*     */       return;
/*     */     }
/* 127 */     wrapper.close();
/*     */     
/* 129 */     setPowerSaveSettings();
/*     */     
/* 131 */     if (shouldStop) {
/* 132 */       adapter.stopLeScan(this.scanCallback);
/*     */       
/* 134 */       if (this.powerSaveHandler != null) {
/* 135 */         this.powerSaveHandler.removeCallbacksAndMessages(null);
/*     */       }
/*     */       
/* 138 */       if (this.handlerThread != null) {
/* 139 */         this.handlerThread.quitSafely();
/* 140 */         this.handlerThread = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void startScanInternal(@NonNull List<ScanFilter> filters, @NonNull ScanSettings settings, @NonNull Context context, @NonNull PendingIntent callbackIntent, int requestCode) {
/* 151 */     Intent service = new Intent(context, ScannerService.class);
/* 152 */     service.putParcelableArrayListExtra("no.nordicsemi.android.support.v18.EXTRA_FILTERS", new ArrayList<>(filters));
/* 153 */     service.putExtra("no.nordicsemi.android.support.v18.EXTRA_SETTINGS", settings);
/* 154 */     service.putExtra("no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT", (Parcelable)callbackIntent);
/* 155 */     service.putExtra("no.nordicsemi.android.support.v18.REQUEST_CODE", requestCode);
/* 156 */     service.putExtra("no.nordicsemi.android.support.v18.EXTRA_START", true);
/* 157 */     context.startService(service);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void stopScanInternal(@NonNull Context context, @NonNull PendingIntent callbackIntent, int requestCode) {
/* 164 */     Intent service = new Intent(context, ScannerService.class);
/* 165 */     service.putExtra("no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT", (Parcelable)callbackIntent);
/* 166 */     service.putExtra("no.nordicsemi.android.support.v18.REQUEST_CODE", requestCode);
/* 167 */     service.putExtra("no.nordicsemi.android.support.v18.EXTRA_START", false);
/* 168 */     context.startService(service);
/*     */   }
/*     */ 
/*     */   
/*     */   public void flushPendingScanResults(@NonNull ScanCallback callback) {
/*     */     BluetoothLeScannerCompat.ScanCallbackWrapper wrapper;
/* 174 */     if (callback == null) {
/* 175 */       throw new IllegalArgumentException("callback cannot be null!");
/*     */     }
/*     */ 
/*     */     
/* 179 */     synchronized (this.wrappers) {
/* 180 */       wrapper = this.wrappers.get(callback);
/*     */     } 
/*     */     
/* 183 */     if (wrapper == null) {
/* 184 */       throw new IllegalArgumentException("callback not registered!");
/*     */     }
/*     */     
/* 187 */     wrapper.flushPendingScanResults();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setPowerSaveSettings() {
/* 195 */     long minRest = Long.MAX_VALUE, minScan = Long.MAX_VALUE;
/* 196 */     synchronized (this.wrappers) {
/* 197 */       for (BluetoothLeScannerCompat.ScanCallbackWrapper wrapper : this.wrappers.values()) {
/* 198 */         ScanSettings settings = wrapper.scanSettings;
/* 199 */         if (settings.hasPowerSaveMode()) {
/* 200 */           if (minRest > settings.getPowerSaveRest()) {
/* 201 */             minRest = settings.getPowerSaveRest();
/*     */           }
/* 203 */           if (minScan > settings.getPowerSaveScan()) {
/* 204 */             minScan = settings.getPowerSaveScan();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 209 */     if (minRest < Long.MAX_VALUE && minScan < Long.MAX_VALUE) {
/* 210 */       this.powerSaveRestInterval = minRest;
/* 211 */       this.powerSaveScanInterval = minScan;
/* 212 */       if (this.powerSaveHandler != null) {
/* 213 */         this.powerSaveHandler.removeCallbacks(this.powerSaveScanTask);
/* 214 */         this.powerSaveHandler.removeCallbacks(this.powerSaveSleepTask);
/* 215 */         this.powerSaveHandler.postDelayed(this.powerSaveSleepTask, this.powerSaveScanInterval);
/*     */       } 
/*     */     } else {
/* 218 */       this.powerSaveRestInterval = this.powerSaveScanInterval = 0L;
/* 219 */       if (this.powerSaveHandler != null) {
/* 220 */         this.powerSaveHandler.removeCallbacks(this.powerSaveScanTask);
/* 221 */         this.powerSaveHandler.removeCallbacks(this.powerSaveSleepTask);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   BluetoothLeScannerImplJB() {
/* 226 */     this.scanCallback = ((device, rssi, scanRecord) -> {
/*     */         ScanResult scanResult = new ScanResult(device, ScanRecord.parseFromBytes(scanRecord), rssi, SystemClock.elapsedRealtimeNanos());
/*     */         synchronized (this.wrappers) {
/*     */           Collection<BluetoothLeScannerCompat.ScanCallbackWrapper> scanCallbackWrappers = this.wrappers.values();
/*     */           for (BluetoothLeScannerCompat.ScanCallbackWrapper wrapper : scanCallbackWrappers)
/*     */             wrapper.handler.post(()); 
/*     */         } 
/*     */       });
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\support\v18\scanner\BluetoothLeScannerImplJB.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */