package com.chileaf.cl831.sample;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;

import com.android.chileaf.model.HistoryOfHeartRate;
import com.android.chileaf.model.HistoryOfRespiratoryRate;
import com.android.chileaf.model.HistoryOfStep;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

/**
 * History detail
 */
public class HistoryDetailActivity extends BaseActivity {

    public static final String EXTRA_TYPE = "extra_type";
    public static final String EXTRA_STAMP = "extra_stamp";

    public static final int TYPE_HR = 0x03;
    public static final int TYPE_RR = 0x05;
    public static final int TYPE_STEP = 0x07;

    private LineChart mChart;
    private AppCompatTextView mTvHistory;
    private SimpleDateFormat mDateFormat;

    @Override
    protected int layoutId() {
        return R.layout.activity_record_detail;
    }

    @Override
    protected void initView() {
        mTvHistory = findViewById(R.id.tv_history);
        mChart = findViewById(R.id.chart_history);
        initChart();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showLoadingAutoDismiss(2000);
        int type = getIntent().getIntExtra(EXTRA_TYPE, 0);
        long stamp = getIntent().getLongExtra(EXTRA_STAMP, 0);
        mDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault());
        if (type == TYPE_HR) {
            setTitle("HR history detail");
            mManager.addHistoryOfHRDataCallback((device, heartRates) -> {
                runOnUiThread(() -> {
                    Timber.d("heartRates:%d %s", heartRates.size(), heartRates.toString());
                    updateHeartRates(heartRates);
                    hideLoading();
                });
            });
            mManager.getHistoryOfHRData(stamp);
//            mManager.addHistoryOfSingleRecordCallback((device, stamp1, step, distance, calorie) -> {
//                runOnUiThread(() -> {
//                    mTvHistory.setText("Step:" + step + "\ndistance: " + distance/ 100f + "m\ncalorie:" + calorie/10f + "kcal");
//                });
//            });
//            mManager.getHistoryOfSingleRecord(stamp);
        } else if (type == TYPE_RR) {
            setTitle("RR history detail");
            mManager.addHistoryOfRRDataCallback((device, respiratoryRates) -> {
                runOnUiThread(() -> {
                    Timber.d("respiratoryRates:%d %s", respiratoryRates.size(), respiratoryRates.toString());
                    updateRespiratoryRates(respiratoryRates);
                    hideLoading();
                });
            });
            mManager.getHistoryOfRRData(stamp);
        } else if (type == TYPE_STEP) {
            setTitle("Step history detail");
            mManager.addHistoryOfStepDataCallback((device, steps) -> {
                runOnUiThread(() -> {
                    Timber.d("steps:%d %s", steps.size(), steps.toString());
                    updateSteps(steps);
                    hideLoading();
                });
            });
            mManager.getHistoryOfStepData(stamp);
        }
    }

    private void initChart() {
        mChart.setNoDataText("");
        mChart.setTouchEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(false);
        mChart.getDescription().setEnabled(false);
        mChart.getLegend().setEnabled(true);
        mChart.setScaleYEnabled(false);
        mChart.setScaleXEnabled(true);
        mChart.setDragEnabled(true);

        mChart.getAxisLeft().setDrawGridLines(true);
        mChart.getAxisLeft().setDrawAxisLine(true);
        mChart.getAxisLeft().setEnabled(true);
        mChart.getAxisLeft().setAxisMinimum(0f);

        mChart.getAxisRight().setEnabled(false);
        mChart.getXAxis().setTextSize(8);
        mChart.getXAxis().setGranularity(1f);
        mChart.getXAxis().setDrawAxisLine(true);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
    }

    private void updateHeartRates(List<HistoryOfHeartRate> heartRates) {
        final List<Entry> values = new ArrayList<>();
        final List<String> stamps = new ArrayList<>();
        for (int i = 0; i < heartRates.size(); i++) {
            HistoryOfHeartRate history = heartRates.get(i);
            values.add(new Entry(i, history.heartRate));
            stamps.add(mDateFormat.format(new Date(history.stamp)));
        }
        mChart.resetTracking();
        LineDataSet dataSet = new LineDataSet(values, "Heart rate");
        dataSet.setValueTextSize(8);
        dataSet.setCircleRadius(1.5f);
        dataSet.setColor(Color.RED);
        dataSet.setFillColor(Color.RED);
        dataSet.setCircleColor(Color.RED);
        dataSet.setCircleHoleColor(Color.RED);
        dataSet.setValueTextColor(Color.RED);
        dataSet.setLineWidth(1f);
        dataSet.setDrawValues(true);
        dataSet.setDrawCircles(true);
        dataSet.setHighlightEnabled(false);
        dataSet.setMode(LineDataSet.Mode.LINEAR);
        dataSet.setDrawFilled(false);

        mChart.getXAxis().setValueFormatter(new StampValueFormatter(stamps));

        LineData data = new LineData(dataSet);
        mChart.setData(data);
        mChart.invalidate();
    }

    private void updateRespiratoryRates(List<HistoryOfRespiratoryRate> respiratoryRates) {
        final List<Entry> values = new ArrayList<>();
        final List<String> stamps = new ArrayList<>();
        for (int i = 0; i < respiratoryRates.size(); i++) {
            HistoryOfRespiratoryRate history = respiratoryRates.get(i);
            values.add(new Entry(i, history.respiratoryRate));
            stamps.add(mDateFormat.format(new Date(history.stamp)));
        }
        mChart.resetTracking();
        LineDataSet dataSet = new LineDataSet(values, "RR");
        dataSet.setValueTextSize(8);
        dataSet.setCircleRadius(1.5f);
        dataSet.setColor(Color.BLUE);
        dataSet.setFillColor(Color.BLUE);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setValueTextColor(Color.BLUE);
        dataSet.setLineWidth(1f);
        dataSet.setDrawValues(true);
        dataSet.setDrawCircles(true);
        dataSet.setHighlightEnabled(false);
        dataSet.setMode(LineDataSet.Mode.LINEAR);
        dataSet.setDrawFilled(false);

        mChart.getXAxis().setValueFormatter(new StampValueFormatter(stamps));

        LineData data = new LineData(dataSet);
        mChart.setData(data);
        mChart.invalidate();
    }

    private void updateSteps(List<HistoryOfStep> steps) {
        final List<Entry> values = new ArrayList<>();
        final List<String> stamps = new ArrayList<>();
        for (int i = 0; i < steps.size(); i++) {
            HistoryOfStep history = steps.get(i);
            values.add(new Entry(i, history.steps));
            stamps.add(mDateFormat.format(new Date(history.stamp)));
        }
        mChart.resetTracking();
        LineDataSet dataSet = new LineDataSet(values, "Step");
        dataSet.setValueTextSize(8);
        dataSet.setCircleRadius(1.5f);
        dataSet.setColor(Color.BLUE);
        dataSet.setFillColor(Color.BLUE);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setValueTextColor(Color.BLUE);
        dataSet.setLineWidth(1f);
        dataSet.setDrawValues(true);
        dataSet.setDrawCircles(true);
        dataSet.setHighlightEnabled(false);
        dataSet.setMode(LineDataSet.Mode.LINEAR);
        dataSet.setDrawFilled(false);

        mChart.getXAxis().setValueFormatter(new StampValueFormatter(stamps));

        LineData data = new LineData(dataSet);
        mChart.setData(data);
        mChart.invalidate();
    }

    private static class StampValueFormatter extends ValueFormatter {

        private final List<String> stamps;

        private StampValueFormatter(List<String> stamps) {
            this.stamps = stamps;
        }

        @Override
        public String getFormattedValue(float value) {
            int index = (int) value;
            if (index >= 0 && index < stamps.size()) {
                return stamps.get(index);
            } else {
                return "";
            }
        }
    }
}
