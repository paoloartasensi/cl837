/*     */ package no.nordicsemi.android.support.v18.scanner;
/*     */ 
/*     */ import android.app.PendingIntent;
/*     */ import android.app.Service;
/*     */ import android.content.Context;
/*     */ import android.content.Intent;
/*     */ import android.os.SystemClock;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
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
/*     */ class PendingIntentExecutor
/*     */   extends ScanCallback
/*     */ {
/*     */   @NonNull
/*     */   private final PendingIntent callbackIntent;
/*     */   @Nullable
/*     */   private Context context;
/*     */   @Nullable
/*     */   private Context service;
/*     */   private long lastBatchTimestamp;
/*     */   private final long reportDelay;
/*     */   
/*     */   PendingIntentExecutor(@NonNull PendingIntent callbackIntent, @NonNull ScanSettings settings) {
/*  46 */     this.callbackIntent = callbackIntent;
/*  47 */     this.reportDelay = settings.getReportDelayMillis();
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
/*     */   
/*     */   PendingIntentExecutor(@NonNull PendingIntent callbackIntent, @NonNull ScanSettings settings, @NonNull Service service) {
/*  62 */     this.callbackIntent = callbackIntent;
/*  63 */     this.reportDelay = settings.getReportDelayMillis();
/*  64 */     this.service = (Context)service;
/*     */   }
/*     */   
/*     */   void setTemporaryContext(@Nullable Context context) {
/*  68 */     this.context = context;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onScanResult(int callbackType, @NonNull ScanResult result) {
/*  73 */     Context context = (this.context != null) ? this.context : this.service;
/*  74 */     if (context == null) {
/*     */       return;
/*     */     }
/*     */     try {
/*  78 */       Intent extrasIntent = new Intent();
/*  79 */       extrasIntent.putExtra("android.bluetooth.le.extra.CALLBACK_TYPE", callbackType);
/*  80 */       extrasIntent.putParcelableArrayListExtra("android.bluetooth.le.extra.LIST_SCAN_RESULT", new ArrayList(
/*  81 */             Collections.singletonList(result)));
/*  82 */       this.callbackIntent.send(context, 0, extrasIntent);
/*  83 */     } catch (android.app.PendingIntent.CanceledException canceledException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBatchScanResults(@NonNull List<ScanResult> results) {
/*  90 */     Context context = (this.context != null) ? this.context : this.service;
/*  91 */     if (context == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  96 */     long now = SystemClock.elapsedRealtime();
/*  97 */     if (this.lastBatchTimestamp > now - this.reportDelay + 5L) {
/*     */       return;
/*     */     }
/* 100 */     this.lastBatchTimestamp = now;
/*     */     
/*     */     try {
/* 103 */       Intent extrasIntent = new Intent();
/* 104 */       extrasIntent.putExtra("android.bluetooth.le.extra.CALLBACK_TYPE", 1);
/*     */       
/* 106 */       extrasIntent.putParcelableArrayListExtra("android.bluetooth.le.extra.LIST_SCAN_RESULT", new ArrayList<>(results));
/*     */       
/* 108 */       extrasIntent.setExtrasClassLoader(ScanResult.class.getClassLoader());
/* 109 */       this.callbackIntent.send(context, 0, extrasIntent);
/* 110 */     } catch (android.app.PendingIntent.CanceledException canceledException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onScanFailed(int errorCode) {
/* 117 */     Context context = (this.context != null) ? this.context : this.service;
/* 118 */     if (context == null) {
/*     */       return;
/*     */     }
/*     */     try {
/* 122 */       Intent extrasIntent = new Intent();
/* 123 */       extrasIntent.putExtra("android.bluetooth.le.extra.ERROR_CODE", errorCode);
/* 124 */       this.callbackIntent.send(context, 0, extrasIntent);
/* 125 */     } catch (android.app.PendingIntent.CanceledException canceledException) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\support\v18\scanner\PendingIntentExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */