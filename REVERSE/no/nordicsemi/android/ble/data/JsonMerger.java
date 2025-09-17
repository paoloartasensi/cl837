/*    */ package no.nordicsemi.android.ble.data;
/*    */ 
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import org.json.JSONArray;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JsonMerger
/*    */   implements DataMerger
/*    */ {
/* 16 */   private String buffer = "";
/*    */ 
/*    */   
/*    */   public boolean merge(@NonNull DataStream output, @Nullable byte[] lastPacket, int index) {
/* 20 */     output.write(lastPacket);
/*    */     
/* 22 */     this.buffer += new String(lastPacket);
/*    */     try {
/* 24 */       new JSONObject(this.buffer);
/* 25 */     } catch (JSONException e) {
/*    */       try {
/* 27 */         new JSONArray(this.buffer);
/* 28 */       } catch (JSONException jsonException) {
/* 29 */         return false;
/*    */       } 
/*    */     } 
/* 32 */     reset();
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void reset() {
/* 40 */     this.buffer = "";
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\data\JsonMerger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */