package androidx.navigation.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.b.a.d;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.c;
import androidx.navigation.j;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: AbstractAppBarOnDestinationChangedListener */
abstract class a implements NavController.b {
    private final Context a;
    private final Set<Integer> b;
    private final WeakReference<DrawerLayout> c;
    private d d;
    private ValueAnimator e;

    a(Context context, d dVar) {
        this.a = context;
        this.b = dVar.c();
        DrawerLayout a2 = dVar.a();
        if (a2 != null) {
            this.c = new WeakReference<>(a2);
        } else {
            this.c = null;
        }
    }

    /* access modifiers changed from: protected */
    public abstract void a(Drawable drawable, int i2);

    public void a(NavController navController, j jVar, Bundle bundle) {
        if (!(jVar instanceof c)) {
            WeakReference<DrawerLayout> weakReference = this.c;
            DrawerLayout drawerLayout = weakReference != null ? (DrawerLayout) weakReference.get() : null;
            if (this.c == null || drawerLayout != null) {
                CharSequence e2 = jVar.e();
                boolean z = true;
                if (e2 != null) {
                    StringBuffer stringBuffer = new StringBuffer();
                    Matcher matcher = Pattern.compile("\\{(.+?)\\}").matcher(e2);
                    while (matcher.find()) {
                        String group = matcher.group(1);
                        if (bundle == null || !bundle.containsKey(group)) {
                            throw new IllegalArgumentException("Could not find " + group + " in " + bundle + " to fill label " + e2);
                        }
                        matcher.appendReplacement(stringBuffer, BuildConfig.FLAVOR);
                        stringBuffer.append(bundle.get(group).toString());
                    }
                    matcher.appendTail(stringBuffer);
                    a((CharSequence) stringBuffer);
                }
                boolean a2 = f.a(jVar, this.b);
                if (drawerLayout != null || !a2) {
                    if (drawerLayout == null || !a2) {
                        z = false;
                    }
                    a(z);
                    return;
                }
                a((Drawable) null, 0);
                return;
            }
            navController.removeOnDestinationChangedListener(this);
        }
    }

    /* access modifiers changed from: protected */
    public abstract void a(CharSequence charSequence);

    private void a(boolean z) {
        boolean z2;
        if (this.d == null) {
            this.d = new d(this.a);
            z2 = false;
        } else {
            z2 = true;
        }
        a(this.d, z ? R$string.nav_app_bar_open_drawer_description : R$string.nav_app_bar_navigate_up_description);
        float f2 = z ? 0.0f : 1.0f;
        if (z2) {
            float a2 = this.d.a();
            ValueAnimator valueAnimator = this.e;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.d, "progress", new float[]{a2, f2});
            this.e = ofFloat;
            ofFloat.start();
            return;
        }
        this.d.setProgress(f2);
    }
}
