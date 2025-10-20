package com.chileaf.cl831.sample;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SwitchCompat;

import com.android.chileaf.fitness.callback.BodyHealthCallback;
import com.android.chileaf.fitness.callback.Sensor6DFrequencyCallback;
import com.android.chileaf.fitness.callback.Sensor6DRawDataCallback;
import com.android.chileaf.fitness.callback.WearManagerCallbacks;
import com.android.chileaf.util.HexUtil;
import com.chileaf.cl831.sample.dfu.DfuActivity;
import com.chileaf.cl831.sample.multi.MultiConnectActivity;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import timber.log.Timber;

public class MainActivity extends BaseActivity implements ScannerFragment.OnDeviceSelectedListener, WearManagerCallbacks {

    private boolean mDeviceConnected = false;

    private TextView mTvDeviceName;
    private TextView mTvSDKVersion;
    private TextView mTvVersion;
    private TextView mTvRssi;
    private TextView mTvBattery;
    private TextView mTvSport;
    private TextView mTvHeartRate;
    private TextView mTvReceivedData;
    private TextView mTvAccelerometer;

    private TextView mTvHRStatus;
    private TextView mTvHRAlertStatus;
    private EditText mEtMin;
    private EditText mEtMax;
    private EditText mEtGoal;
    private TextView mTvHRMax;
    private TextView mTv3DFrequency;
    private TextView mTv3DStatus;
    private EditText mEtHRMax;
    private TextView mTvHealth;
    private TextView mTv6DFrequency;
    private TextView mTv6DRawData;
    private Button mBtnConnect;
    private final SimpleDateFormat mDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.getDefault());
    private final Map<Integer, String> mFrequency3DMap = new HashMap<>();
    private final Map<Integer, String> mFrequency6DMap = new HashMap<>();

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTvDeviceName = findViewById(R.id.tv_device_name);
        mTvSDKVersion = findViewById(R.id.tv_sdk_version);

        mTvSDKVersion.setText("SDK Version: v" + BuildConfig.VERSION_NAME);

        mTvAccelerometer = findViewById(R.id.tv_accelerometer);

        mTvRssi = findViewById(R.id.tv_rssi);
        mTvVersion = findViewById(R.id.tv_version);
        mTvBattery = findViewById(R.id.tv_battery);
        mTvSport = findViewById(R.id.tv_sport);
        mTvHeartRate = findViewById(R.id.tv_hr);
        mTvReceivedData = findViewById(R.id.tv_received);

        mTvHRStatus = findViewById(R.id.tv_heart_rate);
        mTvHRAlertStatus = findViewById(R.id.tv_hr_alert_status);

        mTvHRMax = findViewById(R.id.tv_hr_max);
        mTv3DFrequency = findViewById(R.id.tv_3d_frequency);
        mTv3DStatus = findViewById(R.id.tv_3d_status);

        mEtMin = findViewById(R.id.et_min);
        mEtMax = findViewById(R.id.et_max);
        mEtGoal = findViewById(R.id.et_goal);

        mEtHRMax = findViewById(R.id.et_hr_max);
        mTvHealth = findViewById(R.id.tv_health);

        mTv6DFrequency = findViewById(R.id.tv_6d_frequency);
        mTv6DRawData = findViewById(R.id.tv_6d_data);

        mBtnConnect = findViewById(R.id.btn_connect);

        //Multi connect
        findViewById(R.id.btn_multi).setOnClickListener(view -> startActivity(new Intent(this, MultiConnectActivity.class)));
        //Sport health
        findViewById(R.id.btn_sport_health).setOnClickListener(view -> startActivity(new Intent(this, SportHealthActivity.class)));
        //Get HeartRate Status
        findViewById(R.id.btn_heart_rate).setOnClickListener(view -> mManager.getHeartRateStatus());
        //User information
        findViewById(R.id.btn_user_info).setOnClickListener(view -> startActivity(new Intent(this, UserInfoActivity.class)));
        //Restoration
        findViewById(R.id.btn_restoration).setOnClickListener(view -> mManager.restoration());
        //DFU upgrade
        findViewById(R.id.btn_dfu).setOnClickListener(view -> {
            if (!mManager.isConnected()) {
                showToast("请先连接设备");
                return;
            }
            startActivity(new Intent(this, DfuActivity.class));
        });
        //Get 7 days sport history
        findViewById(R.id.btn_history_sport).setOnClickListener(view -> launchHistory(HistoryActivity.TYPE_SPORT));
        //Heart rate history record
        findViewById(R.id.btn_history_heart).setOnClickListener(view -> launchHistory(HistoryActivity.TYPE_HEART));
        //Heart rate RR history record
        findViewById(R.id.btn_history_rr).setOnClickListener(view -> launchHistory(HistoryActivity.TYPE_HEART_RR));
        //Step history record
        findViewById(R.id.btn_history_step).setOnClickListener(view -> launchHistory(HistoryActivity.TYPE_STEP));
        //Get the number of steps in the interval
        findViewById(R.id.btn_interval).setOnClickListener(view -> launchHistory(HistoryActivity.TYPE_INTERVAL));
        //Get historical data for a single key press
        findViewById(R.id.btn_single).setOnClickListener(view -> launchHistory(HistoryActivity.TYPE_SINGLE));
        //Get historical data for 3d
        findViewById(R.id.btn_3d).setOnClickListener(view -> launchHistory(HistoryActivity.TYPE_3D));

        //Set HeartRate Status
        findViewById(R.id.btn_hr_setting).setOnClickListener(view -> {
            int min = getValue(mEtMin);
            int max = getValue(mEtMax);
            int goal = getValue(mEtGoal);
            mManager.setHeartRateStatus(min, max, goal);
        });

        //Shutdown
        findViewById(R.id.btn_shut_down).setOnClickListener(view -> mManager.shutdown());

        //Blood oxygen
        findViewById(R.id.btn_blood_oxygen).setOnClickListener(view -> startActivity(new Intent(this, BloodOxygenActivity.class)));
        //Real time temperature
        findViewById(R.id.btn_temperature).setOnClickListener(view -> startActivity(new Intent(this, TemperatureActivity.class)));

        //Heart Rate Alarm Switch
        SwitchCompat swAlarm = findViewById(R.id.sw_alarm);
        swAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mManager.setHeartRateAlarm(isChecked);
                showToast(getAlarm(isChecked));
            }
        });

        //Heart Rate Alert By Age
        findViewById(R.id.btn_hr_alert).setOnClickListener(v -> mManager.getHeartRateAlarm());
        //Set HeartRate Max
        findViewById(R.id.btn_hr_max).setOnClickListener(view -> {
            int max = getValue(mEtHRMax);
            mManager.setHeartRateMax(max);
        });
        //Get Heart Rate Max
        findViewById(R.id.btn_get_hr_max).setOnClickListener(v -> mManager.getHeartRateMax());
        //Get Sleep Data
        findViewById(R.id.btn_get_sleep_data).setOnClickListener(view -> startActivity(new Intent(this, HistorySleepActivity.class)));
        //Get 3D Frequency
        findViewById(R.id.btn_3d_frequency).setOnClickListener(v -> mManager.get3DFrequency());
        //Setting 3D Frequency
        findViewById(R.id.btn_3d_0).setOnClickListener(v -> mManager.set3DFrequency(0));//25HZ
        findViewById(R.id.btn_3d_1).setOnClickListener(v -> mManager.set3DFrequency(1));//50HZ
        findViewById(R.id.btn_3d_2).setOnClickListener(v -> mManager.set3DFrequency(2));//100HZ
        findViewById(R.id.btn_3d_3).setOnClickListener(v -> mManager.set3DFrequency(3));//200HZ
        findViewById(R.id.btn_3d_4).setOnClickListener(v -> mManager.set3DFrequency(4));//400HZ
        //Get 6D Frequency
        findViewById(R.id.btn_6d_frequency).setOnClickListener(view -> mManager.get6DFrequency());
        //Set 6D Frequency
        findViewById(R.id.btn_6d_0).setOnClickListener(view -> mManager.set6DFrequency(0));//26hz
        findViewById(R.id.btn_6d_1).setOnClickListener(view -> mManager.set6DFrequency(1));//52hz
        findViewById(R.id.btn_6d_2).setOnClickListener(view -> mManager.set6DFrequency(2));//104hz
        findViewById(R.id.btn_6d_3).setOnClickListener(view -> mManager.set6DFrequency(3));//208hz

        //3D Status Switch
        SwitchCompat sw3dStatus = findViewById(R.id.sw_3d_status);
        sw3dStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mManager.set3DEnabled(isChecked);
                showToast(get3DStatus(isChecked));
            }
        });

        //Get 3D Status
        findViewById(R.id.btn_3d_status).setOnClickListener(v -> mManager.get3DStatus());

        AppCompatEditText etFilter = findViewById(R.id.et_filter);
        mBtnConnect.setOnClickListener(view -> {
            String filter = etFilter.getText().toString();
            if (!TextUtils.isEmpty(filter)) {
                mManager.setFilterNames(filter);
            } else {
                mManager.setFilterNames((String[]) null);
            }
            if (isBLEEnabled()) {
                if (!mDeviceConnected) {
                    showDeviceScanningDialog();
                } else {
                    mManager.disconnectDevice();
                }
            } else {
                showBLEDialog();
            }
        });
    }

    private int getValue(EditText view) {
        String value = view.getText().toString();
        if (value.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        isBLESupported();
        if (!isBLEEnabled()) {
            showBLEDialog();
        }

        mFrequency3DMap.put(0, "25HZ");
        mFrequency3DMap.put(1, "50HZ");
        mFrequency3DMap.put(2, "100HZ");
        mFrequency3DMap.put(3, "200HZ");
        mFrequency3DMap.put(4, "400HZ");

        mFrequency6DMap.put(0, "26HZ");
        mFrequency6DMap.put(1, "52HZ");
        mFrequency6DMap.put(2, "104HZ");
        mFrequency6DMap.put(3, "208HZ");

        mManager.setManagerCallbacks(this);
        mManager.addAccelerometerCallback((device, x, y, z) -> {
            runOnUiThread(() -> mTvAccelerometer.setText(getString(R.string.accelerometer, x, y, z)));
        });
        mManager.addHeartRateStatusCallback((device, min, max, goal) -> {
            runOnUiThread(() -> mTvHRStatus.setText("HR Status Min:" + min + " Max:" + max + " Goal:" + goal));
        });
        mManager.setCustomDataReceivedCallback((device, data) -> {
            runOnUiThread(() -> mTvReceivedData.setText("Received data:" + HexUtil.bytes2HexString(data)));
        });
        mManager.addHeartRateAlarmCallback((device, stamp, enabled) -> {
            runOnUiThread(() -> {
                String status = getAlarm(enabled);
                mTvHRAlertStatus.setText("HR Alarm:" + status + " \n(" + mDateFormat.format(new Date(stamp)) + ")");
            });
        });
        mManager.addHeartRateMaxCallback((device, max) -> runOnUiThread(() -> mTvHRMax.setText("HeartRate Max:" + max)));
        mManager.addSensor3DFrequencyCallback((device, frequency) -> runOnUiThread(() -> mTv3DFrequency.setText("3D Frequency:" + mFrequency3DMap.get(frequency))));
        mManager.addSensor3DStatusCallback((device, enabled) -> runOnUiThread(() -> mTv3DStatus.setText("3D Status:" + (enabled ? "Enabled" : "Disabled"))));
        mManager.addBodyHealthCallback(new BodyHealthCallback() {
            @Override
            public void onHealthReceived(@NonNull BluetoothDevice device, int vo2Max, int breathRate, int emotionLevel, int stressPercent, int stamina, float tp, float lf, float hf) {
                runOnUiThread(() -> mTvHealth.setText("Health vo2Max:" + vo2Max + " breathRate:" + breathRate + " emotionLevel:" + getEmotion(emotionLevel) +
                        " stressPercent:" + stressPercent + "% stamina:" + getStamina(stamina) + " \nTP:" + tp + " LF:" + lf + " HF:" + hf));
            }
        });
        mManager.addSensor6DFrequencyCallback(new Sensor6DFrequencyCallback() {
            @Override
            public void onSensor6DFrequencyReceived(@NonNull BluetoothDevice device, int frequency) {
                runOnUiThread(() -> mTv6DFrequency.setText("6D Frequency:" + mFrequency6DMap.get(frequency)));
            }
        });
        mManager.addSensor6DRawDataCallback(new Sensor6DRawDataCallback() {
            @Override
            public void onSensor6DRawDataReceived(@NonNull BluetoothDevice device, long utc, int sequence, int gyroscopeX, int gyroscopeY, int gyroscopeZ, int accelerometerX, int accelerometerY, int accelerometerZ) {
                runOnUiThread(() -> {
                    //UTC  0xFF:not supported timestamp
                    String time = utc != 0xFF ? "\nUTC:" + mDateFormat.format(new Date(utc)) + "(" + utc + ")" : "";
                    mTv6DRawData.setText("Sensor:" + time + "\nSequence:" + sequence + "\nGyroscopeX:" + gyroscopeX + "\nGyroscopeY:" + gyroscopeY + "\nGyroscopeZ:" + gyroscopeZ
                            + "\nAccelerometerX:" + accelerometerX + "\nAccelerometerY:" + accelerometerY + "\nAccelerometerZ:" + accelerometerZ);
                });
            }
        });
    }

    private void launchHistory(int type) {
        Intent history = new Intent(this, HistoryActivity.class);
        history.putExtra(HistoryActivity.EXTRA_HISTORY, type);
        startActivity(history);
    }

    private void showDeviceScanningDialog() {
        if (isLocationEnabled(this)) {
            XXPermissions.with(this)
                    .permission(getPermissions())
                    .request(new OnPermissionCallback() {
                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (allGranted) {
                                runOnUiThread(() -> {
                                    final ScannerFragment dialog = ScannerFragment.getInstance();
                                    dialog.show(getSupportFragmentManager(), "scan_fragment");
                                });
                            } else {
                                showToast("permission is denied");
                            }
                        }

                        @Override
                        public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                            if (doNotAskAgain) {
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle(getString(R.string.permission_required))
                                        .setMessage(getString(R.string.permission_location_info))
                                        .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                                            onPermissionSettings();
                                        })
                                        .setNegativeButton(getString(R.string.no), null)
                                        .show();
                            } else {
                                showToast("permission is denied");
                            }
                        }
                    });
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.location_permission_title))
                    .setMessage(getString(R.string.location_permission_info))
                    .setPositiveButton("OK", (dialog, which) -> {
                        onEnableLocation();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }

    private void defaultUI() {
        mTvDeviceName.setText("Device name");
        mTvVersion.setText("Version:--");
        mTvRssi.setText("Rssi:--");
        mTvBattery.setText("Battery:--");
        mTvSport.setText("Sport:--");
        mTvAccelerometer.setText("Accelerometer:--");
        mTvHeartRate.setText("Heart Rate:--");
        mTvHRStatus.setText("HR Status Min:--");
        mTvHRAlertStatus.setText("HR Alarm:--");
        mTvHRMax.setText("HeartRate Max:--");
        mTv3DFrequency.setText("3D Frequency:--");
        mTv3DStatus.setText("3D Status:--");
        mTvHealth.setText("Health:--");
        mTv6DFrequency.setText("6D Frequency:--");
        mTv6DRawData.setText("6D RawData:--");
        mBtnConnect.setText(getString(R.string.action_connect));
    }

    private String get3DStatus(boolean enabled) {
        return enabled ? "Enabled 3D" : "Disabled 3D";
    }

    private String getAlarm(boolean enabled) {
        return enabled ? "Alarm By Age" : "Alarm By High-Low";
    }

    @Override
    public void onDeviceSelected(BluetoothDevice device, String name) {
        mManager.connectDevice(device);
        mTvDeviceName.setText(getString(R.string.device_name, name));
    }

    @Override
    public void onError(@NonNull BluetoothDevice device, @NonNull String message, int errorCode) {
        Timber.e("onError: (" + errorCode + ")");
    }

    @Override
    public void onDeviceNotSupported(@NonNull BluetoothDevice device) {
        showToast(getString(R.string.not_supported));
    }

    @Override
    public void onSoftwareVersion(@NonNull BluetoothDevice device, String software) {
        runOnUiThread(() -> mTvVersion.setText("Software Version:" + software));
    }

    @Override
    public void onRssiRead(@NonNull BluetoothDevice device, int rssi) {
        runOnUiThread(() -> mTvRssi.setText("Rssi:" + rssi + "dBm"));
    }

    @Override
    public void onBatteryLevelChanged(@NonNull final BluetoothDevice device, final int batteryLevel) {
        runOnUiThread(() -> mTvBattery.setText(getString(R.string.battery, batteryLevel)));
    }

    @Override
    public void onHeartRateMeasurementReceived(@NonNull BluetoothDevice device, int heartRate, @Nullable Boolean contactDetected, @Nullable Integer energyExpanded, @Nullable List<Integer> rrIntervals) {
        runOnUiThread(() -> {
                    mTvHeartRate.setText(getString(R.string.heart_rate, heartRate));
                    if (rrIntervals != null) {
                        Timber.e("rrIntervals:%s", rrIntervals.toString());
                    }
                }
        );
    }

    @Override
    public void onSportReceived(@NonNull BluetoothDevice device, int step, int distance, int calorie) {
        runOnUiThread(() -> mTvSport.setText(getString(R.string.sport, step, distance / 100f, calorie / 10f)));
    }

    @Override
    public void onDeviceConnected(@NonNull BluetoothDevice device) {
        mDeviceConnected = true;
        runOnUiThread(() -> mBtnConnect.setText(R.string.action_disconnect));
    }

    @Override
    public void onDeviceDisconnected(@NonNull final BluetoothDevice device) {
        runOnUiThread(() -> defaultUI());
        mDeviceConnected = false;
        mManager.close();
    }

    @Override
    public void onLinkLossOccurred(@NonNull BluetoothDevice device) {
        runOnUiThread(() -> defaultUI());
        mDeviceConnected = false;
        mManager.close();
    }

    @Override
    public void onBackPressed() {
        mManager.disconnectDevice();
        super.onBackPressed();
    }

}
