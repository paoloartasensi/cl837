package androidx.appcompat.view.menu;

import android.content.DialogInterface;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.R$layout;
import androidx.appcompat.app.c;
import androidx.appcompat.view.menu.m;

/* compiled from: MenuDialogHelper */
class h implements DialogInterface.OnKeyListener, DialogInterface.OnClickListener, DialogInterface.OnDismissListener, m.a {
    private g e;

    /* renamed from: f  reason: collision with root package name */
    private c f155f;

    /* renamed from: g  reason: collision with root package name */
    e f156g;

    /* renamed from: h  reason: collision with root package name */
    private m.a f157h;

    public h(g gVar) {
        this.e = gVar;
    }

    public void a(IBinder iBinder) {
        g gVar = this.e;
        c.a aVar = new c.a(gVar.e());
        e eVar = new e(aVar.b(), R$layout.abc_list_menu_item_layout);
        this.f156g = eVar;
        eVar.a((m.a) this);
        this.e.a((m) this.f156g);
        aVar.a(this.f156g.a(), (DialogInterface.OnClickListener) this);
        View i2 = gVar.i();
        if (i2 != null) {
            aVar.a(i2);
        } else {
            aVar.a(gVar.g());
            aVar.b(gVar.h());
        }
        aVar.a((DialogInterface.OnKeyListener) this);
        c a = aVar.a();
        this.f155f = a;
        a.setOnDismissListener(this);
        WindowManager.LayoutParams attributes = this.f155f.getWindow().getAttributes();
        attributes.type = 1003;
        if (iBinder != null) {
            attributes.token = iBinder;
        }
        attributes.flags |= 131072;
        this.f155f.show();
    }

    public void onClick(DialogInterface dialogInterface, int i2) {
        this.e.a((MenuItem) (i) this.f156g.a().getItem(i2), 0);
    }

    public void onDismiss(DialogInterface dialogInterface) {
        this.f156g.a(this.e, true);
    }

    public boolean onKey(DialogInterface dialogInterface, int i2, KeyEvent keyEvent) {
        Window window;
        View decorView;
        KeyEvent.DispatcherState keyDispatcherState;
        View decorView2;
        KeyEvent.DispatcherState keyDispatcherState2;
        if (i2 == 82 || i2 == 4) {
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                Window window2 = this.f155f.getWindow();
                if (!(window2 == null || (decorView2 = window2.getDecorView()) == null || (keyDispatcherState2 = decorView2.getKeyDispatcherState()) == null)) {
                    keyDispatcherState2.startTracking(keyEvent, this);
                    return true;
                }
            } else if (keyEvent.getAction() == 1 && !keyEvent.isCanceled() && (window = this.f155f.getWindow()) != null && (decorView = window.getDecorView()) != null && (keyDispatcherState = decorView.getKeyDispatcherState()) != null && keyDispatcherState.isTracking(keyEvent)) {
                this.e.a(true);
                dialogInterface.dismiss();
                return true;
            }
        }
        return this.e.performShortcut(i2, keyEvent, 0);
    }

    public void a() {
        c cVar = this.f155f;
        if (cVar != null) {
            cVar.dismiss();
        }
    }

    public void a(g gVar, boolean z) {
        if (z || gVar == this.e) {
            a();
        }
        m.a aVar = this.f157h;
        if (aVar != null) {
            aVar.a(gVar, z);
        }
    }

    public boolean a(g gVar) {
        m.a aVar = this.f157h;
        if (aVar != null) {
            return aVar.a(gVar);
        }
        return false;
    }
}
