package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ThemedSpinnerAdapter;
import androidx.appcompat.R$attr;
import androidx.appcompat.app.c;
import androidx.appcompat.view.menu.p;
import androidx.core.h.u;
import androidx.core.h.v;

public class AppCompatSpinner extends Spinner implements u {
    private static final int[] m = {16843505};
    private final d e;

    /* renamed from: f  reason: collision with root package name */
    private final Context f213f;

    /* renamed from: g  reason: collision with root package name */
    private t f214g;

    /* renamed from: h  reason: collision with root package name */
    private SpinnerAdapter f215h;

    /* renamed from: i  reason: collision with root package name */
    private final boolean f216i;

    /* renamed from: j  reason: collision with root package name */
    private f f217j;
    int k;
    final Rect l;

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        boolean e;

        static class a implements Parcelable.Creator<SavedState> {
            a() {
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeByte(this.e ? (byte) 1 : 0);
        }

        SavedState(Parcel parcel) {
            super(parcel);
            this.e = parcel.readByte() != 0;
        }
    }

    class a extends t {
        final /* synthetic */ e n;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(View view, e eVar) {
            super(view);
            this.n = eVar;
        }

        public p a() {
            return this.n;
        }

        @SuppressLint({"SyntheticAccessor"})
        public boolean b() {
            if (AppCompatSpinner.this.getInternalPopup().a()) {
                return true;
            }
            AppCompatSpinner.this.a();
            return true;
        }
    }

    class b implements ViewTreeObserver.OnGlobalLayoutListener {
        b() {
        }

        public void onGlobalLayout() {
            if (!AppCompatSpinner.this.getInternalPopup().a()) {
                AppCompatSpinner.this.a();
            }
            ViewTreeObserver viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
            if (viewTreeObserver == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 16) {
                viewTreeObserver.removeOnGlobalLayoutListener(this);
            } else {
                viewTreeObserver.removeGlobalOnLayoutListener(this);
            }
        }
    }

    class c implements f, DialogInterface.OnClickListener {
        androidx.appcompat.app.c e;

        /* renamed from: f  reason: collision with root package name */
        private ListAdapter f218f;

        /* renamed from: g  reason: collision with root package name */
        private CharSequence f219g;

        c() {
        }

        public boolean a() {
            androidx.appcompat.app.c cVar = this.e;
            if (cVar != null) {
                return cVar.isShowing();
            }
            return false;
        }

        public int b() {
            return 0;
        }

        public void b(int i2) {
            Log.e("AppCompatSpinner", "Cannot set vertical offset for MODE_DIALOG, ignoring");
        }

        public void c(int i2) {
            Log.e("AppCompatSpinner", "Cannot set horizontal (original) offset for MODE_DIALOG, ignoring");
        }

        public int d() {
            return 0;
        }

        public void dismiss() {
            androidx.appcompat.app.c cVar = this.e;
            if (cVar != null) {
                cVar.dismiss();
                this.e = null;
            }
        }

        public Drawable e() {
            return null;
        }

        public CharSequence f() {
            return this.f219g;
        }

        public void onClick(DialogInterface dialogInterface, int i2) {
            AppCompatSpinner.this.setSelection(i2);
            if (AppCompatSpinner.this.getOnItemClickListener() != null) {
                AppCompatSpinner.this.performItemClick((View) null, i2, this.f218f.getItemId(i2));
            }
            dismiss();
        }

        public void a(ListAdapter listAdapter) {
            this.f218f = listAdapter;
        }

        public void a(CharSequence charSequence) {
            this.f219g = charSequence;
        }

        public void a(int i2, int i3) {
            if (this.f218f != null) {
                c.a aVar = new c.a(AppCompatSpinner.this.getPopupContext());
                CharSequence charSequence = this.f219g;
                if (charSequence != null) {
                    aVar.b(charSequence);
                }
                aVar.a(this.f218f, AppCompatSpinner.this.getSelectedItemPosition(), (DialogInterface.OnClickListener) this);
                androidx.appcompat.app.c a = aVar.a();
                this.e = a;
                ListView b = a.b();
                if (Build.VERSION.SDK_INT >= 17) {
                    b.setTextDirection(i2);
                    b.setTextAlignment(i3);
                }
                this.e.show();
            }
        }

        public void a(Drawable drawable) {
            Log.e("AppCompatSpinner", "Cannot set popup background for MODE_DIALOG, ignoring");
        }

        public void a(int i2) {
            Log.e("AppCompatSpinner", "Cannot set horizontal offset for MODE_DIALOG, ignoring");
        }
    }

    private static class d implements ListAdapter, SpinnerAdapter {
        private SpinnerAdapter e;

        /* renamed from: f  reason: collision with root package name */
        private ListAdapter f221f;

        public d(SpinnerAdapter spinnerAdapter, Resources.Theme theme) {
            this.e = spinnerAdapter;
            if (spinnerAdapter instanceof ListAdapter) {
                this.f221f = (ListAdapter) spinnerAdapter;
            }
            if (theme == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 23 && (spinnerAdapter instanceof ThemedSpinnerAdapter)) {
                ThemedSpinnerAdapter themedSpinnerAdapter = (ThemedSpinnerAdapter) spinnerAdapter;
                if (themedSpinnerAdapter.getDropDownViewTheme() != theme) {
                    themedSpinnerAdapter.setDropDownViewTheme(theme);
                }
            } else if (spinnerAdapter instanceof c0) {
                c0 c0Var = (c0) spinnerAdapter;
                if (c0Var.getDropDownViewTheme() == null) {
                    c0Var.setDropDownViewTheme(theme);
                }
            }
        }

        public boolean areAllItemsEnabled() {
            ListAdapter listAdapter = this.f221f;
            if (listAdapter != null) {
                return listAdapter.areAllItemsEnabled();
            }
            return true;
        }

        public int getCount() {
            SpinnerAdapter spinnerAdapter = this.e;
            if (spinnerAdapter == null) {
                return 0;
            }
            return spinnerAdapter.getCount();
        }

        public View getDropDownView(int i2, View view, ViewGroup viewGroup) {
            SpinnerAdapter spinnerAdapter = this.e;
            if (spinnerAdapter == null) {
                return null;
            }
            return spinnerAdapter.getDropDownView(i2, view, viewGroup);
        }

        public Object getItem(int i2) {
            SpinnerAdapter spinnerAdapter = this.e;
            if (spinnerAdapter == null) {
                return null;
            }
            return spinnerAdapter.getItem(i2);
        }

        public long getItemId(int i2) {
            SpinnerAdapter spinnerAdapter = this.e;
            if (spinnerAdapter == null) {
                return -1;
            }
            return spinnerAdapter.getItemId(i2);
        }

        public int getItemViewType(int i2) {
            return 0;
        }

        public View getView(int i2, View view, ViewGroup viewGroup) {
            return getDropDownView(i2, view, viewGroup);
        }

        public int getViewTypeCount() {
            return 1;
        }

        public boolean hasStableIds() {
            SpinnerAdapter spinnerAdapter = this.e;
            return spinnerAdapter != null && spinnerAdapter.hasStableIds();
        }

        public boolean isEmpty() {
            return getCount() == 0;
        }

        public boolean isEnabled(int i2) {
            ListAdapter listAdapter = this.f221f;
            if (listAdapter != null) {
                return listAdapter.isEnabled(i2);
            }
            return true;
        }

        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            SpinnerAdapter spinnerAdapter = this.e;
            if (spinnerAdapter != null) {
                spinnerAdapter.registerDataSetObserver(dataSetObserver);
            }
        }

        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            SpinnerAdapter spinnerAdapter = this.e;
            if (spinnerAdapter != null) {
                spinnerAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }
    }

    class e extends u implements f {
        private CharSequence M;
        ListAdapter N;
        private final Rect O = new Rect();
        private int P;

        class a implements AdapterView.OnItemClickListener {
            a(AppCompatSpinner appCompatSpinner) {
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
                AppCompatSpinner.this.setSelection(i2);
                if (AppCompatSpinner.this.getOnItemClickListener() != null) {
                    e eVar = e.this;
                    AppCompatSpinner.this.performItemClick(view, i2, eVar.N.getItemId(i2));
                }
                e.this.dismiss();
            }
        }

        class b implements ViewTreeObserver.OnGlobalLayoutListener {
            b() {
            }

            public void onGlobalLayout() {
                e eVar = e.this;
                if (!eVar.b(AppCompatSpinner.this)) {
                    e.this.dismiss();
                    return;
                }
                e.this.m();
                e.super.c();
            }
        }

        class c implements PopupWindow.OnDismissListener {
            final /* synthetic */ ViewTreeObserver.OnGlobalLayoutListener e;

            c(ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
                this.e = onGlobalLayoutListener;
            }

            public void onDismiss() {
                ViewTreeObserver viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
                if (viewTreeObserver != null) {
                    viewTreeObserver.removeGlobalOnLayoutListener(this.e);
                }
            }
        }

        public e(Context context, AttributeSet attributeSet, int i2) {
            super(context, attributeSet, i2);
            a((View) AppCompatSpinner.this);
            a(true);
            h(0);
            setOnItemClickListener(new a(AppCompatSpinner.this));
        }

        /* access modifiers changed from: package-private */
        public boolean b(View view) {
            return v.C(view) && view.getGlobalVisibleRect(this.O);
        }

        public void c(int i2) {
            this.P = i2;
        }

        public CharSequence f() {
            return this.M;
        }

        /* access modifiers changed from: package-private */
        public void m() {
            int i2;
            Drawable e = e();
            int i3 = 0;
            if (e != null) {
                e.getPadding(AppCompatSpinner.this.l);
                i3 = m0.a(AppCompatSpinner.this) ? AppCompatSpinner.this.l.right : -AppCompatSpinner.this.l.left;
            } else {
                Rect rect = AppCompatSpinner.this.l;
                rect.right = 0;
                rect.left = 0;
            }
            int paddingLeft = AppCompatSpinner.this.getPaddingLeft();
            int paddingRight = AppCompatSpinner.this.getPaddingRight();
            int width = AppCompatSpinner.this.getWidth();
            AppCompatSpinner appCompatSpinner = AppCompatSpinner.this;
            int i4 = appCompatSpinner.k;
            if (i4 == -2) {
                int a2 = appCompatSpinner.a((SpinnerAdapter) this.N, e());
                int i5 = AppCompatSpinner.this.getContext().getResources().getDisplayMetrics().widthPixels;
                Rect rect2 = AppCompatSpinner.this.l;
                int i6 = (i5 - rect2.left) - rect2.right;
                if (a2 > i6) {
                    a2 = i6;
                }
                e(Math.max(a2, (width - paddingLeft) - paddingRight));
            } else if (i4 == -1) {
                e((width - paddingLeft) - paddingRight);
            } else {
                e(i4);
            }
            if (m0.a(AppCompatSpinner.this)) {
                i2 = i3 + (((width - paddingRight) - j()) - n());
            } else {
                i2 = i3 + paddingLeft + n();
            }
            a(i2);
        }

        public int n() {
            return this.P;
        }

        public void a(ListAdapter listAdapter) {
            super.a(listAdapter);
            this.N = listAdapter;
        }

        public void a(CharSequence charSequence) {
            this.M = charSequence;
        }

        public void a(int i2, int i3) {
            ViewTreeObserver viewTreeObserver;
            boolean a2 = a();
            m();
            g(2);
            super.c();
            ListView g2 = g();
            g2.setChoiceMode(1);
            if (Build.VERSION.SDK_INT >= 17) {
                g2.setTextDirection(i2);
                g2.setTextAlignment(i3);
            }
            i(AppCompatSpinner.this.getSelectedItemPosition());
            if (!a2 && (viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver()) != null) {
                b bVar = new b();
                viewTreeObserver.addOnGlobalLayoutListener(bVar);
                setOnDismissListener(new c(bVar));
            }
        }
    }

    interface f {
        void a(int i2);

        void a(int i2, int i3);

        void a(Drawable drawable);

        void a(ListAdapter listAdapter);

        void a(CharSequence charSequence);

        boolean a();

        int b();

        void b(int i2);

        void c(int i2);

        int d();

        void dismiss();

        Drawable e();

        CharSequence f();
    }

    public AppCompatSpinner(Context context) {
        this(context, (AttributeSet) null);
    }

    /* access modifiers changed from: package-private */
    public int a(SpinnerAdapter spinnerAdapter, Drawable drawable) {
        int i2 = 0;
        if (spinnerAdapter == null) {
            return 0;
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
        int max = Math.max(0, getSelectedItemPosition());
        int min = Math.min(spinnerAdapter.getCount(), max + 15);
        View view = null;
        int i3 = 0;
        for (int max2 = Math.max(0, max - (15 - (min - max))); max2 < min; max2++) {
            int itemViewType = spinnerAdapter.getItemViewType(max2);
            if (itemViewType != i2) {
                view = null;
                i2 = itemViewType;
            }
            view = spinnerAdapter.getView(max2, view, this);
            if (view.getLayoutParams() == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            view.measure(makeMeasureSpec, makeMeasureSpec2);
            i3 = Math.max(i3, view.getMeasuredWidth());
        }
        if (drawable == null) {
            return i3;
        }
        drawable.getPadding(this.l);
        Rect rect = this.l;
        return i3 + rect.left + rect.right;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        d dVar = this.e;
        if (dVar != null) {
            dVar.a();
        }
    }

    public int getDropDownHorizontalOffset() {
        f fVar = this.f217j;
        if (fVar != null) {
            return fVar.b();
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getDropDownHorizontalOffset();
        }
        return 0;
    }

    public int getDropDownVerticalOffset() {
        f fVar = this.f217j;
        if (fVar != null) {
            return fVar.d();
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getDropDownVerticalOffset();
        }
        return 0;
    }

    public int getDropDownWidth() {
        if (this.f217j != null) {
            return this.k;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getDropDownWidth();
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public final f getInternalPopup() {
        return this.f217j;
    }

    public Drawable getPopupBackground() {
        f fVar = this.f217j;
        if (fVar != null) {
            return fVar.e();
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getPopupBackground();
        }
        return null;
    }

    public Context getPopupContext() {
        return this.f213f;
    }

    public CharSequence getPrompt() {
        f fVar = this.f217j;
        return fVar != null ? fVar.f() : super.getPrompt();
    }

    public ColorStateList getSupportBackgroundTintList() {
        d dVar = this.e;
        if (dVar != null) {
            return dVar.b();
        }
        return null;
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        d dVar = this.e;
        if (dVar != null) {
            return dVar.c();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        f fVar = this.f217j;
        if (fVar != null && fVar.a()) {
            this.f217j.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        if (this.f217j != null && View.MeasureSpec.getMode(i2) == Integer.MIN_VALUE) {
            setMeasuredDimension(Math.min(Math.max(getMeasuredWidth(), a(getAdapter(), getBackground())), View.MeasureSpec.getSize(i2)), getMeasuredHeight());
        }
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        ViewTreeObserver viewTreeObserver;
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.e && (viewTreeObserver = getViewTreeObserver()) != null) {
            viewTreeObserver.addOnGlobalLayoutListener(new b());
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        f fVar = this.f217j;
        savedState.e = fVar != null && fVar.a();
        return savedState;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        t tVar = this.f214g;
        if (tVar == null || !tVar.onTouch(this, motionEvent)) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    public boolean performClick() {
        f fVar = this.f217j;
        if (fVar == null) {
            return super.performClick();
        }
        if (fVar.a()) {
            return true;
        }
        a();
        return true;
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        d dVar = this.e;
        if (dVar != null) {
            dVar.a(drawable);
        }
    }

    public void setBackgroundResource(int i2) {
        super.setBackgroundResource(i2);
        d dVar = this.e;
        if (dVar != null) {
            dVar.a(i2);
        }
    }

    public void setDropDownHorizontalOffset(int i2) {
        f fVar = this.f217j;
        if (fVar != null) {
            fVar.c(i2);
            this.f217j.a(i2);
        } else if (Build.VERSION.SDK_INT >= 16) {
            super.setDropDownHorizontalOffset(i2);
        }
    }

    public void setDropDownVerticalOffset(int i2) {
        f fVar = this.f217j;
        if (fVar != null) {
            fVar.b(i2);
        } else if (Build.VERSION.SDK_INT >= 16) {
            super.setDropDownVerticalOffset(i2);
        }
    }

    public void setDropDownWidth(int i2) {
        if (this.f217j != null) {
            this.k = i2;
        } else if (Build.VERSION.SDK_INT >= 16) {
            super.setDropDownWidth(i2);
        }
    }

    public void setPopupBackgroundDrawable(Drawable drawable) {
        f fVar = this.f217j;
        if (fVar != null) {
            fVar.a(drawable);
        } else if (Build.VERSION.SDK_INT >= 16) {
            super.setPopupBackgroundDrawable(drawable);
        }
    }

    public void setPopupBackgroundResource(int i2) {
        setPopupBackgroundDrawable(androidx.appcompat.a.a.a.c(getPopupContext(), i2));
    }

    public void setPrompt(CharSequence charSequence) {
        f fVar = this.f217j;
        if (fVar != null) {
            fVar.a(charSequence);
        } else {
            super.setPrompt(charSequence);
        }
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        d dVar = this.e;
        if (dVar != null) {
            dVar.b(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        d dVar = this.e;
        if (dVar != null) {
            dVar.a(mode);
        }
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.spinnerStyle);
    }

    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        if (!this.f216i) {
            this.f215h = spinnerAdapter;
            return;
        }
        super.setAdapter(spinnerAdapter);
        if (this.f217j != null) {
            Context context = this.f213f;
            if (context == null) {
                context = getContext();
            }
            this.f217j.a((ListAdapter) new d(spinnerAdapter, context.getTheme()));
        }
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, -1);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet, int i2, int i3) {
        this(context, attributeSet, i2, i3, (Resources.Theme) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0048, code lost:
        if (r11 != null) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004a, code lost:
        r11.recycle();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005b, code lost:
        if (r11 != null) goto L_0x004a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0062  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public AppCompatSpinner(android.content.Context r7, android.util.AttributeSet r8, int r9, int r10, android.content.res.Resources.Theme r11) {
        /*
            r6 = this;
            r6.<init>(r7, r8, r9)
            android.graphics.Rect r0 = new android.graphics.Rect
            r0.<init>()
            r6.l = r0
            int[] r0 = androidx.appcompat.R$styleable.Spinner
            r1 = 0
            androidx.appcompat.widget.g0 r0 = androidx.appcompat.widget.g0.a(r7, r8, r0, r9, r1)
            androidx.appcompat.widget.d r2 = new androidx.appcompat.widget.d
            r2.<init>(r6)
            r6.e = r2
            if (r11 == 0) goto L_0x0022
            androidx.appcompat.d.d r2 = new androidx.appcompat.d.d
            r2.<init>((android.content.Context) r7, (android.content.res.Resources.Theme) r11)
            r6.f213f = r2
            goto L_0x0034
        L_0x0022:
            int r11 = androidx.appcompat.R$styleable.Spinner_popupTheme
            int r11 = r0.g(r11, r1)
            if (r11 == 0) goto L_0x0032
            androidx.appcompat.d.d r2 = new androidx.appcompat.d.d
            r2.<init>((android.content.Context) r7, (int) r11)
            r6.f213f = r2
            goto L_0x0034
        L_0x0032:
            r6.f213f = r7
        L_0x0034:
            r11 = -1
            r2 = 0
            if (r10 != r11) goto L_0x0066
            int[] r11 = m     // Catch:{ Exception -> 0x0052, all -> 0x0050 }
            android.content.res.TypedArray r11 = r7.obtainStyledAttributes(r8, r11, r9, r1)     // Catch:{ Exception -> 0x0052, all -> 0x0050 }
            boolean r3 = r11.hasValue(r1)     // Catch:{ Exception -> 0x004e }
            if (r3 == 0) goto L_0x0048
            int r10 = r11.getInt(r1, r1)     // Catch:{ Exception -> 0x004e }
        L_0x0048:
            if (r11 == 0) goto L_0x0066
        L_0x004a:
            r11.recycle()
            goto L_0x0066
        L_0x004e:
            r3 = move-exception
            goto L_0x0054
        L_0x0050:
            r7 = move-exception
            goto L_0x0060
        L_0x0052:
            r3 = move-exception
            r11 = r2
        L_0x0054:
            java.lang.String r4 = "AppCompatSpinner"
            java.lang.String r5 = "Could not read android:spinnerMode"
            android.util.Log.i(r4, r5, r3)     // Catch:{ all -> 0x005e }
            if (r11 == 0) goto L_0x0066
            goto L_0x004a
        L_0x005e:
            r7 = move-exception
            r2 = r11
        L_0x0060:
            if (r2 == 0) goto L_0x0065
            r2.recycle()
        L_0x0065:
            throw r7
        L_0x0066:
            r11 = 1
            if (r10 == 0) goto L_0x00a3
            if (r10 == r11) goto L_0x006c
            goto L_0x00b3
        L_0x006c:
            androidx.appcompat.widget.AppCompatSpinner$e r10 = new androidx.appcompat.widget.AppCompatSpinner$e
            android.content.Context r3 = r6.f213f
            r10.<init>(r3, r8, r9)
            android.content.Context r3 = r6.f213f
            int[] r4 = androidx.appcompat.R$styleable.Spinner
            androidx.appcompat.widget.g0 r1 = androidx.appcompat.widget.g0.a(r3, r8, r4, r9, r1)
            int r3 = androidx.appcompat.R$styleable.Spinner_android_dropDownWidth
            r4 = -2
            int r3 = r1.f(r3, r4)
            r6.k = r3
            int r3 = androidx.appcompat.R$styleable.Spinner_android_popupBackground
            android.graphics.drawable.Drawable r3 = r1.b(r3)
            r10.a((android.graphics.drawable.Drawable) r3)
            int r3 = androidx.appcompat.R$styleable.Spinner_android_prompt
            java.lang.String r3 = r0.d(r3)
            r10.a((java.lang.CharSequence) r3)
            r1.a()
            r6.f217j = r10
            androidx.appcompat.widget.AppCompatSpinner$a r1 = new androidx.appcompat.widget.AppCompatSpinner$a
            r1.<init>(r6, r10)
            r6.f214g = r1
            goto L_0x00b3
        L_0x00a3:
            androidx.appcompat.widget.AppCompatSpinner$c r10 = new androidx.appcompat.widget.AppCompatSpinner$c
            r10.<init>()
            r6.f217j = r10
            int r1 = androidx.appcompat.R$styleable.Spinner_android_prompt
            java.lang.String r1 = r0.d(r1)
            r10.a((java.lang.CharSequence) r1)
        L_0x00b3:
            int r10 = androidx.appcompat.R$styleable.Spinner_android_entries
            java.lang.CharSequence[] r10 = r0.f(r10)
            if (r10 == 0) goto L_0x00cb
            android.widget.ArrayAdapter r1 = new android.widget.ArrayAdapter
            r3 = 17367048(0x1090008, float:2.5162948E-38)
            r1.<init>(r7, r3, r10)
            int r7 = androidx.appcompat.R$layout.support_simple_spinner_dropdown_item
            r1.setDropDownViewResource(r7)
            r6.setAdapter((android.widget.SpinnerAdapter) r1)
        L_0x00cb:
            r0.a()
            r6.f216i = r11
            android.widget.SpinnerAdapter r7 = r6.f215h
            if (r7 == 0) goto L_0x00d9
            r6.setAdapter((android.widget.SpinnerAdapter) r7)
            r6.f215h = r2
        L_0x00d9:
            androidx.appcompat.widget.d r7 = r6.e
            r7.a(r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.AppCompatSpinner.<init>(android.content.Context, android.util.AttributeSet, int, int, android.content.res.Resources$Theme):void");
    }

    /* access modifiers changed from: package-private */
    public void a() {
        if (Build.VERSION.SDK_INT >= 17) {
            this.f217j.a(getTextDirection(), getTextAlignment());
        } else {
            this.f217j.a(-1, -1);
        }
    }
}
