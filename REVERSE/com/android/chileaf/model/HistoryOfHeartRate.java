/*    */ package com.android.chileaf.model;
/*    */ 
/*    */ import android.os.Parcel;
/*    */ import android.os.Parcelable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HistoryOfHeartRate
/*    */   implements Parcelable
/*    */ {
/*    */   public long stamp;
/*    */   public int heartRate;
/*    */   
/*    */   public HistoryOfHeartRate(long stamp, int heartRate) {
/* 20 */     this.stamp = stamp;
/* 21 */     this.heartRate = heartRate;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 26 */     return "HistoryOfHeartRate{startTime=" + this.stamp + ", heartRate=" + this.heartRate + '}';
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected HistoryOfHeartRate(Parcel in) {
/* 33 */     this.stamp = in.readLong();
/* 34 */     this.heartRate = in.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToParcel(Parcel dest, int flags) {
/* 39 */     dest.writeLong(this.stamp);
/* 40 */     dest.writeInt(this.heartRate);
/*    */   }
/*    */ 
/*    */   
/*    */   public int describeContents() {
/* 45 */     return 0;
/*    */   }
/*    */   
/* 48 */   public static final Parcelable.Creator<HistoryOfHeartRate> CREATOR = new Parcelable.Creator<HistoryOfHeartRate>()
/*    */     {
/*    */       public HistoryOfHeartRate createFromParcel(Parcel in) {
/* 51 */         return new HistoryOfHeartRate(in);
/*    */       }
/*    */ 
/*    */       
/*    */       public HistoryOfHeartRate[] newArray(int size) {
/* 56 */         return new HistoryOfHeartRate[size];
/*    */       }
/*    */     };
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\model\HistoryOfHeartRate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */