/*    */ package no.nordicsemi.android.ble.response;
/*    */ 
/*    */ import android.bluetooth.BluetoothDevice;
/*    */ import android.os.Parcel;
/*    */ import android.os.Parcelable;
/*    */ import androidx.annotation.IntRange;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import no.nordicsemi.android.ble.callback.MtuCallback;
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
/*    */ public class MtuResult
/*    */   implements MtuCallback, Parcelable
/*    */ {
/*    */   private BluetoothDevice device;
/*    */   @IntRange(from = 23L, to = 517L)
/*    */   private int mtu;
/*    */   
/*    */   public MtuResult() {}
/*    */   
/*    */   public void onMtuChanged(@NonNull BluetoothDevice device, @IntRange(from = 23L, to = 517L) int mtu) {
/* 48 */     this.device = device;
/* 49 */     this.mtu = mtu;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public BluetoothDevice getBluetoothDevice() {
/* 54 */     return this.device;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @IntRange(from = 23L, to = 517L)
/*    */   public int getMtu() {
/* 64 */     return this.mtu;
/*    */   }
/*    */ 
/*    */   
/*    */   protected MtuResult(Parcel in) {
/* 69 */     this.device = (BluetoothDevice)in.readParcelable(BluetoothDevice.class.getClassLoader());
/* 70 */     this.mtu = in.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToParcel(Parcel dest, int flags) {
/* 75 */     dest.writeParcelable((Parcelable)this.device, flags);
/* 76 */     dest.writeInt(this.mtu);
/*    */   }
/*    */ 
/*    */   
/*    */   public int describeContents() {
/* 81 */     return 0;
/*    */   }
/*    */   
/* 84 */   public static final Parcelable.Creator<MtuResult> CREATOR = new Parcelable.Creator<MtuResult>()
/*    */     {
/*    */       public MtuResult createFromParcel(Parcel in) {
/* 87 */         return new MtuResult(in);
/*    */       }
/*    */ 
/*    */       
/*    */       public MtuResult[] newArray(int size) {
/* 92 */         return new MtuResult[size];
/*    */       }
/*    */     };
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\response\MtuResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */