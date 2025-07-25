package com.chileaf.cl831.sample;

import com.android.chileaf.model.HistoryOfSport;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HistorySportAdapter extends BaseQuickAdapter<HistoryOfSport, BaseViewHolder> {

    private SimpleDateFormat mDateFormat;

    public HistorySportAdapter() {
        super(R.layout.item_history, new ArrayList<>());
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryOfSport history) {
        StringBuilder item = new StringBuilder();
        String date = mDateFormat.format(new Date(history.startTime));
        item.append("Date time:").append(date).append("\n")
                .append("Step:").append(history.step).append("步\n")
                .append("Calorie:").append(String.format("%.1f", history.calorie / 10f)).append("CAL");
        helper.setText(R.id.tv_history, item.toString());
    }

}
