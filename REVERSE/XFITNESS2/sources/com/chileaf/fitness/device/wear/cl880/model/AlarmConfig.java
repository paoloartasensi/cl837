package com.chileaf.fitness.device.wear.cl880.model;

import java.io.Serializable;

public class AlarmConfig implements Serializable {
    public String content;
    public boolean enable;
    public boolean friday;
    public int hour;
    public int index;
    public int minute;
    public boolean monday;
    public boolean saturday;
    public boolean single;
    public long stamp;
    public boolean sunday;
    public boolean thursday;
    public boolean tuesday;
    public boolean wednesday;

    public String toString() {
        return "AlarmConfig{index=" + this.index + ", content='" + this.content + '\'' + ", enable=" + this.enable + ", stamp=" + this.stamp + ", single=" + this.single + ", hour=" + this.hour + ", minute=" + this.minute + ", saturday=" + this.saturday + ", friday=" + this.friday + ", thursday=" + this.thursday + ", wednesday=" + this.wednesday + ", tuesday=" + this.tuesday + ", monday=" + this.monday + ", sunday=" + this.sunday + '}';
    }
}
