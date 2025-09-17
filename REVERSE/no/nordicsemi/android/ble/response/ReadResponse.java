/*    */ package no.nordicsemi.android.ble.response;
/*    */ 
/*    */ import android.bluetooth.BluetoothDevice;
/*    */ import android.os.Parcel;
/*    */ import android.os.Parcelable;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import no.nordicsemi.android.ble.callback.DataReceivedCallback;
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
/*    */ 
/*    */ public class ReadResponse
/*    */   implements DataReceivedCallback, Parcelable
/*    */ {
/*    */   private BluetoothDevice device;
/*    */   private Data data;
/*    */   
/*    */   public ReadResponse() {}
/*    */   
/*    */   public void onDataReceived(@NonNull BluetoothDevice device, @NonNull Data data) {
/* 51 */     this.device = device;
/* 52 */     this.data = data;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public BluetoothDevice getBluetoothDevice() {
/* 57 */     return this.device;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Data getRawData() {
/* 62 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ReadResponse(Parcel in) {
/* 67 */     this.device = (BluetoothDevice)in.readParcelable(BluetoothDevice.class.getClassLoader());
/* 68 */     this.data = (Data)in.readParcelable(Data.class.getClassLoader());
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToParcel(Parcel dest, int flags) {
/* 73 */     dest.writeParcelable((Parcelable)this.device, flags);
/* 74 */     dest.writeParcelable((Parcelable)this.data, flags);
/*    */   }
/*    */ 
/*    */   
/*    */   public int describeContents() {
/* 79 */     return 0;
/*    */   }
/*    */   
/* 82 */   public static final Parcelable.Creator<ReadResponse> CREATOR = new Parcelable.Creator<ReadResponse>()
/*    */     {
/*    */       public ReadResponse createFromParcel(Parcel in) {
/* 85 */         return new ReadResponse(in);
/*    */       }
/*    */ 
/*    */       
/*    */       public ReadResponse[] newArray(int size) {
/* 90 */         return new ReadResponse[size];
/*    */       }
/*    */     };
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\response\ReadResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */