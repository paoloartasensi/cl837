package com.chileaf.fitness;

import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.d;
import androidx.databinding.f;
import com.chileaf.fitness.b.b;
import com.chileaf.fitness.b.b0;
import com.chileaf.fitness.b.b1;
import com.chileaf.fitness.b.d0;
import com.chileaf.fitness.b.d1;
import com.chileaf.fitness.b.f0;
import com.chileaf.fitness.b.f1;
import com.chileaf.fitness.b.h;
import com.chileaf.fitness.b.h0;
import com.chileaf.fitness.b.h1;
import com.chileaf.fitness.b.j;
import com.chileaf.fitness.b.j0;
import com.chileaf.fitness.b.j1;
import com.chileaf.fitness.b.l;
import com.chileaf.fitness.b.l0;
import com.chileaf.fitness.b.l1;
import com.chileaf.fitness.b.n;
import com.chileaf.fitness.b.n0;
import com.chileaf.fitness.b.n1;
import com.chileaf.fitness.b.p;
import com.chileaf.fitness.b.p0;
import com.chileaf.fitness.b.p1;
import com.chileaf.fitness.b.r;
import com.chileaf.fitness.b.r0;
import com.chileaf.fitness.b.r1;
import com.chileaf.fitness.b.t;
import com.chileaf.fitness.b.t0;
import com.chileaf.fitness.b.t1;
import com.chileaf.fitness.b.v;
import com.chileaf.fitness.b.v0;
import com.chileaf.fitness.b.v1;
import com.chileaf.fitness.b.x;
import com.chileaf.fitness.b.x0;
import com.chileaf.fitness.b.x1;
import com.chileaf.fitness.b.z;
import com.chileaf.fitness.b.z0;
import com.chileaf.fitness.b.z1;
import java.util.ArrayList;
import java.util.List;

/* compiled from: DataBinderMapperImpl */
public class a extends d {
    private static final SparseIntArray a;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray(39);
        a = sparseIntArray;
        sparseIntArray.put(R$layout.activity_alarm_edit, 1);
        a.put(R$layout.activity_alarm_setting, 2);
        a.put(R$layout.activity_boxing, 3);
        a.put(R$layout.activity_cdn, 4);
        a.put(R$layout.activity_cl800, 5);
        a.put(R$layout.activity_cl820, 6);
        a.put(R$layout.activity_cl830, 7);
        a.put(R$layout.activity_cl831, 8);
        a.put(R$layout.activity_cl880, 9);
        a.put(R$layout.activity_cl880_sleep, 10);
        a.put(R$layout.activity_cl880_sport, 11);
        a.put(R$layout.activity_device_settings, 12);
        a.put(R$layout.activity_devices, 13);
        a.put(R$layout.activity_history, 14);
        a.put(R$layout.activity_history_detail, 15);
        a.put(R$layout.activity_main, 16);
        a.put(R$layout.activity_splash, 17);
        a.put(R$layout.activity_web, 18);
        a.put(R$layout.activity_weight, 19);
        a.put(R$layout.dialog_single_time, 20);
        a.put(R$layout.fragment_about, 21);
        a.put(R$layout.fragment_boxing, 22);
        a.put(R$layout.fragment_cl880_setting, 23);
        a.put(R$layout.fragment_health_setting, 24);
        a.put(R$layout.fragment_main, 25);
        a.put(R$layout.fragment_riding, 26);
        a.put(R$layout.fragment_riding_product, 27);
        a.put(R$layout.fragment_setting, 28);
        a.put(R$layout.fragment_sleep_setting, 29);
        a.put(R$layout.fragment_train_setting, 30);
        a.put(R$layout.fragment_wear, 31);
        a.put(R$layout.fragment_wear_product, 32);
        a.put(R$layout.fragment_weigh, 33);
        a.put(R$layout.info_no_bluetooth, 34);
        a.put(R$layout.info_no_devices, 35);
        a.put(R$layout.info_no_permission, 36);
        a.put(R$layout.item_device, 37);
        a.put(R$layout.layout_tab_text, 38);
        a.put(R$layout.layout_toast, 39);
    }

    public ViewDataBinding a(f fVar, View view, int i2) {
        int i3 = a.get(i2);
        if (i3 <= 0) {
            return null;
        }
        Object tag = view.getTag();
        if (tag != null) {
            switch (i3) {
                case 1:
                    if ("layout/activity_alarm_edit_0".equals(tag)) {
                        return new b(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_alarm_edit is invalid. Received: " + tag);
                case 2:
                    if ("layout/activity_alarm_setting_0".equals(tag)) {
                        return new com.chileaf.fitness.b.d(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_alarm_setting is invalid. Received: " + tag);
                case 3:
                    if ("layout/activity_boxing_0".equals(tag)) {
                        return new com.chileaf.fitness.b.f(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_boxing is invalid. Received: " + tag);
                case 4:
                    if ("layout/activity_cdn_0".equals(tag)) {
                        return new h(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_cdn is invalid. Received: " + tag);
                case 5:
                    if ("layout/activity_cl800_0".equals(tag)) {
                        return new j(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_cl800 is invalid. Received: " + tag);
                case 6:
                    if ("layout/activity_cl820_0".equals(tag)) {
                        return new l(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_cl820 is invalid. Received: " + tag);
                case 7:
                    if ("layout/activity_cl830_0".equals(tag)) {
                        return new n(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_cl830 is invalid. Received: " + tag);
                case 8:
                    if ("layout/activity_cl831_0".equals(tag)) {
                        return new p(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_cl831 is invalid. Received: " + tag);
                case 9:
                    if ("layout/activity_cl880_0".equals(tag)) {
                        return new r(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_cl880 is invalid. Received: " + tag);
                case 10:
                    if ("layout/activity_cl880_sleep_0".equals(tag)) {
                        return new t(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_cl880_sleep is invalid. Received: " + tag);
                case 11:
                    if ("layout/activity_cl880_sport_0".equals(tag)) {
                        return new v(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_cl880_sport is invalid. Received: " + tag);
                case 12:
                    if ("layout/activity_device_settings_0".equals(tag)) {
                        return new x(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_device_settings is invalid. Received: " + tag);
                case 13:
                    if ("layout/activity_devices_0".equals(tag)) {
                        return new z(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_devices is invalid. Received: " + tag);
                case 14:
                    if ("layout/activity_history_0".equals(tag)) {
                        return new b0(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_history is invalid. Received: " + tag);
                case 15:
                    if ("layout/activity_history_detail_0".equals(tag)) {
                        return new d0(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_history_detail is invalid. Received: " + tag);
                case 16:
                    if ("layout/activity_main_0".equals(tag)) {
                        return new f0(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
                case 17:
                    if ("layout/activity_splash_0".equals(tag)) {
                        return new h0(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_splash is invalid. Received: " + tag);
                case 18:
                    if ("layout/activity_web_0".equals(tag)) {
                        return new j0(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_web is invalid. Received: " + tag);
                case 19:
                    if ("layout/activity_weight_0".equals(tag)) {
                        return new l0(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_weight is invalid. Received: " + tag);
                case 20:
                    if ("layout/dialog_single_time_0".equals(tag)) {
                        return new n0(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for dialog_single_time is invalid. Received: " + tag);
                case 21:
                    if ("layout/fragment_about_0".equals(tag)) {
                        return new p0(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_about is invalid. Received: " + tag);
                case 22:
                    if ("layout/fragment_boxing_0".equals(tag)) {
                        return new r0(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_boxing is invalid. Received: " + tag);
                case 23:
                    if ("layout/fragment_cl880_setting_0".equals(tag)) {
                        return new t0(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_cl880_setting is invalid. Received: " + tag);
                case 24:
                    if ("layout/fragment_health_setting_0".equals(tag)) {
                        return new v0(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_health_setting is invalid. Received: " + tag);
                case 25:
                    if ("layout/fragment_main_0".equals(tag)) {
                        return new x0(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_main is invalid. Received: " + tag);
                case 26:
                    if ("layout/fragment_riding_0".equals(tag)) {
                        return new z0(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_riding is invalid. Received: " + tag);
                case 27:
                    if ("layout/fragment_riding_product_0".equals(tag)) {
                        return new b1(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_riding_product is invalid. Received: " + tag);
                case 28:
                    if ("layout/fragment_setting_0".equals(tag)) {
                        return new d1(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_setting is invalid. Received: " + tag);
                case 29:
                    if ("layout/fragment_sleep_setting_0".equals(tag)) {
                        return new f1(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_sleep_setting is invalid. Received: " + tag);
                case 30:
                    if ("layout/fragment_train_setting_0".equals(tag)) {
                        return new h1(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_train_setting is invalid. Received: " + tag);
                case 31:
                    if ("layout/fragment_wear_0".equals(tag)) {
                        return new j1(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_wear is invalid. Received: " + tag);
                case 32:
                    if ("layout/fragment_wear_product_0".equals(tag)) {
                        return new l1(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_wear_product is invalid. Received: " + tag);
                case 33:
                    if ("layout/fragment_weigh_0".equals(tag)) {
                        return new n1(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_weigh is invalid. Received: " + tag);
                case 34:
                    if ("layout/info_no_bluetooth_0".equals(tag)) {
                        return new p1(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for info_no_bluetooth is invalid. Received: " + tag);
                case 35:
                    if ("layout/info_no_devices_0".equals(tag)) {
                        return new r1(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for info_no_devices is invalid. Received: " + tag);
                case 36:
                    if ("layout/info_no_permission_0".equals(tag)) {
                        return new t1(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for info_no_permission is invalid. Received: " + tag);
                case 37:
                    if ("layout/item_device_0".equals(tag)) {
                        return new v1(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for item_device is invalid. Received: " + tag);
                case 38:
                    if ("layout/layout_tab_text_0".equals(tag)) {
                        return new x1(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for layout_tab_text is invalid. Received: " + tag);
                case 39:
                    if ("layout/layout_toast_0".equals(tag)) {
                        return new z1(fVar, view);
                    }
                    throw new IllegalArgumentException("The tag for layout_toast is invalid. Received: " + tag);
                default:
                    return null;
            }
        } else {
            throw new RuntimeException("view must have a tag");
        }
    }

    public ViewDataBinding a(f fVar, View[] viewArr, int i2) {
        if (viewArr == null || viewArr.length == 0 || a.get(i2) <= 0 || viewArr[0].getTag() != null) {
            return null;
        }
        throw new RuntimeException("view must have a tag");
    }

    public List<d> a() {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(new androidx.databinding.library.baseAdapters.a());
        return arrayList;
    }
}
