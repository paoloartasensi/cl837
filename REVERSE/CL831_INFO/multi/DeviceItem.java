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
