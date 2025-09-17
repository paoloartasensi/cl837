/*    */ package no.nordicsemi.android.ble.response;
/*    */ 
/*    */ import android.bluetooth.BluetoothDevice;
/*    */ import android.os.Parcel;
/*    */ import android.os.Parcelable;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import no.nordicsemi.android.ble.callback.DataSentCallback;
/*    */ import no.nordicsemi.android.ble.data.Data;
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
/*    */ public class WriteResponse
/*    */   implements DataSentCallback, Parcelable
/*    */ {
/*    */   private BluetoothDevice device;
/*    */   private Data data;
/*    */   
/*    */   public WriteResponse() {}
/*    */   
/*    */   public void onDataSent(@NonNull BluetoothDevice device, @NonNull Data data) {
/* 50 */     this.device = device;
/* 51 */     this.data = data;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public BluetoothDevice getBluetoothDevice() {
/* 56 */     return this.device;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Data getRawData() {
/* 61 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   protected WriteResponse(Parcel in) {
/* 66 */     this.device = (BluetoothDevice)in.readParcelable(BluetoothDevice.class.getClassLoader());
/* 67 */     this.data = (Data)in.readParcelable(Data.class.getClassLoader());
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToParcel(Parcel dest, int flags) {
/* 72 */     dest.writeParcelable((Parcelable)this.device, flags);
/* 73 */     dest.writeParcelable((Parcelable)this.data, flags);
/*    */   }
/*    */ 
/*    */   
/*    */   public int describeContents() {
/* 78 */     return 0;
/*    */   }
/*    */   
/* 81 */   public static final Parcelable.Creator<WriteResponse> CREATOR = new Parcelable.Creator<WriteResponse>()
/*    */     {
/*    */       public WriteResponse createFromParcel(Parcel in) {
/* 84 */         return new WriteResponse(in);
/*    */       }
/*    */ 
/*    */       
/*    */       public WriteResponse[] newArray(int size) {
/* 89 */         return new WriteResponse[size];
/*    */       }
/*    */     };
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\response\WriteResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */