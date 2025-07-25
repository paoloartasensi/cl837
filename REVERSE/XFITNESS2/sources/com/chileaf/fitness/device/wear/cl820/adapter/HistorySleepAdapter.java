package com.chileaf.fitness.device.wear.cl820.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chileaf.fitness.R$id;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.config.a;
import com.chileaf.fitness.device.wear.cl820.model.HistoryOfSleep;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HistorySleepAdapter extends BaseQuickAdapter<HistoryOfSleep, BaseViewHolder> {
    private SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public HistorySleepAdapter() {
        super(R$layout.item_history, new ArrayList());
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void convert(BaseViewHolder baseViewHolder, HistoryOfSleep historyOfSleep) {
        StringBuilder sb = new StringBuilder();
        String format = this.a.format(new Date(historyOfSleep.startTime));
        String format2 = this.a.format(new Date(historyOfSleep.endTime));
        int i2 = historyOfSleep.sorberSleep;
        int i3 = historyOfSleep.lightSleep;
        int i4 = historyOfSleep.deepSleep;
        sb.append(a.a(R$string.start_time_colon));
        sb.append(format);
        sb.append("\n");
        sb.append(a.a(R$string.end_time_colon));
        sb.append(format2);
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
