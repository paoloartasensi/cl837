package com.github.mikephil.charting.data;

import android.graphics.Color;
import com.github.mikephil.charting.data.Entry;
import h.a.a.a.e.b.b;
import java.util.List;

/* compiled from: BarLineScatterCandleBubbleDataSet */
public abstract class c<T extends Entry> extends DataSet<T> implements b<T> {
    protected int x = Color.rgb(255, 187, 115);

    public c(List<T> list, String str) {
        super(list, str);
    }

    public int K() {
        return this.x;
    }

    public void h(int i2) {
        this.x = i2;
    }
}
