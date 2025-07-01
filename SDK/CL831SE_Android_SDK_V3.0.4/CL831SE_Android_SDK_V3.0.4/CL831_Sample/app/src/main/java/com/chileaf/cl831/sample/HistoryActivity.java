package com.chileaf.cl831.sample;

import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.chileaf.model.HistoryOfRecord;

/**
 * History record
 */
public class HistoryActivity extends BaseActivity {

    public static final String EXTRA_HISTORY = "extra_history";

    public static final int TYPE_SPORT = 0x02;
    public static final int TYPE_HEART = 0x04;
    public static final int TYPE_HEART_RR = 0x06;
    public static final int TYPE_INTERVAL = 0x08;
    public static final int TYPE_SINGLE = 0x10;

    public static final int TYPE_3D = 0x12;

    public static final int TYPE_STEP = 0x14;

    private RecyclerView mRvHistory;

    @Override
    protected int layoutId() {
        return R.layout.activity_history;
    }

    @Override
    protected void initView() {
        mRvHistory = findViewById(R.id.rv_history);
        mRvHistory.setLayoutManager(new LinearLayoutManager(this));
        mRvHistory.setItemAnimator(new DefaultItemAnimator());
        mRvHistory.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRvHistory.setHasFixedSize(true);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        int type = getIntent().getIntExtra(EXTRA_HISTORY, 0);
        if (type == TYPE_SPORT) {
            setTitle("7 days sport history");
            showLoadingAutoDismiss(2000);
            HistorySportAdapter adapter = new HistorySportAdapter();
            mRvHistory.setAdapter(adapter);
            mManager.addHistoryOfSportCallback((device, sports) -> {
                runOnUiThread(() -> {
                    adapter.replaceData(sports);
                    hideLoading();
                });
            });
            mManager.getHistoryOfSport();
        } else if (type == TYPE_HEART) {
            setTitle("Heart rate history record");
            showLoadingAutoDismiss(2000);
            HistoryRecordAdapter adapter = new HistoryRecordAdapter();
            adapter.setOnItemClickListener((adapter1, view, position) -> {
                HistoryOfRecord history = (HistoryOfRecord) adapter1.getData().get(position);
                launchDetail(HistoryDetailActivity.TYPE_HR, history.stamp);
            });
            mRvHistory.setAdapter(adapter);
            mManager.addHistoryOfHRRecordCallback((device, records) -> {
                runOnUiThread(() -> {
                    adapter.replaceData(records);
                    hideLoading();
                });
            });
            mManager.getHistoryOfHRRecord();
        } else if (type == TYPE_HEART_RR) {
            setTitle("RR history record");
            showLoadingAutoDismiss(2000);
            HistoryRecordAdapter adapter = new HistoryRecordAdapter();
            adapter.setOnItemClickListener((adapter1, view, position) -> {
                HistoryOfRecord history = (HistoryOfRecord) adapter1.getData().get(position);
                launchDetail(HistoryDetailActivity.TYPE_RR, history.stamp);
            });
            mRvHistory.setAdapter(adapter);
            mManager.addHistoryOfRRRecordCallback((device, records) -> {
                runOnUiThread(() -> {
                    adapter.replaceData(records);
                    hideLoading();
                });
            });
            mManager.getHistoryOfRRRecord();
        } else if (type == TYPE_STEP) {
            setTitle("Step history record");
            showLoadingAutoDismiss(2000);
            HistoryRecordAdapter adapter = new HistoryRecordAdapter();
            adapter.setOnItemClickListener((adapter1, view, position) -> {
                HistoryOfRecord history = (HistoryOfRecord) adapter1.getData().get(position);
                launchDetail(HistoryDetailActivity.TYPE_STEP, history.stamp);
            });
            mRvHistory.setAdapter(adapter);
            mManager.addHistoryOfStepRecordCallback((device, records) -> {
                runOnUiThread(() -> {
                    adapter.replaceData(records);
                    hideLoading();
                });
            });
            mManager.getHistoryOfStepRecord();
        } else if (type == TYPE_INTERVAL) {
            setTitle("Interval steps record");
            showLoadingAutoDismiss(5000);
            IntervalStepAdapter adapter = new IntervalStepAdapter();
            mRvHistory.setAdapter(adapter);
            mManager.addIntervalStepCallback((device, steps) -> {
                runOnUiThread(() -> {
                    adapter.replaceData(steps);
                    hideLoading();
                });
            });
            mManager.getIntervalSteps();
        } else if (type == TYPE_SINGLE) {
            setTitle("Single pressed record");
            showLoadingAutoDismiss(5000);
            HistoryRecordAdapter adapter = new HistoryRecordAdapter();
            mRvHistory.setAdapter(adapter);
            mManager.addSingleTapRecordCallback((device, records) -> {
                runOnUiThread(() -> {
                    adapter.replaceData(records);
                    hideLoading();
                });
            });
            mManager.getSingleTapRecords();
        } else if (type == TYPE_3D) {
            setTitle("3D History");
            showLoadingAutoDismiss(2000);
            History3DAdapter adapter = new History3DAdapter();
            mRvHistory.setAdapter(adapter);
            mManager.addHistoryOf3DDataCallback((device, history, finish) -> {
                runOnUiThread(() -> {
                    adapter.addData(history);
                    hideLoading();
                    if (finish) {
                        showToast("Complete!");
                    }
                });
            });
            mManager.getHistoryOf3D();
        }
    }

}
