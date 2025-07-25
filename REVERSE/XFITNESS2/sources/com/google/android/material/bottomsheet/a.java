package com.google.android.material.bottomsheet;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import androidx.appcompat.app.f;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.h.v;
import com.google.android.material.R$attr;
import com.google.android.material.R$id;
import com.google.android.material.R$layout;
import com.google.android.material.R$style;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

/* compiled from: BottomSheetDialog */
public class a extends f {

    /* renamed from: g  reason: collision with root package name */
    private BottomSheetBehavior<FrameLayout> f1436g;

    /* renamed from: h  reason: collision with root package name */
    boolean f1437h;

    /* renamed from: i  reason: collision with root package name */
    private boolean f1438i;

    /* renamed from: j  reason: collision with root package name */
    private boolean f1439j;
    private BottomSheetBehavior.c k;

    /* renamed from: com.google.android.material.bottomsheet.a$a  reason: collision with other inner class name */
    /* compiled from: BottomSheetDialog */
    class C0075a implements View.OnClickListener {
        C0075a() {
        }

        public void onClick(View view) {
            a aVar = a.this;
            if (aVar.f1437h && aVar.isShowing() && a.this.b()) {
                a.this.cancel();
            }
        }
    }

    /* compiled from: BottomSheetDialog */
    class c implements View.OnTouchListener {
        c(a aVar) {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* compiled from: BottomSheetDialog */
    class d extends BottomSheetBehavior.c {
        d() {
        }

        public void a(View view, float f2) {
        }

        public void a(View view, int i2) {
            if (i2 == 5) {
                a.this.cancel();
            }
        }
    }

    public a(Context context) {
        this(context, 0);
    }

    private View a(int i2, View view, ViewGroup.LayoutParams layoutParams) {
        FrameLayout frameLayout = (FrameLayout) View.inflate(getContext(), R$layout.design_bottom_sheet_dialog, (ViewGroup) null);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) frameLayout.findViewById(R$id.coordinator);
        if (i2 != 0 && view == null) {
            view = getLayoutInflater().inflate(i2, coordinatorLayout, false);
        }
        FrameLayout frameLayout2 = (FrameLayout) coordinatorLayout.findViewById(R$id.design_bottom_sheet);
        BottomSheetBehavior<FrameLayout> b2 = BottomSheetBehavior.b(frameLayout2);
        this.f1436g = b2;
        b2.a(this.k);
        this.f1436g.b(this.f1437h);
        if (layoutParams == null) {
            frameLayout2.addView(view);
        } else {
            frameLayout2.addView(view, layoutParams);
        }
        coordinatorLayout.findViewById(R$id.touch_outside).setOnClickListener(new C0075a());
        v.a((View) frameLayout2, (androidx.core.h.a) new b());
        frameLayout2.setOnTouchListener(new c(this));
        return frameLayout;
    }

    /* access modifiers changed from: package-private */
    public boolean b() {
        if (!this.f1439j) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{16843611});
            this.f1438i = obtainStyledAttributes.getBoolean(0, true);
            obtainStyledAttributes.recycle();
            this.f1439j = true;
        }
        return this.f1438i;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        if (window != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                window.clearFlags(67108864);
                window.addFlags(Integer.MIN_VALUE);
            }
            window.setLayout(-1, -1);
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        BottomSheetBehavior<FrameLayout> bottomSheetBehavior = this.f1436g;
        if (bottomSheetBehavior != null && bottomSheetBehavior.b() == 5) {
            this.f1436g.c(4);
        }
    }

    public void setCancelable(boolean z) {
        super.setCancelable(z);
        if (this.f1437h != z) {
            this.f1437h = z;
            BottomSheetBehavior<FrameLayout> bottomSheetBehavior = this.f1436g;
            if (bottomSheetBehavior != null) {
                bottomSheetBehavior.b(z);
            }
        }
    }

    public void setCanceledOnTouchOutside(boolean z) {
        super.setCanceledOnTouchOutside(z);
        if (z && !this.f1437h) {
            this.f1437h = true;
        }
        this.f1438i = z;
        this.f1439j = true;
    }

    public void setContentView(int i2) {
        super.setContentView(a(i2, (View) null, (ViewGroup.LayoutParams) null));
    }

    public a(Context context, int i2) {
        super(context, a(context, i2));
        this.f1437h = true;
        this.f1438i = true;
        this.k = new d();
        a(1);
    }

    public void setContentView(View view) {
        super.setContentView(a(0, view, (ViewGroup.LayoutParams) null));
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        super.setContentView(a(0, view, layoutParams));
    }

    /* compiled from: BottomSheetDialog */
    class b extends androidx.core.h.a {
        b() {
        }

        public void a(View view, androidx.core.h.e0.d dVar) {
            super.a(view, dVar);
            if (a.this.f1437h) {
                dVar.a(1048576);
                dVar.g(true);
                return;
            }
            dVar.g(false);
        }

        public boolean a(View view, int i2, Bundle bundle) {
            if (i2 == 1048576) {
                a aVar = a.this;
                if (aVar.f1437h) {
                    aVar.cancel();
                    return true;
                }
            }
            return super.a(view, i2, bundle);
        }
    }

    private static int a(Context context, int i2) {
        if (i2 != 0) {
            return i2;
        }
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(R$attr.bottomSheetDialogTheme, typedValue, true)) {
            return typedValue.resourceId;
        }
        return R$style.Theme_Design_Light_BottomSheetDialog;
    }
}
