package io.objectbox.android;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

/* compiled from: AndroidObjectBrowser */
public class a {
    static Notification.Builder a(Context context, int i2, NotificationManager notificationManager) {
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(new NotificationChannel("objectbox-browser", "ObjectBox Browser", 3));
        }
        if (Build.VERSION.SDK_INT >= 26) {
            builder = new Notification.Builder(context, "objectbox-browser");
        } else {
            builder = new Notification.Builder(context);
        }
        builder.setContentTitle(context.getString(R$string.objectbox_objectBrowserNotificationTitle)).setContentText(context.getString(R$string.objectbox_objectBrowserNotificationText, new Object[]{Integer.valueOf(i2)})).setSmallIcon(R$drawable.objectbox_notification);
        return builder;
    }

    static Intent a(String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        intent.setFlags(268435456);
        return intent;
    }
}
