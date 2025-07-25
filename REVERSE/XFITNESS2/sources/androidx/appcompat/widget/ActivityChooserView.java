package androidx.appcompat.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.R$dimen;
import androidx.appcompat.R$id;
import androidx.appcompat.R$layout;
import androidx.appcompat.R$string;
import androidx.appcompat.R$styleable;
import androidx.appcompat.view.menu.p;

public class ActivityChooserView extends ViewGroup {
    final f e;

    /* renamed from: f  reason: collision with root package name */
    private final g f189f;

    /* renamed from: g  reason: collision with root package name */
    private final View f190g;

    /* renamed from: h  reason: collision with root package name */
    private final Drawable f191h;

    /* renamed from: i  reason: collision with root package name */
    final FrameLayout f192i;

    /* renamed from: j  reason: collision with root package name */
    private final ImageView f193j;
    final FrameLayout k;
    private final ImageView l;
    private final int m;
    androidx.core.h.b n;
    final DataSetObserver o;
    private final ViewTreeObserver.OnGlobalLayoutListener p;
    private u q;
    PopupWindow.OnDismissListener r;
    boolean s;
    int t;
    private boolean u;
    private int v;

    public static class InnerLayout extends LinearLayout {
        private static final int[] e = {16842964};

        public InnerLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            g0 a = g0.a(context, attributeSet, e);
            setBackgroundDrawable(a.b(0));
            a.a();
        }
    }

    class a extends DataSetObserver {
        a() {
        }

        public void onChanged() {
            super.onChanged();
            ActivityChooserView.this.e.notifyDataSetChanged();
        }

        public void onInvalidated() {
            super.onInvalidated();
            ActivityChooserView.this.e.notifyDataSetInvalidated();
        }
    }

    class b implements ViewTreeObserver.OnGlobalLayoutListener {
        b() {
        }

        public void onGlobalLayout() {
            if (!ActivityChooserView.this.b()) {
                return;
            }
            if (!ActivityChooserView.this.isShown()) {
                ActivityChooserView.this.getListPopupWindow().dismiss();
                return;
            }
            ActivityChooserView.this.getListPopupWindow().c();
            androidx.core.h.b bVar = ActivityChooserView.this.n;
            if (bVar != null) {
                bVar.a(true);
            }
        }
    }

    class c extends View.AccessibilityDelegate {
        c(ActivityChooserView activityChooserView) {
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            androidx.core.h.e0.d.a(accessibilityNodeInfo).b(true);
        }
    }

    class d extends t {
        d(View view) {
            super(view);
        }

        public p a() {
            return ActivityChooserView.this.getListPopupWindow();
        }

        /* access modifiers changed from: protected */
        public boolean b() {
            ActivityChooserView.this.c();
            return true;
        }

        /* access modifiers changed from: protected */
        public boolean c() {
            ActivityChooserView.this.a();
            return true;
        }
    }

    class e extends DataSetObserver {
        e() {
        }

        public void onChanged() {
            super.onChanged();
            ActivityChooserView.this.d();
        }
    }

    private class g implements AdapterView.OnItemClickListener, View.OnClickListener, View.OnLongClickListener, PopupWindow.OnDismissListener {
        g() {
        }

        private void a() {
            PopupWindow.OnDismissListener onDismissListener = ActivityChooserView.this.r;
            if (onDismissListener != null) {
                onDismissListener.onDismiss();
            }
        }

        public void onClick(View view) {
            ActivityChooserView activityChooserView = ActivityChooserView.this;
            if (view == activityChooserView.k) {
                activityChooserView.a();
                Intent a = ActivityChooserView.this.e.b().a(ActivityChooserView.this.e.b().a(ActivityChooserView.this.e.c()));
                if (a != null) {
                    a.addFlags(524288);
                    ActivityChooserView.this.getContext().startActivity(a);
                }
            } else if (view == activityChooserView.f192i) {
                activityChooserView.s = false;
                activityChooserView.a(activityChooserView.t);
            } else {
                throw new IllegalArgumentException();
            }
        }

        public void onDismiss() {
            a();
            androidx.core.h.b bVar = ActivityChooserView.this.n;
            if (bVar != null) {
                bVar.a(false);
            }
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
            int itemViewType = ((f) adapterView.getAdapter()).getItemViewType(i2);
            if (itemViewType == 0) {
                ActivityChooserView.this.a();
                ActivityChooserView activityChooserView = ActivityChooserView.this;
                if (!activityChooserView.s) {
                    if (!activityChooserView.e.e()) {
                        i2++;
                    }
                    Intent a = ActivityChooserView.this.e.b().a(i2);
                    if (a != null) {
                        a.addFlags(524288);
                        ActivityChooserView.this.getContext().startActivity(a);
                    }
                } else if (i2 > 0) {
                    activityChooserView.e.b().c(i2);
                }
            } else if (itemViewType == 1) {
                ActivityChooserView.this.a(Integer.MAX_VALUE);
            } else {
                throw new IllegalArgumentException();
            }
        }

        public boolean onLongClick(View view) {
            ActivityChooserView activityChooserView = ActivityChooserView.this;
            if (view == activityChooserView.k) {
                if (activityChooserView.e.getCount() > 0) {
                    ActivityChooserView activityChooserView2 = ActivityChooserView.this;
                    activityChooserView2.s = true;
                    activityChooserView2.a(activityChooserView2.t);
                }
                return true;
            }
            throw new IllegalArgumentException();
        }
    }

    public ActivityChooserView(Context context) {
        this(context, (AttributeSet) null);
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        if (this.e.b() != null) {
            getViewTreeObserver().addOnGlobalLayoutListener(this.p);
            boolean z = this.k.getVisibility() == 0;
            int a2 = this.e.a();
            if (i2 == Integer.MAX_VALUE || a2 <= i2 + (z ? 1 : 0)) {
                this.e.a(false);
                this.e.a(i2);
            } else {
                this.e.a(true);
                this.e.a(i2 - 1);
            }
            u listPopupWindow = getListPopupWindow();
            if (!listPopupWindow.a()) {
                if (this.s || !z) {
                    this.e.a(true, z);
                } else {
                    this.e.a(false, false);
                }
                listPopupWindow.e(Math.min(this.e.f(), this.m));
                listPopupWindow.c();
                androidx.core.h.b bVar = this.n;
                if (bVar != null) {
                    bVar.a(true);
                }
                listPopupWindow.g().setContentDescription(getContext().getString(R$string.abc_activitychooserview_choose_application));
                listPopupWindow.g().setSelector(new ColorDrawable(0));
                return;
            }
            return;
        }
        throw new IllegalStateException("No data model. Did you call #setDataModel?");
    }

    public boolean b() {
        return getListPopupWindow().a();
    }

    public boolean c() {
        if (b() || !this.u) {
            return false;
        }
        this.s = false;
        a(this.t);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void d() {
        if (this.e.getCount() > 0) {
            this.f192i.setEnabled(true);
        } else {
            this.f192i.setEnabled(false);
        }
        int a2 = this.e.a();
        int d2 = this.e.d();
        if (a2 == 1 || (a2 > 1 && d2 > 0)) {
            this.k.setVisibility(0);
            ResolveInfo c2 = this.e.c();
            PackageManager packageManager = getContext().getPackageManager();
            this.l.setImageDrawable(c2.loadIcon(packageManager));
            if (this.v != 0) {
                CharSequence loadLabel = c2.loadLabel(packageManager);
                this.k.setContentDescription(getContext().getString(this.v, new Object[]{loadLabel}));
            }
        } else {
            this.k.setVisibility(8);
        }
        if (this.k.getVisibility() == 0) {
            this.f190g.setBackgroundDrawable(this.f191h);
        } else {
            this.f190g.setBackgroundDrawable((Drawable) null);
        }
    }

    public c getDataModel() {
        return this.e.b();
    }

    /* access modifiers changed from: package-private */
    public u getListPopupWindow() {
        if (this.q == null) {
            u uVar = new u(getContext());
            this.q = uVar;
            uVar.a((ListAdapter) this.e);
            this.q.a((View) this);
            this.q.a(true);
            this.q.setOnItemClickListener(this.f189f);
            this.q.setOnDismissListener(this.f189f);
        }
        return this.q;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        c b2 = this.e.b();
        if (b2 != null) {
            b2.registerObserver(this.o);
        }
        this.u = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        c b2 = this.e.b();
        if (b2 != null) {
            b2.unregisterObserver(this.o);
        }
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.removeGlobalOnLayoutListener(this.p);
        }
        if (b()) {
            a();
        }
        this.u = false;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        this.f190g.layout(0, 0, i4 - i2, i5 - i3);
        if (!b()) {
            a();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        View view = this.f190g;
        if (this.k.getVisibility() != 0) {
            i3 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i3), 1073741824);
        }
        measureChild(view, i2, i3);
        setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public void setActivityChooserModel(c cVar) {
        this.e.a(cVar);
        if (b()) {
            a();
            c();
        }
    }

    public void setDefaultActionButtonContentDescription(int i2) {
        this.v = i2;
    }

    public void setExpandActivityOverflowButtonContentDescription(int i2) {
        this.f193j.setContentDescription(getContext().getString(i2));
    }

    public void setExpandActivityOverflowButtonDrawable(Drawable drawable) {
        this.f193j.setImageDrawable(drawable);
    }

    public void setInitialActivityCount(int i2) {
        this.t = i2;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.r = onDismissListener;
    }

    public void setProvider(androidx.core.h.b bVar) {
        this.n = bVar;
    }

    public ActivityChooserView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActivityChooserView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.o = new a();
        this.p = new b();
        this.t = 4;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ActivityChooserView, i2, 0);
        if (Build.VERSION.SDK_INT >= 29) {
            saveAttributeDataForStyleable(context, R$styleable.ActivityChooserView, attributeSet, obtainStyledAttributes, i2, 0);
        }
        this.t = obtainStyledAttributes.getInt(R$styleable.ActivityChooserView_initialActivityCount, 4);
        Drawable drawable = obtainStyledAttributes.getDrawable(R$styleable.ActivityChooserView_expandActivityOverflowButtonDrawable);
        obtainStyledAttributes.recycle();
        LayoutInflater.from(getContext()).inflate(R$layout.abc_activity_chooser_view, this, true);
        this.f189f = new g();
        View findViewById = findViewById(R$id.activity_chooser_view_content);
        this.f190g = findViewById;
        this.f191h = findViewById.getBackground();
        FrameLayout frameLayout = (FrameLayout) findViewById(R$id.default_activity_button);
        this.k = frameLayout;
        frameLayout.setOnClickListener(this.f189f);
        this.k.setOnLongClickListener(this.f189f);
        this.l = (ImageView) this.k.findViewById(R$id.image);
        FrameLayout frameLayout2 = (FrameLayout) findViewById(R$id.expand_activities_button);
        frameLayout2.setOnClickListener(this.f189f);
        frameLayout2.setAccessibilityDelegate(new c(this));
        frameLayout2.setOnTouchListener(new d(frameLayout2));
        this.f192i = frameLayout2;
        ImageView imageView = (ImageView) frameLayout2.findViewById(R$id.image);
        this.f193j = imageView;
        imageView.setImageDrawable(drawable);
        f fVar = new f();
        this.e = fVar;
        fVar.registerDataSetObserver(new e());
        Resources resources = context.getResources();
        this.m = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R$dimen.abc_config_prefDialogWidth));
    }

    private class f extends BaseAdapter {
        private c e;

        /* renamed from: f  reason: collision with root package name */
        private int f194f = 4;

        /* renamed from: g  reason: collision with root package name */
        private boolean f195g;

        /* renamed from: h  reason: collision with root package name */
        private boolean f196h;

        /* renamed from: i  reason: collision with root package name */
        private boolean f197i;

        f() {
        }

        public void a(c cVar) {
            c b = ActivityChooserView.this.e.b();
            if (b != null && ActivityChooserView.this.isShown()) {
                b.unregisterObserver(ActivityChooserView.this.o);
            }
            this.e = cVar;
            if (cVar != null && ActivityChooserView.this.isShown()) {
                cVar.registerObserver(ActivityChooserView.this.o);
            }
            notifyDataSetChanged();
        }

        public c b() {
            return this.e;
        }

        public ResolveInfo c() {
            return this.e.b();
        }

        public int d() {
            return this.e.c();
        }

        public boolean e() {
            return this.f195g;
        }

        public int f() {
            int i2 = this.f194f;
            this.f194f = Integer.MAX_VALUE;
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
            int count = getCount();
            View view = null;
            int i3 = 0;
            for (int i4 = 0; i4 < count; i4++) {
                view = getView(i4, view, (ViewGroup) null);
                view.measure(makeMeasureSpec, makeMeasureSpec2);
                i3 = Math.max(i3, view.getMeasuredWidth());
            }
            this.f194f = i2;
            return i3;
        }

        public int getCount() {
            int a = this.e.a();
            if (!this.f195g && this.e.b() != null) {
                a--;
            }
            int min = Math.min(a, this.f194f);
            return this.f197i ? min + 1 : min;
        }

        public Object getItem(int i2) {
            int itemViewType = getItemViewType(i2);
            if (itemViewType == 0) {
                if (!this.f195g && this.e.b() != null) {
                    i2++;
                }
                return this.e.b(i2);
            } else if (itemViewType == 1) {
                return null;
            } else {
                throw new IllegalArgumentException();
            }
        }

        public long getItemId(int i2) {
            return (long) i2;
        }

        public int getItemViewType(int i2) {
            return (!this.f197i || i2 != getCount() - 1) ? 0 : 1;
        }

        public View getView(int i2, View view, ViewGroup viewGroup) {
            int itemViewType = getItemViewType(i2);
            if (itemViewType == 0) {
                if (view == null || view.getId() != R$id.list_item) {
                    view = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(R$layout.abc_activity_chooser_view_list_item, viewGroup, false);
                }
                PackageManager packageManager = ActivityChooserView.this.getContext().getPackageManager();
                ResolveInfo resolveInfo = (ResolveInfo) getItem(i2);
                ((ImageView) view.findViewById(R$id.icon)).setImageDrawable(resolveInfo.loadIcon(packageManager));
                ((TextView) view.findViewById(R$id.title)).setText(resolveInfo.loadLabel(packageManager));
                if (!this.f195g || i2 != 0 || !this.f196h) {
                    view.setActivated(false);
                } else {
                    view.setActivated(true);
                }
                return view;
            } else if (itemViewType != 1) {
                throw new IllegalArgumentException();
            } else if (view != null && view.getId() == 1) {
                return view;
            } else {
                View inflate = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(R$layout.abc_activity_chooser_view_list_item, viewGroup, false);
                inflate.setId(1);
                ((TextView) inflate.findViewById(R$id.title)).setText(ActivityChooserView.this.getContext().getString(R$string.abc_activity_chooser_view_see_all));
                return inflate;
            }
        }

        public int getViewTypeCount() {
            return 3;
        }

        public void a(int i2) {
            if (this.f194f != i2) {
                this.f194f = i2;
                notifyDataSetChanged();
            }
        }

        public void a(boolean z) {
            if (this.f197i != z) {
                this.f197i = z;
                notifyDataSetChanged();
            }
        }

        public int a() {
            return this.e.a();
        }

        public void a(boolean z, boolean z2) {
            if (this.f195g != z || this.f196h != z2) {
                this.f195g = z;
                this.f196h = z2;
                notifyDataSetChanged();
            }
        }
    }

    public boolean a() {
        if (!b()) {
            return true;
        }
        getListPopupWindow().dismiss();
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (!viewTreeObserver.isAlive()) {
            return true;
        }
        viewTreeObserver.removeGlobalOnLayoutListener(this.p);
        return true;
    }
}
