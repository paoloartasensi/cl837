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
/*    */ public class IntervalStep
/*    */   implements Parcelable
/*    */ {
/*    */   public long stamp;
/*    */   public int steps;
/*    */   
/*    */   public IntervalStep(long stamp, int steps) {
/* 20 */     this.stamp = stamp;
/* 21 */     this.steps = steps;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 26 */     return "IntervalStep{startTime=" + this.stamp + ", steps=" + this.steps + '}';
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected IntervalStep(Parcel in) {
/* 33 */     this.stamp = in.readLong();
/* 34 */     this.steps = in.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToParcel(Parcel dest, int flags) {
/* 39 */     dest.writeLong(this.stamp);
/* 40 */     dest.writeInt(this.steps);
/*    */   }
/*    */ 
/*    */   
/*    */   public int describeContents() {
/* 45 */     return 0;
/*    */   }
/*    */   
/* 48 */   public static final Parcelable.Creator<IntervalStep> CREATOR = new Parcelable.Creator<IntervalStep>()
/*    */     {
/*    */       public IntervalStep createFromParcel(Parcel in) {
/* 51 */         return new IntervalStep(in);
/*    */       }
/*    */ 
/*    */       
/*    */       public IntervalStep[] newArray(int size) {
/* 56 */         return new IntervalStep[size];
/*    */       }
/*    */     };
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\model\IntervalStep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */