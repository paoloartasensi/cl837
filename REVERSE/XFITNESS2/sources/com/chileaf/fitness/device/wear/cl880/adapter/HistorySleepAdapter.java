package com.chileaf.fitness.device.wear.cl880.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chileaf.fitness.R$id;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.config.a;
import com.chileaf.fitness.device.wear.cl880.model.SleepHistory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistorySleepAdapter extends BaseQuickAdapter<SleepHistory, BaseViewHolder> {
    private SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public HistorySleepAdapter(List<SleepHistory> list) {
        super(R$layout.item_history, list);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void convert(BaseViewHolder baseViewHolder, SleepHistory sleepHistory) {
        StringBuilder sb = new StringBuilder();
        String format = this.a.format(new Date(sleepHistory.stamp));
        int i2 = sleepHistory.wakeSleep;
        int i3 = sleepHistory.lightSleep;
        int i4 = sleepHistory.deepSleep;
        sb.append(a.a(R$string.time_colon));
        sb.append(format);
        sb.append("\n");
        sb.append(a.a(R$string.sorber_sleep_colon));
        sb.append(i2 / 60);
        sb.append(a.a(R$string.hour_unit));
        sb.append(i2 % 60);
        sb.append(a.a(R$string.minute_unit));
        sb.append("\n");
        sb.append(a.a(R$string.light_sleep_colon));
        sb.append(i3 / 60);
        sb.append(a.a(R$string.hour_unit));
        sb.append(i3 % 60);
        sb.append(a.a(R$string.minute_unit));
        sb.append("\n");
        sb.append(a.a(R$string.deep_sleep_colon));
        sb.append(i4 / 60);
        sb.append(a.a(R$string.hour_unit));
        sb.append(i4 % 60);
        sb.append(a.a(R$string.minute_unit));
        sb.append("\n");
        baseViewHolder.setText((int) R$id.tv_history, (CharSequence) sb.toString());
    }
}
