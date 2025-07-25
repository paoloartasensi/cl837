package com.chileaf.fitness.device.wear.cl880.model;

import java.io.Serializable;
import java.util.List;

public class SportHistory implements Serializable {
    public List<Integer> calories;
    public long stamp;
    public List<Integer> steps;

    public SportHistory(long j2, List<Integer> list, List<Integer> list2) {
        this.stamp = j2;
        this.steps = list;
        this.calories = list2;
    }

    public String toString() {
        return "SportHistory{stamp=" + this.stamp + ", step=" + this.steps + ", calorie=" + this.calories + '}';
    }
}
