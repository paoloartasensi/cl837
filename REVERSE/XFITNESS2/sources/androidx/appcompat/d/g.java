package androidx.appcompat.d;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.appcompat.R$styleable;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.view.menu.j;
import androidx.appcompat.widget.g0;
import androidx.appcompat.widget.q;
import androidx.core.h.h;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: SupportMenuInflater */
public class g extends MenuInflater {
    static final Class<?>[] e;

    /* renamed from: f  reason: collision with root package name */
    static final Class<?>[] f103f;
    final Object[] a;
    final Object[] b;
    Context c;
    private Object d;

    /* compiled from: SupportMenuInflater */
    private static class a implements MenuItem.OnMenuItemClickListener {

        /* renamed from: g  reason: collision with root package name */
        private static final Class<?>[] f104g = {MenuItem.class};
        private Object e;

        /* renamed from: f  reason: collision with root package name */
        private Method f105f;

        public a(Object obj, String str) {
            this.e = obj;
            Class<?> cls = obj.getClass();
            try {
                this.f105f = cls.getMethod(str, f104g);
            } catch (Exception e2) {
                InflateException inflateException = new InflateException("Couldn't resolve menu item onClick handler " + str + " in class " + cls.getName());
                inflateException.initCause(e2);
                throw inflateException;
            }
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            try {
                if (this.f105f.getReturnType() == Boolean.TYPE) {
                    return ((Boolean) this.f105f.invoke(this.e, new Object[]{menuItem})).booleanValue();
                }
                this.f105f.invoke(this.e, new Object[]{menuItem});
                return true;
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.lang.Class<?>[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            r0 = 1
            java.lang.Class[] r0 = new java.lang.Class[r0]
            r1 = 0
            java.lang.Class<android.content.Context> r2 = android.content.Context.class
            r0[r1] = r2
            e = r0
            f103f = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.d.g.<clinit>():void");
    }

    public g(Context context) {
        super(context);
        this.c = context;
        Object[] objArr = {context};
        this.a = objArr;
        this.b = objArr;
    }

    private void a(XmlPullParser xmlPullParser, AttributeSet attributeSet, Menu menu) {
        b bVar = new b(menu);
        int eventType = xmlPullParser.getEventType();
        while (true) {
            if (eventType != 2) {
                eventType = xmlPullParser.next();
                if (eventType == 1) {
                    break;
                }
            } else {
                String name = xmlPullParser.getName();
                if (name.equals("menu")) {
                    eventType = xmlPullParser.next();
                } else {
                    throw new RuntimeException("Expecting menu, got " + name);
                }
            }
        }
        String str = null;
        boolean z = false;
        boolean z2 = false;
        while (!z) {
            if (eventType != 1) {
                if (eventType != 2) {
                    if (eventType == 3) {
                        String name2 = xmlPullParser.getName();
                        if (z2 && name2.equals(str)) {
                            str = null;
                            z2 = false;
                        } else if (name2.equals("group")) {
                            bVar.d();
                        } else if (name2.equals("item")) {
                            if (!bVar.c()) {
                                androidx.core.h.b bVar2 = bVar.A;
                                if (bVar2 == null || !bVar2.a()) {
                                    bVar.a();
                                } else {
                                    bVar.b();
                                }
                            }
                        } else if (name2.equals("menu")) {
                            z = true;
                        }
                    }
                } else if (!z2) {
                    String name3 = xmlPullParser.getName();
                    if (name3.equals("group")) {
                        bVar.a(attributeSet);
                    } else if (name3.equals("item")) {
                        bVar.b(attributeSet);
                    } else if (name3.equals("menu")) {
                        a(xmlPullParser, attributeSet, bVar.b());
                    } else {
                        str = name3;
                        z2 = true;
                    }
                }
                eventType = xmlPullParser.next();
            } else {
                throw new RuntimeException("Unexpected end of document");
            }
        }
    }

    public void inflate(int i2, Menu menu) {
        if (!(menu instanceof androidx.core.b.a.a)) {
            super.inflate(i2, menu);
            return;
        }
        XmlResourceParser xmlResourceParser = null;
        try {
            xmlResourceParser = this.c.getResources().getLayout(i2);
            a(xmlResourceParser, Xml.asAttributeSet(xmlResourceParser), menu);
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
        } catch (XmlPullParserException e2) {
            throw new InflateException("Error inflating menu XML", e2);
        } catch (IOException e3) {
            throw new InflateException("Error inflating menu XML", e3);
        } catch (Throwable th) {
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
            throw th;
        }
    }

    /* compiled from: SupportMenuInflater */
    private class b {
        androidx.core.h.b A;
        private CharSequence B;
        private CharSequence C;
        private ColorStateList D = null;
        private PorterDuff.Mode E = null;
        private Menu a;
        private int b;
        private int c;
        private int d;
        private int e;

        /* renamed from: f  reason: collision with root package name */
        private boolean f106f;

        /* renamed from: g  reason: collision with root package name */
        private boolean f107g;

        /* renamed from: h  reason: collision with root package name */
        private boolean f108h;

        /* renamed from: i  reason: collision with root package name */
        private int f109i;

        /* renamed from: j  reason: collision with root package name */
        private int f110j;
        private CharSequence k;
        private CharSequence l;
        private int m;
        private char n;
        private int o;
        private char p;
        private int q;
        private int r;
        private boolean s;
        private boolean t;
        private boolean u;
        private int v;
        private int w;
        private String x;
        private String y;
        private String z;

        public b(Menu menu) {
            this.a = menu;
            d();
        }

        public void a(AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = g.this.c.obtainStyledAttributes(attributeSet, R$styleable.MenuGroup);
            this.b = obtainStyledAttributes.getResourceId(R$styleable.MenuGroup_android_id, 0);
            this.c = obtainStyledAttributes.getInt(R$styleable.MenuGroup_android_menuCategory, 0);
            this.d = obtainStyledAttributes.getInt(R$styleable.MenuGroup_android_orderInCategory, 0);
            this.e = obtainStyledAttributes.getInt(R$styleable.MenuGroup_android_checkableBehavior, 0);
            this.f106f = obtainStyledAttributes.getBoolean(R$styleable.MenuGroup_android_visible, true);
            this.f107g = obtainStyledAttributes.getBoolean(R$styleable.MenuGroup_android_enabled, true);
            obtainStyledAttributes.recycle();
        }

        public void b(AttributeSet attributeSet) {
            g0 a2 = g0.a(g.this.c, attributeSet, R$styleable.MenuItem);
            this.f109i = a2.g(R$styleable.MenuItem_android_id, 0);
            this.f110j = (a2.d(R$styleable.MenuItem_android_menuCategory, this.c) & -65536) | (a2.d(R$styleable.MenuItem_android_orderInCategory, this.d) & 65535);
            this.k = a2.e(R$styleable.MenuItem_android_title);
            this.l = a2.e(R$styleable.MenuItem_android_titleCondensed);
            this.m = a2.g(R$styleable.MenuItem_android_icon, 0);
            this.n = a(a2.d(R$styleable.MenuItem_android_alphabeticShortcut));
            this.o = a2.d(R$styleable.MenuItem_alphabeticModifiers, 4096);
            this.p = a(a2.d(R$styleable.MenuItem_android_numericShortcut));
            this.q = a2.d(R$styleable.MenuItem_numericModifiers, 4096);
            if (a2.g(R$styleable.MenuItem_android_checkable)) {
                this.r = a2.a(R$styleable.MenuItem_android_checkable, false) ? 1 : 0;
            } else {
                this.r = this.e;
            }
            this.s = a2.a(R$styleable.MenuItem_android_checked, false);
            this.t = a2.a(R$styleable.MenuItem_android_visible, this.f106f);
            this.u = a2.a(R$styleable.MenuItem_android_enabled, this.f107g);
            this.v = a2.d(R$styleable.MenuItem_showAsAction, -1);
            this.z = a2.d(R$styleable.MenuItem_android_onClick);
            this.w = a2.g(R$styleable.MenuItem_actionLayout, 0);
            this.x = a2.d(R$styleable.MenuItem_actionViewClass);
            String d2 = a2.d(R$styleable.MenuItem_actionProviderClass);
            this.y = d2;
            boolean z2 = d2 != null;
            if (z2 && this.w == 0 && this.x == null) {
                this.A = (androidx.core.h.b) a(this.y, g.f103f, g.this.b);
            } else {
                if (z2) {
                    Log.w("SupportMenuInflater", "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                }
                this.A = null;
            }
            this.B = a2.e(R$styleable.MenuItem_contentDescription);
            this.C = a2.e(R$styleable.MenuItem_tooltipText);
            if (a2.g(R$styleable.MenuItem_iconTintMode)) {
                this.E = q.a(a2.d(R$styleable.MenuItem_iconTintMode, -1), this.E);
            } else {
                this.E = null;
            }
            if (a2.g(R$styleable.MenuItem_iconTint)) {
                this.D = a2.a(R$styleable.MenuItem_iconTint);
            } else {
                this.D = null;
            }
            a2.a();
            this.f108h = false;
        }

        public boolean c() {
            return this.f108h;
        }

        public void d() {
            this.b = 0;
            this.c = 0;
            this.d = 0;
            this.e = 0;
            this.f106f = true;
            this.f107g = true;
        }

        private char a(String str) {
            if (str == null) {
                return 0;
            }
            return str.charAt(0);
        }

        private void a(MenuItem menuItem) {
            boolean z2 = false;
            menuItem.setChecked(this.s).setVisible(this.t).setEnabled(this.u).setCheckable(this.r >= 1).setTitleCondensed(this.l).setIcon(this.m);
            int i2 = this.v;
            if (i2 >= 0) {
                menuItem.setShowAsAction(i2);
            }
            if (this.z != null) {
                if (!g.this.c.isRestricted()) {
                    menuItem.setOnMenuItemClickListener(new a(g.this.a(), this.z));
                } else {
                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
            }
            boolean z3 = menuItem instanceof i;
            if (z3) {
                i iVar = (i) menuItem;
            }
            if (this.r >= 2) {
                if (z3) {
                    ((i) menuItem).c(true);
                } else if (menuItem instanceof j) {
                    ((j) menuItem).a(true);
                }
            }
            String str = this.x;
            if (str != null) {
                menuItem.setActionView((View) a(str, g.e, g.this.a));
                z2 = true;
            }
            int i3 = this.w;
            if (i3 > 0) {
                if (!z2) {
                    menuItem.setActionView(i3);
                } else {
                    Log.w("SupportMenuInflater", "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                }
            }
            androidx.core.h.b bVar = this.A;
            if (bVar != null) {
                h.a(menuItem, bVar);
            }
            h.a(menuItem, this.B);
            h.b(menuItem, this.C);
            h.a(menuItem, this.n, this.o);
            h.b(menuItem, this.p, this.q);
            PorterDuff.Mode mode = this.E;
            if (mode != null) {
                h.a(menuItem, mode);
            }
            ColorStateList colorStateList = this.D;
            if (colorStateList != null) {
                h.a(menuItem, colorStateList);
            }
        }

        public SubMenu b() {
            this.f108h = true;
            SubMenu addSubMenu = this.a.addSubMenu(this.b, this.f109i, this.f110j, this.k);
            a(addSubMenu.getItem());
            return addSubMenu;
        }

        public void a() {
            this.f108h = true;
            a(this.a.add(this.b, this.f109i, this.f110j, this.k));
        }

        private <T> T a(String str, Class<?>[] clsArr, Object[] objArr) {
            try {
                Constructor<?> constructor = Class.forName(str, false, g.this.c.getClassLoader()).getConstructor(clsArr);
                constructor.setAccessible(true);
                return constructor.newInstance(objArr);
            } catch (Exception e2) {
                Log.w("SupportMenuInflater", "Cannot instantiate class: " + str, e2);
                return null;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Object a() {
        if (this.d == null) {
            this.d = a(this.c);
        }
        return this.d;
    }

    private Object a(Object obj) {
        return (!(obj instanceof Activity) && (obj instanceof ContextWrapper)) ? a(((ContextWrapper) obj).getBaseContext()) : obj;
    }
}
