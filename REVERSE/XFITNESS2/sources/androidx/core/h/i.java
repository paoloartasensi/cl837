package androidx.core.h;

import android.view.MotionEvent;

/* compiled from: MotionEventCompat */
public final class i {
    @Deprecated
    public static int a(MotionEvent motionEvent) {
        return motionEvent.getActionMasked();
    }

    public static boolean a(MotionEvent motionEvent, int i2) {
        return (motionEvent.getSource() & i2) == i2;
    }
}
