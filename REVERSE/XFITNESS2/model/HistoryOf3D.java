package com.android.chileaf.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class HistoryOf3D implements Parcelable {
   public int accX;
   public int accY;
   public int accZ;
   public static final Creator<HistoryOf3D> CREATOR = new Creator<HistoryOf3D>() {
      public HistoryOf3D createFromParcel(Parcel in) {
         return new HistoryOf3D(in);
      }

      public HistoryOf3D[] newArray(int size) {
         return new HistoryOf3D[size];
      }
   };

   public HistoryOf3D(int accX, int accY, int accZ) {
      this.accX = accX;
      this.accY = accY;
      this.accZ = accZ;
   }

   protected HistoryOf3D(Parcel in) {
      this.accX = in.readInt();
      this.accY = in.readInt();
      this.accZ = in.readInt();
   }

   public String toString() {
      return "HistoryOf3D{  accX=" + this.accX + ", accY=" + this.accY + ", accZ=" + this.accZ + '}';
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.accX);
      dest.writeInt(this.accY);
      dest.writeInt(this.accZ);
   }
}
