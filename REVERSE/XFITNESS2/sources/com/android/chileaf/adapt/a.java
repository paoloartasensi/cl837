package com.android.chileaf.adapt;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.j;

/* compiled from: ActivityLifecycleCallbacksImpl */
public class a implements Application.ActivityLifecycleCallbacks {
    private b a;
    private f b;

    public a(b bVar) {
        this.b = new f(bVar);
        this.a = bVar;
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (d.u().q() && (activity instanceof FragmentActivity)) {
            ((FragmentActivity) activity).e().a((j.f) this.b, true);
        }
        b bVar = this.a;
        if (bVar != null) {
            bVar.a(activity, activity);
        }
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
        b bVar = this.a;
        if (bVar != null) {
            bVar.a(activity, activity);
        }
    }

    public void onActivityStopped(Activity activity) {
    }
}
