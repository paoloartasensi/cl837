package com.chileaf.fitness.device.wear.cl880.adapter;

import android.widget.CompoundButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chileaf.fitness.R$id;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.device.wear.cl880.model.AlarmConfig;
import com.chileaf.fitness.widget.SwitchButton;
import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends BaseQuickAdapter<AlarmConfig, BaseViewHolder> {
    private a a;

    public interface a {
        void a();
    }

    public AlarmAdapter() {
        super(R$layout.item_alarm, new ArrayList());
    }

    public void a(a aVar) {
        this.a = aVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void convert(BaseViewHolder baseViewHolder, AlarmConfig alarmConfig) {
        baseViewHolder.setText((int) R$id.alarm_item_time, (CharSequence) a(alarmConfig.hour) + ":" + a(alarmConfig.minute));
        baseViewHolder.setText((int) R$id.alarm_name, (CharSequence) alarmConfig.content);
        baseViewHolder.setChecked(R$id.alarm_item_monday, alarmConfig.monday);
        baseViewHolder.setChecked(R$id.alarm_item_tuesday, alarmConfig.tuesday);
        baseViewHolder.setChecked(R$id.alarm_item_wednesday, alarmConfig.wednesday);
        baseViewHolder.setChecked(R$id.alarm_item_thursday, alarmConfig.thursday);
        baseViewHolder.setChecked(R$id.alarm_item_friday, alarmConfig.friday);
        baseViewHolder.setChecked(R$id.alarm_item_saturday, alarmConfig.saturday);
        baseViewHolder.setChecked(R$id.alarm_item_sunday, alarmConfig.sunday);
        baseViewHolder.setVisible(R$id.alarm_single, alarmConfig.single);
        SwitchButton switchButton = (SwitchButton) baseViewHolder.getView(R$id.alarm_item_toggle);
        switchButton.setChecked(alarmConfig.enable);
        switchButton.setOnCheckedChangeListener(new a(this, alarmConfig));
        alarmConfig.index = baseViewHolder.getAdapterPosition();
    }

    public /* synthetic */ void a(AlarmConfig alarmConfig, CompoundButton compoundButton, boolean z) {
        alarmConfig.enable = z;
        a aVar = this.a;
        if (aVar != null) {
            aVar.a();
        }
    }

    public void a(AlarmConfig alarmConfig) {
        List data = getData();
        for (int i2 = 0; i2 < data.size(); i2++) {
            if (alarmConfig.index == ((AlarmConfig) data.get(i2)).index) {
                data.set(i2, alarmConfig);
            }
        }
        notifyDataSetChanged();
    }

    private String a(int i2) {
        if (i2 >= 10) {
            return String.valueOf(i2);
        }
        return "0" + i2;
    }
}
