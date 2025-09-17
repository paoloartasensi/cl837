/*    */ package no.nordicsemi.android.ble.response;
/*    */ 
/*    */ import android.bluetooth.BluetoothDevice;
/*    */ import android.os.Parcel;
/*    */ import android.os.Parcelable;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import no.nordicsemi.android.ble.callback.PhyCallback;
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
/*    */ public class PhyResult
/*    */   implements PhyCallback, Parcelable
/*    */ {
/*    */   private BluetoothDevice device;
/*    */   private int txPhy;
/*    */   private int rxPhy;
/*    */   
/*    */   public PhyResult() {}
/*    */   
/*    */   public void onPhyChanged(@NonNull BluetoothDevice device, int txPhy, int rxPhy) {
/* 51 */     this.device = device;
/* 52 */     this.txPhy = txPhy;
/* 53 */     this.rxPhy = rxPhy;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public BluetoothDevice getBluetoothDevice() {
/* 58 */     return this.device;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTxPhy() {
/* 63 */     return this.txPhy;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRxPhy() {
/* 68 */     return this.rxPhy;
/*    */   }
/*    */ 
/*    */   
/*    */   protected PhyResult(Parcel in) {
/* 73 */     this.device = (BluetoothDevice)in.readParcelable(BluetoothDevice.class.getClassLoader());
/* 74 */     this.txPhy = in.readInt();
/* 75 */     this.rxPhy = in.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToParcel(Parcel dest, int flags) {
/* 80 */     dest.writeParcelable((Parcelable)this.device, flags);
/* 81 */     dest.writeInt(this.txPhy);
/* 82 */     dest.writeInt(this.rxPhy);
/*    */   }
/*    */ 
/*    */   
/*    */   public int describeContents() {
/* 87 */     return 0;
/*    */   }
/*    */   
/* 90 */   public static final Parcelable.Creator<PhyResult> CREATOR = new Parcelable.Creator<PhyResult>()
/*    */     {
/*    */       public PhyResult createFromParcel(Parcel in) {
/* 93 */         return new PhyResult(in);
/*    */       }
/*    */ 
/*    */       
/*    */       public PhyResult[] newArray(int size) {
/* 98 */         return new PhyResult[size];
/*    */       }
/*    */     };
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\response\PhyResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */