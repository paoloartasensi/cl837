package com.chileaf.fitness.device.wear.cl831.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chileaf.fitness.R$id;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.device.wear.cl831.model.IntervalStep;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class IntervalStepAdapter extends BaseQuickAdapter<IntervalStep, BaseViewHolder> {
    private SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public IntervalStepAdapter() {
        super(R$layout.item_history, new ArrayList());
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void convert(BaseViewHolder baseViewHolder, IntervalStep intervalStep) {
        StringBuilder sb = new StringBuilder();
        String format = this.a.format(new Date(intervalStep.e));
        sb.append("Date time : ");
        sb.append(format);
        sb.append("\n");
        sb.append("Steps : ");
        sb.append(intervalStep.f1194f);
        sb.append("\n");
        baseViewHolder.setText((int) R$id.tv_history, (CharSequence) sb.toString());
    }
}
