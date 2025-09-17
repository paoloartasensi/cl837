/*     */ package no.nordicsemi.android.ble;
/*     */ 
/*     */ import android.os.Handler;
/*     */ import androidx.annotation.IntRange;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import no.nordicsemi.android.ble.callback.AfterCallback;
/*     */ import no.nordicsemi.android.ble.callback.BeforeCallback;
/*     */ import no.nordicsemi.android.ble.callback.FailCallback;
/*     */ import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
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
/*     */ public final class ReliableWriteRequest
/*     */   extends RequestQueue
/*     */ {
/*     */   private boolean initialized;
/*     */   private boolean closed;
/*     */   
/*     */   @NonNull
/*     */   ReliableWriteRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
/*  57 */     super.setRequestHandler(requestHandler);
/*  58 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReliableWriteRequest setHandler(@Nullable Handler handler) {
/*  64 */     super.setHandler(handler);
/*  65 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReliableWriteRequest done(@NonNull SuccessCallback callback) {
/*  71 */     super.done(callback);
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReliableWriteRequest fail(@NonNull FailCallback callback) {
/*  78 */     super.fail(callback);
/*  79 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReliableWriteRequest invalid(@NonNull InvalidRequestCallback callback) {
/*  85 */     super.invalid(callback);
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReliableWriteRequest before(@NonNull BeforeCallback callback) {
/*  92 */     super.before(callback);
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReliableWriteRequest then(@NonNull AfterCallback callback) {
/*  99 */     super.then(callback);
/* 100 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReliableWriteRequest timeout(@IntRange(from = 0L) long timeout) {
/* 106 */     super.timeout(timeout);
/* 107 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ReliableWriteRequest add(@NonNull Operation operation) {
/* 113 */     super.add(operation);
/*     */ 
/*     */     
/* 116 */     if (operation instanceof WriteRequest) {
/* 117 */       ((WriteRequest)operation).forceSplit();
/*     */     }
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void abort() {
/* 126 */     cancel();
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 131 */     int size = super.size();
/*     */ 
/*     */     
/* 134 */     if (!this.initialized) {
/* 135 */       size++;
/*     */     }
/*     */     
/* 138 */     if (!this.closed)
/* 139 */       size++; 
/* 140 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   Request getNext() {
/* 145 */     if (!this.initialized) {
/* 146 */       this.initialized = true;
/* 147 */       return newBeginReliableWriteRequest();
/*     */     } 
/* 149 */     if (isEmpty()) {
/* 150 */       this.closed = true;
/*     */       
/* 152 */       if (this.cancelled)
/* 153 */         return newAbortReliableWriteRequest(); 
/* 154 */       return newExecuteReliableWriteRequest();
/*     */     } 
/* 156 */     return super.getNext();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasMore() {
/* 162 */     if (!this.initialized)
/* 163 */       return super.hasMore(); 
/* 164 */     return !this.closed;
/*     */   }
/*     */ 
/*     */   
/*     */   void cancelQueue() {
/* 169 */     this.cancelled = true;
/* 170 */     super.cancelQueue();
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\ReliableWriteRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */