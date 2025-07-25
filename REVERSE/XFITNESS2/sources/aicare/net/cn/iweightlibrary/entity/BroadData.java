package aicare.net.cn.iweightlibrary.entity;

import java.util.Arrays;

public class BroadData {
    private String address;
    private int deviceType;
    private boolean isBright;
    private String name;
    private int rssi;
    private byte[] specificData;

    public static class AddressComparator {
        public String address;

        public boolean equals(Object obj) {
            if (obj instanceof BroadData) {
                return this.address.equals(((BroadData) obj).getAddress());
            }
            return super.equals(obj);
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof BroadData) {
            return this.address.equals(((BroadData) obj).getAddress());
        }
        return super.equals(obj);
    }

    public String getAddress() {
        return this.address;
    }

    public int getDeviceType() {
        return this.deviceType;
    }

    public String getName() {
        return this.name;
    }

    public int getRssi() {
        return this.rssi;
    }

    public byte[] getSpecificData() {
        return this.specificData;
    }

    public boolean isBright() {
        return this.isBright;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public void setBright(boolean z) {
        this.isBright = z;
    }

    public void setDeviceType(int i2) {
        this.deviceType = i2;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setRssi(int i2) {
        this.rssi = i2;
    }

    public void setSpecificData(byte[] bArr) {
        this.specificData = bArr;
    }

    public String toString() {
        return "BroadData{name='" + this.name + '\'' + ", address='" + this.address + '\'' + ", isBright=" + this.isBright + ", rssi=" + this.rssi + ", specificData=" + Arrays.toString(this.specificData) + ", deviceType=" + this.deviceType + '}';
    }
}
