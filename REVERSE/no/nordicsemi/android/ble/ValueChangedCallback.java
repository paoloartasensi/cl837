/*     */ package no.nordicsemi.android.ble;
/*     */ 
/*     */ import android.bluetooth.BluetoothDevice;
/*     */ import android.os.Handler;
/*     */ import android.util.Log;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import no.nordicsemi.android.ble.callback.ClosedCallback;
/*     */ import no.nordicsemi.android.ble.callback.DataReceivedCallback;
/*     */ import no.nordicsemi.android.ble.callback.ReadProgressCallback;
/*     */ import no.nordicsemi.android.ble.data.Data;
/*     */ import no.nordicsemi.android.ble.data.DataFilter;
/*     */ import no.nordicsemi.android.ble.data.DataMerger;
/*     */ import no.nordicsemi.android.ble.data.DataStream;
/*     */ import no.nordicsemi.android.ble.data.PacketFilter;
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
/*     */ public class ValueChangedCallback
/*     */ {
/*  42 */   private static final String TAG = ValueChangedCallback.class.getSimpleName();
/*     */   
/*     */   private ClosedCallback closedCallback;
/*     */   private ReadProgressCallback progressCallback;
/*     */   private DataReceivedCallback valueCallback;
/*     */   private DataMerger dataMerger;
/*     */   private DataStream buffer;
/*     */   private DataFilter filter;
/*     */   private PacketFilter packetFilter;
/*     */   private CallbackHandler handler;
/*  52 */   private int count = 0;
/*     */   
/*     */   ValueChangedCallback(CallbackHandler handler) {
/*  55 */     this.handler = handler;
/*     */   }
/*     */   
/*     */   @NonNull
/*     */   public ValueChangedCallback setHandler(@Nullable final Handler handler) {
/*  60 */     this.handler = new CallbackHandler()
/*     */       {
/*     */         public void post(@NonNull Runnable r) {
/*  63 */           if (handler != null) {
/*  64 */             handler.post(r);
/*     */           } else {
/*  66 */             r.run();
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void postDelayed(@NonNull Runnable r, long delayMillis) {}
/*     */ 
/*     */ 
/*     */         
/*     */         public void removeCallbacks(@NonNull Runnable r) {}
/*     */       };
/*  79 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ValueChangedCallback with(@NonNull DataReceivedCallback callback) {
/*  91 */     this.valueCallback = callback;
/*  92 */     return this;
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
/*     */   @NonNull
/*     */   public ValueChangedCallback filter(@NonNull DataFilter filter) {
/* 106 */     this.filter = filter;
/* 107 */     return this;
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
/*     */   @NonNull
/*     */   public ValueChangedCallback filterPacket(@NonNull PacketFilter filter) {
/* 123 */     this.packetFilter = filter;
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ValueChangedCallback merge(@NonNull DataMerger merger) {
/* 135 */     this.dataMerger = merger;
/* 136 */     this.progressCallback = null;
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ValueChangedCallback merge(@NonNull DataMerger merger, @NonNull ReadProgressCallback callback) {
/* 149 */     this.dataMerger = merger;
/* 150 */     this.progressCallback = callback;
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ValueChangedCallback then(@NonNull ClosedCallback callback) {
/* 163 */     this.closedCallback = callback;
/* 164 */     return this;
/*     */   }
/*     */   
/*     */   boolean matches(byte[] packet) {
/* 168 */     return (this.filter == null || this.filter.filter(packet));
/*     */   }
/*     */ 
/*     */   
/*     */   void notifyValueChanged(@NonNull BluetoothDevice device, @Nullable byte[] value) {
/* 173 */     DataReceivedCallback valueCallback = this.valueCallback;
/*     */ 
/*     */     
/* 176 */     if (valueCallback == null) {
/*     */       return;
/*     */     }
/*     */     
/* 180 */     if (this.dataMerger == null && (this.packetFilter == null || this.packetFilter.filter(value))) {
/* 181 */       Data data = new Data(value);
/* 182 */       this.handler.post(() -> {
/*     */             try {
/*     */               valueCallback.onDataReceived(device, data);
/* 185 */             } catch (Throwable t) {
/*     */               Log.e(TAG, "Exception in Value callback", t);
/*     */             } 
/*     */           });
/*     */     } else {
/* 190 */       this.handler.post(() -> {
/*     */             if (this.progressCallback != null) {
/*     */               try {
/*     */                 this.progressCallback.onPacketReceived(device, value, this.count);
/* 194 */               } catch (Throwable t) {
/*     */                 Log.e(TAG, "Exception in Progress callback", t);
/*     */               } 
/*     */             }
/*     */           });
/* 199 */       if (this.buffer == null)
/* 200 */         this.buffer = new DataStream(); 
/* 201 */       if (this.dataMerger.merge(this.buffer, value, this.count++)) {
/* 202 */         byte[] merged = this.buffer.toByteArray();
/* 203 */         if (this.packetFilter == null || this.packetFilter.filter(merged)) {
/* 204 */           Data data = new Data(merged);
/* 205 */           this.handler.post(() -> {
/*     */                 try {
/*     */                   valueCallback.onDataReceived(device, data);
/* 208 */                 } catch (Throwable t) {
/*     */                   Log.e(TAG, "Exception in Value callback", t);
/*     */                 } 
/*     */               });
/*     */         } 
/* 213 */         this.buffer = null;
/* 214 */         this.count = 0;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void notifyClosed() {
/* 221 */     if (this.closedCallback != null) {
/*     */       try {
/* 223 */         this.closedCallback.onClosed();
/* 224 */       } catch (Throwable t) {
/* 225 */         Log.e(TAG, "Exception in Closed callback", t);
/*     */       } 
/*     */     }
/* 228 */     free();
/*     */   }
/*     */   
/*     */   private void free() {
/* 232 */     this.closedCallback = null;
/* 233 */     this.valueCallback = null;
/* 234 */     this.dataMerger = null;
/* 235 */     this.progressCallback = null;
/* 236 */     this.filter = null;
/* 237 */     this.packetFilter = null;
/* 238 */     this.buffer = null;
/* 239 */     this.count = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\ValueChangedCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */