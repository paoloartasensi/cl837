package com.chileaf.cl831.sample;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.chileaf.fitness.callback.BloodOxygenCallback;

public class BloodOxygenActivity extends BaseActivity implements BloodOxygenCallback {

    private Switch aSwitch;
    private AppCompatTextView text1;
    private AppCompatTextView text2;
    private AppCompatTextView text3;
    private AppCompatTextView text4;

    @Override
    protected int layoutId() {
        return R.layout.activity_blood_oxygen;
    }

    @Override
    protected void initView() {
        aSwitch = findViewById(R.id.sh_blood);
        aSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                mManager.setBloodOxygen(1);
            } else {
                mManager.setBloodOxygen(0);
            }
        });
        text1 = findViewById(R.id.tv_blood);
        text2 = findViewById(R.id.tv_wrist);
        text3 = findViewById(R.id.tv_pi);
        text4 = findViewById(R.id.tv_onwrist);
        showToast("Please wear tightly and relax your body.");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mManager.addBloodOxygenCallback(this);
    }

    @Override
    public void onBloodOxygenReceived(@NonNull BluetoothDevice device, int bSwitch, String value, int gesture, int piValue, int onwrist) {
        runOnUiThread(() -> {
            aSwitch.setChecked(bSwitch == 1);
            if (value == "" || value == null) {
                return;
            }
            text1.setText(value + "");

            if (gesture == 0) {
                text2.setText("Wrong wrist posture");
            } else if (gesture == 1) {
                text2.setText("Wear the correct posture");
            }

            if (piValue == 0) {
                text3.setText("No pulse detected");
            } else if (piValue < 8) {
                text3.setText("Weak signal");
            } else if (piValue < 15) {
                text3.setText("Good signal");
            } else if (piValue >= 15) {
                text3.setText("Excellent signal");
            }

            if (onwrist == 0) {
                text4.setText("Off the wrist");
            } else if (onwrist == 1) {
                text4.setText("Worn");
            }
        });
    }
}