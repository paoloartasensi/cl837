package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$id;
import androidx.appcompat.R$layout;
import androidx.appcompat.R$styleable;
import androidx.appcompat.view.menu.n;
import androidx.appcompat.widget.g0;
import androidx.core.h.v;

public class ListMenuItemView extends LinearLayout implements n.a, AbsListView.SelectionBoundsAdjuster {
    private i e;

    /* renamed from: f  reason: collision with root package name */
    private ImageView f116f;

    /* renamed from: g  reason: collision with root package name */
    private RadioButton f117g;

    /* renamed from: h  reason: collision with root package name */
    private TextView f118h;

    /* renamed from: i  reason: collision with root package name */
    private CheckBox f119i;

    /* renamed from: j  reason: collision with root package name */
    private TextView f120j;
    private ImageView k;
    private ImageView l;
    private LinearLayout m;
    private Drawable n;
    private int o;
    private Context p;
    private boolean q;
    private Drawable r;
    private boolean s;
    private LayoutInflater t;
    private boolean u;

    public ListMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.listMenuViewStyle);
    }

    private void b() {
        ImageView imageView = (ImageView) getInflater().inflate(R$layout.abc_list_menu_item_icon, this, false);
        this.f116f = imageView;
        a((View) imageView, 0);
    }

    private void d() {
        RadioButton radioButton = (RadioButton) getInflater().inflate(R$layout.abc_list_menu_item_radio, this, false);
        this.f117g = radioButton;
        a(radioButton);
    }

    private LayoutInflater getInflater() {
        if (this.t == null) {
            this.t = LayoutInflater.from(getContext());
        }
        return this.t;
    }

    private void setSubMenuArrowVisible(boolean z) {
        ImageView imageView = this.k;
        if (imageView != null) {
            imageView.setVisibility(z ? 0 : 8);
        }
    }

    public void a(i iVar, int i2) {
        this.e = iVar;
        setVisibility(iVar.isVisible() ? 0 : 8);
        setTitle(iVar.a((n.a) this));
        setCheckable(iVar.isCheckable());
        a(iVar.m(), iVar.d());
        setIcon(iVar.getIcon());
        setEnabled(iVar.isEnabled());
        setSubMenuArrowVisible(iVar.hasSubMenu());
        setContentDescription(iVar.getContentDescription());
    }

    public void adjustListItemSelectionBounds(Rect rect) {
        ImageView imageView = this.l;
        if (imageView != null && imageView.getVisibility() == 0) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.l.getLayoutParams();
            rect.top += this.l.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
        }
    }

    public boolean c() {
        return false;
    }

    public i getItemData() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        v.a((View) this, this.n);
        TextView textView = (TextView) findViewById(R$id.title);
        this.f118h = textView;
        int i2 = this.o;
        if (i2 != -1) {
            textView.setTextAppearance(this.p, i2);
        }
        this.f120j = (TextView) findViewById(R$id.shortcut);
        ImageView imageView = (ImageView) findViewById(R$id.submenuarrow);
        this.k = imageView;
        if (imageView != null) {
            imageView.setImageDrawable(this.r);
        }
        this.l = (ImageView) findViewById(R$id.group_divider);
        this.m = (LinearLayout) findViewById(R$id.content);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        if (this.f116f != null && this.q) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.f116f.getLayoutParams();
            int i4 = layoutParams.height;
            if (i4 > 0 && layoutParams2.width <= 0) {
                layoutParams2.width = i4;
            }
        }
        super.onMeasure(i2, i3);
    }

    public void setCheckable(boolean z) {
        CompoundButton compoundButton;
        CompoundButton compoundButton2;
        if (z || this.f117g != null || this.f119i != null) {
            if (this.e.i()) {
                if (this.f117g == null) {
                    d();
                }
                compoundButton2 = this.f117g;
                compoundButton = this.f119i;
            } else {
                if (this.f119i == null) {
                    a();
                }
                compoundButton2 = this.f119i;
                compoundButton = this.f117g;
            }
            if (z) {
                compoundButton2.setChecked(this.e.isChecked());
                if (compoundButton2.getVisibility() != 0) {
                    compoundButton2.setVisibility(0);
                }
                if (compoundButton != null && compoundButton.getVisibility() != 8) {
                    compoundButton.setVisibility(8);
                    return;
                }
                return;
            }
            CheckBox checkBox = this.f119i;
            if (checkBox != null) {
                checkBox.setVisibility(8);
            }
            RadioButton radioButton = this.f117g;
            if (radioButton != null) {
                radioButton.setVisibility(8);
            }
        }
    }

    public void setChecked(boolean z) {
        CompoundButton compoundButton;
        if (this.e.i()) {
            if (this.f117g == null) {
                d();
            }
            compoundButton = this.f117g;
        } else {
            if (this.f119i == null) {
                a();
            }
            compoundButton = this.f119i;
        }
        compoundButton.setChecked(z);
    }

    public void setForceShowIcon(boolean z) {
        this.u = z;
        this.q = z;
    }

    public void setGroupDividerEnabled(boolean z) {
        ImageView imageView = this.l;
        if (imageView != null) {
            imageView.setVisibility((this.s || !z) ? 8 : 0);
        }
    }

    public void setIcon(Drawable drawable) {
        boolean z = this.e.l() || this.u;
        if (!z && !this.q) {
            return;
        }
        if (this.f116f != null || drawable != null || this.q) {
            if (this.f116f == null) {
                b();
            }
            if (drawable != null || this.q) {
                ImageView imageView = this.f116f;
                if (!z) {
                    drawable = null;
                }
                imageView.setImageDrawable(drawable);
                if (this.f116f.getVisibility() != 0) {
                    this.f116f.setVisibility(0);
                    return;
                }
                return;
            }
            this.f116f.setVisibility(8);
        }
    }

    public void setTitle(CharSequence charSequence) {
        if (charSequence != null) {
            this.f118h.setText(charSequence);
            if (this.f118h.getVisibility() != 0) {
                this.f118h.setVisibility(0);
            }
        } else if (this.f118h.getVisibility() != 8) {
            this.f118h.setVisibility(8);
        }
    }

    public ListMenuItemView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet);
        g0 a = g0.a(getContext(), attributeSet, R$styleable.MenuView, i2, 0);
        this.n = a.b(R$styleable.MenuView_android_itemBackground);
        this.o = a.g(R$styleable.MenuView_android_itemTextAppearance, -1);
        this.q = a.a(R$styleable.MenuView_preserveIconSpacing, false);
        this.p = context;
        this.r = a.b(R$styleable.MenuView_subMenuArrow);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes((AttributeSet) null, new int[]{16843049}, R$attr.dropDownListViewStyle, 0);
        this.s = obtainStyledAttributes.hasValue(0);
        a.a();
        obtainStyledAttributes.recycle();
    }

    private void a(View view) {
        a(view, -1);
    }

    private void a(View view, int i2) {
        LinearLayout linearLayout = this.m;
        if (linearLayout != null) {
            linearLayout.addView(view, i2);
        } else {
            addView(view, i2);
        }
    }

    public void a(boolean z, char c) {
        int i2 = (!z || !this.e.m()) ? 8 : 0;
        if (i2 == 0) {
            this.f120j.setText(this.e.e());
        }
        if (this.f120j.getVisibility() != i2) {
            this.f120j.setVisibility(i2);
        }
    }

    private void a() {
        CheckBox checkBox = (CheckBox) getInflater().inflate(R$layout.abc_list_menu_item_checkbox, this, false);
        this.f119i = checkBox;
        a(checkBox);
    }
}
