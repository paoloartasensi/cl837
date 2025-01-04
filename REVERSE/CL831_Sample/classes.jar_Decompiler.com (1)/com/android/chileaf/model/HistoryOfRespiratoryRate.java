package com.android.chileaf.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class HistoryOfRespiratoryRate implements Parcelable {
   public long stamp;
   public int respiratoryRate;
   public static final Creator<HistoryOfRespiratoryRate> CREATOR = new Creator<HistoryOfRespiratoryRate>() {
      public HistoryOfRespiratoryRate createFromParcel(Parcel in) {
         return new HistoryOfRespiratoryRate(in);
      }

      public HistoryOfRespiratoryRate[] newArray(int size) {
         return new HistoryOfRespiratoryRate[size];
      }
   };

   public HistoryOfRespiratoryRate(long stamp, int respiratoryRate) {
      this.stamp = stamp;
      this.respiratoryRate = respiratoryRate;
   }

   public String toString() {
      return "HistoryOfHeartRate{stamp=" + this.stamp + ", respiratoryRate=" + this.respiratoryRate + '}';
   }

   protected HistoryOfRespiratoryRate(Parcel in) {
      this.stamp = in.readLong();
      this.respiratoryRate = in.readInt();
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeLong(this.stamp);
      dest.writeInt(this.respiratoryRate);
   }

   public int describeContents() {
      return 0;
   }
}
