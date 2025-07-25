package com.chileaf.fitness.device.wear.cl820.adapter;

import android.widget.TextView;
import com.android.chileaf.fitness.model.HistoryOfRecord;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chileaf.fitness.R$id;
import com.chileaf.fitness.R$layout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HistoryRecordAdapter extends BaseQuickAdapter<HistoryOfRecord, BaseViewHolder> {
    private SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public HistoryRecordAdapter() {
        super(R$layout.item_history, new ArrayList());
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void convert(BaseViewHolder baseViewHolder, HistoryOfRecord historyOfRecord) {
        String format = this.a.format(new Date(historyOfRecord.f1142f));
        TextView textView = (TextView) baseViewHolder.getView(R$id.tv_history);
        textView.setTextSize(20.0f);
        textView.setText(format);
    }
}
