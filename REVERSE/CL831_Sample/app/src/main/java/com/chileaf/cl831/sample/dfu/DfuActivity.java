package com.chileaf.cl831.sample.dfu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.NotificationManager;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.chileaf.cl831.sample.BaseActivity;
import com.chileaf.cl831.sample.R;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import no.nordicsemi.android.dfu.DfuProgressListener;
import no.nordicsemi.android.dfu.DfuProgressListenerAdapter;
import no.nordicsemi.android.dfu.DfuServiceInitiator;
import no.nordicsemi.android.dfu.DfuServiceListenerHelper;
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
import no.nordicsemi.android.support.v18.scanner.ScanCallback;
import no.nordicsemi.android.support.v18.scanner.ScanFilter;
import no.nordicsemi.android.support.v18.scanner.ScanResult;
import no.nordicsemi.android.support.v18.scanner.ScanSettings;
import timber.log.Timber;

@SuppressLint("MissingPermission")
public class DfuActivity extends BaseActivity implements UploadCancelFragment.CancelFragmentListener {

    private static final String TAG = "DfuActivity";

    private static final String PREFS_PREFIX = "com.chileaf.dfu";

    private static final String PREFS_FILE_NAME = PREFS_PREFIX + ".PREFS_FILE_NAME";
    private static final String PREFS_FILE_TYPE = PREFS_PREFIX + ".PREFS_FILE_TYPE";
    private static final String PREFS_FILE_SIZE = PREFS_PREFIX + ".PREFS_FILE_SIZE";

    private static final String DATA_STATUS = "status";
    private static final String DATA_DFU_COMPLETED = "dfu_completed";
    private static final String DATA_DFU_ERROR = "dfu_error";

    private TextView mFileNameView;
    private TextView mFileSizeView;
    private TextView mFileStatusView;
    private TextView mTextPercentage;
    private TextView mTextUploading;

    private Button mSelectFileButton;
    private Button mUploadButton;

    private Uri mFileUri;
    private boolean mStatusOk;
    /**
     * Flag set to true in {@link #onRestart()} and to false in {@link #onPause()}.
     */
    private boolean mResumed;
    /**
     * Flag set to true if DFU operation was completed while {@link #mResumed} was false.
     */
    private boolean mDfuCompleted;
    /**
     * The error message received from DFU service while {@link #mResumed} was false.
     */
    private String mDfuError;

    private final ScanCallback mScanCallback = new ScanCallback() {

        @Override
        public void onBatchScanResults(final List<ScanResult> results) {
            for (ScanResult result : results) {
                BluetoothDevice device = result.getDevice();
                if (isDfuDevice(device.getName())) {
                    showToast("Start Update...");
                    dfuUpdated(device);
                    break;
                }
            }
        }
    };

    private static class ContentResultContract extends ActivityResultContract<String, Uri> {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, String type) {
            return new Intent(Intent.ACTION_GET_CONTENT)
                    .addCategory(Intent.CATEGORY_OPENABLE)
                    .setType(type);
        }

        @Override
        public Uri parseResult(int resultCode, @Nullable Intent intent) {
            if (resultCode == Activity.RESULT_OK && intent != null) {
                return intent.getData();
            }
            return null;
        }
    }

    private final ActivityResultLauncher<String> zipLauncher = registerForActivityResult(new ContentResultContract(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri uri) {
            if (uri == null) {
                showToast("Uri is null");
            } else {
                if ("content".equalsIgnoreCase(uri.getScheme())) {
                    Cursor query = getContentResolver().query(uri, null, null, null, null);
                    if (query != null && query.moveToNext()) {
                        int displayNameIndex = query.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME);
                        int fileSizeIndex = query.getColumnIndex(MediaStore.MediaColumns.SIZE);
                        String fileName = query.getString(displayNameIndex);
                        int fileSize = query.getInt(fileSizeIndex);
                        updateFileInfo(uri, fileName, fileSize, DfuService.TYPE_AUTO);
                        query.close();
                    } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                        String filePath = uri.getPath();
                        if (filePath != null) {
                            File file = new File(filePath);
                            updateFileInfo(uri, file.getName(), file.length(), DfuService.TYPE_AUTO);
                        }
                    }
                    Timber.i("zipLauncher  uri:%s", uri);
                }
            }
        }
    });

    /**
     * The progress listener receives events from the DFU Service.
     * If is registered in onCreate() and unregistered in onDestroy() so methods here may also be called
     * when the screen is locked or the app went to the background. This is because the UI needs to have the
     * correct information after user comes back to the activity and this information can't be read from the service
     * as it might have been killed already (DFU completed or finished with error).
     */
    private final DfuProgressListener mDfuProgressListener = new DfuProgressListenerAdapter() {
        @Override
        public void onDeviceConnecting(final String deviceAddress) {
            mTextPercentage.setText("Device Connecting...");
        }

        @Override
        public void onDfuProcessStarting(final String deviceAddress) {
            mTextPercentage.setText("Process Starting...");
        }

        @Override
        public void onEnablingDfuMode(final String deviceAddress) {
            mTextPercentage.setText("Updating...");
        }

        @Override
        public void onFirmwareValidating(final String deviceAddress) {
            mTextPercentage.setText("FirmwareValidating...");
        }

        @Override
        public void onDeviceDisconnecting(final String deviceAddress) {
            mTextPercentage.setText("Device Disconnecting...");
        }

        @Override
        public void onDfuCompleted(final String deviceAddress) {
            mTextPercentage.setText("Update Completed");
            if (mResumed) {
                // let's wait a bit until we cancel the notification. When canceled immediately it will be recreated by service again.
                new Handler().postDelayed(() -> {
                    onTransferCompleted();
                    // if this activity is still open and upload process was completed, cancel the notification
                    final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    if (manager != null) {
                        manager.cancel(DfuService.NOTIFICATION_ID);
                    }
                }, 200);
            } else {
                // Save that the DFU process has finished
                mDfuCompleted = true;
            }
        }

        @Override
        public void onDfuAborted(final String deviceAddress) {
            mTextPercentage.setText("Update Aborted");
            // let's wait a bit until we cancel the notification. When canceled immediately it will be recreated by service again.
            new Handler().postDelayed(() -> {
                onUploadCanceled();
                // if this activity is still open and upload process was completed, cancel the notification
                final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (manager != null) {
                    manager.cancel(DfuService.NOTIFICATION_ID);
                }
            }, 200);
        }

        @Override
        public void onProgressChanged(final String deviceAddress, final int percent, final float speed, final float avgSpeed, final int currentPart, final int partsTotal) {
            mTextPercentage.setText(getString(R.string.dfu_uploading_percentage, percent));
            if (partsTotal > 1)
                mTextUploading.setText(String.format(Locale.getDefault(), "Uploading Progress:%d/%d", currentPart, partsTotal));
            else
                mTextUploading.setText("Updating...");
        }

        @Override
        public void onError(final String deviceAddress, final int error, final int errorType, final String message) {
            if (mResumed) {
                showErrorMessage(message);
                // We have to wait a bit before canceling notification. This is called before DfuService creates the last notification.
                new Handler().postDelayed(() -> {
                    // if this activity is still open and upload process was completed, cancel the notification
                    final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    if (manager != null) {
                        manager.cancel(DfuService.NOTIFICATION_ID);
                    }
                }, 200);
            } else {
                mDfuError = message;
            }
        }
    };

    @Override
    protected int layoutId() {
        return R.layout.activity_dfu;
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DfuServiceInitiator.createDfuNotificationChannel(this);
        }
        if (!isBLEEnabled()) {
            showBLEDialog();
        }
        defaultUI();
        XXPermissions.with(this)
                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                        showToast("permission is " + (allGranted ? "granted" : "denied"));
                    }
                });
        DfuServiceListenerHelper.registerProgressListener(this, mDfuProgressListener);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mStatusOk = mStatusOk || savedInstanceState.getBoolean(DATA_STATUS);
            mUploadButton.setEnabled(mStatusOk);
            mDfuError = savedInstanceState.getString(DATA_DFU_ERROR);
            mDfuCompleted = savedInstanceState.getBoolean(DATA_DFU_COMPLETED);
        }
    }

    private boolean isDfuDevice(String name) {
        return name != null && !TextUtils.isEmpty(name) && (name.toUpperCase().endsWith("U"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DfuServiceListenerHelper.unregisterProgressListener(this, mDfuProgressListener);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(DATA_STATUS, mStatusOk);
        outState.putString(DATA_DFU_ERROR, mDfuError);
        outState.putBoolean(DATA_DFU_COMPLETED, mDfuCompleted);
    }

    private void defaultUI() {
        mFileNameView = findViewById(R.id.file_name);
        mFileSizeView = findViewById(R.id.file_size);
        mFileStatusView = findViewById(R.id.file_status);
        mSelectFileButton = findViewById(R.id.action_select_file);
        mUploadButton = findViewById(R.id.action_upload);
        mTextPercentage = findViewById(R.id.action_progress);
        mTextUploading = findViewById(R.id.action_uploading);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (isDfuServiceRunning()) {
            // Restore image file information
            mFileNameView.setText(preferences.getString(PREFS_FILE_NAME, ""));
            mFileSizeView.setText(preferences.getString(PREFS_FILE_SIZE, ""));
            mFileStatusView.setText(R.string.dfu_file_status_ok);
            mStatusOk = true;
            showProgressBar();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mResumed = true;
        if (mDfuCompleted)
            onTransferCompleted();
        if (mDfuError != null)
            showErrorMessage(mDfuError);
        if (mDfuCompleted || mDfuError != null) {
            // if this activity is still open and upload process was completed, cancel the notification
            final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.cancel(DfuService.NOTIFICATION_ID);
            }
            mDfuCompleted = false;
            mDfuError = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mResumed = false;
    }

    public void startScan(String address) {
        final BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
        final ScanSettings settings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .setUseHardwareBatchingIfSupported(false)
                .setReportDelay(1000)
                .setLegacy(false)
                .build();
        final List<ScanFilter> filters = new ArrayList<>();
        filters.add(new ScanFilter.Builder()
                .setDeviceAddress(address)
                .build());
        scanner.startScan(filters, settings, mScanCallback);
    }

    /**
     * Stop scan devices
     */
    public void stopScan() {
        if (mScanCallback != null) {
            final BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
            scanner.stopScan(mScanCallback);
        }
    }

    /**
     * Updates the file information on UI
     *
     * @param fileName file name
     * @param fileSize file length
     */
    private void updateFileInfo(Uri uri, final String fileName, final long fileSize, final int fileType) {
        mFileUri = uri;
        mFileNameView.setText(fileName);
        mFileSizeView.setText(getString(R.string.dfu_file_size_text, fileSize));
        final String extension = fileType == DfuService.TYPE_AUTO ? "(?i)ZIP" : "(?i)HEX|BIN"; // (?i) =  case insensitive
        final boolean statusOk = mStatusOk = MimeTypeMap.getFileExtensionFromUrl(fileName).matches(extension);
        mFileStatusView.setText(statusOk ? R.string.dfu_file_status_ok : R.string.dfu_file_status_invalid);
        mUploadButton.setEnabled(statusOk);
        // Ask the user for the Init packet file if HEX or BIN files are selected. In case of a ZIP file the Init packets should be included in the ZIP.
        if (statusOk) {
            onUploadClicked(null);
        }
    }

    public void onSelectFileClicked(final View view) {
        zipLauncher.launch(DfuService.MIME_TYPE_ZIP);
    }

    /**
     * Callback of UPDATE/CANCEL button on DfuActivity
     */
    public void onUploadClicked(final View view) {
        if (isDfuServiceRunning()) {
            showUploadCancelDialog();
            return;
        }
        // Check whether the selected file is a HEX file (we are just checking the extension)
        if (!mStatusOk) {
            Toast.makeText(this, R.string.dfu_file_status_invalid_message, Toast.LENGTH_LONG).show();
            return;
        }
        String address = mManager.dfuMode();
        startScan(address);
        showLoadingAutoDismiss(30000L);
    }

    private void dfuUpdated(BluetoothDevice device) {
        stopScan();
        hideLoading();
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFS_FILE_NAME, mFileNameView.getText().toString());
        editor.putString(PREFS_FILE_SIZE, mFileSizeView.getText().toString());
        editor.apply();

        showProgressBar();

        final boolean keepBond = false;
        final boolean forceDfu = false;
        final boolean enablePRNs = Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
        String value = String.valueOf(DfuServiceInitiator.DEFAULT_PRN_VALUE);
        int numberOfPackets;
        try {
            numberOfPackets = Integer.parseInt(value);
        } catch (final NumberFormatException e) {
            numberOfPackets = DfuServiceInitiator.DEFAULT_PRN_VALUE;
        }
        final DfuServiceInitiator starter = new DfuServiceInitiator(device.getAddress())
                .setUnsafeExperimentalButtonlessServiceInSecureDfuEnabled(true)
                .setPacketsReceiptNotificationsValue(numberOfPackets)
                .setPacketsReceiptNotificationsEnabled(enablePRNs)
                .setDeviceName(device.getName())
                .setKeepBond(keepBond)
                .setForceDfu(forceDfu);
        starter.setZip(mFileUri);
        Timber.v("dfuUpdated: %s - %s file uri:%s", device.getName(), device.getAddress(), mFileUri);
        starter.start(this, DfuService.class);
    }

    private void showUploadCancelDialog() {
        final LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        final Intent pauseAction = new Intent(DfuService.BROADCAST_ACTION);
        pauseAction.putExtra(DfuService.EXTRA_ACTION, DfuService.ACTION_PAUSE);
        manager.sendBroadcast(pauseAction);
        final UploadCancelFragment fragment = UploadCancelFragment.getInstance();
        fragment.show(getSupportFragmentManager(), TAG);
    }

    private void showProgressBar() {
        mTextPercentage.setVisibility(View.VISIBLE);
        mTextPercentage.setText(null);
        mTextUploading.setVisibility(View.VISIBLE);
        mTextUploading.setText("Updating...");
        mSelectFileButton.setEnabled(false);
        mUploadButton.setEnabled(true);
        mUploadButton.setText(R.string.dfu_action_upload_cancel);
    }

    private void onTransferCompleted() {
        clearUI(true);
        showToast(getString(R.string.dfu_success));
    }

    public void onUploadCanceled() {
        clearUI(false);
        showToast(getString(R.string.dfu_status_aborted));
    }

    @Override
    public void onCancelUpload() {
        mTextUploading.setText("Cancel Upload...");
        mTextPercentage.setText(null);
    }

    private void showErrorMessage(final String message) {
        clearUI(false);
        showToast("Update Failed: " + message);
    }

    private void clearUI(final boolean clearDevice) {
        mTextPercentage.setVisibility(View.INVISIBLE);
        mTextUploading.setVisibility(View.INVISIBLE);
        mSelectFileButton.setEnabled(true);
        mUploadButton.setEnabled(false);
        mUploadButton.setText(R.string.dfu_action_upload);
        mFileStatusView.setText(null);
        mFileNameView.setText(null);
        mFileSizeView.setText(null);
        mStatusOk = false;
    }

    private boolean isDfuServiceRunning() {
        final ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (DfuService.class.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }

}
