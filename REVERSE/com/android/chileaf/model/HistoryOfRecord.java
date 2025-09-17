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
/*    */ public class HistoryOfRecord
/*    */   implements Parcelable
/*    */ {
/*    */   public long stamp;
/*    */   public long record;
/*    */   
/*    */   public HistoryOfRecord(long stamp, long record) {
/* 20 */     this.stamp = stamp;
/* 21 */     this.record = record;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 26 */     return "HistoryOfSport{startTime=" + this.stamp + ", record=" + this.record + '}';
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected HistoryOfRecord(Parcel in) {
/* 33 */     this.stamp = in.readLong();
/* 34 */     this.record = in.readLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToParcel(Parcel dest, int flags) {
/* 39 */     dest.writeLong(this.stamp);
/* 40 */     dest.writeLong(this.record);
/*    */   }
/*    */ 
/*    */   
/*    */   public int describeContents() {
/* 45 */     return 0;
/*    */   }
/*    */   
/* 48 */   public static final Parcelable.Creator<HistoryOfRecord> CREATOR = new Parcelable.Creator<HistoryOfRecord>()
/*    */     {
/*    */       public HistoryOfRecord createFromParcel(Parcel in) {
/* 51 */         return new HistoryOfRecord(in);
/*    */       }
/*    */ 
/*    */       
/*    */       public HistoryOfRecord[] newArray(int size) {
/* 56 */         return new HistoryOfRecord[size];
/*    */       }
/*    */     };
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\model\HistoryOfRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */