package androidx.core.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: TaskStackBuilder */
public final class m implements Iterable<Intent> {
    private final ArrayList<Intent> e = new ArrayList<>();

    /* renamed from: f  reason: collision with root package name */
    private final Context f471f;

    /* compiled from: TaskStackBuilder */
    public interface a {
        Intent c();
    }

    private m(Context context) {
        this.f471f = context;
    }

    public static m a(Context context) {
        return new m(context);
    }

    public m b(Intent intent) {
        ComponentName component = intent.getComponent();
        if (component == null) {
            component = intent.resolveActivity(this.f471f.getPackageManager());
        }
        if (component != null) {
            a(component);
        }
        a(intent);
        return this;
    }

    @Deprecated
    public Iterator<Intent> iterator() {
        return this.e.iterator();
    }

    public m a(Intent intent) {
        this.e.add(intent);
        return this;
    }

    public m a(Activity activity) {
        Intent c = activity instanceof a ? ((a) activity).c() : null;
        if (c == null) {
            c = f.a(activity);
        }
        if (c != null) {
            ComponentName component = c.getComponent();
            if (component == null) {
                component = c.resolveActivity(this.f471f.getPackageManager());
            }
            a(component);
            a(c);
        }
        return this;
    }

    public void b() {
        a((Bundle) null);
    }

    public m a(ComponentName componentName) {
        int size = this.e.size();
        try {
            Intent a2 = f.a(this.f471f, componentName);
            while (a2 != null) {
                this.e.add(size, a2);
                a2 = f.a(this.f471f, a2.getComponent());
            }
            return this;
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e("TaskStackBuilder", "Bad ComponentName while traversing activity parent metadata");
            throw new IllegalArgumentException(e2);
        }
    }

    public int a() {
        return this.e.size();
    }

    public Intent a(int i2) {
        return this.e.get(i2);
    }

    public void a(Bundle bundle) {
        if (!this.e.isEmpty()) {
            ArrayList<Intent> arrayList = this.e;
            Intent[] intentArr = (Intent[]) arrayList.toArray(new Intent[arrayList.size()]);
            intentArr[0] = new Intent(intentArr[0]).addFlags(268484608);
            if (!androidx.core.content.a.a(this.f471f, intentArr, bundle)) {
                Intent intent = new Intent(intentArr[intentArr.length - 1]);
                intent.addFlags(268435456);
                this.f471f.startActivity(intent);
                return;
            }
            return;
        }
        throw new IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
    }
}
