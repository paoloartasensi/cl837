package com.chileaf.fitness.device.wear.cl880.external;

public enum NotificationType {
    MISSEDCALL(0, "未接来电"),
    EMAIL(1, "EMAIL"),
    SMS(2, "Sms"),
    WECHAT(3, "Wechat"),
    QQ(4, "QQ"),
    SKYPE(5, "Skype"),
    WHATSAPP(6, "WhatsApp"),
    FACEBOOK(7, "Facebook"),
    OTHER(8, "Other");
    
    public final int key;
    public final String title;

    private NotificationType(int i2, String str) {
        this.key = i2;
        this.title = str;
    }
}
