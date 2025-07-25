package com.chileaf.fitness.device.wear.cl880.external;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import androidx.core.app.h;
import com.jeremyliao.liveeventbus.BuildConfig;
import j.a.a;
import java.util.HashMap;

public class NotificationListener extends NotificationListenerService {
    private long e;

    /* renamed from: f  reason: collision with root package name */
    private HashMap<String, Long> f1219f = new HashMap<>();

    /* renamed from: g  reason: collision with root package name */
    private HashMap<String, Long> f1220g = new HashMap<>();

    private void a(StatusBarNotification statusBarNotification) {
        String packageName = statusBarNotification.getPackageName();
        a.a("got call from: %s", packageName);
        Notification notification = statusBarNotification.getNotification();
        if (Build.VERSION.SDK_INT >= 19) {
            a(notification.extras);
            Notification.Action[] actionArr = notification.actions;
            if (actionArr != null && actionArr.length > 0) {
                int length = actionArr.length;
                for (int i2 = 0; i2 < length; i2++) {
                    a.c("Found call action: %s", actionArr[i2].title);
                }
            }
            if (notification.extras.containsKey("android.people")) {
                packageName = notification.extras.getString("android.people");
            } else if (notification.extras.containsKey("android.title")) {
                packageName = notification.extras.getString("android.title");
            } else {
                String a = a(packageName);
                if (a != null) {
                    packageName = a;
                }
            }
            this.e = statusBarNotification.getPostTime();
            b bVar = new b();
            bVar.a = packageName;
            bVar.c = 1;
            c.l().a(bVar);
        }
    }

    private String b(String str) {
        return str.replaceAll("\\p{C}", BuildConfig.FLAVOR);
    }

    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
        if (Build.VERSION.SDK_INT < 21 || !"call".equals(statusBarNotification.getNotification().category)) {
            String lowerCase = statusBarNotification.getPackageName().toLowerCase();
            Notification notification = statusBarNotification.getNotification();
            if (!this.f1220g.containsKey(lowerCase) || notification.when > this.f1220g.get(lowerCase).longValue()) {
                long currentTimeMillis = System.currentTimeMillis();
                if (this.f1219f.containsKey(lowerCase)) {
                    this.f1219f.get(lowerCase).longValue();
                }
                a aVar = new a();
                aVar.b = currentTimeMillis;
                aVar.e = lowerCase;
                NotificationType notificationType = (NotificationType) AppNotificationType.getInstance().get(lowerCase);
                aVar.f1222f = notificationType;
                if (notificationType == null) {
                    aVar.f1222f = NotificationType.OTHER;
                }
                a(notification, aVar);
                if (!new h.d(notification).a().isEmpty() || !h.b(notification)) {
                    this.f1219f.put(lowerCase, Long.valueOf(currentTimeMillis));
                    long j2 = notification.when;
                    if (0 != j2) {
                        this.f1220g.put(lowerCase, Long.valueOf(j2));
                    }
                    c.l().a(aVar);
                    return;
                }
                return;
            }
            return;
        }
        a(statusBarNotification);
    }

    public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
        a.c("Notification removed: %s", statusBarNotification.getPackageName());
        if (Build.VERSION.SDK_INT >= 21 && "call".equals(statusBarNotification.getNotification().category) && this.e == statusBarNotification.getPostTime()) {
            this.e = 0;
            b bVar = new b();
            bVar.b = BuildConfig.FLAVOR;
            bVar.c = 0;
            c.l().a(bVar);
        }
    }

    private String a(String str) {
        PackageManager packageManager = getPackageManager();
        try {
            return (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, 0));
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @TargetApi(19)
    private void a(Notification notification, a aVar) {
        Bundle a = h.a(notification);
        if (a != null) {
            a(a);
            CharSequence charSequence = a.getCharSequence("android.title");
            if (charSequence != null) {
                aVar.c = b(charSequence.toString());
            }
            CharSequence charSequence2 = null;
            if (a.containsKey("android.bigText")) {
                charSequence2 = a.getCharSequence("android.bigText");
            } else if (a.containsKey("android.text")) {
                charSequence2 = a.getCharSequence("android.text");
            }
            if (charSequence2 != null) {
                aVar.d = b(charSequence2.toString());
            }
        }
    }

    private void a(Bundle bundle) {
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
        }
    }
}
