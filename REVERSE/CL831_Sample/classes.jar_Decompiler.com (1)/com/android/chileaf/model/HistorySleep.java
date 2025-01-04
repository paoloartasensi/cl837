package com.android.chileaf.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class HistorySleep implements Parcelable {
   public long utc;
   public int[] actions;
   public static final Creator<HistorySleep> CREATOR = new Creator<HistorySleep>() {
      public HistorySleep createFromParcel(Parcel in) {
         return new HistorySleep(in);
      }

      public HistorySleep[] newArray(int size) {
         return new HistorySleep[size];
      }
   };

   public HistorySleep() {
   }

   public HistorySleep(long utc, int[] actions) {
      this.utc = utc;
      this.actions = actions;
   }

   protected HistorySleep(Parcel in) {
      this.utc = in.readLong();
      this.actions = in.createIntArray();
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeLong(this.utc);
      dest.writeIntArray(this.actions);
   }

   public int describeContents() {
      return 0;
   }
}
