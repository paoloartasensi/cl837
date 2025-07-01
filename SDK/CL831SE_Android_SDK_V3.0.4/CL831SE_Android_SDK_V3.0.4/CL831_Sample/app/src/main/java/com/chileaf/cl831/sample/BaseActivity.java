package com.chileaf.cl831.sample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.chileaf.WearManager;
import com.hjq.permissions.XXPermissions;


public abstract class BaseActivity extends AppCompatActivity {

    protected WearManager mManager;
    protected LoadingDialog mLoading;
    protected Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        initialize();
        initView();
        initData(savedInstanceState);
    }

    @LayoutRes
    protected abstract int layoutId();

    protected abstract void initView();

    protected abstract void initData(Bundle savedInstanceState);

    private void initialize() {
        ImageView ivBack = findViewById(R.id.iv_toolbar_back);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> onBackPressed());
        }
        mManager = WearManager.getInstance(this);
        mManager.setDebug(BuildConfig.DEBUG);
    }

    protected void setTitle(String title) {
        TextView tvTitle = findViewById(R.id.tv_toolbar_title);
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    protected void launchDetail(int type, long stamp) {
        Intent history = new Intent(this, HistoryDetailActivity.class);
        history.putExtra(HistoryDetailActivity.EXTRA_TYPE, type);
        history.putExtra(HistoryDetailActivity.EXTRA_STAMP, stamp);
        startActivity(history);
    }

    protected void showLoading() {
        showLoading(getString(R.string.loading));
    }

    protected void showLoading(String message) {
        mLoading = LoadingDialog.Builder(this)
                .setMessage(message)
                .build();
        mLoading.show();
    }

    protected void showLoadingAutoDismiss(final long delay) {
        showLoading();
        mHandler.postDelayed(this::hideLoading, delay);
    }

    protected void hideLoading() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismiss();
        }
    }

    protected void showToast(final int messageResId) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(final String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    protected void isBLESupported() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            new MaterialDialog.Builder(this)
                    .title(R.string.no_ble)
                    .positiveText(R.string.scanner_action_cancel)
                    .positiveColorRes(R.color.colorPrimary)
                    .onPositive((dialog, which) -> finish())
                    .show();
        }
    }

    protected boolean isBLEEnabled() {
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        final BluetoothAdapter adapter = bluetoothManager.getAdapter();
        return adapter != null && adapter.isEnabled();
    }

    protected String[] getPermissions() {
        int targetSdkVersion = getApplicationInfo().targetSdkVersion;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && targetSdkVersion >= Build.VERSION_CODES.S) {
            return new String[]{android.Manifest.permission.BLUETOOTH_SCAN, android.Manifest.permission.BLUETOOTH_CONNECT, android.Manifest.permission.ACCESS_FINE_LOCATION};
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && targetSdkVersion >= Build.VERSION_CODES.Q) {
            return new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION};
        } else {
            return new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};
        }
    }

    @SuppressLint("MissingPermission")
    protected void showBLEDialog() {
        XXPermissions.with(this)
                .permission(getPermissions())
                .request((permissions, allGranted) -> {
                    if (allGranted) {
                        startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));
                    } else {
                        showToast(getString(R.string.no_required_permission));
                    }
                });
    }

    protected boolean isLocationEnabled(@NonNull final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int locationMode = Settings.Secure.LOCATION_MODE_OFF;
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (final Settings.SettingNotFoundException e) {
                // do nothing
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        }
        return true;
    }

    protected void onEnableLocation() {
        final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    protected void onPermissionSettings() {
        final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    }

    // Get emotion value. (int, as emotion type)
    // 0 - <result not ready>
    // 1 - Calm
    // 2 - Stressed
    // 3 - Happy
    // 4 - Tense
    // 5 - Angry
    protected String getEmotion(int level) {
        if (level == 0) {
            return "Not ready";
        } else if (level == 1) {
            return "Calm";
        } else if (level == 2) {
            return "Stressed";
        } else if (level == 3) {
            return "Happy";
        } else if (level == 4) {
            return "Tense";
        } else if (level == 5) {
            return "Angry";
        } else {
            return "Unknown";
        }
    }

    // 0 - <result not ready>
    // 1 - Normal
    // 2 - Moderate fatigue
    // 3 - Severe fatigue
    protected String getStamina(int stamina) {
        if (stamina == 0) {
            return "Not ready";
        } else if (stamina == 1) {
            return "Normal";
        } else if (stamina == 2) {
            return "Moderate fatigue";
        } else if (stamina == 3) {
            return "Severe fatigue";
        } else {
            return "Unknown";
        }
    }

    protected String getMode(int mode) {
        if (mode == 0) {
            return "Indoor running";
        } else if (mode == 1) {
            return "Outdoor running";
        } else if (mode == 2) {
            return "Outdoor cycling";
        } else if (mode == 3) {
            return "Spinning bike";
        } else if (mode == 4) {
            return "Free training";
        } else if (mode == 5) {
            return "Skipping rope";
        } else {
            return "None";
        }
    }

}
