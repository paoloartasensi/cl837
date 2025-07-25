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
