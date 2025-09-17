/*    */ package no.nordicsemi.android.ble.response;
/*    */ 
/*    */ import android.bluetooth.BluetoothDevice;
/*    */ import android.os.Parcel;
/*    */ import android.os.Parcelable;
/*    */ import androidx.annotation.IntRange;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import no.nordicsemi.android.ble.callback.RssiCallback;
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
/*    */ public class RssiResult
/*    */   implements RssiCallback, Parcelable
/*    */ {
/*    */   private BluetoothDevice device;
/*    */   @IntRange(from = -128L, to = 20L)
/*    */   private int rssi;
/*    */   
/*    */   public RssiResult() {}
/*    */   
/*    */   public void onRssiRead(@NonNull BluetoothDevice device, @IntRange(from = -128L, to = 20L) int rssi) {
/* 48 */     this.device = device;
/* 49 */     this.rssi = rssi;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public BluetoothDevice getBluetoothDevice() {
/* 54 */     return this.device;
/*    */   }
/*    */   
/*    */   @IntRange(from = -128L, to = 20L)
/*    */   public int getRssi() {
/* 59 */     return this.rssi;
/*    */   }
/*    */ 
/*    */   
/*    */   protected RssiResult(Parcel in) {
/* 64 */     this.device = (BluetoothDevice)in.readParcelable(BluetoothDevice.class.getClassLoader());
/* 65 */     this.rssi = in.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToParcel(Parcel dest, int flags) {
/* 70 */     dest.writeParcelable((Parcelable)this.device, flags);
/* 71 */     dest.writeInt(this.rssi);
/*    */   }
/*    */ 
/*    */   
/*    */   public int describeContents() {
/* 76 */     return 0;
/*    */   }
/*    */   
/* 79 */   public static final Parcelable.Creator<RssiResult> CREATOR = new Parcelable.Creator<RssiResult>()
/*    */     {
/*    */       public RssiResult createFromParcel(Parcel in) {
/* 82 */         return new RssiResult(in);
/*    */       }
/*    */ 
/*    */       
/*    */       public RssiResult[] newArray(int size) {
/* 87 */         return new RssiResult[size];
/*    */       }
/*    */     };
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\response\RssiResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */