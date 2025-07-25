package com.chileaf.cl831.sample;

import com.android.chileaf.model.IntervalStep;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class IntervalStepAdapter extends BaseQuickAdapter<IntervalStep, BaseViewHolder> {

    private SimpleDateFormat mDateFormat;

    public IntervalStepAdapter() {
        super(R.layout.item_history, new ArrayList<>());
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    @Override
    protected void convert(BaseViewHolder helper, IntervalStep interval) {
        StringBuilder item = new StringBuilder();
        String date = mDateFormat.format(new Date(interval.stamp));
        item.append("Date time : ").append(date).append("\n")
                .append("Steps : ").append(interval.steps).append("\n");
        helper.setText(R.id.tv_history, item.toString());
    }

}
