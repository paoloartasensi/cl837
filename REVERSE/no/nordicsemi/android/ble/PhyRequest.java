/*     */ package no.nordicsemi.android.ble;
/*     */ 
/*     */ import android.bluetooth.BluetoothDevice;
/*     */ import android.os.Handler;
/*     */ import android.util.Log;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import no.nordicsemi.android.ble.callback.AfterCallback;
/*     */ import no.nordicsemi.android.ble.callback.BeforeCallback;
/*     */ import no.nordicsemi.android.ble.callback.FailCallback;
/*     */ import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
/*     */ import no.nordicsemi.android.ble.callback.PhyCallback;
/*     */ import no.nordicsemi.android.ble.callback.SuccessCallback;
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
/*     */ public final class PhyRequest
/*     */   extends SimpleValueRequest<PhyCallback>
/*     */   implements Operation
/*     */ {
/*     */   public static final int PHY_LE_1M_MASK = 1;
/*     */   public static final int PHY_LE_2M_MASK = 2;
/*     */   public static final int PHY_LE_CODED_MASK = 4;
/*     */   public static final int PHY_OPTION_NO_PREFERRED = 0;
/*     */   public static final int PHY_OPTION_S2 = 1;
/*     */   public static final int PHY_OPTION_S8 = 2;
/*     */   private final int txPhy;
/*     */   private final int rxPhy;
/*     */   private final int phyOptions;
/*     */   
/*     */   PhyRequest(@NonNull Request.Type type) {
/*  82 */     super(type);
/*  83 */     this.txPhy = 0;
/*  84 */     this.rxPhy = 0;
/*  85 */     this.phyOptions = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   PhyRequest(@NonNull Request.Type type, int txPhy, int rxPhy, int phyOptions) {
/*  90 */     super(type);
/*  91 */     if ((txPhy & 0xFFFFFFF8) > 0)
/*  92 */       txPhy = 1; 
/*  93 */     if ((rxPhy & 0xFFFFFFF8) > 0)
/*  94 */       rxPhy = 1; 
/*  95 */     if (phyOptions < 0 || phyOptions > 2)
/*  96 */       phyOptions = 0; 
/*  97 */     this.txPhy = txPhy;
/*  98 */     this.rxPhy = rxPhy;
/*  99 */     this.phyOptions = phyOptions;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   PhyRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
/* 105 */     super.setRequestHandler(requestHandler);
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public PhyRequest setHandler(@Nullable Handler handler) {
/* 112 */     super.setHandler(handler);
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public PhyRequest done(@NonNull SuccessCallback callback) {
/* 119 */     super.done(callback);
/* 120 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public PhyRequest fail(@NonNull FailCallback callback) {
/* 126 */     super.fail(callback);
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public PhyRequest invalid(@NonNull InvalidRequestCallback callback) {
/* 133 */     super.invalid(callback);
/* 134 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public PhyRequest before(@NonNull BeforeCallback callback) {
/* 140 */     super.before(callback);
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public PhyRequest then(@NonNull AfterCallback callback) {
/* 147 */     super.then(callback);
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public PhyRequest with(@NonNull PhyCallback callback) {
/* 154 */     super.with(callback);
/* 155 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   void notifyPhyChanged(@NonNull BluetoothDevice device, int txPhy, int rxPhy) {
/* 160 */     this.handler.post(() -> {
/*     */           if (this.valueCallback != null) {
/*     */             try {
/*     */               this.valueCallback.onPhyChanged(device, txPhy, rxPhy);
/* 164 */             } catch (Throwable t) {
/*     */               Log.e(TAG, "Exception in Value callback", t);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   void notifyLegacyPhy(@NonNull BluetoothDevice device) {
/* 172 */     this.handler.post(() -> {
/*     */           if (this.valueCallback != null) {
/*     */             try {
/*     */               this.valueCallback.onPhyChanged(device, 1, 1);
/* 176 */             } catch (Throwable t) {
/*     */               Log.e(TAG, "Exception in Value callback", t);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   int getPreferredTxPhy() {
/* 184 */     return this.txPhy;
/*     */   }
/*     */ 
/*     */   
/*     */   int getPreferredRxPhy() {
/* 189 */     return this.rxPhy;
/*     */   }
/*     */ 
/*     */   
/*     */   int getPreferredPhyOptions() {
/* 194 */     return this.phyOptions;
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\PhyRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */