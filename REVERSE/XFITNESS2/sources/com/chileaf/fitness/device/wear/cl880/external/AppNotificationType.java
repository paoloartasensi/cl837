package com.chileaf.fitness.device.wear.cl880.external;

import java.util.HashMap;

public class AppNotificationType extends HashMap<String, NotificationType> {
    private static AppNotificationType e;

    private AppNotificationType() {
        put("com.mailboxapp", NotificationType.EMAIL);
        put("com.imaeses.squeaky", NotificationType.EMAIL);
        put("com.android.email", NotificationType.EMAIL);
        put("ch.protonmail.android", NotificationType.EMAIL);
        put("security.pEp", NotificationType.EMAIL);
        put("eu.faircode.email", NotificationType.EMAIL);
        put("com.moez.QKSMS", NotificationType.SMS);
        put("com.android.mms", NotificationType.SMS);
        put("com.android.messaging", NotificationType.SMS);
        put("com.sonyericsson.conversations", NotificationType.SMS);
        put("org.smssecure.smssecure", NotificationType.SMS);
        put("com.tencent.mm", NotificationType.WECHAT);
        put("com.tencent.mobileqq", NotificationType.QQ);
        put("com.skype.raider", NotificationType.SKYPE);
        put("com.microsoft.office.lync15", NotificationType.SKYPE);
        put("com.whatsapp", NotificationType.WHATSAPP);
        put("me.zeeroooo.materialfb", NotificationType.FACEBOOK);
        put("it.rignanese.leo.slimfacebook", NotificationType.FACEBOOK);
        put("me.jakelane.wrapperforfacebook", NotificationType.FACEBOOK);
        put("com.facebook.katana", NotificationType.FACEBOOK);
        put("org.indywidualni.fblite", NotificationType.FACEBOOK);
    }

    public static AppNotificationType getInstance() {
        AppNotificationType appNotificationType = e;
        if (appNotificationType != null) {
            return appNotificationType;
        }
        AppNotificationType appNotificationType2 = new AppNotificationType();
        e = appNotificationType2;
        return appNotificationType2;
    }
}
