package io.objectbox.android;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public class AndroidObjectBrowserService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int i2, int i3) {
        if ("objectBox_objectBrowserStop".equals(intent.getAction())) {
            Log.d("ObjectBrowserService", "Stopping");
            stopForeground(true);
            stopSelf();
            return 2;
        }
        String stringExtra = intent.getStringExtra("url");
        int intExtra = intent.getIntExtra("port", 0);
        int intExtra2 = intent.getIntExtra("notificationId", 0);
        if (stringExtra == null || !stringExtra.startsWith("http") || intExtra <= 0 || intExtra2 <= 0) {
            Log.w("ObjectBrowserService", "Ignoring start command due to incomplete data");
            return 2;
        }
        Intent intent2 = new Intent(this, AndroidObjectBrowserService.class);
        intent2.setAction("objectBox_objectBrowserStop");
        PendingIntent service = PendingIntent.getService(this, 0, intent2, 268435456);
        PendingIntent activity = PendingIntent.getActivity(this, 0, a.a(stringExtra), 0);
        Notification.Builder a = a.a(this, intExtra, (NotificationManager) getSystemService("notification"));
        a.setContentIntent(activity);
        a.setDeleteIntent(service);
        if (Build.VERSION.SDK_INT >= 20) {
            a.addAction(new Notification.Action.Builder(R$drawable.objectbox_stop, "Stop", service).build());
        }
        startForeground(intExtra2, a.getNotification());
        Log.d("ObjectBrowserService", "Started");
        return 3;
    }
}
