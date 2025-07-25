package com.chileaf.fitness.device.wear.cl880.adapter;

import android.widget.CompoundButton;
import com.chileaf.fitness.device.wear.cl880.model.AlarmConfig;

/* compiled from: lambda */
public final /* synthetic */ class a implements CompoundButton.OnCheckedChangeListener {
    private final /* synthetic */ AlarmAdapter a;
    private final /* synthetic */ AlarmConfig b;

    public /* synthetic */ a(AlarmAdapter alarmAdapter, AlarmConfig alarmConfig) {
        this.a = alarmAdapter;
        this.b = alarmConfig;
    }

    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.a.a(this.b, compoundButton, z);
    }
}
