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
