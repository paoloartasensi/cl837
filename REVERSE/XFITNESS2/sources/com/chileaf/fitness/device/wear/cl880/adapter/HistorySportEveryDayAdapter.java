package com.chileaf.fitness.device.wear.cl880.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chileaf.fitness.R$id;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.config.a;
import com.chileaf.fitness.device.wear.cl880.model.SportDayHistory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistorySportEveryDayAdapter extends BaseQuickAdapter<SportDayHistory, BaseViewHolder> {
    private SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public HistorySportEveryDayAdapter(List<SportDayHistory> list) {
        super(R$layout.item_history, list);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void convert(BaseViewHolder baseViewHolder, SportDayHistory sportDayHistory) {
        StringBuilder sb = new StringBuilder();
        String format = this.a.format(new Date(sportDayHistory.stamp));
        sb.append(a.a(R$string.time_colon));
        sb.append(format);
        sb.append("\n");
        sb.append(a.a(R$string.step_colon));
        sb.append(sportDayHistory.step);
        sb.append("\n");
        sb.append(a.a(R$string.calorie_colon));
        sb.append(String.format("%.1f", new Object[]{Float.valueOf(((float) sportDayHistory.calorie) / 10.0f)}));
        sb.append("CAL");
        baseViewHolder.setText((int) R$id.tv_history, (CharSequence) sb.toString());
    }
}
