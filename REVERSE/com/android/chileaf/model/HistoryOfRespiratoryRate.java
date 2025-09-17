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
/*    */ public class HistoryOfRespiratoryRate
/*    */   implements Parcelable
/*    */ {
/*    */   public long stamp;
/*    */   public int respiratoryRate;
/*    */   
/*    */   public HistoryOfRespiratoryRate(long stamp, int respiratoryRate) {
/* 20 */     this.stamp = stamp;
/* 21 */     this.respiratoryRate = respiratoryRate;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 26 */     return "HistoryOfHeartRate{stamp=" + this.stamp + ", respiratoryRate=" + this.respiratoryRate + '}';
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected HistoryOfRespiratoryRate(Parcel in) {
/* 33 */     this.stamp = in.readLong();
/* 34 */     this.respiratoryRate = in.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToParcel(Parcel dest, int flags) {
/* 39 */     dest.writeLong(this.stamp);
/* 40 */     dest.writeInt(this.respiratoryRate);
/*    */   }
/*    */ 
/*    */   
/*    */   public int describeContents() {
/* 45 */     return 0;
/*    */   }
/*    */   
/* 48 */   public static final Parcelable.Creator<HistoryOfRespiratoryRate> CREATOR = new Parcelable.Creator<HistoryOfRespiratoryRate>()
/*    */     {
/*    */       public HistoryOfRespiratoryRate createFromParcel(Parcel in) {
/* 51 */         return new HistoryOfRespiratoryRate(in);
/*    */       }
/*    */ 
/*    */       
/*    */       public HistoryOfRespiratoryRate[] newArray(int size) {
/* 56 */         return new HistoryOfRespiratoryRate[size];
/*    */       }
/*    */     };
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\model\HistoryOfRespiratoryRate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */