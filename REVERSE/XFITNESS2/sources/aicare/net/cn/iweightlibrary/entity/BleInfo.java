package aicare.net.cn.iweightlibrary.entity;

public class BleInfo {
    private String address;
    private int isCheck;
    private String name;
    private String version;

    public String getAddress() {
        return this.address;
    }

    public int getIsCheck() {
        return this.isCheck;
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public void setIsCheck(int i2) {
        this.isCheck = i2;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setVersion(String str) {
        this.version = str;
    }
}
