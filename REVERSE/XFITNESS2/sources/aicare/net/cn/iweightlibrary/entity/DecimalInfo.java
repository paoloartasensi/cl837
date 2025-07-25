package aicare.net.cn.iweightlibrary.entity;

import java.io.Serializable;

public class DecimalInfo implements Serializable {
    private static final long serialVersionUID = -2380160019707581415L;
    private int kgDecimal;
    private int kgGraduation;
    private int lbDecimal;
    private int lbGraduation;
    private int sourceDecimal;
    private int stDecimal;

    public DecimalInfo() {
    }

    public int getKgDecimal() {
        return this.kgDecimal;
    }

    public int getKgGraduation() {
        return this.kgGraduation;
    }

    public int getLbDecimal() {
        return this.lbDecimal;
    }

    public int getLbGraduation() {
        return this.lbGraduation;
    }

    public int getSourceDecimal() {
        return this.sourceDecimal;
    }

    public int getStDecimal() {
        return this.stDecimal;
    }

    public void setKgDecimal(int i2) {
        this.kgDecimal = i2;
    }

    public void setKgGraduation(int i2) {
        this.kgGraduation = i2;
    }

    public void setLbDecimal(int i2) {
        this.lbDecimal = i2;
    }

    public void setLbGraduation(int i2) {
        this.lbGraduation = i2;
    }

    public void setSourceDecimal(int i2) {
        this.sourceDecimal = i2;
    }

    public void setStDecimal(int i2) {
        this.stDecimal = i2;
    }

    public String toString() {
        return "DecimalInfo{sourceDecimal=" + this.sourceDecimal + ", kgDecimal=" + this.kgDecimal + ", lbDecimal=" + this.lbDecimal + ", stDecimal=" + this.stDecimal + ", kgGraduation=" + this.kgGraduation + ", lbGraduation=" + this.lbGraduation + '}';
    }

    public DecimalInfo(int i2, int i3, int i4, int i5) {
        this.sourceDecimal = i2;
        this.kgDecimal = i3;
        this.lbDecimal = i4;
        this.stDecimal = i5;
    }

    public DecimalInfo(int i2, int i3, int i4, int i5, int i6, int i7) {
        this.sourceDecimal = i2;
        this.kgDecimal = i3;
        this.lbDecimal = i4;
        this.stDecimal = i5;
        this.kgGraduation = i6;
        this.lbGraduation = i7;
    }
}
