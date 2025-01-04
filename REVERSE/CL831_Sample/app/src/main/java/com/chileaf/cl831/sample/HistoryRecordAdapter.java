package com.chileaf.cl831.sample;

import android.widget.TextView;

import com.android.chileaf.model.HistoryOfRecord;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HistoryRecordAdapter extends BaseQuickAdapter<HistoryOfRecord, BaseViewHolder> {

    private SimpleDateFormat mDateFormat;

    public HistoryRecordAdapter() {
        super(R.layout.item_history, new ArrayList<>());
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryOfRecord history) {
        String date = mDateFormat.format(new Date(history.record));
        TextView tvHistory = helper.getView(R.id.tv_history);
        tvHistory.setTextSize(20);
        tvHistory.setText(date);
    }
}
