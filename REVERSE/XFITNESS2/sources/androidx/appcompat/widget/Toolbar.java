package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$styleable;
import androidx.appcompat.app.a;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.view.menu.m;
import androidx.appcompat.view.menu.r;
import androidx.appcompat.widget.ActionMenuView;
import androidx.core.h.v;
import androidx.customview.view.AbsSavedState;
import java.util.ArrayList;
import java.util.List;

public class Toolbar extends ViewGroup {
    private int A;
    private CharSequence B;
    private CharSequence C;
    private ColorStateList D;
    private ColorStateList E;
    private boolean F;
    private boolean G;
    private final ArrayList<View> H;
    private final ArrayList<View> I;
    private final int[] J;
    f K;
    private final ActionMenuView.e L;
    private h0 M;
    private ActionMenuPresenter N;
    private d O;
    private m.a P;
    private g.a Q;
    private boolean R;
    private final Runnable S;
    private ActionMenuView e;

    /* renamed from: f  reason: collision with root package name */
    private TextView f259f;

    /* renamed from: g  reason: collision with root package name */
    private TextView f260g;

    /* renamed from: h  reason: collision with root package name */
    private ImageButton f261h;

    /* renamed from: i  reason: collision with root package name */
    private ImageView f262i;

    /* renamed from: j  reason: collision with root package name */
    private Drawable f263j;
    private CharSequence k;
    ImageButton l;
    View m;
    private Context n;
    private int o;
    private int p;
    private int q;
    int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private z x;
    private int y;
    private int z;

    class a implements ActionMenuView.e {
        a() {
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            f fVar = Toolbar.this.K;
            if (fVar != null) {
                return fVar.onMenuItemClick(menuItem);
            }
            return false;
        }
    }

    class b implements Runnable {
        b() {
        }

        public void run() {
            Toolbar.this.k();
        }
    }

    class c implements View.OnClickListener {
        c() {
        }

        public void onClick(View view) {
            Toolbar.this.c();
        }
    }

    public interface f {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public Toolbar(Context context) {
        this(context, (AttributeSet) null);
    }

    private MenuInflater getMenuInflater() {
        return new androidx.appcompat.d.g(getContext());
    }

    private void l() {
        if (this.x == null) {
            this.x = new z();
        }
    }

    private void m() {
        if (this.f262i == null) {
            this.f262i = new AppCompatImageView(getContext());
        }
    }

    private void n() {
        o();
        if (this.e.j() == null) {
            g gVar = (g) this.e.getMenu();
            if (this.O == null) {
                this.O = new d();
            }
            this.e.setExpandedActionViewsExclusive(true);
            gVar.a((m) this.O, this.n);
        }
    }

    private void o() {
        if (this.e == null) {
            ActionMenuView actionMenuView = new ActionMenuView(getContext());
            this.e = actionMenuView;
            actionMenuView.setPopupTheme(this.o);
            this.e.setOnMenuItemClickListener(this.L);
            this.e.a(this.P, this.Q);
            e generateDefaultLayoutParams = generateDefaultLayoutParams();
            generateDefaultLayoutParams.a = 8388613 | (this.r & 112);
            this.e.setLayoutParams(generateDefaultLayoutParams);
            a((View) this.e, false);
        }
    }

    private void p() {
        if (this.f261h == null) {
            this.f261h = new AppCompatImageButton(getContext(), (AttributeSet) null, R$attr.toolbarNavigationButtonStyle);
            e generateDefaultLayoutParams = generateDefaultLayoutParams();
            generateDefaultLayoutParams.a = 8388611 | (this.r & 112);
            this.f261h.setLayoutParams(generateDefaultLayoutParams);
        }
    }

    private void q() {
        removeCallbacks(this.S);
        post(this.S);
    }

    private boolean r() {
        if (!this.R) {
            return false;
        }
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (d(childAt) && childAt.getMeasuredWidth() > 0 && childAt.getMeasuredHeight() > 0) {
                return false;
            }
        }
        return true;
    }

    public void a(g gVar, ActionMenuPresenter actionMenuPresenter) {
        if (gVar != null || this.e != null) {
            o();
            g j2 = this.e.j();
            if (j2 != gVar) {
                if (j2 != null) {
                    j2.b((m) this.N);
                    j2.b((m) this.O);
                }
                if (this.O == null) {
                    this.O = new d();
                }
                actionMenuPresenter.c(true);
                if (gVar != null) {
                    gVar.a((m) actionMenuPresenter, this.n);
                    gVar.a((m) this.O, this.n);
                } else {
                    actionMenuPresenter.a(this.n, (g) null);
                    this.O.a(this.n, (g) null);
                    actionMenuPresenter.a(true);
                    this.O.a(true);
                }
                this.e.setPopupTheme(this.o);
                this.e.setPresenter(actionMenuPresenter);
                this.N = actionMenuPresenter;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0006, code lost:
        r0 = r1.e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean b() {
        /*
            r1 = this;
            int r0 = r1.getVisibility()
            if (r0 != 0) goto L_0x0012
            androidx.appcompat.widget.ActionMenuView r0 = r1.e
            if (r0 == 0) goto L_0x0012
            boolean r0 = r0.i()
            if (r0 == 0) goto L_0x0012
            r0 = 1
            goto L_0x0013
        L_0x0012:
            r0 = 0
        L_0x0013:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.Toolbar.b():boolean");
    }

    public void c() {
        d dVar = this.O;
        i iVar = dVar == null ? null : dVar.f266f;
        if (iVar != null) {
            iVar.collapseActionView();
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams) && (layoutParams instanceof e);
    }

    public void d() {
        ActionMenuView actionMenuView = this.e;
        if (actionMenuView != null) {
            actionMenuView.d();
        }
    }

    /* access modifiers changed from: package-private */
    public void e() {
        if (this.l == null) {
            AppCompatImageButton appCompatImageButton = new AppCompatImageButton(getContext(), (AttributeSet) null, R$attr.toolbarNavigationButtonStyle);
            this.l = appCompatImageButton;
            appCompatImageButton.setImageDrawable(this.f263j);
            this.l.setContentDescription(this.k);
            e generateDefaultLayoutParams = generateDefaultLayoutParams();
            generateDefaultLayoutParams.a = 8388611 | (this.r & 112);
            generateDefaultLayoutParams.b = 2;
            this.l.setLayoutParams(generateDefaultLayoutParams);
            this.l.setOnClickListener(new c());
        }
    }

    public boolean f() {
        d dVar = this.O;
        return (dVar == null || dVar.f266f == null) ? false : true;
    }

    public boolean g() {
        ActionMenuView actionMenuView = this.e;
        return actionMenuView != null && actionMenuView.f();
    }

    public CharSequence getCollapseContentDescription() {
        ImageButton imageButton = this.l;
        if (imageButton != null) {
            return imageButton.getContentDescription();
        }
        return null;
    }

    public Drawable getCollapseIcon() {
        ImageButton imageButton = this.l;
        if (imageButton != null) {
            return imageButton.getDrawable();
        }
        return null;
    }

    public int getContentInsetEnd() {
        z zVar = this.x;
        if (zVar != null) {
            return zVar.a();
        }
        return 0;
    }

    public int getContentInsetEndWithActions() {
        int i2 = this.z;
        return i2 != Integer.MIN_VALUE ? i2 : getContentInsetEnd();
    }

    public int getContentInsetLeft() {
        z zVar = this.x;
        if (zVar != null) {
            return zVar.b();
        }
        return 0;
    }

    public int getContentInsetRight() {
        z zVar = this.x;
        if (zVar != null) {
            return zVar.c();
        }
        return 0;
    }

    public int getContentInsetStart() {
        z zVar = this.x;
        if (zVar != null) {
            return zVar.d();
        }
        return 0;
    }

    public int getContentInsetStartWithNavigation() {
        int i2 = this.y;
        return i2 != Integer.MIN_VALUE ? i2 : getContentInsetStart();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0005, code lost:
        r0 = r0.j();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getCurrentContentInsetEnd() {
        /*
            r3 = this;
            androidx.appcompat.widget.ActionMenuView r0 = r3.e
            r1 = 0
            if (r0 == 0) goto L_0x0013
            androidx.appcompat.view.menu.g r0 = r0.j()
            if (r0 == 0) goto L_0x0013
            boolean r0 = r0.hasVisibleItems()
            if (r0 == 0) goto L_0x0013
            r0 = 1
            goto L_0x0014
        L_0x0013:
            r0 = 0
        L_0x0014:
            if (r0 == 0) goto L_0x0025
            int r0 = r3.getContentInsetEnd()
            int r2 = r3.z
            int r1 = java.lang.Math.max(r2, r1)
            int r0 = java.lang.Math.max(r0, r1)
            goto L_0x0029
        L_0x0025:
            int r0 = r3.getContentInsetEnd()
        L_0x0029:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.Toolbar.getCurrentContentInsetEnd():int");
    }

    public int getCurrentContentInsetLeft() {
        if (v.o(this) == 1) {
            return getCurrentContentInsetEnd();
        }
        return getCurrentContentInsetStart();
    }

    public int getCurrentContentInsetRight() {
        if (v.o(this) == 1) {
            return getCurrentContentInsetStart();
        }
        return getCurrentContentInsetEnd();
    }

    public int getCurrentContentInsetStart() {
        if (getNavigationIcon() != null) {
            return Math.max(getContentInsetStart(), Math.max(this.y, 0));
        }
        return getContentInsetStart();
    }

    public Drawable getLogo() {
        ImageView imageView = this.f262i;
        if (imageView != null) {
            return imageView.getDrawable();
        }
        return null;
    }

    public CharSequence getLogoDescription() {
        ImageView imageView = this.f262i;
        if (imageView != null) {
            return imageView.getContentDescription();
        }
        return null;
    }

    public Menu getMenu() {
        n();
        return this.e.getMenu();
    }

    public CharSequence getNavigationContentDescription() {
        ImageButton imageButton = this.f261h;
        if (imageButton != null) {
            return imageButton.getContentDescription();
        }
        return null;
    }

    public Drawable getNavigationIcon() {
        ImageButton imageButton = this.f261h;
        if (imageButton != null) {
            return imageButton.getDrawable();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public ActionMenuPresenter getOuterActionMenuPresenter() {
        return this.N;
    }

    public Drawable getOverflowIcon() {
        n();
        return this.e.getOverflowIcon();
    }

    /* access modifiers changed from: package-private */
    public Context getPopupContext() {
        return this.n;
    }

    public int getPopupTheme() {
        return this.o;
    }

    public CharSequence getSubtitle() {
        return this.C;
    }

    /* access modifiers changed from: package-private */
    public final TextView getSubtitleTextView() {
        return this.f260g;
    }

    public CharSequence getTitle() {
        return this.B;
    }

    public int getTitleMarginBottom() {
        return this.w;
    }

    public int getTitleMarginEnd() {
        return this.u;
    }

    public int getTitleMarginStart() {
        return this.t;
    }

    public int getTitleMarginTop() {
        return this.v;
    }

    /* access modifiers changed from: package-private */
    public final TextView getTitleTextView() {
        return this.f259f;
    }

    public p getWrapper() {
        if (this.M == null) {
            this.M = new h0(this, true);
        }
        return this.M;
    }

    public boolean h() {
        ActionMenuView actionMenuView = this.e;
        return actionMenuView != null && actionMenuView.g();
    }

    public boolean i() {
        ActionMenuView actionMenuView = this.e;
        return actionMenuView != null && actionMenuView.h();
    }

    /* access modifiers changed from: package-private */
    public void j() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (!(((e) childAt.getLayoutParams()).b == 2 || childAt == this.e)) {
                removeViewAt(childCount);
                this.I.add(childAt);
            }
        }
    }

    public boolean k() {
        ActionMenuView actionMenuView = this.e;
        return actionMenuView != null && actionMenuView.k();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.S);
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this.G = false;
        }
        if (!this.G) {
            boolean onHoverEvent = super.onHoverEvent(motionEvent);
            if (actionMasked == 9 && !onHoverEvent) {
                this.G = true;
            }
        }
        if (actionMasked == 10 || actionMasked == 3) {
            this.G = false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x02a3 A[LOOP:0: B:101:0x02a1->B:102:0x02a3, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x02c5 A[LOOP:1: B:104:0x02c3->B:105:0x02c5, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x02ef  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x02fe A[LOOP:2: B:112:0x02fc->B:113:0x02fe, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00b3  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00ca  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00e7  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0100  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0105  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x011d  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x012d  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0130  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0134  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0137  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0168  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x01a6  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x01b7  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0229  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onLayout(boolean r20, int r21, int r22, int r23, int r24) {
        /*
            r19 = this;
            r0 = r19
            int r1 = androidx.core.h.v.o(r19)
            r2 = 1
            r3 = 0
            if (r1 != r2) goto L_0x000c
            r1 = 1
            goto L_0x000d
        L_0x000c:
            r1 = 0
        L_0x000d:
            int r4 = r19.getWidth()
            int r5 = r19.getHeight()
            int r6 = r19.getPaddingLeft()
            int r7 = r19.getPaddingRight()
            int r8 = r19.getPaddingTop()
            int r9 = r19.getPaddingBottom()
            int r10 = r4 - r7
            int[] r11 = r0.J
            r11[r2] = r3
            r11[r3] = r3
            int r12 = androidx.core.h.v.p(r19)
            if (r12 < 0) goto L_0x003a
            int r13 = r24 - r22
            int r12 = java.lang.Math.min(r12, r13)
            goto L_0x003b
        L_0x003a:
            r12 = 0
        L_0x003b:
            android.widget.ImageButton r13 = r0.f261h
            boolean r13 = r0.d(r13)
            if (r13 == 0) goto L_0x0055
            if (r1 == 0) goto L_0x004e
            android.widget.ImageButton r13 = r0.f261h
            int r13 = r0.b(r13, r10, r11, r12)
            r14 = r13
            r13 = r6
            goto L_0x0057
        L_0x004e:
            android.widget.ImageButton r13 = r0.f261h
            int r13 = r0.a(r13, r6, r11, r12)
            goto L_0x0056
        L_0x0055:
            r13 = r6
        L_0x0056:
            r14 = r10
        L_0x0057:
            android.widget.ImageButton r15 = r0.l
            boolean r15 = r0.d(r15)
            if (r15 == 0) goto L_0x006e
            if (r1 == 0) goto L_0x0068
            android.widget.ImageButton r15 = r0.l
            int r14 = r0.b(r15, r14, r11, r12)
            goto L_0x006e
        L_0x0068:
            android.widget.ImageButton r15 = r0.l
            int r13 = r0.a(r15, r13, r11, r12)
        L_0x006e:
            androidx.appcompat.widget.ActionMenuView r15 = r0.e
            boolean r15 = r0.d(r15)
            if (r15 == 0) goto L_0x0085
            if (r1 == 0) goto L_0x007f
            androidx.appcompat.widget.ActionMenuView r15 = r0.e
            int r13 = r0.a(r15, r13, r11, r12)
            goto L_0x0085
        L_0x007f:
            androidx.appcompat.widget.ActionMenuView r15 = r0.e
            int r14 = r0.b(r15, r14, r11, r12)
        L_0x0085:
            int r15 = r19.getCurrentContentInsetLeft()
            int r16 = r19.getCurrentContentInsetRight()
            int r2 = r15 - r13
            int r2 = java.lang.Math.max(r3, r2)
            r11[r3] = r2
            int r2 = r10 - r14
            int r2 = r16 - r2
            int r2 = java.lang.Math.max(r3, r2)
            r17 = 1
            r11[r17] = r2
            int r2 = java.lang.Math.max(r13, r15)
            int r10 = r10 - r16
            int r10 = java.lang.Math.min(r14, r10)
            android.view.View r13 = r0.m
            boolean r13 = r0.d(r13)
            if (r13 == 0) goto L_0x00c2
            if (r1 == 0) goto L_0x00bc
            android.view.View r13 = r0.m
            int r10 = r0.b(r13, r10, r11, r12)
            goto L_0x00c2
        L_0x00bc:
            android.view.View r13 = r0.m
            int r2 = r0.a(r13, r2, r11, r12)
        L_0x00c2:
            android.widget.ImageView r13 = r0.f262i
            boolean r13 = r0.d(r13)
            if (r13 == 0) goto L_0x00d9
            if (r1 == 0) goto L_0x00d3
            android.widget.ImageView r13 = r0.f262i
            int r10 = r0.b(r13, r10, r11, r12)
            goto L_0x00d9
        L_0x00d3:
            android.widget.ImageView r13 = r0.f262i
            int r2 = r0.a(r13, r2, r11, r12)
        L_0x00d9:
            android.widget.TextView r13 = r0.f259f
            boolean r13 = r0.d(r13)
            android.widget.TextView r14 = r0.f260g
            boolean r14 = r0.d(r14)
            if (r13 == 0) goto L_0x0100
            android.widget.TextView r15 = r0.f259f
            android.view.ViewGroup$LayoutParams r15 = r15.getLayoutParams()
            androidx.appcompat.widget.Toolbar$e r15 = (androidx.appcompat.widget.Toolbar.e) r15
            int r3 = r15.topMargin
            r23 = r7
            android.widget.TextView r7 = r0.f259f
            int r7 = r7.getMeasuredHeight()
            int r3 = r3 + r7
            int r7 = r15.bottomMargin
            int r3 = r3 + r7
            r7 = 0
            int r3 = r3 + r7
            goto L_0x0103
        L_0x0100:
            r23 = r7
            r3 = 0
        L_0x0103:
            if (r14 == 0) goto L_0x011d
            android.widget.TextView r7 = r0.f260g
            android.view.ViewGroup$LayoutParams r7 = r7.getLayoutParams()
            androidx.appcompat.widget.Toolbar$e r7 = (androidx.appcompat.widget.Toolbar.e) r7
            int r15 = r7.topMargin
            r16 = r4
            android.widget.TextView r4 = r0.f260g
            int r4 = r4.getMeasuredHeight()
            int r15 = r15 + r4
            int r4 = r7.bottomMargin
            int r15 = r15 + r4
            int r3 = r3 + r15
            goto L_0x011f
        L_0x011d:
            r16 = r4
        L_0x011f:
            if (r13 != 0) goto L_0x012b
            if (r14 == 0) goto L_0x0124
            goto L_0x012b
        L_0x0124:
            r18 = r6
            r22 = r12
        L_0x0128:
            r1 = 0
            goto L_0x0294
        L_0x012b:
            if (r13 == 0) goto L_0x0130
            android.widget.TextView r4 = r0.f259f
            goto L_0x0132
        L_0x0130:
            android.widget.TextView r4 = r0.f260g
        L_0x0132:
            if (r14 == 0) goto L_0x0137
            android.widget.TextView r7 = r0.f260g
            goto L_0x0139
        L_0x0137:
            android.widget.TextView r7 = r0.f259f
        L_0x0139:
            android.view.ViewGroup$LayoutParams r4 = r4.getLayoutParams()
            androidx.appcompat.widget.Toolbar$e r4 = (androidx.appcompat.widget.Toolbar.e) r4
            android.view.ViewGroup$LayoutParams r7 = r7.getLayoutParams()
            androidx.appcompat.widget.Toolbar$e r7 = (androidx.appcompat.widget.Toolbar.e) r7
            if (r13 == 0) goto L_0x014f
            android.widget.TextView r15 = r0.f259f
            int r15 = r15.getMeasuredWidth()
            if (r15 > 0) goto L_0x0159
        L_0x014f:
            if (r14 == 0) goto L_0x015c
            android.widget.TextView r15 = r0.f260g
            int r15 = r15.getMeasuredWidth()
            if (r15 <= 0) goto L_0x015c
        L_0x0159:
            r17 = 1
            goto L_0x015e
        L_0x015c:
            r17 = 0
        L_0x015e:
            int r15 = r0.A
            r15 = r15 & 112(0x70, float:1.57E-43)
            r18 = r6
            r6 = 48
            if (r15 == r6) goto L_0x01a6
            r6 = 80
            if (r15 == r6) goto L_0x0198
            int r6 = r5 - r8
            int r6 = r6 - r9
            int r6 = r6 - r3
            int r6 = r6 / 2
            int r15 = r4.topMargin
            r22 = r12
            int r12 = r0.v
            r24 = r2
            int r2 = r15 + r12
            if (r6 >= r2) goto L_0x0181
            int r6 = r15 + r12
            goto L_0x0196
        L_0x0181:
            int r5 = r5 - r9
            int r5 = r5 - r3
            int r5 = r5 - r6
            int r5 = r5 - r8
            int r2 = r4.bottomMargin
            int r3 = r0.w
            int r2 = r2 + r3
            if (r5 >= r2) goto L_0x0196
            int r2 = r7.bottomMargin
            int r2 = r2 + r3
            int r2 = r2 - r5
            int r6 = r6 - r2
            r2 = 0
            int r6 = java.lang.Math.max(r2, r6)
        L_0x0196:
            int r8 = r8 + r6
            goto L_0x01b5
        L_0x0198:
            r24 = r2
            r22 = r12
            int r5 = r5 - r9
            int r2 = r7.bottomMargin
            int r5 = r5 - r2
            int r2 = r0.w
            int r5 = r5 - r2
            int r8 = r5 - r3
            goto L_0x01b5
        L_0x01a6:
            r24 = r2
            r22 = r12
            int r2 = r19.getPaddingTop()
            int r3 = r4.topMargin
            int r2 = r2 + r3
            int r3 = r0.v
            int r8 = r2 + r3
        L_0x01b5:
            if (r1 == 0) goto L_0x0229
            if (r17 == 0) goto L_0x01bc
            int r1 = r0.t
            goto L_0x01bd
        L_0x01bc:
            r1 = 0
        L_0x01bd:
            r2 = 1
            r3 = r11[r2]
            int r1 = r1 - r3
            r3 = 0
            int r4 = java.lang.Math.max(r3, r1)
            int r10 = r10 - r4
            int r1 = -r1
            int r1 = java.lang.Math.max(r3, r1)
            r11[r2] = r1
            if (r13 == 0) goto L_0x01f4
            android.widget.TextView r1 = r0.f259f
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            androidx.appcompat.widget.Toolbar$e r1 = (androidx.appcompat.widget.Toolbar.e) r1
            android.widget.TextView r2 = r0.f259f
            int r2 = r2.getMeasuredWidth()
            int r2 = r10 - r2
            android.widget.TextView r3 = r0.f259f
            int r3 = r3.getMeasuredHeight()
            int r3 = r3 + r8
            android.widget.TextView r4 = r0.f259f
            r4.layout(r2, r8, r10, r3)
            int r4 = r0.u
            int r2 = r2 - r4
            int r1 = r1.bottomMargin
            int r8 = r3 + r1
            goto L_0x01f5
        L_0x01f4:
            r2 = r10
        L_0x01f5:
            if (r14 == 0) goto L_0x021d
            android.widget.TextView r1 = r0.f260g
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            androidx.appcompat.widget.Toolbar$e r1 = (androidx.appcompat.widget.Toolbar.e) r1
            int r3 = r1.topMargin
            int r8 = r8 + r3
            android.widget.TextView r3 = r0.f260g
            int r3 = r3.getMeasuredWidth()
            int r3 = r10 - r3
            android.widget.TextView r4 = r0.f260g
            int r4 = r4.getMeasuredHeight()
            int r4 = r4 + r8
            android.widget.TextView r5 = r0.f260g
            r5.layout(r3, r8, r10, r4)
            int r3 = r0.u
            int r3 = r10 - r3
            int r1 = r1.bottomMargin
            goto L_0x021e
        L_0x021d:
            r3 = r10
        L_0x021e:
            if (r17 == 0) goto L_0x0225
            int r1 = java.lang.Math.min(r2, r3)
            r10 = r1
        L_0x0225:
            r2 = r24
            goto L_0x0128
        L_0x0229:
            if (r17 == 0) goto L_0x022f
            int r7 = r0.t
            r1 = 0
            goto L_0x0231
        L_0x022f:
            r1 = 0
            r7 = 0
        L_0x0231:
            r2 = r11[r1]
            int r7 = r7 - r2
            int r2 = java.lang.Math.max(r1, r7)
            int r2 = r24 + r2
            int r3 = -r7
            int r3 = java.lang.Math.max(r1, r3)
            r11[r1] = r3
            if (r13 == 0) goto L_0x0266
            android.widget.TextView r3 = r0.f259f
            android.view.ViewGroup$LayoutParams r3 = r3.getLayoutParams()
            androidx.appcompat.widget.Toolbar$e r3 = (androidx.appcompat.widget.Toolbar.e) r3
            android.widget.TextView r4 = r0.f259f
            int r4 = r4.getMeasuredWidth()
            int r4 = r4 + r2
            android.widget.TextView r5 = r0.f259f
            int r5 = r5.getMeasuredHeight()
            int r5 = r5 + r8
            android.widget.TextView r6 = r0.f259f
            r6.layout(r2, r8, r4, r5)
            int r6 = r0.u
            int r4 = r4 + r6
            int r3 = r3.bottomMargin
            int r8 = r5 + r3
            goto L_0x0267
        L_0x0266:
            r4 = r2
        L_0x0267:
            if (r14 == 0) goto L_0x028d
            android.widget.TextView r3 = r0.f260g
            android.view.ViewGroup$LayoutParams r3 = r3.getLayoutParams()
            androidx.appcompat.widget.Toolbar$e r3 = (androidx.appcompat.widget.Toolbar.e) r3
            int r5 = r3.topMargin
            int r8 = r8 + r5
            android.widget.TextView r5 = r0.f260g
            int r5 = r5.getMeasuredWidth()
            int r5 = r5 + r2
            android.widget.TextView r6 = r0.f260g
            int r6 = r6.getMeasuredHeight()
            int r6 = r6 + r8
            android.widget.TextView r7 = r0.f260g
            r7.layout(r2, r8, r5, r6)
            int r6 = r0.u
            int r5 = r5 + r6
            int r3 = r3.bottomMargin
            goto L_0x028e
        L_0x028d:
            r5 = r2
        L_0x028e:
            if (r17 == 0) goto L_0x0294
            int r2 = java.lang.Math.max(r4, r5)
        L_0x0294:
            java.util.ArrayList<android.view.View> r3 = r0.H
            r4 = 3
            r0.a((java.util.List<android.view.View>) r3, (int) r4)
            java.util.ArrayList<android.view.View> r3 = r0.H
            int r3 = r3.size()
            r7 = 0
        L_0x02a1:
            if (r7 >= r3) goto L_0x02b4
            java.util.ArrayList<android.view.View> r4 = r0.H
            java.lang.Object r4 = r4.get(r7)
            android.view.View r4 = (android.view.View) r4
            r12 = r22
            int r2 = r0.a(r4, r2, r11, r12)
            int r7 = r7 + 1
            goto L_0x02a1
        L_0x02b4:
            r12 = r22
            java.util.ArrayList<android.view.View> r3 = r0.H
            r4 = 5
            r0.a((java.util.List<android.view.View>) r3, (int) r4)
            java.util.ArrayList<android.view.View> r3 = r0.H
            int r3 = r3.size()
            r7 = 0
        L_0x02c3:
            if (r7 >= r3) goto L_0x02d4
            java.util.ArrayList<android.view.View> r4 = r0.H
            java.lang.Object r4 = r4.get(r7)
            android.view.View r4 = (android.view.View) r4
            int r10 = r0.b(r4, r10, r11, r12)
            int r7 = r7 + 1
            goto L_0x02c3
        L_0x02d4:
            java.util.ArrayList<android.view.View> r3 = r0.H
            r4 = 1
            r0.a((java.util.List<android.view.View>) r3, (int) r4)
            java.util.ArrayList<android.view.View> r3 = r0.H
            int r3 = r0.a((java.util.List<android.view.View>) r3, (int[]) r11)
            int r4 = r16 - r18
            int r4 = r4 - r23
            int r4 = r4 / 2
            int r6 = r18 + r4
            int r4 = r3 / 2
            int r6 = r6 - r4
            int r3 = r3 + r6
            if (r6 >= r2) goto L_0x02ef
            goto L_0x02f6
        L_0x02ef:
            if (r3 <= r10) goto L_0x02f5
            int r3 = r3 - r10
            int r2 = r6 - r3
            goto L_0x02f6
        L_0x02f5:
            r2 = r6
        L_0x02f6:
            java.util.ArrayList<android.view.View> r3 = r0.H
            int r3 = r3.size()
        L_0x02fc:
            if (r1 >= r3) goto L_0x030d
            java.util.ArrayList<android.view.View> r4 = r0.H
            java.lang.Object r4 = r4.get(r1)
            android.view.View r4 = (android.view.View) r4
            int r2 = r0.a(r4, r2, r11, r12)
            int r1 = r1 + 1
            goto L_0x02fc
        L_0x030d:
            java.util.ArrayList<android.view.View> r1 = r0.H
            r1.clear()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.Toolbar.onLayout(boolean, int, int, int, int):void");
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        char c2;
        char c3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int[] iArr = this.J;
        int i11 = 0;
        if (m0.a(this)) {
            c3 = 1;
            c2 = 0;
        } else {
            c3 = 0;
            c2 = 1;
        }
        if (d(this.f261h)) {
            a((View) this.f261h, i2, 0, i3, 0, this.s);
            i6 = this.f261h.getMeasuredWidth() + a((View) this.f261h);
            i5 = Math.max(0, this.f261h.getMeasuredHeight() + b((View) this.f261h));
            i4 = View.combineMeasuredStates(0, this.f261h.getMeasuredState());
        } else {
            i6 = 0;
            i5 = 0;
            i4 = 0;
        }
        if (d(this.l)) {
            a((View) this.l, i2, 0, i3, 0, this.s);
            i6 = this.l.getMeasuredWidth() + a((View) this.l);
            i5 = Math.max(i5, this.l.getMeasuredHeight() + b((View) this.l));
            i4 = View.combineMeasuredStates(i4, this.l.getMeasuredState());
        }
        int currentContentInsetStart = getCurrentContentInsetStart();
        int max = 0 + Math.max(currentContentInsetStart, i6);
        iArr[c3] = Math.max(0, currentContentInsetStart - i6);
        if (d(this.e)) {
            a((View) this.e, i2, max, i3, 0, this.s);
            i7 = this.e.getMeasuredWidth() + a((View) this.e);
            i5 = Math.max(i5, this.e.getMeasuredHeight() + b((View) this.e));
            i4 = View.combineMeasuredStates(i4, this.e.getMeasuredState());
        } else {
            i7 = 0;
        }
        int currentContentInsetEnd = getCurrentContentInsetEnd();
        int max2 = max + Math.max(currentContentInsetEnd, i7);
        iArr[c2] = Math.max(0, currentContentInsetEnd - i7);
        if (d(this.m)) {
            max2 += a(this.m, i2, max2, i3, 0, iArr);
            i5 = Math.max(i5, this.m.getMeasuredHeight() + b(this.m));
            i4 = View.combineMeasuredStates(i4, this.m.getMeasuredState());
        }
        if (d(this.f262i)) {
            max2 += a((View) this.f262i, i2, max2, i3, 0, iArr);
            i5 = Math.max(i5, this.f262i.getMeasuredHeight() + b((View) this.f262i));
            i4 = View.combineMeasuredStates(i4, this.f262i.getMeasuredState());
        }
        int childCount = getChildCount();
        for (int i12 = 0; i12 < childCount; i12++) {
            View childAt = getChildAt(i12);
            if (((e) childAt.getLayoutParams()).b == 0 && d(childAt)) {
                max2 += a(childAt, i2, max2, i3, 0, iArr);
                i5 = Math.max(i5, childAt.getMeasuredHeight() + b(childAt));
                i4 = View.combineMeasuredStates(i4, childAt.getMeasuredState());
            }
        }
        int i13 = this.v + this.w;
        int i14 = this.t + this.u;
        if (d(this.f259f)) {
            a((View) this.f259f, i2, max2 + i14, i3, i13, iArr);
            int measuredWidth = this.f259f.getMeasuredWidth() + a((View) this.f259f);
            i8 = this.f259f.getMeasuredHeight() + b((View) this.f259f);
            i10 = View.combineMeasuredStates(i4, this.f259f.getMeasuredState());
            i9 = measuredWidth;
        } else {
            i10 = i4;
            i9 = 0;
            i8 = 0;
        }
        if (d(this.f260g)) {
            int i15 = i8 + i13;
            i9 = Math.max(i9, a((View) this.f260g, i2, max2 + i14, i3, i15, iArr));
            i8 += this.f260g.getMeasuredHeight() + b((View) this.f260g);
            i10 = View.combineMeasuredStates(i10, this.f260g.getMeasuredState());
        } else {
            int i16 = i10;
        }
        int max3 = Math.max(i5, i8);
        int paddingLeft = max2 + i9 + getPaddingLeft() + getPaddingRight();
        int paddingTop = max3 + getPaddingTop() + getPaddingBottom();
        int resolveSizeAndState = View.resolveSizeAndState(Math.max(paddingLeft, getSuggestedMinimumWidth()), i2, -16777216 & i10);
        int resolveSizeAndState2 = View.resolveSizeAndState(Math.max(paddingTop, getSuggestedMinimumHeight()), i3, i10 << 16);
        if (!r()) {
            i11 = resolveSizeAndState2;
        }
        setMeasuredDimension(resolveSizeAndState, i11);
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        MenuItem findItem;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.a());
        ActionMenuView actionMenuView = this.e;
        g j2 = actionMenuView != null ? actionMenuView.j() : null;
        int i2 = savedState.f264g;
        if (!(i2 == 0 || this.O == null || j2 == null || (findItem = j2.findItem(i2)) == null)) {
            findItem.expandActionView();
        }
        if (savedState.f265h) {
            q();
        }
    }

    public void onRtlPropertiesChanged(int i2) {
        if (Build.VERSION.SDK_INT >= 17) {
            super.onRtlPropertiesChanged(i2);
        }
        l();
        z zVar = this.x;
        boolean z2 = true;
        if (i2 != 1) {
            z2 = false;
        }
        zVar.a(z2);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        i iVar;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        d dVar = this.O;
        if (!(dVar == null || (iVar = dVar.f266f) == null)) {
            savedState.f264g = iVar.getItemId();
        }
        savedState.f265h = i();
        return savedState;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.F = false;
        }
        if (!this.F) {
            boolean onTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !onTouchEvent) {
                this.F = true;
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.F = false;
        }
        return true;
    }

    public void setCollapseContentDescription(int i2) {
        setCollapseContentDescription(i2 != 0 ? getContext().getText(i2) : null);
    }

    public void setCollapseIcon(int i2) {
        setCollapseIcon(androidx.appcompat.a.a.a.c(getContext(), i2));
    }

    public void setCollapsible(boolean z2) {
        this.R = z2;
        requestLayout();
    }

    public void setContentInsetEndWithActions(int i2) {
        if (i2 < 0) {
            i2 = Integer.MIN_VALUE;
        }
        if (i2 != this.z) {
            this.z = i2;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public void setContentInsetStartWithNavigation(int i2) {
        if (i2 < 0) {
            i2 = Integer.MIN_VALUE;
        }
        if (i2 != this.y) {
            this.y = i2;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public void setLogo(int i2) {
        setLogo(androidx.appcompat.a.a.a.c(getContext(), i2));
    }

    public void setLogoDescription(int i2) {
        setLogoDescription(getContext().getText(i2));
    }

    public void setNavigationContentDescription(int i2) {
        setNavigationContentDescription(i2 != 0 ? getContext().getText(i2) : null);
    }

    public void setNavigationIcon(int i2) {
        setNavigationIcon(androidx.appcompat.a.a.a.c(getContext(), i2));
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        p();
        this.f261h.setOnClickListener(onClickListener);
    }

    public void setOnMenuItemClickListener(f fVar) {
        this.K = fVar;
    }

    public void setOverflowIcon(Drawable drawable) {
        n();
        this.e.setOverflowIcon(drawable);
    }

    public void setPopupTheme(int i2) {
        if (this.o != i2) {
            this.o = i2;
            if (i2 == 0) {
                this.n = getContext();
            } else {
                this.n = new ContextThemeWrapper(getContext(), i2);
            }
        }
    }

    public void setSubtitle(int i2) {
        setSubtitle(getContext().getText(i2));
    }

    public void setSubtitleTextColor(int i2) {
        setSubtitleTextColor(ColorStateList.valueOf(i2));
    }

    public void setTitle(int i2) {
        setTitle(getContext().getText(i2));
    }

    public void setTitleMarginBottom(int i2) {
        this.w = i2;
        requestLayout();
    }

    public void setTitleMarginEnd(int i2) {
        this.u = i2;
        requestLayout();
    }

    public void setTitleMarginStart(int i2) {
        this.t = i2;
        requestLayout();
    }

    public void setTitleMarginTop(int i2) {
        this.v = i2;
        requestLayout();
    }

    public void setTitleTextColor(int i2) {
        setTitleTextColor(ColorStateList.valueOf(i2));
    }

    public static class e extends a.C0002a {
        int b = 0;

        public e(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        /* access modifiers changed from: package-private */
        public void a(ViewGroup.MarginLayoutParams marginLayoutParams) {
            this.leftMargin = marginLayoutParams.leftMargin;
            this.topMargin = marginLayoutParams.topMargin;
            this.rightMargin = marginLayoutParams.rightMargin;
            this.bottomMargin = marginLayoutParams.bottomMargin;
        }

        public e(int i2, int i3) {
            super(i2, i3);
            this.a = 8388627;
        }

        public e(e eVar) {
            super((a.C0002a) eVar);
            this.b = eVar.b;
        }

        public e(a.C0002a aVar) {
            super(aVar);
        }

        public e(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super((ViewGroup.LayoutParams) marginLayoutParams);
            a(marginLayoutParams);
        }

        public e(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public Toolbar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.toolbarStyle);
    }

    public void b(Context context, int i2) {
        this.p = i2;
        TextView textView = this.f259f;
        if (textView != null) {
            textView.setTextAppearance(context, i2);
        }
    }

    /* access modifiers changed from: protected */
    public e generateDefaultLayoutParams() {
        return new e(-2, -2);
    }

    public void setCollapseContentDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            e();
        }
        ImageButton imageButton = this.l;
        if (imageButton != null) {
            imageButton.setContentDescription(charSequence);
        }
    }

    public void setCollapseIcon(Drawable drawable) {
        if (drawable != null) {
            e();
            this.l.setImageDrawable(drawable);
            return;
        }
        ImageButton imageButton = this.l;
        if (imageButton != null) {
            imageButton.setImageDrawable(this.f263j);
        }
    }

    public void setLogo(Drawable drawable) {
        if (drawable != null) {
            m();
            if (!c((View) this.f262i)) {
                a((View) this.f262i, true);
            }
        } else {
            ImageView imageView = this.f262i;
            if (imageView != null && c((View) imageView)) {
                removeView(this.f262i);
                this.I.remove(this.f262i);
            }
        }
        ImageView imageView2 = this.f262i;
        if (imageView2 != null) {
            imageView2.setImageDrawable(drawable);
        }
    }

    public void setLogoDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            m();
        }
        ImageView imageView = this.f262i;
        if (imageView != null) {
            imageView.setContentDescription(charSequence);
        }
    }

    public void setNavigationContentDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            p();
        }
        ImageButton imageButton = this.f261h;
        if (imageButton != null) {
            imageButton.setContentDescription(charSequence);
        }
    }

    public void setNavigationIcon(Drawable drawable) {
        if (drawable != null) {
            p();
            if (!c((View) this.f261h)) {
                a((View) this.f261h, true);
            }
        } else {
            ImageButton imageButton = this.f261h;
            if (imageButton != null && c((View) imageButton)) {
                removeView(this.f261h);
                this.I.remove(this.f261h);
            }
        }
        ImageButton imageButton2 = this.f261h;
        if (imageButton2 != null) {
            imageButton2.setImageDrawable(drawable);
        }
    }

    public void setSubtitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (this.f260g == null) {
                Context context = getContext();
                AppCompatTextView appCompatTextView = new AppCompatTextView(context);
                this.f260g = appCompatTextView;
                appCompatTextView.setSingleLine();
                this.f260g.setEllipsize(TextUtils.TruncateAt.END);
                int i2 = this.q;
                if (i2 != 0) {
                    this.f260g.setTextAppearance(context, i2);
                }
                ColorStateList colorStateList = this.E;
                if (colorStateList != null) {
                    this.f260g.setTextColor(colorStateList);
                }
            }
            if (!c((View) this.f260g)) {
                a((View) this.f260g, true);
            }
        } else {
            TextView textView = this.f260g;
            if (textView != null && c((View) textView)) {
                removeView(this.f260g);
                this.I.remove(this.f260g);
            }
        }
        TextView textView2 = this.f260g;
        if (textView2 != null) {
            textView2.setText(charSequence);
        }
        this.C = charSequence;
    }

    public void setSubtitleTextColor(ColorStateList colorStateList) {
        this.E = colorStateList;
        TextView textView = this.f260g;
        if (textView != null) {
            textView.setTextColor(colorStateList);
        }
    }

    public void setTitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (this.f259f == null) {
                Context context = getContext();
                AppCompatTextView appCompatTextView = new AppCompatTextView(context);
                this.f259f = appCompatTextView;
                appCompatTextView.setSingleLine();
                this.f259f.setEllipsize(TextUtils.TruncateAt.END);
                int i2 = this.p;
                if (i2 != 0) {
                    this.f259f.setTextAppearance(context, i2);
                }
                ColorStateList colorStateList = this.D;
                if (colorStateList != null) {
                    this.f259f.setTextColor(colorStateList);
                }
            }
            if (!c((View) this.f259f)) {
                a((View) this.f259f, true);
            }
        } else {
            TextView textView = this.f259f;
            if (textView != null && c((View) textView)) {
                removeView(this.f259f);
                this.I.remove(this.f259f);
            }
        }
        TextView textView2 = this.f259f;
        if (textView2 != null) {
            textView2.setText(charSequence);
        }
        this.B = charSequence;
    }

    public void setTitleTextColor(ColorStateList colorStateList) {
        this.D = colorStateList;
        TextView textView = this.f259f;
        if (textView != null) {
            textView.setTextColor(colorStateList);
        }
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* renamed from: g  reason: collision with root package name */
        int f264g;

        /* renamed from: h  reason: collision with root package name */
        boolean f265h;

        static class a implements Parcelable.ClassLoaderCreator<SavedState> {
            a() {
            }

            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f264g = parcel.readInt();
            this.f265h = parcel.readInt() != 0;
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.f264g);
            parcel.writeInt(this.f265h ? 1 : 0);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    private class d implements m {
        g e;

        /* renamed from: f  reason: collision with root package name */
        i f266f;

        d() {
        }

        public void a(Context context, g gVar) {
            i iVar;
            g gVar2 = this.e;
            if (!(gVar2 == null || (iVar = this.f266f) == null)) {
                gVar2.a(iVar);
            }
            this.e = gVar;
        }

        public void a(Parcelable parcelable) {
        }

        public void a(g gVar, boolean z) {
        }

        public void a(m.a aVar) {
        }

        public boolean a(r rVar) {
            return false;
        }

        public int b() {
            return 0;
        }

        public boolean b(g gVar, i iVar) {
            Toolbar.this.e();
            ViewParent parent = Toolbar.this.l.getParent();
            Toolbar toolbar = Toolbar.this;
            if (parent != toolbar) {
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(toolbar.l);
                }
                Toolbar toolbar2 = Toolbar.this;
                toolbar2.addView(toolbar2.l);
            }
            Toolbar.this.m = iVar.getActionView();
            this.f266f = iVar;
            ViewParent parent2 = Toolbar.this.m.getParent();
            Toolbar toolbar3 = Toolbar.this;
            if (parent2 != toolbar3) {
                if (parent2 instanceof ViewGroup) {
                    ((ViewGroup) parent2).removeView(toolbar3.m);
                }
                e generateDefaultLayoutParams = Toolbar.this.generateDefaultLayoutParams();
                Toolbar toolbar4 = Toolbar.this;
                generateDefaultLayoutParams.a = 8388611 | (toolbar4.r & 112);
                generateDefaultLayoutParams.b = 2;
                toolbar4.m.setLayoutParams(generateDefaultLayoutParams);
                Toolbar toolbar5 = Toolbar.this;
                toolbar5.addView(toolbar5.m);
            }
            Toolbar.this.j();
            Toolbar.this.requestLayout();
            iVar.a(true);
            View view = Toolbar.this.m;
            if (view instanceof androidx.appcompat.d.c) {
                ((androidx.appcompat.d.c) view).a();
            }
            return true;
        }

        public boolean d() {
            return false;
        }

        public Parcelable e() {
            return null;
        }

        public void a(boolean z) {
            if (this.f266f != null) {
                g gVar = this.e;
                boolean z2 = false;
                if (gVar != null) {
                    int size = gVar.size();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= size) {
                            break;
                        } else if (this.e.getItem(i2) == this.f266f) {
                            z2 = true;
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
                if (!z2) {
                    a(this.e, this.f266f);
                }
            }
        }

        public boolean a(g gVar, i iVar) {
            View view = Toolbar.this.m;
            if (view instanceof androidx.appcompat.d.c) {
                ((androidx.appcompat.d.c) view).b();
            }
            Toolbar toolbar = Toolbar.this;
            toolbar.removeView(toolbar.m);
            Toolbar toolbar2 = Toolbar.this;
            toolbar2.removeView(toolbar2.l);
            Toolbar toolbar3 = Toolbar.this;
            toolbar3.m = null;
            toolbar3.a();
            this.f266f = null;
            Toolbar.this.requestLayout();
            iVar.a(false);
            return true;
        }
    }

    public Toolbar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.A = 8388627;
        this.H = new ArrayList<>();
        this.I = new ArrayList<>();
        this.J = new int[2];
        this.L = new a();
        this.S = new b();
        g0 a2 = g0.a(getContext(), attributeSet, R$styleable.Toolbar, i2, 0);
        this.p = a2.g(R$styleable.Toolbar_titleTextAppearance, 0);
        this.q = a2.g(R$styleable.Toolbar_subtitleTextAppearance, 0);
        this.A = a2.e(R$styleable.Toolbar_android_gravity, this.A);
        this.r = a2.e(R$styleable.Toolbar_buttonGravity, 48);
        int b2 = a2.b(R$styleable.Toolbar_titleMargin, 0);
        b2 = a2.g(R$styleable.Toolbar_titleMargins) ? a2.b(R$styleable.Toolbar_titleMargins, b2) : b2;
        this.w = b2;
        this.v = b2;
        this.u = b2;
        this.t = b2;
        int b3 = a2.b(R$styleable.Toolbar_titleMarginStart, -1);
        if (b3 >= 0) {
            this.t = b3;
        }
        int b4 = a2.b(R$styleable.Toolbar_titleMarginEnd, -1);
        if (b4 >= 0) {
            this.u = b4;
        }
        int b5 = a2.b(R$styleable.Toolbar_titleMarginTop, -1);
        if (b5 >= 0) {
            this.v = b5;
        }
        int b6 = a2.b(R$styleable.Toolbar_titleMarginBottom, -1);
        if (b6 >= 0) {
            this.w = b6;
        }
        this.s = a2.c(R$styleable.Toolbar_maxButtonHeight, -1);
        int b7 = a2.b(R$styleable.Toolbar_contentInsetStart, Integer.MIN_VALUE);
        int b8 = a2.b(R$styleable.Toolbar_contentInsetEnd, Integer.MIN_VALUE);
        int c2 = a2.c(R$styleable.Toolbar_contentInsetLeft, 0);
        int c3 = a2.c(R$styleable.Toolbar_contentInsetRight, 0);
        l();
        this.x.a(c2, c3);
        if (!(b7 == Integer.MIN_VALUE && b8 == Integer.MIN_VALUE)) {
            this.x.b(b7, b8);
        }
        this.y = a2.b(R$styleable.Toolbar_contentInsetStartWithNavigation, Integer.MIN_VALUE);
        this.z = a2.b(R$styleable.Toolbar_contentInsetEndWithActions, Integer.MIN_VALUE);
        this.f263j = a2.b(R$styleable.Toolbar_collapseIcon);
        this.k = a2.e(R$styleable.Toolbar_collapseContentDescription);
        CharSequence e2 = a2.e(R$styleable.Toolbar_title);
        if (!TextUtils.isEmpty(e2)) {
            setTitle(e2);
        }
        CharSequence e3 = a2.e(R$styleable.Toolbar_subtitle);
        if (!TextUtils.isEmpty(e3)) {
            setSubtitle(e3);
        }
        this.n = getContext();
        setPopupTheme(a2.g(R$styleable.Toolbar_popupTheme, 0));
        Drawable b9 = a2.b(R$styleable.Toolbar_navigationIcon);
        if (b9 != null) {
            setNavigationIcon(b9);
        }
        CharSequence e4 = a2.e(R$styleable.Toolbar_navigationContentDescription);
        if (!TextUtils.isEmpty(e4)) {
            setNavigationContentDescription(e4);
        }
        Drawable b10 = a2.b(R$styleable.Toolbar_logo);
        if (b10 != null) {
            setLogo(b10);
        }
        CharSequence e5 = a2.e(R$styleable.Toolbar_logoDescription);
        if (!TextUtils.isEmpty(e5)) {
            setLogoDescription(e5);
        }
        if (a2.g(R$styleable.Toolbar_titleTextColor)) {
            setTitleTextColor(a2.a(R$styleable.Toolbar_titleTextColor));
        }
        if (a2.g(R$styleable.Toolbar_subtitleTextColor)) {
            setSubtitleTextColor(a2.a(R$styleable.Toolbar_subtitleTextColor));
        }
        if (a2.g(R$styleable.Toolbar_menu)) {
            a(a2.g(R$styleable.Toolbar_menu, 0));
        }
        a2.a();
    }

    private int c(int i2) {
        int i3 = i2 & 112;
        return (i3 == 16 || i3 == 48 || i3 == 80) ? i3 : this.A & 112;
    }

    private boolean d(View view) {
        return (view == null || view.getParent() != this || view.getVisibility() == 8) ? false : true;
    }

    public e generateLayoutParams(AttributeSet attributeSet) {
        return new e(getContext(), attributeSet);
    }

    private boolean c(View view) {
        return view.getParent() == this || this.I.contains(view);
    }

    /* access modifiers changed from: protected */
    public e generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof e) {
            return new e((e) layoutParams);
        }
        if (layoutParams instanceof a.C0002a) {
            return new e((a.C0002a) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new e((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new e(layoutParams);
    }

    private int b(View view, int i2, int[] iArr, int i3) {
        e eVar = (e) view.getLayoutParams();
        int i4 = eVar.rightMargin - iArr[1];
        int max = i2 - Math.max(0, i4);
        iArr[1] = Math.max(0, -i4);
        int a2 = a(view, i3);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(max - measuredWidth, a2, max, view.getMeasuredHeight() + a2);
        return max - (measuredWidth + eVar.leftMargin);
    }

    private int b(int i2) {
        int o2 = v.o(this);
        int a2 = androidx.core.h.d.a(i2, o2) & 7;
        if (a2 == 1 || a2 == 3 || a2 == 5) {
            return a2;
        }
        return o2 == 1 ? 5 : 3;
    }

    private int b(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
    }

    public void a(Context context, int i2) {
        this.q = i2;
        TextView textView = this.f260g;
        if (textView != null) {
            textView.setTextAppearance(context, i2);
        }
    }

    public void a(int i2) {
        getMenuInflater().inflate(i2, getMenu());
    }

    public void a(int i2, int i3) {
        l();
        this.x.b(i2, i3);
    }

    private void a(View view, boolean z2) {
        e eVar;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            eVar = generateDefaultLayoutParams();
        } else if (!checkLayoutParams(layoutParams)) {
            eVar = generateLayoutParams(layoutParams);
        } else {
            eVar = (e) layoutParams;
        }
        eVar.b = 1;
        if (!z2 || this.m == null) {
            addView(view, eVar);
            return;
        }
        view.setLayoutParams(eVar);
        this.I.add(view);
    }

    private void a(View view, int i2, int i3, int i4, int i5, int i6) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i2, getPaddingLeft() + getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i3, marginLayoutParams.width);
        int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i4, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i5, marginLayoutParams.height);
        int mode = View.MeasureSpec.getMode(childMeasureSpec2);
        if (mode != 1073741824 && i6 >= 0) {
            if (mode != 0) {
                i6 = Math.min(View.MeasureSpec.getSize(childMeasureSpec2), i6);
            }
            childMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i6, 1073741824);
        }
        view.measure(childMeasureSpec, childMeasureSpec2);
    }

    private int a(View view, int i2, int i3, int i4, int i5, int[] iArr) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i6 = marginLayoutParams.leftMargin - iArr[0];
        int i7 = marginLayoutParams.rightMargin - iArr[1];
        int max = Math.max(0, i6) + Math.max(0, i7);
        iArr[0] = Math.max(0, -i6);
        iArr[1] = Math.max(0, -i7);
        view.measure(ViewGroup.getChildMeasureSpec(i2, getPaddingLeft() + getPaddingRight() + max + i3, marginLayoutParams.width), ViewGroup.getChildMeasureSpec(i4, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i5, marginLayoutParams.height));
        return view.getMeasuredWidth() + max;
    }

    private int a(List<View> list, int[] iArr) {
        int i2 = iArr[0];
        int i3 = iArr[1];
        int size = list.size();
        int i4 = 0;
        int i5 = 0;
        while (i4 < size) {
            View view = list.get(i4);
            e eVar = (e) view.getLayoutParams();
            int i6 = eVar.leftMargin - i2;
            int i7 = eVar.rightMargin - i3;
            int max = Math.max(0, i6);
            int max2 = Math.max(0, i7);
            int max3 = Math.max(0, -i6);
            int max4 = Math.max(0, -i7);
            i5 += max + view.getMeasuredWidth() + max2;
            i4++;
            i3 = max4;
            i2 = max3;
        }
        return i5;
    }

    private int a(View view, int i2, int[] iArr, int i3) {
        e eVar = (e) view.getLayoutParams();
        int i4 = eVar.leftMargin - iArr[0];
        int max = i2 + Math.max(0, i4);
        iArr[0] = Math.max(0, -i4);
        int a2 = a(view, i3);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(max, a2, max + measuredWidth, view.getMeasuredHeight() + a2);
        return max + measuredWidth + eVar.rightMargin;
    }

    private int a(View view, int i2) {
        e eVar = (e) view.getLayoutParams();
        int measuredHeight = view.getMeasuredHeight();
        int i3 = i2 > 0 ? (measuredHeight - i2) / 2 : 0;
        int c2 = c(eVar.a);
        if (c2 == 48) {
            return getPaddingTop() - i3;
        }
        if (c2 == 80) {
            return (((getHeight() - getPaddingBottom()) - measuredHeight) - eVar.bottomMargin) - i3;
        }
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int height = getHeight();
        int i4 = (((height - paddingTop) - paddingBottom) - measuredHeight) / 2;
        int i5 = eVar.topMargin;
        if (i4 < i5) {
            i4 = i5;
        } else {
            int i6 = (((height - paddingBottom) - measuredHeight) - i4) - paddingTop;
            int i7 = eVar.bottomMargin;
            if (i6 < i7) {
                i4 = Math.max(0, i4 - (i7 - i6));
            }
        }
        return paddingTop + i4;
    }

    private void a(List<View> list, int i2) {
        boolean z2 = v.o(this) == 1;
        int childCount = getChildCount();
        int a2 = androidx.core.h.d.a(i2, v.o(this));
        list.clear();
        if (z2) {
            for (int i3 = childCount - 1; i3 >= 0; i3--) {
                View childAt = getChildAt(i3);
                e eVar = (e) childAt.getLayoutParams();
                if (eVar.b == 0 && d(childAt) && b(eVar.a) == a2) {
                    list.add(childAt);
                }
            }
            return;
        }
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt2 = getChildAt(i4);
            e eVar2 = (e) childAt2.getLayoutParams();
            if (eVar2.b == 0 && d(childAt2) && b(eVar2.a) == a2) {
                list.add(childAt2);
            }
        }
    }

    private int a(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return androidx.core.h.g.b(marginLayoutParams) + androidx.core.h.g.a(marginLayoutParams);
    }

    /* access modifiers changed from: package-private */
    public void a() {
        for (int size = this.I.size() - 1; size >= 0; size--) {
            addView(this.I.get(size));
        }
        this.I.clear();
    }

    public void a(m.a aVar, g.a aVar2) {
        this.P = aVar;
        this.Q = aVar2;
        ActionMenuView actionMenuView = this.e;
        if (actionMenuView != null) {
            actionMenuView.a(aVar, aVar2);
        }
    }
}
