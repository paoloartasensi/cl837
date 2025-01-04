package com.android.chileaf.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class HistoryOfRecord implements Parcelable {
   public long stamp;
   public long record;
   public static final Creator<HistoryOfRecord> CREATOR = new Creator<HistoryOfRecord>() {
      public HistoryOfRecord createFromParcel(Parcel in) {
         return new HistoryOfRecord(in);
      }

      public HistoryOfRecord[] newArray(int size) {
         return new HistoryOfRecord[size];
      }
   };

   public HistoryOfRecord(long stamp, long record) {
      this.stamp = stamp;
      this.record = record;
   }

   public String toString() {
      return "HistoryOfSport{startTime=" + this.stamp + ", record=" + this.record + '}';
   }

   protected HistoryOfRecord(Parcel in) {
      this.stamp = in.readLong();
      this.record = in.readLong();
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeLong(this.stamp);
      dest.writeLong(this.record);
   }

   public int describeContents() {
      return 0;
   }
}
