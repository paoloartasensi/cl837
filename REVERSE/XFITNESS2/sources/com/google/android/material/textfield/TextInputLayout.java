package com.google.android.material.textfield;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.f;
import androidx.appcompat.widget.g0;
import androidx.appcompat.widget.q;
import androidx.core.h.v;
import androidx.core.widget.i;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R$attr;
import com.google.android.material.R$color;
import com.google.android.material.R$dimen;
import com.google.android.material.R$id;
import com.google.android.material.R$layout;
import com.google.android.material.R$string;
import com.google.android.material.R$style;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.e;
import com.google.android.material.internal.k;
import com.google.android.material.internal.l;

public class TextInputLayout extends LinearLayout {
    private int A;
    private final int B;
    private final int C;
    private int D;
    private int E;
    private Drawable F;
    private final Rect G;
    private final RectF H;
    private Typeface I;
    private boolean J;
    private Drawable K;
    private CharSequence L;
    private CheckableImageButton M;
    private boolean N;
    private Drawable O;
    private Drawable P;
    private ColorStateList Q;
    private boolean R;
    private PorterDuff.Mode S;
    private boolean T;
    private ColorStateList U;
    private ColorStateList V;
    private final int W;
    private final int a0;
    private int b0;
    private final int c0;
    private boolean d0;
    private final FrameLayout e;
    final com.google.android.material.internal.c e0;

    /* renamed from: f  reason: collision with root package name */
    EditText f1555f;
    private boolean f0;

    /* renamed from: g  reason: collision with root package name */
    private CharSequence f1556g;
    private ValueAnimator g0;

    /* renamed from: h  reason: collision with root package name */
    private final b f1557h;
    private boolean h0;

    /* renamed from: i  reason: collision with root package name */
    boolean f1558i;
    private boolean i0;

    /* renamed from: j  reason: collision with root package name */
    private int f1559j;
    /* access modifiers changed from: private */
    public boolean j0;
    private boolean k;
    private TextView l;
    private final int m;
    private final int n;
    private boolean o;
    private CharSequence p;
    private boolean q;
    private GradientDrawable r;
    private final int s;
    private final int t;
    private int u;
    private final int v;
    private float w;
    private float x;
    private float y;
    private float z;

    static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* renamed from: g  reason: collision with root package name */
        CharSequence f1560g;

        /* renamed from: h  reason: collision with root package name */
        boolean f1561h;

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
            return "TextInputLayout.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " error=" + this.f1560g + "}";
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            TextUtils.writeToParcel(this.f1560g, parcel, i2);
            parcel.writeInt(this.f1561h ? 1 : 0);
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f1560g = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.f1561h = parcel.readInt() != 1 ? false : true;
        }
    }

    class a implements TextWatcher {
        a() {
        }

        public void afterTextChanged(Editable editable) {
            TextInputLayout textInputLayout = TextInputLayout.this;
            textInputLayout.b(!textInputLayout.j0);
            TextInputLayout textInputLayout2 = TextInputLayout.this;
            if (textInputLayout2.f1558i) {
                textInputLayout2.a(editable.length());
            }
        }

        public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        }

        public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        }
    }

    class b implements View.OnClickListener {
        b() {
        }

        public void onClick(View view) {
            TextInputLayout.this.a(false);
        }
    }

    class c implements ValueAnimator.AnimatorUpdateListener {
        c() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            TextInputLayout.this.e0.b(((Float) valueAnimator.getAnimatedValue()).floatValue());
        }
    }

    public static class d extends androidx.core.h.a {
        private final TextInputLayout d;

        public d(TextInputLayout textInputLayout) {
            this.d = textInputLayout;
        }

        public void a(View view, androidx.core.h.e0.d dVar) {
            super.a(view, dVar);
            EditText editText = this.d.getEditText();
            Editable text = editText != null ? editText.getText() : null;
            CharSequence hint = this.d.getHint();
            CharSequence error = this.d.getError();
            CharSequence counterOverflowDescription = this.d.getCounterOverflowDescription();
            boolean z = !TextUtils.isEmpty(text);
            boolean z2 = !TextUtils.isEmpty(hint);
            boolean z3 = !TextUtils.isEmpty(error);
            boolean z4 = false;
            boolean z5 = z3 || !TextUtils.isEmpty(counterOverflowDescription);
            if (z) {
                dVar.g((CharSequence) text);
            } else if (z2) {
                dVar.g(hint);
            }
            if (z2) {
                dVar.d(hint);
                if (!z && z2) {
                    z4 = true;
                }
                dVar.p(z4);
            }
            if (z5) {
                if (!z3) {
                    error = counterOverflowDescription;
                }
                dVar.c(error);
                dVar.f(true);
            }
        }

        public void c(View view, AccessibilityEvent accessibilityEvent) {
            super.c(view, accessibilityEvent);
            EditText editText = this.d.getEditText();
            CharSequence text = editText != null ? editText.getText() : null;
            if (TextUtils.isEmpty(text)) {
                text = this.d.getHint();
            }
            if (!TextUtils.isEmpty(text)) {
                accessibilityEvent.getText().add(text);
            }
        }
    }

    public TextInputLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    private void e() {
        int i2;
        Drawable drawable;
        if (this.r != null) {
            q();
            EditText editText = this.f1555f;
            if (editText != null && this.u == 2) {
                if (editText.getBackground() != null) {
                    this.F = this.f1555f.getBackground();
                }
                v.a((View) this.f1555f, (Drawable) null);
            }
            EditText editText2 = this.f1555f;
            if (!(editText2 == null || this.u != 1 || (drawable = this.F) == null)) {
                v.a((View) editText2, drawable);
            }
            int i3 = this.A;
            if (i3 > -1 && (i2 = this.D) != 0) {
                this.r.setStroke(i3, i2);
            }
            this.r.setCornerRadii(getCornerRadiiAsArray());
            this.r.setColor(this.E);
            invalidate();
        }
    }

    private void f() {
        Drawable drawable;
        if (this.K == null) {
            return;
        }
        if (this.R || this.T) {
            Drawable mutate = androidx.core.graphics.drawable.a.i(this.K).mutate();
            this.K = mutate;
            if (this.R) {
                androidx.core.graphics.drawable.a.a(mutate, this.Q);
            }
            if (this.T) {
                androidx.core.graphics.drawable.a.a(this.K, this.S);
            }
            CheckableImageButton checkableImageButton = this.M;
            if (checkableImageButton != null && checkableImageButton.getDrawable() != (drawable = this.K)) {
                this.M.setImageDrawable(drawable);
            }
        }
    }

    private void g() {
        int i2 = this.u;
        if (i2 == 0) {
            this.r = null;
        } else if (i2 == 2 && this.o && !(this.r instanceof a)) {
            this.r = new a();
        } else if (!(this.r instanceof GradientDrawable)) {
            this.r = new GradientDrawable();
        }
    }

    private Drawable getBoxBackground() {
        int i2 = this.u;
        if (i2 == 1 || i2 == 2) {
            return this.r;
        }
        throw new IllegalStateException();
    }

    private float[] getCornerRadiiAsArray() {
        if (!l.a(this)) {
            float f2 = this.w;
            float f3 = this.x;
            float f4 = this.y;
            float f5 = this.z;
            return new float[]{f2, f2, f3, f3, f4, f4, f5, f5};
        }
        float f6 = this.x;
        float f7 = this.w;
        float f8 = this.z;
        float f9 = this.y;
        return new float[]{f6, f6, f7, f7, f8, f8, f9, f9};
    }

    private int h() {
        EditText editText = this.f1555f;
        if (editText == null) {
            return 0;
        }
        int i2 = this.u;
        if (i2 == 1) {
            return editText.getTop();
        }
        if (i2 != 2) {
            return 0;
        }
        return editText.getTop() + j();
    }

    private int i() {
        int i2 = this.u;
        if (i2 == 1) {
            return getBoxBackground().getBounds().top + this.v;
        }
        if (i2 != 2) {
            return getPaddingTop();
        }
        return getBoxBackground().getBounds().top - j();
    }

    private int j() {
        float d2;
        if (!this.o) {
            return 0;
        }
        int i2 = this.u;
        if (i2 == 0 || i2 == 1) {
            d2 = this.e0.d();
        } else if (i2 != 2) {
            return 0;
        } else {
            d2 = this.e0.d() / 2.0f;
        }
        return (int) d2;
    }

    private void k() {
        if (l()) {
            ((a) this.r).b();
        }
    }

    private boolean l() {
        return this.o && !TextUtils.isEmpty(this.p) && (this.r instanceof a);
    }

    private void m() {
        Drawable background;
        int i2 = Build.VERSION.SDK_INT;
        if ((i2 == 21 || i2 == 22) && (background = this.f1555f.getBackground()) != null && !this.h0) {
            Drawable newDrawable = background.getConstantState().newDrawable();
            if (background instanceof DrawableContainer) {
                this.h0 = e.a((DrawableContainer) background, newDrawable.getConstantState());
            }
            if (!this.h0) {
                v.a((View) this.f1555f, newDrawable);
                this.h0 = true;
                o();
            }
        }
    }

    private boolean n() {
        EditText editText = this.f1555f;
        return editText != null && (editText.getTransformationMethod() instanceof PasswordTransformationMethod);
    }

    private void o() {
        g();
        if (this.u != 0) {
            t();
        }
        v();
    }

    private void p() {
        if (l()) {
            RectF rectF = this.H;
            this.e0.a(rectF);
            a(rectF);
            ((a) this.r).a(rectF);
        }
    }

    private void q() {
        int i2 = this.u;
        if (i2 == 1) {
            this.A = 0;
        } else if (i2 == 2 && this.b0 == 0) {
            this.b0 = this.V.getColorForState(getDrawableState(), this.V.getDefaultColor());
        }
    }

    private boolean r() {
        return this.J && (n() || this.N);
    }

    private void s() {
        Drawable background;
        EditText editText = this.f1555f;
        if (editText != null && (background = editText.getBackground()) != null) {
            if (q.a(background)) {
                background = background.mutate();
            }
            com.google.android.material.internal.d.a((ViewGroup) this, (View) this.f1555f, new Rect());
            Rect bounds = background.getBounds();
            if (bounds.left != bounds.right) {
                Rect rect = new Rect();
                background.getPadding(rect);
                background.setBounds(bounds.left - rect.left, bounds.top, bounds.right + (rect.right * 2), this.f1555f.getBottom());
            }
        }
    }

    private void setEditText(EditText editText) {
        if (this.f1555f == null) {
            if (!(editText instanceof TextInputEditText)) {
                Log.i("TextInputLayout", "EditText added is not a TextInputEditText. Please switch to using that class instead.");
            }
            this.f1555f = editText;
            o();
            setTextInputAccessibilityDelegate(new d(this));
            if (!n()) {
                this.e0.c(this.f1555f.getTypeface());
            }
            this.e0.a(this.f1555f.getTextSize());
            int gravity = this.f1555f.getGravity();
            this.e0.b((gravity & -113) | 48);
            this.e0.d(gravity);
            this.f1555f.addTextChangedListener(new a());
            if (this.U == null) {
                this.U = this.f1555f.getHintTextColors();
            }
            if (this.o) {
                if (TextUtils.isEmpty(this.p)) {
                    CharSequence hint = this.f1555f.getHint();
                    this.f1556g = hint;
                    setHint(hint);
                    this.f1555f.setHint((CharSequence) null);
                }
                this.q = true;
            }
            if (this.l != null) {
                a(this.f1555f.getText().length());
            }
            this.f1557h.a();
            u();
            a(false, true);
            return;
        }
        throw new IllegalArgumentException("We already have an EditText, can only have one");
    }

    private void setHintInternal(CharSequence charSequence) {
        if (!TextUtils.equals(charSequence, this.p)) {
            this.p = charSequence;
            this.e0.a(charSequence);
            if (!this.d0) {
                p();
            }
        }
    }

    private void t() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.e.getLayoutParams();
        int j2 = j();
        if (j2 != layoutParams.topMargin) {
            layoutParams.topMargin = j2;
            this.e.requestLayout();
        }
    }

    private void u() {
        if (this.f1555f != null) {
            if (r()) {
                if (this.M == null) {
                    CheckableImageButton checkableImageButton = (CheckableImageButton) LayoutInflater.from(getContext()).inflate(R$layout.design_text_input_password_icon, this.e, false);
                    this.M = checkableImageButton;
                    checkableImageButton.setImageDrawable(this.K);
                    this.M.setContentDescription(this.L);
                    this.e.addView(this.M);
                    this.M.setOnClickListener(new b());
                }
                EditText editText = this.f1555f;
                if (editText != null && v.p(editText) <= 0) {
                    this.f1555f.setMinimumHeight(v.p(this.M));
                }
                this.M.setVisibility(0);
                this.M.setChecked(this.N);
                if (this.O == null) {
                    this.O = new ColorDrawable();
                }
                this.O.setBounds(0, 0, this.M.getMeasuredWidth(), 1);
                Drawable[] a2 = i.a((TextView) this.f1555f);
                if (a2[2] != this.O) {
                    this.P = a2[2];
                }
                i.a(this.f1555f, a2[0], a2[1], this.O, a2[3]);
                this.M.setPadding(this.f1555f.getPaddingLeft(), this.f1555f.getPaddingTop(), this.f1555f.getPaddingRight(), this.f1555f.getPaddingBottom());
                return;
            }
            CheckableImageButton checkableImageButton2 = this.M;
            if (checkableImageButton2 != null && checkableImageButton2.getVisibility() == 0) {
                this.M.setVisibility(8);
            }
            if (this.O != null) {
                Drawable[] a3 = i.a((TextView) this.f1555f);
                if (a3[2] == this.O) {
                    i.a(this.f1555f, a3[0], a3[1], this.P, a3[3]);
                    this.O = null;
                }
            }
        }
    }

    private void v() {
        if (this.u != 0 && this.r != null && this.f1555f != null && getRight() != 0) {
            int left = this.f1555f.getLeft();
            int h2 = h();
            int right = this.f1555f.getRight();
            int bottom = this.f1555f.getBottom() + this.s;
            if (this.u == 2) {
                int i2 = this.C;
                left += i2 / 2;
                h2 -= i2 / 2;
                right -= i2 / 2;
                bottom += i2 / 2;
            }
            this.r.setBounds(left, h2, right, bottom);
            e();
            s();
        }
    }

    public void addView(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        if (view instanceof EditText) {
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(layoutParams);
            layoutParams2.gravity = (layoutParams2.gravity & -113) | 16;
            this.e.addView(view, layoutParams2);
            this.e.setLayoutParams(layoutParams);
            t();
            setEditText((EditText) view);
            return;
        }
        super.addView(view, i2, layoutParams);
    }

    /* access modifiers changed from: package-private */
    public void b(boolean z2) {
        a(z2, false);
    }

    /* access modifiers changed from: package-private */
    public void c() {
        Drawable background;
        TextView textView;
        EditText editText = this.f1555f;
        if (editText != null && (background = editText.getBackground()) != null) {
            m();
            if (q.a(background)) {
                background = background.mutate();
            }
            if (this.f1557h.c()) {
                background.setColorFilter(f.a(this.f1557h.e(), PorterDuff.Mode.SRC_IN));
            } else if (!this.k || (textView = this.l) == null) {
                androidx.core.graphics.drawable.a.b(background);
                this.f1555f.refreshDrawableState();
            } else {
                background.setColorFilter(f.a(textView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void d() {
        TextView textView;
        if (this.r != null && this.u != 0) {
            EditText editText = this.f1555f;
            boolean z2 = true;
            boolean z3 = editText != null && editText.hasFocus();
            EditText editText2 = this.f1555f;
            if (editText2 == null || !editText2.isHovered()) {
                z2 = false;
            }
            if (this.u == 2) {
                if (!isEnabled()) {
                    this.D = this.c0;
                } else if (this.f1557h.c()) {
                    this.D = this.f1557h.e();
                } else if (this.k && (textView = this.l) != null) {
                    this.D = textView.getCurrentTextColor();
                } else if (z3) {
                    this.D = this.b0;
                } else if (z2) {
                    this.D = this.a0;
                } else {
                    this.D = this.W;
                }
                if ((z2 || z3) && isEnabled()) {
                    this.A = this.C;
                } else {
                    this.A = this.B;
                }
                e();
            }
        }
    }

    public void dispatchProvideAutofillStructure(ViewStructure viewStructure, int i2) {
        EditText editText;
        if (this.f1556g == null || (editText = this.f1555f) == null) {
            super.dispatchProvideAutofillStructure(viewStructure, i2);
            return;
        }
        boolean z2 = this.q;
        this.q = false;
        CharSequence hint = editText.getHint();
        this.f1555f.setHint(this.f1556g);
        try {
            super.dispatchProvideAutofillStructure(viewStructure, i2);
        } finally {
            this.f1555f.setHint(hint);
            this.q = z2;
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        this.j0 = true;
        super.dispatchRestoreInstanceState(sparseArray);
        this.j0 = false;
    }

    public void draw(Canvas canvas) {
        GradientDrawable gradientDrawable = this.r;
        if (gradientDrawable != null) {
            gradientDrawable.draw(canvas);
        }
        super.draw(canvas);
        if (this.o) {
            this.e0.a(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        if (!this.i0) {
            boolean z2 = true;
            this.i0 = true;
            super.drawableStateChanged();
            int[] drawableState = getDrawableState();
            if (!v.D(this) || !isEnabled()) {
                z2 = false;
            }
            b(z2);
            c();
            v();
            d();
            com.google.android.material.internal.c cVar = this.e0;
            if (cVar != null ? cVar.a(drawableState) | false : false) {
                invalidate();
            }
            this.i0 = false;
        }
    }

    public int getBoxBackgroundColor() {
        return this.E;
    }

    public float getBoxCornerRadiusBottomEnd() {
        return this.y;
    }

    public float getBoxCornerRadiusBottomStart() {
        return this.z;
    }

    public float getBoxCornerRadiusTopEnd() {
        return this.x;
    }

    public float getBoxCornerRadiusTopStart() {
        return this.w;
    }

    public int getBoxStrokeColor() {
        return this.b0;
    }

    public int getCounterMaxLength() {
        return this.f1559j;
    }

    /* access modifiers changed from: package-private */
    public CharSequence getCounterOverflowDescription() {
        TextView textView;
        if (!this.f1558i || !this.k || (textView = this.l) == null) {
            return null;
        }
        return textView.getContentDescription();
    }

    public ColorStateList getDefaultHintTextColor() {
        return this.U;
    }

    public EditText getEditText() {
        return this.f1555f;
    }

    public CharSequence getError() {
        if (this.f1557h.k()) {
            return this.f1557h.d();
        }
        return null;
    }

    public int getErrorCurrentTextColors() {
        return this.f1557h.e();
    }

    /* access modifiers changed from: package-private */
    public final int getErrorTextCurrentColor() {
        return this.f1557h.e();
    }

    public CharSequence getHelperText() {
        if (this.f1557h.l()) {
            return this.f1557h.g();
        }
        return null;
    }

    public int getHelperTextCurrentTextColor() {
        return this.f1557h.h();
    }

    public CharSequence getHint() {
        if (this.o) {
            return this.p;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public final float getHintCollapsedTextHeight() {
        return this.e0.d();
    }

    /* access modifiers changed from: package-private */
    public final int getHintCurrentCollapsedTextColor() {
        return this.e0.f();
    }

    public CharSequence getPasswordVisibilityToggleContentDescription() {
        return this.L;
    }

    public Drawable getPasswordVisibilityToggleDrawable() {
        return this.K;
    }

    public Typeface getTypeface() {
        return this.I;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        EditText editText;
        super.onLayout(z2, i2, i3, i4, i5);
        if (this.r != null) {
            v();
        }
        if (this.o && (editText = this.f1555f) != null) {
            Rect rect = this.G;
            com.google.android.material.internal.d.a((ViewGroup) this, (View) editText, rect);
            int compoundPaddingLeft = rect.left + this.f1555f.getCompoundPaddingLeft();
            int compoundPaddingRight = rect.right - this.f1555f.getCompoundPaddingRight();
            int i6 = i();
            this.e0.b(compoundPaddingLeft, rect.top + this.f1555f.getCompoundPaddingTop(), compoundPaddingRight, rect.bottom - this.f1555f.getCompoundPaddingBottom());
            this.e0.a(compoundPaddingLeft, i6, compoundPaddingRight, (i5 - i3) - getPaddingBottom());
            this.e0.m();
            if (l() && !this.d0) {
                p();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        u();
        super.onMeasure(i2, i3);
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.a());
        setError(savedState.f1560g);
        if (savedState.f1561h) {
            a(true);
        }
        requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.f1557h.c()) {
            savedState.f1560g = getError();
        }
        savedState.f1561h = this.N;
        return savedState;
    }

    public void setBoxBackgroundColor(int i2) {
        if (this.E != i2) {
            this.E = i2;
            e();
        }
    }

    public void setBoxBackgroundColorResource(int i2) {
        setBoxBackgroundColor(androidx.core.content.a.a(getContext(), i2));
    }

    public void setBoxBackgroundMode(int i2) {
        if (i2 != this.u) {
            this.u = i2;
            o();
        }
    }

    public void setBoxStrokeColor(int i2) {
        if (this.b0 != i2) {
            this.b0 = i2;
            d();
        }
    }

    public void setCounterEnabled(boolean z2) {
        if (this.f1558i != z2) {
            if (z2) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
                this.l = appCompatTextView;
                appCompatTextView.setId(R$id.textinput_counter);
                Typeface typeface = this.I;
                if (typeface != null) {
                    this.l.setTypeface(typeface);
                }
                this.l.setMaxLines(1);
                a(this.l, this.n);
                this.f1557h.a(this.l, 2);
                EditText editText = this.f1555f;
                if (editText == null) {
                    a(0);
                } else {
                    a(editText.getText().length());
                }
            } else {
                this.f1557h.b(this.l, 2);
                this.l = null;
            }
            this.f1558i = z2;
        }
    }

    public void setCounterMaxLength(int i2) {
        if (this.f1559j != i2) {
            if (i2 > 0) {
                this.f1559j = i2;
            } else {
                this.f1559j = -1;
            }
            if (this.f1558i) {
                EditText editText = this.f1555f;
                a(editText == null ? 0 : editText.getText().length());
            }
        }
    }

    public void setDefaultHintTextColor(ColorStateList colorStateList) {
        this.U = colorStateList;
        this.V = colorStateList;
        if (this.f1555f != null) {
            b(false);
        }
    }

    public void setEnabled(boolean z2) {
        a((ViewGroup) this, z2);
        super.setEnabled(z2);
    }

    public void setError(CharSequence charSequence) {
        if (!this.f1557h.k()) {
            if (!TextUtils.isEmpty(charSequence)) {
                setErrorEnabled(true);
            } else {
                return;
            }
        }
        if (!TextUtils.isEmpty(charSequence)) {
            this.f1557h.a(charSequence);
        } else {
            this.f1557h.i();
        }
    }

    public void setErrorEnabled(boolean z2) {
        this.f1557h.a(z2);
    }

    public void setErrorTextAppearance(int i2) {
        this.f1557h.b(i2);
    }

    public void setErrorTextColor(ColorStateList colorStateList) {
        this.f1557h.a(colorStateList);
    }

    public void setHelperText(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (!a()) {
                setHelperTextEnabled(true);
            }
            this.f1557h.b(charSequence);
        } else if (a()) {
            setHelperTextEnabled(false);
        }
    }

    public void setHelperTextColor(ColorStateList colorStateList) {
        this.f1557h.b(colorStateList);
    }

    public void setHelperTextEnabled(boolean z2) {
        this.f1557h.b(z2);
    }

    public void setHelperTextTextAppearance(int i2) {
        this.f1557h.c(i2);
    }

    public void setHint(CharSequence charSequence) {
        if (this.o) {
            setHintInternal(charSequence);
            sendAccessibilityEvent(2048);
        }
    }

    public void setHintAnimationEnabled(boolean z2) {
        this.f0 = z2;
    }

    public void setHintEnabled(boolean z2) {
        if (z2 != this.o) {
            this.o = z2;
            if (!z2) {
                this.q = false;
                if (!TextUtils.isEmpty(this.p) && TextUtils.isEmpty(this.f1555f.getHint())) {
                    this.f1555f.setHint(this.p);
                }
                setHintInternal((CharSequence) null);
            } else {
                CharSequence hint = this.f1555f.getHint();
                if (!TextUtils.isEmpty(hint)) {
                    if (TextUtils.isEmpty(this.p)) {
                        setHint(hint);
                    }
                    this.f1555f.setHint((CharSequence) null);
                }
                this.q = true;
            }
            if (this.f1555f != null) {
                t();
            }
        }
    }

    public void setHintTextAppearance(int i2) {
        this.e0.a(i2);
        this.V = this.e0.b();
        if (this.f1555f != null) {
            b(false);
            t();
        }
    }

    public void setPasswordVisibilityToggleContentDescription(int i2) {
        setPasswordVisibilityToggleContentDescription(i2 != 0 ? getResources().getText(i2) : null);
    }

    public void setPasswordVisibilityToggleDrawable(int i2) {
        setPasswordVisibilityToggleDrawable(i2 != 0 ? androidx.appcompat.a.a.a.c(getContext(), i2) : null);
    }

    public void setPasswordVisibilityToggleEnabled(boolean z2) {
        EditText editText;
        if (this.J != z2) {
            this.J = z2;
            if (!z2 && this.N && (editText = this.f1555f) != null) {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            this.N = false;
            u();
        }
    }

    public void setPasswordVisibilityToggleTintList(ColorStateList colorStateList) {
        this.Q = colorStateList;
        this.R = true;
        f();
    }

    public void setPasswordVisibilityToggleTintMode(PorterDuff.Mode mode) {
        this.S = mode;
        this.T = true;
        f();
    }

    public void setTextInputAccessibilityDelegate(d dVar) {
        EditText editText = this.f1555f;
        if (editText != null) {
            v.a((View) editText, (androidx.core.h.a) dVar);
        }
    }

    public void setTypeface(Typeface typeface) {
        if (typeface != this.I) {
            this.I = typeface;
            this.e0.c(typeface);
            this.f1557h.a(typeface);
            TextView textView = this.l;
            if (textView != null) {
                textView.setTypeface(typeface);
            }
        }
    }

    public TextInputLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.textInputStyle);
    }

    private void a(boolean z2, boolean z3) {
        ColorStateList colorStateList;
        TextView textView;
        boolean isEnabled = isEnabled();
        EditText editText = this.f1555f;
        boolean z4 = true;
        boolean z5 = editText != null && !TextUtils.isEmpty(editText.getText());
        EditText editText2 = this.f1555f;
        if (editText2 == null || !editText2.hasFocus()) {
            z4 = false;
        }
        boolean c2 = this.f1557h.c();
        ColorStateList colorStateList2 = this.U;
        if (colorStateList2 != null) {
            this.e0.a(colorStateList2);
            this.e0.b(this.U);
        }
        if (!isEnabled) {
            this.e0.a(ColorStateList.valueOf(this.c0));
            this.e0.b(ColorStateList.valueOf(this.c0));
        } else if (c2) {
            this.e0.a(this.f1557h.f());
        } else if (this.k && (textView = this.l) != null) {
            this.e0.a(textView.getTextColors());
        } else if (z4 && (colorStateList = this.V) != null) {
            this.e0.a(colorStateList);
        }
        if (z5 || (isEnabled() && (z4 || c2))) {
            if (z3 || this.d0) {
                c(z2);
            }
        } else if (z3 || !this.d0) {
            d(z2);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean b() {
        return this.q;
    }

    public TextInputLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f1557h = new b(this);
        this.G = new Rect();
        this.H = new RectF();
        this.e0 = new com.google.android.material.internal.c(this);
        setOrientation(1);
        setWillNotDraw(false);
        setAddStatesFromChildren(true);
        FrameLayout frameLayout = new FrameLayout(context);
        this.e = frameLayout;
        frameLayout.setAddStatesFromChildren(true);
        addView(this.e);
        this.e0.b(com.google.android.material.a.a.a);
        this.e0.a(com.google.android.material.a.a.a);
        this.e0.b(8388659);
        g0 d2 = k.d(context, attributeSet, R$styleable.TextInputLayout, i2, R$style.Widget_Design_TextInputLayout, new int[0]);
        this.o = d2.a(R$styleable.TextInputLayout_hintEnabled, true);
        setHint(d2.e(R$styleable.TextInputLayout_android_hint));
        this.f0 = d2.a(R$styleable.TextInputLayout_hintAnimationEnabled, true);
        this.s = context.getResources().getDimensionPixelOffset(R$dimen.mtrl_textinput_box_bottom_offset);
        this.t = context.getResources().getDimensionPixelOffset(R$dimen.mtrl_textinput_box_label_cutout_padding);
        this.v = d2.b(R$styleable.TextInputLayout_boxCollapsedPaddingTop, 0);
        this.w = d2.a(R$styleable.TextInputLayout_boxCornerRadiusTopStart, 0.0f);
        this.x = d2.a(R$styleable.TextInputLayout_boxCornerRadiusTopEnd, 0.0f);
        this.y = d2.a(R$styleable.TextInputLayout_boxCornerRadiusBottomEnd, 0.0f);
        this.z = d2.a(R$styleable.TextInputLayout_boxCornerRadiusBottomStart, 0.0f);
        this.E = d2.a(R$styleable.TextInputLayout_boxBackgroundColor, 0);
        this.b0 = d2.a(R$styleable.TextInputLayout_boxStrokeColor, 0);
        this.B = context.getResources().getDimensionPixelSize(R$dimen.mtrl_textinput_box_stroke_width_default);
        this.C = context.getResources().getDimensionPixelSize(R$dimen.mtrl_textinput_box_stroke_width_focused);
        this.A = this.B;
        setBoxBackgroundMode(d2.d(R$styleable.TextInputLayout_boxBackgroundMode, 0));
        if (d2.g(R$styleable.TextInputLayout_android_textColorHint)) {
            ColorStateList a2 = d2.a(R$styleable.TextInputLayout_android_textColorHint);
            this.V = a2;
            this.U = a2;
        }
        this.W = androidx.core.content.a.a(context, R$color.mtrl_textinput_default_box_stroke_color);
        this.c0 = androidx.core.content.a.a(context, R$color.mtrl_textinput_disabled_color);
        this.a0 = androidx.core.content.a.a(context, R$color.mtrl_textinput_hovered_box_stroke_color);
        if (d2.g(R$styleable.TextInputLayout_hintTextAppearance, -1) != -1) {
            setHintTextAppearance(d2.g(R$styleable.TextInputLayout_hintTextAppearance, 0));
        }
        int g2 = d2.g(R$styleable.TextInputLayout_errorTextAppearance, 0);
        boolean a3 = d2.a(R$styleable.TextInputLayout_errorEnabled, false);
        int g3 = d2.g(R$styleable.TextInputLayout_helperTextTextAppearance, 0);
        boolean a4 = d2.a(R$styleable.TextInputLayout_helperTextEnabled, false);
        CharSequence e2 = d2.e(R$styleable.TextInputLayout_helperText);
        boolean a5 = d2.a(R$styleable.TextInputLayout_counterEnabled, false);
        setCounterMaxLength(d2.d(R$styleable.TextInputLayout_counterMaxLength, -1));
        this.n = d2.g(R$styleable.TextInputLayout_counterTextAppearance, 0);
        this.m = d2.g(R$styleable.TextInputLayout_counterOverflowTextAppearance, 0);
        this.J = d2.a(R$styleable.TextInputLayout_passwordToggleEnabled, false);
        this.K = d2.b(R$styleable.TextInputLayout_passwordToggleDrawable);
        this.L = d2.e(R$styleable.TextInputLayout_passwordToggleContentDescription);
        if (d2.g(R$styleable.TextInputLayout_passwordToggleTint)) {
            this.R = true;
            this.Q = d2.a(R$styleable.TextInputLayout_passwordToggleTint);
        }
        if (d2.g(R$styleable.TextInputLayout_passwordToggleTintMode)) {
            this.T = true;
            this.S = l.a(d2.d(R$styleable.TextInputLayout_passwordToggleTintMode, -1), (PorterDuff.Mode) null);
        }
        d2.a();
        setHelperTextEnabled(a4);
        setHelperText(e2);
        setHelperTextTextAppearance(g3);
        setErrorEnabled(a3);
        setErrorTextAppearance(g2);
        setCounterEnabled(a5);
        f();
        v.h(this, 2);
    }

    public void setPasswordVisibilityToggleContentDescription(CharSequence charSequence) {
        this.L = charSequence;
        CheckableImageButton checkableImageButton = this.M;
        if (checkableImageButton != null) {
            checkableImageButton.setContentDescription(charSequence);
        }
    }

    public void setPasswordVisibilityToggleDrawable(Drawable drawable) {
        this.K = drawable;
        CheckableImageButton checkableImageButton = this.M;
        if (checkableImageButton != null) {
            checkableImageButton.setImageDrawable(drawable);
        }
    }

    private void c(boolean z2) {
        ValueAnimator valueAnimator = this.g0;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.g0.cancel();
        }
        if (!z2 || !this.f0) {
            this.e0.b(1.0f);
        } else {
            a(1.0f);
        }
        this.d0 = false;
        if (l()) {
            p();
        }
    }

    private void d(boolean z2) {
        ValueAnimator valueAnimator = this.g0;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.g0.cancel();
        }
        if (!z2 || !this.f0) {
            this.e0.b(0.0f);
        } else {
            a(0.0f);
        }
        if (l() && ((a) this.r).a()) {
            k();
        }
        this.d0 = true;
    }

    public boolean a() {
        return this.f1557h.l();
    }

    private static void a(ViewGroup viewGroup, boolean z2) {
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            childAt.setEnabled(z2);
            if (childAt instanceof ViewGroup) {
                a((ViewGroup) childAt, z2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        boolean z2 = this.k;
        if (this.f1559j == -1) {
            this.l.setText(String.valueOf(i2));
            this.l.setContentDescription((CharSequence) null);
            this.k = false;
        } else {
            if (v.d(this.l) == 1) {
                v.g(this.l, 0);
            }
            boolean z3 = i2 > this.f1559j;
            this.k = z3;
            if (z2 != z3) {
                a(this.l, z3 ? this.m : this.n);
                if (this.k) {
                    v.g(this.l, 1);
                }
            }
            this.l.setText(getContext().getString(R$string.character_counter_pattern, new Object[]{Integer.valueOf(i2), Integer.valueOf(this.f1559j)}));
            this.l.setContentDescription(getContext().getString(R$string.character_counter_content_description, new Object[]{Integer.valueOf(i2), Integer.valueOf(this.f1559j)}));
        }
        if (this.f1555f != null && z2 != this.k) {
            b(false);
            d();
            c();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(TextView textView, int i2) {
        boolean z2 = true;
        try {
            i.d(textView, i2);
            if (Build.VERSION.SDK_INT < 23 || textView.getTextColors().getDefaultColor() != -65281) {
                z2 = false;
            }
        } catch (Exception unused) {
        }
        if (z2) {
            i.d(textView, R$style.TextAppearance_AppCompat_Caption);
            textView.setTextColor(androidx.core.content.a.a(getContext(), R$color.design_error));
        }
    }

    public void a(boolean z2) {
        if (this.J) {
            int selectionEnd = this.f1555f.getSelectionEnd();
            if (n()) {
                this.f1555f.setTransformationMethod((TransformationMethod) null);
                this.N = true;
            } else {
                this.f1555f.setTransformationMethod(PasswordTransformationMethod.getInstance());
                this.N = false;
            }
            this.M.setChecked(this.N);
            if (z2) {
                this.M.jumpDrawablesToCurrentState();
            }
            this.f1555f.setSelection(selectionEnd);
        }
    }

    private void a(RectF rectF) {
        float f2 = rectF.left;
        int i2 = this.t;
        rectF.left = f2 - ((float) i2);
        rectF.top -= (float) i2;
        rectF.right += (float) i2;
        rectF.bottom += (float) i2;
    }

    /* access modifiers changed from: package-private */
    public void a(float f2) {
        if (this.e0.i() != f2) {
            if (this.g0 == null) {
                ValueAnimator valueAnimator = new ValueAnimator();
                this.g0 = valueAnimator;
                valueAnimator.setInterpolator(com.google.android.material.a.a.b);
                this.g0.setDuration(167);
                this.g0.addUpdateListener(new c());
            }
            this.g0.setFloatValues(new float[]{this.e0.i(), f2});
            this.g0.start();
        }
    }
}
