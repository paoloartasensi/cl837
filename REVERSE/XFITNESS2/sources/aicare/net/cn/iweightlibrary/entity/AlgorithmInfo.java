package aicare.net.cn.iweightlibrary.entity;

import java.io.Serializable;

public class AlgorithmInfo implements Serializable {
    private static final long serialVersionUID = 9038641101047682770L;
    private int adc;
    private int algorithmId;
    private DecimalInfo decimalInfo;
    private double weight;

    public AlgorithmInfo(double d, int i2, int i3, DecimalInfo decimalInfo2) {
        this.weight = d;
        this.algorithmId = i2;
        this.adc = i3;
        this.decimalInfo = decimalInfo2;
    }

    public int getAdc() {
        return this.adc;
    }

    public int getAlgorithmId() {
        return this.algorithmId;
    }

    public DecimalInfo getDecimalInfo() {
        return this.decimalInfo;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setAdc(int i2) {
        this.adc = i2;
    }

    public void setAlgorithmId(int i2) {
        this.algorithmId = i2;
    }

    public void setDecimalInfo(DecimalInfo decimalInfo2) {
        this.decimalInfo = decimalInfo2;
    }

    public void setWeight(double d) {
        this.weight = d;
    }

    public String toString() {
        return "AlgorithmInfo{weight=" + this.weight + ", algorithmId=" + this.algorithmId + ", adc=" + this.adc + ", decimalInfo=" + this.decimalInfo + '}';
    }

    public AlgorithmInfo() {
    }
}
