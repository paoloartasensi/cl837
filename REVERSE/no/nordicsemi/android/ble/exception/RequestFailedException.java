/*    */ package no.nordicsemi.android.ble.exception;
/*    */ 
/*    */ import no.nordicsemi.android.ble.Request;
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
/*    */ public final class RequestFailedException
/*    */   extends Exception
/*    */ {
/*    */   private final Request request;
/*    */   private final int status;
/*    */   
/*    */   public RequestFailedException(Request request, int status) {
/* 33 */     super("Request failed with status " + status);
/* 34 */     this.request = request;
/* 35 */     this.status = status;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getStatus() {
/* 45 */     return this.status;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Request getRequest() {
/* 53 */     return this.request;
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\exception\RequestFailedException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */