package androidx.legacy.content;

import android.content.BroadcastReceiver;
import android.util.SparseArray;

@Deprecated
public abstract class WakefulBroadcastReceiver extends BroadcastReceiver {
    static {
        new SparseArray();
    }
}
