/*    */ package no.nordicsemi.android.ble.exception;
/*    */ 
/*    */ import androidx.annotation.NonNull;
/*    */ import no.nordicsemi.android.ble.callback.profile.ProfileReadResponse;
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
/*    */ public final class InvalidDataException
/*    */   extends Exception
/*    */ {
/*    */   private final ProfileReadResponse response;
/*    */   
/*    */   public InvalidDataException(@NonNull ProfileReadResponse response) {
/* 33 */     this.response = response;
/*    */   }
/*    */   
/*    */   public ProfileReadResponse getResponse() {
/* 37 */     return this.response;
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\exception\InvalidDataException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */