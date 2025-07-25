package androidx.appcompat.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.d.b;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.l0;
import androidx.core.app.a;
import androidx.core.app.f;
import androidx.core.app.m;
import androidx.fragment.app.FragmentActivity;

public class AppCompatActivity extends FragmentActivity implements d, m.a {
    private e w;
    private Resources x;

    public AppCompatActivity() {
    }

    public b a(b.a aVar) {
        return null;
    }

    public void a(b bVar) {
    }

    public void a(Toolbar toolbar) {
        h().a(toolbar);
    }

    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        h().a(view, layoutParams);
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        h().a(context);
    }

    public void b(b bVar) {
    }

    public void b(m mVar) {
    }

    public boolean b(Intent intent) {
        return f.b((Activity) this, intent);
    }

    public Intent c() {
        return f.a(this);
    }

    /* access modifiers changed from: protected */
    public void c(int i2) {
    }

    public void closeOptionsMenu() {
        a j2 = j();
        if (!getWindow().hasFeature(0)) {
            return;
        }
        if (j2 == null || !j2.e()) {
            super.closeOptionsMenu();
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        a j2 = j();
        if (keyCode != 82 || j2 == null || !j2.a(keyEvent)) {
            return super.dispatchKeyEvent(keyEvent);
        }
        return true;
    }

    public <T extends View> T findViewById(int i2) {
        return h().a(i2);
    }

    public void g() {
        h().f();
    }

    public MenuInflater getMenuInflater() {
        return h().c();
    }

    public Resources getResources() {
        if (this.x == null && l0.b()) {
            this.x = new l0(this, super.getResources());
        }
        Resources resources = this.x;
        return resources == null ? super.getResources() : resources;
    }

    public e h() {
        if (this.w == null) {
            this.w = e.a((Activity) this, (d) this);
        }
        return this.w;
    }

    public b i() {
        return h().a();
    }

    public void invalidateOptionsMenu() {
        h().f();
    }

    public a j() {
        return h().d();
    }

    @Deprecated
    public void k() {
    }

    public boolean l() {
        Intent c = c();
        if (c == null) {
            return false;
        }
        if (b(c)) {
            m a = m.a((Context) this);
            a(a);
            b(a);
            a.b();
            try {
                a.a(this);
                return true;
            } catch (IllegalStateException unused) {
                finish();
                return true;
            }
        } else {
            a(c);
            return true;
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.x != null) {
            this.x.updateConfiguration(configuration, super.getResources().getDisplayMetrics());
        }
        h().a(configuration);
    }

    public void onContentChanged() {
        k();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        e h2 = h();
        h2.e();
        h2.a(bundle);
        super.onCreate(bundle);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        h().g();
    }

    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (a(i2, keyEvent)) {
            return true;
        }
        return super.onKeyDown(i2, keyEvent);
    }

    public final boolean onMenuItemSelected(int i2, MenuItem menuItem) {
        if (super.onMenuItemSelected(i2, menuItem)) {
            return true;
        }
        a j2 = j();
        if (menuItem.getItemId() != 16908332 || j2 == null || (j2.g() & 4) == 0) {
            return false;
        }
        return l();
    }

    public boolean onMenuOpened(int i2, Menu menu) {
        return super.onMenuOpened(i2, menu);
    }

    public void onPanelClosed(int i2, Menu menu) {
        super.onPanelClosed(i2, menu);
    }

    /* access modifiers changed from: protected */
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        h().b(bundle);
    }

    /* access modifiers changed from: protected */
    public void onPostResume() {
        super.onPostResume();
        h().h();
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        h().c(bundle);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        h().i();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        h().j();
    }

    /* access modifiers changed from: protected */
    public void onTitleChanged(CharSequence charSequence, int i2) {
        super.onTitleChanged(charSequence, i2);
        h().a(charSequence);
    }

    public void openOptionsMenu() {
        a j2 = j();
        if (!getWindow().hasFeature(0)) {
            return;
        }
        if (j2 == null || !j2.k()) {
            super.openOptionsMenu();
        }
    }

    public void setContentView(int i2) {
        h().c(i2);
    }

    public void setTheme(int i2) {
        super.setTheme(i2);
        h().d(i2);
    }

    public AppCompatActivity(int i2) {
        super(i2);
    }

    public void a(m mVar) {
        mVar.a((Activity) this);
    }

    public void setContentView(View view) {
        h().a(view);
    }

    public void a(Intent intent) {
        f.a((Activity) this, intent);
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        h().b(view, layoutParams);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0026, code lost:
        r2 = getWindow();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(int r2, android.view.KeyEvent r3) {
        /*
            r1 = this;
            int r2 = android.os.Build.VERSION.SDK_INT
            r0 = 26
            if (r2 >= r0) goto L_0x003e
            boolean r2 = r3.isCtrlPressed()
            if (r2 != 0) goto L_0x003e
            int r2 = r3.getMetaState()
            boolean r2 = android.view.KeyEvent.metaStateHasNoModifiers(r2)
            if (r2 != 0) goto L_0x003e
            int r2 = r3.getRepeatCount()
            if (r2 != 0) goto L_0x003e
            int r2 = r3.getKeyCode()
            boolean r2 = android.view.KeyEvent.isModifierKey(r2)
            if (r2 != 0) goto L_0x003e
            android.view.Window r2 = r1.getWindow()
            if (r2 == 0) goto L_0x003e
            android.view.View r0 = r2.getDecorView()
            if (r0 == 0) goto L_0x003e
            android.view.View r2 = r2.getDecorView()
            boolean r2 = r2.dispatchKeyShortcutEvent(r3)
            if (r2 == 0) goto L_0x003e
            r2 = 1
            return r2
        L_0x003e:
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatActivity.a(int, android.view.KeyEvent):boolean");
    }
}
