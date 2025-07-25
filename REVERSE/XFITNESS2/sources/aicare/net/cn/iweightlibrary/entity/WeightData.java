package aicare.net.cn.iweightlibrary.entity;

import java.io.Serializable;

public class WeightData implements Serializable {
    private static final long serialVersionUID = 5550040827044317791L;
    private int adc;
    private int algorithmType;
    private int cmdType;
    private DecimalInfo decimalInfo;
    private int deviceType;
    private double temp = Double.MAX_VALUE;
    private int unitType;
    private double weight;

    public WeightData() {
    }

    public int getAdc() {
        return this.adc;
    }

    public int getAlgorithmType() {
        return this.algorithmType;
    }

    public int getCmdType() {
        return this.cmdType;
    }

    public DecimalInfo getDecimalInfo() {
        return this.decimalInfo;
    }

    public int getDeviceType() {
        return this.deviceType;
    }

    public double getTemp() {
        return this.temp;
    }

    public int getUnitType() {
        return this.unitType;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setAdc(int i2) {
        this.adc = i2;
    }

    public void setAlgorithmType(int i2) {
        this.algorithmType = i2;
    }

    public void setCmdType(int i2) {
        this.cmdType = i2;
    }

    public void setDecimalInfo(DecimalInfo decimalInfo2) {
        this.decimalInfo = decimalInfo2;
    }

    public void setDeviceType(int i2) {
        this.deviceType = i2;
    }

    public void setTemp(double d) {
        this.temp = d;
    }

    public void setUnitType(int i2) {
        this.unitType = i2;
    }

    public void setWeight(double d) {
        this.weight = d;
    }

    public String toString() {
        return "WeightData{cmdType=" + this.cmdType + ", weight=" + this.weight + ", temp=" + this.temp + ", decimalInfo=" + this.decimalInfo + ", adc=" + this.adc + ", algorithmType=" + this.algorithmType + ", unitType=" + this.unitType + ", deviceType=" + this.deviceType + '}';
    }

    public WeightData(int i2, double d, double d2, DecimalInfo decimalInfo2) {
        this.cmdType = i2;
        this.weight = d;
        this.temp = d2;
        this.decimalInfo = decimalInfo2;
    }

    public WeightData(int i2, float f2, float f3, DecimalInfo decimalInfo2, int i3, int i4, int i5, int i6) {
        this.cmdType = i2;
        this.weight = (double) f2;
        this.temp = (double) f3;
        this.decimalInfo = decimalInfo2;
        this.adc = i3;
        this.algorithmType = i4;
        this.unitType = i5;
        this.deviceType = i6;
    }
}
