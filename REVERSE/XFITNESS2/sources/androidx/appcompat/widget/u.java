package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$styleable;
import androidx.appcompat.view.menu.p;
import androidx.core.h.v;
import androidx.core.widget.h;
import java.lang.reflect.Method;

/* compiled from: ListPopupWindow */
public class u implements p {
    private static Method J;
    private static Method K;
    private static Method L;
    final g A;
    private final f B;
    private final e C;
    private final c D;
    final Handler E;
    private final Rect F;
    private Rect G;
    private boolean H;
    PopupWindow I;
    private Context e;

    /* renamed from: f  reason: collision with root package name */
    private ListAdapter f327f;

    /* renamed from: g  reason: collision with root package name */
    r f328g;

    /* renamed from: h  reason: collision with root package name */
    private int f329h;

    /* renamed from: i  reason: collision with root package name */
    private int f330i;

    /* renamed from: j  reason: collision with root package name */
    private int f331j;
    private int k;
    private int l;
    private boolean m;
    private boolean n;
    private boolean o;
    private int p;
    private boolean q;
    private boolean r;
    int s;
    private View t;
    private int u;
    private DataSetObserver v;
    private View w;
    private Drawable x;
    private AdapterView.OnItemClickListener y;
    private AdapterView.OnItemSelectedListener z;

    /* compiled from: ListPopupWindow */
    class a implements Runnable {
        a() {
        }

        public void run() {
            View i2 = u.this.i();
            if (i2 != null && i2.getWindowToken() != null) {
                u.this.c();
            }
        }
    }

    /* compiled from: ListPopupWindow */
    class b implements AdapterView.OnItemSelectedListener {
        b() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j2) {
            r rVar;
            if (i2 != -1 && (rVar = u.this.f328g) != null) {
                rVar.setListSelectionHidden(false);
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* compiled from: ListPopupWindow */
    private class c implements Runnable {
        c() {
        }

        public void run() {
            u.this.h();
        }
    }

    /* compiled from: ListPopupWindow */
    private class d extends DataSetObserver {
        d() {
        }

        public void onChanged() {
            if (u.this.a()) {
                u.this.c();
            }
        }

        public void onInvalidated() {
            u.this.dismiss();
        }
    }

    /* compiled from: ListPopupWindow */
    private class e implements AbsListView.OnScrollListener {
        e() {
        }

        public void onScroll(AbsListView absListView, int i2, int i3, int i4) {
        }

        public void onScrollStateChanged(AbsListView absListView, int i2) {
            if (i2 == 1 && !u.this.k() && u.this.I.getContentView() != null) {
                u uVar = u.this;
                uVar.E.removeCallbacks(uVar.A);
                u.this.A.run();
            }
        }
    }

    /* compiled from: ListPopupWindow */
    private class f implements View.OnTouchListener {
        f() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            PopupWindow popupWindow;
            int action = motionEvent.getAction();
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (action == 0 && (popupWindow = u.this.I) != null && popupWindow.isShowing() && x >= 0 && x < u.this.I.getWidth() && y >= 0 && y < u.this.I.getHeight()) {
                u uVar = u.this;
                uVar.E.postDelayed(uVar.A, 250);
                return false;
            } else if (action != 1) {
                return false;
            } else {
                u uVar2 = u.this;
                uVar2.E.removeCallbacks(uVar2.A);
                return false;
            }
        }
    }

    /* compiled from: ListPopupWindow */
    private class g implements Runnable {
        g() {
        }

        public void run() {
            r rVar = u.this.f328g;
            if (rVar != null && v.C(rVar) && u.this.f328g.getCount() > u.this.f328g.getChildCount()) {
                int childCount = u.this.f328g.getChildCount();
                u uVar = u.this;
                if (childCount <= uVar.s) {
                    uVar.I.setInputMethodMode(2);
                    u.this.c();
                }
            }
        }
    }

    static {
        if (Build.VERSION.SDK_INT <= 28) {
            Class<PopupWindow> cls = PopupWindow.class;
            try {
                J = cls.getDeclaredMethod("setClipToScreenEnabled", new Class[]{Boolean.TYPE});
            } catch (NoSuchMethodException unused) {
                Log.i("ListPopupWindow", "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
            try {
                L = PopupWindow.class.getDeclaredMethod("setEpicenterBounds", new Class[]{Rect.class});
            } catch (NoSuchMethodException unused2) {
                Log.i("ListPopupWindow", "Could not find method setEpicenterBounds(Rect) on PopupWindow. Oh well.");
            }
        }
        if (Build.VERSION.SDK_INT <= 23) {
            Class<PopupWindow> cls2 = PopupWindow.class;
            try {
                K = cls2.getDeclaredMethod("getMaxAvailableHeight", new Class[]{View.class, Integer.TYPE, Boolean.TYPE});
            } catch (NoSuchMethodException unused3) {
                Log.i("ListPopupWindow", "Could not find method getMaxAvailableHeight(View, int, boolean) on PopupWindow. Oh well.");
            }
        }
    }

    public u(Context context) {
        this(context, (AttributeSet) null, R$attr.listPopupWindowStyle);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v23, resolved type: androidx.appcompat.widget.r} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v24, resolved type: androidx.appcompat.widget.r} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v3, resolved type: android.widget.LinearLayout} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v29, resolved type: androidx.appcompat.widget.r} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int m() {
        /*
            r12 = this;
            androidx.appcompat.widget.r r0 = r12.f328g
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = -1
            r3 = 1
            r4 = 0
            if (r0 != 0) goto L_0x00bd
            android.content.Context r0 = r12.e
            androidx.appcompat.widget.u$a r5 = new androidx.appcompat.widget.u$a
            r5.<init>()
            boolean r5 = r12.H
            r5 = r5 ^ r3
            androidx.appcompat.widget.r r5 = r12.a(r0, r5)
            r12.f328g = r5
            android.graphics.drawable.Drawable r6 = r12.x
            if (r6 == 0) goto L_0x0020
            r5.setSelector(r6)
        L_0x0020:
            androidx.appcompat.widget.r r5 = r12.f328g
            android.widget.ListAdapter r6 = r12.f327f
            r5.setAdapter(r6)
            androidx.appcompat.widget.r r5 = r12.f328g
            android.widget.AdapterView$OnItemClickListener r6 = r12.y
            r5.setOnItemClickListener(r6)
            androidx.appcompat.widget.r r5 = r12.f328g
            r5.setFocusable(r3)
            androidx.appcompat.widget.r r5 = r12.f328g
            r5.setFocusableInTouchMode(r3)
            androidx.appcompat.widget.r r5 = r12.f328g
            androidx.appcompat.widget.u$b r6 = new androidx.appcompat.widget.u$b
            r6.<init>()
            r5.setOnItemSelectedListener(r6)
            androidx.appcompat.widget.r r5 = r12.f328g
            androidx.appcompat.widget.u$e r6 = r12.C
            r5.setOnScrollListener(r6)
            android.widget.AdapterView$OnItemSelectedListener r5 = r12.z
            if (r5 == 0) goto L_0x0052
            androidx.appcompat.widget.r r6 = r12.f328g
            r6.setOnItemSelectedListener(r5)
        L_0x0052:
            androidx.appcompat.widget.r r5 = r12.f328g
            android.view.View r6 = r12.t
            if (r6 == 0) goto L_0x00b6
            android.widget.LinearLayout r7 = new android.widget.LinearLayout
            r7.<init>(r0)
            r7.setOrientation(r3)
            android.widget.LinearLayout$LayoutParams r0 = new android.widget.LinearLayout$LayoutParams
            r8 = 1065353216(0x3f800000, float:1.0)
            r0.<init>(r2, r4, r8)
            int r8 = r12.u
            if (r8 == 0) goto L_0x008d
            if (r8 == r3) goto L_0x0086
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r5 = "Invalid hint position "
            r0.append(r5)
            int r5 = r12.u
            r0.append(r5)
            java.lang.String r0 = r0.toString()
            java.lang.String r5 = "ListPopupWindow"
            android.util.Log.e(r5, r0)
            goto L_0x0093
        L_0x0086:
            r7.addView(r5, r0)
            r7.addView(r6)
            goto L_0x0093
        L_0x008d:
            r7.addView(r6)
            r7.addView(r5, r0)
        L_0x0093:
            int r0 = r12.f330i
            if (r0 < 0) goto L_0x009a
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            goto L_0x009c
        L_0x009a:
            r0 = 0
            r5 = 0
        L_0x009c:
            int r0 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r5)
            r6.measure(r0, r4)
            android.view.ViewGroup$LayoutParams r0 = r6.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r0 = (android.widget.LinearLayout.LayoutParams) r0
            int r5 = r6.getMeasuredHeight()
            int r6 = r0.topMargin
            int r5 = r5 + r6
            int r0 = r0.bottomMargin
            int r5 = r5 + r0
            r0 = r5
            r5 = r7
            goto L_0x00b7
        L_0x00b6:
            r0 = 0
        L_0x00b7:
            android.widget.PopupWindow r6 = r12.I
            r6.setContentView(r5)
            goto L_0x00db
        L_0x00bd:
            android.widget.PopupWindow r0 = r12.I
            android.view.View r0 = r0.getContentView()
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            android.view.View r0 = r12.t
            if (r0 == 0) goto L_0x00da
            android.view.ViewGroup$LayoutParams r5 = r0.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r5 = (android.widget.LinearLayout.LayoutParams) r5
            int r0 = r0.getMeasuredHeight()
            int r6 = r5.topMargin
            int r0 = r0 + r6
            int r5 = r5.bottomMargin
            int r0 = r0 + r5
            goto L_0x00db
        L_0x00da:
            r0 = 0
        L_0x00db:
            android.widget.PopupWindow r5 = r12.I
            android.graphics.drawable.Drawable r5 = r5.getBackground()
            if (r5 == 0) goto L_0x00f7
            android.graphics.Rect r6 = r12.F
            r5.getPadding(r6)
            android.graphics.Rect r5 = r12.F
            int r6 = r5.top
            int r5 = r5.bottom
            int r5 = r5 + r6
            boolean r7 = r12.m
            if (r7 != 0) goto L_0x00fd
            int r6 = -r6
            r12.k = r6
            goto L_0x00fd
        L_0x00f7:
            android.graphics.Rect r5 = r12.F
            r5.setEmpty()
            r5 = 0
        L_0x00fd:
            android.widget.PopupWindow r6 = r12.I
            int r6 = r6.getInputMethodMode()
            r7 = 2
            if (r6 != r7) goto L_0x0107
            goto L_0x0108
        L_0x0107:
            r3 = 0
        L_0x0108:
            android.view.View r4 = r12.i()
            int r6 = r12.k
            int r3 = r12.a(r4, r6, r3)
            boolean r4 = r12.q
            if (r4 != 0) goto L_0x0179
            int r4 = r12.f329h
            if (r4 != r2) goto L_0x011b
            goto L_0x0179
        L_0x011b:
            int r4 = r12.f330i
            r6 = -2
            if (r4 == r6) goto L_0x0142
            r1 = 1073741824(0x40000000, float:2.0)
            if (r4 == r2) goto L_0x0129
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r4, r1)
            goto L_0x015a
        L_0x0129:
            android.content.Context r2 = r12.e
            android.content.res.Resources r2 = r2.getResources()
            android.util.DisplayMetrics r2 = r2.getDisplayMetrics()
            int r2 = r2.widthPixels
            android.graphics.Rect r4 = r12.F
            int r6 = r4.left
            int r4 = r4.right
            int r6 = r6 + r4
            int r2 = r2 - r6
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r2, r1)
            goto L_0x015a
        L_0x0142:
            android.content.Context r2 = r12.e
            android.content.res.Resources r2 = r2.getResources()
            android.util.DisplayMetrics r2 = r2.getDisplayMetrics()
            int r2 = r2.widthPixels
            android.graphics.Rect r4 = r12.F
            int r6 = r4.left
            int r4 = r4.right
            int r6 = r6 + r4
            int r2 = r2 - r6
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r2, r1)
        L_0x015a:
            r7 = r1
            androidx.appcompat.widget.r r6 = r12.f328g
            r8 = 0
            r9 = -1
            int r10 = r3 - r0
            r11 = -1
            int r1 = r6.a(r7, r8, r9, r10, r11)
            if (r1 <= 0) goto L_0x0177
            androidx.appcompat.widget.r r2 = r12.f328g
            int r2 = r2.getPaddingTop()
            androidx.appcompat.widget.r r3 = r12.f328g
            int r3 = r3.getPaddingBottom()
            int r2 = r2 + r3
            int r5 = r5 + r2
            int r0 = r0 + r5
        L_0x0177:
            int r1 = r1 + r0
            return r1
        L_0x0179:
            int r3 = r3 + r5
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.u.m():int");
    }

    private void n() {
        View view = this.t;
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.t);
            }
        }
    }

    public void a(ListAdapter listAdapter) {
        DataSetObserver dataSetObserver = this.v;
        if (dataSetObserver == null) {
            this.v = new d();
        } else {
            ListAdapter listAdapter2 = this.f327f;
            if (listAdapter2 != null) {
                listAdapter2.unregisterDataSetObserver(dataSetObserver);
            }
        }
        this.f327f = listAdapter;
        if (listAdapter != null) {
            listAdapter.registerDataSetObserver(this.v);
        }
        r rVar = this.f328g;
        if (rVar != null) {
            rVar.setAdapter(this.f327f);
        }
    }

    public int b() {
        return this.f331j;
    }

    public void c() {
        int m2 = m();
        boolean k2 = k();
        h.a(this.I, this.l);
        boolean z2 = true;
        if (!this.I.isShowing()) {
            int i2 = this.f330i;
            if (i2 == -1) {
                i2 = -1;
            } else if (i2 == -2) {
                i2 = i().getWidth();
            }
            int i3 = this.f329h;
            if (i3 == -1) {
                m2 = -1;
            } else if (i3 != -2) {
                m2 = i3;
            }
            this.I.setWidth(i2);
            this.I.setHeight(m2);
            c(true);
            this.I.setOutsideTouchable(!this.r && !this.q);
            this.I.setTouchInterceptor(this.B);
            if (this.o) {
                h.a(this.I, this.n);
            }
            if (Build.VERSION.SDK_INT <= 28) {
                Method method = L;
                if (method != null) {
                    try {
                        method.invoke(this.I, new Object[]{this.G});
                    } catch (Exception e2) {
                        Log.e("ListPopupWindow", "Could not invoke setEpicenterBounds on PopupWindow", e2);
                    }
                }
            } else {
                this.I.setEpicenterBounds(this.G);
            }
            h.a(this.I, i(), this.f331j, this.k, this.p);
            this.f328g.setSelection(-1);
            if (!this.H || this.f328g.isInTouchMode()) {
                h();
            }
            if (!this.H) {
                this.E.post(this.D);
            }
        } else if (v.C(i())) {
            int i4 = this.f330i;
            if (i4 == -1) {
                i4 = -1;
            } else if (i4 == -2) {
                i4 = i().getWidth();
            }
            int i5 = this.f329h;
            if (i5 == -1) {
                if (!k2) {
                    m2 = -1;
                }
                if (k2) {
                    this.I.setWidth(this.f330i == -1 ? -1 : 0);
                    this.I.setHeight(0);
                } else {
                    this.I.setWidth(this.f330i == -1 ? -1 : 0);
                    this.I.setHeight(-1);
                }
            } else if (i5 != -2) {
                m2 = i5;
            }
            PopupWindow popupWindow = this.I;
            if (this.r || this.q) {
                z2 = false;
            }
            popupWindow.setOutsideTouchable(z2);
            this.I.update(i(), this.f331j, this.k, i4 < 0 ? -1 : i4, m2 < 0 ? -1 : m2);
        }
    }

    public void d(int i2) {
        this.I.setAnimationStyle(i2);
    }

    public void dismiss() {
        this.I.dismiss();
        n();
        this.I.setContentView((View) null);
        this.f328g = null;
        this.E.removeCallbacks(this.A);
    }

    public Drawable e() {
        return this.I.getBackground();
    }

    public void f(int i2) {
        this.p = i2;
    }

    public void g(int i2) {
        this.I.setInputMethodMode(i2);
    }

    public void h(int i2) {
        this.u = i2;
    }

    public View i() {
        return this.w;
    }

    public int j() {
        return this.f330i;
    }

    public boolean k() {
        return this.I.getInputMethodMode() == 2;
    }

    public boolean l() {
        return this.H;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.I.setOnDismissListener(onDismissListener);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.y = onItemClickListener;
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.z = onItemSelectedListener;
    }

    public u(Context context, AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, 0);
    }

    public void b(int i2) {
        this.k = i2;
        this.m = true;
    }

    public int d() {
        if (!this.m) {
            return 0;
        }
        return this.k;
    }

    public void e(int i2) {
        Drawable background = this.I.getBackground();
        if (background != null) {
            background.getPadding(this.F);
            Rect rect = this.F;
            this.f330i = rect.left + rect.right + i2;
            return;
        }
        j(i2);
    }

    public ListView g() {
        return this.f328g;
    }

    public void h() {
        r rVar = this.f328g;
        if (rVar != null) {
            rVar.setListSelectionHidden(true);
            rVar.requestLayout();
        }
    }

    public void i(int i2) {
        r rVar = this.f328g;
        if (a() && rVar != null) {
            rVar.setListSelectionHidden(false);
            rVar.setSelection(i2);
            if (rVar.getChoiceMode() != 0) {
                rVar.setItemChecked(i2, true);
            }
        }
    }

    public void j(int i2) {
        this.f330i = i2;
    }

    public u(Context context, AttributeSet attributeSet, int i2, int i3) {
        this.f329h = -2;
        this.f330i = -2;
        this.l = 1002;
        this.p = 0;
        this.q = false;
        this.r = false;
        this.s = Integer.MAX_VALUE;
        this.u = 0;
        this.A = new g();
        this.B = new f();
        this.C = new e();
        this.D = new c();
        this.F = new Rect();
        this.e = context;
        this.E = new Handler(context.getMainLooper());
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ListPopupWindow, i2, i3);
        this.f331j = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.ListPopupWindow_android_dropDownHorizontalOffset, 0);
        int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.ListPopupWindow_android_dropDownVerticalOffset, 0);
        this.k = dimensionPixelOffset;
        if (dimensionPixelOffset != 0) {
            this.m = true;
        }
        obtainStyledAttributes.recycle();
        i iVar = new i(context, attributeSet, i2, i3);
        this.I = iVar;
        iVar.setInputMethodMode(1);
    }

    public void b(boolean z2) {
        this.o = true;
        this.n = z2;
    }

    public void a(boolean z2) {
        this.H = z2;
        this.I.setFocusable(z2);
    }

    public void a(Drawable drawable) {
        this.I.setBackgroundDrawable(drawable);
    }

    public void a(View view) {
        this.w = view;
    }

    public void a(int i2) {
        this.f331j = i2;
    }

    public void a(Rect rect) {
        this.G = rect != null ? new Rect(rect) : null;
    }

    public boolean a() {
        return this.I.isShowing();
    }

    /* access modifiers changed from: package-private */
    public r a(Context context, boolean z2) {
        return new r(context, z2);
    }

    private int a(View view, int i2, boolean z2) {
        if (Build.VERSION.SDK_INT > 23) {
            return this.I.getMaxAvailableHeight(view, i2, z2);
        }
        Method method = K;
        if (method != null) {
            try {
                return ((Integer) method.invoke(this.I, new Object[]{view, Integer.valueOf(i2), Boolean.valueOf(z2)})).intValue();
            } catch (Exception unused) {
                Log.i("ListPopupWindow", "Could not call getMaxAvailableHeightMethod(View, int, boolean) on PopupWindow. Using the public version.");
            }
        }
        return this.I.getMaxAvailableHeight(view, i2);
    }

    private void c(boolean z2) {
        if (Build.VERSION.SDK_INT <= 28) {
            Method method = J;
            if (method != null) {
                try {
                    method.invoke(this.I, new Object[]{Boolean.valueOf(z2)});
                } catch (Exception unused) {
                    Log.i("ListPopupWindow", "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
                }
            }
        } else {
            this.I.setIsClippedToScreen(z2);
        }
    }
}
