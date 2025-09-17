/*    */ package com.android.chileaf.model;
/*    */ 
/*    */ import android.os.Parcel;
/*    */ import android.os.Parcelable;
/*    */ 
/*    */ 
/*    */ public class HistoryOf3D
/*    */   implements Parcelable
/*    */ {
/*    */   public int accX;
/*    */   public int accY;
/*    */   public int accZ;
/*    */   
/*    */   public HistoryOf3D(int accX, int accY, int accZ) {
/* 15 */     this.accX = accX;
/* 16 */     this.accY = accY;
/* 17 */     this.accZ = accZ;
/*    */   }
/*    */   
/*    */   protected HistoryOf3D(Parcel in) {
/* 21 */     this.accX = in.readInt();
/* 22 */     this.accY = in.readInt();
/* 23 */     this.accZ = in.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 28 */     return "HistoryOf3D{  accX=" + this.accX + ", accY=" + this.accY + ", accZ=" + this.accZ + '}';
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   public static final Parcelable.Creator<HistoryOf3D> CREATOR = new Parcelable.Creator<HistoryOf3D>()
/*    */     {
/*    */       public HistoryOf3D createFromParcel(Parcel in) {
/* 38 */         return new HistoryOf3D(in);
/*    */       }
/*    */ 
/*    */       
/*    */       public HistoryOf3D[] newArray(int size) {
/* 43 */         return new HistoryOf3D[size];
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   public int describeContents() {
/* 49 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToParcel(Parcel dest, int flags) {
/* 54 */     dest.writeInt(this.accX);
/* 55 */     dest.writeInt(this.accY);
/* 56 */     dest.writeInt(this.accZ);
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\model\HistoryOf3D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */