package com.chileaf.fitness.model;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.chileaf.bluetooth.scanner.ScanResult;

public class DiscoveredDevice implements Parcelable {
    public static final Parcelable.Creator<DiscoveredDevice> CREATOR = new a();
    private final BluetoothDevice device;
    private int highestRssi = -128;
    private ScanResult lastScanResult;
    private String name;
    private int previousRssi;
    private int rssi;

    static class a implements Parcelable.Creator<DiscoveredDevice> {
        a() {
        }

        public DiscoveredDevice createFromParcel(Parcel parcel) {
            return new DiscoveredDevice(parcel);
        }

        public DiscoveredDevice[] newArray(int i2) {
            return new DiscoveredDevice[i2];
        }
    }

    public DiscoveredDevice(ScanResult scanResult) {
        this.device = scanResult.a();
        update(scanResult);
    }

    private int getLevel(int i2) {
        if (i2 <= 10) {
            return 0;
        }
        if (i2 <= 28) {
            return 1;
        }
        if (i2 <= 45) {
            return 2;
        }
        return i2 <= 65 ? 3 : 4;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj instanceof DiscoveredDevice) {
            return this.device.getAddress().equals(((DiscoveredDevice) obj).device.getAddress());
        }
        return super.equals(obj);
    }

    public String getAddress() {
        return this.device.getAddress();
    }

    public BluetoothDevice getDevice() {
        return this.device;
    }

    public int getHighestRssi() {
        return this.highestRssi;
    }

    public String getName() {
        String str = this.name;
        return str != null ? str : this.device.getName();
    }

    public int getRssi() {
        return this.rssi;
    }

    public ScanResult getScanResult() {
        return this.lastScanResult;
    }

    public boolean hasRssiLevelChanged() {
        return getLevel(this.rssi) != getLevel(this.previousRssi);
    }

    public int hashCode() {
        return this.device.hashCode();
    }

    public boolean matches(ScanResult scanResult) {
        return this.device.getAddress().equals(scanResult.a().getAddress());
    }

    public String toString() {
        return "DiscoveredDevice{device=" + this.device + ", lastScanResult=" + this.lastScanResult + ", name='" + this.name + '\'' + ", rssi=" + this.rssi + ", previousRssi=" + this.previousRssi + ", highestRssi=" + this.highestRssi + '}';
    }

    public void update(ScanResult scanResult) {
        this.lastScanResult = scanResult;
        this.name = scanResult.c() != null ? scanResult.c().b() : null;
        this.previousRssi = this.rssi;
        int b = scanResult.b();
        this.rssi = b;
        if (this.highestRssi < b) {
            this.highestRssi = b;
        }
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelable(this.device, i2);
        parcel.writeParcelable(this.lastScanResult, i2);
        parcel.writeString(this.name);
        parcel.writeInt(this.rssi);
        parcel.writeInt(this.previousRssi);
        parcel.writeInt(this.highestRssi);
    }

    public DiscoveredDevice(Parcel parcel) {
        this.device = (BluetoothDevice) parcel.readParcelable(BluetoothDevice.class.getClassLoader());
        this.lastScanResult = (ScanResult) parcel.readParcelable(ScanResult.class.getClassLoader());
        this.name = parcel.readString();
        this.rssi = parcel.readInt();
        this.previousRssi = parcel.readInt();
        this.highestRssi = parcel.readInt();
    }
}
