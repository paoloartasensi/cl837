
// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample
// File: HistoryDetailActivity.java

package com.chileaf.cl831.sample;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;

import com.android.chileaf.model.HistoryOfHeartRate;
import com.android.chileaf.model.HistoryOfRespiratoryRate;
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


// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample
// File: ScannerFragment.java

package com.chileaf.cl831.sample;

import android.Manifest;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.android.chileaf.WearManager;
import no.nordicsemi.android.support.v18.scanner.ScanResult;
import com.android.chileaf.fitness.common.FilterScanCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import timber.log.Timber;


/**
 * ScannerFragment class scan required BLE devices and shows them in a list. This class scans and filter
 * devices with standard BLE Service UUID and devices with custom BLE Service UUID. It contains a
 * list and a button to scan/cancel. There is a interface {@link OnDeviceSelectedListener} which is
 * implemented by activity in order to receive selected device. The scanning will continue to scan
 * for 5 seconds and then stop.
 */
public class ScannerFragment extends DialogFragment {

    private static final long SCAN_DURATION = 15000;
    private static final int REQUEST_PERMISSION_REQ_CODE = 34;
    //    private static final String[] FILTER_NAMES = new String[]{"CL831", "CL833", "CL880", "Buff"};
    //    private static final String[] FILTER_NAMES = null;

    private BluetoothAdapter mBluetoothAdapter;
    private OnDeviceSelectedListener mListener;
    private DeviceListAdapter mAdapter;
    private final Handler mHandler = new Handler();

    private Button mScanButton;

    private View mPermissionRationale;

    private boolean mIsScanning = false;
    private WearManager mManager;

    public static ScannerFragment getInstance() {
        final ScannerFragment fragment = new ScannerFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Interface required to be implemented by activity.
     */
    public interface OnDeviceSelectedListener {
        /**
         * Fired when user selected the device.
         *
         * @param device the device to connect to
         * @param name   the device name. Unfortunately on some devices {@link BluetoothDevice#getName()}
         *               always returns <code>null</code>, i.e. Sony Xperia Z1 (C6903) with Android 4.3.
         *               The name has to be parsed manually form the Advertisement packet.
         */
        void onDeviceSelected(final BluetoothDevice device, final String name);

        /**
         * Fired when scanner dialog has been cancelled without selecting a device.
         */
        default void onDialogCanceled() {
        }
    }

    /**
     * This will make sure that {@link OnDeviceSelectedListener} interface is implemented by activity.
     */
    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnDeviceSelectedListener) context;
        } catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDeviceSelectedListener");
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final BluetoothManager manager = (BluetoothManager) requireContext().getSystemService(Context.BLUETOOTH_SERVICE);
        if (manager != null) {
            mBluetoothAdapter = manager.getAdapter();
        }
    }

    @Override
    public void onDestroyView() {
        stopScan();
        super.onDestroyView();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        mManager = WearManager.getInstance(requireContext());
        final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        final View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_device_scan, null);
        final ListView listview = dialogView.findViewById(android.R.id.list);

        listview.setEmptyView(dialogView.findViewById(android.R.id.empty));
        listview.setAdapter(mAdapter = new DeviceListAdapter(getActivity()));

        builder.setTitle(R.string.scanner_title);
        final AlertDialog dialog = builder.setView(dialogView).create();
        listview.setOnItemClickListener((parent, view, position, id) -> {
            stopScan();
            dialog.dismiss();
            final ExtendedBluetoothDevice d = (ExtendedBluetoothDevice) mAdapter.getItem(position);
            mListener.onDeviceSelected(d.device, d.name);
        });

        mPermissionRationale = dialogView.findViewById(R.id.permission_rationale); // this is not null only on API23+

        mScanButton = dialogView.findViewById(R.id.action_cancel);
        mScanButton.setOnClickListener(v -> {
            if (v.getId() == R.id.action_cancel) {
                if (mIsScanning) {
                    dialog.cancel();
                } else {
                    startScan();
                }
            }
        });

        addBoundDevices();
        if (savedInstanceState == null)
            startScan();
        return dialog;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        mListener.onDialogCanceled();
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, final @NonNull String[] permissions, final @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_REQ_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // We have been granted the Manifest.permission.ACCESS_COARSE_LOCATION permission. Now we may proceed with scanning.
                    startScan();
                } else {
                    mPermissionRationale.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), R.string.no_required_permission, Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    /**
     * Scan for 5 seconds and then stop scanning when a BluetoothLE device is found then mLEScanCallback
     * is activated This will perform regular scan for custom BLE Service UUID and then filter out.
     * using class ScannerServiceParser
     */
    private void startScan() {
        // Since Android 6.0 we need to obtain either Manifest.permission.ACCESS_COARSE_LOCATION or Manifest.permission.ACCESS_FINE_LOCATION to be able to scan for
        // Bluetooth LE devices. This is related to beacons as proximity devices.
        // On API older than Marshmallow the following code does nothing.
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // When user pressed Deny and still wants to use this functionality, show the rationale
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) && mPermissionRationale.getVisibility() == View.GONE) {
                mPermissionRationale.setVisibility(View.VISIBLE);
                return;
            }

            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION_REQ_CODE);
            return;
        }

        // Hide the rationale message, we don't need it anymore.
        if (mPermissionRationale != null)
            mPermissionRationale.setVisibility(View.GONE);

        mAdapter.clearDevices();
        mScanButton.setText(R.string.scanner_action_cancel);

        // mManager.setFilterNames(FILTER_NAMES);
        mManager.startScan(mScanCallback);

        mIsScanning = true;
        mHandler.postDelayed(() -> {
            if (mIsScanning) {
                stopScan();
            }
        }, SCAN_DURATION);
    }

    /**
     * Stop scan if user tap Cancel button
     */
    private void stopScan() {
        if (mIsScanning) {
            mScanButton.setText(R.string.scanner_action_scan);
            WearManager.getInstance(getActivity()).stopScan();
            mIsScanning = false;
        }
    }

    private FilterScanCallback mScanCallback = new FilterScanCallback() {
        @Override
        public void onFilterScanResults(@NonNull List<ScanResult> results) {
            mAdapter.update(results);
        }
    };

    private void addBoundDevices() {
        final Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        mAdapter.addBondedDevices(devices);
    }

    private static class DeviceListAdapter extends BaseAdapter {
        private static final int TYPE_TITLE = 0;
        private static final int TYPE_ITEM = 1;
        private static final int TYPE_EMPTY = 2;

        private final ArrayList<ExtendedBluetoothDevice> mListBondedValues = new ArrayList<>();
        private final ArrayList<ExtendedBluetoothDevice> mListValues = new ArrayList<>();
        private final Context mContext;

        public DeviceListAdapter(final Context context) {
            mContext = context;
        }

        /**
         * Sets a list of bonded devices.
         *
         * @param devices list of bonded devices.
         */
        private void addBondedDevices(final Set<BluetoothDevice> devices) {
            final List<ExtendedBluetoothDevice> bondedDevices = mListBondedValues;
            for (BluetoothDevice device : devices) {
//                if (matchDeviceName(device.getName())) {
                    bondedDevices.add(new ExtendedBluetoothDevice(device));
//                }
            }
            notifyDataSetChanged();
        }

//        private boolean matchDeviceName(String name) {
//            if (FILTER_NAMES == null) {
//                return true;
//            } else {
//                if (name != null && !TextUtils.isEmpty(name)) {
//                    for (String filterName : FILTER_NAMES) {
//                        if (name.toUpperCase().startsWith(filterName)) {
//                            return true;
//                        }
//                    }
//                }
//                return false;
//            }
//        }

        /**
         * Updates the list of not bonded devices.
         *
         * @param results list of results from the scanner
         */
        private void update(final List<ScanResult> results) {
            for (final ScanResult result : results) {
                Timber.e(result.toString());
                final ExtendedBluetoothDevice device = findDevice(result);
                if (device == null) {
                    mListValues.add(new ExtendedBluetoothDevice(result));
                } else if (result.getScanRecord() != null) {
                    device.name = result.getScanRecord().getDeviceName();
                    device.rssi = result.getRssi();
                }
            }
            notifyDataSetChanged();
        }

        private ExtendedBluetoothDevice findDevice(final ScanResult result) {
            for (final ExtendedBluetoothDevice device : mListBondedValues)
                if (device.matches(result))
                    return device;
            for (final ExtendedBluetoothDevice device : mListValues)
                if (device.matches(result))
                    return device;
            return null;
        }

        private void clearDevices() {
            mListValues.clear();
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            final int bondedCount = mListBondedValues.size() + 1; // 1 for the title
            final int availableCount = mListValues.isEmpty() ? 2 : mListValues.size() + 1; // 1 for title, 1 for empty text
            if (bondedCount == 1)
                return availableCount;
            return bondedCount + availableCount;
        }

        @Override
        public Object getItem(int position) {
            final int bondedCount = mListBondedValues.size() + 1; // 1 for the title
            if (mListBondedValues.isEmpty()) {
                if (position == 0)
                    return R.string.scanner_subtitle_not_bonded;
                else
                    return mListValues.get(position - 1);
            } else {
                if (position == 0)
                    return R.string.scanner_subtitle_bonded;
                if (position < bondedCount)
                    return mListBondedValues.get(position - 1);
                if (position == bondedCount)
                    return R.string.scanner_subtitle_not_bonded;
                return mListValues.get(position - bondedCount - 1);
            }
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            return getItemViewType(position) == TYPE_ITEM;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0)
                return TYPE_TITLE;

            if (!mListBondedValues.isEmpty() && position == mListBondedValues.size() + 1)
                return TYPE_TITLE;

            if (position == getCount() - 1 && mListValues.isEmpty())
                return TYPE_EMPTY;

            return TYPE_ITEM;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View oldView, ViewGroup parent) {
            final LayoutInflater inflater = LayoutInflater.from(mContext);
            final int type = getItemViewType(position);

            View view = oldView;
            switch (type) {
                case TYPE_EMPTY:
                    if (view == null) {
                        view = new TextView(mContext);
                        final TextView empty = (TextView) view;
                        empty.setGravity(Gravity.CENTER_HORIZONTAL);
                        empty.setText(mContext.getString(R.string.scanner_empty));
                    }
                    break;
                case TYPE_TITLE:
                    if (view == null) {
                        view = new TextView(mContext);
                    }
                    final TextView title = (TextView) view;
                    title.setGravity(Gravity.CENTER_HORIZONTAL);
                    title.setText((Integer) getItem(position));
                    break;
                default:
                    if (view == null) {
                        view = inflater.inflate(R.layout.item_device_list, parent, false);
                        final ViewHolder holder = new ViewHolder();
                        holder.name = view.findViewById(R.id.name);
                        holder.address = view.findViewById(R.id.address);
                        holder.signal = view.findViewById(R.id.rssi);
                        view.setTag(holder);
                    }

                    final ExtendedBluetoothDevice device = (ExtendedBluetoothDevice) getItem(position);
                    final ViewHolder holder = (ViewHolder) view.getTag();
                    final String name = device.name;
                    holder.name.setText(name != null ? name : mContext.getString(R.string.not_available));
                    holder.address.setText(device.device.getAddress());
                    if (!device.isBonded || device.rssi != ExtendedBluetoothDevice.NO_RSSI) {
                        holder.signal.setText(device.rssi + "dBm");
                        holder.signal.setVisibility(View.VISIBLE);
                    } else {
                        holder.signal.setVisibility(View.GONE);
                    }
                    break;
            }
            return view;
        }

        private class ViewHolder {
            private TextView name;
            private TextView address;
            private TextView signal;
        }
    }

    private static class ExtendedBluetoothDevice {

        private static final int NO_RSSI = -1000;

        private String name;
        private int rssi;
        private boolean isBonded;
        private final BluetoothDevice device;

        private ExtendedBluetoothDevice(final ScanResult scanResult) {
            this.device = scanResult.getDevice();
            this.name = scanResult.getScanRecord() != null ? scanResult.getScanRecord().getDeviceName() : null;
            this.rssi = scanResult.getRssi();
            this.isBonded = false;
        }

        private ExtendedBluetoothDevice(final BluetoothDevice device) {
            this.device = device;
            this.name = device.getName();
            this.rssi = NO_RSSI;
            this.isBonded = true;
        }

        private boolean matches(final ScanResult scanResult) {
            return device.getAddress().equals(scanResult.getDevice().getAddress());
        }
    }
}


// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample
// File: HistorySleepActivity.java

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

// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample
// File: History3DAdapter.java

package com.chileaf.cl831.sample;

import com.android.chileaf.model.HistoryOf3D;
import com.android.chileaf.model.HistoryOfSport;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class History3DAdapter extends BaseQuickAdapter<HistoryOf3D, BaseViewHolder> {

    private SimpleDateFormat mDateFormat;

    public History3DAdapter() {
        super(R.layout.item_history, new ArrayList<>());
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryOf3D history) {
        StringBuilder item = new StringBuilder();
//        String date = mDateFormat.format(new Date(history.stamp));
        item
//                .append("Date time:").append(date).append("\n")
                .append("X,Y,Z:[ ")
                .append(history.accX).append(", ")
//                .append("AccY:")
                .append(history.accY).append(", ")
//                .append("AccZ:")
                .append(history.accZ).append(" ]");
        helper.setText(R.id.tv_history, item.toString());
    }

}


// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample
// File: UserInfoActivity.java

package com.chileaf.cl831.sample;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.android.chileaf.fitness.callback.UserInfoCallback;

/**
 * User information
 */
public class UserInfoActivity extends BaseActivity implements UserInfoCallback {

    private EditText mEtAge;
    private EditText mEtHeight;
    private EditText mEtWeight;
    private EditText mEtUserId;
    private RadioGroup mRgSex;
    private RadioButton mRbMale;
    private RadioButton mRbFemale;

    private int mAge;//age
    private int mSex;//sex
    private int mHeight;//height
    private int mWeight;//weigh
    private long mUserId;//user id (phone number)


    @Override
    protected int layoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView() {
        mEtAge = findViewById(R.id.et_age);
        mEtHeight = findViewById(R.id.et_height);
        mEtWeight = findViewById(R.id.et_weight);
        mEtUserId = findViewById(R.id.et_user_id);
        mRgSex = findViewById(R.id.rg_sex);
        mRbMale = findViewById(R.id.rb_male);
        mRbFemale = findViewById(R.id.rb_female);

        mEtAge.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mAge = Integer.parseInt(s.toString());
                }
            }
        });
        mRgSex.setOnCheckedChangeListener((group, checkedId) -> mSex = checkedId == R.id.rb_male ? 1 : 0);
        mEtHeight.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mHeight = Integer.parseInt(s.toString());
                }
            }
        });

        mEtWeight.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mWeight = Integer.parseInt(s.toString());
                }
            }
        });

        mEtUserId.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mUserId = Long.parseLong(s.toString());
                }
            }
        });
        //get user information
        findViewById(R.id.btn_get_user).setOnClickListener(view -> mManager.getUserInfo());
        //set user information
        findViewById(R.id.btn_set_user).setOnClickListener(view -> {
            if (isEmpty(mEtAge, mAge)) {
                showToast("Please input the correct age");
            } else if (isEmpty(mEtHeight, mHeight)) {
                showToast("Please input the correct height");
            } else if (isEmpty(mEtWeight, mWeight)) {
                showToast("Please input the correct weigh");
            } else if (isEmpty(mEtUserId, mUserId)) {
                showToast("Please input the correct user id");
            } else {
                mManager.setUserInfo(mAge, mSex, mWeight, mHeight, mUserId);
                showToast("Set success");
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setTitle("User information");
        mManager.addUserInfoCallback(this);
    }

    private boolean isEmpty(EditText view, long value) {
        return view.getText().toString().isEmpty() || value == 0;
    }

    @Override
    public void onUserInfoReceived(@NonNull BluetoothDevice device, int age, int sex, int weight, int height, long userId) {
        runOnUiThread(() -> {
            mEtAge.setText(String.valueOf(age));
            mEtHeight.setText(String.valueOf(height));
            mEtWeight.setText(String.valueOf(weight));
            mEtUserId.setText(String.valueOf(userId));
            mRbMale.setChecked(sex == 1);
            mRbFemale.setChecked(sex == 0);
        });
    }

    private abstract static class SimpleTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}


// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample
// File: LoadingDialog.java

package com.chileaf.cl831.sample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;


public class LoadingDialog extends AppCompatDialog {

    private View mRoot;

    public LoadingDialog(Builder builder) {
        this(builder, builder.context, R.style.DialogStyle);
    }

    public LoadingDialog(Builder builder, Context context, int theme) {
        super(context, theme);
        mRoot = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        TextView tvLoading = mRoot.findViewById(R.id.tv_loading);
        if (!TextUtils.isEmpty(builder.message)) {
            tvLoading.setVisibility(View.VISIBLE);
            tvLoading.setText(builder.message);
        } else {
            tvLoading.setVisibility(View.GONE);
        }
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mRoot);
    }

    public static Builder Builder(Activity activity) {
        return new Builder(activity);
    }

    public static final class Builder {
        private Context context;
        private String message;

        private Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public LoadingDialog build() {
            return new LoadingDialog(this);
        }
    }
}


// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample
// File: IntervalStepAdapter.java

package com.chileaf.cl831.sample;

import com.android.chileaf.model.IntervalStep;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class IntervalStepAdapter extends BaseQuickAdapter<IntervalStep, BaseViewHolder> {

    private SimpleDateFormat mDateFormat;

    public IntervalStepAdapter() {
        super(R.layout.item_history, new ArrayList<>());
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    @Override
    protected void convert(BaseViewHolder helper, IntervalStep interval) {
        StringBuilder item = new StringBuilder();
        String date = mDateFormat.format(new Date(interval.stamp));
        item.append("Date time : ").append(date).append("\n")
                .append("Steps : ").append(interval.steps).append("\n");
        helper.setText(R.id.tv_history, item.toString());
    }

}


// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample
// File: MainActivity.java

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


// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample
// File: HistoryRecordAdapter.java

package com.chileaf.cl831.sample;

import android.widget.TextView;

import com.android.chileaf.model.HistoryOfRecord;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HistoryRecordAdapter extends BaseQuickAdapter<HistoryOfRecord, BaseViewHolder> {

    private SimpleDateFormat mDateFormat;

    public HistoryRecordAdapter() {
        super(R.layout.item_history, new ArrayList<>());
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryOfRecord history) {
        String date = mDateFormat.format(new Date(history.record));
        TextView tvHistory = helper.getView(R.id.tv_history);
        tvHistory.setTextSize(20);
        tvHistory.setText(date);
    }
}


// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample
// File: SportHealthActivity.java

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

// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample
// File: BaseActivity.java

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


// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample
// File: Sleep.java

package com.chileaf.cl831.sample;

public class Sleep {
    private String date;
    private int notSleepingTime;
    private int lightSleepTime;
    private int deepSleepTime;
    private long totalTime;
    private int index;

    public Sleep(String date, int notSleepingTime, int lightSleepTime, int deepSleepTime, long totalTime, int index) {
        this.date = date;
        this.notSleepingTime = notSleepingTime;
        this.lightSleepTime = lightSleepTime;
        this.deepSleepTime = deepSleepTime;
        this.totalTime = totalTime;
        this.index = index;
    }

    public Sleep() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNotSleepingTime() {
        return notSleepingTime;
    }

    public void setNotSleepingTime(int notSleepingTime) {
        this.notSleepingTime = notSleepingTime;
    }

    public int getLightSleepTime() {
        return lightSleepTime;
    }

    public void setLightSleepTime(int lightSleepTime) {
        this.lightSleepTime = lightSleepTime;
    }

    public int getDeepSleepTime() {
        return deepSleepTime;
    }

    public void setDeepSleepTime(int deepSleepTime) {
        this.deepSleepTime = deepSleepTime;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return
                "["+date +"]{"+
                "\nnot Sleeping Time = " + notSleepingTime +
                "(minute)\nlight Sleep Time = " + lightSleepTime +
                "(minute)\ndeep Sleep Time = " + deepSleepTime +
                "(minute)\ntotal Time = " + totalTime+"(minute)}";
    }
}


// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample
// File: App.java

package com.chileaf.cl831.sample;

import android.app.Application;
import android.os.Build;

import no.nordicsemi.android.dfu.DfuServiceInitiator;
import timber.log.Timber;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DfuServiceInitiator.createDfuNotificationChannel(this);
        }
    }
}


// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample
// File: TemperatureActivity.java

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

// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample
// File: BloodOxygenActivity.java

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

// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample
// File: HistoryActivity.java

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


// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample
// File: HistorySportAdapter.java

package com.chileaf.cl831.sample;

import com.android.chileaf.model.HistoryOfSport;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HistorySportAdapter extends BaseQuickAdapter<HistoryOfSport, BaseViewHolder> {

    private SimpleDateFormat mDateFormat;

    public HistorySportAdapter() {
        super(R.layout.item_history, new ArrayList<>());
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryOfSport history) {
        StringBuilder item = new StringBuilder();
        String date = mDateFormat.format(new Date(history.startTime));
        item.append("Date time:").append(date).append("\n")
                .append("Step:").append(history.step).append("步\n")
                .append("Calorie:").append(String.format("%.1f", history.calorie / 10f)).append("CAL");
        helper.setText(R.id.tv_history, item.toString());
    }

}


// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample\dfu
// File: UploadCancelFragment.java

package com.chileaf.cl831.sample.dfu;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.chileaf.cl831.sample.R;


/**
 * When cancel button is pressed during uploading this fragment shows uploading cancel dialog
 */
public class UploadCancelFragment extends DialogFragment {

    private static final String TAG = "UploadCancelFragment";

    private CancelFragmentListener mListener;

    public interface CancelFragmentListener {
        void onCancelUpload();
    }

    public static UploadCancelFragment getInstance() {
        return new UploadCancelFragment();
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        try {
            mListener = (CancelFragmentListener) context;
        } catch (final ClassCastException e) {
            Log.d(TAG, "The parent Activity must implement CancelFragmentListener interface");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity()).setTitle(R.string.dfu_confirmation_dialog_title).setMessage(R.string.dfu_upload_dialog_cancel_message).setCancelable(false)
                .setPositiveButton(R.string.yes, (dialog, whichButton) -> {
                    final LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getActivity());
                    final Intent pauseAction = new Intent(DfuService.BROADCAST_ACTION);
                    pauseAction.putExtra(DfuService.EXTRA_ACTION, DfuService.ACTION_ABORT);
                    manager.sendBroadcast(pauseAction);
                    mListener.onCancelUpload();
                }).setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel()).create();
    }

    @Override
    public void onCancel(final DialogInterface dialog) {
        final LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getActivity());
        final Intent pauseAction = new Intent(DfuService.BROADCAST_ACTION);
        pauseAction.putExtra(DfuService.EXTRA_ACTION, DfuService.ACTION_RESUME);
        manager.sendBroadcast(pauseAction);
    }
}


// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample\dfu
// File: DfuService.java

package com.chileaf.cl831.sample.dfu;

import android.app.Activity;

import no.nordicsemi.android.dfu.DfuBaseService;

public class DfuService extends DfuBaseService {

    @Override
    protected Class<? extends Activity> getNotificationTarget() {
        /*
         * As a target activity the NotificationActivity is returned, not the MainActivity. This is because the notification must create a new task:
         *
         * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         *
         * when user press it. Using NotificationActivity we can check whether the new activity is a root activity (that means no other activity was open before)
         * or that there is other activity already open. In the later case the notificationActivity will just be closed. System will restore the previous activity.
         * However if the application has been closed during upload and user click the notification a NotificationActivity will be launched as a root activity.
         * It will create and start the main activity and terminate itself.
         *
         * This method may be used to restore the target activity in case the application was closed or is open. It may also be used to recreate an activity
         * history (see NotificationActivity).
         */
        return NotificationActivity.class;
    }

    @Override
    protected boolean isDebug() {
        return true;
    }
}


// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample\dfu
// File: NotificationActivity.java

package com.chileaf.cl831.sample.dfu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.chileaf.cl831.sample.MainActivity;


public class NotificationActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// If this activity is the root activity of the task, the app is not running
		if (isTaskRoot()) {
			// Start the app before finishing
			final Intent parentIntent = new Intent(this, MainActivity.class);
			parentIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			final Intent startAppIntent = new Intent(this, DfuActivity.class);
			startAppIntent.putExtras(getIntent().getExtras());
			startActivities(new Intent[] { parentIntent, startAppIntent });
		}
		// Now finish, which will drop the user in to the activity that was at the top
		//  of the task stack
		finish();
	}
}

// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample\dfu
// File: PermissionRationaleFragment.java

package com.chileaf.cl831.sample.dfu;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.chileaf.cl831.sample.R;


public class PermissionRationaleFragment extends DialogFragment {

    private static final String ARG_PERMISSION = "ARG_PERMISSION";
    private static final String ARG_TEXT = "ARG_TEXT";

    private PermissionDialogListener mListener;

    public interface PermissionDialogListener {
        void onRequestPermission(final String permission);
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        if (context instanceof PermissionDialogListener) {
            mListener = (PermissionDialogListener) context;
        } else {
            throw new IllegalArgumentException("The parent activity must impelemnt PermissionDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static PermissionRationaleFragment getInstance(final int aboutResId, final String permission) {
        final PermissionRationaleFragment fragment = new PermissionRationaleFragment();

        final Bundle args = new Bundle();
        args.putInt(ARG_TEXT, aboutResId);
        args.putString(ARG_PERMISSION, permission);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final Bundle args = getArguments();
        final StringBuilder text = new StringBuilder(getString(args.getInt(ARG_TEXT)));
        return new AlertDialog.Builder(getActivity()).setTitle(R.string.permission_required).setMessage(text)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, (dialog, which) -> mListener.onRequestPermission(args.getString(ARG_PERMISSION))).create();
    }
}


// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample\dfu
// File: DfuActivity.java

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


// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample\multi
// File: DeviceAdapter.java

package com.chileaf.cl831.sample.multi;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.chileaf.cl831.sample.R;

import java.util.ArrayList;

@SuppressLint({"SetTextI18n", "MissingPermission"})
public class DeviceAdapter extends BaseQuickAdapter<DeviceItem, BaseViewHolder> {

    public DeviceAdapter() {
        super(R.layout.item_device, new ArrayList<>());
        addChildClickViewIds(R.id.btn_disconnect);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceItem item) {
        TextView tvName = helper.getView(R.id.name);
        TextView tvAddress = helper.getView(R.id.address);
        TextView tvHeart = helper.getView(R.id.heart);
        TextView tvStep = helper.getView(R.id.step);
        TextView tvDistance = helper.getView(R.id.distance);
        TextView tvCalorie = helper.getView(R.id.calorie);
        tvName.setText(item.device.getName());
        tvAddress.setText(item.device.getAddress());
        tvHeart.setText(item.heartRate + "BPM");
        tvStep.setText(item.step + "steps");
        tvDistance.setText(item.distance / 100f + "m");
        tvCalorie.setText(item.calorie / 10f + "KCal");
    }

    public void addDevice(DeviceItem item) {
        getData().add(item);
        notifyDataSetChanged();
    }

    public void removeDevice(BluetoothDevice device) {
        DeviceItem item = getItem(device);
        if (item != null) {
            getData().remove(item);
            notifyDataSetChanged();
        }
    }

    public DeviceItem getItem(BluetoothDevice device) {
        for (DeviceItem item : getData()) {
            if (item.device.getAddress() == device.getAddress()) {
                return item;
            }
        }
        return null;
    }

    public void onSoftwareVersion(@NonNull BluetoothDevice device, String software) {
        DeviceItem item = getItem(device);
        if (item != null) {
            item.version = software;
            notifyItemChanged(getData().indexOf(item));
        }
    }

    public void onBatteryLevelChanged(@NonNull final BluetoothDevice device, final int batteryLevel) {
        DeviceItem item = getItem(device);
        if (item != null) {
            item.battery = batteryLevel;
            notifyItemChanged(getData().indexOf(item));
        }
    }

    public void onHeartRateMeasurementReceived(@NonNull BluetoothDevice device, int heartRate) {
        DeviceItem item = getItem(device);
        if (item != null) {
            item.heartRate = heartRate;
            notifyItemChanged(getData().indexOf(item));
        }
    }

    public void onSportReceived(@NonNull BluetoothDevice device, int step, int distance, int calorie) {
        DeviceItem item = getItem(device);
        if (item != null) {
            item.step = step;
            item.distance = distance;
            item.calorie = calorie;
            notifyItemChanged(getData().indexOf(item));
        }
    }

}


// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample\multi
// File: DeviceItem.java

package com.chileaf.cl831.sample.multi;

import android.bluetooth.BluetoothDevice;

public class DeviceItem {

    public BluetoothDevice device;
    public String version;
    public int heartRate;
    public int step;
    public int distance;
    public int calorie;
    public int battery;

    public DeviceItem(BluetoothDevice device) {
        this.device = device;
    }

}


// Directory: app
// Subdirectory: src\main\java\com\chileaf\cl831\sample\multi
// File: MultiConnectActivity.java

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

