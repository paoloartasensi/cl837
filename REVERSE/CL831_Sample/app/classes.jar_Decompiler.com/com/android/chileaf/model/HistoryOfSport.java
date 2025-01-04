package com.android.chileaf.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class HistoryOfSport implements Parcelable {
   public long startTime;
   public long endTime;
   public long step;
   public long calorie;
   public static final Creator<HistoryOfSport> CREATOR = new Creator<HistoryOfSport>() {
      public HistoryOfSport createFromParcel(Parcel in) {
         return new HistoryOfSport(in);
      }

      public HistoryOfSport[] newArray(int size) {
         return new HistoryOfSport[size];
      }
   };

   public HistoryOfSport(long startTime, long step, long calorie) {
      this.startTime = startTime;
      this.step = step;
      this.calorie = calorie;
   }

   public HistoryOfSport(long startTime, long endTime, long step, long calorie) {
      this.startTime = startTime;
      this.endTime = endTime;
      this.step = step;
      this.calorie = calorie;
   }

   public String toString() {
      return "HistoryOfSport{startTime=" + this.startTime + ", endTime=" + this.endTime + ", step=" + this.step + ", calorie=" + this.calorie + '}';
   }

   protected HistoryOfSport(Parcel in) {
      this.startTime = in.readLong();
      this.step = in.readLong();
      this.calorie = in.readLong();
      this.endTime = in.readLong();
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeLong(this.startTime);
      dest.writeLong(this.step);
      dest.writeLong(this.calorie);
      dest.writeLong(this.endTime);
   }

   public int describeContents() {
      return 0;
   }
}
