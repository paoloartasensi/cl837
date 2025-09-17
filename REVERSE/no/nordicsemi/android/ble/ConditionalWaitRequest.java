/*     */ package no.nordicsemi.android.ble;
/*     */ 
/*     */ import android.os.Handler;
/*     */ import android.util.Log;
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
/*     */ public final class ConditionalWaitRequest<T>
/*     */   extends AwaitingRequest<T>
/*     */   implements Operation
/*     */ {
/*     */   @NonNull
/*     */   private final Condition<T> condition;
/*     */   @Nullable
/*     */   private final T parameter;
/*     */   private boolean expected = false;
/*     */   
/*     */   ConditionalWaitRequest(@NonNull Request.Type type, @NonNull Condition<T> condition, @Nullable T parameter) {
/*  33 */     super(type);
/*  34 */     this.condition = condition;
/*  35 */     this.parameter = parameter;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   ConditionalWaitRequest<T> setRequestHandler(@NonNull RequestHandler requestHandler) {
/*  41 */     super.setRequestHandler(requestHandler);
/*  42 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ConditionalWaitRequest<T> setHandler(@Nullable Handler handler) {
/*  48 */     super.setHandler(handler);
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ConditionalWaitRequest<T> done(@NonNull SuccessCallback callback) {
/*  55 */     super.done(callback);
/*  56 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ConditionalWaitRequest<T> fail(@NonNull FailCallback callback) {
/*  62 */     super.fail(callback);
/*  63 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ConditionalWaitRequest<T> invalid(@NonNull InvalidRequestCallback callback) {
/*  69 */     super.invalid(callback);
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ConditionalWaitRequest<T> before(@NonNull BeforeCallback callback) {
/*  76 */     super.before(callback);
/*  77 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ConditionalWaitRequest<T> then(@NonNull AfterCallback callback) {
/*  83 */     super.then(callback);
/*  84 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ConditionalWaitRequest<T> negate() {
/*  94 */     this.expected = true;
/*  95 */     return this;
/*     */   }
/*     */   
/*     */   boolean isFulfilled() {
/*     */     try {
/* 100 */       return (this.condition.predicate(this.parameter) == this.expected);
/* 101 */     } catch (Exception e) {
/* 102 */       Log.e("ConditionalWaitRequest", "Error while checking predicate", e);
/* 103 */       return true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface Condition<T> {
/*     */     boolean predicate(@Nullable T param1T);
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\ConditionalWaitRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */