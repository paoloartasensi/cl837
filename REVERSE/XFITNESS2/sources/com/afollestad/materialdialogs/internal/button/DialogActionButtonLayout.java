package com.afollestad.materialdialogs.internal.button;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatCheckBox;
import com.afollestad.materialdialogs.R$dimen;
import com.afollestad.materialdialogs.R$id;
import com.afollestad.materialdialogs.WhichButton;
import com.afollestad.materialdialogs.internal.main.BaseSubLayout;
import com.afollestad.materialdialogs.j.e;
import com.afollestad.materialdialogs.j.f;
import java.util.ArrayList;
import kotlin.TypeCastException;
import kotlin.jvm.internal.i;

/* compiled from: DialogActionButtonLayout.kt */
public final class DialogActionButtonLayout extends BaseSubLayout {

    /* renamed from: i  reason: collision with root package name */
    private final int f986i;

    /* renamed from: j  reason: collision with root package name */
    private final int f987j;
    private final int k;
    private final int l;
    private final int m;
    private boolean n;
    public DialogActionButton[] o;
    public AppCompatCheckBox p;

    /* compiled from: DialogActionButtonLayout.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    /* compiled from: DialogActionButtonLayout.kt */
    static final class b implements View.OnClickListener {
        final /* synthetic */ DialogActionButtonLayout e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ WhichButton f988f;

        b(DialogActionButtonLayout dialogActionButtonLayout, WhichButton whichButton) {
            this.e = dialogActionButtonLayout;
            this.f988f = whichButton;
        }

        public final void onClick(View view) {
            this.e.getDialog().a(this.f988f);
        }
    }

    static {
        new a((f) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ DialogActionButtonLayout(Context context, AttributeSet attributeSet, int i2, f fVar) {
        this(context, (i2 & 2) != 0 ? null : attributeSet);
    }

    private final int b() {
        if (getVisibleButtons().length == 0) {
            return 0;
        }
        if (!this.n) {
            return this.k;
        }
        return this.k * getVisibleButtons().length;
    }

    public final DialogActionButton[] getActionButtons() {
        DialogActionButton[] dialogActionButtonArr = this.o;
        if (dialogActionButtonArr != null) {
            return dialogActionButtonArr;
        }
        i.d("actionButtons");
        throw null;
    }

    public final AppCompatCheckBox getCheckBoxPrompt() {
        AppCompatCheckBox appCompatCheckBox = this.p;
        if (appCompatCheckBox != null) {
            return appCompatCheckBox;
        }
        i.d("checkBoxPrompt");
        throw null;
    }

    public final boolean getStackButtons$core() {
        return this.n;
    }

    public final DialogActionButton[] getVisibleButtons() {
        DialogActionButton[] dialogActionButtonArr = this.o;
        if (dialogActionButtonArr != null) {
            ArrayList arrayList = new ArrayList();
            for (DialogActionButton dialogActionButton : dialogActionButtonArr) {
                if (f.c(dialogActionButton)) {
                    arrayList.add(dialogActionButton);
                }
            }
            Object[] array = arrayList.toArray(new DialogActionButton[0]);
            if (array != null) {
                return (DialogActionButton[]) array;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        i.d("actionButtons");
        throw null;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        i.b(canvas, "canvas");
        super.onDraw(canvas);
        if (getDrawDivider()) {
            canvas.drawLine(0.0f, 0.0f, (float) getMeasuredWidth(), (float) getDividerHeight(), a());
        }
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        View findViewById = findViewById(R$id.md_button_positive);
        i.a((Object) findViewById, "findViewById(R.id.md_button_positive)");
        View findViewById2 = findViewById(R$id.md_button_negative);
        i.a((Object) findViewById2, "findViewById(R.id.md_button_negative)");
        View findViewById3 = findViewById(R$id.md_button_neutral);
        i.a((Object) findViewById3, "findViewById(R.id.md_button_neutral)");
        this.o = new DialogActionButton[]{(DialogActionButton) findViewById, (DialogActionButton) findViewById2, (DialogActionButton) findViewById3};
        View findViewById4 = findViewById(R$id.md_checkbox_prompt);
        i.a((Object) findViewById4, "findViewById(R.id.md_checkbox_prompt)");
        this.p = (AppCompatCheckBox) findViewById4;
        DialogActionButton[] dialogActionButtonArr = this.o;
        if (dialogActionButtonArr != null) {
            int length = dialogActionButtonArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                dialogActionButtonArr[i2].setOnClickListener(new b(this, WhichButton.Companion.a(i2)));
            }
            return;
        }
        i.d("actionButtons");
        throw null;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        int i9;
        if (a.a(this)) {
            AppCompatCheckBox appCompatCheckBox = this.p;
            if (appCompatCheckBox != null) {
                if (f.c(appCompatCheckBox)) {
                    if (f.b(this)) {
                        i8 = getMeasuredWidth() - this.m;
                        i7 = this.l;
                        AppCompatCheckBox appCompatCheckBox2 = this.p;
                        if (appCompatCheckBox2 != null) {
                            i6 = i8 - appCompatCheckBox2.getMeasuredWidth();
                            AppCompatCheckBox appCompatCheckBox3 = this.p;
                            if (appCompatCheckBox3 != null) {
                                i9 = appCompatCheckBox3.getMeasuredHeight();
                            } else {
                                i.d("checkBoxPrompt");
                                throw null;
                            }
                        } else {
                            i.d("checkBoxPrompt");
                            throw null;
                        }
                    } else {
                        i6 = this.m;
                        i7 = this.l;
                        AppCompatCheckBox appCompatCheckBox4 = this.p;
                        if (appCompatCheckBox4 != null) {
                            i8 = appCompatCheckBox4.getMeasuredWidth() + i6;
                            AppCompatCheckBox appCompatCheckBox5 = this.p;
                            if (appCompatCheckBox5 != null) {
                                i9 = appCompatCheckBox5.getMeasuredHeight();
                            } else {
                                i.d("checkBoxPrompt");
                                throw null;
                            }
                        } else {
                            i.d("checkBoxPrompt");
                            throw null;
                        }
                    }
                    int i10 = i9 + i7;
                    AppCompatCheckBox appCompatCheckBox6 = this.p;
                    if (appCompatCheckBox6 != null) {
                        appCompatCheckBox6.layout(i6, i7, i8, i10);
                    } else {
                        i.d("checkBoxPrompt");
                        throw null;
                    }
                }
                if (this.n) {
                    int i11 = this.f986i;
                    int measuredWidth = getMeasuredWidth() - this.f986i;
                    int measuredHeight = getMeasuredHeight();
                    for (DialogActionButton layout : f.b(getVisibleButtons())) {
                        int i12 = measuredHeight - this.k;
                        layout.layout(i11, i12, measuredWidth, measuredHeight);
                        measuredHeight = i12;
                    }
                    return;
                }
                int measuredHeight2 = getMeasuredHeight() - this.k;
                int measuredHeight3 = getMeasuredHeight();
                if (f.b(this)) {
                    DialogActionButton[] dialogActionButtonArr = this.o;
                    if (dialogActionButtonArr != null) {
                        if (f.c(dialogActionButtonArr[2])) {
                            DialogActionButton[] dialogActionButtonArr2 = this.o;
                            if (dialogActionButtonArr2 != null) {
                                DialogActionButton dialogActionButton = dialogActionButtonArr2[2];
                                int measuredWidth2 = getMeasuredWidth() - this.f987j;
                                dialogActionButton.layout(measuredWidth2 - dialogActionButton.getMeasuredWidth(), measuredHeight2, measuredWidth2, measuredHeight3);
                            } else {
                                i.d("actionButtons");
                                throw null;
                            }
                        }
                        int i13 = this.f986i;
                        DialogActionButton[] dialogActionButtonArr3 = this.o;
                        if (dialogActionButtonArr3 != null) {
                            if (f.c(dialogActionButtonArr3[0])) {
                                DialogActionButton[] dialogActionButtonArr4 = this.o;
                                if (dialogActionButtonArr4 != null) {
                                    DialogActionButton dialogActionButton2 = dialogActionButtonArr4[0];
                                    int measuredWidth3 = dialogActionButton2.getMeasuredWidth() + i13;
                                    dialogActionButton2.layout(i13, measuredHeight2, measuredWidth3, measuredHeight3);
                                    i13 = measuredWidth3;
                                } else {
                                    i.d("actionButtons");
                                    throw null;
                                }
                            }
                            DialogActionButton[] dialogActionButtonArr5 = this.o;
                            if (dialogActionButtonArr5 == null) {
                                i.d("actionButtons");
                                throw null;
                            } else if (f.c(dialogActionButtonArr5[1])) {
                                DialogActionButton[] dialogActionButtonArr6 = this.o;
                                if (dialogActionButtonArr6 != null) {
                                    DialogActionButton dialogActionButton3 = dialogActionButtonArr6[1];
                                    dialogActionButton3.layout(i13, measuredHeight2, dialogActionButton3.getMeasuredWidth() + i13, measuredHeight3);
                                    return;
                                }
                                i.d("actionButtons");
                                throw null;
                            }
                        } else {
                            i.d("actionButtons");
                            throw null;
                        }
                    } else {
                        i.d("actionButtons");
                        throw null;
                    }
                } else {
                    DialogActionButton[] dialogActionButtonArr7 = this.o;
                    if (dialogActionButtonArr7 != null) {
                        if (f.c(dialogActionButtonArr7[2])) {
                            DialogActionButton[] dialogActionButtonArr8 = this.o;
                            if (dialogActionButtonArr8 != null) {
                                DialogActionButton dialogActionButton4 = dialogActionButtonArr8[2];
                                int i14 = this.f987j;
                                dialogActionButton4.layout(i14, measuredHeight2, dialogActionButton4.getMeasuredWidth() + i14, measuredHeight3);
                            } else {
                                i.d("actionButtons");
                                throw null;
                            }
                        }
                        int measuredWidth4 = getMeasuredWidth() - this.f986i;
                        DialogActionButton[] dialogActionButtonArr9 = this.o;
                        if (dialogActionButtonArr9 != null) {
                            if (f.c(dialogActionButtonArr9[0])) {
                                DialogActionButton[] dialogActionButtonArr10 = this.o;
                                if (dialogActionButtonArr10 != null) {
                                    DialogActionButton dialogActionButton5 = dialogActionButtonArr10[0];
                                    int measuredWidth5 = measuredWidth4 - dialogActionButton5.getMeasuredWidth();
                                    dialogActionButton5.layout(measuredWidth5, measuredHeight2, measuredWidth4, measuredHeight3);
                                    measuredWidth4 = measuredWidth5;
                                } else {
                                    i.d("actionButtons");
                                    throw null;
                                }
                            }
                            DialogActionButton[] dialogActionButtonArr11 = this.o;
                            if (dialogActionButtonArr11 == null) {
                                i.d("actionButtons");
                                throw null;
                            } else if (f.c(dialogActionButtonArr11[1])) {
                                DialogActionButton[] dialogActionButtonArr12 = this.o;
                                if (dialogActionButtonArr12 != null) {
                                    DialogActionButton dialogActionButton6 = dialogActionButtonArr12[1];
                                    dialogActionButton6.layout(measuredWidth4 - dialogActionButton6.getMeasuredWidth(), measuredHeight2, measuredWidth4, measuredHeight3);
                                    return;
                                }
                                i.d("actionButtons");
                                throw null;
                            }
                        } else {
                            i.d("actionButtons");
                            throw null;
                        }
                    } else {
                        i.d("actionButtons");
                        throw null;
                    }
                }
            } else {
                i.d("checkBoxPrompt");
                throw null;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        if (!a.a(this)) {
            setMeasuredDimension(0, 0);
            return;
        }
        int size = View.MeasureSpec.getSize(i2);
        AppCompatCheckBox appCompatCheckBox = this.p;
        if (appCompatCheckBox != null) {
            if (f.c(appCompatCheckBox)) {
                int i4 = size - (this.m * 2);
                AppCompatCheckBox appCompatCheckBox2 = this.p;
                if (appCompatCheckBox2 != null) {
                    appCompatCheckBox2.measure(View.MeasureSpec.makeMeasureSpec(i4, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, 0));
                } else {
                    i.d("checkBoxPrompt");
                    throw null;
                }
            }
            Context context = getDialog().getContext();
            i.a((Object) context, "dialog.context");
            Context f2 = getDialog().f();
            for (DialogActionButton dialogActionButton : getVisibleButtons()) {
                dialogActionButton.a(context, f2, this.n);
                if (this.n) {
                    dialogActionButton.measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(this.k, 1073741824));
                } else {
                    dialogActionButton.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(this.k, 1073741824));
                }
            }
            if ((!(getVisibleButtons().length == 0)) && !this.n) {
                int i5 = 0;
                for (DialogActionButton measuredWidth : getVisibleButtons()) {
                    i5 += measuredWidth.getMeasuredWidth();
                }
                if (i5 >= size && !this.n) {
                    this.n = true;
                    for (DialogActionButton dialogActionButton2 : getVisibleButtons()) {
                        dialogActionButton2.a(context, f2, true);
                        dialogActionButton2.measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(this.k, 1073741824));
                    }
                }
            }
            int b2 = b();
            AppCompatCheckBox appCompatCheckBox3 = this.p;
            if (appCompatCheckBox3 != null) {
                if (f.c(appCompatCheckBox3)) {
                    AppCompatCheckBox appCompatCheckBox4 = this.p;
                    if (appCompatCheckBox4 != null) {
                        b2 += appCompatCheckBox4.getMeasuredHeight() + (this.l * 2);
                    } else {
                        i.d("checkBoxPrompt");
                        throw null;
                    }
                }
                setMeasuredDimension(size, b2);
                return;
            }
            i.d("checkBoxPrompt");
            throw null;
        }
        i.d("checkBoxPrompt");
        throw null;
    }

    public final void setActionButtons(DialogActionButton[] dialogActionButtonArr) {
        i.b(dialogActionButtonArr, "<set-?>");
        this.o = dialogActionButtonArr;
    }

    public final void setCheckBoxPrompt(AppCompatCheckBox appCompatCheckBox) {
        i.b(appCompatCheckBox, "<set-?>");
        this.p = appCompatCheckBox;
    }

    public final void setStackButtons$core(boolean z) {
        this.n = z;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DialogActionButtonLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        i.b(context, "context");
        this.f986i = e.a.a(this, R$dimen.md_action_button_frame_padding) - e.a.a(this, R$dimen.md_action_button_inset_horizontal);
        this.f987j = e.a.a(this, R$dimen.md_action_button_frame_padding_neutral);
        this.k = e.a.a(this, R$dimen.md_action_button_frame_spec_height);
        this.l = e.a.a(this, R$dimen.md_checkbox_prompt_margin_vertical);
        this.m = e.a.a(this, R$dimen.md_checkbox_prompt_margin_horizontal);
    }
}
