package aicare.net.cn.iweightlibrary.entity;

public class BM09Data {
    private int adc;
    private String address;
    private int agreementType;
    private int algorithmId;
    private int bleType;
    private String bleVersion;
    private DecimalInfo decimalInfo;
    private int did;
    private boolean isStable;
    private double temp;
    private long timeMillis;
    private int unitType;
    private double weight;

    public BM09Data(int i2, int i3, DecimalInfo decimalInfo2, double d, int i4, double d2, int i5, int i6, String str, int i7, String str2, boolean z) {
        this.agreementType = i2;
        this.unitType = i3;
        this.decimalInfo = decimalInfo2;
        this.weight = d;
        this.adc = i4;
        this.temp = d2;
        this.algorithmId = i5;
        this.did = i6;
        this.bleVersion = str;
        this.bleType = i7;
        this.address = str2;
        this.isStable = z;
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

    public String getBleVersion() {
        return this.bleVersion;
    }

    public DecimalInfo getDecimalInfo() {
        return this.decimalInfo;
    }

    public int getDid() {
        return this.did;
    }

    public double getTemp() {
        return this.temp;
    }

    public long getTimeMillis() {
        return this.timeMillis;
    }

    public int getUnitType() {
        return this.unitType;
    }

    public double getWeight() {
        return this.weight;
    }

    public boolean isStable() {
        return this.isStable;
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

    public void setBleVersion(String str) {
        this.bleVersion = str;
    }

    public void setDecimalInfo(DecimalInfo decimalInfo2) {
        this.decimalInfo = decimalInfo2;
    }

    public void setDid(int i2) {
        this.did = i2;
    }

    public void setStable(boolean z) {
        this.isStable = z;
    }

    public void setTemp(double d) {
        this.temp = d;
    }

    public void setTimeMillis(long j2) {
        this.timeMillis = j2;
    }

    public void setUnitType(int i2) {
        this.unitType = i2;
    }

    public void setWeight(double d) {
        this.weight = d;
    }

    public String toString() {
        return "BM09Data{agreementType=" + this.agreementType + ", unitType=" + this.unitType + ", decimalInfo=" + this.decimalInfo + ", weight=" + this.weight + ", adc=" + this.adc + ", temp=" + this.temp + ", algorithmId=" + this.algorithmId + ", did=" + this.did + ", bleVersion='" + this.bleVersion + '\'' + ", bleType=" + this.bleType + ", address='" + this.address + '\'' + ", timeMillis=" + this.timeMillis + ", isStable=" + this.isStable + '}';
    }

    public BM09Data() {
    }
}
