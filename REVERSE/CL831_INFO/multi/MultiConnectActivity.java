package com.chileaf.cl831.sample.multi;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.chileaf.WearManager;
import com.android.chileaf.fitness.FitnessManager;
import com.android.chileaf.fitness.callback.WearManagerCallbacks;
import com.chileaf.cl831.sample.BaseActivity;
import com.chileaf.cl831.sample.BuildConfig;
import com.chileaf.cl831.sample.R;
import com.chileaf.cl831.sample.ScannerFragment;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import timber.log.Timber;

@SuppressLint("MissingPermission")
public class MultiConnectActivity extends BaseActivity implements ScannerFragment.OnDeviceSelectedListener, WearManagerCallbacks {

    private final List<BluetoothDevice> mManagedDevices = new ArrayList<>();
    private final HashMap<BluetoothDevice, FitnessManager<WearManagerCallbacks>> mBleManagers = new HashMap<>();

    private Button mBtnConnect;
    private RecyclerView mRvDevice;

    private final DeviceAdapter mAdapter = new DeviceAdapter();

    @Override
    protected int layoutId() {
        return R.layout.activity_multi_connect;
    }

    @Override
    protected void initView() {
        mBtnConnect = findViewById(R.id.btn_connect);
        mRvDevice = findViewById(R.id.rv_device);

        mRvDevice.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRvDevice.setLayoutManager(new LinearLayoutManager(this));
        mRvDevice.setItemAnimator(new DefaultItemAnimator());
        mRvDevice.setHasFixedSize(true);

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            DeviceItem item = mAdapter.getItem(position);
            if (item != null) {
                disconnect(item.device);
            }
        });

        mRvDevice.setAdapter(mAdapter);

        mBtnConnect.setOnClickListener(view -> {
            if (isBLEEnabled()) {
                showDeviceScanningDialog();
            } else {
                showBLEDialog();
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        isBLESupported();
        if (!isBLEEnabled()) {
            showBLEDialog();
        }
    }

    public WearManager getWearManager(final BluetoothDevice device) {
        return (WearManager) mBleManagers.get(device);
    }

    public List<BluetoothDevice> getConnectedDevices() {
        final List<BluetoothDevice> list = new ArrayList<>();
        for (BluetoothDevice device : mManagedDevices) {
            final WearManager manager = getWearManager(device);
            if (manager != null && manager.isConnected()) {
                list.add(device);
            }
        }
        return Collections.unmodifiableList(list);
    }

    public void connect(final BluetoothDevice device) {
        // If a device is in managed devices it means that it's already connected, or was connected
        // using autoConnect and the link was lost but Android is already trying to connect to it.
        FitnessManager<WearManagerCallbacks> manager = mBleManagers.get(device);
        if (manager == null) {
            manager = new WearManager(this);
        }
        mBleManagers.put(device, manager);
        manager.setManagerCallbacks(this);
        manager.setDebug(BuildConfig.DEBUG);
        if (!mManagedDevices.contains(device)) {
            mManagedDevices.add(device);
        }
        Timber.v("Connect:%s %s %s", device.getName(), device.getAddress(), manager.toString());
        manager.connect(device)
                .retry(2, 100)
                .useAutoConnect(false)
                .timeout(10000)
                .fail((d, status) -> {
                    mManagedDevices.remove(device);
                    mBleManagers.remove(device);
                })
                .enqueue();
    }

    public final void disconnect(final BluetoothDevice device) {
        final WearManager manager = getWearManager(device);
        if (manager != null) {
            manager.disconnect().enqueue();
        }
    }

    public final void disconnectAll() {
        for (BluetoothDevice device : getConnectedDevices()) {
            disconnect(device);
        }
    }

    public final boolean isConnected(final BluetoothDevice device) {
        final WearManager manager = getWearManager(device);
        return manager != null && manager.isConnected();
    }

    public final boolean isReady(final BluetoothDevice device) {
        final WearManager manager = getWearManager(device);
        return manager != null && manager.isReady();
    }

    public final int getConnectionState(final BluetoothDevice device) {
        final WearManager manager = getWearManager(device);
        return manager != null ? manager.getConnectionState() : BluetoothGatt.STATE_DISCONNECTED;
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
                                new AlertDialog.Builder(MultiConnectActivity.this)
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

    @Override
    public void onDeviceSelected(BluetoothDevice device, String name) {
        showToast("Connect " + device.getName());
        connect(device);
    }

    @Override
    public void onError(@NonNull BluetoothDevice device, @NonNull String message, int errorCode) {
        Timber.e("onError: (" + errorCode + ")");
        showToast(message + " (" + errorCode + ")");
    }

    @Override
    public void onDeviceNotSupported(@NonNull BluetoothDevice device) {
        showToast(getString(R.string.not_supported));
    }

    @Override
    public void onSoftwareVersion(@NonNull BluetoothDevice device, String software) {
        runOnUiThread(() -> mAdapter.onSoftwareVersion(device, software));
    }

    @Override
    public void onBatteryLevelChanged(@NonNull final BluetoothDevice device, final int batteryLevel) {
        runOnUiThread(() -> mAdapter.onBatteryLevelChanged(device, batteryLevel));
    }

    @Override
    public void onHeartRateMeasurementReceived(@NonNull BluetoothDevice device, int heartRate, @Nullable Boolean contactDetected, @Nullable Integer energyExpanded, @Nullable List<Integer> rrIntervals) {
        runOnUiThread(() -> mAdapter.onHeartRateMeasurementReceived(device, heartRate));
    }

    @Override
    public void onSportReceived(@NonNull BluetoothDevice device, int step, int distance, int calorie) {
        runOnUiThread(() -> mAdapter.onSportReceived(device, step, distance, calorie));
    }

    @Override
    public void onDeviceConnected(@NonNull BluetoothDevice device) {
        showToast("Add " + device.getName());
        runOnUiThread(() -> mAdapter.addDevice(new DeviceItem(device)));
    }

    @Override
    public void onDeviceDisconnected(@NonNull final BluetoothDevice device) {
        showToast("Remove " + device.getName());
        runOnUiThread(() -> mAdapter.removeDevice(device));
    }

    @Override
    public void onLinkLossOccurred(@NonNull BluetoothDevice device) {
        showToast("LinkLoss " + device.getName());
        runOnUiThread(() -> mAdapter.removeDevice(device));
    }

    @Override
    public void onBackPressed() {
        disconnectAll();
        super.onBackPressed();
    }

}
