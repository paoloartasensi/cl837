package com.chileaf.fitness.device.wear.cl880.model;

import java.io.Serializable;

public class SportDayHistory implements Serializable {
    public int calorie;
    public long stamp;
    public int step;

    public SportDayHistory(long j2, int i2, int i3) {
        this.stamp = j2;
        this.step = i2;
        this.calorie = i3;
    }

    public String toString() {
        return "SportHistory{stamp=" + this.stamp + ", step=" + this.step + ", calorie=" + this.calorie + '}';
    }
}
