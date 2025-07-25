package com.chileaf.fitness.device.wear.cl880.model;

import java.io.Serializable;

public class DrinkConfig implements Serializable {
    public int amEndHH;
    public int amEndMM;
    public int amStartHH;
    public int amStartMM;
    public boolean enable;
    public int interval;
    public int pmEndHH;
    public int pmEndMM;
    public int pmStartHH;
    public int pmStartMM;

    public String toString() {
        return "InactivityConfig{enable=" + this.enable + ", amStartHH=" + this.amStartHH + ", amStartMM=" + this.amStartMM + ", amEndHH=" + this.amEndHH + ", amEndMM=" + this.amEndMM + ", pmStartHH=" + this.pmStartHH + ", pmStartMM=" + this.pmStartMM + ", pmEndHH=" + this.pmEndHH + ", pmEndMM=" + this.pmEndMM + ", interval=" + this.interval + '}';
    }
}
