/*    */ package no.nordicsemi.android.ble.callback.profile;
/*    */ 
/*    */ import android.bluetooth.BluetoothDevice;
/*    */ import android.os.Parcel;
/*    */ import android.os.Parcelable;
/*    */ import androidx.annotation.NonNull;
/*    */ import no.nordicsemi.android.ble.data.Data;
/*    */ import no.nordicsemi.android.ble.response.ReadResponse;
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
/*    */ public class ProfileReadResponse
/*    */   extends ReadResponse
/*    */   implements ProfileDataCallback, Parcelable
/*    */ {
/*    */   private boolean valid = true;
/*    */   
/*    */   public ProfileReadResponse() {}
/*    */   
/*    */   public void onInvalidDataReceived(@NonNull BluetoothDevice device, @NonNull Data data) {
/* 53 */     this.valid = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValid() {
/* 62 */     return this.valid;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ProfileReadResponse(Parcel in) {
/* 67 */     super(in);
/* 68 */     this.valid = (in.readByte() != 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToParcel(Parcel dest, int flags) {
/* 73 */     super.writeToParcel(dest, flags);
/* 74 */     dest.writeByte((byte)(this.valid ? 1 : 0));
/*    */   }
/*    */   
/* 77 */   public static final Parcelable.Creator<ProfileReadResponse> CREATOR = new Parcelable.Creator<ProfileReadResponse>()
/*    */     {
/*    */       public ProfileReadResponse createFromParcel(Parcel in) {
/* 80 */         return new ProfileReadResponse(in);
/*    */       }
/*    */ 
/*    */       
/*    */       public ProfileReadResponse[] newArray(int size) {
/* 85 */         return new ProfileReadResponse[size];
/*    */       }
/*    */     };
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\callback\profile\ProfileReadResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */