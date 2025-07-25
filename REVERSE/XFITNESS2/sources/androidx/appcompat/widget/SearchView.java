package androidx.appcompat.widget;

import android.app.PendingIntent;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$dimen;
import androidx.appcompat.R$id;
import androidx.appcompat.R$layout;
import androidx.appcompat.R$string;
import androidx.appcompat.R$styleable;
import androidx.core.h.v;
import androidx.customview.view.AbsSavedState;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

public class SearchView extends LinearLayoutCompat implements androidx.appcompat.d.c {
    static final k u0 = new k();
    final ImageView A;
    private final View B;
    private o C;
    private Rect D;
    private Rect E;
    private int[] F;
    private int[] G;
    private final ImageView H;
    private final Drawable I;
    private final int J;
    private final int K;
    private final Intent L;
    private final Intent M;
    private final CharSequence N;
    private m O;
    private l P;
    View.OnFocusChangeListener Q;
    private n R;
    private View.OnClickListener S;
    private boolean T;
    private boolean U;
    g.b.a.a V;
    private boolean W;
    private CharSequence a0;
    private boolean b0;
    private boolean c0;
    private int d0;
    private boolean e0;
    private CharSequence f0;
    private CharSequence g0;
    private boolean h0;
    private int i0;
    SearchableInfo j0;
    private Bundle k0;
    private final Runnable l0;
    private Runnable m0;
    private final WeakHashMap<String, Drawable.ConstantState> n0;
    private final View.OnClickListener o0;
    View.OnKeyListener p0;
    private final TextView.OnEditorActionListener q0;
    private final AdapterView.OnItemClickListener r0;
    private final AdapterView.OnItemSelectedListener s0;
    final SearchAutoComplete t;
    private TextWatcher t0;
    private final View u;
    private final View v;
    private final View w;
    final ImageView x;
    final ImageView y;
    final ImageView z;

    static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* renamed from: g  reason: collision with root package name */
        boolean f249g;

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

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "SearchView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " isIconified=" + this.f249g + "}";
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeValue(Boolean.valueOf(this.f249g));
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f249g = ((Boolean) parcel.readValue((ClassLoader) null)).booleanValue();
        }
    }

    public static class SearchAutoComplete extends AppCompatAutoCompleteTextView {

        /* renamed from: h  reason: collision with root package name */
        private int f250h;

        /* renamed from: i  reason: collision with root package name */
        private SearchView f251i;

        /* renamed from: j  reason: collision with root package name */
        private boolean f252j;
        final Runnable k;

        class a implements Runnable {
            a() {
            }

            public void run() {
                SearchAutoComplete.this.b();
            }
        }

        public SearchAutoComplete(Context context) {
            this(context, (AttributeSet) null);
        }

        private int getSearchViewTextMinWidthDp() {
            Configuration configuration = getResources().getConfiguration();
            int i2 = configuration.screenWidthDp;
            int i3 = configuration.screenHeightDp;
            if (i2 >= 960 && i3 >= 720 && configuration.orientation == 2) {
                return 256;
            }
            if (i2 < 600) {
                return (i2 < 640 || i3 < 480) ? 160 : 192;
            }
            return 192;
        }

        /* access modifiers changed from: package-private */
        public boolean a() {
            return TextUtils.getTrimmedLength(getText()) == 0;
        }

        /* access modifiers changed from: package-private */
        public void b() {
            if (this.f252j) {
                ((InputMethodManager) getContext().getSystemService("input_method")).showSoftInput(this, 0);
                this.f252j = false;
            }
        }

        public boolean enoughToFilter() {
            return this.f250h <= 0 || super.enoughToFilter();
        }

        public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
            InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
            if (this.f252j) {
                removeCallbacks(this.k);
                post(this.k);
            }
            return onCreateInputConnection;
        }

        /* access modifiers changed from: protected */
        public void onFinishInflate() {
            super.onFinishInflate();
            setMinWidth((int) TypedValue.applyDimension(1, (float) getSearchViewTextMinWidthDp(), getResources().getDisplayMetrics()));
        }

        /* access modifiers changed from: protected */
        public void onFocusChanged(boolean z, int i2, Rect rect) {
            super.onFocusChanged(z, i2, rect);
            this.f251i.j();
        }

        public boolean onKeyPreIme(int i2, KeyEvent keyEvent) {
            if (i2 == 4) {
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    KeyEvent.DispatcherState keyDispatcherState = getKeyDispatcherState();
                    if (keyDispatcherState != null) {
                        keyDispatcherState.startTracking(keyEvent, this);
                    }
                    return true;
                } else if (keyEvent.getAction() == 1) {
                    KeyEvent.DispatcherState keyDispatcherState2 = getKeyDispatcherState();
                    if (keyDispatcherState2 != null) {
                        keyDispatcherState2.handleUpEvent(keyEvent);
                    }
                    if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                        this.f251i.clearFocus();
                        setImeVisibility(false);
                        return true;
                    }
                }
            }
            return super.onKeyPreIme(i2, keyEvent);
        }

        public void onWindowFocusChanged(boolean z) {
            super.onWindowFocusChanged(z);
            if (z && this.f251i.hasFocus() && getVisibility() == 0) {
                this.f252j = true;
                if (SearchView.a(getContext())) {
                    SearchView.u0.a(this, true);
                }
            }
        }

        public void performCompletion() {
        }

        /* access modifiers changed from: protected */
        public void replaceText(CharSequence charSequence) {
        }

        /* access modifiers changed from: package-private */
        public void setImeVisibility(boolean z) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
            if (!z) {
                this.f252j = false;
                removeCallbacks(this.k);
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            } else if (inputMethodManager.isActive(this)) {
                this.f252j = false;
                removeCallbacks(this.k);
                inputMethodManager.showSoftInput(this, 0);
            } else {
                this.f252j = true;
            }
        }

        /* access modifiers changed from: package-private */
        public void setSearchView(SearchView searchView) {
            this.f251i = searchView;
        }

        public void setThreshold(int i2) {
            super.setThreshold(i2);
            this.f250h = i2;
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, R$attr.autoCompleteTextViewStyle);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet, int i2) {
            super(context, attributeSet, i2);
            this.k = new a();
            this.f250h = getThreshold();
        }
    }

    class a implements TextWatcher {
        a() {
        }

        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        }

        public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            SearchView.this.b(charSequence);
        }
    }

    class b implements Runnable {
        b() {
        }

        public void run() {
            SearchView.this.l();
        }
    }

    class c implements Runnable {
        c() {
        }

        public void run() {
            g.b.a.a aVar = SearchView.this.V;
            if (aVar instanceof a0) {
                aVar.b((Cursor) null);
            }
        }
    }

    class d implements View.OnFocusChangeListener {
        d() {
        }

        public void onFocusChange(View view, boolean z) {
            SearchView searchView = SearchView.this;
            View.OnFocusChangeListener onFocusChangeListener = searchView.Q;
            if (onFocusChangeListener != null) {
                onFocusChangeListener.onFocusChange(searchView, z);
            }
        }
    }

    class e implements View.OnLayoutChangeListener {
        e() {
        }

        public void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
            SearchView.this.d();
        }
    }

    class f implements View.OnClickListener {
        f() {
        }

        public void onClick(View view) {
            SearchView searchView = SearchView.this;
            if (view == searchView.x) {
                searchView.h();
            } else if (view == searchView.z) {
                searchView.g();
            } else if (view == searchView.y) {
                searchView.i();
            } else if (view == searchView.A) {
                searchView.k();
            } else if (view == searchView.t) {
                searchView.e();
            }
        }
    }

    class g implements View.OnKeyListener {
        g() {
        }

        public boolean onKey(View view, int i2, KeyEvent keyEvent) {
            SearchView searchView = SearchView.this;
            if (searchView.j0 == null) {
                return false;
            }
            if (searchView.t.isPopupShowing() && SearchView.this.t.getListSelection() != -1) {
                return SearchView.this.a(view, i2, keyEvent);
            }
            if (SearchView.this.t.a() || !keyEvent.hasNoModifiers() || keyEvent.getAction() != 1 || i2 != 66) {
                return false;
            }
            view.cancelLongPress();
            SearchView searchView2 = SearchView.this;
            searchView2.a(0, (String) null, searchView2.t.getText().toString());
            return true;
        }
    }

    class h implements TextView.OnEditorActionListener {
        h() {
        }

        public boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
            SearchView.this.i();
            return true;
        }
    }

    class i implements AdapterView.OnItemClickListener {
        i() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
            SearchView.this.a(i2, 0, (String) null);
        }
    }

    class j implements AdapterView.OnItemSelectedListener {
        j() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j2) {
            SearchView.this.d(i2);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public interface l {
        boolean a();
    }

    public interface m {
        boolean a(String str);

        boolean b(String str);
    }

    public interface n {
        boolean a(int i2);

        boolean b(int i2);
    }

    private static class o extends TouchDelegate {
        private final View a;
        private final Rect b = new Rect();
        private final Rect c = new Rect();
        private final Rect d = new Rect();
        private final int e;

        /* renamed from: f  reason: collision with root package name */
        private boolean f253f;

        public o(Rect rect, Rect rect2, View view) {
            super(rect, view);
            this.e = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
            a(rect, rect2);
            this.a = view;
        }

        public void a(Rect rect, Rect rect2) {
            this.b.set(rect);
            this.d.set(rect);
            Rect rect3 = this.d;
            int i2 = this.e;
            rect3.inset(-i2, -i2);
            this.c.set(rect2);
        }

        /* JADX WARNING: Removed duplicated region for block: B:19:0x0041  */
        /* JADX WARNING: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTouchEvent(android.view.MotionEvent r8) {
            /*
                r7 = this;
                float r0 = r8.getX()
                int r0 = (int) r0
                float r1 = r8.getY()
                int r1 = (int) r1
                int r2 = r8.getAction()
                r3 = 2
                r4 = 1
                r5 = 0
                if (r2 == 0) goto L_0x0032
                if (r2 == r4) goto L_0x0020
                if (r2 == r3) goto L_0x0020
                r6 = 3
                if (r2 == r6) goto L_0x001b
                goto L_0x003d
            L_0x001b:
                boolean r2 = r7.f253f
                r7.f253f = r5
                goto L_0x002f
            L_0x0020:
                boolean r2 = r7.f253f
                if (r2 == 0) goto L_0x002f
                android.graphics.Rect r6 = r7.d
                boolean r6 = r6.contains(r0, r1)
                if (r6 != 0) goto L_0x002f
                r4 = r2
                r2 = 0
                goto L_0x003f
            L_0x002f:
                r4 = r2
            L_0x0030:
                r2 = 1
                goto L_0x003f
            L_0x0032:
                android.graphics.Rect r2 = r7.b
                boolean r2 = r2.contains(r0, r1)
                if (r2 == 0) goto L_0x003d
                r7.f253f = r4
                goto L_0x0030
            L_0x003d:
                r2 = 1
                r4 = 0
            L_0x003f:
                if (r4 == 0) goto L_0x0072
                if (r2 == 0) goto L_0x005f
                android.graphics.Rect r2 = r7.c
                boolean r2 = r2.contains(r0, r1)
                if (r2 != 0) goto L_0x005f
                android.view.View r0 = r7.a
                int r0 = r0.getWidth()
                int r0 = r0 / r3
                float r0 = (float) r0
                android.view.View r1 = r7.a
                int r1 = r1.getHeight()
                int r1 = r1 / r3
                float r1 = (float) r1
                r8.setLocation(r0, r1)
                goto L_0x006c
            L_0x005f:
                android.graphics.Rect r2 = r7.c
                int r3 = r2.left
                int r0 = r0 - r3
                float r0 = (float) r0
                int r2 = r2.top
                int r1 = r1 - r2
                float r1 = (float) r1
                r8.setLocation(r0, r1)
            L_0x006c:
                android.view.View r0 = r7.a
                boolean r5 = r0.dispatchTouchEvent(r8)
            L_0x0072:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.SearchView.o.onTouchEvent(android.view.MotionEvent):boolean");
        }
    }

    public SearchView(Context context) {
        this(context, (AttributeSet) null);
    }

    private void b(boolean z2) {
        this.U = z2;
        int i2 = 0;
        int i3 = z2 ? 0 : 8;
        boolean z3 = !TextUtils.isEmpty(this.t.getText());
        this.x.setVisibility(i3);
        a(z3);
        this.u.setVisibility(z2 ? 8 : 0);
        if (this.H.getDrawable() == null || this.T) {
            i2 = 8;
        }
        this.H.setVisibility(i2);
        q();
        c(!z3);
        t();
    }

    private CharSequence c(CharSequence charSequence) {
        if (!this.T || this.I == null) {
            return charSequence;
        }
        double textSize = (double) this.t.getTextSize();
        Double.isNaN(textSize);
        int i2 = (int) (textSize * 1.25d);
        this.I.setBounds(0, 0, i2, i2);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("   ");
        spannableStringBuilder.setSpan(new ImageSpan(this.I), 1, 2, 33);
        spannableStringBuilder.append(charSequence);
        return spannableStringBuilder;
    }

    private void e(int i2) {
        Editable text = this.t.getText();
        Cursor a2 = this.V.a();
        if (a2 != null) {
            if (a2.moveToPosition(i2)) {
                CharSequence a3 = this.V.a(a2);
                if (a3 != null) {
                    setQuery(a3);
                } else {
                    setQuery(text);
                }
            } else {
                setQuery(text);
            }
        }
    }

    private int getPreferredHeight() {
        return getContext().getResources().getDimensionPixelSize(R$dimen.abc_search_view_preferred_height);
    }

    private int getPreferredWidth() {
        return getContext().getResources().getDimensionPixelSize(R$dimen.abc_search_view_preferred_width);
    }

    private void m() {
        this.t.dismissDropDown();
    }

    private boolean n() {
        SearchableInfo searchableInfo = this.j0;
        if (searchableInfo == null || !searchableInfo.getVoiceSearchEnabled()) {
            return false;
        }
        Intent intent = null;
        if (this.j0.getVoiceSearchLaunchWebSearch()) {
            intent = this.L;
        } else if (this.j0.getVoiceSearchLaunchRecognizer()) {
            intent = this.M;
        }
        if (intent == null || getContext().getPackageManager().resolveActivity(intent, 65536) == null) {
            return false;
        }
        return true;
    }

    private boolean o() {
        return (this.W || this.e0) && !f();
    }

    private void p() {
        post(this.l0);
    }

    private void q() {
        boolean z2 = true;
        boolean z3 = !TextUtils.isEmpty(this.t.getText());
        int i2 = 0;
        if (!z3 && (!this.T || this.h0)) {
            z2 = false;
        }
        ImageView imageView = this.z;
        if (!z2) {
            i2 = 8;
        }
        imageView.setVisibility(i2);
        Drawable drawable = this.z.getDrawable();
        if (drawable != null) {
            drawable.setState(z3 ? ViewGroup.ENABLED_STATE_SET : ViewGroup.EMPTY_STATE_SET);
        }
    }

    private void r() {
        CharSequence queryHint = getQueryHint();
        SearchAutoComplete searchAutoComplete = this.t;
        if (queryHint == null) {
            queryHint = BuildConfig.FLAVOR;
        }
        searchAutoComplete.setHint(c(queryHint));
    }

    private void s() {
        this.t.setThreshold(this.j0.getSuggestThreshold());
        this.t.setImeOptions(this.j0.getImeOptions());
        int inputType = this.j0.getInputType();
        int i2 = 1;
        if ((inputType & 15) == 1) {
            inputType &= -65537;
            if (this.j0.getSuggestAuthority() != null) {
                inputType = inputType | 65536 | 524288;
            }
        }
        this.t.setInputType(inputType);
        g.b.a.a aVar = this.V;
        if (aVar != null) {
            aVar.b((Cursor) null);
        }
        if (this.j0.getSuggestAuthority() != null) {
            a0 a0Var = new a0(getContext(), this, this.j0, this.n0);
            this.V = a0Var;
            this.t.setAdapter(a0Var);
            a0 a0Var2 = (a0) this.V;
            if (this.b0) {
                i2 = 2;
            }
            a0Var2.a(i2);
        }
    }

    private void setQuery(CharSequence charSequence) {
        this.t.setText(charSequence);
        this.t.setSelection(TextUtils.isEmpty(charSequence) ? 0 : charSequence.length());
    }

    private void t() {
        this.w.setVisibility((!o() || !(this.y.getVisibility() == 0 || this.A.getVisibility() == 0)) ? 8 : 0);
    }

    public void a(CharSequence charSequence, boolean z2) {
        this.t.setText(charSequence);
        if (charSequence != null) {
            SearchAutoComplete searchAutoComplete = this.t;
            searchAutoComplete.setSelection(searchAutoComplete.length());
            this.g0 = charSequence;
        }
        if (z2 && !TextUtils.isEmpty(charSequence)) {
            i();
        }
    }

    public void clearFocus() {
        this.c0 = true;
        super.clearFocus();
        this.t.clearFocus();
        this.t.setImeVisibility(false);
        this.c0 = false;
    }

    /* access modifiers changed from: package-private */
    public void d() {
        int i2;
        if (this.B.getWidth() > 1) {
            Resources resources = getContext().getResources();
            int paddingLeft = this.v.getPaddingLeft();
            Rect rect = new Rect();
            boolean a2 = m0.a(this);
            int dimensionPixelSize = this.T ? resources.getDimensionPixelSize(R$dimen.abc_dropdownitem_icon_width) + resources.getDimensionPixelSize(R$dimen.abc_dropdownitem_text_padding_left) : 0;
            this.t.getDropDownBackground().getPadding(rect);
            if (a2) {
                i2 = -rect.left;
            } else {
                i2 = paddingLeft - (rect.left + dimensionPixelSize);
            }
            this.t.setDropDownHorizontalOffset(i2);
            this.t.setDropDownWidth((((this.B.getWidth() + rect.left) + rect.right) + dimensionPixelSize) - paddingLeft);
        }
    }

    public boolean f() {
        return this.U;
    }

    /* access modifiers changed from: package-private */
    public void g() {
        if (!TextUtils.isEmpty(this.t.getText())) {
            this.t.setText(BuildConfig.FLAVOR);
            this.t.requestFocus();
            this.t.setImeVisibility(true);
        } else if (this.T) {
            l lVar = this.P;
            if (lVar == null || !lVar.a()) {
                clearFocus();
                b(true);
            }
        }
    }

    public int getImeOptions() {
        return this.t.getImeOptions();
    }

    public int getInputType() {
        return this.t.getInputType();
    }

    public int getMaxWidth() {
        return this.d0;
    }

    public CharSequence getQuery() {
        return this.t.getText();
    }

    public CharSequence getQueryHint() {
        CharSequence charSequence = this.a0;
        if (charSequence != null) {
            return charSequence;
        }
        SearchableInfo searchableInfo = this.j0;
        if (searchableInfo == null || searchableInfo.getHintId() == 0) {
            return this.N;
        }
        return getContext().getText(this.j0.getHintId());
    }

    /* access modifiers changed from: package-private */
    public int getSuggestionCommitIconResId() {
        return this.K;
    }

    /* access modifiers changed from: package-private */
    public int getSuggestionRowLayout() {
        return this.J;
    }

    public g.b.a.a getSuggestionsAdapter() {
        return this.V;
    }

    /* access modifiers changed from: package-private */
    public void h() {
        b(false);
        this.t.requestFocus();
        this.t.setImeVisibility(true);
        View.OnClickListener onClickListener = this.S;
        if (onClickListener != null) {
            onClickListener.onClick(this);
        }
    }

    /* access modifiers changed from: package-private */
    public void i() {
        Editable text = this.t.getText();
        if (text != null && TextUtils.getTrimmedLength(text) > 0) {
            m mVar = this.O;
            if (mVar == null || !mVar.b(text.toString())) {
                if (this.j0 != null) {
                    a(0, (String) null, text.toString());
                }
                this.t.setImeVisibility(false);
                m();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void j() {
        b(f());
        p();
        if (this.t.hasFocus()) {
            e();
        }
    }

    /* access modifiers changed from: package-private */
    public void k() {
        SearchableInfo searchableInfo = this.j0;
        if (searchableInfo != null) {
            try {
                if (searchableInfo.getVoiceSearchLaunchWebSearch()) {
                    getContext().startActivity(b(this.L, searchableInfo));
                } else if (searchableInfo.getVoiceSearchLaunchRecognizer()) {
                    getContext().startActivity(a(this.M, searchableInfo));
                }
            } catch (ActivityNotFoundException unused) {
                Log.w("SearchView", "Could not find voice search activity");
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void l() {
        int[] iArr = this.t.hasFocus() ? ViewGroup.FOCUSED_STATE_SET : ViewGroup.EMPTY_STATE_SET;
        Drawable background = this.v.getBackground();
        if (background != null) {
            background.setState(iArr);
        }
        Drawable background2 = this.w.getBackground();
        if (background2 != null) {
            background2.setState(iArr);
        }
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        removeCallbacks(this.l0);
        post(this.m0);
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        if (z2) {
            a((View) this.t, this.D);
            Rect rect = this.E;
            Rect rect2 = this.D;
            rect.set(rect2.left, 0, rect2.right, i5 - i3);
            o oVar = this.C;
            if (oVar == null) {
                o oVar2 = new o(this.E, this.D, this.t);
                this.C = oVar2;
                setTouchDelegate(oVar2);
                return;
            }
            oVar.a(this.E, this.D);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int i4;
        if (f()) {
            super.onMeasure(i2, i3);
            return;
        }
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (mode == Integer.MIN_VALUE) {
            int i5 = this.d0;
            size = i5 > 0 ? Math.min(i5, size) : Math.min(getPreferredWidth(), size);
        } else if (mode == 0) {
            size = this.d0;
            if (size <= 0) {
                size = getPreferredWidth();
            }
        } else if (mode == 1073741824 && (i4 = this.d0) > 0) {
            size = Math.min(i4, size);
        }
        int mode2 = View.MeasureSpec.getMode(i3);
        int size2 = View.MeasureSpec.getSize(i3);
        if (mode2 == Integer.MIN_VALUE) {
            size2 = Math.min(getPreferredHeight(), size2);
        } else if (mode2 == 0) {
            size2 = getPreferredHeight();
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(size2, 1073741824));
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.a());
        b(savedState.f249g);
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.f249g = f();
        return savedState;
    }

    public void onWindowFocusChanged(boolean z2) {
        super.onWindowFocusChanged(z2);
        p();
    }

    public boolean requestFocus(int i2, Rect rect) {
        if (this.c0 || !isFocusable()) {
            return false;
        }
        if (f()) {
            return super.requestFocus(i2, rect);
        }
        boolean requestFocus = this.t.requestFocus(i2, rect);
        if (requestFocus) {
            b(false);
        }
        return requestFocus;
    }

    public void setAppSearchData(Bundle bundle) {
        this.k0 = bundle;
    }

    public void setIconified(boolean z2) {
        if (z2) {
            g();
        } else {
            h();
        }
    }

    public void setIconifiedByDefault(boolean z2) {
        if (this.T != z2) {
            this.T = z2;
            b(z2);
            r();
        }
    }

    public void setImeOptions(int i2) {
        this.t.setImeOptions(i2);
    }

    public void setInputType(int i2) {
        this.t.setInputType(i2);
    }

    public void setMaxWidth(int i2) {
        this.d0 = i2;
        requestLayout();
    }

    public void setOnCloseListener(l lVar) {
        this.P = lVar;
    }

    public void setOnQueryTextFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.Q = onFocusChangeListener;
    }

    public void setOnQueryTextListener(m mVar) {
        this.O = mVar;
    }

    public void setOnSearchClickListener(View.OnClickListener onClickListener) {
        this.S = onClickListener;
    }

    public void setOnSuggestionListener(n nVar) {
        this.R = nVar;
    }

    public void setQueryHint(CharSequence charSequence) {
        this.a0 = charSequence;
        r();
    }

    public void setQueryRefinementEnabled(boolean z2) {
        this.b0 = z2;
        g.b.a.a aVar = this.V;
        if (aVar instanceof a0) {
            ((a0) aVar).a(z2 ? 2 : 1);
        }
    }

    public void setSearchableInfo(SearchableInfo searchableInfo) {
        this.j0 = searchableInfo;
        if (searchableInfo != null) {
            s();
            r();
        }
        boolean n2 = n();
        this.e0 = n2;
        if (n2) {
            this.t.setPrivateImeOptions("nm");
        }
        b(f());
    }

    public void setSubmitButtonEnabled(boolean z2) {
        this.W = z2;
        b(f());
    }

    public void setSuggestionsAdapter(g.b.a.a aVar) {
        this.V = aVar;
        this.t.setAdapter(aVar);
    }

    private static class k {
        private Method a;
        private Method b;
        private Method c;

        k() {
            try {
                Method declaredMethod = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", new Class[0]);
                this.a = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (NoSuchMethodException unused) {
            }
            try {
                Method declaredMethod2 = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", new Class[0]);
                this.b = declaredMethod2;
                declaredMethod2.setAccessible(true);
            } catch (NoSuchMethodException unused2) {
            }
            Class<AutoCompleteTextView> cls = AutoCompleteTextView.class;
            try {
                Method method = cls.getMethod("ensureImeVisible", new Class[]{Boolean.TYPE});
                this.c = method;
                method.setAccessible(true);
            } catch (NoSuchMethodException unused3) {
            }
        }

        /* access modifiers changed from: package-private */
        public void a(AutoCompleteTextView autoCompleteTextView) {
            Method method = this.b;
            if (method != null) {
                try {
                    method.invoke(autoCompleteTextView, new Object[0]);
                } catch (Exception unused) {
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void b(AutoCompleteTextView autoCompleteTextView) {
            Method method = this.a;
            if (method != null) {
                try {
                    method.invoke(autoCompleteTextView, new Object[0]);
                } catch (Exception unused) {
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a(AutoCompleteTextView autoCompleteTextView, boolean z) {
            Method method = this.c;
            if (method != null) {
                try {
                    method.invoke(autoCompleteTextView, new Object[]{Boolean.valueOf(z)});
                } catch (Exception unused) {
                }
            }
        }
    }

    public SearchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.searchViewStyle);
    }

    public SearchView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.D = new Rect();
        this.E = new Rect();
        this.F = new int[2];
        this.G = new int[2];
        this.l0 = new b();
        this.m0 = new c();
        this.n0 = new WeakHashMap<>();
        this.o0 = new f();
        this.p0 = new g();
        this.q0 = new h();
        this.r0 = new i();
        this.s0 = new j();
        this.t0 = new a();
        g0 a2 = g0.a(context, attributeSet, R$styleable.SearchView, i2, 0);
        LayoutInflater.from(context).inflate(a2.g(R$styleable.SearchView_layout, R$layout.abc_search_view), this, true);
        SearchAutoComplete searchAutoComplete = (SearchAutoComplete) findViewById(R$id.search_src_text);
        this.t = searchAutoComplete;
        searchAutoComplete.setSearchView(this);
        this.u = findViewById(R$id.search_edit_frame);
        this.v = findViewById(R$id.search_plate);
        this.w = findViewById(R$id.submit_area);
        this.x = (ImageView) findViewById(R$id.search_button);
        this.y = (ImageView) findViewById(R$id.search_go_btn);
        this.z = (ImageView) findViewById(R$id.search_close_btn);
        this.A = (ImageView) findViewById(R$id.search_voice_btn);
        this.H = (ImageView) findViewById(R$id.search_mag_icon);
        v.a(this.v, a2.b(R$styleable.SearchView_queryBackground));
        v.a(this.w, a2.b(R$styleable.SearchView_submitBackground));
        this.x.setImageDrawable(a2.b(R$styleable.SearchView_searchIcon));
        this.y.setImageDrawable(a2.b(R$styleable.SearchView_goIcon));
        this.z.setImageDrawable(a2.b(R$styleable.SearchView_closeIcon));
        this.A.setImageDrawable(a2.b(R$styleable.SearchView_voiceIcon));
        this.H.setImageDrawable(a2.b(R$styleable.SearchView_searchIcon));
        this.I = a2.b(R$styleable.SearchView_searchHintIcon);
        i0.a(this.x, getResources().getString(R$string.abc_searchview_description_search));
        this.J = a2.g(R$styleable.SearchView_suggestionRowLayout, R$layout.abc_search_dropdown_item_icons_2line);
        this.K = a2.g(R$styleable.SearchView_commitIcon, 0);
        this.x.setOnClickListener(this.o0);
        this.z.setOnClickListener(this.o0);
        this.y.setOnClickListener(this.o0);
        this.A.setOnClickListener(this.o0);
        this.t.setOnClickListener(this.o0);
        this.t.addTextChangedListener(this.t0);
        this.t.setOnEditorActionListener(this.q0);
        this.t.setOnItemClickListener(this.r0);
        this.t.setOnItemSelectedListener(this.s0);
        this.t.setOnKeyListener(this.p0);
        this.t.setOnFocusChangeListener(new d());
        setIconifiedByDefault(a2.a(R$styleable.SearchView_iconifiedByDefault, true));
        int c2 = a2.c(R$styleable.SearchView_android_maxWidth, -1);
        if (c2 != -1) {
            setMaxWidth(c2);
        }
        this.N = a2.e(R$styleable.SearchView_defaultQueryHint);
        this.a0 = a2.e(R$styleable.SearchView_queryHint);
        int d2 = a2.d(R$styleable.SearchView_android_imeOptions, -1);
        if (d2 != -1) {
            setImeOptions(d2);
        }
        int d3 = a2.d(R$styleable.SearchView_android_inputType, -1);
        if (d3 != -1) {
            setInputType(d3);
        }
        setFocusable(a2.a(R$styleable.SearchView_android_focusable, true));
        a2.a();
        Intent intent = new Intent("android.speech.action.WEB_SEARCH");
        this.L = intent;
        intent.addFlags(268435456);
        this.L.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
        Intent intent2 = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        this.M = intent2;
        intent2.addFlags(268435456);
        View findViewById = findViewById(this.t.getDropDownAnchor());
        this.B = findViewById;
        if (findViewById != null) {
            findViewById.addOnLayoutChangeListener(new e());
        }
        b(this.T);
        r();
    }

    private void a(View view, Rect rect) {
        view.getLocationInWindow(this.F);
        getLocationInWindow(this.G);
        int[] iArr = this.F;
        int i2 = iArr[1];
        int[] iArr2 = this.G;
        int i3 = i2 - iArr2[1];
        int i4 = iArr[0] - iArr2[0];
        rect.set(i4, i3, view.getWidth() + i4, view.getHeight() + i3);
    }

    private void c(boolean z2) {
        int i2 = 8;
        if (this.e0 && !f() && z2) {
            this.y.setVisibility(8);
            i2 = 0;
        }
        this.A.setVisibility(i2);
    }

    /* access modifiers changed from: package-private */
    public void e() {
        if (Build.VERSION.SDK_INT >= 29) {
            this.t.refreshAutoCompleteResults();
            return;
        }
        u0.b(this.t);
        u0.a(this.t);
    }

    private void a(boolean z2) {
        this.y.setVisibility((!this.W || !o() || !hasFocus() || (!z2 && this.e0)) ? 8 : 0);
    }

    /* access modifiers changed from: package-private */
    public void b(CharSequence charSequence) {
        Editable text = this.t.getText();
        this.g0 = text;
        boolean z2 = !TextUtils.isEmpty(text);
        a(z2);
        c(!z2);
        q();
        t();
        if (this.O != null && !TextUtils.equals(charSequence, this.f0)) {
            this.O.a(charSequence.toString());
        }
        this.f0 = charSequence.toString();
    }

    /* access modifiers changed from: package-private */
    public void a(CharSequence charSequence) {
        setQuery(charSequence);
    }

    /* access modifiers changed from: package-private */
    public boolean a(View view, int i2, KeyEvent keyEvent) {
        int i3;
        if (this.j0 != null && this.V != null && keyEvent.getAction() == 0 && keyEvent.hasNoModifiers()) {
            if (i2 == 66 || i2 == 84 || i2 == 61) {
                return a(this.t.getListSelection(), 0, (String) null);
            }
            if (i2 == 21 || i2 == 22) {
                if (i2 == 21) {
                    i3 = 0;
                } else {
                    i3 = this.t.length();
                }
                this.t.setSelection(i3);
                this.t.setListSelection(0);
                this.t.clearListSelection();
                u0.a(this.t, true);
                return true;
            } else if (i2 != 19 || this.t.getListSelection() == 0) {
                return false;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean d(int i2) {
        n nVar = this.R;
        if (nVar != null && nVar.a(i2)) {
            return false;
        }
        e(i2);
        return true;
    }

    public void b() {
        a((CharSequence) BuildConfig.FLAVOR, false);
        clearFocus();
        b(true);
        this.t.setImeOptions(this.i0);
        this.h0 = false;
    }

    private boolean b(int i2, int i3, String str) {
        Cursor a2 = this.V.a();
        if (a2 == null || !a2.moveToPosition(i2)) {
            return false;
        }
        a(a(a2, i3, str));
        return true;
    }

    public void a() {
        if (!this.h0) {
            this.h0 = true;
            int imeOptions = this.t.getImeOptions();
            this.i0 = imeOptions;
            this.t.setImeOptions(imeOptions | 33554432);
            this.t.setText(BuildConfig.FLAVOR);
            setIconified(false);
        }
    }

    private Intent b(Intent intent, SearchableInfo searchableInfo) {
        String str;
        Intent intent2 = new Intent(intent);
        ComponentName searchActivity = searchableInfo.getSearchActivity();
        if (searchActivity == null) {
            str = null;
        } else {
            str = searchActivity.flattenToShortString();
        }
        intent2.putExtra("calling_package", str);
        return intent2;
    }

    /* access modifiers changed from: package-private */
    public boolean a(int i2, int i3, String str) {
        n nVar = this.R;
        if (nVar != null && nVar.b(i2)) {
            return false;
        }
        b(i2, 0, (String) null);
        this.t.setImeVisibility(false);
        m();
        return true;
    }

    private void a(Intent intent) {
        if (intent != null) {
            try {
                getContext().startActivity(intent);
            } catch (RuntimeException e2) {
                Log.e("SearchView", "Failed launch activity: " + intent, e2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, String str, String str2) {
        getContext().startActivity(a("android.intent.action.SEARCH", (Uri) null, (String) null, str2, i2, str));
    }

    private Intent a(String str, Uri uri, String str2, String str3, int i2, String str4) {
        Intent intent = new Intent(str);
        intent.addFlags(268435456);
        if (uri != null) {
            intent.setData(uri);
        }
        intent.putExtra("user_query", this.g0);
        if (str3 != null) {
            intent.putExtra("query", str3);
        }
        if (str2 != null) {
            intent.putExtra("intent_extra_data_key", str2);
        }
        Bundle bundle = this.k0;
        if (bundle != null) {
            intent.putExtra("app_data", bundle);
        }
        if (i2 != 0) {
            intent.putExtra("action_key", i2);
            intent.putExtra("action_msg", str4);
        }
        intent.setComponent(this.j0.getSearchActivity());
        return intent;
    }

    private Intent a(Intent intent, SearchableInfo searchableInfo) {
        ComponentName searchActivity = searchableInfo.getSearchActivity();
        Intent intent2 = new Intent("android.intent.action.SEARCH");
        intent2.setComponent(searchActivity);
        PendingIntent activity = PendingIntent.getActivity(getContext(), 0, intent2, 1073741824);
        Bundle bundle = new Bundle();
        Bundle bundle2 = this.k0;
        if (bundle2 != null) {
            bundle.putParcelable("app_data", bundle2);
        }
        Intent intent3 = new Intent(intent);
        int i2 = 1;
        Resources resources = getResources();
        String string = searchableInfo.getVoiceLanguageModeId() != 0 ? resources.getString(searchableInfo.getVoiceLanguageModeId()) : "free_form";
        String str = null;
        String string2 = searchableInfo.getVoicePromptTextId() != 0 ? resources.getString(searchableInfo.getVoicePromptTextId()) : null;
        String string3 = searchableInfo.getVoiceLanguageId() != 0 ? resources.getString(searchableInfo.getVoiceLanguageId()) : null;
        if (searchableInfo.getVoiceMaxResults() != 0) {
            i2 = searchableInfo.getVoiceMaxResults();
        }
        intent3.putExtra("android.speech.extra.LANGUAGE_MODEL", string);
        intent3.putExtra("android.speech.extra.PROMPT", string2);
        intent3.putExtra("android.speech.extra.LANGUAGE", string3);
        intent3.putExtra("android.speech.extra.MAX_RESULTS", i2);
        if (searchActivity != null) {
            str = searchActivity.flattenToShortString();
        }
        intent3.putExtra("calling_package", str);
        intent3.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", activity);
        intent3.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", bundle);
        return intent3;
    }

    private Intent a(Cursor cursor, int i2, String str) {
        int i3;
        Uri uri;
        String a2;
        try {
            String a3 = a0.a(cursor, "suggest_intent_action");
            if (a3 == null) {
                a3 = this.j0.getSuggestIntentAction();
            }
            if (a3 == null) {
                a3 = "android.intent.action.SEARCH";
            }
            String str2 = a3;
            String a4 = a0.a(cursor, "suggest_intent_data");
            if (a4 == null) {
                a4 = this.j0.getSuggestIntentData();
            }
            if (!(a4 == null || (a2 = a0.a(cursor, "suggest_intent_data_id")) == null)) {
                a4 = a4 + "/" + Uri.encode(a2);
            }
            if (a4 == null) {
                uri = null;
            } else {
                uri = Uri.parse(a4);
            }
            return a(str2, uri, a0.a(cursor, "suggest_intent_extra_data"), a0.a(cursor, "suggest_intent_query"), i2, str);
        } catch (RuntimeException e2) {
            try {
                i3 = cursor.getPosition();
            } catch (RuntimeException unused) {
                i3 = -1;
            }
            Log.w("SearchView", "Search suggestions cursor at row " + i3 + " returned exception.", e2);
            return null;
        }
    }

    static boolean a(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }
}
