package com.afollestad.materialdialogs.internal.main;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.afollestad.materialdialogs.R$dimen;
import com.afollestad.materialdialogs.R$id;
import com.afollestad.materialdialogs.j.e;
import com.afollestad.materialdialogs.j.f;
import kotlin.jvm.internal.i;

/* compiled from: DialogTitleLayout.kt */
public final class DialogTitleLayout extends BaseSubLayout {

    /* renamed from: i  reason: collision with root package name */
    private final int f998i;

    /* renamed from: j  reason: collision with root package name */
    private final int f999j;
    private final int k;
    private final int l;
    private final int m;
    public ImageView n;
    public TextView o;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ DialogTitleLayout(Context context, AttributeSet attributeSet, int i2, f fVar) {
        this(context, (i2 & 2) != 0 ? null : attributeSet);
    }

    public final boolean b() {
        ImageView imageView = this.n;
        if (imageView != null) {
            if (f.a(imageView)) {
                TextView textView = this.o;
                if (textView == null) {
                    i.d("titleView");
                    throw null;
                } else if (f.a(textView)) {
                    return true;
                }
            }
            return false;
        }
        i.d("iconView");
        throw null;
    }

    public final ImageView getIconView$core() {
        ImageView imageView = this.n;
        if (imageView != null) {
            return imageView;
        }
        i.d("iconView");
        throw null;
    }

    public final TextView getTitleView$core() {
        TextView textView = this.o;
        if (textView != null) {
            return textView;
        }
        i.d("titleView");
        throw null;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        i.b(canvas, "canvas");
        super.onDraw(canvas);
        if (getDrawDivider()) {
            canvas.drawLine(0.0f, ((float) getMeasuredHeight()) - ((float) getDividerHeight()), (float) getMeasuredWidth(), (float) getMeasuredHeight(), a());
        }
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        View findViewById = findViewById(R$id.md_icon_title);
        i.a((Object) findViewById, "findViewById(R.id.md_icon_title)");
        this.n = (ImageView) findViewById;
        View findViewById2 = findViewById(R$id.md_text_title);
        i.a((Object) findViewById2, "findViewById(R.id.md_text_title)");
        this.o = (TextView) findViewById2;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        int i9;
        if (!b()) {
            int i10 = this.f998i;
            int measuredHeight = getMeasuredHeight() - this.f999j;
            int i11 = measuredHeight - ((measuredHeight - i10) / 2);
            TextView textView = this.o;
            if (textView != null) {
                int measuredHeight2 = textView.getMeasuredHeight() / 2;
                int i12 = i11 - measuredHeight2;
                int i13 = measuredHeight2 + i11;
                e eVar = e.a;
                TextView textView2 = this.o;
                if (textView2 != null) {
                    int a = i13 + eVar.a(textView2);
                    if (f.b(this)) {
                        i7 = getMeasuredWidth() - this.k;
                        TextView textView3 = this.o;
                        if (textView3 != null) {
                            i6 = i7 - textView3.getMeasuredWidth();
                        } else {
                            i.d("titleView");
                            throw null;
                        }
                    } else {
                        i6 = this.k;
                        TextView textView4 = this.o;
                        if (textView4 != null) {
                            i7 = textView4.getMeasuredWidth() + i6;
                        } else {
                            i.d("titleView");
                            throw null;
                        }
                    }
                    ImageView imageView = this.n;
                    if (imageView != null) {
                        if (f.c(imageView)) {
                            ImageView imageView2 = this.n;
                            if (imageView2 != null) {
                                int measuredHeight3 = imageView2.getMeasuredHeight() / 2;
                                int i14 = i11 - measuredHeight3;
                                int i15 = i11 + measuredHeight3;
                                if (f.b(this)) {
                                    ImageView imageView3 = this.n;
                                    if (imageView3 != null) {
                                        i6 = i7 - imageView3.getMeasuredWidth();
                                        i9 = i6 - this.l;
                                        TextView textView5 = this.o;
                                        if (textView5 != null) {
                                            i8 = i9 - textView5.getMeasuredWidth();
                                        } else {
                                            i.d("titleView");
                                            throw null;
                                        }
                                    } else {
                                        i.d("iconView");
                                        throw null;
                                    }
                                } else {
                                    ImageView imageView4 = this.n;
                                    if (imageView4 != null) {
                                        i7 = imageView4.getMeasuredWidth() + i6;
                                        int i16 = this.l + i7;
                                        TextView textView6 = this.o;
                                        if (textView6 != null) {
                                            int measuredWidth = textView6.getMeasuredWidth() + i16;
                                            i8 = i16;
                                            i9 = measuredWidth;
                                        } else {
                                            i.d("titleView");
                                            throw null;
                                        }
                                    } else {
                                        i.d("iconView");
                                        throw null;
                                    }
                                }
                                ImageView imageView5 = this.n;
                                if (imageView5 != null) {
                                    imageView5.layout(i6, i14, i7, i15);
                                    i7 = i9;
                                    i6 = i8;
                                } else {
                                    i.d("iconView");
                                    throw null;
                                }
                            } else {
                                i.d("iconView");
                                throw null;
                            }
                        }
                        TextView textView7 = this.o;
                        if (textView7 != null) {
                            textView7.layout(i6, i12, i7, a);
                        } else {
                            i.d("titleView");
                            throw null;
                        }
                    } else {
                        i.d("iconView");
                        throw null;
                    }
                } else {
                    i.d("titleView");
                    throw null;
                }
            } else {
                i.d("titleView");
                throw null;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int i4 = 0;
        if (b()) {
            setMeasuredDimension(0, 0);
            return;
        }
        int size = View.MeasureSpec.getSize(i2);
        int i5 = size - (this.k * 2);
        ImageView imageView = this.n;
        if (imageView != null) {
            if (f.c(imageView)) {
                ImageView imageView2 = this.n;
                if (imageView2 != null) {
                    imageView2.measure(View.MeasureSpec.makeMeasureSpec(this.m, 1073741824), View.MeasureSpec.makeMeasureSpec(this.m, 1073741824));
                    ImageView imageView3 = this.n;
                    if (imageView3 != null) {
                        i5 -= imageView3.getMeasuredWidth() + this.l;
                    } else {
                        i.d("iconView");
                        throw null;
                    }
                } else {
                    i.d("iconView");
                    throw null;
                }
            }
            TextView textView = this.o;
            if (textView != null) {
                textView.measure(View.MeasureSpec.makeMeasureSpec(i5, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, 0));
                ImageView imageView4 = this.n;
                if (imageView4 != null) {
                    if (f.c(imageView4)) {
                        ImageView imageView5 = this.n;
                        if (imageView5 != null) {
                            i4 = imageView5.getMeasuredHeight();
                        } else {
                            i.d("iconView");
                            throw null;
                        }
                    }
                    TextView textView2 = this.o;
                    if (textView2 != null) {
                        setMeasuredDimension(size, f.a(i4, textView2.getMeasuredHeight()) + this.f998i + this.f999j);
                    } else {
                        i.d("titleView");
                        throw null;
                    }
                } else {
                    i.d("iconView");
                    throw null;
                }
            } else {
                i.d("titleView");
                throw null;
            }
        } else {
            i.d("iconView");
            throw null;
        }
    }

    public final void setIconView$core(ImageView imageView) {
        i.b(imageView, "<set-?>");
        this.n = imageView;
    }

    public final void setTitleView$core(TextView textView) {
        i.b(textView, "<set-?>");
        this.o = textView;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DialogTitleLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        i.b(context, "context");
        this.f998i = e.a.a(this, R$dimen.md_dialog_frame_margin_vertical);
        this.f999j = e.a.a(this, R$dimen.md_dialog_title_layout_margin_bottom);
        this.k = e.a.a(this, R$dimen.md_dialog_frame_margin_horizontal);
        this.l = e.a.a(this, R$dimen.md_icon_margin);
        this.m = e.a.a(this, R$dimen.md_icon_size);
    }
}
