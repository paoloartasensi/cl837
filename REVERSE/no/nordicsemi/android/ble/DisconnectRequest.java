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
/*    */ public class DisconnectRequest
/*    */   extends TimeoutableRequest
/*    */ {
/*    */   DisconnectRequest(@NonNull Request.Type type) {
/* 39 */     super(type);
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   DisconnectRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
/* 45 */     super.setRequestHandler(requestHandler);
/* 46 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   public DisconnectRequest setHandler(@Nullable Handler handler) {
/* 52 */     super.setHandler(handler);
/* 53 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   public DisconnectRequest timeout(@IntRange(from = 0L) long timeout) {
/* 59 */     super.timeout(timeout);
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   public DisconnectRequest done(@NonNull SuccessCallback callback) {
/* 66 */     super.done(callback);
/* 67 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   public DisconnectRequest fail(@NonNull FailCallback callback) {
/* 73 */     super.fail(callback);
/* 74 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   public DisconnectRequest invalid(@NonNull InvalidRequestCallback callback) {
/* 80 */     super.invalid(callback);
/* 81 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   public DisconnectRequest before(@NonNull BeforeCallback callback) {
/* 87 */     super.before(callback);
/* 88 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   public DisconnectRequest then(@NonNull AfterCallback callback) {
/* 94 */     super.then(callback);
/* 95 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\DisconnectRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */