package com.chileaf.fitness.device.wear.cl880.model;

import java.io.Serializable;

public class SleepConfig implements Serializable {
    public int actionTime = 10;
    public boolean autoWake = true;
    public int autoWakeTime = 40;
    public int restWakeHH = 9;
    public int restWakeMM = 30;
    public int startHH = 21;
    public int startMM = 30;
    public int workWakeHH = 8;
    public int workWakeMM = 0;

    public String toString() {
        return "SleepConfig{autoWake=" + this.autoWake + ", startHH=" + this.startHH + ", startMM=" + this.startMM + ", workWakeHH=" + this.workWakeHH + ", workWakeMM=" + this.workWakeMM + ", restWakeHH=" + this.restWakeHH + ", restWakeMM=" + this.restWakeMM + ", actionTime=" + this.actionTime + ", autoWakeTime=" + this.autoWakeTime + '}';
    }
}
