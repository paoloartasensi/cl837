package com.chileaf.fitness.device.wear.cl880.model;

import java.io.Serializable;

public class SleepHistory implements Serializable {
    public int deepSleep;
    public int lightSleep;
    public long stamp;
    public int wakeSleep;

    public SleepHistory(long j2, int i2, int i3, int i4) {
        this.stamp = j2;
        this.wakeSleep = i2;
        this.lightSleep = i3;
        this.deepSleep = i4;
    }

    public String toString() {
        return "SleepHistory{stamp=" + this.stamp + ", wakeSleep=" + this.wakeSleep + ", lightSleep=" + this.lightSleep + ", deepSleep=" + this.deepSleep + '}';
    }
}
