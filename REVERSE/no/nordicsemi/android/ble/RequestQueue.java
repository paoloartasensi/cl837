/*     */ package no.nordicsemi.android.ble;
/*     */ 
/*     */ import android.os.Handler;
/*     */ import androidx.annotation.IntRange;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import java.util.Deque;
/*     */ import java.util.LinkedList;
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
/*     */ public class RequestQueue
/*     */   extends TimeoutableRequest
/*     */ {
/*     */   @NonNull
/*     */   private final Deque<Request> requests;
/*     */   
/*     */   RequestQueue() {
/*  56 */     super(Request.Type.SET);
/*  57 */     this.requests = new LinkedList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   RequestQueue setRequestHandler(@NonNull RequestHandler requestHandler) {
/*  63 */     super.setRequestHandler(requestHandler);
/*  64 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public RequestQueue setHandler(@Nullable Handler handler) {
/*  70 */     super.setHandler(handler);
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public RequestQueue done(@NonNull SuccessCallback callback) {
/*  77 */     super.done(callback);
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public RequestQueue fail(@NonNull FailCallback callback) {
/*  84 */     super.fail(callback);
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public RequestQueue invalid(@NonNull InvalidRequestCallback callback) {
/*  91 */     super.invalid(callback);
/*  92 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public RequestQueue before(@NonNull BeforeCallback callback) {
/*  98 */     super.before(callback);
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public RequestQueue then(@NonNull AfterCallback callback) {
/* 105 */     super.then(callback);
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public RequestQueue timeout(@IntRange(from = 0L) long timeout) {
/* 112 */     super.timeout(timeout);
/* 113 */     return this;
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
/*     */   @NonNull
/*     */   public RequestQueue add(@NonNull Operation operation) {
/* 126 */     if (operation instanceof Request) {
/* 127 */       Request request = (Request)operation;
/*     */       
/* 129 */       if (request.enqueued) {
/* 130 */         throw new IllegalStateException("Request already enqueued");
/*     */       }
/* 132 */       request.internalFail(this::notifyFail);
/* 133 */       this.requests.add(request);
/*     */       
/* 135 */       request.enqueued = true;
/* 136 */       return this;
/*     */     } 
/* 138 */     throw new IllegalArgumentException("Operation does not extend Request");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addFirst(@NonNull Request request) {
/* 148 */     this.requests.addFirst(request);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @IntRange(from = 0L)
/*     */   public int size() {
/* 158 */     return this.requests.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 168 */     return this.requests.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancel() {
/* 179 */     cancelQueue();
/* 180 */     super.cancel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   Request getNext() {
/*     */     try {
/* 191 */       return this.requests.remove();
/*     */     
/*     */     }
/* 194 */     catch (Exception e) {
/* 195 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasMore() {
/* 205 */     return (!this.finished && !this.requests.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void cancelQueue() {
/* 212 */     this.requests.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\RequestQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */