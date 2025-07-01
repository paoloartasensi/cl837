package com.chileaf.cl831.sample;

import com.android.chileaf.model.HistoryOf3D;
import com.android.chileaf.model.HistoryOfSport;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class History3DAdapter extends BaseQuickAdapter<HistoryOf3D, BaseViewHolder> {

    private SimpleDateFormat mDateFormat;

    public History3DAdapter() {
        super(R.layout.item_history, new ArrayList<>());
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryOf3D history) {
        StringBuilder item = new StringBuilder();
//        String date = mDateFormat.format(new Date(history.stamp));
        item
//                .append("Date time:").append(date).append("\n")
                .append("X,Y,Z:[ ")
                .append(history.accX).append(", ")
//                .append("AccY:")
                .append(history.accY).append(", ")
//                .append("AccZ:")
                .append(history.accZ).append(" ]");
        helper.setText(R.id.tv_history, item.toString());
    }

}
