package com.chileaf.cl831.sample;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.chileaf.fitness.callback.TemperatureCallback;

public class TemperatureActivity extends BaseActivity implements TemperatureCallback {

    private AppCompatTextView text1;
    private AppCompatTextView text2;
    private AppCompatTextView text3;

    @Override
    protected int layoutId() {
        return R.layout.activity_temperature;
    }

    @Override
    protected void initView() {
        text1 = findViewById(R.id.tv_environment);
        text2 = findViewById(R.id.tv_wrist_temperature);
        text3 = findViewById(R.id.tv_temperature);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mManager.addTemperatureCallback(this);
    }

    @Override
    public void onTemperatureReceived(@NonNull BluetoothDevice device, float environment, float wrist, float body) {
        runOnUiThread(() -> {
            text1.setText(environment + "");
            text2.setText(wrist + "");
            text3.setText(body + "");
        });
    }
}