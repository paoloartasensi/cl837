package com.chileaf.cl831.sample;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.chileaf.fitness.callback.HistoryOfSleepCallback;
import com.android.chileaf.model.HistorySleep;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistorySleepActivity extends BaseActivity implements HistoryOfSleepCallback {

    private ListView mListView;
    private ArrayAdapter mArrayAdapter;
    private List<String> strings = new ArrayList<>();
    private static final SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    @Override
    protected int layoutId() {
        return R.layout.activity_sleepdata;
    }

    @Override
    protected void initView() {
        TextView mTvToolbarTitle = findViewById(R.id.tv_toolbar_title);
        mTvToolbarTitle.setText("Get Sleep Data");
        mListView = findViewById(R.id.listView);
        mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, strings);
        mListView.setAdapter(mArrayAdapter);
        showLoadingAutoDismiss(2000);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mManager.addHistoryOfSleepCallback(this);
        mManager.getHistoryOfSleep();
    }

    @Override
    public void onHistoryOfSleepReceived(@NonNull BluetoothDevice device, List<HistorySleep> sleeps) {
        runOnUiThread(() -> {
            for (int i = 0; i < sleeps.size(); i++) {
                HistorySleep sleep = sleeps.get(i);
                int[] actions = sleep.actions;
                int len = actions.length;
                long utc = sleep.utc;
                int index = 0;
                long utc2 = 0;
                String text = "";
                for (int i1 = 0; i1 < len; i1++) {
                    int action = actions[i1]; // one every five minutes
                    Log.d("", "action: " + action);
                    long utc1 = (utc + (i1 * 300000));
                    if (action > 20) { //wide awake
                        if (index >= 3) {
                            long utc3 = utc2 - (300000 * index);
                            for (int i2 = 0; i2 < index; i2++) {
                                text += "\nutc:" + millsToDate(utc3 + (i2 * 300000)) + "\naction Index: deep Sleep";
                            }
                        } else if (index > 0) {
                            long utc3 = utc2 - (300000 * index);
                            for (int i2 = 0; i2 < index; i2++) {
                                text += "\nutc:" + millsToDate(utc3 + (i2 * 300000)) + "\naction Index: light sleep";
                            }
                        }
                        index = 0;
                        utc2 = 0;
                        text += "\nutc:" + millsToDate(utc1) + "\naction Index: not Sleep";
                    } else if (action <= 20 && action > 0) { //light sleep
                        if (index >= 3) {
                            long utc3 = utc2 - (300000 * index);
                            for (int i2 = 0; i2 < index; i2++) {
                                text += "\nutc:" + millsToDate(utc3 + (i2 * 300000)) + "\naction Index: deep Sleep";
                            }

                        } else if (index > 0) {
                            long utc3 = utc2 - (300000 * index);
                            for (int i2 = 0; i2 < index; i2++) {
                                text += "\nutc:" + millsToDate(utc3 + (i2 * 300000)) + "\naction Index: light sleep";
                            }
                        }
                        index = 0;
                        utc2 = 0;
                        text += "\nutc:" + millsToDate(utc1) + "\naction Index: light sleep";
                    } else {   //Deep sleep 3 >= 0 (3 consecutive zeros equals deep sleep)
                        index++;
                        utc2 = utc1;
                    }
                }

                if (index >= 3) {
                    long utc3 = utc2 - (300000 * index);
                    for (int i2 = 0; i2 < index; i2++) {
                        text += "\nutc:" + millsToDate(utc3 + (i2 * 300000)) + "\naction Index: deep Sleep";
                    }

                } else if (index > 0) {
                    long utc3 = utc2 - (300000 * index);
                    for (int i2 = 0; i2 < index; i2++) {
                        text += "\nutc:" + millsToDate(utc3 + (i2 * 300000)) + "\naction Index: light sleep";
                    }
                }
                strings.add(text);
            }
            mArrayAdapter.notifyDataSetChanged();
        });
    }

    private String millsToDate(Long time) {
        Date dt = new Date(time);
        return mFormat.format(dt);
    }

}