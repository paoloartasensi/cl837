package com.chileaf.fitness.device.wear.cl880.model;

import java.io.Serializable;

public class MessageConfig implements Serializable {
    public boolean email;
    public boolean facebook;
    public boolean missedCall;
    public boolean other;
    public boolean qq;
    public boolean skype;
    public boolean sms;
    public boolean wechat;
    public boolean whatsApp;

    public String toString() {
        return "MessageConfig{facebook=" + this.facebook + ", whatsApp=" + this.whatsApp + ", skype=" + this.skype + ", qq=" + this.qq + ", wechat=" + this.wechat + ", sms=" + this.sms + ", email=" + this.email + ", missedCall=" + this.missedCall + ", other=" + this.other + '}';
    }
}
