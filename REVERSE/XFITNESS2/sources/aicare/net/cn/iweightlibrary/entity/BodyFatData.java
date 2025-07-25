package aicare.net.cn.iweightlibrary.entity;

import java.io.Serializable;

public class BodyFatData implements Serializable {
    private static final long serialVersionUID = 3240789953283964275L;
    private int adc;
    private int age;
    private double bfr;
    private double bm;
    private double bmi;
    private double bmr;
    private int bodyAge;
    private String date;
    private DecimalInfo decimalInfo;
    private int height;
    private int number;
    private double pp;
    private double rom;
    private int sex;
    private double sfr;
    private String time;
    private int uvi;
    private double vwc;
    private double weight;

    public BodyFatData() {
    }

    public int getAdc() {
        return this.adc;
    }

    public int getAge() {
        return this.age;
    }

    public double getBfr() {
        return this.bfr;
    }

    public double getBm() {
        return this.bm;
    }

    public double getBmi() {
        return this.bmi;
    }

    public double getBmr() {
        return this.bmr;
    }

    public int getBodyAge() {
        return this.bodyAge;
    }

    public String getDate() {
        return this.date;
    }

    public DecimalInfo getDecimalInfo() {
        return this.decimalInfo;
    }

    public int getHeight() {
        return this.height;
    }

    public int getNumber() {
        return this.number;
    }

    public double getPp() {
        return this.pp;
    }

    public double getRom() {
        return this.rom;
    }

    public int getSex() {
        return this.sex;
    }

    public double getSfr() {
        return this.sfr;
    }

    public String getTime() {
        return this.time;
    }

    public int getUvi() {
        return this.uvi;
    }

    public double getVwc() {
        return this.vwc;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setAdc(int i2) {
        this.adc = i2;
    }

    public void setAge(int i2) {
        this.age = i2;
    }

    public void setBfr(double d) {
        this.bfr = d;
    }

    public void setBm(double d) {
        this.bm = d;
    }

    public void setBmi(double d) {
        this.bmi = d;
    }

    public void setBmr(double d) {
        this.bmr = d;
    }

    public void setBodyAge(int i2) {
        this.bodyAge = i2;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public void setDecimalInfo(DecimalInfo decimalInfo2) {
        this.decimalInfo = decimalInfo2;
    }

    public void setHeight(int i2) {
        this.height = i2;
    }

    public void setNumber(int i2) {
        this.number = i2;
    }

    public void setPp(double d) {
        this.pp = d;
    }

    public void setRom(double d) {
        this.rom = d;
    }

    public void setSex(int i2) {
        this.sex = i2;
    }

    public void setSfr(double d) {
        this.sfr = d;
    }

    public void setTime(String str) {
        this.time = str;
    }

    public void setUvi(int i2) {
        this.uvi = i2;
    }

    public void setVwc(double d) {
        this.vwc = d;
    }

    public void setWeight(double d) {
        this.weight = d;
    }

    public String toString() {
        return "BodyFatData{date='" + this.date + '\'' + ", time='" + this.time + '\'' + ", weight=" + this.weight + ", bmi=" + this.bmi + ", bfr=" + this.bfr + ", sfr=" + this.sfr + ", uvi=" + this.uvi + ", rom=" + this.rom + ", bmr=" + this.bmr + ", bm=" + this.bm + ", vwc=" + this.vwc + ", bodyAge=" + this.bodyAge + ", pp=" + this.pp + ", number=" + this.number + ", sex=" + this.sex + ", age=" + this.age + ", height=" + this.height + ", adc=" + this.adc + ", decimalInfo=" + this.decimalInfo.toString() + '}';
    }

    public BodyFatData(String str, String str2, double d, double d2, double d3, double d4, int i2, double d5, double d6, double d7, double d8, int i3, double d9, int i4, int i5, int i6, int i7, int i8, DecimalInfo decimalInfo2) {
        this.date = str;
        this.time = str2;
        this.weight = d;
        this.bmi = d2;
        this.bfr = d3;
        this.sfr = d4;
        this.uvi = i2;
        this.rom = d5;
        this.bmr = d6;
        this.bm = d7;
        this.vwc = d8;
        this.bodyAge = i3;
        this.pp = d9;
        this.number = i4;
        this.sex = i5;
        this.age = i6;
        this.height = i7;
        this.adc = i8;
        this.decimalInfo = decimalInfo2;
    }
}
