package com.chileaf.cl831.sample;

public class Sleep {
    private String date;
    private int notSleepingTime;
    private int lightSleepTime;
    private int deepSleepTime;
    private long totalTime;
    private int index;

    public Sleep(String date, int notSleepingTime, int lightSleepTime, int deepSleepTime, long totalTime, int index) {
        this.date = date;
        this.notSleepingTime = notSleepingTime;
        this.lightSleepTime = lightSleepTime;
        this.deepSleepTime = deepSleepTime;
        this.totalTime = totalTime;
        this.index = index;
    }

    public Sleep() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNotSleepingTime() {
        return notSleepingTime;
    }

    public void setNotSleepingTime(int notSleepingTime) {
        this.notSleepingTime = notSleepingTime;
    }

    public int getLightSleepTime() {
        return lightSleepTime;
    }

    public void setLightSleepTime(int lightSleepTime) {
        this.lightSleepTime = lightSleepTime;
    }

    public int getDeepSleepTime() {
        return deepSleepTime;
    }

    public void setDeepSleepTime(int deepSleepTime) {
        this.deepSleepTime = deepSleepTime;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return
                "["+date +"]{"+
                "\nnot Sleeping Time = " + notSleepingTime +
                "(minute)\nlight Sleep Time = " + lightSleepTime +
                "(minute)\ndeep Sleep Time = " + deepSleepTime +
                "(minute)\ntotal Time = " + totalTime+"(minute)}";
    }
}
