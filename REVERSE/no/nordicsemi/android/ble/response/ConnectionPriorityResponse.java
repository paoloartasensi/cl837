/*     */ package no.nordicsemi.android.ble.response;
/*     */ 
/*     */ import android.bluetooth.BluetoothDevice;
/*     */ import android.os.Parcel;
/*     */ import android.os.Parcelable;
/*     */ import androidx.annotation.IntRange;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import no.nordicsemi.android.ble.callback.ConnectionParametersUpdatedCallback;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConnectionPriorityResponse
/*     */   implements ConnectionParametersUpdatedCallback, Parcelable
/*     */ {
/*     */   private BluetoothDevice device;
/*     */   @IntRange(from = 6L, to = 3200L)
/*     */   private int interval;
/*     */   @IntRange(from = 0L, to = 499L)
/*     */   private int latency;
/*     */   @IntRange(from = 10L, to = 3200L)
/*     */   private int supervisionTimeout;
/*     */   
/*     */   public ConnectionPriorityResponse() {}
/*     */   
/*     */   public void onConnectionUpdated(@NonNull BluetoothDevice device, @IntRange(from = 6L, to = 3200L) int interval, @IntRange(from = 0L, to = 499L) int latency, @IntRange(from = 10L, to = 3200L) int timeout) {
/*  61 */     this.device = device;
/*  62 */     this.interval = interval;
/*  63 */     this.latency = latency;
/*  64 */     this.supervisionTimeout = timeout;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BluetoothDevice getBluetoothDevice() {
/*  69 */     return this.device;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @IntRange(from = 6L, to = 3200L)
/*     */   public int getConnectionInterval() {
/*  82 */     return this.interval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @IntRange(from = 0L, to = 499L)
/*     */   public int getSlaveLatency() {
/* 100 */     return this.latency;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @IntRange(from = 10L, to = 3200L)
/*     */   public int getSupervisionTimeout() {
/* 114 */     return this.supervisionTimeout;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ConnectionPriorityResponse(Parcel in) {
/* 119 */     this.device = (BluetoothDevice)in.readParcelable(BluetoothDevice.class.getClassLoader());
/* 120 */     this.interval = in.readInt();
/* 121 */     this.latency = in.readInt();
/* 122 */     this.supervisionTimeout = in.readInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToParcel(Parcel dest, int flags) {
/* 127 */     dest.writeParcelable((Parcelable)this.device, flags);
/* 128 */     dest.writeInt(this.interval);
/* 129 */     dest.writeInt(this.latency);
/* 130 */     dest.writeInt(this.supervisionTimeout);
/*     */   }
/*     */ 
/*     */   
/*     */   public int describeContents() {
/* 135 */     return 0;
/*     */   }
/*     */   
/* 138 */   public static final Parcelable.Creator<ConnectionPriorityResponse> CREATOR = new Parcelable.Creator<ConnectionPriorityResponse>()
/*     */     {
/*     */       public ConnectionPriorityResponse createFromParcel(Parcel in) {
/* 141 */         return new ConnectionPriorityResponse(in);
/*     */       }
/*     */ 
/*     */       
/*     */       public ConnectionPriorityResponse[] newArray(int size) {
/* 146 */         return new ConnectionPriorityResponse[size];
/*     */       }
/*     */     };
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\response\ConnectionPriorityResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */