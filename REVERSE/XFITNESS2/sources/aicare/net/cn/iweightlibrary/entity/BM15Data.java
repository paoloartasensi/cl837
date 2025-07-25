package aicare.net.cn.iweightlibrary.entity;

public class BM15Data {
    private int adc;
    private String address;
    private int agreementType;
    private int algorithmId;
    private int bleType;
    private int did;
    private double temp;
    private int unitType;
    private String version;
    private double weight;

    public BM15Data() {
    }

    public int getAdc() {
        return this.adc;
    }

    public String getAddress() {
        return this.address;
    }

    public int getAgreementType() {
        return this.agreementType;
    }

    public int getAlgorithmId() {
        return this.algorithmId;
    }

    public int getBleType() {
        return this.bleType;
    }

    public int getDid() {
        return this.did;
    }

    public double getTemp() {
        return this.temp;
    }

    public int getUnitType() {
        return this.unitType;
    }

    public String getVersion() {
        return this.version;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setAdc(int i2) {
        this.adc = i2;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public void setAgreementType(int i2) {
        this.agreementType = i2;
    }

    public void setAlgorithmId(int i2) {
        this.algorithmId = i2;
    }

    public void setBleType(int i2) {
        this.bleType = i2;
    }

    public void setDid(int i2) {
        this.did = i2;
    }

    public void setTemp(double d) {
        this.temp = d;
    }

    public void setUnitType(int i2) {
        this.unitType = i2;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public void setWeight(double d) {
        this.weight = d;
    }

    public String toString() {
        return "BM15Data{version='" + this.version + '\'' + ", agreementType=" + this.agreementType + ", unitType=" + this.unitType + ", weight=" + this.weight + ", temp=" + this.temp + ", adc=" + this.adc + ", algorithmId=" + this.algorithmId + ", did=" + this.did + ", bleType=" + this.bleType + ", address='" + this.address + '\'' + '}';
    }

    public BM15Data(String str, int i2, int i3, double d, double d2, int i4, int i5, int i6, int i7, String str2) {
        this.version = str;
        this.agreementType = i2;
        this.unitType = i3;
        this.weight = d;
        this.temp = d2;
        this.adc = i4;
        this.algorithmId = i5;
        this.did = i6;
        this.bleType = i7;
        this.address = str2;
    }
}
