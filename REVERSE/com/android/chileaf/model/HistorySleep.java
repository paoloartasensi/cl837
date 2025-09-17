/*    */ package com.android.chileaf.model;
/*    */ 
/*    */ import android.os.Parcel;
/*    */ import android.os.Parcelable;
/*    */ 
/*    */ public class HistorySleep
/*    */   implements Parcelable
/*    */ {
/*    */   public long utc;
/*    */   public int[] actions;
/*    */   
/*    */   public HistorySleep() {}
/*    */   
/*    */   public HistorySleep(long utc, int[] actions) {
/* 15 */     this.utc = utc;
/* 16 */     this.actions = actions;
/*    */   }
/*    */   
/*    */   protected HistorySleep(Parcel in) {
/* 20 */     this.utc = in.readLong();
/* 21 */     this.actions = in.createIntArray();
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToParcel(Parcel dest, int flags) {
/* 26 */     dest.writeLong(this.utc);
/* 27 */     dest.writeIntArray(this.actions);
/*    */   }
/*    */ 
/*    */   
/*    */   public int describeContents() {
/* 32 */     return 0;
/*    */   }
/*    */   
/* 35 */   public static final Parcelable.Creator<HistorySleep> CREATOR = new Parcelable.Creator<HistorySleep>()
/*    */     {
/*    */       public HistorySleep createFromParcel(Parcel in) {
/* 38 */         return new HistorySleep(in);
/*    */       }
/*    */ 
/*    */       
/*    */       public HistorySleep[] newArray(int size) {
/* 43 */         return new HistorySleep[size];
/*    */       }
/*    */     };
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\model\HistorySleep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */