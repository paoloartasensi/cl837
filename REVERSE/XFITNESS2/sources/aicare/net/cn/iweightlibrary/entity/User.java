package aicare.net.cn.iweightlibrary.entity;

public class User {
    private int adc;
    private int age;
    private int height;
    private int id;
    private int sex;
    private int weight;

    public User() {
    }

    public int getAdc() {
        return this.adc;
    }

    public int getAge() {
        return this.age;
    }

    public int getHeight() {
        return this.height;
    }

    public int getId() {
        return this.id;
    }

    public int getSex() {
        return this.sex;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setAdc(int i2) {
        this.adc = i2;
    }

    public void setAge(int i2) {
        this.age = i2;
    }

    public void setHeight(int i2) {
        this.height = i2;
    }

    public void setId(int i2) {
        this.id = i2;
    }

    public void setSex(int i2) {
        this.sex = i2;
    }

    public void setWeight(int i2) {
        this.weight = i2;
    }

    public String toString() {
        return "User{id=" + this.id + ", sex=" + this.sex + ", age=" + this.age + ", height=" + this.height + ", weight=" + this.weight + ", adc=" + this.adc + '}';
    }

    public User(int i2, int i3, int i4, int i5, int i6, int i7) {
        this.id = i2;
        this.sex = i3;
        this.age = i4;
        this.height = i5;
        this.weight = i6;
        this.adc = i7;
    }
}
