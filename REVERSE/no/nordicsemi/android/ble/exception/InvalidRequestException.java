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
/*    */ public final class InvalidRequestException
/*    */   extends Exception
/*    */ {
/*    */   private final Request request;
/*    */   
/*    */   public InvalidRequestException(Request request) {
/* 32 */     super("Invalid request");
/* 33 */     this.request = request;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Request getRequest() {
/* 41 */     return this.request;
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\exception\InvalidRequestException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */