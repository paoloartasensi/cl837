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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HistoryOfSport
/*    */   implements Parcelable
/*    */ {
/*    */   public long startTime;
/*    */   public long endTime;
/*    */   public long step;
/*    */   public long calorie;
/*    */   
/*    */   public HistoryOfSport(long startTime, long step, long calorie) {
/* 28 */     this.startTime = startTime;
/* 29 */     this.step = step;
/* 30 */     this.calorie = calorie;
/*    */   }
/*    */   
/*    */   public HistoryOfSport(long startTime, long endTime, long step, long calorie) {
/* 34 */     this.startTime = startTime;
/* 35 */     this.endTime = endTime;
/* 36 */     this.step = step;
/* 37 */     this.calorie = calorie;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 42 */     return "HistoryOfSport{startTime=" + this.startTime + ", endTime=" + this.endTime + ", step=" + this.step + ", calorie=" + this.calorie + '}';
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected HistoryOfSport(Parcel in) {
/* 51 */     this.startTime = in.readLong();
/* 52 */     this.step = in.readLong();
/* 53 */     this.calorie = in.readLong();
/* 54 */     this.endTime = in.readLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToParcel(Parcel dest, int flags) {
/* 59 */     dest.writeLong(this.startTime);
/* 60 */     dest.writeLong(this.step);
/* 61 */     dest.writeLong(this.calorie);
/* 62 */     dest.writeLong(this.endTime);
/*    */   }
/*    */ 
/*    */   
/*    */   public int describeContents() {
/* 67 */     return 0;
/*    */   }
/*    */   
/* 70 */   public static final Parcelable.Creator<HistoryOfSport> CREATOR = new Parcelable.Creator<HistoryOfSport>()
/*    */     {
/*    */       public HistoryOfSport createFromParcel(Parcel in) {
/* 73 */         return new HistoryOfSport(in);
/*    */       }
/*    */ 
/*    */       
/*    */       public HistoryOfSport[] newArray(int size) {
/* 78 */         return new HistoryOfSport[size];
/*    */       }
/*    */     };
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\model\HistoryOfSport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */