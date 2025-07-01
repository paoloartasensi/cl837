package com.chileaf.cl831.sample;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.chileaf.fitness.callback.BodySportHealthCallback;

public class SportHealthActivity extends BaseActivity implements BodySportHealthCallback {

    private AppCompatTextView mTvSportHealth;

    @Override
    protected int layoutId() {
        return R.layout.activity_sport_health;
    }

    @Override
    protected void initView() {
        setTitle("Sport health");
        mTvSportHealth = findViewById(R.id.tv_sport_health);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mManager.addBodySportHealthCallback(this);
    }

    @Override
    public void onSportHealthReceived(@NonNull BluetoothDevice device, int vo2Max, int breathRate, int emotion, int pressure, int stamina) {
        runOnUiThread(() ->
                mTvSportHealth.setText("Sport health \nvo2Max:" + vo2Max + "\nbreathRate:" + breathRate + "\nemotion:" +
                        getEmotion(emotion) + "\npressure:" + pressure + "%\nstamina:" + getStamina(stamina)));
    }
}