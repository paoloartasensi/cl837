package com.chileaf.fitness.device.wear.cl831;

import java.text.SimpleDateFormat;
import java.util.Locale;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: CL831HistoryDetailActivity.kt */
final class CL831HistoryDetailActivity$mDateFormat$2 extends Lambda implements a<SimpleDateFormat> {
    public static final CL831HistoryDetailActivity$mDateFormat$2 INSTANCE = new CL831HistoryDetailActivity$mDateFormat$2();

    CL831HistoryDetailActivity$mDateFormat$2() {
        super(0);
    }

    public final SimpleDateFormat invoke() {
        return new SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault());
    }
}
