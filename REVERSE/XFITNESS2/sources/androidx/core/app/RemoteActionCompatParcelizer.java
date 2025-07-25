package androidx.core.app;

import android.app.PendingIntent;
import android.os.Parcelable;
import androidx.core.graphics.drawable.IconCompat;
import androidx.versionedparcelable.a;
import androidx.versionedparcelable.c;

public class RemoteActionCompatParcelizer {
    public static RemoteActionCompat read(a aVar) {
        RemoteActionCompat remoteActionCompat = new RemoteActionCompat();
        remoteActionCompat.a = (IconCompat) aVar.a(remoteActionCompat.a, 1);
        remoteActionCompat.b = aVar.a(remoteActionCompat.b, 2);
        remoteActionCompat.c = aVar.a(remoteActionCompat.c, 3);
        remoteActionCompat.d = (PendingIntent) aVar.a(remoteActionCompat.d, 4);
        remoteActionCompat.e = aVar.a(remoteActionCompat.e, 5);
        remoteActionCompat.f445f = aVar.a(remoteActionCompat.f445f, 6);
        return remoteActionCompat;
    }

    public static void write(RemoteActionCompat remoteActionCompat, a aVar) {
        aVar.a(false, false);
        aVar.b((c) remoteActionCompat.a, 1);
        aVar.b(remoteActionCompat.b, 2);
        aVar.b(remoteActionCompat.c, 3);
        aVar.b((Parcelable) remoteActionCompat.d, 4);
        aVar.b(remoteActionCompat.e, 5);
        aVar.b(remoteActionCompat.f445f, 6);
    }
}
