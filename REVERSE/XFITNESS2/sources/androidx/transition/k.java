package androidx.transition;

import android.view.View;
import android.view.ViewGroup;

/* compiled from: Scene */
public class k {
    private ViewGroup a;
    private Runnable b;

    public void a() {
        Runnable runnable;
        if (a(this.a) == this && (runnable = this.b) != null) {
            runnable.run();
        }
    }

    static void a(View view, k kVar) {
        view.setTag(R$id.transition_current_scene, kVar);
    }

    static k a(View view) {
        return (k) view.getTag(R$id.transition_current_scene);
    }
}
