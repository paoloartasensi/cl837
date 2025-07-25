package com.afollestad.materialdialogs.internal.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.afollestad.materialdialogs.LayoutMode;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.R$dimen;
import com.afollestad.materialdialogs.R$id;
import com.afollestad.materialdialogs.internal.button.DialogActionButton;
import com.afollestad.materialdialogs.internal.button.DialogActionButtonLayout;
import com.afollestad.materialdialogs.internal.button.a;
import com.afollestad.materialdialogs.internal.message.DialogContentLayout;
import com.afollestad.materialdialogs.j.c;
import com.afollestad.materialdialogs.j.e;
import com.afollestad.materialdialogs.j.f;
import kotlin.TypeCastException;
import kotlin.jvm.internal.i;

/* compiled from: DialogLayout.kt */
public final class DialogLayout extends FrameLayout {
    private int e;

    /* renamed from: f  reason: collision with root package name */
    private boolean f993f;

    /* renamed from: g  reason: collision with root package name */
    private float[] f994g = new float[0];

    /* renamed from: h  reason: collision with root package name */
    private Paint f995h;

    /* renamed from: i  reason: collision with root package name */
    private final int f996i = e.a.a(this, R$dimen.md_dialog_frame_margin_vertical);

    /* renamed from: j  reason: collision with root package name */
    private final int f997j = e.a.a(this, R$dimen.md_dialog_frame_margin_vertical_less);
    public MaterialDialog k;
    public DialogTitleLayout l;
    public DialogContentLayout m;
    private DialogActionButtonLayout n;
    private LayoutMode o = LayoutMode.WRAP_CONTENT;
    private boolean p = true;
    private int q = -1;
    private final Path r = new Path();
    private final RectF s = new RectF();

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DialogLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        i.b(context, "context");
    }

    static /* synthetic */ void b(DialogLayout dialogLayout, Canvas canvas, int i2, float f2, float f3, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            f3 = f2;
        }
        dialogLayout.b(canvas, i2, f2, f3);
    }

    public final void a(MaterialDialog materialDialog) {
        i.b(materialDialog, "dialog");
        DialogTitleLayout dialogTitleLayout = this.l;
        if (dialogTitleLayout != null) {
            dialogTitleLayout.setDialog(materialDialog);
            DialogActionButtonLayout dialogActionButtonLayout = this.n;
            if (dialogActionButtonLayout != null) {
                dialogActionButtonLayout.setDialog(materialDialog);
                return;
            }
            return;
        }
        i.d("titleLayout");
        throw null;
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        i.b(canvas, "canvas");
        if (!(this.f994g.length == 0)) {
            canvas.clipPath(this.r);
        }
        super.dispatchDraw(canvas);
    }

    public final DialogActionButtonLayout getButtonsLayout() {
        return this.n;
    }

    public final DialogContentLayout getContentLayout() {
        DialogContentLayout dialogContentLayout = this.m;
        if (dialogContentLayout != null) {
            return dialogContentLayout;
        }
        i.d("contentLayout");
        throw null;
    }

    public final float[] getCornerRadii() {
        return this.f994g;
    }

    public final boolean getDebugMode() {
        return this.f993f;
    }

    public final MaterialDialog getDialog() {
        MaterialDialog materialDialog = this.k;
        if (materialDialog != null) {
            return materialDialog;
        }
        i.d("dialog");
        throw null;
    }

    public final int getFrameMarginVertical$core() {
        return this.f996i;
    }

    public final int getFrameMarginVerticalLess$core() {
        return this.f997j;
    }

    public final LayoutMode getLayoutMode() {
        return this.o;
    }

    public final int getMaxHeight() {
        return this.e;
    }

    public final DialogTitleLayout getTitleLayout() {
        DialogTitleLayout dialogTitleLayout = this.l;
        if (dialogTitleLayout != null) {
            return dialogTitleLayout;
        }
        i.d("titleLayout");
        throw null;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Object systemService = getContext().getSystemService("window");
        if (systemService != null) {
            this.q = e.a.a((WindowManager) systemService).component2().intValue();
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type android.view.WindowManager");
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float f2;
        i.b(canvas, "canvas");
        super.onDraw(canvas);
        if (this.f993f) {
            Canvas canvas2 = canvas;
            b(this, canvas2, -16776961, c.a(this, 24), 0.0f, 4, (Object) null);
            a(this, canvas2, -16776961, c.a(this, 24), 0.0f, 4, (Object) null);
            b(this, canvas, -16776961, ((float) getMeasuredWidth()) - c.a(this, 24), 0.0f, 4, (Object) null);
            DialogTitleLayout dialogTitleLayout = this.l;
            if (dialogTitleLayout != null) {
                if (f.c(dialogTitleLayout)) {
                    DialogTitleLayout dialogTitleLayout2 = this.l;
                    if (dialogTitleLayout2 != null) {
                        a(this, canvas, -65536, (float) dialogTitleLayout2.getBottom(), 0.0f, 4, (Object) null);
                    } else {
                        i.d("titleLayout");
                        throw null;
                    }
                }
                DialogContentLayout dialogContentLayout = this.m;
                if (dialogContentLayout != null) {
                    if (f.c(dialogContentLayout)) {
                        DialogContentLayout dialogContentLayout2 = this.m;
                        if (dialogContentLayout2 != null) {
                            a(this, canvas, -256, (float) dialogContentLayout2.getTop(), 0.0f, 4, (Object) null);
                        } else {
                            i.d("contentLayout");
                            throw null;
                        }
                    }
                    if (a.a(this.n)) {
                        if (f.b(this)) {
                            f2 = c.a(this, 8);
                        } else {
                            f2 = ((float) getMeasuredWidth()) - c.a(this, 8);
                        }
                        b(this, canvas, -16711681, f2, 0.0f, 4, (Object) null);
                        DialogActionButtonLayout dialogActionButtonLayout = this.n;
                        if (dialogActionButtonLayout == null || !dialogActionButtonLayout.getStackButtons$core()) {
                            DialogActionButtonLayout dialogActionButtonLayout2 = this.n;
                            if (dialogActionButtonLayout2 == null) {
                                return;
                            }
                            if (dialogActionButtonLayout2 != null) {
                                DialogActionButton[] visibleButtons = dialogActionButtonLayout2.getVisibleButtons();
                                int length = visibleButtons.length;
                                int i2 = 0;
                                while (i2 < length) {
                                    DialogActionButton dialogActionButton = visibleButtons[i2];
                                    DialogActionButtonLayout dialogActionButtonLayout3 = this.n;
                                    if (dialogActionButtonLayout3 != null) {
                                        float top = ((float) dialogActionButtonLayout3.getTop()) + ((float) dialogActionButton.getTop()) + c.a(this, 8);
                                        DialogActionButtonLayout dialogActionButtonLayout4 = this.n;
                                        if (dialogActionButtonLayout4 != null) {
                                            a(canvas, -16711681, 0.4f, ((float) dialogActionButton.getLeft()) + c.a(this, 4), ((float) dialogActionButton.getRight()) - c.a(this, 4), top, ((float) dialogActionButtonLayout4.getBottom()) - c.a(this, 8));
                                            i2++;
                                        } else {
                                            i.a();
                                            throw null;
                                        }
                                    } else {
                                        i.a();
                                        throw null;
                                    }
                                }
                                DialogActionButtonLayout dialogActionButtonLayout5 = this.n;
                                if (dialogActionButtonLayout5 != null) {
                                    a(this, canvas, -65281, (float) dialogActionButtonLayout5.getTop(), 0.0f, 4, (Object) null);
                                    float measuredHeight = ((float) getMeasuredHeight()) - (c.a(this, 52) - c.a(this, 8));
                                    float measuredHeight2 = ((float) getMeasuredHeight()) - c.a(this, 8);
                                    Canvas canvas3 = canvas;
                                    a(this, canvas3, -65536, measuredHeight, 0.0f, 4, (Object) null);
                                    a(this, canvas3, -65536, measuredHeight2, 0.0f, 4, (Object) null);
                                    a(this, canvas3, -16776961, measuredHeight - c.a(this, 8), 0.0f, 4, (Object) null);
                                    return;
                                }
                                i.a();
                                throw null;
                            }
                            i.a();
                            throw null;
                        }
                        DialogActionButtonLayout dialogActionButtonLayout6 = this.n;
                        if (dialogActionButtonLayout6 != null) {
                            float top2 = ((float) dialogActionButtonLayout6.getTop()) + c.a(this, 8);
                            DialogActionButtonLayout dialogActionButtonLayout7 = this.n;
                            if (dialogActionButtonLayout7 != null) {
                                float f3 = top2;
                                for (DialogActionButton left : dialogActionButtonLayout7.getVisibleButtons()) {
                                    float a = f3 + c.a(this, 36);
                                    a(canvas, -16711681, 0.4f, (float) left.getLeft(), ((float) getMeasuredWidth()) - c.a(this, 8), f3, a);
                                    f3 = a + c.a(this, 16);
                                }
                                DialogActionButtonLayout dialogActionButtonLayout8 = this.n;
                                if (dialogActionButtonLayout8 != null) {
                                    a(this, canvas, -16776961, (float) dialogActionButtonLayout8.getTop(), 0.0f, 4, (Object) null);
                                    DialogActionButtonLayout dialogActionButtonLayout9 = this.n;
                                    if (dialogActionButtonLayout9 != null) {
                                        float top3 = ((float) dialogActionButtonLayout9.getTop()) + c.a(this, 8);
                                        float measuredHeight3 = ((float) getMeasuredHeight()) - c.a(this, 8);
                                        Canvas canvas4 = canvas;
                                        a(this, canvas4, -65536, top3, 0.0f, 4, (Object) null);
                                        a(this, canvas4, -65536, measuredHeight3, 0.0f, 4, (Object) null);
                                        return;
                                    }
                                    i.a();
                                    throw null;
                                }
                                i.a();
                                throw null;
                            }
                            i.a();
                            throw null;
                        }
                        i.a();
                        throw null;
                    }
                    return;
                }
                i.d("contentLayout");
                throw null;
            }
            i.d("titleLayout");
            throw null;
        }
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        View findViewById = findViewById(R$id.md_title_layout);
        i.a((Object) findViewById, "findViewById(R.id.md_title_layout)");
        this.l = (DialogTitleLayout) findViewById;
        View findViewById2 = findViewById(R$id.md_content_layout);
        i.a((Object) findViewById2, "findViewById(R.id.md_content_layout)");
        this.m = (DialogContentLayout) findViewById2;
        this.n = (DialogActionButtonLayout) findViewById(R$id.md_button_layout);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        int i6;
        int measuredWidth = getMeasuredWidth();
        DialogTitleLayout dialogTitleLayout = this.l;
        if (dialogTitleLayout != null) {
            int measuredHeight = dialogTitleLayout.getMeasuredHeight();
            DialogTitleLayout dialogTitleLayout2 = this.l;
            if (dialogTitleLayout2 != null) {
                dialogTitleLayout2.layout(0, 0, measuredWidth, measuredHeight);
                if (this.p) {
                    int measuredHeight2 = getMeasuredHeight();
                    DialogActionButtonLayout dialogActionButtonLayout = this.n;
                    i6 = measuredHeight2 - (dialogActionButtonLayout != null ? dialogActionButtonLayout.getMeasuredHeight() : 0);
                    if (a.a(this.n)) {
                        int measuredWidth2 = getMeasuredWidth();
                        int measuredHeight3 = getMeasuredHeight();
                        DialogActionButtonLayout dialogActionButtonLayout2 = this.n;
                        if (dialogActionButtonLayout2 != null) {
                            dialogActionButtonLayout2.layout(0, i6, measuredWidth2, measuredHeight3);
                        } else {
                            i.a();
                            throw null;
                        }
                    }
                } else {
                    i6 = getMeasuredHeight();
                }
                int measuredWidth3 = getMeasuredWidth();
                DialogContentLayout dialogContentLayout = this.m;
                if (dialogContentLayout != null) {
                    dialogContentLayout.layout(0, measuredHeight, measuredWidth3, i6);
                } else {
                    i.d("contentLayout");
                    throw null;
                }
            } else {
                i.d("titleLayout");
                throw null;
            }
        } else {
            i.d("titleLayout");
            throw null;
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int size = View.MeasureSpec.getSize(i2);
        int size2 = View.MeasureSpec.getSize(i3);
        int i4 = this.e;
        if (1 <= i4 && size2 > i4) {
            size2 = i4;
        }
        DialogTitleLayout dialogTitleLayout = this.l;
        if (dialogTitleLayout != null) {
            boolean z = false;
            dialogTitleLayout.measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
            if (a.a(this.n)) {
                DialogActionButtonLayout dialogActionButtonLayout = this.n;
                if (dialogActionButtonLayout != null) {
                    dialogActionButtonLayout.measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
                } else {
                    i.a();
                    throw null;
                }
            }
            DialogTitleLayout dialogTitleLayout2 = this.l;
            if (dialogTitleLayout2 != null) {
                int measuredHeight = dialogTitleLayout2.getMeasuredHeight();
                DialogActionButtonLayout dialogActionButtonLayout2 = this.n;
                int measuredHeight2 = size2 - (measuredHeight + (dialogActionButtonLayout2 != null ? dialogActionButtonLayout2.getMeasuredHeight() : 0));
                DialogContentLayout dialogContentLayout = this.m;
                if (dialogContentLayout != null) {
                    dialogContentLayout.measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(measuredHeight2, Integer.MIN_VALUE));
                    if (this.o == LayoutMode.WRAP_CONTENT) {
                        DialogTitleLayout dialogTitleLayout3 = this.l;
                        if (dialogTitleLayout3 != null) {
                            int measuredHeight3 = dialogTitleLayout3.getMeasuredHeight();
                            DialogContentLayout dialogContentLayout2 = this.m;
                            if (dialogContentLayout2 != null) {
                                int measuredHeight4 = measuredHeight3 + dialogContentLayout2.getMeasuredHeight();
                                DialogActionButtonLayout dialogActionButtonLayout3 = this.n;
                                setMeasuredDimension(size, measuredHeight4 + (dialogActionButtonLayout3 != null ? dialogActionButtonLayout3.getMeasuredHeight() : 0));
                            } else {
                                i.d("contentLayout");
                                throw null;
                            }
                        } else {
                            i.d("titleLayout");
                            throw null;
                        }
                    } else {
                        setMeasuredDimension(size, this.q);
                    }
                    if (this.f994g.length == 0) {
                        z = true;
                    }
                    if (!z) {
                        RectF rectF = this.s;
                        rectF.left = 0.0f;
                        rectF.top = 0.0f;
                        rectF.right = (float) getMeasuredWidth();
                        rectF.bottom = (float) getMeasuredHeight();
                        this.r.addRoundRect(this.s, this.f994g, Path.Direction.CW);
                        return;
                    }
                    return;
                }
                i.d("contentLayout");
                throw null;
            }
            i.d("titleLayout");
            throw null;
        }
        i.d("titleLayout");
        throw null;
    }

    public final void setButtonsLayout(DialogActionButtonLayout dialogActionButtonLayout) {
        this.n = dialogActionButtonLayout;
    }

    public final void setContentLayout(DialogContentLayout dialogContentLayout) {
        i.b(dialogContentLayout, "<set-?>");
        this.m = dialogContentLayout;
    }

    public final void setCornerRadii(float[] fArr) {
        i.b(fArr, "value");
        this.f994g = fArr;
        if (!this.r.isEmpty()) {
            this.r.reset();
        }
        invalidate();
    }

    public final void setDebugMode(boolean z) {
        this.f993f = z;
        setWillNotDraw(!z);
    }

    public final void setDialog(MaterialDialog materialDialog) {
        i.b(materialDialog, "<set-?>");
        this.k = materialDialog;
    }

    public final void setLayoutMode(LayoutMode layoutMode) {
        i.b(layoutMode, "<set-?>");
        this.o = layoutMode;
    }

    public final void setMaxHeight(int i2) {
        this.e = i2;
    }

    public final void setTitleLayout(DialogTitleLayout dialogTitleLayout) {
        i.b(dialogTitleLayout, "<set-?>");
        this.l = dialogTitleLayout;
    }

    private final void b(Canvas canvas, int i2, float f2, float f3) {
        a(canvas, i2, f2, f3, 0.0f, (float) getMeasuredHeight());
    }

    public final void a(boolean z, boolean z2) {
        DialogTitleLayout dialogTitleLayout = this.l;
        if (dialogTitleLayout != null) {
            dialogTitleLayout.setDrawDivider(z);
            DialogActionButtonLayout dialogActionButtonLayout = this.n;
            if (dialogActionButtonLayout != null) {
                dialogActionButtonLayout.setDrawDivider(z2);
                return;
            }
            return;
        }
        i.d("titleLayout");
        throw null;
    }

    static /* synthetic */ Paint a(DialogLayout dialogLayout, int i2, float f2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            f2 = 1.0f;
        }
        return dialogLayout.a(i2, f2);
    }

    private final Paint a(int i2, float f2) {
        if (this.f995h == null) {
            Paint paint = new Paint();
            paint.setStrokeWidth(c.a(this, 1));
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            this.f995h = paint;
        }
        Paint paint2 = this.f995h;
        if (paint2 != null) {
            paint2.setColor(i2);
            setAlpha(f2);
            return paint2;
        }
        i.a();
        throw null;
    }

    private final void a(Canvas canvas, int i2, float f2, float f3, float f4, float f5, float f6) {
        canvas.drawRect(f3, f5, f4, f6, a(i2, f2));
    }

    private final void a(Canvas canvas, int i2, float f2, float f3, float f4, float f5) {
        canvas.drawLine(f2, f4, f3, f5, a(this, i2, 0.0f, 2, (Object) null));
    }

    static /* synthetic */ void a(DialogLayout dialogLayout, Canvas canvas, int i2, float f2, float f3, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            f2 = (float) dialogLayout.getMeasuredHeight();
        }
        if ((i3 & 4) != 0) {
            f3 = f2;
        }
        dialogLayout.a(canvas, i2, f2, f3);
    }

    private final void a(Canvas canvas, int i2, float f2, float f3) {
        a(canvas, i2, 0.0f, (float) getMeasuredWidth(), f2, f3);
    }
}
