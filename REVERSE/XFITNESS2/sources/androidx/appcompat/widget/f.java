package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$color;
import androidx.appcompat.R$drawable;
import androidx.appcompat.widget.x;

/* compiled from: AppCompatDrawableManager */
public final class f {
    /* access modifiers changed from: private */
    public static final PorterDuff.Mode b = PorterDuff.Mode.SRC_IN;
    private static f c;
    private x a;

    /* compiled from: AppCompatDrawableManager */
    static class a implements x.e {
        private final int[] a = {R$drawable.abc_textfield_search_default_mtrl_alpha, R$drawable.abc_textfield_default_mtrl_alpha, R$drawable.abc_ab_share_pack_mtrl_alpha};
        private final int[] b = {R$drawable.abc_ic_commit_search_api_mtrl_alpha, R$drawable.abc_seekbar_tick_mark_material, R$drawable.abc_ic_menu_share_mtrl_alpha, R$drawable.abc_ic_menu_copy_mtrl_am_alpha, R$drawable.abc_ic_menu_cut_mtrl_alpha, R$drawable.abc_ic_menu_selectall_mtrl_alpha, R$drawable.abc_ic_menu_paste_mtrl_am_alpha};
        private final int[] c = {R$drawable.abc_textfield_activated_mtrl_alpha, R$drawable.abc_textfield_search_activated_mtrl_alpha, R$drawable.abc_cab_background_top_mtrl_alpha, R$drawable.abc_text_cursor_material, R$drawable.abc_text_select_handle_left_mtrl_dark, R$drawable.abc_text_select_handle_middle_mtrl_dark, R$drawable.abc_text_select_handle_right_mtrl_dark, R$drawable.abc_text_select_handle_left_mtrl_light, R$drawable.abc_text_select_handle_middle_mtrl_light, R$drawable.abc_text_select_handle_right_mtrl_light};
        private final int[] d = {R$drawable.abc_popup_background_mtrl_mult, R$drawable.abc_cab_background_internal_bg, R$drawable.abc_menu_hardkey_panel_mtrl_mult};
        private final int[] e = {R$drawable.abc_tab_indicator_material, R$drawable.abc_textfield_search_material};

        /* renamed from: f  reason: collision with root package name */
        private final int[] f287f = {R$drawable.abc_btn_check_material, R$drawable.abc_btn_radio_material, R$drawable.abc_btn_check_material_anim, R$drawable.abc_btn_radio_material_anim};

        a() {
        }

        private ColorStateList a(Context context) {
            return b(context, 0);
        }

        private ColorStateList b(Context context) {
            return b(context, b0.b(context, R$attr.colorAccent));
        }

        private ColorStateList c(Context context) {
            return b(context, b0.b(context, R$attr.colorButtonNormal));
        }

        private ColorStateList d(Context context) {
            int[][] iArr = new int[3][];
            int[] iArr2 = new int[3];
            ColorStateList c2 = b0.c(context, R$attr.colorSwitchThumbNormal);
            if (c2 == null || !c2.isStateful()) {
                iArr[0] = b0.b;
                iArr2[0] = b0.a(context, R$attr.colorSwitchThumbNormal);
                iArr[1] = b0.e;
                iArr2[1] = b0.b(context, R$attr.colorControlActivated);
                iArr[2] = b0.f277f;
                iArr2[2] = b0.b(context, R$attr.colorSwitchThumbNormal);
            } else {
                iArr[0] = b0.b;
                iArr2[0] = c2.getColorForState(iArr[0], 0);
                iArr[1] = b0.e;
                iArr2[1] = b0.b(context, R$attr.colorControlActivated);
                iArr[2] = b0.f277f;
                iArr2[2] = c2.getDefaultColor();
            }
            return new ColorStateList(iArr, iArr2);
        }

        public Drawable a(x xVar, Context context, int i2) {
            if (i2 != R$drawable.abc_cab_background_top_material) {
                return null;
            }
            return new LayerDrawable(new Drawable[]{xVar.a(context, R$drawable.abc_cab_background_internal_bg), xVar.a(context, R$drawable.abc_cab_background_top_mtrl_alpha)});
        }

        private ColorStateList b(Context context, int i2) {
            int b2 = b0.b(context, R$attr.colorControlHighlight);
            int a2 = b0.a(context, R$attr.colorButtonNormal);
            return new ColorStateList(new int[][]{b0.b, b0.d, b0.c, b0.f277f}, new int[]{a2, androidx.core.a.a.b(b2, i2), androidx.core.a.a.b(b2, i2), i2});
        }

        private void a(Drawable drawable, int i2, PorterDuff.Mode mode) {
            if (q.a(drawable)) {
                drawable = drawable.mutate();
            }
            if (mode == null) {
                mode = f.b;
            }
            drawable.setColorFilter(f.a(i2, mode));
        }

        private boolean a(int[] iArr, int i2) {
            for (int i3 : iArr) {
                if (i3 == i2) {
                    return true;
                }
            }
            return false;
        }

        public ColorStateList a(Context context, int i2) {
            if (i2 == R$drawable.abc_edit_text_material) {
                return androidx.appcompat.a.a.a.b(context, R$color.abc_tint_edittext);
            }
            if (i2 == R$drawable.abc_switch_track_mtrl_alpha) {
                return androidx.appcompat.a.a.a.b(context, R$color.abc_tint_switch_track);
            }
            if (i2 == R$drawable.abc_switch_thumb_material) {
                return d(context);
            }
            if (i2 == R$drawable.abc_btn_default_mtrl_shape) {
                return c(context);
            }
            if (i2 == R$drawable.abc_btn_borderless_material) {
                return a(context);
            }
            if (i2 == R$drawable.abc_btn_colored_material) {
                return b(context);
            }
            if (i2 == R$drawable.abc_spinner_mtrl_am_alpha || i2 == R$drawable.abc_spinner_textfield_background_material) {
                return androidx.appcompat.a.a.a.b(context, R$color.abc_tint_spinner);
            }
            if (a(this.b, i2)) {
                return b0.c(context, R$attr.colorControlNormal);
            }
            if (a(this.e, i2)) {
                return androidx.appcompat.a.a.a.b(context, R$color.abc_tint_default);
            }
            if (a(this.f287f, i2)) {
                return androidx.appcompat.a.a.a.b(context, R$color.abc_tint_btn_checkable);
            }
            if (i2 == R$drawable.abc_seekbar_thumb_material) {
                return androidx.appcompat.a.a.a.b(context, R$color.abc_tint_seek_thumb);
            }
            return null;
        }

        public boolean b(Context context, int i2, Drawable drawable) {
            if (i2 == R$drawable.abc_seekbar_track_material) {
                LayerDrawable layerDrawable = (LayerDrawable) drawable;
                a(layerDrawable.findDrawableByLayerId(16908288), b0.b(context, R$attr.colorControlNormal), f.b);
                a(layerDrawable.findDrawableByLayerId(16908303), b0.b(context, R$attr.colorControlNormal), f.b);
                a(layerDrawable.findDrawableByLayerId(16908301), b0.b(context, R$attr.colorControlActivated), f.b);
                return true;
            } else if (i2 != R$drawable.abc_ratingbar_material && i2 != R$drawable.abc_ratingbar_indicator_material && i2 != R$drawable.abc_ratingbar_small_material) {
                return false;
            } else {
                LayerDrawable layerDrawable2 = (LayerDrawable) drawable;
                a(layerDrawable2.findDrawableByLayerId(16908288), b0.a(context, R$attr.colorControlNormal), f.b);
                a(layerDrawable2.findDrawableByLayerId(16908303), b0.b(context, R$attr.colorControlActivated), f.b);
                a(layerDrawable2.findDrawableByLayerId(16908301), b0.b(context, R$attr.colorControlActivated), f.b);
                return true;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:18:0x0046  */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x0061 A[RETURN] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean a(android.content.Context r7, int r8, android.graphics.drawable.Drawable r9) {
            /*
                r6 = this;
                android.graphics.PorterDuff$Mode r0 = androidx.appcompat.widget.f.b
                int[] r1 = r6.a
                boolean r1 = r6.a((int[]) r1, (int) r8)
                r2 = 16842801(0x1010031, float:2.3693695E-38)
                r3 = -1
                r4 = 0
                r5 = 1
                if (r1 == 0) goto L_0x0017
                int r2 = androidx.appcompat.R$attr.colorControlNormal
            L_0x0014:
                r8 = -1
            L_0x0015:
                r1 = 1
                goto L_0x0044
            L_0x0017:
                int[] r1 = r6.c
                boolean r1 = r6.a((int[]) r1, (int) r8)
                if (r1 == 0) goto L_0x0022
                int r2 = androidx.appcompat.R$attr.colorControlActivated
                goto L_0x0014
            L_0x0022:
                int[] r1 = r6.d
                boolean r1 = r6.a((int[]) r1, (int) r8)
                if (r1 == 0) goto L_0x002d
                android.graphics.PorterDuff$Mode r0 = android.graphics.PorterDuff.Mode.MULTIPLY
                goto L_0x0014
            L_0x002d:
                int r1 = androidx.appcompat.R$drawable.abc_list_divider_mtrl_alpha
                if (r8 != r1) goto L_0x003c
                r2 = 16842800(0x1010030, float:2.3693693E-38)
                r8 = 1109603123(0x42233333, float:40.8)
                int r8 = java.lang.Math.round(r8)
                goto L_0x0015
            L_0x003c:
                int r1 = androidx.appcompat.R$drawable.abc_dialog_material_background
                if (r8 != r1) goto L_0x0041
                goto L_0x0014
            L_0x0041:
                r8 = -1
                r1 = 0
                r2 = 0
            L_0x0044:
                if (r1 == 0) goto L_0x0061
                boolean r1 = androidx.appcompat.widget.q.a(r9)
                if (r1 == 0) goto L_0x0050
                android.graphics.drawable.Drawable r9 = r9.mutate()
            L_0x0050:
                int r7 = androidx.appcompat.widget.b0.b(r7, r2)
                android.graphics.PorterDuffColorFilter r7 = androidx.appcompat.widget.f.a((int) r7, (android.graphics.PorterDuff.Mode) r0)
                r9.setColorFilter(r7)
                if (r8 == r3) goto L_0x0060
                r9.setAlpha(r8)
            L_0x0060:
                return r5
            L_0x0061:
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.f.a.a(android.content.Context, int, android.graphics.drawable.Drawable):boolean");
        }

        public PorterDuff.Mode a(int i2) {
            if (i2 == R$drawable.abc_switch_thumb_material) {
                return PorterDuff.Mode.MULTIPLY;
            }
            return null;
        }
    }

    public static synchronized f b() {
        f fVar;
        synchronized (f.class) {
            if (c == null) {
                c();
            }
            fVar = c;
        }
        return fVar;
    }

    public static synchronized void c() {
        synchronized (f.class) {
            if (c == null) {
                f fVar = new f();
                c = fVar;
                fVar.a = x.a();
                c.a.a((x.e) new a());
            }
        }
    }

    public synchronized Drawable a(Context context, int i2) {
        return this.a.a(context, i2);
    }

    /* access modifiers changed from: package-private */
    public synchronized Drawable a(Context context, int i2, boolean z) {
        return this.a.a(context, i2, z);
    }

    public synchronized void a(Context context) {
        this.a.a(context);
    }

    /* access modifiers changed from: package-private */
    public synchronized ColorStateList b(Context context, int i2) {
        return this.a.b(context, i2);
    }

    static void a(Drawable drawable, e0 e0Var, int[] iArr) {
        x.a(drawable, e0Var, iArr);
    }

    public static synchronized PorterDuffColorFilter a(int i2, PorterDuff.Mode mode) {
        PorterDuffColorFilter a2;
        synchronized (f.class) {
            a2 = x.a(i2, mode);
        }
        return a2;
    }
}
