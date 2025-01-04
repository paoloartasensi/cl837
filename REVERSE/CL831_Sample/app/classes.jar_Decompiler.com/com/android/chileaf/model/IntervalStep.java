package com.android.chileaf.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class IntervalStep implements Parcelable {
   public long stamp;
   public int steps;
   public static final Creator<IntervalStep> CREATOR = new Creator<IntervalStep>() {
      public IntervalStep createFromParcel(Parcel in) {
         return new IntervalStep(in);
      }

      public IntervalStep[] newArray(int size) {
         return new IntervalStep[size];
      }
   };

   public IntervalStep(long stamp, int steps) {
      this.stamp = stamp;
      this.steps = steps;
   }

   public String toString() {
      return "IntervalStep{startTime=" + this.stamp + ", steps=" + this.steps + '}';
   }

   protected IntervalStep(Parcel in) {
      this.stamp = in.readLong();
      this.steps = in.readInt();
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeLong(this.stamp);
      dest.writeInt(this.steps);
   }

   public int describeContents() {
      return 0;
   }
}
