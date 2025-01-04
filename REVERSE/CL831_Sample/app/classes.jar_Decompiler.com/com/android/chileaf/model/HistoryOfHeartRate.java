package com.android.chileaf.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class HistoryOfHeartRate implements Parcelable {
   public long stamp;
   public int heartRate;
   public static final Creator<HistoryOfHeartRate> CREATOR = new Creator<HistoryOfHeartRate>() {
      public HistoryOfHeartRate createFromParcel(Parcel in) {
         return new HistoryOfHeartRate(in);
      }

      public HistoryOfHeartRate[] newArray(int size) {
         return new HistoryOfHeartRate[size];
      }
   };

   public HistoryOfHeartRate(long stamp, int heartRate) {
      this.stamp = stamp;
      this.heartRate = heartRate;
   }

   public String toString() {
      return "HistoryOfHeartRate{startTime=" + this.stamp + ", heartRate=" + this.heartRate + '}';
   }

   protected HistoryOfHeartRate(Parcel in) {
      this.stamp = in.readLong();
      this.heartRate = in.readInt();
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeLong(this.stamp);
      dest.writeInt(this.heartRate);
   }

   public int describeContents() {
      return 0;
   }
}
