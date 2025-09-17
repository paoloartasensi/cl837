/*    */ package no.nordicsemi.android.ble;
/*    */ 
/*    */ import android.os.Handler;
/*    */ import androidx.annotation.IntRange;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import no.nordicsemi.android.ble.callback.AfterCallback;
/*    */ import no.nordicsemi.android.ble.callback.BeforeCallback;
/*    */ import no.nordicsemi.android.ble.callback.FailCallback;
/*    */ import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
/*    */ import no.nordicsemi.android.ble.callback.SuccessCallback;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SleepRequest
/*    */   extends TimeoutableRequest
/*    */   implements Operation
/*    */ {
/*    */   SleepRequest(@NonNull Request.Type type, @IntRange(from = 0L) long delay) {
/* 40 */     super(type);
/* 41 */     this.timeout = delay;
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   SleepRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
/* 47 */     super.setRequestHandler(requestHandler);
/* 48 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   public SleepRequest setHandler(@Nullable Handler handler) {
/* 54 */     super.setHandler(handler);
/* 55 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   public SleepRequest done(@NonNull SuccessCallback callback) {
/* 61 */     super.done(callback);
/* 62 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   public SleepRequest fail(@NonNull FailCallback callback) {
/* 68 */     super.fail(callback);
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   public SleepRequest invalid(@NonNull InvalidRequestCallback callback) {
/* 75 */     super.invalid(callback);
/* 76 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   public SleepRequest before(@NonNull BeforeCallback callback) {
/* 82 */     super.before(callback);
/* 83 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   public SleepRequest then(@NonNull AfterCallback callback) {
/* 89 */     super.then(callback);
/* 90 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   public SleepRequest timeout(@IntRange(from = 0L) long timeout) {
/* 96 */     super.timeout(timeout);
/* 97 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\SleepRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */